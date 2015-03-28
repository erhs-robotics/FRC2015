package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.OI;
import org.usfirst.frc.team53.robot.RobotMap;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx_mxp.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class DriveTrain extends PIDSubsystem {
	
	// Constants
	private static final double KP = 2, KI = 0.02, KD = 0;// PID constants
	private static final double PID_SCALE = 0.01;// so we have enough sigfigs tuning PID in livewindow
	private static final double AUTO_DRIVE_DIST = 10.0;
	
	// Electronics Objects
	public RobotDrive mRobotDrive;	
	private Talon mDriveTopLeft, mDriveTopRight, mDriveBottomLeft, mDriveBottomRight;	
	public AHRS mGyro;
	private SerialPort gyroSerial;
	
	// Flags and other internals
	private double mRotation;// rotation specified by PID
	private boolean mRotateMode = false;// flag
	
	public DriveTrain() {		
		super(KP, KI, KD);		
		
		gyroSerial = new SerialPort(57600, SerialPort.Port.kMXP); 
		mGyro = new AHRS(gyroSerial, (byte) 60);
		mDriveBottomLeft = new Talon(RobotMap.driveTrainBottomLeft);
		mDriveBottomRight = new Talon(RobotMap.driveTrainBottomRight);
		mDriveTopLeft = new Talon(RobotMap.driveTrainTopLeft);
		mDriveTopRight = new Talon(RobotMap.driveTrainTopRight);
		mRobotDrive = new RobotDrive(mDriveTopLeft, mDriveBottomLeft, mDriveTopRight, mDriveBottomRight);
		mRobotDrive.setInvertedMotor(MotorType.kFrontRight, true);
		mRobotDrive.setInvertedMotor(MotorType.kRearRight, true);
		
		setOutputRange(-0.5 / PID_SCALE, 0.5 / PID_SCALE);
		setSetpoint(mGyro.getYaw());
		disable();
		
		LiveWindow.addActuator("DriveTrain", "Bottom Left", mDriveBottomLeft);
		LiveWindow.addActuator("DriveTrain", "Bottom Right", mDriveBottomRight);
		LiveWindow.addActuator("DriveTrain", "Top Left", mDriveTopLeft);
		LiveWindow.addActuator("DriveTrain", "Top Right", mDriveTopRight);
		LiveWindow.addActuator("DriveTrain", "Gyro", mGyro);
		LiveWindow.addActuator("DriveTrain", "PID", getPIDController());
		SmartDashboard.putData("Gyro", mGyro);
		SmartDashboard.putString("Drivetrain Mode: ", "Drive");				
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
		if(mRotateMode)
			mRobotDrive.mecanumDrive_Cartesian(0, 0, rot, 0);	
		else
			mRobotDrive.mecanumDrive_Cartesian(x*.6, y*.6, mRotation, 0);
		
	}
	
	public void setManualMode() {
		mRotateMode = true;
		disable();
		SmartDashboard.putString("Drivetrain Mode: ", "Rotate");
	}
	
	public void setPIDMode() {
		mRotateMode = false;
		setSetpoint(mGyro.getYaw());
		enable();
		SmartDashboard.putString("Drivetrain Mode: ", "Drive");
	}
	
	public void togglePID() {
		if(getPIDController().isEnable()) {
			disable();			
		} else {
			setSetpoint(mGyro.getYaw());
			enable();			
		}
	}		

	@Override
	protected void initDefaultCommand() { }

	@Override
	protected double returnPIDInput() {		
		return mGyro.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {		
		mRotation = output * PID_SCALE;		
	}
	
	public float getXDisplacement(){
		return mGyro.getDisplacementX();
	}
	
	public float getYDisplacement(){
		return mGyro.getDisplacementY();
	}
	
	public void intelligentAuto(){
		while(getXDisplacement()>AUTO_DRIVE_DIST){
			mRobotDrive.mecanumDrive_Cartesian(-0.4, 0, 0, mGyro.getYaw());
		}
	}
}
