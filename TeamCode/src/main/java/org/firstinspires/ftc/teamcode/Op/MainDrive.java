package org.firstinspires.ftc.teamcode.Op;

import android.view.Display;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Main Drive", group="Critical")
@Config
public class MainDrive extends LinearOpMode {
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    DrivetrainCore drivetrainCore = new DrivetrainCore();
    IntakeCore intakeCore = new IntakeCore();
    ModeCore modeCore = new ModeCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();

    DoubleTele doubleTele = new DoubleTele(telemetry, dashTele);
    public ElapsedTime timer = new ElapsedTime();
    public static int errTolerance = 5;
    public static double freedomPower = 0.01;
    public static int servoActionTol = 3000;
    public static boolean fwd = true;
    public static long suckSleep = 1000;

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            Init();
        } catch (RobotCoreException e) {
            throw new RuntimeException(e);
        }

        waitForStart();
        motion_start();
        while (opModeIsActive()) {
            updateGpadStatus();
//            try {
//                servoCore.edgeDetector(gamepad1, gamepad2);
//            } catch (RobotCoreException e) {
//                throw new RuntimeException(e);
//            }
            int armCurrPos = ArmCore.pvtArm.getCurrentPosition(); //Keeps var of arm pos for ref
            int viperCurrPos = IntakeCore.viper.getCurrentPosition();
            drivetrainCore.run(gamepad1, dashTele);
            intakeCore.vipWristControl(servoCore.currentGamepad, servoCore.previousGamepad, dashTele);
            servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2, dashTele);
            IntakeCore.allianceSwap(telemetry);
            switch (modeCore.MODE){ //Based on the mode set the arm to be in control or moving auto
                case NORMAL_MODE:
                    armCore.trigger(gamepad2, armCurrPos); //Give arm control to driver
                    intakeCore.viperControl(gamepad1, viperCurrPos, dashTele);
                    intakeCore.vipSuckControl(servoCore.currentGamepad, servoCore.previousGamepad, dashTele);
                    dashTele.addData("Arm Pos: ", armCurrPos);
                    dashTele.addData("Arm Pwr: ", armCore.pvtPower);
                    dashTele.update();
                    modeCore.modeHandler(servoCore.currentGamepad, servoCore.previousGamepad, servoCore.currentGamepad2, servoCore.previousGamepad2, servoCore, intakeCore); //Handle variables for reaching the top bar position (X)
                    break; //Why I picked switch statements. Keeps you out of while loop hell
                case ARM_MOVE:
                    armCore.pvtArm.setTargetPosition(ModeCore.armTarget);
                    armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armCore.pvtArm.setVelocity(ModeCore.armVelocity);
                    servoCore.pincer.setPosition(ModeCore.pincerPos);
                    servoCore.wrist.setPosition(ModeCore.wristPos);
                    int err = Math.abs(armCurrPos - ModeCore.armTarget); //amount of ticks to go to target
                    boolean BREAKFREE = Math.abs((gamepad2.right_trigger - gamepad2.left_trigger)) >= freedomPower;
                    modeCore.teleMove(dashTele, err);
                    //This boolean decides whether or not to give control back to the driver
                    if (err <= errTolerance || BREAKFREE) {
                        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                    }
                    break;
                case VIP_MOVE:
                    if (ModeCore.isChain) modeCore.chainRefresh(ModeCore.CHAIN);
                    armCore.trigger(gamepad2, armCurrPos); //Give arm control to driver
                    intakeCore.viper.setTargetPosition(ModeCore.vipTarget);
                    intakeCore.viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    intakeCore.viper.setVelocity(ModeCore.vipVelocity);
                    int vipErr = Math.abs(viperCurrPos - ModeCore.vipTarget);
                    boolean VIPFREE = Math.abs((gamepad1.right_trigger - gamepad1.left_trigger)) >= freedomPower;
                    boolean SPIT_ESCAPE = spit_escape();
                    dashTele.addData("Viper Mode Pos: ", viperCurrPos);
                    dashTele.addData("Viper Mode Err: ", vipErr);
                    dashTele.update();
                    if (vipErr <= errTolerance || VIPFREE || SPIT_ESCAPE){
                        intakeCore.viper.setVelocity(0);
                        intakeCore.viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        if (ModeCore.isChain){
                            modeCore.MODE = ModeCore.nextMode;
                            ModeCore.chainIterator += 1;
                        } else {
                            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                        }
                    }
                    break;
                case VIP_SUCK:
                    if (ModeCore.isChain) modeCore.chainRefresh(ModeCore.CHAIN);
                    armCore.pvtArm.setPower(0);
                    intakeCore.viperControl(gamepad1, viperCurrPos, dashTele);
                    if (servoCore.currentGamepad.left_stick_button && !servoCore.previousGamepad.left_stick_button){
                        ModeCore.isChain = false;
                        intakeCore.stop();
                        intakeCore.vipWrist.setPosition(0);
                        ModeCore.vipVelocity = ModeCore.vipVel;
                        ModeCore.vipTarget = ModeCore.vipHome;
                        modeCore.MODE = ModeCore.RUNNING_MODE.VIP_MOVE;
                        break;
                    }
                    //armCore.trigger(gamepad2, armCurrPos); //Give arm control to driver
                    intakeCore.updateColor(dashTele);
                    boolean takenIn = intakeCore.vipSuckHandler();
                    boolean spitting = intakeCore.spitting;
                    boolean SUCKESCAPE = suck_failsafe();
                    if (SUCKESCAPE) break;
                    dashTele.addData("Taken In? ", takenIn);
                    if (takenIn){
                        intakeCore.vipWrist.setPosition(0);
                        if (ModeCore.isChain){
                            modeCore.MODE = ModeCore.nextMode;
                            ModeCore.chainIterator += 1;
                        } else {
                            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                        }
                    } else {

                    }
                    break;
            }
        }
    }

    public boolean spit_escape(){
        if (servoCore.currentGamepad.x && !servoCore.previousGamepad.x){
            intakeCore.vipWrist.setPosition(0.15);
            intakeCore.spit();
            intakeCore.viper.setVelocity(0);
            intakeCore.viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ModeCore.vipHome += 5;
            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
            return true;
        }
        if (servoCore.currentGamepad2.left_stick_button && !servoCore.previousGamepad.left_stick_button){
            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
            return true;
        }
        return false;
    }

    public boolean suck_failsafe(){
        if (servoCore.currentGamepad.dpad_up && !servoCore.previousGamepad.dpad_up){
            intakeCore.suck();
            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
            return true;
        }
        if (servoCore.currentGamepad.dpad_down && !servoCore.previousGamepad.dpad_down){
            intakeCore.spit();
            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
            return true;
        }
        if (servoCore.currentGamepad.y && !servoCore.previousGamepad.y){
            intakeCore.stop();
            modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
            return true;
        }
        return false;
    }

    void motion_start(){
        servoCore.init_motion();
        intakeCore.init_motion();
    }

    void updateGpadStatus(){
        GamepadCore.gpad1 = gamepad1;
        GamepadCore.gpad2 = gamepad2;
        GamepadCore.edgeDetector();
    }

    private void Init() throws RobotCoreException {
        ArmCore.init(hardwareMap);
        ServoCore.init(hardwareMap);
        DrivetrainCore.init(hardwareMap);
        IntakeCore.init(hardwareMap);
        while (!isStarted()){
            updateGpadStatus();
            IntakeCore.allianceSwap(telemetry);
        }
    }
}