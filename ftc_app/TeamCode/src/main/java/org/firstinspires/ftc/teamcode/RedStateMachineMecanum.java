package org.firstinspires.ftc.teamcode;

/*plotnw*/

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "RedStateMachineMecanum", group = "ITERATIVE_AUTON")
public class RedStateMachineMecanum extends OpMode {

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;
    private int y = 0;

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
            if (robot.isRunningDiagLeft)
            {
                if ((Math.abs(robot.getCurPosBL() - robot.BLtarget) < 25) && (Math.abs(robot.getCurPosFR() - robot.FRtarget) < 25));
                {
                    robot.setIsRunningToTarget(false);
                    robot.isRunningDiagLeft = false;
                    //robot.drive();
                }
            }
            else if (Math.abs(robot.getCurPosFL() - Math.abs(robot.FLtarget)) > 25 && Math.abs(robot.getCurPosBL() - Math.abs(robot.BLtarget)) > 25 && (Math.abs(robot.getCurPosFR() - Math.abs(robot.FRtarget)) > 25) && (Math.abs(robot.getCurPosBR() - Math.abs(robot.BRtarget)) > 25))
                robot.setIsRunningToTarget(false);
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(0, 0.7, 36);
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
                    robot.drive();
                    commandNumber++;
                }
                break;

            case 3:
                robot.runDiagLeft(1, 500);
                commandNumber++;
                break;

            case 4:
                if (y == 0)
                {
                    robot.runUsingEncoders();
                    timer.reset();
                    robot.chill();
                    y++;
                }
                else if (timer.milliseconds() < 500){}
                else if (timer.milliseconds() < 2500){robot.drive(2, 1);}
                else if (timer.milliseconds() > 2500){robot.drive(); commandNumber++;}
                x=1;
                break;

            case 5:
                if (y == 1)
                {
                    timer.reset();
                    robot.chill();
                    y++;
                }
                else if (timer.milliseconds() < 500){}
                else if (timer.milliseconds() < 1500){robot.drive(3,1);}
                else if (timer.milliseconds() > 1500){robot.drive(); commandNumber++;}

            case 6:
                robot.drive(0,.3);
                commandNumber++;
                break;

            case 7:
                if (robot.getRed() >= 2)
                {
                    robot.drive();
                    //robot.runToPosition();
                    commandNumber++;
                }
                break;

            case 8:
                if (x == 1)
                {
                    timer.reset();
                    x++;
                }
                if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                {
                    robot.leftServoOut();
                }
                if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                {
                    robot.leftServoIn();
                }
                if (timer.seconds() > 2.5)
                {
                    robot.leftServoStop();
                    commandNumber++;
                }
                break;

            case 9:
                robot.drive(1, 0.3);
                commandNumber++;
                break;

            case 10:
                if (robot.getRed() >= 2)
                {
                    robot.drive();
                    commandNumber++;
                    x = 2;
                }
                break;
            case 11:
                if (x == 2)
                {
                    timer.reset();
                    x++;
                }
                if (timer.seconds() > 0.5 && timer.seconds() < 1.5)
                {
                    robot.leftServoOut();
                }
                if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                {
                    robot.leftServoIn();
                }
                if (timer.seconds() > 2.5)
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
