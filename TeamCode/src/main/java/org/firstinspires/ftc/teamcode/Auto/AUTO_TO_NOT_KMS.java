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

        drivetrainCore.strafeRight(800, 30, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(800, 13, opModeIsActive(), 250);
        armCore.pvtMove(250, 500, opModeIsActive(), 250, telemetry);
        servoCore.claw3.setPosition(0.6); //Unfold claw
        servoCore.claw4.setPosition(0.1); //Do pivot
        armCore.pvtMove(250, 2500, opModeIsActive(), 250, telemetry);
        drivetrainCore.fwdDrive(800, 11.5, opModeIsActive(), 250);
        armCore.pvtMove(800, 2000, opModeIsActive(), 250, telemetry);
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
