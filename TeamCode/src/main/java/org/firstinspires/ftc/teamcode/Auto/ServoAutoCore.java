package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

        public Servo pincer, wrist; //Declare servo variables
        public double upWrist = 1;
        public double hangWrist = 0.6;
        public double pincerClose = 0;
        public double pincerOpen = 0.23;

        public void init(HardwareMap hwMap) {
            pincer = hwMap.get(Servo.class, "pincer".toLowerCase());
            wrist = hwMap.get(Servo.class, "wrist".toLowerCase());
            //brake = hwMap.get(Servo.class, "brake".toLowerCase());

            pincer.setDirection(Servo.Direction.FORWARD);
            wrist.setDirection(Servo.Direction.FORWARD);

            pincer.setPosition(pincerClose);
            wrist.setPosition(upWrist);
        }


        //Dpad control used in Mason S.'s op mode
}
