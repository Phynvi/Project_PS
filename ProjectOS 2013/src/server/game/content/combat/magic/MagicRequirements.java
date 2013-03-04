package server.game.content.combat.magic;

import server.game.players.Client;
import server.game.players.PlayerHandler;

public class MagicRequirements {
	
	public static boolean wearingStaff(Client c, int runeId) {
		int wep = c.playerEquipment[c.playerWeapon];
		switch (runeId) {
		case 554:
			if (wep == 1387)
				return true;
			break;
		case 555:
			if (wep == 1383)
				return true;
			break;
		case 556:
			if (wep == 1381)
				return true;
			break;
		case 557:
			if (wep == 1385)
				return true;
			break;
		}
		return false;
	}

	public static boolean checkMagicReqs(Client c, int spell) {
		if (c.usingMagic && Magic.RUNES_REQUIRED) { // check for runes
			if ((!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][8],
					c.MAGIC_SPELLS[spell][9]) && !wearingStaff(c, c.MAGIC_SPELLS[spell][8]))
					|| (!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][10],
							c.MAGIC_SPELLS[spell][11]) && !wearingStaff(c, c.MAGIC_SPELLS[spell][10]))
					|| (!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][12],
							c.MAGIC_SPELLS[spell][13]) && !wearingStaff(c, c.MAGIC_SPELLS[spell][12]))
					|| (!c.getItems().playerHasItem(c.MAGIC_SPELLS[spell][14],
							c.MAGIC_SPELLS[spell][15]) && !wearingStaff(c, c.MAGIC_SPELLS[spell][14]))) {
				c.sendMessage("You don't have the required runes to cast this spell.");
				return false;
			}
		}

		if (c.usingMagic && c.playerIndex > 0) {
			if (PlayerHandler.players[c.playerIndex] != null) {
				for (int r = 0; r < c.REDUCE_SPELLS.length; r++) { // reducing
																	// spells,
																	// confuse
																	// etc
					if (PlayerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == c.MAGIC_SPELLS[spell][0]) {
						c.reduceSpellId = r;
						if ((System.currentTimeMillis() - PlayerHandler.players[c.playerIndex].reduceSpellDelay[c.reduceSpellId]) > PlayerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[c.reduceSpellId]) {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = true;
						} else {
							PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId] = false;
						}
						break;
					}
				}
				if (!PlayerHandler.players[c.playerIndex].canUseReducingSpell[c.reduceSpellId]) {
					c.sendMessage("That player is currently immune to this spell.");
					c.usingMagic = false;
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}

		int staffRequired = getStaffNeeded(c);
		if (c.usingMagic && staffRequired > 0 && Magic.RUNES_REQUIRED) { // staff
																			// required
			if (c.playerEquipment[c.playerWeapon] != staffRequired) {
				c.sendMessage("You need a "
						+ c.getItems().getItemName(staffRequired).toLowerCase()
						+ " to cast this spell.");
				return false;
			}
		}

		if (c.usingMagic && Magic.MAGIC_LEVEL_REQUIRED) { // check magic level
			if (c.playerLevel[6] < c.MAGIC_SPELLS[spell][1]) {
				c.sendMessage("You need to have a magic level of "
						+ c.MAGIC_SPELLS[spell][1] + " to cast this spell.");
				return false;
			}
		}
		if (c.usingMagic && Magic.RUNES_REQUIRED) {
			if (c.MAGIC_SPELLS[spell][8] > 0) { // deleting runes
				if (!wearingStaff(c, c.MAGIC_SPELLS[spell][8]))
					c.getItems().deleteItem(c.MAGIC_SPELLS[spell][8],
							c.getItems().getItemSlot(c.MAGIC_SPELLS[spell][8]),
							c.MAGIC_SPELLS[spell][9]);
			}
			if (c.MAGIC_SPELLS[spell][10] > 0) {
				if (!wearingStaff(c, c.MAGIC_SPELLS[spell][10]))
					c.getItems()
							.deleteItem(
									c.MAGIC_SPELLS[spell][10],
									c.getItems().getItemSlot(
											c.MAGIC_SPELLS[spell][10]),
									c.MAGIC_SPELLS[spell][11]);
			}
			if (c.MAGIC_SPELLS[spell][12] > 0) {
				if (!wearingStaff(c, c.MAGIC_SPELLS[spell][12]))
					c.getItems()
							.deleteItem(
									c.MAGIC_SPELLS[spell][12],
									c.getItems().getItemSlot(
											c.MAGIC_SPELLS[spell][12]),
									c.MAGIC_SPELLS[spell][13]);
			}
			if (c.MAGIC_SPELLS[spell][14] > 0) {
				if (!wearingStaff(c, c.MAGIC_SPELLS[spell][14]))
					c.getItems()
							.deleteItem(
									c.MAGIC_SPELLS[spell][14],
									c.getItems().getItemSlot(
											c.MAGIC_SPELLS[spell][14]),
									c.MAGIC_SPELLS[spell][15]);
			}
		}
		return true;
	}
	
	public static int getStaffNeeded(Client c) {
		switch (c.MAGIC_SPELLS[c.spellId][0]) {
		case 1539:
			return 1409;

		case 12037:
			return 4170;

		case 1190:
			return 2415;

		case 1191:
			return 2416;

		case 1192:
			return 2417;

		default:
			return 0;
		}
	}


}
