
package entities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JPanel;

import utliz.LoadSave;
/**
 * the claw for the game
 * @author onezero
 *
 */
public class Claw{
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	private double xCoordinate; // x and y coordinate of Claw center
	private double yCoordinate; 
	private boolean isRotating; // turn true if rotating
	private double angle; // Current angle of Claw w.r.t. horizon
	private Graphics g; // Declare graphics object
	private double angleChange; // Angle change rate (+/- determines rotation direction)
	private boolean grabSuccess; // return true is an item is grabbed
	private boolean isBack; // return true if the claw is back at origin
	private int score; // Cumulative score
	private boolean powerSpeed; // increases item speed when this is true
	private boolean firecracker; // discards the item when it is true
	private BufferedImage splash = LoadSave.GetSpriteAtlas(LoadSave.Splash);
	private int statusMinerWidth = (int) (92 * 2f);
	private int statusMinerHeight = (int) (58 * 2f);
	
	public Claw(Graphics g) {
		xCoordinate = screenSize.width/4;
		yCoordinate = 300;
		isRotating = true;
		angle = 200;
		this.g = g;
		grabSuccess = false;
		isBack = false;
		firecracker = false;
		angleChange = 0;
		}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public boolean getIsRotating() {
		return isRotating;
	}

	public void setIsRotating(boolean isRotating) {
		this.isRotating = isRotating;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// Returns index of object that has been touched
	public int touching(int w, int h, ArrayList<Items> items) { 
		
		if(xCoordinate < 20 || xCoordinate > w-20 || yCoordinate > h-20)
			grabSuccess = true;
		
		for(int i = 0; i < items.size(); i++) {
			int xDif = (int) (xCoordinate-items.get(i).getX());
			int yDif = (int) (yCoordinate-items.get(i).getY());
			double distance = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
			
//			System.out.println("dist: "+distance+" R: "+items.get(i).getR());
			if(distance < items.get(i).getR()) {
				grabSuccess = true;
				grab(items.get(i));
				return i;
			}
				
		}
		
		return -1;
		
	}
	
private int touching_2(int width, int height, ArrayList<Items> items) { 
		
		if(xCoordinate < 20 || xCoordinate > width-20 || yCoordinate > height-20)
			grabSuccess = true;
		
		for(int i = 0; i < items.size(); i++) {
			double xDif = (int) (xCoordinate-items.get(i).getX());
			double yDif = (int) (yCoordinate-items.get(i).getY());
			double distance = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
			
			if(distance < items.get(i).getR()) {
				grabSuccess = true;
				grab(items.get(i));
				return i;
			}
				
		}
		
		return -1;
		
	}
	/**
	 * Sets the x and y coordinate of the item to be the same as the claw so that the item moves back with the claw
	 * @param i is the item to claw back
	 */
	private void grab(Items i) {

		i.setX((int)xCoordinate);
		i.setY((int)yCoordinate);
	}
	
	/**
	 * Using cos and sin to ensure the claw shoots out at this angle
	 */
	private void shootOut() {
		
		int change;
		if(powerSpeed)
			change=3;
			
		else
			change=2;
			xCoordinate += Math.cos(angle*Math.PI/180)*change; 
			yCoordinate += Math.sin(angle*Math.PI/180)*(-change);
		
	}
	/**
	 *  Claw return to the origin in a straight line with speed dictating the change in coordinates and start rotating
	 * @param w
	 * @param h
	 * @param speed
	 */

	private void retract(int w, int h, double speed) { 
		
		if(powerSpeed) {
			speed=speed*1.2; //increase the speed of the item
		}
		xCoordinate -= Math.cos(angle*Math.PI/180)*speed;
		yCoordinate -= Math.sin(angle*Math.PI/180)*(-speed);
		
		double xGap = (int) (xCoordinate-w/2);//gap from x,y coordinate to those of the origin
		double yGap = (int) (yCoordinate-h/4);
		double d = Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2));//get the distance
		
