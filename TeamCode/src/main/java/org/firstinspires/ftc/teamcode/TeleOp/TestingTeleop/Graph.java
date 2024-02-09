package org.firstinspires.ftc.teamcode.TeleOp.TestingTeleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Graph")
@Config
public class Graph extends LinearOpMode {

    public static double cA = 1;
    public static double cV = 0.5;
    public static double cP = 0.1;
    public static double cT = 2;

    public static double t1 = 0;
    public static double t2 = 2;
    public static double t3 = 8;
    public static double t4 = 10;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("t", 0);
        telemetry.addData("a", 0);
        telemetry.addData("v", 0);
        telemetry.addData("p", 0);
        telemetry.update();

        waitForStart();

        sleep(1000);



        double a = 0;
        double v = 0;
        double p = 0;



        double startingT = System.nanoTime() / 1e+9;
        double lastT = 0;



        while (opModeIsActive()) {
            double t = System.nanoTime() / 1e+9 - startingT;
            double dT = (t - lastT) * cT;
            lastT = t;

            // do some stuff with a (you can also add to v and p if needed)
            if (t > t4) {
                a = 0;
                while (opModeIsActive()) {

                }
            } else if (t > t3) {
                a = -1;
            } else if (t > t2) {
                a = 0;
            } else if (t > t1) {
                a = 1;
            } else {
                a = 0;
            }



            v += a * dT;
            p += v * dT;

            telemetry.addData("t", t);
            telemetry.addData("a", a * cA);
            telemetry.addData("v", v * cV);
            telemetry.addData("p", p * cP);
            telemetry.update();
        }
    }
}