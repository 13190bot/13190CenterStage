package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import org.firstinspires.ftc.teamcode.Subsystems.GamepadSubsystem;


public class DriveOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.CIRCLE).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);
    }

    @Override
    public void run() {
        super.run();
    }
}
