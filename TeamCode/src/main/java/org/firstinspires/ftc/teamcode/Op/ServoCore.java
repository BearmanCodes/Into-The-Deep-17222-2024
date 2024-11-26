package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoCore {
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad(); //Set up gamepad variables allowing for rising edge detector

    public Servo claw1, claw2, claw3, claw4, brake; //Declare servo variables

    boolean claw1Stat, claw2Stat, claw3Stat, claw4Stat;

    public void init(HardwareMap hwMap) {
        claw1 = hwMap.get(Servo.class, "claw1".toLowerCase());
        claw2 = hwMap.get(Servo.class, "claw2".toLowerCase());
        claw3 = hwMap.get(Servo.class, "claw3".toLowerCase());
        claw4 = hwMap.get(Servo.class, "claw4".toLowerCase());
        //brake = hwMap.get(Servo.class, "brake".toLowerCase());
 
        claw1.setDirection(Servo.Direction.REVERSE);
        claw2.setDirection(Servo.Direction.FORWARD);
        claw3.setDirection(Servo.Direction.FORWARD);
        claw4.setDirection(Servo.Direction.FORWARD);
        //brake.setDirection(Servo.Direction.FORWARD);
        claw1.setPosition(0);
        claw2.setPosition(0);
        claw3.setPosition(0.6);
        claw4.setPosition(0);
        //brake.setPosition(0);
    }

    //Dpad control used in Mason S.'s op mode
    public void dpadRun(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        if (currentGamepad2.a && !previousGamepad2.a && !currentGamepad2.start) {
            claw4Stat = !claw4Stat;
            if (claw4Stat) {
                claw4.setPosition(0.1); //Up
            } else {
                claw4.setPosition(0); //Down            }
            }
        }
        if (currentGamepad2.b && !previousGamepad2.b && !currentGamepad2.start) {
                claw1Stat = !claw1Stat;
                claw2Stat = !claw2Stat;
                claw1.setPosition(0); //(close)
                claw2.setPosition(0); //(close)
                if (claw1Stat && claw2Stat) {
                    claw1.setPosition(0.04); //(open)
                    claw2.setPosition(0.04); //open
                } else if (!claw1Stat && !claw2Stat) {
                    claw1.setPosition(0); //(close)
                    claw2.setPosition(0); //close
                }
        }
        if (currentGamepad2.x && !previousGamepad2.x) {
            double currPos = claw3.getPosition();
            claw3.setPosition(currPos + 0.05);
        }
        if (currentGamepad2.y && !previousGamepad2.y) {
            double currPos = claw3.getPosition();
            claw3.setPosition(currPos - 0.05);
        }
    }
    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}