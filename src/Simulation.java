
public class Simulation {
	private double t0 = 0.0D;
	private double tn;
	private double t = t0;
	
	private Systeme theUltimateSystem;
	private Regler theUltimateRegler;
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, double setpoint, int i, int d, int p) {
		// Dat is mit Regler
		
		theUltimateSystem = new Systeme(xEquations, yEquations, stepSize);
		theUltimateRegler = new Regler(theUltimateSystem, i, d, p, setpoint, stepSize);
	}
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations) {
		// Und dat ohne
		
		theUltimateSystem = new Systeme(xEquations, yEquations, stepSize);
	}
	
	private void start () {
		while (this.t < this.tn) {
			
		}
	}
	
}
