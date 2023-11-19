package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.AlignCommand;
import org.firstinspires.ftc.teamcode.Commands.DriveRobotCentricCommand;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

// TODO: Change back to BaseOpMode

@TeleOp(name = "AlignTest")
@Config
public class AlignTest extends BaseDriveOpMode {
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        AprilTagDetector.initAprilTag(hardwareMap);

        driveSubsystem.setDefaultCommand(new AlignCommand(driveSubsystem, telemetry));
    }

    @Override
    public void run() {
        super.run();
    }
}
