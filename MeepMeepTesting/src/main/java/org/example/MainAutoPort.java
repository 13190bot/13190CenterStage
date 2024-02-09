package org.example;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MainAutoPort {

    public static void main(String[] args) {



        MeepMeep meepMeep = new MeepMeep(600);





        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 16)
                .followTrajectorySequence(drive ->
                       drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))

                               .forward(27)

                               .strafeLeft(20)
                               .splineToConstantHeading(new Vector2d(-35, -33 + 20), 270)
                               .back(20)
                               .strafeRight(20)
                               .splineToConstantHeading(new Vector2d(-35, -33 + 20), 90)
                               .back(20)

                               .turn(Math.toRadians(-90))
                               .forward(80)

//                               .splineToConstantHeading(new Vector2d(46, 35), Math.toRadians(80))
                               .build()
                );








        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}