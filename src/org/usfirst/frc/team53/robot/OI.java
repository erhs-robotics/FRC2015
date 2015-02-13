package org.usfirst.frc.team53.robot;

import org.usfirst.frc.team53.robot.util.JoystickButtonX;
import org.usfirst.frc.team53.robot.util.SmartDashboardX;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick stick = new Joystick(0);

	public OI() {
		JoystickButtonX button3 = new JoystickButtonX(stick, 3);
		JoystickButtonX button2 = new JoystickButtonX(stick, 2);
		JoystickButtonX button4 = new JoystickButtonX(stick, 4);
		JoystickButtonX button1 = new JoystickButtonX(stick, 1);	
		JoystickButtonX button5 = new JoystickButtonX(stick, 5);
		JoystickButtonX button6 = new JoystickButtonX(stick, 6);

		if(Robot.claw != null) {
			SmartDashboardX.putData("Open Claw", Robot.claw::open);
			SmartDashboardX.putData("Close Claw", Robot.claw::close);			
			button3.whenPressed(Robot.claw::open);
			button4.whenPressed(Robot.claw::close);
		}
		
		if(Robot.driveTrain != null) {		
			button1.whenPressed(() -> Robot.driveTrain.setManualMode());
			button1.whenReleased(() -> Robot.driveTrain.setPIDMode());
			button2.whenPressed(() -> Robot.driveTrain.togglePID());
		}
		if(Robot.elevator != null) {
			button5.whileHeld(() -> Robot.elevator.setSetpoint(Robot.elevator.getSetpoint()-1));
			button6.whileHeld(() -> Robot.elevator.setSetpoint(Robot.elevator.getSetpoint()+1));
			
		}
		
	}
}
