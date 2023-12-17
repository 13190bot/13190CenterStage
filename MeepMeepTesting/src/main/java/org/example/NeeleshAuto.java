package org.example;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class NeeleshAuto {

    public static void main(String[] args) {



        MeepMeep meepMeep = new MeepMeep(900);




        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                       drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))

                               .lineTo(new Vector2d(-35, -30))
                               .splineToConstantHeading(new Vector2d(-60, -49.4), Math.toRadians(0))
                               .splineToConstantHeading(new Vector2d(-22, -34.7), Math.toRadians(-15))
                               .splineToConstantHeading(new Vector2d(20, -35), Math.toRadians(0))
                               .splineToConstantHeading(new Vector2d(35, 35), Math.toRadians(90))

                               //.strafeRight(57)
                               //.forward(70)


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