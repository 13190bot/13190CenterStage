package org.firstinspires.ftc.teamcode.Auto.RR.PranavAuto;//package org.firstinspires.ftc.teamcode.Auto.OldAutos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CV.ColorDetectionYCRCBPipeline;
import org.firstinspires.ftc.teamcode.TeleOp.MainTeleop.BaseOpMode;
import org.firstinspires.ftc.teamcode.util.librarys.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Pranav_Auto_Close_Blue")
public class PranavAuto extends BaseOpMode {

    private OpenCvCamera camera;

    // 2 = blue, 1 = red
    private int colorInd = 2;

    private ColorDetectionYCRCBPipeline.PropPosition position;

    private MotorEx intakeMotor;

    @Override
    public void runOpMode() {
//        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(camera, 30);
        ColorDetectionYCRCBPipeline colorDetectionYCRCBPipeline = new ColorDetectionYCRCBPipeline(colorInd);

        camera.setPipeline(colorDetectionYCRCBPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280 , 720, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {

            }
        });

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory move_to_spike = drive.trajectoryBuilder(new Pose2d())
                .forward(22)
                .build();

        Trajectory move_to_prop = drive.trajectoryBuilder(new Pose2d(move_to_spike.end().getX(), move_to_spike.end().getY(), move_to_spike.end().getHeading() + Math.toRadians(180)))
                .back(-22)
                .build();



//        while (!isStarted()) {
//            position = colorDetectionYCRCBPipeline.getPosition();
//            telemetry.addData("Current Position: ", position);
//            telemetry.update();
//        }

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(move_to_spike);
        sleep(1000);
        drive.turn(Math.toRadians(180));
        sleep(1000);
        drive.followTrajectory(move_to_prop);

//        switch (position) {
//            case LEFT:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .turn(Math.toRadians(-90))
//                        .build()
//                );
//
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .back(22)
//                        .build()
//                );
//            case CENTER:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .turn(Math.toRadians(180))
//                        .build()
//                );
//
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .back(22)
//                        .build()
//                );
//            case RIGHT:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .strafeRight(11)
//                        .build()
//                );
//        }

        // deposit purple pix
//
//        switch (position) {
//            case LEFT:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .back(22)
//                        .build()
//                );
//            case CENTER:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .turn(90)
//                        .back(44)
//                        .build()
//                );
//            case RIGHT:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder(new Pose2d())
//                        .strafeLeft(22)
//                        .turn(-90)
//                        .build()
//                );
//        }

        // place yellow pix

    }
}