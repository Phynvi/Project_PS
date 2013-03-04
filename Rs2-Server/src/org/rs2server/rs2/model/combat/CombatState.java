package org.rs2server.rs2.model.combat;

import java.util.HashMap;
import java.util.Map;

import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.Mob.InteractionMode;
import org.rs2server.rs2.model.Prayers.Prayer;
import org.rs2server.rs2.model.UpdateFlags.UpdateFlag;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction.Spell;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.rs2.tickable.impl.PoisonDrainTick;
import org.rs2server.rs2.tickable.impl.SpecialEnergyRestoreTick;

/**
 * Holds details related to a specific mob's state in combat.
 * 
 * @author Graham Edgecombe
 * 
 */
public final class CombatState {

	/**
	 * Used for defence calculation, EG: White mace vs Low crush defence.
	 * 
	 * @author Michael Bull
	 * 
	 */
	public static enum AttackType {

		STAB(0),

		SLASH(1),

		CRUSH(2),

		MAGIC(3),

		RANGE(4);
		/**
		 * A map of attack types.
		 */
		private static Map<Integer, AttackType> attackTypes = new HashMap<Integer, AttackType>();

		/**
		 * Populates the attack type map.
		 */
		static {
			for (AttackType attackType : AttackType.values()) {
				attackTypes.put(attackType.id, attackType);
			}
		}

		/**
		 * Gets a attack type by its ID.
		 * 
		 * @param attackType
		 *            The attack type id.
		 * @return The attack type, or <code>null</code> if the id is not a
		 *         attack type.
		 */
		public static AttackType forId(int attackType) {
			return attackTypes.get(attackType);
		}

		/**
		 * The attack type's id.
		 */
		private int id;

		private AttackType(int id) {
			this.id = id;
		}

		/**
		 * Gets the attack types id.
		 * 
		 * @return The attack types id.
		 */
		public int getId() {
			return id;
		}
	}

	public static enum CombatStyle {
		ACCURATE(0, new int[] { Skills.ATTACK, Skills.HITPOINTS },
				new double[] { 4, 1.33 }),

		AGGRESSIVE_1(1, new int[] { Skills.STRENGTH }, new double[] { 4 }),

		AGGRESSIVE_2(2, new int[] { Skills.STRENGTH }, new double[] { 4 }),

		DEFENSIVE(3, new int[] { Skills.DEFENCE }, new double[] { 4, 1.33 }),

