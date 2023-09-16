package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;

public class AttrAvgTickPerLog extends LoggerAttribute {
    public AttrAvgTickPerLog() {
        super("Average Tick Per Log");
    }

    @Override
    public String effect(ArrayList logs, ArrayList timestamps, ArrayList data) {
        double sum = 0;
        for (Object val : logs) {
            sum += ((Number) val).doubleValue();
        }
        return String.valueOf(sum / logs.size());
    }
}
