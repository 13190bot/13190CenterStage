package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

import org.opencv.core.Scalar;

public class PixelColorData {
    public static Object[][] colorData = new Object[][]{
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

    public static Scalar getScalarFromColor(PixelColor pixelColor) {
        for (int i = 0; i < colorData.length; i++) {
            if (colorData[i][1] == pixelColor) {
                return (Scalar) colorData[i][0];
            }
        }
        return null;
    }
}