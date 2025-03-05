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
                        .addTemporalMarker(0.25, () -> {
                            //armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                        })
                        .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(0))
                        .addDisplacementMarker(() -> {
                            //while (ArmAutoCore.running){
                             //   drive.setMotorPowers(0, 0, 0, 0);
                                //armCore.checkArm();
                           // };
                        })
                        .addTemporalMarker(2, () -> {
                            //servoCore.wrist.setPosition(0.85);
                        })
                        .addTemporalMarker(2.5, () -> {
                            //servoCore.pincer.setPosition(servoCore.pincerOpen);
                            //armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                        })
                        .waitSeconds(1)
                        //move the arm ALL the way up here, then let it out and move back
                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(-43, 56), Math.toRadians(270))
                        //-41 15
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-48, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-48, 60), Math.toRadians(270))
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-55, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-55, 60), Math.toRadians(270))
                        .setTangent(270)
                        .splineToConstantHeading(new Vector2d(-62, 15), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(-62, 60), Math.toRadians(90))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}