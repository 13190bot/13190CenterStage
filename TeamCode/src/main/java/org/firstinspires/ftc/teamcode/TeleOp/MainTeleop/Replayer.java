// UNTESTED, HAVENT OPENED WITH INTELLIJ, OPTIMIZED AS MUCH AS POSSIBLE

package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@TeleOp(name = "Replayer")
public class RecordTeleOp extends LinearOpMode {
    public String[] motorNames = {"frontLeft", "frontRight", "backLeft", "backRight"};
    DcMotor[] motors = new DcMotor[motorNames.length];

    /*
    Format of data:
    0: nanosecond since opmode start
    1: voltage
    2: 0th motor
    3: 0th servo
     */
    ArrayList[] data = new ArrayList[motorNames.length + 2];

    public void runOpMode() throws InterruptedException {
      
        // TODO: Use getall to get all motors and servos
        // Or prateek's reflection

        // TeleOp code here: START
        DcMotor fl = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor fr = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor bl = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor br = hardwareMap.get(DcMotor.class, "backRight");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        // fr and br need to be reversed for some reason
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        // TeleOp code here: END


        for (int i = 0; i < motorNames.length; i++) {
            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = new ArrayList();
        }

        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();

        // Replay
        telemetry.addLine("Press a to start replaying");
        telemetry.update();

        // Wait
        while (!gamepad1.a && opModeIsActive()) {
            sleep(10);
        }
        telemetry.addLine("Press b to stop replaying");
        telemetry.update();

        double replayingStartTime = System.nanoTime();
        int i = 0;
      
        // Busy looping for each ms (assuming replay loop time is less than recording loop time)
        int length = data[0].size();
        while (opModeIsActive()) {
            while ((double) data[0].get(i) > System.nanoTime() - replayingStartTime) {
                // Busy loop
            }

            // If needed for (more) accuracy, factor in voltage difference (UNTESTED)
            double startVoltage = voltageSensor.getVoltage();
            double powerMultiplier = (double) data[1].get(i) / startVoltage; // assumes linear model
            for (int i2 = 0; i2 < motors.length; i2++) {
                motors[i2].setPower((double) data[i2 + 2].get(i) * powerMultiplier);
            }

            // Don't factor in voltage (TESTED)
//            for (int i2 = 0; i2 < motors.length; i2++) {
//                motors[i2].setPower((double) data[i2 + 2].get(i));
//            }

            // Other code
            // Stop replaying
            if (gamepad1.b) {
                break;
            }

            i = i + 1;
            if (i == length) {
                break;
            }
        }

    }
}
