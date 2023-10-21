package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.arcrobotics.ftclib.command.InstantCommand;
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
            gamepadSubsystem.rumbleGmp1(20,20,1000);
            telemetry.addData("Rumbling Controller",1);
            telemetry.update();
        }));

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);
    }

    @Override
    public void run() {
        super.run();
    }
}
