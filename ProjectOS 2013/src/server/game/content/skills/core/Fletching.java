package server.game.content.skills.core;

import server.Config;
import server.Server;
import server.game.players.Client;
import server.task.Task;

/**
 * Fletching.java
 * 
 * @author Faris
 * 
 **/
public class Fletching {

	Client c;

	public boolean fletching;

	public Fletching(Client c) {
		this.c = c;
	}

	private int compDart;
	private int itemUsed;

	private final int[] fullDarts = { 806, 807, 808, 809, 810, 811 };
	private final int[] fullDartsXP = { 13, 25, 38, 50, 63, 75 };

	private enum Arrows {
		HEADLESS(52, 314, 53, 15, 1), BRONZE(53, 39, 882, 40, 1), IRON(53, 40,
				884, 58, 15), STEEL(53, 41, 886, 95, 30), MITHRIL(53, 42, 888,
				132, 45), ADAMANT(53, 43, 890, 170, 60), RUNE(53, 44, 892, 207,
				75);

		public int item1, item2, outcome, xp, levelReq;

		private Arrows(int item1, int item2, int outcome, int xp, int levelReq) {
			this.item1 = item1;
			this.item2 = item2;
			this.outcome = outcome;
			this.xp = xp;
			this.levelReq = levelReq;
		}

		public int getItem1() {
			return item1;
		}

		public int getItem2() {
			return item2;
		}

		public int getOutcome() {
			return outcome;
		}

		public int getXp() {
			return xp;
		}

		public int getLevelReq() {
			return levelReq;
		}
	}

	private Arrows forArrow(int id) {
		for (Arrows ar : Arrows.values()) {
			if (ar.getItem2() == id) {
				return ar;
			}
		}
		return null;
	}

	public int getPrimary(int item1, int item2) {
		return item1 == 52 || item1 == 53 ? item2 : item1;
	}

	public void makeDarts(int item1, int item2) {
		if (item1 == 819 || item2 == 819) {
			compDart = 0;
			itemUsed = 819;
		} else if (item1 == 820 || item2 == 820) {
			compDart = 1;
			itemUsed = 820;
		} else if (item1 == 821 || item2 == 821) {
			compDart = 2;
			itemUsed = 821;
		} else if (item1 == 822 || item2 == 822) {
			compDart = 3;
			itemUsed = 822;
		} else if (item1 == 823 || item2 == 823) {
			compDart = 4;
			itemUsed = 823;
		} else if (item1 == 824 || item2 == 824) {
			compDart = 5;
			itemUsed = 824;
		} else {
			c.sendMessage("You are not high enough level for this!");
			return;
		}
		if (c.getItems().playerHasItem(item1, 5)
				&& c.getItems().playerHasItem(314, 5)) {
			System.out.println(itemUsed);
			c.getItems().deleteItem2(itemUsed, 5);
			c.getItems().deleteItem2(314, 5);
			c.getItems().addItem(fullDarts[compDart], 15);
			c.getPA().addSkillXP(fullDartsXP[compDart] * 10, Config.FLETCHING);
		} else {
			c.sendMessage("You must have at least 15 of each supply to make arrows!");
		}
	}

	public void makeArrows(int item1, int item2) {
		Arrows arr = forArrow(getPrimary(item1, item2));
		if (arr != null) {
			if (c.playerLevel[Config.FLETCHING] >= arr.getLevelReq()) {

				if (c.getItems().getItemCount(arr.getItem1()) >= 15
						&& c.getItems().getItemCount(arr.getItem2()) >= 15) {
					c.getItems().deleteItem(arr.getItem1(),
							c.getItems().getItemSlot(arr.getItem1()), 15);
					c.getItems().deleteItem(arr.getItem2(),
							c.getItems().getItemSlot(arr.getItem2()), 15);
					c.getItems().addItem(arr.getOutcome(), 15);
					c.getPA().addSkillXP(arr.getXp(), Config.FLETCHING);
				} else {
					c.sendMessage("You must have at least 15 of each supply to make arrows!");
				}
			} else {
				c.sendMessage("You need a fletching level of "
						+ arr.getLevelReq() + " to fletch this.");
			}
		}
	}

