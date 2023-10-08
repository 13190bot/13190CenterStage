package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Board {
    public int sizex;
    public int sizey;
    public Pixel[][] board;
    public Board(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.board = new Pixel[sizey][sizex];
        for (int y = 0; y < sizey; y++) {
            for (int x = 0; x < sizex; x++) {
                this.board[y][x] = new Pixel(x, y, PixelColor.NONE);
            }
        }
    }
    public Board() {
        this(7, 11);
    }

    public String toString() {
        Board board = this;
        String str = "";
        for (int y = board.sizey - 1; y > -1; y--) {
            int rowFactor = ((y % 2 == 0) ? -1 : 0);
            if (rowFactor == -1) {
                str = str + " ";
            }
            for (int x = 0; x < board.sizex + rowFactor; x++) {
//                System.out.println(x + "," + y);
                PixelColor color = board.board[y][x].color;
                if (color == PixelColor.NONE) {
                    str = str + "  ";
                } else {
                    str = str + color.toString().substring(0, 1) + " ";
                }
            }
            str = str + "\n";
        }
        return str;
    }

    public Mat display(Mat outputImage, double bx, double by) {
        Board board = this;
        double cx;
        double cy = by;
        double xSpacing = 40;
        double ySpacing = 25;

//        double yOffset = by + (this.sizey - 0.5) * ySpacing;
//        yOffset = by;
//        cy = yOffset;
//        Imgproc.rectangle(outputImage, new Point(bx, cy), new Point(bx + (this.sizex - 0.8) * xSpacing, by + (this.sizey - 0.5) * ySpacing), new Scalar(0, 0, 0, 0.5), -1);

        // Draw background rect (AND DONT ADD OFFSET SO (bx, by) is top left
        Imgproc.rectangle(outputImage, new Point(bx, by), new Point(bx + (this.sizex - 0.8) * xSpacing, by + (this.sizey - 0.5) * ySpacing), new Scalar(0, 0, 0, 0.5), -1);

        for (int y = board.sizey - 1; y > -1; y--) {
            cx = bx;
            int rowFactor = ((y % 2 == 0) ? -1 : 0);
            if (rowFactor == -1) {
                cx = cx + (xSpacing / 2);
            }
            for (int x = 0; x < board.sizex + rowFactor; x++) {
//                System.out.println(x + "," + y);
                PixelColor color = board.board[y][x].color;
                if (color == PixelColor.NONE) {
                    // Do nothing
                } else {
//                    color.toString().substring(0, 1);
                    Imgproc.putText(outputImage, color.toString().substring(0, 1), new Point(cx, cy), Imgproc.FONT_HERSHEY_COMPLEX, 0.8, PixelColorData.getScalarFromColor(color), 2);
                }
                cx = cx + xSpacing;
            }
            cy = cy + ySpacing;
        }
        return outputImage;
    }

    public Mat displayPixel(Mat outputImage, double bx, double by, Pixel pixel) {
        Board board = this;
        double xSpacing = 40;
        double ySpacing = 25;

        // Draw background rect (AND DONT ADD OFFSET SO (bx, by) is top left
//        Imgproc.rectangle(outputImage, new Point(bx, by), new Point(bx + (this.sizex - 0.8) * xSpacing, by + (this.sizey - 0.5) * ySpacing), new Scalar(0, 0, 0, 0.5), -1);

        int y = pixel.y;
        double cy = by + (board.sizey - y - 1) * ySpacing;
        double cx = bx;
        int rowFactor = ((y % 2 == 0) ? -1 : 0);
        if (rowFactor == -1) {
            cx = cx + (xSpacing / 2);
        }
        int x = pixel.x;
        PixelColor color = pixel.color;
        cx = cx + (board.sizex - x - 1) * xSpacing;
        if (color == PixelColor.NONE) {
            // Do nothing
        } else {
            Imgproc.putText(outputImage, "X", new Point(cx, cy), Imgproc.FONT_HERSHEY_COMPLEX, 0.8, PixelColorData.getScalarFromColor(color), 2);
        }

        return outputImage;
    }
}