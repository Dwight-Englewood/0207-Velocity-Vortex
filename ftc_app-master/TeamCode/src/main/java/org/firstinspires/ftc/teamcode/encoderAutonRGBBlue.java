package org.firstinspires.ftc.teamcode;

/*plotnw*/

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

    final double[] distanceArray = {38.2, 0.0, 0.0};
    int commandNumber = -1;


    @Override
    public void init() {

        poker = hardwareMap.servo.get("poker");
        poker.setPosition(startPos);

        colorSensor = hardwareMap.colorSensor.get("color sensor");
        colorSensor.enableLed(bLedOn);

        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");



        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.FORWARD);

    }

    @Override
    public void start() {
        super.start();
        // Save the system clock when start is pressed
        start_time = System.currentTimeMillis();

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {

        switch(commandNumber)
        {
            case -1:
                leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                commandNumber++;
                break;

            case 0:
                leftMotor.setTargetPosition(747);
                rightMotor.setTargetPosition(747);

                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                rightMotor.setPower(.5);
                leftMotor.setPower(.7);

                while (leftMotor.isBusy() && rightMotor.isBusy()) {}

                rightMotor.setPower(0.0);
                leftMotor.setPower(0.0);

                rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                break;

            case 1:
                if (start_time == current_time)
                {
                    start_time = System.currentTimeMillis();
                }

                current_time = System.currentTimeMillis() + 1;
                time = start_time - current_time - 1;
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
        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.update();

    }
    @Override
    public void stop() {
        poker.setPosition(startPos);
    }
}
