package server.game.content.skills.content.slayer;

import server.game.npcs.NPCHandler;
import server.game.players.Client;

/**
 * Slayer need to finish
 * @author Andrew
 */

public class Slayer extends SlayerInfo {
	
	private Client c;
	public Slayer(Client c) {
		this.c = c;
	}
	
	public boolean checkMaster(int npcId) {//assigning task
		for(SlayerMasters s: SlayerMasters.values()) {
			if (s.getName() == NPCHandler.getNpcListName(NPCHandler.npcs[npcId].npcType).toLowerCase()) {
				if (c.playerLevel[c.playerSlayer] < s.getLevel()) {
					c.sendMessage("You need " + s.getLevel() + " to talk to " + s.getName() + ".");
					return false;
				} else if(s.getQuest() == 1) {
					c.sendMessage("You need to complete the quest " + Shilo + " to talk " + s.getName() + ".");
					return false;
				} else if(s.getQuest() == 2) {
					c.sendMessage("You need to complete the quest " + Zanaris + " to talk " + s.getName() + ".");
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkTask(int npcId) {//attacking monster
		for (SlayerTasks s: SlayerTasks.values()) {
			if (s.getMonster() == NPCHandler.getNpcListName(NPCHandler.npcs[npcId].npcType).toLowerCase()) {
				if (c.playerLevel[c.combatLevel] < s.getLevel()) {
					c.sendMessage("You need " + s.getLevel() + " to slay " + s.getMonster() + ".");
					return false;
				}
			}
		}
		return true;
	}
	
	public void finishTask() {
		taskAmount = 0;
		slayerMaster = "none";
		taskMonster = "none";
		c.sendMessage("You have finished your slayer task, see a slayer master for a new one.");
	}
}
