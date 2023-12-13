package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IMULocaliser extends MyLocalizer{
    public IMULocaliser () {
        computesHeading = true;

        poseEstimate = new Pose2d();
    }

    @NotNull
    @Override
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    @Override
    public void setPoseEstimate(@NotNull Pose2d pose2d) {
        this.poseEstimate = poseEstimate;
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {
        Position pos = imu.getPosition().toUnit(DistanceUnit.INCH);

        //TODO: make sure that it is xRotationRate and not y or z
        poseEstimate = new Pose2d(
                pos.x,
                pos.y,
               poseEstimate.getHeading() + ((double)imu.getAngularVelocity().toAngleUnit(AngleUnit.DEGREES).xRotationRate * System.currentTimeMillis() / 1000)
        );
    }

    BNO055IMUImpl imu;
}
