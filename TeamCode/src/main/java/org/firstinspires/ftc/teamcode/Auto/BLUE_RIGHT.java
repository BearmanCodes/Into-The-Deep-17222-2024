package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Op.ModeCore;

@Config
@Autonomous(name = "BLUE RIGHT", group = "BLUE")
public class BLUE_RIGHT extends LinearOpMode {
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
    public static int wristUp = 110;
    public static int wristTwoUp = 120;
    public static double wristVelocity = 2000;
    public static double netZonePos = 43;
    public static long standardTout = 50;
    public static int wristTol = 20;
    public static double firstout = 10;
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


        if (opModeIsActive()){
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                            setDir(Action.DriveDirection.FWD)
                            .setInches(initalFwd)
                            .setVelocity(straightSpeeds),
                    new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod));
            armCore.wristMotor.setTargetPosition((int) (110 * (3895.9 / 537.7)));
            armCore.wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armCore.wristMotor.setVelocity(wristVelocity);
            int wristErr = Math.abs(armCore.wristMotor.getCurrentPosition() - (int) (110 * (3895.9 / 537.7)));
            while (wristErr >= wristTol){
                telemetry.addData("wristErr: ", wristErr);
                telemetry.addData("wrist Pos: ", armCore.wristMotor.getCurrentPosition());
                telemetry.update();
                wristErr = Math.abs(armCore.wristMotor.getCurrentPosition() - (int) (110 * (3895.9 / 537.7)));
            }
            armCore.wristMotor.setVelocity(0);
            armCore.wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            drivetrainCore.fwdDrive(straightSpeeds, 28, opModeIsActive(), standardTout);
            drivetrainCore.strafeRight(straightSpeeds, 9, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, firstout, opModeIsActive(), standardTout);
            drivetrainCore.revDrive(straightSpeeds, alignreverse, opModeIsActive(), standardTout);
            drivetrainCore.fwdDrive(straightSpeeds, secondout, opModeIsActive(), standardTout);
            action.run(opModeIsActive(), new Action.Wrist(armCore)
                    .setTicks((int) (350 * (3895.9 / 537.7)))
                    .setVelocity(500),
                    new Action.Arm(armCore)
                            .setTicks(700)
                            .setVelocity(1000));
            drivetrainCore.revDrive(straightSpeeds, armwall, opModeIsActive(), 0);
            servoCore.pincer.setPosition(0);
            sleep(1000);
            drivetrainCore.strafeLeft(straightSpeeds, baralign, opModeIsActive(), standardTout);
            action.run(opModeIsActive(), new Action.Drive(drivetrainCore).
                            setDir(Action.DriveDirection.FWD)
                            .setInches(secondFwd)
                            .setVelocity(straightSpeeds),
                    new Action.Arm(armCore)
                            .setVelocity(armVel)
                            .setTicks(armBarPos)
                            .setPeriod(firstWaitPeriod),
                    new Action.Wrist(armCore)
                            .setTicks((int) (20 * (3895.9 / 537.7)))
                            .setVelocity(500));
            sleep(1000);
            armCore.wristMotor.setTargetPosition((int) (120 * (3895.9 / 537.7)));
            armCore.wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armCore.wristMotor.setVelocity(wristVelocity);
            wristErr = Math.abs(armCore.wristMotor.getCurrentPosition() - (int) (120 * (3895.9 / 537.7)));
            while (wristErr >= wristTol){
                telemetry.addData("wristErr: ", wristErr);
                telemetry.addData("wrist Pos: ", armCore.wristMotor.getCurrentPosition());
                telemetry.update();
                wristErr = Math.abs(armCore.wristMotor.getCurrentPosition() - (int) (120 * (3895.9 / 537.7)));
            }
            armCore.wristMotor.setVelocity(0);
            armCore.wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            servoCore.pincer.setPosition(servoCore.openPincer);
            action.run(opModeIsActive(), new Action.Wrist(armCore)
                    .setTicks((int) (20 * (3895.9 / 537.7)))
                    .setVelocity(2000),
                    new Action.Arm(armCore)
                            .setTicks(armBackPos)
                            .setVelocity(armVel), new Action.Drive(drivetrainCore)
                            .setDir(Action.DriveDirection.REV)
                            .setInches(20)
                            .setVelocity(2500));
            drivetrainCore.strafeRight(2500, 40, opModeIsActive(), 20);
            //drivetrainCore.strafeRight(1000, 9, opModeIsActive(), standardTout);
            //drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            //drivetrainCore.fwdDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
            //drivetrainCore.strafeRight(1000, 7, opModeIsActive(), standardTout);
            //drivetrainCore.revDrive(straightSpeeds, 45, opModeIsActive(), standardTout);
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
