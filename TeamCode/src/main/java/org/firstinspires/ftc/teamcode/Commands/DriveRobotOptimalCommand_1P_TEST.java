package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

public class DriveRobotOptimalCommand_1P_TEST extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private GamepadEx gamepadEx1;
//    private double forward, strafe, rotate;

    private boolean slowmodeOn = false;

    //CONFIG

    static double slowmodeMultiplier = 0.5;

    public DriveRobotOptimalCommand_1P_TEST(DriveSubsystem driveSubsystem, GamepadEx gamepadEx1) {
        this.driveSubsystem = driveSubsystem;
        this.gamepadEx1 = gamepadEx1;

        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        /*
        Left joystick (x, y): Movement
        Right joystick (x): Rotate
        Left and right trigger (v): Rotate
        Left and right bumpers (t/f): WheelRotate

        Circle (t/f): Toggle slowmode
         */

        // Slowmode

        gamepadEx1.readButtons();
        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
            slowmodeOn = !slowmodeOn;
            if (slowmodeOn) {
                gamepadEx1.gamepad.setLedColor(232,240,0,200);
                driveSubsystem.speedMultiplier = driveSubsystem.speedMultiplier * slowmodeMultiplier;
            } else {
                gamepadEx1.gamepad.setLedColor(232,240,0,200);
                driveSubsystem.speedMultiplier = driveSubsystem.speedMultiplier / slowmodeMultiplier;
            }
        }

        if (gamepadEx1.wasJustPressed(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
            driveSubsystem.speedMultiplier = -driveSubsystem.speedMultiplier;
        }

        if (gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) || gamepadEx1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            // WheelRotate
            double side1 = gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) ? -1 : 1;
            double side2 = gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) ? 0 : 0;
            driveSubsystem.driveRobotCentric(side2, side1, side1);
        } else {
            // Normal
            driveSubsystem.driveRobotCentric(gamepadEx1.getRightX(), gamepadEx1.getLeftY(), (gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)));
//            driveSubsystem.driveRobotCentric(strafe, forward, rotate);
        }
    }
}
