package org.rs2server.rs2.model.combat.npcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.combat.CombatAction;
import org.rs2server.rs2.model.combat.CombatFormulae;
import org.rs2server.rs2.model.combat.impl.AbstractCombatAction;
import org.rs2server.rs2.model.container.Equipment;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

/**
 * 
 * @author Huey
 * 
 */
public class KingBlackDragon extends AbstractCombatAction {

	/**
	 * The singleton instance.
	 */
	private static final KingBlackDragon INSTANCE = new KingBlackDragon();

	/**
	 * Gets the singleton instance.
	 * 
	 * @return The singleton instance.
	 */
	public static CombatAction getAction() {
		return INSTANCE;
	}

	private final int[] REDUCE_SKILLS = new int[] { Skills.RANGE,
			Skills.ATTACK, Skills.DEFENCE, Skills.MAGIC, Skills.PRAYER,
			Skills.STRENGTH };

	/**
	 * The random number generator.
	 */
	private final Random random = new Random();

	@Override
	public void defend(Mob attacker, Mob victim, boolean blockAnimation) {
		if (victim.getInteractingEntity() == null
				|| victim.getAttribute("isAttacking") == null) {
			if (!attacker.equals(victim.getInteractingEntity())) {
				victim.getCombatState().startAttacking(attacker, true);
				victim.setAttribute("isAttacking", true);
			}
		}

		if (blockAnimation && victim.getCombatState().canAnimate()) {
			Animation defend = Animation.create(404);
			Item shield = victim.getEquipment().get(Equipment.SLOT_SHIELD);
			Item weapon = victim.getEquipment().get(Equipment.SLOT_WEAPON);
			String shieldName = shield != null ? shield.getDefinition()
					.getName() : "";
			if (shieldName.contains("shield") || shieldName.contains("ket-xil")
					|| shieldName.contains("defender")) {
				defend = shield.getEquipmentDefinition().getAnimation()
						.getDefend();
			} else if (weapon != null) {
				defend = weapon.getEquipmentDefinition().getAnimation()
						.getDefend();
			} else if (shield != null) {
				defend = shield.getEquipmentDefinition().getAnimation()
						.getDefend();
			} else {
				defend = victim.getDefendAnimation();
			}
			victim.playAnimation(defend);
		}
	}

	@Override
	public int distance(Mob victim, Mob attacker) {
		return 4;
	}

	private List<Player> getPlayersAttacking(Mob mob) {
		List<Player> players = new ArrayList<Player>();
		for (Player p : mob.getRegion().getPlayers()) {
			if (p.getInteractingEntity() != null
					&& p.getInteractingEntity().equals(mob)) {
				players.add(p);
			}
		}
		for (Region r : mob.getRegion().getSurroundingRegions()) {
			for (Player p : r.getPlayers()) {
				if (!players.contains(p)) {
					if (p.getInteractingEntity() != null
							&& p.getInteractingEntity().equals(mob)) {
						players.add(p);
					}
				}
			}
		}
		return players;
	}

