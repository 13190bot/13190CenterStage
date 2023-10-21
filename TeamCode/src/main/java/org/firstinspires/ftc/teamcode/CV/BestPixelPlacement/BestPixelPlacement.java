package org.firstinspires.ftc.teamcode.CV.BestPixelPlacement;

public class BestPixelPlacement {
    // Helper for calculate
    public static Pixel getPixel(Board board, int x, int y) {
        if (x > -1 && x < board.sizex && y > -1 && y < board.sizey) {
            return board.board[y][x];
        } else {
            return new Pixel(x, y, PixelColor.NONE);
        }
    }

    public static Pixel[] getAroundPixels(Board board, int x, int y) {
        Pixel[] aroundPixels;
        if (y % 2 == 0) {
            aroundPixels = new Pixel[]{
                    getPixel(board, x, y + 1),
                    getPixel(board, x + 1, y + 1),
                    getPixel(board, x + 1, y),
                    getPixel(board, x + 1, y - 1),
                    getPixel(board, x, y - 1),
                    getPixel(board, x - 1, y),
            };
        } else {
            aroundPixels = new Pixel[]{
                    getPixel(board, x - 1, y + 1),
                    getPixel(board, x, y + 1),
                    getPixel(board, x + 1, y),
                    getPixel(board, x, y - 1),
                    getPixel(board, x - 1, y - 1),
                    getPixel(board, x - 1, y),
            };
        }
        return aroundPixels;
    }

    public static boolean isInMosaic(Board board, int x, int y, PixelColor pixelColor) {
        Pixel[] aroundPixels = getAroundPixels(board, x, y);

        boolean isMosaic = false;
        PixelColor mosaicStreak = PixelColor.NONE;
        for (int i = 0; i < aroundPixels.length + 1; i++) {
            PixelColor color = aroundPixels[i == aroundPixels.length ? 0 : i].color;
//                        System.out.println(color);
            if (color == PixelColor.NONE || color == PixelColor.WHITE) {
                // Reset streak
                mosaicStreak = PixelColor.NONE;
            } else if (color == pixelColor) {
                // Check same color mosaic
                if (mosaicStreak == pixelColor) {
                    isMosaic = true;
                    break;
                } else {
                    mosaicStreak = pixelColor;
                }
            } else {
                // Check diff color mosaic
                if (mosaicStreak != pixelColor && mosaicStreak != PixelColor.NONE && mosaicStreak != color) {
                    isMosaic = true;
                    break;
                } else {
                    mosaicStreak = color;
                }
            }
        }

        return isMosaic;
    }

