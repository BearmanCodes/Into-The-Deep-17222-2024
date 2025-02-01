package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "RED LEFT", group = "BLUE")
public class RED_LEFT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();
    Action action = new Action();


    public static double initalFwd = 18.5;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static int armBarPos = 4500;
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

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        drivetrainCore.fwdDrive(straightSpeeds, initalFwd, opModeIsActive(), standardTout);
        action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                        setDir(Action.DriveDirection.STRAFE_RIGHT)
                        .setInches(initialStrafe)
                        .setVelocity(1000),
                new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBarPos)
                        .setPeriod(250));
        servoCore.wrist.setPosition(0.79);
        drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), standardTout);
        servoCore.pincer.setPosition(servoCore.openPincer);
        servoCore.wrist.getController().pwmDisable();
        sleep(100);
        action.run(opModeIsActive(), new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBackPos),
                new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.STRAFE_LEFT)
                        .setVelocity(1000)
                        .setInches(strafeBarClear)
                        .setPeriod(150));
        servoCore.wrist.getController().pwmEnable();
        servoCore.wrist.setPosition(0.88);
        drivetrainCore.fwdDrive(straightSpeeds, fwdHangAlign, opModeIsActive(), standardTout);
        drivetrainCore.strafeLeft(1000, sampleAlign, opModeIsActive(), standardTout);
        drivetrainCore.revDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
        drivetrainCore.fwdDrive(straightSpeeds, netZonePos, opModeIsActive(), standardTout);
        drivetrainCore.strafeLeft(1000, sampleAlign - 1, opModeIsActive(), standardTout);
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

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
