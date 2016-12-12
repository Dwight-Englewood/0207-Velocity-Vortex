package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//a device that can be in one of a set number of stable conditions depending
// on its previous condition and on the present values of its inputs.

//@Disabled
@Autonomous(name = "ShootingStateMachineFar", group = "ITERATIVE_AUTON")
public class ShootingAutonSMFar extends OpMode {

    long start_time = 0;
    long current_time;
    long wait_time;
    long time;

    DcMotor rightMotor = null;
    DcMotor leftMotor = null;
    DcMotor elevator = null;
    DcMotor shooter = null;

    ElapsedTime timer = new ElapsedTime(0);



    int commandNumber = 1;

    private int x = 0;

    int Rtarget;
    int Ltarget;
    boolean runningToTarget = true;

    private double [] powerLevels = {0.3, 0.0, 0.25, -0.3, 0.15, -0.25, -0.15};

    @Override
    public void init()
    {
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() { super.start();}

    @Override
    public void loop() {
        telemetry.addData("Left Position", leftMotor.getCurrentPosition());
        telemetry.addData("Right Position", rightMotor.getCurrentPosition());
        telemetry.addData("Command", commandNumber);
        telemetry.addData("current time", timer.seconds());

        if (runningToTarget && (Math.abs(leftMotor.getCurrentPosition() - Ltarget) > 25 || Math.abs(rightMotor.getCurrentPosition() - Rtarget) > 25)) {
            telemetry.addData("inLoop", runningToTarget);
            telemetry.update();
            return;
        }
        switch (commandNumber) {
            case 1:
                driveToPosition(65, 2);
                commandNumber++;
                break;

            case 2:
                if (x == 0) {
                    timer.reset();
                    x++;
                }
                if (timer.milliseconds() < 500) {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                } else if (timer.milliseconds() < 1500) {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                } else if (timer.milliseconds() < 3000) {
                    shooter.setPower(0.0);
                    elevator.setPower(1.0);
                } else if (timer.milliseconds() < 3500) {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                } else if (timer.milliseconds() < 4500) {
                    shooter.setPower(1.0);
                    elevator.setPower(0.0);
                } else if (timer.milliseconds() < 10000) {
                    shooter.setPower(0.0);
                    elevator.setPower(0.0);
                }
                else if (timer.milliseconds() > 10000)
                {
                    commandNumber++;                                                                                                                                                                                                                                                                                                                                                                                                                                                            
                }
                break;

            case 3:
                stopAndReset();
                driveToPosition(90, 0);
                commandNumber++;
                break;
            case 4:
                stopAndReset();
                commandNumber++;
                break;
        }
        telemetry.update();
    }

    @Override
    public void stop() {}

    public void driveToPosition(double target, int powerSelection)
    {
        int  targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(targetInt);
        rightMotor.setTargetPosition(targetInt);

        Ltarget = targetInt;
        Rtarget = targetInt;
    }

    public void turnLeft(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(targetInt);

        Ltarget = 0;
        Rtarget = targetInt;
    }

    public void turnRight(double target, int powerSelection)
    {
        int targetInt = helperFunction.distanceToRevs(target);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);

        rightMotor.setTargetPosition(0);
        leftMotor.setTargetPosition(targetInt);

        Rtarget = 0;
        Ltarget = targetInt;
    }

    public void startDriving(int powerSelection)
    {
        leftMotor.setPower(powerLevels[powerSelection]);
        rightMotor.setPower(powerLevels[powerSelection]);
    }

    public void stopDriving()
    {
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }

    public void stopAndReset()
    {
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
