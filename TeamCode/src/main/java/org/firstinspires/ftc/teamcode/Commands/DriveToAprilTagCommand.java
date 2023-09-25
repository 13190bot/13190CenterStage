package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Config
public class DriveToAprilTagCommand extends CommandBase {
    private final int TICKS_PER_ROTATION = 10;
    private final int WHEEL_RADIUS = 2;
    private AprilTagDetection detectionID_1 = null;
    private Boolean isCentered = false;
    private MotorEx fl, fr, bl, br;
     static double kP = 0.1; // Adjust these values based on tuning
     static double kI = 0.0;
     static double kD = 0.0;
    PIDController pidController = new PIDController(kP, kI, kD);

    // Set the setpoint based on the desired AprilTag position (you can adjust this as needed)
    double desiredPosition = 10.0; // Set this to the desired position



    public DriveToAprilTagCommand( MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
    }
    @Override
    public void initialize() {
        fl.setRunMode(MotorEx.RunMode.PositionControl);
    }

    @Override
    public void execute() {
        detectionID_1 = AprilTagDetector.getDetectionByID(1);

        if (!(detectionID_1 == null)) {
            // Calculate the error (the difference between the desired position and the detected position)

            // Calculate the control output using the PID controller
            double output = pidController.calculate(detectionID_1.ftcPose.range, desiredPosition);
            double targetTicks = inchesToTicks(output);
            fl.setTargetDistance(targetTicks);
            fr.setTargetDistance(targetTicks);
            bl.setTargetDistance(targetTicks);
            br.setTargetDistance(targetTicks);
        } else {
            // If no AprilTag is detected, stop the robot

                }

    }

    @Override
    public boolean isFinished(){
        return isCentered;
    }

    public double inchesToTicks(double inches) {
        double wheelCircumference = 2 * Math.PI * WHEEL_RADIUS;
        double ticksPerInch = TICKS_PER_ROTATION / wheelCircumference;
        double targetTicks =  (inches * ticksPerInch);
        return targetTicks;
    }
}



