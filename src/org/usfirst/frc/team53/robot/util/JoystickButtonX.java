package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class JoystickButtonX extends JoystickButton {

	public JoystickButtonX(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);		
	}
	
	public void whenPressed(Runnable run) {
		this.whenPressed(Utils.runnable2Command(run));
	}
	
	public void whenActive(Runnable run) {
		this.whenActive(Utils.runnable2Command(run));
	}
	
	public void whenInactive(Runnable run) {
		this.whenInactive(Utils.runnable2Command(run));
	}
	
	public void whenReleased(Runnable run) {
		this.whenReleased(Utils.runnable2Command(run));
	}
	
	public void whileActive(Runnable run) {
		this.whileActive(Utils.runnable2Command(run));
	}
	
	public void whileHeld(Runnable run) {
		this.whileHeld(Utils.runnable2Command(run));
	}
}
