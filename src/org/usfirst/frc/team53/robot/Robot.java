
package org.usfirst.frc.team53.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team53.robot.subsystems.Claw;
import org.usfirst.frc.team53.robot.subsystems.DriveTrain;


/**
 * The main class
 */
public class Robot extends IterativeRobot {

	public static Claw claw;
	public static DriveTrain driveTrain;
	public static OI oi;
	public static Compressor compressor;

    Command autonomousCommand;  
    
    public void robotInit() {
    	claw = new Claw();
    	driveTrain = new DriveTrain();
    	
		oi = new OI();
		compressor = new Compressor();
		//compressor.stop();		
		
        // instantiate the command used for the autonomous period
        autonomousCommand = null;
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
    }
    
    public void disabledInit(){

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
