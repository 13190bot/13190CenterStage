package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

/*
TODO:
Implement photon https://github.com/8872/8872PP/pull/3/files
 */

@TeleOp(name = "MainTeleOp")
public class MainOpMode extends BaseOpMode {


    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);
       // register(clawSubsystem);
        register(intakeSubsystem);

//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        gamepadEx2.getGamepadButton(PlaystationAliases.CROSS).whileHeld(startIntakeCommand);
        gamepadEx2.getGamepadButton(PlaystationAliases.TRIANGLE).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);

       // clawSubsystem.setDefaultCommand(axleMoveCommand);
        //armSubsystem.setDefaultCommand(armMoveCommand);
        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);
        liftSubsystem.setDefaultCommand(manualLiftCommand);
    }

    public void run()
    {
        super.run();
    }
}