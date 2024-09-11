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

package org.firstinspires.ftc.teamcode.Auto.SpikeMark.Red.Left;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.System;
import org.firstinspires.ftc.teamcode.Auto.ArmAutoCore;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.Auto.ServoAutoCore;
import org.firstinspires.ftc.teamcode.Auto.TensorCore;
@Disabled
@Autonomous(name="LeftRedLeft", group="LeftRed")
public class LeftRedLeft extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Red.tflite";

    private static final String[] LABELS = {
            "Red",
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

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(250);



        driveAutoCore.strafeLeft(750, 6, opModeIsActive(), 15); //change this to line up with left tape
        driveAutoCore.fwdDrive(750, 16, opModeIsActive(), 12); //change this to where arm reaches
        armAutoCore.move(500, 1350, opModeIsActive(), 250); //keep this
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly //keep this
        servoAutoCore.lClaw.setPosition(0.23);  //keep this
        sleep(150); //keep this
        armAutoCore.move(500, 150, opModeIsActive(), 250); //keep this
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    private void initialize() {
        driveAutoCore.init(hardwareMap);
        armAutoCore.init(hardwareMap);
        servoAutoCore.init(hardwareMap);
        tensorCore.initTfod(hardwareMap, LABELS, TFOD_MODEL_ASSET);
        x = tensorCore.telemetryTfod(telemetry);
        time.reset();
        while (x == 0 || time.time() >= 6.5){
            x = tensorCore.telemetryTfod(telemetry);
        }
        x = tensorCore.telemetryTfod(telemetry);
        pos = tensorCore.getPos("red", x);
        tensorCore.visionPortal.close();
    }

    public void decide(String position){
        switch (position){
            case "left":

        }
    }
}

