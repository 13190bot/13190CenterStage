package org.firstinspires.ftc.teamcode.TeleOp;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.Commands.StartIntakeCommand;

public class MainOpMode extends BaseOpMode {


    @Override
    public void initialize() {
        super.initialize();

        register(driveSubsystem, intake, claw);

        gb1(GamepadKeys.Button.LEFT_BUMPER).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        gb2(GamepadKeys.Button.A).toggleWhenPressed(startIntakeCommand);
        gb2(GamepadKeys.Button.LEFT_BUMPER).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);

        driveSubsystem.setDefaultCommand(driveRobotCentricCommand);

    }

    public void run()
    {
        super.run();
    }
}