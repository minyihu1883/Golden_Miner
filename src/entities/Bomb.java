package entities;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import utliz.LoadSave;
/**
 * bomb that extends item
 * @author joann
 *
 */
public class Bomb extends Items {
	//static variable common to all typical size golds
		protected static int R = 70/2;//////////////////////////////radius is the same for all golds, can change this value to adjust
		protected static int S = 2;//////////////////////////////score the same..., can change...
		protected static int W = 5;//////////////////////////////weight the same..., can change...
//		protected static Image image = Toolkit.getDefaultToolkit().getImage("TNT"); 
		private BufferedImage Bomb = LoadSave.GetSpriteAtlas(LoadSave.Bomb);
			
	/**
	 * constructor making use of super
	 * @param x coord
	 * @param y coord
	 */

	protected Bomb(int x, int y) {
		super(x, y, R, S, W, null);
//		System.out.println(2222);
		
	}

	@Override
	protected void create() {
//		System.out.println(111111);
//		g.drawImage(image, x, y, null);
		g.drawImage(Bomb, x-70/2, y-70/2,70,70, null);
		
	}

}
