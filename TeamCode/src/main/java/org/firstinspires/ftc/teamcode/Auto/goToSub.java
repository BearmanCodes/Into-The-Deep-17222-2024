package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name="Example Auto")
public class goToSub extends LinearOpMode {
    Pose2d startPose = new Pose2d(-24, 72, Math.toRadians(270));
    CRServo leftSuck;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPoseEstimate(startPose);
        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(-4.79, 55), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34, 42.92), Math.toRadians(270))
                .setTangent(180)
                .splineToSplineHeading(new Pose2d(-45, 13, Math.toRadians(270)), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-48, 55))
                .splineToConstantHeading(new Vector2d(-55.00, 13.00), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-58, 55))
                .splineToConstantHeading(new Vector2d(-62.00, 13.00), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-65, 55))
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
                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory0);
        while (!isStopRequested());
    }
}
