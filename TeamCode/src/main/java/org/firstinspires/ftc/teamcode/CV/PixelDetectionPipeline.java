package org.firstinspires.ftc.teamcode.CV;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CV.BestPixelPlacement.Pixel;
import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;
import org.opencv.features2d.SimpleBlobDetector;
import org.opencv.features2d.SimpleBlobDetector_Params;
//import org.opencv.features2d.;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.teamcode.CV.BestPixelPlacement.Board;

public class PixelDetectionPipeline extends OpenCvPipeline {

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
    public PixelDetectionPipeline(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void initPipeline() {
        blobDetector = SimpleBlobDetector.create();

        SimpleBlobDetector_Params params = new SimpleBlobDetector_Params();
        params.set_filterByArea(true);
        params.set_minArea(25);

        params.set_filterByCircularity(true);
        params.set_minCircularity((float) 0.5);
        params.set_maxCircularity(1);

        params.set_filterByColor(true);



        blobDetector.setParams(params);
    }


    @Override
    public void init(Mat mat) {
        super.init(mat);
        initPipeline();
    }

    @Override
    public Mat processFrame(Mat input) {
        blobDetector.detect(input, findBlobsOutput);

        // draw
        Mat outputImage = input.clone();


        KeyPoint[] keyPoints = findBlobsOutput.toArray();
        telemetry.addData("DATA", "");
        for (int i = 0; i < keyPoints.length; i++) {
            KeyPoint keyPoint = keyPoints[i];
            telemetry.addData(i + "", keyPoint.toString());
            // https://www.tutorialspoint.com/opencv/opencv_drawing_circle.htm
            Imgproc.circle(outputImage, keyPoint.pt, (int) keyPoint.size / 2, new Scalar(255, 0, 0), 10);
        }
        telemetry.update();


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