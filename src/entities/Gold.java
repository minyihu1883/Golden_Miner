package entities;
import java.awt.Color;

/**
 * gold, implements item super class
 * @author joann
 *
 */
public class Gold extends Items {
	//static variable common to all typical size golds
	protected static int R = 60;//////////////////////////////radius is the same for all golds, can change this value to adjust
	protected static int S = 1000;//////////////////////////////score the same..., can change...
	protected static int W = 4;//////////////////////////////weight the same..., can change...
	
	
	/**
	 * constructor making use of super 
	 * @param x
	 * @param y
	 */
	public Gold(int x, int y) {
		super(x, y, R, S, W, null);
		
	}
	/**
	 * constructor to better serve the minigold class
	 * @param x
	 * @param y
	 * @param r
	 */
	public Gold(int x, int y, int r, int s, int w) {
		super(x, y, r, s, w, null);
		
	}
    
    /**
     * draw out the item
     */
	@Override
	protected void create() {
//		System.out.println("gold");
		// draw out the item
		g.setColor(new Color(255, 217, 0));
		int [] xCoordinates = {x+r, x+r*6/7, x+r*1/7, x-r*5/6,  x-r*6/7,  x-r*1/3}; //x coordinates of the polygon
		int [] yCoordinates = {y, y-r*1/2,  y-r*6/7,  y-r*1/2,  y+r*1/3,  y+r*6/7}; //y coordinates of the polygon
		g.fillPolygon(xCoordinates, yCoordinates, 6);
		
		//draw the dark shades
		g.setColor(new Color(204, 153, 0));
		int [] xDarkCoordinates = {x+r*6/7, x+r*1/7, x-r*5/6, x-r*6/7, x-r*1/3,  x+r*1/3 }; //x coordinates of the polygon
		int [] yDarkCoordinates = {y-r*1/2, y-r*6/7, y-r*1/2, y+r*1/3, y-r*1/3,  y-r*2/3}; //y coordinates of the polygon
		g.fillPolygon(xDarkCoordinates, yDarkCoordinates, 6);
		
	}

}
