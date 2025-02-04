package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.Auto.Action;
@TeleOp(name="Main Drive", group="Critical")
@Config
public class MainDrive extends LinearOpMode {
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    DrivetrainCore drivetrainCore = new DrivetrainCore();
    ModeCore modeCore = new ModeCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();

    DoubleTele doubleTele = new DoubleTele(telemetry, dashTele);
    public static int errTolerance = 5;
    public static double freedomPower = 0.01;
    public static int servoActionTol = 3000;
    public static boolean fwd = true;
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive()) {
            try {
                servoCore.edgeDetector(gamepad1, gamepad2);
            } catch (RobotCoreException e) {
                throw new RuntimeException(e);
            }
            int armCurrPos = armCore.pvtArm.getCurrentPosition(); //Keeps var of arm pos for ref
            int wristCurrPos = armCore.wristMotor.getCurrentPosition();
            drivetrainCore.run(gamepad1);
            servoCore.hookHandler(servoCore.currentGamepad, servoCore.previousGamepad);
            servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2, dashTele);
            switch (modeCore.MODE){ //Based on the mode set the arm to be in control or moving auto
                case NORMAL_MODE:
                    armCore.trigger(gamepad1, gamepad2, armCurrPos); //Give arm control to driver
                    dashTele.addData("Arm Pos: ", armCurrPos);
                    dashTele.addData("Wrist Pos: ", wristCurrPos);
                    dashTele.addData("Arm Pwr: ", armCore.pvtPower);
                    dashTele.update();
                    determineWristPos();
                    modeCore.modeHandler(servoCore.currentGamepad2, servoCore.previousGamepad2, servoCore); //Handle variables for reaching the top bar position (X)
                    break; //Why I picked switch statements. Keeps you out of while loop hell
                case MOVE_MODE:
                    armCore.pvtArm.setTargetPosition(ModeCore.armTarget);
                    armCore.wristMotor.setTargetPosition(ModeCore.wristTarget);
                    armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armCore.wristMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armCore.pvtArm.setVelocity(ModeCore.armVelocity);
                    armCore.wristMotor.setVelocity(ModeCore.wristVelocity);
                    servoCore.pincer.setPosition(ModeCore.pincerPos);
                    int wristErr = Math.abs(wristCurrPos - ModeCore.wristTarget);
                    int err = Math.abs(armCurrPos - ModeCore.armTarget); //amount of ticks to go to target
                    boolean BREAKFREE = Math.abs((gamepad2.right_trigger - gamepad2.left_trigger)) >= freedomPower;
                    boolean WRISTFREE = Math.abs((gamepad1.right_trigger - gamepad1.left_trigger)) >= freedomPower;
                    modeCore.teleMove(dashTele, err);
                    //This boolean decides whether or not to give control back to the driver
                    if ((err <= errTolerance || BREAKFREE) && (wristErr <= errTolerance || WRISTFREE)) {
                        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        armCore.wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                    }
                    break;
            }
        }
    }

    public void determineWristPos(){
        double wristVel = armCore.wristMotor.getVelocity();
        double wristPos = armCore.wristMotor.getCurrentPosition();
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}