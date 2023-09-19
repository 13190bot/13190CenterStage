package org.firstinspires.ftc.teamcode.CV;

import org.opencv.core.*;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectionPipeline extends OpenCvPipeline {

    public enum PropPosition {
        NOPOS,
        LEFT,
        CENTER,
        RIGHT
    }

    //TODO: change anchor points to correct points
    private static Point LEFTPOS_TOPLEFT_ANCHOR_POINT = new Point(0, 0);

    private static Point CENTERPOS_TOPLEFT_ANCHOR_POINT = new Point(100, 100);

    private static Point RIGHTPOS_TOPLEFT_ANCHOR_POINT = new Point(200, 200);

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
    private volatile PropPosition position = PropPosition.NOPOS;

    @Override
    public Mat processFrame(Mat input) {
        // Get the submat frames, and then sum all the values for each frame
        Mat leftAreaMat = input.submat(new Rect(left_pointA, left_pointB));
        Scalar leftSumColors = Core.sumElems(leftAreaMat);

        Mat centerAreaMat = input.submat(new Rect(center_pointA, center_pointB));
        Scalar centerSumColors = Core.sumElems(leftAreaMat);

        Mat rightAreaMat = input.submat(new Rect(right_pointA, right_pointB));
        Scalar rightSumColors = Core.sumElems(leftAreaMat);

        // Get the minimum RGB value from every single channel
        double minLeftColor = Math.min(leftSumColors.val[0], Math.min(leftSumColors.val[1], leftSumColors.val[2]));
        double minCenterColor = Math.min(centerSumColors.val[0], Math.min(centerSumColors.val[1], centerSumColors.val[2]));
        double minRightColor = Math.min(rightSumColors.val[0], Math.min(rightSumColors.val[1], rightSumColors.val[2]));

        if (leftSumColors.val[2] == minLeftColor) {
            position = PropPosition.LEFT;
        }

        if (centerSumColors.val[2] == minCenterColor) {
            position = PropPosition.CENTER;
        }

        if (rightSumColors.val[2] == minRightColor) {
            position = PropPosition.RIGHT;
        }

        // Release and return input
        leftAreaMat.release();
        centerAreaMat.release();
        rightAreaMat.release();
        return input;
    }

    public PropPosition getPosition() {
        return position;
    }
}