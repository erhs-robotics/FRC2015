package org.usfirst.frc.team53.robot.util;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author michael
 */
public class MovingAverage {
    
    private LinkedList<Double> list = new LinkedList<Double>();    
    private final int mSize;
    
    public MovingAverage(final int size) {
    	mSize = size;
    }   

    public double filter(double x) {
    	if(list.size() == mSize) {
	    	Double first = list.poll();
	    	first = x;
	    	list.add(first);
    	} else {
    		list.add(x);
    	} 
    	
        double sum = 0;
        Iterator<Double> iter = list.iterator();
        while(iter.hasNext()) {
        	sum += iter.next();
        }        
        
        return sum / list.size();
    }   
}