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
    public static int armTarget, wristTarget;
    public static double armVelocity, wristVelocity;
    public static double wristPos, pincerPos;
    public static int barCompensator, speciCompensator = 0;
    public static int fwdGrabArm = 6550;
    public static int fwdGrabWrist = 3010;
    public static int fwdHangArm = 5200;
    public static int fwdHangWrist = 4000;
    public static int rearHangArm = 4250;
    public static int rearHangWrist = 1400;
    public static int rearGrabArm = 550;
    public static int rearGrabWrist = 2200;

    public void modeHandler(Gamepad currGamepad2, Gamepad prevGamepad2, ServoCore servoCore){
        if (currGamepad2.dpad_down && !prevGamepad2.dpad_down) { //Demonstrative variables used, replace later please.
                                                                //I did not, in fact, replace them later.
            //FORWARD GRAB HANDLER
            armTarget = fwdGrabArm; //change this
            armVelocity = 2000;
            wristTarget = fwdGrabWrist;
            wristVelocity = 2000; //change this
            if (servoCore.pincerStat) servoCore.pincerStat = !servoCore.pincerStat;
            pincerPos = 0.06;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_up && !prevGamepad2.dpad_up) {
            //FORWARD HANG
            armTarget = fwdHangArm + barCompensator; //change this
            armVelocity = 2000;
            wristTarget = fwdHangWrist; //change this
            wristVelocity = 2000; //change this
            pincerPos = 0;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_left && !prevGamepad2.dpad_left) { //Demonstrative variables used, replace later please.
            //I did not, in fact, replace them later.
            //REAR HANG
            armTarget = rearHangArm + barCompensator; //change this
            armVelocity = 2000;
            wristTarget = rearHangWrist; //change this
            wristVelocity = 2000; //change this
            pincerPos = 0;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
        if (currGamepad2.dpad_right && !prevGamepad2.dpad_right) {
            //REAR GRAB
            armTarget = rearGrabArm; //change this
            armVelocity = 2000;
            wristTarget = rearGrabWrist; //change this
            wristVelocity = 2000; //change this
            if (servoCore.pincerStat) servoCore.pincerStat = !servoCore.pincerStat;
            pincerPos = 0.06;
            MODE = RUNNING_MODE.MOVE_MODE;
        }

    }

    public void Compensate(Gamepad currGamepad1, Gamepad prevGamepad1){
        if ((currGamepad1.right_trigger >= 0.8) && !(prevGamepad1.right_trigger >= 0.8)){
            speciCompensator -= 50;
        }
        if ((currGamepad1.left_trigger >= 0.8) && !(prevGamepad1.left_trigger >= 0.8)){
            speciCompensator += 50;
        }
        if (currGamepad1.right_bumper && !prevGamepad1.right_bumper){
            barCompensator -= 50;
        }
        if (currGamepad1.left_bumper && !prevGamepad1.left_bumper){
            barCompensator += 50;
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
