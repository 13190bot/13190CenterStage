package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
@Config
public class PitchSubsystem extends SubsystemBase {
    private SimpleServo pitch;
    public static double pitchUp = 0;
    public static double pitchDown = 1;

    public PitchSubsystem(SimpleServo pitch) {
        this.pitch = pitch;
    }

        public void pitchUp() {
        pitch.setPosition(pitchUp);
        }

        public void pitchDown() {
        pitch.setPosition(pitchDown);
        }
}
