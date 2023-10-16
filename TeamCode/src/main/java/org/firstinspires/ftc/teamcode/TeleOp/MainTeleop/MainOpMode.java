package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.GamepadSubsystem;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;

@TeleOp(name = "MainTeleop")
public class MainOpMode extends BaseOpMode {


    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem);

        gamepadSubsystem.gb1(GamepadSubsystem.PLAYSTATION_BUTTONS.CIRCLE).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        gamepadSubsystem.gb2(GamepadSubsystem.PLAYSTATION_BUTTONS.SQUARE).toggleWhenPressed(startIntakeCommand);
        gamepadSubsystem.gb2(GamepadSubsystem.PLAYSTATION_BUTTONS.CROSS).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);

    }

    public void run()
    {
        super.run();
    }
}