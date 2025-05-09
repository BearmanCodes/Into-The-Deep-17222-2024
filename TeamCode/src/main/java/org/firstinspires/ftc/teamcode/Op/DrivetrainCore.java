package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;


//This is the core drivetrain class that every TeleOp uses when they need to access drivetrain features.

//NOTE, IF THERE'S A PROBLEM WITH MOTOR(S) FASTER THAN THE OTHER(S) CHECK THE ENCODER CABLE
@Config
public class DrivetrainCore{

    public static DcMotorEx frontleft;
    public static DcMotorEx frontright;
    public static DcMotorEx backleft;
    public static DcMotorEx backright; //Declare the drivetrian motors
    public static double reducer = 1; //Change for reducing drive power
    YawPitchRollAngles robotOrientation; //IMU YPR Angles
    static IMU imu; //Declare the IMU
    static IMU.Parameters imuparams; //Declare the IMU's settingsx
    TrajectorySequence trajectorySequence;
    static SampleMecanumDrive drive;
    public static Pose2d startPose = new Pose2d(-63.25, 72, Math.toRadians(270));
    public static Pose2d testPose = new Pose2d(-16.5, 71.75, Math.toRadians(270));

    public static void init(HardwareMap hwMap){
        frontleft = hwMap.get(DcMotorEx.class, "frontleft");  //change these motor names depending on the config
        frontright = hwMap.get(DcMotorEx.class, "frontright");
        backleft = hwMap.get(DcMotorEx.class, "backleft");
        backright = hwMap.get(DcMotorEx.class, "backright");
        drive = new SampleMecanumDrive(hwMap);
        drive.setPoseEstimate(testPose);

        imu = hwMap.get(IMU.class, "imu");
        imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuparams);
        imu.resetYaw(); //Reset the robot's current perceived position after initalizing the imu specifications.

        motorSetUp(); //Assigns all needed motor modes and settings out of view
    }

    public void run(Gamepad gamepad1, Telemetry tele){ //Main running function, TeleOp's will use this function.
        double Vertical = gamepad1.left_stick_y;
        double Horizontal = gamepad1.left_stick_x;
        double Pivot = gamepad1.right_stick_x;
        double denominator = Math.max(Math.abs(Vertical) + Math.abs(Horizontal) + Math.abs(Pivot), 1);

        double frontLeftPower = (-Pivot + (Vertical - Horizontal)) * reducer;
        double frontRightPower = (Pivot + Vertical + Horizontal) * reducer; //Mecanum drivetrain shenanigans
        double backRightPower = (Pivot + (Vertical - Horizontal)) * reducer;
        double backLeftPower = (-Pivot + Vertical + Horizontal) * reducer;
        //I don't understand any of this math but it allows the mecanum wheels to, do what they do.
        frontleft.setPower(frontLeftPower);
        frontright.setPower(frontRightPower);
        backleft.setPower(backLeftPower);
        backright.setPower(backRightPower);
        drive.update();
        Pose2d pose = drive.getPoseEstimate();
        tele.addData("BOT X: ", pose.getX());
        tele.addData("BOX Y: ", pose.getY());
        tele.addData("BOT HEAD: ", pose.getHeading());
        tele.update();
    }

    public void allMotorPower(double power){
        frontright.setPower(power);
        frontleft.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);
    }

    public static void motorSetUp(){ //Background work for declaring motor modes and settings
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontleft.setDirection(DcMotorSimple.Direction.FORWARD); //Change these directions for your drive
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.FORWARD);

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //below are functions of convenience that apply to all the motors in just one line of code

    public void setMotorVelocity(double fLvelocity, double fRvelocity, double bLvelocity, double bRvelocity) {
        frontleft.setVelocity(fLvelocity);
        frontright.setVelocity(fRvelocity);
        backleft.setVelocity(bLvelocity);
        backright.setVelocity(bRvelocity);
    }

    public void setMotorPower(double fLvelocity, double fRvelocity, double bLvelocity, double bRvelocity) {
        frontleft.setPower(fLvelocity);
        frontright.setPower(fRvelocity);
        backleft.setPower(bLvelocity);
        backright.setPower(bRvelocity);
    }

    public void allMotorVelocity(double velocity) {
        frontleft.setVelocity(velocity);
        frontright.setVelocity(velocity);
        backleft.setVelocity(velocity);
        backright.setVelocity(velocity);
    }

}