package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {
                "speed up (its soo slow as u can tell if u look through the code",
                "make getPosVelocity return the average of all the not-null values, or null if all are null"
        },
        done = {
                "defined update as average of all the localizers' results"
        }
)
public class CompositeLocalizer extends MyLocalizer {
    public CompositeLocalizer (Pose2d startingPose, MyLocalizer[] localizers) {
        poseEstimate = startingPose;
        this.localizers = localizers;

        for (Localizer localizer : localizers) {
            localizer.setPoseEstimate(startingPose);
        }

        computesHeading = true;
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {
        boolean wasHeadingUpdated = false;
        double avgX = 0,
               avgY = 0,
               avgH = 0;

        for (MyLocalizer localizer : localizers) {
            localizer.setPoseEstimate(poseEstimate);

            localizer.update();

            avgX += localizer.getPoseEstimate().getX();
            avgY += localizer.getPoseEstimate().getY();

            if (localizer.computesHeading) {
                avgH += localizer.getPoseEstimate().getHeading();
                wasHeadingUpdated = true;
            }
        }

        avgX /= localizers.length;
        avgY /= localizers.length;
        if (wasHeadingUpdated) {
            avgH /= localizers.length;
            poseEstimate = new Pose2d(avgX, avgY, avgH);
        } else {
            poseEstimate = new Pose2d(avgX, avgY, poseEstimate.getHeading());
        }
    }

    MyLocalizer[] localizers;
}
