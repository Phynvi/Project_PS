package server.game.content.music;

import server.Config;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.util.Misc;

public class Sound {

	Client c;

	public Sound(Client c) {
		this.c = c;
	}

	/**
	 * Singular sound variables.
	 */

	public static final int LEVELUP = 67;
	public static final int DUELWON = 77;
	public static final int DUELLOST = 76;
	public static final int FOODEAT = 317;
	public static final int DROPITEM = 376;
	public static final int COOKITEM = 357;
	public static final int SHOOT_ARROW = 370;
	public static final int TELEPORT = 202;
	public static final int BONE_BURY = 380;
	public static final int DRINK_POTION = 334;

	private static final int[][] npcAttackSounds = { { 417, 1, 10 },
			{ 9, 1, 10 } };

	public static final int[][] getNpcAttackSounds() {
		return npcAttackSounds;
	}

	public static int getNpcAttackSounds(int NPCID) {
		String npc = NPCHandler.getNpcListName(NPCID).toLowerCase();
		if (npc.contains("bat")) {
			return 1;
		}
		if (npc.contains("cow")) {
			return 4;
		}
		if (npc.contains("imp")) {
			return 11;
		}
		if (npc.contains("rat")) {
			return 17;
		}
		if (npc.contains("duck")) {
			return 26;
		}
		if (npc.contains("golem")) {
			return 47;
		}
		if (npc.contains("wolf") || npc.contains("bear")) {
			return 28;
		}
		if (npc.contains("dragon")) {
			return 47;
		}
		if (npc.contains("ghost")) {
			return 57;
		}
		if (npc.contains("goblin")) {
			return 88;
		}
		if (npc.contains("skeleton") || npc.contains("demon")
				|| npc.contains("ogre") || npc.contains("giant")
				|| npc.contains("tz-") || npc.contains("jad")) {
			return 48;
		}
		if (npc.contains("zombie")) {
			return 1155;
		}
		if (npc.contains("man") || npc.contains("woman")
				|| npc.contains("monk")) {
			return 417;
		}
		return Misc.random(6) > 3 ? 398 : 394;

	}

	private static final int[][] npcBlockSounds = { { 9, 1, 30 }, { 81, 1, 30 } };

	public static final int[][] getNpcBlockSounds() {
		return npcBlockSounds;
	}

	public static int getNpcBlockSound(int NPCID) {
		String npc = NPCHandler.getNpcListName(NPCID).toLowerCase();
		if (npc.contains("bat")) {
			return 7;
		}
		if (npc.contains("cow")) {
			return 5;
		}
		if (npc.contains("imp")) {
			return 11;
		}
		if (npc.contains("golem")) {
			return 47;
		}
		if (npc.contains("rat")) {
			return 16;
		}
		if (npc.contains("duck")) {
			return 24;
		}
		if (npc.contains("wolf") || npc.contains("bear")) {
			return 34;
		}
		if (npc.contains("dragon")) {
			return 45;
		}
		if (npc.contains("ghost")) {
			return 53;
		}
		if (npc.contains("goblin")) {
			return 87;
		}
		if (npc.contains("skeleton") || npc.contains("demon")
				|| npc.contains("ogre") || npc.contains("giant")
				|| npc.contains("tz-") || npc.contains("jad")) {
			return 1154;
		}
		if (npc.contains("zombie")) {
			return 1151;
		}
		if (npc.contains("man") && !npc.contains("woman")) {
			return 816;
		}
		if (npc.contains("monk")) {
			return 816;
		}
		if (!npc.contains("man") && npc.contains("woman")) {
			return 818;
		}
		return 791;
	}

	private static final int[][] npcDeathSounds = { { 81, 3, 1, 20 } };

	public static final int[][] getNpcDeathSounds() {
		return npcDeathSounds;
	}

	public static int getNpcDeathSounds(int NPCID) {
		String npc = NPCHandler.getNpcListName(NPCID).toLowerCase();
		if (npc.contains("bat")) {
			return 7;
		}
		if (npc.contains("cow")) {
			return 3;
		}
		if (npc.contains("imp")) {
			return 9;
		}
		if (npc.contains("golem")) {
			return 47;
		}
		if (npc.contains("rat")) {
			return 15;
		}
		if (npc.contains("duck")) {
			return 25;
		}
		if (npc.contains("wolf") || npc.contains("bear")) {
			return 35;
		}
		if (npc.contains("dragon")) {
			return 44;
		}
		if (npc.contains("ghost")) {
			return 53;
		}
		if (npc.contains("goblin")) {
			return 125;
		}
		if (npc.contains("jad")) {
			return -1;
		}
		if (npc.contains("skeleton") || npc.contains("demon")
				|| npc.contains("ogre") || npc.contains("giant")
				|| npc.contains("tz-") || npc.contains("jad")) {
			return 70;
		}
		if (npc.contains("zombie")) {
			return 1140;
		}
		return 70;
	}

