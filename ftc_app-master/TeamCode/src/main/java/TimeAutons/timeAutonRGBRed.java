package TimeAutons;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous(name = "timeAutonRGBRed", group = "ITERATIVE_AUTON")
public class timeAutonRGBRed extends OpMode {

    long start_time;
    long current_time;
    long time;
    long wait_time;
    int beacons = 0;

    DcMotor rightMotor = null;
    DcMotor leftMotor = null;
    DcMotor elevator = null;
    DcMotor shooter = null;
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
    }

    @Override
    public void loop() {
        current_time = System.currentTimeMillis();
        time = current_time - start_time;

        if (colorSensor.blue() < 2 && colorSensor.red() >= 2 && beacons < 2)
        {
            wait_time = System.currentTimeMillis() - start_time;
            beacons++;

            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);
            if (wait_time > 500)
            {
                currentPos = maxPos;
            }
            if(wait_time > 1500)
            {
                currentPos = startPos;
                leftMotor.setPower(0.5);
                rightMotor.setPower(0.7);
            }
        }
        if (beacons == 2)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);
            poker.setPosition(startPos);

        }
        else
        {
            leftMotor.setPower(0.5);//Magic numbers
            rightMotor.setPower(0.7 );//Do not touch
            elevator.setPower(0.0);
            shooter.setPower(0.0);
            currentPos = startPos;
        }


        poker.setPosition(currentPos);

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
