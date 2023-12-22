package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Testing Odometry Encoders")
public class OdometryTest extends OpMode {
    DcMotorEx backLeft, frontLeft;
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        telemetry.addData("backLeft", backLeft.getCurrentPosition());
        telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
        telemetry.update();

    }
}