	public static String getItemName(Client c, int ItemID) {

		if (ItemID == -1)
			return "unarmed";
		return c.getItems().getItemName(ItemID);
	}

	public static int getPlayerBlockSounds(Client c) {

		int blockSound = 511;

		if (c.playerEquipment[Config.CHEST] == 2499
				|| c.playerEquipment[Config.CHEST] == 2501
				|| c.playerEquipment[Config.CHEST] == 2503
				|| c.playerEquipment[Config.CHEST] == 4746
				|| c.playerEquipment[Config.CHEST] == 4757
				|| c.playerEquipment[Config.CHEST] == 10330) {// Dragonhide
			// sound
			blockSound = 24;
		} else if (c.playerEquipment[Config.CHEST] == 10551 || // Torso
				c.playerEquipment[Config.CHEST] == 10438) {// 3rd age
			blockSound = 32;// Weird sound
		} else if (c.playerEquipment[Config.CHEST] == 10338 || // 3rd age
				c.playerEquipment[Config.CHEST] == 7399 || // Enchanted
				c.playerEquipment[Config.CHEST] == 6107 || // Ghostly
				c.playerEquipment[Config.CHEST] == 4091 || // Mystic
				c.playerEquipment[Config.CHEST] == 4101 || // Mystic
				c.playerEquipment[Config.CHEST] == 4111 || // Mystic
				c.playerEquipment[Config.CHEST] == 1035 || // Zamorak
				c.playerEquipment[Config.CHEST] == 12971) {// Combat
			blockSound = 14;// Robe sound
		} else if (c.playerEquipment[Config.SHIELD] == 4224) {// Crystal
																// Shield
			blockSound = 30;// Crystal sound
		} else if (c.playerEquipment[Config.CHEST] == 1101
				|| // Chains
				c.playerEquipment[Config.CHEST] == 1103
				|| c.playerEquipment[Config.CHEST] == 1105
				|| c.playerEquipment[Config.CHEST] == 1107
				|| c.playerEquipment[Config.CHEST] == 1109
				|| c.playerEquipment[Config.CHEST] == 1111
				|| c.playerEquipment[Config.CHEST] == 1113
				|| c.playerEquipment[Config.CHEST] == 1115
				|| // Plates
				c.playerEquipment[Config.CHEST] == 1117
				|| c.playerEquipment[Config.CHEST] == 1119
				|| c.playerEquipment[Config.CHEST] == 1121
				|| c.playerEquipment[Config.CHEST] == 1123
				|| c.playerEquipment[Config.CHEST] == 1125
				|| c.playerEquipment[Config.CHEST] == 1127
				|| c.playerEquipment[Config.CHEST] == 4720
				|| // Barrows armour
				c.playerEquipment[Config.CHEST] == 4728
				|| c.playerEquipment[Config.CHEST] == 4749
				|| c.playerEquipment[Config.CHEST] == 4712
				|| c.playerEquipment[Config.CHEST] == 11720
				|| // Godwars armour
				c.playerEquipment[Config.CHEST] == 11724
				|| c.playerEquipment[Config.CHEST] == 3140
				|| // Dragon
				c.playerEquipment[Config.CHEST] == 2615
				|| // Fancy
				c.playerEquipment[Config.CHEST] == 2653
				|| c.playerEquipment[Config.CHEST] == 2661
				|| c.playerEquipment[Config.CHEST] == 2669
				|| c.playerEquipment[Config.CHEST] == 2623
				|| c.playerEquipment[Config.CHEST] == 3841
				|| c.playerEquipment[Config.CHEST] == 1127) {// Metal
			// armour
			// sound
			blockSound = 511;
		} else {
			blockSound = 511;
		}
		return blockSound;
	}

	private static final int[][] weaponBlockSounds = { { 4151, 1, 40 },
			{ 5698, 1, 40 }, { 861, 1, 40 } };

