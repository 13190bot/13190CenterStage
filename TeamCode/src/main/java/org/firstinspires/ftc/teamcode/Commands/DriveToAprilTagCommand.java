package org.firstinspires.ftc.teamcode.Commands;

import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.KalmanFilter;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@Config(value = "DriveToAprilTag")
public class DriveToAprilTagCommand extends CommandBase {

    private AprilTagDetection detection = null;
    private int tagID;
    private Boolean isCentered = false;
     public static double kP = 0.1; // Adjust these values based on tuning
     public static double kI = 0.1;
     public static double kD = 0.1;

     private Telemetry telemetry;
     private Boolean forwardCentered = false;

     private DriveSubsystem driveSubsystem;
    PIDController pidControllerForward = new PIDController(kP, kI, kD);
    PIDController pidControllerRotation = new PIDController(kP,kI,kD);
    PIDController pidControllerStrafe = new PIDController(kP,kI,kD);


    // Set the setpoint based on the desired AprilTag position (you can adjust this as needed)
    double desiredPosition = 10.0; // Set this to the desired position
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    public static double Q = 3; // High values put more emphasis on the sensor.
    public static double R = 0.3; // High Values put more emphasis on regression.
    public static int N = 3; // The number of estimates in the past we perform regression on.
    KalmanFilter filter = new KalmanFilter(Q,R,N);



    public DriveToAprilTagCommand(DriveSubsystem driveSubsystem, int tagID, Telemetry telemetry) {
        this.tagID = tagID;
        this.driveSubsystem = driveSubsystem;
        this.telemetry = telemetry;
        addRequirements(driveSubsystem);
    }
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        detection = AprilTagDetector.getDetectionByID(tagID);

        if (!(detection == null)) {
            // Calculate the error (the difference between the desired position and the detected position)

            // Calculate the control output using the PID controller
            double outputForward = -pidControllerForward.calculate(detection.ftcPose.range, desiredPosition);
            double outputRotation = pidControllerRotation.calculate(detection.ftcPose.bearing, 0);
            double outputStrafe = pidControllerRotation.calculate(detection.ftcPose.x, 0);


            pidControllerForward.setTolerance(3);
            pidControllerRotation.setTolerance(3);
            pidControllerStrafe.setTolerance(3);


            double filteredOutputForward = filter.estimate(outputForward);

            double targetPowerForward = (Math.tanh(filteredOutputForward/2)/3);
            double targetPowerRotation = (Math.tanh(outputRotation/4)/3);
            double targetPowerStrafe = (Math.tanh(outputStrafe/4)/3);

            if (pidControllerForward.atSetPoint()) targetPowerForward = 0;
            if (pidControllerRotation.atSetPoint()) targetPowerRotation = 0;
            if (pidControllerStrafe.atSetPoint()) targetPowerStrafe = 0;

            dashboardTelemetry.addData("Output of PID in inches",outputForward);
            dashboardTelemetry.addData("Filtered Output",filteredOutputForward);
            dashboardTelemetry.addData("Motor Power",targetPowerForward);

            telemetry.addData("Target Power Rotation",targetPowerRotation);
            telemetry.addData("Target Power Strafe", targetPowerStrafe);
            telemetry.addData("___", "___");
            telemetry.addData("PID output Rotation", outputRotation);
            telemetry.addData("PID output Strafe", outputStrafe);
            dashboardTelemetry.update();
            telemetry.update();

                driveSubsystem.driveRobotCentric(0, targetPowerForward, 0);


        } else {
            // If no AprilTag is detected, stop the robot
                if (!(filter == null))   driveSubsystem.driveRobotCentric(0, (Math.tanh(filter.getX()/2)/3), 0);
                else driveSubsystem.driveRobotCentric(0, 0, 0);
                }

    }

    @Override
    public boolean isFinished(){
        return isCentered;
    }


}



