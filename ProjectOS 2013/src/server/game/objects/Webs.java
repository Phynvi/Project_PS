package server.game.objects;

import server.Server;
import server.game.players.Client;
import server.util.Misc;

/**
 *@author Andrew
 */

public class Webs {
	
	public static int[] CLICKING_OBJECTS = {733};
	
	public static boolean webs(Client c, int object) {
		for(int i = 0; i < CLICKING_OBJECTS.length; i++) {
			if(object == CLICKING_OBJECTS[i]) {
				return true;
			}
		}
		return false;
	}

	public static void slashWeb(Client c, final int objectClickId, final int objectX, final int objectY) {
		if (System.currentTimeMillis() - c.webSlashDelay > 1800)
		if (Misc.random(3) > 0) {
			Server.objectHandler.createAnObject(c, -1, objectX, objectY);
			c.startAnimation(451);
			c.webSlashDelay = System.currentTimeMillis();
		} else {
			c.sendMessage("You fail to slash the web.");
		}
	}
}