package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;

@Config
public class ClawSubsystem extends SubsystemBase {

    public static SimpleServo claw;
    public static double clawOpenPos = 0.5;
    public static double clawClosedPos = 0.2;


    public ClawSubsystem(SimpleServo claw) {
        this.claw = claw;
    }

    public void grab() {
       claw.setPosition(clawClosedPos);
    }

    public void release() {
       claw.setPosition(clawOpenPos);
    }



}
