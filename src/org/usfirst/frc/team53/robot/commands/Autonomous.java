package org.usfirst.frc.team53.robot.commands;

import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.util.CommandGroupX;
import org.usfirst.frc.team53.robot.util.Utils;

import edu.wpi.first.wpilibj.Timer;


public class Autonomous extends CommandGroupX {
	public Autonomous() {
		/*
		addSequential(Robot.claw::close);
		addSequential(Utils.delay(1.5));
		addSequential(() -> Robot.elevator.setLevel(2));
		addSequential(Utils.delay(1.5));
		*/
		addSequential(() -> Robot.driveTrain.mRobotDrive.mecanumDrive_Polar(0.8, 0, 0));
		addSequential(Utils.delay(1.9));
		addSequential(() -> Robot.driveTrain.mRobotDrive.arcadeDrive(0, 0));
	}
}
