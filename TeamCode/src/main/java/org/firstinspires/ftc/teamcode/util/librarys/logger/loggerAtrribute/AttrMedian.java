package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;
import java.util.Collections;

public class AttrMedian extends LoggerAttribute{

    public AttrMedian() {
        super("Median");
    }

    @Override
    public String effect(ArrayList logs, ArrayList timestamps, ArrayList data) {
        ArrayList<Comparable> sorted = new ArrayList<>(data); //to ensure the actual copy of data is not sorted
        if( !(sorted.get(0) instanceof Number) ) {
            return "NaN";
        }

        Collections.sort(sorted);
        return String.valueOf(data.get(data.size()/2));
    }
}