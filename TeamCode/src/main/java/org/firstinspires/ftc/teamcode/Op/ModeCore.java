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

    public void modeHandler(Gamepad currGamepad2, Gamepad prevGamepad2, ServoCore servoCore){
        if (currGamepad2.dpad_down && !prevGamepad2.dpad_down) { //Demonstrative variables used, replace later please.
                                                                //I did not, in fact, replace them later.
            //SPECIMEN FLOOR HANDLER
            armTarget = 6800;
            armVelocity = 2000;
            wristPos = 0.88;
            pincerPos = 0.05;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_up && !prevGamepad2.dpad_up) {
            //BAR HANDLER
            armTarget = 5785;
            armVelocity = 2000;
            wristPos = 0.85;
            pincerPos = 0;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_right && !prevGamepad2.dpad_right) {
            //HANGING HANDLER
            armTarget = 4100;
            armVelocity = 2000;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
    }

    public void teleMove(Telemetry dashTele, int err){
        dashTele.addData("Arm Target: ", armTarget);
        dashTele.addData("Arm Velocity: ", armVelocity);
        dashTele.addData("Error: ", err);
        dashTele.update();
    }
}
