package org.firstinspires.ftc.teamcode.Auto.winterBreakAutos;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoImpl;
import org.firstinspires.ftc.teamcode.Localisation.*;

import static org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.MainOpMode.*;

@Autonomous()
public class RedManual extends LinearOpMode {
    CompositeLocalizer localizer;
    int spike; //0 for no turn, 1 for turn 90, 2 for turn 180

    DcMotor lf, lb, rf, rb, intake;
    ServoImpl claw, arm, pitch;
    BNO055IMUImpl cHubImu, eHubImu;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry);

        lf = hardwareMap.dcMotor.get("frontLeft");
        lb = hardwareMap.dcMotor.get("backLeft");
        rf = hardwareMap.dcMotor.get("frontRight");
        rb = hardwareMap.dcMotor.get("backRight");

        intake = hardwareMap.dcMotor.get("intakeMotor");

        claw = hardwareMap.get(ServoImpl.class, "claw");
        arm = hardwareMap.get(ServoImpl.class, "arm");
        pitch = hardwareMap.get(ServoImpl.class, "pitch");


        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        MyLocalizer[] localizers = {
//                new IMULocaliser(
//                        //
//                ),
//
//                new AprilTagLocalizer(
//                        //
//                ),
//
//                new OdoLocalizer(
//                        //
//                )
        };
        localizer = new CompositeLocalizer(new Pose2d(STARTING_X, STARTING_Y, HEADING), localizers);

        //setup camera

        //get spike
        while (!isStarted()) {
            //
        }

        //while the absolute value of the difference between (the heading) and (the starting heading plus (90 degrees times target spike index)) is greater than the heading tolerance:
        //turn towards spike
        lf.setPower(DRIVE_POWER);
        lb.setPower(DRIVE_POWER);
        rf.setPower(-DRIVE_POWER);
        rb.setPower(-DRIVE_POWER);
        while (Math.abs( localizer.getPoseEstimate().getHeading() - (HEADING + (spike * Math.toRadians(90))) ) > HEADING_TOLERANCE) {
            localizer.update();
        }

        //stop
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);

        //deposit pixel
        intake.setPower(-.2);
        sleep(1000);
        intake.setPower(0);

        //while the absolute value of the difference between the current heading and the starting heading is greater than the heading tolerance:
        //turn towards starting heading
        lf.setPower(-DRIVE_POWER);
        lb.setPower(-DRIVE_POWER);
        rf.setPower(DRIVE_POWER);
        rb.setPower(DRIVE_POWER);
        while (Math.abs( localizer.getPoseEstimate().getHeading() - HEADING) > HEADING_TOLERANCE) {
            localizer.update();
        }

        //while the y position is greater than position of the robot ot score on backboard (negative) plus the y tolerance:
        //move backwards (towards backdrop)
        lf.setPower(-DRIVE_POWER);
        lb.setPower(-DRIVE_POWER);
        rf.setPower(-DRIVE_POWER);
        rb.setPower(-DRIVE_POWER);
        while (localizer.getPoseEstimate().getY() > (BACKBOARD_Y + Y_TOLERANCE)) {
            localizer.update();
        }

        //while the absolute value of the x position is less than (the absolute value of the width of a backboard section * (1 - spike index){aka +- 1 if not center, 0 if center]) - the the x tolerance:
        //strafe in the spike direction
        lf.setPower(-DRIVE_POWER * (spike-1));
        lb.setPower( DRIVE_POWER * (spike-1));
        rf.setPower( DRIVE_POWER * (spike-1));
        rb.setPower(-DRIVE_POWER * (spike-1));
        while ( Math.abs(localizer.getPoseEstimate().getX()) < Math.abs((BACKBOARD_SECT_WIDTH) * (spike-1)) - X_TOLERANCE ) {
            localizer.update();
        }

        //stop
        lf.setPower(0);
        lb.setPower(0);
        rf.setPower(0);
        rb.setPower(0);

        //grab pixel
        arm.setPosition(armMax);
        sleep(500);
        pitch.setPosition(pitchMax);
        sleep(100);

        //deposit pixel
        claw.setPosition(clawClosed); // Close claw
        sleep(250);
        arm.setPosition(0.66);
        sleep(250);
        pitch.setPosition(0.168);

        arm.setPosition(armMin); // Slightly above pixel bottom

        claw.setPosition(clawOpen); // Open claw
        sleep(100);

        // Shake it
        pitch.setPosition(pitch.getPosition() + 0.1);
        sleep(100);
        pitch.setPosition(pitch.getPosition() - 0.1);
        sleep(600);

        // Move arm back
        arm.setPosition(0.6);

        //while the x position is less than the position in which the robot may park - the x tolerance:
        //strafe away form the drivers
        lf.setPower(-DRIVE_POWER);
        lb.setPower( DRIVE_POWER);
        rf.setPower( DRIVE_POWER);
        rb.setPower(-DRIVE_POWER);
        while (localizer.getPoseEstimate().getX() < (PARK_X - X_TOLERANCE)) {
            localizer.update();
        }

        //while the y position is greater than the park y + the y tolerance:
        //drive backwards
        lf.setPower(-DRIVE_POWER);
        lb.setPower(-DRIVE_POWER);
        rf.setPower(-DRIVE_POWER);
        rb.setPower(-DRIVE_POWER);
        while (localizer.getPoseEstimate().getY() > (PARK_Y + Y_TOLERANCE)) {
            localizer.update();
        }
    }

    static final double
  //Speed                  Speed
    DRIVE_POWER          = .5,

  //Tolerances             Tolerances
    HEADING_TOLERANCE    = Math.toRadians(2),
    Y_TOLERANCE          = .5,
    X_TOLERANCE          = .25,

  //Positions              Positions
    BACKBOARD_Y          = -1,
    BACKBOARD_SECT_WIDTH = -1,
    PARK_X               = -1,
    PARK_Y               = -1,

  //Start Pose             Start Pose
    STARTING_X           = -1,
    STARTING_Y           = -1,
    HEADING              = -1;
}
