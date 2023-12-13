package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;

@TeleOp(name = "MainOpMode_TestingChassis")
public class MainOpMode_TestingChassis extends CommandOpMode {

    protected DriveSubsystem driveSubsystem;
    protected IntakeSubsystem intakeSubsystem;
//    protected ClawSubsystem clawSubsystem;
    protected LiftSubsystem liftSubsystem;
    private MotorEx fl, fr, bl, br, intakeMotor, liftLeft, liftRight;

    private SimpleServo clawServo;
    private SimpleServo axleServo;
    private MotorEx[] motors = {fl, fr, bl, br};
    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;

    protected DriveRobotOptimalCommand2 driveRobotOptimalCommand;

//    protected StartIntakeCommand startIntakeCommand;
//    protected ClawGrabCommand clawGrabCommand;
//    protected ClawReleaseCommand clawReleaseCommand;
//    protected ManualLiftCommand manualLiftCommand;
//    protected AxleMoveCommand axleMoveCommand;


    @Override
    public void initialize() {

        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");

        fl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


//        fl.setInverted(true);
//        bl.setInverted(true);
//        fr.setInverted(true);
//        br.setInverted(true);

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        driveRobotOptimalCommand = new DriveRobotOptimalCommand2(driveSubsystem, gamepadEx1);
        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);

    }

    @Override
    public void run() {
        super.run();
    }
}