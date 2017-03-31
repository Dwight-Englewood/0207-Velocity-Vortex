package org.firstinspires.ftc.teamcode;

/*plotnw*/

// Same comments as the red side auton
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name = "Blue Two Shot", group = "BLUE")
public class Auton_BlueTwoShots extends OpMode {

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;
    private int y = 1;
    boolean isGoingForward = false;

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
        telemetry.addData("Command", commandNumber);

        if (robot.getIsRunningToTarget() && isGoingForward)
        {
            if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 25) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 25) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 25) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 25)
                    )
            {
                robot.stopMovement();
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
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 500) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 500) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 500) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 500)
                    )
            {
                robot.drive(0, .1);
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
            /**
             * Update telemetry then return to the beginning of the statement as the bot hasn't
             * reached the target position yet.
             */
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
                robot.stopMovement();
                robot.setIsRunningToTarget(false);
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 250) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 250) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 250) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 250)
                    )
            {
                robot.drive(0, .1);
            }

            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                isGoingForward = true;
                robot.runToPosition(39);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    robot.chill();
                    x++;
                }
                if (timer.milliseconds() < 500)
                {

                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 2000)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 2500)
                {
                    robot.setElevator(-1);
                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() < 4000 )
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() > 500)
                {
                    robot.setShooter(0);
                    robot.stopMovement();
                    commandNumber++;
                }
                break;

            case 3:
                isGoingForward = false;
                robot.runTurnRight(0.3, 19);
                commandNumber++;
                break;

            case 4:
                isGoingForward = true;
                robot.runToPosition(184);
                commandNumber++;
                break;

            case 5:
                isGoingForward = false;
                robot.runTurnLeft(0.3, 19);
                commandNumber++;
                break;

            case 6:
                //robot.runToPosition(.4, 100);
                commandNumber++;
                break;

            case 7:
                //robot.runToLeft(1, 80);
                commandNumber++;
                break;

            case 8:
                robot.runUsingEncoders();
                robot.drive(0, .3);
                commandNumber++;
                timer.reset();
                break;

            case 9:
                if (robot.getBlue() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(7);
                    x = 0;
                    commandNumber++;
                }
                else if (timer.milliseconds() > 4000)
                {
                    robot.drive(1, .3);
                    commandNumber = 11;
                }
                break;

            case 10:
                if (x == 0)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 2100)
                {
                    robot.rightServoOut();
                }
                else if (timer.milliseconds() < 2400)
                {
                    robot.rightServoIn();
                }
                else if (timer.milliseconds() < 3400)
                {
                    robot.drive(1, 0.3);
                }
                else if (timer.milliseconds() > 4000)
                {
                    commandNumber++;
                }
                break;

            case 11:
                if (robot.getBlue() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(8);
                    commandNumber++;
                    x = 2;
                }
                break;

            case 12:
                if (x == 2)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 2100)
                {
                    robot.rightServoOut();
                }
                else if (timer.milliseconds() < 2400)
                {
                    robot.rightServoIn();
                }
                else if (timer.milliseconds() > 2400)
                {
                    commandNumber++;
                }
                break;

            case 13:
                if (x == 3)
                {
                    timer.reset();
                    robot.stopMovement();
                    robot.runUsingEncoders();
                    x++;
                }
                else if (timer.milliseconds() < 3000)
                    robot.drive(2, 1);
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}
}
