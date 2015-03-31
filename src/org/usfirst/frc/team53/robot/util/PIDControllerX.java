package org.usfirst.frc.team53.robot.util;


/**
 * A general purpose PID Controller that can be specified to use an interface 
 * system to define PIDInput and PIDOutput sources or used to simply compute and
 * return the PID response.  Assumes a constant time interval between each run 
 * of the controller.
 * @author Nick Yaculak
 */
public class PIDControllerX {
    
    public static interface PIDInput {
        /**
         * The method used by the PID Controller to determine the input state of the
         * system.
         * @return the input state of the system 
         */
        public double getPIDInput();
    }
    public static interface PIDOutput {
        /**
         * The method used to run the PID Controller
         * @param pidResponse the PID response to use as a control parameter.
         */
        public void runPIDOutput(double pidResponse);
    }
    
    private double kP, kI, kD, setpoint;
    private double lastError, totalError;
    private double minOutput, maxOutput;
    private boolean capOutput;
    private boolean firstRun = true;
    private boolean enabled = true;
    
    private PIDInput  input;
    private PIDOutput output;
    
    private final int ERROR_LOG_LENGTH = 10;
    private double[] errorLog = new double[ERROR_LOG_LENGTH];
    
    /**
     * Constructor to provide a PIDInput and PIDOutput source for an automated
     * PID Controller.
     * 
     * @param kP proportional PID constant
     * @param kI integral PID constant
     * @param kD derivative PID constant
     * @param setpoint the setpoint to reach
     * @param input the PIDInput source
     * @param output the PIDOutput source
     */
    public PIDControllerX(double kP, double kI, double kD, double setpoint, 
                          PIDInput input, PIDOutput output) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.setpoint = setpoint;
        this.input = input;
        this.output = output;
    }
    
    /**
     * Constructs the PIDController without the PIDInput and PIDOutput instances.
     * Should be used if you only want the PIDResponse.
     * 
     * @param kP proportional PID constant
     * @param kI integral PID constant
     * @param kD derivative PID constant
     * @param setpoint the setpoint to reach
     */
    public PIDControllerX(double kP, double kI, double kD, double setpoint) {
        this(kP, kI, kD, setpoint, null, null);
    }
    
    public PIDControllerX(double kP, double kI, double kD, double setpoint, PIDOutput output)
    {
        this(kP, kI, kD, setpoint, null, output);
    }
    
    /**
     * Given the current state of the system, returns the PID response to send 
     * to the output source to reach the setpoint.  This function will still run
     * even if the disable() function is called.
     * 
     * @param input the current state of the system
     * @return the calculated PID response
     */
    public double getPIDResponse(double input) {
        double response;
        
        double error = setpoint - input;
        totalError += error;
        
        if(firstRun) { 
            response = kP * error;
            firstRun = false;
        } else {
            double de = error - lastError;
            response = kP * error + kI * totalError + kD * de;
        }
        
        if(capOutput) {
            response = clamp(response, minOutput, maxOutput);
        }
        
        lastError = error;
        
        // move up errorLog[i] into errorLog[i-1] and store most recent error
        // into the last index of the errorLog
        for(int i = 1; i < errorLog.length; i++) {
            errorLog[i-1] = errorLog[i];
        }
        errorLog[errorLog.length-1] = error;
        
        return response;
    }
    
    /**
     * Run the PID Control loop if a PIDInput and PIDOutput instance are
     * provided and the PID Controller is enabled.
     */
    public void runPID() {
        if(input instanceof PIDInput && output instanceof PIDOutput) {
            if(enabled) {
                double response = getPIDResponse(input.getPIDInput());
                output.runPIDOutput(response);
            }
        }
    }
    
    /**
     * Resets the state of the PID controller.  Used to reset the integral
     * accumulator.
     */
    public void reset() {
        this.lastError = 0;
        this.totalError = 0;
        this.firstRun = true;
    }
    
    /**
     * Caps the output of the PID response
     * 
     * @param minOutput the minimum output
     * @param maxOutput the maximum output
     */
    public void capOutput(double minOutput, double maxOutput) {
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
        this.capOutput = true;
    }
    
    /**
     * Allows the runPID() function to run.
     */
    public void enable() {
        this.enabled = true;
    }
    
    /**
     * Stops the runPID() function from running.  The getPIDResponse() function
     * will still run.
     */
    public void disable() { 
        this.enabled = false;
    }
    
    /**
     * Function not yet implemented.  Checks if the PID response has converged
     * to a steady state error.
     * @param iterations number of iterations to check for convergence
     * @return whether the PID control has converged
     * @throws java.lang.Exception
     */
    public boolean isConverged(int iterations) throws Exception {
        throw new Exception("Function not implemented");
        /*
        double div = 1;
        for(int i = 0; i < errorLog.length-1; i++) {
            div *= errorLog[i+1] / errorLog[i];
        }
        return false;
        */
    }
    
    /**************************
     * Mutators and Accessors *
     **************************/
    public void setKP(double kP) { this.kP = kP; }
    public void setKI(double kI) { this.kI = kI; }
    public void setKD(double kD) { this.kD = kD; }
    public void setSetpoint(double setpoint) { this.setpoint = setpoint; }
    public double getKP() { return this.kP; }
    public double getKI() { return this.kI; }
    public double getKD() { return this.kD; }
    public double getSetpoint() { return this.setpoint; }
    public double getMinOutput() { return this.minOutput; }
    public double getMaxOutput() { return this.maxOutput; }
    public boolean isEnabled() { return this.enabled; }
    
    /****************************
     * Private Helper Functions *
     ****************************/
    private double clamp(double input, double min, double max) {
        if(input <= min) {
            return min;
        } else if (input >= max) {
            return max;
        }
        return input;
    }
    
}
