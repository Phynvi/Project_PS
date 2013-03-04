package server.game.players.packets;

import server.game.content.questhandling.FreeRewards;
import server.game.content.skills.core.Herblore;
import server.game.content.skills.core.Prayer;
import server.game.dialogues.FreeDialogues;
import server.game.items.ExperienceLamp;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		
		switch (itemId) {
		
			case 2528:
				ExperienceLamp.showInterface(c);
		    break;
            
			case 8007:
				if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8007, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(3213, 3424, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
				
			case 8008:
				if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8008, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(3222, 3218, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
			
			case 8009:
				if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8009, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(2965, 3379, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
			
			case 8010:
				if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8010, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(2757, 3477, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
			
			case 8011:
				if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8011, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(2661, 3305, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
			
				case 8012:
					if(System.currentTimeMillis() - c.buryDelay > 1500) {
					c.getItems().deleteItem(8012, 1);
					c.sendMessage("You break the teleport tab.");
					c.startAnimation(4731);
					c.gfx0(678);
					c.getPA().movePlayer(2549, 3112, 0);
					c.buryDelay = System.currentTimeMillis();
				}
				break;
					
				case 5070:
					c.getItems().deleteItem(5070, 1);
					c.getItems().addItem(5076, 1);
				break;
				
				case 5071:
					c.getItems().deleteItem(5071, 1);
					c.getItems().addItem(5078, 1);
				break;
				
				case 5072:
					c.getItems().deleteItem(5072, 1);
					c.getItems().addItem(5077, 1);
				break;
				
				case 5073:
					c.getItems().deleteItem(5073, 1);
					c.getItems().addItem(5299, 1);
				break;
				
				case 5074:
					c.getItems().deleteItem(5074, 1);
					c.getItems().addItem(1636, 1);
				break;
		
				case 1891:
					c.getItems().addItem(1893, 1);
					c.getItems().deleteItem(1891, 1);
				break;
				
				case 1893:
					c.getItems().addItem(1895, 1);
					c.getItems().deleteItem(1893, 1);
				break;
				
				case 1895:
					c.getItems().deleteItem(1895, 1);
				break;
					
				case 2297:
					c.getItems().addItem(2299, 1);
					c.getItems().deleteItem(2297, 1);
				break;
				
				case 2299:
					c.getItems().deleteItem(2299, 1);
					break;
					
				case 2301:
					c.getItems().addItem(2303, 1);
					c.getItems().deleteItem(2301, 1);
				break;
				
				case 2303:
					c.getItems().deleteItem(2303, 1);
				break;
					
				case 2293:
					c.getItems().addItem(2295, 1);
					c.getItems().deleteItem(2293, 1);
				break;
				
				case 2295:
					c.getItems().deleteItem(2295, 1);
				break;
				
				case 2520:
					c.forcedChat("Come on Dobbin, we can win the race!");
					c.startAnimation(918);
				break;
				case 2522:
					c.forcedChat("Come on Dobbin, we can win the race!");
					c.startAnimation(919);
				break;
				case 2524:
					c.forcedChat("Hi-ho Silver, and away!");
					c.startAnimation(920);
				break;
				case 2526:
					c.forcedChat("Neahhhyyy! Giddy-up horsey!");
					c.startAnimation(921);
				break;
				
				case 405:
					c.getItems().addItem(995, 450);
					c.getItems().addItem(1639, 1);
					c.getItems().addItem(1635, 1);
					c.getItems().deleteItem(405, 1);
				break;
				
				case 433:
					c.getDH().sendStatement2(c, "Visit the city of the white knights.", "In the park saradomin points to the X that marks the spot.");
				break;
			}
		
		Prayer.buryBone(c, itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId, itemSlot);
		if (Herblore.isHerb(c, itemId))
			Herblore.cleanTheHerb(c, itemId);
		if (itemId == 952) {
			if (c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if (c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if (c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if (c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if (c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if (c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			} else if (c.absX == 2999 && c.absY == 3382) {
				FreeDialogues.handledialogue(c, 1007, c.npcType);
			} else if (c.absX == 2996 && c.absY == 3381) {
				FreeDialogues.handledialogue(c, 1007, c.npcType);
			} else if (c.absX == 2999 && c.absY == 3383 && c.pirateTreasure == 4) {
				NPCHandler.spawnNpc(c, 1217, c.getX(), c.getY(), 0, 0, 25, 5, 25, 25, true, true);
				c.getDH().sendNpcChat1("First moles, now this! Take this, vanda!", c.talkingNpc, "Gardener");
				c.pirateTreasure = 5;
			} else if (c.absX == 2999 && c.absY == 3383 && c.pirateTreasure == 5) {
				FreeRewards.pirateFinish(c);
			}
		}
	}

}
