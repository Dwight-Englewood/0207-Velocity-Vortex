package org.firstinspires.ftc.teamcode;

/*plotnw*/

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name = "Red Two Shot", group = "RED")
public class Auton_RedTwoShots extends OpMode {

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);
    // Integer to hold the current command number for the switch below.
    int commandNumber = 1;

    // Integers to ensure an if statement is only called into once in later code
    private int x = 0;
    private int y = 1;

    // This boolean states whether the robot is driving forward or turning when a command is given to the bot
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
        //Various telemetry
        telemetry.addData("FL Pos", robot.getCurPosFL());
        telemetry.addData("BL Pos", robot.getCurPosBL());
        telemetry.addData("FR Pos", robot.getCurPosFR());
        telemetry.addData("BR Pos", robot.getCurPosBR());
        telemetry.addData("Command", commandNumber);

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
         * If the robot is driving with encoder ticks but not driving forward then slipping is not
         * an issue. Thus, the program enters this statement and checks only if the robot is at the
         * target. If it is, the robot will be stopped and the next command will be given.
         */
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
            // See line 149 comment
            telemetry.update();
            return;
        }
        /**
         * If the bot is not driving to the next position, then based on the current command code
         * will be run.
         */
        switch (commandNumber)
        {
            /**
             * Command 1: the bot drives forward 36 centimeters.
             */
            case 1:
                isGoingForward = true;
                robot.runToPosition(39);
                commandNumber++;
                break;

            /**
             * Command 2: wait one second then shoot, elevate a ball, shoot based on time.
             */
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

            /**
             * Command 3: the bot turns to the left until it is at approximately a 45 degree angle.
             */
            case 3:
                isGoingForward = false;
                robot.runTurnLeft(0.3, 21);
                commandNumber++;
                break;

            /**
             * Command 4: the bot drives forward 199 centimeters.
             */
            case 4:
                isGoingForward = true;
                robot.runToPosition(177);
                commandNumber++;
                break;

            /**
             * Command 5: the bot turns to the right until it is at approximately a 45 degree angle.
             */
            case 5:
                isGoingForward = false;
                robot.runTurnRight(0.3, 19);
                commandNumber++;
                break;

            /**
             * Command 6: previously did something, currently nothing.
             */
            case 6:
                commandNumber++;
                break;

            /**
             * Command 7: previously did something, currently nothing.
             */
            case 7:
                commandNumber++;
                break;

            /**
             * Command 8: the drive motors are set to run using encoders. The bot drives forward at
             * speed 0.3 indefinitely
             */
            case 8:
                robot.runUsingEncoders();
                robot.drive(0, .3);
                commandNumber++;
                timer.reset();
                break;

            /**
             * Command 9: if the color sensor finds a value of red greater than 2, the robot stops
             * moving. Then, the robot adjusts so that the poker is in front of the beacon button.
             */
            case 9:
                if (robot.getRed() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(4);
                    commandNumber++;
                }
                else if (timer.milliseconds() > 4000)
                {
                    robot.drive(1, .3);
                    commandNumber = 11;
                }
                break;

            /**
             * Command 10: the robot extends the servo. After 2.5 seconds the bot starts driving
             * backwards to the other beacon and the servo gets pulled back. The next command is
             * called after 1/2 a second to prevent mistakenly hitting the same beacon twice.
             */
            case 10:
                if (x == 1)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.drive(2, .3);
                }
                else if (timer.milliseconds() > 1500)
                {
                    robot.stopMovement();
                    commandNumber++;
                }
                break;

            /**
             * Command 11: same as command 9.
             */
            case 11:
                if (robot.getRed() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(5);
                    commandNumber++;
                }
                break;

            /**
             * Command 12: same as command 10 except the robot does not drive backwards afterwards.
             */
            case 12:
                if (x == 2)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.drive(2, .3);
                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.drive(3, .3);
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

            /**
             * Command 13: the robot strafes to the right
             */
            case 13:
                if (x == 3)
                {
                    timer.reset();
                    robot.stopMovement();
                    robot.runUsingEncoders();
                    x++;
                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.drive(3, 1);
                }
                break;
        }
        telemetry.addData("commandNumber", commandNumber);
        telemetry.update();
    }

    @Override
    public void stop() {}
}
