package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class IntakeSubsystem extends SubsystemBase {

    private MotorEx intakeMotor;
    private double INTAKE_SPEED = .5;

    //debug method
    public void setIntakeSpeed (double intakeSpeed) {
        this.INTAKE_SPEED = intakeSpeed;
    }

    public IntakeSubsystem(MotorEx intakeMotor) {
        this.intakeMotor = intakeMotor;
    }


    public void startIntake() {
        intakeMotor.motor.setPower(INTAKE_SPEED);
    }
    public void stopIntake() {
        intakeMotor.motor.setPower(0);
    }
}
