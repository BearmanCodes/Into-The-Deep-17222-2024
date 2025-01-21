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
    public static long standardTout = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();
        if (opModeIsActive()){
            drivetrainCore.fwdDrive(800, initalFwd, opModeIsActive(), standardTout);
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_RIGHT)
                            .setVelocity(1000)
                            .setInches(initialStrafe),
                    new Action.Arm(armCore)
                            .setTicks(armBarPos)
                            .setVelocity(armVel)
                            .setPeriod(450));
            servoCore.wrist.setPosition(0.79);
            drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), standardTout);
            servoCore.pincer.setPosition(0.05);
            servoCore.wrist.getController().pwmDisable();
            action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setTicks(armBackPos)
                            .setVelocity(armVel)
                            .setPeriod(0),
                    new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_LEFT)
                            .setVelocity(1000)
                            .setInches(strafeBarClear)
                            .setPeriod(250));
            servoCore.wrist.getController().pwmEnable();
            servoCore.wrist.setPosition(0.88);
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
