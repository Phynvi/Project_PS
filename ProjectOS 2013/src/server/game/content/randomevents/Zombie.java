package server.game.content.randomevents;

import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.util.Misc;

public class Zombie {

	private static int[][] zombie = {{3, 10, 419}, {11, 20, 420}, {21, 40, 421}, {61, 90, 422}, {91, 110, 423}, {111, 138, 424}};

	public static void spawnZombie(Client c) {
		for (int[] element : zombie) {
			if (Misc.random(300) == 4) {
			if (c.combatLevel >= element[0] && c.combatLevel <= element[1]) {
				NPCHandler.spawnNpc(c, element[2], c.absX, c.absY, c.heightLevel, 0, element[3], element[4], element[4] * 10, element[4] * 10, true, false);
				break;
			}
		}
	}	
  }
}