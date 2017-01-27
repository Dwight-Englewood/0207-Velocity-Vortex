package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

//@Disabled
@Autonomous(name = "BlueStateMachineMecanum", group = "ITERATIVE_AUTON")
public class BlueStateMachineMecanum extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;
    private int y = 0;

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

        if (robot.getIsRunningToTarget() && (Math.abs(robot.getCurPosFL() - FLtarget) > 25 && Math.abs(robot.getMaxPowBL() - BLtarget) > 25 && (Math.abs(robot.getCurPosFR() - FRtarget) > 25) && (Math.abs(robot.getCurPosBR() - BRtarget) > 25)))
        {
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
                    timer.reset();
                    robot.runUsingEncoders();
                    robot.drive();
                    commandNumber++;
                }
                break;

            case 3:
                if (y == 0)
                {
                    timer.reset();
                    robot.chill();
                    y++;
                }
                else if (timer.milliseconds() < 500){}
                else if (timer.milliseconds() < 3000){robot.drive(3, 1);}
                else if (timer.milliseconds() > 3000){robot.drive(); commandNumber++;}
                break;

            case 4:
                if (y == 1)
                {
                    timer.reset();
                    robot.chill();
                    y++;
                }
                else if (timer.milliseconds() < 500){}
                else if (timer.milliseconds() < 2500){robot.drive(0,.5);}
                else if (timer.milliseconds() > 2500){robot.drive(); commandNumber++;}
                x=1;
                break;

            case 5:
                if (y == 2)
                {
                    timer.reset();
                    robot.chill();
                    y++;
                }
                else if (timer.milliseconds() < 500){}
                else if (timer.milliseconds() < 2500){robot.drive(3,1);}
                else if (timer.milliseconds() > 2500){robot.drive(); commandNumber++;}
                break;

            case 6:
                robot.drive(0,.3);
                commandNumber++;
                break;

            case 7:
                if (robot.getBlue() >= 2)
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
                    robot.rightServoOut();
                }
                if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                {
                    robot.rightServoIn();
                }
                if (timer.seconds() > 2.5)
                {
                    robot.rightServoStop();
                    commandNumber++;
                }
                break;
            case 9:
                robot.drive(0, 0.3);
                commandNumber++;
                break;
            case 10:
                if (robot.getBlue() >= 2)
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
                    robot.rightServoOut();
                }
                if (timer.seconds() > 1.5 && timer.seconds() < 2.5)
                {
                    robot.rightServoIn();
                }
                if (timer.seconds() > 2.5)
                {
                    robot.rightServoStop();
                    commandNumber++;
                }
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}
}
