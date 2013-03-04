package org.rs2server.rs2.model.content;

import org.rs2server.rs2.action.impl.ItemInteractAction;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;

/**
 * 
 * @author Sneakyhearts
 * 
 */
public class ToyHorsey {

	/**
	 * Used to access this function.
	 * 
	 * @return
	 */
	public static ToyHorsey getToyHorsey() {
		return new ToyHorsey();
	}

	protected final int animation = 921;

	/**
	 * The messages that were shouted in classical RuneScape. - Josh
	 */
	protected final String shoutMessages[] = {
			"Come on Dobbin, we can win the race!", "Hi-ho Silver, and away!",
			"Giddy-up horsey!" };

	public int getAnimation() {
		return animation;
	}

	public String[] getShoutMessages() {
		return shoutMessages;
	}

	/**
	 * Initialise the 'Toy Horsey' preformance.
	 * 
	 * @param mob
	 * @param itemId
	 */
	public void init(Mob mob, Item item) {
		ItemInteractAction toyHorseyAction = new ItemInteractAction(mob, item);
		toyHorseyAction.execute();
	}

}
