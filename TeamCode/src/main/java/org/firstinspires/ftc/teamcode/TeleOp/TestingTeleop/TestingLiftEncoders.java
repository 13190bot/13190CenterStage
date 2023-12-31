package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Testing Lift Encoders")
public class TestingLiftEncoders extends OpMode {
    DcMotorEx slideRight, slideLeft;
    @Override
    public void init() {
        slideLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        slideRight = hardwareMap.get(DcMotorEx.class, "liftRight");
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("left", slideLeft.getCurrentPosition());
        telemetry.addData("right", slideRight.getCurrentPosition());
        telemetry.update();

    }
}
