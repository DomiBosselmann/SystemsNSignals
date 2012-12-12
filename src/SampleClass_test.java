public class SampleClass_test implements DerivnFunction {
	
	private double[] u;

	@Override
	public double[] derivn(double t, double[] x) {
		double[] x_dot_vec = new double[x.length];
		
		x_dot_vec[0] = Math.sin(t) + 3 * x[0];
		x_dot_vec[1] = Math.pow(t, x[1]);
		
		return x_dot_vec;
	}
	
	public void setU (double[] u) {
		this.u = u;
	}

}
