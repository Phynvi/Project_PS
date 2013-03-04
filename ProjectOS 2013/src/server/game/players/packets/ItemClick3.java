package server.game.players.packets;

import server.game.items.HandleEmpty;
import server.game.items.Teles;
import server.game.players.Client;
import server.game.players.PacketType;
import server.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		
		if (HandleEmpty.canEmpty(itemId)) {
            HandleEmpty.handleEmptyItem(c, itemId, HandleEmpty.filledToEmpty(itemId));
            return;
		}	

		switch (itemId) {

		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.itemUsing = itemId;
			Teles.ROD(c);
		break;
		
		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.itemUsing = itemId;
			Teles.AOG(c);
		break;
		
		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			c.itemUsing = itemId;
			Teles.GN(c);
		break;

		default:
			
			if (c.playerRights == 3)
				Misc.println(c.playerName + " - Item3rdOption: " + itemId
						+ " : " + itemId11 + " : " + itemId1);
			break;
		}

	}

}
