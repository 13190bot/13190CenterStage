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

    public static double armPosHome = 0;
    public static double armPosAway = 0;
    public static double pitchPosHome = 0;
    public static double pitchPosAway = 0;
    public SimpleServo arm, pitch;
    private DoubleSupplier doubleSupplier;

    public ArmSubsystem(SimpleServo arm, SimpleServo pitch) {
        this.arm =arm;
        this.pitch = pitch;
    }

    public void moveArm(double pos) {
       arm.setPosition(pos);
    }

    public void movePitch(double pos) {
       pitch.setPosition(pos);
    }

//    @Override
//    public void periodic() {
//        if(doubleSupplier.getAsDouble()!=0) {
//            arm.setPosition(0.3*doubleSupplier.getAsDouble() + 0.5);
//        }
//    }
}
