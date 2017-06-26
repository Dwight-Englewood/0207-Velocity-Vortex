package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aburur on 2/26/17.
 */

@Autonomous(name = "EXTREME", group = "RED")
//@Disabled
public class EXTREME_RED_AUTON extends OpMode
{
    Bot robot = new Bot();

    ElapsedTime timer = new ElapsedTime();
    int commandNumber = 1;

    boolean isGoingForward = false;
    boolean runningToDistance = false;
    int x = -1;

    int targetHeading = 0;
    int targetRange = 15;

    @Override
    public void init()
    {
        robot.init(hardwareMap, telemetry);
        robot.intakeServoOpen();
        timer.reset();
    }

    @Override
    public void init_loop()
    {

        if (!robot.isCalibrating() && timer.milliseconds() % 100 < 20)
        {
            telemetry.addLine("GOOD TO GO");
            telemetry.update();
        }

    }
    @Override
    public void start() { super.start(); robot.resetZ();}

    @Override
    public void loop()
    {

        //telemetry.addData("Distance", robot.leftDistance());
        //telemetry.addData("Heading", robot.getHeading());
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
            if (robot.leftDistance() <= targetRange )
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
                isGoingForward = false;
                robot.runToLeft(10);
                commandNumber++;
                break;

            case 2:
                if (x == -1)
                {
                    isGoingForward = true;
                    robot.runUsingEncoders();
                    x++;
                }
                if (robot.rightDistance() > targetRange)
                {
                    robot.drive(3, .2);
                }
                else if (robot.rightDistance() < 10)
                {
                    robot.drive(2, .1);
                }
                else
                {
                    commandNumber++;
                    timer.reset();
                }
                break;

            case 3:
                if (x == 0)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    //runningToDistance = false;
                    x++;
                }
                if (timer.milliseconds() < 500)
                {
                    robot.stillAdjust(0);
                }
                else if (timer.milliseconds() > 500)
                {
                    robot.drive(1, .3);
                    commandNumber++;
                }
                break;



            case 4:
                if (robot.getRBlue() >= 2)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(8);
                    commandNumber++;
                }
                break;

            case 5:
                if (x == 1)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1100)
                {
                    robot.rightServoOut();
                    robot.drive(3, .3);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.rightServoIn();
                    robot.drive(2, .3);
                }
                else if (timer.milliseconds() > 1500)
                {
                    commandNumber++;
                }
                break;

            case 6:
                if (x == 2)
                {
                    timer.reset();
                    robot.drive(0,0);
                    robot.runUsingEncoders();
                    x++;
                }
                if (timer.milliseconds() < 1000)
                {
                    robot.stillAdjust(0);
                }
                else if (timer.milliseconds() < 1200)
                {
                    robot.drive(0,.4);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.drive(0, .6);
                }
                else if (timer.milliseconds() > 1500)
                {
                    if (robot.rightDistance() > targetRange)
                    {
                        robot.drive(3, .2);
                    }
                    else if (robot.rightDistance() < 10)
                    {
                        robot.drive(2, .1);
                    }
                    else
                    {
                        robot.stillAdjust(0);
                        commandNumber++;
                        timer.reset();
                    }
                }

                break;

            case 7:
                if (timer.milliseconds() > 500)
                {
                    robot.drive(0, .4);
                    timer.reset();
                    commandNumber++;
                }
                else
                {
                    robot.stillAdjust(0);
                }
                break;

            case 8:
                if (robot.getRBlue() >= 2)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(7);
                    commandNumber++;
                }
                break;

            case 9:
                if (x == 3)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1200)
                {
                    robot.rightServoOut();
                    robot.drive(3, .3);
                }
                else if (timer.milliseconds() > 1200)
                {
                    robot.leftServoIn();
                    robot.drive(0,0);
                    timer.reset();
                    commandNumber++;
                }
                break;

            case 10:
                if (timer.milliseconds() < 500)
                {
                    robot.drive(2, 1);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.leftServoStop();
                    robot.stillAdjust(-45);
                }
                else if (timer.milliseconds() > 1500)
                {
                    robot.runToPosition(140);
                    commandNumber++;
                }
                break;

            case 11:
                if (x == 4)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    x++;
                }
                if (timer.milliseconds() < 1000)
                {
                    robot.stillAdjust(-90);
                }
                else
                {
                    commandNumber++;
                }
                break;

            case 12:
                if (robot.rightDistance() > targetRange)
                {
                    robot.drive(3, .2);
                }
                else if (robot.rightDistance() < 10)
                {
                    robot.drive(2, .1);
                }
                else
                {
                    robot.drive(0,0);
                    commandNumber++;
                    timer.reset();
                }
                break;

            case 13:
                if (x == 5)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    //runningToDistance = false;
                    x++;
                }
                if (timer.milliseconds() < 1000)
                {
                    robot.stillAdjust(-90);
                }
                else if (timer.milliseconds() > 1000)
                {
                    robot.drive(0, .3);
                    commandNumber++;
                }
                break;

            case 14:
                if (robot.getRBlue() >= 2)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(8);
                    commandNumber++;
                }
                break;

            case 15:
                if (x == 6)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1100)
                {
                    robot.rightServoOut();
                    robot.drive(3, .3);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.rightServoIn();
                    robot.drive(2, .3);
                }
                else if (timer.milliseconds() > 1500)
                {
                    commandNumber++;
                }
                break;

            case 16:
                if (x == 7)
                {
                    timer.reset();
                    robot.drive(0,0);
                    robot.runUsingEncoders();
                    x++;
                }
                if (timer.milliseconds() < 1000)
                {
                    robot.stillAdjust(-90);
                }
                else if (timer.milliseconds() < 1200)
                {
                    robot.drive(1,.4);
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.drive(1, .6);
                }
                else if (timer.milliseconds() > 1500)
                {
                    if (robot.leftDistance() > targetRange)
                    {
                        robot.drive(3, .2);
                    }
                    else if (robot.leftDistance() < 10)
                    {
                        robot.drive(2, .1);
                    }
                    else
                    {
                        robot.stillAdjust(-90);
                        commandNumber++;
                        timer.reset();
                    }
                }
                break;

            case 17:
                if (timer.milliseconds() > 500)
                {
                    robot.drive(0, .3);
                    timer.reset();
                    commandNumber++;
                }
                else
                {
                    robot.stillAdjust(-90);
                }
                break;

            case 18:
                if (robot.getRBlue() >= 2)
                {
                    robot.stopMovement();
                    isGoingForward = true;
                    robot.runToPosition(7);
                    commandNumber++;
                }
                break;

            case 19:
                if (x == 3)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1200)
                {
                    robot.rightServoOut();
                    robot.drive(3, .3);
                }
                else if (timer.milliseconds() > 1200)
                {
                    robot.rightServoIn();
                    robot.drive(0,0);
                    timer.reset();
                    commandNumber++;
                }
                break;

        }
    }

    @Override
    public void stop() {}
}