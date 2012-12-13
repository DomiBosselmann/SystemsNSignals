public class SampleClass233 implements SimpleFunction {
	private double[] u;
	public double[] calc(double t) {
		double[] x = new double[20];

		x[0] = Math.sin(t);
		

		return x;
	}	public void setU(double[] u) {
		this.u = u;
	}
}