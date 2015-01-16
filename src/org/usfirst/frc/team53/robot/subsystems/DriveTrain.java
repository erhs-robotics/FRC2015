package org.usfirst.frc.team53.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveTrain extends Subsystem {
	
	Talon talon;
	
	public DriveTrain() {
		talon = new Talon(0);
		LiveWindow.addActuator("DriveTrain", "Motor", talon);
	}

	@Override
	protected void initDefaultCommand() {		
		
	}

}
