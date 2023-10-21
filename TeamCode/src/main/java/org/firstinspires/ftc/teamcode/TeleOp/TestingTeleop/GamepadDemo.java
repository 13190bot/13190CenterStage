package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.GamepadSubsystem;
import org.firstinspires.ftc.teamcode.util.CommandOpModeEx;
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

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.SQUARE).whenPressed(new InstantCommand(() -> {
            gamepadSubsystem.rumbleGmp1();
        }));
        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.CIRCLE).whenPressed(new InstantCommand(() -> {
            gamepadSubsystem.rumbleGmp1(0,0.5,1000);
        }));

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.CROSS).whenPressed(new InstantCommand(() -> {
            gamepadEx1.gamepad.rumble(rumbleStrength,rumbleStrength,1000);
        }));

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.DPAD_DOWN).whenPressed(new InstantCommand(() -> {
           gamepadEx1.gamepad.runRumbleEffect(testRumbleEffect);
        }));

        }

        @Override

        public void run(){
        super.initialize();
            gamepadEx1.gamepad.setLedColor(red,green,blue,Gamepad.RUMBLE_DURATION_CONTINUOUS);
            telemetry.addData("Touchpad Button",gamepadSubsystem.touchPressedGmp1());
        telemetry.addLine("______");
        telemetry.addData("Touching Touchpad, finger 1",gamepadSubsystem.isTouchingGmp1(GamepadSubsystem.TOUCHPAD.FINGER_1));
        telemetry.addData("Touching Touchpad, finger 2",gamepadSubsystem.isTouchingGmp1(GamepadSubsystem.TOUCHPAD.FINGER_2));
        telemetry.addLine("______");
        telemetry.addData("Finger 1 X",gamepadSubsystem.touchPadGmp1(GamepadSubsystem.TOUCHPAD.FINGER_1_X));
        telemetry.addData("Finger 1 Y",gamepadSubsystem.touchPadGmp1(GamepadSubsystem.TOUCHPAD.FINGER_1_Y));
        telemetry.addLine("______");
        telemetry.addData("Finger 2 X",gamepadSubsystem.touchPadGmp1(GamepadSubsystem.TOUCHPAD.FINGER_2_X));
        telemetry.addData("Finger 2 Y",gamepadSubsystem.touchPadGmp1(GamepadSubsystem.TOUCHPAD.FINGER_2_Y));
        telemetry.update();
        }
    }

