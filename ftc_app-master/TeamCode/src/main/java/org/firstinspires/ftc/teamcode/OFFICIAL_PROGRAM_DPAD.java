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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.helperFunction;

@TeleOp(name="OFFICIAL_PROGRAM_DPAD", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class OFFICIAL_PROGRAM_DPAD extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor elevator = null;
    private DcMotor shooter = null;

    //private Servo rightPoker = null;
    //private Servo leftPoker = null;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftMotor  = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        //leftPoker = hardwareMap.servo.get("left poker");
        //rightPoker = hardwareMap.servo.get("right poker");


        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);

        //leftPoker.setDirection(Servo.Direction.FORWARD);
        //rightPoker.setDirection(Servo.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
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
        double driveLeft = gamepad1.left_stick_y;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = gamepad1.left_trigger;
        double runShooter = gamepad1.right_trigger;
        //if (gamepad1.dpad_up) {
          //  driveLeft = 1;
        //} else if (gamepad1.dpad_down) {
          //  driveLeft = -1;
        //} else {
          //  driveLeft = 0;
        //}

        //If the right bumper is pressed, reverse the directions
        if (gamepad1.right_bumper) {
            driveLeft = 0 - driveLeft;
            driveRight = 0 - driveRight;
        }
        //If the left trigger is pressed, reverse elevator
        if (gamepad1.left_bumper) {
            runElevator = 0 - runElevator;
        }

        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        leftMotor.setPower(driveLeft);
        rightMotor.setPower(driveRight);
        elevator.setPower(helperFunction.triggerToFlat(runElevator));
        shooter.setPower(helperFunction.triggerToFlat(runShooter));

        //leftPoker.setPosition(buttonToPower(gamepad1.x));
        //rightPoker.setPosition(buttonToPower(gamepad1.y));
        // to make this work we can scale the range of the servo down from whatever the normal is to 90 degrees
    }

    @Override
    public void stop() {
    }


}