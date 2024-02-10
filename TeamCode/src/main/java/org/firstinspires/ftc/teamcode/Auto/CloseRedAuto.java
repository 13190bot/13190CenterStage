package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import static org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode.*;

@Config
@Autonomous(name = "CloseRedAuto")
public class CloseRedAuto extends BaseOpMode {
    private OpenCvCamera camera;

    // 2 = blue, 1 = red
    private int colorInd = 1;
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

        intakeMotor.resetEncoder();

        switch(propPosition) {
            case LEFT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(30)
                        .strafeRight(11)

                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.25)
                        .addTemporalMarker(() -> {
                            int intakeTarget = 150 * (Math.round(intakeMotor.getCurrentPosition() / 150 ));
                            telemetry.addData("intakeTarget",intakeTarget);
                            telemetry.addData("Current Pos",intakeMotor.getCurrentPosition());
                            telemetry.update();
                            intakeMotor.setTargetPosition(150);
                            while ( (150 - 10 <= intakeMotor.getCurrentPosition()) && (intakeMotor.getCurrentPosition() <= 150 +10)) {
                                intakeMotor.set(0.23);
                            }

                            telemetry.addData("Reached",intakeMotor.getCurrentPosition());
                            telemetry.update();

                            intakeMotor.set(0);

                        })
                        .strafeLeft(11)
                        .turn(Math.toRadians(90))
                        .back(38)
                        .build());
                break;
            case CENTER:
            case NOPOS:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(40)
                        .back(11)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.25)
                        .addTemporalMarker(() -> {
                            int intakeTarget = 150 * (Math.round(intakeMotor.getCurrentPosition() / 150 ));
                            telemetry.addData("intakeTarget",intakeTarget);
                            telemetry.addData("Current Pos",intakeMotor.getCurrentPosition());
                            telemetry.update();
                            intakeMotor.setTargetPosition(150);
                            while ( (150 - 10 <= intakeMotor.getCurrentPosition()) && (intakeMotor.getCurrentPosition() <= 150 +10)) {
                                intakeMotor.set(0.2);
                            }

                            telemetry.addData("Reached",intakeMotor.getCurrentPosition());
                            telemetry.update();

                            intakeMotor.set(0);

                        })
                        .back(9)

                        .turn(Math.toRadians(90))
                        .back(38)
                        .strafeLeft(7)
                        .build());
                break;
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .forward(27)
                        .strafeLeft(14)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(-0.3);
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(() -> {
                            intakeMotor.set(0.25);
                        })
                        .waitSeconds(1.25)
                        .addTemporalMarker(() -> {
                            int intakeTarget = 150 * (Math.round(intakeMotor.getCurrentPosition() / 150 ));
                            telemetry.addData("intakeTarget",intakeTarget);
                            telemetry.addData("Current Pos",intakeMotor.getCurrentPosition());
                            telemetry.update();
                            intakeMotor.setTargetPosition(150);
                            while ( (150 - 10 <= intakeMotor.getCurrentPosition()) && (intakeMotor.getCurrentPosition() <= 150 +10)) {
                                intakeMotor.set(0.2);
                            }

                            telemetry.addData("Reached",intakeMotor.getCurrentPosition());
                            telemetry.update();

                            intakeMotor.set(0);

                        })
                        .back(5)
                        .turn(Math.toRadians(90))
                        .back(25.8)
                        .strafeLeft(7)
                        .build());
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
                        .strafeRight(22)
                        .turn(Math.toRadians(30))
                        .back(18)
                        .build());
                break;
            case NOPOS:
            case CENTER:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeRight(23)
                        .turn(Math.toRadians(30))
                        .back(18)
                        .build());
                break;
            case RIGHT:
                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
                        .strafeRight(23)
                        .turn(Math.toRadians(30))
                        .back(14)
                        .build());
                break;
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