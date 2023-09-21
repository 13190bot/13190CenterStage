package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class DriveToAprilTagCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;

    public DriveToAprilTagCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);
    }
    @Override
    public void execute() {
        AprilTagDetection detetctionID_1 = AprilTagDetector.detectionByID(1);
        
        if (!(detetctionID_1 == null)) {
            while (8 < detetctionID_1.ftcPose.range) {
                driveSubsystem.driveRobotCentric(0.5, 0.0, 0.0);
            }
            while (8 > detetctionID_1.ftcPose.range) {
                driveSubsystem.driveRobotCentric(-0.5, 0.0, 0.0);
            }
        }
    }
}
