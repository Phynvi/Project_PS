package server.game.items;

import server.Config;
import server.game.content.skills.SkillHandler;
import server.game.content.skills.cooking.Cooking;
import server.game.content.skills.crafting.GemCutting;
import server.game.content.skills.crafting.JewelryMaking;
import server.game.content.skills.crafting.LeatherMaking;
import server.game.objects.CrystalChest;
import server.game.objects.FlourMill;
import server.game.players.Client;
import server.util.Misc;

/**
 * 
 * @author Ryan / Lmctruck30
 * 
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX,
			int objectY, int itemId) {
		final int goodPosXType1 = objectX - 5;
		final int goodPosXType2 = objectX + 5;
		final int goodPosYType1 = objectY - 5;
		final int goodPosYType2 = objectY + 5;
		if ((c.absX >= goodPosXType1 && c.absX <= goodPosXType2)
				&& (c.absY >= goodPosYType1 && c.absY <= goodPosYType2)) {
			c.turnPlayerTo(objectX, objectY);
		} else {
			c.getPA().playerWalk(objectX, objectY);
		}
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch (objectID) {
		case 2714:
			FlourMill.grainOnHopper(c, objectID, itemId);
		break;
		case 172:
			if(itemId == CrystalChest.KEY)
			CrystalChest.searchChest(c, objectID, objectX, objectY);
		break;
		
		case 2783:
			if ((c.absX >= goodPosXType1 && c.absX <= goodPosXType2)
					&& (c.absY >= goodPosYType1 && c.absY <= goodPosYType2)) {
				c.getSmithingInt().showSmithInterface(itemId);
				c.turnPlayerTo(objectX, objectY);
			} else {
				c.getPA().playerWalk(objectX, objectY);
			}
			break;
				
		case 8689:
		if(c.getItems().playerHasItem(1925, 1) && c.objectId == 8689) {
			c.turnPlayerTo(c.objectX, c.objectY);
			c.startAnimation(2292);
			c.getItems().addItem(1927 ,1);
			c.getItems().deleteItem(1925, 1);
		} else {
			c.sendMessage("You need a bucket to milk a cow!");
		}
		break;
		
		default:
			if (c.playerRights == 3)
				Misc.println("Player At Object id: " + objectID
						+ " with Item id: " + itemId);
			break;
		}

	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		if (c.getItems().getItemName(itemUsed).contains("(")
				&& c.getItems().getItemName(useWith).contains("("))
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		if (itemUsed == 6703 || useWith == 6703)
			c.getPTS().handlePotato(itemUsed,useWith);
		if (itemUsed == 1755 || useWith == 1755)
			GemCutting.cutGem(c, itemUsed, useWith);
		if (itemUsed == 1759 || useWith == 1759)
			JewelryMaking.stringAmulet(c, itemUsed, useWith);
		if (itemUsed == 1733 || useWith == 1733)
			LeatherMaking.craftLeatherDialogue(c, itemUsed, useWith);
		if (itemUsed == 822 || useWith == 822)
			c.getFletching().makeDarts(itemUsed, useWith);
		if (itemUsed == 819 || useWith == 819)
			c.getFletching().makeDarts(itemUsed, useWith);
		if (itemUsed == 820 || useWith == 820)
			c.getFletching().makeDarts(itemUsed, useWith);
		if (itemUsed == 824 || useWith == 824)
			c.getFletching().makeDarts(itemUsed, useWith);
		if (itemUsed == 823 || useWith == 823)
			c.getFletching().makeDarts(itemUsed, useWith);
		if (itemUsed == 821 || useWith == 821)
			c.getFletching().makeDarts(itemUsed, useWith);

				
		 /**
         * Pizza Creation
         */
        if (itemUsed == 1982  && useWith == 2283 || itemUsed == 2283 && useWith == 1982) {
            Cooking.pastryCreation(c, 1982, 2283, 2285, "");
        }
        if (itemUsed == 2285  && useWith == 1985 || itemUsed == 1985 && useWith == 2285) {
            Cooking.pastryCreation(c, 2285, 1985, 2287, "");
        }
        if (itemUsed == 2140  && useWith == 2289 || itemUsed == 2289 && useWith == 2140) {
            Cooking.cookingAddon(c, 2140, 2289, 2293, 45, 26);
        }
        if (itemUsed == 319  && useWith == 2289 || itemUsed == 2289 && useWith == 319) {
            Cooking.cookingAddon(c, 319, 2289, 2297, 55, 39);
        }
        if (itemUsed == 2116  && useWith == 2289 || itemUsed == 2289 && useWith == 2116) {
            Cooking.cookingAddon(c, 2116, 2289, 2301, 65, 45);
        }
        /**
         * Pie Making
         */
        if (itemUsed == 2313  && useWith == 1953 || itemUsed == 1953 && useWith == 2313) {
            Cooking.pastryCreation(c, 2313, 1953, 2315, "You put the pastry dough into the pie dish to make a pie shell.");
        }
        if (itemUsed == 2315  && useWith == 1955 || itemUsed == 1955 && useWith == 2315) {
            Cooking.pastryCreation(c, 2315, 1955, 2317, "You fill the pie with cooking apple.");
        }
        if (itemUsed == 2315  && useWith == 5504 || itemUsed == 5504 && useWith == 2315) {
            Cooking.pastryCreation(c, 2315, 5504, 7212, "");
        }
        if (itemUsed == 7212  && useWith == 5982 || itemUsed == 5982 && useWith == 7212) {
            Cooking.pastryCreation(c, 7212, 5982, 7214, "");
        }
        if (itemUsed == 1955  && useWith == 7214 || itemUsed == 7214 && useWith == 1955) {
            Cooking.pastryCreation(c, 1955, 7214, 7216, "");
        }
        if (itemUsed == 2315  && useWith == 1951 || itemUsed == 1951 && useWith == 2315) {
            Cooking.pastryCreation(c, 1951, 2315, 2321, "");
        }
        /**
         * Pitta/ Ugthanki Kebab
         */
        if (itemUsed == 1865  && useWith == 1881 || itemUsed == 1881 && useWith == 1865) {
            Cooking.cookingAddon(c, 1865, 1881, 1883, 0, 40);
        }
        
		if (itemUsed == 1929 && useWith == 1933 || itemUsed == 1933 && useWith == 1929)
        if (c.getItems().playerHasItem(1933, 1)) {
                c.getItems().deleteItem(1933, 1);
                c.getItems().deleteItem(1929, 1);
                c.getItems().addItem(2307, 1);
                c.sendMessage("Click 'use' on the range now.");
       }    
		
		if (itemUsed == 1987 || useWith == 1937 || itemUsed == 1937 || useWith == 1987)
		if (c.playerLevel[c.playerCooking] >= 35) {
			c.getItems().addItem(1993, 1);
			c.getPA().addSkillXP(200, c.playerCooking);
		} else {
			c.sendMessage("You need grapes and a jug of water to make wine.");
		}
		
		if (itemUsed == 946 || useWith == 946)
			c.getFletching().handleLog(itemUsed, useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52)
			c.getFletching().makeArrows(itemUsed, useWith);
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190
				&& useWith == 9142) {
			if (c.playerLevel[Config.FLETCHING] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9241, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 6 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191
				&& useWith == 9143) {
			if (c.playerLevel[Config.FLETCHING] >= 63) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9242, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192
				&& useWith == 9143) {
			if (c.playerLevel[Config.FLETCHING] >= 65) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9243, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193
				&& useWith == 9144) {
			if (c.playerLevel[Config.FLETCHING] >= 71) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9244, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 10 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194
				&& useWith == 9144) {
			if (c.playerLevel[Config.FLETCHING] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9245, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 13 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1601) {
			if (c.playerLevel[Config.FLETCHING] >= 63) {
				c.getItems()
						.deleteItem(1601, c.getItems().getItemSlot(1601), 1);
				c.getItems().addItem(9192, 15);
				c.getPA().addSkillXP(8 *SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1607) {
			if (c.playerLevel[Config.FLETCHING] >= 65) {
				c.getItems()
						.deleteItem(1607, c.getItems().getItemSlot(1607), 1);
				c.getItems().addItem(9189, 15);
				c.getPA().addSkillXP(8 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1605) {
			if (c.playerLevel[Config.FLETCHING] >= 71) {
				c.getItems()
						.deleteItem(1605, c.getItems().getItemSlot(1605), 1);
				c.getItems().addItem(9190, 15);
				c.getPA().addSkillXP(8 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1603) {
			if (c.playerLevel[Config.FLETCHING] >= 73) {
				c.getItems()
						.deleteItem(1603, c.getItems().getItemSlot(1603), 1);
				c.getItems().addItem(9191, 15);
				c.getPA().addSkillXP(8 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1615) {
			if (c.playerLevel[Config.FLETCHING] >= 73) {
				c.getItems()
						.deleteItem(1615, c.getItems().getItemSlot(1615), 1);
				c.getItems().addItem(9193, 15);
				c.getPA().addSkillXP(8 * SkillHandler.FLETCHING_EXPERIENCE,
						Config.FLETCHING);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}

		switch (itemUsed) {

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemUsed
						+ " with Item id: " + useWith);
			break;
		}
	}

	public static void ItemonNpc(final Client c, final int itemId, final int npcId, final int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

		}
	}
