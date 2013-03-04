package server.game.content.questhandling;

import server.game.players.Client;

/*
 * @author Andrew
 */

public class Stages {
	
	public static void stage(Client c) { //stages
	c.getPA().sendFrame126("Quest Points: "+c.questPoints+" ", 640);
	if(c.pirateTreasure == 0) {
		c.getPA().sendFrame126("Pirate's Treasure", 7341);
	} else if(c.pirateTreasure == 5) {
		c.getPA().sendFrame126("@gre@Pirate's Treasure", 7341);
	} else {
		c.getPA().sendFrame126("@yel@Pirate's Treasure",7341);
	}
	if(c.witchspot == 0) {
		c.getPA().sendFrame126("Witch's Potion", 7348);
	} else if(c.witchspot == 2) {
		c.getPA().sendFrame126("@gre@Witch's Potion", 7348);
	} else {
		c.getPA().sendFrame126("@yel@Witch's Potion",7348);
	}
	if(c.romeojuliet == 0) {
		c.getPA().sendFrame126("Romeo and Juliet", 7343);
	} else if (c.romeojuliet < 9) {
		c.getPA().sendFrame126("@yel@Romeo and Juliet", 7343);
	} else if(c.romeojuliet >= 9) {
		c.getPA().sendFrame126("@gre@Romeo and Juliet", 7343);
	}
	if(c.vampSlayer == 0) {
		c.getPA().sendFrame126("Vampyre Slayer", 7347);
	} else if(c.vampSlayer == 4) {
		c.getPA().sendFrame126("@gre@Vampyre Slayer", 7347);
	} else {
		c.getPA().sendFrame126("@yel@Vampyre Slayer", 7347);
	}
	if(c.doricQuest == 0) {
		c.getPA().sendFrame126("Doric's Quest", 7336);
	} else if(c.doricQuest == 3) {
		c.getPA().sendFrame126("@gre@Doric's Quest", 7336);
	} else {
		c.getPA().sendFrame126("@yel@Doric's Quest", 7336);
	}
	if(c.restGhost == 0) {
		c.getPA().sendFrame126("Restless Ghost", 7337);
	} else if(c.restGhost == 5) {
		c.getPA().sendFrame126("@gre@Restless Ghost", 7337);
	} else {
		c.getPA().sendFrame126("@yel@Restless Ghost", 7337);
	}
	if(c.impsC == 0) {
		c.getPA().sendFrame126("Imp Catcher", 7340);
	} else if(c.impsC == 1) {
		c.getPA().sendFrame126("@yel@Imp Catcher", 7340);
	} else if(c.impsC == 2) {
		c.getPA().sendFrame126("@gre@Imp Catcher", 7340);
	}
	if(c.gertCat == 0) {
		c.getPA().sendFrame126("Gertrudes Cat", 7360);
	} else if(c.gertCat == 7) {
		c.getPA().sendFrame126("@gre@Gertrudes Cat", 7360);
	} else {
		c.getPA().sendFrame126("@yel@Gertrudes Cat", 7360);
	}
	if(c.sheepShear == 0) {
		c.getPA().sendFrame126("Sheep Shearer", 7344);
	} else if(c.sheepShear == 2) {
		c.getPA().sendFrame126("@gre@Sheep Shearer", 7344);
	} else {
		c.getPA().sendFrame126("@yel@Sheep Shearer", 7344);
	}
	if (c.runeMist == 0) {
		c.getPA().sendFrame126("Rune Mysteries", 7335);
	} else if (c.runeMist == 4) {
		c.getPA().sendFrame126("@gre@Rune Mysteries", 7335);
	} else {
		c.getPA().sendFrame126("@yel@Rune Mysteries", 7335);
	}
	if(c.cookAss == 0) {
		c.getPA().sendFrame126("Cook's Assistant", 7333);
	} else if(c.cookAss == 3) {
		c.getPA().sendFrame126("@gre@Cook's Assistant", 7333);
	} else {
		c.getPA().sendFrame126("@yel@Cook's Assistant", 7333);
		}
	}
}