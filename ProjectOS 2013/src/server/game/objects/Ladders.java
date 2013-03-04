package server.game.objects;

import server.game.players.Client;

/**
 *@author Andrew
 */

public class Ladders {
		
		public static int EXTRA[][] = {{7057, 3093, 3251, 1, 828},
		{7056, 3089, 3251, 0, 827}};//for ladders that don't work with the system
		
		 	public static void extraLadders(Client c, final int objectClickId) {
		 	for (int[] data : EXTRA) {
		 	final int objectId = data[0];
		 	int x = data[1];
		 	int y = data[2];
		 	int h = data[3];
		 	int a = data[4];
		 	if (objectClickId == objectId) {
		 		c.getPA().movePlayer(x, y, h);
		 		c.startAnimation(a);
		 	break;
		 }
	}
}
		public static boolean manualLadders(Client c, int object) {
			for(int i = 0; i < EXTRA.length; i++) {
				if(object == EXTRA[i][0]) {
					return true;
				}
			}
			return false;
		}

	public static void climbUp(Client c) {
		switch (c.heightLevel) {
		case 0:
			c.getPA().movePlayer(c.absX, c.absY, 1);
			c.sendMessage("You climb up the ladder.");
			c.startAnimation(828);
		break;
		case 1:
			c.getPA().movePlayer(c.absX, c.absY, 2);
			c.sendMessage("You climb up the ladder.");
			c.startAnimation(828);
		break;
		case 2:
			c.getPA().movePlayer(c.absX, c.absY, 3);
			c.sendMessage("You climb up the ladder.");
			c.startAnimation(828);
		break;
		default:
			c.sendMessage("This height level is not supported.");
			System.out.println("Bug detected with climbing up ladders.");
		break;
	}
}
		
	public static void climbDown(Client c) {
		switch (c.heightLevel) {
		case 2:
			c.getPA().movePlayer(c.absX, c.absY, 1);
			c.sendMessage("You climb down the ladder.");
			c.startAnimation(827);
		break;
		case 3:
			c.getPA().movePlayer(c.absX, c.absY, 2);
			c.sendMessage("You climb down the ladder.");
			c.startAnimation(827);
		break;
		case 1:
			c.getPA().movePlayer(c.absX, c.absY, 0);
			c.sendMessage("You climb down the ladder.");
			c.startAnimation(827);
		break;
		default:
			c.sendMessage("This height level is not supported.");
			System.out.println("Bug detected with climbing down ladders.");
		break;
	}
}
	
	public static void handleLadder(Client c) {
			c.getDH().sendOption2("Climb Up", "Climb Down");
			c.dialogueAction = 700;	
		}
	}