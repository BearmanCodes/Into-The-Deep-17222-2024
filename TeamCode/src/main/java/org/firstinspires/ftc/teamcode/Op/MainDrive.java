package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Main Drive", group="Critical")
@Config
public class MainDrive extends LinearOpMode {
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    DrivetrainCore drivetrainCore = new DrivetrainCore();
    ModeCore modeCore = new ModeCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();

    //RUNNING_MODE MODE = RUNNING_MODE.NORMAL_MODE;
    public int errTolerance = 20;
    public double freedomPower = 0.25;
    public int servoActionTol = 1000;
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
            drivetrainCore.run(gamepad1);
            if (armCurrPos >= servoActionTol
            ) {
                servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2);
            }
            switch (modeCore.MODE){ //Based on the mode set the arm to be in control or moving auto
                case NORMAL_MODE:
                    armCore.trigger(gamepad2, armCurrPos); //Give arm control to driver
                    modeCore.Mode_Handler(servoCore.currentGamepad, servoCore.previousGamepad); //Handle variables for reaching the top bar position (X)
                    break; //Why I picked switch statements. Keeps you out of while loop hell
                case MOVE_MODE:
                    modeCore.Move_Init(armCore); //init for running to position
                    int err = Math.abs(armCurrPos - modeCore.armTarget); //amount of ticks to go to target
                    boolean BREAKFREE = Math.abs((gamepad2.right_trigger - gamepad2.left_trigger)) >= freedomPower;
                    //This boolean decides whether or not to give control back to the driver
                    if (err <= errTolerance || BREAKFREE) {
                        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                    }
                    modeCore.Move_Tele(dashTele, err);
                    break;
            }
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}