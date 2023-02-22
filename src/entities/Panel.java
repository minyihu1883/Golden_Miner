package entities;

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



//import main.Game;
import utliz.LoadSave;
/**
 * design the window for the game
 * @author onezero
 *
 */
public class Panel extends JPanel {
//public class Panel extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private Frame frame; // Frame used to listen to key strokes
	private Claw claw; 
	private int score; // Score at beginning of each level
	private ArrayList<Items> items; // Randomly generated Items per level
	private boolean inGame; // If true: a level is in play
	private long startTime; // Time at which a level begins
	private int secondsLeft; // Seconds left in a level at a given time
	private int level; // Current level the user is at
	private int winCondition; // Score necessary for player to beat level
	private JButton button; // Button used to start a level after shipping
	private JButton start; // Button used to start a level at first
	private JButton quit; // Button used to quit the game
	private JButton speedBoostButton; // Button used to purchase speed boost
	private JButton firecrackerButton; // Button used to purchase firecracker
	private JButton valueMultButton; // Button used to purchase value multiplier
	private String resultString; // Tells user the status
	private boolean speedBoost; // If true, the user has bought the speed boost
	private boolean valueMult; // If true, the user has bought the value multiplier
	private boolean firecrackerChosen; // If true, the user has bought the firecracker
	private String speech; // Speech strings
	private double goldChance; // Probability of generating gold on a certain level
	private double diamondChance; // Probability of generating diamond on a certain level
	private double minigoldChance;// Probability of generating mini gold on a certain level
	private double bombChance;// Probability of generating tnt on a certain level
	private double chance; // Random variable determining the probability of generating of each item

	private int inShop;// check if in shop
	//pictures used through LoadSave
	private BufferedImage backgroundImg= LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
	private BufferedImage minerImg=LoadSave.GetSpriteAtlas(LoadSave.PLAYER);
	private BufferedImage backgroundImgShop= LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
	private BufferedImage shopImg= LoadSave.GetSpriteAtlas(LoadSave.SHOP_BG_IMG);
	private BufferedImage minerWire= LoadSave.GetSpriteAtlas(LoadSave.PLAYER_WIRE);
	private BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
	private BufferedImage[] imgs=new BufferedImage[3];
	private BufferedImage flash = LoadSave.GetSpriteAtlas(LoadSave.FLASH_IMG);
	private BufferedImage firecracker = LoadSave.GetSpriteAtlas(LoadSave.FIRE);
	private BufferedImage splash = LoadSave.GetSpriteAtlas(LoadSave.Splash);
	
	//parameters set for showing pictures
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private int statusMinerWidth = (int) (92 * SCALE);
	private int statusMinerHeight = (int) (58 * SCALE);
	private int statusBarX = (int) (280 * SCALE);
	private int statusBarY = (int) (20 * SCALE);

