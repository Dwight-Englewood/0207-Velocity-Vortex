package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@TeleOp(name="Testing_Gyro", group="TESTING")
//@Disabled
public class Testing_Gyro extends OpMode {

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap, telemetry);
        robot.intakeServoOpen();
        robot.runUsingEncoders();
        timer.reset();
    }


    @Override
    public void init_loop() {
        if (!robot.isCalibrating() && timer.milliseconds() % 100 < 20)
        {
            telemetry.addLine("GOOD TO GO");
            telemetry.update();
        }
    }

    @Override
    public void start() {super.start(); robot.resetZ();}

    @Override
    public void loop() {
        robot.stillAdjust(0);
        telemetry.addData("heading", robot.getHeading());
        telemetry.addData("tilted?", robot.isTilted());
        telemetry.update();
    }

    @Override
    public void stop() {}
}