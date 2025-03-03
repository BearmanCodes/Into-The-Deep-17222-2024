package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

        public Servo pincer, wrist; //Declare servo variables
        public double upWrist = 1;
        public double hangWrist = 0.79;
        public double closePincer = 0;
        public double openPincer = 0.06;

        public void init(HardwareMap hwMap) {
            pincer = hwMap.get(Servo.class, "pincer".toLowerCase());
            wrist = hwMap.get(Servo.class, "wrist".toLowerCase());
            //brake = hwMap.get(Servo.class, "brake".toLowerCase());

            pincer.setDirection(Servo.Direction.FORWARD);
            wrist.setDirection(Servo.Direction.FORWARD);

            pincer.setPosition(closePincer);
            wrist.setPosition(upWrist);
        }


        //Dpad control used in Mason S.'s op mode
}
