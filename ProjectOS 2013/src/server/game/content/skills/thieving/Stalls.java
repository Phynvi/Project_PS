package server.game.content.skills.thieving;

import server.Server;
import server.game.content.randomevents.EventHandler;
import server.game.content.skills.SkillHandler;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.task.Task;
import server.util.Misc;

public class Stalls {

	/**
	 * Stalls.java
	 *
	 * @author Nighel
	 *
	 **/
	
	  public static boolean isObject(int object) {
          for (final stallData s : stallData.values()) {
              if(object == s.getObject()) {
                  return true;
              }
          }
          return false;
      }

	public static enum stallData {

		  VEGETABLE_STALL(4706, 2, 10, 0, new int[] {1965, 1}),
	        BAKER_STALL(2561, 5, 16, 3, new int[] {2309, 1}, new int[] {1891, 1}, new int[] {1895, 1}),
	        TEA_STALL(635, 5, 16, 0, new int[] {712, 1}),
	        SILK_STALL(2560, 20, 24, 2, new int[] {950, 1}),
	        WINE_STALL(14011, 22, 27, 0, new int[] {1935, 1}),
	        SEED_STALL(7053, 27, 10, 0, new int[] {5318, 1}),
	        FUR_STALL(2563, 35, 36, 0, new int[] {6814, 1}, new int[] {958, 1}),
	        FISH_STALL(4705, 42, 42, 0, new int[] {359, 1}),
	        SILVER_STALL(2565, 50, 54, 2, new int[] {442, 1}, new int[] {2355, 1}),
	        SPICE_STALL(2564, 65, 81.3, 0, new int[] {2007, 1}, new int[] {946, 1}, new int[] {1550, 1}),
	        GEM_STALL(2562, 75, 160, 3, new int[] {1625, 1}, new int[] {1617, 1}, new int[] {1619, 1}, new int[] {1621, 1}, new int[] {1623, 1}, new int[] {1627, 1}, new int[] {1629, 1});
	         
	        private int objectId, levelReq, face;
	        private int[][] stalls;
	        private double xp;
	         
	        private stallData(final int objectId, final int levelReq, final double xp, final int face, final int[]... stalls) {
	            this.objectId = objectId;
	            this.levelReq = levelReq;
	            this.xp = xp;
	            this.face = face;
	            this.stalls = stalls;
	        }
	 
	        public int getObject() {
	            return objectId;
	        }
	 
	        public int getLevel() {
	            return levelReq;
	        }
	 
	        public double getXp() {
	            return xp;
	        }
	 
	        public int getFace() {
	            return face;
	        }
	 
	        public int[][] getStalls() {
	            return stalls;
	        }
	  }
	
	public static void attemptStall(final Client c, final int objectId, final int x, final int y) {
		for (final stallData s : stallData.values()) {
			if (System.currentTimeMillis() - c.lastThieve < 2500)
				return;
			if (!SkillHandler.THIEVING) {
				c.sendMessage("This skill is currently disabled.");
				return;
			}
			if(c.underAttackBy > 0 || c.underAttackBy2 > 0) {
				c.sendMessage("You can't steal from a stall while in combat!");
				return;	
			}
			if (c.getItems().freeSlots() == 0) {
				c.sendMessage("Not enough space in your inventory.");
				return;
			}
			if(objectId == s.getObject()) {
				if (c.playerLevel[c.playerThieving] >= s.getLevel()) {
					if(Misc.random(3) == 1) {
						failGuards(c);
						return;
					}
					if (c.getItems().freeSlots() == 0) {
						c.sendMessage("Not enough space in your inventory.");
						return;
					}
					c.startAnimation(832);
					EventHandler.randomEvents(c);
					Server.objectHandler.createAnObject(c, 634, x, y, s.getFace());
					c.getPA().addSkillXP((int) s.getXp(), c.playerThieving);
					int[] random = s.getStalls()[Misc.random(s.getStalls().length-1)];
					c.getItems().addItem(random[0], random[1] + (random.length > 2 ? Misc.random(random[2]) : 0));
					c.lastThieve = System.currentTimeMillis();
					c.sendMessage("You steal a "+c.getItems().getItemName(random[0])+" from the stall.");
					   Server.getTaskScheduler().schedule(new Task(getRespawnTime(c, s.getObject())) {
                           @Override
                           protected void execute() {
							Server.objectHandler.createAnObject(c, s.getObject(), x, y, s.getFace());
							stop();
						}
						@Override
						public void stop() {
						}
					});
				} else {
					c.getDH().sendStatement("You must have a thieving level of " + s.getLevel() + " to steal from this stall.");
				}
			}
		}
	}

	private static int getRespawnTime(Client c, int i) {
		switch (i) {
		case 4706: return 3;//veg
		case 2561: return 4;//baker
		case 635:  return 12;//tea
		case 2560: return 13;//silk
		case 14011:return 27;//wine
		case 7053: return 18;//seed
		case 2563: return 25;//fur
		case 4705: return 27;//fish
		case 2565: return 50;//silver
		case 2564: return 133;//spice
		case 2562: return 300;//gem
		}
		return 5;
	}

	private static void failGuards(final Client c) {
		for (int i = 1; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcType == 32) {
					if (c.goodDistance(c.absX, c.absY, NPCHandler.npcs[i].absX, NPCHandler.npcs[i].absY, 7)
						&& c.heightLevel == NPCHandler.npcs[i].heightLevel) {
						if (!NPCHandler.npcs[i].underAttack) {
							NPCHandler.npcs[i].forceChat("What do you think you're doing?!?");
							NPCHandler.npcs[i].underAttack = true;
							NPCHandler.npcs[i].killerId = c.playerId;
							return;
						}
					}
				}
			}
		}
	}
}