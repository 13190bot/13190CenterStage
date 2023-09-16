package org.firstinspires.ftc.teamcode.util.librarys.logger.logger;

import org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute.LoggerAttribute;
import org.firstinspires.ftc.teamcode.util.librarys.logger.util.Stopwatch;

import java.util.ArrayList;
import java.util.function.Supplier;

//TODO: add time-based
public abstract class AbstractLogger<T> {
    public AbstractLogger(Supplier src, String name, String file, String unit, ArrayList<LoggerAttribute> attributes) {
        currentTick = 0;
        data = new ArrayList<>();
        tickLog = new ArrayList<>();
        timeStamps = new ArrayList<>();
        stopwatch = new Stopwatch();

        this.src = src;
        this.name = name;
        this.file = file;
        this.unit = unit;
        this.attributes = attributes;
    }

    public AbstractLogger(AbstractLogger other) {
        currentTick = other.currentTick;
        data = other.data;
        tickLog = other.tickLog;
        timeStamps = other.timeStamps;
        stopwatch = other.stopwatch;

        src  = other.src ;
        name = other.name;
        file = other.file;
        unit = other.unit;
        attributes = other.attributes;
    }

    //
    @Deprecated
    public abstract void tick(T value) ;

    public abstract void tick() ;

    public void log () {
        timeStamps.add(stopwatch.nano()); //TODO: nanoTime or milliseconds?
        tickLog.add(currentTick);
        data.add(src.get());
    }

    public void log (T value) {
        timeStamps.add(stopwatch.nano()); //TODO: nanoTime or milliseconds?
        tickLog.add(currentTick);
        data.add(value);
    }

    public String name() {
        return name;
    }

    public String file() {
        return file;
    }

    public void activate () {
        isActive = true;
        //
    }

    public void deactivate () {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public ArrayList<LoggerAttribute> attributes() {
        return attributes;
    }

    public ArrayList<Long> tickLog() {
        return tickLog;
    }

    public ArrayList<Long> timeStamps() {
        return timeStamps;
    }

    public ArrayList<T> data() {
        return data;
    }

    public String unit() {
        return (unit == null || unit.isEmpty()) ? "no unit supplied" : unit;
    }

    Supplier<T>     src;
    boolean         isActive;
    Stopwatch       stopwatch;
    long            currentTick;
    String          unit;
    String          name;
    String          file;
    ArrayList<T>    data;
    ArrayList<Long> tickLog;
    ArrayList<Long> timeStamps;
    ArrayList<LoggerAttribute> attributes;
}