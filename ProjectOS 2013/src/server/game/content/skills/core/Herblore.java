package server.game.content.skills.core;

import server.Server;
import server.game.content.randomevents.EventHandler;
import server.game.content.skills.SkillHandler;
import server.game.players.Client;
import server.task.Task;

public class Herblore extends SkillHandler {

	private static final int[][] CLEANING_HERB = { { 199, 249, 1, 100 },
			{ 201, 251, 5, 110 }, { 203, 253, 11, 120 }, { 205, 255, 20, 150 },
			{ 207, 257, 25, 200 }, { 209, 259, 30, 300 },
			{ 211, 261, 40, 400 }, { 213, 263, 54, 450 },
			{ 215, 265, 65, 600 }, { 217, 267, 70, 700 },
			{ 219, 269, 75, 800 }, { 3051, 3000, 59, 500 },
			{ 3049, 2998, 30, 310 }, { 2485, 2481, 67, 650 }, };

	private static final int[][] ITEM_ON_VIAL = { { 249, 91, 1 },
			{ 251, 93, 5 }, { 253, 95, 12 }, { 255, 97, 22 }, { 257, 99, 30 },
			{ 259, 101, 34 }, { 261, 103, 45 }, { 263, 105, 55 },
			{ 265, 107, 66 }, { 267, 109, 72 }, { 269, 111, 75 },
			{ 2481, 2483, 67 }, { 3000, 3004, 59 }, { 2998, 3002, 30 }, };

	private static final int[][] ITEM_ON_ITEM = { { 91, 221, 121, 1, 25 },
			{ 93, 235, 175, 5, 38 }, { 95, 225, 115, 12, 50 },
			{ 97, 223, 127, 22, 63 }, { 99, 239, 133, 30, 75 },
			{ 97, 9736, 9741, 36, 84 }, { 99, 231, 139, 38, 88 },
			{ 101, 221, 145, 45, 100 }, { 101, 235, 181, 48, 106 },
			{ 103, 231, 151, 50, 112 }, { 105, 225, 157, 55, 125 },
			{ 105, 241, 5940, 60, 137 }, { 3004, 223, 3026, 63, 142 },
			{ 107, 239, 163, 66, 150 }, { 2483, 241, 2454, 69, 158 },
			{ 109, 245, 169, 72, 163 }, { 2483, 3138, 3042, 76, 173 },
			{ 111, 247, 189, 78, 175 }, { 3002, 6693, 6687, 81, 180 },
			{ 157, 267, 15313, 89, 230 }, { 145, 261, 15309, 88, 220 },
			{ 163, 2481, 15317, 90, 240 }, { 3042, 9594, 15321, 91, 250 },
			{ 169, 267, 15325, 92, 260 }, { 139, 257, 15329, 94, 270 }, };

	private static boolean view190;

	public static void addTime(final Client c) {
		c.doAmount--;
	}

	public static boolean cleanTheHerb(final Client c, final int itemId) {
		for (final int[] element : CLEANING_HERB) {
			if (itemId == element[0]) {
				if (c.playerLevel[c.playerHerblore] < element[2]) {
					if (!HERBLORE) {
						c.sendMessage("This skill is currently disabled.");
						return false;
					}
					EventHandler.randomEvents(c);
					//membersOnly();
					c.sendMessage("You haven't got high enough Herblore level to clean this herb!");
					c.sendMessage("You need the Herblore level of "
							+ element[2] + " to clean this herb.");
					c.sendMessage(
							"You need the herblore level of " + element[2]
									+ " to clean this herb.");
					resetHerblore(c);
					return false;
				}
				c.getPA().addSkillXP(element[3], c.playerHerblore);
				c.getItems().deleteItem(element[0],
						c.getItems().getItemSlot(element[0]), 1);
				c.getItems().addItem(element[1], 1);
				c.sendMessage("You clean the herb.");
			}
		}
		return false;
	}

