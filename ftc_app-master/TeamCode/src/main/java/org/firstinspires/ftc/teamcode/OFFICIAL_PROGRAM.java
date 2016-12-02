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
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TELEBOP", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class OFFICIAL_PROGRAM extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor elevator = null;
    private DcMotor shooter = null;

    private Servo poker = null;
    private Servo pokerWheel = null;
    private int countLoop = 0;

    //Max: .69
    //Min: .18
    //Start: .48
    final double maxPos = 0.69;
    final double minPos = 0.18;
    final double startPos = .48;
    double curPos = startPos;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftMotor  = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        elevator = hardwareMap.dcMotor.get("elevator");
        shooter = hardwareMap.dcMotor.get("shooter");

        poker = hardwareMap.servo.get("poker");
        pokerWheel = hardwareMap.servo.get("wheelPoker");


        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);
        poker.setPosition(curPos);
        pokerWheel.setPosition(.5f); //Might need to be changed. Depends on whether the the position is fully out or not
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
        countLoop++;
        telemetry.addData("Status", "Running: " + runtime.toString());
        double driveLeft;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = gamepad2.left_trigger;
        double runShooter = gamepad2.right_trigger;

        if (gamepad1.dpad_up)
        {
            driveLeft = -1;
        }
        else if (gamepad1.dpad_down)
        {
            driveLeft = 1;
        }
        else
        {
            driveLeft = 0;
        }

        //If the right bumper is pressed, reverse the directions
        if (gamepad1.right_bumper)
        {
            driveLeft = 0 - driveLeft;
            driveRight = 0 - driveRight;
        }
        //If the left trigger is pressed, reverse elevator
        if (gamepad2.left_bumper)
        {
            runElevator = -1;
        }
        if (gamepad2.right_bumper) {
            runShooter = -.5;
        }
        if (gamepad1.a || gamepad2.a)
        {
            pokerWheel.setPosition(.5f);
        }
        if (gamepad1.x || gamepad2.x)
        {
            pokerWheel.setPosition(.45f);
        }
        if (gamepad1.y || gamepad2.y)
        {
            pokerWheel.setPosition(.55f);
        }
        if (gamepad1.left_bumper)
        {
            curPos = startPos;

        }

        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        leftMotor.setPower(driveLeft);
        rightMotor.setPower(driveRight);
        elevator.setPower(helperFunction.triggerToFlat(runElevator));
        shooter.setPower(helperFunction.triggerToFlat(runShooter));
        poker.setPosition(curPos);
        telemetry.addData("loop number", countLoop);
        telemetry.addData("driveLeft", driveLeft);
        telemetry.addData("driveRight", driveRight);
        telemetry.addData("elevator", runElevator);
        telemetry.addData("shooter", runShooter);
        telemetry.addData("poker", curPos);
    }

    @Override
    public void stop() {}


}