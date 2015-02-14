package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {
	private Talon talon;
	private Encoder encoder;
	private DigitalInput limitSwitch;
	private static final double kp = -12.6, ki = 0, kd = 0;
	private static final double levelStep = 10;
	private static final double DISTANCE_PER_PULSE = 0.001;
	
	private static final int ELEVATOR_MAX_LEVEL = 6;
	
	private static final double ELEVATOR_LEVEL[] = { 0,
													 10,
													 20,
													 30,
													 40,
													 50,
													 60 };
	
	public Elevator() {
		super(kp, ki, kd);
		talon = new Talon(RobotMap.elevatorMotor);
		limitSwitch = new DigitalInput(RobotMap.elevatorLimitSwitch);
		encoder = new Encoder(RobotMap.elevatorEncoderChannelA, RobotMap.elevatorEncoderChannelB);		
		encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		// disable pid controller
		// this.disable();		
		
		LiveWindow.addActuator("Elevator", "Talon", talon);
		LiveWindow.addSensor("Elevator", "Encoder", encoder);
		LiveWindow.addSensor("Elevator", "Limit Switch", limitSwitch);
		LiveWindow.addActuator("Elevator", "PID", this.getPIDController());	
		
		setSetpoint(0);	
	}
	
	public void resetEncoder() {
		encoder.reset();
	}
	
	public void setSetpoint(double set) {
		getPIDController().setSetpoint(set);
	}
	
	public void setLevel(int level) {
		level = Math.max(level, 0);
		level = Math.min(level, ELEVATOR_MAX_LEVEL);
		setSetpoint(level * levelStep);
	}
	
	public boolean isAtBottom() {
		return limitSwitch.get();
	}
	
	public void setSpeed(double speed) {
		if(!this.getPIDController().isEnable()) 
			talon.set(speed);
	}

	@Override
	protected void initDefaultCommand() { }


	@Override
	protected double returnPIDInput() {		
		return encoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		talon.set(output);
	}
}
