package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

public class ServoCore {
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    private static final DecimalFormat dformat = new DecimalFormat("0.00");

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad(); //Set up gamepad variables allowing for rising edge detector

    public Servo claw1, pincer, wrist, brake, hook; //Declare servo variables

    boolean claw1Stat, pincerStat, wristStat, brakeStat, MMStat;
    boolean hookStat = true;
    public double upWrist = 0.88;
    public double hookClose = 0.06;
    public double hookOpen = 0.25;

    public void init(HardwareMap hwMap) {
        pincer = hwMap.get(Servo.class, "claw2".toLowerCase());
        wrist = hwMap.get(Servo.class, "claw4".toLowerCase());
        hook = hwMap.get(Servo.class, "hook".toLowerCase());
        //brake = hwMap.get(Servo.class, "brake".toLowerCase());
 
        pincer.setDirection(Servo.Direction.FORWARD);
        wrist.setDirection(Servo.Direction.REVERSE);
        //brake.setDirection(Servo.Direction.FORWARD);
        pincer.setPosition(0);
        wrist.setPosition(upWrist);
        hook.setPosition(hookClose);
        //brake.setPosition(0);
    }

    //Dpad control used in Mason S.'s op mode
    public void  dpadRun(Gamepad currentGamepad2, Gamepad previousGamepad2, Telemetry dashTele) {
        if (currentGamepad2.b && !previousGamepad2.b && !currentGamepad2.start) {
                pincerStat = !pincerStat;
                pincer.setPosition(0); //(close)
                if (pincerStat) {
                    pincer.setPosition(0.05); //open
                } else {
                    pincer.setPosition(0); //close
                }
        }

        if (currentGamepad2.dpad_left && !previousGamepad2.dpad_left && !currentGamepad2.start) {
            wrist.setPosition(0.85);
            pincer.setPosition(0.05); //open
        }

        if (currentGamepad2.x && !previousGamepad2.x) {
            double currPos = Math.round(wrist.getPosition() * 100.00) / 100.00;
            wrist.setPosition(currPos + 0.01);
        }
        if (currentGamepad2.y && !previousGamepad2.y) {
            double currPos = Math.round(wrist.getPosition() * 100.00) / 100.00;
            if (currPos - 0.01 >= 0.67){
                wrist.setPosition(currPos - 0.01);
            }
        }
        if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
            wrist.setPosition(upWrist);
        }

        dashTele.addData("Wrist Pos: ", wrist.getPosition());
        dashTele.addData("Pincer Pos: ", pincer.getPosition());
    }

    public void hookHandler(Gamepad currentGamepad, Gamepad previousGamepad){
        if (currentGamepad.dpad_down && !previousGamepad.dpad_down) {
            hookStat = !hookStat;
            if (hookStat){
                hook.setPosition(hookClose);
            } else {
                hook.setPosition(hookOpen);
            }
        }
    }

    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}