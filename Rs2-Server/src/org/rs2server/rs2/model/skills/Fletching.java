package org.rs2server.rs2.model.skills;

import java.util.HashMap;
import java.util.Map;
import org.rs2server.rs2.Constants;

import org.rs2server.rs2.action.impl.ProductionAction;
import org.rs2server.rs2.model.*;

/**
 * Josh Mai
 * 
 */
public class Fletching extends ProductionAction {

	public static enum ArrowTip {

		BRONZE(39, 882, 1, 2.6), IRON(40, 884, 15, 3.8), STEEL(41, 886, 30, 6.3), MITHRIL(
				42, 888, 45, 8.8), ADAMANT(43, 890, 60, 11.3), RUNE(44, 892,
				75, 13.8);
		/**
		 * The id
		 */
		private int id;
		/**
		 * The reward;
		 */
		private int reward;
		/**
		 * The level required.
		 */
		private int levelRequired;
		/**
		 * The experience granted.
		 */
		private double experience;
		/**
		 * A map of item ids to arrow tips.
		 */
		private static Map<Integer, ArrowTip> arrowtips = new HashMap<Integer, ArrowTip>();

		/**
		 * Populates the log map.
		 */
		static {
			for (ArrowTip arrowtip : ArrowTip.values()) {
				arrowtips.put(arrowtip.id, arrowtip);
			}
		}

		/**
		 * Gets an arrow tip by an item id.
		 * 
		 * @param item
		 *            The item id.
		 * @return The ArrowTip, or <code>null</code> if the object is not a
		 *         arrow tip.
		 */
		public static ArrowTip forId(int item) {
			return arrowtips.get(item);
		}

		private ArrowTip(int id, int reward, int levelRequired,
				double experience) {
			this.id = id;
			this.reward = reward;
			this.levelRequired = levelRequired;
			this.experience = experience;
		}

		/**
		 * @return the experience
		 */
		public double getExperience() {
			return experience;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the levelRequired
		 */
		public int getLevelRequired() {
			return levelRequired;
		}

		/**
		 * @return the reward
		 */
		public int getReward() {
			return reward;
		}
	}

	public static enum Bolt {

		BRONZE(9375, 877, 9, 0.5), IRON(9377, 9140, 39, 1.5), STEEL(9378, 9141,
				46, 3.5), MITHRIL(9379, 9142, 54, 5), ADAMANT(9380, 9143, 61, 7), RUNE(
				9381, 9144, 69, 10);
		/**
		 * The id
		 */
		private int id;
		/**
		 * The reward;
		 */
		private int reward;
		/**
		 * The level required.
		 */
		private int levelRequired;
		/**
		 * The experience granted.
		 */
		private double experience;
		/**
		 * A map of item ids to arrow tips.
		 */
		private static final Map<Integer, Bolt> bolts = new HashMap<Integer, Bolt>();

		/**
		 * Populates the log map.
		 */
		static {
			for (Bolt bolt : Bolt.values()) {
				bolts.put(bolt.id, bolt);
			}
		}

		/**
		 * Gets an arrow tip by an item id.
		 * 
		 * @param item
		 *            The item id.
		 * @return The ArrowTip, or <code>null</code> if the object is not a
		 *         arrow tip.
		 */
		public static Bolt forId(int item) {
			return bolts.get(item);
		}

		private Bolt(int id, int reward, int levelRequired, double experience) {
			this.id = id;
			this.reward = reward;
			this.levelRequired = levelRequired;
			this.experience = experience;
		}

		/**
		 * @return the experience
		 */
		public double getExperience() {
			return experience;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the levelRequired
		 */
		public int getLevelRequired() {
			return levelRequired;
		}

		/**
		 * @return the reward
		 */
		public int getReward() {
			return reward;
		}
	}

	/**
	 * An enumeration storing all the bow data
	 * 
	 * @author Josh
	 * 
	 */
	public enum Bow {

		NORMAL_SHORTBOW(50, 841, 1, 5), NORMAL_LONGBOW(48, 839, 10, 10), OAK_SHORTBOW(
				54, 843, 20, 16.5), OAK_LONGBOW(56, 845, 25, 25), WILLOW_SHORTBOW(
				60, 849, 35, 33.25), WILLOW_LONGBOW(58, 847, 40, 41.5), MAPLE_SHORTBOW(
				64, 853, 50, 50), MAPLE_LONGBOW(62, 851, 55, 58.2), YEW_SHORTBOW(
				68, 857, 65, 67.5), YEW_LONGBOW(66, 855, 70, 75), MAGIC_SHORTBOW(
				72, 861, 80, 83.2), MAGIC_LONGBOW(70, 859, 85, 91.5);
		/**
		 * The unstrung bow
		 */
		private final int item;
		/**
		 * The finished product
		 */
		private final int product;
		/**
		 * The level required for making a bow
		 */
		private final int levelRequired;
		/**
		 * The experience gained from making the bow
		 */
		private final double experience;
		/**
		 * A {@link Map} of the {@link Bow}s in this enum
		 */
		private static final Map<Integer, Bow> bow = new HashMap<Integer, Bow>();

