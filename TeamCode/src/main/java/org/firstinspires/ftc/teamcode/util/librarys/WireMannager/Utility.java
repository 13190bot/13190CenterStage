package org.firstinspires.ftc.teamcode.util.librarys.WireMannager;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Utility {
    public static void listAllDevices(HardwareMap hardwareMap, Telemetry telemetry){
        for (HardwareDevice device : hardwareMap.getAll(HardwareDevice.class)){
            telemetry.addLine("----------------------------------");
            telemetry.addData("Device", device.getDeviceName());
            telemetry.addData("Connection Info", device.getConnectionInfo());
            telemetry.addData("Manufacturer", device.getManufacturer());
            telemetry.addLine("----------------------------------");
        }

    }

}
