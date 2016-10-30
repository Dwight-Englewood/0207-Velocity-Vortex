package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Time Auton Shoot", group = "LINEAR_AUTON")
public class timeAutonShoot extends OpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;
    int newTargetL;
    int newTargetR;
    long start_time;
    int i;
    double powerlevel;
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
        if (System.currentTimeMillis() < start_time + 1000) {
            shoot = 0;
            powerlevel = 0.0f;
            elevate = 0;
        } else if (System.currentTimeMillis() > start_time + 1100 && System.currentTimeMillis() < start_time + 2100) {
            shoot = 1.0;
            powerlevel = 0;
            elevate = 0;
        } else if (System.currentTimeMillis() > start_time + 2200 && System.currentTimeMillis() < start_time + 7100) {
            shoot = 0;
            powerlevel = 0;
            elevate = 1.0;
        } else if (System.currentTimeMillis() > start_time + 10100 && System.currentTimeMillis() < start_time + 10100) {
            shoot = 1.0;
            powerlevel = 0;
            elevate = 0;
        } else {
            shoot = 0;
            powerlevel = 0;
            elevate = 0;
        }

        shooter.setPower(shoot);
        elevator.setPower(elevate);
        leftMotor.setPower(powerlevel);
        rightMotor.setPower(powerlevel);
      
     }
}
