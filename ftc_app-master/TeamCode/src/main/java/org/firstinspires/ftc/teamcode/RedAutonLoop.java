package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name = "encoderAutonRGBRed", group = "ITERATIVE_AUTON")
public class RedAutonLoop extends OpMode {

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

    int commandNumber = 1;

    private int x = 0;

    private double [] powerLevels = {0.3, 0.25, -0.3};

    @Override
    public void init()
    {
        poker = hardwareMap.crservo.get("poker");

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
    public void loop()
    {
        if (leftMotor.isBusy() || rightMotor.isBusy())
        {
            telemetry.addData("Left Position", leftMotor.getCurrentPosition());
            telemetry.addData("Right Position", rightMotor.getCurrentPosition());
            telemetry.addData("Red Value", colorSensor.red());
            telemetry.addData("Blue Value", colorSensor.blue());
            return;
        }
        try { Thread.sleep(500); } catch (InterruptedException e) {} //do nothing
        
        switch (commandNumber)
        {
            case 1:
                driveToPosition(28.5, 1);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    x++;
                }
                if (timer.seconds() < 0.5)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (timer.seconds() < 1.5)
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (timer.seconds() < 3.0)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(1.0);
                }
                else if (timer.seconds() < 3.5)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (timer.seconds() < 4.5 )
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (timer.seconds() > 4.5)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                    commandNumber++;
                }
                break;

            case 3:
                turnLeft(30, 1);
                commandNumber++;
                break;

            case 4:
                driveToPosition(152.5, 0);
                commandNumber++;
                break;

            case 5:
                turnRight(30, 1);
                commandNumber++;
                break;

            case 6:
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                startDriving(0);
                break;

            case 7:
                if (colorSensor.red() > 2 && colorSensor.blue() < 2)
                {
                    if (x == 1)
                    {
                        stopDriving();
                        timer.reset();
                        x++;
                    }
                    if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.REVERSE);
                        poker.setPower(0.5);
                    }
                    if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.FORWARD);
                        poker.setPower(0.5);
                    }
                    if (timer.seconds() > 2.5)
                    {
                        poker.setPower(0.0);
                        commandNumber++;
                    }
                }
                break;

            case 8:
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                startDriving(2);
                break;

            case 9:
                if (colorSensor.red() > 2 && colorSensor.blue() < 2)
                {
                    if (x == 2)
                    {
                        stopDriving();
                        timer.reset();
                        x++;
                    }
                    if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.REVERSE);
                        poker.setPower(0.5);
                    }
                    if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.FORWARD);
                        poker.setPower(0.5);
                    }
                    if (timer.seconds() > 2.5)
                    {
                        poker.setPower(0.0);
                        commandNumber++;
                    }
                }
                break;

            case 10:
                break;
        }
    }

    @Override
    public void stop() {}

    public void driveToPosition(double target, int powerSelection)
    {
        int  targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + targetInt);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + targetInt);
    }

    public void turnLeft(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(leftMotor.getCurrentPosition());
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + targetInt);
    }

    public void turnRight(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        rightMotor.setTargetPosition(rightMotor.getCurrentPosition());
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + targetInt);
    }

    public void startDriving(int powerSelection)
    {
        leftMotor.setPower(powerSelection);
        rightMotor.setPower(powerSelection);
    }

    public void stopDriving()
    {
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }
}
