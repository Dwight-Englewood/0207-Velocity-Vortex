package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Plotnw on 10/19/2016.
 */

public class gridFunctions {
    public static void moveByVector (double xComp, double yComp, DcMotor right, DcMotor left) {
        //We assume the robot is looking on vector i, we move on vector xComp i + yComp j

        double dist = Math.sqrt((Math.pow(xComp, 2.0) + Math.pow(yComp, 2.0))); //Magnitude
        double angle = Math.atan(yComp / xComp); // Angle to turn

    }
}
