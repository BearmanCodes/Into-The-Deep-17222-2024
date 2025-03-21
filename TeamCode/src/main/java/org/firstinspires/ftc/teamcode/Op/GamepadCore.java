package org.firstinspires.ftc.teamcode.Op;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadCore {
    public static Gamepad gpad1;
    public static Gamepad gpad2;

    public GamepadCore(Gamepad gamepad1, Gamepad gamepad2){
        gpad1 = gamepad1;
        gpad2 = gamepad2;
    }

    public Gamepad currentGamepad = new Gamepad();
    public Gamepad previousGamepad = new Gamepad();

    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();
    public void edgeDetector(Gamepad gamepad1, Gamepad gamepad2) throws RobotCoreException {
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);
    }
}
