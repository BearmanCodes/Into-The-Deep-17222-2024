package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name = "RR BLUE RIGHT", group = "BLUE")
public class RR_BLUE_RIGHT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    Action action = new Action();
    ServoAutoCore servoCore = new ServoAutoCore();
    SampleMecanumDrive drive;
    public static double kF = 0.0025;
    private final double TICKS_PER_REV = 537.7; //look up for gobuilda motor
    private final double GEAR_REDUCTION = 2; //2nd teeth gears divided by 1st teeth gears
    private final double TICKS_PER_GEARS = TICKS_PER_REV * GEAR_REDUCTION;
    private final double TICKS_PER_DEGREE = TICKS_PER_GEARS / 360;

    public static double initalFwd = 24;
    public static int liftArmWall = 900;
    public static double firstWaitPeriod = 125;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 4000;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 1250;
    public static double fwdHangAlign = 25;
    public static double hangNudge = 9;
    public static double nudgeFwd = 3;
    public static int armBackPos = 5;
    public static double armVel = 3000;
    public static int turnAmount = -90;
    public static double wristScore = 0.23;
    public static double sampleAlign = 9;
    public static double wristUp = 0.61; //Refine
    public static double wristVelocity = 2000; //Refine
    public static int wristInit = 10; //Refine
    public static double firstEscapeStrafe = 28;
    public static double wristGrabSpeci = 0.51;
    public static int wristTwoUp = 120;
    public static double netZonePos = 43;
    public static long standardTout = 50;
    public static double firstout = 10;
    public static int armRear = 635;
    public static double alignreverse = 20;
    public static double secondout = 20;
    public static double armwall = 15.5;
    public static double baralign = 35;
    public static double secondFwd = 20;
    public static double driveParkRev = 17;
    public static Pose2d startPose = new Pose2d(-24, 72, Math.toRadians(270));
    public ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        TrajectorySequence firstScoreTraj = drive.trajectorySequenceBuilder(new Pose2d(-24.00, 72.00, Math.toRadians(270.00)))

                /*.addTemporalMarker(0.25, () ->{
                    servoCore.wrist.setPosition(wristScore);
                    action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos));
                })
                 */
                .splineTo(new Vector2d(-4.79, 38), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34, 42.92), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-45, 13, Math.toRadians(270)), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-45, 55))
                .splineToConstantHeading(new Vector2d(-55.00, 13.00), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-55, 55))
                .splineToConstantHeading(new Vector2d(-62.00, 13.00), Math.toRadians(180))
                .lineToConstantHeading(new Vector2d(-62, 55))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-48, 50), Math.toRadians(90))
                /*
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(wristGrabSpeci);
                    action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBackPos));
                })

                 */
                .lineToConstantHeading(new Vector2d(-48, 55))
                .splineToSplineHeading(new Pose2d(-4.79, 53.64, Math.toRadians(90)), Math.toRadians(0))
                /*
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(wristScore);
                    action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos));
                })

                 */
                .lineToConstantHeading(new Vector2d(-4.79, 38.00))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-48, 50, Math.toRadians(270)), Math.toRadians(0))
                /*
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(wristGrabSpeci);
                    action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBackPos));
                })

                 */
                .lineToConstantHeading(new Vector2d(-48, 55))
                .setTangent(270)
                .splineToSplineHeading(new Pose2d(-4.79, 53.64, Math.toRadians(90)), Math.toRadians(0))
                /*
                .addDisplacementMarker(() -> {
                    servoCore.wrist.setPosition(wristScore);
                    action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos));
                })

                 */
                .lineToConstantHeading(new Vector2d(-4.79, 38.00))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-48, 60, Math.toRadians(270)), Math.toRadians(0))
                .build();
        waitForStart();
        drive.followTrajectorySequence(firstScoreTraj);
        while (!isStopRequested());
    }

    private void Init(){
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.setPoseEstimate(startPose);
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
