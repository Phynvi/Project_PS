package server.game.content.skills.core;

import server.game.content.randomevents.EventHandler;
import server.game.content.randomevents.Zombie;
import server.game.content.skills.SkillHandler;
import server.game.players.Client;

/**
* @author Andrew
*/

public class Prayer extends SkillHandler {
	
	public static final int[] BURY_BONE = {526, 2859, 528, 3179, 3181, 3179, 3181, 530, 532, 10976, 10977, 3181, 3182, 4812, 3123, 534, 6812, 536, 4830, 4832, 6729, 4834};
	public static final int[] BONE_EXPERIENCE = {5, 5, 5, 5, 5, 5, 15, 15, 15, 18, 20, 23, 25, 30, 50, 72, 84, 96, 125, 140};
	
	public static boolean buryBone(Client c, int itemId) {
		for (int i = 0; i < BURY_BONE.length; i++) {
			if (itemId == BURY_BONE[i]) {
				if (canDoAction(800)) {
					if (!PRAYER) {
						c.sendMessage("This skill is currently disabled.");
						return false;
					}
					c.getItems().deleteItem(BURY_BONE[i], 1);
					c.getPA().addSkillXP(BONE_EXPERIENCE[i], 5);
					c.startAnimation(827);
					c.sendMessage("You bury the " + c.getItems().getItemName(BURY_BONE[i]).toLowerCase() + ".");
					EventHandler.randomEvents(c);
					Zombie.spawnZombie(c);
				}
				return true;
			}
		}
	return false;
	}
}

