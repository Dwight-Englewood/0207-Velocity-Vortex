package org.firstinspires.ftc.teamcode;

/**
 * Created by aburur on 10/14/16.
 */

public class helperFunctions {
    public static double buttonToPower (boolean isDown) {
        /*This function was designed for the R2 and L2 bumpers (or other buttons). Those buttons return a boolean for down or up.
         This function takes a boolean and returns full power or no power, for true and false, respectively
        */
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

    public static double triggerToFlat (double input)
    {
        /*This function was designed for the R1 and L1 triggers. Those buttons return doubles; however, we just want it to be full power or off.
        This function will take a double, and turn it to full power or no power, depending on the range it is in
        */
        if (input > 0.1) {//0.1, since that will prevent accidental triggers
            return 1.0;
        } else {
            return 0.0;
        }
    } 
}
