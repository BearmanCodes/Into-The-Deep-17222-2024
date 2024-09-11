/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Auto.SpikeMark.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto.ArmAutoCore;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.Auto.ServoAutoCore;
import org.firstinspires.ftc.teamcode.Auto.TensorCore;

//COMPLETE
@Autonomous(name="RightBlue", group="Blue")
public class RightBlueCore extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Red.tflite";
    private static final String[] LABELS = {
            "Blue",
    };

    DriveAutoCore driveAutoCore = new DriveAutoCore();
    ArmAutoCore armAutoCore = new ArmAutoCore();
    ServoAutoCore servoAutoCore = new ServoAutoCore();
    TensorCore tensorCore = new TensorCore();

    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    double x;

    String pos;


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        x = tensorCore.telemetryTfod(telemetry);
        time.reset();
        double t = time.time();
        while (x == 0){
            x = tensorCore.telemetryTfod(telemetry);
            //t = time.time();
            //if (t >= 7){
            //  break;
            //}
            //telemetry.addData("time", t);
            telemetry.addData("x", x);
            telemetry.update();
        }
        x = tensorCore.telemetryTfod(telemetry);
        pos = tensorCore.getPos("rblue", x);
        tensorCore.visionPortal.close();
        telemetry.addData("Pos: ", x);
        telemetry.update();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(150);

        decide(pos);
        //Middle around 302x
        //Right around 526 x
        //Left around 33x
    }

    private void initialize() {
        driveAutoCore.init(hardwareMap);
        armAutoCore.init(hardwareMap);
        servoAutoCore.init(hardwareMap);
        tensorCore.initTfod(hardwareMap, LABELS, TFOD_MODEL_ASSET);

    }

    public void decide(String position) throws InterruptedException {
        switch (position){
            case "left":
                LeftGo();
                break;
            case "right":
                RightGo();
                break;
            case "middle":
                MiddleGo();
        }
    }

    public void LeftGo() throws InterruptedException {
        driveAutoCore.imu.resetYaw();
        driveAutoCore.strafeRight(1500, 5 + 3, opModeIsActive(), 12);
        driveAutoCore.fwdDrive(1500, 17, opModeIsActive(), 15);
        driveAutoCore.turnAmount(70, opModeIsActive(), telemetry);
        driveAutoCore.fwdDrive(1000, 13, opModeIsActive(), 23);
        driveAutoCore.revDrive(3000, 13, opModeIsActive(), 250);   //NOT EVEN SLIGHTLY CONFIDENT ON THIS ONE
        driveAutoCore.turnAmount(-1, opModeIsActive(), telemetry);
        driveAutoCore.revDrive(1500, 15, opModeIsActive(), 0); //change
        driveAutoCore.strafeLeft(3000, 70 + 6.75, opModeIsActive(), 250);
        driveAutoCore.fwdDrive(1500, 18 - 6.785, opModeIsActive(), 0); //change
        driveAutoCore.turnAmount(90, opModeIsActive(), telemetry);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 0); //change
        armAutoCore.move(1000, armAutoCore.armBoard, opModeIsActive(), 500);
        servoAutoCore.lClaw.setPosition(0.65);
        armAutoCore.move(550, 15, opModeIsActive(), 0);
        driveAutoCore.strafeRight(1500, 28 +6.25, opModeIsActive(), 0);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 250);
        sleep(150);
    }

    public void RightGo() throws InterruptedException{
        driveAutoCore.imu.resetYaw();
        driveAutoCore.strafeRight(1500, 5 + 6.5, opModeIsActive(), 12);
        driveAutoCore.fwdDrive(1500, 25, opModeIsActive(), 15);
        driveAutoCore.revDrive(1500, 23, opModeIsActive(), 23);
        driveAutoCore.strafeLeft(3000, 70 + 10.25, opModeIsActive(), 250);
        driveAutoCore.fwdDrive(1500, 18 + 7.1, opModeIsActive(), 0); //change
        driveAutoCore.turnAmount(90, opModeIsActive(), telemetry);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 0); //change
        armAutoCore.move(1000, armAutoCore.armBoard, opModeIsActive(), 500);
        servoAutoCore.lClaw.setPosition(0.65);
        armAutoCore.move(550, 15, opModeIsActive(), 0);
        driveAutoCore.strafeRight(1500, 28 - 7, opModeIsActive(), 0);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 250);
        sleep(150);
    }

    public void MiddleGo() throws InterruptedException{
        driveAutoCore.imu.resetYaw();
        driveAutoCore.strafeRight(1500, 5, opModeIsActive(), 12);
        driveAutoCore.fwdDrive(1500, 30, opModeIsActive(), 15);
        driveAutoCore.revDrive(1500, 28, opModeIsActive(), 23);
        driveAutoCore.strafeLeft(3000, 70, opModeIsActive(), 250);
        driveAutoCore.fwdDrive(1500, 18, opModeIsActive(), 0); //change
        driveAutoCore.turnAmount(90, opModeIsActive(), telemetry);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 0); //change
        armAutoCore.move(1000, armAutoCore.armBoard, opModeIsActive(), 500);
        servoAutoCore.lClaw.setPosition(0.65);
        armAutoCore.move(550, 15, opModeIsActive(), 0);
        driveAutoCore.strafeRight(1500, 28, opModeIsActive(), 0);
        driveAutoCore.fwdDrive(1500, 10, opModeIsActive(), 250);
        sleep(150);
    }
}
