package org.firstinspires.ftc.teamcode.Op;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Motor Test")
public class MotorTest extends LinearOpMode {
    float motorpower;
    float motorpower2;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        DcMotorEx motor2 = hardwareMap.get(DcMotorEx.class, "motor");
        waitForStart();
        while (opModeIsActive()){
            motorpower = (gamepad1.right_trigger - gamepad1.left_trigger);
            motor.setPower(motorpower);
            motorpower2 = (gamepad2.right_trigger - gamepad2.right_trigger);
            motor2.setPower(motorpower2);
        }
    }
}

