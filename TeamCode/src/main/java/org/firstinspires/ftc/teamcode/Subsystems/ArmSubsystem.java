package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;

public class ArmSubsystem extends SubsystemBase {
    public static SimpleServo arm;

    public ArmSubsystem(SimpleServo arm) {
        this.arm = arm;
    }

    public void moveArm(double pos) {
        arm.setPosition(pos);
    }



}