		if (d < 100) {
			isBack = true;}
	
	}
	
	/**
	 * draw the claw
	 * @param w
	 * @param h
	 */
	public void draw(int w, int h) {
		
		int x = (int) xCoordinate; 
		int y = (int) yCoordinate;
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(5));
		
		g.setColor(new Color(82, 95, 123));
		g2.drawLine(w/2, h/4, x, y);
		
		int lowerClawPtX = x+(int)(20*Math.cos((angle-90)*Math.PI/180));
		int upperClawPtX = x-(int)(20*Math.cos((angle-90)*Math.PI/180));
		int lowerClawPtY = y-(int)(20*Math.sin((angle-90)*Math.PI/180));
		int upperClawPtY = y+(int)(20*Math.sin((angle-90)*Math.PI/180));
		
		g.drawLine(lowerClawPtX,lowerClawPtY,upperClawPtX,upperClawPtY);
		g.drawLine(lowerClawPtX, lowerClawPtY, lowerClawPtX+(int)(20*Math.cos(angle*Math.PI/180)), lowerClawPtY-(int)(20*Math.sin(angle*Math.PI/180)));
		g.drawLine(upperClawPtX, upperClawPtY, upperClawPtX+(int)(20*Math.cos(angle*Math.PI/180)), upperClawPtY-(int)(20*Math.sin(angle*Math.PI/180)));
		
		g.setColor(new Color(40, 42, 53));
		g.fillOval(x-10, y-10, 20, 20);
		
	}
	
	
	/**
	 * change the coordinate of the claw when an angle is set by the down arrow
	 * @param w
	 * @param h
	 */
	private void changeCoordinates(int w, int h) {
		xCoordinate = w/2 + (100*Math.cos(angle*Math.PI/180));
		yCoordinate = h/4 - (100*Math.sin(angle*Math.PI/180));
	}
	
	// General Claw movement method
	// Returns True if hit TNT
	public boolean move(int w, int h, ArrayList<Items> items, boolean ps,Graphics g) { 
		
		powerSpeed=ps;
		try {
			Thread.sleep(10);
		}
		
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		if(isRotating) {
			rotate(w,h);
			
		}else {
			int touchIndex = touching(w,h,items);
			if(!grabSuccess)
				shootOut();
				
			else
				
				if(!isBack) {
					if(firecracker&&touchIndex>-1) {
						items.remove(touchIndex);
						touchIndex=-1;
						firecracker=false;
					}
					double speed;
					if(touchIndex>=0) {
						speed = items.get(touchIndex).getSpeed();//.......setW???
					}else
						speed = 3;
					if(powerSpeed)
						speed*=2;
					retract(w,h,speed);
					
				}else {
			
					if(touchIndex>-1) {
						if(items.get(touchIndex) instanceof Bomb) {
							g.drawImage(splash, items.get(touchIndex).getX(),items.get(touchIndex).getY(),(int)(statusMinerWidth*0.9), (int)(statusMinerHeight*0.8), null);
							score += items.remove(touchIndex).getS();
							resetAngleRotation(w,h);
							return true;
						}else {	
							score += items.remove(touchIndex).getS();
						}
					}
					resetAngleRotation(w,h);
				}
					
		}
		return false;
	}
	
	public boolean isFirecracker() {
		return firecracker;
	}

	public void setFirecracker(boolean firecracker) {
		this.firecracker = firecracker;
	}

	public boolean grabSuccess() {
		return grabSuccess;
	}

	public void setGrabbing(boolean grabSuccess) {
		this.grabSuccess = grabSuccess;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	// Default constructor
	public Claw() {
		super();
		xCoordinate = 300;
		yCoordinate = 300;
		isRotating = true;
		angle = 200;
		
	}
	
	/**
	 * Rotation
	 * @param w
	 * @param h
	 */
	
	private void rotate(int w, int h) {

		if(angle <= 200)// set the max and min degrees for rotation
			angleChange = 0.75;
		else if(angle >= 340)
			angleChange = -0.75;
		
		angle += angleChange;
		changeCoordinates(w,h);
	}
	
	
	/**
	 * when the claw returns to the origin, remove the item, and starting to rotate 
	 * @param w
	 * @param h
	 */
	private void resetAngleRotation(int w, int h) {
		
			changeCoordinates(w,h);
			isRotating = true;
			grabSuccess = false;
			isBack = false;
		
	}

	
	
}

