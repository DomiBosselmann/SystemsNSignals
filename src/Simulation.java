// Autor: Dominique Bosselmann, 3530073

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
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, double[] xStartValues, double setpoint, int i, int d, int p, EingabeGUI graph) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		this.graph = graph;
		this.stepSize = stepSize;
		
		this.tn = duration;
		
		// Dat is mit Regler
		
		theUltimateSystem = new Systeme(xEquations, yEquations, xStartValues, stepSize);
		theUltimateRegler = new Regler(theUltimateSystem, i, d, p, setpoint, stepSize);
		
		this.start();
	}
	
	public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, double[] xStartValues, EingabeGUI graph) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.graph = graph;
		this.stepSize = stepSize;
		
		this.tn = duration;
		
		this.input = MathEvaluator.convertNullOrderEquationToClass(uEquations);
		// Und dat ohne
		
		theUltimateSystem = new Systeme(xEquations, yEquations, xStartValues, stepSize);
		
		this.start();
	}
	
	private void start () {
		while (this.t < this.tn) {
			// NŠchsten Schritt simulieren
			
			if (this.theUltimateRegler != null) {
				// Erst den Regler ranlassen
				this.theUltimateRegler.simulateNextStep();
				
				// Jetzt hat das System fŸnf Schritte, um zu reagieren
				for (int i = 1; i <= 5; i++) {
					this.theUltimateSystem.simulateNextStep(this.t, this.input.calc(this.t));
					// Punkte der Simulation bestimmen und zeichnen lassen
					this.graph.addPoint(this.t, this.theUltimateSystem.getCurrentValues());
					this.t += this.stepSize;
				}
				
				// Und jetzt die Scho§e wieder von vorne
				
			} else {
				this.theUltimateSystem.simulateNextStep(this.t, this.input.calc(this.t));
				
				// Punkte der Simulation bestimmen und zeichnen lassen
				System.out.println("\n\n====Systemzustand bei t = " + this.t + ":\n");
				double[] currentY = this.theUltimateSystem.getCurrentValues();
				
				for (int y = 0; y < currentY.length; y++) {
					System.out.println("y[" + y + "]: " + currentY[y]);
				}
				
				System.out.println("====");
				
				this.graph.addPoint(this.t, this.theUltimateSystem.getCurrentValues());
				
				// Zeit weiter setzen
				this.t += this.stepSize;
			}
		}		
	}
	
}
