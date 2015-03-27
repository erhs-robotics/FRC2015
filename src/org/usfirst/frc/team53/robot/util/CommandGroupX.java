package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CommandGroupX extends CommandGroup {	
	public void addSequential(Runnable run) {
		this.addSequential(Utils.r2c(run));
	}
	
	public void addParallel(Runnable run) {
		this.addParallel(Utils.r2c(run));		
	}
	
	public void addSequential(Runnable run, double timeout) {
		this.addSequential(Utils.r2c(run), timeout);
	}
	
	public void addParallel(Runnable run, double timeout) {
		this.addParallel(Utils.r2c(run), timeout);		
	}
}
