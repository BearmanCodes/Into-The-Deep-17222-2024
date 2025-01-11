package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Config
@Autonomous(name = "BLUE TEST", group = "BLUE")
public class BLUE_TEST extends LinearOpMode {
    ArmTestCore armCore = new ArmTestCore();
    DriveTestCore drivetrainCore = new DriveTestCore();
    ServoAutoCore servoCore = new ServoAutoCore();
    Action action = new Action();
    public static double initalFwd = 19.5;
    public static int armBarPos = 4500;
    public static double nudgeFwd = 3;
    public static int armBackPos = 25;
    public static double armVel = 3000;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();
        if (opModeIsActive()){
            action.run(new Action.Drive().
                            setDir(Action.DriveDirection.FWD)
                            .setInches(initalFwd)
                            .setVelocity(800)
                            .setPeriod(0.5),
                    new Action.Arm()
                            .setVelocity(800)
                            .setTicks(armBarPos)
                            .setPeriod(0));
        }

        drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, 800, initalFwd, opModeIsActive(), 250);
        armCore.pvtMove(armVel, armBarPos, opModeIsActive(), 250, telemetry);
        //Ability to start an action
        //Ability to wait a certain period of time after an action to start another one
        //Ability for one action to end before another and keep going until they're all done
        //Class stuff?
        servoCore.wrist.setPosition(0.88);
        drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, 800, nudgeFwd, opModeIsActive(), 250);
        servoCore.pincer.setPosition(0.05);
        servoCore.wrist.getController().pwmDisable();
        sleep(100);
        armCore.pvtMove(armVel, armBackPos, opModeIsActive(), 250, telemetry);
        drivetrainCore.drive(DriveTestCore.DriveDirection.REV, 1000, 9, opModeIsActive(), 250);
        servoCore.wrist.getController().pwmEnable();
        servoCore.wrist.setPosition(0.96);
        drivetrainCore.drive(DriveTestCore.DriveDirection.STRAFE_RIGHT, 1000, 26, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, 1000, 37, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.STRAFE_RIGHT, 1000, 10, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.REV, 1000, 45, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.FWD, 1000, 45, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.STRAFE_RIGHT, 1000, 10, opModeIsActive(), 250);
        drivetrainCore.drive(DriveTestCore.DriveDirection.REV, 1000, 45, opModeIsActive(), 250);
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
