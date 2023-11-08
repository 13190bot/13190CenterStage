package org.firstinspires.ftc.teamcode.Auto.comp1;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.CV.PropDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config(value = "Time Based Auto Values")
@Autonomous(name = "Time Based Auto", group = "Comp 1 Auto")
public class TimeAuto extends LinearOpMode {

    public DcMotor lf, lb, rf, rb, intake;

    private OpenCvCamera camera;
    private PropDetectionPipeline propDetectionPipeline;

    private PropDetectionPipeline.PropPosition position;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry);

        VOLTAGE_SCALE = hardwareMap.getAll(LynxModule.class).get(0).getInputVoltage(VoltageUnit.VOLTS);
        telemetry.addData("voltage", VOLTAGE_SCALE);
        telemetry.update();
        
        VOLTAGE_SCALE = TUNED_VOLTAGE / VOLTAGE_SCALE;

        //init camera
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

        //init motors
        lf = hardwareMap.dcMotor.get("frontLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        lb = hardwareMap.dcMotor.get("backLeft");
        rb = hardwareMap.dcMotor.get("backRight");

        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.dcMotor.get("intakeMotor");

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.a) {

                //determine position
                while (!gamepad1.b) {
                    position = propDetectionPipeline.getPosition();
                    telemetry.addData("Detected position", () -> position);
                }

                //turn position to direction
                switch (position) {
                    case LEFT:   TURN_DIR =  1; break;
                    case RIGHT:  TURN_DIR = -1; break;
                    case NOPOS:
                    case CENTER: TURN_DIR =  0; break;

                    default: throw new IllegalArgumentException("Unrecognised Position: " + position);
                }

                //Turn to face spike
                lf.setPower(DRIVE_SPEED * VOLTAGE_SCALE * TURN_DIR);
                lb.setPower(DRIVE_SPEED * VOLTAGE_SCALE * TURN_DIR);
                rf.setPower(DRIVE_SPEED * VOLTAGE_SCALE * TURN_DIR);
                rb.setPower(DRIVE_SPEED * VOLTAGE_SCALE * TURN_DIR);

                telemetry.addData("Turning to face spike. Sleeping for (ms)", TURN_TIME);
                sleep(TURN_TIME);

                lf.setPower(0);
                lb.setPower(0);
                rf.setPower(0);
                rb.setPower(0);

                intake.setPower(INTAKE_SPEED);
                sleep(1000);
                intake.setPower(0);

                //Turn back to normal
                lf.setPower(DRIVE_SPEED * VOLTAGE_SCALE * -TURN_DIR);
                lb.setPower(DRIVE_SPEED * VOLTAGE_SCALE * -TURN_DIR);
                rf.setPower(DRIVE_SPEED * VOLTAGE_SCALE * -TURN_DIR);
                rb.setPower(DRIVE_SPEED * VOLTAGE_SCALE * -TURN_DIR);

                telemetry.addData("Turning to face normal. Sleeping for (ms)", TURN_TIME);
                sleep(TURN_TIME);

                lf.setPower(0);
                lb.setPower(0);
                rf.setPower(0);
                rb.setPower(0);

                //Strafe sideways
                lf.setPower(-DRIVE_SPEED * VOLTAGE_SCALE);
                lb.setPower( DRIVE_SPEED * VOLTAGE_SCALE);
                rf.setPower( DRIVE_SPEED * VOLTAGE_SCALE);
                rb.setPower(-DRIVE_SPEED * VOLTAGE_SCALE);

                telemetry.addData("Strafing. Sleeping for (ms)", STRAFE_TIME);
                sleep(STRAFE_TIME);

                //break
                lf.setPower(0);
                lb.setPower(0);
                rf.setPower(0);
                rb.setPower(0);

                lf.setPower(DRIVE_SPEED * VOLTAGE_SCALE);
                lb.setPower(DRIVE_SPEED * VOLTAGE_SCALE);
                rf.setPower(DRIVE_SPEED * VOLTAGE_SCALE);
                rb.setPower(DRIVE_SPEED * VOLTAGE_SCALE);

                telemetry.addData("Moving forwards. Sleeping for (ms)", FORWARDS_TIME);
                sleep(FORWARDS_TIME);

                lf.setPower(0);
                lb.setPower(0);
                rf.setPower(0);
                rb.setPower(0);
            }
        }
    }

    public static double VOLTAGE_SCALE;

    public static   int TURN_DIR = 1; //plus or minus 1
    public static double DRIVE_SPEED = .1;
    public static double INTAKE_SPEED = .1;
    public static double TUNED_VOLTAGE;

    public static long FORWARDS_TIME = 5000;
    public static long STRAFE_TIME = 5000;
    public static long TURN_TIME = 2000;
}
