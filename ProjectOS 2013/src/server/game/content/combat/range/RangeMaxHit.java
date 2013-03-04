package server.game.content.combat.range;

import server.game.content.combat.melee.MeleeData;
import server.game.players.Client;

public class RangeMaxHit {
	
	public static int rangeMaxHit(Client c) {
		int rangeLevel = c.playerLevel[4];
		double modifier = 1.0;
		double wtf = c.specDamage;
		int itemUsed = c.usingBow ? c.lastArrowUsed : c.lastWeaponUsed;
		if (c.prayerActive[3])
			modifier += 0.05;
		else if (c.prayerActive[11])
			modifier += 0.10;
		else if (c.prayerActive[19])
			modifier += 0.15;
		if (MeleeData.fullVoidRange(c))
			modifier += 0.20;
		double f = modifier * rangeLevel;
		int rangeStr = getRangeStr(itemUsed);
		double max = (f + 8) * (rangeStr + 64) / 640;
		if (wtf != 1)
			max *= wtf;
		if (max < 1)
			max = 1;
		return (int) max;
	}

	public static int getRangeStr(int i) {
		if (i == 4214)
			return 70;
		switch (i) {
		// bronze to rune bolts
		case 877:
			return 10;
		case 9140:
			return 46;
		case 9141:
			return 64;
		case 9142:
		case 9241:
		case 9240:
			return 82;
		case 9143:
		case 9243:
		case 9242:
			return 100;
		case 9144:
		case 9244:
		case 9245:
			return 115;
			// bronze to dragon arrows
		case 882:
			return 7;
		case 884:
			return 10;
		case 886:
			return 16;
		case 888:
			return 22;
		case 890:
			return 31;
		case 892:
		case 4740:
			return 49;
		case 11212:
			return 60;
			// knifes
		case 864:
			return 3;
		case 863:
			return 4;
		case 865:
			return 7;
		case 866:
			return 10;
		case 867:
			return 14;
		case 868:
			return 24;
		}
		return 0;
	}

}