	public static final int[][] getWeaponBlockSounds() {
		return weaponBlockSounds;
	}

	public static int GetWeaponSound(Client c) {

		String wep = getItemName(c, c.playerEquipment[Config.WEAPON])
				.toLowerCase();

		if (c.playerEquipment[Config.WEAPON] == 4718) {// Dharok's
														// Greataxe
			return 1320;
		}

		if (c.playerEquipment[Config.WEAPON] == 4734) {// Karil's Crossbow
			return 1081;
		}
		if (c.playerEquipment[Config.WEAPON] == 4747) {// Torag's Hammers
			return 1330;
		}
		if (c.playerEquipment[Config.WEAPON] == 4710) {// Ahrim's Staff
			return 2555;
		}
		if (c.playerEquipment[Config.WEAPON] == 4755) {// Verac's Flail
			return 1323;
		}
		if (c.playerEquipment[Config.WEAPON] == 4726) {// Guthan's
														// Warspear
			return 2562;
		}

		if (c.playerEquipment[Config.WEAPON] == 772
				|| c.playerEquipment[Config.WEAPON] == 1379
				|| c.playerEquipment[Config.WEAPON] == 1381
				|| c.playerEquipment[Config.WEAPON] == 1383
				|| c.playerEquipment[Config.WEAPON] == 1385
				|| c.playerEquipment[Config.WEAPON] == 1387
				|| c.playerEquipment[Config.WEAPON] == 1389
				|| c.playerEquipment[Config.WEAPON] == 1391
				|| c.playerEquipment[Config.WEAPON] == 1393
				|| c.playerEquipment[Config.WEAPON] == 1395
				|| c.playerEquipment[Config.WEAPON] == 1397
				|| c.playerEquipment[Config.WEAPON] == 1399
				|| c.playerEquipment[Config.WEAPON] == 1401
				|| c.playerEquipment[Config.WEAPON] == 1403
				|| c.playerEquipment[Config.WEAPON] == 1405
				|| c.playerEquipment[Config.WEAPON] == 1407
				|| c.playerEquipment[Config.WEAPON] == 1409
				|| c.playerEquipment[Config.WEAPON] == 9100) { // Staff
																// wack
			return 394;
		}
		if (c.playerEquipment[Config.WEAPON] == 839
				|| c.playerEquipment[Config.WEAPON] == 841
				|| c.playerEquipment[Config.WEAPON] == 843
				|| c.playerEquipment[Config.WEAPON] == 845
				|| c.playerEquipment[Config.WEAPON] == 847
				|| c.playerEquipment[Config.WEAPON] == 849
				|| c.playerEquipment[Config.WEAPON] == 851
				|| c.playerEquipment[Config.WEAPON] == 853
				|| c.playerEquipment[Config.WEAPON] == 855
				|| c.playerEquipment[Config.WEAPON] == 857
				|| c.playerEquipment[Config.WEAPON] == 859
				|| c.playerEquipment[Config.WEAPON] == 861
				|| c.playerEquipment[Config.WEAPON] == 4734
				|| c.playerEquipment[Config.WEAPON] == 2023 // RuneC'Bow
				|| c.playerEquipment[Config.WEAPON] == 4212
				|| c.playerEquipment[Config.WEAPON] == 4214
				|| c.playerEquipment[Config.WEAPON] == 4934
				|| c.playerEquipment[Config.WEAPON] == 9104
				|| c.playerEquipment[Config.WEAPON] == 9107) { // Bows/Crossbows
			return 370;
		}
		if (c.playerEquipment[Config.WEAPON] == 1363
				|| c.playerEquipment[Config.WEAPON] == 1365
				|| c.playerEquipment[Config.WEAPON] == 1367
				|| c.playerEquipment[Config.WEAPON] == 1369
				|| c.playerEquipment[Config.WEAPON] == 1371
				|| c.playerEquipment[Config.WEAPON] == 1373
				|| c.playerEquipment[Config.WEAPON] == 1375
				|| c.playerEquipment[Config.WEAPON] == 1377
				|| c.playerEquipment[Config.WEAPON] == 1349
				|| c.playerEquipment[Config.WEAPON] == 1351
				|| c.playerEquipment[Config.WEAPON] == 1353
				|| c.playerEquipment[Config.WEAPON] == 1355
				|| c.playerEquipment[Config.WEAPON] == 1357
				|| c.playerEquipment[Config.WEAPON] == 1359
				|| c.playerEquipment[Config.WEAPON] == 1361
				|| c.playerEquipment[Config.WEAPON] == 9109) { // BattleAxes/Axes
			return 399;
		}
		if (c.playerEquipment[Config.WEAPON] == 4718
				|| c.playerEquipment[Config.WEAPON] == 7808) { // Dharok
			// GreatAxe
			return 400;
		}
		if (c.playerEquipment[Config.WEAPON] == 6609
				|| c.playerEquipment[Config.WEAPON] == 1307
				|| c.playerEquipment[Config.WEAPON] == 1309
				|| c.playerEquipment[Config.WEAPON] == 1311
				|| c.playerEquipment[Config.WEAPON] == 1313
				|| c.playerEquipment[Config.WEAPON] == 1315
				|| c.playerEquipment[Config.WEAPON] == 1317
				|| c.playerEquipment[Config.WEAPON] == 1319) { // 2h
			return 425;
		}
		if (c.playerEquipment[Config.WEAPON] == 1321
				|| c.playerEquipment[Config.WEAPON] == 1323
				|| c.playerEquipment[Config.WEAPON] == 1325
				|| c.playerEquipment[Config.WEAPON] == 1327
				|| c.playerEquipment[Config.WEAPON] == 1329
				|| c.playerEquipment[Config.WEAPON] == 1331
				|| c.playerEquipment[Config.WEAPON] == 1333
				|| c.playerEquipment[Config.WEAPON] == 4587) { // Scimitars
			return 396;
		}
		if (wep.contains("halberd")) {
			return 420;
		}
		if (wep.contains("long")) {
			return 396;
		}
		if (wep.contains("knife")) {
			return 368;
		}
		if (wep.contains("unarmed")) {
			return 1321;
		}
		if (wep.contains("javelin")) {
			return 364;
		}

		if (c.playerEquipment[Config.WEAPON] == 9110) {
			return 401;
		}
		if (c.playerEquipment[Config.WEAPON] == 4755) {
			return 1059;
		}
		if (c.playerEquipment[Config.WEAPON] == 4153) {
			return 1079;
		}
		if (c.playerEquipment[Config.WEAPON] == 9103) {
			return 385;
		}
		if (c.playerEquipment[Config.WEAPON] == -1) { // fists
			return 417;
		}
		if (c.playerEquipment[Config.WEAPON] == 2745
				|| c.playerEquipment[Config.WEAPON] == 2746
				|| c.playerEquipment[Config.WEAPON] == 2747
				|| c.playerEquipment[Config.WEAPON] == 2748) { // Godswords
			return 390;
		}
		if (c.playerEquipment[Config.WEAPON] == 4151) {
			return 1080;
		} else {
			return 398; // Daggers(this is enything that isn't added)
		}
	}

