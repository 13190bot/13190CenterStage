package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

@Config
public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private MotorEx fl, fr, bl, br;

    public final double defaultSpeedMultiplier = 1;
    public double speedMultiplier = defaultSpeedMultiplier;

    public DriveSubsystem(MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;

        mecanumDrive = new MecanumDrive(true, fl, fr, bl, br);
    }



    // Limit acceleration
    public double maxAcceleration = 0.00000000001; // Max Acceleration per "tick"

    public double lastStrafe0, lastForward0, lastRotate0; // Position
    public double lastLastStrafe0, lastLastForward0, lastLastRotate0; // Position
    public double lastStrafe1, lastForward1, lastRotate1; // Speed


    public void driveRobotCentric(double strafe0, double forward0, double rotate0) {
//        double strafe1, forward1, rotate1;
//        strafe1 = strafe0 - lastStrafe0;
//        forward1 = forward0 - lastForward0;
//        rotate1 = rotate0 - lastRotate0;
//
//        double strafe2, forward2, rotate2;
//        strafe2 = strafe1 - lastStrafe1;
//        forward2 = forward1 - lastForward1;
//        rotate2 = rotate1 - lastRotate1;
//
//        lastStrafe1 = strafe1;
//        lastForward1 = forward1;
//        lastRotate1 = rotate1;
//
//        lastLastStrafe0 = lastStrafe0;
//        lastLastForward0 = lastForward0;
//        lastLastRotate0 = lastRotate0;
//        lastStrafe0 = strafe0;
//        lastForward0 = forward0;
//        lastRotate0 = rotate0;
//
//        // Limit accel
//        strafe2 = Math.min(strafe2, maxAcceleration);
//        forward2 = Math.min(forward2, maxAcceleration);
//        rotate2 = Math.min(rotate2, maxAcceleration);
//
//        strafe1 = lastStrafe1 + strafe2;
//        forward1 = lastForward1 + forward2;
//        rotate1 = lastRotate1 + rotate2;
//
//        strafe0 = lastStrafe0 + strafe1;
//        forward0 = lastForward0 + forward1;
//        rotate0 = lastRotate0 + rotate1;


        mecanumDrive.driveRobotCentric(strafe0 * speedMultiplier, forward0 * speedMultiplier, rotate0 * Math.abs(speedMultiplier));
    }
}