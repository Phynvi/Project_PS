package server.game.content.combat.magic;

import server.game.players.Client;

/**
 *@author Andrew
 */

public class Superheat {

	// ore1, ore1amount, ore2, ore2amount, item, xp, smith lvl req
		private static final int[][] SMELT = {{436, 1, 438, 1, 2349, 6, 1}, // TIN
				{438, 1, 436, 1, 2349, 6, 1}, // COPPER
				{440, 1, 453, 2, 2353, 18, 30}, // STEEL ORE
				{440, 1, -1, -1, 2351, 13, 15}, // IRON ORE
				{442, 1, -1, -1, 2355, 14, 20}, // SILVER ORE
				{444, 1, -1, -1, 2357, 23, 40}, // GOLD BAR
				{447, 1, 453, 4, 2359, 30, 50}, // MITHRIL ORE
				{449, 1, 453, 6, 2361, 38, 70}, // ADDY ORE
				{451, 1, 453, 8, 2363, 50, 85}, // RUNE ORE
		};
		
	public static boolean superHeatItem(Client c, int itemID) {
		for (int smelt[] : SMELT) {
			if (itemID == smelt[0]) {
				if (!c.getItems().playerHasItem(smelt[2], smelt[3])) {
					if (itemID == 440 && smelt[2] == 453) {
						continue;
					} else {
						c.sendMessage("You haven't got enough " + c.getItems().getItemName(smelt[2]).toLowerCase() + " to cast this spell!");
						return false;
					}
				}
				if (c.playerLevel[c.playerMagic] < smelt[6]) {
					c.sendMessage("You need a smithing level of " + smelt[6] + " to superheat this ore.");
					return false;
				}
				c.getItems().deleteItem(itemID, 1);
				c.getItems().deleteItem(smelt[2], smelt[3]);
				c.getItems().addItem(smelt[4], 1);
				c.getPA().addSkillXP(smelt[5], c.playerMagic);
				c.getPA().sendFrame106(6);
				return true;
			}
		}
		c.sendMessage("You can only cast superheat item on ores!");
		return false;
	}
}