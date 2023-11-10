package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

@TeleOp(name = "TestingServos")
@Config
public class TestingServos extends BaseOpMode {
    public static double test = 1;
    @Override
    public void initialize() {
        super.initialize();
        register(clawSubsystem);

        gamepadEx2.getGamepadButton(PlaystationAliases.TRIANGLE).toggleWhenPressed(clawGrabCommand, clawReleaseCommand);


    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("Claw Position", clawSubsystem.claw.getPosition());
        telemetry.addData("Claw Angle", clawSubsystem.claw.getAngle());
        telemetry.addLine("_________________________");
        telemetry.addLine("_________________________");
        telemetry.addData("Scheduled command on Claw Subsystem",clawSubsystem.getCurrentCommand());
        telemetry.addData("Claw Grab Command", clawGrabCommand.isScheduled());
        telemetry.addData("Claw Release Command", clawReleaseCommand.isScheduled());

        telemetry.update();


    }

}
