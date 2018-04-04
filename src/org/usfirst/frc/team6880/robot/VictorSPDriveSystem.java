package org.usfirst.frc.team6880.robot;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class VictorSPDriveSystem {
	Robot robot;
	VictorSP motorL1;
	VictorSP motorL2;
	SpeedControllerGroup motorLeft;
	VictorSP motorR1;
	VictorSP motorR2;
	SpeedControllerGroup motorRight;
	DifferentialDrive drive;
	Encoder leftEnc;
	Encoder rightEnc;
	
	private double wheelDiameter;
	private double wheelCircumference;
	private double distancePerCount;
	private double mult;
	
	
	public VictorSPDriveSystem(Robot robot)
	{
		this.robot = robot;
		
		wheelDiameter = 6.0;
		wheelCircumference = Math.PI * wheelDiameter;
		// We will assume that the same encoder is used on both left and right sides of the drive train. 
		distancePerCount = wheelCircumference / 1440;
		
		// TODO  Use configReader.getChannelNum() method to identify the
		//   channel numbers where each motor controller is plugged in
		motorL1 = new VictorSP(0);
		motorL2 = new VictorSP(1);
		motorLeft = new SpeedControllerGroup(motorL1, motorL2);
		motorR1 = new VictorSP(2);
		motorR2 = new VictorSP(3);
		motorRight = new SpeedControllerGroup(motorR1, motorR2);
		drive = new DifferentialDrive(motorLeft, motorRight);
		int[] encoderChannelsLeft = {0,1};
		leftEnc = new Encoder(encoderChannelsLeft[0], encoderChannelsLeft[1], false, Encoder.EncodingType.k4X);
		leftEnc.setDistancePerPulse(distancePerCount);
        int[] encoderChannelsRight = {2,3};
		rightEnc = new Encoder(encoderChannelsRight[0], encoderChannelsRight[1], true, Encoder.EncodingType.k4X);
		rightEnc.setDistancePerPulse(distancePerCount);
		
		mult = 1.0;
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(mult*leftSpeed, mult*rightSpeed);
	}
	
	public void arcadeDrive(double speed, double rotationRate)
	{
		drive.arcadeDrive(mult*speed, mult*rotationRate);
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getEncoderDist()
	{
		return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
	}
	public void setLoSpd()
	{
	}
	public void setHiSpd()
	{
	}
	public boolean isMoving()
	{
		if(drive.isAlive())
    		return true;
    	return false;
	}

	public void changeMultiplier(double multr) {
		// TODO Auto-generated method stub
		this.mult = mult;
	}
}