package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp
public class PID extends LinearOpMode {
    public DcMotorEx motor;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry tele = dashboard.getTelemetry();
    public static double gamepadReducer = 0.25;
    public static boolean fwd = false;
    public static double powerCapper = 0.000257;
    public static double kP = 0.001;
    public static double kI;
    public static double kD = 0.001;
    public static double errTol = 5;
    public final double tpR = 3895.9;
    public double lasterror = 0;
    public double totalErrors = 0;
    public ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    //3895.9 ticks per revolution. Meaning 360 meaning 2pi radians.
    //Motor currently moves cc given positive power because screw you. Means that positive power go cc and negative go cw. why it's target - curr.

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        //while (!isStopRequested()){

       // }
        controller(tpR);
    }

    public void Init(){
        motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (fwd) motor.setDirection(DcMotorSimple.Direction.FORWARD);
        else motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void controller(double target){
        timer.reset();
        double error = target - motor.getCurrentPosition();
        lasterror = error;
        double time = timer.seconds();
        double P = kP * (error);
        double D = kD * ((lasterror - error)/error) * time;
        totalErrors += error * time;
        double I = kI * totalErrors;
        double power = Math.min(Math.max(P + D + I, 0), 1);
        while (error >= errTol && !isStopRequested()) {
            lasterror = error;
            error = target - motor.getCurrentPosition();
            P = kP * (error);
            D = kD * ((lasterror - error)/error) * timer.seconds();
            totalErrors += error * time;
            I = kI * totalErrors;
            power = Math.min(Math.max(P + D + I, 0), 1);
            motor.setPower(power);
            tele.addData("P: ", P);
            tele.addData("D: ", D);
            tele.addData("I: ", I);
            tele.addData("Power: ", power);
            tele.addData("Error: ", error);
            tele.addData("LastError: ", lasterror);
            tele.addData("TotalError: ", totalErrors);
            tele.addData("Pos: ", motor.getCurrentPosition());
            tele.update();
            timer.reset();
        }
        motor.setPower(0);
        tele.addData("P: ", P);
        tele.addData("D: ", D);
        tele.addData("Power: ", power);
        tele.addData("Error: ", error);
        tele.addData("LastError: ", lasterror);
        tele.addData("Pos: ", motor.getCurrentPosition());
        tele.addData("FINISHED: ", true);
        tele.update();
    }

    public void slowGuy(double revs){
        double target = revs * tpR;
        double error = target - motor.getCurrentPosition();
        while (error >= errTol && !isStopRequested()) {
            motor.setPower(0.1);
            error = target - motor.getCurrentPosition();
            tele.addData("Error: ", error);
            tele.addData("Pos: ", motor.getCurrentPosition());
            tele.update();
        }
        motor.setPower(0);
        tele.addData("Error: ", error);
        tele.addData("Pos: ", motor.getCurrentPosition());
        tele.addData("FINISHED: ", true);
        tele.update();
    }

    public void gamer(){
        double power = gamepad1.left_stick_x * gamepadReducer;
        motor.setPower(power);
        tele.addData("Power: ", power);
        tele.update();
    }
}
