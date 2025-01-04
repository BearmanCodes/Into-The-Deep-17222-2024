package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RED RIGHT", group = "BLUE")
public class RED_RIGHT extends LinearOpMode {
    ArmAutoCore armCore = new ArmAutoCore();
    DriveAutoCore drivetrainCore = new DriveAutoCore();
    ServoAutoCore servoCore = new ServoAutoCore();


    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        waitForStart();

        //drivetrainCore.strafeRight(800, 36, opModeIsActive(), 0);

        drivetrainCore.fwdDrive(800, 20.5, opModeIsActive(), 250);
        armCore.pvtMove(800, 1250, opModeIsActive(), 250, telemetry);
        servoCore.claw3.setPosition(0);
        drivetrainCore.fwdDrive(800, 2, opModeIsActive(), 250);
        servoCore.claw3.getController().pwmDisable();
        servoCore.claw1.setPosition(0.04);
        servoCore.claw2.setPosition(0.04);
        sleep(100);
        armCore.pvtMove(800, 25, opModeIsActive(), 250, telemetry);
        //drivetrainCore.fwdDrive(1000, 10.5, opModeIsActive(), 250);
        drivetrainCore.revDrive(1000, 10, opModeIsActive(), 250);
        servoCore.claw3.getController().pwmEnable();
        servoCore.claw3.setPosition(1);
        drivetrainCore.strafeRight(1000, 26, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(1000, 37, opModeIsActive(), 250);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), 250);
        drivetrainCore.revDrive(1000, 45, opModeIsActive(), 250);
        drivetrainCore.fwdDrive(1000, 45, opModeIsActive(), 250);
        drivetrainCore.strafeRight(1000, 10, opModeIsActive(), 250);
        drivetrainCore.revDrive(1000, 45, opModeIsActive(), 250);
    }

    private void Init(){
        armCore.init(hardwareMap);
        servoCore.init(hardwareMap);
        drivetrainCore.init(hardwareMap);
    }
}
