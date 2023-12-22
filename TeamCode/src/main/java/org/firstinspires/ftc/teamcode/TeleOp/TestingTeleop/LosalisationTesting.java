package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Localisation.*;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;

public class LosalisationTesting extends BaseOpMode {
    CompositeLocalizer localizer;
    AprilTagDetector aprilTagDetector1 = new AprilTagDetector("Webcam 1", hardwareMap);

    @Override
    public void initialize() {
        super.initialize();

        localizer = new CompositeLocalizer(
                new Pose2d(),
                new MyLocalizer[]{
                        new IMULocaliser((BNO055IMUImpl) hardwareMap.getAll(BNO055IMU.class).get(0)),
                        new IMULocaliser((BNO055IMUImpl) hardwareMap.getAll(BNO055IMU.class).get(1)),

                        new AprilTagLocalizer(aprilTagDetector1.aprilTagProcessor)

//                        new OdoLocalizer()
                }
        );
    }
}
