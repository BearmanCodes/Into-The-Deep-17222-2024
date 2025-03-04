package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Op.ModeCore;

@Config
@Autonomous(name = "BLUE RIGHT", group = "BLUE")
public class BLUE_RIGHT extends LinearOpMode {
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
    public static int liftArmWall = 900;
    public static double firstWaitPeriod = 125;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 4000;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 2000;
    public static double strafeSpeeds = 1250;
    public static double fwdHangAlign = 25;
    public static double hangNudge = 9;
    public static double nudgeFwd = 3;
    public static int armBackPos = 5;
    public static double armVel = 4000;
    public static int turnAmount = -90;
    public static double sampleAlign = 9;
    public static int wristUp = 1400; //Refine
    public static double normWrist = 0.6;
    public static double wristVelocity = 2000; //Refine
    public static int wristInit = 10; //Refine
    public static double firstEscapeStrafe = 28;
    public static int wristGrabSpeci = (int) (350 * (3895.9 / 537.7));
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
    public ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();


        if (opModeIsActive()){
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                            setDir(Action.DriveDirection.FWD)
                            .setInches(initalFwd)
                            .setVelocity(straightSpeeds),
                    new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod)); //Initial driveforward with arm touching the bar
            servoCore.wrist.setPosition(normWrist);
            servoCore.pincer.setPosition(servoCore.pincerOpen); //open up the pincer afterwards
            action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBackPos),
                    new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_RIGHT)
                            .setVelocity(strafeSpeeds)
                            .setInches(firstEscapeStrafe)
                            .setPeriod(250));
            servoCore.wrist.setPosition(servoCore.upWrist);
            drivetrainCore.fwdDrive(straightSpeeds, 28, opModeIsActive(), standardTout); //Move up past first sampe
            drivetrainCore.strafeRight(straightSpeeds, 12, opModeIsActive(), standardTout); //Strafe right to get right above sample
            drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout); //Reverse drive into the observation zone to drop off the sample
            drivetrainCore.fwdDrive(straightSpeeds, firstout, opModeIsActive(), standardTout); //Drive forward a bit so that the human player can grab it
            drivetrainCore.revDrive(straightSpeeds, alignreverse, opModeIsActive(), standardTout); //Reverse back into the wall to straighten out the bot
            drivetrainCore.fwdDrive(straightSpeeds, secondout, opModeIsActive(), standardTout); //Move forward so the human player can align the specimen to the wall
            servoCore.pincer.setPosition(servoCore.pincerOpen);
            servoCore.wrist.setPosition(normWrist);
            drivetrainCore.revDrive(straightSpeeds, armwall, opModeIsActive(), 0); //Reverse drive to grab the specimen. Arm keeps up becasuse of code in the standard drives
            servoCore.pincer.setPosition(0); //Close to grab the specimen
            sleep(1000); //Wait until the servo has actually gripped it
            armCore.pvtMove(500, liftArmWall, opModeIsActive(),  telemetry);
            drivetrainCore.fwdDrive(1250, 1, opModeIsActive(), 0);
            drivetrainCore.strafeLeft(straightSpeeds, baralign, opModeIsActive(), standardTout); //Strafe left to align with the specimen bar
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                            setDir(Action.DriveDirection.FWD)
                            .setInches(secondFwd)
                            .setVelocity(straightSpeeds),
                    new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod));
            servoCore.pincer.setPosition(servoCore.pincerOpen); //Open up the wrist so we can move back
            action.run(opModeIsActive(),
                    new Action.Arm(armCore)
                            .setTicks(armBackPos)
                            .setVelocity(armVel),
                    new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.REV)
                            .setInches(driveParkRev)
                            .setVelocity(2500)); //CHANGE AGAIN, Move wrist back into position, arm back, reverse into parking
            drivetrainCore.strafeRight(2500, 50, opModeIsActive(), 20); //Strafe right into the parking zone
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
