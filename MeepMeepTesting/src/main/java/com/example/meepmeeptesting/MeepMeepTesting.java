package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static Pose2d startPose = new Pose2d(-16.5, 72, Math.toRadians(270));
    //static Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));



    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(56.855292234154874, 56.855292234154874, Math.toRadians(183.88477629466686), Math.toRadians(188.84453843478258), 17.24)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                        .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                        //move the arm up here
                        .lineToConstantHeading(new Vector2d(-3, 59))
                        //move the arm ALL the way up here, then let it out and move back
                        .splineTo(new Vector2d(-42, 19.07), Math.toRadians(270))
                        .splineToConstantHeading(new Vector2d(-45, 67.81), Math.toRadians(95.41))
                        .splineToConstantHeading(new Vector2d(-51, 12.68), Math.toRadians(259.33))
                        .splineToConstantHeading(new Vector2d(-54, 68.40), Math.toRadians(90.62))
                        .back(5)
                        .strafeRight(10)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}