		CONTROLLED_1(4, new int[] { Skills.ATTACK, Skills.STRENGTH,
				Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),

		CONTROLLED_2(5, new int[] { Skills.ATTACK, Skills.STRENGTH,
				Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),

		CONTROLLED_3(6, new int[] { Skills.ATTACK, Skills.STRENGTH,
				Skills.DEFENCE }, new double[] { 1.33, 1.33, 1.33 }),

		AUTOCAST(7, new int[] { Skills.MAGIC }, new double[] { 2 }),

		DEFENSIVE_AUTOCAST(8, new int[] { Skills.MAGIC, Skills.DEFENCE },
				new double[] { 1.33, 1 });
		/**
		 * A map of combat styles.
		 */
		private static Map<Integer, CombatStyle> combatStyles = new HashMap<Integer, CombatStyle>();

		/**
		 * Populates the combat style map.
		 */
		static {
			for (CombatStyle combatStyle : CombatStyle.values()) {
				combatStyles.put(combatStyle.id, combatStyle);
			}
		}

		/**
		 * Gets a combat style by its ID.
		 * 
		 * @param combatStyle
		 *            The combat style id.
		 * @return The combat style, or <code>null</code> if the id is not a
		 *         combat style.
		 */
		public static CombatStyle forId(int combatStyle) {
			return combatStyles.get(combatStyle);
		}

		/**
		 * The combat style's id.
		 */
		private int id;

		/**
		 * The skills this combat style adds experience to.
		 */
		private int[] skills;

		/**
		 * The amounts of experience this combat style adds.
		 */
		private double[] experiences;

		private CombatStyle(int id, int[] skills, double[] experiences) {
			this.id = id;
			this.skills = skills;
			this.experiences = experiences;
		}

		/**
		 * Gets an amount of experience this attack type adds by its index.
		 * 
		 * @param index
		 *            The experience index.
		 * @return The amount of experience this attack type adds by its index.
		 */
		public double getExperience(int index) {
			return experiences[index];
		}

		/**
		 * Gets the experience amounts this attack type adds.
		 * 
		 * @return The experience amounts this attack type adds.
		 */
		public double[] getExperiences() {
			return experiences;
		}

		/**
		 * Gets the combat styles id.
		 * 
		 * @return The combat styles id.
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets a skill this attack type adds experience to by its index.
		 * 
		 * @param index
		 *            The skill index.
		 * @return The skill this attack type adds experience to by its index.
		 */
		public int getSkill(int index) {
			return skills[index];
		}

		/**
		 * Gets the skills this attack type adds experience to.
		 * 
		 * @return The skills this attack type adds experience to.
		 */
		public int[] getSkills() {
			return skills;
		}
	}

	/**
	 * The mob whose combat state this is.
	 */
	private Mob mob;

	/**
	 * The damage map of this entity.
	 */
	private DamageMap damageMap = new DamageMap();

	/**
	 * The current attack event.
	 */
	private AttackAction attackEvent;

	/**
	 * The attack delay. (Each value of 1 counts for 600ms, e.g. 3 = 1800ms).
	 */
	private int attackDelay;

	/**
	 * The spell delay. (Each value of 1 counts for 600ms, e.g. 3 = 1800ms).
	 */
	private int spellDelay;

	/**
	 * The mob's combat style.
	 */
	private CombatStyle combatStyle = CombatStyle.ACCURATE;

	/**
	 * The mob's attack type.
	 */
	private AttackType attackType;

	/**
	 * The mob's state of life.
	 */
	private boolean isDead;

	/**
	 * The mob's spell book.
	 */
	private int spellBook = 0;

	/**
	 * The current spell this mob is casting.
	 */
	private Spell currentSpell;

	/**
	 * The spell to be performed once our combat cooldown is over.
	 */
	private Spell queuedSpell;

	/**
	 * The mob's poison damage.
	 */
	private int poisonDamage = 0;

	/**
	 * The mob's last hit timer.
	 */
	private long lastHitTimer;

	/**
	 * The mob who last hit this mob.
	 */
	private Mob lastHitBy;

	/**
	 * The delay before you can equip another weapon, used to stop emotes
	 * overlapping (EG: whip using dds anim).
	 */
	private int weaponSwitchTimer;

	/**
	 * The mob's poisonable flag.
	 */
	private boolean canBePoisoned = true;

	/*
	 * Combat attributes.
	 */

	/**
	 * Ring of Recoil use amount.
	 */
	private int ringOfRecoil = 40;

	/**
	 * The movement flag.
	 */
	private boolean canMove = true;

	/**
	 * The frozen flag.
	 */
	private boolean canBeFrozen = true;

	/**
	 * The teleblock flag.
	 */
	private boolean teleblocked;

	/**
	 * The charged flag.
	 */
	private boolean charged;

	/**
	 * The eating flag.
	 */
	private boolean canEat = true;

	/**
	 * The drinking flag.
	 */
	private boolean canDrink = true;

	/**
	 * The animation flag. This flag stops important emotes overlapping each
	 * other, EG: block emote overlapping attack emote.
	 */
	private boolean canAnimate = true;

	/**
	 * The teleport flag.
	 */
	private boolean canTeleport = true;

	/**
	 * Special attack flag.
	 */
	private boolean special = false;

	/**
	 * Special energy amount.
	 */
	private int specialEnergy = 100;

	/**
	 * The active prayers.
	 */
	private boolean[] prayers = new boolean[26];

	/**
	 * The players prayer head icon.
	 */
	private int prayerHeadIcon = -1;

	/**
	 * The mob's bonuses.
	 */
	private int[] bonuses = new int[13];

	/**
	 * The vengeance flag.
	 */
	private boolean vengeance = false;

	/**
	 * The can vengeance flag.
	 */
	private boolean canVengeance = true;

	/**
	 * The spellbook swap flag.
	 */
	private boolean spellbookSwap = false;

	/**
	 * The can spellbook swap flag.
	 */
	private boolean canSpellbookSwap = true;

	/**
	 * The amount of ticks left before this mobs skull is removed.
	 */
	private int skullTicks;

	/**
	 * Creates the combat state class for the specified mob.
	 * 
	 * @param mob
	 *            The mob.
	 */
	public CombatState(Mob mob) {
		this.mob = mob;
	}

	/**
	 * Calculates the bonuses.
	 */
	public void calculateBonuses() {
		resetBonuses();
		for (Item item : mob.getEquipment().toArray()) {
			if (item != null && item.getEquipmentDefinition() != null) {
				for (int i = 0; i < item.getEquipmentDefinition().getBonuses().length; i++) {
					setBonus(i, getBonus(i)
							+ item.getEquipmentDefinition().getBonus(i));
				}
			}
		}
	}

	/**
	 * Gets the mob's animation flag.
	 * 
	 * @return The mob's animation flag.
	 */
	public boolean canAnimate() {
		return canAnimate;
	}

	/**
	 * @return the canBeFrozen
	 */
	public boolean canBeFrozen() {
		return canBeFrozen;
	}

	/**
	 * @return the canBePoisoned
	 */
	public boolean canBePoisoned() {
		return canBePoisoned;
	}

	/**
	 * Gets the mob's drinking flag.
	 * 
	 * @return The mob's drinking flag.
	 */
	public boolean canDrink() {
		return canDrink;
	}

	/**
	 * Gets the mob's eating flag.
	 * 
	 * @return The mob's eating flag.
	 */
	public boolean canEat() {
		return canEat;
	}

	/**
	 * @return the canMove
	 */
	public boolean canMove() {
		return canMove;
	}

	/**
	 * @return The canSpellbookSwap.
	 */
	public boolean canSpellbookSwap() {
		return canSpellbookSwap;
	}

	/**
	 * @return the canTeleport
	 */
	public boolean canTeleport() {
		return canTeleport;
	}

	/**
	 * @return the canVengeance
	 */
	public boolean canVengeance() {
		return canVengeance;
	}

	/**
	 * Decreases the current attack delay.
	 * 
	 * @param amount
	 *            The amount to decrease by.
	 */
	public void decreaseAttackDelay(int amount) {
		this.attackDelay -= amount;
	}

	/**
	 * @param poisonDamage
	 *            the poisonDamage to set
	 */
	public void decreasePoisonDamage(int poisonDamage) {
		this.poisonDamage -= poisonDamage;
		if (mob.getPoisonDrainTick() != null && this.poisonDamage < 1) {
			mob.getPoisonDrainTick().stop();
			mob.setPoisonDrainTick(null);
		}
	}

	/**
	 * @param skullTicks
	 *            the skullTicks to set
	 */
	public void decreaseSkullTicks(int skullTicks) {
		this.skullTicks -= skullTicks;
		if (skullTicks < 1) {
			mob.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
		}
	}

	/**
	 * Decreases the special energy amount.
	 * 
	 * @param amount
	 *            The amount to decrease by.
	 */
	public void decreaseSpecial(int amount) {
		if (amount > specialEnergy) {
			amount = specialEnergy;
		}
		this.specialEnergy -= amount;
		if (this.specialEnergy < 100 && mob.getSpecialUpdateTick() == null) {
			mob.setSpecialUpdateTick(new SpecialEnergyRestoreTick(mob));
			World.getWorld().submit(mob.getSpecialUpdateTick());
		}
		if (mob.getActionSender() != null) {
			mob.getActionSender().updateSpecialConfig();
		}
	}

	/**
	 * @param spellDelay
	 *            the spellDelay to set
	 */
	public void decreaseSpellDelay(int amount) {
		this.spellDelay -= amount;
	}

	/**
	 * @param weaponSwitchTimer
	 *            the weaponSwitchTimer to set
	 */
	public void decreaseWeaponSwitchTimer(int weaponSwitchTimer) {
		this.weaponSwitchTimer -= weaponSwitchTimer;
	}

	/**
	 * Gets the current attack delay.
	 * 
	 * @return The current attack delay.
	 */
	public int getAttackDelay() {
		return attackDelay;
	}

	/**
	 * Gets the current attack event.
	 * 
	 * @return The current attack event.
	 */
	public AttackAction getAttackEvent() {
		return attackEvent;
	}

	/**
	 * Gets the attack type.
	 * 
	 * @return The attack type.
	 */
	public AttackType getAttackType() {
		return attackType; // TODO
	}

	/**
	 * Gets a bonus by its index.
	 * 
	 * @param index
	 *            The bonus index.
	 * @return The bonus.
	 */
	public int getBonus(int index) {
		return bonuses[index];
	}

	/**
	 * Gets the mob's bonuses.
	 * 
	 * @return The mob's bonuses.
	 */
	public int[] getBonuses() {
		return bonuses;
	}

	/**
	 * Gets the combat style.
	 * 
	 * @return The combat style.
	 */
	public CombatStyle getCombatStyle() {
		return combatStyle; // TODO
	}

	/**
	 * @return the currentSpell
	 */
	public Spell getCurrentSpell() {
		return currentSpell;
	}

	/**
	 * Gets the damage map of this entity.
	 * 
	 * @return The damage map.
	 */
	public DamageMap getDamageMap() {
		return damageMap;
	}

	/**
	 * @return the lastHitBy
	 */
	public Mob getLastHitBy() {
		return lastHitBy;
	}

	/**
	 * @return the lastHitTimer
	 */
	public long getLastHitTimer() {
		return lastHitTimer;
	}

	/**
	 * @return the poisonDamage
	 */
	public int getPoisonDamage() {
		return poisonDamage;
	}

	/**
	 * @param index
	 * @return the prayers
	 */
	public boolean getPrayer(int index) {
		return prayers[index];
	}

	/**
	 * Gets the players prayer head icon.
	 * 
	 * @return The players prayer head icon.
	 */
	public int getPrayerHeadIcon() {
		return prayerHeadIcon;
	}

	/**
	 * @return the prayers
	 */
	public boolean[] getPrayers() {
		return prayers;
	}

	/**
	 * @return the queuedSpell
	 */
	public Spell getQueuedSpell() {
		return queuedSpell;
	}

	/**
	 * @return the ringOfRecoil
	 */
	public int getRingOfRecoil() {
		return ringOfRecoil;
	}

	/**
	 * @return the skullTicks
	 */
	public int getSkullTicks() {
		return skullTicks;
	}

	/**
	 * Gets the special energy amount.
	 * 
	 * @return The special energy amount.
	 */
	public int getSpecialEnergy() {
		return specialEnergy;
	}

	/**
	 * @return the spellBook
	 */
	public int getSpellBook() {
		return spellBook;
	}

	/**
	 * @return the spellDelay
	 */
	public int getSpellDelay() {
		return spellDelay;
	}

	/**
	 * @return the weaponSwitchTimer
	 */
	public int getWeaponSwitchTimer() {
		return weaponSwitchTimer;
	}

	/**
	 * @return the vengeance
	 */
	public boolean hasVengeance() {
		return vengeance;
	}

	/**
	 * Increases the current attack delay.
	 * 
	 * @param amount
	 *            The amount to increase by.
	 */
	public void increaseAttackDelay(int amount) {
		this.attackDelay += amount;
	}

	/**
	 * Increases the special energy amount.
	 * 
	 * @param amount
	 *            The amount to increase by.
	 */
	public void increaseSpecial(int amount) {
		if (amount > (100 - this.specialEnergy)) {
			amount = 100 - this.specialEnergy;
		}
		this.specialEnergy += amount;
		if (mob.getActionSender() != null) {
			mob.getActionSender().updateSpecialConfig();
		}
	}

	/**
	 * Inverses the special attack flag.
	 */
	public void inverseSpecial() {
		this.special = !this.special;
		if (mob.getActionSender() != null) {
			mob.getActionSender().sendConfig(301, isSpecialOn() ? 1 : 0);
		}
	}

	/**
	 * @return the charged
	 */
	public boolean isCharged() {
		return charged;
	}

	/**
	 * Gets the mob's state of life.
	 * 
	 * @return The mob's state of life.
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * Gets the special attack flag.
	 * 
	 * @return The special attack flag.
	 */
	public boolean isSpecialOn() {
		return special;
	}

	/**
	 * @return the isTeleblocked
	 */
	public boolean isTeleblocked() {
		return teleblocked;
	}

	/**
	 * Resets the mob's bonuses.
	 */
	public void resetBonuses() {
		bonuses = new int[13];
	}

	/**
	 * Resets all the players prayers.
	 */
	public void resetPrayers() {
		for (int i = 0; i < prayers.length; i++) {
			prayers[i] = false;
			if (mob.getActionSender() != null) {
				mob.getActionSender().sendConfig(
						Prayer.forId(i).getClientConfiguration(), 0);
			}
		}
		setPrayerHeadIcon(-1);
		if (mob.getPrayerUpdateTick() != null) {
			mob.getPrayerUpdateTick().stop();
			mob.setPrayerUpdateTick(null);
		}
	}

	/**
	 * Sets the current attack delay.
	 * 
	 * @param attackDelay
	 *            The attack delay to set.
	 */
	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}

