package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static Pose2d startPose = new Pose2d(-24, 72, Math.toRadians(270));

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(56.855292234154874, 56.855292234154874, Math.toRadians(183.88477629466686), Math.toRadians(188.84453843478258), 17.24)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .addTemporalMarker(0.25, () ->{
                            //move arm
                        })
                        .splineTo(new Vector2d(-4.79, 38), Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-34, 42.92), Math.toRadians(270))
                        .splineToSplineHeading(new Pose2d(-45, 13, Math.toRadians(270)), Math.toRadians(180))
                        .lineToConstantHeading(new Vector2d(-45, 55))
                        .splineToConstantHeading(new Vector2d(-55.00, 13.00), Math.toRadians(180))
                        .lineToConstantHeading(new Vector2d(-55, 55))
                        .splineToConstantHeading(new Vector2d(-62.00, 13.00), Math.toRadians(180))
                        .lineToConstantHeading(new Vector2d(-62, 55))
                        .setTangent(Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-48, 50), Math.toRadians(90))
                        .lineToConstantHeading(new Vector2d(-48, 55))
                        .waitSeconds(1) //here you grab
                        .splineToSplineHeading(new Pose2d(-4.79, 53.64, Math.toRadians(90)), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(-4.79, 38.00))
                        .setTangent(90)
                        .waitSeconds(1) //here you score
                        .splineToLinearHeading(new Pose2d(-48, 50, Math.toRadians(270)), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(-48, 55))
                        .waitSeconds(1) //here you grab
                        .setTangent(270)
                        .splineToSplineHeading(new Pose2d(-4.79, 53.64, Math.toRadians(90)), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(-4.79, 38.00))
                        .setTangent(90)
                        .waitSeconds(1) //here you score
                        .splineToLinearHeading(new Pose2d(-48, 60, Math.toRadians(270)), Math.toRadians(0))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}