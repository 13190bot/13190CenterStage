package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Testing Lift Encoders")
public class TestingLiftEncoders extends OpMode {
    DcMotorEx slideRight, slideLeft, intake;
    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        slideLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        slideRight = hardwareMap.get(DcMotorEx.class, "liftRight");
        intake = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        slideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        telemetry.addData("left", slideLeft.getCurrentPosition());
        telemetry.addData("right", slideRight.getCurrentPosition());
        telemetry.addData("intake",intake.getCurrentPosition());

        if (intake.getCurrentPosition() < 150){
            intake.setPower(-0.3);
        } else if (intake.getCurrentPosition() > 150){
            intake.setPower(0.3);
        }
    }
}
