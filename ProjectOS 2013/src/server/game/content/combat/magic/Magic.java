package server.game.content.combat.magic;

import server.Config;
import server.game.content.randomevents.EventHandler;
import server.game.players.Client;
import server.util.Misc;

/**
 *@author Andrew
 */

public class Magic {

	public static void handleLoginText(Client c) {
		c.getPA().sendFrame126("Varrock Teleport", 1300);
		c.getPA().sendFrame126("Lumbridge Teleport", 1325);
		c.getPA().sendFrame126("Falador Teleport", 1350);
		c.getPA().sendFrame126("Camelot Teleport", 1382);
		c.getPA().sendFrame126("Ardougne Teleport", 1415);
		c.getPA().sendFrame126("Paddewwa Teleport", 13037);
		c.getPA().sendFrame126("Senntisten Teleport", 13047);
		c.getPA().sendFrame126("Kharyrll Teleport", 13055);
		c.getPA().sendFrame126("Lassar Teleport", 13063);
		c.getPA().sendFrame126("Dareeyak Teleport", 13071);
	}
	
	public static boolean
	MAGIC_LEVEL_REQUIRED = true,
	RUNES_REQUIRED = true;
	
	public static enum Teleports {
		VARROCK(4140, 25, 35, new int[]{563, 554, 556}, new int[]{1, 1, 3}, new int[]{3213, 3423}),
	    LUMBRIDGE(4143, 31, 41, new int[]{563, 557, 556}, new int[]{1, 1, 3}, new int[]{3223, 3218}),
	    FALADOR(4146, 37, 48, new int[]{563, 555, 556}, new int[]{1, 1, 3}, new int[]{2965, 3378}),
	    CAMELOT(4150, 45, 56, new int[]{563, 556}, new int[]{1, 5}, new int[]{2757, 3477}),
	    ARDOUGNE(6004, 51, 61, new int[]{563, 555}, new int[]{2, 2}, new int[]{2662, 3305}),
	    WATCHTOWER(6005, 58, 68, new int[]{563, 557}, new int[]{2, 2}, new int[]{2544, 3112}),
	    TROLLHEIM(29031, 61, 68, new int[]{563, 554}, new int[]{2, 2}, new int[]{2891, 3678}),
	    APE_ATOLL(72038, 64, 76, new int[]{554, 555, 563, 1963}, new int[]{2, 2, 2, 1}, new int[]{2754, 2785}),
	    PADDEWWA(50235, 54, 64, new int[]{563, 556, 554}, new int[]{2, 1, 1}, new int[]{3098, 9882}),
	    SENNTISTEN(50245, 60, 70, new int[]{563, 566}, new int[]{2, 1}, new int[]{3320, 3338}),
	    KHARYRLL(50253, 66, 76, new int[]{563, 565}, new int[]{2, 1}, new int[]{3493, 3472}),
	    LASSAR(51005, 72, 72, new int[]{563, 555}, new int[]{2, 4}, new int[]{3003, 3470}),
	    DAREEYAK(51013, 78, 88, new int[]{563, 554, 556}, new int[]{2, 3, 2}, new int[]{2966, 3696}),
	    CARRALLANGAR(51023, 84, 94, new int[]{563, 566}, new int[]{2, 2}, new int[]{3163, 3664}),
	    ANNAKARL(51031, 90, 100, new int[]{563, 565}, new int[]{2, 2}, new int[]{3287, 3883}),
	    GHORROCK(51039, 96, 106, new int[]{563, 555}, new int[]{2, 8}, new int[]{2972, 3873});
	
		int levelReq, clickingButton, xp;
        int[] reqItems, reqAmounts, newLoc;
 
        Teleports(int clickingButton, int levelReq, int xp, int[] reqItems, int[] reqAmounts, int[] newLoc) {
            this.xp = xp;
            this.levelReq = levelReq;
            this.reqItems = reqItems;
            this.reqAmounts = reqAmounts;
            this.newLoc = newLoc;
            this.clickingButton = clickingButton;
        }
    }
	
	private static boolean hasItems(Client c, int[] items, int[] amount) {
        int temp = 0;
        for (int i = 0; i < items.length; i++) {
            if (c.getItems().playerHasItem(items[i], amount[i])) {
                temp += 1;
            }
        }
        return temp == items.length;
    }
 
    private static void deleteItems(Client c, int[] items, int[] amount) {
        for (int i = 0; i < items.length; i++) {
            c.getItems().deleteItem(items[i], c.getItems().getItemSlot(items[i]), amount[i]);
        }
    }
 
    private static Teleports getTele(int clickingButton) {
        for (Teleports t : Teleports.values()) {
            if (t.clickingButton == clickingButton) {
                return t;
            }
        }
        return null;
    }
 
    public static void handleTeleport(final Client c, int actionButton) {
    	Teleports t = getTele(actionButton);
        if (t != null) {
        	if (System.currentTimeMillis() - c.lastCast < 5000) {
				return;
			}
        	if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
    			c.sendMessage("You can't teleport above level "
    					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
    			return;
    		}
        	c.getPA().spellTeleport(t.newLoc[0] + (Misc.random(10) <= 4 ? -Misc.random(2) : + Misc.random(2)), t.newLoc[1] + (Misc.random(10) <= 4 ? -Misc.random(2) : + Misc.random(2)), 0);
                    c.sendMessage("You teleport to "+Misc.optimizeText(t.name().toLowerCase()).replace("_", " ").replace("ancient", "")+".");

                    c.lastCast = System.currentTimeMillis();
        			EventHandler.randomEvents(c);
                 }
       // }
    }
}