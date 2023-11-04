package org.firstinspires.ftc.teamcode.Auto.comp1;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.CV.PropDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;

@Deprecated //too much time to tune
//@Config(value = "Position Based Auto Values")
//@Autonomous(name = "Position Based Auto", group = "Comp 1 Auto")
public class EncAuto extends LinearOpMode {

    public int turnDir;

    public DcMotor lf, lb, rf, rb, intake;

    private OpenCvCamera camera;
    private PropDetectionPipeline propDetectionPipeline;

    private PropDetectionPipeline.PropPosition position;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry);

        lf = hardwareMap.dcMotor.get("frontLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        lb = hardwareMap.dcMotor.get("backLeft");
        rb = hardwareMap.dcMotor.get("backRight");

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setTargetPosition(0);
        lb.setTargetPosition(0);
        rf.setTargetPosition(0);
        rb.setTargetPosition(0);

        lf.setPower(DRIVE_SPEED);
        lb.setPower(DRIVE_SPEED);
        rf.setPower(DRIVE_SPEED);
        rb.setPower(DRIVE_SPEED);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);

        intake = hardwareMap.dcMotor.get("intakeMotor");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {

//                //determine position
//                while (!gamepad1.b) {
//                    position = propDetectionPipeline.getPosition();
//                    telemetry.addData("Detected position", () -> position);
//                }
//
//                //turn position to direction
//                switch (position) {
//                    case LEFT:   turnDir =  1; break;
//                    case RIGHT:  turnDir = -1; break;
//                    case NOPOS:
//                    case CENTER: turnDir =  0; break;
//
//                    default: throw new IllegalArgumentException("Unrecognised Position: " + position);
//                }
//
//                //Turn to face spike
//                lf.setTargetPosition(LF_//);
//                lb.setTargetPosition(LB_PARK_POSITION);
//                rf.setTargetPosition(RF_PARK_POSITION);
//                rb.setTargetPosition(RB_PARK_POSITION);
//
//                telemetry.addData("Turning to face spike. Sleeping for (ms)", TURN_TIME);
//                sleep(TURN_TIME);
//
//                lf.setPower(0);
//                lb.setPower(0);
//                rf.setPower(0);
//                rb.setPower(0);
//
//                intake.setPower(INTAKE_SPEED);
//                sleep(1000);
//                intake.setPower(0);
//
//                //Turn back to normal
//                lf.setPower(DRIVE_SPEED * -turnDir);
//                lb.setPower(DRIVE_SPEED * -turnDir);
//                rf.setPower(DRIVE_SPEED * -turnDir);
//                rb.setPower(DRIVE_SPEED * -turnDir);
//
//                telemetry.addData("Turning to face normal. Sleeping for (ms)", TURN_TIME);
//                sleep(TURN_TIME);
//
//                lf.setPower(0);
//                lb.setPower(0);
//                rf.setPower(0);
//                rb.setPower(0);

                //strafe back to starting position
                lf.setTargetPosition(LF_STRAFE_POSITION);
                lb.setTargetPosition(LB_STRAFE_POSITION);
                rf.setTargetPosition(RF_STRAFE_POSITION);
                rb.setTargetPosition(RB_STRAFE_POSITION);

                telemetry.addData("Strafing. Sleeping for (ms)", STRAFE_TIME);
                sleep(STRAFE_TIME);

                //strafe to park
                lf.setTargetPosition(LF_PARK_POSITION);
                lb.setTargetPosition(LB_PARK_POSITION);
                rf.setTargetPosition(RF_PARK_POSITION);
                rb.setTargetPosition(RB_PARK_POSITION);

                telemetry.addData("Moving forwards. Sleeping for (ms)", FORWARDS_TIME);
                sleep(FORWARDS_TIME);

                lf.setPower(0);
                lb.setPower(0);
                rf.setPower(0);
                rb.setPower(0);
            }
        }
    }

    public static double DRIVE_SPEED = .1;
    public static double INTAKE_SPEED = .1;


    public static long STRAFE_TIME = 5000;
    public static long FORWARDS_TIME = 5000;
    public static long TURN_TIME = 2000;


    public static int LF_STRAFE_POSITION = 0;
    public static int RF_STRAFE_POSITION = 0;
    public static int LB_STRAFE_POSITION = 0;
    public static int RB_STRAFE_POSITION = 0;

    public static int LF_PARK_POSITION = 0;
    public static int LB_PARK_POSITION = 0;
    public static int RF_PARK_POSITION = 0;
    public static int RB_PARK_POSITION = 0;
}
