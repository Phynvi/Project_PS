package org.rs2server.rs2.model.minigame.barrows;

import org.rs2server.rs2.model.Location;
import org.rs2server.util.Misc;

public class BarrowsData {

	protected static int AHRIM = 0;

	protected static int DHAROK = 1;
	protected static int GUTHAN = 2;
	protected static int KARIL = 3;
	protected static int TORAG = 4;
	protected static int VERAC = 5;
	protected static final int BARROWS_CHANCE = 5;

	protected static int BARROWS_BROTHERS[] = { 2025, 2026, 2027, 2028, 2029,
			2030 };

	public static Location BARROWS_TOP = Location.create(3565, 3306);

	/**
	 * Brother spawning locations with offsets for extra detail.
	 */
	static Location AHRIMS_TOMB = Location.create(Misc.random(3554, 3558),
			9696, 3);

	static Location DHAROKS_TOMB = Location.create(3552,
			Misc.random(9712, 9717), 3);
	static Location VERACS_TOMB = Location.create(3570,
			Misc.random(9704, 9708), 3);
	static Location TORAGS_TOMB = Location.create(3572,
			Misc.random(9684, 9688), 3);
	static Location KARILS_TOMB = Location.create(3554,
			Misc.random(9680, 9685), 3);
	static Location GUTHANS_TOMB = Location.create(3543,
			Misc.random(9701, 9706), 3);
	/**
	 * Sarcophagus object IDs
	 */
	static final int AHRIM_SARCOPHAGUS = 6821;
	static final int DHAROK_SARCOPHAGUS = 6771;
	static final int VERAC_SARCOPHAGUS = 6823;
	static final int TORAG_SARCOPHAGUS = 6772;
	static final int KARIL_SARCOPHAGUS = 6822;
	static final int GUTHAN_SARCOPHAGUS = 6773;
	/**
	 * Barrow rewards.
	 */
	protected static final int[] BARROW_REWARDS = { 4757, 4759, 4753, 4755, // Verac's
			4736, 4738, 4734, 4732, // Karil's
			4745, 4747, 4749, 4751, // Torag's
			4708, 4710, 4712, 4714, // Ahrim's
			4716, 4718, 4720, 4722, // Dharok's
			4724, 4726, 4728, 4730, // Guthan's
	};

	/**
	 * Array of the barrow heads Purple Purple & green
	 */
	protected static final int[][] HEADS = {
			{ 4761, 4763, 4765, 4767, 4769, 4771 },
			{ 4761, 4762, 4763, 4764, 4765, 4766, 4767, 4768, 4769, 4770, 4771 }, };

	/**
	 * The X and Y you must be stood on to use the crypt stairs, also the coords
	 * you will be teleport to upon entering a crypt.
	 */
	protected static final int[][] STAIR_COORDS = { { 3578, 9706 }, // Verac.
			{ 3556, 9718 }, // Dharok.
			{ 3557, 9703 }, // Ahrim.
			{ 3534, 9704 }, // Guthan.
			{ 3546, 9684 }, // Karil.
			{ 3568, 9683 }, // Torag.
	};

	/**
	 * The area the player must be stood in to open the coffin.
	 */
	protected static final int[][] COFFIN_AREA = { { 3572, 9704, 3575, 9708 }, // Verac.
			{ 3553, 9713, 3557, 9716 }, // Dharok.
			{ 3554, 9697, 3557, 9701 }, // Ahrim.
			{ 3537, 9702, 3541, 9705 }, // Guthan.
			{ 3549, 9681, 3552, 9685 }, // Karil.
			{ 3568, 9684, 3571, 9688 }, // Torag.
	};

	/**
	 * Tunnel door Id's.
	 */
	protected static final int[] DOORS = { 6747, 6741, 6735, 6739, 6746, 6745,
			6737, 6735, 6728, 6722, 6716, 6720, 6727, 6726, 6718, 6716, 6731,
			6728, 6722, 6720, 6727, 6731, 6726, 6718, 6750, 6747, 6741, 6739,
			6746, 6750, 6745, 6737, 6742, 6749, 6748, 6743, 6744, 6740, 6742,
			6738, 6723, 6730, 6729, 6724, 6725, 6723, 6721, 6719, 6749, 6748,
			6736, 6743, 6744, 6740, 6738, 6736, 6730, 6729, 6717, 6724, 6725,
			6721, 6719, 6717, };

