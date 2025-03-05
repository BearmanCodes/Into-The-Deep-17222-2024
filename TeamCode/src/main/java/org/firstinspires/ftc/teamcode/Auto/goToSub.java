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
    public static int armBar = 4550;
    ServoAutoCore servoCore = new ServoAutoCore();
    ArmAutoCore armCore = new ArmAutoCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    CRServo leftSuck;

    @Override
    public void runOpMode() throws InterruptedException {
        servoCore.init(hardwareMap);
        armCore.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drive.setPoseEstimate(testPose);

        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    //open pincer and set wrist to grab
                })
                .lineToConstantHeading(new Vector2d(-49, 68.5))
                .addDisplacementMarker(() -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                })
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.update();
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                .lineToConstantHeading(new Vector2d(-3, 59))
                .addDisplacementMarker(() -> {
                    armCore.pvtArm.setPower(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
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
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        drive.update();
                        armCore.checkArm();
                    };
                })
                //move the arm up here
                .lineToConstantHeading(new Vector2d(-3, 59))
                .addDisplacementMarker(() -> {
                    armCore.pvtArm.setPower(1);
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    armCore.pvtArm.setPower(0);
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                //move the arm ALL the way up here, then let it out and move back
                .splineTo(new Vector2d(-42, 19.07), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-45, 67.81), Math.toRadians(95.41))
                .splineToConstantHeading(new Vector2d(-51, 12.68), Math.toRadians(259.33))
                .splineToConstantHeading(new Vector2d(-54, 68.40), Math.toRadians(90.62))
                .back(5)
                .strafeRight(10)
                .addDisplacementMarker(() -> {
                    drive.setPoseEstimate(startPose);
                    drive.followTrajectorySequenceAsync(trajectory0);
                })
                .build();

        waitForStart();
        drive.followTrajectorySequenceAsync(trajectory0);
        while (!isStopRequested()){
            drive.update();
            armCore.checkArm();
        }
    }
}
