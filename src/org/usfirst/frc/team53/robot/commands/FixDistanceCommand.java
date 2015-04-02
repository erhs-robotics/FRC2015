package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.util.MovingAverage;

import edu.wpi.first.wpilibj.command.Command;

public class FixDistanceCommand extends Command {
	private MovingAverage filter;	
	private static final double thresh = 0.01;

	@Override
	protected void initialize() {
		requires(Robot.driveTrain);
		filter = new MovingAverage(20);
		Robot.driveTrain.disableManualControl();		
	}

	@Override
	protected void execute() {
		Robot.driveTrain.runDistanceController();		
	}

	@Override
	protected boolean isFinished() {
		double error = Robot.driveTrain.distanceController.getLastError();
		return Math.abs(filter.filter(error)) <= thresh;
	}

	@Override
	protected void end() {
		Robot.driveTrain.enableManualControl();
		
	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.enableManualControl();		
	}

}
