package org.firstinspires.ftc.oldFiles;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

@Disabled
@Autonomous(name = "DO NOT USE PLZ", group = "ITERATIVE_AUTON")
public class BlueAutonSM extends OpMode {

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

    int Rtarget;
    int Ltarget;
    boolean runningToTarget = true;

    private double [] powerLevels = {0.3, 0.0, 0.25, -0.3, 0.15, -0.25, -0.15};

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
       /* telemetry.addData("Left Position", leftMotor.getCurrentPosition());
        telemetry.addData("Right Position", rightMotor.getCurrentPosition());
        telemetry.addData("Red Value", colorSensor.red());
        telemetry.addData("Blue Value", colorSensor.blue());
        telemetry.addData("Command", commandNumber);
        telemetry.addData("current time", timer.seconds());

        if (runningToTarget && (Math.abs(leftMotor.getCurrentPosition() - Ltarget) > 25 || Math.abs(rightMotor.getCurrentPosition() - Rtarget) > 25))
        {
            telemetry.addData("inLoop", runningToTarget);
            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                driveToPosition(28.5, 2);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    x++;
                }
                if (timer.milliseconds() < 500)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (timer.milliseconds() < 1500)
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (timer.milliseconds() < 3000)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(1.0);
                }
                else if (timer.milliseconds() < 3500)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (timer.milliseconds() < 4500 )
                {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                }
                else if (timer.milliseconds() > 4500)
                {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                    commandNumber++;
                }
                break;

            case 3:
                stopAndReset();
                turnRight(164.088484, 2);
                commandNumber++;
                break;

            case 4:
                stopAndReset();
                driveToPosition(-125, 3);
                commandNumber++;
                break;

            case 5:
                stopAndReset();
                turnLeft(31, 5);
                commandNumber++;
                break;

            case 6:
                runningToTarget = false;
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                startDriving(3);
                commandNumber++;
                x=1;
                break;

            case 7:
                if (colorSensor.blue() >= 2)
                {
                    stopDriving();
                    stopAndReset();
                    runningToTarget = true;
                    driveToPosition(6, 4);
                    commandNumber++;
                }
                break;

            case 8:
                runningToTarget = false;
                if (x == 1)
                {
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
                break;

            case 9:
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                startDriving(0);
                commandNumber++;
                try {Thread.sleep(1500);} catch (InterruptedException e) {}
                break;

            case 10:
                if (colorSensor.blue() >= 2)
                {
                    stopDriving();
                    commandNumber++;
                    x = 2;
                }
                break;
            case 11:
                if (x == 2)
                {
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
                break;
        }
        telemetry.update();*/
    }

    @Override
    public void stop() {}

    /*public void driveToPosition(double target, int powerSelection)
    {
        int  targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(targetInt);
        rightMotor.setTargetPosition(targetInt);

        Ltarget = targetInt;
        Rtarget = targetInt;
    }

    public void turnLeft(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(targetInt);

        Ltarget = 0;
        Rtarget = targetInt;
    }

    public void turnRight(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        rightMotor.setTargetPosition(0);
        leftMotor.setTargetPosition(targetInt);

        Rtarget = 0;
        Ltarget = targetInt;
    }

    public void startDriving(int powerSelection)
    {
        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);
    }

    public void stopDriving()
    {
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }

    public void stopAndReset()
    {
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }*/
}
