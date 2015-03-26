package org.usfirst.frc.team53.robot;

import edu.wpi.first.wpilibj.DigitalSource;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	// PWM	
	public static final int driveTrainBottomLeft = 1,
						    driveTrainBottomRight = 2,
						    driveTrainTopLeft = 3,
						    driveTrainTopRight = 4;
	public static final int elevatorMotor1 = 5, 
							elevatorMotor2 = 6;
	
	// Analog In	
	public static int gyro = 0;
	
	// Solenoid
	public static int clawSolenoid1 = 1;
	public static int clawSolenoid2 = 0;
	
	// Digital
	public static final int elevatorEncoderChannelA = 0;
	public static final int elevatorEncoderChannelB = 1;
	public static final int elevatorTopLimitSwitch = 5;
	public static final int elevatorBottomLimitSwitch = 6;
}
