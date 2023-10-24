package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;

public class BaseOpMode extends CommandOpMode {

    protected DriveSubsystem driveSubsystem;
    protected IntakeSubsystem intake;
    protected ClawSubsystem claw;
    protected LiftSubsystem lift;
    private MotorEx fl, fr, bl, br, intakeMotor, liftLeft, liftRight;

    private SimpleServo clawServo;
    private SimpleServo axleServo;
    private MotorEx[] motors = {fl, fr, bl, br};
    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;

    protected DriveRobotOptimalCommand driveRobotOptimalCommand;

    protected StartIntakeCommand startIntakeCommand;
    protected ClawGrabCommand clawGrabCommand;
    protected ClawReleaseCommand clawReleaseCommand;
    protected ManualLiftCommand manualLiftCommand;


    @Override
    public void initialize() {

        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");
        //liftLeft = new MotorEx(hardwareMap, "liftLeft");
        //liftRight = new MotorEx(hardwareMap, "liftRight");

       // clawServo = new SimpleServo(hardwareMap, "claw", 0, 180);
        //axleServo = new SimpleServo(hardwareMap, "axle", 0, 180);

       intakeMotor = new MotorEx(hardwareMap, "intakeMotor");
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
       intake = new IntakeSubsystem(intakeMotor);
       // lift = new LiftSubsystem(liftRight, liftLeft);

        fl.setInverted(true);
        fr.setInverted(true);


        driveRobotOptimalCommand = new DriveRobotOptimalCommand(driveSubsystem, gamepadEx1);


       // manualLiftCommand = new ManualLiftCommand(lift, gamepadEx2::getLeftY);

     startIntakeCommand = new StartIntakeCommand(intake);
        AprilTagDetector.initAprilTag(hardwareMap);

    }

    @Override
    public void run() {
        super.run();
    }
}