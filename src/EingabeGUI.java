import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import view.Graph;
import view.Punkt;

//Author: Sebastian Schneider    Email: schneider.sebastian@ymail.com  Matrikelnummer: 2913215

public class EingabeGUI{

	JFrame jf=new JFrame();
	JButton start=new JButton("Berechnen");
	JButton regler=new JButton("Regler starten");
	JButton neu=new JButton("neue Funktion");
	JTextField function_text=new JTextField();
	JTextField h_text=new JTextField();
	JTextField t0_text=new JTextField();
	JTextField x1_text=new JTextField();
	JTextField tmax_text=new JTextField();
	JTextField kp_text=new JTextField();
	JTextField ki_text=new JTextField();
	JTextField kd_text=new JTextField();
	JTextField soll_text=new JTextField();
	JTextField hfun_text=new JTextField();
	JTextField ein_text=new JTextField();
	ArrayList<Punkt> punkte=new ArrayList();
	JPanel sys=new JPanel();
	JPanel regl=new JPanel();
	EingabeGUI self=this;
	
	int f=1;
	EingabeGUI(){
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(400,400);
		jf.setLayout(new GridLayout(2,1));
		jf.add(sys);
		jf.add(regl);
		sys.setLayout(new GridLayout(8+f,2));
		regl.setLayout(new GridLayout(6,2));
		sys.add(new JLabel("Funktion x'"+f+"(t,x,u)"));
		sys.add(function_text);
		sys.add(new JLabel("Funktion y(t,x,u)"));
		sys.add(hfun_text);
		sys.add(new JLabel("Eingänge"));
		sys.add(ein_text);
		sys.add(new JLabel("Schrittweite(h)"));
		sys.add(h_text);
		sys.add(new JLabel("t0"));
		sys.add(t0_text);
		sys.add(new JLabel("x1"));
		sys.add(x1_text);
		sys.add(new JLabel("Dauer"));
		sys.add(tmax_text);
		sys.add(start);
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
//				double d=Double.parseDouble(tmax_text.getText());
//				double h=Double.parseDouble(h_text.getText());
	//			public Simulation (double duration, double stepSize, String[] uEquations, String[] xEquations, String[] yEquations, EingabeGUI graph) {
				Simulation sim=new Simulation(d,h,ein_text.getText(),function_text.getText(),hfun_text.getText(),self);
				
				
				new Graph(punkte);
				
			}
			
		});
		sys.add(neu);
		neu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				f++;
				System.out.println(f);
				sys.add(new JLabel("Funktion x'"+f+"(t,x,u)"));
				sys.add(new JTextField());
				sys.setLayout(new GridLayout(7+f,2));
				sys.revalidate();
				jf.revalidate();
				
			}
			
		});
		regl.add(new JLabel("Regler:"));
		regl.add(new JLabel());
		regl.add(new JLabel("kp:"));
		regl.add(kp_text);
		regl.add(new JLabel("ki:"));
		regl.add(ki_text);
		regl.add(new JLabel("kd:"));
		regl.add(kd_text);
		regl.add(new JLabel("sollwert:"));
		regl.add(soll_text);
		regl.add(regler);
		regler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		jf.pack();
		jf.setVisible(true);
		
	}
	
	public void addPoint(double t,double[] y){
		punkte.add(new Punkt(t,y[0]));
	}
	
	public static void main(String[] args) {
		EingabeGUI a=new EingabeGUI();
	}

}
