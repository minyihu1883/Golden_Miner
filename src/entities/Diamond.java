package entities;
import java.awt.Color;

/**
 * diamonds extends items
 * @author joann
 *
 */
public class Diamond extends Items {
	//static variable common to all typical size golds
		protected static int R = Gold.R/3;//////////////////////////////radius is the same for all golds, can change this value to adjust
		protected static int S = Gold.S+200;//////////////////////////////score the same..., can change...
		protected static int W = Gold.W-1;//////////////////////////////weight the same..., can change...	
    
	/**
	 * construct diamond by calling super 
	 * @param x
	 * @param y
	 */
		
	protected Diamond(int x, int y) {
		super(x, y, R, S, W, null);
		// TODO Auto-generated constructor stub
	}
	
    /**
     * draw the diamond
     */
	@Override
	protected void create() {
		g.setColor(new Color(154, 188, 209));
		int[] xOuterPts = {x,x-r,x-r*3/4,x+r*3/4,x+r};
		int[] yOuterPts = {y+r,y,y-r*1/5,y-r/5,y};
		g.fillPolygon(xOuterPts, yOuterPts, 5);
		
		g.setColor(new Color(213, 225, 249));
		int[] xLightPts = {x, x+r, x+r*3/4};
		int[] yLightPts = {y, y, y-r/5};
		g.fillPolygon(xLightPts, yLightPts, 3);

		g.setColor(new Color(120, 150, 180));
		int[] xShadePts = {x,x-r,x-r*3/4};
		int[] yShadePts = {y,y,y-r*1/5};
		g.fillPolygon(xShadePts, yShadePts, 3);
	
	}
	

}