package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.DashboardCore;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

@Config
public class Action {
    private ElapsedTime timer = new ElapsedTime();
    public enum DriveDirection {
        FWD,
        REV,
        NW,
        NE,
        STRAFE_LEFT,
        STRAFE_RIGHT,
        TURN_CC,
        TURN_CW,
        SW,
        SE
    }
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();
    public static double driveTol = 25;
    public static double armTol = 10;
    public interface Executable {
        void run();
        double getPeriod();
        boolean getRunCondition();
        void setupParams();
    }
    public static class Drive implements Executable {
        private DriveTestCore driveCore = new DriveTestCore();
        static final double TicksPerRev = 560;
        static final double WheelInches = (75 / 25.4);
        static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);
        private DriveDirection dir;
        private double inches;
        private double velocity;
        public double period;
        int frontLeftTarget;
        int frontRightTarget;
        int backLeftTarget;
        int backRightTarget;
        public boolean canRun = true;

        public Drive setDir(DriveDirection dir){
            this.dir = dir;
            return this;
        }

        public Drive setVelocity(double vel){
            this.velocity = vel;
            return this;
        }

        public Drive setInches(double inches){
            this.inches = inches;
            return this;
        }

        public Drive setPeriod(double period){
            this.period = period;
            return this;
        }

        public Drive setCore(DriveTestCore core){
            this.driveCore = core;
            return this;
        }

        @Override
        public double getPeriod(){
            return this.period;
        }

        @Override
        public boolean getRunCondition(){
            return this.canRun;
        }

        @Override
        public void setupParams(){
            inches *= TicksPerIn;
            frontLeftTarget = driveCore.frontLeft.getCurrentPosition() + (int) inches;
            frontRightTarget = driveCore.frontRight.getCurrentPosition() + (int) inches;
            backLeftTarget = driveCore.backLeft.getCurrentPosition() + (int) inches;
            backRightTarget = driveCore.backRight.getCurrentPosition() + (int) inches;
            switch (dir){
                case NW:
                    driveCore.allTargetPosition(0, frontRightTarget, backLeftTarget, 0);
                    break;
                case FWD:
                    driveCore.allTargetPosition(frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget);
                    break;
                case REV:
                    driveCore.allTargetPosition(-frontLeftTarget, -frontRightTarget, -backLeftTarget, -backRightTarget);
                    break;
                case NE:
                    driveCore.allTargetPosition(frontLeftTarget, 0, 0, backRightTarget);
                    break;
                case STRAFE_LEFT:
                    driveCore.allTargetPosition(-frontLeftTarget, frontRightTarget, backLeftTarget, -backRightTarget);
                    break;
                case STRAFE_RIGHT:
                    driveCore.allTargetPosition(frontLeftTarget, -frontRightTarget, -backLeftTarget, backRightTarget);
                    break;
                case TURN_CC:
                    driveCore.allTargetPosition(-frontLeftTarget, frontRightTarget, -backLeftTarget, backRightTarget);
                    break;
                case TURN_CW:
                    driveCore.allTargetPosition(frontLeftTarget, -frontRightTarget, backLeftTarget, -backRightTarget);
                    break;
                case SW:
                    driveCore.allTargetPosition(-frontLeftTarget, 0, 0, -backRightTarget);
                    break;
                case SE:
                    driveCore.allTargetPosition(0, -frontRightTarget, -backLeftTarget, 0);
                    break;
            }
        }

        @Override
        public void run(){
            int err = Math.abs(frontLeftTarget - driveCore.frontLeft.getCurrentPosition());
            if (err > driveTol){
                driveCore.allMotorVelocity(velocity);
            } else {
                driveCore.allMotorVelocity(0);
                driveCore.allMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
                canRun = false;
            }
        }
    }
    public static class Arm implements Executable{
        public int ticks;
        public double velocity;
        public double period;
        public ArmTestCore armCore;
        boolean canRun = true;

        public Arm setVelocity(double vel){
            this.velocity = vel;
            return this;
        }

        public Arm setTicks(int ticks){
            this.ticks = ticks;
            return this;
        }

        public Arm setPeriod(double period){
            this.period = period;
            return this;
        }

        public Arm setCore(ArmTestCore core){
            this.armCore = core;
            return this;
        }

        @Override
        public double getPeriod() {
            return this.period;
        }

        public boolean getRunCondition(){
            return this.canRun;
        }

        @Override
        public void setupParams(){
            armCore.pvtArm.setTargetPosition(ticks);
            armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        @Override
        public void run(){
            int err = Math.abs(ticks - armCore.pvtArm.getCurrentPosition());

            if (err > armTol){
                armCore.pvtArm.setVelocity(velocity);
            } else {
                armCore.pvtArm.setVelocity(0);
                armCore.pvtArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                canRun = false;
            }
        }
    }

    public void run(Executable... Actions){
        boolean work = true;
        int count = 0;
        for (Executable action: Actions){
            action.setupParams();
        }
        while (work){
            count = 0;
            for (Executable action: Actions){
                if (action.getRunCondition()){
                    action.run();
                } else {
                    for (Executable actionCheck: Actions){
                        dashTele.addData("Run?: ", actionCheck.getRunCondition());
                        dashTele.update();
                        if (actionCheck.getRunCondition()){
                            dashTele.addData("Count: ", count);
                            dashTele.update();
                            count++;
                            dashTele.addData("Count: ", count);
                            dashTele.update();
                            if (count >= Actions.length) work = false;
                        }
                    }
                    break;
                }
            }
        }
    }
}