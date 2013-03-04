package server.game.content.quests.member;

import server.game.players.Client;

/**
 * Gertrudes Cat
 * @author Andrew
 */

public class GertrudesCat {

	Client c;
	
	public GertrudesCat(Client c) {
		this.c = c;
	}
	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Gertrudes Cat", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.gertCat == 0) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Gertrude in", 8147);
			c.getPA().sendFrame126("Varrock.", 8148);
			c.getPA().sendFrame126("Minimum Requirments:", 8149);
			c.getPA().sendFrame126("5 Fishing.", 8150);
		} else if(c.gertCat == 1) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@I've talked to Gertrude", 8147);
			c.getPA().sendFrame126("I should speak to Wilough and Shilop.", 8148);
		} else if(c.gertCat == 2) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@I've talked to Wilough and Shilop", 8147);
			c.getPA().sendFrame126("I gave him 100 coins", 8148);
			c.getPA().sendFrame126("I should speak to the bartender", 8149);
		} else if(c.gertCat == 3) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Gertrudes Cat", 8147);
			c.getPA().sendFrame126("@str@She seemed mad so I gave her", 8148);
			c.getPA().sendFrame126("@str@a bucket of milk", 8149);
		} else if(c.gertCat == 4) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@The cat is still mad", 8147);
			c.getPA().sendFrame126("@str@So i gave it some", 8148);
			c.getPA().sendFrame126("@str@seasoned samon.", 8149);
		} else if(c.gertCat == 5) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@The cat seems to be mad", 8147);
			c.getPA().sendFrame126("@str@because she can't find her kittens.", 8148);
			c.getPA().sendFrame126("@str@I should go in the lumberyard and", 8149);
			c.getPA().sendFrame126("@str@check it out.", 8150);
		} else if(c.gertCat == 6) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@I gave gertrudes cat", 8147);
			c.getPA().sendFrame126("@str@her kittens", 8148);
			c.getPA().sendFrame126("@str@and she now seems happy.", 8149);
		} else if(c.gertCat == 7) {
			c.getPA().sendFrame126("Gertrudes Cat", 8144);
			c.getPA().sendFrame126("@str@I helped gertrude with her", 8147);
			c.getPA().sendFrame126("@str@Cat so she awarded me.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8149);
			c.getPA().sendFrame126("As a reward, I gained 1525 Cooking exp.", 8150);
			c.getPA().sendFrame126("A kitten! And the ability to raise cats.", 8150);
			c.getPA().sendFrame126("And 1 Quest Point", 8151);
			c.getPA().sendFrame126("", 8152);
		}
		c.getPA().showInterface(8134);
	}


}