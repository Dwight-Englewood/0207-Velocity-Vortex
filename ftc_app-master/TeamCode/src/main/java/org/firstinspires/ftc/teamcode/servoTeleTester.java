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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="servo tele tester", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class servoTeleTester extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private Servo poker = null;
    private double currentPos = 0.48;
    private double maxPos = 0.69;
    private double minPos = 0.18 ;

    @Override
    public void init() {

        poker = hardwareMap.servo.get("poker");
        poker.setPosition(currentPos);
    }

    // a = full left
    // b = full right
    // x = increment left
    //y = increment right
    //3.6cm at mid point
    @Override
    public void init_loop() {}

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        if (gamepad1.a)
        {
            currentPos = maxPos;
        }
        if (gamepad1.b)
        {
            currentPos = minPos;
        }
        if (gamepad1.x)
        {
            currentPos += 0.01;
            if(currentPos >= maxPos)
            {
                currentPos = maxPos;
            }
        }
        if (gamepad1.y) {
            currentPos -= 0.01;
            if (currentPos <= minPos) {
                currentPos = minPos;
            }
        }

        poker.setPosition(currentPos);
        telemetry.addData("gamepad1a", gamepad1.a);
        telemetry.addData("gamepad1b", gamepad1.b);
        telemetry.addData("Current Position", currentPos);
        updateTelemetry(telemetry);
    }


    @Override
    public void stop() {}

}
