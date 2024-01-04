// for testing color sensors

package org.firstinspires.ftc.teamcode.TeleOp.DriveTestOpMode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "Color Sensor Testing")
public class ColorSensorTest extends LinearOpMode {
    leftColorSensor = new ColorSensor(hardwareMap, "leftSensor");
    rightColorSensor = new ColorSensor(hardwareMap, "rightSensor");
    @Override
    public void runOpMode() throws  InterruptedException {
        telemetry.addData("Initialization:", "Initialized, variables defined.");
        telemetry.update();
    }

}
