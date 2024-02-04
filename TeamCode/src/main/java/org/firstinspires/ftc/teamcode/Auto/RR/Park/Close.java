package org.firstinspires.ftc.teamcode.Auto.RR.Park;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Auto.BaseAuto;

@Autonomous(name = "Park Close", group = "Park")
public class Close extends BaseAuto {
    @Override
    public void overrideMe() {
        telemetry = new MultipleTelemetry(telemetry);

        drive.followTrajectorySequence(
                drive.trajectorySequenceBuilder(new Pose2d())
                        .back(2 * TILE_SIZE - 4)
                        .build()
        );
    }
}
