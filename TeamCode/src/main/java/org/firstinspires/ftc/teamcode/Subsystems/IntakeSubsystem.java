package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class IntakeSubsystem extends SubsystemBase {

    private MotorEx intakeMotor;

    public IntakeSubsystem(MotorEx intakeMotor) {
        this.intakeMotor = intakeMotor;
    }


    public void startIntake() {
        intakeMotor.motor.setPower(0.7);
    }
    public void stopIntake() {
        intakeMotor.motor.setPower(0);
    }
}
