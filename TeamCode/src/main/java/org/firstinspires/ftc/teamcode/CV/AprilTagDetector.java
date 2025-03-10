package org.firstinspires.ftc.teamcode.CV;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import javax.annotation.Nullable;
import java.util.List;



public class AprilTagDetector {

//    private String webcamName = "Webcam 1";
    public String webcamName;
    public AprilTagProcessor aprilTagProcessor;
    public VisionPortal visionPortal;

    public List<AprilTagDetection> allCurrentDetections;


    public AprilTagDetector(String webcamName, HardwareMap hardwareMap) {
        this.webcamName = webcamName;
        aprilTagProcessor = new AprilTagProcessor.Builder()
                //Set Calibration Here

                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, webcamName))
                .addProcessor(aprilTagProcessor)
                .build();
    }


    public List<AprilTagDetection> getAllCurrentDetections(){
        return allCurrentDetections;
    }

    public void updateAprilTagDetections(){
        allCurrentDetections = aprilTagProcessor.getDetections();

    }

    public int getNumberOfDetections(){
        return allCurrentDetections.size();
    }

    public boolean isAprilTagDetected(){
        return allCurrentDetections.size() > 0;
    }

    public AprilTagDetection getDetectionByID(int id){
        for (AprilTagDetection detection : allCurrentDetections){
            if (id == detection.id){
                return detection;
            }
        }
        return null;
    }

    public void aprilTagTelemetry(Telemetry telemetry){ // Stolen Code from samples but it works
        if (!(allCurrentDetections == null)) {
            telemetry.addData("# AprilTags Detected", allCurrentDetections.size());

            // Step through the list of detections and display info for each one.
            for (AprilTagDetection detection : allCurrentDetections) {
                if (detection.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                }
            }   // end for() loop

            // Add "key" information to telemetry
            telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
            telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
            telemetry.addLine("RBE = Range, Bearing & Elevation");
            telemetry.update();
        }
    }




}
