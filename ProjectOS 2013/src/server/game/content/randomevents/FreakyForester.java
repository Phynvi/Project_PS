package server.game.content.randomevents;

import server.game.content.tutorial.TutorialIsland;
import server.game.players.Client;
import server.util.Misc;

public class FreakyForester extends EventHandler {

	public static void teleportToLocation(final Client c) {
		if (TutorialIsland.getTutorialIslandStage() <= 15)
			return;
		c.teleportToX = 2602;
		c.teleportToY = 4775;
		c.lastX = c.absX;
		c.lastY = c.absY;
		c.lastH = c.heightLevel;
		c.sendMessage("Talk to the freaky forester to get out.");
	}

	private static String[] pheasant = {
		"one", "two", "three", "four",
	};

	public static String getPheasant(Client c) {
		if(c.getPheasent < 0) {
			c.getPheasent = Misc.random(3);
		}
		return pheasant[c.getPheasent] +" tailed";
	}

	public static void leaveArea(Client c) {
		if(c.killedPheasant[c.getPheasent]) {
			c.getPA().movePlayer(c.lastX, c.lastY, c.lastH);
			c.cantTeleport = false;
			c.sendMessage("Congratulations, you've completed the freaky forester event!");
			c.getItems().addItem(6180 + Misc.random(2), 1);
		} else {
			failEvent(c);
		}
		for(int i = 0; i < 4; i++ ) {
			c.killedPheasant[i] = false;
		}
		c.getPheasent = -1;
		c.canLeaveArea = false;
	}

	public static void killedPheasant(Client c, int p) {
		for(int i = 0; i < 4; i++ ) {
			c.killedPheasant[i] = false;
		}
		c.killedPheasant[p] = true;
	}

	public static boolean hasKilledPheasant(Client c) {
		for(int i = 0; i < 4; i++ ) {
			if(c.killedPheasant[i]) {
				return true;
			}
		}
		return false;
	}

}