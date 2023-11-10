package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

/*
TODO:
Implement photon https://github.com/8872/8872PP/pull/3/files
 */

@Config
@TeleOp(name = "MainTeleOp")
public class MainOpMode extends BaseOpMode {
    //public static double test = 0;
    public static boolean a = false;

    @Override
    public void initialize() {
        super.initialize();

        gb2(PlaystationAliases.CROSS).whileHeld(startIntakeCommand);

        gamepadEx2.getGamepadButton(PlaystationAliases.SQUARE).toggleWhenPressed(grabAndUpCommand, releaseAndDownCommand);


        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);
        liftSubsystem.setDefaultCommand(manualLiftCommand);
        register(driveSubsystem, intakeSubsystem, armSubsystem);
    }

    public void run()
    {
        telemetry.addData("Arm Position", armSubsystem.arm.getPosition());
        telemetry.addData("Claw Position", ClawSubsystem.claw.getPosition());
        super.run();
        telemetry.update();
    }
}