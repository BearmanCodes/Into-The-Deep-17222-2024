package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmAutoCore {
    public DcMotorEx arm;
    public int armDown = 1700;
    public int armLay = 15;
    public int armBoard = 1450;

    public void init(HardwareMap hwMap){
        arm = hwMap.get(DcMotorEx.class, "left".toLowerCase()); //Change depending on config

        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void move(double velocity, int ticks, boolean active, int timeout) throws InterruptedException {
        arm.setTargetPosition(ticks);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(velocity);

        while (active && arm.isBusy()){

        }
        arm.setVelocity(0);

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Thread.sleep(timeout);
    }
}
