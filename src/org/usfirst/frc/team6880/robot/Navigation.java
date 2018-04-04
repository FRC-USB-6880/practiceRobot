
package org.usfirst.frc.team6880.robot;

public class Navigation {

	Robot robot;
	double kp;
	public Navigation(Robot robot, double kp)
	{
		this.robot = robot;
		this.kp = kp;
	}
	
	public void keepDistFromWall(double targetValue, double speed, boolean isLeft)
	{
		double error = 0;
		double correction = 0;
		double leftSpeed = speed;
		double rightSpeed = speed;
		if(isLeft)
		{
			error = robot.usLeft.getDistanceInches() - targetValue;
			correction = kp*error/2;
			leftSpeed -= correction;
			rightSpeed += correction;
		}
		else
		{
			error = robot.usRight.getDistanceInches() - targetValue;
			correction = kp*error/2;
			leftSpeed += correction;
			rightSpeed -= correction;
		}
		leftSpeed = Math.max(leftSpeed, 0.3);
		rightSpeed = Math.max(rightSpeed, 0.3);
		robot.driveSys.tankDrive(leftSpeed, rightSpeed);
	}

}
