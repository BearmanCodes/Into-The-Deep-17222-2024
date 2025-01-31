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
    ActionOP action = new ActionOP();

    public static int errTolerance = 5;
    public static double freedomPower = 0.01;
    public static int servoActionTol = 3000;
    public static boolean fwd = true;
    public static double wristReducer = 1;
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
            servoCore.hookHandler(servoCore.currentGamepad, servoCore.previousGamepad);
            if (armCurrPos >= servoActionTol) {
                servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2, dashTele);
            }
            switch (modeCore.MODE){ //Based on the mode set the arm to be in control or moving auto
                case NORMAL_MODE:
                    armCore.trigger(gamepad2, armCurrPos); //Give arm control to driver
                    determineWristPos();
                    doubleTele.addData("Arm Position: ", armCurrPos);
                    doubleTele.addData("Arm Power: ", armCore.pvtPower);
                    doubleTele.addData("Arm Velocity: ", armCore.pvtArm.getVelocity());
                    doubleTele.update();
                    modeCore.modeHandler(servoCore.currentGamepad2, servoCore.previousGamepad2, servoCore); //Handle variables for reaching the top bar position (X)
                    //modeCore.Compensate(servoCore.currentGamepad, servoCore.previousGamepad);
                    break; //Why I picked switch statements. Keeps you out of while loop hell
                case MOVE_MODE:
                    servoCore.pincer.setPosition(ModeCore.pincerPos);
                    action.run(opModeIsActive(), gamepad2, new ActionOP.Arm(armCore)
                                    .setTicks(ModeCore.armTarget)
                                    .setVelocity(ModeCore.armVelocity),
                            new ActionOP.Wrist(servoCore)
                                    .setTicks(ModeCore.wristTarget)
                                    .setVelocity(ModeCore.wristVelocity));
                    modeCore.MODE = ModeCore.RUNNING_MODE.NORMAL_MODE;
                    //This boolean decides whether or not to give control back to the driver
                    break;
            }
        }
    }

    public void determineWristPos(){
        DcMotorEx wristMotor = hardwareMap.get(DcMotorEx.class, "wristmotor");
        if (fwd) wristMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        else wristMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wristMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        double wristPwr = ((gamepad1.right_trigger - gamepad1.left_trigger) * wristReducer);
        wristMotor.setPower(wristPwr);
        double wristVel = wristMotor.getVelocity();
        double wristPos = wristMotor.getCurrentPosition();
        doubleTele.addData("Wrist Pow: ", wristPwr);
        doubleTele.addData("Wrist Vel: ", wristVel);
        doubleTele.addData("Wrist Pos: ", wristPos);
        doubleTele.update();
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}