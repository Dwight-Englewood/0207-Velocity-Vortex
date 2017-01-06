package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
    private ColorSensor colorSensor;
    private CRServo lServo;
    private CRServo rServo;

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
        colorSensor = hwMap.colorSensor.get("colorSensor");
        lServo = hwMap.crservo.get("lServo");
        rServo = hwMap.crservo.get("rServo");

        // Set motor/servo modes
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lServo.setDirection(DcMotorSimple.Direction.REVERSE);
        rServo.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    // Driving Methods
    // TODO: brian's idea for driving - implement in teleop program
    public void drive(int direction, int power)
    {
        if (direction == 0){driveForwards(power);}
        else if (direction == 1){driveBackwards(power);}
        else if (direction == 2){driveLeft(power);}
        else if (direction == 3){driveRight(power);}
        else if (direction == 4){turnRight(power);}
        else if (direction == 5){turnLeft(power);}
    }

    // 0
    private void driveForwards(int power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // 1
    private void driveBackwards(int power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // 2
    private void driveLeft(int power)
    {
        FL.setPower(-power);
        BL.setPower(power);
        FR.setPower(power);
        BR.setPower(-power);
    }

    // 3
    private void driveRight(int power)
    {
        FL.setPower(power);
        BL.setPower(-power);
        FR.setPower(-power);
        BR.setPower(power);
    }

    // 4
    private void turnRight(int power)
    {
        FL.setPower(power);
        BL.setPower(power);
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // 5
    private  void turnLeft(int power)
    {
        FL.setPower(-power);
        BL.setPower(-power);
        FR.setPower(power);
        BR.setPower(power);
    }

    // Move-to Methods

    public void runToPosition(int direction, int power, double target)
    {
        int targetInt = distanceToRevs(target);

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
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void runUsingEncoders()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
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
    public void leftServoOut()
    {

    }

    public void leftServoIn()
    {

    }

    public void leftServoStop()
    {
        lServo.setPower(0);
    }

    public void rightServoOut()
    {

    }

    public void rightServoIn()
    {

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

    // Helper Methods
    public static int distanceToRevs (double distance)
    {
        // TODO: REMEASURE THINGIES (CIRCUMFERENCE)
        //MAKE SURE DISTANCE IS GIVEN IN CENTIMETERS
        final double wheelCirc = 31.9185813;
        final double gearMotorTickThing = 833.33;
        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }
}
