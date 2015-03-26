package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetElevator extends Command {
	
	public ResetElevator() {
		super();
		requires(Robot.elevator);
	}

	@Override
	protected void initialize() {
		Robot.elevator.disable();
		Robot.elevator.setSpeed(-.2);
	}

	@Override
	protected void execute() {
				
	}

	@Override
	protected boolean isFinished() {		
		return Robot.elevator.isAtBottom();
	}

	@Override
	protected void end() {
		Robot.elevator.resetEncoder();
		Robot.elevator.setSetpoint(0);
		Robot.elevator.enable();		
	}

	@Override
	protected void interrupted() {		
		
	}

}