	@Override
	public void hit(final Mob attacker, final Mob victim) {
		super.hit(attacker, victim);

		if (!attacker.isNPC()) {
			return; // this should be an NPC!
		}

		NPC npc = (NPC) attacker;
		npc.setAttribute("isAttacking", null);
		int maxHit;
		int damage;
		int randomHit;
		int hitDelay;
		boolean blockAnimation;
		final int hit;
		int r = Misc.random(2);

		if (r < 1 && TileControl.calculateDistance(attacker, victim) != 1) {
			r++;
		}
		switch (r) {
		default:
		case 0:
			Animation anim = attacker.getAttackAnimation();
			anim = Animation.create(91);

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
		case 1:
			if (Misc.getDistance(attacker.getLocation(), victim.getLocation()) < 1) {
				return;
			}
			attacker.playAnimation(Animation.create(82));
			attacker.playGraphics(Graphic.create(1, 0, 100));
			if (victim instanceof Player) {
			}
			hitDelay = 2;
			blockAnimation = false;
			maxHit = 65;
			if (victim.getEquipment().contains(1540)
					|| victim.getEquipment().contains(11283)
					|| victim.getEquipment().contains(11284)) {
				maxHit = 23;
				victim.getActionSender()
						.sendMessage(
								"Your shield absorbs most of the dragon's firey breath.");
			} else {
				if (Misc.random(3) == 3) {
					maxHit = 35;
					victim.getActionSender().sendMessage(
							"You manage to resist some of the dragon's fire.");
				} else {
					maxHit = 65;
				}
			}
			randomHit = Misc.random(maxHit);
			if (randomHit >= 30) {
				victim.getActionSender().sendMessage(
						"The dragon's fire burns you heavily!");
			}
			hit = randomHit;
			break;
		case 2:
			int clientSpeed;
			int gfxDelay;
			if (attacker.getLocation().isWithinDistance(attacker, victim, 1)) {
				clientSpeed = 70;
				gfxDelay = 80;
			} else if (attacker.getLocation().isWithinDistance(attacker,
					victim, 5)) {
				clientSpeed = 90;
				gfxDelay = 100;
			} else if (attacker.getLocation().isWithinDistance(attacker,
					victim, 8)) {
				clientSpeed = 110;
				gfxDelay = 120;
			} else {
				clientSpeed = 130;
				gfxDelay = 140;
			}
			hitDelay = (gfxDelay / 20) - 1;

			attacker.playAnimation(Animation.create(81));
			int type = Misc.random(393, 396);
			switch (type) {
			case 394:
				victim.getCombatState().setPoisonDamage(8, attacker);
				if (victim.getActionSender() != null) {
					victim.getActionSender().sendMessage(
							"You have been poisoned!");
				}
				break;
			case 395:
				int freezeTimer = 33;
				if (victim.getCombatState().canMove()
						&& victim.getCombatState().canBeFrozen()) {
					/*
					 * If the enemy has protect from magic, freeze time is
					 * halved.
					 */
					if (victim.getCombatState().getPrayer(
							Prayers.PROTECT_FROM_MAGIC)) {
						freezeTimer = freezeTimer / 2;
					}
					victim.getCombatState().setCanMove(false);
					victim.getCombatState().setCanBeFrozen(false);
					victim.getWalkingQueue().reset();
					if (victim.getActionSender() != null) {
						victim.getActionSender().sendMessage(
								"You have been frozen!");
					}
					World.getWorld().submit(new Tickable(freezeTimer) {

						@Override
						public void execute() {
							victim.getCombatState().setCanMove(true);
							this.stop();
						}
					});
					World.getWorld().submit(new Tickable(freezeTimer + 5) {

						@Override
						public void execute() {
							victim.getCombatState().setCanBeFrozen(true);
							this.stop();
						}
					});
					break;
				}
			case 396:
				int rand = Misc.random(REDUCE_SKILLS.length - 1);
				int reduce = Misc.random(15);
				if (reduce >= victim.getSkills().getLevel(REDUCE_SKILLS[rand])) {
					reduce = victim.getSkills().getLevel(REDUCE_SKILLS[rand]) - 1;
				}
				victim.getSkills().decreaseLevel(REDUCE_SKILLS[rand], reduce);
				break;
			}
			attacker.playProjectile(Projectile.create(
					attacker.getCentreLocation(), victim.getCentreLocation(),
					type, 45, 50, clientSpeed, 43, 35,
					victim.getProjectileLockonIndex(), 10, 48));
			victim.playSound(Sound.create(3750, (byte) 1, 100));
			blockAnimation = false;
			if (victim.getEquipment().contains(1540)
					|| victim.getEquipment().contains(11283)
					|| victim.getEquipment().contains(11284)) {
				maxHit = 23;
				victim.getActionSender().sendMessage(
						"Your shield absorbs most of the dragon's breath.");
			} else {
				if (Misc.random(3) == 3) {
					maxHit = 35;
					victim.getActionSender().sendMessage(
							"You manage to resist some of the dragon's fire.");
				} else {
					maxHit = 65;
				}
			}

			if (victim.getCombatState().getPrayer(Prayers.PROTECT_FROM_MAGIC)) {
				maxHit = 28;
			} else {
				// maxHit = 65;
			}
			randomHit = Misc.random(maxHit);
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