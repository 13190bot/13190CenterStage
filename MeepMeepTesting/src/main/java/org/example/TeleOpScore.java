package org.example;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class TeleOpScore {

    public static void main(String[] args) {



        MeepMeep meepMeep = new MeepMeep(600);


        /*
        NOTES:
        1 tile is 24
         */
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)

                // TELEOP
                // Optimal scoring, blue team
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-60, -60, Math.toRadians(225)))
                                // GO TO BACKBOARD

                                // Reverse bot, don't waste time rotating
                                .setReversed(true) // https://learnroadrunner.com/trajectory-sequence.html#setreversed-boolean

                                .splineToSplineHeading(new Pose2d(-36 - 7, -36 - 7, Math.toRadians(180)), Math.toRadians(45))
                                .splineToConstantHeading(new Vector2d(-12 - 10, -36), Math.toRadians(0))


                                // Option 1
//                                .splineToConstantHeading(new Vector2d(-12, -36 + 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12, 36 - 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12 + 10, 36), Math.toRadians(0))
//                                .splineToConstantHeading(new Vector2d(36, 36), Math.toRadians(0))

                                // Option 2
//                                .splineToConstantHeading(new Vector2d(-12, -36 + 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12, 12 - 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12 + 10, 12), Math.toRadians(0))
//                                .splineToConstantHeading(new Vector2d(36, 36), Math.toRadians(0))

                                // Option 3
//                                .splineToConstantHeading(new Vector2d(-12, -36 + 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12, -12 - 10), Math.toRadians(90))
//                                .splineToConstantHeading(new Vector2d(-12 + 10, -12), Math.toRadians(0))
//                                .splineToConstantHeading(new Vector2d(36, 36), Math.toRadians(0))

                                // Option 4 (RELIABLE, OPTIMAL)
                                .splineToConstantHeading(new Vector2d(12 - 15, -36), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(36, 36), Math.toRadians(45))



                                // GO TO HUMAN (just reverse this)


                                .build()
                );

                // AUTONOMOUS
//                .followTrajectorySequence(drive ->
//                       drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
//                               .splineTo(new Vector2d(-35, -43), Math.toRadians(90))
//                               .forward(90)
//
//
//                                .build()
//                );




        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}