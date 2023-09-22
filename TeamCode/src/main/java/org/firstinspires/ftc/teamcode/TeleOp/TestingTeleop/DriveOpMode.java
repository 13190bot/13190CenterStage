package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class DriveOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gb1(GamepadKeys.Button.X).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);
    }

    @Override
    public void run() {
        super.run();
    }
}
