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

package org.firstinspires.ftc.teamcode.Auto.SpikeMark.Blue.Left;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.ArmAutoCore;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.Auto.ServoAutoCore;
@Disabled
@Autonomous(name="LeftBlueLeft", group="LeftBlue")
public class LeftBlueLeft extends LinearOpMode {

    DriveAutoCore driveAutoCore = new DriveAutoCore();
    ArmAutoCore armAutoCore = new ArmAutoCore();
    ServoAutoCore servoAutoCore = new ServoAutoCore();


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(250);

        driveAutoCore.strafeLeft(750, 6, opModeIsActive(), 15); //Wherever to line up with left tape
        driveAutoCore.fwdDrive(750, 16, opModeIsActive(), 12); //However much to line up arm
        armAutoCore.move(500, 1350, opModeIsActive(), 250);
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        driveAutoCore.revDrive(750, 13, opModeIsActive(), 12); // Make this whatever we drove forward -2
        driveAutoCore.strafeLeft(2000, 36   , opModeIsActive(), 12); //This is 42 - whatever we strafed left
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    private void initialize() {
        driveAutoCore.init(hardwareMap);
        armAutoCore.init(hardwareMap);
        servoAutoCore.init(hardwareMap);
    }
}

