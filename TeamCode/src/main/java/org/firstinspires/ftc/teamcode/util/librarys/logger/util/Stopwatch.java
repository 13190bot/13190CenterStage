package org.firstinspires.ftc.teamcode.util.librarys.logger.util;

public class Stopwatch {
    public Stopwatch () {
        startTimeNano = System.nanoTime();
        startTimeMilli = System.currentTimeMillis();
    }

    public void reset () {
        startTimeNano = System.nanoTime();
        startTimeMilli = System.currentTimeMillis();
    }

    public long nano () {
        return System.nanoTime() - startTimeNano;
    }

    public long milli () {
        return System.currentTimeMillis() - startTimeMilli;
    }

    long startTimeNano;
    long startTimeMilli;
}
