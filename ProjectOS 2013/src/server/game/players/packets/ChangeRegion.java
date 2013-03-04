package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;

public class ChangeRegion implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getPA().removeObjects();
		Server.objectHandler.updateObjects(c);
	}

}
