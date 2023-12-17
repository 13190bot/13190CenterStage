package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.teamcode.util.FieldPositions;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AprilTagLocalizer extends MyLocalizer {

    //only really to be used for CompositeLocalizer
    public AprilTagLocalizer(AprilTagProcessor detectionSupplier) {
        poseEstimate = new Pose2d();
        this.detectionSupplier = detectionSupplier;
        computesHeading = true;
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {
        List<AprilTagDetection> detections = detectionSupplier.getDetections();

        double avgX = 0,
               avgY = 0
               ;

        for(AprilTagDetection detection : detections) {
            avgX += detection.metadata.fieldPosition.get(0) - detection.ftcPose.x; //x
            avgY += detection.metadata.fieldPosition.get(1) + detection.ftcPose.y; //y
        }
        avgX /= detections.size();
        avgY /= detections.size();

        poseEstimate = new Pose2d(
                poseEstimate.getX() + avgX,
                poseEstimate.getY() + avgY,
                poseEstimate.getHeading() + Math.atan(avgY/avgX)
        );
    }

    AprilTagProcessor detectionSupplier;
}
