package org.firstinspires.ftc.teamcode.Op;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="controller2")
public class cONTROLLER2 extends LinearOpMode {
    float motorpower;
    Gamepad currentGamepad = new Gamepad();
    Gamepad previousGamepad = new Gamepad();
    boolean closed = false;
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad(); //Set up gamepad variables allowing for rising edge detector
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "Motor");
        Servo servo = hardwareMap.get(Servo.class, "clawy");
        //servo.setPosition(0.15);
        waitForStart();
        while (opModeIsActive()){
            try {
                edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }

            if (currentGamepad2.a && !previousGamepad2.a){
                closed = !closed;
                if (closed) servo.setPosition(0);
                else servo.setPosition(0.15);
            }

            motorpower = (gamepad2.right_trigger - gamepad2.left_trigger);
            motor.setPower(motorpower);
        }
    }

    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}

