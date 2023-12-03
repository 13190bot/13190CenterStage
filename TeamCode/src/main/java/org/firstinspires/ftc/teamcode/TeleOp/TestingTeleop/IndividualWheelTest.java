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
            if (gamepad1.dpad_left) {

            }


            if (gamepad1.a) {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(1);
            } else if (gamepad1.b) {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(-1);
            } else {
                hardwareMap.dcMotor.get(MOTOR_NAME).setPower(0);
            }
        }
    }
}