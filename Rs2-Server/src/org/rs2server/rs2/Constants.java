package org.rs2server.rs2;

/**
 * Holds global server constants.
 * 
 * @author Graham Edgecombe
 * 
 */
public class Constants {

	/**
	 * The main screen window pane.
	 */
	public static final int MAIN_WINDOW = 548;
	/**
	 * The game window area.
	 */
	public static final int GAME_WINDOW = 75;
	/**
	 * The side tabs area.
	 */
	public static final int SIDE_TABS = 97;
	/**
	 * The chat box area.
	 */
	public static final int CHAT_BOX = 92;
	/**
	 * The login screen window pane.
	 */
	public static final int LOGIN_SCREEN = 549;
	/**
	 * The parameters for the equipment screen.
	 */
	public static final Object[] EQUIPMENT_PARAMETERS = new Object[] { "", "",
			"", "", "", "", "", "", "Wear", -1, 0, 7, 4, 98, 22020096 };
	/**
	 * The sting to send on the equipment interface run script.
	 */
	public final static String EQUIPMENT_TYPE_STRING = "IviiiIsssssssss";
	/**
	 * The message of the week, as displayed on the login screen.
	 */
	public static final String MESSAGE_OF_THE_WEEK = Constants.SERVER_NAME
			+ " is currently in a testing stage.";
	/**
	 * The interface sent in a run script for numerical input.
	 */
	public static final int NUMERICAL_INPUT_INTERFACE = 108;
	/**
	 * The interface sent in a run script for alphabetical & numerical input.
	 */
	public static final int ALPHA_NUMERICAL_INPUT_INTERFACE = 110;
	/**
	 * The interface sent to remove chat box interface input.
	 */
	public static final int REMOVE_INPUT_INTERFACE = 101;
	/**
	 * The first set of trade parameters.
	 */
	public final static Object[] TRADE_PARAMETERS_1 = new Object[] { -2, 0, 7,
			4, 80, 21954610 };
	/**
	 * The offer parameters.
	 */
	public final static Object[] OFFER_PARAMETERS = new Object[] { "", "", "",
			"", "Offer-X", "Offer-All", "Offer-10", "Offer-5", "Offer", -1, 0,
			7, 4, 82, 22020096 };
	/**
	 * The second set of trade parameters.
	 */
	public final static Object[] TRADE_PARAMETERS_2 = new Object[] { "", "",
			"", "", "Remove-X", "Remove-All", "Remove-10", "Remove-5",
			"Remove", -1, 0, 7, 4, 81, 21954608 };
	/**
	 * The sting to send on the trade interface run script.
	 */
	public final static String TRADE_TYPE_STRING = "IviiiIsssssssss";
	/**
	 * The set of sell parameters for shopping.
	 */
	public static final Object[] SELL_PARAMETERS = new Object[] { "", "", "",
			"", "Sell-X", "Sell-10", "Sell-5", "Sell-1", "Value", -1, 0, 7, 4,
			93, 40697856 };
	/**
	 * The set of buy parameters for shopping.
	 */
	public static final Object[] BUY_PARAMETERS = new Object[] { "", "", "",
			"", "Buy-X", "Buy-10", "Buy-5", "Buy-1", "Value", -1, 0, 4, 10, 91,
			40632344 };
	/**
	 * The sting to send on the shopping interface run script.
	 */
	public final static String MAIN_STOCK_TYPE_STRING = "vg";
	/**
	 * The sting to send on the shopping interface run script.
	 */
	public final static String MAIN_STOCK_OPTIONS_STRING = "IviiiIsssssssss";
	/**
	 * The examine option for run scripts.
	 */
	public static final int SCRIPT_OPTIONS_EXAMINE = 1278;

	/**
	 * The media displayed on the message of the week. 16 = Moving cogs 17 =
	 * Question marks 18 = Drama faces 19 = Bank pin vaults 20 = Bank pin
	 * question marks 21 = Player scamming 22 = Bank pin vaults with moving key
	 * 23 = Christmas presents & Santa
	 */
	public static final int MESSAGE_OF_THE_WEEK_SCREEN = 23;
	// public static final int MESSAGE_OF_THE_WEEK_SCREEN = Misc.random(17, 23);
	/**
	 * The server's name.
	 */
	public static final String SERVER_NAME = "Project PS";
	/**
	 * Bonuses as displayed on the equipment screen.
	 */
	public static final String[] BONUSES = { "Stab", "Slash", "Crush", "Magic",
			"Range", "Stab", "Slash", "Crush", "Magic", "Range", "Strength",
			"Prayer", "Ranged Strength" };

