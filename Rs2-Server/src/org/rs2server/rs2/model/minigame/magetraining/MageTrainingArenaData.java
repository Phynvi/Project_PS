package org.rs2server.rs2.model.minigame.magetraining;

import org.rs2server.rs2.model.Location;

public class MageTrainingArenaData {

	public static final Location ARENA_LOBBY = Location.create(3363, 3300, 0);
	public static final Location PORTAL_CENTER = Location.create(3363, 3318, 0);
	public static final Location ALCEHMIST_CHAMBER = Location.create(3366,
			9623, 2);
	public static final Location GRAVEYARD_CHAMBER = Location.create(3364,
			9640, 1);
	public static final Location TELEKENETIC_CHAMBER = Location.create(3362,
			9713, 1);
	public static final Location ENCHANTMENT_CHAMBER = Location.create(3363,
			9649, 0);

	public static final int cupboardItems[] = { 6893, 6894, 6895, 6896, 6897 };

	protected static final double enchantmentExperienceRate = 0.75;

	protected static final int alchemyInterfaceIndex = 0;
	protected static final int enchantmentInterfaceIndex = 1;
	protected static final int graveyardInterfaceIndex = 2;
	protected static final int shopInterfaceIndex = 3;
	protected static final int telekeneticInterfaceIndex = 4;

	protected static final int MagetrainingInterfaces[] = { 194, 195, 196, 197,
			198 };
	
	public static int[] getCupboarditems() {
		return cupboardItems;
	}

	public static double getEnchantmentexperiencerate() {
		return enchantmentExperienceRate;
	}

	public static int getAlchemyinterfaceindex() {
		return alchemyInterfaceIndex;
	}

	public static int getEnchantmentinterfaceindex() {
		return enchantmentInterfaceIndex;
	}

	public static int getGraveyardinterfaceindex() {
		return graveyardInterfaceIndex;
	}

	public static int getShopinterfaceindex() {
		return shopInterfaceIndex;
	}

	public static int getTelekeneticinterfaceindex() {
		return telekeneticInterfaceIndex;
	}

}
