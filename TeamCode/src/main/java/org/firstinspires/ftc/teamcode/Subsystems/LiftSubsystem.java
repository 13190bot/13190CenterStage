package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;


public class LiftSubsystem extends SubsystemBase {

    private MotorEx liftRight, liftLeft;

    public LiftSubsystem(MotorEx liftRight, MotorEx liftLeft) {
        this.liftRight = liftRight;
        this.liftLeft = liftLeft;
    }


    public void lift(double power) {
        liftRight.motor.setPower(power);
        liftLeft.motor.setPower(power);
    }
}