package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.text.DecimalFormat;

import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ServoTest")
public class servoTest extends LinearOpMode {
    Servo servo1, servo2, servo3, servo4;
          //1        2        4       3
    Gamepad currentGamepad =  new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    private static final DecimalFormat dformat = new DecimalFormat("0.00");
    double servo1Pos, servo2Pos, servo3Pos, servo4Pos;
    @Override

    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.get(Servo.class, "claw1".toLowerCase());
        servo2 = hardwareMap.get(Servo.class, "claw2".toLowerCase());
        servo3 = hardwareMap.get(Servo.class, "claw3".toLowerCase());
        servo4 = hardwareMap.get(Servo.class, "claw4".toLowerCase());

        servo1.setDirection(Servo.Direction.REVERSE);
        servo2.setDirection(Servo.Direction.FORWARD);
        servo3.setDirection(Servo.Direction.FORWARD);
        servo4.setDirection(Servo.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector();
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            if (currentGamepad.a && !previousGamepad.a){
                servo1Pos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.b && !previousGamepad.b) {
                servo1Pos -= 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.x && !previousGamepad.x){
                servo2Pos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.y && !previousGamepad.y) {
                servo2Pos -= 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.dpad_up && !previousGamepad.dpad_up) {
                servo3Pos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.dpad_down && !previousGamepad.dpad_down) {
                servo3Pos -= 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.right_bumper && !previousGamepad.right_bumper){
                servo4Pos += 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.left_bumper && !previousGamepad.left_bumper) {
                servo4Pos -= 0.01;
                telemetryUpdate();
            }
            if (currentGamepad.start && !previousGamepad.start){
                servo1.setPosition(servo1Pos); //0.04 open, 0 closed
                servo2.setPosition(servo2Pos); //0.04 open, 0 closed
                servo3.setPosition(servo3Pos);
                servo4.setPosition(servo4Pos); //0.10 flipped, 0 init
                telemetryUpdate();
            }
        }
    }

    public void telemetryUpdate(){
        telemetry.addData("First Servo Position", dformat.format(servo1Pos));
        telemetry.addData("Current First Servo Position", dformat.format(servo1.getPosition()));
        telemetry.addData("Second Servo Position", dformat.format(servo2Pos));
        telemetry.addData("Current Second Servo Position", dformat.format(servo2.getPosition()));
        telemetry.addData("Third Servo Position", dformat.format(servo3Pos));
        telemetry.addData("Current Third Servo Position", dformat.format(servo3.getPosition()));
        telemetry.addData("Fourth Servo Position", dformat.format(servo4Pos));
        telemetry.addData("Current Fourth Servo Position", dformat.format(servo4.getPosition()));
        telemetry.update();
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}
