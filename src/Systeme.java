import java.util.ArrayList;


public class Systeme {
	public int t0;
	public int tn;
	public int abtastrate = 1;

	//Koennen auch Vektoren sein, deshalb Listen
	private ArrayList<Double> currentY; 
	private ArrayList<Double> currentX;
	private ArrayList<Double> currentU;
	
	private ArrayList<String> XEquations;
	private ArrayList<String> YEquations;
	
	private ArrayList<DerivnFunction> ProcessableXEquations;
	private ArrayList<DerivnFunction> ProcessableYEquations;
	
	Systeme (String[] xEquations, String[] yEquations) {
		for (String xEquation : XEquations) {
			this.XEquations.add(xEquation);
		}
		
		for (String yEquation : yEquations) {
			this.YEquations.add(yEquation);
		}
	}
	
	public void inputSignal (ArrayList<Double> currentU, int time) {
		this.currentU = currentU;
		this.calculateStatus();
	}
	
	public void convertEquations () {
		for (String xEquation : this.XEquations) {
			this.ProcessableXEquations.add(MathEvaluator.convertToClass(xEquation));
		}
		
		for (String yEquation : this.YEquations) {
			this.ProcessableYEquations.add(MathEvaluator.convertToClass(yEquation));
		}
	}
	
	public void calculateStatus () {
		// erst die Zustaende
		
		// Create an instance of RungeKutta
        RungeKutta rk = new RungeKutta();

        // Set values needed by fixed step size method
        rk.setInitialValueOfX(x0);
        rk.setFinalValueOfX(xn);
        rk.setInitialValuesOfY(y0);
        rk.setStepSize(h);

        // Call Fourth Order Runge-Kutta method
        yn = rk.fourthOrder(d1);
		
		// jetzt der Ausgang
	}
	
	public ArrayList<Double> getCurrentValues () {
		return currentY;
	}
}
