package server.game.content.skills.thieving;

import server.Server;
import server.game.content.randomevents.EventHandler;
import server.game.content.skills.SkillHandler;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.task.Task;
import server.util.Misc;

public class Pickpocket extends SkillHandler {

	/**
	 * Pickpocket.java
	 **/

	public static enum npcData {
		
		MAN(new int[] {1, 3}, 1, 8.0, 1, 5, new int[] {995, 3}),
		WOMEN(new int[] {4}, 1, 8.0, 1, 5, new int[] {995, 3}),
		FARMER(new int[] {7}, 10, 14.5, 1, 5, new int[] {995, 9}, new int[]{5318, 4}),
		HAM_FEMALE(new int[] {1715}, 15, 18.5, 2, 4, new int[] {995, 3}),//to do
		HAM_MALE(new int[] {1713}, 20, 22.5, 2, 4,  new int[] {995, 3}),
		WARRIOR(new int[] {15}, 25, 26.0, 2, 5, new int[] {995, 18}),
		ROGUE(new int[] {187}, 32, 35.5, 2, 5, new int[]{995, 25}, new int[]{995, 40}, new int[]{7919, 1}, new int[]{556, 6}, new int[]{5686, 1}, new int[]{1523, 1}, new int[]{1944, 1}),
		CAVE_GOBLIN(new int[] {1822}, 36, 40.0, 2, 5, new int[] {995, 3}),//to do
		MASTER_FARMER(new int[] {2234}, 38, 43.0, 2, 5, new int[] {995, 3}),//to do
		GUARD(new int[] {9, 32}, 40, 46.8, 2, 5, new int[] {995, 30}),
		KNIGHT(new int[] {26}, 55, 84.3, 3, 5, new int[] {995, 50}),
		MENAPHITE_THUG(new int[] {1904}, 65, 137.5, 5, 5, new int[] {995, 60}),
		WATCHMAN(new int[] {431}, 65, 137.5, 3, 5, new int[] {995, 60}, new int[] {4593, 1}),
		PALADIN(new int[] {20}, 70, 151.8, 5, 4, new int[] {995, 80}, new int[] {562, 2}),
		GNOME(new int[] {66}, 75, 198.3, 1, 6,  new int[]{995, 300}, new int[]{557, 1}, new int[]{444, 1}, new int[]{569, 1}, new int[]{2150}, new int[]{2162}),
		HERO(new int[] {21}, 80, 273.3, 4, 6, new int[]{995, 300}, new int[]{560, 2}, new int[] {565, 1}, new int[]{569, 1}, new int[]{1617, 1}, new int[]{444, 1}, new int[]{1993});
		
		private int levelReq, damage, stun;
		private int[] npcId;
		private int[][] pickpockets;
		private double xp;
		
		private npcData(final int[] npcId, final int levelReq, final double xp, final int damage, final int stun, final int[]... pickpockets) {
			this.npcId = npcId;
			this.levelReq = levelReq;
			this.xp = xp;
			this.pickpockets = pickpockets;
			this.damage = damage;
			this.stun = stun;
		}

		public int getNpc(final int npc) {
			for (int i = 0; i < npcId.length; i++) {
				if (npc == npcId[i]) {
					return npcId[i];
				}
			}
			return -1;
		}

		public int getLevel() {
			return levelReq;
		}

		public double getXp() {
			return xp;
		}

		public int[][] getPickPockets() {
			return pickpockets;
		}
		
		public int getDamage() {
			return damage;
		}
		
		public int getStun() {
	            return stun;
	     } 
	}

	public static boolean isNPC(Client c, int npc) {
		for (final npcData n : npcData.values()) {
			if(npc == n.getNpc(npc)) {
				return true;
			}
		}
		return false;
	}

	public static void attemptPickpocket(final Client c, final int npcId) {
		if (System.currentTimeMillis() - c.lastThieve < 2000 || c.playerStun) {
			return;
		}
		if(c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't pickpocket while in combat!");
			return;	
		}
		if (!THIEVING) {
			c.sendMessage("This skill is currently disabled.");
			return;
		}
		//membersOnly();
		for (final npcData n : npcData.values()) {
			if (npcId == n.getNpc(npcId)) {
				if (c.playerLevel[c.playerThieving] < n.getLevel()) {
					c.getDH().sendStatement("You need a Thieving level of "+n.getLevel()+" to pickpocket the "+NPCHandler.getNpcListName(n.getNpc(npcId)).toLowerCase()+".");
					return; 
				}
				c.sendMessage("You attempt to pick the "+ NPCHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() +"'s pocket.");
				c.startAnimation(881);
				if (Misc.random(c.playerLevel[17] + 5) <  Misc.random(n.getLevel())) {
					   Server.getTaskScheduler().schedule(new Task(2) {
                           @Override
                           protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.setHitDiff(n.getDamage());
							c.setHitUpdateRequired(true);
							c.playerLevel[3] -= n.getDamage();
							c.getPA().refreshSkill(3);
							c.gfx100(80);
							c.startAnimation(404);
							EventHandler.randomEvents(c);
							NPCHandler.npcs[npcId].forceChat("What do you think you're doing?");
							NPCHandler.npcs[npcId].facePlayer(c.getId());
							c.lastThieve = System.currentTimeMillis() + 7500;
							c.sendMessage("You fail to pick the "+ NPCHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() +"'s pocket.");
							c.playerStun = true;
							stop();
						}
					});
					   Server.getTaskScheduler().schedule(new Task(n.getStun()) {
                           @Override
                           protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.playerStun = false;
							stop();
						}
					});
				} else {
					String message = "You pick the "+ NPCHandler.getNpcListName(n.getNpc(npcId)).toLowerCase() +"'s pocket.";
					final String message2 = message;
					   Server.getTaskScheduler().schedule(new Task(2) {
                           @Override
                           protected void execute() {
							c.sendMessage(message2);
							c.getPA().addSkillXP((int) n.getXp(), c.playerThieving);
							int[] random = n.getPickPockets()[Misc.random(n.getPickPockets().length-1)];
							c.getItems().addItem(random[0], random[1] + (random.length > 2 ? Misc.random(random[2]) : 0));
							stop();
						}
					});
				}
				c.lastThieve = System.currentTimeMillis();
			}
		}
	}
}