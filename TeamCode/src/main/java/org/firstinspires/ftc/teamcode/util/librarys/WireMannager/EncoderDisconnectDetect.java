package org.firstinspires.ftc.teamcode.util.librarys.WireMannager;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class EncoderDisconnectDetect {
    protected static MotorEx encoderMotor;
    protected ArrayList<Integer> encoderValues = new ArrayList<>();
    protected static boolean encoderDisconnected = false;

    public EncoderDisconnectDetect(MotorEx encoderMotor){
        this.encoderMotor = encoderMotor;
    }

    public void recordEncoderValues() {
        encoderValues.add(encoderMotor.getCurrentPosition());

        if (encoderValues.size() > 3) {
            encoderValues.remove(0);
        }

        if (encoderMotor.getVelocity() != 0 && encoderMotor.getCurrentPosition() == 0 && encoderValues.get(0) == 0) {
            encoderDisconnected = true;
        }


        if (encoderValues.size() == 3) {
            if (encoderValues.get(2) == 0) {
                if (abs(encoderValues.get(0)-encoderValues.get(2)) > 50) {
                    encoderDisconnected = true;
                }
            }
        }
    }

    public boolean isEncoderDisconnected(){
        return encoderDisconnected;
    }
}
