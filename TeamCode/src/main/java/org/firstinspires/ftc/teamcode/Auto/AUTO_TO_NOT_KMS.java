package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Op.ArmCore;

@Autonomous(name = "Test Auto", group = "Autos")
public class AUTO_TO_NOT_KMS extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();


    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        drivetrainCore.fwdDrive(800, 9, opModeIsActive(), 250);
        servoCore.claw3.setPosition(1);
        servoCore.claw4.setPosition(0.1);
        drivetrainCore.strafeRight(800, 30, opModeIsActive(), 250);
        armCore.pvtMove(500, 1750, opModeIsActive(), 250, telemetry);
        drivetrainCore.revDrive(800, 8.5, opModeIsActive(), 250);
        armCore.pvtMove(800, 0, opModeIsActive(), 250, telemetry);
        servoCore.claw1.setPosition(0.04); //(open)
        servoCore.claw2.setPosition(0.04);
        drivetrainCore.revDrive(800, 8, opModeIsActive(), 250);
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
