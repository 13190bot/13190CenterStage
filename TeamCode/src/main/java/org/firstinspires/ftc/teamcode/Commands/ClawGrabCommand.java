package org.firstinspires.ftc.teamcode.Commands;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;

public class ClawGrabCommand extends CommandBase {
    private ClawSubsystem clawSubsystem;

    public ClawGrabCommand(ClawSubsystem clawSubsystem) {
        this.clawSubsystem = clawSubsystem;

    }

    @Override
    public void initialize() {
        clawSubsystem.grab();
    }


}