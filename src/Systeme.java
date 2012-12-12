// Autor: Dominique Bosselmann, 3530073

import java.io.IOException;
import java.util.ArrayList;


public class Systeme {
	public double t;
	public double stepSize = 0.1D;

	//Koennen auch Vektoren sein, deshalb Listen
	private double[] currentY; 
	private double[] currentX;
	private double[] currentU;
	
	private String[] XEquations;
	private String[] YEquations;
	
	private DerivnFunction ProcessableXEquations;
	private OutputFunction ProcessableYEquations;
	
	private int numberOfOutputs;
	
	Systeme (String[] xEquations, String[] yEquations, double[] xStartValues, double stepSize) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		this.XEquations = xEquations;
		this.YEquations = yEquations;
		this.currentX = xStartValues;
		this.stepSize = stepSize;
		
		this.numberOfOutputs = yEquations.length;
		
		// Gleichungen umwandeln
		
		this.convertEquations();
	}
	
	public void simulateNextStep (double t, double[] currentU) {
		this.t = t;
		this.currentU = currentU;
		this.ProcessableXEquations.setU(this.currentU);
		this.ProcessableYEquations.setU(this.currentU);
		this.calculateStatus();
	}
	
	public void simulateNextStep (double t) {
		this.t = t;
		this.currentU = new double[] {};
		this.calculateStatus();
	}
	
	public void convertEquations () throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		this.ProcessableXEquations = MathEvaluator.convertFirstOrderEquationToClass(this.XEquations);
		this.ProcessableYEquations = MathEvaluator.convertOutputFunctionToClass(this.YEquations);
	}
	
	private void calculateStatus () {
		// erst die Zustaende
		
		this.ProcessableXEquations.setU(this.currentU);
        this.currentX = RungeKutta.fourthOrder(this.ProcessableXEquations, this.t, this.currentX, this.t + this.stepSize, this.stepSize);
		
		// jetzt der Ausgang
        
        this.ProcessableYEquations.setU(this.currentU);
        this.currentY = this.ProcessableYEquations.calc(this.t, this.currentX);
	}
	
	public double[] getCurrentValues () {
		return currentY;
	}
	
	public int getNumberOfOutputs () {
		return this.numberOfOutputs;
	}
}
