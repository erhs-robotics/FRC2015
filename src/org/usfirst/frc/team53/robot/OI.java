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
		JoystickButtonX button1 = new JoystickButtonX(stick, 1);// drivetrain manual mode/pid mode
		JoystickButtonX button2 = new JoystickButtonX(stick, 2);// drivetrain toggle pid
		JoystickButtonX button3 = new JoystickButtonX(stick, 3);// claw open		
		JoystickButtonX button4 = new JoystickButtonX(stick, 4);// claw close		
		JoystickButtonX button5 = new JoystickButtonX(stick, 5);// decrement elevator
		JoystickButtonX button6 = new JoystickButtonX(stick, 6);// increment elevator
		JoystickButtonX button7 = new JoystickButtonX(stick, 7);// elevator mode toggle		

		if(Robot.claw != null) {
			SmartDashboardX.putData("Open Claw", Robot.claw::open);
			SmartDashboardX.putData("Close Claw", Robot.claw::close);			
			button3.whenPressed(Robot.claw::open);
			button4.whenPressed(Robot.claw::close);
		}
		
		if(Robot.driveTrain != null) {	
			SmartDashboardX.putData("Drivetrain: Set Rotate Mode", Robot.driveTrain::setManualMode);
			SmartDashboardX.putData("DriveTrain: Set Drive Mode", Robot.driveTrain::setPIDMode);
			button1.whenPressed(Robot.driveTrain::setManualMode);
			button1.whenReleased(Robot.driveTrain::setPIDMode);
			button2.whenPressed(Robot.driveTrain::togglePID);
		}
		
		if(Robot.elevator != null) {
			button5.whenPressed(Robot.elevator::decrementLevel);
			button6.whenPressed(Robot.elevator::incrementLevel);
			button7.whenPressed(Robot.elevator::toggleMode);
		}		
	}
}
