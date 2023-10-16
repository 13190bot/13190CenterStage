package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadSubsystem extends SubsystemBase {
    private static GamepadEx gamepadEx1;
    private static GamepadEx gamepadEx2;

    public enum PLAYSTATION_BUTTONS {
        CIRCLE(GamepadKeys.Button.B),
        CROSS(GamepadKeys.Button.A),
        TRIANGLE(GamepadKeys.Button.Y),
        SQUARE(GamepadKeys.Button.X),
        SHARE(GamepadKeys.Button.BACK),
        OPTIONS(GamepadKeys.Button.START);
        public final GamepadKeys.Button button;

        PLAYSTATION_BUTTONS(GamepadKeys.Button button) {
            this.button = button;
        }



        public GamepadKeys.Button getButton() {
            return button;
        }
    }
    public enum TOUCHPAD {
        FINGER_1,
        FINGER_2,
        FINGER_1_X,
        FINGER_2_X,
        FINGER_1_Y,
        FINGER_2_Y,

    }


    public GamepadSubsystem(GamepadEx gamepadEx1, GamepadEx gamepadEx2) {
        this.gamepadEx1 = gamepadEx1;
        this.gamepadEx2 = gamepadEx2;
    }

    /**
     * Get a gamepad button based on PS5 button layout
     */
    public static GamepadButton gb1(PLAYSTATION_BUTTONS button) {
        return gamepadEx1.getGamepadButton(button.getButton());
    }

    public static GamepadButton gb2(PLAYSTATION_BUTTONS button) {
        return gamepadEx2.getGamepadButton(button.getButton());
    }

    /**
     * Rumble the gamepad with a set duration
     */
    public static void rumbleGmp1(double rumble1, double rumble2,int durationMs) {
        gamepadEx2.gamepad.rumble(rumble1, rumble2, durationMs);
    }
    public static void rumbleGmp2(double rumble1, double rumble2,int durationMs) {
        gamepadEx2.gamepad.rumble(rumble1, rumble2, durationMs);
    }

    /**
     * Rumble the gamepad with a custom-built rumble effect
     */
    public void customRumbleGmp1(Gamepad.RumbleEffect rumbleEffect){
        gamepadEx1.gamepad.runRumbleEffect(rumbleEffect);
    }
    public void customRumbleGmp2(Gamepad.RumbleEffect rumbleEffect){
        gamepadEx2.gamepad.runRumbleEffect(rumbleEffect);
    }

    /**
     * Set the color of the gamepad with a set duration
     */
    public static void colorGmp1(double red, double green, double blue,int durationMs) {
        gamepadEx1.gamepad.setLedColor(red, green, blue,durationMs);
    }
    public static void colorGmp2(double red, double green, double blue,int durationMs) {
        gamepadEx2.gamepad.setLedColor(red, green, blue,durationMs);
    }

    /**
     * Set the color of the gamepad with a continuous duration
     */
    public static void colorGmp1(double red, double green, double blue) {
        gamepadEx1.gamepad.setLedColor(red, green, blue, Gamepad.LED_DURATION_CONTINUOUS);
    }
    public static void colorGmp2(double red, double green, double blue) {
        gamepadEx2.gamepad.setLedColor(red, green, blue, Gamepad.LED_DURATION_CONTINUOUS);
    }

    /**
     * Run a custom built LED effect
     */
    public static void customColorGmp1(Gamepad.LedEffect ledEffect){
        gamepadEx1.gamepad.runLedEffect(ledEffect);
    }
    public static void customColorGmp2(Gamepad.LedEffect ledEffect){
        gamepadEx2.gamepad.runLedEffect(ledEffect);
    }

    /**
     * Get the value of a touchpad
     */
    public static float touchPadGmp1(TOUCHPAD touchpad) {
        switch (touchpad) {
            case FINGER_1_X: {
                return gamepadEx1.gamepad.touchpad_finger_1_x;
            }
            case FINGER_1_Y: {
                return gamepadEx1.gamepad.touchpad_finger_1_y;
            }
            case FINGER_2_X: {
                return gamepadEx1.gamepad.touchpad_finger_2_x;
            }
            case FINGER_2_Y: {
                return gamepadEx1.gamepad.touchpad_finger_2_y;
            }
            default: {
                return 0;
            }

        }
    }
    public static float touchPadGmp2(TOUCHPAD touchpad) {
        switch (touchpad) {
            case FINGER_1_X: {
                return gamepadEx2.gamepad.touchpad_finger_1_x;
            }
            case FINGER_1_Y: {
                return gamepadEx2.gamepad.touchpad_finger_1_y;
            }
            case FINGER_2_X: {
                return gamepadEx2.gamepad.touchpad_finger_2_x;
            }
            case FINGER_2_Y: {
                return gamepadEx2.gamepad.touchpad_finger_2_y;
            }
            default: {
                return 0;
            }

        }
    }

    /**
     * Check if a touchpad is being touched
     */
    public static Boolean isTouchingGmp1(TOUCHPAD touchpad) {
        switch (touchpad) {
            case FINGER_1:
            case FINGER_1_X:
            case FINGER_1_Y: {
                return gamepadEx1.gamepad.touchpad_finger_1;
            }
            case FINGER_2:
            case FINGER_2_X:
            case FINGER_2_Y: {
                return gamepadEx1.gamepad.touchpad_finger_2;
            }


            default: {
                return false;
            }

        }
    }
    public static boolean isTouchingGmp2(TOUCHPAD touchpad){
        switch (touchpad){
            case FINGER_1:
            case FINGER_1_X:
            case FINGER_1_Y: {
                return gamepadEx2.gamepad.touchpad_finger_1;
            }
            case FINGER_2:
            case FINGER_2_X:
            case FINGER_2_Y: {
                return gamepadEx2.gamepad.touchpad_finger_2;
            }

            default: {
                return false;
            }
        }
    }


}
