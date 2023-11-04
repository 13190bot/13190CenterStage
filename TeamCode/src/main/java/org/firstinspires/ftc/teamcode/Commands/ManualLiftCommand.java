package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;

import java.util.function.DoubleSupplier;

public class ManualLiftCommand extends CommandBase {
    private LiftSubsystem lift;
    private DoubleSupplier power;

    public ManualLiftCommand(LiftSubsystem lift, DoubleSupplier power) {
        this.lift = lift;
        this.power = power;
    }

    @Override
    public void execute() {
        lift.manualLift(power.getAsDouble());
    }
}
