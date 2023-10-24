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
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

public class BaseDriveOpMode extends CommandOpMode {

    protected DriveSubsystem driveSubsystem;
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


        fr.setInverted(true);
        fl.setInverted(true);

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        individualMotorTestCommand = new IndividualMotorTestCommand(
                driveSubsystem,
                fl,fr,bl,br,
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),
                gamepadEx1::getLeftY,
                gamepadEx1::getRightY,
                () -> gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).get(),
                () -> gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).get()

        );



        driveRobotCentricCommand = new DriveRobotCentricCommand(driveSubsystem, gamepadEx1::getLeftY, gamepadEx1::getLeftX, gamepadEx1::getRightX);
        driveRobotCentricSlowModeCommand = new DriveRobotCentricSlowModeCommand(driveSubsystem, gamepadEx1::getLeftY, gamepadEx1::getLeftX, gamepadEx1::getRightX);


        AprilTagDetector.initAprilTag(hardwareMap);
    }

    @Override
    public void run() {
        super.run();
       AprilTagDetector.updateAprilTagDetections();
    }


}