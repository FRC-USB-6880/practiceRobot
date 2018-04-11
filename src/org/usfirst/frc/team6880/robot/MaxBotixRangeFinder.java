/**
 * 
 */
package org.usfirst.frc.team6880.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author FRC6880
 *
 */
public class MaxBotixRangeFinder {
    String maxbotixModel;
    double Vcc = 5.0; // Volts supplied to the range finder
    double VoltsPerCm; // Volts per cm output by the range finder in analog mode
    AnalogInput usSensor;
    Robot robot;
    double previousReading;
    int iteration;

    /**
     * 
     */
    public MaxBotixRangeFinder(Robot robot, String maxbotixModel, int port) {
        // TODO Auto-generated constructor stub
        this.robot = robot;
        this.maxbotixModel = maxbotixModel;
        this.Vcc = 5.0;
        this.usSensor =  new AnalogInput(port);
        previousReading = 0;
        iteration = 1;
        switch (maxbotixModel) {
        case "MB1013": {
            VoltsPerCm = 2 * (Vcc/1024);
            break;
        }
        case "MB1200" : {
            VoltsPerCm = Vcc/1024;
            break;
        }
        default:
            break;
        }
    }
    
    public double getDistanceInches() {
        double inches = 0.393701; // inches per cm
        double Vm = usSensor.getAverageVoltage();
        double distanceInIn = Vm / VoltsPerCm;
        if(iteration != 1) distanceInIn = (0.7*distanceInIn) + (0.3*previousReading);
        previousReading = distanceInIn;
        inches *= distanceInIn;
        iteration++;
        return (inches);
    }
    
    public void maintainDistance(double targetDist, double basePower) {
        double error = targetDist - getDistanceInches();
        double correction = robot.usKp * error;
        double leftPower = basePower - (correction / 2);
        double rightPower = basePower + (correction / 2);
        
        SmartDashboard.putNumber("LeftPower", leftPower);
        SmartDashboard.putNumber("rightPower", rightPower);
        robot.driveSys.tankDrive(leftPower, rightPower);
    }
    
    public double median(double[] last4vals) {
        double medVal;
        int len = last4vals.length;
        Arrays.sort(last4vals);
        if ((len % 2) == 0)
            medVal = ((double)last4vals[len/2] + (double)last4vals[(len/2) - 1]) / 2;
        else
            medVal = (double)last4vals[len/2];

        return (medVal);
    }

}
