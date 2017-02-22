package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * @author Robert Aburustum & Wen Plotnick
 * @version 2/22/17
 */

public class Bot
{
    // Instance Fields - declaration of hardware and software fields

    // Motor declaration
    private DcMotor FL;
    private DcMotor BL;
    private DcMotor FR;
    private DcMotor BR;
    private DcMotor elevator;
    private DcMotor shooter;
    private DcMotor leftCap;
    private DcMotor rightCap;

    // Color sensor declaration
    private ColorSensor colorSensorRight;
    private ColorSensor colorSensorLeft;
    private ColorSensor colorSensorIntake;

    // Optical distance sensor declaration
    private OpticalDistanceSensor opticalLineFinder;
    private OpticalDistanceSensor opticalWallFinder;

    /*
    Servo declaration
    Hardware wise, these are actually CRServos. However, we find it easier to use the servo.setPosition method
    Since a CRServo is just a servo that always thinks it's at the center, this works
     */

    private Servo lServo;
    private Servo rServo;
    private Servo intakeServo;

    // Booleans which hold current driving data
    private boolean runningToTarget;
    private boolean strafing;

    // Integers which hold the encoder target tick data [Public to shorten speedup/slowdown in autons]
    public int FRtarget;
    public int BRtarget;
    public int FLtarget;
    public int BLtarget;

    // Holds the max ticks the cap motors can run to.
    private int maxCapTicks;

    // HardwareMap to hold data as to where each hardware device is located.
    private HardwareMap hwMap;

    // Constructor(s) - delcaration of constructor methods (Empty as unnecessary in this class)
    public Bot()
    {

    }

    // Initialization Method - initialize all fields to their corresponding hardware devices
    public void init (HardwareMap hwm)
    {
        hwMap = hwm;

        // Initializing the motors/sensors
        FL = hwMap.dcMotor.get("FL");
        BL = hwMap.dcMotor.get("BL");
        FR = hwMap.dcMotor.get("FR");
        BR = hwMap.dcMotor.get("BR");
        elevator = hwMap.dcMotor.get("elevator");
        shooter = hwMap.dcMotor.get("shooter");
        leftCap = hwMap.dcMotor.get("leftCap");
        rightCap = hwMap.dcMotor.get("rightCap");

        lServo = hwMap.servo.get("lServo");
        rServo = hwMap.servo.get("rServo");
        intakeServo = hwMap.servo.get("intakeServo");

        /**
         * Initializing sensors and setting LEDs on/off.
         *
         * In the case of the color sensors we set their i2c addresses away the default because
         * otherwise they would all have the same address. If they all had the same address the
         * program would be unable to distinguish one from another, making them useless.
         */
        colorSensorRight = hwMap.colorSensor.get("colorSensorRight");
        colorSensorRight.setI2cAddress(I2cAddr.create7bit(0x1e)); // 7bit for 0x3c
        colorSensorRight.enableLed(false);

        colorSensorLeft = hwMap.colorSensor.get("colorSensorleft");
        colorSensorLeft.setI2cAddress(I2cAddr.create7bit(0x26)); // 7bit for 0x4c
        colorSensorLeft.enableLed(false);

        colorSensorIntake = hwMap.colorSensor.get("colorSensorIntake");
        colorSensorIntake.setI2cAddress(I2cAddr.create7bit(0x2e)); // 7bit for 0x5c
        colorSensorIntake.enableLed(true);

        opticalLineFinder = hwMap.opticalDistanceSensor.get("opticalLineFinder");
        opticalLineFinder.enableLed(true);

        opticalWallFinder = hwMap.opticalDistanceSensor.get("opticalWallFinder");
        opticalWallFinder.enableLed(true);

        /**
         * Setting the motor run modes to run using encoders or without encoders dependent on
         * whether there is an encoder connected to the motor. Sets the direction based on the
         * orientation of the motors. Also sets the starting powers to 0 to ensure nothing is running
         * during initialization.
         */
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftCap.setDirection(DcMotorSimple.Direction.FORWARD);
        rightCap.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set these to Stop and Reset so that we can check their encoder ticks later.
        leftCap.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightCap.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        elevator.setPower(0);
        shooter.setPower(0);
        leftCap.setPower(0);
        rightCap.setPower(0);

        leftServoStop();
        rightServoStop();
        intakeServoStop();

        // Initialize booleans to false as the bot does not start running to a target or strafing.
        runningToTarget = false;
        strafing = false;

        //maxCapTicks = (int)(1120 * (317.5 / 39.8982267));
        maxCapTicks = 10;
    }

