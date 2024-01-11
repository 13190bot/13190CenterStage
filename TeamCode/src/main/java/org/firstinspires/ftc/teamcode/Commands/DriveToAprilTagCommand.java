//package org.firstinspires.ftc.teamcode.Commands;
//
//import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.KalmanFilter;
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.arcrobotics.ftclib.command.CommandBase;
//import com.arcrobotics.ftclib.controller.PIDController;
//import com.arcrobotics.ftclib.controller.wpilibcontroller.ProfiledPIDController;
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
//import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//
//@Config(value = "DriveToAprilTag")
//public class DriveToAprilTagCommand extends CommandBase {
//
//    private AprilTagDetection detection = null;
//    private int tagID;
//    private Boolean isCentered = false;
//     public static double kP = 0.1; // Adjust these values based on tuning
//     public static double kI = 0.1;
//     public static double kD = 0.1;
//
//     private Telemetry telemetry;
//     private Boolean forwardCentered = false;
//
//     private DriveSubsystem driveSubsystem;
//    PIDController pidControllerForward = new PIDController(kP, kI, kD);
//    PIDController pidControllerRotation = new PIDController(kP,kI,kD);
//    PIDController pidControllerStrafe = new PIDController(kP,kI,kD);
//
//
//    // Set the setpoint based on the desired AprilTag position (you can adjust this as needed)
//    double desiredPosition = 10.0; // Set this to the desired position
//    FtcDashboard dashboard = FtcDashboard.getInstance();
//    Telemetry dashboardTelemetry = dashboard.getTelemetry();
//    private double outputForward, outputRotation, outputStrafe;
//
//
//
//
//    public DriveToAprilTagCommand(DriveSubsystem driveSubsystem, int tagID, Telemetry telemetry) {
//        this.tagID = tagID;
//        this.driveSubsystem = driveSubsystem;
//        this.telemetry = telemetry;
//        addRequirements(driveSubsystem);
//    }
//    @Override
//    public void initialize() {
//    }
//
//    @Override
//    public void execute() {
//        detection = AprilTagDetector.getDetectionByID(tagID);
//
//        if (!(detection == null)) {
//
//             outputForward = -pidControllerForward.calculate(detection.ftcPose.range, desiredPosition);
//             outputRotation = -Math.tanh(detection.ftcPose.bearing);
//             outputStrafe = pidControllerRotation.calculate(detection.ftcPose.x, 0);
//
//
//            pidControllerForward.setTolerance(3);
//            pidControllerRotation.setTolerance(3);
//            pidControllerStrafe.setTolerance(3);
//
//
//            if (pidControllerForward.atSetPoint()) outputForward = 0;
//          //  if (Math.abs(outputRotation) < 0.1) outputRotation = 0;
//            if (pidControllerStrafe.atSetPoint()) outputRotation = 0;
//
//            dashboardTelemetry.addData("Output of PID in inches", outputForward);
//            telemetry.addData("FTC bearing (rotation)",detection.ftcPose.bearing);
//            telemetry.addLine("____");
//            telemetry.addData("PID output forward", outputForward/10);
//            telemetry.addData("PID output Rotation", outputRotation);
//            telemetry.addData("PID output Strafe", outputStrafe/500);
//            dashboardTelemetry.update();
//            telemetry.update();
//
//            driveSubsystem.driveRobotCentric(0, Math.round(outputForward/10), outputRotation);
//
//
//        } else {
//            // If no AprilTag is detected, stop the robot
//
//
//        }
//    }
//    @Override
//    public boolean isFinished(){
//        return isCentered;
//    }
//
//
//}
//
//
//
