public class SampleClass61 implements DerivnFunction {
	private double[] u;
	public double[] derivn(double t, double[] x) {
		double[] x_dot_vec = new double[x.length];

		x_dot_vec[0] = u[0];
		

		return x_dot_vec;
	}	public void setU(double[] u) {
		this.u = u;
	}
}