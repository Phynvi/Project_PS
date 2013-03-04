package server.game.content.quests.free;

import server.game.players.Client;


/*
 * Credits Andrew
 */

public class RuneMysteries {

	
	Client c;
	
	public RuneMysteries(Client c) {
		this.c = c;
	}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Rune Mysteries", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.runeMist == 0) {
			c.getPA().sendFrame126("Rune Mysteries", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Duke Horiaco", 8147);
			c.getPA().sendFrame126("who is located on the 2nd floor of the Lumbridge Castle.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("There are no minimum requirments.", 8150);
		} else if(c.runeMist == 1) {
			c.getPA().sendFrame126("Rune Mysteries", 8144);
			c.getPA().sendFrame126("@str@I've talked to the duke", 8147);
			c.getPA().sendFrame126("I should take the talisman to the Head Wizard.", 8148);
		} else if(c.runeMist == 2) {
			c.getPA().sendFrame126("Rune Mysteries", 8144);
			c.getPA().sendFrame126("@str@I've talked to Sedridor", 8147);
			c.getPA().sendFrame126("@str@I gave him the talisman", 8148);
			c.getPA().sendFrame126("I should bring the notes to Aubury.", 8149);
		} else if(c.runeMist == 3) {
			c.getPA().sendFrame126("Rune Mysteries", 8144);
			c.getPA().sendFrame126("@str@I've talked to Aubury.", 8147);
			c.getPA().sendFrame126("@str@I gave him the notes", 8148);
			c.getPA().sendFrame126("I should go back to the wizard tower", 8149);
		} else if(c.runeMist == 4) {
			c.getPA().sendFrame126("Rune Mysteries", 8144);
			c.getPA().sendFrame126("@str@I talked to Sedridor", 8147);
			c.getPA().sendFrame126("@str@I gave him his items.", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 1 Quest point", 8151);
			c.getPA().sendFrame126("And an air talisman.", 8152);
		}
		c.getPA().showInterface(8134);
	}


}