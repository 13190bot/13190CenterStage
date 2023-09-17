package org.firstinspires.ftc.teamcode.localisation;

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
public class CompositeLocalizer implements Localizer {
    public CompositeLocalizer (Pose2d startingPose, Localizer[] localizers) {
        poseEstimate = startingPose;
        this.localizers = localizers;

        for (Localizer localizer : localizers) {
            localizer.setPoseEstimate(startingPose);
        }
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
        double avgX = 0,
               avgY = 0,
               avgH = 0;

        for (Localizer localizer : localizers) {
            localizer.update();

            avgX += localizer.getPoseEstimate().getX();
            avgY += localizer.getPoseEstimate().getY();
            avgH += localizer.getPoseEstimate().getHeading();
        }
        avgX /= localizers.length;
        avgY /= localizers.length;
        avgH /= localizers.length;

        poseEstimate = new Pose2d(avgX, avgY, avgH);
    }

    Pose2d poseEstimate;
    Localizer[] localizers;
}
