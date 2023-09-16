package org.firstinspires.ftc.teamcode.util.librarys.logger.logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class TimeLogger extends AbstractLogger{
    public TimeLogger(Supplier src, String name, String file, String unit, ArrayList arrayList, long pauseMillis) {
        super(src, name, file, unit, arrayList);
        logger = new Timer();
        this.pauseMillis = pauseMillis;
    }

    public TimeLogger (AbstractLogger other) {
        super(other);
        logger = new Timer();
        pauseMillis = 1000;
    }

    @Override
    public void tick(Object value) {}

    @Override
    public void tick() {}

    @Override
    public void activate () {
        super.activate();
        logger.scheduleAtFixedRate(
            new TimerTask() {
                @Override
                public void run() {
                    log();
                }
            },
            new Date(),
            pauseMillis
        );
    }

    @Override
    public void deactivate() {
        super.deactivate();
        logger.cancel();
    }

    public long getPauseMillis() {
        return pauseMillis;
    }

    public void setPauseMillis(long pauseMillis) {
        this.pauseMillis = pauseMillis;
    }

    long pauseMillis;
    Timer logger;
}
