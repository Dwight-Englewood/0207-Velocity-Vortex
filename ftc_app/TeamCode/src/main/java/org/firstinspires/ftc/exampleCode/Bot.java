package org.firstinspires.ftc.exampleCode;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by aburur on 2/23/17.
 *
 * Use this class as the base for future robot classes. Extend it to the class you are making.
 */

public abstract class Bot
{
    // Declare your hardware/software fields
    private HardwareMap hwMap;

    // Constructor (Does nothing)
    public Bot(){}

    // Init function - to be used when initializing the bot. (See fleshed out Bot in teamcode for example)
    public abstract void init();

    // Drive function
    public abstract  void drive();

    // Stop driving function (All drive motors --> power 0)
    public abstract void stopMovement();

    // Stop and reset encoders function (Set all drive motors to runmode STOP_AND_RESET_ENCODERS)
    public abstract void stopAndReset();

    // Run using encoders function (Set all drive motors to runmode RUN_USING_ENCODER)
    public abstract void runUsingEncoders();

    /**
     * Takes centimeter input and turns it into encoder ticks. Should be overridden in the actual
     * class as you will have different motor/gear/wheel setup than us.
     */

    public int distanceToRevs (double distance)
    {
        final double wheelCirc = 31.4159;

        final double gearMotorTickThing = 1220; //neverrest 40 = 1220

        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }
}
