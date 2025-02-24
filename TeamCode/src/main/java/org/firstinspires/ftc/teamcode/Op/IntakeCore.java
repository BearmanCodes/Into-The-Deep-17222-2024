package org.firstinspires.ftc.teamcode.Op;

import android.sax.StartElementListener;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class IntakeCore {
    public DcMotorEx viper;
    public Servo vipWrist;
    public CRServo suckL, suckR;
    public static boolean vipDir, vipWristDir, suckLDir = true;
    public boolean suckRDir = false;
    public double viperPower;
    public double vipPos;
    public double vipReducer = 0.05;
    public void init(HardwareMap hwMap){
        viper = hwMap.get(DcMotorEx.class, "viper");
        vipWrist = hwMap.get(Servo.class, "vWrist");
        suckL = hwMap.get(CRServo.class, "suckL");
        suckR = hwMap.get(CRServo.class, "suckR");
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (vipDir) viper.setDirection(DcMotorSimple.Direction.FORWARD);
        else viper.setDirection(DcMotorSimple.Direction.REVERSE);
        if (vipWristDir) vipWrist.setDirection(Servo.Direction.FORWARD);
        else vipWrist.setDirection(Servo.Direction.REVERSE);
        if (suckLDir) suckL.setDirection(DcMotorSimple.Direction.FORWARD);
        else suckL.setDirection(DcMotorSimple.Direction.REVERSE);
        if (suckRDir) suckR.setDirection(DcMotorSimple.Direction.FORWARD);
        else suckR.setDirection(DcMotorSimple.Direction.REVERSE);

        viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipWrist.setPosition(0);
        suckR.setPower(0);
        suckL.setPower(0);
    }
    public void viperControl(Gamepad gamepad1, Telemetry tele){
        viperPower = (gamepad1.left_trigger - gamepad1.right_trigger) * vipReducer;
        vipPos = viper.getCurrentPosition();
        viper.setPower(viperPower);
        if (vipPos >= 1500){
            if (viperPower > 0){
                viper.setPower(viperPower);
            } else {
                viper.setPower(0);
            }
        }
        tele.addData("vipPower: ", viperPower);
        tele.addData("vipPos: ", viper.getCurrentPosition());
        tele.update();
    }
    public void vipWristControl(Gamepad currentGamepad1, Gamepad previousGamepad1, Telemetry tele){
        if (currentGamepad1.a && !previousGamepad1.a) {
            double currPos = Math.round(vipWrist.getPosition() * 100.00) / 100.00;
            vipWrist.setPosition(currPos + 0.01);
        }
        if (currentGamepad1.b && !previousGamepad1.b) {
            double currPos = Math.round(vipWrist.getPosition() * 100.00) / 100.00;
            vipWrist.setPosition(currPos - 0.01);
        }
        tele.addData("vipWristPos: ", vipWrist.getPosition());
        tele.update();
    }
    public void vipSuckControl(Gamepad currentGamepad1, Gamepad previousGamepad1){
        if (currentGamepad1.x && !previousGamepad1.x){
            suckL.setPower(1);
            suckR.setPower(1);
        }
        // start
        if (currentGamepad1.y && !previousGamepad1.y){
            suckL.setPower(0);
            suckR.setPower(0);
        }
        //stop
    }
    // a & b move wrist, x start suck, y stop suck
}
