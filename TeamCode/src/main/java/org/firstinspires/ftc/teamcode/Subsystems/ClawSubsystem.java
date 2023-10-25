package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ClawSubsystem extends SubsystemBase {

    private SimpleServo clawLeft,clawRight;
    private SimpleServo axle;

    public ClawSubsystem(SimpleServo clawLeft,SimpleServo clawRight, SimpleServo axle) {
        this.clawLeft = clawLeft;
        this.clawRight = clawRight;
        this.axle = axle;
    }

    public void grab() {
        clawLeft.setPosition(0.2);
        clawRight.setPosition(0.2);
        axle.setPosition(0.7);
    }

    public void release() {
        clawLeft.setPosition(0.7);
        clawRight.setPosition(0.7);
        axle.setPosition(0);
    }
}
