package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickButtonX extends JoystickButton {

	public JoystickButtonX(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);		
	}
	
	private Command Runnable2Command(Runnable run) {
		return new Command() {			
			@Override
			protected boolean isFinished() { return true; }			
			@Override
			protected void interrupted() { }			
			@Override
			protected void initialize() { run.run(); }			
			@Override
			protected void execute() { }			
			@Override
			protected void end() { }
		};
	}
	
	public void whenPressed(Runnable run) {
		this.whenPressed(Runnable2Command(run));
	}
	
	public void whenActive(Runnable run) {
		this.whenActive(Runnable2Command(run));
	}
	
	public void whenInactive(Runnable run) {
		this.whenInactive(Runnable2Command(run));
	}
	
	public void whenReleased(Runnable run) {
		this.whenReleased(Runnable2Command(run));
	}
	
	public void whileActive(Runnable run) {
		this.whileActive(Runnable2Command(run));
	}
	
	public void whileHeld(Runnable run) {
		this.whileHeld(Runnable2Command(run));
	}

}
