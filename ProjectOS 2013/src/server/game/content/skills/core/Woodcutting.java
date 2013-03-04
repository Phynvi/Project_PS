package server.game.content.skills.core;

import server.Config;
import server.Server;
import server.game.content.clipping.clip.region.Region;
import server.game.content.randomevents.EventHandler;
import server.game.content.skills.SkillHandler;
import server.game.objects.Object;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.task.Task;
import server.util.Misc;

public class Woodcutting extends SkillHandler {

	private Client c;

	public final static int[][] Axe_Settings = {
		{1351, 1, 1, 879}, //Bronze
		{1349, 1, 2, 877}, //Iron
		{1353, 6, 3, 875}, //Steel
		{1361, 6, 4, 873}, //Black
		{1355, 21, 5, 871}, //Mithril
		{1357, 31, 6, 869}, //Addy
		{1359, 41, 7, 867} //Rune
	};

	public final int[][] Tree_Settings = {
			{1276, 1342, 1, 25, 1511, 45, 100}, //Tree
			{1278, 1342, 1, 25, 1511, 45, 100}, //Tree
			{1286, 1342, 1, 25, 1511, 45, 100}, //Dead Tree
			{1281, 1356, 15, 38, 1521, 11, 20}, //Oak
			{1308, 7399, 30, 68, 1519, 11, 8}, //Willow
			{5552, 7399, 30, 68, 1519, 11, 8}, //Willow
			{1307, 1343, 45, 100, 1517, 48, 8}, //Maple
			{1309, 7402, 60, 175, 1515, 79, 5}, //Yew
			{1306, 7401, 75, 250, 1513, 150, 3}, //Magic
			{5551, 7399, 30, 68, 1519, 11, 8}, //Willow
			{5553, 7399, 30, 68, 1519, 11, 8}, //Willow
			{3033, 1342, 1, 25, 1511, 45, 100}, //Tree
			{1282, 1342, 1, 25, 1511, 45, 100} //Tree
	};

	public static int[][] FIX_AXE = { { 492, 508, 1351 }, { 492, 510, 1349 },
		{ 492, 512, 1353 }, { 492, 514, 1361 }, { 492, 516, 1355 },
		{ 492, 518, 1357 }, { 492, 520, 1359 }, };

	int a = -1;

	public Woodcutting(Client c) {
		this.c = c;
	}

	public void repeatAnimation() {
		Server.getTaskScheduler().schedule(new Task (3) {
			@Override
			protected void execute() {
				if (c.isWoodcutting) {
					c.startAnimation(Axe_Settings[a][3]);
					c.isWoodcutting = true;
				} else if (!c.isWoodcutting) {
					stop();
				}
			}
			@Override
			public void stop() {
				c.startAnimation(65535);
				
				
			}
		});
	}


	public void handleCanoe(final Client c, final int objectId) {
		for (int axes[] : Axe_Settings) {
			int type = axes[0];
			int level = axes[1];
			int anim = axes[2];
			if (c.playerLevel[c.playerWoodcutting] >= level
					&& c.getItems().playerHasItem(type)
					|| c.playerLevel[c.playerWoodcutting] >= level
					&& c.playerEquipment[c.playerWeapon] == type) {
				if (c.checkBusy()) {
					return;
				}
				c.setBusy(true);
				c.turnPlayerTo(c.objectX, c.objectY);
				c.startAnimation(anim);
				c.sendMessage("You swing your axe at the station.");
				Server.getTaskScheduler().schedule(new Task(4) {

					@Override
					public void execute() {
						addFallenTree(c, objectId);
						Server.getTaskScheduler().schedule(new Task(1) {

							@Override
							public void execute() {
								c.dialogueAction = 122;
								c.getDH().sendOption3(
										"Travel using Log Canoe to enter Barbarian Village.",
										"Travel using Waka Canoe to enter King Black Dragon Wilderness.",
										"Do Nothing.");
								stop();
								c.setBusy(false);
							}

						});
						c.setBusy(false);
						c.sendMessage("You cut down the canoe. Please wait...");
						stop();
					}

				});

			}
		}
	}

	public void fixAxe(final Client c) {
		for (int fix[] : FIX_AXE) {
			int axeHandle = fix[0];
			int axeHead = fix[1];
			final int fixedAxe = fix[2];
			if (c.getItems().playerHasItem(axeHandle)
					&& c.getItems().playerHasItem(axeHead)) {
				if (c.checkBusy()) {
					return;
				}
				c.isWoodcutting = true;
				c.setBusy(true);
				c.getItems().deleteItem(axeHandle, 1);
				c.getItems().deleteItem(axeHead, 1);
				c.getPA().removeAllWindows();
				c.sendMessage("Your axe handle and axe head have been taken.");
				Server.getTaskScheduler().schedule(new Task(1) {
					@Override
					protected void execute() {
						c.getItems().addItem(fixedAxe, 1);
						c.sendMessage("Your axe has been fixed.");
						c.setBusy(false);
						stop();
					}
				});
			}
		}
	}

