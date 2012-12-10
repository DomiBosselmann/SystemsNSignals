import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//Author: Sebastian Schneider    Email: schneider.sebastian@ymail.com  Matrikelnummer: 2913215

public class EingabeGUI {

	JFrame jf = new JFrame();
	JButton start = new JButton("Berechnen");
	JButton regler = new JButton("Regler starten");
	JTextField function_text = new JTextField();
	JTextField h_text = new JTextField();
	JTextField t0_text = new JTextField();
	JTextField x1_text = new JTextField();
	JTextField tmax_text = new JTextField();
	JTextField kp_text = new JTextField();
	JTextField ki_text = new JTextField();
	JTextField kd_text = new JTextField();
	JTextField soll_text = new JTextField();

	String function_t;
	String h_t;
	String t0_t;
	String x1_t;
	String tmax_t;
	String kp_t;
	String ki_t;
	String kd_t;
	String soll_t;

	EingabeGUI() {
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(400, 400);
		jf.setLayout(new GridLayout(12, 2));
		jf.add(new JLabel("Funktion f(t,x,u)"));
		jf.add(function_text);
		jf.add(new JLabel("Schrittweite(h)"));
		jf.add(h_text);
		jf.add(new JLabel("t0"));
		jf.add(t0_text);
		jf.add(new JLabel("x1"));
		jf.add(x1_text);
		jf.add(new JLabel("t-Max"));
		jf.add(tmax_text);
		jf.add(start);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		jf.add(new JLabel());
		jf.add(new JLabel("Regler:"));
		jf.add(new JLabel());
		jf.add(new JLabel("kp:"));
		jf.add(kp_text);
		jf.add(new JLabel("ki:"));
		jf.add(ki_text);
		jf.add(new JLabel("kd:"));
		jf.add(kd_text);
		jf.add(new JLabel("sollwert:"));
		jf.add(soll_text);
		jf.add(regler);
		regler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});
		jf.pack();
		jf.setVisible(true);
	}

	public void getInput() {
		function_t = function_text.getText();
		x1_t = x1_text.getText();
		h_t = h_text.getText();
		t0_t = t0_text.getText();
		tmax_t = tmax_text.getText();
		kp_t = kp_text.getText();
		ki_t = ki_text.getText();
		kd_t = kd_text.getText();
		soll_t = soll_text.getText();
	}

	public static void main(String[] args) {
		EingabeGUI a = new EingabeGUI();
	}

}
