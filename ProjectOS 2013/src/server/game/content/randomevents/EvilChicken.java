package server.game.content.randomevents;

import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.util.Misc;

public class EvilChicken {

	private static int[][] chicken = {
		{3, 	10, 	2463, 	19, 	1},
		{11, 	20, 	2464, 	40, 	1},
		{21, 	40, 	2465, 	60, 	2},
		{41, 	60, 	2466, 	80, 	3},
		{61, 	90, 	2467, 	105, 	4},
		{91, 	138, 	2468, 	120, 	5},
	};

	public static void spawnChicken(Client c) {
		for (int[] aChicken : chicken) {
			if (c.combatLevel >= aChicken[0] && c.combatLevel <= aChicken[1]) {
				NPCHandler.spawnNpc(c, aChicken[2], c.absX + Misc.random(1), c.absY + Misc.random(1), c.heightLevel, 0, aChicken[3], aChicken[4], aChicken[4] * 10, aChicken[4] * 10, true, false);
			}
		}
	}
}