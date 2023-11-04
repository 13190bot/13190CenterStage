package org.firstinspires.ftc.teamcode.tests_n_stuff;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

@Config(value = "intake tuning values")
@TeleOp(name = "intake speed tuner", group = "Tuning")
public class IntakeTuningOpMode extends LinearOpMode {
    IntakeSubsystem intake;
    MotorEx intakeMotor;
    public static double intakeSpeed = .5;

    @Override
    public void runOpMode() throws InterruptedException {
        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");

        intake = new IntakeSubsystem(intakeMotor);

        waitForStart();

        while (opModeIsActive()) {
            intake.startIntake();
            intake.setIntakeSpeed(intakeSpeed);
        }
    }
}
