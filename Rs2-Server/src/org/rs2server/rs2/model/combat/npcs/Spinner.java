package org.rs2server.rs2.model.combat.npcs;

import java.util.Random;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.CombatAction;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.tickable.Tickable;

public class Spinner extends AbstractCombatAction {

	/**
	 * The singleton instance.
	 */
	private static final Spinner INSTANCE = new Spinner();

	/**
	 * Gets the singleton instance.
	 * 
	 * @return The singleton instance.
	 */
	public static CombatAction getAction() {
		return INSTANCE;
	}

	private final Random random = new Random();

	/**
	 * Default private constructor.
	 */
	public Spinner() {
	}

	@Override
	public int distance(Mob attacker, Mob victim) {
		return 1;
	}

	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);

		if (!attacker.isNPC()) {
			return; // this should be an NPC!
		}

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
		damage = damage(maxHit, attacker, victim, attacker.getCombatState()
				.getAttackType(), Skills.ATTACK, Prayers.PROTECT_FROM_MELEE,
				false, false);
		randomHit = random.nextInt(damage < 1 ? 1 : damage + 1);
		if (randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
			randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
		}
		hit = randomHit;

		attacker.getCombatState().setAttackDelay(5);
		attacker.getCombatState().setSpellDelay(5);

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