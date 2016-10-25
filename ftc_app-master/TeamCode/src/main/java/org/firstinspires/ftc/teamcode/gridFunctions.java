package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.ArrayList;
import org.firstinspires.ftc.teamcode.Tuple;

/**
 * Created by Plotnw on 10/19/2016.
 */

public class gridFunctions {
    public static ArrayList<Tuple> moveByVector (double xComp, double yComp) {
        //We assume the robot is looking on vector i, we move on vector xComp i + yComp j
        ArrayList<Tuple> listOfInstructions = new ArrayList<>(); //What will be returned

        double dist = Math.sqrt((Math.pow(xComp, 2.0) + Math.pow(yComp, 2.0))); //Magnitude
        double angle = Math.atan(yComp / xComp); // Angle to turn

        double turnRevs = helperFunction.distanceToRevs(Math.PI * angle / 12); //Revolutions needed to turn the angle
        double moveRevs = helperFunction.distanceToRevs(dist); //Revolutions needed to move;

        Tuple turning = new Tuple(turnRevs, (0 - turnRevs));//Creating Tuples for instructions
        Tuple movement = new Tuple(moveRevs, moveRevs);

        listOfInstructions.add(turning);//Adding instructions
        listOfInstructions.add(movement);

        return listOfInstructions;//Returning instructions
    }
    public static void execute (DcMotor right, DcMotor left, ArrayList<Tuple> instructions) {
        //Execute an arraylist of instructions.
        int instrCount = instructions.size();
        for (int i = 0; i < instrCount; i++) {
            Tuple intstr = instructions.get(i);
            int rR = (Integer) intstr.getRight();
            int rL = (Integer) intstr.getLeft();
        //TODO FINISH THIS 
        }
    }
}
