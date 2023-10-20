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
    // TODO: implement
//    public static double rotate_kp = 0.1;
//    public static double rotate_ki = 0;
//    public static double rotate_kd = 0;
//    public static double rotate_kf = 0;
    private PIDFController forwardPIDF;
    private PIDFController strafePIDF;

    public AlignCommand(DriveSubsystem driveSub, Telemetry telemetry) {
        this.driveSubsystem = driveSub;
        this.telemetry = telemetry;

        // https://docs.ftclib.org/ftclib/features/controllers
        rotatePIDF = new PIDFController(0.2, 0, 0, 0);
        forwardPIDF = new PIDFController(0.2, 0, 0, 0);
        strafePIDF = new PIDFController(0.2, 0, 0, 0);

        // Should be initted in the opmode
//        AprilTagDetector.initAprilTag(hardwareMap);

        addRequirements(driveSub);
    }

    @Override
    public void execute() {
        AprilTagDetector.updateAprilTagDetections();

        AprilTagDetection tag = AprilTagDetector.getDetectionByID(1);

        if (tag != null) {
            if (!rotatePIDF.atSetPoint()) {
                telemetry.addLine("Rotate PIDF");
                if (rotate == 0) {
                    rotatePIDF.reset();
                }
                double input = tag.ftcPose.bearing / 180; // Degrees: Should be >-180 and <180, so divide to match motor power
                double output = rotatePIDF.calculate(input);
                rotate = -output;
                forward = 0;
                strafe = 0;
            } else {
                if (!forwardPIDF.atSetPoint()) {
                    telemetry.addLine("Forward PIDF");
                    if (forward == 0) {
                        forwardPIDF.reset();
                    }
                    double input = tag.ftcPose.y / 10; // Inches: TODO: tune
                    double output = forwardPIDF.calculate(input);
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
                        double output = strafePIDF.calculate(input);
                        rotate = 0;
                        forward = 0;
                        strafe = output;
                    } else {
                        // Done aligning!
                        telemetry.addLine("Done aligning!");
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

        driveSubsystem.driveRobotCentric(strafe, forward, rotate);
    }

}
