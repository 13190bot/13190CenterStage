package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class IndividualMotorTestCommand extends CommandBase {
    protected MotorEx fl, fr, bl, br;
    protected DoubleSupplier frontLeftPower, frontRightPower, backLeftPower, backRightPower;
    protected BooleanSupplier leftTriggerPressed,rightTriggerPressed;
    private DriveSubsystem driveSubsystem;

    public IndividualMotorTestCommand(DriveSubsystem driveSubsystem, MotorEx fl, MotorEx fr, MotorEx bl, MotorEx br, DoubleSupplier frontLeftPower, DoubleSupplier frontRightPower, DoubleSupplier backLeftPower, DoubleSupplier backRightPower, BooleanSupplier leftTriggerPressed, BooleanSupplier rightTriggerPressed){
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.driveSubsystem = driveSubsystem;
        this.frontLeftPower = frontLeftPower;
        this.frontRightPower = frontRightPower;
        this.backLeftPower = backLeftPower;
        this.backRightPower = backRightPower;
        this.leftTriggerPressed = leftTriggerPressed;
        this.rightTriggerPressed = rightTriggerPressed;

        addRequirements(driveSubsystem);

        fr.setInverted(true);
    }
    @Override
    public void execute() {
        double finalFrontLeftPower;
        double finalFrontRightPower;
        if(leftTriggerPressed.getAsBoolean()){
             finalFrontLeftPower = -frontLeftPower.getAsDouble();
        } else {
            finalFrontLeftPower = frontLeftPower.getAsDouble();
        }

        if (rightTriggerPressed.getAsBoolean()){
            finalFrontRightPower = -frontRightPower.getAsDouble();
        } else {
            finalFrontRightPower = frontRightPower.getAsDouble();
        }
        double finalBackLeftPower = backLeftPower.getAsDouble();
        double finalBackRightPower= backRightPower.getAsDouble();


        fl.set(finalFrontLeftPower);
        fr.set(finalFrontRightPower);
        bl.set(finalBackLeftPower);
        br.set(finalBackRightPower);
    }
}
