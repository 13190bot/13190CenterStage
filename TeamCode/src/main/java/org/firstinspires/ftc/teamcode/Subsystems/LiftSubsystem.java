package org.firstinspires.ftc.teamcode.Subsystems;
import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.KalmanFilter;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class LiftSubsystem extends SubsystemBase {

    public MotorEx liftRight, liftLeft;

    public static double kP = 0.01001; //0.01
    public static double kI = 0.00001;
    public static double kD = 0.0002;  //0.0003
    public static double kG = 0.001;
    public static double maxVelocity = 5000; // 4000
    public static double maxAcceleration = 4000; // 4000
    public static int tolerance = 2;
    public static final int lowerLimit = 0;
    public static final int upperLimit = 4700;
    private final boolean useLimits = true;

    public static double goalRight = 0;
    public static double goalLeft = 0;




    private final ProfiledPIDController controllerLeft = new ProfiledPIDController(kP, kI, kD,
            new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration));
    private final ProfiledPIDController controllerRight = new ProfiledPIDController(kP, kI, kD,
            new TrapezoidProfile.Constraints(maxVelocity, maxAcceleration));

    private Telemetry telemetry;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();



    public static double manualPower = 350; //these are in ticks, 300

    //Pass on values
    public LiftSubsystem(MotorEx liftRight, MotorEx liftLeft, Telemetry telemetry) {
        this.liftRight = liftRight;
        this.liftLeft = liftLeft;
        this.telemetry = telemetry;

        controllerLeft.setTolerance(tolerance);
        controllerRight.setTolerance(tolerance);
    }

    //Function that is run repeatedly to move the lift to the target
    public void moveLiftToTarget() {
        //Set PID values
        controllerRight.setP(kP);
        controllerRight.setI(kI);
        controllerRight.setD(kD);

        controllerLeft.setP(kP);
        controllerLeft.setI(kI);
        controllerLeft.setD(kD);


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


    //Set the goal position
    public static void setLiftGoal(int goal){
        goalRight = goal;
        goalLeft = goal;
    }

    //Check if the lift is within the limits
    public boolean checkLimits(double inputPower){
        if (useLimits) {
            if (inputPower > 0 && liftRight.getCurrentPosition() >= upperLimit) {
                return false;
            } else if (inputPower < 0 && liftRight.getCurrentPosition() <= lowerLimit) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }


    }



    //Move the lift manually
    public void lift(double inputPower) {
        if (checkLimits(inputPower)) {
                //Set PID Goal
                goalRight = inputPower * manualPower + liftRight.getCurrentPosition();
                goalLeft = inputPower * manualPower + liftLeft.getCurrentPosition();
                moveLiftToTarget();
        } else {
            moveLiftToTarget();
        }


    }


    public void captureCurrentPos(){
        goalRight = liftRight.getCurrentPosition();
        goalLeft = liftLeft.getCurrentPosition();
    }

    //Lift debug telemetry
    public void liftTelemetry(){
        telemetry.addData("Goal Pos",goalRight);
        telemetry.addData("Current Pos Right",liftRight.getCurrentPosition());
        telemetry.addData("Current Pos Left",liftLeft.getCurrentPosition());

        dashboardTelemetry.addData("Goal Pos",goalRight);
        dashboardTelemetry.addData("Current Pos Right",liftRight.getCurrentPosition());
        dashboardTelemetry.addData("Current Pos Left",liftLeft.getCurrentPosition());
        dashboardTelemetry.update();
    }

}