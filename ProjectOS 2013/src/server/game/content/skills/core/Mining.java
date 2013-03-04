package server.game.content.skills.core;

import server.game.content.randomevents.EventHandler;
import server.game.content.randomevents.Golem;
import server.game.content.skills.SkillHandler;
import server.game.players.Client;
import server.task.Task;
import server.util.Misc;
import server.*;

public class Mining {

	/**
	 * Mining.java
	 *
	 * @author Nighel
	 *
	 **/

	public static enum pickaxeData {

		BRONZE_PICKAXE(1265, 1, 625, 10),
		IRON_PICKAXE(1267, 1, 626, 9),
		STEEL_PICKAXE(1269, 6, 627, 8),
		MITHRIL_PICKAXE(1273, 21, 628, 7),
		ADAMANT_PICKAXE(1271, 31, 629, 6),
		RUNE_PICKAXE(1275, 41, 624, 5);

		private int pickaxe, levelReq, animation, speed;

		private pickaxeData(final int pickaxe, final int levelReq, final int animation, final int speed) {
			this.pickaxe = pickaxe;
			this.levelReq = levelReq;
			this.animation = animation;
			this.speed = speed;
		}

		public int getPickaxe() {
			return pickaxe;
		}

		public int getLevel() {
			return levelReq;
		}

		public int getAnim() {
			return animation;
		}

		public int getSpeed() {
			return speed;
		}
	}

	public static enum rockData {

		CLAY(new int[] {2108, 2109}, 1, 5, 1, 5, new int[] {434}),
		COPPER(new int[] {3042, 2091, 2090}, 1, 18, 1, 8, new int[] {436}),
		TIN(new int[] {2094, 2095}, 1, 18, 1, 8, new int[] {438}),
		BLURITE(new int[] {10574, 10583, 2110}, 10, 20, 1, 8, new int[] {668}),
		IRON(new int[] {2093, 2092}, 15, 35, 2, 5, new int[] {440}),
		SILVER(new int[] {2101}, 20, 40, 3, 20, new int[] {442}),
		COAL(new int[] {2096, 2097}, 30, 50, 4, 25, new int[] {453}),
		GOLD(new int[] {2099, 2098}, 40, 65, 6, 33, new int[] {444}),
		MITHRIL(new int[] {2103, 2102}, 55, 80, 8, 50, new int[] {447}),
		ADAMANT(new int[] {2104, 2105}, 70, 95, 10, 83, new int[] {449}),
		RUNE(new int[] {14859, 14860, 2106}, 85, 125, 20, 166, new int[] {451}),
		GRANITE(new int[] {10947}, 45, 75, 10, 10, new int[] {6979, 6981, 6983}),
		SANDSTONE(new int[] {10946}, 35, 60, 5, 5, new int[] {6971, 6973, 6975, 6977}),
		GEM(new int[] {2111}, 40, 65, 6, 120, new int[] {1617, 1619, 1621, 1622, 1623, 1625, 1627, 1629});
		
		private int levelReq, mineTimer, respawnTimer, xp;
		private int[] oreIds;
		private int[] objectId;

		private rockData(final int[] objectId, final int levelReq, final int xp,  final int mineTimer, final int respawnTimer, final int... oreIds) {
			this.objectId = objectId;
			this.levelReq = levelReq;
			this.xp = xp;
			this.mineTimer = mineTimer;
			this.respawnTimer = respawnTimer;
			this.oreIds = oreIds;
		}

		public int getObject(final int object) {
			for (int i = 0; i < objectId.length; i++) {
				if (object == objectId[i]) {
					return objectId[i];
				}
			}
			return -1;
		}

		public int getLevel() {
			return levelReq;
		}

		public int getXp() {
			return xp;
		}

		public int getTimer() {
			return mineTimer;
		}

		public int getResapwn() {
			return respawnTimer;
		}

		public int[] getOreIds() {
			return oreIds;
		}
	}

	private final static int[] RANDOM_GEMS = {1617, 1619, 1621, 1622, 1623, 1625, 1627, 1629};

