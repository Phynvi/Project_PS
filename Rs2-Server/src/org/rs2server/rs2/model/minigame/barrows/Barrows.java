package org.rs2server.rs2.model.minigame.barrows;

import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.NPCDefinition;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.boundary.BoundaryManager;
import org.rs2server.util.Misc;

/**
 * Barrows Minigame
 * 
 * @author Josh Mai
 * 
 */
public class Barrows extends BarrowsData {

	private static Barrows instance = null;

	public static Barrows getBarrows() {
		if (instance == null) {
			instance = new Barrows();
		}
		return instance;
	}

	/**
	 * Append the rewarded items to players inventory.
	 * 
	 * @param player
	 *            the player to award the rewards to.
	 */
	protected void barrowsReward(Player player) {
		int barrowChance = Misc.random(BARROWS_CHANCE);
		int killCount = player.getAttribute("barrowsKillCount");
		if (barrowChance == 0) {
			Item reward = new Item(
					BARROW_REWARDS[(int) (Math.random() * BARROW_REWARDS.length)]);
			player.getInventory().add(reward);
		}
		if (Misc.random(20) == 0) {
			player.getInventory().add(new Item(1149));
		} else if (Misc.random(15) == 0) {
			int halfKey = Misc.random(1) == 0 ? 985 : 987;
			player.getInventory().add(new Item(halfKey));
		}
		if (Misc.random(3) == 0) { // Bolt
			int amount = getAmountOfReward(4740, killCount);
			player.getInventory().add(new Item(4740), amount);
		}

		if (Misc.random(3) == 0) { // Blood runes
			int amount = getAmountOfReward(565, killCount);
			player.getInventory().add(new Item(565, amount));
		}
		if (Misc.random(2) == 0) { // Death runes
			int amount = getAmountOfReward(560, killCount);
			player.getInventory().add(new Item(560, amount));
		}
		if (Misc.random(1) == 0) { // Chaos runes
			int amount = getAmountOfReward(562, killCount);
			player.getInventory().add(new Item(562, amount));
		}
		if (Misc.random(1) == 0) { // Coins
			int amount = getAmountOfReward(995, killCount);
			player.getInventory().add(new Item(995, amount));
		}
		if (Misc.random(1) == 0) {
			int amount = getAmountOfReward(558, killCount); // Mind runes.
			player.getInventory().add(new Item(558, amount));
		}
	}

	/**
	 * Complete the barrows minigame.
	 * 
	 * @param player
	 */
	private void completeBarrowsMinigame(Player player) {
		player.getActionSender().sendMessage("You complete the game!");
		resetBarrowsMinigame(player);
		barrowsReward(player);
	}

	/**
	 * Determine how much of the rewarded amount to append.
	 * 
	 * @param item
	 * @param killCount
	 * @return
	 */
	private int getAmountOfReward(int item, int killCount) {
		int amount = 0;
		for (int i = 0; i < OTHER_REWARDS.length; i++) {
			if (OTHER_REWARDS[i] == item) {
				for (int j = 0; j < REWARD_KILLCOUNT[i].length; j++) {
					if (killCount >= REWARD_KILLCOUNT[i][j]) {
						amount = REWARD_AMOUNT[i][j];
					}
				}
				if (amount < MINIMUM_AMOUNT[i]) {
					amount = MINIMUM_AMOUNT[i];
				}
				break;
			}
		}
		return Misc.random(amount);
	}

	/**
	 * Dig into the crypt.
	 * 
	 * @return whether the crypt was entered or not.
	 */
	public boolean handleSpade(Player player) {
		if (player.getLocation().getZ() != 0) {
			return false;
		}
		if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Verac's Mound")) {
			player.setTeleportTarget(Location.create(3578, 9706, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Dharok's Mound")) {
			player.setTeleportTarget(Location.create(3556, 9718, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Ahrim's Mound")) {
			player.setTeleportTarget(Location.create(3557, 9703, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Torag's Mound")) {
			player.setTeleportTarget(Location.create(3568, 9683, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Karil's Mound")) {
			player.setTeleportTarget(Location.create(3546, 9684, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Guthan's Mound")) {
			player.setTeleportTarget(Location.create(3534, 9704, 3));
			player.getActionSender().sendMessage("You've broken into a crypt.");
			return true;
		}
		return false;
	}

	/**
	 * Unregister the brother, increase the kill
	 * 
	 * @param killer
	 * @param brother
	 */
	public void killBrother(Player killer, NPC brother) {
		World.getWorld().unregister(brother);
		setKillCount(killer, getKillCount(killer) + 1);
		killer.getActionSender().sendString(24, 0,
				"Kill Count: " + getKillCount(killer));
		if (getKillCount(killer) == BARROWS_BROTHERS.length) {
			completeBarrowsMinigame(killer);
		}
	}

	/**
	 * Handle the object option for the sarcophagus in the crypt.
	 * 
	 * @param objectId
	 */
	public void openSarcophagus(Player player, final int objectId) {
		switch (objectId) {
		case AHRIM_SARCOPHAGUS:
			if (player.getAttribute("ahrimSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[AHRIM]);
				player.setAttribute("ahrimSpawned", 1);
			}
			break;
		case DHAROK_SARCOPHAGUS:
			if (player.getAttribute("dharokSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[DHAROK]);
				player.setAttribute("dharokSpawned", 1);
			}
			break;
		case GUTHAN_SARCOPHAGUS:
			if (player.getAttribute("guthanSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[GUTHAN]);
				player.setAttribute("guthanSpawned", 1);
			}
			break;
		case KARIL_SARCOPHAGUS:
			if (player.getAttribute("karilSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[KARIL]);
				player.setAttribute("karilSpawned", 1);
			}
			break;
		case TORAG_SARCOPHAGUS:
			if (player.getAttribute("toragSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[TORAG]);
				player.setAttribute("toragSpawned", 1);
			}
			break;
		case VERAC_SARCOPHAGUS:
			if (player.getAttribute("veracSpawned") == null) {
				summonBrother(player, BARROWS_BROTHERS[VERAC]);
				player.setAttribute("veracSpawned", 1);
			}
			break;
		}
	}

	/**
	 * Reset the variables associated with this minigame.
	 * 
	 * @param player
	 */
	private void resetBarrowsMinigame(Player player) {
		setKillCount(player, 0);
		player.removeAttribute("ahrimSpawned");
		player.removeAttribute("dharokSpawned");
		player.removeAttribute("guthanSpawned");
		player.removeAttribute("karilSpawned");
		player.removeAttribute("toragSpawned");
		player.removeAttribute("veracSpawned");
	}

	/**
	 * Summon the Barrow's brother
	 * 
	 * @param npcId
	 *            the id of the brother to spawn
	 */
	private void summonBrother(Player player, int npcId) {
		NPC npc = new NPC(NPCDefinition.forId(npcId), Location.create(
				getLocation(npcId).getX(), getLocation(npcId).getY(),
				getLocation(npcId).getZ()), getLocation(npcId),
				getLocation(npcId), 6);
		World.getWorld().register(npc);
		npc.setForceChat("You dare disturb my rest!");
		npc.forceChat(npc.getForcedChatMessage());
		player.getActionSender().sendHintArrow(npc, 0, 1);
	}

	public void setKillCount(Player player, int val) {
		player.setBarrowsKilled(val);
	}

	public int getKillCount(Player player) {
		return player.getBarrowsKilled();
	}

}
