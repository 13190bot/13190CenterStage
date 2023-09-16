package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.trajectorysequence.TrajectorySequenceRunner;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {"make sure the scheduler is the OpMode's scheduler"}
)
//allows you to after a trajectory has been made to schedual somethin to run after it has ran
public class RRScheduler {
    public RRScheduler (TrajectorySequenceRunner trajRunner) {
        this.scheduler = CommandScheduler.getInstance();
        this.trajRunner = trajRunner;
        cmdQue = new ArrayList<>();
    }

    public RRScheduler (SampleMecanumDrive mechDrive) {
        this.scheduler = CommandScheduler.getInstance();
        cmdQue = new ArrayList<>();

        try {
            Field f = SampleMecanumDrive.class.getDeclaredField("trajectorySequenceRunner");
            f.setAccessible(true);
            trajRunner = (TrajectorySequenceRunner) f.get(mechDrive);
        } catch (NoSuchFieldException e) { //should never happen with a SampleMecanumDrive
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) { //should never happen as we set it to be accesable
            throw new RuntimeException(e);
        }
    }

    public void schedule (Command cmd) {
        cmdQue.add(cmd);
    }

    public void schedule (Runnable cmd) {
        cmdQue.add(new InstantCommand(cmd));
    }

    public void tick () {
        if (trajRunner.isBusy() || cmdQue.isEmpty()) ;

        scheduler.schedule(cmdQue.get(0));
        cmdQue.remove(0);
    }

    public void clearQue () {
        cmdQue.clear();
    }

    public CommandScheduler scheduler;
    TrajectorySequenceRunner trajRunner;
    List<Command> cmdQue;
}
