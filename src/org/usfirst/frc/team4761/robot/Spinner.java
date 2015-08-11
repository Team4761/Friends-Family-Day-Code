package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class Spinner {
	private Talon spinner1;
	private Talon spinner2;
	
	private boolean spinning = false;
	private double defaultSpeed = 1;
	private double secondarySpeed = 0.8;
	private double currentSpeed = defaultSpeed;
	
	public Spinner(int port1, int port2) {
		spinner1 = new Talon(port1);
		spinner2 = new Talon(port2);
	}
	
	public void spin(double speed) {
		if(speed < 0 || speed > 1) {
			throw new IllegalArgumentException("Invalid speed for spinner (must be 0..1)");
		}
		if(RobotMap.safetySensor.getDistance() > RobotMap.safetyAcceptableDistance) {
			spinner1.set(speed);
			spinner2.set(speed);
			spinning = true;
		}
	}
	
	public void stop() {
		spinner1.set(0);
		spinner2.set(0);
		spinning = false;
	}
	
	public void toggleSpinning() {
		if (spinning) {
			stop();
		} else {
			spin(currentSpeed);
		}
	}
	
	public void toggleSpeed() {
		if (currentSpeed == defaultSpeed) {
			currentSpeed = secondarySpeed;
		}
		else {
			currentSpeed = defaultSpeed;
		}
		if (spinning) {
			spinner1.set(currentSpeed);
			spinner2.set(currentSpeed);
		}
	}
	
	public void emergencyStop() {
		spinner1.stopMotor();
		spinner2.stopMotor();
		spinning = false;
	}
}
