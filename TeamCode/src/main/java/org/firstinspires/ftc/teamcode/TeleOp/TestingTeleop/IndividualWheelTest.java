package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "Individual Wheel Test", group = "Testing")
public class IndividualWheelTest extends LinearOpMode {
    public static String MOTOR_NAME = "frontRight";

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            // In case of no dashboard
            if (gamepad1.dpad_up) {
                if (gamepad1.dpad_left) {
                    MOTOR_NAME = "frontLeft";
                } else if (gamepad1.dpad_right) {
                    MOTOR_NAME = "frontRight";
                }
            } else if (gamepad1.dpad_down) {
                if (gamepad1.dpad_left) {
                    MOTOR_NAME = "backLeft";
                } else if (gamepad1.dpad_right) {
                    MOTOR_NAME = "backRight";
                }
            }



            if (gamepad1.a) {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(1);
            } else if (gamepad1.b) {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(-1);
            } else {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(0);
            }
            telemetry.addData("motor", MOTOR_NAME);
            telemetry.update();
        }
    }
}