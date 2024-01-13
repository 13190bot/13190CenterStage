package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class DriveSubsystem extends SubsystemBase {

    private MecanumDrive mecanumDrive;
    private MotorEx fl, fr, bl, br;

    public final double defaultSpeedMultiplier = 1;
    public double speedMultiplier = defaultSpeedMultiplier;
    Telemetry telemetry;

    public DriveSubsystem(MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br, Telemetry telemetry) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.telemetry = telemetry;

        mecanumDrive = new MecanumDrive(true, fl, fr, bl, br);
    }



    // Limit acceleration
//    public double maxAcceleration = 0.00000000001; // Max Acceleration per "tick"
//
//    public double lastStrafe0, lastForward0, lastRotate0; // Position
//    public double lastLastStrafe0, lastLastForward0, lastLastRotate0; // Position
//    public double lastStrafe1, lastForward1, lastRotate1; // Speed

    
    public static double maxAcceleration = 100000; // Max Acceleration per second
    ElapsedTime timer = new ElapsedTime();
    public double lastStrafe, lastForward, lastRotate;

    public void driveRobotCentric(double strafe0, double forward0, double rotate0) {



        double strafeAcceleration = (strafe0 - lastStrafe) / timer.seconds();
        if (strafeAcceleration > maxAcceleration) {
            strafeAcceleration = maxAcceleration;
            strafe0 = lastStrafe + strafeAcceleration;
        } else if (strafeAcceleration < -maxAcceleration) {
            strafeAcceleration = -maxAcceleration;
            strafe0 = lastStrafe + strafeAcceleration;
        }

        double forwardAcceleration = (forward0 - lastForward) / timer.seconds();
        telemetry.addData("forwardAcceleration", forwardAcceleration);
        telemetry.update();
        if (forwardAcceleration > maxAcceleration) {
            forwardAcceleration = maxAcceleration;
            forward0 = lastForward + forwardAcceleration;
        } else if (forwardAcceleration < -maxAcceleration) {
            forwardAcceleration = -maxAcceleration;
            forward0 = lastForward + forwardAcceleration;
        }

        double rotateAcceleration = (rotate0 - lastRotate) / timer.seconds();
        if (rotateAcceleration > maxAcceleration) {
            rotateAcceleration = maxAcceleration;
            rotate0 = lastRotate + rotateAcceleration;
        } else if (rotateAcceleration < -maxAcceleration) {
            rotateAcceleration = -maxAcceleration;
            rotate0 = lastRotate + rotateAcceleration;
        }

        lastStrafe = strafe0;
        lastForward = forward0;
        lastRotate = rotate0;

        timer.reset();

        mecanumDrive.driveRobotCentric(strafe0 * speedMultiplier, forward0 * speedMultiplier, rotate0 * Math.abs(speedMultiplier));
    }
}