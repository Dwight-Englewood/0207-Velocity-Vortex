package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "DRIVE SHOOT", group = "LINEAR_AUTON")
public class timeAutonDriveShoot  extends OpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;
    int newTargetL;
    int newTargetR;
    long start_time;
    long current_time;
    int i;
    double powerlevelR;
    double powerlevelL;
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
        if (current_time < start_time + 100) {
            shoot = 0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else if (current_time > start_time + 1000 && current_time < start_time + 2000) {
            shoot = 1.0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else if (current_time > start_time + 2100 && current_time < start_time + 8000) {
            elevate = 1.0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            shoot = 0;
        } else if (current_time > start_time + 9000 && current_time < start_time + 10000) {
            shoot = 1.0;
            powerlevelL = 0.0f;
            powerlevelR = 0.0f;
            elevate = 0;
        } else if (current_time > start_time + 12000 && current_time < start_time + 16000) {
            shoot = 0;
            powerlevelL = 0.5f;
            powerlevelR = 0.5f;
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
      
     }
}
