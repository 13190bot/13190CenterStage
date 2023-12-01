package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public final class Recorder {
    public void init (HardwareMap hMap, String file) {
        currentFile = file;

        if (!isInitialised) {
            isInitialised = true;

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

            File dir = new File(DIRPATH + File.separator + "temp");
            dir.mkdirs();
            dir.delete();
        }

        data = new ArrayList[motors.size() + servos.size() + 2];
        for (int i = 2; i < motors.size() + 2; ++i) data[i] = new ArrayList<Double>();
        for (int i = motors.size(); i < servos.size() + motors.size(); ++i) data[i] = new ArrayList<Integer>();
    }

    public void stratLogging () {
        recordingStartTime = System.nanoTime();
    }

    public void log () {
        data[0].add(System.nanoTime() - recordingStartTime);
        data[1].add(voltageSensor.getVoltage());

        for (int i = 0;             i < motors.size();                 ++i) data[i].add(motors.get(i                ).getPower());
        for (int i = motors.size(); i < servos.size() + motors.size(); ++i) data[i].add(servos.get(i - motors.size()).getPosition());
    }

    public void saveLog () {
        File file = new File(DIRPATH + currentFile + ".java");
        try {
            // https://www.w3schools.com/java/java_files_create.asp

            file.createNewFile();
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(dataToCode());
            myWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String dataToCode () {
        String out = "";

        out +=
                //header stuff TODO: MAKE SURE NO MORE HEADERS NECESSARY
                "package org.firstinspires.ftc.teamcode.recordedAutos;"        + "\n"
                        + ""                                                             + "\n"
                        + "import com.qualcomm.robotcore.eventloop.opmode.TeleOp;"       + "\n"
                        + "import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;" + "\n"
                        + "import com.qualcomm.robotcore.hardware.*;"                    + "\n"
                        + ""                                                             + "\n"
                        + "import java.util.ArrayList"                                   + "\n"
                        + ""                                                             + "\n"
        ;

        //code:
        out +=
                "@Autonomous(group = \"Recorded Autos\")"                                                                 + "\n"
                        + "public class " + currentFile + " extends LinearOpMode {"                                                    + "\n"
                        + "    ArrayList<DcMotor> motors;"                                                                          + "\n"
                        + "    ArrayList<Servo>   servos;"                                                                          + "\n"
                        + ""                                                                                                        + "\n"
                        + "    String[] motorNames = {" + motorNames.toString().substring(1, motorNames.toString().length()) + "};" + "\n"
                        + "    String[] servoNames = {" + servoNames.toString().substring(1, servoNames.toString().length()) + "};" + "\n"
                        + ""                                                                                                        + "\n"
                        + "    ArrayList[] data  = {"                                                                               + "\n"
        ;

        //data:
        for (int i = 0; i < data.length-1; ++i) out += "        Arrays.asList(" + data[i].toString().substring(1, data.toString().length()-1) + "),\n";
        out += "        Arrays.asList(" + data[data.length-1].toString().substring(1, data[data.length-1].toString().length()-1) + ")\n";

        //continue rest of code
        out +=
                "    };"                                                                                                                    + "\n"
                        + ""                                                                                                                          + "\n"
                        + "    //replay based off of data"                                                                                            + "\n"
                        + "    @Override"                                                                                                             + "\n"
                        + "    public void runOpMode() throws InterruptedException {"                                                                 + "\n"
                        + "        //get devices"                                                                                                     + "\n"
                        + "        motors = new ArrayList<>()"                                                                                        + "\n"
                        + "        servos = new Arraylist<>()"                                                                                        + "\n"
                        + "        for (String name : motorNames) motors.add(hardwareMap.dcMotor.get(name));"                                         + "\n"
                        + "        for (String name : servoNames) servos.add(hardwareMap.dcMotor.get(name));"                                         + "\n"
                        + ""                                                                                                                          + "\n"
                        + "        telemetry.addData(\"init\", \"done\");"                                                                            + "\n"
                        + "        telemetry.update();"                                                                                               + "\n"
                        + ""                                                                                                                          + "\n"
                        + "        waitForStart();"                                                                                                   + "\n"
                        + ""                                                                                                                          + "\n"
                        + "        //loop through the logs as long as op mode is active or untill we hit the end"                                     + "\n"
                        + "        double replayingStartTime = System.nanoTime();"                                                                    + "\n"
                        + "        for (int i = 0; opModeIsActive() && i < data[0].size(); ++i) {"                                                    + "\n"
                        + "            while ((double) data[0].get(i) > System.nanoTime() - replayingStartTime) {} //wait till next log"              + "\n"
                        + ""                                                                                                                          + "\n"
                        + "            //write data to devices"                                                                                       + "\n"
                        + "            for (int i2 = 0; i2 < motors.size(); ++i2) motors[i2].setPower((double) data[i2                 + 2].get(i));" + "\n"
                        + "            for (int i2 = 0; i2 < servos.size(); ++i2) servos[i2].setPower((double) data[i2 + motors.size() + 2].get(i));" + "\n"
                        + ""                                                                                                                          + "\n"
                        + "            //write to telemetry and increment targel log # (i)"                                                           + "\n"
                        + "            telemetry.addData(\"datapoint, i++ + \"/\" + data[0].size());"                                                 + "\n"
                        + "            telemety.update();"                                                                                            + "\n"
                        + "        }"                                                                                                                 + "\n"
                        + "    }"                                                                                                                     + "\n"
                        + "}"
        ;

        return out;
    }

    boolean isInitialised = false;

    /*
        Format of data:
        0: nanosecond since opmode start (long)
        1: voltage                       (double)
        2: motor powers                  (double)
        3: servo positions               (int)
    */
    ArrayList[] data;

    ArrayList<DcMotor> motors;
    ArrayList<Servo> servos;
    VoltageSensor voltageSensor;

    ArrayList<String> motorNames;
    ArrayList<String> servoNames;

    String currentFile;
    public static final String DIRPATH = "";

    long recordingStartTime;
}
