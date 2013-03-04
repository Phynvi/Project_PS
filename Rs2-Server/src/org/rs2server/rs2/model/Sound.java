package org.rs2server.rs2.model;

import org.rs2server.rs2.model.container.Equipment;

/**
 * Represents an in-game sound.
 * 
 * @author Josh Mai
 * 
 */
public class Sound {

	public static Sound create(int id, byte volume, int delay) {
		return new Sound(id, volume, delay);
	}

	/**
	 * 
	 * play attack sound if the mob/entity is a player. null pointer caught if
	 * sound is attempted to be played for NPC mob, as NPC does not have a
	 * client to write the packet to.
	 * 
	 * @param attacker
	 *            the player to play the sound for.
	 * @return whether or not the sound is being played.
	 */
	public static boolean playAttackSound(Mob attacker, Mob target) {
		if (attacker.isPlayer()) {
			if (attacker.getEquipment().isSlotUsed(Equipment.SLOT_WEAPON)) {
				attacker.getActionSender().playSound(
						create(getRegularAttackSound(attacker.getEquipment()
								.get(Equipment.SLOT_WEAPON).getId()), (byte) 1,
								0));
			} else {
				attacker.getActionSender().playSound(
						create(getRegularAttackSound(-1), (byte) 1, 0));
			}
			if (target.isPlayer()) {
				if (attacker.getEquipment().isSlotUsed(Equipment.SLOT_WEAPON)) {
					target.getActionSender().playSound(
							create(getRegularAttackSound(attacker
									.getEquipment().get(Equipment.SLOT_WEAPON)
									.getId()), (byte) 1, 0));
				} else {
					target.getActionSender().playSound(
							create(getRegularAttackSound(-1), (byte) 1, 0));
				}
			}
			return true;
		}

		if (attacker.isNPC() && target.isPlayer()) {
			NPC npc = (NPC) attacker;
			target.getActionSender().playSound(
					create(getNpcAttackSound(npc.getDefinition().getId()),
							(byte) 1, 0));
			return true;
		}
		return false;
	}

	/**
	 * Plays the sound for a special attack
	 * 
	 * @param attacker
	 *            the attacker of the victim.
	 * @param victim
	 *            the victim of the attacker.
	 * @return whether or not the sound is being played.
	 */
	public static boolean playSpecialAttackSound(Mob attacker, Mob victim) {
		if (attacker.isPlayer()) {
			attacker.getActionSender().playSound(
					create(getSpecialAttackSound(attacker.getEquipment()
							.get(Equipment.SLOT_WEAPON).getId()),
							(byte) 1,
							getSpecialSoundDelay(attacker.getEquipment()
									.get(Equipment.SLOT_WEAPON).getId())));
			if (victim.isPlayer()) {
				victim.getActionSender().playSound(
						create(getSpecialAttackSound(attacker.getEquipment()
								.get(Equipment.SLOT_WEAPON).getId()), (byte) 1,
								getSpecialSoundDelay(attacker.getEquipment()
										.get(Equipment.SLOT_WEAPON).getId())));
			}
			return true;
		}
		return false;
	}

	/**
	 * Plays the sound for a spell attack
	 * 
	 * @param attacker
	 *            the attacker of the victim.
	 * @param victim
	 *            the victim of the attacker.
	 * @return whether or not the sound is being played.
	 */
	public static boolean playSpellAttackSound(Mob attacker, Mob victim,
			int spellId) {
		if (attacker.isPlayer()) {
			Player p1 = (Player) attacker;
			if (p1.getName().equalsIgnoreCase("sneaky") && p1.isDebugMode()) {
				p1.getActionSender().sendMessage(
						"Spellbook : "
								+ attacker.getCombatState().getSpellBook());
				System.out.println("Spellbook : "
						+ attacker.getCombatState().getSpellBook());
			}
			p1.getActionSender().playSound(
					create(getSpellAttackSound(attacker.getCombatState()
							.getSpellBook(), spellId), (byte) 1,
							getSpellSoundDelay(spellId)));
			if (victim.isPlayer()) {
				victim.getActionSender().playSound(
						create(getSpellAttackSound(attacker.getCombatState()
								.getSpellBook(), spellId), (byte) 1,
								getSpellSoundDelay(spellId)));
			}
			return true;
		}
		return false;
	}

	/**
	 * Plays the sound for a spell splash
	 * 
	 * @param attacker
	 *            the attacker of the victim.
	 * @param victim
	 *            the victim of the attacker.
	 * @return whether or not the sound is being played.
	 */
	public static boolean playSpellSplashSound(Mob attacker, Mob victim,
			int spellId) {
		if (attacker.isPlayer()) {
			attacker.getActionSender().playSound(
					create(getSplashSound(spellId), (byte) 1, 0));
			if (victim.isPlayer()) {
				victim.getActionSender().playSound(
						create(getSplashSound(spellId), (byte) 1, 0));
			}
			return true;
		}
		return false;
	}

	/**
	 * Plays a sound when the player changes equipment.
	 * 
	 * @param player
	 * @param slot
	 * @return whether or not the sound is being played.
	 */
	public static boolean playEquipmentChangedSound(Player player, int slot) {
		if (player != null) {
			player.getActionSender().playSound(
					create(getEquipmentChangedSound(slot), (byte) 1, 0));
			return true;

		}
		return false;
	}

