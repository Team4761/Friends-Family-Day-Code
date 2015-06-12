package org.usfirst.frc.team4761.robot;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows for easy mapping of Joystick buttons to the execution of commands.
 * Useful Methods: setToggle, runOnPress, runWhilePressed, runOnceOnHold
 * 
 * Usage:
 * public static void BindButtons() {
 * 	ButtonManager manager = new ButtonManager();
 * 
 * 	Method handler = getClass().getMethod("handlerMethod");
 * 	manager.runOnPress(1, ButtonManager.CONTROLLER, handler, null)
 * }
 * public static void handlerMethod() {
 * 	// handle button code in here!
 * 
 * }
 * 
 * OR, from a non-static context, replace the 4th argument of the method (null in the example above) with the current Object.
 */

public class ButtonManager {
	
	private static boolean inited = false, buttonDown;
	public static final int CONTROLLER = 0, BUTTONBOARD_ONE = 1, BUTTONBOARD_TWO = 2;
	
	private static CopyOnWriteArrayList<ButtonCommand> list = new CopyOnWriteArrayList<ButtonCommand>();
	
	private static JoystickButton[][] buttons = new JoystickButton[3][20];
	private static Joystick[] joysticks = {new Joystick(CONTROLLER), new Joystick(BUTTONBOARD_ONE), new Joystick(BUTTONBOARD_TWO)};
	
	public ButtonManager() {}
	
	private void init() {
		try {
			for (int x = 0; x < buttons.length; x++) {
				for (int i = 0; i < 3; i++) {
					buttons[i][x] = new JoystickButton(joysticks[i], x);
				}
			}
			
			new Thread(new ButtonManagerHandler()).start();
			inited = true;
		} catch (Exception e) {
			System.out.println("Error in ButtonManager init!");
			e.printStackTrace();
		}
	}
	
	public void setToggle(int button, int joystick, Object object, Method method) {
		checkInit();
		new ButtonCommand(button, joystick, object, method, ButtonCommand.TYPE_TOGGLEABLE);
	}
	
	public void runOnPress (int button, int joystick, Object object, Method method) {
		checkInit();
		new ButtonCommand(button, joystick, object, method, ButtonCommand.TYPE_ROP);
	}
	
	public void runWhilePressed (int button, int joystick, Object object, Method method) {
		checkInit();
		new ButtonCommand(button, joystick, object, method, ButtonCommand.TYPE_RWP);
	}
	
	public void runOnceOnHold (int button, int joystick, Object object, Method method) {
		checkInit();
		new ButtonCommand(button, joystick, object, method, ButtonCommand.TYPE_CROP);
	}
	
	private void checkInit() {
		try {
			if (!inited) {
				init();
			}
		} catch (Error e) {
			System.out.println(e);
		}
	}
	
	private class ButtonManagerHandler implements Runnable {	// Handles the main ButtonManager thread.
		public void run() {
			try {
				while (true) {
					for (ButtonCommand command : list) {
						buttonDown = command.get();
						if (command.type == ButtonCommand.TYPE_TOGGLEABLE) {
							if (command.pressed()) {
								command.store = !command.store;
								if (command.store) {
									command.start();
								} else {
									command.stop();
								}
							}
						} else if (command.type == ButtonCommand.TYPE_ROP) {
							if (command.pressed()) {
								command.start();
							}
						} else if (command.type == ButtonCommand.TYPE_RWP) {
							if (buttonDown) {
								command.start();
							}
						} else if (command.type == ButtonCommand.TYPE_CROP) {
							if (buttonDown && !command.store) {
								command.start();
								command.store = true;
							} else if (!buttonDown) {
								command.stop();
								command.store = false;
							}
						}
						command.last = buttonDown;
					}
					Thread.sleep(20);
				}
			} catch (Exception e) {
				System.out.println("Error in ButtonManagerHandler thread!");
				e.printStackTrace();
			}
		}
	}
	
	private class ButtonCommand {	// Storage class for each bound button's information.
		private int button;
		private Joystick stick;
		private int type;
		private boolean store = false, last = false;
		private static final int TYPE_TOGGLEABLE = 0, TYPE_ROP = 1, TYPE_RWP = 2, TYPE_CROP = 3;
		private ButtonThread methodThread;
		private Thread runningThread;
		private ButtonCommand(int button, int joystick, Object object, Method method, int type) {
			try {
				this.button = button;
				this.type = type;
				stick = ButtonManager.joysticks[joystick];
				ButtonManager.list.add(this);
				methodThread = new ButtonThread(method, object);
				runningThread = new Thread(methodThread);
			} catch (Error e) {
				System.out.println("Error creating a ButtonCommand!");
				e.printStackTrace();
			}
		}
		public boolean get() {
				return stick.getRawButton(button);
		}
		public void start() {
			runningThread.start();
		}
		@SuppressWarnings("deprecation")
		public void stop() {
			runningThread.stop();
		}
		public boolean pressed() {
			return buttonDown && !last;
		}
	}
	private class ButtonThread implements Runnable {	// Handles a Thread that acts as a Command would in a Command-based program.
		private Object obj;
		private Method handlerMethod;
		private ButtonThread(Method m, Object o) {
			obj = o;
			handlerMethod = m;
		}
		@SuppressWarnings("deprecation")
		public void run() {
			try {
				handlerMethod.invoke(obj, new Object[0]);
			} catch (Exception e) {
				System.out.println("[ButtonManager] Error while calling method '" + handlerMethod.getName() + "' on an object of type '" + obj.getClass().getSimpleName() + "'");
				e.printStackTrace();
				Thread.currentThread().stop();	// In case of error, forcibly stop the running thread.
			}
		}
	}
}
