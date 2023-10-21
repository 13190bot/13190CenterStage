package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.GamepadSubsystem;

@TeleOp(name = "DriveOpMode")

public class DriveOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.CIRCLE).toggleWhenPressed(driveRobotCentricSlowModeCommand);
        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.SQUARE).whenPressed(new InstantCommand(() -> {
            gamepadSubsystem.rumbleGmp1();
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
