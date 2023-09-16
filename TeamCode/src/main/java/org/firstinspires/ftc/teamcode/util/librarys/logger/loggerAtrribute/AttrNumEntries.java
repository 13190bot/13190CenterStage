package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute;

import java.util.ArrayList;

public class AttrNumEntries extends LoggerAttribute {
    public AttrNumEntries() {
        super("Number of Entries");
    }

    @Override
    public String effect(ArrayList logs, ArrayList timestamps, ArrayList data) {
        return String.valueOf(data.size());
    }
}