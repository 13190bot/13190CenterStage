package org.example;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class AmongUS {

    public static void main(String[] args) {



        MeepMeep meepMeep = new MeepMeep(900);




        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(-32, -40, Math.toRadians(90)))
                                        .splineToSplineHeading(new Pose2d(0, 38, Math.toRadians(90)), Math.toRadians(0))
                                        .splineToSplineHeading(new Pose2d(20, 0, Math.toRadians(10)), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(27, 23, Math.toRadians(90)), Math.toRadians(0))
                                        .splineToSplineHeading(new Pose2d(25, -16, Math.toRadians(90)), Math.toRadians(180))
                                        .splineToSplineHeading(new Pose2d(15, -40, Math.toRadians(0)), Math.toRadians(270))
                                        .splineToSplineHeading(new Pose2d(-5, -40, Math.toRadians(0)), Math.toRadians(0))
                                        .splineToSplineHeading(new Pose2d(-9, -22, Math.toRadians(0)), Math.toRadians(260))
                                        .splineToSplineHeading(new Pose2d(-20, -40, Math.toRadians(0)), Math.toRadians(180))
                                        .splineToSplineHeading(new Pose2d(-32, -40, Math.toRadians(0)), Math.toRadians(180))
//                                        .splineToSplineHeading(new Pose2d(27, -16, Math.toRadians(90)), Math.toRadians(180))
//                                        .splineToConstantHeading(new Vector2d(-20, 35), Math.toRadians(0))
//                                        .splineToSplineHeading(new Pose2d(35, 30, Math.toRadians(90)), Math.toRadians(0))
//                                        .splineToSplineHeading(new Pose2d(3, -34, Math.toRadians(90)), Math.toRadians(180))



//                                       // .waitSeconds(4)
//                                        .addDisplacementMarker(() -> {
//                                            //pick up pixels
//                                        })
//                                        .back(53)
//                                        .splineToConstantHeading(new Vector2d(46, 36), Math.toRadians(0))

                                        .build()
                );








        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}