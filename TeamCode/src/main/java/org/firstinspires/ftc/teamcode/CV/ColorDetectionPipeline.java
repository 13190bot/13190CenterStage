package org.firstinspires.ftc.teamcode.CV;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ColorDetectionPipeline extends OpenCvPipeline {

    public enum PropPosition {
        NOPOS,
        LEFT,
        CENTER,
        RIGHT
    }

    private Mat hsvMat = new Mat();
    private Mat binaryMat = new Mat();

    //TODO: change anchor points to correct points
    private static Point LEFTPOS_TOPLEFT_ANCHOR_POINT = new Point(100, 300);

    private static Point CENTERPOS_TOPLEFT_ANCHOR_POINT = new Point(500, 300);

    private static Point RIGHTPOS_TOPLEFT_ANCHOR_POINT = new Point(1000, 300);

    // Width and height for the bounding boxes
    public static int REGION_WIDTH = 30;
    public static int REGION_HEIGHT = 50;

    // Anchor point definitions
    Point left_pointA = new Point(
            LEFTPOS_TOPLEFT_ANCHOR_POINT.x,
            LEFTPOS_TOPLEFT_ANCHOR_POINT.y
    );
    Point left_pointB = new Point(
            LEFTPOS_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            LEFTPOS_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT
    );

    Point center_pointA = new Point(
            CENTERPOS_TOPLEFT_ANCHOR_POINT.x,
            CENTERPOS_TOPLEFT_ANCHOR_POINT.y
    );
    Point center_pointB = new Point(
            CENTERPOS_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            CENTERPOS_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT
    );

    Point right_pointA = new Point(
            RIGHTPOS_TOPLEFT_ANCHOR_POINT.x,
            RIGHTPOS_TOPLEFT_ANCHOR_POINT.y
    );
    Point right_pointB = new Point(
            RIGHTPOS_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            RIGHTPOS_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT
    );

    // Running variable storing the parking position
    private volatile PropDetectionPipeline.PropPosition position = PropDetectionPipeline.PropPosition.NOPOS;

    private Scalar lowerB;
    private Scalar upperB;

    public ColorDetectionPipeline(Scalar lowerB, Scalar upperB) {
        this.lowerB = lowerB;
        this.upperB = upperB;
    }


    @Override
    public Mat processFrame(Mat input) {

//        Scalar lowerB = new Scalar(0, 255, 255);
//        Scalar upperB = new Scalar(0, 255, 125);

        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(input, lowerB, upperB, binaryMat);

        double left = 0, right = 0, center = 0;

    // process the pixel value for each rectangle  (255 = W, 0 = B)
        for (int i = (int) left_pointA.x; i <= left_pointB.x; i++) {
            for (int j = (int) left_pointA.y; j <= left_pointB.y; j++) {
                if (binaryMat.get(i, j)[0] == 255) {
                    left++;
                }
            }
        }

        for (int i = (int) center_pointA.x; i <= center_pointB.x; i++) {
            for (int j = (int) center_pointA.y; j <= center_pointB.y; j++) {
                if (binaryMat.get(i, j)[0] == 255) {
                    center++;
                }
            }
        }

        for (int i = (int) right_pointA.x; i <= right_pointB.x; i++) {
            for (int j = (int) right_pointA.y; j <= right_pointB.y; j++) {
                if (binaryMat.get(i, j)[0] == 255) {
                    right++;
                }
            }
        }

        double max = Math.max(left, Math.max(center, right));

        if (max == left) {
            position = PropDetectionPipeline.PropPosition.LEFT;
        }
        if (max == center) {
            position = PropDetectionPipeline.PropPosition.CENTER;
        }
        if (max == right) {
            position = PropDetectionPipeline.PropPosition.RIGHT;
        }

        binaryMat.release();
        return binaryMat;

    }

    public PropDetectionPipeline.PropPosition getPosition() {
        return position;
    }
}
