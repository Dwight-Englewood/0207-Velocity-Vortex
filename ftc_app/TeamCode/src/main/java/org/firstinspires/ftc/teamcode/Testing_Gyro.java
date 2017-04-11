package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Testing_Gyro", group="TESTING")
//@Disabled
public class Testing_Gyro extends OpMode {

    Bot robot = new Bot();

    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap, telemetry);
        robot.intakeServoOpen();
        robot.runUsingEncoders();
    }


    @Override
    public void init_loop() {robot.isCalibrating();}

    @Override
    public void start() {super.start(); robot.resetZ();}

    @Override
    public void loop() {
        robot.adjustPower(0);
        telemetry.addData("heading", robot.getHeading());
        telemetry.update();
    }

    @Override
    public void stop() {}
}