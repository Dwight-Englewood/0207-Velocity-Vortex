package org.firstinspires.ftc.teamcode;

/*plotnw*/

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Disabled
@Autonomous(name = "RedTurnAuton", group = "ITERATIVE_AUTON")
public class RedTurnAuton extends OpMode {

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;
    private int y = 1;
    boolean diagRun = false;

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
        telemetry.addData("FL Delta", Math.abs(robot.getCurPosFL() - robot.FLtarget));
        telemetry.addData("FR Delta", Math.abs(robot.getCurPosFR() - robot.FRtarget));
        telemetry.addData("BL Delta", Math.abs(robot.getCurPosBL() - robot.BLtarget));
        telemetry.addData("BR Delta", Math.abs(robot.getCurPosBR() - robot.BRtarget));

        if (robot.getIsRunningToTarget() && diagRun)
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
                diagRun = false;
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 1000) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 1000) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 1000) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 1000)
                    )
            {
                robot.drive(0, .3);
            }
            else if (
                    (Math.abs(robot.getCurPosFL() - robot.FLtarget) < 2500) &&
                    (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 2500) &&
                    (Math.abs(robot.getCurPosBL() - robot.BLtarget) < 2500) &&
                    (Math.abs(robot.getCurPosBR() - robot.BRtarget) < 2500)
                    )
            {
                robot.drive(0, .5);
            }
            telemetry.update();
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
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
                robot.setIsRunningToTarget(false);
            }
            telemetry.update();
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
            return;
        }


        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(0.35, 36);
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
                    robot.drive();
                    commandNumber++;
                }
                break;

            case 3:
                robot.runTurnLeft(.3, 16);
                commandNumber++;
                break;

            case 4:
                robot.runToPosition(.7, 175);
                diagRun = true;
                commandNumber++;
                break;

            case 5:
                robot.runTurnRight(.3, 16);
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
                if (robot.getRed() >= 3)
                {
                    robot.drive();
                    robot.runToPosition(.2, 3);
                    x = 0;
                    commandNumber++;
                }
                break;

            case 10:
                if (x == 0)
                {
                    timer.reset();
                    robot.runUsingEncoders();
                    x++;
                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.leftServoOut();
                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.leftServoIn();
                }
                else if (timer.milliseconds() > 3000)
                {
                    robot.leftServoStop();
                    commandNumber++;
                }
                break;

            case 11:
                robot.drive(1, 0.3);
                commandNumber++;
                break;

            case 12:
                if (robot.getRed() >= 3)
                {
                    robot.drive();
                    robot.runToPosition(.2, 3);
                    commandNumber++;
                    x = 2;
                }
                break;

            case 13:
                if (x == 1)
                {
                    timer.reset();
                    x++;
                }
                if (timer.seconds() > 0.5 && timer.seconds() < 2.0)
                {
                    robot.leftServoOut();
                }
                if (timer.seconds() > 2.0 && timer.seconds() < 3.5)
                {
                    robot.leftServoIn();
                }
                if (timer.seconds() > 3.5)
                {
                    robot.leftServoStop();
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
