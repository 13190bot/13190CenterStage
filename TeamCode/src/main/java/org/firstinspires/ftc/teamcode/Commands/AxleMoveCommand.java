package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class AxleMoveCommand extends CommandBase {

    private ClawSubsystem clawSubsystem;
    private GamepadEx gamepad2;
    private double axleUpPos = 0.1;
    private double axleDownPos = -0.1;
    private DoubleSupplier targetPos;
    private SimpleServo axle;

    // Manual with double right y pos
    // Two booleans for up and down preset




    public AxleMoveCommand(ClawSubsystem clawSubsystem, GamepadEx gamePad2, SimpleServo axle){
        this.clawSubsystem = clawSubsystem;
        this.gamepad2 = gamePad2;
        this.axle = axle;

    }


    @Override
    public void execute(){
        if (gamepad2.getButton(GamepadKeys.Button.DPAD_UP))
            axle.setPosition(axleUpPos);
        else if (gamepad2.getButton(GamepadKeys.Button.DPAD_DOWN)) {
            axle.setPosition(axleDownPos);
        }else{
           axle.setPosition(gamepad2.getRightY()+axle.getPosition());
        }
    }
}
