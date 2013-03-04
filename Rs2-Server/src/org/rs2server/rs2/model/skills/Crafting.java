/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.skills;

import org.rs2server.rs2.action.impl.ProductionAction;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Graphic;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.Sound;

/**
 * 
 * @author Stephen Soltys
 */
public class Crafting extends ProductionAction {

	public enum Gem {

		OPAL(1625, 1609, 891, 1, 15, true), JADE(1627, 1611, 891, 13, 20, true), REDTOPAZ(
				1629, 1613, 892, 16, 25, true), SAPPHIRE(1623, 1607, 888, 1,
				50, false), EMERALD(1621, 1605, 889, 27, 68, false), RUBY(1619,
				1603, 887, 34, 85, false), DIAMOND(1617, 1601, 890, 43, 108,
				false), DRAGONSTONE(1631, 1615, 890, 55, 138, false), ONYX(
				6571, 6573, 2717, 67, 168, false);
		private int uncutID, cutID, animation, levelReq, XP;
		private boolean isSemiPrecious;

		private Gem(int uncutID, int cutID, int animation, int levelReq,
				int XP, boolean semiPrecious) {
			this.uncutID = uncutID;
			this.cutID = cutID;
			this.animation = animation;
			this.levelReq = levelReq;
			this.XP = XP;
			this.isSemiPrecious = semiPrecious;
		}

		public int getAnim() {
			return animation;
		}

		public int getCut() {
			return cutID;
		}

		public int getReq() {
			return levelReq;
		}

		public int getUncut() {
			return uncutID;
		}

		public int getXP() {
			return XP;
		}

		public boolean isSemiPrecious() {
			return isSemiPrecious;
		}
	}

	public enum Hide {

		COWHIDE(1739, 1741), GREEN_DRAGONHIDE(1753, 1745);
		private int id, outcome;

		private Hide(int id, int outcome) {
			this.id = id;
			this.outcome = outcome;
		}
	}

	public enum Leather {

		LEATHERGLOVES(1741, 1059, 1, 13.8, 1), LEATHERBOOTS(1741, 1061, 7,
				16.25, 1), LEATHERCOWL(1741, 1167, 9, 18.5, 1), LEATHERVAMBS(
				1741, 1063, 11, 22, 1), LEATHERBODY(1741, 1129, 14, 25, 1), LEATHERCHAPS(
				1741, 1095, 18, 27, 1), COIF(1741, 1169, 38, 37, 1), GREENVAMBS(
				1745, 1065, 57, 62, 1), GREENCHAPS(1745, 1099, 60, 124, 2), GREENBODY(
				1745, 1135, 63, 186, 3), BLUEVAMBS(2505, 2487, 66, 70, 1), BLUECHAPS(
				2505, 2493, 68, 140, 2), BLUEBODY(2505, 2499, 71, 210, 3), REDVAMBS(
				2507, 2489, 73, 78, 1), REDCHAPS(2507, 2495, 75, 156, 2), REDBODY(
				2507, 2501, 77, 234, 3), BLACKVAMBS(2509, 2491, 79, 86, 1), BLACKCHAPS(
				2509, 2497, 82, 172, 2), BLACKBODY(2509, 2503, 84, 258, 3);
		private int leatherId, outcome, reqLevel, reqAmt;
		private double XP;

		private Leather(int leatherId, int outcome, int reqLevel, double XP,
				int reqAmt) {
			this.leatherId = leatherId;
			this.outcome = outcome;
			this.reqLevel = reqLevel;
			this.XP = XP;
			this.reqAmt = reqAmt;
		}

		public int getLeather() {
			return leatherId;
		}

		public int getOutcome() {
			return outcome;
		}

		public int getReqAmt() {
			return reqAmt;
		}

		public int getReqLevel() {
			return reqLevel;
		}

		public double getXP() {
			return XP;
		}
	}

	public enum Spinning {

		BOWSTRING(new Item(1779), new Item(1777), Animation.create(894), 15, 10);
		private Item item, outcome;
		private Animation animation;
		private double experience;
		private int requiredLevel;

		private Spinning(Item item, Item outcome, Animation animation,
				double experience, int requiredLevel) {
			this.item = item;
			this.outcome = outcome;
			this.animation = animation;
			this.experience = experience;
			this.requiredLevel = requiredLevel;
		}

		public Animation getAnimation() {
			return animation;
		}

		public double getExperience() {
			return experience;
		}

		public Item getItem() {
			return item;
		}

		public Item getOutcome() {
			return outcome;
		}

		public int getRequiredLevel() {
			return requiredLevel;
		}
	}

	public static Gem gemForId(int id) {
		for (Gem g : Gem.values()) {
			if (g.getUncut() == id) {
				return g;
			}
		}
		return null;
	}

