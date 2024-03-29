package org.firstinspires.ftc.oldFiles;

/**
 * Created by plotnw on 10/14/16.
 */

public class helperFunction {
    public static double buttonToPower (boolean isDown) {
        /*This function was designed for the right and left bumpers but can be used on any button that returns a boolean.
         This function takes a boolean and returns full power or no power, for true and false, respectively
        */
          if (isDown) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
    public static int distanceToRevs (double distance) {
        //MAKE SURE DISTANCE IS GIVEN IN CENTIMETERS
        final double wheelCirc = 31.9185813;
        final double gearMotorTickThing = 833.33;
        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }

    public static double triggerToFlat (double input)
    {
        /*This function was designed for the Right and Left triggers. Those buttons return doubles; however, we just want it to be full power or off.
        This function will take a double, and turn it to full power or no power, depending on the range it is in
        */
        if (input > 0.1) {//0.1, since that will prevent accidental triggers
            return 1.0;
        } else if (input < -.1) {
            return -1.0;
        }
        return 0;
    }

}