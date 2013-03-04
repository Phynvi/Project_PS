package server.game.players.packets;

/**
 * @author Ryan / Lmctruck30
 */


import server.game.content.skills.cooking.Cooking;
import server.game.content.traveling.Desert;
import server.game.items.Fillables;
import server.game.items.UseItem;
import server.game.players.Client;
import server.game.players.PacketType;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*
		 * a = ? b = ?
		 */

		c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readSignedWordBigEndian();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		c.getInStream().readUnsignedWord();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		c.cookingCoords[0] = objectX;
		c.cookingCoords[1] = objectY;
		c.turnPlayerTo(objectX, objectY);
		c.objectX = objectX;
		c.objectY = objectY;
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		
		switch (objectId) {
		case 12269:
		case 2732:
		case 114:
		Cooking.startCooking(c, itemId, objectId);
		break;
		
		case 2783:
			c.getSmithingInt().showSmithInterface(itemId);
		break;
		
		case 2452:
		case 2453:
		case 2454:
		case 2455:
		case 2456:
		case 2457:
		case 2458:
		case 2459:
		case 2460:
		case 2461:
		case 2462:
			c.getRC().enterAltar(objectId, itemId);
		break;
		
		case 2670:
			Desert.cutCactus(c);
		break;
		
		/*/case 2242:
		case 2243:
		case 2241:
		case 2240:
		case 2152:
		if (itemId == 567) {
			Charge.activeateObleisk(c, objectId);
		}
		break;/*/
		
		case 10093:
		if (c.getItems().playerHasItem(1927, 1)) {
			c.turnPlayerTo(c.objectX, c.objectY);
			c.startAnimation(883);
			c.getItems().addItem(2130, 1);
			c.getItems().deleteItem(1927, 1);
			c.getPA().addSkillXP(18, c.playerCooking);
		} else {
			c.sendMessage("You need a bucket of milk to do this.");
		}
		break;
	}
		
		
		if (itemId == 1710 || itemId == 1708 || itemId == 1706 || itemId == 1704 && objectId == 2638) { //glory
			int amount = (c.getItems().getItemCount(1710) + c.getItems().getItemCount(1708) + c.getItems().getItemCount(1706) + c.getItems().getItemCount(1704));
			int[] glories = {1710, 1708, 1706, 1704};
			for (int i : glories) {
				c.getItems().deleteItem(i, c.getItems().getItemCount(i));
			}
			c.startAnimation(832);
			c.getItems().addItem(1712, amount);
		}
		
		if (itemId == 954 && objectId == 3827) {
			c.getPA().movePlayer(3484, 9509, 2);
		}
		
		if (itemId == 1737 && objectId == 2644) {
			int amount = (c.getItems().getItemCount(1759));
			int[] spin = {1737};
			for (int i : spin) {
				c.getItems().deleteItem(i, c.getItems().getItemCount(i));
			}
			c.startAnimation(883);
			c.getItems().addItem(1759, amount);
		}
			
		if (itemId == 1777 && objectId == 2644) {
			int amount = (c.getItems().getItemCount(1777));
			int[] spin = {1777};
			for (int i : spin) {
				c.getItems().deleteItem(i, c.getItems().getItemCount(i));
			}
			c.startAnimation(883);
			c.getItems().addItem(1779, amount);
		}
			
		if (Fillables.canFill(itemId, objectId) && c.getItems().playerHasItem(itemId)) {
	        c.getItems().deleteItem(itemId, 1);
	        c.getItems().addItem(Fillables.counterpart(itemId), 1);
	        c.sendMessage(Fillables.fillMessage(itemId, objectId));
	        c.startAnimation(832);
	        return;
		}
			
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);

	}

}
