package org.firstinspires.ftc.teamcode.tests_n_stuff;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;

@Config(value = "ARM PIDF Tuner")
@TeleOp(name = "ARM_PID_TUNER")
public class PIDTunerArm extends LinearOpMode {
    LiftSubsystem lift;
    MotorEx liftLeft, liftRight;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry);
        telemetry.addData("Motor speed", () -> targetPos);

        liftLeft = new MotorEx(hardwareMap, "liftLeft");
        liftRight = new MotorEx(hardwareMap, "liftRight");

        lift = new LiftSubsystem(liftRight, liftLeft);

        waitForStart();

        while (opModeIsActive()) {
            //lift.setPIDF(kP, kI, kD, kF);
            //lift.setTarget(targetPos);
            //lift.runLift();
            lift.manualLift(targetPos);
            telemetry.update();
        }
    }


    public static double kP = 1, kI = 0, kD = 0, kF = 0;
    public static double targetPos;
}
