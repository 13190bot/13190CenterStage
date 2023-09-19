package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ClawSubsystem extends SubsystemBase {

    private SimpleServo claw;
    private SimpleServo axle;

    public ClawSubsystem(SimpleServo claw, SimpleServo axle) {
        this.claw = claw;
        this.axle = axle;
    }

    public void grab() {
        claw.setPosition(0.2);
        axle.setPosition(0.7);
    }

    public void release() {
        claw.setPosition(0.7);
        axle.setPosition(0);
    }
}
