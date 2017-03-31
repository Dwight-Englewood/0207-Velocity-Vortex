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
package org.firstinspires.ftc.oldFiles.Pre_Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.oldFiles.Pre_Worlds.*;

import java.util.ArrayList;

@TeleOp(name="Testing_TimeTest", group="TESTING")








































































































































































































































































































@Disabled

public class Testing_TimeTest extends OpMode {

    org.firstinspires.ftc.oldFiles.Pre_Worlds.Bot robot = new org.firstinspires.ftc.oldFiles.Pre_Worlds.Bot();

    //adds a timeout to the invert button, to prevent rapid switching of invert and back
    private ElapsedTime timer = new ElapsedTime();

    private ArrayList<Double> timings = new ArrayList<Double>();

    private boolean timeThing = false;

    private double average;

    double count = 0;


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

        if (gamepad1.a && !timeThing)
        {
            timeThing = true;
            timer.reset();
            count = 0;
            robot.rightServoOut();
            robot.leftServoOut();
        }
        else if (!gamepad1.a && timeThing)
        {
            timings.add(timer.seconds());
            robot.rightServoStop();
            robot.leftServoStop();
            timeThing = false;
            for (int i = 0; i < timings.size(); i++)
            {
                count += timings.get(i);
            }
            if (timings.size() > 0)
                average = count / timings.size();

        }

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