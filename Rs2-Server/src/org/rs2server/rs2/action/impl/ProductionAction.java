package org.rs2server.rs2.action.impl;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.DialogueManager;
import org.rs2server.rs2.model.Graphic;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Sound;

/**
 * <p>
 * A producing action is an action where on item is transformed into another,
 * typically this is in skills such as smithing and crafting.
 * </p>
 * 
 * <p>
 * This class implements code related to all production-type skills, such as
 * dealing with the action itself, replacing the items and checking levels.
 * </p>
 * 
 * <p>
 * The individual crafting, smithing, and other skills implement functionality
 * specific to them such as random events.
 * </p>
 * 
 * @author Michael (Scu11)
 */
public abstract class ProductionAction extends Action {

	/**
	 * This starts the actions animation and requirement checks, but prevents
	 * the production from immediately executing.
	 */
	private boolean started = false;

	/**
	 * The cycle count.
	 */
	private int cycleCount = 0;

	/**
	 * The amount of items to produce.
	 */
	private int productionCount = 0;

	/**
	 * Creates the production action for the specified mob.
	 * 
	 * @param mob
	 *            The mob to create the action for.
	 */
	public ProductionAction(Mob mob) {
		super(mob, 0);
	}

	/**
	 * Performs extra checks that a specific production event independently
	 * uses, e.g. checking for ingredients in herblore.
	 */
	public abstract boolean canProduce();

	@Override
	public void execute() {
		if (getMob().getSkills().getLevelForExperience(getSkill()) < getRequiredLevel()) {
			//getMob().getActionSender().removeAllInterfaces();
			DialogueManager.setMessage(getInsufficentLevelMessage());
			DialogueManager.openDialogue((Player) getMob(), 214);
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		for (Item item : getConsumedItems()) {
			if (getMob().getInventory().getCount(item.getId()) < item
					.getCount()) {
				getMob().playAnimation(Animation.create(-1));
				this.stop();
				return;
			}
		}
		if (!canProduce()) {
			this.stop();
			return;
		}
		if (!started) {
			started = true;
			if (getAnimation() != null) {
				getMob().playAnimation(getAnimation());
			}
			if (getGraphic() != null) {
				getMob().playGraphics(getGraphic());
			}
			productionCount = getProductionCount();
			cycleCount = getCycleCount();
			return;
		}

		if (cycleCount > 1) {
			cycleCount--;
			return;
		}

		if (getAnimation() != null) {
			getMob().playAnimation(getAnimation());
		}
		if (getGraphic() != null) {
			getMob().playGraphics(getGraphic());
		}

		if (getSound() != null) {
			getMob().playSound(getSound());
		}

		cycleCount = getCycleCount();

		productionCount--;

		for (Item item : getConsumedItems()) {
			getMob().getInventory().remove(item);
		}
		for (Item item : getRewards()) {
			getMob().getInventory().add(item);
		}
		getMob().getActionSender()
				.sendMessage(getSuccessfulProductionMessage());
		getMob().getSkills().addExperience(getSkill(),
				getExperience() * Constants.getExpModifier());

		if (productionCount < 1) {
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		for (Item item : getConsumedItems()) {
			if (getMob().getInventory().getCount(item.getId()) < item
					.getCount()) {
				getMob().playAnimation(Animation.create(-1));
				this.stop();
				return;
			}
		}
	}

	/**
	 * Gets the animation played whilst producing the item.
	 * 
	 * @return The animation played whilst producing the item.
	 */
	public abstract Animation getAnimation();

	@Override
	public Action.AnimationPolicy getAnimationPolicy() {
		return Action.AnimationPolicy.RESET_ALL;
	}

	@Override
	public Action.CancelPolicy getCancelPolicy() {
		return Action.CancelPolicy.ALWAYS;
	}

	/**
	 * Gets the consumed item in the production of this item.
	 * 
	 * @return The consumed item in the production of this item.
	 */
	public abstract Item[] getConsumedItems();

	/**
	 * Gets the amount of cycles before the item is produced.
	 * 
	 * @return The amount of cycles before the item is produced.
	 */
	public abstract int getCycleCount();

	/**
	 * Gets the experience granted for each item that is successfully produced.
	 * 
	 * @return The experience granted for each item that is successfully
	 *         produced.
	 */
	public abstract double getExperience();

	/**
	 * Gets the graphic played whilst producing the item.
	 * 
	 * @return The graphic played whilst producing the item.
	 */
	public abstract Graphic getGraphic();

	/**
	 * Gets the message sent when the mob's level is too low to produce this
	 * item.
	 * 
	 * @return The message sent when the mob's level is too low to produce this
	 *         item.
	 */
	public abstract String getInsufficentLevelMessage();

	/**
	 * Gets the amount of times an item is produced.
	 * 
	 * @return The amount of times an item is produced.
	 */
	public abstract int getProductionCount();

	/**
	 * Gets the required level to produce this item.
	 * 
	 * @return The required level to produce this item.
	 */
	public abstract int getRequiredLevel();

	/**
	 * Gets the rewarded items from production.
	 * 
	 * @return The rewarded items from production.
	 */
	public abstract Item[] getRewards();

	/**
	 * Gets the skill we are using to produce.
	 * 
	 * @return The skill we are using to produce.
	 */
	public abstract int getSkill();

	/**
	 * Gets the sound played whilst producing the item.
	 * 
	 * @return The sound played whilst producing the item.
	 */
	public abstract Sound getSound();

	@Override
	public Action.StackPolicy getStackPolicy() {
		return Action.StackPolicy.NEVER;
	}

	/**
	 * Gets the message sent when the mob successfully produces an item.
	 * 
	 * @return The message sent when the mob successfully produce an item.
	 */
	public abstract String getSuccessfulProductionMessage();

	public boolean isStarted() {
		return started;
	}

	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}

	public void setProductionCount(int productionCount) {
		this.productionCount = productionCount;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
}
