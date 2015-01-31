package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.OI;
import org.usfirst.frc.team53.robot.Robot;
import org.usfirst.frc.team53.robot.RobotMap;
import org.usfirst.frc.team53.robot.util.Utils;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class DriveTrain extends PIDSubsystem {
	
	public RobotDrive robotDrive;	
	private Talon driveTopLeft, driveTopRight, driveBottomLeft, driveBottomRight;
	private static final double kp = 2, ki = 0.02, kd = 0;
	public Gyro gyro;
	private double rotation;
	private static final double K = 0.01;
	private boolean manualMode = false;
	
	public DriveTrain() {		
		super(kp, ki, kd);		
		gyro = new Gyro(RobotMap.gyro);		
		
		driveBottomLeft = new Talon(RobotMap.driveTrainBottomLeft);
		driveBottomRight = new Talon(RobotMap.driveTrainBottomRight);
		driveTopLeft = new Talon(RobotMap.driveTrainTopLeft);
		driveTopRight = new Talon(RobotMap.driveTrainTopRight);
		robotDrive = new RobotDrive(driveTopLeft, driveBottomLeft, driveTopRight, driveBottomRight);
		robotDrive.setInvertedMotor(MotorType.kFrontRight, true);
		robotDrive.setInvertedMotor(MotorType.kRearRight, true);
		LiveWindow.addActuator("DriveTrain", "Bottom Left", driveBottomLeft);
		LiveWindow.addActuator("DriveTrain", "Bottom Right", driveBottomRight);
		LiveWindow.addActuator("DriveTrain", "Top Left", driveTopLeft);
		LiveWindow.addActuator("DriveTrain", "Top Right", driveTopRight);
		LiveWindow.addActuator("DriveTrain", "Gyro", gyro);
		LiveWindow.addActuator("DriveTrain", "PID", getPIDController());
		setOutputRange(-0.5 / K, 0.5 / K);
		setSetpoint(0);
		//disable();
	}
	
	public void mecanumDrive() {		
		double x = OI.stick.getX();
		double y = OI.stick.getY();
		double rot = OI.stick.getTwist();
		x = x*x*x;
		y = y*y*y;
		rot = rot*rot*rot;
		rot = Math.min(rot, 0.4);
		rot = Math.max(rot, -0.4);
		if(manualMode)
			robotDrive.mecanumDrive_Cartesian(0, 0, rot, 0);	
		else
			robotDrive.mecanumDrive_Cartesian(x*.6, y*.6, rotation, 0);
	}
	
	public void setManualMode() {
		manualMode = true;
		disable();
	}
	
	public void setPIDMode() {
		manualMode = false;
		setSetpoint(gyro.getAngle());
		enable();
	}
	
	public void togglePID() {
		if(getPIDController().isEnable()) {
			disable();
		} else {
			setSetpoint(gyro.getAngle());
			enable();
		}
	}
		

	@Override
	protected void initDefaultCommand() {		
	}

	@Override
	protected double returnPIDInput() {		
		return gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {		
		rotation = output * K;		
	}
}
