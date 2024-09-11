package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This is the core arm class every single TeleOp uses to access functions pertaining to the arm
public class ArmCore {

    public DcMotorEx actualArm;
    public DcMotorEx extender; //Declare the 2 arm motors, this one is the extender
    public double reducerActualArm = /*0.4*/1; //Change this depending on how much you want to reduce your arm
    public double actualArmPower;
    public double extenderArmPower;

    public void init(HardwareMap hwMap){
        extender = hwMap.get(DcMotorEx.class, "Extend".toLowerCase()); //Servo for the Linear extender thing
        actualArm = hwMap.get(DcMotorEx.class, "left".toLowerCase()); //Change the string to name in config

        actualArm.setDirection(DcMotorSimple.Direction.REVERSE);
        actualArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        actualArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2){
        actualArmPower = ((gamepad2.right_trigger + -gamepad2.left_trigger) * reducerActualArm) - 0.00225; //the -0.0025 counteracts gravity
        actualArm.setPower(actualArmPower);
    }

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
}