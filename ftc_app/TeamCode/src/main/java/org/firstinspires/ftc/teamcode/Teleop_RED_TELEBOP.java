/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Bot;

import static java.lang.Thread.sleep;

@TeleOp(name="Red Telebop", group="MAIN")
//@Disabled

public class Teleop_RED_TELEBOP extends OpMode {
    private ElapsedTime timer = new ElapsedTime();
    Bot robot = new Bot();

    //These booleans are used to determine which strafing action is being performed, to avoid conflicts between the different strafing options
    boolean strafingLeft = false;
    boolean strafingRight = false;
    boolean strafingDiagDU = false;
    boolean strafingDiagDD = false;
    boolean strafingDiagY = false;
    boolean strafingDiagA = false;

    //Enum used to store state of a servo
    public enum ServoStates {STOP, IN, OUT};

    //variables to store the servo states
    private ServoStates rservo = ServoStates.STOP;
    private ServoStates lservo = ServoStates.STOP;

    //Variables used to store the time at which the servo button was pressed
    private long rtime;
    private long ltime;

    //boolean that stores whether the servo is currently moving, and thus whether to ignore relvant inputs
    private boolean rservoactive = false;
    private boolean lservoactive = false;

    //counters for how many times the servo has been extended, and thus to track whether pressing it again will
    //make it fall out
    private int rcount = 0;
    private int lcount = 0;

    //int used to control inversion. int for reasons explained later
    private int invert = 1;

    //adds a timeout to the invert button, to prevent rapid switching of invert and back
    private long invertLen = 1;

    //whether bot is currently in automated beacon press mode
    private boolean hittingBeacon = false;
    private boolean autoDrive = false;

    //Whether bot is currently in automated cap routine
    private boolean capRoutine = false;
    private long capTimer = 0;

    //Whether an incorrect ball intake has been detected.
    private boolean wrongBall = false;

