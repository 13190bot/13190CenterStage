//// for testing color sensors
//
//package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.arcrobotics.ftclib.gamepad.GamepadEx;
//
//import com.qualcomm.hardware.rev.RevColorSensorV3;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.ColorSensor;
//
//@Config
//@TeleOp(name = "Color Sensor Testing")
//public class ColorSensorTest extends LinearOpMode {
//    // defining the color sensors
//    ColorSensor leftColorSensor, rightColorSensor;
//
//    public static int SCALE = 256;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // TODO: fix the name of color sensors
//        leftColorSensor = hardwareMap.get(RevColorSensorV3.class, "leftSensor");
//        rightColorSensor = hardwareMap.get(RevColorSensorV3.class, "rightSensor");
//
//        telemetry.addData("Initialization:", "Initialized, variables defined.");
//
//        int leftLight = leftColorSensor.alpha();
//        int rightLight = rightColorSensor.alpha();
//
//
//
//        telemetry.addData("Light Levels: ", "Left Color Sensor: " + leftLight + "\n" + "Right Color Sensor: " + rightLight);
//        telemetry.update();
//
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            // RGB Values of the left and right color sensors
//            // TODO: test hue instead of RGB individually. hue() returns 0-360, which is degree on hue wheel.
//              int[] leftValues = new int[3];
//              int[] rightValues = new int[3];
//              int leftHue = leftColorSensor.argb();
//              int rightHue = rightColorSensor.argb();
//
//
//
//            // defining R, G, and B values of left, scaled to 0-255
//            leftValues[0] = leftColorSensor.red()/SCALE;
//            leftValues[1] = leftColorSensor.green()/SCALE;
//            leftValues[2] = leftColorSensor.blue()/SCALE;
//
//            // same w right
//            rightValues[0] = rightColorSensor.red()/SCALE;
//            rightValues[1] = rightColorSensor.green()/SCALE;
//            rightValues[2] = rightColorSensor.blue()/SCALE;
//
//
//
//            telemetry.addData("left info", "\nRed: " + leftValues[0] + "\n" + "Green: " + leftValues[1] + "\n" + "Blue: " + leftValues[2]);
//            telemetry.addData("right info", "\nRed: " + rightValues[0] + "\n" + "Green: " + rightValues[1] + "\n" + "Blue: " + rightValues[2]);
//            telemetry.update();
//            sleep(100);
//
//
//        }
//
//    }
//}

/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
 *
 * This OpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The OpMode assumes that the color sensor
 * is configured with a name of "sensor_color".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "Color Sensor Test")
@Disabled
public class ColorSensorTest extends LinearOpMode {

    ColorSensor colorSensor;    // Hardware Device Object


    @Override
    public void runOpMode() {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float[] hsvValues = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float[] values = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our ColorSensor object.
        colorSensor = hardwareMap.get(ColorSensor.class, "leftSensor");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);

        // wait for the start button to be pressed.
        waitForStart();

        // while the OpMode is active, loop and read the RGB data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            // check the status of the x button on either gamepad.
            bCurrState = gamepad1.x;

            // check for button state transitions.
            if (bCurrState && (bCurrState != bPrevState))  {

                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                colorSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}

