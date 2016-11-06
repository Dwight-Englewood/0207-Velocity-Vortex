package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Tuple;


@Autonomous(name = "Auton_Encoder", group = "LINEAR_AUTON")
public class autonEncoders extends OpMode {

    DcMotor rightMotor;
    DcMotor leftMotor;
    DcMotor elevator = null;
    DcMotor shooter = null;
    long start_time;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        //Gets stuck in infinite loop? Not sure what's happening, though I suspect it's because the encoders aren't in fully
        //So it can't read the isBusy, and never will end.
        //I THINK I KNOW WHATS WRONG
        //SO LIKE JUST REMEMBER THAT THIS ENTIRE MEHTOD LOOPS
        //LOOPS
        //SO LIKE IDK BUT THAT PROBABLY MESSES WITH STUFF SINCE HOW ITS WORKING
        //instructions will have tuple with the positions required for the different positions
        //first we see if the motors are busy
        //if theyre not set the instructions, then increment the counter for which instruction
        //then set target position
        //if busy, set power
        //else keep it at zero
        Tuple[] instructions = new Tuple[10];
        instructions[0] = new Tuple (7, 7);
        //Move forward
        instructions[1] = new Tuple (-7, 7);
        //Turn left
        instructions[2] = new Tuple (7, -7);
        //Turn right
        instructions[3] = new Tuple (21, 21);
        //Move forward a lot
        instructions[4] = new Tuple (7, -7);
        //turn right
        instructions[5] = new Tuple (14, 14);
        //Move forward some

        //Random values, hopefully we can get some info out of it.
        //Possibly move this to init?

        int newTargetL = 0;
        int newTargetR = 0;
        int i = 0;
        double power = 0.0;
        if (!(rightMotor.isBusy()) && !(leftMotor.isBusy())) {
          
          newTargetL = newTargetL + (Integer) instructions[i].getLeft();
          newTargetR = newTargetR + (Integer) instructions[i].getRight();
          i++;
        }

        leftMotor.setTargetPosition(newTargetL);
        rightMotor.setTargetPosition(newTargetR);

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        if (rightMotor.isBusy() || leftMotor.isBusy()) {
            power = .5;
        }

        leftMotor.setPower(power);
        rightMotor.setPower(power);
        //Perhaps setting the power to zero causes the loop? maybe if isBusy doesn't work, then it sets to zero, which will never finish?

    }
}
