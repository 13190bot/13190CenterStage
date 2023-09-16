package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;

public abstract class LoggerAttribute {
    public LoggerAttribute (String name) {
        this.name = name;
    }
    String name;
    public abstract String effect (ArrayList logs, ArrayList timestamps, ArrayList data) ;

    public String name() {
        return name;
    }
}