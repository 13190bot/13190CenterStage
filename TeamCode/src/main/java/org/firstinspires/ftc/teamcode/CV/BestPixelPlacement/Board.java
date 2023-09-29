package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

public class Board {
    int sizex;
    int sizey;
    Pixel[][] board;
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
}