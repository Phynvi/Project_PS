package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;

/**
 * @author JaydenD12/Jaydennn
 */

public class ItemOnPlayer implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;
		switch (itemId) {
		
		case 962:
		 Client o = (Client) PlayerHandler.players[playerId];
				c.gfx0(176);
				c.startAnimation(451);
				c.sendMessage("You pull the Christmas Cracker...");
				o.sendMessage("You pull the Christmas Cracker...");
				c.getItems().deleteItem(962, 1);
				if(Misc.random(3) == 1) {
					o.forcedText = "Yay I got the Cracker!";
					o.forcedChatUpdateRequired = true;
					o.getItems().addItem((1038 + Misc.random(5)*2), 1);
				} else {
					c.forcedText = "Yay I got the Cracker!";
					c.forcedChatUpdateRequired = true;	
					c.getItems().addItem((1038 + Misc.random(5)*2), 1);			
				}
				c.turnPlayerTo(o.absX, o.absY);
				break;
		default:
			c.sendMessage("Nothing interesting happens.");
			break;
		}

	}
}