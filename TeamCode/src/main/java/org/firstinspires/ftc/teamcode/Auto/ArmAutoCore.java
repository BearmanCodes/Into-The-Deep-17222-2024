package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmAutoCore {
    public DcMotorEx fndtlArm;
    public DcMotorEx pvtArm;


    public void init(HardwareMap hwMap){
        fndtlArm = hwMap.get(DcMotorEx.class, "fndtl");
        pvtArm = hwMap.get(DcMotorEx.class, "pvt");

        fndtlArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pvtArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fndtlArm.setDirection(DcMotorSimple.Direction.REVERSE);
        pvtArm.setDirection(DcMotorSimple.Direction.REVERSE);
        fndtlArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fndtlArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void fndtlMove(double velocity, int ticks, boolean active, int timeout, Telemetry tele) throws InterruptedException {
        fndtlArm.setTargetPosition(ticks);
        fndtlArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fndtlArm.setVelocity(velocity);

        while (active && fndtlArm.isBusy()){

            tele.addData("Current Fndtl Position ", fndtlArm.getCurrentPosition());
            tele.update();
        }
        fndtlArm.setVelocity(0);

        fndtlArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



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
