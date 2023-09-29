package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

enum PixelColor {
    X_TEST,
    WHITE,
    PURPLE,
    YELLOW,
    GREEN,
    NONE // air / no pixel
}

public class Pixel {
    int x;
    int y;
    PixelColor color;
    public Pixel(int x, int y, PixelColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public String toString() {
        return "(" + this.x + ", " + this.y + "), Color: " + this.color;
    }
}