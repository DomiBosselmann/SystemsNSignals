import java.util.ArrayList;


public class Systeme {
	public int t0;
	public int tn;
	public int abtastrate = 1;
	
	private ArrayList<Double> currentY;
	private ArrayList<Double> currentX;
	private ArrayList<Double> currentU;
	
	Systeme () {
		
	}
	
	public void inputSignal (ArrayList<Double> currentU, int time) {
		this.currentU = currentU;
		this.calculateStatus();
	}
	
	public void calculateStatus () {
		// erst die Zustände
		
		
		// jetzt der Ausgang
	}
	
	public ArrayList<Double> getCurrentValues () {
		return currentY;
	}
}
