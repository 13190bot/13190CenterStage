package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class AxleMoveCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private GamepadEx gamepad2;
    private double axleUpPos = 1;
    private double axleDownPos = 0;
    private CRServo axle;

    // Manual with double right y pos
    // Two booleans for up and down preset




    public AxleMoveCommand(ClawSubsystem clawSubsystem, GamepadEx gamePad2, CRServo axle){
        this.clawSubsystem = clawSubsystem;
        this.gamepad2 = gamePad2;
        this.axle = axle;
    addRequirements(clawSubsystem);
    }


    @Override
    public void execute(){
     axle.set(gamepad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - gamepad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
    }
}
