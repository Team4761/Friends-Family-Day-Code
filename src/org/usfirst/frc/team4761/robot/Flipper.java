package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;

public class Flipper {
	Victor motor;
	int waitTime;
	
	public Flipper(int port, int waitTime){
		motor = new Victor(port); // We don't know the motor type at this moment. TODO: Fix motor type.
		this.waitTime = waitTime;
	}
	
	public void slap() throws InterruptedException{
		motor.set(.75);
		Thread.sleep(250); // TODO: Find better time to wait for!
		motor.set(-.75);
		Thread.sleep(200);
		motor.set(0);
	}
}
