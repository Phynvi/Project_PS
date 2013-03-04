package org.rs2server.rs2.model.combat.npcs;

import java.util.Random;
import org.rs2server.rs2.clipping.RegionClipping;

import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.UpdateFlags.UpdateFlag;
import org.rs2server.rs2.model.combat.CombatAction;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

/**
 * Monk of Zamorak combat instance
 * 
 * @author Michael Bull
 * 
 */
public class AbyssalDemon extends AbstractCombatAction {

	private enum CombatStyle {

		MELEE, MAGIC
	}

	/**
	 * The singleton instance.
	 */
	private static final AbyssalDemon INSTANCE = new AbyssalDemon();

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
	public AbyssalDemon() {
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

		final NPC npc = (NPC) attacker;
		final Player player = (Player) victim;

		CombatStyle style = CombatStyle.MAGIC;

		int maxHit;
		int damage;
		int randomHit;
		final int hitDelay;
		final boolean blockAnimation;
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
			int pX = player.getLocation().getX();
			int pY = player.getLocation().getY();
			int pZ = player.getLocation().getZ();
			final Location locations[] = new Location[] {
					Location.create(pX, pY + 1, pZ),
					Location.create(pX, pY - 1, pZ),
					Location.create(pX + 1, pY, pZ),
					Location.create(pX - 1, pY, pZ) };
			boolean blocked[] = new boolean[] {
					RegionClipping.getRegionClipping().blockedNorth(
							player.getLocation()),
					RegionClipping.getRegionClipping().blockedSouth(
							player.getLocation()),
					RegionClipping.getRegionClipping().blockedEast(
							player.getLocation()),
					RegionClipping.getRegionClipping().blockedWest(
							player.getLocation()) };
			anim = attacker.getAttackAnimation();
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
			boolean brk = true;
			for (int i = 0; i < blocked.length; i++) {
				if (!blocked[i] && !locations[i].equals(npc.getLocation())) {
					brk = false;
				}
			}
			if (brk) {
				break;
			}
			while (true) {
				final int rand = Misc.random(locations.length - 1);
				if (blocked[rand] || locations[rand].equals(npc.getLocation())) {
					continue;
				}
				npc.setTeleportTarget(Location.create(locations[rand].getX(),
						locations[rand].getY(), 4));
				World.getWorld().submit(new Tickable(1) {

					@Override
					public void execute() {
						npc.setTeleportTarget(locations[rand]);
						npc.face(player.getLocation());
						npc.getUpdateFlags().flag(UpdateFlag.FACE_COORDINATE);
						npc.playGraphics(Graphic.create(409, 0));
						attacker.getCombatState().setAttackDelay(4);
						attacker.getCombatState().setSpellDelay(4);

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

						victim.getActiveCombatAction().defend(attacker, victim,
								blockAnimation);
						this.stop();
					}
				});
				return;
			}
		}

		attacker.getCombatState().setAttackDelay(4);
		attacker.getCombatState().setSpellDelay(4);

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
