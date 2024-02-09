package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import static org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode.*;

@Autonomous(name = "CloseBlueAuto")
public class CloseBlueAuto extends BaseOpMode {
    private OpenCvCamera camera;

    // 2 = blue, 1 = red
    private int colorInd = 2;
    private final boolean isFarSide = false;
    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry);

        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");
        claw = new SimpleServo(hardwareMap, "claw", 0, 180);
        arm = new SimpleServo(hardwareMap, "arm", 0, 255);
        pitch = new SimpleServo(hardwareMap, "pitch", 0, 255);


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

        while (!isStarted()) {
            telemetry.addData("Current Position: ", colorDetectionYCRCBPipeline.getPosition());
            telemetry.update();
        }

        ColorDetectionYCRCBPipeline.PropPosition propPosition = colorDetectionYCRCBPipeline.getPosition();


        if(isStopRequested()) return;




        switch(propPosition) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(27)
                        .strafeRight(10)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.2)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0);
                        })
                        .back(5)
                        .turn(Math.toRadians(-90))
                        .back(28.5)
                        .strafeRight(7)
                        .build());
                break;
            case CENTER:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(40)
                        .back(11.5)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.2)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0);
                        })
                        .back(9)

                        .turn(Math.toRadians(-90))
                        .back(36)
                        .strafeRight(11)
                        .build());
                break;
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(27)
                        .turn(Math.toRadians(-90))
                        .forward(10)
                        .back(10)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.2)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0);
                        })
                        .back(37)
                        .strafeRight(15)
                        .build());
                break;
            case NOPOS:
                break;
            default:
                break;
        }



        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeRight(0.1)
                        .addDisplacementMarker(() -> {
                            claw.setPosition(clawClosed);
                            sleep(1000);
                            double armPosition = armMin;
                            arm.setPosition(armPosition);
                            sleep(1000);

                            double armPercent = (armPosition - armMin) / (armMax - armMin);
                            double pitchPercent = (0.5 - armPercent) / (0.5);
                            pitch.setPosition((1 - pitchPercent) * (1 - pitchMax) + pitchMax);
                            sleep(1000);

                            claw.setPosition(clawOpen+0.02);
                            sleep(300);
                            claw.setPosition(clawOpen-0.02);
                            sleep(300);
                            // Shake it
                            pitch.setPosition(pitch.getPosition() + 0.1);
                            sleep(100);
                            pitch.setPosition(pitch.getPosition() - 0.1);
                            sleep(600);
                            armPosition = 0.655;
                            pitch.setPosition(pitchMin); // ready to pick up
                            arm.setPosition(armPosition);
                        })
                .build());


        switch(propPosition) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeLeft(10)
                        .turn(Math.toRadians(-30))
                        .back(5)
                        .build());
            case CENTER:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeLeft(23)
                        .turn(Math.toRadians(-30))
                        .back(18)
                        .build());
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeLeft(27)
                        .turn(Math.toRadians(-30))
                        .back(18)
                        .build());
        }







//
//
//        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
//                .forward(27)
//                .build());
//
//        switch(propPosition) {
//            case LEFT:
//                drive.turn(Math.toRadians(-90));
//                break;
//            case CENTER:
//                drive.turn(Math.toRadians(180));
//                break;
//            case RIGHT:
//                drive.turn(Math.toRadians(90));
//                break;
//            case NOPOS:
//                break;
//            default:
//                break;
//        }

//        drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                .back(10)
//                .forward(7)
//                .turn(Math.toRadians(180))
//                .addDisplacementMarker(() -> {
//                    // spin intake
//                    intakeMotor.set(0.1);
//                })
//                .waitSeconds(2)
//                .addDisplacementMarker(() -> {
//                    // stop intake
//                    intakeMotor.set(0);
//                })
//                .back(3)
//                .build());



//        if (colorInd == 2) {
//            // blue (needs to face right)
//
//            switch(propPosition) {
//                case LEFT:
//                    drive.turn(Math.toRadians(-180));
//                    break;
//                case CENTER:
//                    drive.turn(Math.toRadians(90));
//                    break;
//                case RIGHT:
//                    break;
//                case NOPOS:
//                    break;
//                default:
//                    break;
//            }
//        } else {
//            // red (needs to face left)
//            switch(propPosition) {
//                case LEFT:
//                case CENTER:
//                    drive.turn(Math.toRadians(90));
//                case RIGHT:
//                    drive.turn(Math.toRadians(180));
//                case NOPOS:
//                default:
//            }
//        }
//
//
//        if (isFarSide) {
//            drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                    .back(farBackdropDistance)
//                    .build());
//        } else {
//            drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                    .back(farBackdropDistance - 48)
//                    .build());
//        }
//
//        // score
//        MainOpMode mainOpMode = new MainOpMode();
////        mainOpMode.armPickup.schedule();

    }
}