		/**
		 * Fill in the values in the {@link bow}s {@link HashMap} to make it
		 * easier to find back the values
		 */
		static {
			for (Bow b : Bow.values()) {
				bow.put(b.getItem(), b);
			}
		}

		public static Bow forId(int item) {
			return bow.get(item);
		}

		/**
		 * Constructs a new {@link Bow}s object w/ given parameters
		 * 
		 * @param item
		 *            The unstrung bow
		 * @param product
		 *            The strung bow
		 * @param levelRequired
		 *            The level required for a bow
		 * @param experience
		 *            The experience gained for a bow
		 */
		Bow(int item, int product, int levelRequired, double experience) {
			this.item = item;
			this.product = product;
			this.levelRequired = levelRequired;
			this.experience = experience;
		}

		/**
		 * The experience gained from crafting a bow
		 * 
		 * @return The experience gained from crafting a bow
		 */
		public double getExperience() {
			return experience;
		}

		/**
		 * The unstrung bows that need stringing
		 * 
		 * @return the bows that need to be strung
		 */
		public int getItem() {
			return item;
		}

		/**
		 * The level that is required for a bow
		 * 
		 * @return The certain level for a required bow
		 */
		public int getLevelRequired() {
			return levelRequired;
		}

		/**
		 * The finished product or "strung bow"
		 * 
		 * @return The finished product
		 */
		public int getProduct() {
			return product;
		}
	}

	/**
	 * Represents a type of log that is able to be fletched.
	 * 
	 * @author Michael
	 * 
	 */
	public static enum Log {

		NORMAL(1511, new int[] { /*
								 * 52,
								 */53, 50, 48, 9440 },
				new int[] { 1, 5, 10, 9 }, new double[] { 5, 10, 20, 24 }), OAK(
				1521, new int[] { 54, 9442, 56 }, new int[] { 20, 24, 25 },
				new double[] { 16.5, 30, 25 }), WILLOW(1519, new int[] { 60,
				9444, 58 }, new int[] { 35, 39, 40 },
				new double[] { 30, 35, 35 }), MAPLE(1517, new int[] { 64, 9448,
				62 }, new int[] { 50, 54, 55 }, new double[] { 35, 40, 40 }), YEW(
				1515, new int[] { 68, 9452, 66 }, new int[] { 65, 69, 70 },
				new double[] { 67.5, 70, 75 }), MAGIC(1513,
				new int[] { 72, 70 }, new int[] { 80, 85 }, new double[] {
						83.25, 91.5 });
		/**
		 * The id of the logs
		 */
		private int logId;

		/**
		 * The first item displayed on the fletching interface.
		 */
		private int[] item;

		/**
		 * The level required to fletch the first item on the fletching
		 * interface.
		 */
		private int[] level;
		/**
		 * The experience granted for the first item on the flteching interface.
		 */
		private double[] experience;
		/**
		 * A map of item ids to logs.
		 */
		private static Map<Integer, Log> logs = new HashMap<Integer, Log>();
		/**
		 * Populates the log map.
		 */
		static {
			for (Log log : Log.values()) {
				logs.put(log.logId, log);
			}
		}

		/**
		 * Gets a log by an item id.
		 * 
		 * @param item
		 *            The item id.
		 * @return The Log, or <code>null</code> if the object is not a log.
		 */
		public static Log forId(int item) {
			return logs.get(item);
		}

		private Log(int logId, int[] item, int[] level, double[] experience) {
			this.logId = logId;
			this.item = item;
			this.level = level;
			this.experience = experience;
		}

		/**
		 * @return the experience
		 */
		public double[] getExperience() {
			return experience;
		}

		/**
		 * @return the item
		 */
		public int[] getItem() {
			return item;
		}

		/**
		 * @return the level
		 */
		public int[] getLevel() {
			return level;
		}

		/**
		 * @return the logId
		 */
		public int getLogId() {
			return logId;
		}
	}

