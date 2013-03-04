package server.game.content.quests.free;

import server.game.players.Client;

/**
* Romeo & Juliet
* @author Genesis
* @author Converted by andrew
*/

public class RomeoJuliet {

	Client c;
	
	public RomeoJuliet(Client c) {
		this.c = c;
	}
	
	public void showInformation() {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.getPA().sendFrame126("@dre@Romeo & Juliet", 8144);
		c.getPA().sendFrame126("", 8145);
		if(c.romeojuliet == 0) {
			c.getPA().sendFrame126("To start the quest, you should talk with Romeo", 8147);
			c.getPA().sendFrame126("found in Varrock Square.", 8148);
		} else if(c.romeojuliet == 1) {
			c.getPA().sendFrame126("@str@To start the quest, you should talk with Romeo", 8147);
			c.getPA().sendFrame126("@str@found in Varrock Square.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("Romeo has asked you to speak to Juliet for him", 8150);
			c.getPA().sendFrame126("and return to him, as she hasn't been responding to any", 8151);
			c.getPA().sendFrame126("his letters lately.", 8152);
		} else if(c.romeojuliet == 2) {
			c.getPA().sendFrame126("@str@To start the quest, you should talk with Romeo", 8147);
			c.getPA().sendFrame126("@str@found in Varrock Square.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("@str@Romeo has asked you to speak to Juliet for him", 8150);
			c.getPA().sendFrame126("@str@and return to him, as she hasn't been responding to any", 8151);
			c.getPA().sendFrame126("@str@his letters lately.", 8152);
			c.getPA().sendFrame126("", 8153);
			c.getPA().sendFrame126("You have spoken to Juliet who's been acting strange", 8154);
			c.getPA().sendFrame126("she gave you a message and asked you to leave", 8155);
			c.getPA().sendFrame126("Could this be for Romeo?", 8156);
		} else if(c.romeojuliet == 9) {
			c.getPA().sendFrame126("@str@To start the quest, you should talk with Romeo", 8147);
			c.getPA().sendFrame126("@str@found in Varrock Square.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("@str@Romeo has asked you to speak to Juliet for him", 8150);
			c.getPA().sendFrame126("@str@and return to him, as she hasn't been responding to any", 8151);
			c.getPA().sendFrame126("@str@his letters lately.", 8152);
			c.getPA().sendFrame126("", 8153);
			c.getPA().sendFrame126("@str@You have spoken to Juliet who's been acting strange", 8154);
			c.getPA().sendFrame126("@str@she gave you a message and asked you to leave", 8155);
			c.getPA().sendFrame126("@str@could this be for Romeo?", 8156);
		}
		c.getPA().showInterface(8134);
	}
	
}
