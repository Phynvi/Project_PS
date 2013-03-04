package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.content.music.Music;
import server.game.players.Client;
import server.game.players.PacketType;
import server.world.GlobalDropsHandler;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		GlobalDropsHandler.load(c);
		
		if (Config.SOUND && c.musicOn) {
            Music.playMusic(c);
		}

		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
			c.getPA().removeObjects();
		}

	}

}
