package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class DriveToAprilTagCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private AprilTagDetection detectionID_1 = null;


    public DriveToAprilTagCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        detectionID_1 = AprilTagDetector.getDetectionByID(1);

        if (!(detectionID_1 == null)) {
            if (6 < detectionID_1.ftcPose.range) {
                if (0== (int) Math.round(detectionID_1.ftcPose.bearing)){
                    driveSubsystem.driveRobotCentric(0, 0.1, 0);
                } else if (0 < detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0, 0.1, -0.1);
                } else if (0 > detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0, 0.1, 0.1);
                }
            }
            if (6 == (int) Math.round(detectionID_1.ftcPose.range)) {
                if (0== (int) Math.round(detectionID_1.ftcPose.bearing)){
                    driveSubsystem.driveRobotCentric(0.0, 0, 0);
                } else if (0 < detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0.0, 0, -0.1);
                } else if (0 > detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0.0, 0, 0.1);
                }
            }
            if (6 > detectionID_1.ftcPose.range) {
                if (0== (int) Math.round(detectionID_1.ftcPose.bearing)){
                    driveSubsystem.driveRobotCentric(0, -0.1, 0);
                } else if (0 < detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0, -0.1, -0.1);
                } else if (0 > detectionID_1.ftcPose.bearing){
                    driveSubsystem.driveRobotCentric(0, -0.1, 0.1);
                }
            }


        } else {
            driveSubsystem.driveRobotCentric(0, 0, 0);
        }

    }
}



