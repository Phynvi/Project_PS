package server.game.content.quests.free;

import server.game.players.Client;

/*
 * Made by Andrew
 * 
 */

public class VampyreSlayer {

	Client c;
	
	public VampyreSlayer(Client c) {
		this.c = c;
	}

	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Vampyre Slayer", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.vampSlayer == 0) {
			c.getPA().sendFrame126("Vampyre Slayer", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Morgan in", 8147);
			c.getPA().sendFrame126("Draynor Village.", 8148);
			c.getPA().sendFrame126("Minimum Requirments:", 8149);
			c.getPA().sendFrame126("Be able to kill a level 37 monster.", 8150);
		} else if(c.vampSlayer == 1) {
			c.getPA().sendFrame126("Vampyre Slayer", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Morgan", 8147);
			c.getPA().sendFrame126("I should speak to Doctor Harlow", 8148);
		} else if(c.vampSlayer == 2) {
			c.getPA().sendFrame126("Vampyre Slayer", 8144);
			c.getPA().sendFrame126("@str@I've talked to Doctor Harlow", 8147);
			c.getPA().sendFrame126("I need to him a beer.", 8148);
			c.getPA().sendFrame126("I should speak to the bartender", 8149);
		} else if(c.vampSlayer == 3) {
			c.getPA().sendFrame126("Vampyre Slayer", 8144);
			c.getPA().sendFrame126("@str@I've talked to the bartender", 8147);
			c.getPA().sendFrame126("@str@I gave Doctor Harlow the beer", 8148);
			c.getPA().sendFrame126("@str@Doctor Harlow talked to me and", 8149);
			c.getPA().sendFrame126("@str@Gave me a stake and hammer.", 8150);
			c.getPA().sendFrame126("Get everything you need and go to Draynor Village.", 8151);
			c.getPA().sendFrame126("Begin your battle.", 8152);
		} else if(c.vampSlayer == 4) {
			c.getPA().sendFrame126("Vampyre Slayer", 8144);
			c.getPA().sendFrame126("@str@I've killed the Vampire", 8147);
			c.getPA().sendFrame126("@str@I've Talked to Morgan", 8148);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
			c.getPA().sendFrame126("As a reward, I gained 4825 Attack Exp.", 8151);
			c.getPA().sendFrame126("And 1 Quest Point", 8152);
			c.getPA().sendFrame126("", 8152);
		}
		c.getPA().showInterface(8134);
	}


}