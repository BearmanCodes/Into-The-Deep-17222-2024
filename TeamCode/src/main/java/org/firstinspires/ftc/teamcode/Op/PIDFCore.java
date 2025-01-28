package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDFCore {
    private ElapsedTime time = new ElapsedTime();
    private double kP, kI, kD, kF;
    private double P, I, D, F, iSum;
    private int target, motorPos, error;
    private double output, seconds;
    private final double TICKS_PER_REV = 537.7; //look up for gobuilda motor
    private final double GEAR_REDUCTION = 28; //2nd teeth gears divided by 1st teeth gears
    private final double TICKS_PER_GEARS = TICKS_PER_REV * GEAR_REDUCTION;
    private final double TICKS_PER_DEGREE = TICKS_PER_GEARS / 360;
    private double lastError = 0;
    Telemetry telemetry;
    Telemetry dashTele;
    public PIDFCore(double kP, double kI, double kD, double kF, Telemetry telemetry, Telemetry dashTele){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.telemetry = telemetry;
        this.dashTele = dashTele;
    }

    public PIDFCore setTarget(int target){
        this.target = target;
        time.reset();
        this.iSum = 0;
        return this;
    }

    public void tele(){
        telemetry.addData("PIDF Err: ", this.error);
        telemetry.addData("Seconds: ", this.seconds);
        telemetry.addData("Target: ", this.target);
        telemetry.addData("Output: ", this.output);
        telemetry.addData("Last Error: ", this.lastError);
        dashTele.addData("PIDF Err: ", this.error);
        dashTele.addData("Seconds: ", this.seconds);
        dashTele.addData("Target: ", this.target);
        dashTele.addData("Output: ", this.output);
        dashTele.addData("Last Error: ", this.lastError);
        telemetry.update();
        dashTele.update();
    }

    public double build(int error){
        this.error = error;
        this.seconds = time.seconds();
        P = kP * (this.error);
        I = kI * (iSum += (this.error * this.seconds));
        D = kD * ((this.error - this.lastError) / this.seconds);
        //F = kF * (Math.cos(Math.toRadians(this.target / TICKS_PER_DEGREE)));
        this.output = P + I + D; //+ F;
        this.lastError = error;
        tele();
        return this.output;
    }
}
