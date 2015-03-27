
package org.usfirst.frc.team53.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team53.robot.commands.Autonomous;
import org.usfirst.frc.team53.robot.subsystems.Claw;
import org.usfirst.frc.team53.robot.subsystems.DriveTrain;
import org.usfirst.frc.team53.robot.subsystems.Elevator;


/**
 * The main class
 */
public class Robot extends IterativeRobot {

	public static Claw claw;
	public static DriveTrain driveTrain;
	public static OI oi;
	public static Compressor compressor;
	public static Elevator elevator;
	public static DigitalInput autoSelector;

    Command autonomousCommand;  
    
    public void robotInit() {
    	claw = new Claw();
    	driveTrain = new DriveTrain();	
		compressor = new Compressor();
		elevator = new Elevator();
		autoSelector = new DigitalInput(RobotMap.autoSelector);
		oi = new OI();
		CameraServer server = CameraServer.getInstance();
		server.setQuality(30);
		server.startAutomaticCapture("cam0");
		//compressor.stop();
		
		SmartDashboard.putData("Elevator", elevator);
		SmartDashboard.putData("Scheduler", Scheduler.getInstance());
        // instantiate the command used for the autonomous period
        autonomousCommand = new Autonomous();
        
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }
    
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {		
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.driveTrain.mRobotDrive.arcadeDrive(0, 0);
        driveTrain.setSetpoint(driveTrain.mGyro.getAngle());
    }
    
    public void disabledInit() {

    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        driveTrain.mecanumDrive();
    }    
      
    public void testPeriodic() {
        LiveWindow.run();
        //driveTrain.mecanumDrive();
        //System.out.println(driveTrain.gyro.getAngle());

    }
}
