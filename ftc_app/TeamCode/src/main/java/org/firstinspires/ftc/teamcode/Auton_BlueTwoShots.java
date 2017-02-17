package org.firstinspires.ftc.teamcode;

/*plotnw*/

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
        //telemetry.addData("Red Value", robot.getRed());
        //telemetry.addData("Blue Value", robot.getBlue());
        telemetry.addData("Command", commandNumber);
        //telemetry.addData("current time", timer.seconds());

        if (robot.getIsRunningToTarget() && isGoingForward)
        {
            if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 10) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 10) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 10) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 10)
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
                robot.stopMovement();
                robot.setIsRunningToTarget(false);
            }

            telemetry.update();
            telemetry.addData("inLoop", false);
            return;
        }

        switch (commandNumber)
        {
            case 1:
                isGoingForward = true;
                robot.runToPosition(0.1, 36);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    robot.chill();
                    x++;
                }
                if (timer.milliseconds() < 1000)
                {

                }
                else if (timer.milliseconds() < 2000)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 4000)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 4500)
                {
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() < 5500 )
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
                robot.runTurnRight(0.3, 18);
                commandNumber++;
                break;

            case 4:
                isGoingForward = true;
                robot.runToPosition(0.1, 205);
                commandNumber++;
                break;

            case 5:
                isGoingForward = false;
                robot.runTurnLeft(0.3, 18);
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
                break;

            case 9:
                if (robot.getBlue() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(.3, 7);
                    x = 0;
                    commandNumber++;
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
                else if (timer.milliseconds() < 2500)
                {
                    robot.rightServoOut();
                }
                else if (timer.milliseconds() < 4500)
                {
                    robot.rightServoIn();
                    robot.drive(1, 0.3);
                    x = 2;
                }
                else if (timer.milliseconds() > 3500)
                {
                    //robot.rightServoStop();
                    commandNumber++;
                }
                break;

            case 11:
                /*if (x == 1)
                {
                    timer.reset();
                    robot.stopMovement(1, .3);
                    x++;
                }
                else if (timer.milliseconds() < 1000){}
                else { commandNumber++; }*/
                commandNumber++;
                break;

            case 12:
                if (robot.getBlue() >= 3)
                {
                    robot.stopMovement();
                    robot.runToPosition(.3, 8);
                    commandNumber++;
                    x = 2;
                }
                break;

            case 13:
                if (x == 2)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.stopMovement();
                    x++;
                }
                else if (timer.milliseconds() < 2500)
                {
                    robot.rightServoOut();
                }
                else if (timer.milliseconds() < 4500)
                {
                    robot.rightServoIn();
                }
                else if (timer.milliseconds() > 4500)
                {
                    robot.rightServoStop();
                    commandNumber++;
                }
                break;
        }
        telemetry.addData("inLoop", robot.getIsRunningToTarget());
        telemetry.update();
    }

    @Override
    public void stop() {}
}
