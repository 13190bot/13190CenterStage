package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;

public class ClawReleaseCommand extends CommandBase {
    private ClawSubsystem claw;

    public ClawReleaseCommand(ClawSubsystem claw) {
        this.claw = claw;

        addRequirements(claw);
    }

    @Override
    public void initialize() {
        claw.release();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
