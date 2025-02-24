package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "SPECI RED LEFT", group = "SPECI")
public class RED_LEFT_SPECI extends LinearOpMode {
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
    public static double firstEscapeStrafe = 28;
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
        sleep(5000);
        drivetrainCore.strafeRight(1000, initialStrafe, opModeIsActive(), standardTout);
        action.run(opModeIsActive(), new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.FWD)
                        .setVelocity(1000)
                        .setInches(initalFwd),
                new Action.Arm(armCore)
                        .setTicks(armBarPos)
                        .setVelocity(armVel)
                        .setPeriod(450));
        armCore.wristMove(wristVelocity, wristUp, opModeIsActive(), 0, telemetry); //Move wrist up to get specimen clipped in
        servoCore.pincer.setPosition(servoCore.openPincer);
        action.run(opModeIsActive(), new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBackPos),
                new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.REV)
                        .setVelocity(1000)
                        .setInches(initalFwd-4)
                        .setPeriod(250),
                new Action.Wrist(armCore)
                        .setVelocity(wristVelocity)
                        .setTicks(wristInit)
                        .setPeriod(500));
            /*
            action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setTicks(armBackPos)
                            .setVelocity(armVel),
                    new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_LEFT)
                            .setVelocity(1000)
                            .setInches(strafeBarClear)
                            .setPeriod(250),
                    new Action.Wrist(armCore)
                            .setVelocity(wristVelocity)
                            .setTicks(wristInit)
                            .setPeriod(500));
             */
        drivetrainCore.strafeLeft(1000, firstEscapeStrafe, opModeIsActive(), standardTout);
        drivetrainCore.fwdDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
        drivetrainCore.turnAmount(turnAmount, opModeIsActive(), telemetry);
        action.run(opModeIsActive(), new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.FWD)
                        .setVelocity(straightSpeeds)
                        .setInches(hangNudge),
                new Action.Arm(armCore)
                        .setTicks(armBarPos)
                        .setVelocity(armVel)
                        .setPeriod(350));
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
