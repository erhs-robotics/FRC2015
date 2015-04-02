package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.util.MovingAverage;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.hal.AnalogJNI;

public class FixAlignCommand extends Command {
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
		Robot.driveTrain.runAlignmentController();		
	}

	@Override
	protected boolean isFinished() {
		double error = Robot.driveTrain.alignmentController.getLastError();
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