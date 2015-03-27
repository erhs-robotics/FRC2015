package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.command.Command;
public class Utils {
	public static Command runnable2Command(Runnable run) {
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
	
	public static Command r2c(Runnable run) {
		return runnable2Command(run);
	}
	
}
