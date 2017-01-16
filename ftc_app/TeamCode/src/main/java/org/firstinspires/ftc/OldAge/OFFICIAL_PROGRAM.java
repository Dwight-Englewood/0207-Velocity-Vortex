package OldAge;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static OldAge.OFFICIAL_PROGRAM.Tri.STOP;
import static OldAge.OFFICIAL_PROGRAM.Tri.IN;
import static OldAge.OFFICIAL_PROGRAM.Tri.OUT;

@TeleOp(name="TELEBOP", group="Iterative Opmode")
@Disabled
public class OFFICIAL_PROGRAM extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    //Defining Motors/Servos
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor elevator = null;
    private DcMotor shooter = null;
    private CRServo poker = null;
    private Servo pokerWheel = null;

    private long time = 0;
    //Used for servo state
    public enum Tri {OUT, IN, STOP};
    private Tri servoState = STOP;
    //Magic values for when we use the crservo as a regular servo
    private double stop = .49;
    private double in = .54;
    private double out = .42;

    boolean servoRoutineActive = false;



    @Override
    public void init() {
        //Getting the actual motors and binding them to the previously defined objects
        leftMotor  = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");
        poker = hardwareMap.crservo.get("poker");
        pokerWheel = hardwareMap.servo.get("pokerWheel");

        //Setting directions for the motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);

        //Setting default location for the servo
        pokerWheel.setPosition(0);

        //poker.setPosition(.49); //Commented since we are not using the crservo as a regular servo

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

        //poker.setPosition(.49); //Commented since we are not using the crservo as a regular servo
    }

    @Override
    public void loop() {
        //Runtime telemetry
        telemetry.addData("Status", "Running: " + runtime.toString());

        //get raw values from controller
        double driveLeft;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = helperFunction.triggerToFlat(gamepad2.left_trigger);
        double runShooter = helperFunction.triggerToFlat(gamepad2.right_trigger);

        //Left motor controls
        //Different from right motor controls due to the way our controllers are set up
        if (gamepad1.dpad_up) {
            driveLeft = -1;
        }
        else if (gamepad1.dpad_down) {
            driveLeft = 1;
        }
        else {
            driveLeft = 0;
        }
        //Servo Controls
        if (gamepad1.x || gamepad2.x) {
            //poker.setPosition(in);
            servoState = IN;
        } else if (gamepad1.y || gamepad2.y) {
            //poker.setPosition(out);
            servoState = OUT;
        }
        else {
            //poker.setPosition(stop);
            servoState = STOP;
            servoRoutineActive = false;
        }

        //Servo routine logic
        if (gamepad1.b || gamepad2.b || servoRoutineActive) {
            servoRoutineActive = true;
            switch (servoState) {
                case STOP:
                    //Log when routine was started
                    time = System.currentTimeMillis();
                    //Move to next enum value
                    servoState = OUT;
                    break;
                case OUT:
                    //If the time is under a second, do nothing
                    if (System.currentTimeMillis() - time < 1000) {
                        ;
                    //If the servo has been moving for a second, advance to next move to next enum value
                    } else {
                        servoState = IN;
                        time = System.currentTimeMillis();
                    }
                    break;
                case IN:
                    //If the time is under a second, do nothing
                    if (System.currentTimeMillis() - time < 1000) {
                        ;
                    //If the servo has been moving for a second, advance to next move to next enum value
                    } else {
                        servoState = STOP;
                        time = 0;
                        servoRoutineActive = false;
                    }
            }

        }
        //servo movement logic
        switch (servoState) {
            case OUT:
                //When servoState is out, move the servo out
                //poker.setPosition(out);
                poker.setDirection(DcMotorSimple.Direction.REVERSE);
                poker.setPower(0.5);
                break;
            case IN:
                //When servoState is in, move servo in
                //poker.setPosition(in);
                poker.setDirection(DcMotorSimple.Direction.FORWARD);
                poker.setPower(0.5);
                break;
            case STOP:
                //When servoState is stop, stop the servo. Note that this actually slowly retracts the servo
                //poker.setPosition(stop);
                poker.setPower(0.0);
                break;
        }
        //Alternate controls for elevator and shooter
        if (gamepad2.left_bumper) {
            runElevator = -1;
        }
        if (gamepad2.right_bumper) {
            runShooter = -.5;
        }
        

        //Use raw values from controller to determine motor movement
        leftMotor.setPower(driveLeft);
        rightMotor.setPower(driveRight);
        elevator.setPower(helperFunction.triggerToFlat(runElevator));
        shooter.setPower(helperFunction.triggerToFlat(runShooter));

        //Add telemetry about what the robot is doing
        telemetry.addData("servoState", servoState);
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
