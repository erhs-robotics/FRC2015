package org.usfirst.frc.team53.robot.subsystems;

import org.usfirst.frc.team53.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Claw extends Subsystem {
	
	private DoubleSolenoid mDoubleSolenoid;
	
	public Claw() {
		super();
		
		mDoubleSolenoid = new DoubleSolenoid(RobotMap.clawSolenoid1, RobotMap.clawSolenoid2);
		
		LiveWindow.addActuator("Claw", "Double Solenoid", mDoubleSolenoid);
		SmartDashboard.putData("Claw", mDoubleSolenoid);		
	}
	
	public void open() {
		mDoubleSolenoid.set(Value.kForward);
	}
	
	public void close() {
		mDoubleSolenoid.set(Value.kReverse);
	}
	
	public void off() {
		mDoubleSolenoid.set(Value.kOff);
	}
	
	public void toggle() {
		if(mDoubleSolenoid.get() == Value.kForward) {
			close();
		} else {
			open();
		}
	}
	
	public Value getValue() {
		return mDoubleSolenoid.get();
	}

	@Override
	protected void initDefaultCommand() { }
}
