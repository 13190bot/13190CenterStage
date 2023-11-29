package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="DONOTUSE_MainAuto")
public class MainDysfunctionalAuto extends OpMode {

    private OpenCvCamera camera;

    private ColorDetectionYCRCBPipeline colorDetectionYCRCBPipeline;

    private ColorDetectionYCRCBPipeline.PropPosition position;

    private int colorInd = 1;
    private int count;

    private ClawSubsystem clawSubsytem;
    private ArmSubsystem armSubsystem;
    private LiftSubsystem liftSubsystem;

    private SampleMecanumDrive drive;

    private MotorEx fl, fr, bl, br, intakeMotor, leftLift, rightLift;
    private SimpleServo clawServo, armServo, pitchServo;

    public enum AutoState {
        WAIT_FOR_PRELOAD,
        MOVE_TO_PROP,
        MOVE_TO_BACKDROP,
        MOVE_TO_STACK,
        PARK
    }

    private AutoState autoState = AutoState.WAIT_FOR_PRELOAD;

    @Override
    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        colorDetectionYCRCBPipeline = new ColorDetectionYCRCBPipeline(colorInd);

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

        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");

        fr.setInverted(true);
        br.setInverted(true);

        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");

        leftLift = new MotorEx(hardwareMap, "liftLeft");
        rightLift = new MotorEx(hardwareMap, "liftRight");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 180);
        armServo = new SimpleServo(hardwareMap, "arm", 0, 255);
        pitchServo = new SimpleServo(hardwareMap, "pitch", 0, 255);

        clawSubsytem = new ClawSubsystem(clawServo);
        armSubsystem = new ArmSubsystem(clawServo);
        liftSubsystem = new LiftSubsystem(leftLift, rightLift, telemetry);



    }

    @Override
    public void init_loop() {
        position = colorDetectionYCRCBPipeline.getPosition();
    }

    @Override
    public void loop() {

        switch (autoState) {
            case WAIT_FOR_PRELOAD:
                clawSubsytem.grab();

                autoState = AutoState.MOVE_TO_PROP;
                break;
            case MOVE_TO_PROP:

                switch (position) {
                    case LEFT:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(new Pose2d())

                                .lineToLinearHeading(new Pose2d(-35, -35, Math.toRadians(270)))
                                .addDisplacementMarker(() -> {
                                    // add pixel placement code here
                                })
                                .strafeRight(30)
                                .build());

                        break;
                    case CENTER:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(new Pose2d())

                                .lineToLinearHeading(new Pose2d(-20, -35, Math.toRadians(0)))
                                .addDisplacementMarker(() -> {
                                    // add pixel placement code here
                                })
                                .forward(15)
                                .build());

                        break;
                    case RIGHT:
                        drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(new Pose2d())

                                .lineToLinearHeading(new Pose2d(-35, -35, Math.toRadians(90)))
                                .addDisplacementMarker(() -> {
                                    // add pixel placement code here
                                })
                                .strafeRight(30)
                                .build());

                        break;
                }

                autoState = AutoState.MOVE_TO_BACKDROP;
                break;
            case MOVE_TO_BACKDROP:
                drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(new Pose2d())

                        .splineToLinearHeading(new Pose2d(-35, 45, Math.toRadians(270)), Math.toRadians(-180))
                        .addDisplacementMarker(() -> {
                            // add pixel placement code here
                        })
                        .build());

                count++;

//                autoState = (count != 5) ? AutoState.MOVE_TO_STACK : AutoState.PARK;
                autoState = AutoState.PARK;
                break;
            case MOVE_TO_STACK:
                drive.followTrajectorySequenceAsync(drive.trajectorySequenceBuilder(new Pose2d())

                        .lineToLinearHeading(new Pose2d(-60, 30, Math.toRadians(270)))
                        .forward(60)
                        .splineToLinearHeading(new Pose2d(-35, -56, Math.toRadians(90)), Math.toRadians(-359))
                        .addDisplacementMarker(() -> {
                            // add pixel placement code here
                        })
                        .strafeRight(30)
                        .build());

                autoState = AutoState.MOVE_TO_BACKDROP;
                break;
            case PARK:
                //rr code in here

                autoState = AutoState.PARK;
                break;
        }

    }
}
