package org.rs2server.rs2.model.combat.npcs;

import java.util.Random;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.CombatAction;
import org.rs2server.rs2.model.combat.CombatFormulae;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.tickable.Tickable;

/**
 * Blue Dragon combat instance
 * 
 * @author Michael Bull
 * 
 */
public class BlueDragon extends AbstractCombatAction {

	private enum CombatStyle {

		MELEE, MAGIC
	}

	/**
	 * The singleton instance.
	 */
	private static final BlueDragon INSTANCE = new BlueDragon();

	/**
	 * Gets the singleton instance.
	 * 
	 * @return The singleton instance.
	 */
	public static CombatAction getAction() {
		return INSTANCE;
	}

	/**
	 * The random number generator.
	 */
	private final Random random = new Random();

	/**
	 * Default private constructor.
	 */
	public BlueDragon() {
	}

	@Override
	public int distance(Mob victim, Mob attacker) {
		return 5;
	}

	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);
		if (!attacker.isNPC()) {
			return; // this should be an NPC!
		}

		NPC npc = (NPC) attacker;

		CombatStyle style = CombatStyle.MAGIC;

		int maxHit;
		int damage;
		int randomHit;
		int hitDelay;
		boolean blockAnimation;
		final int hit;

		if (attacker.getLocation().isWithinDistance(attacker, victim, 1)) {
			switch (random.nextInt(2)) {
			case 0:
				style = CombatStyle.MELEE;
				break;
			case 1:
				style = CombatStyle.MAGIC;
				break;
			}
		}

		switch (style) {
		case MELEE:
			Animation anim = attacker.getAttackAnimation();
			if (random.nextInt(2) == 1) {
				anim = Animation.create(91);
			}
			attacker.playAnimation(anim);

			hitDelay = 1;
			blockAnimation = true;
			maxHit = npc.getCombatDefinition().getMaxHit();
			damage = damage(maxHit, attacker, victim, attacker.getCombatState()
					.getAttackType(), Skills.ATTACK,
					Prayers.PROTECT_FROM_MELEE, false, false);
			randomHit = random.nextInt(damage < 1 ? 1 : damage + 1);
			if (randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
				randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
			}
			hit = randomHit;
			break;
		default:
		case MAGIC:
			attacker.playAnimation(Animation.create(81));
			attacker.playGraphics(Graphic.create(1, 0, 100));

			hitDelay = 2;
			blockAnimation = false;
			maxHit = 50;
			randomHit = random.nextInt(maxHit);
			if (randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
				randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
			}
			double dragonfireReduction = CombatFormulae
					.dragonfireReduction(victim);
			if (dragonfireReduction > 0) {
				randomHit -= (randomHit * dragonfireReduction);
				if (randomHit < 0) {
					randomHit = 0;
				}
			}
			hit = randomHit;
			break;
		}

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
