package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "BLUE LEFT", group = "BLUE")
public class BLUE_LEFT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    Action action = new Action();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();

    public static double initalFwd = 18.5;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 3770;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 1250;
    public static double fwdHangAlign = 25;
    public static double hangNudge = 9;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;
    public static int turnAmount = -90;
    public static double sampleAlign = 9;
    public static double netZonePos = 43;
    public static long standardTout = 150;
    public static int wristUp = (int) (110 * (3895.9 / 537.7)); //Refine
    public static int wristInit = 10; //Refine
    public static double wristVelocity = 2000; //Refine

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();
        if (opModeIsActive()){
            drivetrainCore.fwdDrive(straightSpeeds, initalFwd, opModeIsActive(), standardTout);
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_RIGHT)
                            .setVelocity(1000)
                            .setInches(initialStrafe),
                    new Action.Arm(armCore)
                            .setTicks(armBarPos)
                            .setVelocity(armVel)
                            .setPeriod(450));
            armCore.wristMove(wristVelocity, wristUp, opModeIsActive(), 0, telemetry); //Move wrist up to get specimen clipped in
            servoCore.pincer.setPosition(servoCore.openPincer);
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
            drivetrainCore.fwdDrive(straightSpeeds, fwdHangAlign, opModeIsActive(), standardTout);
            drivetrainCore.strafeLeft(1000, sampleAlign, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
            drivetrainCore.strafeLeft(1000, sampleAlign, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
            drivetrainCore.turnAmount(turnAmount, opModeIsActive(), telemetry);
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.FWD)
                            .setVelocity(straightSpeeds)
                            .setInches(hangNudge + (sampleAlign * 2)),
                    new Action.Arm(armCore)
                            .setTicks(armBarPos)
                            .setVelocity(armVel)
                            .setPeriod(350));
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
