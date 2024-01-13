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

    // 2 = blue, 1 = red
    private int colorInd = 2;
    @Override
    public void overrideMe() {
        telemetry = new MultipleTelemetry(telemetry);

        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(camera, 30);
        ColorDetectionYCRCBPipeline colorDetectionYCRCBPipeline = new ColorDetectionYCRCBPipeline(colorInd);

        camera.setPipeline(colorDetectionYCRCBPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280 , 720, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {

            }
        });

        drive.followTrajectorySequence(
                drive.trajectorySequenceBuilder(new Pose2d())
                        .back(DRIVE_DIST)
                        .build()
        );
    }

    public int COLOR = 1; //blue = 1, red = -1
    public int DRIVE_DIST = 44; //44 for close
}
