package li.rudin.pid.controller.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import li.rudin.pid.api.PIDInput;
import li.rudin.pid.api.PIDOutput;
import li.rudin.pid.controller.PIDController;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestScheduler implements PIDOutput, PIDInput
{
	
	private static final Logger logger = LoggerFactory.getLogger(TestScheduler.class);
	
	@Test
	public void test() throws Exception
	{
		PIDController pid = new PIDController(1, 0.2, 0, this, this);
		
		processValue = 0;
		target = 20;
		
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		
		pool.scheduleAtFixedRate(pid, 0, 5, TimeUnit.MILLISECONDS);
		
		Thread.sleep(1000);
		
		pool.shutdown();
		
		Assert.assertEquals(20.0, processValue, 1.0);
	}
	
	double processValue, target;

	@Override
	public double getInput() {
		return processValue;
	}

	@Override
	public double getTarget() {
		return target;
	}

	@Override
	public void setOutput(double output){ 
		processValue += output/10;
		
		logger.debug("Target: {}, processValue: {}, output: {}", target, processValue, output);
	}

	@Override
	public double getMax() {
		return 10;
	}

	@Override
	public double getMin() {
		return -10;
	}

}
