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


@TeleOp(name="Telebop", group="MAIN")  // @Autonomous(...) is the other common choice
//@Disabled
//TODO: ADD REVERSE DRIVING SWITCH, 2 MORE MOTORS WHICH MUST BE SYNCED, 3 MORE SERVOS
public class Teleop_TELEBOP extends OpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timer = new ElapsedTime();
    Bot robot = new Bot();


    boolean strafingLeft = false;
    boolean strafingRight = false;

    int lServoPush = 0;
    int rServoPush = 0;
    public enum ServoStates {STOP, IN, OUT};
    private ServoStates rservo = ServoStates.STOP;
    private long rtime;
    private boolean rservoinorout = false;
    private boolean rservoactive = false;
    private boolean lservoinorout = false;
    private double rsevoStop = .5;
    private double lsevoStop = .5;
    //private int rcount = 0;
    //private int lcount = 0;
    private int invert = 1;
    private long invertLen = 1;
    private ServoStates lservo = ServoStates.STOP;
    private long ltime;
    private boolean lservoactive = false;
    private boolean hittingBeacon = false;
    /*to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        // Old Driving commands
        /*if (gamepad1.left_stick_y < -0.5)       { robot.stopMovement(0,1); }
        else if (gamepad1.left_stick_y > 0.5)   { robot.stopMovement(1,1); }
        else if (gamepad1.left_stick_x > 0.5)   { robot.stopMovement(2,1); }
        else if (gamepad1.left_stick_x < -0.5)  { robot.stopMovement(3,1); }
        else if (gamepad1.right_stick_x > 0.5)  { robot.stopMovement(4,1); }
        else if (gamepad1.right_stick_x < -0.5) { robot.stopMovement(5,1); }
        else { robot.stopMovement(); }
        */
        // Driving commands (tank controls + strafe)
        /*if (gamepad1.left_bumper && (System.currentTimeMillis() - invertLen  > 500)) {
            invert = invert * (-1);
            invertLen = System.currentTimeMillis();
        } else {
            ;
        }*/

        if (!robot.getIsStrafing())
        {
            if (gamepad1.right_stick_y > 0.5)
            {
                robot.drive(7, 1);
            }
            else if (gamepad1.right_stick_y < -0.5)
            {
                robot.drive(7, -1);
            }
            else
            {
                robot.drive(7, 0);
            }

            if (gamepad1.left_stick_y > 0.5)
            {
                robot.drive(6, 1);
            }
            else if (gamepad1.left_stick_y < -0.5)
            {
                robot.drive(6, -1);
            }
            else
            {
                robot.drive(6, 0);
            }

            if (gamepad1.left_trigger > 0.5)
            {
                robot.drive(2,1);
                strafingLeft = true;
            }

            else if (gamepad1.right_trigger > 0.5)
            {
                robot.drive(3,1);
                strafingRight = true;
            }
        }
        else
        {
            if (gamepad1.left_trigger == 0 && strafingLeft)
            {
                robot.stopMovement();
                strafingLeft = false;
            }
            else if (gamepad1.right_trigger == 0 && strafingRight)
            {
                robot.stopMovement();
                strafingRight = false;
            }
        }

        // Shooting and elevating commands
        if (gamepad2.right_trigger > 0.5)       {robot.setShooter(1);}
        else                                    {robot.setShooter(0);}

        if (gamepad2.left_trigger > 0.5)        {robot.setElevator(1); robot.intakeServoIn();}
        else if (gamepad2.left_bumper)          {robot.setElevator(-1); robot.intakeServoOut();}
        else                                    {robot.setElevator(0); robot.intakeServoStop();}

        // Left servo commands
        //b out a in right
        //x out y in left
        //left servo stuff

        if (gamepad2.x) {
            lservo = ServoStates.OUT;
            ltime = System.currentTimeMillis();
            lservoactive = true;
            //lcount++;
        } else if (gamepad2.y) {
            lservo = ServoStates.IN;
            ltime = System.currentTimeMillis();
            lservoactive = true;
            //lcount--;
        } else {

        }
        if (lservoactive) {
            lservoactive = true;
            switch (lservo) {
                case STOP:
                    //ltime = System.currentTimeMillis();
                    //lservo = ServoStates.OUT;
                    break;
                case OUT:
                    if (System.currentTimeMillis() - ltime < 300) {
                        ;
                    } else {
                        lservo = ServoStates.STOP;
                        lservoactive = false;
                        //lcount++;
                    }
                    break;
                case IN:
                    if (System.currentTimeMillis() - ltime < 300) {
                        ;
                    } else {
                        lservo = ServoStates.STOP;
                        lservoactive = false;
                        //lcount--;
                    }
                    break;
            }
        }
        switch (lservo) {
            case OUT:
                robot.leftServoOut();
                break;
            case IN:
                robot.leftServoIn();
                break;
            case STOP:
                //r4obot.leftServoReset();
                robot.leftServoStop();
                break;
        }
        //right servo
        if (gamepad2.b) {
            rservo = ServoStates.OUT;
            rtime = System.currentTimeMillis();
            rservoactive = true;
        } else if (gamepad2.a) {
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
                    //ltime = System.currentTimeMillis();
                    //lservo = ServoStates.OUT;
                    break;
                case OUT:
                    if (System.currentTimeMillis() - rtime < 300) {
                        ;
                    } else {
                        rservo = ServoStates.STOP;
                        rservoactive = false;
                        //rcount++;
                    }
                    break;
                case IN:
                    if (System.currentTimeMillis() - rtime < 300) {
                        ;
                    } else {
                        rservo = ServoStates.STOP;
                        rservoactive = false;
                        //rcount--;
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

        // lift methods
        /*
        if (gamepad1.a)
        {
            robot.lowerCap();
        }
        else if (gamepad1.b)
        {
            robot.liftCap();
        }
        else
        {
            robot.stopLiftCap();
        }
        */

        // Automated Beacon Lineup/Hit
        if (gamepad1.b)
        {
            if (!hittingBeacon)
            {
                hittingBeacon = true;
            }

            if (robot.getLineLight() > 16)
            {
                robot.stopMovement();
                if (hittingBeacon)
                {
                    timer.reset();
                    hittingBeacon = false;
                }
                else if (timer.milliseconds() < 1000)
                {
                    robot.leftServoOut();
                }
                else
                {
                    robot.leftServoIn();
                }

            }
            else
            {
                robot.drive(0, .1);
            }
        }

        telemetry.addData("intake color", robot.getIntake());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {}
}