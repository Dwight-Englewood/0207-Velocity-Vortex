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
    public static double increaseToOne (double input)
    {
        /*This function was designed for the R1 and L1 triggers. Those buttons return doubles; however, we just want
        it to be full power or off.
        turns inputs of less than 1 into inputs of one, as long as they are greater than 0.1
        */
        if (input > 0.1) {//0.1, since that will prevent accidental triggers
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
