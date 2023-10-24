package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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

//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        gamepadEx2.getGamepadButton(PlaystationAliases.CROSS).toggleWhenPressed(startIntakeCommand);
        gamepadEx2.getGamepadButton(PlaystationAliases.SQUARE).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);

        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);

    }

    public void run()
    {
        super.run();
    }
}