package org.firstinspires.ftc.teamcode.Auto.RR.Park;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.RR.BaseAuto;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Park Close", group = "Park")
public class Close extends BaseAuto {
    @Override
    public void overrideMe() {
        telemetry = new MultipleTelemetry(telemetry);

        drive.followTrajectorySequence(
                drive.trajectorySequenceBuilder(new Pose2d())
                        .back(2 * TILE_SIZE - 4)
                        .build()
        );
    }
}
