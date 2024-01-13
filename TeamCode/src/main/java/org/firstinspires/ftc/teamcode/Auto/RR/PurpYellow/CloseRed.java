package org.firstinspires.ftc.teamcode.Auto.RR.PurpYellow;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.Main;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.RR.BaseAuto;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode;
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

        MainOpMode mainOpMode = new MainOpMode();

        builder
                .forward(10 + (spike != 1 ? 10 : 0))
                .strafeRight(ZONE_WIDTH * spike)

                .addDisplacementMarker(() -> {

                    new SequentialCommandGroup(
                            // 1
                            new InstantCommand(() -> claw.setPosition(mainOpMode.clawClosed)), // Close claw
                            new WaitCommand(250),
                            new InstantCommand(() -> {mainOpMode.armPosition = 0.66;}),
                            mainOpMode.updateArm(),
                            new WaitCommand(250),
                            new InstantCommand(() -> {pitch.setPosition(0.168);}),

                            new WaitCommand(1000),
                            // 2
                            new InstantCommand(() -> {mainOpMode.armPosition = mainOpMode.armMin;}), // Slightly above pixel bottom
                            mainOpMode.updateArm(),

                            new WaitCommand(3000),

                            // 3
                            new InstantCommand(() -> claw.setPosition(mainOpMode.clawOpen)), // Open claw
                            new WaitCommand(100),

                            // Shake it
                            new InstantCommand(() -> {pitch.setPosition(pitch.getPosition() + 0.1);}),
                            new WaitCommand(100),
                            new InstantCommand(() -> {pitch.setPosition(pitch.getPosition() - 0.1);}),
                            new WaitCommand(100),

                            new WaitCommand(500),

                            // Move arm back
                            new InstantCommand(() -> {mainOpMode.armPosition = 0.6;}),
                            mainOpMode.updateArm()
                    ).schedule();

                })
                ;

        //build and follow traj
        drive.followTrajectorySequenceAsync(builder.build());

        while (true) CommandScheduler.getInstance().run();
    }

    int spike; //0 for center, -1 for left, 1 for right
    final int ZONE_WIDTH = -1;
    int ST_X, ST_Y;
}
