package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;

import java.util.function.DoubleSupplier;

@Config
public class ArmSubsystem extends SubsystemBase {

    public static double armPosUp = 0.5;
    public static double armPosDown = 1;

    public SimpleServo arm;

    public ArmSubsystem(SimpleServo arm) {
        this.arm = arm;
    }

    public void armUp() {
       arm.setPosition(armPosUp);
    }

    public void armDown() {
        arm.setPosition(armPosDown);
    }




}
