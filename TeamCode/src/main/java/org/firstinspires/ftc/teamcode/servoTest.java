package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.text.DecimalFormat;

import com.qualcomm.robotcore.hardware.Servo;
//0.04 rest up
//0.25 start home
//0.06 close
//0.47/8 rest down
@TeleOp(name = "ServoTest")
public class servoTest extends LinearOpMode {
    Servo servo1, pincer, wrist;
          //1        2        4       3
    Gamepad currentGamepad =  new Gamepad();
    Gamepad previousGamepad = new Gamepad();

    private static final DecimalFormat dformat = new DecimalFormat("0.00");
    double servo1Pos, servo2Pos, servo3Pos, servo4Pos;
    @Override

    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.get(Servo.class, "hook".toLowerCase());
        pincer = hardwareMap.get(Servo.class, "pincer".toLowerCase());
        wrist = hardwareMap.get(Servo.class, "wrist".toLowerCase());

        servo1.setDirection(Servo.Direction.FORWARD);
        pincer.setDirection(Servo.Direction.FORWARD);
        wrist.setDirection(Servo.Direction.REVERSE);
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
                //pincer.setPosition(servo2Pos); //0.04 open, 0 closed
                //wrist.setPosition(servo3Pos); // servo 1 port 2, servo 3 port 4
                telemetryUpdate();
            }
        }
    }

    public void telemetryUpdate(){
        telemetry.addData("First Servo Position", dformat.format(servo1Pos));
        telemetry.addData("Current First Servo Position", dformat.format(servo1.getPosition()));
        telemetry.addData("Second Servo Position", dformat.format(servo2Pos));
        telemetry.addData("Current Second Servo Position", dformat.format(pincer.getPosition()));
        telemetry.addData("Third Servo Position", dformat.format(servo3Pos));
        telemetry.addData("Current Third Servo Position", dformat.format(wrist.getPosition()));

        telemetry.update();
    }

    public void edgeDetector() throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad2);
    }
}
