package org.firstinspires.ftc.teamcode;
/* plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class auton_reddit_edition extends OpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;

    long start_time;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void start() {
        super.start();
        // Save the system clock when start is pressed
        start_time = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        int newTargetL = leftMotor.getCurrentPosition() + 7;
        int newTargetR = rightMotor.getCurrentPosition() + 7;
        int i = 0;
        leftMotor.setTargetPosition(newTargetL);
        rightMotor.setTargetPosition(newTargetR);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(.5);
        rightMotor.setPower(.5);
        while (rightMotor.isBusy() && leftMotor.isBusy()) {
            i++;
            telemetry.addData("Status", Integer.toString(i));

        }
        i = 0;


        // If we're still with the first 3 seconds after pressing start keep driving forward
        //if (System.currentTimeMillis() < start_time + 3000) {
           // powerlevel = 0.5f;
        //}
        //motorLeft.setPower(powerlevel);
        //motorRight.setPower(powerlevel);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
