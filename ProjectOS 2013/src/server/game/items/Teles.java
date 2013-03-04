package server.game.items;

import server.game.players.Client;

/**
 * @author Andrew
 */

public class Teles {

	public static void AOG(Client c) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Draynor");
		c.dialogueAction = 51;
		c.sendMessage("You rub the Amulet of Glory...");
	}

	public static void ROD(Client c) {
		c.getDH().sendOption2("Duel Arena", "Castle Wars");
		c.usingROD = true;
		c.sendMessage("You rub the Ring of Dueling...");
	}

	public static void GN(Client c) {
		c.getDH().sendOption2("Burthrope Games Room", "Barbarian Outpost");
		c.dialogueAction = 50;
		c.sendMessage("You rub the Games Necklace...");
	}
	
	private static int NECKLACES [][] = {{3853, 3855, 7}, {3855, 3857, 6}, {3857, 3859, 5}, {3859, 3861, 4}, {3861, 3863, 3}, {3863, 3865, 2}, {3865, 3867, 1},//gn
	{2552, 2554, 7}, {2554, 2556, 6}, {2556, 2558, 5}, {2558, 2560, 4}, {2560, 2562, 3}, {2562, 2564, 2}, {2564, 2566, 1},//rod
	{1712, 1710, 3}, {1710, 1708, 2}, {1708, 1706, 1}, {1706, 1704, 0}};//aog
	
	public static void necklaces(Client c) {
	       for (int i = 0; i < NECKLACES.length; i++) {
	            if (c.itemUsing == NECKLACES[i][0]) {
	            if (c.isOperate) {
	                c.playerEquipment[c.playerAmulet] = NECKLACES[i][1];
	            } else {
	                c.getItems().deleteItem(NECKLACES[i][0], 1);
	                c.getItems().addItem(NECKLACES[i][1], 1);
	            }
	            if (NECKLACES[i][2] > 1) {
	                c.sendMessage("You have "+NECKLACES[i][2]+" charges left.");
	            } else {
	                c.sendMessage("You have "+NECKLACES[i][2]+" charge left.");
	                }
	            }   
	        }
	        c.getItems().updateSlot(c.playerAmulet);
	        c.isOperate = false;
	        c.itemUsing = -1;
	    }
	}