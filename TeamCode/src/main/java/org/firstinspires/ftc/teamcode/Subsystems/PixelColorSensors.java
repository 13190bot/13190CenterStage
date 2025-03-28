package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.CV.BestPixelPlacement.PixelColor;
import org.opencv.core.Scalar;


/**
 * sensor for the dustpan pixels
 */
public class PixelColorSensors {
    Object[][] colorData = new Object[][] {
            {
                    new Scalar(239, 238, 244),
                    PixelColor.WHITE
            },
            {
                    new Scalar(176, 154, 227),
                    PixelColor.PURPLE
            },
            {
                    new Scalar(255, 199, 0),
                    PixelColor.YELLOW
            },
            {
                    new Scalar(85, 180, 36),
                    PixelColor.GREEN
            }
    };

    public PixelColorSensors (HardwareMap hMap, String leftName, String rightName) {
        leftSensor  = hMap.colorSensor.get(leftName);
        rightSensor = hMap.colorSensor.get(rightName);

        leftSensor.enableLed(true);
        rightSensor.enableLed(true);
    }

    PixelColor leftPix, rightPix;
    public ColorSensor leftSensor, rightSensor;
    public PixelColor getLeft  () { return leftPix ; }
    public PixelColor getRight () { return rightPix; }

    public PixelColor updateIndividual (int r, int g, int b) {
        return PixelColor.NONE;
    }

    public void update () {
        leftPix  = updateIndividual(leftSensor.red() , leftSensor.green() , leftSensor.blue() );
        rightPix = updateIndividual(rightSensor.red(), rightSensor.green(), rightSensor.blue());
    }

    public static int TOLERANCE = -1;

    public static int R_WHITE  = -1;
    public static int R_PURPLE = -1;
    public static int R_YELLOW = -1;
    public static int R_GREEN  = -1;

    public static int G_WHITE  = -1;
    public static int G_PURPLE = -1;
    public static int G_YELLOW = -1;
    public static int G_GREEN  = -1;

    public static int B_WHITE  = -1;
    public static int B_PURPLE = -1;
    public static int B_YELLOW = -1;
    public static int B_GREEN  = -1;
}
