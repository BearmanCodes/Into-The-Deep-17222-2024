package org.firstinspires.ftc.teamcode.Op;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Main Drive", group="Critical")
public class ArmDrive extends LinearOpMode {
    ArmCore armCore = new ArmCore();
    ServoCore servoCore = new ServoCore();
    DrivetrainCore drivetrainCore = new DrivetrainCore();
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashTele = dashboard.getTelemetry();

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

            armCore.trigger(gamepad2);
            drivetrainCore.run(gamepad1);
            if (armCore.pvtArm.getCurrentPosition() >= 1000
            ){
                servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2);
            } else{
                servoCore.claw3.setPosition(1);
            }
            dashTele.addData("We are: ", "Running");
            dashTele.addData("PVT Position: ", armCore.pvtArm.getCurrentPosition());
            dashTele.addData("PVT Power: ", armCore.pvtPower);
            dashTele.addData("FNDTL Position: ", armCore.fndtlArm.getCurrentPosition());
            dashTele.addData("FNDTL Power: ", armCore.pvtPower);
            dashTele.update();
            telemetry.addData("We are: ", "Running");
            telemetry.addData("PVT Position: ", armCore.pvtArm.getCurrentPosition());
            telemetry.addData("PVT Power: ", armCore.pvtPower);
            telemetry.addData("FNDTL Position: ", armCore.fndtlArm.getCurrentPosition());
            telemetry.addData("FNDTL Power: ", armCore.pvtPower);
            telemetry.addData("SERVO Pos: ", servoCore.claw3.getPosition());
            telemetry.update();
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}