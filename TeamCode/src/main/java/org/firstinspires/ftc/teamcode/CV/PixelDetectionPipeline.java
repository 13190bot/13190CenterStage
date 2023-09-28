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

        // Average size and filter out "noise" (non-pixel blobs)
        double sizeSum = 0;
        for (int i = 0; i < keyPoints.length; i++) {
            KeyPoint keyPoint = keyPoints[i];
            sizeSum = sizeSum + keyPoint.size;
        }
        double sizeAverage = sizeSum / keyPoints.length;
        double sizeCutoff = sizeAverage / 2; // you can tune
        for (int i = 0; i < keyPoints.length; i++) {
            KeyPoint keyPoint = keyPoints[i];
            if (keyPoint.size < sizeCutoff) {
                keyPoints[i] = null;
            }
        }

        // Now actual data gathering
        for (int i = 0; i < keyPoints.length; i++) {
            if (keyPoints[i] != null) {
                KeyPoint keyPoint = keyPoints[i];
                telemetry.addData(i + "", keyPoint.toString());
                // https://www.tutorialspoint.com/opencv/opencv_drawing_circle.htm
                Imgproc.circle(outputImage, keyPoint.pt, (int) (keyPoint.size / 2), new Scalar(255, 0, 0), 10);

                int colorx = (int) (keyPoint.pt.x + 3 * keyPoint.size / 4);
                int colory = (int) (keyPoint.pt.y);
                double[] d = input.get(colorx, colory);
                Scalar p = new Scalar(d);
                Imgproc.circle(outputImage, new Point(colorx, colory), 10, new Scalar(0, 0, 255), 10);

                telemetry.addData("color", p.toString());
            }
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