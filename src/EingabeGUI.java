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
	ArrayList<Punkt> punkte=new ArrayList();
	JPanel sys=new JPanel();
	JPanel regl=new JPanel();
	
	
	int f=1;
	String function_t;
	String h_t;
	String t0_t;
	String x1_t;
	String tmax_t;
	String kp_t;
	String ki_t;
	String kd_t;
	String soll_t;
	String hfun_t;
	EingabeGUI(){
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(400,400);
		jf.setLayout(new GridLayout(2,1));
		jf.add(sys);
		jf.add(regl);
		sys.setLayout(new GridLayout(7+f,2));
		regl.setLayout(new GridLayout(6,2));
		sys.add(new JLabel("Funktion f"+f+"(t,x,u)"));
		sys.add(function_text);
		sys.add(new JLabel("Funktion h(t,x,u"));
		sys.add(hfun_text);
		sys.add(new JLabel("Schrittweite(h)"));
		sys.add(h_text);
		sys.add(new JLabel("t0"));
		sys.add(t0_text);
		sys.add(new JLabel("x1"));
		sys.add(x1_text);
		sys.add(new JLabel("t-Max"));
		sys.add(tmax_text);
		sys.add(start);
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=-500;i<=500;i++){
					int y=i*i;
					punkte.add(new Punkt(i,y));
				}
				new Graph(punkte);
				
			}
			
		});
		sys.add(neu);
		neu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				f++;
				System.out.println(f);
				sys.add(new JLabel("Funktion f"+f+"(t,x,u"));
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
	public void getInput(){
		function_t=function_text.getText();
		x1_t=x1_text.getText();
		h_t=h_text.getText();
		t0_t=t0_text.getText();
		tmax_t=tmax_text.getText();
		kp_t=kp_text.getText();
		ki_t=ki_text.getText();
		kd_t=kd_text.getText();
		soll_t=soll_text.getText();
		hfun_t=hfun_text.getText();
	}
	
	public static void main(String[] args) {
		EingabeGUI a=new EingabeGUI();
	}

}
