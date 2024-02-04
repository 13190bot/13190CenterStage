package org.firstinspires.ftc.teamcode.Auto.configurable.configs;

import org.firstinspires.ftc.teamcode.Auto.configurable.ConfigFile;

public class ExampleConfig extends ConfigFile {
    @Override
    public int alliance() {
        return RED;
    }

    @Override
    public int startingDist() {
        return CLOSE;
    }

    @Override
    public boolean doesUsePurple() {
        return false;
    }

    @Override
    public int numCycles() {
        return 0;
    }
}
