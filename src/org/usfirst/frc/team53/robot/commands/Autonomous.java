package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.util.CommandGroupX;
import org.usfirst.frc.team53.robot.util.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


public class Autonomous extends CommandGroupX {
	private static final double BUMP_DELTA = 2.2;
	private static final double NO_BUMP_DELTA = 2;
	public Autonomous() {
		/*
		addSequential(Robot.claw::close);
		addSequential(Utils.delay(1.5));
		addSequential(() -> Robot.elevator.setLevel(2));
		addSequential(Utils.delay(1.5));
		*/	
		
		//addSequential(() -> Robot.driveTrain.mRobotDrive.mecanumDrive_Polar(0.8, 0, 0));
		addSequential(new Command() {
			double mStartTime;
			double mDelta;
			@Override
			protected boolean isFinished() { return (Timer.getFPGATimestamp() - mStartTime) >= mDelta; }			
			@Override
			protected void interrupted() { }			
			@Override
			protected void initialize() {				
				mDelta = Robot.autoSelector.get() ? NO_BUMP_DELTA : BUMP_DELTA;
				System.out.println("Delta: " + mDelta);
				mStartTime = Timer.getFPGATimestamp();
			}
			@Override
			protected void execute() { Robot.driveTrain.mecanumDrivePID(0.8, 0);}			
			@Override
			protected void end() { Robot.driveTrain.mRobotDrive.mecanumDrive_Polar(0, 0, 0);}
		});
		
	}
}
