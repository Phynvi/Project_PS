package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.npcs.Pets;
import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		if (System.currentTimeMillis() - c.alchDelay < 1800) {
			return;
		}
		if (c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		if (c.hasNpc == true) {
			c.sendMessage("You already have a pet dropped.");
			return;
		}

		switch (itemId) {
		case 1560:
		if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, true, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1559:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1558:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
			} else {
				c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1557:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
			} else {
				c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1556:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1555:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1561:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1562:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1563:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1564:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1565:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 7583:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Hell Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 1566:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Cat.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 7585:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Hell Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		case 4045:
	        int explosiveHit = 15;
	        c.startAnimation(827);
	        c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
	        c.handleHitMask(explosiveHit);
	        c.dealDamage(explosiveHit);
	        c.getPA().refreshSkill(3);
	        c.forcedText = "Ow! That really hurt!";
	        c.forcedChatUpdateRequired = true;
	        c.updateRequired = true;
	    break;
		case 7584:
			if (!c.hasNpc) {
				Server.npcHandler.spawnNpc3(c, Pets.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, false, false, true);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				c.hasNpc = true;
				c.getPA().followPlayer();
				c.sendMessage("You drop your Hell Kitten.");
		} else {
			c.sendMessage("You already dropped your Pet.");
			return;
		}
		break;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		
		for (int p : Pets.CAT_ITEMS) {
			if (p == itemId) {
			if(c.hasNpc == true) {
				droppable = false;
				break;
			}
		}
	}
		
		if (c.playerItemsN[slot] != 0 && itemId != -1
				&& c.playerItems[slot] == itemId + 1) {
			if (droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				Server.itemHandler.createGroundItem(c, itemId, c.getX(),
						c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			} else {
				c.sendMessage("This items cannot be dropped.");
				}
			}
		}
	}