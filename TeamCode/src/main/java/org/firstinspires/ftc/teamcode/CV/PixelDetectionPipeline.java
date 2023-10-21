package org.firstinspires.ftc.teamcode.CV;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CV.BestPixelPlacement.*;
import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;
import org.opencv.features2d.SimpleBlobDetector;
import org.opencv.features2d.SimpleBlobDetector_Params;
//import org.opencv.features2d.;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Arrays;
import java.util.Comparator;

public class PixelDetectionPipeline extends OpenCvPipeline {
    //testing
    public int blur = 0;
    public boolean DEBUG = true;
    class SortKeyPointsByX implements Comparator<KeyPoint> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(KeyPoint a, KeyPoint b)
        {
            return (int) (a.pt.x - b.pt.x);
        }
    }
    class SortKeyPointsByY implements Comparator<KeyPoint> {
        // SHITS REVERSED SINCE IMAGE COORDS ARE REVERSED ((0, 0) is top left)
        // Used for sorting in *descending* order of
        // roll number
        public int compare(KeyPoint a, KeyPoint b)
        {
            return (int) -(a.pt.y - b.pt.y);
        }
    }

    // color data: matches rgb to pixelcolor
    // colors (scalars) are taken from https://www.andymark.com/products/ftc-23-24-am-5101 ideal
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


    /*
    READ
    https://github.com/OpenFTC/EasyOpenCV/blob/master/doc/user_docs/pipelines_overview.md
    https://docs.opencv.org/4.x/
    https://docs.opencv.org/4.x/d0/d7a/classcv_1_1SimpleBlobDetector.html
     */

    Board board;

    SimpleBlobDetector blobDetector;

    MatOfKeyPoint findBlobsOutput = new MatOfKeyPoint();

//    https://deltacv.gitbook.io/eocv-sim/features/telemetry
    Telemetry telemetry;
//    HardwareMap hardwareMap;

