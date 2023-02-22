package entities;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
/**
 * with the help of JFrame to control the claw
 * @author onezero
 *
 */
public class Frame extends JFrame implements KeyListener{
//public class Frame extends JPanel implements KeyListener{
	
	private Claw claw; 
	private boolean inGame; 
	private boolean firecracker;
	
	public boolean isFirecracker() {
		return firecracker;
	}

	public void setFirecracker(boolean firecracker) {
		this.firecracker = firecracker;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Frame(boolean ig) throws HeadlessException {
		super();
		setFocusable(true);
		addKeyListener(this);
		setVisible(true);
		inGame = ig;
	}
	
	public void setClaw(Claw c) {
		claw = c;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {		
		if (inGame) {
			
			// If down arrow pressed, Claw shoots out
			if(e.getKeyCode()==KeyEvent.VK_DOWN) { 
					claw.setIsRotating(false);
			}
		
			// If up arrow is pressed while user has dynamite, item touching claw is discarded
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				if(firecracker) {
					if(!claw.getIsRotating() && claw.grabSuccess()) {
						claw.setFirecracker(true);
						
					}
				}
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}

	
	

}
