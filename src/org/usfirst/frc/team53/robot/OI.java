package org.usfirst.frc.team53.robot;

import org.usfirst.frc.team53.robot.commands.HoldClaw;
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
		//SmartDashboardX.putData("Open Claw", Robot.claw::open);
		//SmartDashboardX.putData("Close Claw", Robot.claw::close);

		JoystickButtonX button3 = new JoystickButtonX(stick, 3);
		JoystickButtonX button2 = new JoystickButtonX(stick, 2);
		JoystickButtonX button4 = new JoystickButtonX(stick, 4);

		//button3.whenPressed(Robot.claw::open);
		//button2.whenPressed(Robot.claw::close);
		//button4.whenPressed(new HoldClaw());
	}
}
