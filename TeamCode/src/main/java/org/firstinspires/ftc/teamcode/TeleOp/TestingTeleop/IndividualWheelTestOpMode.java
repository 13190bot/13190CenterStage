package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Commands.DriveToAprilTagCommand;
import org.firstinspires.ftc.teamcode.Commands.IndividualMotorTestCommand;
@TeleOp(name = "Wheel Testing Op Mode")
public class IndividualWheelTestOpMode extends BaseDriveOpMode{
    @Override
    public void initialize() {
        super.initialize();
        register(driveSubsystem);

        driveSubsystem.setDefaultCommand(individualMotorTestCommand);

    }
    @Override
    public void run() {
        super.run();
        telemetry.addData("Front Left Power",fl.motor.getPower());
        telemetry.addData("Front Right Power",fr.motor.getPower());
        telemetry.addData("Back Left Power",bl.motor.getPower());
        telemetry.addData("Back Right Power",br.motor.getPower());
        telemetry.update();
    }
}
