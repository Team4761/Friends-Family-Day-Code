package org.usfirst.frc.team4761.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;

/**
 * Something like the RobotMap we all know and love
 */
public class RobotMap {
	public static final int spinner1Port = 4;
	public static final int spinner2Port = 5;
	public static final Spinner spinner = new Spinner(spinner1Port, spinner2Port);
	
	public static final int flipperPort = 6;
	public static final int flipperWaitTime = 500;
	public static final Flipper flipper = new Flipper(flipperPort, flipperWaitTime);
	
	public static final int safetySensorPort = 1;
	public static final int safetyAcceptableDistance = 150;
	public static final DistanceSensor safetySensor = new DistanceSensor(new AnalogInput(safetySensorPort));
}
