package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.OFFICIAL_PROGRAM.Tri.STOP;
import static org.firstinspires.ftc.teamcode.OFFICIAL_PROGRAM.Tri.IN;
import static org.firstinspires.ftc.teamcode.OFFICIAL_PROGRAM.Tri.OUT;

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
    private long time = 0;
    public enum Tri {OUT, IN, STOP};
    private Tri servoState = STOP;
    private double stop = .49;
    private double in = .54;
    private double out = .42;


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

        poker.setPosition(.49);
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

        poker.setPosition(.49);
    }

    @Override
    public void loop() {

        telemetry.addData("Status", "Running: " + runtime.toString());
        double driveLeft;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = helperFunction.triggerToFlat(gamepad2.left_trigger);
        double runShooter = helperFunction.triggerToFlat(gamepad2.right_trigger);

        //Left motor controls
        if (gamepad1.dpad_up) {
            driveLeft = -1;
        }
        else if (gamepad1.dpad_down) {
            driveLeft = 1;
        }
        else {
            driveLeft = 0;
        }

        if (gamepad1.x) {
            poker.setPosition(in);
        }
        if (gamepad1.y) {//goes out
            poker.setPosition(out);
        }
        if (gamepad1.a) {
            poker.setPosition(stop);
        }
        if (gamepad1.b) {
            switch (servoState) {
                case STOP:
                    time = System.currentTimeMillis();
                    servoState = OUT;
                    break;
                case OUT:
                    if (System.currentTimeMillis() - time < 1650) {
                        ;
                    } else {
                        servoState = IN;
                        time = System.currentTimeMillis();
                    }
                    break;
                case IN:
                    if (System.currentTimeMillis() - time < 1650) {
                        ;
                    } else {
                        servoState = STOP;
                        time = 0;
                    }
            }

        }

        switch (servoState) {
            case OUT:
                poker.setPosition(.42);
                break;
            case IN:
                poker.setPosition(.54);
                break;
            case STOP:
                poker.setPosition(.49);
                break;
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
