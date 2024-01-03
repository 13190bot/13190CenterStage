package org.firstinspires.ftc.teamcode.CV;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="PixelDetectionTest")
public class PixelDetectionTest extends LinearOpMode {

    private OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry);
        telemetry.addData("you have run the right program", "");
        telemetry.update();
//        sleep(2000);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        telemetry.addData("got camera", "");
        telemetry.update();
//        sleep(2000);

        FtcDashboard.getInstance().startCameraStream(camera, 30);
        telemetry.addData("started stream", "");
        telemetry.update();
//        sleep(2000);

        PixelDetectionPipeline pixelDetectionPipeline = new PixelDetectionPipeline(telemetry);
        telemetry.addData("created pipeline", "");
        telemetry.update();
//        sleep(2000);

        camera.setPipeline(pixelDetectionPipeline);
        telemetry.addData("Attatched Pipeline", "");
        telemetry.update();
//        sleep(2000);


        pixelDetectionPipeline.initPipeline();
        telemetry.addData("initialised pipeline", "");
        telemetry.update();
//        sleep(2000);

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
        telemetry.addData("opened camera", "");
        telemetry.update();
        sleep(2000);

        while (!isStarted()) {
//            telemetry.addData("Current Position: ", colorDetectionYCBCRPipeline.getPosition());
            telemetry.update();
        }

    }
}
