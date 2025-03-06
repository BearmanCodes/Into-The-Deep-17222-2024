package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.concurrent.TimeUnit;

public class MeepMeepTesting {

    public static Pose2d startPose = new Pose2d(-16.5, 72, Math.toRadians(270));
    //static Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));



    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(56.855292234154874, 56.855292234154874, Math.toRadians(183.88477629466686), Math.toRadians(188.84453843478258), 17.24)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .addTemporalMarker(0.1, () -> {
                        })
                        .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(0))
                        .addTemporalMarker(1.4, () -> {
                        })
                        .addTemporalMarker(1.5, () -> {
                        })
                        .addTemporalMarker(2, () -> {
                        })
                        .waitSeconds(.75)
                        //move the arm ALL the way up here, then let it out and move back
                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(-38.2, 46), Math.toRadians(270))
                        //-41 15
                        //.setTangent(270)
                        .splineToConstantHeading(new Vector2d(-47, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-47, 60), Math.toRadians(270))
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-55, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-55, 60), Math.toRadians(270))
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-62, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-62, 60), Math.toRadians(270))
                        //.setTangent(270)
                        //.splineToConstantHeading(new Vector2d(-62, 15), Math.toRadians(90))
                        //.splineToConstantHeading(new Vector2d(-62, 60), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                        .setTangent(0)
                        .addTemporalMarker(9.5, () -> {
                        })
                        .lineToConstantHeading(new Vector2d(-49, 69))
                        .addTemporalMarker(10.55, () -> {
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(11.55, () -> {
                        })
                        .setTangent(0)
                        .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(270))
                        .addTemporalMarker(13.5, () -> {

                        })
                        .addTemporalMarker(13.75, () -> {
                        })
                        .addTemporalMarker(14.5, () -> {
                        })
                        .waitSeconds(.8)
                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(180))
                        .setTangent(0)
                        .lineToConstantHeading(new Vector2d(-49, 69))
                        .addTemporalMarker(17.5, () -> {
                        })
                        .waitSeconds(1)
                        .addTemporalMarker(20.5, () -> {
                        })
                        //
                        .setTangent(0)
                        .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(270))
                        .addTemporalMarker(13.75, () -> {
                        })
                        .addTemporalMarker(14.5, () -> {
                        })
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}