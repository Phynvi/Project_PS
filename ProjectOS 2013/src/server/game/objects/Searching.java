package server.game.objects;

import server.game.players.Client;

/**
 *@author Andrew
 * unfinished accidentaly didn't save
 */

public class Searching {
	
	public static int[][] SEARCHING_ITEMS = {
         {9146, 757}, // object id, item id
         {9147, 784},
         {7058, 757},
         {7066, 757},
         {7065, 784},
         {7068, 784}};
	
	public static boolean search(Client c, int object) {
		for(int i = 0; i < SEARCHING_ITEMS.length; i++) {
			if(object == SEARCHING_ITEMS[i][0]) {
				return true;
			}
		}
		return false;
	}
	
public static void search(Client c, final int objectClickId, final int objectX, final int objectY) {
	 if (c.miscTimer + 1800 > System.currentTimeMillis())
         return;
     for (int[] data : SEARCHING_ITEMS) {
         final int objectId = data[0];
         int itemId = data[1];
         if (objectClickId == objectId) {
             c.getItems().addItem(itemId, 1);
             break;
         }
     }
     if (c.getItems().freeSlots() > 0) {
         c.turnPlayerTo(objectX, objectY);
         c.miscTimer = System.currentTimeMillis();
         c.sendMessage("You start searching...");
     } else {
    	 c.sendMessage("Your inventory is full!");
     	}
	}
}