	public static void startMining(final Client c, final int objectId, final int objectX, final int objectY) {
			if (rightPickaxe(c) && !useablePickaxe(c)){
				c.getDH().sendStatement("You need a higher Mining level to use this pickaxe.");
				return;
			}

			if (!rightPickaxe(c)) {
				c.getDH().sendStatement("You need a pickaxe to start mining!");
				return;
			}

			if (c.playerLevel[c.playerMining] < getLevel(c, objectId)) {
				c.getDH().sendStatement("You need a Mining level of "+getLevel(c, objectId)+" to mine this.");
				c.startAnimation(65535);
				return; 
			}

			c.sendMessage("You swing your pick at the rock.");
			c.turnPlayerTo(objectX, objectY);

			/*/if(c.isMining = true) {
				return;
			}/*/

			c.isMining = true;
			c.startAnimation(pickaxeAnim(c));
			for(final rockData r : rockData.values()) {
			if(objectId == r.getObject(objectId)) {
				c.miningSettings[1] = r.getOreIds()[Misc.random(r.getOreIds().length-1)];
				c.miningSettings[2] = getExp(c, objectId);
				c.startAnimation(pickaxeAnim(c));
				 Server.getTaskScheduler().schedule(new Task(getSpeed(c, objectId)) {
                     @Override
                     protected void execute() {
						c.getItems().addItem(c.miningSettings[1], 1);
						if (Misc.random(50) == 10) {
							c.getItems().addItem(RANDOM_GEMS[(int) (RANDOM_GEMS.length * Math.random())], 1);
							c.sendMessage("You have found a gem!");
						}
						EventHandler.randomEvents(c);
						if (Misc.random(300) == 3) {
							Golem.randomGolemSpawn(c);
						}
						if (!SkillHandler.MINING) {
							c.sendMessage("This skill is currently disabled.");
							return;
						}
						c.sendMessage("You manage to mine some "+ c.getItems().getItemName(c.miningSettings[1]).toLowerCase()+".");
						c.getPA().addSkillXP(c.miningSettings[2], c.playerMining);
						Server.objectHandler.createAnObject(c, 451, objectX, objectY);
						if(!rightPickaxe(c)) {
							c.sendMessage("You need a Mining pickaxe which you need a Mining level to use.");
							resetMining(c);
							c.isMining = false;
							c.startAnimation(65535);
							stop();
						}
						resetMining(c);
						c.isMining = false;
						c.startAnimation(65535);
						stop();
					}
				 });
				  Server.getTaskScheduler().schedule(new Task(getSpeed(c, objectId) + getTime(c, objectId)) {
                      @Override
                      protected void execute() {
						Server.objectHandler.createAnObject(c, objectId, objectX, objectY);
						stop();
					}
				});
				  Server.getTaskScheduler().schedule(new Task(15) {
                      @Override
                      protected void execute() {
						if(c.isMining) {
							c.startAnimation(pickaxeAnim(c));
						}
						if(!c.isMining) {
							resetMining(c);
							stop();
							c.startAnimation(65535);
						}
					}
				});
			}
		}
	}

	public static void mineEss(final Client c, final int objectId, final int objectX, final int objectY) {

		if (!rightPickaxe(c)) {
			c.getDH().sendStatement("You need a pickaxe to start mining!");
			return;
		}

		if (c.playerLevel[c.playerMining] < getLevel(c, objectId)) {
			c.getDH().sendStatement("You need a Mining level of "+getLevel(c, objectId)+" to mine this.");
			c.startAnimation(65535);
			return; 
		}

		c.sendMessage("You swing your pick at the rock.");
		c.turnPlayerTo(objectX, objectY);
		
		EventHandler.randomEvents(c);
		if (Misc.random(300) == 3) {
			Golem.randomGolemSpawn(c);
		}
		if (!SkillHandler.MINING) {
			c.sendMessage("This skill is currently disabled.");
			return;
		}

		/*/if(c.isMining) {
			c.sendMessage("this method is called");
			return;
		}/*/

		c.isMining = true;
		c.startAnimation(pickaxeAnim(c));

		  Server.getTaskScheduler().schedule(new Task(2) {
              @Override
              protected void execute() {	
				if (c.playerLevel[c.playerMining] >= 30) {
					c.getItems().addItem(7936, 1);
					c.sendMessage("You manage to mine some "+ c.getItems().getItemName(7936).toLowerCase()+".");
				} else {
					c.getItems().addItem(1436, 1);
					c.sendMessage("You manage to mine some "+ c.getItems().getItemName(1436).toLowerCase()+".");
				}
				c.getPA().addSkillXP(5, c.playerMining);
				c.startAnimation(pickaxeAnim(c));

				if(!rightPickaxe(c)) {
					c.sendMessage("You need a Mining pickaxe which you need a Mining level to use.");
					resetMining(c);
					stop();
				}

			}
		});
	}

