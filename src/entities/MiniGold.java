package entities;
/**
 * create a minigold class inheriting from gold
 * @author Qi
 *
 */
public class MiniGold extends Gold {
	protected static int MINIR = Gold.R/2;//////////////////////////////radius is the same for all mini golds, can change this value to adjust
	protected static int MINIS = Gold.S/2;//////////////////////////////score the same..., can change...
	protected static int MINIW = Gold.W/2;//////////////////////////////weight the same..., can change...
	
    /**
     * constructor that calls super
     * @param x
     * @param y
     */
	public MiniGold(int x, int y) {
		super(x, y, MINIR, MINIS, MINIW);
		
	}

}
