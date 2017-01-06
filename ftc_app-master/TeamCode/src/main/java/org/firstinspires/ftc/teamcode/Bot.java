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

    // Movement Methods - make one public drive and a bunch of smaller for each specific drive. used the drive method to choose which one will be used
    // TODO: brian's idea for driving - implement in teleop program
    public void drive(int direction, int power)
    {

    }

    public void driveForward(int power)
    {

    }

    public void driveBackwards(int power)
    {

    }

    public void driveLeft(int power)
    {

    }

    public void driveRight(int power)
    {

    }

    public void driveForwardLeft(int power)
    {

    }

    public void driveBackwardLeft(int power)
    {

    }

    public void driveForwardRight(int power)
    {

    }

    public void driveBackwardRight(int power)
    {

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
}