	/**
	 * Tunnel door locations (X&Y), their index is the relevant to the door id
	 * in DOORS[].
	 */
	protected static final int[][] DOOR_LOCATION = { { 3569, 9684 },
			{ 3569, 9701 }, { 3569, 9718 }, { 3552, 9701 }, { 3552, 9684 },
			{ 3535, 9684 }, { 3535, 9701 }, { 3535, 9718 }, { 3568, 9684 },
			{ 3568, 9701 }, { 3568, 9718 }, { 3551, 9701 }, { 3551, 9684 },
			{ 3534, 9684 }, { 3534, 9701 }, { 3534, 9718 }, { 3569, 9671 },
			{ 3569, 9688 }, { 3569, 9705 }, { 3552, 9705 }, { 3552, 9688 },
			{ 3535, 9671 }, { 3535, 9688 }, { 3535, 9705 }, { 3568, 9671 },
			{ 3568, 9688 }, { 3568, 9705 }, { 3551, 9705 }, { 3551, 9688 },
			{ 3534, 9671 }, { 3534, 9688 }, { 3534, 9705 }, { 3575, 9677 },
			{ 3558, 9677 }, { 3541, 9677 }, { 3541, 9694 }, { 3558, 9694 },
			{ 3558, 9711 }, { 3575, 9711 }, { 3541, 9711 }, { 3575, 9678 },
			{ 3558, 9678 }, { 3541, 9678 }, { 3541, 9695 }, { 3558, 9695 },
			{ 3575, 9712 }, { 3558, 9712 }, { 3541, 9712 }, { 3562, 9678 },
			{ 3545, 9678 }, { 3528, 9678 }, { 3545, 9695 }, { 3562, 9695 },
			{ 3562, 9712 }, { 3545, 9712 }, { 3528, 9712 }, { 3562, 9677 },
			{ 3545, 9677 }, { 3528, 9677 }, { 3545, 9694 }, { 3562, 9694 },
			{ 3562, 9711 }, { 3545, 9711 }, { 3528, 9711 }, };

	/**
	 * The direction the door will face when opening Door id, face, distance
	 * from old doors (distance from doors = 1 = x+1, 2 = y+1, 3 = x-1, 4 = y-1)
	 */
	protected static final int[][] DOOR_OPEN_DIRECTION = { { 6732, 2, 4 },
			{ 6732, 2, 4 }, { 6732, 2, 4 }, { 6732, 2, 4 }, { 6732, 2, 4 },
			{ 6732, 2, 4 }, { 6732, 2, 4 }, { 6732, 2, 4 }, { 6713, 0, 4 },
			{ 6713, 0, 4 }, { 6713, 0, 4 }, { 6713, 0, 4 }, { 6713, 0, 4 },
			{ 6713, 0, 4 }, { 6713, 0, 4 }, { 6713, 0, 4 }, { 6713, 2, 2 },
			{ 6713, 2, 2 }, { 6713, 2, 2 }, { 6713, 2, 2 }, { 6713, 2, 2 },
			{ 6713, 2, 2 }, { 6713, 2, 2 }, { 6713, 2, 2 }, { 6732, 4, 2 },
			{ 6732, 4, 2 }, { 6732, 4, 2 }, { 6732, 4, 2 }, { 6732, 4, 2 },
			{ 6732, 4, 2 }, { 6732, 4, 2 }, { 6732, 4, 2 }, { 6732, 3, 3 },
			{ 6732, 3, 3 }, { 6732, 3, 3 }, { 6732, 3, 3 }, { 6732, 3, 3 },
			{ 6732, 3, 3 }, { 6732, 3, 3 }, { 6732, 3, 3 }, { 6713, 1, 3 },
			{ 6713, 1, 3 }, { 6713, 1, 3 }, { 6713, 1, 3 }, { 6713, 1, 3 },
			{ 6713, 1, 3 }, { 6713, 1, 3 }, { 6713, 1, 3 }, { 6732, 1, 1 },
			{ 6732, 1, 1 }, { 6732, 1, 1 }, { 6732, 1, 1 }, { 6732, 1, 1 },
			{ 6732, 1, 1 }, { 6732, 1, 1 }, { 6732, 1, 1 }, { 6713, 3, 1 },
			{ 6713, 3, 1 }, { 6713, 3, 1 }, { 6713, 3, 1 }, { 6713, 3, 1 },
			{ 6713, 3, 1 }, { 6713, 3, 1 }, { 6713, 3, 1 }, };