	public final int[] unstrungs = { 52, 50, 48, 54, 56, 60, 58, 64, 62, 68,
			66, 72, 70 };

	private enum Fletch {

		ARROWSHAFTS(1511, 52, 5, 15),

		SHORTBOW(1511, 841, 5, 5), LONGBOW(1511, 839, 10, 10),

		OAKSBOW(1521, 843, 17, 20), OAKLBOW(1521, 845, 25, 25),

		WILLOWSBOW(1519, 849, 34, 35), WILLOWLBOW(1519, 847, 42, 40),

		MAPLESBOW(1517, 853, 50, 50), MAPLELBOW(1517, 851, 59, 55),

		YEWSBOW(1515, 857, 68, 65), YEWLBOW(1515, 855, 75, 70),

		MAGICSBOW(1513, 861, 84, 80), MAGICLBOW(1513, 859, 92, 87);

		public int logID, unstrungBow, xp, levelReq;

		private Fletch(int logID, int unstrungBow, int xp, int levelReq) {
			this.logID = logID;
			this.unstrungBow = unstrungBow;
			this.xp = xp;
			this.levelReq = levelReq;
		}

		public int getLogID() {
			return logID;
		}

		public int getBowID() {
			return unstrungBow;
		}

		public int getXp() {
			return xp;
		}

		public int getLevelReq() {
			return levelReq;
		}
	}

	private Fletch forBow(int id) {
		for (Fletch fl : Fletch.values()) {
			if (fl.getBowID() == id) {
				return fl;
			}
		}
		return null;
	}

	public void handleLog(int item1, int item2) {
		openFletching((item1 == 946) ? item2 : item1);
	}

	private void resetFletching() {
		c.startAnimation(65535);
		c.log = -1;
		c.getPA().removeAllWindows();
	}

	public void handleFletchingClick(int abutton) {
		final int fletchx5 = 5;
		final int fletchx10 = 10;
		final int fletchx = 28;
		if (c.isFletching) {
			return;
		}

		switch (abutton) {
		case 34186:
			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(839, fletchx, 2);
				break;
			case 1521: // Oak log
				fletchXBows(845, fletchx, 4);
				break;
			case 1519: // Willow log
				fletchXBows(847, fletchx, 6);
				break;
			case 1517: // Maple log
				fletchXBows(851, fletchx, 8);
				break;
			case 1515: // Yew log
				fletchXBows(855, fletchx, 10);
				break;
			case 1513: // Magic logs
				fletchXBows(869, fletchx, 12);
				break;
			}
			break;
		case 34182:
			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(841, fletchx, 1);
				break;
			case 1521: // Oak log
				fletchXBows(843, fletchx, 3);
				break;
			case 1519: // Willow log
				fletchXBows(849, fletchx, 5);
				break;
			case 1517: // Maple log
				fletchXBows(853, fletchx, 7);
				break;
			case 1515: // Yew log
				fletchXBows(857, fletchx, 9);
				break;
			case 1513: // Magic logs
				fletchXBows(861, fletchx, 11);
				break;
			}
			break;
		case 34190:
			fletchXBows(52, fletchx, 0);
			break;

		case 34192: // shafts
			fletchXBows(52, fletchx5, 0);
			break;

