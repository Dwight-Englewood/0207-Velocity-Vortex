package org.firstinspires.ftc.teamcode;

/*plotnw*/

//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "RedStateMachineMecanum", group = "ITERATIVE_AUTON")
public class RedStateMachineMecanum extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    Bot robot = new Bot();
    ElapsedTime timer = new ElapsedTime(0);

    int commandNumber = 1;

    private int x = 0;

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

        if (robot.getIsRunningToTarget() && (Math.abs(robot.getCurPosFL() - FLtarget) > 25 || Math.abs(robot.getMaxPowBL() - BLtarget) > 25 || (Math.abs(robot.getCurPosFR() - FRtarget) > 25) || (Math.abs(robot.getCurPosBR() - BRtarget) > 25)))
        {
            telemetry.addData("inLoop", robot.getIsRunningToTarget());
            telemetry.update();
            return;
        }

        switch (commandNumber)
        {
            case 1:
                robot.runToPosition(0, 0.3, 28.5);
                commandNumber++;
                break;

            case 2:
                if (x == 0)
                {
                    timer.reset();
                    x++;
                }
                if (timer.milliseconds() < 500)
                {

                }
                else if (timer.milliseconds() < 1500)
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() < 3000)
                {
                    robot.setShooter(0);
                    robot.setElevator(1);
                }
                else if (timer.milliseconds() < 3500)
                {
                    robot.setElevator(0);
                }
                else if (timer.milliseconds() < 4500 )
                {
                    robot.setShooter(1);
                }
                else if (timer.milliseconds() > 4500)
                {
                    robot.setShooter(0);
                    commandNumber++;
                }
                break;

            case 3:
                robot.runToPosition(2, 0.7, 100);
                commandNumber++;
                break;

            case 4:
                robot.runUsingEncoders();
                robot.drive(0, 0.3);
                commandNumber++;
                x=1;
                break;

            case 5:
                if (robot.getRed() >= 2)
                {
                    robot.drive();
                    //robot.runToPosition();
                    commandNumber++;
                }
                break;

            case 6:
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

            case 7:
                robot.runUsingEncoders();
                robot.drive(0, 0.3);
                commandNumber++;
                break;

            case 8:
                if (robot.getRed() >= 2)
                {
                    robot.drive();
                    commandNumber++;
                    x = 2;
                }
                break;
            case 9:
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