	/**
	 * The directory for the engine scripts.
	 */
	public static final String SCRIPTS_DIRECTORY = "./data/scripts/";
	/**
	 * Difference in X coordinates for directions array.
	 */
	public static final byte[] DIRECTION_DELTA_X = new byte[] { -1, 0, 1, -1,
			1, -1, 0, 1 };
	/**
	 * Difference in Y coordinates for directions array.
	 */
	public static final byte[] DIRECTION_DELTA_Y = new byte[] { 1, 1, 1, 0, 0,
			-1, -1, -1 };
	/**
	 * Default sidebar interfaces array.
	 */
	/*
	 * Values as per array index. - Sneaky 0 - ?? 1 - Weapon 2 - Stats 3 -
	 * Quests 4 - Inventory 5 - Equipment 6 - Prayer 7 - Magic 8 - Clan Chat 9 -
	 * Friends list 10 - Ignore list 11 - Logout 12 - Settings 13 - Emotes 14 -
	 * Music
	 */
	public static final int SIDEBAR_INTERFACES[][] = new int[][] {
			new int[] { 90, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
					109, 110, 111, 112 },
			new int[] { 137, 92, 320, 274, 149, 387, 271, 192, 589, 550, 551,
					182, 261, 464, 239 } };
	/**
	 * Incoming packet sizes array.
	 */
	public static final int[] PACKET_SIZES = new int[] {
			// 0-1---2---3---4---5---6---7---8---9
			-3, 8, -3, 6, -3, -3, -3, -3, -3, -3, // 0
			-3, -1, -3, -3, -3, -3, -3, -3, -3, -3, // 1
			-3, 2, 8, -3, -3, -3, -3, 13, -3, 6, // 2
			-3, -3, -3, -3, 1, 8, -3, -3, -3, -3, // 3
			-3, -3, -3, -3, -3, -3, -1, -3, -3, -3, // 4
			-3, -3, -1, -3, -3, 8, -3, -3, -3, -1, // 5
			-3, -3, -3, -3, 6, -3, -3, -3, -3, -3, // 6
			6, 8, -3, -3, -3, -3, -3, -3, -3, -3, // 7
			-3, -3, -3, -3, -3, -3, -3, 6, -1, 8, // 8
			-3, -3, -3, -3, -3, -3, 2, -3, -3, -3, // 9
			-3, -3, 8, -3, -3, 6, -3, 8, -3, -3, // 10
			-3, -3, -3, -3, -3, -3, -3, -3, -3, -3, // 11
			-3, -3, 8, -3, 8, -3, -3, -3, -3, -3, // 12
			-3, 8, -3, -3, -3, -3, -3, -3, -3, -3, // 13
			-3, -3, -3, -3, -3, -3, -3, -3, -3, -1, // 14
			-3, 0, 8, -3, -3, -3, 6, 8, -3, -3, // 15
			2, -3, -3, 8, -3, -3, 9, 4, -3, 4, // 16
			-3, -3, -3, -3, -3, -3, -3, -3, 2, -3, // 17
			-3, -3, -3, -3, -3, -3, 0, -3, -3, -3, // 18
			-3, -3, -3, -3, -3, -3, 2, -3, -3, -3, // 19
			-3, -3, -1, 6, -3, 8, -3, -3, -3, -3, // 20
			-3, -3, -3, -3, -3, 4, -3, -3, -3, -3, // 21
			-3, -3, -3, -3, -3, -3, -3, 2, -3, -3, // 22
			-3, -3, -3, -3, 6, -3, 1, -3, -3, -3, // 23
			-3, 8, -3, -3, -3, -3, -3, 2, -3, 4, // 24
			9, 3, -1, -3, -3, -3 // 25
	};
	/**
	 * 474 Update Reference Keys
	 */
	public static final int[] UPDATE_KEYS = { -1, 0, -1, 0, 0, 0, 0, -128, -2,
			-69, -92, 95, 0, 0, 0, 0, 43, 61, 92, -40, 0, 0, 0, 0, -7, -76, 26,
			-31, 0, 0, 0, -2, 92, -80, 107, -41, 0, 0, 0, 108, 90, 98, -32, 25,
			0, 0, 0, 20, -90, -124, 46, 119, 0, 0, 0, 84, 37, -107, -52, -39,
			0, 0, 0, 0, 103, -9, -101, 90, 0, 0, 0, 116, 42, 19, -99, -8, 0, 0,
			0, 25, -55, -93, 70, 58, 0, 0, 0, 3, 46, -53, -92, -83, 0, 0, 0, 0,
			30, 44, -35, 98, 0, 0, 0, 0, -127, -57, -52, -118, 0, 0, 0, 83, 7,
			-114, 106, 62, 0, 0, 0, 1, -85, 111, 40, -123, 0, 0, 0, -118, -72,
			-14, 77, 33, 0, 0, 0, 0 };/*
									 * 0xff, 0x0, 0xff, 0x0, 0x0, 0x0, 0x0,
									 * 0x80, 0xfe, 0xbb, 0xa4, 0x5f, 0x0, 0x0,
									 * 0x0, 0x0, 0x2b, 0x3d, 0x5c, 0xd8, 0x0,
									 * 0x0, 0x0, 0x0, 0xf9, 0xb4, 0x1a, 0xe1,
									 * 0x0, 0x0, 0x0, 0xfe, 0x5c, 0xb0, 0x6b,
									 * 0xd7, 0x0, 0x0, 0x0, 0x6c, 0x5a, 0x62,
									 * 0xe0, 0x19, 0x0, 0x0, 0x0, 0x14, 0xa6,
									 * 0x84, 0x2e, 0x77, 0x0, 0x0, 0x0, 0x54,
									 * 0xa, 0xe4, 0x31, 0x30, 0x0, 0x0, 0x0,
									 * 0x0, 0x67, 0xf7, 0x9b, 0x5a, 0x0, 0x0,
									 * 0x0, 0x74, 0x2a, 0x13, 0x9d, 0xf8, 0x0,
									 * 0x0, 0x0, 0x19, 0xc9, 0xa3, 0x46, 0x3a,
									 * 0x0, 0x0, 0x0, 0x3, 0x2e, 0xcb, 0xa4,
									 * 0xad, 0x0, 0x0, 0x0, 0x0, 0x1e, 0x2c,
									 * 0xdd, 0x62, 0x0, 0x0, 0x0, 0x0, 0x81,
									 * 0xc7, 0xcc, 0x8a, 0x0, 0x0, 0x0, 0x53,
									 * 0x7, 0x8e, 0x6a, 0x3e, 0x0, 0x0, 0x0,
									 * 0x1, 0xa3, 0x8c, 0xf6, 0x94, 0x0, 0x0,
									 * 0x0, 0x1, 0xb8, 0xf2, 0x4d, 0x21, 0x0,
									 * 0x0, 0x0, 0x0 };
									 */

	/**
	 * The player cap.
	 */
	public static final int MAX_PLAYERS = 2000;
	/**
	 * The NPC cap.
	 */
	public static final int MAX_NPCS = 32000;
	/**
	 * An array of valid characters in a long username.
	 */
	public static final char VALID_CHARS[] = { '_', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '^', '&', '*',
			'(', ')', '-', '+', '=', ':', ';', '.', '>', '<', ',', '"', '[',
			']', '|', '?', '/', '`' };
	/**
	 * Packed text translate table.
	 */
	public static final char XLATE_TABLE[] = { ' ', 'e', 't', 'a', 'o', 'i',
			'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g',
			'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(',
			')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$',
			'%', '"', '[', ']' };

	/**
	 * The maximum amount of items in a stack.
	 */
	public static final int MAX_ITEMS = Integer.MAX_VALUE;

	/**
	 * The experience modifier, made this field sort of controversial.
	 */
	public static double EXP_MODIFIER = 5;

	public static double getExpModifier() {
		return EXP_MODIFIER;
	}

	public static void setExpModifier(double val) {
		EXP_MODIFIER = val;
	}

}
