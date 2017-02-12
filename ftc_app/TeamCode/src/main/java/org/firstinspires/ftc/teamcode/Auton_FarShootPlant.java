package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

//@Disabled
@Autonomous(name = "Shoot Drive FAR", group = "SHOOTING")
public class Auton_FarShootPlant extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    boolean isGoingForward = true;

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

        if (robot.getIsRunningToTarget() && isGoingForward)
        {
            if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 10) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 10) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 10) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 10)
                    )
            {
                robot.drive();
                robot.setIsRunningToTarget(false);
            }
            else if (
                    (Math.abs(robot.getCurPosFL()) < 200) &&
                            (Math.abs(robot.getCurPosFR()) < 200) &&
                            (Math.abs(robot.getCurPosBL()) < 200) &&
                            (Math.abs(robot.getCurPosBR()) < 200)
                    )
            {
                robot.drive(0, .1);
            }
            else if (
                    (Math.abs(robot.getCurPosFL()) < 1000) &&
                            (Math.abs(robot.getCurPosFR()) < 1000) &&
                            (Math.abs(robot.getCurPosBL()) < 1000) &&
                            (Math.abs(robot.getCurPosBR()) < 1000)
                    )
            {
                robot.drive(0, .3);
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 700) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 700) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 700) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 700)
                    )
            {
                robot.drive(0, .1);
                robot.setIsRunningToTarget(false);
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 2000) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 2000) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 2000) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 2000)
                    )
            {
                robot.drive(0, .3);
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 2400) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 2400) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 2400) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 2400)
                    )
            {
                robot.drive(0, .5);
            }
            else
            {
                robot.drive(0, .7);
            }

            telemetry.addData("ingoodLoop", true);
            telemetry.update();
            return;
        }
        else if (robot.getIsRunningToTarget())
        {
            if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 25) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 25) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 25) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 25)
                    )
            {
                robot.drive();
                robot.setIsRunningToTarget(false);
            }

            telemetry.update();
            telemetry.addData("inLoop", false);
            return;
        }

        switch (commandNumber)
        {
            case 1:
                if (x == 0)
                {
                    timer.reset();
                    x++;
                }
                else if (timer.milliseconds() > 15000)
                    commandNumber++;
                break;

            case 2:
                robot.runToPosition(0.1, 78);
                commandNumber++;
                break;

            case 3:
                if (x == 1)
                {
                    timer.reset();
                    robot.chill();
                    x++;
                }
                else if (timer.milliseconds() < 1500 && timer.milliseconds() > 500)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 4500)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 5500)
                {
                    robot.setElevator(-1);
                }
                else if (timer.milliseconds() < 6500 )
                {
                    robot.setElevator(0);
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() > 4500)
                {
                    robot.setShooter(0);
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.drive();
                    commandNumber++;
                }
                break;

            case 4:
                if (timer.milliseconds() > 3000)
                    commandNumber++;
                break;
            case 5:
                if (x == 2)
                {
                    timer.reset();
                    x++;
                }
                else if (timer.milliseconds() < 1000)
                {
                    robot.drive(0, 1);
                }
                else
                {
                    robot.drive();
                }
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}
}
