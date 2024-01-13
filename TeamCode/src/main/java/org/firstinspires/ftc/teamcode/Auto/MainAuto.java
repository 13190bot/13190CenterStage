package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.CV.PixelDetectionPipeline;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "MainAuto")
public class MainAuto extends BaseOpMode {
    private OpenCvCamera camera;

    // 2 = blue, 1 = red
    private int colorInd = 2;
    private final boolean isFarSide = false;
    @Override
    public void runOpMode() {
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




        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory goToMiddle = drive.trajectoryBuilder(new Pose2d())
                .forward(27)
                .build();

        TrajectorySequence placeOnSpike = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(10)
                .back(7)
                .addDisplacementMarker(() -> {
                    // spin intake
//                    intakeMotor.set(0.5);
                })
                .waitSeconds(2)
                .back(3)
                .build();

        double farBackdropDistance = 80;
        TrajectorySequence moveToBackdropFar = drive.trajectorySequenceBuilder(new Pose2d())
                .back(farBackdropDistance)
                .build();

        TrajectorySequence moveToBackdrop = drive.trajectorySequenceBuilder(new Pose2d())
                .back(farBackdropDistance - 48)
                .build();

//        waitForStart();

        while (!isStarted()) {
            telemetry.addData("Current Position: ", colorDetectionYCRCBPipeline.getPosition());
            telemetry.update();
        }

        ColorDetectionYCRCBPipeline.PropPosition propPosition = colorDetectionYCRCBPipeline.getPosition();


        if(isStopRequested()) return;

        drive.followTrajectory(goToMiddle);

        switch(propPosition) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .turn(Math.toRadians(90))
                        .build());
                break;

            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .turn(Math.toRadians(-90))
                        .build());
                break;

            case CENTER: break;
            case NOPOS:  break;
            default:     break;
        }

        drive.followTrajectorySequence(placeOnSpike);

        if (colorInd == 2) {
            // blue (needs to face right)

            switch(propPosition) {
                case LEFT:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                            .turn(Math.toRadians(-180))
                            .build());
                    break;

                case CENTER:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                            .turn(Math.toRadians(-90))
                            .build());
                    break;

                case RIGHT: break;
                default:    break;
            }
        } else {
            // red (needs to face left)
            switch(propPosition) {
                case CENTER:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                            .turn(Math.toRadians(90))
                            .build());
                    break;

                case RIGHT:
                    drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                            .turn(Math.toRadians(180))
                            .build());

                default: break;
            }
        }


        if (isFarSide) {
            drive.followTrajectorySequence(moveToBackdropFar);
        } else {
            drive.followTrajectorySequence(moveToBackdrop);
        }

        // score
        MainOpMode mainOpMode = new MainOpMode();
//        mainOpMode.armPickup.schedule();

    }
}