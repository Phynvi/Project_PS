package server.game.content.quests.free;

import server.game.players.Client;


/*
 * Credits to Andrew
 */

public class PiratesTreasure {
	
	Client c;
	
	public PiratesTreasure(Client c) {
		this.c = c;
	}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Pirate's Treasure", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.pirateTreasure == 0) {
			c.getPA().sendFrame126("Pirate's Treasure", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Redbeard Frank in", 8147);
			c.getPA().sendFrame126("Port Sarim", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("There are no minimum requirments.", 8150);
		} else if(c.pirateTreasure == 1) {
			c.getPA().sendFrame126("Pirate's Treasure", 8144);
			c.getPA().sendFrame126("@str@I've talked to Redbeard.", 8147);
			c.getPA().sendFrame126("He wants me to get him some rum", 8148);
		} else if(c.pirateTreasure == 2) {
			c.getPA().sendFrame126("Pirate's Treasure", 8144);
			c.getPA().sendFrame126("@str@I talked to Redbeard.", 8147);
			c.getPA().sendFrame126("@str@I found a way to get the rum", 8148);
			c.getPA().sendFrame126("I should get the rum and return to Redbeard.", 8149);
		} else if(c.pirateTreasure == 3) {
			c.getPA().sendFrame126("Pirate's Treasure", 8144);
			c.getPA().sendFrame126("@str@I talked to Redbeard.", 8147);
			c.getPA().sendFrame126("@str@I found a way to get the rum", 8148);
			c.getPA().sendFrame126("@str@I gave him the rum", 8149);
			c.getPA().sendFrame126("He told me I need to look at the chest in", 8149);
			c.getPA().sendFrame126("The blue moon inn", 8150);
		} else if(c.pirateTreasure == 4) {
			c.getPA().sendFrame126("Pirate's Treasure", 8144);
			c.getPA().sendFrame126("@str@I talked to Redbeard.", 8147);
			c.getPA().sendFrame126("@str@I found a way to get the rum", 8148);
			c.getPA().sendFrame126("@str@I gave him the rum", 8149);
			c.getPA().sendFrame126("@str@I looked in the chest", 8149);
			c.getPA().sendFrame126("I need to go to falador and find the casket", 8150);
		} else if(c.pirateTreasure == 5) {
			c.getPA().sendFrame126("@str@I talked to Redbeard.", 8147);
			c.getPA().sendFrame126("@str@I found a way to get the rum", 8148);
			c.getPA().sendFrame126("@str@I gave him the rum", 8149);
			c.getPA().sendFrame126("@str@I looked in the chest", 8149);
			c.getPA().sendFrame126("@str@I went to falador and found the casket", 8150);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8151);
			c.getPA().sendFrame126("As a reward, I gained a casket.", 8152);
			c.getPA().sendFrame126("2 Quest Points.", 8153);
		}
		c.getPA().showInterface(8134);
	}


}