    //Whether currently moving a ball into the shooter
    private boolean movingBall = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap, telemetry);
    }


    @Override
    public void init_loop() {}

    @Override
    public void start() {robot.primeCaps();}

    @Override
    public void loop() {
         /* Invert Button
            We check whether the left bumper is presed, and the last invert was more than half a second ago.
            Without that check, invert would switch many times per second, since the person controlling the
            invert button cannot press it for only one cycle of the teleop.
          */
        if (gamepad1.left_bumper && (System.currentTimeMillis() - invertLen  > 500)) {
            invert = invert * (-1); //change the sign of invert
            invertLen = System.currentTimeMillis(); //reset invertlen, which stores when the last inversion was
        } else {
            ; //Do nothing. The else is here for readability.
        }

         /*
            Driving, based on joysticks
          */
        if (!robot.getIsStrafing() && !autoDrive) { //Check if the robot is not strafing. If it is, we don't want to mess with it strafing and thus we skip all joystick related movement
            if (gamepad1.right_stick_y > 0.15) {
                robot.driveInvert(10 + invert, (1 * invert * gamepad1.right_stick_y), (this.invert));
                 /* First instance of the invert
                    The way that invert is implemented is through a single variable that can be two values: 1, or -1
                    This makes it simple to reverse motor directions, as we just multiply the power by invert.
                    What is more difficult is switching which joystick controls which drive trains
                    I implemented this by modifying the original robot.drive function
                    The new, robot.driveInvert function has each drive train centered around a value
                    then, by adding the value of invert, the value passed to robot.driveInvert will
                    be for the correct motor
                  */

            }
            else if (gamepad1.right_stick_y < -0.15) {
                robot.driveInvert(10 + invert, (-1) * invert * Math.abs(gamepad1.right_stick_y), (this.invert)); //See comment starting at line 116
            }
            else {
                robot.driveInvert(10 + invert, 0, this.invert);
            }

            if (gamepad1.left_stick_y > 0.15) {
                robot.driveInvert(10 - invert, 1 * invert * gamepad1.left_stick_y, this.invert); //See comment starting at line 116
            }
            else if (gamepad1.left_stick_y < -0.15) {
                robot.driveInvert(10 - invert, (-1) * invert * Math.abs(gamepad1.left_stick_y) - .5, (this.invert)); //See comment starting at line 116
            }
            else {
                robot.driveInvert(10 - invert, 0, this.invert);
            }

            if (gamepad1.left_trigger > 0.1) {
                robot.driveInvert(4 - invert, gamepad1.left_trigger, this.invert); //See comment starting at line 116
                strafingLeft = true; //Tell the program we're strafing, so we won't interfere with it with joystick control
            }

            if (gamepad1.right_trigger > 0.1) {
                robot.driveInvert(4 +  invert, gamepad1.right_trigger, this.invert); //See comment starting at line 116
                strafingRight = true;
            }
            if (gamepad1.dpad_up) {
                robot.driveInvert(13 - invert, 1 * invert, this.invert);
                strafingDiagDU = true;
            }

            if (gamepad1.dpad_down) {
                robot.driveInvert(13 + invert, (-1) * invert, this.invert);
                strafingDiagDD = true;
            }

            if (gamepad1.y) {
                robot.driveInvert(13 + invert, 1 * invert, this.invert);
                strafingDiagY = true;
            }

            if (gamepad1.a) {
                robot.driveInvert(13 - invert, (-1) * invert, this.invert);
                strafingDiagA = true;
            }

        }
        else if (!autoDrive){
            if (strafingDiagDU && !(gamepad1.dpad_up)) {
                robot.stopMovement();
                strafingDiagDU = false;
            }
            if (strafingDiagDD && !(gamepad1.dpad_down)) {
                robot.stopMovement();
                strafingDiagDD = false;
            }

            if (strafingDiagY && !(gamepad1.y)) {
                robot.stopMovement();
                strafingDiagY = false;
            }

            if (strafingDiagA && !(gamepad1.a)) {
                robot.stopMovement();
                strafingDiagA = false;
            }

            if (strafingLeft && gamepad1.left_trigger > 0) {
                robot.driveInvert(4 - invert, gamepad1.left_trigger, this.invert);
            }

            if (strafingRight && gamepad1.right_trigger > 0) {
                robot.driveInvert(4 +  invert, gamepad1.right_trigger, this.invert);
            }
            if (strafingLeft && gamepad1.left_trigger == 0) {
                robot.stopMovement();
                strafingLeft = false; //If we are no longer pressing the left trigger, stop strafing
            }
            else if (strafingRight && gamepad1.right_trigger == 0 ) {
                robot.stopMovement();
                strafingRight = false;
            }
        }

         /* Shooter and Elevator commands

         */
        if (gamepad2.right_trigger > 0.5)       {robot.setShooter(1);}
        else                                    {robot.setShooter(0);}

        /**
         * Elevator commands + auto rejection of blue balls(red side)
         * If the left trigger is pressed and wrong ball is not active
         */
        if (gamepad2.left_stick_y > 0.5 && !wrongBall)
        {
            //Checks to see if the intake color sensor is sensing a blue ball
            if (robot.getIntake().equals("blue"))
            {
                // If so, wrong ball = true, moving us to the next command
                wrongBall = true;
                timer.reset();
            }
            // Otherwise, the elevator runs as normal
            else
            {
                robot.setElevator(1);
            }
        }
        // If left bumper is pressed and wrong ball is not active, the elevator runs backwards
        else if (gamepad2.left_stick_y < -.5 && !wrongBall)
        {
            robot.setElevator(-1);
        }
        // If nothing is pressed and wrong ball is not active, the elevator does not move
        else if (!wrongBall && !movingBall)
        {
            robot.setElevator(0);
        }

        //Front spinner servo control
        if (gamepad2.right_stick_y > 0.5 && !wrongBall)
        {
            //Checks to see if the intake color sensor is sensing a blue ball
            if (robot.getIntake().equals("blue"))
            {
                // If so, wrong ball = true, moving us to the next command
                wrongBall = true;
                timer.reset();
            }
            // Otherwise, the elevator runs as normal
            else
            {
                robot.spinnerServoIn();
            }
        }
        // If left bumper is pressed and wrong ball is not active, the elevator runs backwards
        else if (gamepad2.right_stick_y < -.5 && !wrongBall)
        {
            robot.spinnerServoOut();
        }
        // If nothing is pressed and wrong ball is not active, the elevator does not move
        else if (!wrongBall && !movingBall)
        {
            robot.spinnerServoStop();
        }

        // If wrong ball is active
        if (wrongBall)
        {
            // If time is less than 2000, run the elevator backwards, pushing the incorrectly colored ball out of the system
            if (timer.milliseconds() < 2000)
            {
                robot.setElevator(-1);
                robot.spinnerServoOut();
            }
            // If the time is greater than two seconds, set the elevator to power zero and wrongball is inactive, returning to normal function
            else
            {
                robot.setElevator(0);
                robot.spinnerServoStop();
                wrongBall = false;
            }

        }

          /* Servo Control
            We represent the servo as existing in 3 states - STOP, IN, OUT - each corresponding to what it should be doing at the time
            This structure is needed due to the design of the TELEOP, in which in continually loops this method.
            If we moved the servo directly within this, the bot may get caught by the watchdog process and get killed
            Also, it would prevent other actions from being performed simultaneously
            We then have 3 seperate control structures
          */

         /* This part gets the user input
            It also checks to see whether the servo extension count is < 4, to prevent the servo pushing the beacon presser off
         */
        if (gamepad2.x && lcount < 4) {
            lservo = ServoStates.OUT;
            ltime = System.currentTimeMillis(); //Store the time it was pushed - This is used to control the timing of the servo states
            lservoactive = true;
        } else if (gamepad2.y && lcount > -1) {
            lservo = ServoStates.IN;
            ltime = System.currentTimeMillis();
            lservoactive = true;
        } else {

        }
         /* Controls the state of the servo
            Since we want the servo to automatically stop after a period of time
          */
        if (lservoactive) {
            //lservoactive = true;//I dont think we need this, doesnt make much sense
            switch (lservo) {
                case STOP:
                    //If the servo is stopped, it shouldn't change states, so it just does nothing
                    break;
                case OUT:
                    if (System.currentTimeMillis() - ltime < 300) {
                        ;
                    } else {
                        //After 300 miliseconds have elapsed, set the servo state to STOP, so it no longer moves out
                        lservo = ServoStates.STOP;
                        lservoactive = false;
                        lcount++; //Increment the counter for extensions as the servo was extended
                    }
                    break;
                case IN:
                    if (System.currentTimeMillis() - ltime < 300) {
                        ;
                    } else {
                        lservo = ServoStates.STOP;
                        lservoactive = false;
                        lcount--; //Decrement the counter for extensions as the servo was pulled in
                    }
                    break;
            }
        }

         /*
         Handing off the actual servo control to the bot class
          */
        switch (lservo) {
            case OUT:
                robot.servoOut((-1) * invert);
                break;
            case IN:
                robot.servoIn((-1) * invert);
                break;
            case STOP:
                robot.servoStop((-1) * invert);
                break;
        }

        //This segment is identical to the lservo routine. See above
        if (gamepad2.b && rcount < 4) {
            rservo = ServoStates.OUT;
            rtime = System.currentTimeMillis();
            rservoactive = true;
        } else if (gamepad2.a && rcount > -1) {
            rservo = ServoStates.IN;
            rtime = System.currentTimeMillis();
            rservoactive = true;
        } else {
            ;
        }
        if (rservoactive) {
            rservoactive = true;
            switch (rservo) {
                case STOP:
                    break;
                case OUT:
                    if (System.currentTimeMillis() - rtime < 300) {
                        ;
                    } else {
                        rservo = ServoStates.STOP;
                        rservoactive = false;
                        rcount++;
                    }
                    break;
                case IN:
                    if (System.currentTimeMillis() - rtime < 300) {
                        ;
                    } else {
                        rservo = ServoStates.STOP;
                        rservoactive = false;
                        rcount--;
                    }
                    break;
            }
        }
        switch (rservo) {
            case OUT:
                robot.servoOut(1 * invert);
                break;
            case IN:
                robot.servoIn(1 * invert);
                break;
            case STOP:
                robot.servoStop(1 * invert);
                break;
        }

        /**
         *  The intake servo prevents balls from entering the shooter. This ensures that only one
         *  ball is in the shooter at a time. The following routine will move one ball from the
         *  elevator into the shooter.
         */
        if (gamepad2.right_bumper && !movingBall)
        {
            timer.reset();
            movingBall = true;
        }

        if (movingBall)
        {
            if (timer.milliseconds() < 500)
            {
                robot.intakeServoOpen();
                robot.setElevator(1);
            }
            else if (timer.milliseconds() < 550)
            {
                robot.intakeServoClosed();
                robot.setElevator(0);
            }
            else if (timer.milliseconds() < 750)
            {
                robot.setElevator(-1);
            }
            else if (timer.milliseconds() > 750)
            {
                robot.setElevator(0);
                movingBall = false;
            }

        }

        /*
         *   Automatically lines up and strafes into a beacon, changing the color with very high
         *   precision. This helps our drivers when they have trouble seeing how the robot is lined
         *   up with the far beacons.
         */
        if (gamepad1.b && !hittingBeacon)
        {
            //Sets autodrive to true, preventing normal drive commands from overriding the ones to come.
            autoDrive = true;

            // If the ODS sensor finds the line under the beacon, stop movement and go to next statement, otherwise drive forward.
            if (robot.getLineLight() > .7)
            {
                timer.reset();
                robot.stopMovement();
                hittingBeacon = true;
            }
            else
            {
                robot.drive(0, .3);
            }
        }
        else if (gamepad1.b && hittingBeacon)
        {
            // Drive backwards 11 centimeters so that the beacon poker is lined up with a button
            if (timer.milliseconds() < 100)
            {
                robot.runToPosition(-11);
            }
            // Strafe the poker into the beacon
            else if (timer.milliseconds() > 2000 && timer.milliseconds() < 4500)
            {
                robot.runUsingEncoders();
                robot.drive(2, .5);
            }
            // Pull awawy from the beacon
            else if (timer.milliseconds() < 4500)
            {
                robot.drive(3, .3);
            }
            // Return to normal driving
            else if (timer.milliseconds() > 4500)
            {
                hittingBeacon = false;
                autoDrive = false;
            }
        }
        // Failsafe returns to normal driving conditions
        else if (autoDrive)
        {
            robot.stopMovement();
            robot.runUsingEncoders();
            autoDrive = false;
        }

        // Auto fork-drop
        /**
         * Automatically lifts and lowers the linear motion. This drops the forks down and prevents
         * the linear motion from de-spooling. This could be done by hand by presssing the up then
         * down buttons on the gamepads. However, by doing it via programming, we eliminate risk of
         * driver error and de-spooling.
         */

        // If dpad right is pressed, the fork drop begins.
        if (gamepad2.dpad_right)
        {
            capRoutine = true;
            timer.reset();
        }

        // If fork drop is running
        if (capRoutine)
        {
            if (timer.milliseconds() < 500)
            {
                robot.launchForks();
            }
            else
            {
                robot.stopLaunchingForks();
            }
            // For one second, raise the cap
            if (timer.milliseconds() < 1500 && timer.milliseconds() > 500 && !robot.getIsCapMaxed())
            {
                robot.liftCap();
            }
            // For 1 second lower the cap
            else if (1750 < timer.milliseconds() && timer.milliseconds() < 2500)
            {
                robot.lowerCap();
            }
            // Close the routine
            else if (timer.milliseconds() > 2750)
            {
                capRoutine = false;
            }
            // Stop moving the cap
            else
            {
                robot.stopLiftCap();
            }
        }

        /* Cap ball Controls
         * Depending on button pressing, it will raise or lower the linear motion. However, when
         * raising the cap, the program checks to ensure that the linear motion is not riased too
         * high
         */
        if (!capRoutine)
        {
            if (gamepad2.dpad_up && !robot.getIsCapMaxed())
            {
                robot.liftCap();
            }
            else if (gamepad2.dpad_down)
            {
                robot.lowerCap();
            }
            else
            {
                robot.stopLiftCap();
            }
        }

         /* Various telemetry */
        telemetry.addData("invert", invert);
        telemetry.update();
    }

    @Override
    public void stop() {}
}