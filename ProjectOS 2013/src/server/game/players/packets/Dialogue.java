package server.game.players.packets;

import server.game.content.tutorial.TutorialIsland;
import server.game.dialogues.FreeDialogues;
import server.game.dialogues.MemberDialogues;
import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.nextChat > 0) {
			FreeDialogues.handledialogue(c, c.nextChat, c.talkingNpc);
			MemberDialogues.handledialogue(c, c.nextChat, c.talkingNpc);
		} else {
			if (c.getNewComersSide().isInTutorialIslandStage()) {
				c.getNewComersSide().setTutorialIslandStage(c, TutorialIsland.getTutorialIslandStage() + 1, true);
				c.getPA().removeAllWindows();
				c.getNewComersSide().sendGiveItemsInstructor(c);
		} else {
			FreeDialogues.handledialogue(c, 0, -1);
			MemberDialogues.handledialogue(c, 0, -1);
			}
		}	
	}
}
