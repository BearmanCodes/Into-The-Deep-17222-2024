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
    public int armTarget, armVelocity;

    public void Mode_Handler(Gamepad currGamepad1, Gamepad prevGamepad1){
        if (currGamepad1.x && !prevGamepad1.x) { //Demonstrative variables used, replace later please.
            //BAR HANDLER
            armTarget = 700;
            armVelocity = 800;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad1.a && !prevGamepad1.a) {
            //SPECIMEN FLOOR HANDLER
            armTarget = 2000;
            armVelocity = 800;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad1.y && !prevGamepad1.y) { //Demonstrative variables used, replace later please. Bar
            //CUSTOM HANDLER
            MODE = RUNNING_MODE.MOVE_MODE;
        }
    }

    public void Move_Init(ArmCore armCore){
        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armCore.pvtArm.setTargetPosition(armTarget);
        armCore.pvtArm.setVelocity(armVelocity);
    }

    public void Move_Tele(Telemetry dashTele, int err){
        dashTele.addData("Arm Target: ", armTarget);
        dashTele.addData("Arm Velocity: ", armVelocity);
        dashTele.addData("Error: ", err);
        dashTele.update();
    }
}