	public static int[][] getWeaponSounds() {
		return weaponAttackSounds;
	}

	/**
	 * Sets the weapons sound type and delay Weapon Id Sound Type Sound Delay
	 */

	private static int[][] weaponAttackSounds = { { 4151, 1, 40 },
			{ 861, 1, 40 }, { 5698, 1, 40 } };

	public static int specialSounds(int id) {
		if (id == 4151) // whip
		{
			return 1081;
		}
		if (id == 5698) // dds
		{
			return 385;
		}
		if (id == 1434)// Mace
		{
			return 387;
		}
		if (id == 3204) // halberd
		{
			return 420;
		}
		if (id == 4153) // gmaul
		{
			return 1082;
		}
		if (id == 7158) // d2h
		{
			return 426;
		}
		if (id == 4587) // dscim
		{
			return 1305;
		}
		if (id == 1215) // Dragon dag
		{
			return 385;
		}
		if (id == 1305) // D Long
		{
			return 390;
		}
		if (id == 861) // MSB
		{
			return 386;
		}
		if (id == 11235) // DBow
		{
			return 386;
		}
		if (id == 6739) // D Axe
		{
		}
		if (id == 1377) // DBAxe
		{
			return 389;
		}
		return -1;
	}

	private static final int[][] weaponSpecialSounds = { { 5698, 1, 20 },
			{ 4151, 1, 15 } };

	public static final int[][] getWeaponSpecialSounds() {
		return weaponSpecialSounds;
	}
}