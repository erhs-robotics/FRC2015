package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {	
	
	// Constants
	private static final double KP = 12.6, KI = 0, KD = 0;// PID constants
	private static final double DISTANCE_PER_PULSE = 0.001;// scales the tick count on the encoder motor
	private static final double STACK_ADJUST = 0.9;// move the elevator up by this when stacking
	private static final double HOOK_ADJUST = 0.3;// move the elevator up by this when using hooks
	private static final double ELEVATOR_LEVELS[] = {0.00, 0.90, 1.85, 2.65, 3.45};// Tote levels
	
	// Electronics Objects
	private Talon mTalon1;
	private Talon mTalon2;
	private Encoder mEncoder;
	//private DigitalInput mLimitSwitch;
	
	// Flags and other internals
	private int mLevel = 0;// specifies height of elevator
	private boolean mStackAdjust = false;// flag
	private boolean mHookAdjust = false;// flag												
	
	public Elevator() {
		super(KP, KI, KD);
		
		mTalon1 = new Talon(RobotMap.elevatorMotor1);	
		mTalon2 = new Talon(RobotMap.elevatorMotor2);
		//mLimitSwitch = new DigitalInput(RobotMap.elevatorLimitSwitch);
		mEncoder = new Encoder(RobotMap.elevatorEncoderChannelA, RobotMap.elevatorEncoderChannelB);		
		
		mEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		setSetpoint(0);
		
		LiveWindow.addActuator("Elevator", "Talon1", mTalon1);
		LiveWindow.addActuator("Elevator", "Talon2", mTalon2);
		LiveWindow.addSensor("Elevator", "Encoder", mEncoder);
		//LiveWindow.addSensor("Elevator", "Limit Switch", mLimitSwitch);
		LiveWindow.addActuator("Elevator", "PID", this.getPIDController());			
		updateDashboard();
		enable();
	}
	
	public void resetEncoder() {
		mEncoder.reset();
	}	
	
	public void toggleStackAdjust() {
		System.out.println("mStackAdjust = " + mStackAdjust);
		mStackAdjust = !mStackAdjust;
		updateSetpoint();
		updateDashboard();
	}
	
	public void toggleHookAdjust() {
		System.out.println("mStackAdjust = " + mHookAdjust);
		mHookAdjust = !mHookAdjust;
		updateSetpoint();
		updateDashboard();
	}
	
	public void setLevel(int level) {		
		mLevel = level;
		mLevel = Math.max(mLevel, 0);
		mLevel = Math.min(mLevel, ELEVATOR_LEVELS.length - 1);
		updateSetpoint();
		updateDashboard();
	}
	
	public void incrementLevel() {
		mLevel++;		
		mLevel = Math.max(mLevel, 0);
		mLevel = Math.min(mLevel, ELEVATOR_LEVELS.length - 1);
		System.out.println("increment elevator, level = " + mLevel);
		updateSetpoint();
		updateDashboard();
	}
	
	public void decrementLevel() {		
		mLevel--;		
		mLevel = Math.max(mLevel, 0);
		mLevel = Math.min(mLevel, ELEVATOR_LEVELS.length - 1);
		System.out.println("decrement elevator, level = " + mLevel);
		updateSetpoint();
		updateDashboard();
	}
	
	
	public void setSpeed(double speed) {
		if(!this.getPIDController().isEnable()) {
			mTalon1.set(-speed);
			mTalon2.set(speed);
		}
	}
	
	private void updateSetpoint() {	
		setSetpoint(ELEVATOR_LEVELS[mLevel] + 
					(mStackAdjust ? STACK_ADJUST : 0) + 
					(mHookAdjust ? HOOK_ADJUST : 0));		
	}
	
	private void updateDashboard() {
		SmartDashboard.putNumber("Level: ", mLevel);
		SmartDashboard.putBoolean("Stack Adjust: ", mStackAdjust);
		SmartDashboard.putBoolean("Hook Adjust", mHookAdjust);
		SmartDashboard.putNumber("Setpoint: ", getSetpoint());		
	}

	@Override
	protected void initDefaultCommand() { }

	@Override
	protected double returnPIDInput() {		
		return mEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		mTalon1.set(-output);
		mTalon2.set(output);
	}	
}
