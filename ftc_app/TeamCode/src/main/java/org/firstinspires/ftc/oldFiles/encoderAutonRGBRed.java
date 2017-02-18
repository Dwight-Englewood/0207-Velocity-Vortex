package useless.junkerino;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.oldFiles.helperFunction;

@Disabled
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

    private double [] powerLevels = {0.3, 0.25, 0.2};

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
                driveToPlace(28.5, 1);
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
                turnLeft(30); // 45 degree turn
                commandNumber++;
                break;
            case 3:
                driveToPlace(152.5, 0);
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
                        timer.reset();
                        x++;
                    }
                    telemetry.addData("Red  ", colorSensor.red());
                    telemetry.addData("Blue ", colorSensor.blue());
                    telemetry.addData("time Sec", timer.time());

                    if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.REVERSE);
                        poker.setPower(0.5);
                        telemetry.addData("1st", true);
                    }
                    if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.FORWARD);
                        poker.setPower(0.5);
                        telemetry.addData("2nd", true);
                    }
                    if (timer.seconds() > 2.5)
                    {
                        poker.setPower(0.0);
                        commandNumber++;
                    }
                }
                break;
            case 7:
                driveBackwards();
                x = 1;
                break;
            case 8:
                if (colorSensor.red() > 2 && colorSensor.blue() < 2)
                {
                    if (x == 1)
                    {
                        stopDriving();
                        telemetry.clearAll();
                        timer.reset();
                        x++;
                    }
                    telemetry.addData("Red  ", colorSensor.red());
                    telemetry.addData("Blue ", colorSensor.blue());
                    telemetry.addData("time Sec", timer.time());

                    if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.REVERSE);
                        poker.setPower(0.5);
                        telemetry.addData("1st", true);
                    }
                    if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                    {
                        poker.setDirection(DcMotorSimple.Direction.FORWARD);
                        poker.setPower(0.5);
                        telemetry.addData("2nd", true);
                    }
                    if (timer.seconds() > 2.5)
                    {
                        poker.setPower(0.0);
                        commandNumber++;
                    }
                }
                break;
        }
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
    public void driveToPlace(double distance, int powerNum)
    {
        int distanceInt = helperFunction.distanceToRevs(distance);

        leftMotor.setTargetPosition(distanceInt);
        rightMotor.setTargetPosition(distanceInt);

        leftMotor.setPower(powerLevels[powerNum]);
        rightMotor.setPower(powerLevels[powerNum]);

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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // do nothing
        }
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    //(86pi * theta) / 360 = turning distances
    public void turnRight(double distance)
    {
        int distanceInt = helperFunction.distanceToRevs((distance));

        leftMotor.setTargetPosition(distanceInt);
        rightMotor.setTargetPosition(0);

        leftMotor.setPower(0.3);
        rightMotor.setPower(0.3);


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

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // do nothing
        }

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void turnLeft(double distance)
    {
        int distanceInt = helperFunction.distanceToRevs((distance));

        rightMotor.setTargetPosition(distanceInt);
        leftMotor.setTargetPosition(0);

        rightMotor.setPower(0.3);
        leftMotor.setPower(0.3);

        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (rightMotor.isBusy() || leftMotor.isBusy())
        {
            telemetry.addData("left Busy", leftMotor.isBusy());
            telemetry.addData("right busy", rightMotor.isBusy());
            telemetry.addData("left position", leftMotor.getCurrentPosition());
            telemetry.addData("right position", rightMotor.getCurrentPosition());

            telemetry.update();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // do nothing
        }

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
