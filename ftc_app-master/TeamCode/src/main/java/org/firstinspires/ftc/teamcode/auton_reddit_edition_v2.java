package org.firstinspires.ftc.teamcode;

/*plotnw*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "redditlullul", group = "LINEAR_AUTON")
public class auton_reddit_edition_v2 extends OpMode {

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
        //should work? probably won't compile right now, i know i didnt import the tuple thing
        instructions = new Tuple[10]
        instructions[0] = Tuple (7, 7);
      
        int newTargetL;
        int newTargetR;
        int i = 0;
        doule power = 0.0;
        if (!(rightMotor.isBusy()) && !(leftMotor.isBusy())) {
          
          newTargetL = newTargetL + getLeft(instructions[i]);
          newTargetR = newTargetR + getRight(instructions[i];
          i++;
        }

        leftMotor.setTargetPosition(newTargetL);
        rightMotor.setTargetPosition(newTargetR);

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        if (rightMotor.isBusy() || leftMotor.isBusy()) {
            power = .5
        }

        motorLeft.setPower(power);
        motorRight.setPower(power);
        //Perhaps setting the power to zero causes the loop? maybe if isBusy doesn't work, then it sets to zero, which will never finish?

    }
}
