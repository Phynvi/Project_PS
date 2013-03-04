package org.rs2server.rs2.model.combat.npcs;

import java.util.Random;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.model.container.Equipment;
import org.rs2server.rs2.tickable.Tickable;

/**
 * Banshee combat instance
 * 
 * @author Shiver
 * 
 */
public class Banshee extends AbstractCombatAction {

	private enum CombatStyle {

		MELEE
	}

	/**
	 * The random number generator.
	 */
	private final Random random = new Random();

	/**
	 * Default private constructor.
	 */
	public Banshee() {
	}

	@Override
	public boolean canHit(Mob attacker, Mob victim, boolean messages,
			boolean cannon) {
		if (!super.canHit(attacker, victim, messages, cannon)) {
			return false;
		}
		/*
		 * if (attacker.isPlayer()) { Player c = (Player) attacker; if
		 * (c.getSkills().getLevel(18) < 15) {
		 * 
		 * } }
		 */
		return true;
	}

	@Override
	public int distance(Mob victim, Mob attacker) {
		return 5;
	}

	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);

		if (!victim.isPlayer()) {
			return;
		}

		Player p = (Player) victim;
		NPC npc = (NPC) attacker;

		int maxHit;
		int damage;
		int randomHit;
		int hitDelay;
		boolean blockAnimation;
		final int hit;

		Animation anim = attacker.getAttackAnimation();
		attacker.playAnimation(anim);

		hitDelay = 1;
		blockAnimation = true;
		maxHit = npc.getCombatDefinition().getMaxHit();
		boolean earsBlocked = true;
		if (p.getEquipment().get(Equipment.SLOT_HELM) == null
				|| p.getEquipment().get(Equipment.SLOT_HELM).getId() != 4166) {
			earsBlocked = false;
		}
		if (!earsBlocked) {
			maxHit = p.getSkills().getLevelForExperience(Skills.HITPOINTS) / 10;
			p.getActionSender().sendMessage(
					"The Banshee's screech hurts your ears!");
		}
		damage = damage(maxHit, attacker, victim, attacker.getCombatState()
				.getAttackType(), Skills.ATTACK, Prayers.PROTECT_FROM_MELEE,
				false, false);
		randomHit = random.nextInt(damage < 1 ? 1 : damage + 1);
		if (!earsBlocked && randomHit < damage / 2) {
			randomHit = damage / 2;
		}
		if (randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
			randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
		}
		hit = randomHit;
		attacker.getCombatState().setAttackDelay(5);

		if (!earsBlocked) {
			attacker.getCombatState().setAttackDelay(4);
		}

		World.getWorld().submit(new Tickable(hitDelay) {

			@Override
			public void execute() {
				victim.inflictDamage(new Hit(hit), attacker);
				smite(attacker, victim, hit);
				recoil(attacker, victim, hit);
				this.stop();
			}
		});
		vengeance(attacker, victim, hit, 1);

		victim.getActiveCombatAction().defend(attacker, victim, blockAnimation);
	}
}
