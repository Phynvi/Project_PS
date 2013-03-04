package server.game.players.packets;

import server.game.items.UseItem;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType,
			int packetSize) {
		final int itemId = c.getInStream().readSignedWordA();
		final int i = c.getInStream().readSignedWordA();
		final int slot = c.getInStream().readSignedWordBigEndian();
		//final int npcId = NPCHandler.npcs[i].npcType;
		c.getPA().resetVariables();
		if (c.playerRights == 3) {
			c.sendMessage("item id: " + itemId + " slot: " + slot + " i: "
					+ i);
		}
		if (c.isDead || c.isTeleporting) {
			return;
		}
		if (!c.getItems().playerHasItem(itemId, 1, slot)) {
			return;
		}
		c.faceNpc(i);
				if (c == null || !c.getItems().playerHasItem(itemId, 1, slot)
						|| NPCHandler.npcs[i] == null
						|| NPCHandler.npcs[i].isDead) {
					return;
				}
				UseItem.ItemonNpc(c, itemId, slot, i);
				if (c.getItems().freeSlots() < 1 || c.disconnected == true) {
					c.sendMessage("Your inventory is full.");
					return;
			}
	}
}