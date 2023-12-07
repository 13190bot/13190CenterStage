package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;

public class droneSubsystem extends SubsystemBase {
    SimpleServo droneServo;
    double launchPos = 1;

    public droneSubsystem(SimpleServo droneServo) {
        this.droneServo = droneServo;
    }

    public void launch() {
        droneServo.setPosition(launchPos);
    }
    public void reset() {
        droneServo.setPosition(0);
    }

}
