package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ModeCore {
    public enum RUNNING_MODE{
        NORMAL_MODE,
        ARM_MOVE,
        VIP_MOVE,
        VIP_SUCK
    } //This isn't needed. But enum's are cool for switch statements so...

    public enum CHAINS {
        EXTEND_SUCK_BACK,
        SUCK_BACK
    }

    public RUNNING_MODE MODE = RUNNING_MODE.NORMAL_MODE;
    public static CHAINS CHAIN;
    public static boolean isChain = false;
    public static int chainIterator = 0;
    public static RUNNING_MODE nextMode;
    public static int armTarget, wristTarget, vipTarget;
    public static double armVelocity, wristVelocity, vipVelocity;
    public static double wristPos, pincerPos, vipWristTarget;
    public static int barCompensator, speciCompensator = 0;
    public static int vipExtended = 500;
    public static int armVel = 4000;
    public static double vipWristDown = 0.4;
    public static int vipVel = 4000;
    public static int fwdGrabArm = 6550;
    public static int fwdGrabWrist = 3010;
    public static int fwdHangArm = 4550;
    public static int fwdHangWrist = 4000;
    public static int rearHangArm = 5045;
    public static int rearHangWrist = 1400;
    public static double rearHangWristPos = 0.23;
    public static double currPincerPos;
    public static int rearGrabArm = 5;
    public static int rearGrabWrist = 5300;
    public static double rearGrabWristPos = 0.7;
    public static double pincerOpen = 0.23;
    public static double pincerClose = 0;
    public static int vipHome = 10;

    RUNNING_MODE[] modes;
    int[] vipTargets;
    int[] vipVelocities;
    double[] vipWristTargets;
    int[] modeIterations;


    public void modeHandler(Gamepad currGamepad1, Gamepad prevGamepad1, Gamepad currGamepad2, Gamepad prevGamepad2, ServoCore servoCore, IntakeCore intakeCore){
        if (currGamepad2.dpad_down && !prevGamepad2.dpad_down) { //Demonstrative variables used, replace later please.
                                                                //I did not, in fact, replace them later.
            //FORWARD GRAB HANDLER
            armTarget = fwdGrabArm; //change this
            armVelocity = armVel;
            wristTarget = fwdGrabWrist;
            wristVelocity = 2000; //change this
            pincerPos = 0.06;
            currPincerPos = Math.round(servoCore.pincer.getPosition() * 100.00) / 100.00;
            if (currPincerPos != pincerPos) servoCore.pincerStat = !servoCore.pincerStat;
            //SHAAAWN ADD THE WRISTPOS YOU WANT HERE
            MODE = RUNNING_MODE.ARM_MOVE;
        }
        if (currGamepad2.dpad_up && !prevGamepad2.dpad_up) {
            //FORWARD HANG
            armTarget = fwdHangArm; //change this
            armVelocity = armVel;
            wristTarget = fwdHangWrist; //change this
            wristVelocity = 2000; //change this
            pincerPos = 0;
            currPincerPos = Math.round(servoCore.pincer.getPosition() * 100.00) / 100.00;
            if (currPincerPos != pincerPos) servoCore.pincerStat = !servoCore.pincerStat;
            MODE = RUNNING_MODE.ARM_MOVE;
            //SHAAAWN ADD THE WRISTPOS YOU WANT HERE

        }
        if (currGamepad2.dpad_left && !prevGamepad2.dpad_left) { //Demonstrative variables used, replace later please.
            //I did not, in fact, replace them later.
            //REAR HANG
            armTarget = rearHangArm + barCompensator; //change this
            armVelocity = armVel;
            wristVelocity = 2000; //change this
            pincerPos = pincerClose;
            currPincerPos = Math.round(servoCore.pincer.getPosition() * 100.00) / 100.00;
            if (currPincerPos != pincerPos) servoCore.pincerStat = !servoCore.pincerStat;
            MODE = RUNNING_MODE.ARM_MOVE;
            //SHAAAWN ADD THE WRISTPOS YOU WANT HERE
        }
        if (currGamepad2.dpad_right && !prevGamepad2.dpad_right) {
            //REAR GRAB
            armTarget = rearGrabArm; //change this
            armVelocity = armVel;
            wristPos = rearGrabWristPos; //change this
            pincerPos = pincerOpen;
            currPincerPos = Math.round(servoCore.pincer.getPosition() * 100.00) / 100.00;
            if (currPincerPos != pincerPos) servoCore.pincerStat = !servoCore.pincerStat;
            MODE = RUNNING_MODE.ARM_MOVE;
            //SHAAAWN ADD THE WRISTPOS YOU WANT HERE
        }
        if (currGamepad1.left_stick_button && !prevGamepad1.left_stick_button){
            isChain = false;
            intakeCore.stop();
            intakeCore.vipWrist.setPosition(0);
            vipVelocity = vipVel;
            vipTarget = vipHome;
            MODE = RUNNING_MODE.VIP_MOVE;
        }
        if (currGamepad1.right_stick_button && !prevGamepad1.right_stick_button){
            chainIterator = 0;
            isChain = true;
            intakeCore.vipWrist.setPosition(vipWristDown);
            CHAIN = CHAINS.SUCK_BACK;
            MODE = RUNNING_MODE.VIP_SUCK;
        }
    }

    public void chainRefresh(CHAINS chain){
        switch (chain){
            case EXTEND_SUCK_BACK:
                modes = new RUNNING_MODE[]{RUNNING_MODE.VIP_MOVE, RUNNING_MODE.VIP_SUCK, RUNNING_MODE.VIP_MOVE, RUNNING_MODE.NORMAL_MODE};
                vipTargets = new int[]{vipExtended, 0, vipHome};
                vipVelocities = new int[]{vipVel, 0, vipVel};
                vipWristTargets = new double[]{0, vipWristDown, 0};
                modeIterations = new int[]{1, 2, 3};
                vipTarget = vipTargets[chainIterator];
                vipVelocity = vipVelocities[chainIterator];
                vipWristTarget = vipWristTargets[chainIterator];
                nextMode = modes[modeIterations[chainIterator]];
            case SUCK_BACK:
                modes = new RUNNING_MODE[]{RUNNING_MODE.VIP_SUCK, RUNNING_MODE.VIP_MOVE, RUNNING_MODE.NORMAL_MODE};
                vipTargets = new int[]{0, vipHome};
                vipVelocities = new int[]{0, vipVel};
                vipWristTargets = new double[]{vipWristDown, 0};
                modeIterations = new int[]{1, 2};
                vipTarget = vipTargets[chainIterator];
                vipVelocity = vipVelocities[chainIterator];
                vipWristTarget = vipWristTargets[chainIterator];
                nextMode = modes[modeIterations[chainIterator]];

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
