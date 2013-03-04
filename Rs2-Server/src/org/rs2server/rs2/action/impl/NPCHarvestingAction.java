package org.rs2server.rs2.action.impl;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.Skills;

/**
 * <p>
 * A harvesting action is a resource-gathering action, which includes, but is
 * not limited to, woodcutting and mining.
 * </p>
 * 
 * <p>
 * This class implements code related to all harvesting-type skills, such as
 * dealing with the action itself, looping, expiring the object (i.e. changing
 * rocks to the gray rock and trees to the stump), checking requirements and
 * giving out the harvested resources.
 * </p>
 * 
 * <p>
 * The individual woodcutting and mining classes implement things specific to
 * these individual skills such as random events.
 * </p>
 * 
 * @author Michael Bull
 * 
 */
public abstract class NPCHarvestingAction extends Action {

	/**
	 * This starts the actions animation and requirement checks, but prevents
	 * the harvest from immediately executing.
	 */
	private boolean started = false;

	/**
	 * The current cycle time.
	 */
	private int currentCycles = 0;

	/**
	 * The amount of cycles before an animation.
	 */
	private int lastAnimation = 0;

	/**
	 * Creates the harvesting action for the specified mob.
	 * 
	 * @param mob
	 *            The mob to create the action for.
	 */
	public NPCHarvestingAction(Mob mob) {
		super(mob, 0);
	}

	/**
	 * Performs extra checks that a specific harvest event independently uses,
	 * e.g. checking for a pickaxe in mining.
	 */
	public abstract boolean canHarvest();

	@Override
	public void execute() {
		if (!getMob().getInventory().hasRoomFor(getReward())) {
			getMob().getActionSender().sendString(210, 0,
					getInventoryFullMessage());
			getMob().getActionSender().sendChatboxInterface(210);
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (getMob().getSkills().getLevelForExperience(getSkill()) < getRequiredLevel()) {
			getMob().getActionSender().sendString(210, 0,
					getLevelTooLowMessage());
			getMob().getActionSender().sendChatboxInterface(210);
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (!canHarvest()) {
			this.stop();
			return;
		}
		if (!started) {
			started = true;
			getMob().playAnimation(getAnimation());
			getMob().getActionSender().sendMessage(getHarvestStartedMessage());
			currentCycles = getCycleCount();
			return;
		}
		if (getNPC().getSkills().getLevel(Skills.HITPOINTS) < 1) {
			this.stop();
			return;
		}

		if (lastAnimation > 3) {
			getMob().playAnimation(getAnimation()); // keeps the emote playing
			lastAnimation = 0;
		}
		lastAnimation++;

		if (currentCycles > 0) {
			currentCycles--;
			return;
		}

		// execute
		currentCycles = getCycleCount();
		getMob().getActionSender().sendMessage(getSuccessfulHarvestMessage());
		getMob().getInventory().add(getReward());
		getMob().getSkills().addExperience(getSkill(),
				getExperience() * Constants.getExpModifier());

		if (!getMob().getInventory().hasRoomFor(getReward())) {
			getMob().getActionSender().sendString(210, 0,
					getInventoryFullMessage());
			getMob().getActionSender().sendChatboxInterface(210);
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
	}

	/**
	 * Gets the animation played whilst harvesting the object.
	 * 
	 * @return The animation played whilst harvesting the object.
	 */
	public abstract Animation getAnimation();

	@Override
	public AnimationPolicy getAnimationPolicy() {
		return AnimationPolicy.RESET_ALL;
	}

	@Override
	public CancelPolicy getCancelPolicy() {
		return CancelPolicy.ALWAYS;
	}

	/**
	 * Gets the amount of cycles before the object is interacted with.
	 * 
	 * @return The amount of cycles before the object is interacted with.
	 */
	public abstract int getCycleCount();

	/**
	 * Gets the experience granted for each item that is successfully harvested.
	 * 
	 * @return The experience granted for each item that is successfully
	 *         harvested.
	 */
	public abstract double getExperience();

	/**
	 * Gets the message sent when the harvest successfully begins.
	 * 
	 * @return The message sent when the harvest successfully begins.
	 */
	public abstract String getHarvestStartedMessage();

	/**
	 * Gets the message sent when the mob has a full inventory.
	 * 
	 * @return The message sent when the mob has a full inventory.
	 */
	public abstract String getInventoryFullMessage();

	/**
	 * Gets the message sent when the mob's level is too low to harvest this
	 * object.
	 * 
	 * @return The message sent when the mob's level is too low to harvest this
	 *         object.
	 */
	public abstract String getLevelTooLowMessage();

	/**
	 * Gets the game object we are harvesting.
	 * 
	 * @return The game object we are harvesting.
	 */
	public abstract NPC getNPC();

	/**
	 * Gets the required level to harvest this object.
	 * 
	 * @return The required level to harvest this object.
	 */
	public abstract int getRequiredLevel();

	/**
	 * Gets the reward from harvesting the object.
	 * 
	 * @return The reward from harvesting the object.
	 */
	public abstract Item getReward();

	/**
	 * Gets the skill we are using to harvest.
	 * 
	 * @return The skill we are using to harvest.
	 */
	public abstract int getSkill();

	@Override
	public StackPolicy getStackPolicy() {
		return StackPolicy.NEVER;
	}

	/**
	 * Gets the message sent when the mob successfully harvests from the object.
	 * 
	 * @return The message sent when the mob successfully harvests from the
	 *         object.
	 */
	public abstract String getSuccessfulHarvestMessage();
}
