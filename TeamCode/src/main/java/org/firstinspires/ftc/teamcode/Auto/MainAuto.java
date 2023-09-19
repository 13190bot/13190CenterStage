package org.firstinspires.ftc.teamcode.Auto;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.cv.PropDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@Autonomous(name="MainAuto")
public class MainAuto extends OpMode {

    private OpenCvCamera camera;
    private PropDetectionPipeline propDetectionPipeline;

    private PropDetectionPipeline.PropPosition position;

    private MotorEx fl, fr, bl, br, intakeMotor, leftLift, rightLift;
    private SimpleServo clawServo, axleServo;

    public enum AutoState {
        WAIT_FOR_PRELOAD,
        MOVE_TO_PROP,
        MOVE_TO_BACKDROP,
        BACKDROP_TO_STACK,
        STACK_TO_BACKDROP,
        PARK
    }

    private AutoState autoState = AutoState.WAIT_FOR_PRELOAD;

    @Override
    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        propDetectionPipeline = new PropDetectionPipeline();

        camera.setPipeline(propDetectionPipeline);
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

        leftLift = new MotorEx(hardwareMap, "leftLift");
        rightLift = new MotorEx(hardwareMap, "rightLift");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 180);
        axleServo = new SimpleServo(hardwareMap, "axle", 0, 180);

    }

    @Override
    public void init_loop() {
        position = propDetectionPipeline.getPosition();
    }

    @Override
    public void loop() {

        switch (autoState) {
            case WAIT_FOR_PRELOAD:
                //rr code in here

                autoState = AutoState.MOVE_TO_PROP;
                break;
            case MOVE_TO_PROP:

                switch (position) {
                    case LEFT:
                        //rr code in here

                        break;
                    case CENTER:
                        //rr code in here

                        break;
                    case RIGHT:
                        //rr code in here

                        break;
                }

                autoState = AutoState.MOVE_TO_BACKDROP;
                break;
            case MOVE_TO_BACKDROP:
                //rr code in here

                autoState = AutoState.BACKDROP_TO_STACK;
                break;
            case BACKDROP_TO_STACK:
                //rr code in here

                autoState = AutoState.STACK_TO_BACKDROP;
                break;
            case STACK_TO_BACKDROP:
                //rr code in here

                autoState = AutoState.BACKDROP_TO_STACK;
                break;
            case PARK:
                //rr code in here

                autoState = AutoState.PARK;
                break;
        }

    }
}
