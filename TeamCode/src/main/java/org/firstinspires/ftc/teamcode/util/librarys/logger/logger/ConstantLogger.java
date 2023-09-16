package org.firstinspires.ftc.teamcode.util.librarys.logger.logger;

import org.firstinspires.ftc.teamcode.util.librarys.logger.loggerAtrribute.LoggerAttribute;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ConstantLogger extends AbstractLogger{

    public ConstantLogger(Supplier src, String name, String file, String unit, ArrayList<LoggerAttribute> attributes) {
        super(src, name, file, unit, attributes);
    }

    public ConstantLogger (AbstractLogger other) {
        super(other);
    }

    @Deprecated
    @Override
    public void tick(Object value) {
        log(value);
        ++currentTick;
    }

    @Override
    public void tick() {
        log(src.get());
        ++currentTick;
    }
}
