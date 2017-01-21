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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Telebop", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class TELEBOP extends OpMode
{
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
    private ServoStates lservo = ServoStates.STOP;
    private long ltime;
    /*to run ONCE when the driver hits INIT
     */
    @Override
    public void init()
    {
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
    public void start()
    {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop()
    {
        telemetry.addData("Status", "Running: " + runtime.toString());

        // Driving commands (left/right stick brian zhang method)
        /*if (gamepad1.left_stick_y < -0.5)       { robot.drive(0,1); }
        else if (gamepad1.left_stick_y > 0.5)   { robot.drive(1,1); }
        else if (gamepad1.left_stick_x > 0.5)   { robot.drive(2,1); }
        else if (gamepad1.left_stick_x < -0.5)  { robot.drive(3,1); }
        else if (gamepad1.right_stick_x > 0.5)  { robot.drive(4,1); }
        else if (gamepad1.right_stick_x < -0.5) { robot.drive(5,1); }
        else { robot.drive(); }
        */
        // Driving commands (tank controls + strafe) BUILT FOR DUMB MODE
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

            if (gamepad1.right_trigger > 0.5)
            {
                robot.drive(3,1);
                strafingRight = true;
            }
        }
        else
        {
            if (gamepad1.left_trigger == 0 && strafingLeft)
            {
                robot.drive();
                strafingLeft = false;
            }
            else if (gamepad1.right_trigger == 0 && strafingRight)
            {
                robot.drive();
                strafingRight = false;
            }
        }

        // Shooting and elevating commands
        if (gamepad2.right_trigger > 0.5)       {robot.setShooter(1);}
        else                                    {robot.setShooter(0);}

        if (gamepad2.left_trigger > 0.5)        {robot.setElevator(1);}
        else if (gamepad2.left_bumper)          {robot.setElevator(-1);}
        else                                    {robot.setElevator(0);}

        // Left servo commands
        //b out a in right
        //x out y in left
        if (!(rservo.equals(ServoStates.OUT) || rservo.equals(ServoStates.IN))) {
            if (gamepad2.b && !lservo.equals(ServoStates.OUT)) {
                robot.leftServoOut();
                lservo = ServoStates.OUT;
                ltime = System.currentTimeMillis();
            } else if (gamepad2.a) {
                robot.leftServoIn();
                lservo = ServoStates.IN;
                ltime = System.currentTimeMillis();
            } else {
                robot.leftServoStop();
                lservo = ServoStates.STOP;
            }
        } else if (System.currentTimeMillis() - ltime >= 2000) {
            robot.leftServoStop();
        } else {
        }


        // Right servo commands
        if (gamepad2.x)
        {
            robot.rightServoOut();
            rservo = ServoStates.OUT;
        }
        else if (gamepad2.y)
        {
            robot.rightServoIn();
            rservo = ServoStates.IN;
        }
        else
        {
            robot.rightServoStop();
            rservo = ServoStates.STOP;
        }


        telemetry.addData("FR MaxPow ", robot.getMaxPowFR());
        telemetry.addData("BR MaxPow ", robot.getMaxPowBR());
        telemetry.addData("FL MaxPow ", robot.getMaxPowFL());
        telemetry.addData("BL MaxPow ", robot.getMaxPowBL());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {}
}