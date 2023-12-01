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

@TeleOp(name = "RecordTeleOp")
public class RecordTeleOp extends LinearOpMode {
    public String filename = "recording.java"; // file where recording is saved to
    public String[] motorNames = {"frontLeft", "frontRight", "backLeft", "backRight"};
    public static double maxSpeed = 0.8;





    DcMotor[] motors = new DcMotor[motorNames.length];

    /*
    Format of data:
    0: nanosecond since opmode start
    1: voltage
    2: 0th motor
     */
    ArrayList[] data = new ArrayList[motorNames.length + 2];


    // Autogen / Serializer
    // ASSUMES EVERYTHING IS DOUBLES (which it is)
    public static String dataToCode(ArrayList[] data) {
        String out = "ArrayList[]data={";
        for (int i = 0; i < data.length; i++) {
            // Optimized (https://stackoverflow.com/a/23183963)
            out = out + "new ArrayList(Arrays.asList(" + data[i].stream().map(Object::toString).collect(Collectors.joining(",")) + "))";
            if (i != data.length - 1) {
                out = out + ",";
            }


//            ArrayList row = data[i];
//            for (int i2 = 0; i2 < row.size(); i2++) {
//
//            }
        }
        out = out + "}";
        return out;
    }
    // Test
//    public static void main(String args[]) {
//        System.out.println(dataToCode(
//                new ArrayList[]{new ArrayList(Arrays.asList(0, 1, 2, 2.5))}
//        ));
//    }

    public static ArrayList[] reverseData(ArrayList[] data) {
        ArrayList[] out = new ArrayList[data.length];
        for (int i = 0; i < data.length; i++) {
            ArrayList row = data[i];
            ArrayList rowOut = out[i];
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add(row.get(i2));
            }
        }
        return out;
    }


    @Override
    public void runOpMode() throws InterruptedException {
//        hardwareMap.getAll(DcMotor.class);

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

        // Recording
        telemetry.addLine("Press a to start recording");
        telemetry.update();

        // Wait
        while (!gamepad1.a && opModeIsActive()) {
            sleep(10);
        }

        telemetry.addLine("Press b to stop recording");
        telemetry.update();

        double recordingStartTime = System.nanoTime();

        while (opModeIsActive()) {
            double startTime = System.nanoTime();
            double startVoltage = voltageSensor.getVoltage();

            // TeleOp code here: START
            // motors[0].setPower(gamepad1.left_stick_y);
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_trigger - gamepad1.left_trigger;
            double denominator = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rx), 1) / maxSpeed;
            fl.setPower((y + x + rx) / denominator);
            bl.setPower((y - x + rx) / denominator);
            fr.setPower((y - x - rx) / denominator);
            br.setPower((y + x - rx) / denominator);
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


        // Save recording
        /*
        File file = new File(filename);
        try {
            // https://www.w3schools.com/java/java_files_create.asp

            file.createNewFile();
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(dataToCode(data));
            myWriter.close();

            telemetry.addData("file path:", file.getAbsolutePath());
            telemetry.update();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */





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

        // Loop as fast as possible
        /*
        while (opModeIsActive()) {
            double time = System.nanoTime() - replayingStartTime;

            while ((double) data[0].get(i) > time) {
                i = i + 1;
            }

            i = i - 1;

            for (int i2 = 0; i2 < motors.length; i2++) {
                motors[i2].setPower((double) data[i2 + 2].get(i));
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
