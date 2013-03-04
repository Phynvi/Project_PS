package server.game.content.random;

import server.Server;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.task.Task;
import server.util.Misc;

/**
 * Drop's Holiday Items
 * @author Andrew
 */

public class Holidays {
	
	public static void message(Client c) {
		for (final HolidayDrops d : HolidayDrops.values()) {
    		for (int j = 0; j < PlayerHandler.players.length; j++)
            if (PlayerHandler.players[j] != null) {
            Client p1 = (Client)PlayerHandler.players[j];
            if (d.getHoliday()) {
            p1.sendMessage("The " + d.getName() + " event has started.");
    	    dropItems(c);
            }
        }
    }
}
	
	public static void dropItems(final Client c) {
			Server.getTaskScheduler().schedule(new Task(2) {
			@Override
			protected void execute() {
				for (final HolidayDrops d : HolidayDrops.values()) {
				for (int j = 0; j < PlayerHandler.players.length; j++)
		             if (PlayerHandler.players[j] != null) {
		             Client p1 = (Client)PlayerHandler.players[j];
				if (d.count == 400 && d.getHoliday()) {
					stop();
					p1.sendMessage("The " + d.getName() + " event has ended.");
	        	} else if (d.count < 400 && d.getHoliday()) {
	    				switch(Misc.random(d.drops)) {
	    				case 0://varrock
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3214-Misc.random(40), 3424-Misc.random(40), 1, j);
	    					d.count ++;
	    		 		break;
	    				case 1://lumbridge
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3222-Misc.random(40), 3218-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break;
	    				case 2://falador
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 2964-Misc.random(40), 3378-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break;
	    				case 3://barb village
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3082-Misc.random(40), 3419-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break;
	    				case 4://draynor
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3082-Misc.random(40), 3249-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break;
	    				case 5://al kharid
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3293-Misc.random(40), 3180-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break;
	    				case 6://Rimmington
	    					Server.itemHandler.createGroundItem(p1, d.getItem(), 3034-Misc.random(40), 3246-Misc.random(40), 1, j);
	    					d.count ++;
	    			 	break; 
	    			 		}
	                    }
	                }
	        	}
			}
		});
	}
}