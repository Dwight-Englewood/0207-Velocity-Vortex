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


 @TeleOp(name="Telebop_Invert", group="MAIN")  // @Autonomous(...) is the other common choice
 //@Disabled
 //TODO: ADD REVERSE DRIVING SWITCH, 2 MORE MOTORS WHICH MUST BE SYNCED, 3 MORE SERVOS
 public class Teleop_Invert extends OpMode {
     private ElapsedTime runtime = new ElapsedTime();
     private ElapsedTime timer = new ElapsedTime();
     Bot robot = new Bot();

     //These booleans are used to determine whether to skip the driving section
     boolean strafingLeft = false;
     boolean strafingRight = false;

     //Enum used to store state of a servo
     public enum ServoStates {STOP, IN, OUT};

     //variables to store the servo states
     private ServoStates rservo = ServoStates.STOP;
     private ServoStates lservo = ServoStates.STOP;

     //temporary variiables used to store the time at which the servo button was pressed
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


     @Override
     public void init() {
         telemetry.addData("Status", "Initialized");
         robot.init(hardwareMap);
     }


     @Override
     public void init_loop() {}

     @Override
     public void start() {
         runtime.reset();
     }

     @Override
     public void loop() {
         telemetry.addData("Status", "Running: " + runtime.toString());

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

         /* Driving, based on joysticks

          */
         if (!robot.getIsStrafing()) { //Check if the robot is not strafing. If it is, we don't want to mess with it strafing and thus we skip all joystick related movement
             if (gamepad1.right_stick_y > 0.15) {
                 robot.driveInvert(10 + invert, 1 * invert * gamepad1.right_stick_y);
                 /* First instance of the invert
                    The way that invert is implemented is through a single variable that can be two values: 1, or -1
                    This makes it simple to reverse motor directions, as we just multiply the power by invert.
                    What is more difficult is switching which joystick controls which stopMovement trains
                    I implemented this by modifying the original robot.stopMovement function
                    The new, robot.driveInvert function has each stopMovement train centered around a value
                    then, by adding the value of invert, the value passed to robot.driveInvert will
                    be for the correct motor
                  */
             }
             else if (gamepad1.right_stick_y < -0.15) {
                 robot.driveInvert(10 + invert, (-1) * invert * Math.abs(gamepad1.right_stick_y)); //See comment starting at line 116
             }
             else {
                 robot.driveInvert(9, 0);
             }

             if (gamepad1.left_stick_y > 0.15) {
                 robot.driveInvert(10 - invert, 1 * invert * gamepad1.left_stick_y); //See comment starting at line 116
             }
             else if (gamepad1.left_stick_y < -0.15) {
                 robot.driveInvert(10 - invert, (-1) * invert * Math.abs(gamepad1.left_stick_y)); //See comment starting at line 116
             }
             else {
                 robot.driveInvert(11, 0);
             }

             if (gamepad1.left_trigger > 0.5) {
                 robot.driveInvert(4 - invert,1 * invert); //See comment starting at line 116
                 strafingLeft = true; //Tell the program we're strafing, so we won't interfere with it with joystick control
             }

             if (gamepad1.right_trigger > 0.5) {
                 robot.driveInvert(4 + invert,1 * invert); //See comment starting at line 116
                 strafingRight = true;
             }
         }
         else {
             if (gamepad1.left_trigger == 0 && strafingLeft) {
                 robot.stopMovement();
                 strafingLeft = false; //If we are no longer pressing the left trigger, stop strafing
             }
             else if (gamepad1.right_trigger == 0 && strafingRight) {
                 robot.stopMovement();
                 strafingRight = false;
             }
         }

         /* Shooter and Elevator commands

         */
         if (gamepad2.right_trigger > 0.5)       {robot.setShooter(1);}
         else                                    {robot.setShooter(0);}

         if (gamepad2.left_trigger > 0.5)        {robot.setElevator(1);}
         else if (gamepad2.left_bumper)          {robot.setElevator(-1);}
         else                                    {robot.setElevator(0);}

         /* Servo Control
            We represent the servo as exsisting in 3 states - STOP, IN, OUT - each corrseponding to what it should be doing at the time
            This structure is needed due to the design of the TELEOP, in which in continually loops this method.
            If we moved the servo directly within this, the bot may get caught by the watchdog process and get killed
            Also, it would rpevent other actions from beign performed simultaneously
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

         /* Handing off the actual servo control to the bot class

          */
         switch (lservo) {
             case OUT:
                 robot.leftServoOut();
                 break;
             case IN:
                 robot.leftServoIn();
                 break;
             case STOP:
                 robot.leftServoStop();
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
                 robot.rightServoOut();
                 break;
             case IN:
                 robot.rightServoIn();
                 break;
             case STOP:
                 robot.rightServoStop();
                 break;
         }

         /* Various telemetry
            Show
          */
         telemetry.addData("invert", invert);

         telemetry.update();
     }

     @Override
     public void stop() {}
 }