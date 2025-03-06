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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class IntakeCore {
    public DcMotorEx viper;
    public Servo vipWrist;
    public CRServo suckL, suckR;
    public ColorSensor color;
    public static boolean vipDir = false;
    public static boolean vipWristDir = false;
    public static boolean suckRDir = true;
    public static boolean suckLDir = false;
    public static boolean alliance = true; //true = blue false = red
    public static boolean suckToggle = false;
    public static boolean timingSpit = false;
    public boolean spitting = false;
    public ElapsedTime timer = new ElapsedTime();
    public static double colorDeterminer = 1000;
    public static double wristIncrementer = 0.05;
    public static double blue;
    public static double red;
    public static double green;
    public static boolean bluethresh;
    public static boolean redthresh;
    public static boolean greenthresh;
    public double viperPower;
    public double vipPos;
    public static double vipReducer = 1;
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

    public void suck(){
        suckR.setPower(1);
        suckL.setPower(1);
    }

    public void spit(){
        suckR.setPower(-1);
        suckL.setPower(-1);
    }

    public void timedSpit(ElapsedTime elapsedTime){
        timingSpit = true;
        elapsedTime.reset();
    }

    public void stop(){
        suckR.setPower(0);
        suckL.setPower(0);
    }

    public void updateColor(Telemetry dashtele){
        blue = color.blue();
        red = color.red();
        green = color.green();
        bluethresh = blue >= colorDeterminer;
        redthresh = red >= colorDeterminer;
        greenthresh = green >= colorDeterminer;
        dashtele.addData("blue: ", blue);
        dashtele.addData("green: ", green);
        dashtele.addData("red: ", red);
        dashtele.addData("Spitting? ", spitting);
        dashtele.update();
    }

    public boolean vipSuckHandler(){
        if (alliance){
            if (bluethresh){
                spitting = false;
                stop();
                spitting = false;
                return true;
            }
            if (redthresh && !greenthresh && (red> green)){
                spitting = true;
                spit();
                spitting = true;
                return false;
            }
        }
        if (!alliance){
            if (bluethresh && !greenthresh && (blue > green)){
                spitting = true;
                spit();
                spitting = true;
                return false;
            }
            if (redthresh){
                spitting = false;
                stop();
                spitting = false;
                return true;
            }
        }
        if (greenthresh && redthresh && (green > red)){
            spitting = true;
            spit();
            spitting = true;
            return false;
        }
        if (!greenthresh && !redthresh && !bluethresh){
            spitting = false;
            suck();
            return false;
        }
        return false;
    }

    public void viperControl(Gamepad gamepad1, int vipPos, Telemetry tele){
        viperPower = (gamepad1.right_trigger - gamepad1.left_trigger) * vipReducer;
        if (vipPos >= 1400){
            if (viperPower < 0){
                viper.setPower(viperPower);
            } else if (viperPower >= 0) {
                viper.setPower(0);
            }
        } else if (vipPos < 1400){
            viper.setPower(viperPower);
        }
        tele.addData("vipPower: ", viperPower);
        tele.addData("vipPos: ", vipPos);
        tele.addData("vip Vel: ", viper.getVelocity());
        tele.update();
    }
    public void vipWristControl(Gamepad currentGamepad1, Gamepad previousGamepad1, Telemetry tele){
        if (currentGamepad1.a && !previousGamepad1.a && !currentGamepad1.start) {
            double currPos = Math.round(vipWrist.getPosition() * 100.00) / 100.00;
            vipWrist.setPosition(currPos + wristIncrementer);
        }
        if (currentGamepad1.b && !previousGamepad1.b && !currentGamepad1.start) {
            double currPos = Math.round(vipWrist.getPosition() * 100.00) / 100.00;
            vipWrist.setPosition(currPos - wristIncrementer);
        }
        if (currentGamepad1.x && !previousGamepad1.x){
            vipWrist.setPosition(0.15);
            timedSpit(timer);
        }
        tele.addData("vipWristPos: ", vipWrist.getPosition());
        tele.update();
    }
    public void vipSuckControl(Gamepad currentGamepad1, Gamepad previousGamepad1, Telemetry tele){
        //viper, 600
        //vipwrist, .40
        if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down){
            suck();
        }
        if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up){
            spit();
        }
        if (currentGamepad1.y && !previousGamepad1.y){
            stop();
        }
    }
    // a & b move wrist, x start suck, y stop suck
}
