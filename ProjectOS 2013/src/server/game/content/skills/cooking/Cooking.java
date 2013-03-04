package server.game.content.skills.cooking;

import java.security.SecureRandom;

import server.Server;
import server.game.content.randomevents.EventHandler;
import server.game.content.skills.SkillHandler;
import server.game.dialogues.DialogueHandler;
import server.game.players.Client;
import server.task.Task;

public class Cooking extends SkillHandler {
	
	private static SecureRandom cookingRandom = new SecureRandom(); // The random factor
	
	private static enum CookingItems {
		SHRIMP(317, 315, 7954, 1, 30, 33, "shrimp"),
		ANCHOVIES(321, 319, 323, 5, 45, 34, "anchovies"),
		TROUT(335, 333, 343, 20, 70, 50, "trout"),
		SALMON(331, 329, 343, 30, 90, 58, "salmon"),
		PIKE(359, 361, 35, 343, 100, 64, "pike"),
		LOBSTER(377, 379, 381, 40, 120, 74, "lobster"),
		SWORDFISH(371, 373, 375, 50, 140, 86, "swordfish"),
		MONKFISH(7944, 7946, 7948, 62, 150, 91, "monkfish"),
		SHARK(383, 385, 387, 80, 210, 94, "shark"),
		MANTA_RAY(389, 391, 393, 91, 169, 99, "manta ray");
		
		int rawItem, cookedItem, burntItem, levelReq, xp, stopBurn; String name;
		
		private CookingItems(int rawItem, int cookedItem, int burntItem, int levelReq, int xp, int stopBurn, String name) {
			this.rawItem = rawItem;
			this.cookedItem = cookedItem;
			this.burntItem = burntItem;
			this.levelReq = levelReq;
			this.xp = xp;
			this.stopBurn = stopBurn;
			this.name = name;
		}

		private int getRawItem() {
			return rawItem;
		}

		private int getCookedItem() {
			return cookedItem;
		}

		private int getBurntItem() {
			return burntItem;
		}

		private int getLevelReq() {
			return levelReq;
		}

		private int getXp() {
			return xp;
		}

		private int getStopBurn() {
			return stopBurn;
		}

		private String getName() {
			return name;
		}
	}
	
	public static CookingItems forId(int itemId) {
		for (CookingItems item : CookingItems.values()) {
			if (itemId == item.getRawItem()) {
				return item;
			}
		}
		return null;
	}
	
    public static void makeBreadOptions(Client c, int item) {
        if (c.getItems().playerHasItem(1929) && c.getItems().playerHasItem(1933) && item == c.breadID) {
            c.getItems().deleteItem(1929, 1);
            c.getItems().deleteItem(1933, 1);
            c.getItems().addItem(1925, 1);
            c.getItems().addItem(1931, 1);
            c.getItems().addItem(item, 1);
            c.sendMessage("You make the water and flour to make some " + c.getItems().getItemName(item) + ".");
        }
        c.getPA().closeAllWindows();
    }

    public static void pastryCreation(Client c, int itemID1, int itemID2, int giveItem, String message) {
        if (c.getItems().playerHasItem(itemID1) && c.getItems().playerHasItem(itemID2)) {
            c.getItems().deleteItem(itemID1, 1);
            c.getItems().deleteItem(itemID2, 1);
            c.getItems().addItem(giveItem, 1);
            if (message.equalsIgnoreCase("")) {
                c.sendMessage("You mix the two ingredients and get an " + c.getItems().getItemName(giveItem) + ".");
            } else {
                c.sendMessage(message);
            }
        }
    }

    public static void cookingAddon(Client c, int itemID1, int itemID2, int giveItem, int requiredLevel, int expGained) {
        if (c.playerLevel[7] >= requiredLevel) {
            if (c.getItems().playerHasItem(itemID1) && c.getItems().playerHasItem(itemID2)) {
                c.getItems().deleteItem(itemID1, 1);
                c.getItems().deleteItem(itemID2, 1);
                c.getItems().addItem(giveItem, 1);
                c.getPA().addSkillXP(expGained, 7);
                c.sendMessage("You create a " + c.getItems().getItemName(giveItem) + ".");
            }
        } else {
            c.sendMessage("You don't have the required level to make an " + c.getItems().getItemName(giveItem));
        }
    }
	
