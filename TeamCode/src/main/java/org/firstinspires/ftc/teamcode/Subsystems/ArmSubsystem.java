package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;

public class ArmSubsystem extends SubsystemBase {
    public static CRServo arm;

    public ArmSubsystem(CRServo arm) {
        this.arm = arm;
    }

    public void moveArm(double speed) {
        arm.set(speed);
    }



}
