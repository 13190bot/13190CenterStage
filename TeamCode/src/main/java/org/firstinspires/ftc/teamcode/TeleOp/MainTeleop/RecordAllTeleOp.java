package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.sql.Array;
import java.util.ArrayList;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//FIXME: for now just doing motors and servos
@Config(value = "Recording Teleop for motors and servos")
public class RecordAllTeleOp extends LinearOpMode {
    public static final String DIRPATH = "";
    public ststic String filename = "Recording"; // file where recording is saved to

    ArrayList<DcMotor> motors;
    ArrayList<Servo> servos;
    ArrayList<String> motorNames, servoNames;

    /*
    Format of data:
    0: nanosecond since opmode start (long)
    1: voltage                       (double)
    2: motor powers                  (double)
    3: servo positions               (int)
     */
    ArrayList[] data;

    @Override
    public void runOpMode() throws InterruptedException {
        // TeleOp code here: START
        DcMotor fl = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor fr = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor bl = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor br = hardwareMap.get(DcMotor.class, "backRight");
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        // TeleOp code here: END

        //get the devices
        motors = new ArrayList<>();
        servos = new ArrayList<>();

        motorNames = new ArrayList<>();
        servoNames = new ArrayList<>();
        try{ //dont need the names but i cant be bothered
            Field deviceMapField = HardwareMap.DeviceMapping.class.getField("map");
            deviceMapField.setAccessible(true);

            ((HashMap<String, DcMotor>)deviceMapField.get(hardwareMap.dcMotor)).forEach((k, v) -> { motorNames.add(k); motors.add(v); } );
            ((HashMap<String, Servo  >)deviceMapField.get(hardwareMap.servo  )).forEach((k, v) -> { servoNames.add(k); servos.add(v); } );
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        data = new ArrayList[motors.size() + servos.size() + 3];
        for (int i = 2            ; i < motors.size() + 2            ; ++i) data[i] = new ArrayList<Double >();
        for (int i = motors.size(); i < servos.size() + motors.size(); ++i) data[i] = new ArrayList<Integer>();

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
            // motors[0].setPower(gamepad1.left_stick_y);
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_trigger - gamepad1.left_trigger;
            double denominator = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rx), 1);
            fl.setPower((y + x + rx) / denominator);
            bl.setPower((y - x + rx) / denominator);
            fr.setPower((y - x - rx) / denominator);
            br.setPower((y + x - rx) / denominator);
            // TeleOp code here: END

//            double endTime = System.nanoTime();
//            double endVoltage = voltageSensor.getVoltage();

            data[0].add(startTime - recordingStartTime);
            data[1].add(startVoltage);

            for (int i = 0            ; i < motors.size()                ; ++i) data[i].add(motors.get(i).getPower());
            for (int i = motors.size(); i < servos.size() + motors.size(); ++i) data[i].add(servos.get(i + servos.size()).getPosition());

            // Other code
            // Stop recording
            if (gamepad1.b) {
                break;
            }
        }



//        // Replay
//
//        // Wait
//        while (!gamepad1.a) {
//            sleep(10);
//        }
//
//        double replayingStartTime = System.nanoTime();
//        int i = 0;
//
//        // Loop as fast as possible
//        /*
//        while (opModeIsActive()) {
//            double time = System.nanoTime() - replayingStartTime;
//
//            while ((double) data[0].get(i) > time) {
//                i = i + 1;
//            }
//
//            i = i - 1;
//
//            for (int i2 = 0; i2 < motors.length; i2++) {
//                motors[i2].setPower((double) data[i2 + 2].get(i));
//            }
//
//            i = i + 1;
//
//            // Other code
//            // Stop replaying
//            if (gamepad1.b) {
//                break;
//            }
//        }
//         */
//
//        // Busy looping for each ms (assuming replay loop time is less than recording loop time)
//        while (opModeIsActive()) {
//            while ((double) data[0].get(i) > System.nanoTime() - replayingStartTime) {
//                // Busy loop
//            }
//
//            for (int i2 = 0; i2 < motors.size(); i2++) {
//                motors[i2].setPower((double) data[i2 + 2].get(i));
//            }
//            for (int i2 = 0; i2 < servos.size(); ++i2) {
//               servos[i2].setPower(data[i2 + motors.size() + 2];
//            }
//
//            // Other code
//            // Stop replaying
//            if (gamepad1.b) {
//                break;
//            }
//
//            i = i + 1;
//        }

        // Save recording (copied from RecordTeleOp
        File file = new File(getFileName());
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

            telemetry.addData("file path:", file.getAbsolutePath());
            telemetry.update();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    public String getFileName () { return DIRPATH + filename + ".java"; }

    //TODO: add canmera and zoning capabillity to the dataToString
    public String dataToCode () {
        String out = "";

        out += 
        //header stuff
              "package org.firstinspires.ftc.teamcode.;" /* insert pckage here */ + "\n"
            + /* imports */ ";"                                                  + "\n"
            ; 

        //code:
            + "@Autonomous(group = \"Recorded Autos\")"                                                                 + "\n"
            + "public class " + filename + " extends LinearOpMode {"                                                    + "\n"
            + "    ArrayList<DcMotor> motors;"                                                                          + "\n"
            + "    ArrayList<Servo>   servos;"                                                                          + "\n"
            + ""                                                                                                        + "\n"
            + "    String[] motorNames = {" + motorNames.toString().substring(1, motorNames.toString().length()) + "};" + "\n"
            + "    String[] servoNames = {" + servoNames.toString().substring(1, servoNames.toString().length()) + "};" + "\n"
            + ""                                                                                                        + "\n"
            + "    ArrayList[] data  = {"                                                                               + "\n"
            ;
        
        //data:
        for (int i = 0; i < data.size-1)(; ++i) out += "        Arrays.asList(" + data[i].toString().substring(1, data.toString().length()-1) + "),\n";
        out += "        Arrays.asList(" + data[i].toString().substring(1, data.toString().length()-1) + ")\n";

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
    }
}

