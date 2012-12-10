package view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Graph extends JFrame 
{
	private static final long serialVersionUID = 1L;

	private GeneralPath path;
	private double x = 1000.0;

	private double y = 1000.0;
	ArrayList<Punkt> points;
   public Graph(ArrayList<Punkt> punkte)
   {
       super("FunctionPlotter");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
       setSize((int)(x),(int) (y));
       setVisible(true);
       points=punkte;
   }
   
   
   public void paint(Graphics g) 
   {

       g.setColor(Color.white);
       super.paint(g);
       g.setColor(Color.black);
       drawCross(g);
       g.setColor(Color.red);
       drawFunction(g);
   }

   private void drawCross(Graphics g) 
   {
       g.drawLine(0, (int)y / 2, (int)x, (int)y / 2);
       g.drawLine((int)x / 2, 0, (int)x / 2, (int)y);
   }
   
   private void drawFunction(Graphics g) 
   {
       if (path == null) {
           path = new GeneralPath();
           double halfY = y / 2; //Mitte der Y Skala
           double halfX = x / 2; //Mitte der X Skala
           //float scale = 180;
           path.moveTo(points.get(0).getX()*5+halfX,halfY-points.get(0).getY()*5);
           for(Punkt punkt: points )
           {
        	   path.lineTo(halfX+punkt.getX()*5, halfY-punkt.getY()*5);
           }
           
           //f(x) = x ^3
//           for (doublex = (int) -halfX; x < halfX; x++) 
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
}
