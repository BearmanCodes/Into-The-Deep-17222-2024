package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
//This is the core arm class every single TeleOp uses to access functions pertaining to the arm
public class ArmCore {

    public DcMotorEx fndtlArm;
    public DcMotorEx pvtArm; //Declare the 2 arm motors, this one is the extender
    public DcMotorEx hangArm;
    public static double wristReducer = 0.8;
    public static double kF = 0.0025;
    public double reducerActualArm = 0.45; //Change this depending on how much you want to reduce your arm
    public double reducerPvt = (1); //Change this depending on how much you want to reduce your arm
    public double fndtlPower;
    public double pvtPower;
    public double hangPower;
    public static boolean fwd = true;
    public double wristPower;
    public boolean hangMode = false;

    private final double TICKS_PER_REV = 3895.9; //look up for gobuilda motor
    private final double GEAR_REDUCTION = 2; //2nd teeth gears divided by 1st teeth gears
    private final double TICKS_PER_GEARS = TICKS_PER_REV * GEAR_REDUCTION;
    private final double TICKS_PER_DEGREE = TICKS_PER_GEARS / 360;


    public void init(HardwareMap hwMap){
        pvtArm = hwMap.get(DcMotorEx.class, "pvt");
        hangArm = hwMap.get(DcMotorEx.class, "hang");

        pvtArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hangArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pvtArm.setDirection(DcMotorSimple.Direction.REVERSE);

        pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //This uses the triggers to move the arm as used in Mason M.'s op mode
    public void trigger(Gamepad gamepad2, int currPos){
        pvtPower = ((gamepad2.right_trigger - gamepad2.left_trigger) * reducerPvt); //might need something to counteract gravity
        hangPower = gamepad2.right_stick_y;

        pvtArm.setPower(pvtPower);
        hangArm.setPower(hangPower);
        //fndtlArm.setPower(fndtlPower);
    }

    public void hangModeHandler(Gamepad currentGamepad1, Gamepad previousGamepad1){
        if (currentGamepad1.start && currentGamepad1.x && !previousGamepad1.start && !previousGamepad1.x){
            hangMode = !hangMode;
        }
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