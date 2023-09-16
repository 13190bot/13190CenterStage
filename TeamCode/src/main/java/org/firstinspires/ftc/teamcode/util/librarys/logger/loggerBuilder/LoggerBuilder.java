package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerBuilder;

import org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute.*;
import org.firstinspires.ftc.teamcode.util.librarys.logger.logger.AbstractLogger;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.util.librarys.logger.util.DefaultFile.DEFAULT_FILE;

public class LoggerBuilder {
    public LoggerBuilder(Supplier src, String name, String file, String unit) {
        this.src = src;
        this.name = name;
        this.unit = unit;
        this.file = (file == null || file.equals("")) ? DEFAULT_FILE : file;
        attributes = new ArrayList<>();
    }

    public LoggerBuilder average () {
        attributes.add(new AttrAvg());
        return this;
    }
    public LoggerBuilder mean () {
        return average();
    }

    public LoggerBuilder averageTickPerLog () {
        attributes.add(new AttrAvgTickPerLog());
        return this;
    }

    public LoggerBuilder averageTimePerLog () {
        attributes.add(new AttrAvgTimePerLog());
        return this;
    }

    public LoggerBuilder median () {
        attributes.add(new AttrMedian());
        return this;
    }

    public LoggerBuilder mode () {
        attributes.add(new AttrMedian());
        return this;
    }

    //TODO: populate
    public LoggerBuilder everything () {
        average();
        median();
        mode();

        //

        return this;
    }

    //TODO: define
    public LoggerBuilder allApllicable () {
        return this;
    }

    public AbstractLogger build () {
        return new LoggerFrame(src, name, file, unit, attributes);
    }

    Supplier src;
    String unit;
    String name;
    String file;
    ArrayList<LoggerAttribute> attributes;
}