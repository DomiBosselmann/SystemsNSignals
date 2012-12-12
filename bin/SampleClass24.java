public class SampleClass24 implements DerivnFunction {
	private double[] u;
	public double[] derivn(double t, double[] x) {
		double[] x_dot_vec = new double[x.length];

		x_dot_vec[0] = Math.pow(t,3) * Math.tan(t - 2);
		x_dot_vec[1] = t/2 + Math.sin(t);
		

		return x_dot_vec;
	}	public void setU(double[] u) {
		this.u = u;
	}
}