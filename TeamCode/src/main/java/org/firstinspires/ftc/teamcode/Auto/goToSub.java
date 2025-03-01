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
    Pose2d startPos = new Pose2d(24, 63.5, Math.toRadians(270));
    CRServo leftSuck;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSuck = hardwareMap.get(CRServo.class, "suckL");

        drive.setPoseEstimate(startPos);
        TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(startPos)
               .setTangent(0)
                .splineTo(new Vector2d(23.23, 5.78), Math.toRadians(180))
                //.addDisplacementMarker(2, () -> {

                  //  leftSuck.setPower(1);

                //})

                //.lineTo(new Vector2d(48,0))
                //.waitSeconds(0.5)
                //.lineTo(new Vector2d(48,63.5))

                .build();
        waitForStart();
        drive.followTrajectorySequence(trajectory0);
        while (!isStopRequested());
    }
}
