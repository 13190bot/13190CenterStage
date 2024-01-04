// for testing color sensors

package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;

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
        leftColorSensor = hardwareMap.get(ColorSensor.class, "left");
        rightColorSensor = hardwareMap.get(ColorSensor.class, "right");
        telemetry.addData("Initialization:", "Initialized, variables defined.");
        int leftLight = leftColorSensor.alpha();
        int rightLight = rightColorSensor.alpha();
        telemetry.addData("Light Levels: ", "Left Color Sensor: " + leftLight + "\n" + "Right Color Sensor: " + rightLight);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // RGB Values of the left and right color sensors
            // TODO: test hue instead of RGB individually. hue() returns 0-360, which is degree on hue wheel.
            int[] leftValues = new int[3];
            int[] rightValues = new int[3];

            // defining R, G, and B values of left
            leftValues[0] = leftColorSensor.red();
            leftValues[1] = leftColorSensor.green();
            leftValues[2] = leftColorSensor.blue();

            // same w right
            rightValues[0] = rightColorSensor.red();
            rightValues[1] = rightColorSensor.green();
            rightValues[2] = rightColorSensor.blue();

            telemetry.addData("left info: ", "Red: " + leftValues[0] + "\n" + "Green: " + leftValues[1] + "\n" + "Blue: " + leftValues[2]);
            telemetry.addData("right info: ", "Red: " + rightValues[0] + "\n" + "Green: " + rightValues[1] + "\n" + "Blue: " + rightValues[2]);
            telemetry.update();
            Thread.sleep(100);


        }

    }
}