    public static boolean isPixelStable(Board board, int x, int y) {
        int cx, cy;
        // The pixel is already stable on one pixel, which is the pixel that it is just above
        // Other pixel is left (if y % 2 == 1) and right (if y % 2 == 0)
        if (y % 2 == 0) {
            // Check right bottom pixel
            cx = x + 1;
            cy = y - 1;
        } else {
            // Check left bottom pixel
            cx = x - 1;
            cy = y - 1;
        }

        // Check
        if (cx == -1 || cx == board.sizex || cy == -1 || board.board[cy][cx].color != PixelColor.NONE) {
            // Stable
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAroundMosaic(Board board, int x, int y) {
        Pixel[] aroundPixels = getAroundPixels(board, x, y);

        boolean r = false;

        for (int i = 0; i < aroundPixels.length; i++) {
            Pixel p = aroundPixels[i];
            if (isInMosaic(board, p.x, p.y, p.color)) {
                r = true;
                break;
            }
        }
        return r;
    }


    /*
     * PixelPosition {x, y}:
     * y is number of rows from bottom: bottom row is 0.
     * x is number of columns from left: left column is 0.
     *
     * board[row][column]
     */
    public static Pixel calculate(Board board, PixelColor pixelColor) {
        /*
        Row: # columns
        0: 6
        1: 7
        2: 6
         */

        /*
         * Stategy: Brute force
         * 1: Find the places where we can place
         * 2: Check if place is "stable" (2 pixels left right same y)
         * 3: Automatically find best spot
         * 3a: If white pixel: Go for height
         * 3b: If color pixel: Go for mosaic
         */

        // Data structures that will be reused over and over
        Pixel pixel = new Pixel(0, 0, pixelColor);
        int optimalScore = 1; // if pixelColor is not white, is the color optimal? or is it better for a different color? 1: optimal, 0: same as white, -1: breaks mosaic

        // 1: Find the places where we can place

        // For each row, topmost pixel of each column
        int[] columnsTopY = new int[board.sizex];
        for (int x = 0; x < board.sizex; x++) {
            if (board.board[board.sizey - 1][x].color != PixelColor.NONE) {
                // Edge case: Row is filled up all the way to the top
                columnsTopY[x] = -1;
            } else {
                if (x == board.sizex - 1) {
                    // Edge case: Pixel is leaning against the right wall
                    for (int y = board.sizey - 1; y > -1; y--) {
                        if (y == 0 || (board.board[y - 1][x].color != PixelColor.NONE)) {
                            columnsTopY[x] = y;
                            if (y % 2 == 0) {
                                columnsTopY[x]++;
                            }
                            break;
                        }
                    }
                } else {
                    for (int y = board.sizey - 1; y > -1; y--) {
                        if (y == 0 || (board.board[y - 1][x].color != PixelColor.NONE)) {
                            columnsTopY[x] = y;
                            break;
                        }
                    }
                }
            }
        }
//        System.out.println(Arrays.toString(columnsTopY));

        // Test: Mark pixels and print
//        for (int x = 0; x < columnsTopY.length; x++) {
//            if (columnsTopY[x] != -1) {
//                board.board[columnsTopY[x]][x].color = PixelColor.X_TEST;
//            }
//        }
//        System.out.println(board.toString());

        // 2: Check if place is "stable" (2 pixels left right same y)
        for (int x = 0; x < columnsTopY.length; x++) {
            int y = columnsTopY[x];
            if (y != -1) {
                if (isPixelStable(board, x, y)) {
                    // Stable
                } else {
                    columnsTopY[x] = -1;
                }
            }
        }

        // Test: Mark pixels and print
//        for (int x = 0; x < columnsTopY.length; x++) {
//            if (columnsTopY[x] != -1) {
//                board.board[columnsTopY[x]][x].color = PixelColor.X_TEST;
//            }
//        }
//        System.out.println(board.toString());

        // Now, we have all the possible places where we can place.

        // 3: Automatically find best spot
        boolean heightOverride = false;
        if (pixelColor != PixelColor.WHITE) {
            // 3b: If color pixel: Go for mosaic
            /*
            Find mosaics
            If found:
            Go to biggest height for mosaic
            else:
            Complex future-seeing algorithm
             */

            // Find mosaics (2 consecutive around pixel (same or different))
            boolean[] columnsIsMosaic = new boolean[columnsTopY.length];
            for (int x = 0; x < columnsTopY.length; x++) {
                int y = columnsTopY[x];
                if (y != -1) {
                    columnsIsMosaic[x] = isInMosaic(board, x, y, pixelColor);
                }
            }

            int cx, cy;
            cx = -1;
            cy = -1;
            for (int x = 0; x < columnsTopY.length; x++) {
                int y = columnsTopY[x];
                if (columnsIsMosaic[x]) {
                    if (y > cy) {
                        cx = x;
                        cy = y;
                    }
                }
            }
            if (cx != -1) {
                // If there are mosaics: then find highest one
                pixel.x = cx;
                pixel.y = cy;
            } else {
                // Check if place doesn't "break" a mosaic
                for (int x = 0; x < columnsTopY.length; x++) {
                    int y = columnsTopY[x];
                    if (y != -1) {
                        // If there is a mosaic next to the pixel, then it will "break" that mosaic
                        if (isAroundMosaic(board, x, y)) {
                            columnsTopY[x] = -1;
                        }
                    }
                }

                // Now, all the "unsafe" spots are gone:
                // Find "distance" (number of new pixels needed not including the current pixel
                /*
                0: Not possible, if this code runs, it should already been found
                1: One other pixel (not white and (not pixelColor or not color)
                2: No pixels nearby, but can place 2 to get mosaic
                3: Do not place here
                 */
                int[] distance = new int[columnsTopY.length];
                boolean[] isMosaicSame = new boolean[columnsTopY.length]; // if mosaic is same color mosaic
                boolean[] isMosaicDifferent = new boolean[columnsTopY.length]; // if mosaic is same color mosaic
                for (int x = 0; x < columnsTopY.length; x++) {
                    int y = columnsTopY[x];
                    if (y == -1) {
                        distance[x] = 3;
                        isMosaicSame[x] = false;
                        isMosaicDifferent[x] = false;
                    } else {
                        Pixel[] aroundPixels = getAroundPixels(board, x, y);

                        int nOfColorPixels = 0;
                        int lastColorPixeli = -1;
                        for (int i = 0; i < aroundPixels.length; i++) {
                            Pixel p = aroundPixels[i];
                            if (p.color != PixelColor.WHITE && p.color != PixelColor.NONE) {
                                nOfColorPixels++;
                                lastColorPixeli = i;
                            }
                        }
                        if (nOfColorPixels < 2) {
                            boolean[] isPixelViable = new boolean[aroundPixels.length];
                            for (int i = 0; i < aroundPixels.length; i++) {
                                Pixel p = aroundPixels[i];
                                if ((p.color == PixelColor.WHITE || p.color == PixelColor.NONE) && isAroundMosaic(board, p.x, p.y) == false) {
                                    isPixelViable[i] = true;
                                }
                            }

                            if (nOfColorPixels == 0) {
                                boolean success = false;

                                for (int i = 0; i < aroundPixels.length; i++) {
                                    int nexti = i == aroundPixels.length - 1 ? 0 : i + 1;
                                    if (isPixelViable[i] && isPixelViable[nexti]) {
                                        // Ok, so this pixel and next pixel are both viable
                                        // Check stability 1-2 and 2-1
                                        Pixel p1 = aroundPixels[i];
                                        Pixel p2 = aroundPixels[nexti];
                                        PixelColor temp;
                                        // 1-2
                                        temp = p1.color;
                                        p1.color = pixelColor;
                                        if (isPixelStable(board, p2.x, p2.y)) {
                                            // SUCCESS!
                                            p1.color = temp;
                                            success = true;
                                            break;
                                        }
                                        p1.color = temp;
                                        // 2-1
                                        temp = p2.color;
                                        p2.color = pixelColor;
                                        if (isPixelStable(board, p1.x, p1.y)) {
                                            // SUCCESS!
                                            p2.color = temp;
                                            success = true;
                                            break;
                                        }
                                        p2.color = temp;

                                    }
                                }

                                if (success) {
                                    isMosaicSame[x] = true;
                                    isMosaicDifferent[x] = true;
                                    distance[x] = 2;
                                } else {
                                    isMosaicSame[x] = false;
                                    isMosaicDifferent[x] = false;
                                    distance[x] = 3;
                                }
                            } else {
                                // nOfColorPixels == 1
                                boolean success = false;

                                int i = lastColorPixeli;
                                int nexti = i == aroundPixels.length - 1 ? 0 : i + 1;
                                if (isPixelViable[nexti]) {
                                    // Ok, so this pixel and next pixel are both viable
                                    // Check stability 1-2 and 2-1
                                    Pixel p1 = aroundPixels[i];
                                    Pixel p2 = aroundPixels[nexti];
                                    PixelColor temp;
                                    // 1-2
                                    temp = p1.color;
                                    p1.color = pixelColor;
                                    if (isPixelStable(board, p2.x, p2.y)) {
                                        // SUCCESS!
//                                        p1.color = temp;
                                        success = true;
                                    }
                                    p1.color = temp;
                                    // 2-1
                                    temp = p2.color;
                                    p2.color = pixelColor;
                                    if (isPixelStable(board, p1.x, p1.y)) {
                                        // SUCCESS!
//                                        p2.color = temp;
                                        success = true;
                                    }
                                    p2.color = temp;
                                }

//                                int i = lastColorPixeli
                                int lasti = i == aroundPixels.length - 1 ? 0 : i + 1;
                                if (isPixelViable[lasti]) {
                                    // Ok, so this pixel and next pixel are both viable
                                    // Check stability 1-2 and 2-1
                                    Pixel p1 = aroundPixels[i];
                                    Pixel p2 = aroundPixels[lasti];
                                    PixelColor temp;
                                    // 1-2
                                    temp = p1.color;
                                    p1.color = pixelColor;
                                    if (isPixelStable(board, p2.x, p2.y)) {
                                        // SUCCESS!
//                                        p1.color = temp;
                                        success = true;
                                    }
                                    p1.color = temp;
                                    // 2-1
                                    temp = p2.color;
                                    p2.color = pixelColor;
                                    if (isPixelStable(board, p1.x, p1.y)) {
                                        // SUCCESS!
//                                        p2.color = temp;
                                        success = true;
                                    }
                                    p2.color = temp;
                                }

                                if (success) {
                                    if (aroundPixels[lastColorPixeli].color == pixelColor) {
                                        isMosaicSame[x] = true;
                                        isMosaicDifferent[x] = false;
                                    } else {
                                        isMosaicSame[x] = false;
                                        isMosaicDifferent[x] = true;
                                    }
                                }
                            }


                        } else {
                            // BAD, no mosaic can be formed
                            distance[x] = 3;
                            isMosaicSame[x] = false;
                            isMosaicDifferent[x] = false;
                        }

                    }
                }

                // Sort by height and place at a spot where a mosaic will form, or can be formed
                int cx1, cy1;

                // distance=1
                cx1 = -1;
                cy1 = -1;
                for (int x1 = 0; x1 < columnsTopY.length; x1++) {
                    int y1 = columnsTopY[x1];
                    if (distance[x1] == 1 && y1 > cy1) {
                        cx1 = x1;
                        cy1 = y1;
                    }
                }

                if (cx1 == -1) {
                    // distance=2
                    for (int x1 = 0; x1 < columnsTopY.length; x1++) {
                        int y1 = columnsTopY[x1];
                        if (distance[x1] == 2 && y1 > cy1) {
                            cx1 = x1;
                            cy1 = y1;
                        }
                    }

                    if (cx1 == -1) {
                        // No good positions, give up
                        // Just go for height, pass on to white algorithm
                        heightOverride = true;
                        optimalScore = 0;
                    } else {
                        // TODO: Return mosaic options
                        pixel.x = cx1;
                        pixel.y = cy1;
                    }
                } else {
                    // TODO: Return mosaic options
                    pixel.x = cx1;
                    pixel.y = cy1;
                }
            }
        }

        if (pixelColor == PixelColor.WHITE || heightOverride) {
            // 3a: If white pixel: Go for height
            int cx, cy;
            cx = -1;
            cy = -2;
            for (int x = 0; x < columnsTopY.length; x++) {
                int y = columnsTopY[x];
//                System.out.println(x + "," + y);
                if (y > cy) {
                    cx = x;
                    cy = y;
                }
            }

            if (cy == -1) {
                // Edge case: No stable place // Error: This cannot possibly happen.
                // OR: Edge case: No "good" place // Can happen

                // Feed white pixel to algorithm, and return that but change color
                Pixel p = calculate(board, PixelColor.WHITE);
                pixel.x = p.x;
                pixel.y = p.y;
                optimalScore = -1;
            } else if (cy == -2) {
                // Edge case: No columns

                // Error: This cannot possibly happen.
            } else {
                pixel.x = cx;
                pixel.y = cy;
            }

        }

        return pixel;
    }

    // TODO: Make this into its own class?
    static PixelColor[] mosaicColors = {PixelColor.PURPLE, PixelColor.YELLOW, PixelColor.GREEN};

    public static PixelColor[] calculateViableColors(Board board) {
        PixelColor[] viableColors = new PixelColor[4];
        for (int i = 0; i < mosaicColors.length; i++) {
            PixelColor color = mosaicColors[i];
            Pixel pixel = calculate(board, color);
            if (pixel.optimalScore == 1) {
                viableColors[i] = color;
            } else {
                viableColors[i] = null;
            }
        }

        return viableColors;
    }
    public static Pixel calculate(Board board) {
        // Also calculate which color, automatically based on optimalScore

        PixelColor[] viableColors = calculateViableColors(board);

        // Choose first element, since we aren't given a color
        PixelColor color = PixelColor.WHITE;
        for (int i = 0; i < viableColors.length; i++) {
            if (viableColors[i] != null) {
                color = viableColors[i];
                break;
            }
        }

        return calculate(board, color);
    }

    public static void main(String[] args) {
        /*
        My vision: press x, y, a, b depending on color of loaded pixel

         */

//        Board board = new Board(7, 11);
        Board board = new Board();


        // set pixels
        int i = 0;
        PixelColor[] data = {
                PixelColor.YELLOW, PixelColor.YELLOW, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE
                , PixelColor.WHITE, PixelColor.YELLOW, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.PURPLE, PixelColor.WHITE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.PURPLE, PixelColor.PURPLE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.GREEN, PixelColor.GREEN, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.GREEN, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.WHITE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
                , PixelColor.WHITE, PixelColor.WHITE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
                , PixelColor.GREEN, PixelColor.YELLOW, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
                , PixelColor.PURPLE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE, PixelColor.NONE
        };
        for (int y = 0; y < board.sizey; y++) {
            for (int x = 0; x < board.sizex + ((y % 2 == 0) ? -1 : 0); x++) {
//                System.out.println(x + "," + y);
                board.board[y][x].color = data[i];
                i++;
            }
        }


        // Print board
//        System.out.println(board);


        int nTimes = 10;
        for (int i2 = 0; i2 < nTimes; i2++) {
            PixelColor color = PixelColor.GREEN;

            Pixel pixel = calculate(board, color);
            board.board[pixel.y][pixel.x].color = PixelColor.X_TEST;
            System.out.println(pixel);
            System.out.println(board);
            board.board[pixel.y][pixel.x].color = color;
        }

    }
}