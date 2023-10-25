package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ClawSubsystem extends SubsystemBase {

    private SimpleServo clawLeft,clawRight;
    private SimpleServo axle;
    private final double clawOpenPos = 0.7;
    private final double clawClosedPos = 0.2;


    public ClawSubsystem(SimpleServo clawLeft,SimpleServo clawRight, SimpleServo axle) {
        this.clawLeft = clawLeft;
        this.clawRight = clawRight;
        this.axle = axle;
    }

    public void grab() {
        clawLeft.setPosition(clawClosedPos);
        clawRight.setPosition(clawClosedPos);
        axle.setPosition(0.7);
    }

    public void release() {
        clawLeft.setPosition(clawOpenPos);
        clawRight.setPosition(clawOpenPos);
        axle.setPosition(0);
    }
}
