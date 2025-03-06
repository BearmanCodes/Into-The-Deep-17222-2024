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

@Config
@Autonomous(name="Example Auto")
public class goToSub extends LinearOpMode {
    Pose2d startPose =  new Pose2d(-63.25, 72, Math.toRadians(270));
    public static Pose2d testPose = new Pose2d(-16.5, 72, Math.toRadians(270));
    public static int armVelocity = 4000;
    public static int armBack = 5;
    public static int armBar = 3555;
    public static int armScore = 4650;
    ServoAutoCore servoCore = new ServoAutoCore();
    ArmAutoCore armCore = new ArmAutoCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    ElapsedTime timer = new ElapsedTime();
    CRServo leftSuck;

    @Override
    public void runOpMode() throws InterruptedException {
        servoCore.init(hardwareMap);
        armCore.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPoseEstimate(testPose);
        timer.reset();

        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addTemporalMarker(0.25, () -> {
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    //open pincer and set wrist to grab
                })
                .lineToConstantHeading(new Vector2d(-49, 68.5))
                .addDisplacementMarker(() -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1)
                .addTemporalMarker(1.25, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                })
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0,0);
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                .lineToConstantHeading(new Vector2d(-3, 59))
                .addTemporalMarker(3, () -> {
                    armCore.pvtArm.setPower(1);
                })
                .addTemporalMarker(3.5, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(4, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.update();
                        armCore.checkArm();
                    }
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    //open pincer and set wrist to grab also move arm back
                })
                .lineToConstantHeading(new Vector2d(-49, 67.5))
                .build();

        TrajectorySequence firstTrajectory = drive.trajectorySequenceBuilder(testPose)
                .addTemporalMarker(0.25, () -> {
                    armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                })
                .splineToConstantHeading(new Vector2d(-3, 49), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0, 0);
                        armCore.checkArm();
                    };
                })
                .addTemporalMarker(2, () -> {
                    servoCore.wrist.setPosition(0.8);
                })
                .addTemporalMarker(2.5, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .waitSeconds(1)
                //move the arm ALL the way up here, then let it out and move back
                .setTangent(90)
                .splineToConstantHeading(new Vector2d(-43, 56), Math.toRadians(270))
                //-41 15
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-47, 15), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-47, 60), Math.toRadians(270))
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-55, 15), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-55, 60), Math.toRadians(270))
                //.setTangent(270)
                //.splineToConstantHeading(new Vector2d(-62, 15), Math.toRadians(90))
                //.splineToConstantHeading(new Vector2d(-62, 60), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addTemporalMarker(11, () -> {
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    //open pincer and set wrist to grab
                })
                .lineToConstantHeading(new Vector2d(-49, 69))
                .addTemporalMarker(12.5, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1)
                .addTemporalMarker(14, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                })
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0,0);
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                .forward(5)
                //.lineToConstantHeading(new Vector2d(-3, 59))
                .addTemporalMarker(17.5, () -> {
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(19.5, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(21, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .lineToConstantHeading(new Vector2d(-49, 69))
                .addTemporalMarker(23, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .waitSeconds(1)
                .addTemporalMarker(24, () -> {
                    armCore.pvtMove(armVelocity, armScore, opModeIsActive(), dashTele);
                })
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.setMotorPowers(0, 0, 0,0);
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                .forward(5)
                //.lineToConstantHeading(new Vector2d(-3, 59))
                .addTemporalMarker(27.5, () -> {
                    armCore.pvtArm.setPower(-1);
                })
                .addTemporalMarker(29.5, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .addTemporalMarker(21, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .build();

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
