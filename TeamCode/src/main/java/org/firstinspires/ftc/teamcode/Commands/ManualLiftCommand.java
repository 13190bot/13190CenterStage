package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.LiftSubsystem;

import java.util.function.DoubleSupplier;

public class ManualLiftCommand extends CommandBase {
    private LiftSubsystem liftSubsystem;
    private DoubleSupplier power;

    public ManualLiftCommand(LiftSubsystem liftSubsystem, DoubleSupplier power) {
        this.liftSubsystem = liftSubsystem;
        this.power = power;
        addRequirements(liftSubsystem);
    }

    @Override
    public void execute() {
        if (power.getAsDouble() != 0) {
            liftSubsystem.lift(power.getAsDouble());
        } else {
            liftSubsystem.stabilize();
        }
        liftSubsystem.PIDtelem();

    }



}
