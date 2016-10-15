package org.firstinspires.ftc.teamcode;

/**
 * Created by aburur on 10/14/16.
 */

public class helperFunctions {
    public static double triggerToPower (boolean isDown) {
        if (isDown) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
    public static double distanceToRevs (double distance) {
        //MAKE SURE DISTANCE IS GIVEN IN CENTIMETERS
        double wheelSize = 10;  //TODO: This should be changed to the circumference of the wheel, 10 is currently just a filler
        return (distance / wheelSize);
    }
}
