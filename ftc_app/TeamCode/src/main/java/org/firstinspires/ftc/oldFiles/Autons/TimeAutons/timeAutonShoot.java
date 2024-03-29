package TimeAutons;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous(name = "SHOOT", group = "LINEAR_AUTON")
public class timeAutonShoot extends OpMode {

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
    double powerlevelL;
    double powerlevelR;
    double shoot = 0.0;
    double elevate = 0.0;
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

        if (time < 1000) {
            shoot = 0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else if (time > 1100 && time < 2100) {
            shoot = 1.0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else if (time > 2200 && time < 7100) {
            shoot = 0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 1.0;
        } else if (time > 10100 && time < 11100) {
            shoot = 1.0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else {
            shoot = 0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        }

        shooter.setPower(shoot);
        elevator.setPower(elevate);
        leftMotor.setPower(powerlevelL);
        rightMotor.setPower(powerlevelR);

        telemetry.addData("Shoot", shoot);
        telemetry.addData("Elevate", elevate);
        telemetry.addData("powerlevelL", powerlevelL);
        telemetry.addData("powerlevelR", powerlevelR);
        updateTelemetry(telemetry);
     }
}
