package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "BLUE LEFT", group = "BLUE")
public class BLUE_LEFT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();
    public static double initalFwd = 18.5;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 4500;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 1250;
    public static double fwdHangAlign = 25;
    public static double hangNudge = 8;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;
    public static int turnAmount = -90;
    public static double sampleAlign = 10;
    public static double netZonePos = 43;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        drivetrainCore.fwdDrive(800, initalFwd, opModeIsActive(), 250);
        drivetrainCore.strafeRight(800, initialStrafe, opModeIsActive(), 250);
        armCore.pvtMove(armVel, armBarPos, opModeIsActive(), 250, telemetry);
        servoCore.wrist.setPosition(0.88);
        drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), 250);
        servoCore.pincer.setPosition(0.05);
        servoCore.wrist.getController().pwmDisable();
        sleep(100);
        armCore.pvtMove(armVel, armBackPos, opModeIsActive(), 250, telemetry);
        drivetrainCore.revDrive(straightSpeeds, intialRev, opModeIsActive(), 250);
        servoCore.wrist.getController().pwmEnable();
        servoCore.wrist.setPosition(0.96);
        drivetrainCore.strafeLeft(1000, strafeBarClear, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(straightSpeeds, fwdHangAlign, opModeIsActive(), 250);
        drivetrainCore.strafeLeft(1000, sampleAlign, opModeIsActive(), 250);
        drivetrainCore.revDrive(straightSpeeds, netZonePos, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(straightSpeeds, netZonePos, opModeIsActive(), 250);
        drivetrainCore.turnAmount(turnAmount, opModeIsActive(), telemetry);
        drivetrainCore.fwdDrive(straightSpeeds, hangNudge + sampleAlign, opModeIsActive(), 250);
        armCore.pvtMove(armVel, armBarPos, opModeIsActive(), 250, telemetry);
        /*
        drivetrainCore.fwdDrive(800, 9, opModeIsActive(), 250);
        servoCore.claw3.setPosition(1);
        servoCore.claw4.setPosition(0.1);
        drivetrainCore.strafeRight(800, 30, opModeIsActive(), 250);
        armCore.pvtMove(500, 1750, opModeIsActive(), 250, telemetry);
        drivetrainCore.revDrive(800, 8.5, opModeIsActive(), 250);
        armCore.pvtMove(800, 0, opModeIsActive(), 250, telemetry);
        servoCore.claw1.setPosition(0.04); //(open)
        servoCore.claw2.setPosition(0.04);
        drivetrainCore.revDrive(800, 8, opModeIsActive(), 250);
         */
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
