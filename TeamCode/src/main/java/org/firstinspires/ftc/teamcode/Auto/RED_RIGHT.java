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
    Action action = new Action();
    public static double initalFwd = 18.5;
    public static double initialStrafe = 10;
    public static int armBarPos = 4500;
    public static double strafeBarClear = 26;
    public static double straightSpeeds = 1250;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;
    public static long standardTout = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        drivetrainCore.fwdDrive(800, initalFwd, opModeIsActive(), standardTout);
        action.run(new Action.Drive(drivetrainCore).
                        setDir(Action.DriveDirection.STRAFE_LEFT)
                        .setInches(initialStrafe)
                        .setVelocity(1000)
                        .setPeriod(0),
                new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBarPos)
                        .setPeriod(0.250));
        servoCore.wrist.setPosition(0.88);
        drivetrainCore.fwdDrive(800, nudgeFwd, opModeIsActive(), standardTout);
        servoCore.pincer.setPosition(0.05);
        servoCore.wrist.getController().pwmDisable();
        sleep(100);
        action.run(new Action.Arm(armCore)
                        .setVelocity(armVel)
                        .setTicks(armBackPos),
                new Action.Drive(drivetrainCore)
                        .setDir(Action.DriveDirection.STRAFE_RIGHT)
                        .setVelocity(1000)
                        .setInches(strafeBarClear));
        servoCore.wrist.getController().pwmEnable();
        servoCore.wrist.setPosition(0.96);
        drivetrainCore.fwdDrive(straightSpeeds, 28, opModeIsActive(), standardTout);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), standardTout);
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), standardTout);
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        drivetrainCore.strafeRight(1000, 7, opModeIsActive(), standardTout);
        drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
