package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.function.DoubleSupplier;

public class AlignCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private Telemetry telemetry;
    double strafe = 0;
    double forward = 0;
    double rotate = 0;
    private PIDFController rotatePIDF;
    private PIDFController forwardPIDF;
    private PIDFController strafePIDF;

    public static double kp = 0.1;
    public static double ki = 0;
    public static double kd = 0;
    public static double kf = 0;

    public AlignCommand(DriveSubsystem driveSub, Telemetry telemetry) {
        this.driveSubsystem = driveSub;
        this.telemetry = telemetry;

        // https://docs.ftclib.org/ftclib/features/controllers
        rotatePIDF = new PIDFController(0.1, 0, 0, 0);
        forwardPIDF = new PIDFController(0.1, 0, 0, 0);
        strafePIDF = new PIDFController(0.1, 0, 0, 0);

        // Should be initted in the opmode
//        AprilTagDetector.initAprilTag(hardwareMap);

        addRequirements(driveSub);
    }

    @Override
    public void execute() {
        rotatePIDF.setPIDF(kp, ki, kd, kf);

        AprilTagDetector.updateAprilTagDetections();

        AprilTagDetection tag = AprilTagDetector.getDetectionByID(1);

        if (tag != null) {
            if (true || !rotatePIDF.atSetPoint()) {
                rotatePIDF.setPIDF(kp, ki, kd, kf);
                telemetry.addLine("Rotate PIDF");
                if (rotate == 0) {
                    rotatePIDF.reset();
                }
                double input = tag.ftcPose.bearing / 180; // Degrees: Should be >-180 and <180, so divide to match motor power
                double output = rotatePIDF.calculate(input, 0);
                rotate = -output;
                forward = 0;
                strafe = 0;
            } else {
                if (!forwardPIDF.atSetPoint()) {
                    telemetry.addLine("Forward PIDF");
                    if (forward == 0) {
                        forwardPIDF.reset();
                    }
                    double input = tag.ftcPose.y; // Inches: TODO: tune
                    double output = forwardPIDF.calculate(input, 4);
                    rotate = 0;
                    forward = output;
                    strafe = 0;
                } else {
                    if (!strafePIDF.atSetPoint()) {
                        telemetry.addLine("Strafe PIDF");
                        if (strafe == 0) {
                            strafePIDF.reset();
                        }
                        double input = tag.ftcPose.x / 10; // Inches: TODO: tune
                        double output = strafePIDF.calculate(input, 0);
                        rotate = 0;
                        forward = 0;
                        strafe = output;
                    } else {
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

        AprilTagDetector.aprilTagTelemetry(telemetry);

        rotate = 0;
        strafe = 0;
        forward = 0;
        driveSubsystem.driveRobotCentric(strafe, forward, rotate);
    }

}