	public static Leather getDragonLeather(int index, int leather) {
		switch (index) {
		case 0: // vambs
			switch (leather) {
			case 1745:
				return Leather.GREENVAMBS;
			case 2505:
				return Leather.BLUEVAMBS;
			case 2507:
				return Leather.REDVAMBS;
			case 2509:
				return Leather.BLACKVAMBS;
			}
			break;
		case 1: // chaps
			switch (leather) {
			case 1745:
				return Leather.GREENCHAPS;
			case 2505:
				return Leather.BLUECHAPS;
			case 2507:
				return Leather.REDCHAPS;
			case 2509:
				return Leather.BLACKCHAPS;
			}
			break;
		case 2: // body
			switch (leather) {
			case 1745:
				return Leather.GREENBODY;
			case 2505:
				return Leather.BLUEBODY;
			case 2507:
				return Leather.REDBODY;
			case 2509:
				return Leather.BLACKBODY;
			}
			break;
		}
		return null;
	}

	public static Leather leatherForId(int id) {
		for (Leather lc : Leather.values()) {
			if (lc.getLeather() == id) {
				return lc;
			}
		}
		return null;
	}

	private Gem gem;

	private Leather leather;

	private Hide hide;

	private Spinning spinningTask;

	private int productionCount;

	private int index;

	public Crafting(Mob mob, Gem gem, Leather leather, int productionCount) {
		super(mob);
		this.gem = gem;
		this.leather = leather;
		if (mob.getAttribute("spinningTask") != null) {
			this.spinningTask = (Spinning) mob.getAttribute("spinningTask");
			mob.setAttribute("spinningTask", null);
		}
		this.productionCount = productionCount;
	}

	@Override
	public boolean canProduce() {
		return true;
	}

	@Override
	public Animation getAnimation() {
		if (leather != null) {
			return Animation.create(1249);
		} else if (gem != null) {
			return Animation.create(gem.getAnim());
		} else if (spinningTask != null) {
			return spinningTask.getAnimation();
		}
		return null;
	}

	@Override
	public Item[] getConsumedItems() {
		if (leather != null) {
			return new Item[] {
					new Item(leather.getLeather(), leather.getReqAmt()),
					new Item(1733, leather.getReqAmt()), new Item(1734, 1) };
		} else if (gem != null) {
			return new Item[] { new Item(gem.getUncut(), 1) };
		} else if (spinningTask != null) {
			return new Item[] { spinningTask.getItem() };
		}
		return null;
	}

	@Override
	public int getCycleCount() {
		if (this.spinningTask != null) {
			return 3;
		}
		return 4;
	}

	@Override
	public double getExperience() {
		if (leather != null) {
			return leather.getXP();
		} else if (gem != null) {
			return gem.getXP();
		} else if (spinningTask != null) {
			return spinningTask.getExperience();
		}
		return 1;
	}

	@Override
	public Graphic getGraphic() {
		return null;
	}

	@Override
	public String getInsufficentLevelMessage() {
		String z = "1";
		if (leather != null) {
			z = "" + leather.getReqLevel();
		} else if (gem != null) {
			z = "" + gem.getReq();
		} else if (spinningTask != null) {
			z = "" + spinningTask.getRequiredLevel();
		}
		return "You need a Crafting level of " + z + " to do that.";
	}

	@Override
	public int getProductionCount() {
		return productionCount;
	}

	@Override
	public int getRequiredLevel() {
		if (leather != null) {
			return leather.getReqLevel();
		} else if (gem != null) {
			return gem.getReq();
		} else if (spinningTask != null) {
			return spinningTask.getRequiredLevel();
		}
		return 1;
	}

	@Override
	public Item[] getRewards() {
		if (leather != null) {
			return new Item[] { new Item(leather.getOutcome(), 1) };
		} else if (gem != null) {
			return new Item[] { new Item(gem.getCut(), 1) };
		} else if (spinningTask != null) {
			return new Item[] { spinningTask.getOutcome() };
		}
		return null;
	}

	@Override
	public int getSkill() {
		return Skills.CRAFTING;
	}

	@Override
	public Sound getSound() {
		return null;
	}

	@Override
	public String getSuccessfulProductionMessage() {
		if (leather != null) {
			return "You craft the "
					+ new Item(leather.getLeather(), 1).getDefinition()
							.getName()
					+ " into "
					+ new Item(leather.getOutcome(), 1).getDefinition()
							.getName() + ".";
		} else if (gem != null) {
			return "You cut the "
					+ this.getRewards()[0].getDefinition().getName() + ".";
		} else if (spinningTask != null) {
			return "You spin the "
					+ spinningTask.getItem().getDefinition().getName()
					+ " into a "
					+ spinningTask.getOutcome().getDefinition().getName() + ".";
		}
		return "";
	}
}
