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
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.Sound;

/**
 * 
 * @author Stephen Soltys
 */
public class Runecrafting extends ProductionAction {

	public static enum Rune {

		AIR(556, 7936, 1, 5), MIND(558, 7936, 2, 5.5), WATER(555, 7936, 5, 6), EARTH(
				557, 7936, 9, 6.5), FIRE(554, 7936, 14, 7), BODY(559, 7936, 20,
				7.5), COSMIC(564, 7936, 27, 10), CHAOS(562, 7936, 35, 10), ASTRAL(
				9075, 7936, 40, 10), NATURE(561, 7936, 44, 11), LAW(563, 7936,
				54, 12), DEATH(560, 7936, 65, 13), BLOOD(565, 7936, 77, 14), SOUL(
				566, 7936, 85, 15);
		private int product;
		private int essence;
		private int levelReq;
		private double experience;

		public static Map<Integer, Rune> runes = new HashMap<Integer, Rune>();

		static {
			for (Rune rune : Rune.values()) {
				runes.put(rune.product, rune);
			}
		}

		private Rune(int product, int essence, int levelReq, double experience) {
			this.product = product;
			this.essence = essence;
			this.levelReq = levelReq;
			this.experience = experience;
		}

		public int getEssenceType() {
			return essence;
		}

		public double getExp() {
			return experience;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getProduct() {
			return product;
		}
	}

	private Rune rune;

	private int essCount;

	private static int[][] HANDLE_MULTIPLIER = { // TODO Could be done better.
	// rune, level
			{ 556, 11, 22, 33, 44, 55, 66, 77, 88, 99 }, // air
			{ 558, 14, 28, 42, 56, 70, 84, 98 }, // mind
			{ 555, 19, 38, 57, 76, 95 }, // water
			{ 557, 26, 52, 78 }, // earth
			{ 554, 35, 70 }, // fire
			{ 559, 46, 92 } // body
	};

	public static Rune forId(int clickId) {
		switch (clickId) {
		case 2478: // Air alter
			return Rune.AIR;
		case 2479: // Mind Alter
			return Rune.MIND;
		case 2480: // Water Alter
			return Rune.WATER;
		case 2481: // Earth Alter
			return Rune.EARTH;
		case 2482: // Fire Alter
			return Rune.FIRE;
		case 2483: // Body Alter
			return Rune.BODY;
		case 2484: // Cosmic Alter
			return Rune.COSMIC;
		case 2487: // Chaos Alter
			return Rune.CHAOS;
		case 2486: // Nature Alter
			return Rune.NATURE;
		case 2485: // Law Alter
			return Rune.LAW;
		case 2488: // Death Alter
			return Rune.DEATH;
		case 2490: // Blood Alter
			return Rune.BLOOD;
		}
		return null;
	}

	public Runecrafting(Mob mob, Rune rune) {
		super(mob);
		this.rune = rune;
		this.essCount = ((Player) mob).getInventory().getCount(
				rune.getEssenceType());
	}

	@Override
	public boolean canProduce() {
		return essCount != 0;
	}

	@Override
	public Animation getAnimation() {
		return Animation.create(791);
	}

	@Override
	public Item[] getConsumedItems() {
		return new Item[] { new Item(rune.getEssenceType(), ((Player) getMob())
				.getInventory().getCount(rune.getEssenceType())) };
	}

	@Override
	public int getCycleCount() {
		return 6;
	}

	@Override
	public double getExperience() {
		return (rune.getExp() * essCount);
	}

	@Override
	public Graphic getGraphic() {
		return Graphic.create(186, 0, 50);
	}

	@Override
	public String getInsufficentLevelMessage() {
		return "You need a level of " + rune.getLevelReq() + " to craft that.";
	}

	@Override
	public int getProductionCount() {
		return 1;
	}

	@Override
	public int getRequiredLevel() {
		return rune.getLevelReq();
	}

	@Override
	public Item[] getRewards() {
		int multiplier = 1;
		for (int i = 0; i < HANDLE_MULTIPLIER.length; i++) {
			if (rune.getProduct() == HANDLE_MULTIPLIER[i][0]) {
				for (int j = 1; j < HANDLE_MULTIPLIER[i].length; j++) {
					if (((Player) getMob()).getSkills().getLevel(
							Skills.RUNECRAFTING) >= HANDLE_MULTIPLIER[i][j]) {
						multiplier++;
					}
				}
			}
		}
		return new Item[] { new Item(rune.getProduct(), multiplier * essCount) };
	}

	@Override
	public int getSkill() {
		return Skills.RUNECRAFTING;
	}

	@Override
	public Sound getSound() {
		return null;
	}

	@Override
	public String getSuccessfulProductionMessage() {
		return "You craft the rune essence into "
				+ new Item(rune.getProduct(), 1).getDefinition().getName()
				+ "s.";
	}
}