	/**
	 * Sets the mob's attack type.
	 * 
	 * @param attackType
	 *            The attack type to set.
	 */
	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

	/**
	 * Sets one of the mob's bonuses.
	 * 
	 * @param index
	 *            The bonus index.
	 * @param amount
	 *            The bonus to set.
	 */
	public void setBonus(int index, int amount) {
		bonuses[index] = amount;
	}

	/**
	 * Sets one of the mob's bonuses.
	 * 
	 * @param index
	 *            The bonus index.
	 * @param amount
	 *            The bonus to set.
	 */
	public void setBonuses(int[] bonuses) {
		this.bonuses = bonuses;
	}

	/**
	 * Sets the mob's animation flag.
	 * 
	 * @param canAnimate
	 *            The animation flag to set.
	 */
	public void setCanAnimate(boolean canAnimate) {
		this.canAnimate = canAnimate;
	}

	/**
	 * @param canBeFrozen
	 *            the canBeFrozen to set
	 */
	public void setCanBeFrozen(boolean canBeFrozen) {
		this.canBeFrozen = canBeFrozen;
	}

	/**
	 * @param canBePoisoned
	 *            the canBePoisoned to set
	 */
	public void setCanBePoisoned(boolean canBePoisoned) {
		this.canBePoisoned = canBePoisoned;
	}