    /**
     * This is one of three public normal driving functions. This one is the normal drive function
     * which chooses a direction to drive then calls the appropriate function to set the motor powers.
     * @param direction - the direction of choice
     * @param power - the power to set the motors to
     */
    public void drive(int direction, double power)
    {
        if (direction == 0){driveForwards(power);}
        else if (direction == 1){driveBackwards(power);}
        else if (direction == 2){driveLeft(power);}
        else if (direction == 3){driveRight(power);}
        else if (direction == 4){turnRight(power);}
        else if (direction == 5){turnLeft(power);}
        else if (direction == 6){driveLeftTrain(power);}
        else if (direction == 7){driveRightTrain(power);}
        else if (direction == 8){driveDiagLeft(power);}
        else if (direction == 9){driveDiagRight(power);}
    }

    /**
     * In drive invert, the opposite driving, such as opposite drive trains, are centered around a specific value
     * This allows for ease of use with the invert method of driving, as the invert can add/subtract 1.
     * @param direction - the direction of choice
     * @param power - the power to set the motors to
     */
    public void driveInvert(int direction, double power)
    {
        if (direction == 0){driveForwards(power);}
        else if (direction == 2){driveBackwards(power);}
        else if (direction == 3){driveLeft(power);}
        else if (direction == 5){driveRight(power);}
        else if (direction == 6){turnRight(power);}
        else if (direction == 8){turnLeft(power);}
        else if (direction == 9){driveLeftTrain(power);}
        else if (direction == 10){driveLeftTrain(0);driveRightTrain(0);}
        else if (direction == 11){driveRightTrain(power);}
        else if (direction == 12){driveDiagLeft(power);}
        else if (direction == 14){driveDiagRight(power);}
    }

    /**
     * The third public normal driving function. This one just sets the powers of all motors to 0
     * and resets the strafing boolean.
     */
    public void stopMovement()
    {
        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        strafing = false;
    }