	/**
	 * Handles the player attaching the string to the bow
	 * 
	 * @param player
	 *            The player creating the bow
	 * @param itemUsed
	 *            The item being used
	 * @param usedWith
	 *            The item having an action being done to it.
	 */
	public static void stringBow(Player player, int itemUsed, int usedWith) {
		int itemId = itemUsed != 1777 ? itemUsed : usedWith;
		if (Bow.forId(itemId) == null) {
			return;
		}
		if (player.getSkills().getLevelForExperience(Skills.FLETCHING) < Bow
				.forId(itemId).getLevelRequired()) {
			player.getActionSender().sendMessage(
					"You need a Fletching level of "
							+ Bow.forId(itemId).getLevelRequired()
							+ " to string this bow.");
			return;
		}
		if (itemUsed == 1777 && usedWith == Bow.forId(itemId).getItem()
				|| usedWith == 1777 && itemUsed == Bow.forId(itemId).getItem()) {
			player.getInventory().remove(new Item(1777, 1));
			player.getInventory().remove(
					new Item(Bow.forId(itemId).getItem(), 1));
			player.playAnimation(Animation.create(
					getStringingAnimation(itemUsed == 1777 ? usedWith
							: itemUsed), 0));
			player.getInventory().add(
					new Item(Bow.forId(itemId).getProduct(), 1));
			player.getSkills().addExperience(
					9,
					Bow.forId(itemId).getExperience()
							* Constants.getExpModifier());
			player.getActionSender().sendMessage(
					"You attach the string to the bow.");
		}
	}

	/**
	 * The amount of items to produce.
	 */
	private int productionCount;

	/**
	 * The log index.
	 */
	private int logIndex;

	/**
	 * The log we are fletching.
	 */
	private Log log;

	public Fletching(Mob mob, int productionCount, int logIndex, Log log) {
		super(mob);
		this.productionCount = productionCount;
		this.logIndex = logIndex;
		this.log = log;
	}

	/**
	 * TODO make sure this isn't sending wrong animations i was so tired - sneaky
	 * 
	 * @param bowId
	 *            the bow to be strung.
	 * @return the animation corresponding the the correct bow.
	 */
	public static int getStringingAnimation(int bowId) {
		System.out.println(bowId);
		switch (bowId) {
		case 48:
			return 6684;
		case 50:
			return 6678;
		case 54:
			return 6679;
		case 56:
			return 6685;
		case 58:
			return 6686;
		case 60:
			return 6680;
		case 62:
			return 6687;
		case 64:
			return 6681;
		case 66:
			return 6688;
		case 68:
			return 6682;
		case 70:
			return 6689;
		case 72:
			return 6683;
		default:
			return -1;
		}
	}

	@Override
	public boolean canProduce() {
		return true;
	}

	@Override
	public Animation getAnimation() {
		return Animation.create(1248);
	}

	@Override
	public Item[] getConsumedItems() {
		return new Item[] { new Item(log.getLogId()) };
	}

	@Override
	public int getCycleCount() {
		return 3;
	}

	@Override
	public double getExperience() {
		return log.getExperience()[logIndex];
	}

	@Override
	public Graphic getGraphic() {
		return null;
	}

	@Override
	public String getInsufficentLevelMessage() {
		return "You need a Fletching level of " + getRequiredLevel()
				+ " to fletch this.";
	}

	@Override
	public int getProductionCount() {
		return productionCount;
	}

	@Override
	public int getRequiredLevel() {
		return log.getLevel()[logIndex];
	}

	@Override
	public Item[] getRewards() {
		return new Item[] { new Item(log.getItem()[logIndex],
				log.getItem()[logIndex] == 53 ? 15 : 1) };
	}

	@Override
	public int getSkill() {
		return Skills.FLETCHING;
	}

	@Override
	public Sound getSound() {
		return null;
	}

	@Override
	public String getSuccessfulProductionMessage() {
		String prefix = "a";
		String suffix = "";
		String result = ItemDefinition.forId(log.getItem()[logIndex]).getName()
				.toLowerCase();
		char first = ItemDefinition.forId(log.getItem()[logIndex]).getName()
				.toLowerCase().charAt(0);
		if (first == 'a' || first == 'e' || first == 'i' || first == 'o'
				|| first == 'u') {
			prefix = "an";
		}
		if (log.getItem()[logIndex] == 52) {
			prefix = "some";
			suffix = "s";
		}
		if (result.contains("shortbow")) {
			result = "shortbow";
		} else if (result.contains("longbow")) {
			result = "longbow";
		} else if (result.contains("stock")) {
			result = "crossbow stock";
		}

		return "You carefully cut the logs " + "into " + prefix + " " + result
				+ suffix + ".";
	}
}
