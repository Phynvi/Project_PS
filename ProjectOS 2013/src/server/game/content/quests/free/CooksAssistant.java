package server.game.content.quests.free;

import server.game.players.Client;


/**
 * Credits to Andrew
 */

public class CooksAssistant {
	
	Client c;
	
	public CooksAssistant(Client c) {
		this.c = c;
	}

	private static final int EGG = 1944;
	private static final int MILK = 1927;
	private static final int FLOUR = 1933;

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Cook's Assistant", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.cookAss == 0) {
			c.getPA().sendFrame126("Cook's Assistant", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to the Cook in the", 8147);
			c.getPA().sendFrame126("Lumbridge Castle kitchen.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("There are no minimum requirments.", 8150);
		} else if(c.cookAss == 1) {
			c.getPA().sendFrame126("Cook's Assistant", 8144);
			c.getPA().sendFrame126("@str@I've talked to the cook.", 8147);
			c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
			if(c.getItems().playerHasItem(EGG, 1)) {
			c.getPA().sendFrame126("@str@1 egg", 8149);
			} else {
			c.getPA().sendFrame126("@red@1 egg", 8149);
			}
			if(c.getItems().playerHasItem(MILK, 1)) {
			c.getPA().sendFrame126("@str@1 bucket of milk", 8150);
			} else {
				c.getPA().sendFrame126("@red@1 bucket of milk", 8150);	
			}
			if(c.getItems().playerHasItem(FLOUR, 1)) {
			c.getPA().sendFrame126("@str@1 heap of flour", 8151);
			} else {
				c.getPA().sendFrame126("@red@1 pot of flour", 8151);
			}
		} else if(c.cookAss == 2) {
			c.getPA().sendFrame126("Cook's Assistant", 8144);
			c.getPA().sendFrame126("@str@I talked to the cook.", 8147);
			c.getPA().sendFrame126("@str@I gave the cook his items.", 8148);
			c.getPA().sendFrame126("I should go speak to the cook.", 8149);
		} else if(c.cookAss == 3) {
			c.getPA().sendFrame126("Cook's Assistant", 8144);
			c.getPA().sendFrame126("@str@I talked to the cook.", 8147);
			c.getPA().sendFrame126("@str@I gave him his items.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 150 Cooking Experience.", 8151);
		}
		c.getPA().showInterface(8134);
	}


}