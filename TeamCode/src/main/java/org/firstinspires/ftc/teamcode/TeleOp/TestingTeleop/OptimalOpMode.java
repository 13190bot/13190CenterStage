package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Commands.DriveRobotCentricCommand;
import org.firstinspires.ftc.teamcode.Subsystems.GamepadSubsystem;

@TeleOp(name = "OptimalOpMode")
/*
Pushing the limits of mecanum drive
New motions!
 */
public class OptimalOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        /*
        Left joystick (x, y): Movement
        Right joystick (x): Rotate
        Left and right trigger (v): Rotate
        Left and right bumpers (true/false): WheelRotate
         */
        driveRobotCentricCommand = new DriveRobotCentricCommand(
                driveSubsystem,
                () -> {
                    double y = gamepadEx1.getLeftY();
                    if (gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) || gamepadEx1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                        if (y > 0) {
                            return y; // Back wheel (going forwards)
                        } else if (y < 0) {
                            return 0; // TODO Front wheel (going backwards)
                        } else {
                            return 0; // None
                        }
                    } else {
                        return y;
                    }
                },
                () -> {
                    double y = gamepadEx1.getLeftY();
                    if (gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
                        if (y == 0) {
                            // None or rotate
                            return 0; // TODO
                        } else {
                           return -y;
                        }
                    } else if (gamepadEx1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                        if (y == 0) {
                            // None or rotate
                            return 0; // TODO
                        } else {
                            return y;
                        }
                    } else {
                        return gamepadEx1.getLeftX();
                    }
                },
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + gamepadEx1.getRightX()
        );

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);
    }

    @Override
    public void run() {
        double rightTrigger = gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);

        super.run();
    }
}
