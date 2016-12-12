package TimeAutons;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//@Disabled
@Autonomous(name = "DRIVE SHOOT FAR (MAYBE BAD LMAO)", group = "LINEAR_AUTON")
public class timeAutonDriveShootFar extends OpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;

    long start_time;
    long current_time;
    long time;



    @Override
    public void init() {
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

        if (time < 1000)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);

        }
        else if (time > 1000 && time < 2600)
        {
            leftMotor.setPower(.5);
            rightMotor.setPower(.7);
            elevator.setPower(0.0);
            shooter.setPower(0.0);
        }
        else if (time > 2600 && time < 3300)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);
        }
        else if (time > 3300 && time < 4300)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(1.0);
        }
        else if (time > 4300 && time < 5300)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(1.0);
            shooter.setPower(0.0);
        }
        else if (time > 5500 && time < 6500)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(1.0);
        }
        else if (time > 6500 && time < 7500)
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);

        }
        else if (time > 12000 && time < 13800) {

            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);

        } else {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            elevator.setPower(0.0);
            shooter.setPower(0.0);
        }


        updateTelemetry(telemetry);
    }
}