	/**
	 * Sets the mob's drinking flag.
	 * 
	 * @param canEat
	 *            The drinking flag to set.
	 */
	public void setCanDrink(boolean canDrink) {
		this.canDrink = canDrink;
	}

	/**
	 * Sets the mob's eating flag.
	 * 
	 * @param canEat
	 *            The eating flag to set.
	 */
	public void setCanEat(boolean canEat) {
		this.canEat = canEat;
	}

	/**
	 * @param canMove
	 *            the canMove to set
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	/**
	 * @param canSpellbookSwap
	 *            The canSpellbookSwap to set.
	 */
	public void setCanSpellbookSwap(boolean canSpellbookSwap) {
		this.canSpellbookSwap = canSpellbookSwap;
	}

	/**
	 * @param canSpellbookSwap
	 *            The canSpellbookSwap to set.
	 */
	public void setCanSpellbookSwap(int ticks) {
		World.getWorld().submit(new Tickable(ticks) {
			@Override
			public void execute() {
				canSpellbookSwap = true;
			}
		});
	}

	/**
	 * @param canTeleport
	 *            the canTeleport to set
	 */
	public void setCanTeleport(boolean canTeleport) {
		this.canTeleport = canTeleport;
	}

	/**
	 * @param canVengeance
	 *            the canVengeance to set
	 */
	public void setCanVengeance(boolean canVengeance) {
		this.canVengeance = canVengeance;
	}

