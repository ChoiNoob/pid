package li.rudin.pid.api;

public interface PIDSettings
{

	double getProportional();
	void setProportional(double value);
	
	double getIntegral();
	void setIntegral(double value);
	
	double getDerivative();
	void setDerivative(double value);

	
}
