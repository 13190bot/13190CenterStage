// UNTESTED: TEST ASAP


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
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

public final class Recorder {
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

            motors = new ArrayList<>();
            servos = new ArrayList<>();
            for (int i = 0; i < motorNames.size(); i++) {
                motors.add(hardwareMap.get(DcMotor.class, motorNames.get(i)));
            }
            for (int i = 0; i < servoNames.size(); i++) {
                servos.add(hardwareMap.get(Servo.class, servoNames.get(i)));
            }






            File dir = new File(DIRPATH + File.separator + "temp");
            dir.mkdirs();
            dir.delete();
        }

        clearRecording();
    }

    public static void clearRecording() {
        data = new ArrayList[motors.size() + servos.size() + 2];
        data[0] = new ArrayList();
        data[1] = new ArrayList();
        for (int i = 2; i < motors.size() + servos.size() + 2; ++i) data[i] = new ArrayList<Double>();
    }

    public static void startRecording () {
        // clear data
        clearRecording();

        recordingStartTime = System.nanoTime();
    }

    public static void record () {
        data[0].add(System.nanoTime() - recordingStartTime);
        data[1].add(voltageSensor.getVoltage());

        int motorsLength = motors.size();
        for (int i = 0; i < motorsLength; i++) {
            data[i + 2].add(motors.get(i).getPower());
        }
        for (int i = 0; i < servos.size(); i++) {
            data[i + motorsLength + 2].add(servos.get(i).getPosition());
        }


//        for (int i = 0;             i < motors.size();                 ++i) data[i + 2].add(motors.get(i                ).getPower());
//        for (int i = motors.size(); i < servos.size() + motors.size(); ++i) data[i].add(servos.get(i - motors.size()).getPosition());
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

//    public static String dataToCode () {
//        String out = "";
//
//        out +=
//                //header stuff TODO: MAKE SURE NO MORE HEADERS NECESSARY
//                          "package org.firstinspires.ftc.teamcode.recordedAutos;"        + "\n"
//                        + ""                                                             + "\n"
//                        + "import com.qualcomm.robotcore.eventloop.opmode.TeleOp;"       + "\n"
//                        + "import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;" + "\n"
//                        + "import com.qualcomm.robotcore.hardware.*;"                    + "\n"
//                        + ""                                                             + "\n"
//                        + "import java.util.ArrayList"                                   + "\n"
//                        + ""                                                             + "\n"
//        ;
//
//        //code:
//        out +=
//                          "@Autonomous(group = \"Recorded Autos\")"                                                                 + "\n"
//                        + "public class " + currentFile + " extends LinearOpMode {"                                                 + "\n"
//                        + "    ArrayList<DcMotor> motors;"                                                                          + "\n"
//                        + "    ArrayList<Servo>   servos;"                                                                          + "\n"
//                        + ""                                                                                                        + "\n"
//                        + "    String[] motorNames = {" + motorNames.toString().substring(1, motorNames.toString().length()) + "};" + "\n"
//                        + "    String[] servoNames = {" + servoNames.toString().substring(1, servoNames.toString().length()) + "};" + "\n"
//                        + ""                                                                                                        + "\n"
//                        + "    ArrayList[] data  = {"                                                                               + "\n"
//        ;
//
//        //data:
//        for (int i = 0; i < data.length-1; ++i) out += "        (ArrayList)Arrays.asList(" + data[i].toString().substring(1, data.toString().length()-1) + "),\n";
//        out += "        new ArrayList(Arrays.asList(" + data[data.length-1].toString().substring(1, data[data.length-1].toString().length()-1) + "))\n";
//
//        //continue rest of code
//        out +=
//                          "    };"                                                                                                                    + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "    //replay based off of data"                                                                                            + "\n"
//                        + "    @Override"                                                                                                             + "\n"
//                        + "    public void runOpMode() throws InterruptedException {"                                                                 + "\n"
//                        + "        //get devices"                                                                                                     + "\n"
//                        + "        motors = new ArrayList<>()"                                                                                        + "\n"
//                        + "        servos = new Arraylist<>()"                                                                                        + "\n"
//                        + "        for (String name : motorNames) motors.add(hardwareMap.dcMotor.get(name));"                                         + "\n"
//                        + "        for (String name : servoNames) servos.add(hardwareMap.dcMotor.get(name));"                                         + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "        telemetry.addData(\"init\", \"done\");"                                                                            + "\n"
//                        + "        telemetry.update();"                                                                                               + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "        waitForStart();"                                                                                                   + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "        //loop through the logs as long as op mode is active or untill we hit the end"                                     + "\n"
//                        + "        double replayingStartTime = System.nanoTime();"                                                                    + "\n"
//                        + "        for (int i = 0; opModeIsActive() && i < data[0].size(); ++i) {"                                                    + "\n"
//                        + "            while ((double) data[0].get(i) > System.nanoTime() - replayingStartTime) {} //wait till next log"              + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "            //write data to devices"                                                                                       + "\n"
//                        + "            for (int i2 = 0; i2 < motors.size(); ++i2) motors[i2].setPower((double) data[i2                 + 2].get(i));" + "\n"
//                        + "            for (int i2 = 0; i2 < servos.size(); ++i2) servos[i2].setPower((double) data[i2 + motors.size() + 2].get(i));" + "\n"
//                        + ""                                                                                                                          + "\n"
//                        + "            //write to telemetry and increment targel log # (i)"                                                           + "\n"
//                        + "            telemetry.addData(\"datapoint, i++ + \"/\" + data[0].size());"                                                 + "\n"
//                        + "            telemetry.update();"                                                                                            + "\n"
//                        + "        }"                                                                                                                 + "\n"
//                        + "    }"                                                                                                                     + "\n"
//                        + "}"
//        ;
//
//        return out;
//    }


    public static String dataToCode() {
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

    public static String hardwareToCode() {
        // WARNING: GENERATES A PRIMITIVE ARRAY WHILE THIS LIBRARY USES ARRAYLIST
        return "String[]motorNames={" + String.join(",", motorNames) + "};\nString[]servoNames={" + String.join(",", servoNames) + "};";
    }

    public static String generateAuto() {
        String out = "";

    }

    public static void startReplaying(ArrayList[] data, BooleanSupplier replaying) {
        double replayingStartTime = System.nanoTime();
        int i = 0;
        // Busy looping for each ms (assuming replay loop time is less than recording loop time)
        int length = data[0].size();
        while (replaying.getAsBoolean()) {
            while ((long) data[0].get(i) > System.nanoTime() - replayingStartTime) {
                // Busy loop
            }


            // Motors

            // If needed for (more) accuracy, factor in voltage difference (UNTESTED)
//            double startVoltage = voltageSensor.getVoltage();
//            double powerMultiplier = (double) data[1].get(i) / startVoltage; // assumes linear model
//            for (int i2 = 0; i2 < motors.size(); i2++) {
//                motors.get(i2).setPower((double) data[i2 + 2].get(i) * powerMultiplier);
//
//                telemetry.addData("a", (double) data[i2 + 2].get(i) * powerMultiplier);
//            }

            // Don't factor in voltage (TESTED)
            for (int i2 = 0; i2 < motors.size(); i2++) {
                motors.get(i2).setPower((double) data[i2 + 2].get(i));
            }

            // Servos
            for (int i2 = 0; i2 < servos.size(); i2++) {
                servos.get(i2).setPosition((double) data[i2 + motors.size() + 2].get(i));
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

        // servos: todo
        for (int i = 2 + motors.size(); i < 2 + motors.size() + servos.size(); i++) {
            ArrayList row = data[i];
            out[i] = new ArrayList();
            ArrayList rowOut = out[i];
            for (int i2 = row.size() - 1; i2 > -1; i2--) {
                rowOut.add(row.get(i2));
            }
        }

        return out;
    }
















    static boolean isInitialised = false;

    /*
        Format of data:
        0: nanosecond since opmode start (long)
        1: voltage                       (double)
        2: motor powers                  (double)
        3: servo positions               (double)
    */
    public static ArrayList[] data;

    static ArrayList<DcMotor> motors;
    static ArrayList<Servo> servos;
    static VoltageSensor voltageSensor;

    static ArrayList<String> motorNames;
    static ArrayList<String> servoNames;

    static String currentFile;
    public static final String DIRPATH = "/sdcard/FIRST/Recorder";

    static long recordingStartTime;
    public static boolean recording = false;
    public static Telemetry telemetry;
}
