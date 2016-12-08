
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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
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

    private CRServo poker = null;
    private Servo pokerWheel = null;
    private int countLoop = 0;
    private boolean should = false;
    private long timer = 0;
    private long startime = 0;
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

        poker = hardwareMap.crservo.get("poker");
        pokerWheel = hardwareMap.servo.get("pokerWheel");

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);

        pokerWheel.setPosition(0);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        runtime.reset();
        pokerWheel.setPosition(.5);
    }

    @Override
    public void loop() {
        //make counter to determine th
        if (should) {
            countLoop++;
        }
        telemetry.addData("Status", "Running: " + runtime.toString());
        double driveLeft;
        double driveRight = gamepad1.right_stick_y;
        double runElevator = helperFunction.triggerToFlat(gamepad2.left_trigger);
        double runShooter = helperFunction.triggerToFlat(gamepad2.right_trigger);

        if (gamepad1.dpad_up) {
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
        if (gamepad1.b) {
            should = !should;
        }
        if (gamepad2.b) {
            timer = System.currentTimeMillis() - startime;
        }
        if (gamepad1.x) {
            poker.setPower(.25);
            should = true;

        }
        if (gamepad1.y) {
            poker.setPower(-.25);
            should = true;
            startime = System.currentTimeMillis();

        }
        if (gamepad1.a) {
            poker.setPower(0);
        }
        if (gamepad2.left_bumper)
        {
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


        telemetry.addData("loop number", countLoop);
        telemetry.addData("timer", timer);
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
