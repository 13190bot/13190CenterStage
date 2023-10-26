package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;

public class ClawSubsystem extends SubsystemBase {

    public static SimpleServo claw;
    public static SimpleServo axle;
    private final double clawOpenPos = 0.7;
    private final double clawClosedPos = 0.2;


    public ClawSubsystem(SimpleServo claw, SimpleServo axle) {
        this.claw = claw;
        this.axle = axle;
    }

    public void grab() {
        claw.setPosition(clawClosedPos);
        axle.setPosition(0.7);
    }

    public void release() {
       claw.setPosition(clawOpenPos);
    }



}
