package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.concurrent.TimeUnit;

@Config
@Autonomous(name="Example Auto")
public class goToSub extends LinearOpMode {
    Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));
    public static Pose2d testPose = new Pose2d(-16.5, 71.75, Math.toRadians(270));
    public static int armVelocity = 4000;
    public static double threeSpeciPlus = 1.5;
    public static double firstWristScore = 0.75;
    public static int armBack = 5;
    public static int armBar = 3555;
    public static int armScore = 4525;
    public static double wristScore = 0.2;
    public static double wristBar = 0.65;
    public static double wristGrab = 0.65;
    ServoAutoCore servoCore = new ServoAutoCore();
    ArmAutoCore armCore = new ArmAutoCore();
    public static double waitFix = 0.5;
    public static double anotherFix = 0.35;
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
                .addTemporalMarker(0.1, () -> {
                    armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                })
                .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(0))
                .addTemporalMarker(1.4, () -> {
                    double enterTime = timer.now(TimeUnit.MILLISECONDS);
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0, 0);
                        armCore.checkArm();
                    };
                    double exitTimeMs = Math.round((timer.now(TimeUnit.MILLISECONDS) - enterTime) * 100.00) / 100.00;
                    double exitTimeSec = Math.round((exitTimeMs / 1000) * 100.00) / 100.00;

                    timeOffset += exitTimeSec;
                    dashTele.addData("Exit Time Sec", exitTimeSec);
                    dashTele.addData("Time Offset: ", timeOffset);
                    dashTele.update();
                })
                .addTemporalMarker(1.5 + timeOffset, () -> {
                    servoCore.wrist.setPosition(firstWristScore);
                })
                .addTemporalMarker(2 + timeOffset, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                    servoCore.wrist.setPosition(wristGrab);
                })
                .waitSeconds(.75 + timeOffset)
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
                .lineToConstantHeading(new Vector2d(-49, 70))
                //+3.45 seconds
                .addTemporalMarker(11.7 + timeOffset, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1.85) //13.55
                .addTemporalMarker(12.45 + timeOffset, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                    servoCore.wrist.setPosition(wristBar);
                })
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(1, 50.5), Math.toRadians(270))
                .addTemporalMarker(15.2 + timeOffset, () -> {
                    double enterTime = timer.now(TimeUnit.MILLISECONDS);
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0, 0);
                        armCore.checkArm();
                    };
                    double exitTimeMs = Math.round((timer.now(TimeUnit.MILLISECONDS) - enterTime) * 100.00) / 100.00;
                    double exitTimeSec = Math.round((exitTimeMs / 1000) * 100.00) / 100.00;

                    timeOffset += exitTimeSec;
                    dashTele.addData("Exit Time Sec", exitTimeSec);
                    dashTele.addData("Time Offset: ", timeOffset);
                    dashTele.update();
                })
                .addTemporalMarker(15.35 + timeOffset , () -> {
                    drive.setMotorPowers(0, 0, 0, 0);
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(15.75 + timeOffset, () -> {
                    drive.setMotorPowers(0, 0, 0, 0);
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(16 + timeOffset, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(wristGrab);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                //15.4
                //16.5
                .waitSeconds(1.1)
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(180))
                .setTangent(0)
                .lineToConstantHeading(new Vector2d(-49, 69))
                //19.7
                .addTemporalMarker(19.7 + timeOffset , () -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1.85 + timeOffset)
                .addTemporalMarker(20.5 + timeOffset, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                    servoCore.wrist.setPosition(wristBar);
                })
                //
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(3, 50.5), Math.toRadians(270))
                .addTemporalMarker(23.5 + timeOffset, () -> {
                    double enterTime = timer.now(TimeUnit.MILLISECONDS);
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0, 0);
                        armCore.checkArm();
                    };
                    double exitTimeMs = Math.round((timer.now(TimeUnit.MILLISECONDS) - enterTime) * 100.00) / 100.00;
                    double exitTimeSec = Math.round((exitTimeMs / 1000) * 100.00) / 100.00;

                    timeOffset += exitTimeSec;
                    dashTele.addData("Exit Time Sec", exitTimeSec);
                    dashTele.addData("Time Offset: ", timeOffset);
                    dashTele.update();
                })
                .addTemporalMarker(24 + timeOffset + waitFix + anotherFix, () -> {
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(24.5 + timeOffset + waitFix+ anotherFix, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(24.75 + timeOffset + waitFix+ anotherFix, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.upWrist);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .waitSeconds(2 + timeOffset)
                .strafeRight(72)
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
