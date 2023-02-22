package main;

import javax.swing.*;

import javax.swing.JFrame;

import entities.Frame;
import entities.Panel;
/**
 * Mainclass for CIT-591 final project Miner Gold
 * @author onezero
 *
 */
public class MainClass {

	public static void main(String[] args) {
//		new Game();
		

		Frame frame = new Frame(false); // Creates starting screen
		frame.setTitle("Gold Miner");
		frame.setSize(1300, 900); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	
		Panel panel = new Panel(false,frame); // Creates panel with frame
		frame.getContentPane().add(panel); 
		frame.setVisible(true);
		
		boolean play = true;
		
		while(play) {
			frame.repaint(); // Continuously updates/draws game
		}
		
	}

}
