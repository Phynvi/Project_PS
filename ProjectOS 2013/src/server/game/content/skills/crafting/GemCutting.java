package server.game.content.skills.crafting;

import server.Server;
import server.game.players.Client;
import server.task.Task;

public class GemCutting extends CraftingData {
	
	public static boolean cutGem(final Client c, final int itemUsed, final int usedWith) {
		if (isSkilling[12] == true) {
			return false;
		}
		final int itemId = (itemUsed == 1755 ? usedWith : itemUsed);
		for (final cutGemData g : cutGemData.values()) {
			if (itemId == g.getUncut()) {
				if (c.playerLevel[12] < g.getLevel()) {
					c.sendMessage("You need a crafting level of "+ g.getLevel() +" to cut this gem.");
					return false;
				}
				if (!c.getItems().playerHasItem(itemId)) {
					return false;
				}
				if (!CRAFTING) {
					c.sendMessage("This skill is currently disabled.");
					return false;
				}
				isSkilling[12] = true;
				c.startAnimation(g.getAnimation());
				Server.getTaskScheduler().schedule(new Task(4) {
                    @Override
                    protected void execute() {
						if (isSkilling[12] == true) {
							if (c.getItems().playerHasItem(itemId)) {
								c.getItems().deleteItem(itemId, 1);
								c.getItems().addItem(g.getCut(), 1);	
								c.getPA().addSkillXP((int) g.getXP(), 12);
								c.sendMessage("You cut the "+ c.getItems().getItemName(itemId).toLowerCase() +".");
								c.startAnimation(g.getAnimation());
							} else {
								stop();
							}
						} else {
							stop();
						}
					}
				});
			}
		}
		return false;
	}
}