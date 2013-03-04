package server.game.content.quests.free;

import server.game.players.Client;

/*
 * Made by Andrew
 * 
 */

public class SheepShearer {

	Client c;
	
	public SheepShearer(Client c) {
		this.c = c;
	}
	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Sheep Shearer", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.sheepShear == 0) {
			c.getPA().sendFrame126("Sheep Shearer", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Fred in", 8147);
			c.getPA().sendFrame126("Lumbridge.", 8148);
			c.getPA().sendFrame126("Minimum Requirments:", 8149);
			c.getPA().sendFrame126("None.", 8150);
		} else if(c.sheepShear == 1) {
			c.getPA().sendFrame126("Sheep Shearer", 8144);
			c.getPA().sendFrame126("@str@I've talked to fred", 8147);
			c.getPA().sendFrame126("I've agreed to get him some wool.", 8148);
			if(c.getItems().playerHasItem(1759, 20)) {
			c.getPA().sendFrame126("@str@Bal of Wool", 8149);
			} else {
			c.getPA().sendFrame126("@red@Ball of Wool", 8149);
			}
		} else if(c.sheepShear == 2) {
			c.getPA().sendFrame126("Sheep Shearer", 8144);
			c.getPA().sendFrame126("@str@I gave fred his wool", 8147);
			c.getPA().sendFrame126("@str@So he awarded me.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8149);
			c.getPA().sendFrame126("As a reward, 60 coins.", 8150);
			c.getPA().sendFrame126("150 crating exp", 8150);
			c.getPA().sendFrame126("And 1 Quest Point", 8151);
			c.getPA().sendFrame126("", 8152);
		}
		c.getPA().showInterface(8134);
	}


}