	/**
	 * @param canVengeance
	 *            the canVengeance to set
	 */
	public void setCanVengeance(int ticks) {
		World.getWorld().submit(new Tickable(ticks) {
			@Override
			public void execute() {
				canVengeance = true;
				this.stop();
			}
		});
	}

	/**
	 * @param charged
	 *            the charged to set
	 */
	public void setCharged(boolean charged) {
		this.charged = charged;
	}

	/**
	 * @param charged
	 *            the charged to set
	 */
	public void setCharged(int ticks) {
		this.charged = true;
		World.getWorld().submit(new Tickable(100 * 7) {
			@Override
			public void execute() {
				charged = false;
				if (mob.getActionSender() != null) {
					mob.getActionSender().sendMessage(
							"Your magical charge fades away.");
				}
			}
		});
	}

	/**
	 * Sets the mob's combat style.
	 * 
	 * @param combatStyle
	 *            The combat style to set.
	 */
	public void setCombatStyle(CombatStyle combatStyle) {
		this.combatStyle = combatStyle;
	}

	/**
	 * @param currentSpell
	 *            the currentSpell to set
	 */
	public void setCurrentSpell(Spell currentSpell) {
		this.currentSpell = currentSpell;
	}

	/**
	 * Sets the mob's state of life.
	 * 
	 * @param isDead
	 *            The state of life to set.
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
	 * @param lastHitBy
	 *            the lastHitBy to set
	 */
	public void setLastHitBy(Mob lastHitBy) {
		this.lastHitBy = lastHitBy;
	}

