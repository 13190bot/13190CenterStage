package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="SlidesTeleOp")
public class SlidesTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor liftLeft = hardwareMap.get(DcMotor.class, "liftRight");
        DcMotor liftRight = hardwareMap.get(DcMotor.class, "liftRight");

        waitForStart();

        while (opModeIsActive()) {
            // Manual lift
            double power = gamepad2.left_stick_y;

            liftLeft.setPower(power);
            liftRight.setPower(power);
        }
    }
}
