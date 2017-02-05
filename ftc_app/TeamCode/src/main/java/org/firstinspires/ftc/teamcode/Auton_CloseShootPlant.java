package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

//@Disabled
@Autonomous(name = "Shoot Drive", group = "ITERATIVE_AUTON")
public class Auton_CloseShootPlant extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;

    int FRtarget;
    int BRtarget;
    int FLtarget;
    int BLtarget;

    @Override
    public void init()
    {
        robot.init(hardwareMap);
    }

    @Override
    public void start() { super.start();}

    @Override
    public void loop()
    {
        telemetry.addData("FL Pos", robot.getCurPosFL());
        telemetry.addData("BL Pos", robot.getCurPosBL());
        telemetry.addData("FR Pos", robot.getCurPosFR());
        telemetry.addData("BR Pos", robot.getCurPosBR());
        telemetry.addData("Red Value", robot.getRed());
        telemetry.addData("Blue Value", robot.getBlue());
        telemetry.addData("Command", commandNumber);
        telemetry.addData("current time", timer.seconds());

        if (robot.getIsRunningToTarget())
        {
            if (Math.abs(robot.getCurPosFL() - Math.abs(robot.FLtarget)) > 25 || Math.abs(robot.getCurPosBL() - Math.abs(robot.BLtarget)) > 25 || (Math.abs(robot.getCurPosFR() - Math.abs(robot.FRtarget)) > 25) || (Math.abs(robot.getCurPosBR() - Math.abs(robot.BRtarget)) > 25))
                robot.setIsRunningToTarget(false);
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(0.4, 36);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    robot.chill();
                    x++;
                }
                if (timer.milliseconds() < 3000)
                {

                }
                else if (timer.milliseconds() < 4000)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 6000)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 6500)
                {
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() < 7500 )
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() > 7500)
                {
                    robot.setShooter(0);
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.drive();
                    commandNumber++;
                }
                break;

            case 3:
                if (timer.milliseconds() > 8000)
                    commandNumber++;
                break;
            case 4:
                robot.runToPosition(1, 115);
                commandNumber++;
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}
}
