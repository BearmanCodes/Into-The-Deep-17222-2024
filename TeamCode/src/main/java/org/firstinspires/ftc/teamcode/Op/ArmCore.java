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

        pvtArm.setDirection(DcMotorSimple.Direction.REVERSE);

        fndtlArm.setDirection(DcMotorSimple.Direction.REVERSE);
        fndtlArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fndtlArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2, int currPos){
        pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt); //might need something to counteract gravity
        //fndtlPower = (gamepad2.left_stick_y * reducerActualArm); //might need something to counteract gravity
        if (currPos >= 5700) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) - 0.0050;
        if (currPos >= 6250) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) - 0.0025;
        if (currPos >= 6800) pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt) - 0.0045;

        pvtArm.setPower(pvtPower);
        //fndtlArm.setPower(fndtlPower);
    }
}