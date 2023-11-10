package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

/*
TODO:
Implement photon https://github.com/8872/8872PP/pull/3/files
 */

@Config
@TeleOp(name = "MainTeleOp")
public class MainOpMode extends BaseOpMode {
    public static double test = 0.5;

    @Override
    public void initialize() {
        super.initialize();



//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).toggleWhenPressed(driveRobotCentricSlowModeCommand);

        gb2(PlaystationAliases.CROSS).whileHeld(startIntakeCommand);
        gb2(PlaystationAliases.TRIANGLE).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);
        // open and down
        gb2(PlaystationAliases.CROSS).whenPressed(() -> {
            clawSubsystem.release();
            sleep(20);

        });
        // close and up


        //TEST
        gb2(PlaystationAliases.SQUARE).whenPressed(() -> {
            armSubsystem.movePitch(test);

        });
//        gb2(PlaystationAliases)

//        gb1(GamepadKeys.Button.A).toggleWhenPressed(armSubsystem.moveArm(ArmSubsystem.armPosHome), armSubsystem.moveArm(ArmSubsystem.armPosAway));
//        gb1(GamepadKeys.Button.B).toggleWhenPressed(armSubsystem.movePitch(ArmSubsystem.pitchPosHome), armSubsystem.movePitch(ArmSubsystem.pitchPosAway));
       // clawSubsystem.setDefaultCommand(axleMoveCommand);
        //armSubsystem.setDefaultCommand(armMoveCommand);
        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);
        liftSubsystem.setDefaultCommand(manualLiftCommand);
        register(driveSubsystem,intakeSubsystem);
    }

    public void run()
    {
        super.run();
    }
}