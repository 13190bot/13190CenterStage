package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;
import java.util.function.DoubleSupplier;

@Config
public class AlignCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private Telemetry telemetry;
    double strafe = 0;
    double forward = 0;
    double rotate = 0;
    private PIDFController rotatePIDF;
    private PIDFController forwardPIDF;
    private PIDFController strafePIDF;

    public static double kp = 0.07;
    public static double ki = 0;
    public static double kd = 0.0001;
    public static double kf = 0;

    AprilTagDetector aprilTagDetector;

    public AlignCommand(DriveSubsystem driveSub, Telemetry telemetry, AprilTagDetector aprilTagDetector) {
        this.driveSubsystem = driveSub;
        this.telemetry = telemetry;
        this.aprilTagDetector = aprilTagDetector;

        // https://docs.ftclib.org/ftclib/features/controllers
        rotatePIDF = new PIDFController(0.07, 0, 0.0001, 0);
        forwardPIDF = new PIDFController(0.05, 0, 0.0001, 0);
        strafePIDF = new PIDFController(0.1, 0, 0, 0);
        rotatePIDF.setTolerance(0);
        forwardPIDF.setTolerance(0);
        strafePIDF.setTolerance(0);

        // Should be initted in the opmode
//        AprilTagDetector.initAprilTag(hardwareMap);

        addRequirements(driveSub);
    }

    @Override
    public void execute() {
//        rotatePIDF.setPIDF(kp, ki, kd, kf);
//        forwardPIDF.setPIDF(kp, ki, kd, kf);
//        strafePIDF.setPIDF(kp, ki, kd, kf);

        aprilTagDetector.updateAprilTagDetections();

        List allTags = aprilTagDetector.getAllCurrentDetections();





        telemetry.addData("numTags", allTags.size());


        if (allTags.size() > 0) {
            AprilTagDetection tag = (AprilTagDetection) allTags.get(0);
            telemetry.addData("tag id: ", tag.id);

            if (!rotatePIDF.atSetPoint()) {
                // DON'T USE BEARING, USE YAW
                if (rotate == 0) {
                    rotatePIDF.reset();
                }

//                double bearing = 0;
//                for (int i = 0; i < allTags.size(); i++) {
//                    bearing += ((AprilTagDetection) allTags.get(i)).ftcPose.bearing;
//
//                    telemetry.addData("bearing " + i, ((AprilTagDetection) allTags.get(i)).ftcPose.bearing);
//                }
//                bearing = bearing / allTags.size();

//                AprilTagDetection rTag = (AprilTagDetection) allTags.get(0);

//                double bearing = rTag.ftcPose.bearing;
                // pitch on the real bot since the camera is rotated 90 degrees
                double yaw = tag.ftcPose.pitch; // yaw normally

                yaw = yaw;

//                rotatePIDF.setPIDF(kp, ki, kd, kf);
                telemetry.addLine("Rotate PIDF");
                telemetry.addData("yaw", yaw);
                double input = yaw; // Degrees: Should be >-180 and <180, so divide to match motor power
                double output = rotatePIDF.calculate(input, 0); // Target value is 2nd argument
                rotate = output;
                forward = 0;
                strafe = 0;
            } else {

//            if (rotate < 0.5 && rotate > -0.5) {
                if (!forwardPIDF.atSetPoint()) {
                    if (forward == 0) {
                        forwardPIDF.reset();
                    }

                    telemetry.addLine("Forward PIDF");
                    double input = tag.ftcPose.y; // Inches: TODO: tune
                    double output = forwardPIDF.calculate(input, 10);
//                    rotate = 0;
                    forward = output;
                    strafe = 0;
                }

                if (true) {
                    if (false && !strafePIDF.atSetPoint()) {
                        if (strafe == 0) {
                            strafePIDF.reset();
                        }

                        telemetry.addLine("Strafe PIDF");
                        double input = tag.ftcPose.x; // Inches: TODO: tune
                        double output = strafePIDF.calculate(input, 0);
//                    rotate = 0;
//                    forward = 0;
                        strafe = output;
                    }

                    if (true) {
                        // Done aligning!
                        telemetry.addLine("Done aligning!");
                    rotate = 0;
                    forward = 0;
                    strafe = 0;
                    }
                }

            }
        } else {
            telemetry.addLine("No PIDF (apriltag not detected)");
            rotate = 0;
            forward = 0;
            strafe = 0;
        }

        telemetry.addData("rotate", rotate);
        telemetry.addData("forward", forward);
        telemetry.addData("strafe", strafe);

        aprilTagDetector.aprilTagTelemetry(telemetry);

//        rotate = 0;
//        strafe = 0;
//        forward = 0;
        driveSubsystem.driveRobotCentric(strafe, forward, rotate);
    }

}
