package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp(name="Hi :P")
public class COreHedx extends LinearOpMode {
    float motorPower;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx Motor = hardwareMap.get(DcMotorEx.class,"Motor");
        waitForStart();
        while(opModeIsActive()){
            motorPower = (gamepad1.right_trigger - gamepad1.left_trigger);
            Motor.setPower(motorPower);
        }
    }
}