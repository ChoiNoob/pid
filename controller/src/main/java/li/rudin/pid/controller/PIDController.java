package li.rudin.pid.controller;

import li.rudin.pid.api.PIDInput;
import li.rudin.pid.api.PIDOutput;
import li.rudin.pid.api.PIDSettings;

public class PIDController implements PIDSettings, Runnable
{

	public PIDController(double Kp, double Ki, double Kd, PIDInput input, PIDOutput output)
	{
		this.Ki = Ki;
		this.Kp = Kp;
		this.Kd = Kd;

		this.input = input;
		this.output = output;
	}

	public PIDController(double p, double i, PIDInput input, PIDOutput output)
	{
		this(p, i, 0, input, output);
	}

	/**
	 * PID input
	 */
	private final PIDInput input;

	/**
	 * PID Output
	 */
	private final PIDOutput output;

	@Override
	public synchronized void run()
	{
		double input =  this.input.getInput();

		if (lastInput == null)
			//Use current value, no derivative action on first sample
			lastInput = input;

		double target = this.input.getTarget();

		double outputMax = this.output.getMax();
		double outputMin = this.output.getMin();

		double error = target - input;

		iSum += Ki * error;

		if (iSum > outputMax)
			iSum = outputMax;
		else if (iSum < outputMin)
			iSum = outputMin;

		double inputDelta = input - lastInput;

		double output = (Kp * error) + iSum - (Kd * inputDelta);

		if (output > outputMax)
			output = outputMax;
		else if (output < outputMin)
			output = outputMin;

		this.output.setOutput(output);

		lastInput = input;
	}

	/**
	 * Last input value
	 */
	 private Double lastInput;


	 /**
	  * Integral sum
	  */
	 private double iSum;

	 /**
	  * PID Settings
	  */
	 private double Kp, Ki, Kd;

	 /*
	  * PID Settings setter/getter
	  */

	 @Override
	 public double getProportional() {
		 return Kp;
	 }

	 @Override
	 public void setProportional(double value) {
		 Kp = value;
	 }

	 @Override
	 public double getIntegral() {
		 return Ki;
	 }

	 @Override
	 public void setIntegral(double value) {
		 Ki = value;
	 }

	 @Override
	 public double getDerivative() {
		 return Kd;
	 }

	 @Override
	 public void setDerivative(double value) {
		 Kd = value;
	 }



}
