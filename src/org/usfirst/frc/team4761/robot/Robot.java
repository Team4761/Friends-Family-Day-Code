
package org.usfirst.frc.team4761.robot;


import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick stick;
    ButtonManager buttonManager;
    Flipper flipper;
    Spinner spinner;
    Rope rope;

    public Robot() {
        myRobot = new RobotDrive(0, 1, 2, 3);
        myRobot.setExpiration(0.1);
        stick = new Joystick(0);
        
        flipper = new Flipper(9, 250);
        spinner = new Spinner(8, 6);
        rope = new Rope(5);
        bindButtons();
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	myRobot.mecanumDrive_Cartesian(stick.getRawAxis(0), stick.getRawAxis(1), stick.getRawAxis(4), 0);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
    
    public void bindButtons() {
    	buttonManager = new ButtonManager();
    	try {
    		Method flipperMethod = flipper.getClass().getMethod("slap");
    		Method spinnerMethod = spinner.getClass().getMethod("toggleSpinning");	// replace "XXXX" with the name of the method to call to toggle the motors (must accept 0 parameters)
    		buttonManager.runOnPress(1, ButtonManager.CONTROLLER, flipper, flipperMethod);	// replace null with the Flipper object!
    		buttonManager.setToggle(2, ButtonManager.CONTROLLER, spinner, spinnerMethod);		// replace null with the Spinner object!
    		} catch (NoSuchMethodException e) {
    			System.out.println("Error while attempting to bind buttons.");
    			e.printStackTrace();
    		}
    }
}
