package server.game.npcs;

/**
 * Transforming
 */

public class Transforming {
	
	/*/public static final int[][] COMBAT_TRANSFORM = {{1, 6006}, {4, 6007}};
	public static final int[][] NON_COMBAT_TRANSFORM = {{43, 42, 1735, 1737}};
	
	public static boolean isTransformNpc(int npcType) {
		for (int[] w : COMBAT_TRANSFORM) {
			if (w[0] == npcType) {
				return true;
			}
		}
		return false;
	}

	public static int transformNpc(int npcType) {
		for (int[] w : COMBAT_TRANSFORM) {
			if (w[0] == npcType) {
				return w[1];
			}
		}
		return -1;
	}
	
	public static boolean isNonCombat(int npcType) {
		for (int[] w: NON_COMBAT_TRANSFORM) {
			if(w[0] == npcType) {
				return true;
			}
		}
		return false;
	}
	

	public static int transformNon(int npcType) {
		for (int[] w : NON_COMBAT_TRANSFORM) {
			if (w[0] == npcType) {
				return w[1];
			}
		}
		return -1;
	}
	
	public static void transform(Client c) {
		for (int i = 0; i < NON_COMBAT_TRANSFORM.length; i++) {
		int reqItem = NON_COMBAT_TRANSFORM[i][2];
		int itemId = NON_COMBAT_TRANSFORM[i][3];
		final int npcId = NPCHandler.npcs[i].npcType;
		if (isNonCombat(npcId)) {
		if (c.getItems().playerHasItem(reqItem)) {
			return;
		}
		if (c.getItems().freeSlots() < 1) {
			c.sendMessage("Your inventory is full.");
			return;
		}
			c.startAnimation(893);
			c.getItems().addItem(itemId, 1);
			c.sendMessage("You shear the sheep and get some wool.");
			NPCHandler.npcs[i].forceChat("Baaaa!");
			NPCHandler.npcs[i].requestTransform(transformNon(npcId));
			} else {
				c.sendMessage("This npc has already been transformed.");
			}
		}
	}/*/
}