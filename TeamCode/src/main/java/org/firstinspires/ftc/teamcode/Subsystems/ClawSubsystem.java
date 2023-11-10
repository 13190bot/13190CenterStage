package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
@Config
public class ClawSubsystem extends SubsystemBase {

    public static SimpleServo claw;
    public static CRServo axle;
    public static double clawOpenPos = 0.7;
    public static double clawClosedPos = 0.2;


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
