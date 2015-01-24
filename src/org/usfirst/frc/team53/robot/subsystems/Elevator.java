package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Elevator extends PIDSubsystem {
	private Talon talon;
	private AnalogPotentiometer pot;
	private static final double kp = 1, ki = 0, kd = 0;
	private static final double levelStep = 10;
	
	public Elevator() {
		super(kp, ki, kd);		
		talon = new Talon(RobotMap.elevatorMotor);
		pot = new AnalogPotentiometer(RobotMap.elevatorPot);
		setSetpoint(pot.get());
		
		LiveWindow.addActuator("Elevator", "Talon", talon);
		LiveWindow.addSensor("Elevator", "Pot", pot);
	}
	
	public void setSetpoint(double set) {
		getPIDController().setSetpoint(set);
	}
	
	public void setLevel(int level) {
		setSetpoint(level * levelStep);
	}

	@Override
	protected void initDefaultCommand() { }


	@Override
	protected double returnPIDInput() {		
		return pot.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		talon.set(output);
	}
}
