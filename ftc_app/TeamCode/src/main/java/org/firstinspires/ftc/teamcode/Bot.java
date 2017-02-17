package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by aburur on 1/5/17.
 */

public class Bot
{
    // Instance Fields - declaration of hardware and software fields
    private DcMotor FL;
    private DcMotor BL;
    private DcMotor FR;
    private DcMotor BR;
    private DcMotor elevator;
    private DcMotor shooter;
    private DcMotor leftCap;
    private DcMotor rightCap;

    private ColorSensor colorSensorRight;
    private ColorSensor colorSensorLeft;
    private ColorSensor colorSensorIntake;

    private OpticalDistanceSensor opticalLineFinder;
    private OpticalDistanceSensor opticalWallFinder;

    public Servo lServo;
    public Servo rServo;
    public Servo intakeServo;

    private boolean runningToTarget;
    private boolean strafing;

    int FRtarget;
    int BRtarget;
    int FLtarget;
    int BLtarget;

    HardwareMap hwMap;

    // Class Fields

    // Constructor(s) - delcaration of constructor methods (Empty as unnecessary in this class)
    public Bot()
    {

    }

    // Initialization Method - initialize all fields to their corrosponding hardware
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

        // Initializing sensors - setting LEDs on/off
        colorSensorRight = hwMap.colorSensor.get("colorSensorRight");
        colorSensorRight.setI2cAddress(I2cAddr.create7bit(0x1e)); // for 0x3c
        colorSensorRight.enableLed(false);

        colorSensorLeft = hwMap.colorSensor.get("colorSensorleft");
        colorSensorLeft.setI2cAddress(I2cAddr.create7bit(0x26)); // for 0x4c
        colorSensorLeft.enableLed(false);

        colorSensorIntake = hwMap.colorSensor.get("colorSensorIntake");
        //TODO: colorSensorIntake.setI2cAddress() // for 0x5c
        colorSensorIntake.enableLed(true);

        opticalLineFinder = hwMap.opticalDistanceSensor.get("opticalLineFinder");
        opticalLineFinder.enableLed(true);

        opticalWallFinder = hwMap.opticalDistanceSensor.get("opticalWallFinder");
        opticalWallFinder.enableLed(true);
        
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

        leftCap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightCap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        // Initialize booleans to false
        runningToTarget = false;
        strafing = false;
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
        else if (direction == 8){driveDiagLeft(power);}
        else if (direction == 9){driveDiagRight(power);}
    }

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

    public void stopMovement()
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
        FL.setPower(-power);
        BL.setPower(-power);
    }

    // 7
    private void driveRightTrain(double power)
    {
        FR.setPower(-power);
        BR.setPower(-power);
    }

    // 8
    private void driveDiagRight(double power)
    {
        FL.setPower(power);
        BR.setPower(power);
    }

    // 9
    private void driveDiagLeft(double power)
    {
        FR.setPower(power);
        BL.setPower(power);
    }

    // Move-to Methods

    public void runToPosition(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = targetInt;
        BLtarget = targetInt;
        FRtarget = targetInt;
        BRtarget = targetInt;

        FL.setTargetPosition(targetInt);
        BL.setTargetPosition(targetInt);
        FR.setTargetPosition(targetInt);
        BR.setTargetPosition(targetInt);

        drive(0, power);
    }

    public void runTurnLeft(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = -targetInt;
        BLtarget = -targetInt;
        FRtarget = targetInt;
        BRtarget = targetInt;

        FL.setTargetPosition(-targetInt);
        BL.setTargetPosition(-targetInt);
        FR.setTargetPosition(targetInt);
        BR.setTargetPosition(targetInt);

        drive(0, power);
    }

    public void runTurnRight(double power, double target)
    {
        int targetInt = distanceToRevs(target);
        stopAndReset();

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLtarget = targetInt;
        BLtarget = targetInt;
        FRtarget = -targetInt;
        BRtarget = -targetInt;

        FL.setTargetPosition(targetInt);
        BL.setTargetPosition(targetInt);
        FR.setTargetPosition(-targetInt);
        BR.setTargetPosition(-targetInt);

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

    public void chill()
    {
        try {Thread.sleep(500);} catch (InterruptedException e) {}
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
    public int getRed()
    {
        return colorSensorLeft.red();
    }

    public int getBlue()
    {
        return colorSensorRight.blue();
    }

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

    public double getLineLight() { return opticalLineFinder.getRawLightDetected(); }
    public double getWallDistance() { return opticalWallFinder.getLightDetected(); }

    // Cap Methods

    public void liftCap()
    {
        leftCap.setPower(.5);
        rightCap.setPower(.5);
    }

    public void lowerCap()
    {
        leftCap.setPower(-.5);
        rightCap.setPower(-.5);
    }

    public void stopLiftCap ()
    {
        leftCap.setPower(0);
        rightCap.setPower(0);
    }

    // Helper Methods
    public int distanceToRevs (double distance)
    {
        //MAKE SURE DISTANCE IS GIVEN IN CENTIMETERS
        final double wheelCirc = 31.9185813;

        final double gearMotorTickThing = 1220; //neverrest 40 = 1220, 20tooth : 40tooth : 40tooth = 1/2 gear ratio

        return (int)(gearMotorTickThing * (distance / wheelCirc));
    }

    public void setIsRunningToTarget(boolean x)
    {
        runningToTarget = x;
    }
    public boolean getIsRunningToTarget() {return runningToTarget;}

    public void setIsStrafing(boolean s) {strafing = s;}
    public boolean getIsStrafing() {return strafing;}

    public int getCurPosFL() {return FL.getCurrentPosition();}
    public int getCurPosBL() {return BL.getCurrentPosition();}
    public int getCurPosFR() {return FR.getCurrentPosition();}
    public int getCurPosBR() {return BR.getCurrentPosition();}
}
