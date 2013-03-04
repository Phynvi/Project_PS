package org.rs2server.rs2.model.combat.npcs;

import java.util.Random;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.CombatState;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.model.container.Equipment;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

/**
 * Banshee combat instance
 * 
 * @author Shiver
 * 
 */
public class AberrantSpectre extends AbstractCombatAction {

	private enum CombatStyle {

		MAGIC
	}

	/**
	 * The random number generator.
	 */
	private final Random random = new Random();

	private final int[] REDUCE_SKILLS = new int[] { Skills.RANGE,
			Skills.ATTACK, Skills.DEFENCE, Skills.MAGIC, Skills.PRAYER,
			Skills.STRENGTH };

	/**
	 * Default private constructor.
	 */
	public AberrantSpectre() {
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
		return 7;
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
		int hitDelay = 1;
		boolean blockAnimation;
		int hit;

		Animation anim = attacker.getAttackAnimation();
		attacker.playAnimation(anim);
		boolean noseBlocked = true;
		if (p.getEquipment().get(Equipment.SLOT_HELM) == null
				|| p.getEquipment().get(Equipment.SLOT_HELM).getId() != 4168) {
			noseBlocked = false;
			victim.playGraphics(Graphic.create(184, 0, 100));
		}

		hitDelay = 2;
		blockAnimation = false;
		maxHit = 12;
		damage = damage(maxHit, attacker, victim, CombatState.AttackType.MAGIC,
				Skills.MAGIC, Prayers.PROTECT_FROM_MAGIC, false, true);
		randomHit = random.nextInt(damage < 1 ? 1 : damage + 1);
		if (randomHit > victim.getSkills().getLevel(Skills.HITPOINTS)) {
			randomHit = victim.getSkills().getLevel(Skills.HITPOINTS);
		}
		hit = randomHit;
		if (!noseBlocked) {
			hit = 12;
			p.getActionSender().sendMessage(
					"The Aberrant spectre's smell causes you damage!");
			int rand = Misc.random(REDUCE_SKILLS.length - 1);
			int reduce = Misc.random(25);
			if (reduce >= p.getSkills().getLevel(REDUCE_SKILLS[rand])) {
				reduce = p.getSkills().getLevel(REDUCE_SKILLS[rand]) - 1;
			}
			p.getSkills().decreaseLevel(REDUCE_SKILLS[rand], reduce);
		}
		attacker.getCombatState().setAttackDelay(5);
		attacker.getCombatState().setSpellDelay(5);

		final int h = hit;
		World.getWorld().submit(new Tickable(hitDelay) {

			@Override
			public void execute() {
				victim.inflictDamage(new Hit(h), attacker);
				smite(attacker, victim, h);
				recoil(attacker, victim, h);
				this.stop();
			}
		});
		vengeance(attacker, victim, hit, 1);

		victim.getActiveCombatAction().defend(attacker, victim, blockAnimation);
	}
}
