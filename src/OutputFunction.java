public interface OutputFunction {
	double[] calc(double t, double[] x);
	void setU(double[] u);
}
