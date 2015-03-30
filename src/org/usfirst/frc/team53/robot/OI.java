package org.usfirst.frc.team53.robot;

import org.usfirst.frc.team53.robot.util.JoystickButtonX;
import org.usfirst.frc.team53.robot.util.SmartDashboardX;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick mDriveStick = new Joystick(0);
	public static Joystick mClawStick = new Joystick(1);

	public OI() {
		JoystickButtonX toggleDriveModeB = new JoystickButtonX(mDriveStick, 1);// drivetrain manual mode/pid mode
		JoystickButtonX toggleDrivePID_B = new JoystickButtonX(mDriveStick, 2);// drivetrain toggle pid
		JoystickButtonX driveSlowB = new JoystickButtonX(mDriveStick, 12);
		JoystickButtonX toggleClawB = new JoystickButtonX(mClawStick, 1);// claw toggle			
		JoystickButtonX decrementElevatorB = new JoystickButtonX(mClawStick, 2);// decrement elevator
		JoystickButtonX incrementElevatorB = new JoystickButtonX(mClawStick, 3);// increment elevator
		JoystickButtonX stackAdjustB = new JoystickButtonX(mClawStick, 10);// elevator toggle stack adjust
		JoystickButtonX hookAdjustB = new JoystickButtonX(mClawStick, 11);// elevator toggle hook adjust
		JoystickButtonX manualUpB = new JoystickButtonX(mClawStick, 8);// elevator toggle hook adjust\
		JoystickButtonX manualDownB = new JoystickButtonX(mClawStick, 9);// elevator toggle hook adjust
		
		if(Robot.claw != null) {
			SmartDashboardX.putData("Open Claw", Robot.claw::open);
			SmartDashboardX.putData("Close Claw", Robot.claw::close);			
			toggleClawB.whenPressed(Robot.claw::toggle);			
		}
		
		if(Robot.driveTrain != null) {	
			SmartDashboardX.putData("Drivetrain: Set Rotate Mode", Robot.driveTrain::setManualMode);
			SmartDashboardX.putData("DriveTrain: Set Drive Mode", Robot.driveTrain::setDriveMode);
			toggleDriveModeB.whenPressed(Robot.driveTrain::setManualMode);
			toggleDriveModeB.whenReleased(Robot.driveTrain::setDriveMode);
			toggleDrivePID_B.whenPressed(Robot.driveTrain::togglePID);
			driveSlowB.whenPressed(Robot.driveTrain::setSpeedSlow);
			driveSlowB.whenReleased(Robot.driveTrain::setSpeedNormal);
		}
		
		if(Robot.elevator != null) {
			decrementElevatorB.whenPressed(Robot.elevator::decrementLevel);
			incrementElevatorB.whenPressed(Robot.elevator::incrementLevel);
			stackAdjustB.whenPressed(Robot.elevator::toggleStackAdjust);
			hookAdjustB.whenPressed(Robot.elevator::toggleHookAdjust);
			manualDownB.whileActive(() -> Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() - 0.01));
			manualUpB.whileActive(() -> Robot.elevator.setSetpoint(Robot.elevator.getSetpoint() + 0.01));
		}		
	}
}
