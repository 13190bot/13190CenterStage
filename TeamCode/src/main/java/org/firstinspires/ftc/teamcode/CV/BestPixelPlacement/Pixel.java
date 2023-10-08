package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

import org.opencv.core.KeyPoint;

public class Pixel {
    public int x;
    public int y;
    public PixelColor color;
    public int optimalScore; // Used in BestPixelPlacement to convey optimality of pixel

    // used for the original positions in the image
    public double imgX;
    public double imgY;
    public KeyPoint keyPoint;
    public Pixel(int x, int y, PixelColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public String toString() {
        return "(" + this.x + ", " + this.y + "), Color: " + this.color;
    }
}