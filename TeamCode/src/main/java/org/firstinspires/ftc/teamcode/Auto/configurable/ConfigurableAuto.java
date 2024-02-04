package org.firstinspires.ftc.teamcode.Auto.configurable;

import org.firstinspires.ftc.teamcode.Auto.BaseAuto;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class ConfigurableAuto extends BaseAuto {
    public static final Class configFile = ConfigFile
            .class;

    TrajectorySequenceBuilder traj;
    ConfigFile config;

    @Override
    public void overrideMe() {
        try {
            config = (ConfigFile) configFile.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        traj = drive.trajectorySequenceBuilder(config.startingRRPose());

        while (opModeInInit()) {
            //do camera stuff;
        }

        // build path
        //

        //execute path
        drive.followTrajectorySequence(traj.build());
    }
}
