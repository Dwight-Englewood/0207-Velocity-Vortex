package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Testing_ServoAlign", group="TESTING")
//Note if you are using this make sure to comment out the @Disabled below or it won't show up on your phone.
//@Disabled
public class Testing_ServoAlign extends OpMode {
    //Declare a servo to represent your CRServo
    Bot robot = new Bot();

    //Used to add a cooldown to the button, so it won't trigger so quickly
    //Thus you can actually finely control the values
    private long pressTracker = 0;

    //The position the Cr servo will be set to
    private double position = .5;
    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap, telemetry);
        // The name of the crservo in the robot config on your robot controller phone
        // Note: despite being a crservo we still choose "servo" from the dropdown in the phone
    }


    @Override
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {
        //We check if the time since the last button press is greater than 500 ms, and that the button is down
        if (gamepad1.dpad_down && System.currentTimeMillis() - pressTracker > 500) {
            //Reset last button press time
            pressTracker = System.currentTimeMillis();

            //Decrement position, since dpad_down
            position = position - .01;
        } else if (gamepad1.dpad_up && System.currentTimeMillis() - pressTracker > 500) {
            //Reset last button press time
            pressTracker = System.currentTimeMillis();

            //Increment position, since dpad_up
            position = position + .01;
        }

        robot.setIntakeServo(position);

        //The current value of position to telemetry
        telemetry.addData("Current Position", position);
        telemetry.update();
    }

    @Override
    public void stop() {}
}