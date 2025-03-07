package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.concurrent.TimeUnit;

@Config
@Autonomous(name="Left Red Auto")
public class goToLeftSubRed extends LinearOpMode {
    Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));
    public static Pose2d testPose = new Pose2d(-16.5, -71.75, Math.toRadians(90));
    public static int armVelocity = 4000;
    public static double threeSpeciPlus = 1.5;
    public static double firstWristScore = 0.45;
    public static int armBack = 5;
    public static int armBar = 3555;
    public static int armScore = 4525;
    public static double wristScore = 0.2;
    public static double wristBar = 0.6;
    public static double wristGrab = 0.6;
    ServoAutoCore servoCore = new ServoAutoCore();
    ArmAutoCore armCore = new ArmAutoCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    ElapsedTime timer = new ElapsedTime();
    double timeOffset;
    CRServo leftSuck;

    @Override
    public void runOpMode() throws InterruptedException {
        servoCore.init(hardwareMap);
        armCore.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPoseEstimate(testPose);
        timer.reset();

        TrajectorySequence firstTrajectory = drive.trajectorySequenceBuilder(testPose)
                .waitSeconds(5)
                .addTemporalMarker(5.1, () -> {
                    armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                })
                .splineToConstantHeading(new Vector2d(-3, -50), Math.toRadians(90))
                .addTemporalMarker(6.4, () -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0, 0);
                        armCore.checkArm();
                    };
                })
                .addTemporalMarker(6.5, () -> {
                    servoCore.wrist.setPosition(firstWristScore);
                })
                .addTemporalMarker(7.5, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .waitSeconds(1.45)
                //move the arm ALL the way up here, then let it out and move back
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-55, -55), Math.toRadians(270))
                .build();
                /*
                //.lineToConstantHeading(new Vector2d(-3, 59))
                .waitSeconds(1.85 + timeOffset)
                .addTemporalMarker(13.55 + timeOffset, () -> {
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(15.25 + timeOffset, () -> {
                    armCore.pvtArm.setPower(0);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .lineToConstantHeading(new Vector2d(-49, 69))
                .addTemporalMarker(18.25 +timeOffset, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1 + timeOffset)
                .addTemporalMarker(19.25, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                })
                        .build();

                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 60), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0,0);
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                //.lineToConstantHeading(new Vector2d(-3, 59))
                .addTemporalMarker(27.5, () -> {
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(29.5, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(30 , () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .build();
        */

        /*
        .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.update();
                        armCore.checkArm();
                    }
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    //open pincer and set wrist to grab also move arm back
                })
         */

        waitForStart();
        drive.followTrajectorySequenceAsync(firstTrajectory);
        while (!isStopRequested()){
            dashTele.addData("Time (ms): ", timer.milliseconds());
            dashTele.update();
            drive.update();
            armCore.checkArm();
        }
    }
}