	private static boolean useablePickaxe(Client c) {
		boolean axe = false;
		if(rightLevel(c, 1265, 0) || rightLevel(c, 1267, 0) || rightLevel(c, 1269, 6) || rightLevel(c, 1273, 21) || rightLevel(c, 1271, 31)
			 || rightLevel(c, 1275, 41) || rightLevel(c, 13661, 41) || rightLevel(c, 15259, 61)) {
			axe = true;
		}
		return axe;
	}

	private static boolean rightLevel(Client c, int i, int l) {
		return (c.getItems().playerHasItem(i) || c.playerEquipment[c.playerWeapon] == i) && c.playerLevel[c.playerMining] >= l;
	}

	private static boolean rightPickaxe(Client c) {
		boolean has = false;
		for(final pickaxeData a : pickaxeData.values()) {
			if(c.getItems().playerHasItem(a.getPickaxe()) || c.playerEquipment[c.playerWeapon] == a.getPickaxe()) {
				has = true;
			}
		}
		return has;
	}

	private static int pickaxeAnim(Client c) {
		int anim = -1;
		for(final pickaxeData a : pickaxeData.values()) {
			if(c.playerLevel[c.playerMining] >= a.getLevel()) {
				if(c.getItems().playerHasItem(a.getPickaxe()) || c.playerEquipment[c.playerWeapon] == a.getPickaxe()) {
					anim = a.getAnim();
				}
			}
		}
		return anim;
	}

	private static int getSpeed(Client c, int objectId) {
		return (mineTimer(c, objectId) + pickaxeTimer(c) + levelMining(c));
	}

	private static int pickaxeTimer(Client c) {
		for(final pickaxeData a : pickaxeData.values()) {
			if(c.getItems().playerHasItem(a.getPickaxe()) || c.playerEquipment[c.playerWeapon] == a.getPickaxe()) {
				if(c.playerLevel[c.playerMining] >= a.getLevel()) {
					return a.getSpeed();
				}
			}
		}
		return 10;
	}

	private static int levelMining(Client c) {
		return (10 - (int)Math.floor(c.playerLevel[c.playerMining] / 10));
	}



	private static int mineTimer(Client c, int object) {
		for(final rockData a : rockData.values()) {
			if(object == a.getObject(object)) {
				return a.getTimer();
			}
		}
		return -1;
	}

	private static int getTime(Client c, int objectId) {
		int respawnTimer = -1;
		for(final rockData a : rockData.values()) {
			if(objectId == a.getObject(objectId)) {
				respawnTimer = a.getResapwn();
			}
		}
		return respawnTimer;
	}

	private static int getLevel(Client c, int objectId) {
		int rockLevel = -1;
		for(final rockData a : rockData.values()) {
			if(objectId == a.getObject(objectId)) {
				rockLevel = a.getLevel();
			}
		}
		return rockLevel;
	}

	private static int getExp(Client c, int objectId) {
		int rockLevel = -1;
		for(final rockData a : rockData.values()) {
			if(objectId == a.getObject(objectId)) {
				rockLevel = a.getXp();
			}
		}
		return rockLevel;
	}

	private static void resetMining(Client c) {
		c.getPA().removeAllWindows();
		for(int i = 0; i < 5; i++) {
			c.miningSettings[i] = -1;
		}
	}

	public static boolean rockExists(Client c, int rockExist) {
		boolean rockExists = false;
		for(final rockData a : rockData.values()) {
			if(rockExist == a.getObject(rockExist)){
				rockExists = true;
			}
		}
		return rockExists;
	}
	
	public static void prospectRock(final Client c, final String itemName) {
		c.sendMessage("You examine the rock for ores...");
		   Server.getTaskScheduler().schedule(new Task(3) {
               @Override
               protected void execute() {
				c.sendMessage("This rock contains "+itemName+".");
				stop();
               }
         });
	}
	
	public static void prospectNothing(final Client c) {
		c.sendMessage("You examine the rock for ores...");
		 Server.getTaskScheduler().schedule(new Task(2) {
             @Override
             protected void execute() {
				c.sendMessage("There is no ore left in this rock.");
				stop();
             }
         });
	}
}