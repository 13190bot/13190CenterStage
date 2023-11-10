package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;

import java.util.function.BooleanSupplier;

@Config
public class MoveArmAndPitchCommand extends CommandBase {
    private ArmSubsystem armSubsystem;

    public static double downPitch = 1;
    public static double upPitch = 0;
    public static double downArm = 1;
    public static double upArm = 0;

    public MoveArmAndPitchCommand(ArmSubsystem armSubsystem, GamepadEx gamepadEx2, BooleanSupplier booleanSupplier){
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void execute(){

    }
}
