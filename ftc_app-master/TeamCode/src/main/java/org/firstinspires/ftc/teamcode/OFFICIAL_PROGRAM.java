package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TELEBOP", group="Iterative Opmode")
//@Disabled
public class OFFICIAL_PROGRAM extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    //Defining Motors/Servos
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor elevator = null;
    private DcMotor shooter = null;
    private Servo poker = null;
    private Servo pokerWheel = null;

    //Defining various variables used
    private int countLoop = 0;
    private boolean should = false;
    private long timer = 0;
    private long starTime = 0;

    @Override
    public void init() {
        //Getting the actual motors and binding them to the previously defined objects
        leftMotor  = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");
        poker = hardwareMap.servo.get("poker");
        pokerWheel = hardwareMap.servo.get("pokerWheel");
        //Setting directions for the motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);
        //Setting default location for the servo
        pokerWheel.setPosition(0);
        //Telling user that the initialization has been completed
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        runtime.reset();
        //Moving poker out from previous position
        pokerWheel.setPosition(.5);
    }

    @Override
    public void loop() {
        if (should) {
            countLoop++;
        }
        telemetry.addData("Status", "Running: " + runtime.toString());
        double driveLeft;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = helperFunction.triggerToFlat(gamepad2.left_trigger);
        double runShooter = helperFunction.triggerToFlat(gamepad2.right_trigger);

        if (gamepad1.dpad_up) {
            driveLeft = -1;
        }
        else if (gamepad1.dpad_down) {
            driveLeft = 1;
        }
        else {
            driveLeft = 0;
        }
        if (gamepad1.b) {
            should = !should;
        }
        if (gamepad2.x) {
            countLoop = 0;
        }
        if (gamepad2.b) {
            timer = System.currentTimeMillis() - starTime;
        }
        if (gamepad1.x) {
            poker.setPosition(.55);
            should = true;
            starTime = System.currentTimeMillis();
        }
        if (gamepad1.y) {
            poker.setPosition(.45);
            should = true;
            starTime = System.currentTimeMillis();
        }
        if (gamepad1.a) {
            poker.setPosition(.50);
        }
        if (gamepad2.left_bumper) {
            runElevator = -1;
        }

        if (gamepad2.right_bumper) {
            runShooter = -.5;
        }
        

        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        leftMotor.setPower(driveLeft);
        rightMotor.setPower(driveRight);
        elevator.setPower(helperFunction.triggerToFlat(runElevator));
        shooter.setPower(helperFunction.triggerToFlat(runShooter));


        telemetry.addData("loop number", countLoop);
        telemetry.addData("timer", timer);
        telemetry.addData("driveLeft", driveLeft);
        telemetry.addData("driveRight", driveRight);
        telemetry.addData("elevator", runElevator);
        telemetry.addData("shooter", runShooter);

    }

    @Override
    public void stop() {
        pokerWheel.setPosition(0);
    }


}
