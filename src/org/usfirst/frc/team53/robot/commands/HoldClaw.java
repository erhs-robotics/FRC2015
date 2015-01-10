package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.OI;
import org.usfirst.frc.team53.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class HoldClaw extends Command {

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.claw.open();
		Timer.delay(0.05);
		Robot.claw.close();
		Timer.delay(0.05);		
	}

	@Override
	protected boolean isFinished() {
		if(!OI.stick.getRawButton(4))
			return true;
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
