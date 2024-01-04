/*
TODO:
 - Use encoders and slow down / speed up depending on how it matches up
 - Use replayer function in Replayer.java instead of hardcoding
 - Multiple recorded data
 - Test saving

 - Play around with motor powers to see if odometry correction works (or set motor to 0 and replay and look at powerMultiplier)
 - Get recorded data from telemetry
 */


/*
NOTES ABOUT ODOMETRY:
frontLeft Encoder = left (positive = backward, negative = forward)
backLeft Encoder = back (positive = right, negative = left)


 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

@Config
public class Recorder {

    public static double kO = 0.001; // Odometry correcting coefficient

    public static void init (HardwareMap hardwareMap, String file, Telemetry t) {
        telemetry = t;
        currentFile = file;

        if (!isInitialised) {
            isInitialised = true;

            /*
            motors = new ArrayList<>();
            servos = new ArrayList<>();

            motorNames = new ArrayList<>();
            servoNames = new ArrayList<>();

            voltageSensor = hMap.voltageSensor.iterator().next();

            try {
                Field deviceMapField = HardwareMap.DeviceMapping.class.getField("map");
                deviceMapField.setAccessible(true);

                ((HashMap<String, DcMotor>) deviceMapField.get(hMap.dcMotor)).forEach((k, v) -> {
                    motorNames.add(k);
                    motors.add(v);
                });
                ((HashMap<String, Servo>) deviceMapField.get(hMap.servo)).forEach((k, v) -> {
                    servoNames.add(k);
                    servos.add(v);
                });
            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
             */


            voltageSensor = hardwareMap.voltageSensor.iterator().next();

            motorNames = new ArrayList<>(Arrays.asList("frontLeft", "frontRight", "backLeft", "backRight"));
            servoNames = new ArrayList<>(Arrays.asList("arm", "pitch", "claw"));
            odometryNames = new ArrayList<>(Arrays.asList("frontLeft", "backLeft"));

            motors = new ArrayList<>();
            servos = new ArrayList<>();
            odometry = new ArrayList<>();
            for (int i = 0; i < motorNames.size(); i++) {
                motors.add(hardwareMap.get(DcMotor.class, motorNames.get(i)));
            }
            for (int i = 0; i < servoNames.size(); i++) {
                servos.add(hardwareMap.get(Servo.class, servoNames.get(i)));
            }
            for (int i = 0; i < odometryNames.size(); i++) {
                odometry.add(hardwareMap.get(DcMotor.class, odometryNames.get(i)));
            }






            File dir = new File(DIRPATH + File.separator + "temp");
            dir.mkdirs();
            dir.delete();
        }

        clearRecording();
    }

    public static void clearRecording() {
        data = new ArrayList[2 + motors.size() + servos.size() + odometry.size()];
        data[0] = new ArrayList();
        data[1] = new ArrayList();
        for (int i = 2; i < 2 + motors.size() + servos.size() + odometry.size(); i++) data[i] = new ArrayList<Double>();
    }

    public static void resetOdometry() {
        for (int i = 0; i < odometry.size(); i++) {
            DcMotor motor = odometry.get(i);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public static void startRecording () {
        // clear data
        clearRecording();

        resetOdometry();

        recordingStartTime = System.nanoTime();
    }

    public static void record () {
        data[0].add(System.nanoTime() - recordingStartTime);
        data[1].add(voltageSensor.getVoltage());

        for (int i = 0; i < motors.size(); i++) {
            data[2 + i].add(motors.get(i).getPower());
        }
        for (int i = 0; i < servos.size(); i++) {
            Servo servo = servos.get(i);
            if (((ServoImplEx) servo).isPwmEnabled()) {
                data[2 + motors.size() + i].add(servo.getPosition());
            } else {
                data[2 + motors.size() + i].add(Double.NaN);
            }
        }
        for (int i = 0; i < odometry.size(); i++) {
            data[2 + motors.size() + servos.size() + i].add(odometry.get(i).getCurrentPosition());
        }
    }

    public static void saveRecording () {
        File file = new File(DIRPATH + File.separator + currentFile + ".java");
        try {
            // https://www.w3schools.com/java/java_files_create.asp

            file.createNewFile();
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(generateAuto());
            myWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static String dataToCode() {
        // WARNING: GENERATES A PRIMITIVE ARRAY WHILE THIS LIBRARY USES ARRAYLIST
        String out = "double[][]data={";
        for (int i = 0; i < data.length; i++) {
            // Optimized (https://stackoverflow.com/a/23183963)
            out = out + "{" + data[i].stream().map(Object::toString).collect(Collectors.joining(",")) + "}";
            if (i != data.length - 1) {
                out = out + ",";
            }
        }
        out = out + "};";
        return out;
    }

    public static String hardwareToCode() {
        // WARNING: GENERATES A PRIMITIVE ARRAY WHILE THIS LIBRARY USES ARRAYLIST
        return "String[]motorNames={" + String.join(",", motorNames) + "};\nString[]servoNames={" + String.join(",", servoNames) + "};\nString[]odometryNames={" + String.join(",", odometryNames) + "};";
    }

    public static String generateAuto() {
        // Replayer.java
        return "// Generated by Recorder.java\n" +
                "package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;\n" +
                "\n" +
                "import com.qualcomm.robotcore.eventloop.opmode.Autonomous;\n" +
                "import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;\n" +
                "import com.qualcomm.robotcore.hardware.DcMotor;\n" +
                "import com.qualcomm.robotcore.hardware.Servo;\n" +
                "\n" +
                "@Autonomous(group=\"Recorder\", name=\"Replayer\")\n" +
                "public class Replayer extends LinearOpMode {\n" +
                "\n" +
                "    // AUTOGEN\n" +
//                "    double[][] data = {{}, {}};\n" +
//                "    String[] motorNames = {\"\"};\n" +
//                "    String[] servoNames = {\"\"};\n" +
                dataToCode() + "\n" +
                hardwareToCode() + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "    DcMotor[] motors = new DcMotor[motorNames.length];\n" +
                "    Servo[] servos = new Servo[servoNames.length];\n" +
                "    @Override\n" +
                "    public void runOpMode() throws InterruptedException {\n" +
                "        for (int i = 0; i < motorNames.length; i++) {\n" +
                "            motors[i] = hardwareMap.get(DcMotor.class, motorNames[i]);\n" +
                "        }\n" +
                "        for (int i = 0; i < servoNames.length; i++) {\n" +
                "            servos[i] = hardwareMap.get(Servo.class, servoNames[i]);\n" +
                "        }\n" +
                "\n" +
                "        waitForStart();\n" +
                "\n" +
                "        replay(data);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    // Yields, synchronous\n" +
                "    public void replay(double[][] data) {\n" +
                "        long replayingStartTime = System.nanoTime();\n" +
                "        int i = 0;\n" +
                "        // Busy looping for each ms (assuming replay loop time is less than recording loop time)\n" +
                "        int length = data[0].length;\n" +
                "        while (opModeIsActive()) {\n" +
                "            while ((long) data[0][i] > System.nanoTime() - replayingStartTime) {\n" +
                "                // Busy loop\n" +
                "            }\n" +
                "\n" +
                "\n" +
                "            // Motors\n" +
                "\n" +
                "            // If needed for (more) accuracy, factor in voltage difference (UNTESTED)\n" +
                "//            double startVoltage = voltageSensor.getVoltage();\n" +
                "//            double powerMultiplier = (double) data[1].get(i) / startVoltage; // assumes linear model\n" +
                "//            for (int i2 = 0; i2 < motors.size(); i2++) {\n" +
                "//                motors.get(i2).setPower((double) data[i2 + 2].get(i) * powerMultiplier);\n" +
                "//\n" +
                "//                telemetry.addData(\"a\", (double) data[i2 + 2].get(i) * powerMultiplier);\n" +
                "//            }\n" +
                "\n" +
                "            // Don't factor in voltage (TESTED)\n" +
                "            for (int i2 = 0; i2 < motors.length; i2++) {\n" +
                "                motors[i2].setPower(data[i2 + 2][i]);\n" +
                "            }\n" +
                "\n" +
                "            // Servos\n" +
                "            for (int i2 = 0; i2 < servos.length; i2++) {\n" +
                "                servos[i2].setPosition(data[i2 + motors.length + 2][i]);\n" +
                "            }\n" +
                "\n" +
                "            // Other code\n" +
                "            // Stop replaying\n" +
                "//            if (gamepad1.b) {\n" +
                "//                break;\n" +
                "//            }\n" +
                "\n" +
                "            i = i + 1;\n" +
                "            if (i == length) {\n" +
                "                break;\n" +
                "            }\n" +
                "\n" +
                "\n" +
                "\n" +
                "            // telemetry shit\n" +
                "            telemetry.addData(\"REPLAYING\", i + \"/\" + length);\n" +
                "            telemetry.update();\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
    }

    public static void stopRecording() {
        // Just prints data out to telemetry

        telemetry.clear();

        telemetry.addLine(hardwareToCode() + "\n\n" + dataToCode());

        telemetry.update();
    }

    /*
    Optimization Ideas:
    - Only update if value changes (or maybe not since it already does internal check? idk)
    - Benchmark
     */
    public static void startReplaying(ArrayList[] data, BooleanSupplier replaying) {
        // Reset encoders
        resetOdometry();

        double replayingStartTime = System.nanoTime();
        int i = 0;
        // Busy looping for each ms (assuming replay loop time is less than recording loop time)
        int length = data[0].size();
        while (replaying.getAsBoolean()) {
            boolean loopedAtLeastOnce = false; // TODO FOR DEBUG, REMOVE
            while ((long) data[0].get(i) > System.nanoTime() - replayingStartTime) {
                // Busy loop
                loopedAtLeastOnce = true; // TODO FOR DEBUG, REMOVE
            }


            // Motors

            // DOESN'T WORK
            // If needed for (more) accuracy, factor in voltage difference (UNTESTED)
//            double startVoltage = voltageSensor.getVoltage();
//            double powerMultiplier = (double) data[1].get(i) / startVoltage; // assumes linear model
//            for (int i2 = 0; i2 < motors.size(); i2++) {
//                motors.get(i2).setPower((double) data[i2 + 2].get(i) * powerMultiplier);
//
//                telemetry.addData("a", (double) data[i2 + 2].get(i) * powerMultiplier);
//            }



            if (i != length - 1) {
                // If needed for (more) accuracy, factor in odometry
                double powerMultiplier = 0;
                for (int i2 = 0; i2 < odometry.size(); i2++) {
//                    double lastV = (double) data[2 + motors.size() + servos.size() + i2].get(i - 1);
                    double currentV = (double) (int) data[2 + motors.size() + servos.size() + i2].get(i);
                    double nextV = (double) (int) data[2 + motors.size() + servos.size() + i2].get(i + 1);
                    double currentT = (double) (long) data[0].get(i);
                    double nextT = (double) (long) data[0].get(i + 1);
                    telemetry.addLine("Encoder " + odometryNames.get(i2) + "(" + i2 + ")");
                    telemetry.addData("Difference (recorded - measured): ", currentV - odometry.get(i2).getCurrentPosition());
                    powerMultiplier = powerMultiplier + (1 + kO * ((nextV - currentV) / (nextT - currentT) * (currentV - odometry.get(i2).getCurrentPosition())));
                }
                powerMultiplier = powerMultiplier / odometry.size();
                telemetry.addData("powerMultiplier", powerMultiplier);
                for (int i2 = 0; i2 < motors.size(); i2++) {
                    motors.get(i2).setPower((double) data[i2 + 2].get(i) * powerMultiplier);
                }

            } else {
                // Don't factor in anything (TESTED)
                for (int i2 = 0; i2 < motors.size(); i2++) {
                    motors.get(i2).setPower((double) data[2 + i2].get(i));
                }
            }


            // Servos
            for (int i2 = 0; i2 < servos.size(); i2++) {
                double v = (double) data[2 + motors.size() + i2].get(i);

                if (i == 0 || ((double) data[2 + motors.size() + i2].get(i - 1) != v)) {
                    if (Double.isNaN(v)) {
                        ((ServoImplEx) servos.get(i2)).setPwmDisable();
                    } else {
                        ((ServoImplEx) servos.get(i2)).setPwmEnable();
                        servos.get(i2).setPosition(v);
                    }
                }
            }

            // Other code
            // Stop replaying
//            if (gamepad1.b) {
//                break;
//            }

            i = i + 1;
            if (i == length) {
                break;
            }



            // telemetry shit
            telemetry.addData("REPLAYING", i + "/" + length);
            telemetry.addData("KEEPING UP WITH RECORDED", loopedAtLeastOnce);
            telemetry.update();
        }
    }



    public static ArrayList[] reverseData(ArrayList[] data) {
        ArrayList[] out = new ArrayList[data.length];

        // ms
        out[0] = new ArrayList();
        for (int i = 0; i < data[0].size(); i++) {
            out[0].add(data[0].get(i));
        }

        // voltage
        {
            int i = 1;
            ArrayList row = data[i];
            out[i] = new ArrayList();
            ArrayList rowOut = out[i];
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add(row.get(i2));
            }
        }

        // motors
        for (int i = 2; i < 2 + motors.size(); i++) {
            ArrayList row = data[i];
            out[i] = new ArrayList();
            ArrayList rowOut = out[i];
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add(-(double) row.get(i2));
            }
        }

        // servos
        for (int i = 2 + motors.size(); i < 2 + motors.size() + servos.size(); i++) {
            ArrayList row = data[i];
            out[i] = new ArrayList();
            ArrayList rowOut = out[i];
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add(row.get(i2));
            }
        }

        // odometry
        for (int i = 2 + motors.size() + servos.size(); i < 2 + motors.size() + servos.size() + odometry.size(); i++) {
            ArrayList row = data[i];
            out[i] = new ArrayList();
            ArrayList rowOut = out[i];
            int rowLastV = (int) row.get(row.size() - 1);
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add((int) row.get(i2) - rowLastV);
            }
        }

        return out;
    }
















    static boolean isInitialised = false;

    /*
        Format of data:
        0: nanosecond since opmode start (long)
        1: voltage                       (double)
        2 to #motors - 1 + 2: motor powers                  (double)
        3 to #motors - 1 + #servos - 1 + 2: servo positions               (double)
    */
    public static ArrayList[] data;

    static ArrayList<DcMotor> motors;
    static ArrayList<Servo> servos;
    static ArrayList<DcMotor> odometry;
    static VoltageSensor voltageSensor;

    static ArrayList<String> motorNames;
    static ArrayList<String> servoNames;
    static ArrayList<String> odometryNames;

    static String currentFile;
    public static final String DIRPATH = "/sdcard/FIRST/Recorder";

    static long recordingStartTime;
    public static boolean recording = false;
    public static Telemetry telemetry;
}
