package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Talon;

public class Rope {
	Talon motor;
	
	
	public Rope(int port){
		this.motor = new Talon(port);
	}
	public void pull () {
		this.motor.set(-.25);
	}
	public void stop(){
		this.motor.set(0);
	}
	public void release(){
		this.motor.set(.25);
	}
	
}
