package server.game.players.packets;

/**
 * @author Ryan / Lmctruck30
 */

import server.game.content.skills.core.Herblore;
import server.game.items.UseItem;
import server.game.players.Client;
import server.game.players.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		if (!c.getItems().playerHasItem(useWith, 1, usedWithSlot)
				|| !c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		
		if(Herblore.mixPotion(c, itemUsed, useWith)) {
			Herblore.setUpUnfinished(c, itemUsed, useWith);
		}
		
		if(Herblore.mixPotionNew(c, itemUsed, useWith)) {
			Herblore.setUpPotion(c, itemUsed, useWith);
		}
		
		if (c.getFiremaking().isLog(itemUsed)) {
			c.getFiremaking().lightFire(c, itemUsed, itemUsedSlot);
		}
		if (c.getFiremaking().isLog(useWith)) {
			c.getFiremaking().lightFire(c, useWith, usedWithSlot);
		}
		
		UseItem.ItemonItem(c, itemUsed, useWith);
	}

}
