package org.example;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MeepMeepTesting {

    public static void main(String[] args) {
        // REPLACE THE PATH WITH YOUR PATH
        String filePathBackground = "/Users/benjaminfaershtein/IdeaProjects/13190CenterStage/MeepMeepTesting/src/main/java/org/example/BackgroundImages/Juice-CENTERSTAGE-Dark.png";
        Image backgroundImage = null;

        try { backgroundImage = ImageIO.read(new File(filePathBackground)); }
        catch (IOException e) {}


        MeepMeep meepMeep = new MeepMeep(900);



        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                       drive.trajectorySequenceBuilder(new Pose2d(-60, -36, 0))
                               .splineTo(new Vector2d(-35, -43), Math.toRadians(90))
                               .forward(90)


                                .build()
                );




        meepMeep.setBackground(backgroundImage)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}