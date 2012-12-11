public class Regler 
{	
	//Constants
	private double b = 1;
	private double Tf = 1;
	private double Tt = 1;
	private double uMin = -20.5;
	private double uMax = 20.5;
	
	//commited values
	private int ki; 
	private int kd; 
	private int kp; 
	private double setpoint; //Setpoint = Sollwert
	private double stepSize;
	private double[] actualValue;
	private int dimension;
	
	//Controller values
	private double[] yOld;
	private double[] P;
	private double[] I;
	private double[] D;
	
	private double ad;
	private double bi;
	private double bd;
	private double br;
	
	
	public Regler (Systeme system, int i, int d, int p, double setpoint, double stepSize) 
	{
		this.setpoint = setpoint;
		this.stepSize = stepSize;
		this.kp = p;
		this.ki = i; 
		this.kd = d;
		this.actualValue = system.getCurrentValues();
		this.dimension = actualValue.length;
		
		this.yOld = new double[dimension];
		this.P = new double[dimension];
		this.I = new double[dimension];
		this.D = new double[dimension];
		
				
		for (int j = 0; j < dimension; j++) {
			yOld[j] = 0;
			P[j] = 0;
			I[j] = 0;
			D[j] = 0;
		}

		this.ad = this.Tf / (this.Tf + this.stepSize);
		this.bi = this.ki*this.stepSize;
		this.bd = this.kd / (this.Tf+this.stepSize);
		this.br = this.stepSize/this.Tt;		
	}
	
	public double[] Proceed()
	{
		double[] v = new double[this.actualValue.length];
		
		for(int i=0; i<this.actualValue.length; i++)
		{
			this.P[i] = this.kp * (this.b * this.setpoint - this.actualValue[i]);
			this.D[i] = ad * this.D[i] - bd * (this.actualValue[i] - yOld[i]);
			
			v[i] = this.P[i] + this.I[i] + this.D[i];
			
			sat(v[i], this.uMin, this.uMax);
			
			this.I[i] = this.I[i] + bi * (this.setpoint - this.actualValue[i]) + this.br * (v[i] - this.actualValue[i]);
			this.yOld[i] = this.actualValue[i];
		}

		return v;
	}
	
	
	private void sat(double v, double uMin, double uMax) 
	{
		if (v > uMax) 
		{
			v = uMax;
		}
		if (v < uMin) 
		{
			v = uMin;
		}
	}
}