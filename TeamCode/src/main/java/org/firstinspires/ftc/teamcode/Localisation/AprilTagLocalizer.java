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

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {
                "define a better way to find the position then averaging the positions",
                "get the proper units (for now just using whatever units are standard to the april tag pipeline)",
                "make sure the directions line up",

                "getPoseVelocity",
                    "figure out what im supposed to do with this (it wants a pose2d???)",
                    "figure out the units",

                "implement a way to do heading (if necessary)"
        }
)
public class AprilTagLocalizer extends MyLocalizer {
    public AprilTagLocalizer(AprilTagProcessor detectionSupplier, Pose2d startingPose, double camera_offset_x, double camera_offset_y) {
        CAMERA_OFFSET_X = camera_offset_x;
        CAMERA_OFFSET_Y = camera_offset_y;
        poseEstimate = startingPose;
        this.detectionSupplier = detectionSupplier;
        computesHeading = true;
    }

    //only really to be used for CompositeLocalizer
    public AprilTagLocalizer(AprilTagProcessor detectionSupplier, double camera_offset_x, double camera_offset_y) {
        CAMERA_OFFSET_X = camera_offset_x;
        CAMERA_OFFSET_Y = camera_offset_y;
        poseEstimate = null;
        this.detectionSupplier = detectionSupplier;
        computesHeading = true;
    }

    @NotNull
    @Override
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    @Override
    public void setPoseEstimate(@NotNull Pose2d pose2d) {
        poseEstimate = pose2d;
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
               avgY = 0,
               avgH = 0; //heading //TODO: DO SOMETHING WITH


        for(AprilTagDetection detection : detections) {
            avgX += FieldPositions.TAG_POS_BY_ID_X[detection.id] + detection.ftcPose.x + CAMERA_OFFSET_X;
            avgY += FieldPositions.TAG_POS_BY_ID_Y[detection.id] + detection.ftcPose.y + CAMERA_OFFSET_Y;
        }
        avgX /= detections.size();
        avgY /= detections.size();

        poseEstimate = new Pose2d(
                poseEstimate.getX() + avgX,
                poseEstimate.getY() + avgY,
                poseEstimate.getHeading() + avgH
        );
    }

    Pose2d poseEstimate;
    AprilTagProcessor detectionSupplier;
    final double CAMERA_OFFSET_X, CAMERA_OFFSET_Y;
}
