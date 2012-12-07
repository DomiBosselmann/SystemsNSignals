package view;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Graph extends JFrame 
{
	private static final long serialVersionUID = 1L;

	private GeneralPath path;
	private int x = 320;

	private int y = 240;

   public Graph()
   {
       super("FunctionPlotter");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
       setSize(x, y);
       setVisible(true);
   }
   
   
   public void paint(Graphics g) 
   {
       super.paint(g);
       drawCross(g);
       drawFunction(g);
   }

   private void drawCross(Graphics g) 
   {
       g.drawLine(0, y / 2, x, y / 2);
       g.drawLine(x / 2, 0, x / 2, y);
   }
   
   private ArrayList<Punkt> addPunkte(ArrayList<Punkt> punkte)
   {
	   for(int i=0; i<100; i++)
	   {
		   punkte.add(new Punkt(i,i));
	   }
	   
	   
	   return punkte; 
   }
   private void drawFunction(Graphics g) 
   {
       if (path == null) {
           path = new GeneralPath();
           path.moveTo(0, y / 2);
           float halfY = y / 2; //Mitte der Y Skala
           float halfX = x / 2; //Mitte der X Skala
           float scale = 180;
           
           ArrayList<Punkt> punkte = new ArrayList<Punkt>();
           punkte = addPunkte(punkte);
           
           for(Punkt punkt: punkte )
           {
        	   path.lineTo(halfX+punkt.getX(), halfY-punkt.getY());
           }
           
           //f(x) = x ^3
//           for (int x = (int) -halfX; x < halfX; x++) 
//           {
//               //Mit divsion durch scale skalieren wir die Funktionswerte auf
//               //"Bildschirmfreundliche" Dimensionen...
//               path.lineTo(halfX + x, //die X- Schrittweite
//            		   	halfY - (x * x * x) / scale); //Y-Schrittweite, in der Klammer steht die Funktion 
//           }
       }
       Graphics2D g2d = (Graphics2D) g;
       g2d.draw(path);
   }

   public static void main(String[] args) 
   {
       new Graph();
   }
}
