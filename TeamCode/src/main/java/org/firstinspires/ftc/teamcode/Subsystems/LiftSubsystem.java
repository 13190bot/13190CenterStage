package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

//TODO: IDEA - USE FTCLib ElevatorFeedforward
public class LiftSubsystem extends SubsystemBase {

    private MotorEx liftRight, liftLeft;

    private double kP = 1, kI = 0, kD = 0, kF = 0;

    private double integralSum;

    private double lastError;

    private ElapsedTime timer = new ElapsedTime();

    private PIDFController controller;
//    private PIDFController rController;


    public LiftSubsystem(MotorEx liftRight, MotorEx liftLeft) {
        this.liftRight = liftRight;
        this.liftLeft = liftLeft;

        controller = new PIDFController(kP, kI, kD, kF);
        controller.setSetPoint(0);
    }

    public void setPIDF (double kP, double kI, double kD, double kF) {
        controller.setPIDF(kP, kI, kD, kF);
    }

    public double getTarget () {
        return controller.getSetPoint();
    }

    public void setTarget (double target) {
        controller.setSetPoint(target);
    }

    public boolean isAtTarget () {
        return controller.atSetPoint();
    }

    public void runLift () {
        if(!controller.atSetPoint()) {
            liftLeft.setVelocity(controller.calculate(liftLeft.getCurrentPosition()));
            liftRight.setVelocity(controller.calculate(liftRight.getCurrentPosition()));
        }
    }

    public void manualLift (double velocity) {
        liftLeft.setVelocity(velocity);
        liftRight.setVelocity(velocity);
    }

//    public void lift(double finalPos) {
//        while (liftRight.getCurrentPosition() != finalPos) {
//
//            double error = finalPos - liftRight.getCurrentPosition();
//
//            double derivative = (error - lastError) / timer.seconds();
//            integralSum = integralSum + (error * timer.seconds());
//
//            double output = (kP * error) + (kI * integralSum) + (kD * lastError);
//            liftRight.set(output);
//            liftLeft.set(output);
//
//            lastError = error;
//
//            timer.reset();
//        }
//    }

    public static final double GRAB_HEIGHT = 0;
}