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

import java.util.ArrayList;

@TeleOp(name="Testing_TimeTest", group="TESTING")
//@Disabled

public class Testing_TimeTest extends OpMode {

    Bot robot = new Bot();

    //adds a timeout to the invert button, to prevent rapid switching of invert and back
    private long timer = 1;

    private ArrayList<Long> timings = new ArrayList<Long>();

    private boolean timeThing = false;

    private long average;


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        robot.init(hardwareMap);
    }


    @Override
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {

        if (gamepad1.a && !timeThing) {
            timeThing = !timeThing;
            timer = System.currentTimeMillis();
        } else if (gamepad1.a && timeThing) {
            ;
        } else if (!gamepad1.a && timeThing) {
            timings.add(System.currentTimeMillis() - timer);

        } else {
            ;
        }

        long count = 0;
        for (int i = 0; i < timings.size(); i++) {
            count = count + timings.get(i);
        }
        average = count / timings.size();

         /* Various telemetry
            Show
          */
        telemetry.addData("Timings", timings.toString());
        telemetry.addData("Average", average);
        telemetry.update();
    }

    @Override
    public void stop() {}
}