package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TestFollowAprilTagOpMode")
public class TestFollowAprilTagOpMode extends BaseOpMode{
    @Override
    public void initialize() {
        super.initialize();
        register(driveSubsystem);

        driveSubsystem.setDefaultCommand(driveToAprilTagCommand);
    }
    public void run()
    {
        super.run();
    }

    }

