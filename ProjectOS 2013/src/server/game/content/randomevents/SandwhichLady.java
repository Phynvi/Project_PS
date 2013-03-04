package server.game.content.randomevents;

import server.game.players.Client;
import server.util.Misc;

/**
 * Sandwhich
 * @author Andrew
 */

public class SandwhichLady extends EventHandler {

	public static void handleOptions(Client c, int actionbuttonId) {
		switch (actionbuttonId) {
		case 63013:
			if(c.pieSelect == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(2323, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63014:
			if(c.kebabSelect == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(1971, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63015:
			if(c.chocSelect == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(1973, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63009:
			if(c.bagelSelect == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(6961, 10);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63010:
			if(c.triangleSandwich == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(6962, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63011:
			if(c.squareSandwich == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(6965, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
			break;

		case 63012:
			if(c.breadSelect == 1 && c.eventTimer + 1800 > System.currentTimeMillis()) {
				c.getPA().closeAllWindows();
				c.getItems().addItem(2309, 1);
				c.sendMessage("Congratulations, you have completed the random event!");
				c.eventTimer = System.currentTimeMillis();
			} else {
				c.sendMessage("You have chosen the wrong Item!");
				failEvent(c);
			}
		}
	}

	public static void randomEvent(Client c) {
			c.getPA().sendFrame126(" ", 16131);
			c.getPA().showInterface(16135);		
			int randomMessage = Misc.random(6);
			switch (randomMessage) {
			case 0:
				c.getPA().sendFrame126("Please select the pie.", 16145);
				c.pieSelect = 1;
				break;
			case 1:
				c.getPA().sendFrame126("Please select the kebab.", 16145);
				c.kebabSelect = 1;
				break;
			case 2:
				c.getPA().sendFrame126("Please select the chocolate.", 16145);
				c.chocSelect = 1;
				break;
			case 3:
				c.getPA().sendFrame126("Please select the bagel.", 16145);
				c.bagelSelect = 1;
				break;
			case 4:
				c.getPA().sendFrame126("Please select the triangle sandwich.", 16145);
				c.triangleSandwich = 1;
				break;
			case 5:
				c.getPA().sendFrame126("Please select the square sandwich.", 16145);
				c.squareSandwich = 1;
				break;
			case 6:
				c.getPA().sendFrame126("Please select the bread.", 16145);
				c.breadSelect = 1;
				break;
			}
		}
	}