		case 34184: // norma short x5

			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(841, fletchx5, 1);
				break;
			case 1521: // Oak log
				fletchXBows(843, fletchx5, 3);
				break;
			case 1519: // Willow log
				fletchXBows(849, fletchx5, 5);
				break;
			case 1517: // Maple log
				fletchXBows(853, fletchx5, 7);
				break;
			case 1515: // Yew log
				fletchXBows(857, fletchx5, 9);
				break;
			case 1513: // Magic logs
				fletchXBows(861, fletchx5, 11);
				break;
			}
			break;
		case 34188: // norma long x5

			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(839, fletchx5, 2);
				break;
			case 1521: // Oak log
				fletchXBows(845, fletchx5, 4);
				break;
			case 1519: // Willow log
				fletchXBows(847, fletchx5, 6);
				break;
			case 1517: // Maple log
				fletchXBows(851, fletchx5, 8);
				break;
			case 1515: // Yew log
				fletchXBows(855, fletchx5, 10);
				break;
			case 1513: // Magic logs
				fletchXBows(859, fletchx5, 12);
				break;
			}
			break;
		case 34191: // shafts
			fletchXBows(52, fletchx10, 0);
			break;
		case 34183:

			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(841, fletchx10, 1);
				break;
			case 1521: // Oak log
				fletchXBows(843, fletchx10, 3);
				break;
			case 1519: // Willow log
				fletchXBows(849, fletchx10, 5);
				break;
			case 1517: // Maple log
				fletchXBows(853, fletchx10, 7);
				break;
			case 1515: // Yew log
				fletchXBows(857, fletchx10, 9);
				break;
			case 1513: // Magic logs
				fletchXBows(861, fletchx10, 11);
				break;
			}
			break;

		case 34187:

			switch (c.log) {
			case 1511: // Normal log
				fletchXBows(839, fletchx10, 2);
				break;
			case 1521: // Oak log
				fletchXBows(845, fletchx10, 4);
				break;
			case 1519: // Willow log
				fletchXBows(847, fletchx10, 6);
				break;
			case 1517: // Maple log
				fletchXBows(851, fletchx10, 8);
				break;
			case 1515: // Yew log
				fletchXBows(855, fletchx10, 10);
				break;
			case 1513: // Magic logs
				fletchXBows(859, fletchx10, 12);
				break;
			}
			break;

		case 34185:
			switch (c.log) {
			case 1511: // Normal log
				fletchBow(841, 1);
				break;
			case 1521: // Oak log
				fletchBow(843, 3);
				break;
			case 1519: // Willow log
				fletchBow(849, 5);
				break;
			case 1517: // Maple log
				fletchBow(853, 7);
				break;
			case 1515: // Yew log
				fletchBow(857, 9);
				break;
			case 1513: // Magic logs
				fletchBow(861, 11);
				break;
			}
			break;
		case 34189:
			switch (c.log) {
			case 1511: // Normal log
				fletchBow(839, 2);
				break;
			case 1521: // Oak log
				fletchBow(845, 4);
				break;
			case 1519: // Willow log
				fletchBow(847, 6);
				break;
			case 1517: // Maple log
				fletchBow(851, 8);
				break;
			case 1515: // Yew log
				fletchBow(855, 10);
				break;
			case 1513: // Magic logs
				fletchBow(859, 12);
				break;
			}
			break;
		case 34193: // Arrow shafts
			fletchBow(52, 0);
			break;
		}
	}

	public void fletchBow(int id, int reward) {
		final int rwID = reward;
		Fletch fle = forBow(id);
		if (fle != null) {
			if (id == 52) {
				int[] logArray = { 1511, 1521, 1519, 1517, 1515, 1513 };
				for (int i = 0; i < logArray.length; i++)
					if (c.getItems().playerHasItem(logArray[i])) {
						c.getItems().deleteItem(logArray[i],
								c.getItems().getItemSlot(logArray[i]), 1);
						c.getItems().addItem(unstrungs[rwID], 15);
						c.getPA().addSkillXP(fle.getXp(), Config.FLETCHING);
						c.startAnimation(1248);
						c.getPA().removeAllWindows();
					}
			} else {
				if (c.getItems().playerHasItem(fle.getLogID())) {
					if (c.playerLevel[Config.FLETCHING] >= fle.getLevelReq()) {
						c.getItems().deleteItem(fle.getLogID(),
								c.getItems().getItemSlot(fle.getLogID()), 1);
						c.getItems().addItem(unstrungs[rwID], 1);
						c.getPA().addSkillXP(fle.getXp(), Config.FLETCHING);
						c.startAnimation(1248);
						c.getPA().removeAllWindows();
					} else {
						c.sendMessage("You need a fletching level of at least "
								+ fle.getLevelReq() + " to cut this log.");
					}
				}
			}
			  Server.getTaskScheduler().schedule(new Task(3) {
                  @Override
                  protected void execute() {
					stop();
					resetFletching();
				}
			});

		}
	}

	public void fletchBowXHandler(int id, int reward) {
		final int rwID = reward;
		Fletch fle = forBow(id);
		if (fle != null) {
			if (id == 52) {
				int[] logArray = { 1511, 1521, 1519, 1517, 1515, 1513 };
				for (int i = 0; i < logArray.length; i++)
					if (c.getItems().playerHasItem(logArray[i])) {
						c.getItems().deleteItem(logArray[i],
								c.getItems().getItemSlot(logArray[i]), 1);
						c.getItems().addItem(unstrungs[rwID], 15);
						c.getPA().addSkillXP(fle.getXp(), Config.FLETCHING);
						c.startAnimation(1248);
					}
			} else {
				if (c.getItems().playerHasItem(fle.getLogID()) && id != 52) {
					if (c.playerLevel[Config.FLETCHING] >= fle.getLevelReq()) {
						c.getItems().deleteItem(fle.getLogID(),
								c.getItems().getItemSlot(fle.getLogID()), 1);
						c.getItems().addItem(unstrungs[rwID], 1);
						c.getPA().addSkillXP(fle.getXp(), Config.FLETCHING);
						c.startAnimation(1248);
					}
				}
			}
		}
	}

	public void fletchXBows(final int logID, final int amount, final int reward) {
		c.isFletching = true;
		Fletch fle = forBow(logID);
		if (c.playerLevel[Config.FLETCHING] < fle.getLevelReq()) {
			c.sendMessage("You need a fletching level of at least "
					+ fle.getLevelReq() + " to cut this log.");
			resetFletching();
			c.isFletching = false;
			return;
		}

		c.getPA().removeAllWindows();
		c.startAnimation(1248);
		fletchBowXHandler(logID, reward);
		  Server.getTaskScheduler().schedule(new Task(3) {
			  int timer = amount - 1;
				Fletch fle = forBow(logID);
              @Override
              protected void execute() {

				if (!c.isFletching) {
					resetFletching();
					stop();
				}
				if (c.isFletching) {
					c.startAnimation(65535);
					c.startAnimation(1248);
					switch (timer) {

					case 0:
						resetFletching();
						stop();
						c.isFletching = false;
						break;
					}

					timer--;

					if (timer >= 0) {
						fletchBowXHandler(logID, reward);

						if (!c.getItems().playerHasItem(fle.getLogID())) {
							resetFletching();
							stop();
							c.isFletching = false;
							c.sendMessage("You have run out of logs!");
							return;
						}

					}
				}
			}
		});
	}

	int[][] ifItems = { { 1511, 48, 50 }, { 1521, 56, 54 }, { 1519, 58, 60 },
			{ 1517, 62, 64 }, { 1515, 66, 68 }, { 1513, 70, 72 } };

	public void openFletching(int item) {
		if (!c.isFletching) {
			for (int i = 0; i < ifItems.length; i++) {
				if (ifItems[i][0] == item) {
					c.getPA().sendFrame164(8880);
					c.getPA().sendFrame126(
							"What would you like to make?", 8879);
					c.getPA().sendFrame246(8884, 250, ifItems[i][1]); // middle
					c.getPA().sendFrame246(8883, 250, ifItems[i][2]); // left
					// picture
					c.getPA().sendFrame246(8885, 250, 52); // right
																		// pic
					c.getPA().sendFrame126("Shortbow (u)", 8889);
					c.getPA().sendFrame126("Longbow (u)", 8893);
					c.getPA().sendFrame126("Arrow Shafts", 8897);
				}
			}
			c.log = item;
			fletching = true;
		}
	}
}