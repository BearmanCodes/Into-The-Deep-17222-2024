package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Op.GamepadCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TeleOp(name="LED TEST", group = "tests")
public class PS4 extends LinearOpMode {
    GamepadCore gamepadCore = new GamepadCore();
    ColorSensor colorSensor;
    List<Integer> sensorRGB = new ArrayList<>(3);
    @Override
    public void runOpMode() throws InterruptedException {
        Init(); //Git Test To Add
        waitForStart();
        while (opModeIsActive()) {
            try {
                gamepadCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            //sensorRGB = Arrays.asList(colorSensor.red(), colorSensor.green(), colorSensor.blue());
            //gamepadSetColorSensor(gamepad1, sensorRGB);
            gamepadColorRGB(gamepad1, 255, 0, 255);
        }
    }

    private void Init(){
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
    }

    //Unused for now
    private void gamepadColorString(Gamepad gpad, String color){
        switch(color.toUpperCase()) {
            case "RED":
                gpad.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
                break;
            case "GREEN":
                gpad.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                break;
            case "BLUE":
                gpad.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                break;
        }
    }

    private void gamepadColorRGB(Gamepad gpad, int red, int green, int blue){
        gpad.setLedColor(red, green, blue, Gamepad.LED_DURATION_CONTINUOUS);
    }

    private void gamepadSetColorSensor(Gamepad gpad, List<Integer> rgb){
        gpad.setLedColor(rgb.get(0), rgb.get(1), rgb.get(2), Gamepad.LED_DURATION_CONTINUOUS);
    }
}


