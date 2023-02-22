package utliz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
/**
 * class to read all the related  pics
 * @author onezero
 *
 */
public class LoadSave {

	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";


	public static final String MENU_BACKGROUND_IMG = "background.jpg";
	public static final String PLAYING_BG_IMG = "ground.jpg";
	public static final String SHOP_BG_IMG = "shop.jpg";

	

//	public static final String PLAYER = "player.png";
	public static final String PLAYER = "miner.jpg";
	public static final String PLAYER_WIRE = "wirerope.png";

	public static final String FLASH_IMG = "flashing.png";
	public static final String FIRE = "firecracker.png";
	public static final String Bomb = "TNT.png";
	public static final String Splash = "splash.png";
	public static final String Claw = "claw.png";
	
	

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
//

}