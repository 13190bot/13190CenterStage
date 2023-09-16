package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerOut;

import org.firstinspires.ftc.teamcode.util.librarys.logger.logger.AbstractLogger;
import org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute.LoggerAttribute;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LoggerFileOut {
    public LoggerFileOut (String filepath) {
        file = filepath;
        loggers = new ArrayList<>();
    }

    String file;
    ArrayList<AbstractLogger> loggers;

    public String formatLoggerOutput (AbstractLogger logger) {
        String out = "------------------------------------------------------------------------------------------------------------------------\n"
                   + logger.name() + "(" + logger.unit() + ")" + "\n\n";


        ArrayList<LoggerAttribute> attrs = logger.attributes();
        ArrayList<Long> ticks  = logger.tickLog();
        ArrayList<Long> times  = logger.timeStamps();
        ArrayList<Object> data = logger.data();
        for(LoggerAttribute attr : attrs) {
            out += attr.name() + ": " + attr.effect(ticks, times, data) + "\n";
        }

        out += "tick; time; value\n";
        for (int i = 0; i < data.size(); ++i) {
            out += String.valueOf(ticks.get(i)) + "; " + String.valueOf(times.get(i)) + "; " + data.get(i) + "\n";
        }

        return out + "\n------------------------------------------------------------------------------------------------------------------------\n";
    }

    public String formatOutput () {
        String out = "";

        for(AbstractLogger logger: loggers) {
            out += "\n\n" + formatLoggerOutput(logger);
        }

        return out;
    }

    public void writeToFile () throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(formatOutput());
        writer.flush();
    }

    public void addLogger (AbstractLogger logger) {
        loggers.add(logger);
    }
}