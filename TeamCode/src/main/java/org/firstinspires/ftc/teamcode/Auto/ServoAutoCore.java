package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

        public Servo claw1, claw2, claw3, claw4, brake; //Declare servo variables

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
            //claw3.setPosition(0.6);
            //claw4.setPosition(0);
            //brake.setPosition(0);
        }


        //Dpad control used in Mason S.'s op mode
}
