//package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
//import org.firstinspires.ftc.teamcode.Commands.DriveToAprilTagCommand;
//
////import static org.firstinspires.ftc.teamcode.CV.AprilTagDetector.visionPortal;
//
//@Disabled
//@TeleOp(name = "TestFollowAprilTagOpMode")
//public class TestFollowAprilTagOpMode extends BaseDriveOpMode {
//    @Override
//    public void initialize() {
//        super.initialize();
//        register(driveSubsystem);
//        DriveToAprilTagCommand driveToAprilTagCommand = new DriveToAprilTagCommand(driveSubsystem,1,telemetry);
//        driveSubsystem.setDefaultCommand(driveToAprilTagCommand);
//    }
//    @Override
//    public void run()
//    {
//        super.run();
//        AprilTagDetector.updateAprilTagDetections();
//       // AprilTagDetector.aprilTagTelemetry(telemetry);
//    }
//
//    }
//
