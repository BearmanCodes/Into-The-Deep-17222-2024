package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoAutoCore {

        public Servo pincer, wrist; //Declare servo variables
        public DcMotorEx wristMotor;
        public double upWrist = 0.88;
        public double hangWrist = 0.79;
        public double closePincer = 0;
        public double openPincer = 0.05;
        public static boolean fwd = true;

        public void init(HardwareMap hwMap) {
            pincer = hwMap.get(Servo.class, "claw2".toLowerCase());
            wrist = hwMap.get(Servo.class, "claw4".toLowerCase());
            wristMotor = hwMap.get(DcMotorEx.class, "wristmotor");
            //brake = hwMap.get(Servo.class, "brake".toLowerCase());

            pincer.setDirection(Servo.Direction.FORWARD);
            wrist.setDirection(Servo.Direction.REVERSE);
            if (fwd) wristMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            else wristMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            pincer.setPosition(closePincer);
            wrist.setPosition(upWrist);
            wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        //Dpad control used in Mason S.'s op mode
}
