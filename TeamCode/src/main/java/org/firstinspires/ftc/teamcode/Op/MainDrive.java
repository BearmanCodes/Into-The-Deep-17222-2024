package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Main Drive", group="Critical")
public class MainDrive extends LinearOpMode {
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    DrivetrainCore drivetrainCore = new DrivetrainCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    public enum RUNNING_MODE{
        NORMAL_MODE,
        MOVE_MODE
    }

    RUNNING_MODE MODE = null;
    public int armTarget, armVelocity;
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
            int armCurrPos = armCore.pvtArm.getCurrentPosition();
            drivetrainCore.run(gamepad1);
            if (armCurrPos >= 1000
            ) {
                servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2);
            }
            switch (MODE){
                case NORMAL_MODE:
                    armCore.trigger(gamepad2, armCurrPos);
                    bar_handler();
                    specimen_handler();
                    break;
                case MOVE_MODE:
                    armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armCore.pvtArm.setTargetPosition(armTarget);
                    armCore.pvtArm.setVelocity(armVelocity);
                    int err = Math.abs(armCurrPos - armTarget);
                    boolean BREAKFREE = Math.abs((gamepad2.right_trigger - gamepad2.left_trigger)) >= 0.5;
                    if (err <= 20 || BREAKFREE) {
                        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        MODE = RUNNING_MODE.NORMAL_MODE;
                    }
                    break;
            }
        }
    }

    public void bar_handler(){
        if (servoCore.currentGamepad.x && !servoCore.previousGamepad.x) { //Demonstrative variables used, replace later please. Bar
            //REACH BAR COMMAND
            armTarget = 700;
            armVelocity = 800;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
    }
    public void specimen_handler(){
        if (servoCore.currentGamepad.a && !servoCore.previousGamepad.a) { //Demonstrative variables used, replace later please. Bar
            //REACH BAR COMMAND
            armTarget = 2000;
            armVelocity = 800;
            MODE = RUNNING_MODE.MOVE_MODE;
        }
    }

    private void Init(){
        MODE = RUNNING_MODE.NORMAL_MODE;
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}