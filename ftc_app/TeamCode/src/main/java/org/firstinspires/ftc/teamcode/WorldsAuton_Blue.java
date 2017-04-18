package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aburur on 2/26/17.
 */

@Autonomous(name = "WorldsAuton_Blue", group = "BLUE")
//@Disabled
public class WorldsAuton_Blue extends OpMode
{
    Bot robot = new Bot();

    ElapsedTime timer = new ElapsedTime();
    int commandNumber = 1;

    boolean isGoingForward = false;
    boolean runningToDistance = false;
    int x = 0;

    int targetHeading = 0;
    int targetRange = 15;

    @Override
    public void init()
    {
        robot.init(hardwareMap, telemetry);
        robot.intakeServoOpen();
    }

    @Override
    public void init_loop()
    {
        robot.isCalibrating();
    }

    @Override
    public void start() { super.start(); robot.resetZ();}

    @Override
    public void loop()
    {

        telemetry.addData("Distance", robot.rightDistance());
        telemetry.addData("Heading", robot.getHeading());
        /**
         * This set of if statements allows the robot to speed up and slow down when driving forward in the auton,
         * addressing the issue of sliding when speeding up too quickly. It also helps evade the "opmode
         * stuck in loop" problem we had earlier in the year as the program won't check the commandNumber
         * switch until the bot is within a range from the target. To do this, the program first
         * checks if the robot is attempting to drive forward to a position using the run to position run-mode.
         */
        if (robot.getIsRunningToTarget() && isGoingForward)
        {
            /**
             * If the robot is trying to move forward, the program checks if the target number of encoder
             * ticks is withing a set range of the current encoder ticks. If it is, the motors are set
             * to a different power level corresponding to the distance from the target and from the
             * start position.
             *
             * The first range checked is if the target and current values are within 25 ticks of one
             * another. If yes, stop moving and move to the next command. Otherwise, check the next
             * statement.
             */
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
            /**
             * The next range checked is if the current encoder ticks on the motors are under 200.
             * If so, the power levels are set to 0.1. This starts the motors at a very slow
             * speed to prevent slipping.
             */
            else if (
                    (Math.abs(robot.getCurPosFL()) < 200) &&
                            (Math.abs(robot.getCurPosFR()) < 200) &&
                            (Math.abs(robot.getCurPosBL()) < 200) &&
                            (Math.abs(robot.getCurPosBR()) < 200)
                    )
            {
                robot.drive(0, .1);
            }
            /**
             * The next range checked is if the current encoder ticks on the motors are under 1000.
             * If so, the power levels are set to 0.3. This speeds the motors up as the bot moves
             * from its starting position.
             */
            else if (
                    (Math.abs(robot.getCurPosFL()) < 1000) &&
                            (Math.abs(robot.getCurPosFR()) < 1000) &&
                            (Math.abs(robot.getCurPosBL()) < 1000) &&
                            (Math.abs(robot.getCurPosBR()) < 1000)
                    )
            {
                robot.drive(0, .3);
            }
            /**
             * This is the first check for distance from the target position. If within 700 encoder
             * ticks of the target, set the motor speeds to 0.1.
             */
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 500) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 500) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 500) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 500)
                    )
            {
                robot.drive(0, .1);
            }
            /**
             * The next range checked is if the motors are within 2000 encoder ticks of the target.
             * If so, set power to 0.3. This will slow the bot down on its approach to the target.
             */
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 2000) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 2000) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 2000) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 2000)
                    )
            {
                robot.drive(0, .3);
            }
            /**
             * The next range checked is if the motors are within 2400 encoder ticks of the target.
             * If so, set power to 0.5. This will slow the bot down on its approach to the target.
             */
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 2400) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 2400) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 2400) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 2400)
                    )
            {
                robot.drive(0, .5);
            }
            /**
             * If the current encoder ticks are not within any of these values, set the power to the
             * motors at 0.7.
             */
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
        /**
         * If the robot is driving with encoder ticks but not driving forward then slipping is less of
         * an issue. Thus, the program enters this statement and checks only if the robot is at the
         * target. If it is, the robot will be stopped and the next command will be given. This section
         * also includes the code for our gyro as it is most useful when strafing/turning. In this auton, this
         */
        else if (robot.getIsRunningToTarget())
        {
            if (robot.rightDistance() <= targetRange )
            {
                robot.stopMovement();
                robot.setIsRunningToTarget(false);
            }

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
            //Prevents from getting stuck in loop
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 250) &&
                            (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 250) &&
                            (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 250) &&
                            (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 250)
                    )
            {
                robot.drive(0, .1);
            }
            else
            {
                robot.adjustPower(targetHeading);
            }
            // See line 149 comment
            telemetry.update();
            return;
        }


        switch(commandNumber)
        {
            case 1:
                isGoingForward = true;
                robot.runToPosition(41);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    x++;
                }
                if (timer.milliseconds() < 300)
                {
                    robot.rightServoOut();
                }
                else if (timer.milliseconds() < 1100)
                {
                    robot.setShooter(1);
                    robot.rightServoStop();
                }
                else if (timer.milliseconds() < 2600)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 3400)
                {
                    robot.setShooter(1);
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() > 3400)
                {
                    robot.setShooter(0);
                    timer.reset();
                    robot.stopMovement();
                    robot.runUsingEncoders();
                    commandNumber++;
                }
                break;

            case 3:
                if (x == 1)
                {
                    timer.reset();
                    x--;
                }
                if (timer.milliseconds() < 400)
                {
                    robot.drive(3, .3);
                }
                else
                {
                    isGoingForward = false;
                    commandNumber++;
                    targetHeading = 0;
                    x++;
                }

                break;

            case 4:
                robot.runDiagRight(350);
                commandNumber++;
                break;

            case 5:
                if (x == 1)
                {
                    timer.reset();
                    robot.drive(0,0);
                    robot.runUsingEncoders();
                    //runningToDistance = false;
                    x++;
                }
                if (timer.milliseconds() < 1200)
                {
                    robot.stillAdjust(0);
                }
                else if (timer.milliseconds() > 1200)
                {
                    commandNumber++;
                }
                break;

            case 6:
                if (robot.rightDistance() > targetRange)
                {
                    robot.drive(3, .2);
                }
                else if (robot.rightDistance() < 10)
                {
                    robot.drive(2, .15);
                }
                else
                {
                    robot.drive(0, .3);
                    commandNumber++;
                    timer.reset();
                }
                break;

            case 7:
                /**if (robot.getLineLight() > .7)
                 {
                 robot.stopMovement();
                 x = 99;
                 commandNumber++;
                 }
                 else if (timer.milliseconds() > 4000)
                 {
                 robot.drive(1, .3);
                 timer.reset();
                 commandNumber = 5;
                 }
                 telemetry.addData("leftRed", robot.getLRed());
                 telemetry.addData("leftblue", robot.getLBlue());*/
                commandNumber++;
                break;

            case 8:
                if (robot.getRBlue() >= 3)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(8);
                    commandNumber++;
                }
                else if (timer.milliseconds() > 4000)
                {
                    robot.drive(1, .4);
                    timer.reset();
                    commandNumber = 7;
                }
                /*else
                {
                    if (x == 99)
                    {
                    isGoingForward = true;
                    robot.runToPosition(-8);
                    x++;
                    }
                    else if (x == 100)
                    {
                        robot.runToLeft(10);
                        x++;
                        commandNumber++;
                    }
                }*/
                break;

            case 9:
                if (x == 2)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x = 101;
                }
                else if (timer.milliseconds() < 1100)
                {
                    robot.drive(3, .3);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.drive(2, .3);
                }
                else if (timer.milliseconds() > 1500)
                {
                    commandNumber++;
                }
                break;

            case 10:
                if (x == 101)
                {
                    timer.reset();
                    robot.drive(0,0);
                    robot.runUsingEncoders();
                    x++;
                }
                if (timer.milliseconds() < 1200)
                {
                    robot.stillAdjust(0);
                }
                else if (timer.milliseconds() < 1600)
                {
                    robot.drive(1,.4);
                }
                else if (timer.milliseconds() < 2300)
                {
                    robot.drive(1, .6);
                }
                else if (timer.milliseconds() > 2300)
                {
                    if (robot.rightDistance() > targetRange)
                    {
                        robot.drive(3, .2);
                    }
                    else if (robot.rightDistance() < 11)
                    {
                        robot.drive(2, .15);
                    }
                    else
                    {
                        robot.stillAdjust(0);
                        commandNumber++;
                        timer.reset();
                    }
                }

                break;

            case 11:
                if (x == 102)
                {
                    x--;
                    timer.reset();
                }
                if (timer.milliseconds() > 500)
                {
                    robot.drive(1, .3);
                    timer.reset();
                    commandNumber++;
                }
                break;

            case 12:
                if (robot.getRBlue() >= 3)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(9);
                    x = 102;
                    commandNumber++;
                }
                else if (timer.milliseconds() > 4000)
                {
                    robot.drive(0,.4);
                    timer.reset();
                    commandNumber = 7;
                }
                break;

            case 13:
                if (x == 102)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1150)
                {
                    robot.drive(3, .4);
                }
                else if (timer.milliseconds() > 1150)
                {
                    timer.reset();
                    commandNumber++;
                }
                break;

            case 14:
                if (timer.milliseconds() < 500)
                {
                    robot.drive(2, 1);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.stillAdjust(-45);
                }
                else if (timer.milliseconds() < 2200)
                {
                    robot.drive(2, 1);
                }
                else if (timer.milliseconds() < 4000)
                {
                    robot.drive(1, 1);
                }
                else if (timer.milliseconds() > 4000)
                {
                    robot.drive(0, 0);
                    commandNumber++;
                }
                break;

        }
    }

    @Override
    public void stop() {}
}
