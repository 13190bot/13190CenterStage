package org.firstinspires.ftc.teamcode.CV;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Commands.DriveRobotCentricCommand;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "AlignTest")
@Config
public class AlignTest extends BaseOpMode {

    double forward = 0;
    double strafe = 0;
    double rotate = 0;

    PIDFController rotatePIDF;
    public static double rotate_kp = 0.1;
    public static double rotate_ki = 0;
    public static double rotate_kd = 0;
    public static double rotate_kf = 0;




    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        driveRobotCentricCommand = new DriveRobotCentricCommand(driveSubsystem, () -> {return forward;}, () -> {return strafe;}, () -> {return rotate;});

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);

        // https://docs.ftclib.org/ftclib/features/controllers
        rotatePIDF = new PIDFController(1, 0, 0, 0);

        AprilTagDetector.initAprilTag(hardwareMap);
    }

    @Override
    public void run() {
        super.run();

        AprilTagDetector.updateAprilTagDetections();

        AprilTagDetection tag = AprilTagDetector.getDetectionByID(1);

        if (!rotatePIDF.atSetPoint()) {
            double output = rotatePIDF.calculate(tag.ftcPose.bearing);
            rotate = -output;
        } else {
            rotate = 0;
        }

    }
}
