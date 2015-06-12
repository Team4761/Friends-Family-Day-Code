package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Talon;

public class Flipper {
	Talon motor;
	int waitTime;
	
	public Flipper(int port, int waitTime){
		motor = new Talon(port); // We don't know the motor type at this moment. TODO: Fix motor type.
	}
	
	public void slap() throws InterruptedException{
		motor.set(1); // TODO: Find out if there are switches to use to prevent stalling motors.
		Thread.sleep(waitTime); // TODO: Find better time to wait for!
		motor.set(-1);
	}
}
