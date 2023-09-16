package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;

public class AttrAvgTimePerLog extends LoggerAttribute {
    public AttrAvgTimePerLog() {
        super("Average Time Per Log");
    }

    @Override
    public String effect(ArrayList logs, ArrayList timestamps, ArrayList data) {
        double sum = 0;
        for (Object val : timestamps) {
            sum += ((Number) val).doubleValue();
        }
        return String.valueOf(sum / timestamps.size());
    }
}