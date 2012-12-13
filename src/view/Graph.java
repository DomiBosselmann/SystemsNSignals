package view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graph extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private GeneralPath path;
	private double x = 500.0;
	double tmax;
	private double y = 500.0;
	ArrayList<Punkt> points;
	Graph self=this;
   public Graph(ArrayList<Punkt> punkte,double tmax)
   {
       super("FunctionPlotter");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
       setSize((int)(x),(int) (y));
       setVisible(true);
       this.tmax=tmax;
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
       g.drawLine(0, (int)y/2+40, (int)x, (int)y/2+40 );
       g.drawLine(10,0, 10, (int)y);
   }
   
   private void drawFunction(Graphics g) 
   {
       if (path == null) {
           path = new GeneralPath();
           double halfY = y /2; 
           double halfX = x ; 
           double scale=tmax;
           double maxy=1;
           for(Punkt p:points){
        	   if(p.getY()>maxy)maxy=p.getY();
           }
           path.moveTo(points.get(0).getX()+10,(halfY-points.get(0).getY())+40);
           double scaley=halfY/maxy;
           System.out.println(maxy);
           System.out.println(scaley);
           for(Punkt punkt: points )
           {
        	   path.lineTo((punkt.getX()*x/scale)+10, (halfY-(punkt.getY())*scaley)+40);
           }

       }
       Graphics2D g2d = (Graphics2D) g;
       g2d.draw(path);
   }
}
