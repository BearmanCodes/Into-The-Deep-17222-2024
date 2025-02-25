package org.firstinspires.ftc.teamcode.Op;

import android.sax.StartElementListener;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
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
    public ColorSensor color;
    public static boolean vipDir = true;
    public static boolean vipWristDir = false;
    public static boolean suckRDir = true;
    public static boolean suckLDir = false;
    public static boolean alliance = true; //true = blue false = red
    public static boolean suckToggle = false;
    public double viperPower;
    public double vipPos;
    public double vipReducer = 0.05;
    public void init(HardwareMap hwMap){
        viper = hwMap.get(DcMotorEx.class, "viper");
        vipWrist = hwMap.get(Servo.class, "vWrist");
        suckL = hwMap.get(CRServo.class, "suckL");
        suckR = hwMap.get(CRServo.class, "suckR");
        color = hwMap.get(ColorSensor.class, "color");
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
    public void vipSuckControl(Gamepad currentGamepad1, Gamepad previousGamepad1, Telemetry tele){
        //viper, 600
        //vipwrist, .40
        double blue = color.blue();
        double red = color.red();
        double green = color.green();
        boolean bluethresh = blue >= 1000;
        boolean redthresh = red >= 1000;
        boolean greenthresh = green >= 1000;
        if (currentGamepad1.left_stick_button && !previousGamepad1.left_stick_button){
            viper.setTargetPosition(500);
            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            viper.setVelocity(50);
            while (viper.isBusy()){
                tele.addData("vipWristPos: ", vipWrist.getPosition());
                tele.update();
            }
            viper.setVelocity(0);
            viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            vipWrist.setPosition(0.4);
            while (alliance && !bluethresh){
                suckR.setPower(1);
                suckL.setPower(1);
            }
            suckR.setPower(0);
            suckL.setPower(0);
            vipWrist.setPosition(0);
            viper.setTargetPosition(0);
            viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            viper.setVelocity(100);
            while (viper.isBusy()){
                tele.addData("vipWristPos: ", vipWrist.getPosition());
                tele.update();
            }
            viper.setVelocity(0);
            viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        if (currentGamepad1.x && !previousGamepad1.x){
            suckToggle = !suckToggle;
            if (suckToggle){
                suckL.setPower(-1);
                suckR.setPower(-1);
            }
            else{
                suckL.setPower(1);
                suckR.setPower(1);
            }
        }
        if (alliance && bluethresh){
            suckL.setPower(0);
            suckR.setPower(0);
        }
        if (!alliance && bluethresh){
            suckL.setPower(-1);
            suckR.setPower(-1);
        }
        if (!alliance && redthresh){
            suckL.setPower(0);
            suckR.setPower(0);
        }
        if (alliance && redthresh){
            suckL.setPower(-1);
            suckR.setPower(-1);
        }
        if (greenthresh){
            suckL.setPower(0);
            suckR.setPower(0);
        }
        // start
        if (currentGamepad1.y && !previousGamepad1.y){
            suckL.setPower(0);
            suckR.setPower(0);
        }
        tele.addData("Blue: ", blue);
        tele.addData("Red: ", red);
        tele.addData("Green: ", green);
        //stop
    }
    // a & b move wrist, x start suck, y stop suck
}
