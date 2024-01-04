package org.firstinspires.ftc.teamcode.TestChassis;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Localisation.IMULocaliser;

@Autonomous
public class IMULoclaiserTest extends LinearOpMode {
    /**
     * Override this method and place your code here.
     * <p>
     * Please do not catch {@link InterruptedException}s that are thrown in your OpMode
     * unless you are doing it to perform some brief cleanup, in which case you must exit
     * immediately afterward. Once the OpMode has been told to stop, your ability to
     * control hardware will be limited.
     *
     * @throws InterruptedException When the OpMode is stopped while calling a method
     *                              that can throw {@link InterruptedException}
     */
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry);

        BNO055IMUImpl imu = (BNO055IMUImpl) hardwareMap.getAll(BNO055IMU.class).get(0);
        if (imu == null) {
            telemetry.addData("imu no exist", "");
            telemetry.update();
            sleep(10000);
        }

        telemetry.addData("imu", imu);
        telemetry.update();

        imu.initialize();

        telemetry.addData("imu clibration", imu.isSystemCalibrated());
        telemetry.update();
//        assert imu.isSystemCalibrated();

        IMULocaliser localiser = new IMULocaliser(
                imu
        );

        waitForStart();
        while (opModeIsActive()) {
            Pose2d p = localiser.getPoseEstimate();

            telemetry.addData("Pos", p.getX() + ", " + p.getY() + "@" + Math.toDegrees(p.getHeading()));
            telemetry.addData("gravity", imu.getGravity());

            telemetry.update();
            localiser.update();
        }
    }
}
