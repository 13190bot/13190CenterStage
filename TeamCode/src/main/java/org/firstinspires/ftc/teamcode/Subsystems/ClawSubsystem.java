package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;

public class ClawSubsystem extends SubsystemBase {

    public static SimpleServo claw;
    public static CRServo axle;
    private final double clawOpenPos = 0.7;
    private final double clawClosedPos = 0.2;


    public ClawSubsystem(SimpleServo claw, CRServo axle) {
        this.claw = claw;
        this.axle = axle;
    }

    public void grab() {
        claw.setPosition(clawClosedPos);
    }

    public void release() {
       claw.setPosition(clawOpenPos);
    }



}
