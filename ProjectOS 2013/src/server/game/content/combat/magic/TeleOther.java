package server.game.content.combat.magic;

import server.Server;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.task.Task;

public class TeleOther {

	public static boolean hasRunes(Client c, int[] runes, int[] amount) {
		for (int i = 0; i < runes.length; i++) {
			if (c.getItems().playerHasItem(runes[i], amount[i])
					|| c.playerRights > 0) {
				return true;
			}
		}
		c.sendMessage("You don't have enough required runes to cast this spell!");
		return false;
	}

	public static void deleteRunes(Client c, int[] runes, int[] amount) {
		if (c.playerRights == 0 || c.playerRights == 1) {
			for (int i = 0; i < runes.length; i++) {
				c.getItems().deleteItem(runes[i],
						c.getItems().getItemSlot(runes[i]), amount[i]);
			}
		}
	}

	public static boolean hasRequiredLevel(Client c, int i) {
		return c.playerLevel[6] >= i;
	}

	public static final int FIRE = 554;
	public static final int WATER = 555;
	public static final int AIR = 556;
	public static final int EARTH = 557;
	public static final int MIND = 558;
	public static final int BODY = 559;
	public static final int DEATH = 560;
	public static final int NATURE = 561;
	public static final int CHAOS = 562;
	public static final int LAW = 563;
	public static final int COSMIC = 564;
	public static final int BLOOD = 565;
	public static final int SOUL = 566;
	public static final int ASTRAL = 9075;

	public static boolean castOnOtherSpells(Client c) {
		int[] spells = { 12435, 12455, 12425, 30298, 30290 };
		for (int i = 0; i < spells.length; i++) {
			if (c.castingSpellId == spells[i]) {
				return true;
			}
		}
		return false;
	}

	public static void teleOtherDistance(Client c, int type, int i) {
		c.startAnimation(1818);
		c.gfx0(343);
		Client castOn = (Client) PlayerHandler.players[i];
		int[][] data = { { 74, SOUL, LAW, EARTH, 1, 1, 1 },
				{ 82, SOUL, LAW, WATER, 1, 1, 1 },
				{ 90, SOUL, LAW, -1, 2, 1, -1 }, };
		if (!hasRequiredLevel(c, data[type][0])) {
			c.sendMessage("You need to have a magic level of " + data[type][0]
					+ " to cast this spell.");
			return;
		}
		if (!hasRunes(c, new int[] { data[type][1], data[type][2],
				data[type][3] }, new int[] { data[type][4], data[type][5],
				data[type][6] })) {
			return;
		}
		if (castOn != null) {
			if (castOn.distanceToPoint(c.absX, c.absY) <= 15) {
				if (c.heightLevel == castOn.heightLevel) {
					deleteRunes(c, new int[] { data[type][1], data[type][2],
							data[type][3] }, new int[] { data[type][4],
							data[type][5], data[type][6] });
					castOn.getPA().sendFrame126(getLocation(type),
							12560);
					castOn.getPA().sendFrame126(c.playerName, 12558);
					castOn.getPA().showInterface(12468);
					castOn.teleotherType = type;
				}
			}
		}
	}

	public static void teleOtherLocation(final Client c, final int i,
			boolean decline) {
		c.getPA().removeAllWindows();
		if (!decline) {
			 Server.getTaskScheduler().schedule(new Task(3) {
                 @Override
                 protected void execute() {
					getTeleOtherCoords(c, i);
					c.startAnimation(715);
					stop();
				}
			});
			c.startAnimation(1816);
			c.gfx100(342);
		}
	}

	private static String getLocation(int i) {
		String[] s = { "Lumbridge", "Falador", "Camelot", };
		return s[i];
	}

	private static void getTeleOtherCoords(Client c, int i) {
		int[][] coords = { { 3222, 3218 }, // LUMBRIDGE
				{ 2964, 3378 }, // FALADOR
				{ 2757, 3477 }, // CAMELOT
		};
		c.teleportToX = coords[i][0];
		c.teleportToY = coords[i][1];
		c.teleotherType = -1;
	}
}