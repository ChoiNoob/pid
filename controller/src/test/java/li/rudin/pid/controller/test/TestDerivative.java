package li.rudin.pid.controller.test;

import li.rudin.pid.api.PIDInput;
import li.rudin.pid.api.PIDOutput;
import li.rudin.pid.controller.PIDController;

import org.junit.Assert;
import org.junit.Test;

public class TestDerivative implements PIDOutput, PIDInput
{
	
	
	@Test
	public void test()
	{
		PIDController pid = new PIDController(0.0, 0.0, 1.0, this, this);
		
		input = 10;
		target = 20;
		
		pid.run();	
		Assert.assertEquals(0.0, output, 0.1);
		
		pid.run();	
		Assert.assertEquals(0.0, output, 0.1);

		
		input = 20;
		pid.run();
		Assert.assertEquals(-10.0, output, 0.1);
		
		pid.run();	
		Assert.assertEquals(0.0, output, 0.1);

		input = 10;
		pid.run();
		Assert.assertEquals(10.0, output, 0.1);
		
		pid.run();	
		Assert.assertEquals(0.0, output, 0.1);

	}
	
	double input, output, target;

	@Override
	public double getInput() {
		return input;
	}

	@Override
	public double getTarget() {
		return target;
	}

	@Override
	public void setOutput(double output) {
		this.output = output;
	}

	@Override
	public double getMax() {
		return 100;
	}

	@Override
	public double getMin() {
		return -100;
	}

}
