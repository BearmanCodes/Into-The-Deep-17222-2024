package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "PID TUNING", group = "TUNING")
public class PID_TUNING extends LinearOpMode {
    public static int errTolerance = 10;
    public static double kP, kI, kD, kF = 1;
    public static int target = 500;
    boolean tarStat = false;
    ArmCore armCore = new ArmCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    PIDFCore pidfCore = new PIDFCore(kP, kI, kD, kF, telemetry, dashTele);

    @Override
    public void runOpMode() throws InterruptedException {
        armCore.init(hardwareMap);
        boolean finished = false;
        armCore.pvtArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armCore.pvtArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pidfCore.setTarget(target);
        waitForStart();
        while (opModeIsActive() && !finished){
            int armCurrPos = armCore.pvtArm.getCurrentPosition(); //Keeps var of arm pos for ref
            int err = Math.abs(armCurrPos - target); //amount of ticks to go to target
            dashTele.addData("Actual Position: ", armCurrPos);
            double power = pidfCore.build(err);
            armCore.pvtArm.setPower(power);
            //This boolean decides whether or not to give control back to the driver
            if (err <= errTolerance) {
                armCore.pvtArm.setPower(0);
                finished = true;
            }
        }
    }
}
