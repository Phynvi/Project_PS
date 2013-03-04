package server.game.content.combat.npcs;

import server.game.npcs.NPCHandler;

public class NpcEmotes {
	
	public static int getAttackEmote(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2550:
			if (NPCHandler.npcs[i].attackType == 0)
				return 7060;
			else
				return 7063;
        case 172:
            return 1162;
		case 2892:
		case 2894:
			return 2868;
		case 2627:
			return 2621;
		case 2630:
			return 2625;
		case 2631:
			return 2633;
		case 2741:
			return 2637;
		case 2746:
			return 2637;
		case 2607:
			return 2611;
		case 2743:// 360
			return 2647;
			// bandos gwd
		case 2551:
		case 2552:
		case 2553:
			return 6154;
			// end of gwd
			// arma gwd
		case 2558:
			return 3505;
		case 2560:
			return 6953;
		case 2559:
			return 6952;
		case 2561:
			return 6954;
			// end of arma gwd
			// sara gwd
		case 2562:
			return 6964;
		case 2563:
			return 6376;
		case 2564:
			return 7018;
		case 2565:
			return 7009;
			// end of sara gwd
		case 13: // wizards
			return 711;

		case 103:
		case 655:
			return 123;

		case 1624:
			return 1557;

		case 1648:
			return 1590;

		case 2783: // dark beast
			return 2733;

		case 1615: // abby demon
			return 1537;

		case 1613: // nech
			return 1528;

		case 1610:
		case 1611: // garg
			return 1519;

		case 1616: // basilisk
			return 1546;

		case 90: // skele
			return 260;

		case 50:// drags
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return 80;

		case 124: // earth warrior
			return 390;

		case 803: // monk
			return 422;

		case 52: // baby drag
			return 25;

		case 58: // Shadow Spider
		case 59: // Giant Spider
		case 60: // Giant Spider
		case 61: // Spider
		case 62: // Jungle Spider
		case 63: // Deadly Red Spider
		case 64: // Ice Spider
		case 134:
			return 143;

		case 105: // Bear
		case 106: // Bear
			return 41;

		case 412:
		case 78:
			return 30;

		case 2033: // rat
			return 138;

		case 2031: // bloodworm
			return 2070;

		case 101: // goblin
			return 309;

		case 81: // cow
			return 0x03B;

		case 21: // hero
			return 451;

		case 41: // chicken
			return 55;

		case 9: // guard
		case 32: // guard
		case 20: // paladin
			return 451;

		case 1338: // dagannoth
		case 1340:
		case 1342:
			return 1341;

		case 19: // white knight
			return 406;

		case 110:
		case 111: // ice giant
		case 112:
		case 117:
			return 128;

		case 2452:
			return 1312;

		case 2889:
			return 2859;

		case 118:
		case 119:
			return 99;

		case 82:// Lesser Demon
		case 83:// Greater Demon
		case 84:// Black Demon
		case 1472:// jungle demon
			return 64;

		case 1267:
		case 1265:
			return 1312;

		case 125: // ice warrior
		case 178:
			return 451;

		case 1153: // Kalphite Worker
		case 1154: // Kalphite Soldier
		case 1155: // Kalphite guardian
		case 1156: // Kalphite worker
		case 1157: // Kalphite guardian
			return 1184;

		case 123:
		case 122:
			return 164;

		case 2028: // karil
			return 2075;

		case 2025: // ahrim
			return 729;

		case 2026: // dharok
			return 2067;

		case 2027: // guthan
			return 2080;

		case 2029: // torag
			return 0x814;

		case 2030: // verac
			return 2062;

		case 2881: // supreme
			return 2855;

		case 2882: // prime
			return 2854;

		case 2883: // rex
			return 2851;

		case 3200:
			return 3146;

		case 2745:
			if (NPCHandler.npcs[i].attackType == 2)
				return 2656;
			else if (NPCHandler.npcs[i].attackType == 1)
				return 2652;
			else if (NPCHandler.npcs[i].attackType == 0)
				return 2655;

		default:
			return 0x326;
		}
	}

	public static int getDeadEmote(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		// sara gwd
		case 2562:
			return 6965;
		case 2563:
			return 6377;
		case 2564:
			return 7016;
		case 2565:
			return 7011;
			// bandos gwd
		case 2551:
		case 2552:
		case 2553:
			return 6156;
		case 2550:
			return 7062;
		case 2892:
		case 2894:
			return 2865;
		case 1612: // banshee
			return 1524;
		case 2558:
			return 3503;
		case 2559:
		case 2560:
		case 2561:
			return 6956;
		case 2607:
			return 2607;
		case 2627:
			return 2620;
		case 2630:
			return 2627;
		case 2631:
			return 2630;
		case 2738:
			return 2627;
		case 2741:
			return 2638;
		case 2746:
			return 2638;
		case 2743:
			return 2646;
		case 2745:
			return 2654;

		case 3777:
		case 3778:
		case 3779:
		case 3780:
			return -1;

		case 3200:
			return 3147;

		case 2035: // spider
			return 146;

		case 2033: // rat
			return 141;

		case 2031: // bloodvel
			return 2073;

		case 101: // goblin
			return 313;

		case 81: // cow
			return 0x03E;

		case 41: // chicken
			return 57;

		case 1338: // dagannoth
		case 1340:
		case 1342:
			return 1342;

		case 2881:
		case 2882:
		case 2883:
			return 2856;

		case 111: // ice giant
			return 131;

		case 125: // ice warrior
			return 843;

		case 751:// Zombies!!
			return 302;

		case 1626:
		case 1627:
		case 1628:
		case 1629:
		case 1630:
		case 1631:
		case 1632: // turoth!
			return 1597;

		case 1616: // basilisk
			return 1548;

		case 1653: // hand
			return 1590;

		case 82:// demons
		case 83:
		case 84:
			return 67;

		case 1605:// abby spec
			return 1508;

		case 51:// baby drags
		case 52:
		case 1589:
		case 3376:
			return 28;

		case 1610:
		case 1611:
			return 1518;

		case 1618:
		case 1619:
			return 1553;

		case 1620:
		case 1621:
			return 1563;

		case 2783:
			return 2732;

		case 1615:
			return 1538;

		case 1624:
			return 1558;

		case 1613:
			return 1530;

		case 1633:
		case 1634:
		case 1635:
		case 1636:
			return 1580;

		case 1648:
		case 1649:
		case 1650:
		case 1651:
		case 1652:
		case 1654:
		case 1655:
		case 1656:
		case 1657:
			return 1590;

		case 100:
		case 102:
			return 313;

		case 105:
		case 106:
			return 44;

		case 412:
		case 78:
			return 36;

		case 122:
		case 123:
			return 167;

		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 134:
			return 146;

		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
			return 1190;

		case 103:
		case 104:
			return 123;

		case 118:
		case 119:
			return 102;

		case 50:// drags
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return 92;

		default:
			return 2304;
		}
	}

	/**
	 * Attack delays
	 **/
	public static int getNpcDelay(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2025:
		case 2028:
			return 7;

		case 2745:
			return 8;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2550:
			return 6;
			// saradomin gw boss
		case 2562:
			return 2;

		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 **/
	public static int getHitDelay(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
		case 2892:
		case 2894:
			return 3;

		case 2743:
		case 2631:
		case 2558:
		case 2559:
		case 2560:
			return 3;

		case 2745:
			if (NPCHandler.npcs[i].attackType == 1 || NPCHandler.npcs[i].attackType == 2)
				return 5;
			else
				return 2;

		case 2025:
			return 4;
		case 2028:
			return 3;

		default:
			return 2;
		}
	}

	/**
	 * Npc respawn time
	 **/
	public static int getRespawnTime(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2881:
		case 2882:
		case 2883:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2550:
		case 2551:
		case 2552:
		case 2553:
			return 100;
		case 3777:
		case 3778:
		case 3779:
		case 3780:
			return 500;
		case 1532:
		case 1534:
			return -1;
		default:
			return 25;
		}
	}

}