	private static void setCooking(Client c) {
		c.playerIsCooking = true;
		c.stopPlayerSkill = true;
	}
	
	private static void resetCooking(Client c) {
		c.playerIsCooking = false;
		c.stopPlayerSkill = false;
	}
	
	private static void viewCookInterface(Client c, int item) {
    	c.getPA().sendFrame164(1743);
     	c.getPA().sendFrame246(13716, view190 ? 190 : 180, item);
 		c.getPA().sendFrame126(getLine(c)+""+c.getItems().getItemName(item)+"", 13717);
	}
	
	public static String getLine(Client c) {
		return c.below459 ? ("\\n\\n\\n\\n") : ("\\n\\n\\n\\n\\n");
	}

	public static boolean startCooking(Client c, int itemId, int objectId) {
		CookingItems item = forId(itemId);
		if(item != null) {
			if(c.playerLevel[c.playerCooking] < item.getLevelReq()) {
				c.getPA().removeAllWindows();
				DialogueHandler.sendStatement(c, "You need a Cooking level of " + item.getLevelReq() + " to cook this.");
				return false;
			}
			if(c.playerIsCooking) {
				c.getPA().removeAllWindows();
				return false;
			}
			if (!COOKING) {
				c.sendMessage("This skill is currently disabled.");
				return false;
			}
			// save the id of the item and object for the cooking interface.
			c.cookingItem = itemId;
			c.cookingObject = objectId;
			viewCookInterface(c, item.getRawItem());
			return true;
		}
		return false;
	}
	
	private static boolean getSuccess(Client c, int burnBonus, int levelReq, int stopBurn) {
		if (c.playerLevel[c.playerCooking] >= stopBurn) {
			return false;
		}
		double burn_chance = (55.0 - burnBonus);
		double cook_level = c.playerLevel[c.playerCooking];
		double lev_needed = (double) levelReq;
		double burn_stop = (double) stopBurn;
		double multi_a = (burn_stop - lev_needed);
		double burn_dec = (burn_chance / multi_a);
		double multi_b = (cook_level - lev_needed);
		burn_chance -= (multi_b * burn_dec);
		double randNum = cookingRandom.nextDouble() * 100.0;
		return burn_chance <= randNum;
	}
	
	public static void cookItem(final Client c, final int itemId, final int amount, final int objectId) {
		final CookingItems item = forId(itemId);
		if(item != null) {
			setCooking(c);
			EventHandler.randomEvents(c);
			c.getPA().removeAllWindows();
			c.doAmount = amount;
			if(c.doAmount > c.getItems().getItemAmount(itemId)) {
				c.doAmount = c.getItems().getItemAmount(itemId);
			}
			if(objectId > 0) {
				c.startAnimation(objectId == 2732 ? 897 : 896);
			}
			 Server.getTaskScheduler().schedule(new Task(4) {
                 @Override
                 protected void execute() {
					if(!c.playerIsCooking) {
						resetCooking(c);
						this.stop();
						return;
					}
				    if (!c.getItems().playerHasItem(item.getRawItem(), 1)) {
						c.sendMessage("You have run out of " + item.getName() + " to cook.");
						resetCooking(c);
						this.stop();
						return;
				    }
				    boolean burn = getSuccess(c, 3, item.getLevelReq(), item.getStopBurn());
					c.getItems().deleteItem(item.getRawItem(), c.getItems().getItemSlot(itemId), 1);
					if(!burn) {
						c.sendMessage("You successfully cook the "+ item.getName().toLowerCase() +".");
						c.getPA().addSkillXP(item.getXp() * FISHING_EXPERIENCE, c.playerCooking);
						c.getItems().addItem(item.getCookedItem(), 1);
					} else {
						c.sendMessage("Oops! You accidentally burnt the "+ item.getName().toLowerCase() +"!");
						c.getItems().addItem(item.getBurntItem(), 1);
					}
					c.doAmount--;
					if(c.doAmount > 0) {
						if(objectId > 0) {
							c.startAnimation(objectId == 2732 ? 897 : 896);
						}
					} else if(c.doAmount == 0) {
						resetCooking(c);
						this.stop();
					}
				}
			});
		}
	}
}