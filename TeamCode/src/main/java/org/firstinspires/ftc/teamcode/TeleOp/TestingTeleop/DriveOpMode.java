package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

@TeleOp(name = "DriveOpMode")
@Disabled
public class DriveOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gamepadEx1.getGamepadButton(PlaystationAliases.CIRCLE).toggleWhenPressed(driveRobotCentricSlowModeCommand);
        gamepadEx1.getGamepadButton(PlaystationAliases.SQUARE).whenPressed(new InstantCommand(() -> {
            gamepadEx1.gamepad.rumble(Gamepad.RUMBLE_DURATION_CONTINUOUS);
            telemetry.addData("Rumbling Controller",1);
            telemetry.update();
        }));

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);
    }

    @Override
    public void run() {
        double rightTrigger = gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);

        super.run();
    }
}