	/**
	 * An array of coordinates for the 'mini tunnels' inbetween doors. X, Y, X2,
	 * Y2
	 */
	protected static final int[][] DB = { { 3532, 9665, 3570, 9671 },
			{ 3575, 9676, 3581, 9714 }, { 3534, 9718, 3570, 9723 },
			{ 3523, 9675, 3528, 9712 }, { 3541, 9711, 3545, 9712 },
			{ 3558, 9711, 3562, 9712 }, { 3568, 9701, 3569, 9705 },
			{ 3551, 9701, 3552, 9705 }, { 3534, 9701, 3535, 9705 },
			{ 3541, 9694, 3545, 9695 }, { 3558, 9694, 3562, 9695 },
			{ 3568, 9684, 3569, 9688 }, { 3551, 9684, 3552, 9688 },
			{ 3534, 9684, 3535, 9688 }, { 3541, 9677, 3545, 9678 },
			{ 3558, 9677, 3562, 9678 }, };

	/**
	 * Other rewards.
	 */
	protected static final int[] OTHER_REWARDS = { 4740, // Bolt rack.
			995, // Money.
			560, // Death runes.
			565, // Blood runes.
			562, // Chaos runes.
			558, // Mind runes.
	};

	protected static final int[][] REWARD_KILLCOUNT = {
			{ 6, 15, 25, 38, 75, 143, 373, 563, 838, 1734, 2843, 3948, 4733,
					5938, 6883, 8232, 9639 },
			{ 6, 7, 22, 59, 121, 283, 694, 1038, 1774, 2533, 3746, 4837, 5661,
					6880, 7390, 8403, 9840 },
			{ 6, 8, 19, 43, 92, 186, 228, 473, 771, 990, 1484, 1945, 2566,
					3849, 5002, 5982, 6760, 7389, 8923 },
			{ 6, 20, 45, 74, 135, 201, 273, 483, 893, 1027, 1877, 2043, 2837,
					3766, 4650, 5847, 7299, 8034, 8774, 9531 },
			{ 6, 15, 29, 78, 129, 198, 287, 407, 694, 883, 1287, 2084, 2776,
					3581, 4299, 5400, 6839, 8394, 9984 },
			{ 6, 17, 39, 100, 199, 401, 674, 886, 1083, 1572, 2037, 3684, 4763,
					6847, 7049, 8164, 8918, 9927 }, };

	protected static final int[][] REWARD_AMOUNT = {
			{ 98, 144, 179, 202, 232, 293, 390, 421, 489, 529, 590, 683, 772,
					805, 993, 1482, 1823 }, // Amounts
			{ 1779, 1934, 2844, 4554, 6948, 8771, 9028, 11837, 14839, 19837,
					24827, 30485, 35774, 46384, 58344, 78374, 108334 },
			{ 86, 111, 163, 187, 231, 300, 379, 402, 502, 592, 607, 699, 782,
					901, 983, 1386, 1746, 2049, 2673 },
			{ 61, 103, 172, 200, 233, 304, 355, 442, 511, 573, 599, 661, 701,
					780, 892, 990, 1254, 1532, 1763, 1994 },
			{ 160, 242, 277, 304, 398, 465, 503, 606, 698, 799, 872, 945, 1023,
					1382, 1491, 1687, 1983, 2455, 2873 },
			{ 283, 473, 701, 892, 1033, 1983, 2387, 2763, 3884, 4118, 4479,
					4766, 5520, 6948, 7389, 7689, 8376, 8911, 10938 }, };

	protected static final int[] MINIMUM_AMOUNT = { 39, // Bolt rack.
			1000, // Money.
			51, // Death runes.
			38, // Blood runes.
			84, // Chaos runes.
			139, // Mind runes.
	};

	protected static final Location getLocation(int npcId) {
		Location location = null;
		switch (npcId) {
		case 2025:
			location = AHRIMS_TOMB;
			break;
		case 2026:
			location = DHAROKS_TOMB;
			break;
		case 2027:
			location = GUTHANS_TOMB;
			break;
		case 2028:
			location = KARILS_TOMB;
			break;
		case 2029:
			location = TORAGS_TOMB;
			break;
		case 2030:
			location = VERACS_TOMB;
			break;
		}
		return location;
	}

	public BarrowsData() {

	}

}
