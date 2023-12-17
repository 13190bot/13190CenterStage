package org.firstinspires.ftc.teamcode.Localisation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.util.Encoder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OdoLocalizer extends MyLocalizer{
    public OdoLocalizer (DcMotor encX, DcMotor encY, BNO055IMUImpl imu) {
        this.encX = encX;
        prevX = encX.getCurrentPosition();

        this.encY = encY;
        prevY = encY.getCurrentPosition();

        this.imu = imu;
        computesHeading = false;
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        return null;
    }

    @Override
    public void update() {
        //TODO: which dir is the horizontal rotation and what is a negligible amount
        if (imu.getAngularVelocity().toAngleUnit(AngleUnit.DEGREES).xRotationRate > NOISE_CONSTANT) { return; }

        //TODO: is poseEstimate.heading acc in radiens
        poseEstimate= new Pose2d(
                (poseEstimate.getX() + ((encX.getCurrentPosition() - prevX) * TICKS_TO_IN)) * Math.cos(poseEstimate.getHeading()),
                (poseEstimate.getY() + ((encY.getCurrentPosition() - prevY) * TICKS_TO_IN)) * Math.sin(poseEstimate.getHeading()),
                poseEstimate.getHeading()
        );
    }

    int prevX, prevY;
    BNO055IMUImpl imu;
    DcMotor encX, encY;

    final double TICKS_TO_IN = -1;
    final double NOISE_CONSTANT = 2;
}
