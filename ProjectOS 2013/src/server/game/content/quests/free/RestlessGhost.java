package server.game.content.quests.free;

import server.game.players.Client;

/**
 * Restless Ghost
 * @author Andrew
 */

public class RestlessGhost {

	Client c;
	
	public RestlessGhost(Client c) {
		this.c = c;
	}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Restless Ghost", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.restGhost == 0) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Father Aereck in", 8147);
			c.getPA().sendFrame126("Lumbridge", 8148);
			c.getPA().sendFrame126("Minimum Requirments:", 8149);
			c.getPA().sendFrame126("None.", 8150);
		} else if(c.restGhost == 1) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("@str@I've talked to Father Aereck", 8147);
			c.getPA().sendFrame126("I should speak to Father Urhey", 8148);
		} else if(c.restGhost == 2) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("@str@I've talked Father Urhey", 8147);
			c.getPA().sendFrame126("@str@He gave me a amulet", 8148);
			c.getPA().sendFrame126("I should speak to the ghost", 8149);
		} else if(c.restGhost == 3) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Ghost", 8147);
			c.getPA().sendFrame126("I should travel to the wizards tower and kill the skeleton", 8148);
			c.getPA().sendFrame126("I should find the ghosts skull", 8149);
		} else if(c.restGhost == 4) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("@str@I've found the skull", 8147);
			c.getPA().sendFrame126("@str@I killed the skeleton", 8148);
			c.getPA().sendFrame126("I should travel back to the ghost", 8149);
		} else if(c.restGhost == 5) {
			c.getPA().sendFrame126("Restless Ghost", 8144);
			c.getPA().sendFrame126("@str@I've set the skull in the coffin", 8147);
			c.getPA().sendFrame126("@str@I've freed the ghost.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 125 Prayer Exp.", 8151);
			c.getPA().sendFrame126("And 1 Quest Point", 8152);
			c.getPA().sendFrame126("", 8152);
		}
		c.getPA().showInterface(8134);
	}


}