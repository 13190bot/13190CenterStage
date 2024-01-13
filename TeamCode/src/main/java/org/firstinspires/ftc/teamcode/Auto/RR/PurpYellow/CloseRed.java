package org.firstinspires.ftc.teamcode.Auto.RR.PurpYellow;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.RR.BaseAuto;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "PY Close Red", group = "Purple and Yellow")
public class CloseRed extends BaseAuto {

    // 2 = blue, 1 = red
    private int colorInd = 1;

    public ColorDetectionYCRCBPipeline colorDetectionYCRCBPipeline;

    ColorDetectionYCRCBPipeline.PropPosition position;

    @Override
    public void overrideMe() {

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

        while (!isStarted()) {
            telemetry.addData("Current Position: ", colorDetectionYCRCBPipeline.getPosition());
            telemetry.update();
        }

        switch (position) {
            case LEFT:
                spike = -1;
            case CENTER:
                spike = 0;
            case RIGHT:
                spike = 1;
            case NOPOS:
                spike = 0;
        }

        TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(ST_X, ST_Y));

        //move forwards
        builder.forward(TILE_SIZE);

        //if left, turn
        if (spike == -1) builder.turn(-90);

        //if not forwards, deposit center pix
        if (spike != 1) {
            builder
                    .addDisplacementMarker(() -> intakeMotor.set(-.5) )
                    .waitSeconds(1)
                    .addDisplacementMarker(() -> intakeMotor.set(0) )
                    ;
        }

        //if right, turn, move, and deposit
        else {
            builder
                    .turn(90)
                    .forward(TILE_SIZE)
                    .addDisplacementMarker(() -> intakeMotor.set(-.5))
                    .waitSeconds(1)
                    .addDisplacementMarker( () -> intakeMotor.set(0))
                    ;
        }

        //if not already facing proper heading, face proper dir
        if(spike != 1) builder.turn(90 * (spike+1));

        //approach bord, stafe to pos, deposit yellow
        builder
                .forward(10 + (spike != 1 ? 10 : 0))
                .strafeRight(ZONE_WIDTH * spike)

                .addDisplacementMarker(() -> {
                    //
                })
                ;

        //build and follow traj
        drive.followTrajectorySequence(builder.build());
    }

    int spike; //0 for center, -1 for left, 1 for right
    final int ZONE_WIDTH = -1;
    int ST_X, ST_Y;
}
