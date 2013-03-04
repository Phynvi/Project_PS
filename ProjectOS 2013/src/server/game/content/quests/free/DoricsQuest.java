package server.game.content.quests.free;

import server.game.players.Client;

/**
 * Doric's Quest
 * Made by Andrew
 */

public class DoricsQuest {

	Client c;
	
	public DoricsQuest(Client c) {
		this.c = c;
	}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Dorics Quest", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.doricQuest == 0) {
			c.getPA().sendFrame126("Dorics Quest", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to doric", 8147);
			c.getPA().sendFrame126("Northwest of falador.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("Recommended Levels: 15 Mining", 8150);
		} else if(c.doricQuest == 1) {
			c.getPA().sendFrame126("Dorics Quest", 8144);
			c.getPA().sendFrame126("@str@I've talked to the doric.", 8147);
			c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
			if(c.getItems().playerHasItem(434,6)) {
			c.getPA().sendFrame126("@str@6 Clay", 8149);
			} else {
			c.getPA().sendFrame126("@red@6 Clay", 8149);
			}
			if(c.getItems().playerHasItem(436,4)) {
			c.getPA().sendFrame126("@str@4 Copper", 8150);
			} else {
				c.getPA().sendFrame126("@red@4 Copper", 8150);	
			}
			if(c.getItems().playerHasItem(440,2)) {
			c.getPA().sendFrame126("@str@2 Iron ore", 8151);
			} else {
				c.getPA().sendFrame126("@red@2 Iron ore", 8151);
			}
		} else if(c.doricQuest == 2) {
			c.getPA().sendFrame126("Dorics Quest", 8144);
			c.getPA().sendFrame126("@str@I talked to the doric.", 8147);
			c.getPA().sendFrame126("@str@I gave the doric his items.", 8148);
			c.getPA().sendFrame126("I should go speak to the doric.", 8149);
		} else if(c.doricQuest == 3) {
			c.getPA().sendFrame126("Dorics Quest", 8144);
			c.getPA().sendFrame126("@str@I talked to the doric.", 8147);
			c.getPA().sendFrame126("@str@I gave him his items.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 26000 Mining Exp", 8151);
			c.getPA().sendFrame126("180 Coins", 8152);
			c.getPA().sendFrame126("And 1 Quest Point.", 8152);
		}
		c.getPA().showInterface(8134);
	}


}