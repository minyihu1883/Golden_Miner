package entities;
import java.awt.Color;

/**
 * create a rock class inheriting from gold
 * @author joann
 *
 */
public class Rock extends Items {
	
    protected static int ROCKR = Gold.R-2;//////////////////////////////radius is the same for all mini golds, can change this value to adjust
	protected static int ROCKS = Gold.S/4;//////////////////////////////score the same..., can change...
	protected static int ROCKW = Gold.W+21;//////////////////////////////weight the same..., can change...
	
	/**
	 * constructor by calling super
	 * @param x
	 * @param y
	 */
	public Rock(int x, int y) {
		super(x, y, ROCKR, ROCKS, ROCKW, null);
	}
	/**
	 * draw out the item
	 */
	@Override
	protected void create() {
		// similar to gold
		// draw out the item
		g.setColor(new Color(102, 51, 0));
		int [] xCoordinates = {x+r, x+r*6/7, x+r*1/7, x-r*5/6,  x-r*6/7,  x-r*1/3}; //x coordinates of the polygon
		int [] yCoordinates = {y, y-r*1/2,  y-r*6/7,  y-r*1/2,  y+r*1/3,  y+r*6/7}; //y coordinates of the polygon
		g.fillPolygon(xCoordinates, yCoordinates, 6);
		
		//draw the dark shades
		g.setColor(new Color(26, 13, 0));
		int [] xDarkCoordinates = {x+r*6/7, x+r*1/7, x-r*5/6, x-r*6/7, x-r*1/3,  x+r*1/3 }; //x coordinates of the polygon
		int [] yDarkCoordinates = {y-r*1/2, y-r*6/7, y-r*1/2, y+r*1/3, y-r*1/3,  y-r*2/3}; //y coordinates of the polygon
		g.fillPolygon(xDarkCoordinates, yDarkCoordinates, 6);
		
	}
	
	

}
