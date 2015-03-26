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
	private Talon talon1;
	private Talon talon2;
	private Encoder encoder;
	private DigitalInput topLimitSwitch;
	private DigitalInput bottomLimitSwitch;
	private static final double KP = 12.6, KI = 0, KD = 0;	
	private static final double DISTANCE_PER_PULSE = 0.001;	
	private static int level = 0;
	private static int mode = 0;
	private static final double ELEVATOR_LEVELS[][] = {
		{0.0, 1.0, 2.0, 3.0, 4},  // Tote levels
		{0.3, 1.3, 2.3, 3.3, 4}}; // Stacking levels
	
	
	public Elevator() {
		super(KP, KI, KD);
		talon1 = new Talon(RobotMap.elevatorMotor1);	
		talon2 = new Talon(RobotMap.elevatorMotor2);
		topLimitSwitch = new DigitalInput(RobotMap.elevatorTopLimitSwitch);
		bottomLimitSwitch = new DigitalInput(RobotMap.elevatorBottomLimitSwitch);
		encoder = new Encoder(RobotMap.elevatorEncoderChannelA, RobotMap.elevatorEncoderChannelB);		
		encoder.setDistancePerPulse(DISTANCE_PER_PULSE);		
		
		LiveWindow.addActuator("Elevator", "Talon1", talon1);
		LiveWindow.addActuator("Elevator", "Talon2", talon2);
		LiveWindow.addSensor("Elevator", "Encoder", encoder);
		LiveWindow.addSensor("Elevator", "Limit Switch", topLimitSwitch);
		LiveWindow.addSensor("Elevator", "Limit Switch", bottomLimitSwitch);
		LiveWindow.addActuator("Elevator", "PID", this.getPIDController());	
		
		setSetpoint(0);	
	}
	
	public void resetEncoder() {
		encoder.reset();
	}
	
	public void setSetpoint(double set) {
		getPIDController().setSetpoint(set);
	}
	
	public void toggleMode() {
		mode = (mode == 0) ? 1 : 0;
	}
	
	public void setToteLevelMode() {
		mode = 0;
	}
	
	public void setStackLevelMode() {
		mode = 1;
	}
	
	public void incrementLevel() {
		level++;
		level = Math.max(level, 0);
		level = Math.min(level, ELEVATOR_LEVELS[mode].length - 1);
		setSetpoint(ELEVATOR_LEVELS[mode][level]);
	}
	
	public void decrementLevel() {
		level--;
		level = Math.max(level, 0);
		level = Math.min(level, ELEVATOR_LEVELS[mode].length - 1);
		setSetpoint(ELEVATOR_LEVELS[mode][level]);
	}	
	
	public boolean isAtBottom() {
		return bottomLimitSwitch.get();
	}
	
	public boolean isAtTop() { 
		return topLimitSwitch.get();
	}
	
	public void setSpeed(double speed) {
		if(!this.getPIDController().isEnable()) {
			talon1.set(-speed);
			talon2.set(speed);
		}
	}

	@Override
	protected void initDefaultCommand() { }


	@Override
	protected double returnPIDInput() {		
		return encoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		talon1.set(-output);
		talon2.set(output);
	}
}
