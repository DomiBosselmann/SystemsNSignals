import java.io.IOException;


public class Simulation {
	private double t0 = 0.0D;
	private double tn;
	private double t = t0;
	private double stepSize = 0.01D;
	
	private Systeme theUltimateSystem;
	private Regler theUltimateRegler;
	
	private SimpleFunction input;
	
	private EingabeGUI graph;
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, double setpoint, int i, int d, int p, EingabeGUI graph) {
		this.graph = graph;
		this.stepSize = stepSize;
		
		// Dat is mit Regler
		
		theUltimateSystem = new Systeme(xEquations, yEquations, stepSize);
		theUltimateRegler = new Regler(theUltimateSystem, i, d, p, setpoint, stepSize);
	}
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, EingabeGUI graph) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.graph = graph;
		this.stepSize = stepSize;
		
		this.input = MathEvaluator.convertNullOrderEquationToClass(uEquations);
		// Und dat ohne
		
		theUltimateSystem = new Systeme(xEquations, yEquations, stepSize);
	}
	
	private void start () {
		while (this.t < this.tn) {
			// NŠchsten Schritt simulieren
			
			if (this.theUltimateRegler != null) {
				this.theUltimateRegler.simulateNextStep();
			} else {
				this.theUltimateSystem.simulateNextStep(this.t, this.input.calc(this.t));
			}
			
			// Punkte der Simulation bestimmen und zeichnen lassen
			this.graph.addPoint(this.t, this.theUltimateSystem.getCurrentValues());
			
			// Zeit weiter setzen
			this.t += this.stepSize;
		}		
	}
	
}
