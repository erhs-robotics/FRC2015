package org.usfirst.frc.team53.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardX extends SmartDashboard {	
	public static void putData(String key, Runnable run) {
		SmartDashboardX.putData(key, Utils.runnable2Command(run));
	}
}
