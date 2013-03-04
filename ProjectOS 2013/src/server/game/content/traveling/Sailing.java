package server.game.content.traveling;

import server.Config;
import server.Server;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.task.Task;

/**
 * @author Andrew
 * @author someone else that I can't remember that release the array for this
 */

public class Sailing {

	private static final int[][] TRAVEL_DATA = {{}, // 0 - Null
		{2834, 3335, 15}, // 1 - From Port Sarim to Entrana
		{3048, 3234, 15}, // 2 - From Entrana to Port Sarim
		{2853, 3237, 12}, // 3 - From Port Sarim to Crandor
		{2834, 3335, 13}, // 4 - From Crandor to Port Sarim
		{2956, 3146, 9}, // 5 - From Port Sarim to Karajama
		{3029, 3217, 8}, // 6 - From Karajama to Port Sarim
		{2772, 3234, 3}, // 7 - From Ardougne to Brimhaven
		{3029, 3217, 3}, // 8 - From Brimhaven to Ardougne
		{2998, 3043, 23}, // 11 - From Port Khazard to Ship Yard
		{2676, 3170, 23}, // 12 - From Ship Yard to Port Khazard
		{2998, 3043, 17}, // 13 - From Cairn Island to Ship Yard
		{2659, 2676, 12}, // 14 - From Port Sarim to Pest Control
		{3041, 3202, 12}, // 15 - From Pest Control to Port Sarim
		{2763, 2956, 10}, // 16 - To Cairn Isle from Feldip Hills
};
	
	public static boolean searchForAlcohol(Client c) {
		for (int i = 0; i < Config.ALCOHOL_RELATED_ITEMS.length; i++) {
			if (c.getItems().playerHasItem(Config.ALCOHOL_RELATED_ITEMS[i], 1)) {
				c.getDH().sendNpcChat1("You can't bring intoxicating items to Asgarnia!", c.npcType, NPCHandler.getNpcListName(c.npcType));
				c.nextChat = 0;
				return false;
			}
		}
		c.sendMessage("Your clean of any possible alchohol.");
		return true;
	}
	
	public static boolean quickSearch(Client c) {
		for (int i = 0; i < Config.COMBAT_RELATED_ITEMS.length; i++) {
			if (c.getItems().playerHasItem(Config.COMBAT_RELATED_ITEMS[i], 1) || c.getItems().playerHasEquipped(Config.COMBAT_RELATED_ITEMS[i])) {
				c.getDH().sendNpcChat2("Grr! I see you brought some illegal items! Get", "out of my sight immediately!", c.npcType, NPCHandler.getNpcListName(c.npcType));
				c.nextChat = 0;
				return false;
			}
		}
		c.sendMessage("Your clean of any possible weapons.");
		return true;
	}


	public static void startTravel(final Client player, final int i) {
		if (i == 1) {//entrana check
			if (!quickSearch(player)) {
				return;
			}
		}
		if (i == 2) {//entrana check
			if (!searchForAlcohol(player)) {
				return;
			}
		}
		player.getPA().showInterface(3281);
		player.getPA().sendFrame36(75, i);
		 Server.getTaskScheduler().schedule(new Task(getTime(i) - 1) {
				@Override
				protected void execute() {
				player.getPA().movePlayer(getX(i), getY(i), 0);
				stop();
			}
		});
		
		 Server.getTaskScheduler().schedule(new Task(getTime(i)) {
				@Override
				protected void execute() {
				player.getPA().sendFrame36(75, -1);
				player.getPA().closeAllWindows();
				player.getDH().sendStatement("You arrive safely.");
				stop();
			}
		});
	}

	public static int getX(int i) {
		return TRAVEL_DATA[i][0];
	}

	public static int getY(int i) {
		return TRAVEL_DATA[i][1];
	}
	
	public static int getTime(int i) {
		return TRAVEL_DATA[i][2];
	}

}