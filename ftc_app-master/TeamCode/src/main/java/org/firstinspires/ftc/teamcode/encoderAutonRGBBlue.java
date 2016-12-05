package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
@Autonomous(name = "encoderAutonRGBBlue", group = "ITERATIVE_AUTON")
public class encoderAutonRGBBlue extends OpMode {

    long start_time = 0;
    long current_time = 0;
    long wait_time;
    long time;

    DcMotor rightMotor = null;
    DcMotor leftMotor = null;
    DcMotor elevator = null;
    DcMotor shooter = null;
    Servo poker = null;

    private double startPos = 0.48;
    private double currentPos = startPos;
    private double maxPos = 0.78;
    private double minPos = 0.18;

    ColorSensor colorSensor;
    boolean bLedOn = false;


    int commandNumber = 0;

    private int x = 0;


    @Override
    public void init() {
        poker = hardwareMap.servo.get("poker");
        //poker.setPosition(startPos);

        colorSensor = hardwareMap.colorSensor.get("color sensor");
        colorSensor.enableLed(bLedOn);

        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        super.start();
        // Save the system clock when start is pressed

    }

    @Override
    public void loop() {

        switch(commandNumber)
        {
            case 0:
                driveToPlace(48.5);
                commandNumber++;
                break;

            case 1:
                if (x == 0)
                {
                    start_time = System.currentTimeMillis();
                    x++;
                }
                current_time = System.currentTimeMillis();
                time = current_time - start_time;
                telemetry.addData("Current Time", time);
                telemetry.update();

                if (time < 1000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (time < 2000)
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (time < 4000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(1.0);
                }
                else if (time < 5000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (time < 6000)
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (time > 6000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                    commandNumber++;
                }

                break;
        }

        telemetry.addData("Current Case", commandNumber);
        telemetry.addData("Right Busy", rightMotor.isBusy());
        telemetry.addData("Left Busy", leftMotor.isBusy());
        telemetry.addData("left position", leftMotor.getCurrentPosition());
        telemetry.addData("right position", rightMotor.getCurrentPosition());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.update();

    }
    @Override
    public void stop() {

    }

    public void driveForward()
    {
        leftMotor.setPower(0.3);
        rightMotor.setPower(0.3);
    }
    public void stopDriving()
    {
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }
    //(86pi * theta) / 360 = turning distances
    public void driveToPlace(double distance)
    {
        int distanceInt = helperFunction.distanceToRevs(distance);

        leftMotor.setTargetPosition(distanceInt);
        rightMotor.setTargetPosition(distanceInt);

        driveForward();

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (leftMotor.isBusy() || rightMotor.isBusy())
        {
            telemetry.addData("left Busy", leftMotor.isBusy());
            telemetry.addData("right busy", rightMotor.isBusy());
            telemetry.addData("left position", leftMotor.getCurrentPosition());
            telemetry.addData("right position", rightMotor.getCurrentPosition());

            telemetry.update();
        }
        stopDriving();

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