	/**
	 * @param lastHitTimer
	 *            the lastHitTimer to set
	 */
	public void setLastHitTimer(long lastHitTimer) {
		this.lastHitTimer = lastHitTimer + System.currentTimeMillis();
	}

	/**
	 * @param poisonDamage
	 *            the poisonDamage to set
	 */
	public void setPoisonDamage(int poisonDamage, Mob attacker) {
		this.poisonDamage = poisonDamage;
		if (mob.getPoisonDrainTick() == null && poisonDamage > 0) {
			mob.setPoisonDrainTick(new PoisonDrainTick(mob));
			World.getWorld().submit(mob.getPoisonDrainTick());
		} else if (mob.getPoisonDrainTick() != null && poisonDamage < 1) {
			mob.getPoisonDrainTick().stop();
			mob.setPoisonDrainTick(null);
		}
	}

	/**
	 * Sets a prayer by its index.
	 * 
	 * @param index
	 *            The index.
	 * @param prayer
	 *            The flag.
	 */
	public void setPrayer(int index, boolean prayer) {
		this.prayers[index] = prayer;
	}

	/**
	 * Sets the players prayer head icon.
	 * 
	 * @param prayerHeadIcon
	 *            The prayer head icon to set.
	 */
	public void setPrayerHeadIcon(int prayerHeadIcon) {
		this.prayerHeadIcon = prayerHeadIcon;
		mob.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
	}

	/**
	 * @param prayers
	 *            the prayers to set
	 */
	public void setPrayers(boolean[] prayers) {
		this.prayers = prayers;
	}

	/**
	 * @param queuedSpell
	 *            the queuedSpell to set
	 */
	public void setQueuedSpell(Spell queuedSpell) {
		this.queuedSpell = queuedSpell;
	}

