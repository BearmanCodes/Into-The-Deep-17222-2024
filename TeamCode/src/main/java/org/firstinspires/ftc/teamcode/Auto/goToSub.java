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
    public static int armVelocity = 4000;
    public static int armBack = 5;
    public static int armBar = 4750;
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

        drive.setPoseEstimate(startPose);
        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    //open pincer and set wrist to grab
                })
                .lineToConstantHeading(new Vector2d(-49, 67.5))
                .addDisplacementMarker(() -> {
                    servoCore.pincer.setPosition(servoCore.pincerClose);
                    armCore.pvtMove(armVelocity, armBar, opModeIsActive(), dashTele);
                })
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-3, 65), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        armCore.checkArm();
                    };
                    //wait for arm to finish in case it hasn't
                })
                .lineToConstantHeading(new Vector2d(-3, 59))
                .addDisplacementMarker(() -> {
                    armCore.pvtArm.setPower(1);
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    armCore.pvtArm.setPower(0);
                    armCore.pvtMove(armVelocity, armBack, opModeIsActive(), dashTele);
                })
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49, 55), Math.toRadians(90))
                .setTangent(0)
                .addDisplacementMarker(() -> {
                    while (ArmAutoCore.running){
                        armCore.checkArm();
                    }
                    servoCore.pincer.setPosition(servoCore.pincerOpen);
                    servoCore.wrist.setPosition(servoCore.hangWrist);
                    //open pincer and set wrist to grab also move arm back
                })
                .lineToConstantHeading(new Vector2d(-49, 67.5))
                .build();
        waitForStart();
        drive.followTrajectorySequenceAsync(trajectory0);
        while (!isStopRequested()){
            drive.update();
            armCore.checkArm();
        }
    }
}
