package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Set;


public class IntakeSubsystem extends SubsystemBase {

    private MotorEx intakeMotor;
    public static double intakeSpeed = 0.4;

    public IntakeSubsystem(MotorEx intakeMotor) {
        this.intakeMotor = intakeMotor;
    }


    public void startIntake() {
        intakeMotor.motor.setPower(intakeSpeed);
    }
    public void reverseIntake() {
        intakeMotor.motor.setPower(-intakeSpeed);
    }
    public void stopIntake() {
        intakeMotor.motor.setPower(0);
    }

    public Command startIntakeCommand() {
        return new InstantCommand() {

            @Override
            public void execute() {
                startIntake();
            }

            @Override
            public void end(boolean interrupted) {
                stopIntake();
            }


        };
    }

    public Command reverseIntakeCommand() {
        return new InstantCommand() {

            @Override
            public void execute() {
                reverseIntake();
            }

            @Override
            public void end(boolean interrupted) {
                stopIntake();
            }


        };
    }
}
