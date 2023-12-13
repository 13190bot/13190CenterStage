package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public abstract class MyLocalizer implements Localizer {
    @NotNull
    @Override
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    @Override
    public void setPoseEstimate(@NotNull Pose2d pose2d) {
        poseEstimate = pose2d;
    }

    protected Pose2d poseEstimate;
    public boolean computesHeading = false; //is this changed?
}