	public static boolean finishPotion(final Client c, int amount) {
		if ((c.newHerb == -1) || (c.doingHerb == -1)) {
			resetHerblore(c);
			return false;
		}
		if (!HERBLORE) {
			c.sendMessage("This skill is currently disabled.");
			return false;
		}
		EventHandler.randomEvents(c);
		//membersOnly();
		final int item1 = c.getItems().getItemAmount(c.newItem);
		final int item2 = c.getItems().getItemAmount(c.doingHerb);
		if (amount > item2) {
			amount = item2;
		} else if (amount > item1) {
			amount = item1;
		}
		if (!c.getItems().playerHasItem(c.newItem, amount)) {
			c.sendMessage("You don't have enough "
					+ c.getItems().getItemName(c.newItem)
					+ " to make that many!");
			resetHerblore(c);
			return false;
		}
		if (!c.getItems().playerHasItem(c.doingHerb, amount)) {
			c.sendMessage("You don't have enough unfinished potions to make that many!");
			resetHerblore(c);
			return false;
		}
		c.doAmount = amount;
		makePotion(c, amount, c.newItem, "");
		return false;
	}

	public static boolean finishUnfinished(final Client c, int amount) {
		if ((c.newHerb == -1) || (c.doingHerb == -1)) {
			resetHerblore(c);
			return false;
		}
		if (!HERBLORE) {
			c.sendMessage("This skill is currently disabled.");
			return false;
		}
		EventHandler.randomEvents(c);
		//membersOnly();
		final int item1 = c.getItems().getItemAmount(227);
		final int item2 = c.getItems().getItemAmount(c.doingHerb);
		if (amount > item2) {
			amount = item2;
		} else if (amount > item1) {
			amount = item1;
		}
		if (!c.getItems().playerHasItem(227, amount)) {
			c.sendMessage("You don't have enough vials of water to make that many!");
			resetHerblore(c);
			return false;
		}
		if (!c.getItems().playerHasItem(c.doingHerb, amount)) {
			c.sendMessage("You don't have enough herbs to make that many!");
			resetHerblore(c);
			return false; 
		}
		c.doAmount = amount;
		makePotion(c, amount, 227, "un");
		return false;
	}

	public static boolean isHerb(final Client c, final int item) {
		boolean isHerb = false;
		for (final int[] element : CLEANING_HERB) {
			if (item == element[0]) {
				isHerb = true;
			}
		}
		return isHerb;
	}

	private static boolean makePotion(final Client c, final int amount,
			final int otherItem, final String s) {
		final int xp = (c.newXp * HERBLORE_EXPERIENCE);
		final int item1 = c.doingHerb;
		final int item2 = otherItem;
		final int newItem1 = c.newHerb;
		c.stopPlayerSkill = true;
		c.startAnimation(363);
		c.getPA().removeAllWindows();
		if (c.herbloreI) {
			return false;
		}
		if (!HERBLORE) {
			c.sendMessage("This skill is currently disabled.");
			return false;
		}
		EventHandler.randomEvents(c);
		//membersOnly();
		c.herbloreI = true;
		  Server.getTaskScheduler().schedule(new Task(2) {
              @Override
              protected void execute() {
				c.getPA().addSkillXP(xp, c.playerHerblore);
				c.getItems().deleteItem(item1, c.getItems().getItemSlot(item1),
						1);
				c.getItems().deleteItem(item2, c.getItems().getItemSlot(item2),
						1);
				c.getItems().addItem(newItem1, 1);
				c.sendMessage("You make a "
						+ c.getItems().getItemName(newItem1).toLowerCase()
						+ ".");
				c.startAnimation(363);
				addTime(c);
				if (!c.getItems().playerHasItem(item1, 1)
						|| !c.getItems().playerHasItem(item2, 1)
						|| (c.doAmount <= 0)) {
					resetHerblore(c);
					stop();
				}
				if (!c.stopPlayerSkill) {
					resetHerblore(c);
					stop();
				  }
              }
		  	});
		return false;
		  }

	public static boolean mixPotion(final Client c, final int item,
			final int item1) {
		boolean isHerb = false;
		for (final int[] element : ITEM_ON_VIAL) {
			if ((item == element[0]) || (item1 == element[0])) {
				isHerb = true;
			}
		}
		return isHerb;
	}

