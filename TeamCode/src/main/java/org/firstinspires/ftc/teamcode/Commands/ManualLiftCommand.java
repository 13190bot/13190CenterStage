package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;

public class ManualLiftCommand extends CommandBase {
    private LiftSubsystem liftSubsystem;
    private GamepadEx gamepadEx2;
    private double power;

    public ManualLiftCommand(LiftSubsystem liftSubsystem, GamepadEx gamepadEx2) {
        this.liftSubsystem = liftSubsystem;
        this.gamepadEx2 = gamepadEx2;

        addRequirements(liftSubsystem);
    }

    @Override
    public void execute() {
            power = (gamepadEx2.getLeftY())*0.8;


                if (liftSubsystem.checkLimits(power)) {


                    if (power != 0) {
                        liftSubsystem.liftRight.set(-power);
                        liftSubsystem.liftLeft.set(-power);
                    } else {
                        //Stabilize
                        liftSubsystem.liftRight.set(0);
                        liftSubsystem.liftLeft.set(0);
                    }
                } else {
                    liftSubsystem.liftRight.set(0);
                    liftSubsystem.liftLeft.set(0);
                }



    }
}
