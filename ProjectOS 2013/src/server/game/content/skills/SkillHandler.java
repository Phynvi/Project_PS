package server.game.content.skills;

import server.game.players.Client;

/**
* @author Andrew
*/

public class SkillHandler {
	
	public static Client c;
	
	/*/
	 * Skill Constants
	 */
	
	public static boolean FISHING = true;
	public static boolean AGILITY = true;
	public static boolean COOKING = true;
	public static boolean FIREMAKING = true;
	public static boolean HERBLORE = true;
	public static boolean MINING = true;
	public static boolean RUNECRAFTING = true;
	public static boolean WOODCUTTING = true;
	public static boolean THIEVING = true;
	public static boolean PRAYER = true;
	public static boolean CRAFTING = true;
	public static boolean FLETCHING = true;
	public static boolean MAGIC = true;
	public static boolean FARMING = true; //unfinished
	public static boolean SLAYER = true; //unfinished
	public static boolean SMITHING = true;
	public static final int SKILLING_EXP = 28;
	public static final int SLAYER_EXP = 20;
	public static final int WOODCUTTING_EXPERIENCE = SKILLING_EXP;
	public static final int MINING_EXPERIENCE = SKILLING_EXP;
	public static final int SMITHING_EXPERIENCE = SKILLING_EXP;
	public static final int FARMING_EXPERIENCE = SKILLING_EXP;
	public static final int FIREMAKING_EXPERIENCE = SKILLING_EXP;
	public static final int HERBLORE_EXPERIENCE = SKILLING_EXP;
	public static final int FISHING_EXPERIENCE = SKILLING_EXP;
	public static final int AGILITY_EXPERIENCE = SKILLING_EXP;
	public static final int PRAYER_EXPERIENCE = SKILLING_EXP;
	public static final int RUNECRAFTING_EXPERIENCE = SKILLING_EXP;
	public static final int CRAFTING_EXPERIENCE = SKILLING_EXP;
	public static final int THIEVING_EXPERIENCE = SKILLING_EXP;
	public static final int SLAYER_EXPERIENCE = SLAYER_EXP;
	public static final int COOKING_EXPERIENCE = SKILLING_EXP;
	public static final int FLETCHING_EXPERIENCE = SKILLING_EXP;
	public static boolean view190 = true;
	private static long lastAction = 0;
	public static boolean[] isSkilling = new boolean[25];
	public static long lastSkillingAction;
	
	public static boolean canDoAction(int timer) {
		if (System.currentTimeMillis() >= lastAction) {
			lastAction = System.currentTimeMillis() + timer;
			return true;
		}
		return false;
	}
	
	public static boolean membersOnly() {
		if (c.membership == false) {
			c.sendMessage("This is a members only skill."); 
			return false;
		}
		return true;
	}
	
	public static boolean noInventorySpace(Client c, String skill) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You don't have enough inventory space to continue "
					+ skill + "!");
			return false;
		}
		return true;
	}
	
	public static boolean playerHasItem(Client c, String itemName, String skill, int itemID) {
		if (!c.getItems().playerHasItem(itemID, 1)) {
			c.sendMessage("You dont have any " + itemName + " to continue "+skill+"!");
			c.getDH().sendStatement("You dont have any " + itemID + " to continue "+skill+"!");
			return false;
		}
		return true;
	}

	public static void resetPlayerSkillVariables(Client c) {
		for(int i = 0; i < 20; i++) {
			if(c.playerSkilling[i]) {
				for(int l = 0; l < 15; l++) {
					c.playerSkillProp[i][l] = -1;
				}
			}
		}
	}
	
	public static boolean hasRequiredLevel(final Client c, int id, int lvlReq,
			String skill, String event) {
		if (c.playerLevel[id] < lvlReq) {
			c.sendMessage("You don't have a high enough " + skill + " level to " + event + ".");
			return false;
		}
		return true;
	}
	
	public static void deleteTime(Client c) {
		c.doAmount--;
	}

}