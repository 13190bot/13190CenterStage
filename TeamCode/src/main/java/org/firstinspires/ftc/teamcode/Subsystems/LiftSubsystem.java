package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
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
    public static final int lowerLimit = 0;
    public static final int upperLimit = 4700; //Set these to the actual values
    private final boolean useLimits = true;

    private static double goalRight;
    private static double goalLeft;



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

    private void moveLiftToTarget() {
        controllerRight.setGoal(goalRight);
        controllerLeft.setGoal(goalLeft);

        //Calculate PID ouput
        double rightPower = -controllerRight.calculate(liftRight.getCurrentPosition()) + kG;
        double leftPower = -controllerLeft.calculate(liftLeft.getCurrentPosition()) + kG;

        //Check if the tolerance is met
        if (controllerRight.atGoal()) rightPower = 0;
        if (controllerLeft.atGoal()) leftPower = 0;

        //Set power based on PID output
        liftRight.set(rightPower);
        liftLeft.set(rightPower);
    }

    public static void setLiftGoal(int goal){
        goalRight = goal;
        goalLeft = goal;
    }




    public void lift(double inputPower) {
        if (useLimits) {
            //Check Limits
            if (inputPower > 0 && liftRight.getCurrentPosition() >= upperLimit) {
                stabilize();
            } else if (inputPower < 0 && liftRight.getCurrentPosition() <= lowerLimit) {
                stabilize();
            } else {
                //Set PID Goal
                goalRight = inputPower * manualPower + liftRight.getCurrentPosition();
                goalLeft = inputPower * manualPower + liftLeft.getCurrentPosition();
                moveLiftToTarget();
            }
        } else {
                //Set PID Goal
                goalRight = inputPower * manualPower + liftRight.getCurrentPosition();
                goalLeft = inputPower * manualPower + liftLeft.getCurrentPosition();
                moveLiftToTarget();
        }


    }

    public void stabilize(){
       moveLiftToTarget();
    }

    public void liftTelemetry(){
        telemetry.addData("Goal Pos",goalRight);
        telemetry.addData("Current Pos Right",liftRight.getCurrentPosition());
        telemetry.addData("Current Pos Left",liftLeft.getCurrentPosition());
        telemetry.update();

        dashboardTelemetry.addData("Goal Pos",goalRight);
        dashboardTelemetry.addData("Current Pos Right",liftRight.getCurrentPosition());
        dashboardTelemetry.addData("Current Pos Left",liftLeft.getCurrentPosition());
        dashboardTelemetry.update();
    }

}