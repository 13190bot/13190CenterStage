package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.DriveToAprilTagCommand;

@TeleOp(name = "TestFollowAprilTagOpMode")
public class TestFollowAprilTagOpMode extends BaseDriveOpMode {
    @Override
    public void initialize() {
        super.initialize();
        register(driveSubsystem);
        DriveToAprilTagCommand driveToAprilTagCommand = new DriveToAprilTagCommand(fl,fr,bl,br);
        driveSubsystem.setDefaultCommand(driveToAprilTagCommand);

    }
    @Override
    public void run()
    {
        super.run();
        AprilTagDetector.updateAprilTagDetections();
        AprilTagDetector.aprilTagTelemetry(telemetry);
    }

    }