//    public PixelDetectionPipeline(Telemetry telemetry, HardwareMap hardwareMap) {
//        this.telemetry = telemetry;
//        this.hardwareMap = hardwareMap;
//    }

    public PixelDetectionPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void initPipeline() {
        blobDetector = SimpleBlobDetector.create();

        SimpleBlobDetector_Params params = new SimpleBlobDetector_Params();
        params.set_filterByArea(true);
//        params.set_minArea(25);
        // some "holes" in between pixels might also be detected
        // real solution: see if some pixels are drastically lower (average/4 cutoff)
//        params.set_minArea(0);

        // temp solution: (NEVER USE)
//        params.set_minArea(50);

        params.set_filterByCircularity(true);
        params.set_minCircularity((float) 0.5);
        params.set_maxCircularity(1);

        params.set_filterByColor(true);



        blobDetector.setParams(params);


        // aprilTag
//        AprilTagDetector.initAprilTag(hardwareMap);
    }


    @Override
    public void init(Mat mat) {
        super.init(mat);
        initPipeline();
    }

    Mat outputImage;

    @Override
    public Mat processFrame(Mat input) {
        //testing
        if (true) {
            if (blur > 0 && blur % 2 == 1) {
                Imgproc.GaussianBlur(input, input, new Size(blur, blur), 0);
            } else if (blur > 0) {
                Imgproc.GaussianBlur(input, input, new Size(blur + 1, blur + 1), 0);
            }
        }


        board = new Board();

//        AprilTagDetector.updateAprilTagDetections();

        blobDetector.detect(input, findBlobsOutput);

        // draw
        outputImage = input.clone();


        KeyPoint[] keyPoints = findBlobsOutput.toArray();

        // Average size and filter out "noise" (non-pixel blobs)
        double sizeSum = 0;
        for (int i = 0; i < keyPoints.length; i++) {
            KeyPoint keyPoint = keyPoints[i];
            sizeSum = sizeSum + keyPoint.size;
        }
        double sizeAverage = sizeSum / keyPoints.length;
        double sizeCutoff = sizeAverage / 2; // you can tune
        int nRemoved = 0;
        for (int i = 0; i < keyPoints.length; i++) {
            KeyPoint keyPoint = keyPoints[i];
            if (keyPoint.size < sizeCutoff) {
                keyPoints[i] = null;
                nRemoved++;
            }
        }
        KeyPoint[] newKeyPoints = new KeyPoint[keyPoints.length - nRemoved];
        nRemoved = 0;
        for (int i = 0; i < keyPoints.length; i++) {
            if (keyPoints[i] != null) {
                newKeyPoints[i - nRemoved] = keyPoints[i];
            } else {
                nRemoved++;
            }
        }
        keyPoints = newKeyPoints;


        // this is some horrendous code
        // Sort by x coordinate (to get min and max)
        Arrays.sort(keyPoints, new SortKeyPointsByX());
        double xMin = keyPoints[0].pt.x;
        double xMax = keyPoints[keyPoints.length - 1].pt.x;

        // Sort by y coordinate
        Arrays.sort(keyPoints, new SortKeyPointsByY());
        // Remember: for y axis, up is less, down is more
        double yMax = keyPoints[0].pt.y;
        double yMin = keyPoints[keyPoints.length - 1].pt.y;



        // Colors DONE: integrate into detection/slope
//        for (int i = 0; i < keyPoints.length; i++) {
//            if (keyPoints[i] != null) {
//                KeyPoint keyPoint = keyPoints[i];
//
//
//
//
//
//
//
//
//
//                // telemetry and visuals
//                telemetry.addData(i + "", keyPoint.toString());
//                // https://www.tutorialspoint.com/opencv/opencv_drawing_circle.htm
//                Imgproc.circle(outputImage, keyPoint.pt, (int) (keyPoint.size / 2), new Scalar(255, 0, 0), 10);
//
//                int colorx = (int) (keyPoint.pt.x + 3 * keyPoint.size / 4);
//                int colory = (int) (keyPoint.pt.y);
//                double[] d = input.get(colorx, colory);
//                Scalar p = new Scalar(d);
//                Imgproc.circle(outputImage, new Point(colorx, colory), 10, new Scalar(0, 0, 255), 10);
//
//                telemetry.addData("color", p.toString());
//            }
//        }


        // Detect xSpacing from apriltag
        // TODO: Calculate spacing from apriltag
        // Temporary fix: use size of keypoint
        double xSpacing = 0;


        // Use slope between pixels (between min and max?)
        double slopeMin = -0.1;
        double slopeMax = 0.1;
        boolean[] keyPointsProcessed = new boolean[keyPoints.length];
        int nOfPointsProcessed = 0;
        int y = 0;
        KeyPoint lastReferenceKeyPoint = null;
        while (true) {
            // reference point
            KeyPoint referenceKeyPoint = keyPoints[0];
            for (int i = 0; i < keyPoints.length; i++) {
                if (keyPointsProcessed[i] == false && keyPoints[i] != null) {
                    referenceKeyPoint = keyPoints[i];
                    break;
                }
            }

            KeyPoint[] keyPointsRow = new KeyPoint[keyPoints.length];
            int i2 = 0;
            for (int i = 0; i < keyPoints.length; i++) {
                if (keyPointsProcessed[i] == false) {
                    if (keyPoints[i] != null) {
                        KeyPoint keyPoint = keyPoints[i];
                        double slope = (keyPoint.pt.y - referenceKeyPoint.pt.y) / (keyPoint.pt.x - referenceKeyPoint.pt.x);
                        if ((slopeMin <= slope && slope <= slopeMax) || keyPoint == referenceKeyPoint) {
                            keyPointsRow[i2] = keyPoint;
                            i2++;

                            keyPointsProcessed[i] = true;
                            nOfPointsProcessed++;
                        }
                    } else {
                        keyPointsProcessed[i] = true;
                        nOfPointsProcessed++;
                    }
                }
            }

            KeyPoint[] keyPointsRow2 = new KeyPoint[i2];
            for (int i = 0; i < i2; i++) {
                keyPointsRow2[i] = keyPointsRow[i];
            }

            Arrays.sort(keyPointsRow2, new SortKeyPointsByX());
            referenceKeyPoint = keyPointsRow2[0];


            // x may have an offset: find the offset
            // TODO: replace with apriltag
            int xOffset = 0;
            if (lastReferenceKeyPoint != null) {
                // Temp solution: use lastreferenceKeyPoint.x
                if (y % 2 == 0) {
                    // normal (0.5x to the right)
                    xOffset = (int) Math.round((referenceKeyPoint.pt.x - lastReferenceKeyPoint.pt.x) / xSpacing - 0.5);
                } else {
                    // 0.5x to the left
                    xOffset = (int) Math.round((referenceKeyPoint.pt.x - lastReferenceKeyPoint.pt.x) / xSpacing + 0.5);
                }
            } else {
                // cope
                xOffset = 0;
            }
//            telemetry.addData("xOffset", xOffset);

            for (int i = 0; i < keyPointsRow2.length; i++) {
                KeyPoint keyPoint = keyPointsRow2[i];

                // TODO: replace with apriltag
                // Temp solution: use size of keypoints
                xSpacing = keyPoints[0].size * 2.2;

                telemetry.addData("rawX", (keyPoint.pt.x - referenceKeyPoint.pt.x) / xSpacing);
                int x = (int) Math.round((keyPoint.pt.x - referenceKeyPoint.pt.x) / xSpacing) + xOffset;

                // WE'VE FINALLY OBTAINED X AND Y!!!!!!!!

                // now color!
//                telemetry.addData(i + "", keyPoint.toString());
                // https://www.tutorialspoint.com/opencv/opencv_drawing_circle.htm
//                Imgproc.circle(outputImage, keyPoint.pt, (int) (keyPoint.size / 2), new Scalar(255, 0, 0), 10);

                int colorx = (int) (keyPoint.pt.x + 3 * keyPoint.size / 4);
                int colory = (int) (keyPoint.pt.y);
                double[] d = input.get(colory, colorx); // WATCH OUT, (y, x)
                Scalar p = new Scalar(d);
//                Imgproc.circle(outputImage, new Point(colorx, colory), 10, new Scalar(0, 0, 255), 10);

                double bestScore = -1; // higher the score the better
                PixelColor bestColor = null;
                Scalar bestRGB = null;
                for (int i3 = 0; i3 < colorData.length; i3++) {
                    double score = 255 * 3 - (Math.abs(((Scalar) colorData[i3][0]).val[0] - p.val[0]) + Math.abs(((Scalar) colorData[i3][0]).val[1] - p.val[1]) + Math.abs(((Scalar) colorData[i3][0]).val[2] - p.val[2]));
                    if (score > bestScore) {
                        bestScore = score;
                        bestColor = (PixelColor) colorData[i3][1];
                        bestRGB = (Scalar) colorData[i3][0];
                    }
                }


                // FINALLY, ADD TO BOARD
                Pixel pixel = board.board[y][x];
                pixel.color = bestColor;
                pixel.keyPoint = keyPoint;
                pixel.imgX = keyPoint.pt.x;
                pixel.imgY = keyPoint.pt.y;


                // telemetry
                if (DEBUG) {
                    Imgproc.circle(outputImage, keyPoint.pt, (int) (keyPoint.size / 2), new Scalar(255, 0, 0), 10);
                    Imgproc.circle(outputImage, new Point(colorx, colory), 10, new Scalar(0, 0, 255), 10);
                    Imgproc.putText(outputImage, "x" + x + " y" + y, new Point(keyPoint.pt.x - keyPoint.size, keyPoint.pt.y + keyPoint.size / 2), Imgproc.FONT_HERSHEY_COMPLEX, 0.8, new Scalar(0, 255, 0), 2);
                    Imgproc.putText(outputImage, "c" + bestColor, new Point(keyPoint.pt.x - keyPoint.size, keyPoint.pt.y + keyPoint.size), Imgproc.FONT_HERSHEY_COMPLEX, 0.8, new Scalar(0, 255, 0), 2);
                }

            }


            if (nOfPointsProcessed == keyPoints.length) {
                break;
            }
            lastReferenceKeyPoint = referenceKeyPoint;
            y++;
        }



        // draw board

        // BOARD DISPLAY
        // top-left
//        board.display(outputImage, 0, 0);

        // top-right
//        board.display(outputImage, 710, 0);

        double bx = 710;
        double by = 0;
        board.display(outputImage, bx, by);

//        telemetry.addLine("Board:\n" + board.toString());


        // BEST PIXEL DISPLAY (DEBUG, REMOVE IN PROD)
        Pixel bestPixel = BestPixelPlacement.calculate(board);

        Pixel[] aroundPixels = BestPixelPlacement.getAroundPixels(board, bestPixel.x, bestPixel.y);
        // aroundPixels[3, 4] are bottom 2 pixels
        Pixel bottomRight = aroundPixels[3];
        Pixel bottomLeft = aroundPixels[4];
        double distance = Math.sqrt((bottomRight.imgX - bottomLeft.imgX) * (bottomRight.imgX - bottomLeft.imgX) + (bottomRight.imgY - bottomLeft.imgY) *(bottomRight.imgY - bottomLeft.imgY));
//        double pixelRadius = distance / 2;
        double dx = (bottomRight.imgX - bottomLeft.imgX) / distance;
        double dy = (bottomRight.imgY - bottomLeft.imgY) / distance;
        double distanceToCenter = distance * Math.sqrt(3) / 2;
        bestPixel.imgX = ((bottomRight.imgX + bottomLeft.imgX) / 2) + distanceToCenter * dy;
        bestPixel.imgY = ((bottomRight.imgY + bottomLeft.imgY) / 2) - distanceToCenter * dx;
        Point pixelCenter = new Point(bestPixel.imgX, bestPixel.imgY);

        // telemetry
        board.displayPixel(outputImage, bx, by, bestPixel);
        Imgproc.circle(outputImage, pixelCenter, (int) (bottomRight.keyPoint.size / 2), PixelColorData.getScalarFromColor(bestPixel.color), 10);
        telemetry.addLine("bestPixel: (" + bestPixel.toString());

        // OUTPUT
        telemetry.addLine("To human player: Place " + bestPixel.color);
//        Imgproc.fillPoly(outputImage, );



//        AprilTagDetector.aprilTagTelemetry(telemetry);
         telemetry.update(); // aprilTagTelemetry has telemetry.update()


//        // https://stackoverflow.com/a/28282965
//        Mat outputImage = new Mat();
//        // Your image, keypoints, and output image
//        Features2d.drawKeypoints(input, findBlobsOutput, outputImage);

        return outputImage;
    }

    public Board getBoard() {
        return board;
    }
}