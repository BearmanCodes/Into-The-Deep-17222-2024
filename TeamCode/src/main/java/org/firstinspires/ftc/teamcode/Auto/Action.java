package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    public interface Executable {
        void run();
        double getPeriod();
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
        public void run(){
            inches *= TicksPerIn;
            int frontLeftTarget = driveCore.frontLeft.getCurrentPosition() + (int) inches;
            int frontRightTarget = driveCore.frontRight.getCurrentPosition() + (int) inches;
            int backLeftTarget = driveCore.backLeft.getCurrentPosition() + (int) inches;
            int backRightTarget = driveCore.backRight.getCurrentPosition() + (int) inches;
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
            driveCore.allMotorVelocity(velocity);
        }
    }
    public static class Arm implements Executable{
        public int ticks;
        public double velocity;
        public double period;
        public ArmTestCore armCore;

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

        @Override
        public void run(){
            armCore.pvtArm.setTargetPosition(ticks);
            armCore.pvtArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armCore.pvtArm.setVelocity(velocity);
        }
    }

    public void run(Executable... Actions){
        while (true){
            for (Executable action: Actions){
                action.run();
            }
        }
    }
}