package server.game.content.randomevents;

import server.game.npcs.NPCHandler;
import server.game.players.Client;

public class Golem {
	
	private static final int 	
	GOLEM_LEVEL_14 = 413, 
	GOLEM_LEVEL_29 = 414, 
	GOLEM_LEVEL_49 = 415, 
	GOLEM_LEVEL_79 = 416, 
	GOLEM_LEVEL_120 = 417, 
	GOLEM_LEVEL_159 = 418;
	
	public static boolean randomGolemSpawn(Client c) {
		if (c.playerHasRandomEvent == false) {
			if (c.combatLevel >= 3 && c.combatLevel < 29) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_14, c.absX, c.absY + 1, c.heightLevel, 0, 28, 1, 10, 10, true, false);
				c.playerHasRandomEvent = true;
				return true;
			} else if (c.combatLevel >= 29 && c.combatLevel < 49) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_29, c.absX, c.absY + 1, c.heightLevel, 0, 36, 3, 50, 50, true, false);
				c.playerHasRandomEvent = true;
				return true;
			} else if (c.combatLevel >= 49 && c.combatLevel < 79) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_49, c.absX, c.absY + 1, c.heightLevel, 0, 36, 3, 75, 75, true, false);
				c.playerHasRandomEvent = true;
				return true;
			} else if (c.combatLevel >= 79 && c.combatLevel < 100) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_79, c.absX, c.absY + 1, c.heightLevel, 0, 36, 3, 100, 100, true, false);
				c.playerHasRandomEvent = true;
				return true;
			} else if (c.combatLevel >= 100 && c.combatLevel < 110) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_120, c.absX, c.absY + 1, c.heightLevel, 0, 36, 3, 100, 100, true, false);
				c.playerHasRandomEvent = true;
				return true;
			} else if (c.combatLevel >= 110) {
				NPCHandler.spawnNpc(c, GOLEM_LEVEL_159, c.absX, c.absY + 1, c.heightLevel, 0, 36, 3, 100, 100, true, false);
				c.playerHasRandomEvent = true;
				return true;
			}
		}
		return false;
	}

}
