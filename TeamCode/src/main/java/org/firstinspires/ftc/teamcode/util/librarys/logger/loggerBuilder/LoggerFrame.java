package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerBuilder;

import org.firstinspires.ftc.teamcode.util.librarys.logger.logger.AbstractLogger;

import java.util.ArrayList;
import java.util.function.Supplier;

public class LoggerFrame extends AbstractLogger {
    public LoggerFrame(Supplier src, String name, String file, String unit, ArrayList arrayList) {
        super(src, name, file, unit, arrayList);
    }

    @Deprecated
    @Override
    public void tick(Object value) {}

    @Override
    public void tick() {

    }
}
