package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Config
@Autonomous(name = "BLUE RIGHT", group = "BLUE")
public class BLUE_RIGHT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    Action action = new Action();
    ServoAutoCore servoCore = new ServoAutoCore();

    public static double initalFwd = 19;
    public static double firstWaitPeriod = 125;
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
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                            setDir(Action.DriveDirection.FWD)
                            .setInches(initalFwd)
                            .setVelocity(straightSpeeds),
                    new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod));
            servoCore.wrist.setPosition(servoCore.hangWrist);
            drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), standardTout);
            servoCore.pincer.setPosition(servoCore.openPincer);
            servoCore.wrist.getController().pwmDisable();
            action.run(opModeIsActive(), new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBackPos),
                    new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.STRAFE_RIGHT)
                            .setVelocity(1000)
                            .setInches(26)
                            .setPeriod(250));
            servoCore.wrist.getController().pwmEnable();
            servoCore.wrist.setPosition(servoCore.upWrist);
            drivetrainCore.fwdDrive(straightSpeeds, 28, opModeIsActive(), standardTout);
            drivetrainCore.strafeRight(straightSpeeds, 10, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            drivetrainCore.strafeRight(1000, 10, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            drivetrainCore.strafeRight(1000, 7, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