	public static boolean mixPotionNew(final Client c, final int item,
			final int item1) {
		boolean isHerb = false;
		for (final int[] element : ITEM_ON_ITEM) {
			if ((item == element[0]) || (item1 == element[0])) {
				isHerb = true;
			}
		}
		return isHerb;
	}

	private static void resetHerblore(final Client c) {
		c.getPA().removeAllWindows();
		c.newHerb = -1;
		c.doingHerb = -1;
		c.newXp = 0;
		c.herbloreI = false;
		c.herbAmount = -1;
		c.newItem = -1;
		c.secondHerb = false;
	}
	
	public static String getLine(Client c) {
		return c.below459 ? ("\\n\\n\\n\\n") : ("\\n\\n\\n\\n\\n");
	}

	public static boolean setUpPotion(final Client c, final int useItem,
			final int otherItem) {
		c.secondHerb = true;
		for (int i = 0; i < ITEM_ON_ITEM.length; i++) {
			if (((useItem == ITEM_ON_ITEM[i][1]) && (otherItem == ITEM_ON_ITEM[i][0]))
					|| ((useItem == ITEM_ON_ITEM[i][0]) && (otherItem == ITEM_ON_ITEM[i][1]))) {
				if (c.playerLevel[c.playerHerblore] < ITEM_ON_ITEM[i][3]) {
					c.sendMessage("You haven't got high enough Herblore level to make this potion!");
					c.sendMessage("You need the Herblore level of "
							+ ITEM_ON_ITEM[i][3] + " to make this potion.");
					c.sendMessage(
							"You need the herblore level of "
									+ ITEM_ON_ITEM[i][3]
									+ " to make this potion.");
					resetHerblore(c);
					return false;
				}
				if (!HERBLORE) {
					c.sendMessage("This skill is currently disabled.");
					return false;
				}
				EventHandler.randomEvents(c);
				//membersOnly();
				c.getPA().sendFrame164(4429);
				c.getPA().sendFrame246(1746, view190 ? 190 : 150,
						ITEM_ON_ITEM[i][2]);
				c.getPA().sendFrame126(
						getLine(c) + ""
								+ c.getItems().getItemName(ITEM_ON_ITEM[i][2])
								+ "", 2799);
				c.doingHerb = ITEM_ON_ITEM[i][0];
				c.newHerb = ITEM_ON_ITEM[i][2];
				c.newItem = ITEM_ON_ITEM[i][1];
				c.newXp = ITEM_ON_ITEM[i][4];
			}
		}
		return false;
	}

	public static boolean setUpUnfinished(final Client c, final int useItem,
			final int otherItem) {
		c.secondHerb = false;
		for (int i = 0; i < ITEM_ON_VIAL.length; i++) {
			if (((useItem == 227) && (otherItem == ITEM_ON_VIAL[i][0]))
					|| ((useItem == ITEM_ON_VIAL[i][0]) && (otherItem == 227))) {
				if (c.playerLevel[c.playerHerblore] < ITEM_ON_VIAL[i][2]) {
					c.sendMessage("You haven't got high enough Herblore level to make this potion!");
					c.sendMessage("You need the Herblore level of "
							+ ITEM_ON_VIAL[i][2] + " to make this potion.");
					c.sendMessage(
							"You need the herblore level of "
									+ ITEM_ON_VIAL[i][2]
									+ " to make this potion.");
					resetHerblore(c);
					return false;
				}
				if (!HERBLORE) {
					c.sendMessage("This skill is currently disabled.");
					return false;
				}
				EventHandler.randomEvents(c);
				//membersOnly();
				c.getPA().sendFrame164(4429);
				c.getPA().sendFrame246(1746, view190 ? 190 : 150,
						ITEM_ON_VIAL[i][1]);
				c.getPA().sendFrame126(
						getLine(c) + ""
								+ c.getItems().getItemName(ITEM_ON_VIAL[i][1])
								+ "", 2799);
				c.doingHerb = ITEM_ON_VIAL[i][0];
				c.newHerb = ITEM_ON_VIAL[i][1];
				c.newXp = 0;
			}
		}
		return false;
	}

}
