package org.firstinspires.ftc.teamcode.Op;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="controller2")
public class cONTROLLER2 extends LinearOpMode {
    float motorpower;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "Motor");
        waitForStart();
        while (opModeIsActive()){
            motorpower = (gamepad2.right_trigger - gamepad2.left_trigger);
            motor.setPower(motorpower);
        }
    }
}

