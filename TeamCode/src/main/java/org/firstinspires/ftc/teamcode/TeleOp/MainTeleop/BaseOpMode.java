package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.*;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.firstinspires.ftc.teamcode.util.CommandOpModeEx;

import java.util.concurrent.TimeUnit;

public class BaseOpMode extends CommandOpModeEx {
    protected DroneSubsystem droneSubsystem;

    protected DriveSubsystem driveSubsystem;
    protected IntakeSubsystem intakeSubsystem;
    protected LiftSubsystem liftSubsystem;
    public MotorEx fl, fr, bl, br, intakeMotor, liftLeft, liftRight;
    public SimpleServo arm,pitch,claw,drone;
    private MotorEx[] motors = {fl, fr, bl, br};
    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;
    protected DriveRobotOptimalCommand driveRobotOptimalCommand;

//    protected PIDLiftCommand PIDLiftCommand;
//    protected ManualLiftCommand manualLiftCommand;
    protected Command grabAndUpCommand, releaseAndDownCommand;
    protected Timing.Timer beforeMatchEnd;


    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry);

        //Timer to calculate time left in match
        beforeMatchEnd = new Timing.Timer(150, TimeUnit.SECONDS);

        //Motors
        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");

        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");


        liftLeft = new MotorEx(hardwareMap, "liftLeft");
        liftRight = new MotorEx(hardwareMap, "liftRight");


        //Prevent Drift
        fl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //ONLY USE IF NOT USING LIFT PID
        liftLeft.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setInverted(true);


        //Zero the lift encoders

        liftRight.resetEncoder();
        liftLeft.resetEncoder();



        //Reverse Motors
//        fl.setInverted(true);
//        fr.setInverted(true);
//        br.setInverted(true);

        bl.setInverted(true);
        fl.setInverted(true);
        br.setInverted(true);
        fr.setInverted(false);

       // pitch.setPosition(0.5);

        //Servos
        claw = new SimpleServo(hardwareMap, "claw", 0, 180);
        arm = new SimpleServo(hardwareMap, "arm", 0, 255);
        arm.setInverted(true);
        pitch = new SimpleServo(hardwareMap, "pitch", 0, 255);
        drone = new SimpleServo(hardwareMap, "drone", 0, 255);


        //Gamepads
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        //Subsystems
        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        intakeSubsystem = new IntakeSubsystem(intakeMotor);
        liftSubsystem = new LiftSubsystem(liftRight, liftLeft,telemetry);
        droneSubsystem = new DroneSubsystem(drone);



        //Commands
        driveRobotOptimalCommand = new DriveRobotOptimalCommand(driveSubsystem, gamepadEx1);
//        PIDLiftCommand = new PIDLiftCommand(liftSubsystem, gamepadEx2::getLeftY);
//        manualLiftCommand = new ManualLiftCommand(liftSubsystem, gamepadEx2);


        driveSubsystem.speedMultiplier = 1;

        //Setup up April Tag Detector
        AprilTagDetector.initAprilTag(hardwareMap);

    }

    @Override
    public void run() {
        super.run();
    }

    protected GamepadButton gb1(GamepadKeys.Button button){
        return gamepadEx1.getGamepadButton(button);
    }

    protected GamepadButton gb2(GamepadKeys.Button button){
        return gamepadEx2.getGamepadButton(button);
    }

}