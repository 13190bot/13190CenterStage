package org.firstinspires.ftc.teamcode.Auto;
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

    ColorDetectionYCRCBPipeline.PropPosition position;

    public AprilTagDetector aprilTagDetector1, aprilTagDetector2;

    protected EncoderDisconnectDetect encoderDisconnectDetect;

    public void initializeHardware() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        //Timer to calculate time left in match
        beforeMatchEnd = new Timing.Timer(150, TimeUnit.SECONDS);

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

        //Servos
        if (USINGREALBOT) {
            claw = new SimpleServo(hardwareMap, "claw", 0, 180);
            arm = new SimpleServo(hardwareMap, "arm", 0, 255);
            pitch = new SimpleServo(hardwareMap, "pitch", 0, 255);
            drone = new SimpleServo(hardwareMap, "drone", 0, 255);
        }

        //Subsystems
        if (USINGREALBOT) {
            intakeSubsystem = new IntakeSubsystem(intakeMotor);
            liftSubsystem = new LiftSubsystem(liftRight, liftLeft, telemetry);
        }
        droneSubsystem = new DroneSubsystem(drone);


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