    // Direction 0 - sets all motors to the same power (drive forwards)
    private void driveForwards(double power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // Direction 1 - sets all motors to the same negative power (drive backwards)
    private void driveBackwards(double power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    /**
     * Direction 2 - sets front left and back right to negative powers. Sets back left and front
     * right to positive powers. (strafe right)
     */
    private void driveLeft(double power)
    {
        FL.setPower(-power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(-power);
        strafing = true;
    }

    /**
     * Direction 3 - sets front left and back right to positive powers. Sets back left and front
     * right to negative powers. (strafe left)
     */
    private void driveRight(double power)
    {
        FL.setPower(power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(power);
        strafing = true;
    }

    // Direction 4 - sets left motors to positive powers and right motors to negative powers (turn right)
    private void turnRight(double power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // Direction 5 - sets left motors to negative powers and right motors to positive powers (turn left)
    private void turnLeft(double power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // Direction 6 - sets the left motors to negative powers. (drive left side)
    private void driveLeftTrain(double power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
    }

    // Direction 7 - sets the right motors to negative powers. (drive right side)
    private void driveRightTrain(double power)
    {
        FR.setPower(-power);
        BR.setPower(-power);
    }

    /**
     * Direction 8 - sets the front left and back right motors to positive powers. (drive diagonally
     * forward to the right)
     */
    private void driveDiagRight(double power)
    {
        FL.setPower(power);
        BR.setPower(power);
    }

    /**
     * Direction 9 - sets the front right and back left motors to positive powers. (drive diagonally
     * forward to the left)
     */
    private void driveDiagLeft(double power)
    {
        FR.setPower(power);
        BL.setPower(power);
    }

    // Move-to Methods - methods that allow the bot to run to a specific position

    /**
     * Runs the robot forward to a target position provided by the caller.
     * @param target - the distance in centimeters the bot should drive.
     */
    public void runToPosition(double target)
    {
        // Converts input centimeters to encoder ticks.
        int targetInt = distanceToRevs(target);

        // Resets encoder values and sets running to target to true
        stopAndReset();

        // Sets the drive motors to run to position run mode
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets the target int as the targets.
        FLtarget = targetInt;
        BLtarget = targetInt;
        FRtarget = targetInt;
        BRtarget = targetInt;

        // Provides the motors with the target ints
        FL.setTargetPosition(FLtarget);
        BL.setTargetPosition(BLtarget);
        FR.setTargetPosition(FRtarget);
        BR.setTargetPosition(BRtarget);

        // Gives the motors power to run
        drive(0, 0.1);
    }

    /**
     * Turns the robot to the left a specific amount based on encoder ticks.
     * @param power - the power to run the motors at
     * @param target - the target distance to run the motors in centimeters
     */
    public void runTurnLeft(double power, double target)
    {
        // Converts the input centimeters to encoder ticks
        int targetInt = distanceToRevs(target);

        // Resets the encoder values and sets running to target to true
        stopAndReset();

        // Sets the drive motors to the run to position run mode
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets the left side target ints to negative values and the right side target ints to positive values
        FLtarget = -targetInt;
        BLtarget = -targetInt;
        FRtarget = targetInt;
        BRtarget = targetInt;

        // Provides the motors with the targets
        FL.setTargetPosition(FLtarget);
        BL.setTargetPosition(BLtarget);
        FR.setTargetPosition(FRtarget);
        BR.setTargetPosition(BRtarget);

        // Sends power to the motors
        drive(0, power);
    }

    /**
     * Turns the robot to the right a specific amount based on encoder ticks.
     * @param power - the power to run the motors at
     * @param target - the target distance to run the motors in centimeters
     */
    public void runTurnRight(double power, double target)
    {
        // Converts the input centimeters to encoder ticks
        int targetInt = distanceToRevs(target);

        // Resets the encoder values and sets running to target to true
        stopAndReset();

        // Sets the drive motors to the run to position run mode
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets the right side target ints to negative values and the left side target ints to positive values
        FLtarget = targetInt;
        BLtarget = targetInt;
        FRtarget = -targetInt;
        BRtarget = -targetInt;

        // Provides the motors with the targets
        FL.setTargetPosition(FLtarget);
        BL.setTargetPosition(BLtarget);
        FR.setTargetPosition(FRtarget);
        BR.setTargetPosition(BRtarget);

        // Sends power to the motors
        drive(0, power);
    }

    public void runToLeft(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = -targetInt;
        BLtarget = targetInt;
        FRtarget = targetInt;
        BRtarget = -targetInt;

        FL.setTargetPosition(-targetInt);
        BL.setTargetPosition(targetInt);
        FR.setTargetPosition(targetInt);
        BR.setTargetPosition(-targetInt);

        drive (0,power);
    }

    public void runToRight(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = targetInt;
        BLtarget = -targetInt;
        FRtarget = -targetInt;
        BRtarget = targetInt;

        FL.setTargetPosition(targetInt);
        BL.setTargetPosition(-targetInt);
        FR.setTargetPosition(-targetInt);
        BR.setTargetPosition(targetInt);

        drive (0,power);
    }

    public void runDiagLeft(double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = 0;
        BLtarget = targetInt;
        FRtarget = targetInt;
        BRtarget = 0;

        FL.setTargetPosition(0);
        BL.setTargetPosition(targetInt);
        FR.setTargetPosition(targetInt);
        BR.setTargetPosition(0);

        FL.setPower(1);
        BL.setPower(1);
        FR.setPower(1);
        BR.setPower(1);
    }

    public void runDiagRight(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = targetInt;
        BLtarget = 0;
        FRtarget = 0;
        BRtarget = targetInt;

        FL.setTargetPosition(targetInt);
        BL.setTargetPosition(0);
        FR.setTargetPosition(0);
        BR.setTargetPosition(targetInt);

        drive(0, power);
    }

    /**
     * Idles the thread for 1/2 a second then sets running to target to true and sets the run modes
     * of the drive motors to stop and reset encoder. This resets the encoder values and prepares the
     * motors for the next run to position command.
     */
    private void stopAndReset()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
        runningToTarget = true;
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Lets the thread idle for 1/2 a second before setting the motor's run modes to run using
     * encoders. Also says that the bot isn't running to a target.
     */
    public void runUsingEncoders()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
        runningToTarget = false;
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void chill()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
    }

    // Shoot/Elevate methods - take an input power and set the motors accordingly
    public void setElevator(int power)
    {
        elevator.setPower(power);
    }
    public void  setShooter(int power)
    {
        shooter.setPower(power);
    }

    /**
     * These are the values that make the servo move in and out
     * Since these "servos" are really CRServos, when we tell them to move to a position
     * They always think they are at the center, and thus continue to move.
     * They are not perfectly centered however, which is why the stop value for the servos
     * is not necessarily .50
     * The values were found by having a teleop which adjusted a variable by .01 whenever
     * a button was pressed, and the servo then given that position.
     */
    public void leftServoOut()
    {
        lServo.setPosition(.55);
    }
    public void leftServoIn()
    {
        lServo.setPosition(.40);
    }
    public void leftServoStop()
    {
        lServo.setPosition(.48);
    }

    public void rightServoOut()
    {
        rServo.setPosition(.66);
    }
    public void rightServoIn()
    {
        rServo.setPosition(.34);
    }
    public void rightServoStop()
    {
        rServo.setPosition(.50);
    }

    /*
    This is a set of servo methods that are used to easily switch between the servos during invert mode
    Depending on the value of invert, each method will call the respective motor that
    will result in the buttons being in the correct orientation
     */
    public void servoOut(int i) {
        if (i == 1) {
            this.rightServoOut();
        } else if (i == -1) {
            this.leftServoOut();
        } else {
            ;
        }
    }

    public void servoIn(int i) {
        if (i == 1) {
            this.rightServoIn();
        } else if (i == -1) {
            this.leftServoIn();
        } else {
            ;
        }
    }

    public void servoStop(int i) {
        if (i == 1) {
            this.rightServoStop();
        } else if (i == -1) {
            this.leftServoStop();
        } else {
            ;
        }
    }

    public void intakeServoOut() { intakeServo.setPosition(.61); }
    public void intakeServoIn() { intakeServo.setPosition(.48); }
    public void intakeServoStop() { intakeServo.setPosition(0.55); }

    // Sensor Methods
    // Returns the red and blue values from the respective color sensors
    public int getRed()
    {
        return colorSensorLeft.red();
    }
    public int getBlue()
    {
        return colorSensorRight.blue();
    }

    // Takes a reading from the intake color sensor and returns whether it is red, blue or neither
    public String getIntake()
    {
        if (colorSensorIntake.blue() > colorSensorIntake.red() && colorSensorIntake.blue() > 3)
        {
            return "blue";
        }
        else if (colorSensorIntake.red() > colorSensorIntake.blue() && colorSensorIntake.red() > 3)
        {
            return "red";
        }
        else
        {
            return "";
        }
    }

    // Get the readings from the wall and line optical distance sensors
    public double getLineLight() { return opticalLineFinder.getRawLightDetected(); }
    public double getWallDistance() { return opticalWallFinder.getLightDetected(); }

    // Cap Methods

    /**
     * Set the runmodes the the cap lifts to run using encoders. This way they can run during endgame.
     */
    public void primeCaps()
    {
        leftCap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightCap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Run the cap motors up.
     */
    public void liftCap()
    {
        leftCap.setPower(.5);
        rightCap.setPower(.5);
    }

    /**
     * Run the cap motors down.
     */
    public void lowerCap()
    {
        leftCap.setPower(-.5);
        rightCap.setPower(-.5);
    }

    /**
     * Stop the cap motors.
     */
    public void stopLiftCap ()
    {
        leftCap.setPower(0);
        rightCap.setPower(0);
    }

   public boolean getIsCapMaxed()
   {
        if (leftCap.getCurrentPosition() < maxCapTicks && rightCap.getCurrentPosition() < maxCapTicks)
        {
            return false;
        }
       return true;
   }

    // Helper Methods

    /**
     * Takes centimeters and converts to encoder ticks using the cirumference of the wheels and
     * the ticks per rotation of the motor
     * @param distance - distance to be converted in centimeters
     * @return - number of ticks to run the motors for
     */
    public int distanceToRevs (double distance)
    {
        final double wheelCirc = 31.9185813;

        final double gearMotorTickThing = 1220; //neverrest 40 = 1220

        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }

    // Set and get the runningToTarget boolean
    public void setIsRunningToTarget(boolean x)
    {
        runningToTarget = x;
    }
    public boolean getIsRunningToTarget() {return runningToTarget;}

    // Set and get the strafing boolean
    public void setIsStrafing(boolean s) {strafing = s;}
    public boolean getIsStrafing() {return strafing;}

    public int getCurPosFL() {return FL.getCurrentPosition();}
    public int getCurPosBL() {return BL.getCurrentPosition();}
    public int getCurPosFR() {return FR.getCurrentPosition();}
    public int getCurPosBR() {return BR.getCurrentPosition();}
}
