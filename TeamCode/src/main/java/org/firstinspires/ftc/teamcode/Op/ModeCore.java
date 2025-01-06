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

    public void modeHandler(Gamepad currGamepad2, Gamepad prevGamepad2, ServoCore servoCore){
        if (currGamepad2.dpad_down && !prevGamepad2.dpad_down) { //Demonstrative variables used, replace later please.
            //BAR HANDLER
            armTarget = 6750;
            armVelocity = 2000;
            servoCore.wrist.setPosition(0.88);
            servoCore.pincer.setPosition(0.05);
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_up && !prevGamepad2.dpad_up) {
            //SPECIMEN FLOOR HANDLER
            armTarget = 5800;
            armVelocity = 2000;
            servoCore.wrist.setPosition(0.85);
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.y && !prevGamepad2.y) { //Demonstrative variables used, replace later please. Bar
            //CUSTOM HANDLER
            armTarget = armTarget;
            armVelocity = armVelocity;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
    }

    public void initMove(ArmCore armCore){
        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armCore.pvtArm.setTargetPosition(armTarget);
        armCore.pvtArm.setVelocity(armVelocity);
    }

    public void teleMove(Telemetry dashTele, int err){
        dashTele.addData("Arm Target: ", armTarget);
        dashTele.addData("Arm Velocity: ", armVelocity);
        dashTele.addData("Error: ", err);
        dashTele.update();
    }
}
