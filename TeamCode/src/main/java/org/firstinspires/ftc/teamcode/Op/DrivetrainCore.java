package org.firstinspires.ftc.teamcode.Op;

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


//This is the core drivetrain class that every TeleOp uses when they need to access drivetrain features.

//NOTE, IF THERE'S A PROBLEM WITH MOTOR(S) FASTER THAN THE OTHER(S) CHECK THE ENCODER CABLE
public class DrivetrainCore{

    public DcMotorEx frontleft, frontright, backleft, backright, tetrix; //Declare the drivetrian motors
    public double reducer = /*0.85*/1; //Change for reducing drive power

    YawPitchRollAngles robotOrientation; //IMU YPR Angles

    IMU imu; //Declare the IMU
    IMU.Parameters imuparams; //Declare the IMU's settings

    public void init(HardwareMap hwMap){
        frontleft = hwMap.get(DcMotorEx.class, "frontleft");  //change these motor names depending on the config
        frontright = hwMap.get(DcMotorEx.class, "frontright");
        backleft = hwMap.get(DcMotorEx.class, "backleft");
        backright = hwMap.get(DcMotorEx.class, "backright");
        tetrix = hwMap.get(DcMotorEx.class, "tetrix");

        imu = hwMap.get(IMU.class, "imu");
        imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuparams);
        imu.resetYaw(); //Reset the robot's current perceived position after initalizing the imu specifications.

        motorSetUp(); //Assigns all needed motor modes and settings out of view

    }

    public void run(Gamepad gamepad1){ //Main running function, TeleOp's will use this function.
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
    }

    public void tPully(Gamepad gamepad1){
        double tetrixPower = (gamepad1.right_trigger - gamepad1.left_trigger) * 0.75;
        tetrix.setPower(tetrixPower);
    }

    public void allMotorPower(double power){
        frontright.setPower(power);
        frontleft.setPower(power);
        backleft.setPower(power);
        backright.setPower(power);
    }

    public void motorSetUp(){ //Background work for declaring motor modes and settings
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tetrix.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE); //Change these directions for your drive
        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        backleft.setDirection(DcMotorSimple.Direction.FORWARD);

        tetrix.setDirection(DcMotorSimple.Direction.FORWARD);

        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        tetrix.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tetrix.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