	public static final int B_WIDTH_DEFAULT = 140;
	public static final int B_HEIGHT_DEFAULT = 56;
	public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * 2f);
	public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * 2f);
	
	public boolean isSpeedBoost() {
		return speedBoost;
	}

	public void setSpeedBoost(boolean speedBoost) {
		this.speedBoost = speedBoost;
	}

	public boolean isValueMult() {
		return valueMult;
	}

	public void setValueMult(boolean valueMult) {
		this.valueMult = valueMult;
	}

	public Panel(boolean ig, Frame f) {
		super();

		level = 0;
		inGame = ig;
		frame = f;
		inShop=1;
		this.setSize(frame.getSize());
		items = new ArrayList<Items>();
		resultString = "";
		resetLevel();

		button = new JButton("Play");
		start = new JButton("Press to PLAY");
		quit=new JButton("Press to QUIT");
		speech = "Welcome! Press the down arrow to pick up\nitems. Collect as many as you can in order\nto meet the goal before the time runs out.";
		speedBoostButton = new JButton("Speed Boost");
		valueMultButton = new JButton("Value Multiplier");
		firecrackerButton = new JButton("Firecracker");
	}

	public void setInGame(boolean ig) {
		inGame = ig;
		inShop=0;
		if (inGame)
			startTime = System.currentTimeMillis();
	}

	// Sets and draws the panel based on if the user is currently playing a level
	// Resets game once the user has won all 10 levels
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (level == 10) {
			resultString = "You won all 10 levels!";
			speech="Press the button to play again.";
			button.setText("Play Again");
			restartGame();
		}

		// If playing, draws actual level; if in-between levels or before playing, draws a start screen
		if (inGame) {
			level(g);
		} else {
			if(inShop==0) {
				startScreen(g);
			}
			else {
				startScreen_2(g);
			}
			
		}
	}


	// Draws current score, goal, and level while user is playing a level
	private void drawPlayerAttributes(Graphics g) { 

		int fontSize = 22;
		g.setFont(new Font("Apple LiGothic", Font.BOLD, fontSize));
		g.setColor(new Color(179, 102, 0));
		g.drawString("Score: " + claw.getScore(), getWidth() / 32, getHeight() / 16);
		g.drawString("Goal: " + winCondition, getWidth() / 32, getHeight() / 8);
		g.drawString("Level: " + (level + 1) + "/10", getWidth() / 2 + getWidth() / 4 + getWidth() / 16, getHeight() / 8);

	}

	// Updates/draws remaining time while user is playing a level
	
	private void drawCountdown(Graphics g) { 

		long currentTime = System.currentTimeMillis();
		secondsLeft = (int) ((20+40/(level+1)) - (TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime))); 
		g.drawString("Time: " + secondsLeft, getWidth() / 2 + getWidth() / 4 + getWidth() / 16, getHeight() / 16);
//		g.drawString("Time: " + secondsLeft, getWidth() / 2 + getWidth() / 4 + getWidth() / 16, getHeight() / 16);
//		if(level<2) {
//			secondsLeft = (int) (20 - (TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime))); 
//			g.drawString("Time: " + secondsLeft, getWidth() / 2 + getWidth() / 4 + getWidth() / 16, getHeight() / 16);
//		}
//		else if(level>=2) {
//			secondsLeft = (int) (20 - (TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime))); 
//			g.drawString("Time: " + secondsLeft, getWidth() / 2 + getWidth() / 4 + getWidth() / 16, getHeight() / 16);
//		}
		if (secondsLeft <= 0) {

			inGame = false;
			frame.setInGame(false);

			if (claw.getScore() >= winCondition) {
				level++;
				score = claw.getScore();
				button.setText("Next Level");
				if(score>=500) {
					resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
				}
				else if(score<500){
					resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nSorry you cannot buy.\nEach item takes $500.";
				}
//				resultString = "You made it to the next level!\nYou have $" + score + " to spend";
				speech = "Welcome! Hover over an item for a description of its ability, \nand click an item to purchase. All items are $500.";
				resetLevel();
				
			} else {
				button.setText("Play Again");
				restartGame();
				speech = "Better luck next time.";
				resultString = "GAME OVER!";
			}

		}

	}

	// Draws level graphics
	// Determines if firecracker has been used through Frame
	private void level(Graphics g) { 
//		drawGameBackground(g);
		//To do
//		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		
		g.drawImage(backgroundImg, 0, 0,GAME_WIDTH, GAME_HEIGHT, null);
		g.drawImage(minerImg, statusBarX, statusBarY, statusMinerWidth, statusMinerHeight, null);
		g.drawImage(minerWire, statusBarX, statusBarY+110, (int)(statusMinerWidth*0.9), (int)(statusMinerHeight*0.8), null);
		
		drawPlayerAttributes(g);
		drawCountdown(g);
		claw.setG(g);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setG(g);
			
		}

		
		
		claw.draw(getWidth(), getHeight());
		claw.move(getWidth(), getHeight(), items, speedBoost,g);
