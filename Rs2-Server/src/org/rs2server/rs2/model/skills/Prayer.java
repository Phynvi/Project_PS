/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.skills;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.Sound;

/**
 * 
 * @author Stephen Soltys
 */
public class Prayer extends Action {

	public enum Bone {

		BONES(new Item(526), 5), WOLF_BONES(new Item(2859), 5), BIG_BONES(
				new Item(532), 15), BABY_DRAGON_BONES(new Item(534), 30), DRAGON_BONES(
				new Item(536), 72);
		Item bone;
		double exp;

		Bone(Item bone, double exp) {
			this.bone = bone;
			this.exp = exp;
		}

		Item getBone() {
			return bone;
		}

		double getExp() {
			return exp;
		}
	}

	public static Bone getBoneForId(int id) {
		switch (id) {
		case 526:
			return Bone.BONES;
		case 2859:
			return Bone.WOLF_BONES;
		case 532:
			return Bone.BIG_BONES;
		case 534:
			return Bone.BABY_DRAGON_BONES;
		case 536:
			return Bone.DRAGON_BONES;
		}
		return null;
	}

	private Bone bone;

	private int slot;

	/**
	 * This starts the actions animation and requirement checks, but prevents
	 * the production from immediately executing.
	 */
	private boolean started = false;

	/**
	 * The cycle count.
	 */
	private int cycleCount = 2;

	public Prayer(Mob mob, Bone bone, int slot) {
		super(mob, 1);
		this.bone = bone;
		this.slot = slot;
	}

	@Override
	public void execute() {
		if (getMob().getInventory().getCount(bone.getBone().getId()) < bone
				.getBone().getCount()) {
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (!started) {
			started = true;
			getMob().playAnimation(Animation.create(827));
			getMob().playSound(Sound.create(2738, (byte) 1, 0));
			cycleCount = 2;
			return;
		}

		if (cycleCount > 1) {
			cycleCount--;
			return;
		}

		if (getMob().getInventory().get(slot) != null
				&& getMob().getInventory().get(slot).getId() == bone.getBone()
						.getId()) {
			getMob().getInventory().remove(bone.getBone(), slot);
		} else {
			this.stop();
			return;
		}
		getMob().getActionSender().sendMessage("You bury the bones.");
		getMob().getSkills().addExperience(Skills.PRAYER,
				bone.getExp() * Constants.getExpModifier());

		if (getMob().getInventory().getCount(bone.getBone().getId()) < bone
				.getBone().getCount()) {
			getMob().playAnimation(Animation.create(-1));
			this.stop();
		}
	}

	@Override
	public Action.AnimationPolicy getAnimationPolicy() {
		return Action.AnimationPolicy.RESET_ALL;
	}

	@Override
	public Action.CancelPolicy getCancelPolicy() {
		return Action.CancelPolicy.ALWAYS;
	}

	@Override
	public Action.StackPolicy getStackPolicy() {
		return Action.StackPolicy.NEVER;
	}

	public boolean isStarted() {
		return started;
	}

	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
}
