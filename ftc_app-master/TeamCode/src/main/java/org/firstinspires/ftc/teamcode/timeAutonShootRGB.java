package org.firstinspires.ftc.teamcode;

/*plotnw*/

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "RGB SHOOT BLUE", group = "ITERATIVE_AUTON")
public class timeAutonShootRGB extends OpMode {


    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;
    int newTargetL;
    int newTargetR;
    long start_time;
    long current_time;
    long time;
    int i;
    double powerlevelR;
    double powerlevelL;
    double shoot = 0.0;
    double elevate = 0.0;
    ColorSensor sensorRGB;
    int count = 0;
    Servo poker = null;

    final double maxPos = 0.69;
    final double minPos = 0.18;
    double curPos = .48;
    final double startPos = .48;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.FORWARD);

        float hsvValues[] = {0F,0F,0F};

        final float values[] = hsvValues;

        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

        boolean bPrevState = false;
        boolean bCurrState = false;
        double alphaA = 0;
        double redA = 0;
        double greA = 0;
        double bluA = 0;
        int times = 0;

        sensorRGB = hardwareMap.colorSensor.get("color sensor");
        sensorRGB.enableLed(false);

        poker.setPosition(startPos);

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
        //Do Nothing
        if (time < 500) {
            shoot = 0;
            powerlevelL = 1.0;
            powerlevelR = 1.0;
            elevate = 0;
        //Shoot Number 1
        /*} else if (time > 1000 && time < 2000) {
            shoot = 1.0;
            powerlevelL = 0.0;
            powerlevelR = 0.0;
            elevate = 0;
            */
        //Run elevator
        /*} else if (time > 2100 && time < 4000) {
            elevate = 1.0;
            powerlevelL = 0.0;
            powerlevelR = 0.0;
            shoot = 0;
            */
        //Shoot Number 2
        /*} else if (time > 5100 && time < 6100) {
            shoot = 1.0;
            powerlevelL = 0.0;
            powerlevelR = 0.0;
            elevate = 0;
            */
        //Turn right
        /*} else if (time > 6500 && time < 7300) {
            shoot = 0;
            powerlevelL = 1.0;
            powerlevelR = 0.0;
            elevate = 0;
            */
        //Drive Forward
        } else if (time > 8100 && sensorRGB.blue() >= 2 && 1000 > count) {

            shoot = 0;
            powerlevelL = 0;
            powerlevelR = 0;
            elevate = 0;
            curPos = maxPos;
            count++;
        } else if (time > 8100 && time < 15100) {
            shoot = 0;
            powerlevelL = 1.0;
            powerlevelR = 1.0;
            elevate = 0;
        }
        else {
            shoot = 0;
            powerlevelL = 0.0;
            powerlevelR = 0.0;
            elevate = 0;
            count = 0;
        }

        shooter.setPower(shoot);
        elevator.setPower(elevate);
        leftMotor.setPower(powerlevelL);
        rightMotor.setPower(powerlevelR);
        poker.setPosition(curPos);
        telemetry.addData("Time", time);
        telemetry.addData("shoot", shoot);
        telemetry.addData("powerlevelL", powerlevelL);
        telemetry.addData("powerlevelR", powerlevelR);
        telemetry.addData("elevate", elevate);
        telemetry.addData("poker", curPos);
        telemetry.addData("count", count);




        updateTelemetry(telemetry);
      
     }
}
