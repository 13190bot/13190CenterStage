package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;

public class ClawGrabCommand extends CommandBase {
    private ClawSubsystem claw;

    public ClawGrabCommand(ClawSubsystem claw) {
        this.claw = claw;

        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.grab();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}