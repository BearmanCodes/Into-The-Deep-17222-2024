/* Copyright (c) 2019 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.Auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

//I really don't want to comment this mess AHHHHHHHHH
//This is frankenstined from the ConceptTensorFlowObjectDetection FTC sample

public class TensorCore {


    public TfodProcessor tfod;

    public VisionPortal visionPortal;

    public void initTfod(HardwareMap hardwareMap, String[] labels, String file) {

        tfod = new TfodProcessor.Builder()

                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(file)
                //.setModelFileName(TFOD_MODEL_FILE)

                .setModelLabels(labels)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build(); //I felt it prudent to leave all these options here, I only understand half of it though.

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        builder.setCameraResolution(new Size(640, 480));

        builder.enableLiveView(true);

        builder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);

        builder.setAutoStopLiveView(false);


        builder.addProcessor(tfod);

        visionPortal = builder.build();
        //Please don't change anything above this except the webcam name if needed.
        if (file.contains("Red")) tfod.setMinResultConfidence(0.6f);
        else tfod.setMinResultConfidence(0.8f); //It's much better at detecting red's than blue so you gotta filter out the worst blue predections
    }

    public double telemetryTfod(Telemetry telemetry) {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        double x = 0;
        if (currentRecognitions.size() == 0) x = 0;
        x = 0;
        for (Recognition recognition : currentRecognitions) {
            x = 0;
            x = (recognition.getLeft() + recognition.getRight()) / 2 ; //X is the only important one in this case
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ; //But Y might be needed one day

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            telemetry.update();
            //This telmemetry doesn't really matter but it's nice to see when it recognizes something
            return x;
        }
        return x;
        //I keep setting x to 0 and returning it here, just really emphasizing it's THE important variable
        //And it being 0 means nothing was sensed so..
    }

    public String getPos(String color, double x){
        if (color.equalsIgnoreCase("rred")){ //Positions change based on every field and color
                                                        //variation so I need TWELVE if statements (more efficient way maybe?)
            if (x >= (135 - 50) && x <= (135 + 50)){
                return "left";
            }
            if (x >= (340 - 50) && x <= (340 + 50)){
                return "middle";
            }
            if (x >= (550 - 50) && x <= (550 + 50)){
                return "right";
            }
            //-50 and +50 tolerances for positions
        }
        if (color.equalsIgnoreCase("lred")){
            if (x >= (60 - 50) && x <= (85 + 50)){
                return "left";
            }
            if (x >= (310 - 50) && x <= (310 + 50)){
                return "middle";
            }
            if (x >= (515 - 50) && x <= (515 + 50)) {
                return "right";
            }
        }
        if (color.equalsIgnoreCase("lblue")){
            if (x >= (55 - 50) && x <= (55 + 50)){
                return "left";
            }
            if (x >= (285 - 50) && x <= (285 + 50)){
                return "middle";
            }
            if (x >= (495 - 50) && x <= (495 + 100)) {
                return "right";
            }
        }
        if (color.equalsIgnoreCase("rblue")){
            if (x >= (155 - 50) && x <= (155 + 50)){
                return "left";
            }
            if (x >= (360 - 50) && x <= (360 + 50)){
                return "middle";
            }
            if (x >= (565 - 50) && x <= (565 + 50)){
                return "right";
            }
        }
        return "";
    }

}   // end class
