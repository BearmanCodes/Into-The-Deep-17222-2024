package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ModeCore {
    public enum RUNNING_MODE{
        NORMAL_MODE,
        MOVE_MODE
    } //This isn't needed. But enum's are cool for switch statements so...

    public RUNNING_MODE MODE = RUNNING_MODE.NORMAL_MODE;
    public static int armTarget, armVelocity;
    public static double wristPos, pincerPos;
    public static int barCompensator, speciCompensator = 0;

    public void modeHandler(Gamepad currGamepad2, Gamepad prevGamepad2, ServoCore servoCore){
        if (currGamepad2.dpad_down && !prevGamepad2.dpad_down) { //Demonstrative variables used, replace later please.
                                                                //I did not, in fact, replace them later.
            //SPECIMEN FLOOR HANDLER
            armTarget = 6800 + speciCompensator;
            armVelocity = 2000;
            wristPos = 0.80;
            pincerPos = 0.05;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_up && !prevGamepad2.dpad_up) {
            //BAR HANDLER
            armTarget = 5700 + barCompensator;
            armVelocity = 2000;
            wristPos = 0.77;
            pincerPos = 0;
            MODE = RUNNING_MODE.MOVE_MODE;
        }

    }

    public void Compensate(Gamepad currGamepad1, Gamepad prevGamepad1){
        if (currGamepad1.dpad_up && !prevGamepad1.dpad_up){
            barCompensator -= 50;
        }
        if (currGamepad1.dpad_down && !prevGamepad1.dpad_down){
            barCompensator += 50;
        }
        if (currGamepad1.dpad_right && !prevGamepad1.dpad_right){
            speciCompensator -= 50;
        }
        if (currGamepad1.dpad_left && !prevGamepad1.dpad_left){
            speciCompensator += 50;
        }
    }

    public void teleMove(Telemetry dashTele, int err){
        dashTele.addData("Arm Target: ", armTarget);
        dashTele.addData("Arm Velocity: ", armVelocity);
        dashTele.addData("Error: ", err);
        dashTele.addData("Bar Compensator: ", barCompensator);
        dashTele.addData("Specimen Compensator: ", speciCompensator);
        dashTele.update();
    }
}
