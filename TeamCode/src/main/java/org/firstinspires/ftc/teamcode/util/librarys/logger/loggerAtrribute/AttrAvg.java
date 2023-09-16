package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;

public class AttrAvg extends LoggerAttribute{
    public AttrAvg () {
        super("Average");
    }

    @Override
    public String effect(ArrayList logs, ArrayList timestamps, ArrayList data) {
        double sum = 0;
        for (Object val : data) {
            if (val instanceof Number) {
                sum += ((Number) val).doubleValue();
            } else { return "NaN"; }
        }
        return String.valueOf(sum / data.size());
    }
}
