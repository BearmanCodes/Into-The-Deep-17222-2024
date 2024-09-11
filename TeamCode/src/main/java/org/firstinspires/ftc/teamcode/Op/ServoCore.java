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

    public Servo lClaw, rClaw, airplane; //Declare servo variables

    public DrivetrainCore dTrain = new DrivetrainCore();

    Boolean launched = true;
    boolean Lclawstat, Rclawstat;

    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    public void init(HardwareMap hwMap){
        rClaw = hwMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hwMap.get(Servo.class, "lclaw".toLowerCase());
        airplane = hwMap.get(Servo.class, "air".toLowerCase());


        airplane.setDirection(Servo.Direction.REVERSE);
        rClaw.setDirection(Servo.Direction.REVERSE);

        lClaw.setPosition(0.90); //(close)
        rClaw.setPosition(0.90); //(close)
        airplane.setPosition(0.36);
    }

    //Dpad control used in Mason S.'s op mode
    public void dpadRun(Gamepad currentGamepad2, Gamepad previousGamepad2){

        if (currentGamepad2.y && !previousGamepad2.y){
            Lclawstat = !Lclawstat;
            Rclawstat = !Rclawstat;
            lClaw.setPosition(0.90); //(close)
            rClaw.setPosition(0.90); //(close)
        }
        if (currentGamepad2.a && !previousGamepad2.a && !currentGamepad2.start){
            Lclawstat = !Lclawstat;
            Rclawstat = !Rclawstat;
            lClaw.setPosition(0.65); //(open)
            rClaw.setPosition(0.65); //(open)
        }
        if (currentGamepad2.b && !previousGamepad2.b && !currentGamepad2.start){
            Lclawstat = !Lclawstat;
            if(Lclawstat){
                lClaw.setPosition(0.65); //(open)
            } else {
                lClaw.setPosition(0.90); //(close)
            }
        }
        if (currentGamepad2.x && !previousGamepad2.x){
            Rclawstat = !Rclawstat;
            if(Rclawstat){
                rClaw.setPosition(0.65); //(open)
            } else {
                rClaw.setPosition(0.90); //(close)
            }
        }
    }

    public void airLaunch(Telemetry telemetry){
        if (currentGamepad.x && !previousGamepad.x){
            launched = !launched;
            if (launched) airplane.setPosition(0.36);
            else airplane.setPosition(0.75);
        }
    }

    //Toggle controls used in Joel and Mason M.'s op modes

    public void toggleRun(){
        if(currentGamepad2.a && !previousGamepad2.a){
            Lclawstat = !Lclawstat;
            if(Lclawstat){
                lClaw.setPosition(0.65); //(open)
                rClaw.setPosition(0.65); //(open)
            } else {
                lClaw.setPosition(0.90); //(close)
                rClaw.setPosition(0.90); //(close)
            }
        }
    }

    /*Edge detector, super important so that the stupid functions
    don't think every ms you hold the button it should execute*/
    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }

}