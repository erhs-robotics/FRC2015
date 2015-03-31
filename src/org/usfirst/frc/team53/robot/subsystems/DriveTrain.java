package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.OI;
import org.usfirst.frc.team53.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends PIDSubsystem {
	
	// Constants
	private static final double KP = 2, KI = 0.02, KD = 0;// PID constants
	private static final double PID_SCALE = 0.01;// so we have enough sigfigs tuning PID in livewindow
	private static final double NORMAL_SPEED = 0.6;
	private static final double SLOW_SPEED = 0.3;
	private static final double SONAR_SCALE_INCHES = (1/(5/512.0)); // 1/(Vcc/512) * data
	private static final double SONAR_SCALE_TOTES = SONAR_SCALE_INCHES * (1/27); // scale to tote lengths
	
	// Electronics Objects
	public RobotDrive mRobotDrive;	
	private Talon mDriveTopLeft, mDriveTopRight, mDriveBottomLeft, mDriveBottomRight;	
	public Gyro mGyro;
	public AnalogInput mSonarLeft;
	public AnalogInput mSonarRight;
	
	// Flags and other internals
	private double mRotation = 0;// rotation specified by PID
	private boolean mRotateMode = false;// flag
	private double mMaxSpeed = NORMAL_SPEED;
	
	public DriveTrain() {		
		super(KP, KI, KD);		
		
		mGyro = new Gyro(RobotMap.gyro);	
		mSonarLeft = new AnalogInput(RobotMap.sonarLeft);
		mSonarRight = new AnalogInput(RobotMap.sonarRight);
		mDriveBottomLeft = new Talon(RobotMap.driveTrainBottomLeft);
		mDriveBottomRight = new Talon(RobotMap.driveTrainBottomRight);
		mDriveTopLeft = new Talon(RobotMap.driveTrainTopLeft);
		mDriveTopRight = new Talon(RobotMap.driveTrainTopRight);
		mRobotDrive = new RobotDrive(mDriveTopLeft, mDriveBottomLeft, mDriveTopRight, mDriveBottomRight);
		mRobotDrive.setInvertedMotor(MotorType.kFrontRight, true);
		mRobotDrive.setInvertedMotor(MotorType.kRearRight, true);
		mRobotDrive.setSafetyEnabled(false);
		
		setOutputRange(-0.5 / PID_SCALE, 0.5 / PID_SCALE);

		setSetpoint(mGyro.getAngle());		

		disable();
		LiveWindow.addActuator("DriveTrain", "Bottom Left", mDriveBottomLeft);
		LiveWindow.addActuator("DriveTrain", "Bottom Right", mDriveBottomRight);
		LiveWindow.addActuator("DriveTrain", "Top Left", mDriveTopLeft);
		LiveWindow.addActuator("DriveTrain", "Top Right", mDriveTopRight);
		LiveWindow.addActuator("DriveTrain", "Gyro", mGyro);
		LiveWindow.addActuator("DriveTrain", "PID", getPIDController());
		SmartDashboard.putData("Gyro", mGyro);
		SmartDashboard.putString("Drivetrain Mode: ", "Drive");
		SmartDashboard.putNumber("Sonar (inches)", getCenterSonarDist());
	}
	
	public void mecanumDrive() {		
		double x = OI.mDriveStick.getX();
		double y = OI.mDriveStick.getY();
		double rot = OI.mDriveStick.getTwist();
		x = x*x*x;
		y = y*y*y;
		rot = rot*rot*rot;
		rot = Math.min(rot, 0.35);
		rot = Math.max(rot, -0.35);
		if(mRotateMode)
			mRobotDrive.mecanumDrive_Cartesian(0, 0, rot, 0);	
		else
			mRobotDrive.mecanumDrive_Cartesian(x*mMaxSpeed, y*mMaxSpeed, mRotation, 0);
		
	}
	
	public void mecanumDrivePID(double x, double y) {
		mRobotDrive.mecanumDrive_Cartesian(x, y, mRotation, 0);		
	}
	
	public void setSpeedSlow() {
		mMaxSpeed = SLOW_SPEED;
	}
	
	public void setSpeedNormal() {
		mMaxSpeed = NORMAL_SPEED;
	}
	
	public void setManualMode() {
		mRotateMode = true;
		disable();
		SmartDashboard.putString("Drivetrain Mode: ", "Rotate");
	}
	
	public void setDriveMode() {
		setSetpoint(mGyro.getAngle());
		mRotateMode = false;
		//enable();
		SmartDashboard.putString("Drivetrain Mode: ", "Drive");
	}
	
	public void togglePID() {
		if(getPIDController().isEnable()) {
			disable();			
		} else {
			setSetpoint(mGyro.getAngle());
			enable();			
		}
	}		

	@Override
	protected void initDefaultCommand() { }

	@Override
	protected double returnPIDInput() {		
		return mGyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {		
		mRotation = output * PID_SCALE;		
	}
	
	public double getLeftSonarDist(){
		return mSonarLeft.getVoltage() * SONAR_SCALE_INCHES; 
	}
	
	public double getRightSonarDist(){
		return mSonarRight.getVoltage() * SONAR_SCALE_INCHES; 
	}
	
	public double getSonarRotation(){
		return Math.asin(getLeftSonarDist()-getCenterSonarDist());
	}
	
	public double getCenterSonarDist(){
		return (getLeftSonarDist() + getRightSonarDist())/2;
	}
}
