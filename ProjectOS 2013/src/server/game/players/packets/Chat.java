package server.game.players.packets;

import server.Connection;
import server.game.content.tutorial.TutorialIsland;
import server.game.players.Client;
import server.game.players.PacketType;
import server.util.Misc;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
        ReportHandler.addText(c.playerName, c.getChatText(), packetSize - 2);
        String word = Misc.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
        if (System.currentTimeMillis() < c.muteTime) {
        	c.sendMessage("You are muted for for breaking the rules.");
        	c.sendMessage("Please read over the rules to prevent doing so again.");
        	return;
        } else {
        	c.muteTime = 0;
        }
        if (word.contains(c.playerPass)) {
            c.sendMessage("Please don't give out your password to other players.");
            c.sendMessage("If you are receiving this in error, change your password.");
            c.sendMessage("typing ::changepass (newpassword).");
            return;
        }
        if (TutorialIsland.getTutorialIslandStage() <= 15 && c.playerRights < 2) {
    		c.sendMessage("Your messages do not appear for other players in tutorial island.");
    		return;
    	}
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);
	}	
}

