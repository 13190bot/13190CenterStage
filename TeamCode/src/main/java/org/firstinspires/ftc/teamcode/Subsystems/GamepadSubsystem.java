package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.jetbrains.annotations.NotNull;

public class GamepadSubsystem extends SubsystemBase {
    public static GamepadEx gamepadEx1;
    public static GamepadEx gamepadEx2;

    public enum PLAYSTATION_BUTTONS {
        R3(GamepadKeys.Button.LEFT_STICK_BUTTON),
        R2(GamepadKeys.Button.LEFT_STICK_BUTTON),
        DPAD_UP(GamepadKeys.Button.DPAD_UP),
        DPAD_DOWN(GamepadKeys.Button.DPAD_DOWN),
        DPAD_RIGHT(GamepadKeys.Button.DPAD_RIGHT),
        DPAD_LEFT(GamepadKeys.Button.DPAD_LEFT),
        L1(GamepadKeys.Button.LEFT_BUMPER),
        R1(GamepadKeys.Button.LEFT_BUMPER),
        CIRCLE(GamepadKeys.Button.B),
        CROSS(GamepadKeys.Button.A),
        TRIANGLE(GamepadKeys.Button.Y),
        SQUARE(GamepadKeys.Button.X),
        SHARE(GamepadKeys.Button.BACK),
        OPTIONS(GamepadKeys.Button.START);
        private final GamepadKeys.Button button;

        PLAYSTATION_BUTTONS(GamepadKeys.Button button) {
            this.button = button;
        }


        public GamepadKeys.Button getButton() {
            return button;
        }
    }

    public enum PLAYSTATION_TRIGGERS {
        R2(GamepadKeys.Trigger.RIGHT_TRIGGER),
        L2(GamepadKeys.Trigger.LEFT_TRIGGER);


        private final GamepadKeys.Trigger trigger;

        PLAYSTATION_TRIGGERS(GamepadKeys.Trigger trigger) {
            this.trigger = trigger;
        }

        public GamepadKeys.Trigger getTrigger() {
            return trigger;
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
    public GamepadButton gb1(PLAYSTATION_BUTTONS button) {
        return gamepadEx1.getGamepadButton(button.getButton());
    }

    public GamepadButton gb2(PLAYSTATION_BUTTONS button) {
        boolean ps = gamepadEx2.gamepad.square;
        return gamepadEx2.getGamepadButton(button.getButton());
    }
    /**
     * Get a trigger on the gamepad based on PS5 Trigger naming
     */
    public double gpT1(PLAYSTATION_TRIGGERS trigger) {
        return gamepadEx1.getTrigger(trigger.getTrigger());
    }
    public double gpT2(PLAYSTATION_TRIGGERS trigger) {
        return gamepadEx2.getTrigger(trigger.getTrigger());
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
     * Check if a touchpad is being pressed
     */
    public static Boolean touchPressedGmp1(){
        return gamepadEx1.gamepad.touchpad;
    }
    public static Boolean touchPressedGmp2(){
        return gamepadEx2.gamepad.touchpad;
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
