package server.game.content.quests.free;

import server.game.players.Client;

/**
 * Witch's Potion
 * @author Andrew
 */

public class WitchsPotion {

	Client c;
	
	public WitchsPotion(Client c) {
		this.c = c;
	}
	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Witch's Potion", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.witchspot == 0) {
			c.getPA().sendFrame126("Witch's Potion", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Hetty", 8147);
			c.getPA().sendFrame126("Rimmington.", 8148);
			c.getPA().sendFrame126("Minimum Requirments:", 8149);
			c.getPA().sendFrame126("None.", 8150);
		} else if(c.witchspot == 1) {
			c.getPA().sendFrame126("Witch's Potion", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Hetty", 8147);
			c.getPA().sendFrame126("I've agreed to get her the ingredients", 8148);
		} else if(c.witchspot == 2) {
			c.getPA().sendFrame126("Witch's Potion", 8144);
			c.getPA().sendFrame126("@str@I have all the ingredients", 8147);
			c.getPA().sendFrame126("I should talk to hetty.", 8148);
			c.getPA().sendFrame126("", 8149);
		} else if(c.witchspot == 3) {
			c.getPA().sendFrame126("Witch's Potion", 8144);
			c.getPA().sendFrame126("@str@I've Talked to Hetty", 8147);
			c.getPA().sendFrame126("@str@I drank from the Cauldron", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 325 Magic Exp.", 8151);
			c.getPA().sendFrame126("And 1 Quest Point", 8152);
			c.getPA().sendFrame126("", 8152);
		}
		c.getPA().showInterface(8134);
	}


}