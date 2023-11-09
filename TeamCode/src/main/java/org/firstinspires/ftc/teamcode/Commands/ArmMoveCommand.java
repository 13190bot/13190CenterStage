package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;

public class ArmMoveCommand extends CommandBase {
    private ArmSubsystem armSubsystem;
    private GamepadEx gamepadEx2;

    public ArmMoveCommand(ArmSubsystem armSubsystem, GamepadEx gamepadEx2) {
        this.armSubsystem = armSubsystem;
        this.gamepadEx2 = gamepadEx2;

        addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
        armSubsystem.moveArm(gamepadEx2.getLeftY());
    }
}
