package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.OI;
import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.RobotMap;
import org.usfirst.frc.team53.robot.util.Utils;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveTrain extends Subsystem {
	
	public RobotDrive robotDrive;
	Talon driveTopLeft, driveTopRight, driveBottomLeft, driveBottomRight;	
	
	public DriveTrain() {		
		driveBottomLeft = new Talon(RobotMap.driveTrainBottomLeft);
		driveBottomRight = new Talon(RobotMap.driveTrainBottomRight);
		driveTopLeft = new Talon(RobotMap.driveTrainTopLeft);
		driveTopRight = new Talon(RobotMap.driveTrainTopRight);
		robotDrive = new RobotDrive(driveTopLeft, driveBottomLeft, driveTopRight, driveBottomRight);
		LiveWindow.addActuator("DriveTrain", "Bottom Left", driveBottomLeft);
		LiveWindow.addActuator("DriveTrain", "Bottom Right", driveBottomRight);
		LiveWindow.addActuator("DriveTrain", "Top Left", driveTopLeft);
		LiveWindow.addActuator("DriveTrain", "Top Right", driveTopRight);
	}	

	@Override
	protected void initDefaultCommand() {	
		Command command = Utils.Runnable2Command(
					() -> robotDrive.mecanumDrive_Cartesian(OI.stick.getX(), OI.stick.getY(), OI.stick.getTwist(), 0)
				, Robot.driveTrain);
		setDefaultCommand(command);
		
	}

}