	/**
	 * 
	 * @param weaponId
	 *            the item ID of the currently wielded weapon.
	 * @return the appropriate sound for the designated weapon.
	 */
	public static int getRegularAttackSound(int weaponId) {
		switch (weaponId) {
		case -1:
			return 2564;
		case 861:
		case 859:
		case 857:
		case 855:
		case 853:
		case 851:
			return 2693;
		case 4151:
			return 2720;
		case 4726:
			return 1328;
		case 4755:
			return 1323;
		case 4747:
			return 1332;
		case 4675:
			return 2555;
		case 6528:
			return 2520;
		case 4587:
		case 1305:
			return 2500;
		case 1307: // 2h swords.
		case 1309:
		case 1311:
		case 1313:
		case 1315:
		case 1317:
		case 1319:
		case 6609:
		case 7158:
			return 2503;
		case 4718:
			return 1321;
		case 5698:
			return 2517;
		case 4734:
		case 9185:
			return 2700;
		case 868:
			return 2696;
		case 11235:
			return 3731;
		case 11694:
		case 11696:
		case 11698:
		case 11700:
		case 11730:
			return 3846;
		default:
			return 10000;
		}

	}

	/**
	 * get the sound to play for the special attack corresponding with the
	 * weaponId parameter
	 * 
	 * @param weaponId
	 *            the weapon to get the special attack sound for
	 * @return the sound that corresponds with the weaponId
	 */
	public static int getSpecialAttackSound(int weaponId) {
		switch (weaponId) {
		case 861:
			return 2545;
		case 1434:
			return 2541;
		case 4151:
			return 2713;
		case 5698:
			return 2537;
		case 9244:
			return 2915;
		case 11235:
			return 3737;
		case 11694:
			return 3865;
		case 11696:
			return 3834;
		case 11730:
			return 3853;
		default:
			return 10000;
		}
	}

	public static int getSpellAttackSound(int bookId, int spellId) {
		switch (bookId) {
		case 0:
			switch (spellId) {
			case 1: // wind strike
				return 221;
			case 4: // water strike
				return 212;
			case 6: // earth strike
				return 133;
			case 8: // fire strike
				return 161;
			case 10: // wind bolt
				return 219;
			case 14: // water bolt
				return 210;
			case 17: // earth bolt
				return 131;
			case 20: // fire bolt
				return 158;
			case 24: // wind blast
				return 217;
			case 27: // water blast
				return 208;
			case 33: // earth blast
				return 129;
			case 38: // fire blast
				return 156;
			case 43: // flames of zamorak
				return 1655;
			case 45: // wind wave
				return 223;
			case 48: // water wave
				return 214;
			case 52: // earth wave
				return 135;
			case 55: // fire wave
				return 163;
			}
			break;
		case 1:
			switch (spellId) {
			case 1:
				return 169;
			case 2:
				return 170;
			case 3: // ice barrage
				return 168;
			case 4:
				return 174;
			case 5:
			case 6:
			case 7:
				return 110;
			case 8:
				return 181; // not correct
			case 9:
				return 184;
			case 10: // smoke burst
				return 180;
			}
			break;
		}
		return 10000;

	}

	/**
	 * Not working for any but weapon yet for some reason. TODO: fix this
	 * method.
	 * 
	 * @param slot
	 *            the item is being changed for.
	 * @return the sound to play for the corresponding equipment slot.
	 */
	public static int getEquipmentChangedSound(int slot) {
		switch (slot) {
		case Equipment.SLOT_WEAPON:
			return 2234;
		case Equipment.SLOT_SHIELD:
			return 2248;
		case Equipment.SLOT_BOTTOMS:
		case Equipment.SLOT_CHEST:
		case Equipment.SLOT_HELM:
			return 2239;
		case Equipment.SLOT_BOOTS:
		case Equipment.SLOT_GLOVES:
		case Equipment.SLOT_CAPE:
			return 2244;
		default:
			return 2239;
		}
	}

	public static int getSplashSound(int spellId) {
		switch (spellId) {
		case 43:
			return 1654;
		default:
			return 227;
		}
	}

	/**
	 * Get the corresponding attack sound for the NPC attacking.
	 * 
	 * @param npcId
	 * @return the sound to play for the NPC
	 */
	public static int getNpcAttackSound(int npcId) {
		switch (npcId) {
		case -1:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return 2564;
		default:
			return 10000;
		}
	}

	/**
	 * Gets the delay for the special attack sound
	 * 
	 * @param weaponId
	 *            the weapon attacking with
	 * @return the delay for the sound for the specified weaponId
	 */
	public static int getSpecialSoundDelay(int weaponId) {
		switch (weaponId) {
		case 11694:
			return 25;
		case 11235:
		case 11730:
			return 1;
		default:
			return 0;
		}
	}

	/**
	 * Gets the delay for the special attack sound
	 * 
	 * @param weaponId
	 *            the weapon attacking with
	 * @return the delay for the sound for the specified weaponId
	 */
	public static int getSpellSoundDelay(int spellId) {
		switch (spellId) {
		default:
			return 100;
		}
	}

	/**
	 * The id of this sound.
	 */
	private int id;

	/**
	 * The volume this sound is played at.
	 */
	private byte volume = 1;

	/**
	 * The delay before this sound is played.
	 */
	private int delay = 0;

	public Sound(int id, byte volume, int delay) {
		this.id = id;
		this.volume = volume;
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public int getId() {
		return id;
	}

	public byte getVolume() {
		return volume;
	}
}