//		if (claw.move(getWidth(), getHeight(), items, speedBoost)) {
//			int xpos=items.get(claw.touching(getWidth(), getHeight(),items)).getX();
//			int ypos=items.get(claw.touching(getWidth(), getHeight(),items)).getY();
////			System.out.println("x,y: "+xpos+" "+ypos);
//			if(items.get(claw.touching(getWidth(), getHeight(),items)) instanceof Bomb) {
//				System.out.println("int: "+claw.touching(getWidth(), getHeight(),items));
//				g.drawImage(splash, xpos,ypos,(int)(statusMinerWidth*0.9), (int)(statusMinerHeight*0.8), null);
//			}
//			
////			startTime = -15; // before actual end time
////			claw.setScore(2);
//		}

		for (int i = 0; i < items.size(); i++)
			items.get(i).create();
		
	}

	// the screen for shops
	private void startScreen(Graphics g) {
		
		g.drawImage(shopImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		

		g.setFont(new Font("Arial", Font.BOLD, 100));
		g.setColor(new Color(230, 153, 0));
		g.drawString("Gold Miner", getWidth() / 32, getHeight() / 4);
		g.setColor(new Color(130, 53, 0));
		g.setFont(new Font("Apple LiGothic", Font.BOLD, 40));
		splitString(resultString, getWidth() / 32 + 5, getHeight() / 4 + getHeight() / 16, 50, g);

		button.setSize(300, 100);
		button.setFont(new Font("Apple LiGothic", Font.BOLD, 30));
		button.setForeground(new Color(65, 193, 110));
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setBackground(new Color(45, 104, 40));
		button.setLocation(getWidth() * 5 / 8, getHeight() / 8);


		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				setInGame(true);
				frame.setInGame(true);
				frame.setFirecracker(firecrackerChosen);
				remove(button);
				remove(speedBoostButton);
				remove(valueMultButton);
				remove(firecrackerButton);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		add(button);

		powerUpButtons();
		
		// Establishes win condition
		// Win condition is based off the values of the randomly generated items; always a feasible score to attain
		winCondition = score;
		for (Items e : items) {
			winCondition += 0.4*e.getS();
			System.out.println("1: "+e.getS());
		}
		System.out.println("11: "+winCondition);

		if(winCondition<0)
			winCondition=0;
		claw.setScore(score);

		drawSpeedBoost(g);
		drawValueMult(g);
		drawFirecracker(g);
		
//		System.out.println("score: "+score);
//		System.out.println("winc: "+winCondition);
	}
	
	//load the menu buttons
	private void loadImgs() {
//		imgs = new BufferedImage[3];
//		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
		for (int i = 0; i < imgs.length; i++) {
			if (i!=1) {
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, i * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
			}
		}
	}
	
	
//the screen for the menu
private void startScreen_2(Graphics g) {
		
		g.drawImage(backgroundImgShop, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		loadImgs();
//		for (MenuButton mb : buttons) {
//			mb.draw(g);	
//		}
		for (int i = 0; i < imgs.length; i++) {
			g.drawImage(imgs[i], 692, 300+140*i, B_WIDTH, B_HEIGHT, null);
		}
			
//		System.out.println("lBBB");
		
//		this.setBackground(new Color(112, 81, 38));
		g.setFont(new Font("Arial", Font.BOLD, 100));
		g.setColor(new Color(230, 153, 0));
		g.drawString("Gold Miner", getWidth() / 32, getHeight() / 4);

		g.setFont(new Font("Apple LiGothic", Font.BOLD, 40));
		
		
		splitString(speech, getWidth() / 32 + 5, getHeight() / 4 + getHeight() / 16, 50, g);
//		splitString(resultString, getWidth() / 32 + 5, getHeight() / 4 + getHeight() / 16, 50, g);	
		start.setSize(300, 100);
//		start.setFont(new Font("Apple LiGothic", Font.BOLD, 30));
//		start.setForeground(new Color(117, 193, 110));
//		start.setForeground(null);
		start.setOpaque(false);
		start.setBorderPainted(false);
//		start.setBackground(new Color(45, 104, 40));
//		start.setLocation(getWidth() * 5 / 8, getHeight() / 8);
		start.setLocation(692, 300);
//		start.setLocation(692+300, 300);

		
		start.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				setInGame(true);
				frame.setInGame(true);
				remove(quit);

				remove(start);
				revalidate();

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				resultString = "Welcome! Press the down arrow to pick up\nitems. Collect as many as you can in order\nto meet the goal before the time runs out.";
//				g.drawString(speech, getWidth() / 32, getHeight() / 4);
				g.setFont(new Font("Apple LiGothic", Font.BOLD, 40));
				splitString(resultString, getWidth() / 32 + 5, getHeight() / 4 + getHeight() / 16, 50, g);
//				splitString(speech, getWidth() / 32 + 5, getHeight() / 4 + getHeight() / 16, 50, g);
//				System.out.println("mouseEnter");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			

		});

		
		
		quit.setSize(300, 100);
//		start.setFont(new Font("Apple LiGothic", Font.BOLD, 30));
//		start.setForeground(new Color(117, 193, 110));
		quit.setOpaque(false);
		quit.setBorderPainted(false);
//		start.setBackground(new Color(45, 104, 40));
//		start.setLocation(getWidth() * 5 / 8, getHeight() / 8);
		quit.setLocation(692, 580);

		
		quit.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.exit(0);
				remove(quit);
				remove(start);
				revalidate();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

		});
		