	/**
	 * @param ringOfRecoil
	 *            the ringOfRecoil to set
	 */
	public void setRingOfRecoil(int ringOfRecoil) {
		this.ringOfRecoil = ringOfRecoil;
	}

	/**
	 * @param skullTicks
	 *            the skullTicks to set
	 */
	public void setSkullTicks(int skullTicks) {
		this.skullTicks = skullTicks;
		if (skullTicks > 0) {
			mob.getUpdateFlags().flag(UpdateFlag.APPEARANCE);
		}
	}

	/**
	 * Sets the special attack flag.
	 * 
	 * @param special
	 *            The special attack flag to set.
	 */
	public void setSpecial(boolean special) {
		this.special = special;
		if (mob.getActionSender() != null) {
			mob.getActionSender().sendConfig(301, isSpecialOn() ? 1 : 0);
		}
	}

	/**
	 * Sets the special energy amount.
	 * 
	 * @param specialEnergy
	 *            The special energy amount to set.
	 */
	public void setSpecialEnergy(int specialEnergy) {
		/**
		 * Indicate the special energy has decreased and needs refilling.
		 */
		if (specialEnergy < this.specialEnergy && specialEnergy < 100) {
			if (mob.getSpecialUpdateTick() == null) {
				mob.setSpecialUpdateTick(new SpecialEnergyRestoreTick(mob));
				World.getWorld().submit(mob.getSpecialUpdateTick());
			}
		} else if (specialEnergy > 99) {
			if (mob.getSpecialUpdateTick() != null) {
				mob.getSpecialUpdateTick().stop();
				mob.setSpecialUpdateTick(null);
			}
		}
		this.specialEnergy = specialEnergy;
	}

	/**
	 * @param spellBook
	 *            the spellBook to set
	 */
	public void setSpellBook(int spellBook) {
		this.spellBook = spellBook;
	}

	/**
	 * @param spellbookSwap
	 *            The spellbookSwap to set.
	 */
	public void setSpellbookSwap(boolean spellbookSwap) {
		this.spellbookSwap = spellbookSwap;
	}

	/**
	 * @param spellDelay
	 *            the spellDelay to set
	 */
	public void setSpellDelay(int spellDelay) {
		this.spellDelay = spellDelay;
	}

	/**
	 * @param teleblocked
	 *            the teleblocked to set
	 */
	public void setTeleblocked(boolean teleblocked) {
		this.teleblocked = teleblocked;
	}

	/**
	 * @param vengeance
	 *            the vengeance to set
	 */
	public void setVengeance(boolean vengeance) {
		this.vengeance = vengeance;
	}

	/**
	 * @param weaponSwitchTimer
	 *            the weaponSwitchTimer to set
	 */
	public void setWeaponSwitchTimer(int weaponSwitchTimer) {
		this.weaponSwitchTimer = weaponSwitchTimer;
	}

	/**
	 * @return The spellbookSwap.
	 */
	public boolean spellbookSwap() {
		return spellbookSwap;
	}

	/**
	 * Begins an attack on the specified victim.
	 * 
	 * @param victim
	 *            The victim.
	 * @param retaliating
	 *            A boolean flag indicating if the attack is a retaliation.
	 */
	public void startAttacking(Mob victim, boolean retaliating) {
		mob.setInteractingEntity(InteractionMode.ATTACK, victim);
		// prevents running to the mob if you are already in distance
		if (mob.getLocation().isWithinDistance(victim.getLocation(),
				mob.getActiveCombatAction().distance(mob, victim))) {
			mob.getWalkingQueue().reset();
		}
		if (attackEvent == null || !attackEvent.isRunning()) {
			attackEvent = new AttackAction(mob, retaliating);
			if (mob.getActionSender() != null) {
				mob.getActionSender().sendFollowing(victim, 1);
			}
			mob.getActionQueue().clearRemovableActions(); // cancels all actions
			mob.getActionQueue().addAction(attackEvent);
		} // else the attack event is reused to preserve cooldown period
	}

}
