package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Utils {
	public static Command Runnable2Command(Runnable run) {
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
	
	public static Command Runnable2Command(Runnable run, Subsystem ...subsystems) {
		Command command = new Command() {
			{
				for(Subsystem system : subsystems) {
					requires(system);
				}
			}
			
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
		
		return command;
		
	}
}