//		powerUpButtons();		
		// Establishes win condition
		// Win condition is based off the values of the randomly generated items; always a feasible score to attain
		winCondition = score;
		for (Items e : items) {

			winCondition += 0.4*e.getS();
			System.out.println("2: "+e.getS());
		}
		System.out.println("22: "+winCondition);
//		winCondition = (winCondition) / 100 * 100; 
		if(winCondition<0)
			winCondition=0;
		claw.setScore(score);
		
//		System.out.println("score: "+score);
//		System.out.println("winc: "+winCondition);
//		System.out.println("score: "+score);
		add(start);
		add(quit);
	}

	// Resets the variables to start a level from the beginning
	private void resetLevel() {
		firecrackerChosen = false;
		claw = new Claw();
		frame.setClaw(claw);
		makeLevel();
		valueMult = false;
		speedBoost = false;

	}

	// Restarts the game from the beginning (called when player looses)
	private void restartGame() {
		score = 0;
		level = 0;
		inGame = false;
		frame.setInGame(false);
		resetLevel();

	}


	// Produces items in each level randomly given specific probabilities
	
	private void makeLevel() {
		items.clear();

		if (level <= 4) {

			goldChance = (double) 20 / 100;
			diamondChance = (double) 35 / 100;
			minigoldChance=(double) 40/100;
			bombChance = (double) (level / 100 +goldChance+ minigoldChance);
//			bombChance = (double) ( 50 / 100 );

		}

		else {

			goldChance = (double) 20 / 100;
			diamondChance = (double) 30 / 100;
			minigoldChance=(double) 40/100;
			bombChance = (double) 80 / 100;
			

		}
		
//		ArrayList<Integer> positionsX= new ArrayList<Integer>(6+level);
//		ArrayList<Integer> positionsY= new ArrayList<Integer>(6+level);
		ArrayList<Integer> positionsX= new ArrayList<Integer>();
		ArrayList<Integer> positionsY= new ArrayList<Integer>();
//
		for (int i = 0; i < 6 + level; i++) {

			chance = Math.random();
//			System.out.println("chance: "+chance);
//			int a=
//			System.out.println((int)(3810.8));
			int posX=(int)(Math.random() * (getWidth() - 200) + 50);
			int posY=(int)(Math.random() * (getHeight() / 2 - 50));
//			System.out.println(getWidth() - 200);
//			System.out.println("posX: "+posX);
//			while(positionsX.contains(posX)) {
//				posX=(int)(Math.random() * (getWidth() - 200) + 50);
//			}
//			while(positionsX.contains(posX)) {
//				if(items !=null) {
//					for (Items e: items) {
//						if(posX<e.getX()+e.getR()*2 && posX >e.getX()-e.getR()*2) {
//							posX=(int)(Math.random() * (getWidth() - 200) + 50);
//						}
//					}
//				}
//			}
//			positionsX.add(posX);
			if(items !=null) {
				for (Items e: items) {
					if(posX<e.getX()+e.getR()*2 && posX >e.getX()-e.getR()*2) {
						posX=(int)(Math.random() * (getWidth() - 200) + 50);
					}
					if(posY<e.getY()+e.getR()*2 && posY >e.getY()-e.getR()*2) {
						posY=(int)(Math.random() * (getWidth() - 200) + 50);
					}
				}
			}
//			while(positionsY.contains(posY)) {
//				posY=(int)(Math.random() * (getWidth() - 200) + 50);
//			}
//			
//			while(positionsY.contains(posY)) {
//				posY=(int)(Math.random() * (getHeight() / 2 - 50));
//				for (Items e: items) {
//					if(posY<e.getY()+e.getR()*2 && posY >e.getY()-e.getR()*2) {
//						posY=(int)(Math.random() * (getWidth() - 200) + 50);
//					}
//				}
//			}
//			positionsY.add(posY);
//			System.out.println("posX: "+posX);
//			System.out.println("ArrlistX: "+positionsX);
			if (chance < goldChance) {

				items.add(new Gold((int) (posX),(int) (posY + getHeight() / 2)));

			}

			else if (chance > goldChance && chance < diamondChance) {

				items.add(new Diamond((int) (posX),(int) (posY + getHeight() / 2)));

			}
			
			else if (chance > diamondChance && chance < minigoldChance) {

				items.add(new MiniGold((int) (posX),(int) (posY + getHeight() / 2)));

			}
			
		
			else if (chance > minigoldChance && chance < bombChance) {

				items.add(new Bomb((int) (posX),(int) (posY + getHeight() / 2)));

			}

			else {

				items.add(new Rock((int) (posX),(int) (posY + getHeight() / 2)));
			}
			
//			if (chance < goldChance) {
//
//				items.add(new Gold((int) (Math.random() * (getWidth() - 200) + 50),(int) (Math.random() * (getHeight() / 2 - 50)) + getHeight() / 2));
//
//			}
//
//			else if (chance > goldChance && chance < diamondChance) {
//
//				items.add(new Diamond((int) (Math.random() * (getWidth() - 200) + 50),
//						(int) (Math.random() * (getHeight() / 2 - 50)) + getHeight() / 2));
//
//			}
//			
//			else if (chance > diamondChance && chance < minigoldChance) {
//
//				items.add(new MiniGold((int) (Math.random() * (getWidth() - 200) + 50),
//						(int) (Math.random() * (getHeight() / 2 - 50)) + getHeight() / 2));
//
//			}
//			
//		
//			else if (chance > minigoldChance && chance < bombChance) {
//
//				items.add(new Bomb((int) (Math.random() * (getWidth() - 200) + 50),
//						(int) ((Math.random() * (getHeight() / 2 - 50)) + getHeight() / 2)));
//
//			}
//
//			else {
//
//				items.add(new Rock((int) (Math.random() * (getWidth() - 100) + 50),
//						(int) (Math.random() * (getHeight() / 2 - 50)) + getHeight() / 2));
//
//			}

		}
//		System.out.println("ArrlistX: "+positionsX);
//		System.out.println("ArrlistY: "+positionsY);

	}

	// Draws power up buttons when user has enough money and hasn't already purchased boost
	// When user clicks a button, the power up is applied for the duration of the next level and money is subtracted from score
	// When the user hovers the mouse over a button, the user sees an explanation of the power up
	private void powerUpButtons() {

		speedBoostButton.setSize(170, 40);
		speedBoostButton.setFont(new Font("Arial", Font.BOLD, 15));
		speedBoostButton.setForeground(new Color(226, 180, 111));
		speedBoostButton.setOpaque(true);
		speedBoostButton.setBorderPainted(false);
		speedBoostButton.setBackground(new Color(150, 119, 73));
		speedBoostButton.setLocation(getWidth() / 16 + 10, getHeight() * 7 / 8 + 5);

		valueMultButton.setSize(170, 40);
		valueMultButton.setFont(new Font("Arial", Font.BOLD, 15));
		valueMultButton.setForeground(new Color(226, 180, 111));
		valueMultButton.setOpaque(true);
		valueMultButton.setBorderPainted(false);
		valueMultButton.setBackground(new Color(150, 119, 73));
		valueMultButton.setLocation(getWidth() / 16 + 190, getHeight() * 7 / 8 + 5);

		firecrackerButton.setSize(170, 40);
		firecrackerButton.setFont(new Font("Arial", Font.BOLD, 15));
		firecrackerButton.setForeground(new Color(226, 180, 111));
		firecrackerButton.setOpaque(true);
		firecrackerButton.setBorderPainted(false);
		firecrackerButton.setBackground(new Color(150, 119, 73));
		firecrackerButton.setLocation(getWidth() / 16 + 370, getHeight() * 7 / 8 + 5);

		speedBoostButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (speedBoost == false && score >= 500) {
					score -= 500;
					speedBoost = true;
					remove(speedBoostButton);
//					speech = "Welcome! Hover over an item for a description of its ability, \nand click an item to purchase. All items are $500.";
					resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
					revalidate();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				resultString = "This Speed Boost makes the claw move faster\nwhen it is grabbing items.";
			}

			@Override
			public void mouseExited(MouseEvent e) {
				resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
			}

		});

		if (score >= 500 && speedBoost == false) {
			add(speedBoostButton);
		}else {
			remove(speedBoostButton);
		}
		revalidate();

		valueMultButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (valueMult == false && score >= 500) {
					score -= 500;
					valueMult = true;
					remove(valueMultButton);
					
					if (valueMult)
						for (Items i : items)
							i.setS((int) (i.getS() * 1.5));
					
//					speech = "Welcome! Hover over an item for a description of its ability, \nand click an item to purchase. All items are $500.";
					resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
					revalidate();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				resultString = "This Value Multiplier makes every item worth 1.5\ntimes its original value.";
			}

			@Override
			public void mouseExited(MouseEvent e) {
				resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
			}

		});

		if (score >=500 && valueMult == false) {
			add(valueMultButton);
		}else
			remove(valueMultButton);
		
		revalidate();

		firecrackerButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (firecrackerChosen == false && score >= 500) {
					score -= 500;
					firecrackerChosen = true;
					remove(firecrackerButton);
//					speech = "Welcome! Hover over an item for a description of its ability, \nand click an item to purchase. All items are $500.";
					resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
					revalidate();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				resultString = "This Firecracker allows you to drop items\nyou have picked up. Use the up arrow to\nactivate your firecracker in game.";
			}

			@Override
			public void mouseExited(MouseEvent e) {
				resultString = "You made it to the next level!\nYou have $" + score + " to spend.\nEach item takes $500.";
			}

		});

		if (score >= 500 && firecrackerChosen == false) {
			add(firecrackerButton);
		} else {
			remove(firecrackerButton);
		}
		
		revalidate();
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	// Draws icon for speed boost
	private void drawSpeedBoost(Graphics g) {
		g.drawImage(flash, getWidth() / 16 + 10, getHeight() * 7 / 8 - 125, (int)( 0.5*B_WIDTH), B_HEIGHT, null);
	}

	// Draws icon for value multiplier
	private void drawValueMult(Graphics g) {
		g.setColor(new Color(31, 45, 145));
		g.setFont(new Font("Apple LiGothic", Font.BOLD, 70));
		g.drawString("x1.5", getWidth() / 16 + 200, getHeight() * 7 / 8 - 40);
	}

	// Draws icon for firecracker
	private void drawFirecracker(Graphics g) {
		g.drawImage(firecracker, getWidth() / 16 + 370, getHeight() * 7 / 8 - 125, (int)( 0.5*B_WIDTH), B_HEIGHT, null);
		
	}

	// Draws a string, with a new line for every "\n"
	private void splitString(String text, int x, int y, int increment, Graphics g) {
		for (String e : text.split("\n"))
			g.drawString(e, x, y += increment);
//			System.out.println("split");
	}
}
