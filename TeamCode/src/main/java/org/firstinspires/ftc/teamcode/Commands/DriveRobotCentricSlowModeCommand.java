package org.firstinspires.ftc.teamcode.Commands;


import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveRobotCentricSlowModeCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private DoubleSupplier forward, strafe, rotate;

    public DriveRobotCentricSlowModeCommand(DriveSubsystem driveSub, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotate) {
        this.driveSubsystem = driveSub;
        this.forward = forward;
        this.strafe = strafe;
        this.rotate = rotate;

        addRequirements(driveSub);
    }

    @Override
    public void execute() {
        driveSubsystem.driveRobotCentric(strafe.getAsDouble(), forward.getAsDouble(), rotate.getAsDouble());
    }
}
