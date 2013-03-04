package server.game.content.questhandling;

import server.game.players.Client;

/*
 * @author Andrew
 */

public class Buttons {

	public static void questButtons(final Client c, int buttonId) {
		switch (buttonId) {
			case 28165:
				c.getCA().showInformation();
			break;
			case 28167:
				c.getRM().showInformation();
			break;
			case 28168:
				c.getDC().showInformation();
			break;
			case 28169:
				c.getRG().showInformation();
			break;
			case 28172:
				c.getIMP().showInformation();
			break;
			case 28173:
				c.getPT().showInformation();
			break;
			case 28175:
				c.getRJ().showInformation();
			break;
			case 28176:
				c.getSS().showInformation();
			break;	
			case 28179:
				c.getVS().showInformation();
			break;
			case 28180:
				c.getWP().showInformation();
			break;
			case 28192:
				c.getGC().showInformation();
			break;
		}
	}
}
