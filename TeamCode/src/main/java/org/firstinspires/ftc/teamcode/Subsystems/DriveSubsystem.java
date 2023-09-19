package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;



public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private MotorEx fl, fr, bl, br;

    public DriveSubsystem(MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;

        mecanumDrive = new MecanumDrive(true, fl, fr, bl, br);
    }


    public void driveRobotCentric(double forward, double strafe, double rotate) {
        mecanumDrive.driveRobotCentric(forward, strafe, rotate);
    }

    public void driveRobotCentricSlowMode(double forward, double strafe, double rotate) {
        mecanumDrive.driveRobotCentric(forward/3, strafe/3, rotate/3);
    }
}