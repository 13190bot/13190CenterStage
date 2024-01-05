// for testing color sensors

package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;


@TeleOp(name = "Color Sensor Testing")
public class ColorSensorTest extends LinearOpMode {
    // defining the color sensors
    ColorSensor leftColorSensor, rightColorSensor;


    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: fix the name of color sensors
        leftColorSensor = hardwareMap.get(ColorSensor.class, "leftSensor");
        rightColorSensor = hardwareMap.get(ColorSensor.class, "rightSensor");

        telemetry.addData("Initialization:", "Initialized, variables defined.");

        int leftLight = leftColorSensor.alpha();
        int rightLight = rightColorSensor.alpha();

        const int SCALE = 256;

        telemetry.addData("Light Levels: ", "Left Color Sensor: " + leftLight + "\n" + "Right Color Sensor: " + rightLight);
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {
            // RGB Values of the left and right color sensors
            // TODO: test hue instead of RGB individually. hue() returns 0-360, which is degree on hue wheel.
              int[] leftValues = new int[3];
              int[] rightValues = new int[3];
              int leftHue = leftColorSensor.argb();
              int rightHue = rightColorSensor.argb();



            // defining R, G, and B values of left, scaled to 0-255
            leftValues[0] = leftColorSensor.red()/SCALE;
            leftValues[1] = leftColorSensor.green()/SCALE;
            leftValues[2] = leftColorSensor.blue()/SCALE;

            // same w right
            rightValues[0] = rightColorSensor.red()/SCALE;
            rightValues[1] = rightColorSensor.green()/SCALE;
            rightValues[2] = rightColorSensor.blue()/SCALE;

            if (leftValues[0] ) {

            }


            telemetry.addData("left info", "\nRed: " + leftValues[0] + "\n" + "Green: " + leftValues[1] + "\n" + "Blue: " + leftValues[2]);
            telemetry.addData("right info", "\nRed: " + rightValues[0] + "\n" + "Green: " + rightValues[1] + "\n" + "Blue: " + rightValues[2]);
            telemetry.update();
            sleep(100);


        }

    }
}
