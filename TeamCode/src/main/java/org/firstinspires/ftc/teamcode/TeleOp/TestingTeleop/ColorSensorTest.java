package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import android.graphics.Color;
import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Config
@TeleOp(name = "Color Sensor Testing")
public class ColorSensorTest extends LinearOpMode {
    // defining the color sensors
    ColorSensor leftColorSensor, rightColorSensor;

    public static int SCALE = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: fix the name of color sensors
        leftColorSensor = hardwareMap.get(ColorSensor.class, "leftSensor");
        rightColorSensor = hardwareMap.get(ColorSensor.class, "rightSensor");

        telemetry.addData("Initialization:", "Initialized, variables defined.");

        int leftLight = leftColorSensor.alpha();
        int rightLight = rightColorSensor.alpha();

        float[] lhsvValues = {0F, 0F, 0F};
        float[] rhsvValues = {0F, 0F, 0F};

        telemetry.addData("Light Levels: ", "Left Color Sensor: " + leftLight + "\n" + "Right Color Sensor: " + rightLight);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // RGB Values of the left and right color sensors, also create hue variables.
            int[] leftValues = new int[3];
            int[] rightValues = new int[3];

              // RGB to HSV
            Color.RGBToHSV(leftColorSensor.red() * 8, leftColorSensor.green() * 8, leftColorSensor.blue() * 8, lhsvValues);
            Color.RGBToHSV(rightColorSensor.red() * 8, rightColorSensor.green() * 8, rightColorSensor.blue() * 8, rhsvValues);


            // defining R, G, and B values of left, scale factor removed for now
            leftValues[0] = leftColorSensor.red()/SCALE;
            leftValues[1] = leftColorSensor.green()/SCALE;
            leftValues[2] = leftColorSensor.blue()/SCALE;

            // same w right
            rightValues[0] = rightColorSensor.red()/SCALE;
            rightValues[1] = rightColorSensor.green()/SCALE;
            rightValues[2] = rightColorSensor.blue()/SCALE;



            telemetry.addData("left info", "\nRed: " + leftValues[0] + "\n" + "Green: " + leftValues[1] + "\n" + "Blue: " + leftValues[2] + "\nHue: " + lhsvValues[0]);
            telemetry.addData("right info", "\nRed: " + rightValues[0] + "\n" + "Green: " + rightValues[1] + "\n" + "Blue: " + rightValues[2] + "\nHue: " + rhsvValues[0]);
//
//
//            // what are the other values
            telemetry.addData("testing values", "left: \n" + lhsvValues[1] + "\n" + lhsvValues[2]);
            telemetry.addData("testing values", "left: \n" + rhsvValues[1] + "\n" + rhsvValues[2]);

            telemetry.update();
            sleep(100);


        }

    }
}
