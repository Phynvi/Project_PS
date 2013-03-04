/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.skills;

import java.util.HashMap;
import java.util.Map;
import org.rs2server.rs2.action.impl.ProductionAction;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Graphic;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.Sound;

/**
 * 
 * @author Steve
 */
public class Smelting extends ProductionAction {

	public static enum Bar {

		BRONZE(2349, 12.5, new Item[] { new Item(438), new Item(436) }, 1), IRON(
				2351, 15, new Item[] { new Item(440) }, 15), STEEL(2353, 20,
				new Item[] { new Item(440), new Item(453, 2) }, 30), MITHRIL(
				2359, 25, new Item[] { new Item(447), new Item(453, 4) }, 50), ADAMANT(
				2361, 30, new Item[] { new Item(449), new Item(453, 6) }, 70), RUNITE(
				2363, 35, new Item[] { new Item(451), new Item(453, 8) }, 85);
		/**
		 * The item id.
		 */
		private int id;
		/**
		 * The experience granted per bar consumed.
		 */
		private double experience;
		/**
		 * The array of items this bar can make.
		 */
		private Item[] ores;
		/**
		 * The level required to make the item.
		 */
		private int levelRequired;
		/**
		 * The map of item ids to bars.
		 */
		private static Map<Integer, Bar> bars = new HashMap<Integer, Bar>();

		/**
		 * Populates the bar map.
		 */
		static {
			for (Bar bar : Bar.values()) {
				bars.put(bar.id, bar);
			}
		}

		public static Bar forId(int item) {
			return bars.get(item);
		}

		/**
		 * Gets a bar by an item id.
		 * 
		 * @param item
		 *            The item id.
		 * @return The <code>Bar</code> or <code>null</code> if the item is not
		 *         a bar.
		 */
		public static Bar forOreId(int item) {
			for (Bar bar : Bar.values()) {
				for (Item item2 : bar.getOres()) {
					if (item2.getId() == item) {
						return bar;
					}
				}
			}
			return null;
		}

		private Bar(int id, double experience, Item[] ores, int levelRequired) {
			this.id = id;
			this.ores = ores;
			this.experience = experience;
			this.levelRequired = levelRequired;
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
		 * @return the items
		 */
		public Item getOre(int i) {
			return ores[i];
		}

		/**
		 * @return the items
		 */
		public Item[] getOres() {
			return ores;
		}
	}

	public static final int INTERFACE = 311;

	public static void showSmeltingInterface(Mob mob) {
		int interfaceId = INTERFACE;
		mob.getActionSender().sendInterfaceModel(311, 5, 125, 9467);// bluerite
		mob.getActionSender().sendInterfaceModel(311, 6, 125, 2351);// iron
		mob.getActionSender().sendInterfaceModel(311, 7, 125, 2355);// silver
		mob.getActionSender().sendInterfaceModel(311, 8, 125, 2353);// steel
		mob.getActionSender().sendInterfaceModel(311, 9, 125, 2357);// gold
		mob.getActionSender().sendInterfaceModel(311, 10, 125, 2359);// mith
		mob.getActionSender().sendInterfaceModel(311, 11, 125, 2361);// addy
		mob.getActionSender().sendInterfaceModel(311, 12, 125, 2363);// rune
		mob.getActionSender().sendChatboxInterface(interfaceId);
	}

	private Bar bar;

	private int productionAmount;

	public Smelting(Mob mob, Bar bar, int productionAmount) {
		super(mob);
		this.bar = bar;
		this.productionAmount = productionAmount;
	}

	@Override
	public boolean canProduce() {
		return true;
	}

	@Override
	public Animation getAnimation() {
		return Animation.create(899);
	}

	public Bar getBar() {
		return bar;
	}

	@Override
	public Item[] getConsumedItems() {
		return bar.getOres();
	}

	@Override
	public int getCycleCount() {
		return 4;
	}

	@Override
	public double getExperience() {
		return bar.getExperience();
	}

	@Override
	public Graphic getGraphic() {
		return null;
	}

	@Override
	public String getInsufficentLevelMessage() {
		return "You need a Smithing level of " + this.getRequiredLevel()
				+ " to smelt this.";
	}

	@Override
	public int getProductionCount() {
		return productionAmount;
	}

	@Override
	public int getRequiredLevel() {
		return bar.getLevelRequired();
	}

	@Override
	public Item[] getRewards() {
		return new Item[] { new Item(bar.getId()) };
	}

	@Override
	public int getSkill() {
		return Skills.SMITHING;
	}

	@Override
	public Sound getSound() {
		return Sound.create(2725, (byte) 1, 0);
	}

	@Override
	public String getSuccessfulProductionMessage() {
		return "You smelt the ore into a(n) "
				+ new Item(bar.getId()).getDefinition().getName();
	}
}
