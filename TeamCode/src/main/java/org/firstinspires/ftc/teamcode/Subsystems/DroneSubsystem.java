package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
@Config
public class DroneSubsystem extends SubsystemBase {

    SimpleServo droneServo;
    public static double launchPos = 0;

    public DroneSubsystem(SimpleServo droneServo) {
        this.droneServo = droneServo;
    }

    public void launch() {
        droneServo.setPosition(launchPos);
    }
    public void reset() {
        droneServo.setPosition(1);
    }

    public InstantCommand launchCommand() {
        return new InstantCommand() {
            @Override
            public void initialize() {
                launch();
            }
        };
    }

}
