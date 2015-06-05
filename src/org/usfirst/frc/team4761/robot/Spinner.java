package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Victor;

public class Spinner {
	private Victor spinner1 = new Victor(4); //TODO: Change this if needed when the real robot is ready
	private Victor spinner2 = new Victor(5);
	public void spin(double speed) {
		if(speed < 0 || speed > 1) {
			throw new IllegalArgumentException("Invalid speed for spinner (must be 0..1)");
		}
		spinner1.set(speed);
		spinner2.set(speed);
	}
	public void stop() {
		spinner1.set(0);
		spinner2.set(0);
	}
	public void emergencyStop() {
		spinner1.stopMotor();
		spinner2.stopMotor();
	}
}
