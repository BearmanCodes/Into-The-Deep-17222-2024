package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static Pose2d startPose = new Pose2d(-63.25, 72, Math.toRadians(270));

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(56.855292234154874, 56.855292234154874, Math.toRadians(183.88477629466686), Math.toRadians(188.84453843478258), 17.24)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                        .setTangent(0)
                        .addDisplacementMarker(() -> {
                            //open pincer and set wrist to grab
                        })
                        .lineToConstantHeading(new Vector2d(-49, 67.5))
                        .addDisplacementMarker(() -> {
                            //close pincer and move arm up
                        })
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(-3, 59))
                        .addDisplacementMarker(() -> {
                            //move arm up and open pincer
                        })
                        .waitSeconds(0.5)
                        .setTangent(180)
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                        .setTangent(0)
                        .addDisplacementMarker(() -> {
                            //open pincer and set wrist to grab also move arm back
                        })
                        .lineToConstantHeading(new Vector2d(-49, 67.5))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}