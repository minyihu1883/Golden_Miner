package entities;
import java.awt.Graphics;

/**
 * template for creating mines and other items
 * @author joann
 *
 */

public abstract class Items {

	//instance variables, protected st can be accessible by subclasses
	protected int x =0; //x coordinate for the center of the item
	protected int y=0; // y coordinate for the center of the item
	protected int r = 0; // radius of the item
	protected int s = 0;//score of this item
	protected int w = 0; //weight of this item, which impacts the speed
	protected double speed = 0.0; // pulling speed
	protected Graphics g; // graphics object
	
	//constructor
	protected Items(int x, int y, int r,int s,int w, Graphics g) {
		this.x=x;
		this.y=y;
		this.r=r;
		this.s=s;
		this.w=w;
		this.speed = 0.5*r/(w); /////////////////////////////////////////////////////////can change this to modify speed
		}
	
	// methods to be implemented for different item subclasses
		protected abstract void create() ;

	//getters and setters
	/**
	 * get x coordinate
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * set x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * get y coordinate
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * set y coordinate
	
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * get radius
	 * @return r
	 */
	public int getR() {
		return r;
	}
	/**
	 * set radius
	 */
	public void setR(int r) {
		this.r = r;
	}
	/**
	 * get score
	 * @return s
	 */
	public int getS() {
		return s;
	}
	/**
	 * set score
	 */
	public void setS(int s) {
		this.s = s;
	}
	/**
	 * get weight
	 * @return w
	 */
	public double getSpeed() {
		return this.speed;
	}
	/**
	 * set weight
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * set weight
	 */
	public void setG(Graphics g) {
		this.g = g;
	}

	

}
