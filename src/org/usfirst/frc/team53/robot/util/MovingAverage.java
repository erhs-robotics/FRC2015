package org.usfirst.frc.team53.robot.util;
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

    public double[] memory;
    private LinkedList<Double> list = new LinkedList<Double>();
    private double[] w;
    private double w_sum;
    private final int mSize;
    
    public MovingAverage(final int size) {
    	mSize = size;
    }   

    private void move(double x, int i) {
        double buf = memory[i];
        memory[i] = x;
        if (i == 0) return;        
        move(buf, i - 1);
    }

    public double filter(double x) {
    	if(list.size() == mSize) {
	    	Double first = list.poll();
	    	first = x;
	    	list.add(first);
    	} else {
    		list.add(x);
    	}
    	
        double sum = x;
        for (int i = 0; i < memory.length; i++) {
            
            sum += memory[i];
        }
        move(x, memory.length - 1);
        return sum / (memory.length + 1);
    }

    public double weightedFilter(double x) {
        double sum = x * w[w.length - 1];
        for (int i = 0; i < memory.length; i++) {
            sum += memory[i] * w[i];
        }
        move(x, memory.length - 1);
        return sum / w_sum;
    }
}