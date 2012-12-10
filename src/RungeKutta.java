/*
*   Class RungeKutta
*       requires interfaces DerivFunction and DerivnFunction
*
*   Contains the methods for the Runge-Kutta procedures for solving
*   single or solving sets of ordinary differential equations (ODEs)
*   [draws heavily on the approach adopted in Numerical Recipes
*   (C language version)http://www.nr.com]
*
*   A single ODE is supplied by means of an interface,
*       DerivFunction
*   A set of ODEs is supplied by means of an interface,
*       DerivnFunction
*
*   WRITTEN BY: Dr Michael Thomas Flanagan
*
*   DATE:	    February 2002
*   UPDATES:    22 June 2003,  April 2004,
*               15 September 2006 (to incorporate improvements suggested by Klaus Benary [Klaus.Benary@gede.de])
*               11 April 2007,  25 April 2007,   4 July 2008,   26-31 January 2010
*
*   DOCUMENTATION:
*   See Michael Thomas Flanagan's Java library on-line web page:
*   http://www.ee.ucl.ac.uk/~mflanaga/java/RungeKutta.html
*   http://www.ee.ucl.ac.uk/~mflanaga/java/
*
*   Copyright (c) 2002 - 2010
*
*   PERMISSION TO COPY:
*   Permission to use, copy and modify this software and its documentation for
*   NON-COMMERCIAL purposes is granted, without fee, provided that an acknowledgement
*   to the author, Michael Thomas Flanagan at www.ee.ucl.ac.uk/~mflanaga, appears in all copies.
*
*   Dr Michael Thomas Flanagan makes no representations about the suitability
*   or fitness of the software for any or for a particular purpose.
*   Michael Thomas Flanagan shall not be liable for any damages suffered
*   as a result of using, modifying or distributing this software or its derivatives.
*
***************************************************************************************/



import com.sun.org.apache.xpath.internal.functions.Function;

// Class for Runge-Kutta solution of ordinary differential equations
public class RungeKutta{

        private double x0 = Double.NaN;                 // initial value of x
        private double xn = Double.NaN;;                // final value of x
        private double y0 = Double.NaN;                 // initial value of y; single ODE
        private double[] yy0 = null;                    // initial values of y; multiple ODEs
        private int nODE = 0;                           // number of ODEs
        private double step = Double.NaN;               // step size

        private double relTol = 1.0e-5;                 // tolerance multiplicative factor in adaptive step methods
        private double absTol = 1.0e-3;                 // tolerance additive factor to ensure non-zeto tolerance in adaptive step methods
        private int maxIter = -1;                       // maximum iterations allowed in adaptive step methods
        private int nIter = 0;                          // number of iterations taken
        private static int nStepsMultiplier = 1000;     // multiplied by number of steps to give internally calculated
                                                        //   maximum allowed iterations in adaptive step methods

        private static double safetyFactor = 0.9;       // safety factor for Runge Kutta Fehlberg and CashKarp tolerance checks as
                                                        // error estimations are uncertain
        private static double incrementFactor = -0.2;   // factor used in calculating a step size increment in Fehlberg and CashKarp procedures
        private static double decrementFactor = -0.25;  // factor used in calculating a step size decrement in Fehlberg and CashKarp procedures

        public RungeKutta(){
        }

        public void setInitialValueOfX(double x0)
        {
            this.x0 = x0;
            
        }

        public void setFinalValueOfX(double xn){
            this.xn = xn;
        }

        public void setInitialValueOfY(double y0){
            this.setInitialValuesOfY(y0);
        }

        public void setInitialValueOfY(double[] yy0){
            this.setInitialValuesOfY(yy0);
        }

        public void setInitialValuesOfY(double y0){
            this.y0 = y0;
            this.yy0 = new double[1];
            this.yy0[0] = y0;
            this.nODE = 1;
        }

        public void setInitialValuesOfY(double[] yy0){
            this.yy0 = yy0;
            this.nODE = yy0.length;
            if(this.nODE==1)this.y0 = yy0[0];
        }

        public void setStepSize(double step){
            this.step = step;
        }

        public void setToleranceScalingFactor(double relTol){
            this.relTol = relTol;
        }

        public void setToleranceAdditionFactor(double absTol){
            this.absTol = absTol;
        }

        public void setMaximumIterations(int maxIter){
            this.maxIter = maxIter;
        }

        public int getNumberOfIterations(){
            return this.nIter;
        }

        public static void resetNstepsMultiplier(int multiplier){
            RungeKutta.nStepsMultiplier = multiplier;
        }
        // Fourth order Runge-Kutta for a single ordinary differential equation
        // Non-static method

	    public double[] fourthOrder(DerivnFunction g){
            if(Double.isNaN(this.x0))throw new IllegalArgumentException("No initial x value has been entered");
            if(Double.isNaN(this.xn))throw new IllegalArgumentException("No final x value has been entered");
            if(this.yy0==null)throw new IllegalArgumentException("No initial y values have been entered");
            if(Double.isNaN(this.step))throw new IllegalArgumentException("No step size has been entered");

        	double[] k1 =new double[this.nODE];
        	double[] k2 =new double[this.nODE];
        	double[] k3 =new double[this.nODE];
        	double[] k4 =new double[this.nODE];
        	double[] y =new double[this.nODE];
        	double[] yd =new double[this.nODE];
        	double[] dydx =new double[this.nODE];
        	double x = 0.0D;

        	// Calculate nsteps
        	double ns = (this.xn - this.x0)/this.step;
        	ns = Math.rint(ns);
        	int nsteps = (int) ns;
        	this.nIter = nsteps;
        	double stepUsed = (this.xn - this.x0)/ns;

        	// initialise
        	for(int i=0; i<this.nODE; i++)y[i] = this.yy0[i];

            // iteration over allowed steps
	        for(int j=0; j<nsteps; j++){
        	    	x  = this.x0 + j*stepUsed;
            		dydx = g.derivn(x, y);
            		for(int i=0; i<this.nODE; i++)k1[i] = stepUsed*dydx[i];

	            	for(int i=0; i<this.nODE; i++)yd[i] = y[i] + k1[i]/2;
        		    dydx = g.derivn(x + stepUsed/2, yd);
            		for(int i=0; i<this.nODE; i++)k2[i] = stepUsed*dydx[i];

	            	for(int i=0; i<this.nODE; i++)yd[i] = y[i] + k2[i]/2;
        	    	dydx = g.derivn(x + stepUsed/2, yd);
            		for(int i=0; i<this.nODE; i++)k3[i] = stepUsed*dydx[i];

	            	for(int i=0; i<this.nODE; i++)yd[i] = y[i] + k3[i];
        	    	dydx = g.derivn(x + stepUsed, yd);
            		for(int i=0; i<this.nODE; i++)k4[i] = stepUsed*dydx[i];

	            	for(int i=0; i<this.nODE; i++)y[i] += k1[i]/6 + k2[i]/3 + k3[i]/3 + k4[i]/6;

        	}
        	return y;
    	}

    	// Fourth order Runge-Kutta for n ordinary differential equations (ODE)
    	// Static method
	    public static double[] fourthOrder(DerivnFunction g, double x0, double[] y0, double xn, double h){

	        RungeKutta rk = new RungeKutta();
	        rk.setInitialValueOfX(x0);
	        rk.setFinalValueOfX(xn);
	        rk.setInitialValuesOfY(y0);
	        rk.setStepSize(h);

	        return rk.fourthOrder(g);
	    }
}



