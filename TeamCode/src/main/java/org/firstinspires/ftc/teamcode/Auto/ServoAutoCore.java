package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

        public Servo pincer, wrist; //Declare servo variables
        public double upWrist = 0.88;
        public double hangWrist = 0.79;
        public double closePincer = 0;
        public double openPincer = 0.06;

        public void init(HardwareMap hwMap) {
            pincer = hwMap.get(Servo.class, "claw2".toLowerCase());
            wrist = hwMap.get(Servo.class, "claw4".toLowerCase());
            //brake = hwMap.get(Servo.class, "brake".toLowerCase());

            pincer.setDirection(Servo.Direction.FORWARD);
            wrist.setDirection(Servo.Direction.REVERSE);

            pincer.setPosition(closePincer);
            wrist.setPosition(upWrist);
        }


        //Dpad control used in Mason S.'s op mode
}
