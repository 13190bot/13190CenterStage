package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;

//store static values between OpModes
@Config
public class PermVals {
    public static Pose2d autoEndPos;

    public static void resetVals () {
        autoEndPos = null;
    }

    public static void printVals (Telemetry telemetry) {
        Field[] fs = PermVals.class.getDeclaredFields();

        for (Field f : fs) {
            try { telemetry.addData(f.getName(), f.get(null)); }
            catch (IllegalAccessException ignored) {}
        }
    }
}