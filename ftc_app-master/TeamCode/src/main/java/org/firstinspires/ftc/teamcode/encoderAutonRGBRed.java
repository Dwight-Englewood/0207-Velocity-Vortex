package org.firstinspires.ftc.teamcode;

/*plotnw*/

import android.provider.Settings;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name = "encoderAutonRGBRed", group = "ITERATIVE_AUTON")
public class encoderAutonRGBRed extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    DcMotor rightMotor = null;
    DcMotor leftMotor = null;
    DcMotor elevator = null;
    DcMotor shooter = null;
    CRServo poker = null;
    ElapsedTime timer = new ElapsedTime(0);

    ColorSensor colorSensor;

    int commandNumber = 0;

    private int x = 0;

    @Override
    public void init() {
        poker = hardwareMap.crservo.get("poker");
        poker.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor = hardwareMap.colorSensor.get("color sensor");
        colorSensor.enableLed(false);

        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    @Override
    public void start() { super.start();}

    @Override
    public void loop() {

        switch(commandNumber)
        {
            case 0:
                driveToPlace(28.5);
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
                    //shooter.setPower(1.0);
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
                   // shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (time > 6000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                    commandNumber++;
                }

                break;
            case 2:
                turnLeft(33.7721211); // 45 degree turn
                commandNumber++;
                break;
            case 3:
                driveToPlace(143);
                commandNumber++;
                break;
            case 4:
                turnRight(30); // 45 degree turn
                commandNumber++;
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                x = 1;
                break;
            case 5:
                driveForward();
                commandNumber++;
                break;
            case 6:

                if (colorSensor.red() > 2 && colorSensor.blue() < 2)
                {
                    if (x == 1)
                    {
                        stopDriving();
                        telemetry.clearAll();
                        //start_time = System.currentTimeMillis();
                        timer.reset();
                        x++;
                    }
                    //current_time = System.currentTimeMillis();
                    //time = current_time - start_time;
                    //telemetry.addData("Current Time", time);
                    telemetry.addData("Red  ", colorSensor.red());
                    telemetry.addData("Blue ", colorSensor.blue());

                    if (timer.time() > 0.5 && timer.time() < 1.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.REVERSE);
                        poker.setPower(0.5);
                        telemetry.addData("1st", true);
                    }
                    if (timer.time() > 1.5 && timer.time() < 2.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.FORWARD);
                        poker.setPower(0.5);
                        telemetry.addData("2nd", true);
                    }
                    if (timer.time() > 2500)
                    {
                        poker.setPower(0.0);
                        //commandNumber++;
                    }
                }
                break;
            case 7:
                driveBackwards();
                break;
        }

/*        telemetry.addData("Current Case", commandNumber);
        telemetry.addData("Right Busy", rightMotor.isBusy());
        telemetry.addData("Left Busy", leftMotor.isBusy());
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("left position", leftMotor.getCurrentPosition());
        telemetry.addData("right position", rightMotor.getCurrentPosition());*/
        telemetry.update();

    }
    @Override
    public void stop() {}

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
    public void driveBackwards()
    {
        leftMotor.setPower(-0.3);
        rightMotor.setPower(-0.3);
    }
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
    //(86pi * theta) / 360 = turning distances
    public void turnRight(double distance)
    {
        int distanceInt = helperFunction.distanceToRevs((distance));

        leftMotor.setTargetPosition(distanceInt);

        leftMotor.setPower(0.3);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (leftMotor.isBusy())
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
    public void turnLeft(double distance)
    {
        int distanceInt = helperFunction.distanceToRevs((distance));


        rightMotor.setTargetPosition(distanceInt);

        rightMotor.setPower(0.3);

        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (rightMotor.isBusy())
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
