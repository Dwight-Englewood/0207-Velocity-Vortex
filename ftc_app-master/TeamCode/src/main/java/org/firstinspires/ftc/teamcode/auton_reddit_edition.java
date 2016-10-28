package org.firstinspires.ftc.teamcode;
/* plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class auton_reddit_edition extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor elevator = null;
    DcMotor shooter = null;

    long start_time;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("left motor");
        motorRight = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void start() {
        super.start();
        // Save the system clock when start is pressed
        start_time = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        float powerlevel = 0.0f;

        // If we're still with the first 3 seconds after pressing start keep driving forward
        if (System.currentTimeMillis() < start_time + 3000) {
            powerlevel = 0.5f;
        }
        motorLeft.setPower(powerlevel);
        motorRight.setPower(powerlevel);
    }
}