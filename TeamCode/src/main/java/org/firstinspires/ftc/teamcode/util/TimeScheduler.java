package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {"make sure the scheduler is the OpMode's scheduler"}
)
public class TimeScheduler {
    public static final long END_OF_AUTO   = TimeUnit.SECONDS.toMillis(30); //Auto is 30 sec long
    public static final long END_GAME      = TimeUnit.SECONDS.toMillis((2*60)); //timer resets after auto, till end game is 2 min long
    public static final long END_OF_TELEOP = TimeUnit.SECONDS.toMillis((2*60) + 30); //end game +30 sec


    public TimeScheduler () {
        this.scheduler = CommandScheduler.getInstance();
        tScheduler = new Timer(true); //should i set the isDaemon?
    }

    /**
     * schedual a comm
     * @param delay
     * @param delayUnit
     * @param cmd
     */
    public void scheduleFromNowRaw (long delay, TimeUnit delayUnit, Runnable cmd) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        cmd.run();
                    }
                },
                delayUnit.toMillis(delay)
        );
    }

    /**
     *
     * @param delay
     * @param delayUnit
     * @param command
     */
    public void scheduleFromNow (long delay, TimeUnit delayUnit, Command command) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        scheduler.schedule(command);
                    }
                },
                delayUnit.toMillis(delay)
        );
    }

    /**
     *
     * @param delay
     * @param delayUnit
     * @param command
     */
    public void scheduleFromNow (long delay, TimeUnit delayUnit, Runnable command) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        scheduler.schedule(new InstantCommand(command));
                    }
                },
                delayUnit.toMillis(delay)
        );
    }

    /**
     *
     * @param point
     * @param delay
     * @param delayUnit
     * @param cmd
     */
    public void scheduleFromPoint (long point, long delay, TimeUnit delayUnit, Command cmd) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        scheduler.schedule(cmd);
                    }
                },
                point + delayUnit.toMillis(delay)
        );
    }

    /**
     *
     * @param point
     * @param delay
     * @param delayUnit
     * @param cmd
     */
    public void scheduleFromPoint (long point, long delay, TimeUnit delayUnit, Runnable cmd) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        scheduler.schedule(new InstantCommand(cmd));
                    }
                },
                point + delayUnit.toMillis(delay)
        );
    }

    /**
     *
     * @param point
     * @param delay
     * @param delayUnit
     * @param cmd
     */
    public void scheduleFromPointRaw (long point, long delay, TimeUnit delayUnit, Runnable cmd) {
        tScheduler.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        cmd.run();
                    }
                },
                point + delayUnit.toMillis(delay)
        );
    }

    Timer tScheduler; //TODO: is this a copy or the instance
    CommandScheduler scheduler;
}