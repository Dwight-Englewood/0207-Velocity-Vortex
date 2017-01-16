package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aburur on 1/5/17.
 */

public class Bot
{
    //Instance Fields
    private DcMotor FL;
    private DcMotor BL;
    private DcMotor FR;
    private DcMotor BR;
    private DcMotor elevator;
    private DcMotor shooter;
    //private ColorSensor colorSensor;
    //private CRServo lServo;
    //private CRServo rServo;

    private boolean runningToTarget;
    private boolean strafing;

    HardwareMap hwMap;
    //Class Fields

    //Constructor(s)
    public Bot()
    {

    }

    // Initialization Method
    public void init (HardwareMap hwm)
    {
        hwMap = hwm;

        // Defining the motors/sensors/etc.
        FL = hwMap.dcMotor.get("FL");
        BL = hwMap.dcMotor.get("BL");
        FR = hwMap.dcMotor.get("FR");
        BR = hwMap.dcMotor.get("BR");
        elevator = hwMap.dcMotor.get("elevator");
        shooter = hwMap.dcMotor.get("shooter");
        //colorSensor = hwMap.colorSensor.get("colorSensor");
        //lServo = hwMap.crservo.get("lServo");
        //rServo = hwMap.crservo.get("rServo");

        // Set motor/servo modes
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

        //lServo.setDirection(DcMotorSimple.Direction.REVERSE);
        //rServo.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        elevator.setPower(0);
        shooter.setPower(0);
        //lServo.setPower(0);
        //rServo.setPower(0);

        // Running to target false
        runningToTarget = false;

        // Set color sensor LED
        //colorSensor.enableLed(false);
    }

    // Driving Methods
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
    }

    public void drive()
    {
        FL.setPower(0);
        BL.setPower(0);
        FR.setPower(0);
        BR.setPower(0);
        strafing = false;
    }

    // 0
    private void driveForwards(double power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // 1
    private void driveBackwards(double power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // 2
    private void driveLeft(double power)
    {
        FL.setPower(-power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(-power);
        strafing = true;
    }

    // 3
    private void driveRight(double power)
    {
        FL.setPower(power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(power);
        strafing = true;
    }

    // 4
    private void turnRight(double power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // 5
    private void turnLeft(double power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // 6
    private void driveLeftTrain(double power)
    {
        FL.setPower(power);
        BL.setPower(power);
    }

    // 7
    private void driveRightTrain(double power)
    {
        FR.setPower(power);
        BR.setPower(power);
    }

    // Move-to Methods

    public void runToPosition(int direction, double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FL.setTargetPosition(targetInt);
        BL.setTargetPosition(targetInt);
        FR.setTargetPosition(targetInt);
        BR.setTargetPosition(targetInt);

        drive(direction, power);
    }

    private void stopAndReset()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
        runningToTarget = true;
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runUsingEncoders()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
        runningToTarget = false;
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Motor Methods
    public void setElevator(int power)
    {
        elevator.setPower(power);
    }

    public void  setShooter(int power)
    {
        shooter.setPower(power);
    }

    // Servo Methods
    /*public void leftServoOut()
    {
        lServo.setDirection(DcMotorSimple.Direction.REVERSE);
        lServo.setPower(1.0);
    }

    public void leftServoIn()
    {
        lServo.setDirection(DcMotorSimple.Direction.FORWARD);
        lServo.setPower(1.0);
    }

    public void leftServoStop()
    {
        lServo.setPower(0);
    }

    public void rightServoOut()
    {
        rServo.setDirection(DcMotorSimple.Direction.FORWARD);
        rServo.setPower(1.0);
    }

    public void rightServoIn()
    {
        rServo.setDirection(DcMotorSimple.Direction.REVERSE);
        rServo.setPower(1.0);
    }

    public void rightServoStop()
    {
        rServo.setPower(0);
    }

    // Sensor Methods
    public int getRed()
    {
        return colorSensor.red();
    }

    public int getBlue()
    {
        return colorSensor.blue();
    }
    */
    // Helper Methods
    public int distanceToRevs (double distance)
    {
        // TODO: REMEASURE THINGIES (CIRCUMFERENCE)
        //MAKE SURE DISTANCE IS GIVEN IN CENTIMETERS
        final double wheelCirc = 31.9185813;
        final double gearMotorTickThing = 833.33;
        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }

    public boolean getIsRunningToTarget() {return runningToTarget;}

    public void setIsStrafing(boolean s) {strafing = s;}
    public boolean getIsStrafing() {return strafing;}
}
