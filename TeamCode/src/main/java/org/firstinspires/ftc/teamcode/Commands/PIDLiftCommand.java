package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;

import java.util.function.DoubleSupplier;

public class PIDLiftCommand extends CommandBase {
    private LiftSubsystem liftSubsystem;
    private DoubleSupplier power;

    public PIDLiftCommand(LiftSubsystem liftSubsystem, DoubleSupplier power) {
        this.liftSubsystem = liftSubsystem;
        this.power = power;
        addRequirements(liftSubsystem);
    }

    @Override
    public void execute() {
        if (power.getAsDouble() != 0) {
            liftSubsystem.lift(power.getAsDouble());
        } else {
            liftSubsystem.moveLiftToTarget();
        }
        liftSubsystem.liftTelemetry();

    }



}
