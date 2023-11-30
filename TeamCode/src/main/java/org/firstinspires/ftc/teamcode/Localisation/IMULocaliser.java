package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IMULocaliser extends MyLocalizer{
    @NotNull
    @Override
    public Pose2d getPoseEstimate() {
        return null;
    }

    @Override
    public void setPoseEstimate(@NotNull Pose2d pose2d) {

    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {

    }
}
