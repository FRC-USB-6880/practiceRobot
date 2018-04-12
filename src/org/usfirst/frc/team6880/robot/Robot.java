/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6880.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
//	AnalogInput ultrasonic1;
	MaxBotixRangeFinder usLeft, usRight;
	
	Preferences prefs;
	double usKp, targetDist, basePower;
	
	public VictorSPDriveSystem driveSys;
	public Navigation navigateWall;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	    prefs = Preferences.getInstance();
	    int leftPort = prefs.getInt("LeftPort", 0);
	    int rightPort = prefs.getInt("RightPort", 1);
//		ultrasonic1 = new AnalogInput(0);
	    System.out.println("Left Port = " + leftPort + 
	            "RightPort = " + rightPort);
		usLeft = new MaxBotixRangeFinder(this, "MB1200", leftPort);
		usRight = new MaxBotixRangeFinder(this, "MB1013", rightPort);
		
		usKp = 0.25;
		targetDist = prefs.getDouble("targetDist", 20.0);
		basePower = prefs.getDouble("basePower", 0.4);
		SmartDashboard.putNumber("usKp", usKp);
        SmartDashboard.putNumber("targetDist", targetDist);
		SmartDashboard.putNumber("basePower", basePower);
		
		driveSys = new VictorSPDriveSystem(this);
		
		navigateWall = new Navigation(this, usKp);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
        SmartDashboard.putNumber("DistanceLeft", usLeft.getDistanceInches());
        SmartDashboard.putNumber("DistanceRight", usRight.getDistanceInches());
//        navigateWall.keepDistFromWall(targetDist, basePower, true);
//        usLeft.maintainDistance(targetDist, basePower);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
//	    double Vm = ultrasonic1.getAverageVoltage();
//	    double Vcc = 5.0;
//        double VoltsPerCm = Vcc/1024;
//        double distanceInCm = Vm / VoltsPerCm;
	    
//	    System.out.println("DistanceLeft = " + usLeft.getDistanceInches() + 
//	            ", DistanceRight = " + usRight.getDistanceInches());
	    SmartDashboard.putNumber("DistanceLeft", usLeft.getDistanceInches());
	    SmartDashboard.putNumber("DistanceRight", usRight.getDistanceInches());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
