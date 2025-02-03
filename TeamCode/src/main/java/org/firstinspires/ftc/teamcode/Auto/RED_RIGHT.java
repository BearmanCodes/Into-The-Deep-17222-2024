package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "RED RIGHT", group = "BLUE")
public class RED_RIGHT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    Action action = new Action();
    ServoAutoCore servoCore = new ServoAutoCore();
    public static double kF = 0.0025;
    private final double TICKS_PER_REV = 537.7; //look up for gobuilda motor
    private final double GEAR_REDUCTION = 2; //2nd teeth gears divided by 1st teeth gears
    private final double TICKS_PER_GEARS = TICKS_PER_REV * GEAR_REDUCTION;
    private final double TICKS_PER_DEGREE = TICKS_PER_GEARS / 360;

    public static double initalFwd = 24;
    public static double firstWaitPeriod = 125;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 4000;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 1250;
    public static double fwdHangAlign = 45;
    public static double hangNudge = 10;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;
    public static int turnAmount = -90;
    public static double sampleAlign = 9;
    public static double thirdSampleAlign = 2;
    public static int wristUp = 1400; //Refine
    public static double wristVelocity = 2000; //Refine
    public static int wristInit = 10; //Refine
    public static int wristGrabSpeci = (int) (350 * (3895.9 / 537.7));
    public static int wristTwoUp = 120;
    public static double netZonePos = 43;
    public static long standardTout = 50;
    public static double firstout = 10;
    public static int armRear = 550;
    public static double alignreverse = 20;
    public static double secondout = 20;
    public static double armwall = 15.5;
    public static double baralign = 35;
    public static double secondFwd = 20;
    public ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                        setDir(Action.DriveDirection.FWD)
                        .setInches(initalFwd)
                        .setVelocity(straightSpeeds),
                new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBarPos)
                        .setPeriod(firstWaitPeriod)); //Initial driveforward with arm touching the bar
        armCore.wristMove(wristVelocity, wristUp, opModeIsActive(), 0, telemetry); //Move wrist up to get specimen clipped in
        servoCore.pincer.setPosition(servoCore.openPincer); //open up the pincer afterwards
        action.run(opModeIsActive(), new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBackPos),
                new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.STRAFE_RIGHT)
                        .setVelocity(1000)
                        .setInches(26)
                        .setPeriod(250),
                new Action.Wrist(armCore)
                        .setVelocity(wristVelocity)
                        .setTicks(wristInit)
                        .setPeriod(500)); //Move the arm back while strafing to clear the submersiable bar, move wrist to start too.
        drivetrainCore.fwdDrive(straightSpeeds, 28, opModeIsActive(), standardTout); //Move up past first sampe
        drivetrainCore.strafeRight(straightSpeeds, 9, opModeIsActive(), standardTout); //Strafe right to get right above sample
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout); //Reverse drive into the observation zone to drop off the sample
        drivetrainCore.fwdDrive(straightSpeeds, firstout, opModeIsActive(), standardTout); //Drive forward a bit so that the human player can grab it
        drivetrainCore.revDrive(straightSpeeds, alignreverse, opModeIsActive(), standardTout); //Reverse back into the wall to straighten out the bot
        drivetrainCore.fwdDrive(straightSpeeds, secondout, opModeIsActive(), standardTout); //Move forward so the human player can align the specimen to the wall
        action.run(opModeIsActive(), new Action.Wrist(armCore)
                        .setTicks(wristGrabSpeci)
                        .setVelocity(wristVelocity),
                new Action.Arm(armCore)
                        .setTicks(armRear)
                        .setVelocity(1000)); //Move the arm and wrist into specimen grabbing position
        drivetrainCore.revDrive(straightSpeeds, armwall, opModeIsActive(), 0); //Reverse drive to grab the specimen. Arm keeps up becasuse of code in the standard drives
        servoCore.pincer.setPosition(0); //Close to grab the specimen
        sleep(1000); //Wait until the servo has actually gripped it
        drivetrainCore.strafeLeft(straightSpeeds, baralign, opModeIsActive(), standardTout); //Strafe left to align with the specimen bar
        action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                        setDir(Action.DriveDirection.FWD)
                        .setInches(secondFwd)
                        .setVelocity(straightSpeeds),
                new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBarPos)
                        .setPeriod(firstWaitPeriod),
                new Action.Wrist(armCore)
                        .setTicks(20)
                        .setVelocity(wristVelocity)); //COMPLETEY CHANGE. Drive forward, arm forward, and wrist forward into position. Maybe stop and reset encoder here?
        sleep(250); //Wait a second? I'm not sure why, probably remove this
        armCore.wristMove(wristVelocity, wristUp, opModeIsActive(), 0, telemetry); //Move wrist up to get specimen clipped in
        servoCore.pincer.setPosition(servoCore.openPincer); //Open up the wrist so we can move back
        action.run(opModeIsActive(), new Action.Wrist(armCore)
                        .setTicks(20)
                        .setVelocity(wristVelocity),
                new Action.Arm(armCore)
                        .setTicks(armBackPos)
                        .setVelocity(armVel),
                new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.REV)
                        .setInches(15)
                        .setVelocity(2500)); //CHANGE AGAIN, Move wrist back into position, arm back, reverse into parking
        drivetrainCore.strafeRight(2500, 50, opModeIsActive(), 20);
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
