package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class LiftSubsystem extends SubsystemBase {

    private MotorEx liftRight, liftLeft;

    public static double kP = 0.01;
    public static double kI = 0;
    public static double kD = 0.0003;
    public static double kG = 0.1;
    public static double maxVelocity = 4000;
    public static double maxAcceleration = 4000;
    public static int tolerance = 2;
    private double goalRight;
    private double goalLeft;



    private final ProfiledPIDController controllerLeft = new ProfiledPIDController(kP, kI, kD,
            new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration));
    private final ProfiledPIDController controllerRight = new ProfiledPIDController(kP, kI, kD,
            new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration));

    private Telemetry telemetry;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();



    public static double manualPower = 300; //these are in ticks


    public LiftSubsystem(MotorEx liftRight, MotorEx liftLeft,Telemetry telemetry) {
        this.liftRight = liftRight;
        this.liftLeft = liftLeft;
        this.telemetry = telemetry;

        controllerLeft.setTolerance(tolerance);
        controllerRight.setTolerance(tolerance);



    }


    public void lift(double finalPos) {
        //Set PID Goal
        goalRight = finalPos*manualPower+liftRight.getCurrentPosition();
        goalLeft  = finalPos*manualPower+liftLeft.getCurrentPosition();
        controllerRight.setGoal(goalRight);
        controllerLeft.setGoal(goalLeft);

        //Calculate PID ouput
        double rightPower = -controllerRight.calculate(liftRight.getCurrentPosition())+kG;
        double leftPower = -controllerLeft.calculate(liftLeft.getCurrentPosition())+kG;

        //Check if the tolerance is met
        if (controllerRight.atGoal()) rightPower = 0;
        if (controllerLeft.atGoal()) leftPower = 0;

        //Set power based on PID output
        liftRight.set(rightPower);
        liftLeft.set(rightPower);

    }

    public void stabilize(){
        liftRight.set(0);
        liftLeft.set(0);
    }

    public void PIDtelem(){
        telemetry.addData("Goal Pos",goalRight);
        telemetry.addData("Actual Pos",liftRight.getCurrentPosition());
        telemetry.update();

        dashboardTelemetry.addData("Goal Pos",goalRight);
        dashboardTelemetry.addData("Actual Pos",liftRight.getCurrentPosition());
        dashboardTelemetry.update();
    }

}