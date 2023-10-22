package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;



public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private MotorEx fl, fr, bl, br;

    public double speedMultiplier = 1;

    public DriveSubsystem(MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;

        mecanumDrive = new MecanumDrive(true, fl, fr, bl, br);
    }


    public void driveRobotCentric(double strafe, double forward, double rotate) {
        mecanumDrive.driveRobotCentric(strafe * speedMultiplier, forward * speedMultiplier, rotate * speedMultiplier);
    }
}