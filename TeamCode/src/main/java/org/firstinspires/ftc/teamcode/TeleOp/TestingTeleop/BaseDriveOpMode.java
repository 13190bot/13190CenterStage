package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;

public class BaseDriveOpMode extends CommandOpMode {

    protected DriveSubsystem driveSubsystem;
    protected GamepadSubsystem gamepadSubsystem;
    protected MotorEx fl, fr, bl, br;
    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;

    protected DriveRobotCentricCommand driveRobotCentricCommand;
    protected DriveRobotCentricSlowModeCommand driveRobotCentricSlowModeCommand;

    protected DriveToAprilTagCommand driveToAprilTagCommand;
    protected IndividualMotorTestCommand individualMotorTestCommand;



    @Override
    public void initialize() {

        initializeComponents();

    }


    private void initializeComponents() {

        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");

        fl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        gamepadSubsystem = new GamepadSubsystem(gamepadEx1, gamepadEx2);
        individualMotorTestCommand = new IndividualMotorTestCommand(
                driveSubsystem,
                fl,fr,bl,br,
                () -> gamepadSubsystem.gpT1(GamepadSubsystem.PLAYSTATION_TRIGGERS.L2),
                () -> gamepadSubsystem.gpT1(GamepadSubsystem.PLAYSTATION_TRIGGERS.R2),
                gamepadEx1::getLeftY,
                gamepadEx1::getRightY,
                () -> gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.L1).get(),
                () -> gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.R1).get()

        );



        driveRobotCentricCommand = new DriveRobotCentricCommand(driveSubsystem, gamepadEx1::getLeftY, gamepadEx1::getLeftX, gamepadEx1::getRightX);
        driveRobotCentricSlowModeCommand = new DriveRobotCentricSlowModeCommand(driveSubsystem, gamepadEx1::getLeftY, gamepadEx1::getLeftX, gamepadEx1::getRightX);


        //AprilTagDetector.initAprilTag(hardwareMap);
    }

    @Override
    public void run() {
        super.run();
       // AprilTagDetector.updateAprilTagDetections();
    }


}