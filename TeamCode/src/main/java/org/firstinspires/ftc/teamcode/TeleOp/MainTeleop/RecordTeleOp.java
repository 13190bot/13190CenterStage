package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.sql.Array;
import java.util.ArrayList;

public class RecordTeleOp extends LinearOpMode {
    public String[] motorNames = {"frontLeft", "frontRight", "backLeft", "backRight"};
    DcMotor[] motors = new DcMotor[motorNames.length];

    /*
    Format of data:
    0: nanosecond since opmode start
    1: voltage
    2: 0th motor
     */
    ArrayList[] data = new ArrayList[motorNames.length + 2];

    @Override
    public void runOpMode() throws InterruptedException {
        for (int i = 0; i < motorNames.length; i++) {
            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);
        }

        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        waitForStart();

        // Recording

        // Wait
        while (!gamepad1.a) {
            sleep(10);
        }

        double recordingStartTime = System.nanoTime();

        while (opModeIsActive()) {
            double startTime = System.nanoTime();
            double startVoltage = voltageSensor.getVoltage();

            // TeleOp code here: START
            motors[0].setPower(gamepad1.left_stick_y);
            // TeleOp code here: END

//            double endTime = System.nanoTime();
//            double endVoltage = voltageSensor.getVoltage();

            data[0].add(startTime - recordingStartTime);
            data[1].add(startVoltage);

            for (int i = 0; i < motors.length; i++) {
                data[i + 2].add(motors[i].getPower());
            }

            // Other code
            // Stop recording
            if (gamepad1.b) {
                break;
            }
        }



        // Replay

        // Wait
        while (!gamepad1.a) {
            sleep(10);
        }

        double replayingStartTime = System.nanoTime();
        int i = 0;

        // Loop as fast as possible
        /*
        while (opModeIsActive()) {
            double time = System.nanoTime() - replayingStartTime;

            while ((double) data[0].get(i) > time) {
                i = i + 1;
            }

            i = i - 1;

            for (int i2 = 0; i2 < motors.length; i2++) {
                motors[i].setPower((double) data[i2 + 2].get(i));
            }

            i = i + 1;

            // Other code
            // Stop replaying
            if (gamepad1.b) {
                break;
            }
        }
         */

        // Busy looping for each ms (assuming replay loop time is less than recording loop time)
        while (opModeIsActive()) {
            while ((double) data[0].get(i) > System.nanoTime() - replayingStartTime) {
                // Busy loop
            }

            for (int i2 = 0; i2 < motors.length; i2++) {
                motors[i].setPower((double) data[i2 + 2].get(i));
            }

            // Other code
            // Stop replaying
            if (gamepad1.b) {
                break;
            }

            i = i + 1;
        }

    }
}
