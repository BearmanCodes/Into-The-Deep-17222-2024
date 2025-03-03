package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmAutoCore {
    public DcMotorEx pvtArm;
    public DcMotorEx wristMotor;
    public static int wristTol = 20;

    public void init(HardwareMap hwMap){
        pvtArm = hwMap.get(DcMotorEx.class, "pvt");
        wristMotor = hwMap.get(DcMotorEx.class, "wristmotor");

        pvtArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pvtArm.setDirection(DcMotorSimple.Direction.REVERSE);
        pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void wristMove(double velocity, int ticks, boolean active, long timeout, Telemetry tele) throws InterruptedException{
        wristMotor.setTargetPosition(ticks);
        wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wristMotor.setVelocity(velocity);
        int wristPos = wristMotor.getCurrentPosition();
        int wristErr = Math.abs(wristPos - ticks);
        while (active && wristErr >= wristTol){
            wristPos = wristMotor.getCurrentPosition();
            tele.addData("wristErr: ", wristErr);
            tele.addData("wrist Pos: ", wristPos);
            tele.update();
            wristErr = Math.abs(wristPos - ticks);
        }
        wristMotor.setVelocity(0);
        wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Thread.sleep(timeout);
    }

    public void pvtMove(double velocity, int ticks, boolean active, long timeout, Telemetry tele) throws InterruptedException {
        pvtArm.setTargetPosition(ticks);
        pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pvtArm.setVelocity(velocity);

        tele.addData("Current Position ", pvtArm.getCurrentPosition());
        tele.update();

        while (active && pvtArm.isBusy()){
            tele.addData("Current Position ", pvtArm.getCurrentPosition());
            tele.update();
        }
        pvtArm.setVelocity(0);

        pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Thread.sleep(timeout);
    }
}
