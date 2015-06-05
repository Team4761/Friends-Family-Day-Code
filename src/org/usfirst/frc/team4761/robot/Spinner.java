package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.Talon;

public class Spinner {
	private Talon spinner = new Talon(4); //TODO: Change this if needed when the real robot is ready
	public void spin(double speed) {
		if(speed < 0 || speed > 1) {
			throw new IllegalArgumentException("Invalid speed for spinner (must be 0..1)");
		}
		spinner.set(speed);
	}
	public void stop() {
		spinner.stopMotor();
	}
}