	public static void addFallenTree(Client client, int canoe) {
		if (canoe == client.objectId) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					new Object(1296, client.objectX, client.objectY, 0, 0, 10,
							canoe, 20 + Misc.random(40));
				}
			}
		}

	}

	public boolean startWoodcutting(final int j, final int x, final int y, final int type) {
		if (c.isWoodcutting)
			return false;
		if (!WOODCUTTING) {
			c.sendMessage("This skill is currently disabled.");
			return false;
		}
		int wcLevel = c.playerLevel[8];
		a = -1;
		c.turnPlayerTo(x, y);
		if (Tree_Settings[j][2] > wcLevel) {
			c.sendMessage("You need a Woodcutting level of " + Tree_Settings[j][2] + " to cut this tree.");
			return false;
		}
		for (int i = 0; i < Axe_Settings.length; i++) {
			if (c.getItems().playerHasItem(Axe_Settings[i][0]) || c.playerEquipment[c.playerWeapon] == Axe_Settings[i][0]) {
				if (Axe_Settings[i][1] <= wcLevel) {
					a = i;
				}
			}
		}
		if (a == -1) {
			c.sendMessage("You need an axe to cut this tree.");
			return false;
		}
		if (c.getItems().freeSlots() < 1) {
			c.sendMessage("You do not have enough inventory slots to do that.");
			return false;
		}
		if (Config.goodDistance(c.objectX, c.objectY, c.absX, c.absY,
				2)) {
		c.startAnimation(Axe_Settings[a][3]);
		c.isWoodcutting = true;
		repeatAnimation();
		c.treeX = x;
		c.treeY = y;
		Server.getTaskScheduler().schedule(new Task(getTimer(j, a, wcLevel)) {
			@Override
			protected void execute() {
				if (c.disconnected) {
					stop();
					stopWoodcutting(c);
					return;
				}
				if (!c.isWoodcutting) {
					stop();
					stopWoodcutting(c);
				}
				if (c.isWoodcutting)
					c.startAnimation(Axe_Settings[a][3]);
				if (c.getItems().freeSlots() < 1) {
					c.sendMessage("You have ran out of inventory slots.");
					stop();
					stopWoodcutting(c);
				}
				int xp = Tree_Settings[j][3];
				//c.getPA().addSkillXP(Tree_Settings[j][3] * WOODCUTTING_EXPERIENCE,
						//c.playerWoodcutting);
				if (c.isWoodcutting) {
					c.getItems().addItem(Tree_Settings[j][4], 1);
					c.getPA().addSkillXP(xp, 8); // << this can cause it
					c.sendMessage("You get some "
							+ c.getItems()
									.getItemName(Tree_Settings[j][4]) + ".");
				}
				if (c.getItems().freeSlots() < 1) {
					c.sendMessage("You have ran out of inventory slots.");
					stop();
					stopWoodcutting(c);
				}
//				birdNests();
//				if(Misc.random(300) == 2) {
//					randomSpiritSpawn();
//				}
				EventHandler.randomEvents(c);
				if (c.getItems().freeSlots() < 1) {
					c.sendMessage("You have ran out of inventory slots.");
					stop();
					stopWoodcutting(c);
				}
				if (Misc.random(100) <= Tree_Settings[j][6]) {
					cutDownTree(Tree_Settings[j][5], x, y, type, Tree_Settings[j][1], Tree_Settings[j][0]);
					stop();
					stopWoodcutting(c);
				}
			}
			@Override
			public void stop() {
				c.startAnimation(65535);
				c.isWoodcutting = false;
				stopWoodcutting(c);
				c.treeX = 0;
				c.treeY = 0;
				return;
			}
		});
	}
		return false;
}
	
	private static void stopWoodcutting(Client c) {
		c.startAnimation(65535);
		c.isWoodcutting = false;
		c.treeX = 0;
		c.treeY = 0;
	}

	public int getTimer(int b, int c, int level) {
		double timer = (int)((Tree_Settings[b][2]  * 2) + 20 + Misc.random(20))-((Axe_Settings[c][2] * (Axe_Settings[c][2] * 0.75)) + level);
		if (timer < 3.0) {
			return 3;
		} else {
			return (int)timer;
		}
	}

	public void birdNests() {
		if (Misc.random(150) < 1) {
			c.sendMessage("A birds nest falls from the branches.");
			dropNest();
		}
	}

	public void dropNest() {
		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			Server.itemHandler.createGroundItem(c, 5070, c.getX() - 1,
					c.getY(), 1, c.playerId);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1,
				0)) {
			Server.itemHandler.createGroundItem(c, 5070, c.getX() + 1,
					c.getY() - 1, 1, c.playerId);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0,
				-1)) {
			Server.itemHandler.createGroundItem(c, 5070, c.getX(),
					c.getY() - 1, 1, c.playerId);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0,
				1)) {
			Server.itemHandler.createGroundItem(c, 5070, c.getX(),
					c.getY() + 1, 1, c.playerId);
		}
	}

	public void cutDownTree(int respawnTime, int x, int y, int type, int i, int j) {
		new Object(i, x, y, 0, type, 10, j, respawnTime);
		for (int t = 0; t < PlayerHandler.players.length; t++) {
			if (PlayerHandler.players[t] != null) {
				if (PlayerHandler.players[t].treeX == x && PlayerHandler.players[t].treeY == y) {
					PlayerHandler.players[t].isWoodcutting = false;
					PlayerHandler.players[t].startAnimation(65535);
					PlayerHandler.players[t].treeX = 0;
					PlayerHandler.players[t].treeY = 0;
				}
			}
		}
	}
}