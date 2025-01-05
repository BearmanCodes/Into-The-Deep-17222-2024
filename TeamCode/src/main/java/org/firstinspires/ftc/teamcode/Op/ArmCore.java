package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This is the core arm class every single TeleOp uses to access functions pertaining to the arm
public class ArmCore {

    public DcMotorEx fndtlArm;
    public DcMotorEx pvtArm; //Declare the 2 arm motors, this one is the extender

    public double reducerActualArm = 0.45; //Change this depending on how much you want to reduce your arm
    public double reducerPvt = (1); //Change this depending on how much you want to reduce your arm
    public double fndtlPower;
    public double pvtPower;

    public void init(HardwareMap hwMap){
        fndtlArm = hwMap.get(DcMotorEx.class, "fndtl");
        pvtArm = hwMap.get(DcMotorEx.class, "pvt");

        fndtlArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pvtArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pvtArm.setDirection(DcMotorSimple.Direction.FORWARD);

        fndtlArm.setDirection(DcMotorSimple.Direction.REVERSE);
        fndtlArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fndtlArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2, int currPos){
        pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt); //might need something to counteract gravity
        //fndtlPower = (gamepad2.left_stick_y * reducerActualArm); //might need something to counteract gravity
        if (currPos >= 1150) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) - 0.0075;
        if (currPos >= 1650) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) - 0.0055;
        if (currPos <= 950) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) + 0.0037;
        if (currPos <= 450) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) + 0.0025;
        pvtArm.setPower(pvtPower);
        //fndtlArm.setPower(fndtlPower);
    }

    /*
    //This uses the right stick to move the arm as used in Mason S.'s op mode
    public void rStick(Gamepad gamepad2){
        if (actualArm.getCurrentPosition() >= 415) actualArmPower = ((gamepad2.right_stick_y) * reducerActualArm) - 0.00450;
        else if (actualArm.getCurrentPosition() <= 414) actualArmPower = ((gamepad2.right_stick_y) * reducerActualArm) + 0.00050;
        actualArm.setPower(actualArmPower);
    }

    //This uses the left stick to move the arm as used in Joel's op mode
    public void lStick(Gamepad gamepad2, Telemetry telemetry){
        if (actualArm.getCurrentPosition() >= 415) actualArmPower = ((gamepad2.left_stick_y) * reducerActualArm) - 0.00450;
        else if (actualArm.getCurrentPosition() <= 414) actualArmPower = ((gamepad2.left_stick_y) * reducerActualArm) + 0.00050;
        actualArm.setPower(actualArmPower);
    }

    //Main extender arm function
    public void Extender(Gamepad gamepad2){
        extenderArmPower = (gamepad2.left_trigger + -gamepad2.right_trigger);
        extender.setPower(extenderArmPower);
    }
     */
}