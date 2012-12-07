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
	
	Systeme () {
		
	}
	
	public void inputSignal (ArrayList<Double> currentU, int time) {
		this.currentU = currentU;
		this.calculateStatus();
	}
	
	public void calculateStatus () {
		// erst die Zustaende
		
		
		// jetzt der Ausgang
	}
	
	public ArrayList<Double> getCurrentValues () {
		return currentY;
	}
}
