package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

@Config
@TeleOp(name = "GamePad Demo OpMode")
public class GamepadDemo extends BaseDriveOpMode {
    public static double rumbleStrength = 0.5;
    public static int red = 255;
    public static int green = 255;
    public static int blue = 204;

    Gamepad.RumbleEffect testRumbleEffect = new Gamepad.RumbleEffect.Builder()
            .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
            .addStep(0.0, 0.0, 300)  //  Pause for 300 mSec
            .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
            .addStep(0.0, 0.0, 250)  //  Pause for 250 mSec
            .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
            .build();
    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(() -> {
            gamepadEx1.gamepad.rumble(0,1.0,500);
        });

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(() -> {
            gamepadEx1.gamepad.rumble(1.0,0,500);
        });



    }

    @Override

    public void run() {
        super.initialize();
        gamepadEx1.gamepad.setLedColor(red, green, blue, Gamepad.RUMBLE_DURATION_CONTINUOUS);
        telemetry.addData("Touchpad Button", gamepadEx1.gamepad.touchpad);
        telemetry.addLine("______");
        telemetry.addData("Touching Touchpad, finger 1", gamepadEx1.gamepad.touchpad_finger_1);
        telemetry.addData("Touching Touchpad, finger 2", gamepadEx1.gamepad.touchpad_finger_2);
        telemetry.addLine("______");
        telemetry.addData("Finger 1 X", gamepadEx1.gamepad.touchpad_finger_1_x);
        telemetry.addData("Finger 1 Y", gamepadEx1.gamepad.touchpad_finger_1_y);
        telemetry.addLine("______");
        telemetry.addData("Finger 2 X", gamepadEx1.gamepad.touchpad_finger_2_x);
        telemetry.addData("Finger 2 Y", gamepadEx1.gamepad.touchpad_finger_2_y);
        telemetry.update();
        if (gamepadEx1.gamepad.touchpad) {
            gamepadEx1.gamepad.setLedColor(0, 0, 0, Gamepad.RUMBLE_DURATION_CONTINUOUS);
        }
    }
}