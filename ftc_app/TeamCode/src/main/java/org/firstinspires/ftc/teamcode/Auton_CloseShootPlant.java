package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

//@Disabled
@Autonomous(name = "Shoot Drive CLOSE", group = "SHOOTING")
public class Auton_CloseShootPlant extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;

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
        /**
         * This set of if statements allows the robot to speed up and slow down when driving forward in the auton,
         * addressing the issue of sliding when speeding up too quickly. It also helps evade the "opmode
         * stuck in loop" problem we had earlier in the year as the program won't check the commandNumber
         * switch until the bot is within a range from the target. To do this, the program first
         * checks if the robot is attempting to drive forward to a position using the run to position run-mode.
         */
        if (robot.getIsRunningToTarget())
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

        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(36);
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
                    robot.stopMovement();
                    commandNumber++;
                }
                break;

            case 3:
                if (timer.milliseconds() > 8000)
                    commandNumber++;
                break;
            case 4:
                robot.runToPosition(115);
                commandNumber++;
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}
}
