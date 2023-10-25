package org.firstinspires.ftc.teamcode.CV;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ColorDetectionYCBCRPipeline extends OpenCvPipeline {

    private enum PropPosition {
        NOPOS,
        LEFT,
        CENTER,
        RIGHT
    }

    private final Mat ycbcrMat = new Mat();
//    private final Mat binaryMat = new Mat();

    //TODO: change anchor points to correct points
    private final Point LEFTPOS_TOPLEFT_ANCHOR_POINT = new Point(100, 300);

    private final Point CENTERPOS_TOPLEFT_ANCHOR_POINT = new Point(500, 300);

    private final Point RIGHTPOS_TOPLEFT_ANCHOR_POINT = new Point(1000, 300);

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

    private final Scalar lowerB;
    private final Scalar upperB;

    public ColorDetectionYCBCRPipeline(Scalar lowerB, Scalar upperB) {
        this.lowerB = lowerB;
        this.upperB = upperB;
    }


    @Override
    public Mat processFrame(Mat input) {

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

//        Scalar lowerB = new Scalar(0, 255, 255);
//        Scalar upperB = new Scalar(0, 255, 125);

        Imgproc.cvtColor(input, ycbcrMat, Imgproc.COLOR_RGB2YCrCb);

        Mat leftRegion = ycbcrMat.submat(new Rect(left_pointA, left_pointB));
        Mat centerRegion = ycbcrMat.submat(new Rect(center_pointA, center_pointB));
        Mat rightRegion = ycbcrMat.submat(new Rect(right_pointA, right_pointB));

        int leftAvg = (int) Core.mean(leftRegion).val[2];
        int centerAvg = (int) Core.mean(centerRegion).val[2];
        int rightAvg = (int) Core.mean(rightRegion).val[2];

        if (leftAvg < lowerB.val[2] && centerAvg < lowerB.val[2] && rightAvg < lowerB.val[2]) {
            position = PropDetectionPipeline.PropPosition.NOPOS;
        }


        if (leftAvg > lowerB.val[2] && leftAvg < upperB.val[2]) {
            position = PropDetectionPipeline.PropPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    left_pointA,
                    left_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        if (centerAvg > lowerB.val[2] && centerAvg < upperB.val[2]) {
            position = PropDetectionPipeline.PropPosition.CENTER;
            Imgproc.rectangle(
                    input,
                    center_pointA,
                    center_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        if (rightAvg > lowerB.val[2] && rightAvg < upperB.val[2]) {
            position = PropDetectionPipeline.PropPosition.RIGHT;
            Imgproc.rectangle(
                    input,
                    right_pointA,
                    right_pointB,
                    new Scalar(255, 0, 0),
                    1
            );
        }

        return input;

    }

    public PropDetectionPipeline.PropPosition getPosition() {
        return position;
    }
}
