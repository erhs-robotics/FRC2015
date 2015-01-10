package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardX extends SmartDashboard {
	
	public static void putData(String key, Runnable run) {
		SmartDashboardX.putData(key, new Command() {
			
			@Override
			protected boolean isFinished() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			protected void interrupted() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void initialize() {
				run.run();				
			}
			
			@Override
			protected void execute() {
				// TODO Auto-generated method stub				
			}
			
			@Override
			protected void end() {
				// TODO Auto-generated method stub				
			}
		});
	}
}
