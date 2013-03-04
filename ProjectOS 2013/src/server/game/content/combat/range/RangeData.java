package server.game.content.combat.range;

import server.game.content.combat.melee.MeleeData;
import server.game.players.Client;

public class RangeData {
	
	public static int calculateRangeAttack(Client c) {
		int attackLevel = c.playerLevel[4];
		attackLevel *= c.specAccuracy;
		if (MeleeData.fullVoidRange(c))
			attackLevel += c.getLevelForXP(c.playerXP[c.playerRanged]) * 0.1;
		if (c.prayerActive[3])
			attackLevel *= 1.05;
		else if (c.prayerActive[11])
			attackLevel *= 1.10;
		else if (c.prayerActive[19])
			attackLevel *= 1.15;
		// dbow spec
		if (MeleeData.fullVoidRange(c) && c.specAccuracy > 1.15) {
			attackLevel *= 1.75;
		}
		return (int) (attackLevel + (c.playerBonus[4] * 1.95));
	}

	public static int calculateRangeDefence(Client c) {
		int defenceLevel = c.playerLevel[1];
		if (c.prayerActive[0]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
		} else if (c.prayerActive[5]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
		} else if (c.prayerActive[13]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
		} else if (c.prayerActive[24]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
		} else if (c.prayerActive[25]) {
			defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
		}
		return (int) (defenceLevel + c.playerBonus[9] + (c.playerBonus[9] / 2));
	}

	public static boolean usingBolts(Client c) {
		return c.playerEquipment[c.playerArrows] >= 9130
				&& c.playerEquipment[c.playerArrows] <= 9145
				|| c.playerEquipment[c.playerArrows] >= 9230
				&& c.playerEquipment[c.playerArrows] <= 9245;
	}

	public static boolean properBolts(Client c) {
		return c.playerEquipment[c.playerArrows] >= 9140
				&& c.playerEquipment[c.playerArrows] <= 9144
				|| c.playerEquipment[c.playerArrows] >= 9240
				&& c.playerEquipment[c.playerArrows] <= 9244;
	}

}
