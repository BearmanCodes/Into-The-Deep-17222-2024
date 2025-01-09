package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "RED RIGHT", group = "BLUE")
public class RED_RIGHT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();
    public static double initalFwd = 18.5;
    public static double initialStrafe = 10;
    public static int armBarPos = 4500;
    public static double strafeBarClear = 26;
    public static double straightSpeeds = 1250;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        drivetrainCore.fwdDrive(800, initalFwd, opModeIsActive(), 250);
        drivetrainCore.strafeLeft(800, initialStrafe, opModeIsActive(), 250);
        armCore.pvtMove(armVel, armBarPos, opModeIsActive(), 250, telemetry);
        servoCore.wrist.setPosition(0.88);
        drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), 250);
        servoCore.pincer.setPosition(0.05);
        servoCore.wrist.getController().pwmDisable();
        sleep(100);
        armCore.pvtMove(armVel, armBackPos, opModeIsActive(), 250, telemetry);
        drivetrainCore.revDrive(1000, 9, opModeIsActive(), 250);
        servoCore.wrist.getController().pwmEnable();
        servoCore.wrist.setPosition(0.96);
        drivetrainCore.strafeRight(1000, strafeBarClear, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(straightSpeeds, 37, opModeIsActive(), 250);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), 250);
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), 250);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), 250);
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), 250);


    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
