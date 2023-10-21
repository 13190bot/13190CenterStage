package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;


public class LiftSubsystem extends SubsystemBase {

    private MotorEx liftRight, liftLeft;

    private double kP = 0, kI = 0, kD = 0;

    private double integralSum;

    private double lastError;

    private ElapsedTime timer = new ElapsedTime();


    public LiftSubsystem(MotorEx liftRight, MotorEx liftLeft) {
        this.liftRight = liftRight;
        this.liftLeft = liftLeft;
    }


    public void lift(double finalPos) {
        while (liftRight.getCurrentPosition() != finalPos) {

            double error = finalPos - liftRight.getCurrentPosition();

            double derivative = (error - lastError) / timer.seconds();
            integralSum = integralSum + (error * timer.seconds());

            double output = (kP * error) + (kI * integralSum) + (kD * lastError);
            liftRight.set(output);
            liftLeft.set(output);

            lastError = error;

            timer.reset();
        }
    }
}