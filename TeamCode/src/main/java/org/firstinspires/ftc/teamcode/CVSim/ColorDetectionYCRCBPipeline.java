package org.firstinspires.ftc.teamcode.CVSim;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ColorDetectionYCRCBPipeline extends OpenCvPipeline {

    private enum PropPosition {
        NOPOS,
        LEFT,
        CENTER,
        RIGHT
    }

    private final Mat ycrcbMat = new Mat();
    private final Mat binaryMat = new Mat();

    //TODO: change anchor points to correct points
    private final Point LEFTPOS_TOPLEFT_ANCHOR_POINT = new Point(35, 90);

    private final Point CENTERPOS_TOPLEFT_ANCHOR_POINT = new Point(135, 90);

    private final Point RIGHTPOS_TOPLEFT_ANCHOR_POINT = new Point(235, 90);

    // Width and height for the bounding boxes
    public static int REGION_WIDTH = 10;
    public static int REGION_HEIGHT = 10;

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

//    private final int colorInd;

//    public ColorDetectionYCRCBPipeline(int colorInd) {
//        this.colorInd = colorInd;
//    }


    @Override
    public Mat processFrame(Mat input) {

        Scalar lowerB = new Scalar(158, 158, 158);
        Scalar upperB = new Scalar(255, 255, 255);
//
        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(ycrcbMat, ycrcbMat, 1);

        Imgproc.morphologyEx(ycrcbMat, ycrcbMat, Imgproc.MORPH_CLOSE, new Mat());
        Imgproc.morphologyEx(ycrcbMat, ycrcbMat, Imgproc.MORPH_OPEN, new Mat());
//
        Core.inRange(ycrcbMat, lowerB, upperB, binaryMat);

        Imgproc.rectangle(
                input,
                left_pointA,
                left_pointB,
                new Scalar(255, 255, 255),
                1
        );

        Imgproc.rectangle(
                input,
                center_pointA,
                center_pointB,
                new Scalar(255, 255, 255),
                1
        );

        Imgproc.rectangle(
                input,
                right_pointA,
                right_pointB,
                new Scalar(255, 255, 255),
                1
        );

        Mat leftRegion = binaryMat.submat(new Rect(left_pointA, left_pointB));
        Mat centerRegion = binaryMat.submat(new Rect(center_pointA, center_pointB));
        Mat rightRegion = binaryMat.submat(new Rect(right_pointA, right_pointB));
//
//
        int leftAvg = (int) Core.mean(leftRegion).val[0];
        int centerAvg = (int) Core.mean(centerRegion).val[0];
        int rightAvg = (int) Core.mean(rightRegion).val[0];

        int max = Math.max(leftAvg, Math.max(centerAvg, rightAvg));
//
//
        if (max == leftAvg) {
            position = PropPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    left_pointA,
                    left_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        if (max == centerAvg) {
            position = PropPosition.CENTER;
            Imgproc.rectangle(
                    input,
                    center_pointA,
                    center_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        if (max == rightAvg) {
            position = PropPosition.RIGHT;
            Imgproc.rectangle(
                    input,
                    right_pointA,
                    right_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        return binaryMat;

    }

    public PropPosition getPosition() {
        return position;
    }

}
