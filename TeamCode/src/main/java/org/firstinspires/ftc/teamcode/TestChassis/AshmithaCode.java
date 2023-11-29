package org.firstinspires.ftc.teamcode.TestChassis;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp
public class AshmithaCode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                frontLeftMotor.setPower(1.0);
                frontRightMotor.setPower(1.0);
                backLeftMotor.setPower(1.0);
                backRightMotor.setPower(1.0);
            } else if (gamepad1.b) {
                frontLeftMotor.setPower(-1.0);
                frontRightMotor.setPower(-1.0);
                backLeftMotor.setPower(-1.0);
                backRightMotor.setPower(-1.0);
            } if (gamepad1.x) {
                frontLeftMotor.setPower(1.0);
                frontRightMotor.setPower(-1.0);
                backLeftMotor.setPower(-1.0);
                backRightMotor.setPower(1.0);
            } if (gamepad1.y) {
                frontLeftMotor.setPower(-1.0);
                frontRightMotor.setPower(1.0);
                backLeftMotor.setPower(1.0);
                backRightMotor.setPower(-1.0);
            }
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            telemetry.addData("y", y);
            telemetry.addData("x", x);
            telemetry.addData("rx", rx);

            frontLeftMotor.setPower(y + x + rx);
            backLeftMotor.setPower(y - x + rx);
            frontRightMotor.setPower(y - x - rx);
            backRightMotor.setPower(y + x - rx);
            telemetry.update();

        }

    }

}
