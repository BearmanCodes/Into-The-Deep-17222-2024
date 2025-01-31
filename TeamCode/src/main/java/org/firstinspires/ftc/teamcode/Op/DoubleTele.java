package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DoubleTele {
    Telemetry telemetry;
    Telemetry dashTele;
    public DoubleTele(Telemetry telemetry, Telemetry dashTele){
        this.telemetry = telemetry;
        this.dashTele = dashTele;
    }
    public void addData(String caption, Object value){
        telemetry.addData(caption, value);
        dashTele.addData(caption, value);
    }
    public void update(){
        telemetry.update();
        dashTele.update();
    }
}