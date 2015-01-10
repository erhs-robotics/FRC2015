package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Claw extends Subsystem {
	
	private DoubleSolenoid doubleSolenoid;
	
	public Claw() {
		super();
		doubleSolenoid = new DoubleSolenoid(RobotMap.clawSolenoid1, RobotMap.clawSolenoid2);
		LiveWindow.addActuator("Claw", "Double Solenoid", doubleSolenoid);
	}
	
	public void open() {
		doubleSolenoid.set(Value.kForward);
	}
	
	public void close() {
		doubleSolenoid.set(Value.kReverse);
	}
	
	public void off() {
		doubleSolenoid.set(Value.kOff);
	}
	
	public Value getValue() {
		return doubleSolenoid.get();
	}

	@Override
	protected void initDefaultCommand() { }

}
