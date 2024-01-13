package org.firstinspires.ftc.teamcode.Auto.RR;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.firstinspires.ftc.teamcode.util.librarys.WireMannager.EncoderDisconnectDetect;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.concurrent.TimeUnit;


public abstract class BaseAuto extends LinearOpMode {
    public static boolean USINGREALBOT = true; // true on real bot, false on test chassis

    protected SampleMecanumDrive drive;

    protected OpenCvCamera camera;

    protected DroneSubsystem droneSubsystem;

    protected DriveSubsystem driveSubsystem;
    protected IntakeSubsystem intakeSubsystem;
    protected LiftSubsystem liftSubsystem;
    public MotorEx fl, fr, bl, br, intakeMotor, liftLeft, liftRight;
    public SimpleServo arm,pitch,claw,drone;
    private MotorEx[] motors = {fl, fr, bl, br};
    protected Timing.Timer beforeMatchEnd;

    public AprilTagDetector aprilTagDetector1, aprilTagDetector2;

    protected EncoderDisconnectDetect encoderDisconnectDetect;

    public void initializeHardware() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        //Timer to calculate time left in match
        beforeMatchEnd = new Timing.Timer(150, TimeUnit.SECONDS);

        //Motors
        if (USINGREALBOT) {
            fl = new MotorEx(hardwareMap, "frontLeft");
            fr = new MotorEx(hardwareMap, "frontRight");
            bl = new MotorEx(hardwareMap, "backLeft");
            br = new MotorEx(hardwareMap, "backRight");

            bl.setInverted(true);
            fl.setInverted(false);
            br.setInverted(true);
            fr.setInverted(false);
        } else {
            fl = new MotorEx(hardwareMap, "frontRight");
            fr = new MotorEx(hardwareMap, "frontLeft");
            bl = new MotorEx(hardwareMap, "backRight");
            br = new MotorEx(hardwareMap, "backLeft");

            bl.setInverted(false);
            fl.setInverted(false);
            br.setInverted(false);
            fr.setInverted(false);
        }

        if (USINGREALBOT) {
            intakeMotor = new MotorEx(hardwareMap, "intakeMotor");
            liftLeft = new MotorEx(hardwareMap, "liftLeft");
            liftRight = new MotorEx(hardwareMap, "liftRight");

            //ONLY USE IF NOT USING LIFT PID
            liftLeft.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftRight.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftRight.setInverted(true);

            //Zero the lift encoders
            liftRight.resetEncoder();
            liftLeft.resetEncoder();
        }


        //Prevent Drift
        fl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // ODOMETRY ENCODERS
        bl.stopAndResetEncoder();
        fl.stopAndResetEncoder();
//        bl.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fl.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Servos
        if (USINGREALBOT) {
            claw = new SimpleServo(hardwareMap, "claw", 0, 180);
            arm = new SimpleServo(hardwareMap, "arm", 0, 255);
            pitch = new SimpleServo(hardwareMap, "pitch", 0, 255);
            drone = new SimpleServo(hardwareMap, "drone", 0, 255);
        }

        //Subsystems
        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        if (USINGREALBOT) {
            intakeSubsystem = new IntakeSubsystem(intakeMotor);
            liftSubsystem = new LiftSubsystem(liftRight, liftLeft, telemetry);
        }
        droneSubsystem = new DroneSubsystem(drone);



        if (USINGREALBOT) {
            encoderDisconnectDetect = new EncoderDisconnectDetect(liftLeft);
        }

        driveSubsystem.speedMultiplier = 1;

        //Setup up April Tag Detector
        if (USINGREALBOT) {
            aprilTagDetector1 = new AprilTagDetector("Webcam 1", hardwareMap);
        }
//        aprilTagDetector2 = new AprilTagDetector("Webcam 2", hardwareMap);

        drive = new SampleMecanumDrive(hardwareMap);

    }

    public abstract void overrideMe () ;

    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware();

        overrideMe();
    }

    public static final int TILE_SIZE = 24;
}