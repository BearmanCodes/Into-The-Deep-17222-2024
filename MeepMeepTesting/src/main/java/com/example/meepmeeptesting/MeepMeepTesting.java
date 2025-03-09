package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.concurrent.TimeUnit;

public class MeepMeepTesting {

    public static Pose2d startPose = new Pose2d(-16.5, -71.75, Math.toRadians(90));
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
                        .addTemporalMarker(1.5 , () -> {
                        })
                        .addTemporalMarker(2 , () -> {

                        })
                        .waitSeconds(.75 )
                        //move the arm ALL the way up here, then let it out and move back
                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(-38.2, 46), Math.toRadians(270))
                        //-41 15
                        //.setTangent(270)
                        .splineToConstantHeading(new Vector2d(-40, 15), Math.toRadians(90))
                        .waitSeconds(0.25)
                        .splineToConstantHeading(new Vector2d(-40, 60), Math.toRadians(270))
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-45, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-45, 60), Math.toRadians(270))
                        //.setTangent(270)
                        //.splineToConstantHeading(new Vector2d(-62, 15), Math.toRadians(90))
                        //.splineToConstantHeading(new Vector2d(-62, 60), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                        .setTangent(0)
                        .addTemporalMarker(9.5, () -> {
                        })
                        .lineToConstantHeading(new Vector2d(-49, 70))
                        //+3.45 seconds
                        .addTemporalMarker(10.55, () -> {
                        })
                        .waitSeconds(1.85)
                        .addTemporalMarker(11.55 , () -> {

                        })
                        .setTangent(0)
                        .splineToConstantHeading(new Vector2d(1, 50.5), Math.toRadians(270))
                        .addTemporalMarker(15, () -> {

                        })
                        .addTemporalMarker(14.6, () -> {
                        })
                        .addTemporalMarker(14.8, () -> {

                        })
                        .addTemporalMarker(15  , () -> {

                        })
                        .waitSeconds(1.1)
                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(180))
                        .setTangent(0)
                        .lineToConstantHeading(new Vector2d(-49, 69))
                        .addTemporalMarker(19.5 , () -> {
                        })
                        .waitSeconds(1.85  )
                        .addTemporalMarker(20.5 , () -> {

                        })
                        //
                        .setTangent(0)
                        .splineToConstantHeading(new Vector2d(3, 50.5), Math.toRadians(270))
                        .addTemporalMarker(24, () -> {

                        })
                        .addTemporalMarker(24.15, () -> {
                        })
                        .addTemporalMarker(24.75 , () -> {
                        })
                        .addTemporalMarker(25, () -> {

                        })
                        .waitSeconds(2 )
                        .strafeRight(72)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}