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

        if (robot.getIsRunningToTarget())
        {

            if (((Math.abs(robot.getCurPosFL() - robot.FLtarget)) < 150) && ((Math.abs(robot.getCurPosFR() - robot.FRtarget)) < 150) && ((Math.abs(robot.getCurPosBL() - robot.BLtarget)) < 150) && ((Math.abs(robot.getCurPosBR() - robot.BRtarget)) < 150))
            {
                robot.setIsRunningToTarget(false);
            }
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(0.7, 36);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    robot.chill();
                    x++;
                }
                if (timer.milliseconds() < 2000)
                {

                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 5000)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 5500)
                {
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() < 6500 )
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
                robot.runTurnLeft(.5, 48);
                commandNumber++;
                break;

            case 4:

                robot.runToPosition(.7, 50);
                commandNumber++;
                break;

            case 5:
                robot.runTurnRight(.5, 48);
                commandNumber++;
                break;

            case 6:
                robot.runToLeft(1, 50);
                commandNumber++;
                break;

            case 7:
                robot.runUsingEncoders();
                robot.drive(0, .3);
                commandNumber++;
                break;

            case 8:
                if (robot.getRed() >= 3)
                {
                    robot.drive();
                    robot.runToPosition(.2, 8);
                    commandNumber++;
                }
                break;

            case 9:
                if (x == 1)
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

            case 10:
                robot.drive(1, 0.3);
                commandNumber++;
                break;

            case 11:
                if (robot.getRed() >= 3)
                {
                    robot.drive();
                    robot.runToPosition(.2, 8);
                    commandNumber++;
                    x = 2;
                }
                break;

            case 12:
                if (x == 2)
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
