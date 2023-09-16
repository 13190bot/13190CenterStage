package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerManager;

import org.firstinspires.ftc.teamcode.util.librarys.logger.logger.AbstractLogger;
import org.firstinspires.ftc.teamcode.util.librarys.logger.loggerOut.LoggerFileOut;
import org.firstinspires.ftc.teamcode.util.librarys.logger.util.DefaultFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LoggerManager {
    public LoggerManager () {
        loggers = new HashMap<>();
        louts   = new HashMap<>();

        new File(DefaultFile.DEFAULT_DIR).mkdir(); //if the default directory does not exist, create it and the default file
    }

    public void tick () {
        for(AbstractLogger logger : loggers.values()) {
            if(logger.isActive()) {
                logger.tick();
            }
        }
    }

    public void activateAll () {
        for(AbstractLogger logger : loggers.values()) {
            logger.activate();
        }
    }

    public void deactivateAll () {
        for(AbstractLogger logger : loggers.values()) {
            logger.deactivate();
        }
    }

    public void activateLogger (String loggerName) {
        loggers.get(loggerName).activate();
    }

    public void deactivateLogger (String loggerName) {
        loggers.get(loggerName).deactivate();
    }

    public AbstractLogger add (AbstractLogger logger) {
        String file = logger.file();
        if (file == null) {
            file = DefaultFile.DEFAULT_FILE;
        }

        System.out.println("file = " + file);

        if (louts.containsKey(file)) {
            louts.get(file).addLogger(logger);
        } else {
            LoggerFileOut lout = new LoggerFileOut(file);
            System.out.println("lout, file, logger is null? " + (lout == null) + ", " + (file == null) + ", " + (logger == null));
            lout.addLogger(logger);
            louts.put(file, lout);
        }

        return loggers.put(logger.name(), logger);
    }

    public void storeLogs () throws IOException {
        System.out.printf("hi\n");
        for(LoggerFileOut lout : louts.values()) {
            lout.writeToFile();
        }
    }

    HashMap<String, AbstractLogger> loggers;
    HashMap<String, LoggerFileOut> louts;
}
