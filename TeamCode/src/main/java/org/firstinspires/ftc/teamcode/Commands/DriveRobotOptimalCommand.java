package org.firstinspires.ftc.teamcode.Commands;
import com.acmerobotics.roadrunner.drive.Drive;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

import java.util.function.DoubleSupplier;

public class DriveRobotOptimalCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private GamepadEx gamepadEx1;
//    private double forward, strafe, rotate;

    private boolean slowmodeOn = false;

    //CONFIG

    static double slowmodeMultiplier = 0.3;

    public DriveRobotOptimalCommand(DriveSubsystem driveSubsystem, GamepadEx gamepadEx1) {
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
        if (gamepadEx1.wasJustPressed(PlaystationAliases.CIRCLE)) {
            slowmodeOn = !slowmodeOn;
            if (slowmodeOn) {
                gamepadEx1.gamepad.setLedColor(255,0,239,200);
                driveSubsystem.speedMultiplier = slowmodeMultiplier;
            } else {
                gamepadEx1.gamepad.setLedColor(255,0,239,200);
                driveSubsystem.speedMultiplier = 1;
            }
        }

        if (gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) || gamepadEx1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            // WheelRotate
            double side = gamepadEx1.getButton(GamepadKeys.Button.LEFT_BUMPER) ? -1 : 1;
            driveSubsystem.driveRobotCentric(0, side, side);
        } else {
            // Normal
            driveSubsystem.driveRobotCentric(gamepadEx1.getRightX(), -gamepadEx1.getLeftY(), gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
//            driveSubsystem.driveRobotCentric(strafe, forward, rotate);
        }
    }
}
