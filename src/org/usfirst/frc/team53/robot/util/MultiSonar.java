package org.usfirst.frc.team53.robot.util;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class MultiSonar {
	private ArrayList<AnalogInput> sonar;
	private double scaleFactor;
	private DigitalOutput pulsePin;
	
	public MultiSonar(int numSensors, int pulsePin, double scaleFactor, ArrayList<Integer> pins){
		assert numSensors == pins.size();
		this.pulsePin = new DigitalOutput(pulsePin);
		pins.forEach((pin) -> this.sonar.add(new AnalogInput(pin)));
		this.scaleFactor = scaleFactor;
	}
	
	/**
	 * The pins should be initiated left from right from the view of the robot
	 */
	public MultiSonar(double scaleFactor, int... pins)
	{
		this.scaleFactor = scaleFactor;
		for(int i = 0; i < pins.length; i++)
		{
			this.sonar.add(new AnalogInput(pins[i]));
		}
	}
	
	public double getSonar(int index){
		return this.getRawSonar(index)*this.scaleFactor;
	}
	
	public double getRawSonar(int index){
		this.pulsePin.pulse(this.pulsePin.getChannel(), (float) 35.0);
		return this.sonar.get(index).getVoltage();
	}
	
	public void setScaleFactor(double newScaleFactor){
		this.scaleFactor = newScaleFactor;
	}
	
	public double getScaleFactor(){
		return this.scaleFactor;
	}
	
}
