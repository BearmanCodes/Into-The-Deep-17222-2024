package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

@Config
public class ServoCore {
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    private static final DecimalFormat dformat = new DecimalFormat("0.00");

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad(); //Set up gamepad variables allowing for rising edge detector

    public Servo claw1, pincer, wrist, brake; //Declare servo variables
    public static boolean fwd = true;


    boolean claw1Stat, pincerStat, wristStat, brakeStat, MMStat;
    //boolean hookStat = true;
    public static double upWrist = 0.88;
    public static double hookClose = 0.06;
    public static double hookOpen = 0.25;
    public static double pincerClose = 0;
    public static double pincerOpen = 0;

    public void init(HardwareMap hwMap) {
        pincer = hwMap.get(Servo.class, "pincer".toLowerCase());
        wrist = hwMap.get(Servo.class, "wrist".toLowerCase());
        //hook = hwMap.get(Servo.class, "hook".toLowerCase());
        //brake = hwMap.get(Servo.class, "brake".toLowerCase());
 
        pincer.setDirection(Servo.Direction.FORWARD);
        //wrist.setDirection(Servo.Direction.REVERSE);
        if (fwd) wrist.setDirection(Servo.Direction.FORWARD);
        else wrist.setDirection(Servo.Direction.REVERSE);
        //wrist.setDirection(Servo.Direction.REVERSE);
        //brake.setDirection(Servo.Direction.FORWARD);
        pincer.setPosition(0);
        //wrist.setPosition(upWrist);
        //hook.setPosition(hookClose);
        //brake.setPosition(0);
    }

    //Dpad control used in Mason S.'s op mode
    public void  dpadRun(Gamepad currentGamepad2, Gamepad previousGamepad2, Telemetry dashTele) {

        //YO Shawn, once you dial in the pincer positions you can remove these a and b button statements and replace them with the commented out one
        if (currentGamepad2.b && !previousGamepad2.b && !currentGamepad2.start) {
            double currPos = Math.round(pincer.getPosition() * 100.00) / 100.00;
            pincer.setPosition(currPos + 0.01);
            dashTele.addData("Pincer Pos: ", pincer.getPosition());
            dashTele.update();
        }
        if (currentGamepad2.a && !previousGamepad2.a && !currentGamepad2.start) {
            double currPos = Math.round(pincer.getPosition() * 100.00) / 100.00;
            pincer.setPosition(currPos - 0.01);
            dashTele.addData("Pincer Pos: ", pincer.getPosition());
            dashTele.update();
        }
        /*
        !!!!YO Shawn this is for when you find the right pincer open and close positions. Uncomment these lines but set it to the B button instead of dpad_right, 'cause otherwise it'll all die
        if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
            pincerStat = !pincerStat;
            if (pincerStat) {
                pincer.setPosition(pincerOpen); //open
            } else {
                pincer.setPosition(pincerClose); //close
            }
        }
        */
        if (currentGamepad2.x && !previousGamepad2.x) {
            double currPos = Math.round(wrist.getPosition() * 100.00) / 100.00;
            wrist.setPosition(currPos + 0.01);
            dashTele.addData("Wrist Pos: ", wrist.getPosition());
            dashTele.update();
        }
        if (currentGamepad2.y && !previousGamepad2.y) {
            double currPos = Math.round(wrist.getPosition() * 100.00) / 100.00;
            wrist.setPosition(currPos - 0.01);
            dashTele.addData("Wrist Pos: ", wrist.getPosition());
            dashTele.update();
        }
    }

    /*
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

    }
    */

    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}