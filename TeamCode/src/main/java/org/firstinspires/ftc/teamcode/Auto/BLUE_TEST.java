package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Config
@Disabled
@Autonomous(name = "BLUE TEST", group = "BLUE")
public class BLUE_TEST extends LinearOpMode {
    ArmTestCore armCore = new ArmTestCore();
    DriveTestCore drivetrainCore = new DriveTestCore();
    ServoAutoCore servoCore = new ServoAutoCore();
    Action action = new Action();
    public static double initalFwd = 18.5;
    public static int armBarPos = 4500;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;
    public static double firstWaitPeriod = 0.5;
    public static double initialStrafe = 25;
    public static double intialRev = 0;
    public static double strafeBarClear = 30;
    public static double straightSpeeds = 1250;
    public static double fwdHangAlign = 25;
    public static double hangNudge = 9;
    public static int turnAmount = -90;
    public static double sampleAlign = 9;
    public static double netZonePos = 43;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();
        /*
        if (opModeIsActive()){
            action.run(new Action.Drive().
                            setDir(Action.DriveDirection.FWD)
                            //.setCore(drivetrainCore)
                            .setInches(initalFwd)
                            .setVelocity(straightSpeeds)
                            .setPeriod(0),
                    new Action.Arm()
                            .setVelocity(armVel)
                            //.setCore(armCore)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod));
            servoCore.wrist.setPosition(0.88);
            drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, 800, nudgeFwd, opModeIsActive(), 250);
            servoCore.pincer.setPosition(0.05);
            servoCore.wrist.getController().pwmDisable();
            action.run(new Action.Arm()
                    //.setCore(armCore)
                    .setVelocity(armVel)
                    .setTicks(armBackPos),
                    new Action.Drive()
                            .setDir(Action.DriveDirection.STRAFE_RIGHT)
                            //.setCore(drivetrainCore)
                            .setVelocity(1000)
                            .setInches(26)
                            .setPeriod(0.250));
            servoCore.wrist.getController().pwmEnable();
            servoCore.wrist.setPosition(0.96);
            drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, straightSpeeds, 28, opModeIsActive(), 250);
            drivetrainCore.drive(DriveTestCore.DriveDirection.STRAFE_RIGHT, 1000, 10, opModeIsActive(), 250);
            drivetrainCore.drive(DriveTestCore.DriveDirection.REV, straightSpeeds, 45, opModeIsActive(), 250);
            drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, straightSpeeds, 45, opModeIsActive(), 250);
            drivetrainCore.drive(DriveTestCore.DriveDirection.STRAFE_RIGHT, 1000, 10, opModeIsActive(), 250);
            drivetrainCore.drive(DriveTestCore.DriveDirection.REV, straightSpeeds, 45, opModeIsActive(), 250);
        }
         */


    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
