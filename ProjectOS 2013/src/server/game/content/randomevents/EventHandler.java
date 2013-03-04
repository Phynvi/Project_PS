package server.game.content.randomevents;

import server.game.content.tutorial.TutorialIsland;
import server.game.players.Client;
import server.util.Misc;

/**
* Eventhandler 
* @author Andrew
*/

public class EventHandler {
	
	private final static int amt = 2;

	public static void resetEvent(Client c) {
		c.randomActions = 0;
	}

	private static int[][] failCoords = {
		{3333, 3333}, {3196, 3193}, {3084, 3549},
		{2974, 3346}, {2781, 3506}, {2810, 3508},
	};

	public static void failEvent(final Client c) {
		int loc = Misc.random(failCoords.length-1);
		c.teleportToX = failCoords[loc][0];
		c.teleportToY = failCoords[loc][1];
		c.sendMessage("You wake up in a strange location...");
		c.randomActions = 0;
	}

	public static void randomEvent(Client c) { //add all random events here
	if (TutorialIsland.getTutorialIslandStage() <= 15 || c.inFightCaves() || c.playerEquipment[c.playerWeapon] == 4024)// to do add no randoms during pest control
		return;
	switch (Misc.random(amt)) {
	case 0:
		SandwhichLady.randomEvent(c);
		c.randomActions = 0;
	break;
    case 1:
    	EvilChicken.spawnChicken(c);
    	c.randomActions = 0;
    break;
    case 2:
    	FreakyForester.teleportToLocation(c);
    	c.randomActions = 0;
	break;
	default:
		System.out.println("Error no random event called for " + c.playerName + "");
	break;
	}
}
	
	public static void randomEvents(Client c) { //call random actions each time you kill an npc, open an shop, teleport etc, open bank, click an npc, and after you beat a quest, do a skill. And they save on logout, and reset after random event.
		if (c.randomActions >= 1000) {
			randomEvent(c);
			c.getPA().closeAllWindows();
		} else {
			c.randomActions += 1;
		}	
	}
}