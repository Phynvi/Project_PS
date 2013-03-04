package org.rs2server.rs2.model.minigame.magetraining;

import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Player;
import org.rs2server.util.Misc;

public class MageTrainingArena extends MageTrainingArenaData {

	//TODO: 6 dragonstones in enchantment chamber
	public static MageTrainingArena getMageTrainingArena() {
		return new MageTrainingArena();
	}

	/**
	 * 
	 * @param objectId
	 * @return the item id corresponding to the specified object.
	 */
	public int getItemForObject(int objectId) {
		switch (objectId) {
		case 10799:
			return 6899;
		case 10800:
			return 6898;
		case 10801:
			return 6900;
		case 10802:
			return 6901;
		default:
			return -1;
		}
	}

	/**
	 * 
	 * @param player
	 * @param objectId
	 * @return
	 */
	public boolean handleObjectOption(Player player, int objectId) {
		switch (objectId) {
		case 10734:
			coinCollector(player);
			return true;
		case 10783:
		case 10785:
		case 10787:
		case 10789:
		case 10791:
		case 10793:
		case 10795:
		case 10797:
			searchCupboard(player, cupboardItems);
			return true;
		case 10799:
		case 10800:
		case 10801:
		case 10802:
			collectItem(player, getItemForObject(objectId));
			return true;
		case 10782:
			player.setTeleportTarget(PORTAL_CENTER);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 *            the player collecting the item from the pile.
	 * @param itemId
	 *            the item to append.
	 * @return
	 */
	public boolean collectItem(Player player, int itemId) {
		Item item = new Item(itemId);
		player.getInventory().add(item);
		return true;
	}

	/**
	 * Player receives one pizazz point per 100gp - sneaky
	 * 
	 * @param player
	 * @return
	 */
	public boolean coinCollector(Player player) {
		int pizazzCoin = 8890;
		if (player.getInventory().contains(pizazzCoin)) {
			Item pizazzCoins = new Item(pizazzCoin, player.getInventory()
					.getById(pizazzCoin).getCount());
			if (pizazzCoins.getCount() < 100) {
				if (player.getAttribute("pizazzCoins") != null) {
					player.setAttribute("pizazzCoins",
							(Integer) player.getAttribute("pizazzCoins")
									+ pizazzCoins.getCount());
				} else {
					player.setAttribute("pizazzCoins", pizazzCoins.getCount());
				}
			}
			if (player.getAttribute("pizazzCoins") != null) {
				if ((Integer) player.getAttribute("pizazzCoins") >= 100) {
					player.setPizazzPoints(player.getPizazzPoints()
							+ (int) Math.floor((Integer) player
									.getAttribute("pizazzCoins") / 100));
					if ((Integer) player.getAttribute("pizazzCoins") != 100) {
						player.setAttribute(
								"pizazzCoins",
								(Integer) player.getAttribute("pizazzCoins") - 100);
					} else {
						player.removeAttribute("pizazzCoins");
					}
				}

			}
			player.getInventory().remove(pizazzCoins);
			player.setPizazzPoints(player.getPizazzPoints()
					+ (int) Math.floor(pizazzCoins.getCount() / 100));
			ScriptManager.getScriptManager().call("alchemy_interface", player);
			if (player.isDebugMode()) {
				System.out
						.println(player.getName() + " has "
								+ player.getAttribute("pizazzCoins")
								+ " coins stored.");
			}
		}
		return true;
	}

	/**
	 * 
	 * @param player
	 *            the player searching the cupboard.
	 * @param items
	 *            the item array of rewards.
	 * @return the item reward to append.
	 */
	public boolean searchCupboard(Player player, int items[]) {
		// TODO: item appended should not be random. check wiki for more
		// details.
		int itemId = Misc.randomIntFromArray(items);
		Item item = new Item(itemId);
		player.getInventory().add(item);
		ScriptManager.getScriptManager().call("alchemy_interface", player);
		return true;
	}

}
