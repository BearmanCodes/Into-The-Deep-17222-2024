package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.concurrent.TimeUnit;

public class MeepMeepTesting {

    public static Pose2d startPose = new Pose2d(16.5, 71.75, Math.toRadians(270));
    //static Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));



    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(56.855292234154874, 56.855292234154874, Math.toRadians(183.88477629466686), Math.toRadians(188.84453843478258), 17.24)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .waitSeconds(5)
                        .addTemporalMarker(0.1, () -> {
                        })
                        .splineToConstantHeading(new Vector2d(-1, 49), Math.toRadians(270))
                        .addTemporalMarker(1.4, () -> {
                        })
                        .addTemporalMarker(1.5 , () -> {
                        })
                        .addTemporalMarker(2 , () -> {
                        })
                        .waitSeconds(1.45)
                        //move the arm ALL the way up here, then let it out and move back
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(55,55), Math.toRadians(90))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}