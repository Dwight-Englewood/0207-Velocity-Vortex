package org.firstinspires.ftc.teamcode;

/*plotnw*/

import android.app.Activity;
import android.graphics.Color;
import android.provider.SearchRecentSuggestions;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "ROBS_TESTER", group = "ITERATIVE_AUTON")
public class RobsRGBTest extends OpMode {

    long start_time;
    long current_time;
    long time;

    Servo poker = null;
    private double startPos = 0.48;
    private double currentPos = startPos;
    private double maxPos = 0.78;
    private double minPos = 0.18 ;

    ColorSensor colorSensor;
    boolean bLedOn = false;

    @Override
    public void init() {

        poker = hardwareMap.servo.get("poker");
        poker.setPosition(startPos);

        colorSensor = hardwareMap.colorSensor.get("color sensor");
        colorSensor.enableLed(bLedOn);
    }

    @Override
    public void start() {
        super.start();
        // Save the system clock when start is pressed
        start_time = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        current_time = System.currentTimeMillis();
        time = current_time - start_time;

        if (colorSensor.blue() >= 2 && colorSensor.red() < 2)
        {
            currentPos = maxPos;
        }
        if (colorSensor.red() >= 2 && colorSensor.blue() < 2)
        {
            currentPos = minPos;
        }
        if (colorSensor.red() < 2 && colorSensor.blue() < 2)
        {
            currentPos = startPos;
        }


        poker.setPosition(currentPos);

        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Blue ", colorSensor.blue());

        telemetry.update();

    }
}
