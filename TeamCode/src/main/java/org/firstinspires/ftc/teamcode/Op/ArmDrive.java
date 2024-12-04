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
            servoCore.dpadRun(servoCore.currentGamepad2, servoCore.previousGamepad2);
            drivetrainCore.run(gamepad1);

            dashTele.addData("We are: ", "Running");
            dashTele.addData("Arm Position: ", armCore.pvtArm.getCurrentPosition());
            dashTele.addData("Motor Power: ", armCore.pvtPower);
            dashTele.update();
            telemetry.addData("We are: ", "Running");
            telemetry.addData("Arm Position: ", armCore.pvtArm.getCurrentPosition());
            telemetry.addData("Motor Power: ", armCore.pvtPower);
            telemetry.update();
        }
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}