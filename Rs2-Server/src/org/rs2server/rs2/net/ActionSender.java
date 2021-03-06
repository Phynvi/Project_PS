package org.rs2server.rs2.net;

import java.util.List;
import java.util.Map;

import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.Constants;
import org.rs2server.rs2.model.Entity;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.GroundItem;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Palette;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.Sound;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.Animation.FacialAnimation;
import org.rs2server.rs2.model.Mob.InteractionMode;
import org.rs2server.rs2.model.Palette.PaletteTile;
import org.rs2server.rs2.model.PrivateChat.ClanRank;
import org.rs2server.rs2.model.RequestManager.RequestState;
import org.rs2server.rs2.model.boundary.BoundaryManager;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction.SpellBook;
import org.rs2server.rs2.model.container.Equipment;
import org.rs2server.rs2.model.container.Inventory;
import org.rs2server.rs2.model.container.impl.EquipmentContainerListener;
import org.rs2server.rs2.model.container.impl.InterfaceContainerListener;
import org.rs2server.rs2.model.container.impl.WeaponContainerListener;
import org.rs2server.rs2.model.minigame.impl.FightPits;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.net.Packet.Type;
import org.rs2server.rs2.util.NameUtils;
import org.rs2server.rs2.util.TextUtils;

/**
 * A utility class for sending packets.
 * 
 * @author Graham Edgecombe
 * @author Josh Mai
 * 
 */
public class ActionSender {

	public enum DialogueType {

		NPC, PLAYER, OPTION, MESSAGE, MESSAGE_MODEL_LEFT, AGILITY_LEVEL_UP, ATTACK_LEVEL_UP, COOKING_LEVEL_UP, CRAFTING_LEVEL_UP, DEFENCE_LEVEL_UP, FARMING_LEVEL_UP, FIREMAKING_LEVEL_UP, FISHING_LEVEL_UP, FLETCHING_LEVEL_UP, HERBLORE_LEVEL_UP, HITPOINT_LEVEL_UP, MAGIC_LEVEL_UP, MINING_LEVEL_UP, PRAYER_LEVEL_UP, RANGING_LEVEL_UP, RUNECRAFTING_LEVEL_UP, SLAYER_LEVEL_UP, SMITHING_LEVEL_UP, STRENGTH_LEVEL_UP, THIEVING_LEVEL_UP, WOODCUTTING_LEVEL_UP
	}

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * The player's ping count.
	 */
	private int pingCount;

	/**
	 * Creates an action sender for the specified player.
	 * 
	 * @param player
	 *            The player to create the action sender for.
	 */
	public ActionSender(Player player) {
		this.player = player;
	}

	/**
	 * Animates an object.
	 * 
	 * @param obj
	 *            The object.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender animateObject(GameObject obj, int animationId) {
		if (player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(55);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.putByteA((byte) ot);
		spb.put((byte) 0);
		spb.putShort(animationId);
		player.write(spb.toPacket());
		return this;
	}

	/**
	 * Sends a 'jingle' music track unlocked by region.
	 * 
	 * @param id
	 *            - the music track to play.
	 * @return
	 */
	public ActionSender playMusic(int id) {
		PacketBuilder bldr = new PacketBuilder(121);
		bldr.putLEShortA(id);
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a sound to the client.
	 * 
	 * @param sound
	 *            The sound to play.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender playSound(Sound sound) {
		PacketBuilder bldr = new PacketBuilder(243);
		bldr.putShort(sound.getId()).put(sound.getVolume())
				.putShort(sound.getDelay());
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Removes an open interface.
	 * 
	 * @param id
	 *            The interface id.
	 * @param child
	 *            The interface child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeAllInterfaces() {
		sendInterfacesRemovedClientSide();
		removeInterface();
		removeSidetabInterface();
		removeChatboxInterface();
		return this;
	}

	/**
	 * Removes the chatbox interface.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeChatboxInterface() {
		player.getSession().write(
				new PacketBuilder(174).putInt(
						Constants.MAIN_WINDOW << 16 | Constants.CHAT_BOX)
						.toPacket()); // chat box screen
		player.getActionSender().sendRunScript(
				Constants.REMOVE_INPUT_INTERFACE, new Object[] { "" }, "");
		return this;
	}

	/**
	 * Shows an item on the ground.
	 * 
	 * @param item
	 *            The ground item.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeGroundItem(GroundItem item) {
		if (item == null || item.getItem().getId() < 1
				|| item.getLocation().getX() < 1
				|| item.getLocation().getY() < 1) {
			return this;
		}
		sendArea(item.getLocation(), 0, 0);
		PacketBuilder bldr = new PacketBuilder(28);
		bldr.putShortA(item.getItem().getId());
		bldr.putByteA((byte) 0);
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Removes the side tab interface.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeInterface() {
		if (player.getInterfaceState().isWalkableInterface()) {
			return this;
		}
		player.getInterfaceState().interfaceClosed();
		player.getSession().write(
				new PacketBuilder(174).putInt(
						Constants.MAIN_WINDOW << 16 | Constants.GAME_WINDOW)
						.toPacket()); // main game screen
		return this;
	}

	/**
	 * Removes an open interface.
	 * 
	 * @param id
	 *            The interface id.
	 * @param child
	 *            The interface child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeInterfaces(int id, int child) {
		if (id == Constants.MAIN_WINDOW && child == Constants.GAME_WINDOW) {
			sendInterfacesRemovedClientSide();
		}
		player.getSession().write(
				new PacketBuilder(174).putInt(id << 16 | child).toPacket());
		return this;
	}

	/**
	 * Removes a game object on a players screen.
	 * 
	 * @param obj
	 *            The object to remove.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeObject(GameObject obj) {
		if (player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(86);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.putByteC((byte) ot);
		spb.putByteC((byte) 0);
		player.write(spb.toPacket());
		return this;
	}

	/**
	 * Removes the sidebars temporarily.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeSideBars() {
		return sendInterface(Constants.MAIN_WINDOW, 97, 149, false);
	}

	/**
	 * Removes the side tab interface.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender removeSidetabInterface() {
		player.getSession().write(
				new PacketBuilder(174).putInt(
						Constants.MAIN_WINDOW << 16 | Constants.SIDE_TABS)
						.toPacket()); // tabs screen
		return this;
	}

	public void resetMapFlag() {
		PacketBuilder pb = new PacketBuilder(84);
		player.getSession().write(pb.toPacket());
	}

	/**
	 * Sends an access mask to the client.
	 * 
	 * @param set
	 *            The set.
	 * @param window
	 *            The window
	 * @param interfaceId
	 *            The interface id.
	 * @param offset
	 *            The offset.
	 * @param length
	 *            The length.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendAccessMask(int set, int window, int interfaceId,
			int offset, int length) {
		PacketBuilder bldr = new PacketBuilder(133);
		bldr.putLEInt(set);
		bldr.putLEShortA(length);
		bldr.putShort(interfaceId).putShort(window);
		bldr.putShortA(offset);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends your location to the client.
	 * 
	 * @param location
	 *            The location.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendArea(Location location, int xOffset, int yOffset) {
		PacketBuilder bldr = new PacketBuilder(168);
		int regionX = player.getLastKnownRegion().getRegionX(), regionY = player
				.getLastKnownRegion().getRegionY();
		bldr.put((byte) ((location.getX() - ((regionX) * 8)) + xOffset));
		bldr.putByteA((byte) ((location.getY() - ((regionY) * 8)) + yOffset));
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendAreaInterface(Location before, Location after) {
		boolean afterInPvP = BoundaryManager.isWithinBoundaryNoZ(after,
				"PvP Zone");
		boolean beforeInPvP = before != null ? BoundaryManager
				.isWithinBoundaryNoZ(before, "PvP Zone") : false;
		boolean afterInFightPits = BoundaryManager.isWithinBoundaryNoZ(after,
				"Fight Pits");
		boolean afterInPestControl = BoundaryManager.isWithinBoundaryNoZ(after,
				"Pest Control");
		boolean afterInPestControlWaiting = BoundaryManager
				.isWithinBoundaryNoZ(after, "Pest Control Waiting Room");
		if (player.getLocation().equals(FightPits.CENTRE_ORB)
				|| player.getLocation().equals(FightPits.NORTH_EAST)
				|| player.getLocation().equals(FightPits.NORTH_WEST)
				|| player.getLocation().equals(FightPits.SOUTH_EAST)
				|| player.getLocation().equals(FightPits.SOUTH_WEST)) {
			afterInFightPits = false;
			afterInPvP = false;
		}
		if (afterInPvP) {
			if (!afterInFightPits) {
				int wildernessLevel = 1 + (player.getLocation().getY() - 3520) / 8;
				sendString(380, 1, "Level: " + wildernessLevel);
			} else if (afterInFightPits) {
				sendString(373, 0, "Current Champion: "
						+ World.getWorld().getFightPits().getWinner());
				sendString(373, 1, World.getWorld().getFightPits()
						.getParticipants().size() > 1 ? "Foes Remaining: "
						+ (World.getWorld().getFightPits().getParticipants()
								.size() - 1) : "You're the Winner!");
			}
			if (!beforeInPvP) {
				if (afterInFightPits) {
					sendWalkableInterface(373);
				} else {
					sendWalkableInterface(380);
				}
				sendInteractionOption("Attack", 1, true);
				sendInteractionOption("null", 2, false);
			}
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Alchemy Chamber")) {
			ScriptManager.getScriptManager().call("alchemy_interface", player);
		} else if (before != null
				&& BoundaryManager.isWithinBoundary(before, "Alchemy Chamber")) {
			sendWalkableInterface(-1);
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Enchantment Chamber")) {
			ScriptManager.getScriptManager().call("enchantment_interface",
					player);
		} else if (before != null
				&& BoundaryManager.isWithinBoundary(before,
						"Enchantment Chamber")) {
			sendWalkableInterface(-1);
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Graveyard Chamber")) {
			ScriptManager.getScriptManager()
					.call("graveyard_interface", player);
		} else if (before != null
				&& BoundaryManager
						.isWithinBoundary(before, "Graveyard Chamber")) {
			sendWalkableInterface(-1);
		} else if (BoundaryManager.isWithinBoundary(player.getLocation(),
				"Telekenetic Chamber")) {
			ScriptManager.getScriptManager().call("telekenetic_interface",
					player);
		} else if (before != null
				&& BoundaryManager.isWithinBoundary(before,
						"Telekenetic Chamber")) {
			sendWalkableInterface(-1);
		}

		else if (BoundaryManager.isWithinBoundaryNoZ(player.getLocation(),
				"Barrow Tunnels")) {
			sendWalkableInterface(24);
			sendString(24, 0, "Kill Count: " + player.getBarrowsKilled());
			// player.getActionSender().sendMinimapBlackout(2);
		} else if (before != null
				&& BoundaryManager
						.isWithinBoundaryNoZ(before, "Barrow Tunnels")) {
			sendWalkableInterface(-1);
			// player.getActionSender().sendMinimapBlackout(0);
			// player.getActionSender().sendMinimapBlackout(2);
		} else if (BoundaryManager.isWithinBoundaryNoZ(after,
				"God Wars Entrance")) {
			sendWalkableInterface(481); // snow
		} else if (beforeInPvP
				|| before != null
				&& BoundaryManager.isWithinBoundaryNoZ(before,
						"God Wars Entrance")) {
			sendWalkableInterface(-1);
			sendInteractionOption("null", 1, true);
			sendInteractionOption("null", 2, false);
		} else if (afterInPestControl) {
			sendWalkableInterface(408);
		} else if (afterInPestControlWaiting) {
			sendWalkableInterface(407);
		} else if (before != null
				&& BoundaryManager.isWithinBoundaryNoZ(before, "Pest Control")) {
			sendWalkableInterface(-1);
		} else if (before != null
				&& BoundaryManager.isWithinBoundaryNoZ(before,
						"Pest Control Waiting Room")) {
			sendWalkableInterface(-1);
		}

		if (BoundaryManager.isWithinBoundaryNoZ(player.getLocation(),
				"DuelArena")) {
			/*
			 * if (player.getDueling() != null) { if
			 * (player.getDueling().getDuelStatus() >= 4) {
			 * sendInteractionOption("Fight", 1, true);
			 * sendInteractionOption("null", 2, false); } else if
			 * (player.getDueling().getDuelStatus() < 4) {
			 * sendInteractionOption("null", 1, true);
			 * sendInteractionOption("Challenge", 2, false); } } else {
			 */
			sendInteractionOption("null", 1, true);
			sendInteractionOption("Challenge", 2, false);
			// }
			sendWalkableInterface(389);
		}

		if (BoundaryManager.isWithinBoundaryNoZ(after, "MultiCombat")) {
			sendInterfaceConfig(Constants.MAIN_WINDOW, 78, false);
		} else {
			sendInterfaceConfig(Constants.MAIN_WINDOW, 78, true);
		}
		return this;
	}

	/**
	 * Sends the players bonuses to the client.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendBonuses() {
		int id = 108;
		for (int index = 0; index < player.getCombatState().getBonuses().length; index++) {
			if (id == 118) {
				id++;
			}
			String bonus = (Constants.BONUSES[index] + ": "
					+ (player.getCombatState().getBonus(index) >= 0 ? "+" : "") + player
					.getCombatState().getBonus(index));
			if (index == 10) {
				bonus += "/"
						+ (player.getCombatState().getBonus(12) >= 0 ? "+" : "")
						+ player.getCombatState().getBonus(12);
			}
			sendString(Equipment.SCREEN, id++, bonus);
			if (index == 11) {
				break;
			}
		}
		return this;
	}

	/**
	 * Sends a chatbox interface.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param inventoryInterfaceId
	 *            The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendChatboxInterface(int inventoryInterfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, Constants.CHAT_BOX,
				inventoryInterfaceId, false);
	}

	public ActionSender sendChatInterface(int interfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, Constants.CHAT_BOX,
				interfaceId, false);
	}

	public ActionSender sendClanChannel(String owner, String channelName,
			boolean inChannel, List<Player> members, Map<Long, ClanRank> friends) {
		PacketBuilder bldr = new PacketBuilder(56, Type.VARIABLE_SHORT);
		bldr.putLong(TextUtils.playerNameToLong(owner));
		bldr.putLong(TextUtils.playerNameToLong(channelName));
		bldr.put((byte) (inChannel ? 1 : 0));
		bldr.put((byte) (inChannel ? members.size() : 0)); // size
		// loop here
		if (inChannel) {
			for (Player p : members) {
				bldr.putLong(p.getNameAsLong());// player name
				bldr.putShort(1);// world
				bldr.put((byte) (friends.containsKey(p.getNameAsLong()) ? friends
						.get(p.getNameAsLong()).getId() : (p.getName().equals(
						owner) ? ClanRank.OWNER.getId() : -1))); // rank
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendClanMessage(String name, String channelName,
			byte[] message, int rights) {
		PacketBuilder pkt = new PacketBuilder(35, Type.VARIABLE);
		pkt.putLong(NameUtils.nameToLong(name));
		pkt.put((byte) 1);// it's just read does nothing
		pkt.putLong(NameUtils.nameToLong(channelName));
		pkt.putShort(1);// like message counter from friends
		pkt.put((byte) 0);
		pkt.put((byte) 0);
		pkt.put((byte) 0);
		pkt.put((byte) rights); // rights
		pkt.put(message);
		player.write(pkt.toPacket());
		return this;
	}

	public ActionSender sendCommandsMenu() {
		sendString(275, 2, "Commands");
		String[] info = new String[] {
				"Welcome to " + Constants.SERVER_NAME + ", " + player.getName()
						+ ".",
				"This is the commands menu, it will show you what commands you can use",
				"and how to use them.", "", "", "",
				"<col=0000FF>Item Spawning", "",
				"The command to spawn an item is:",
				"<col=990000>::item ID AMT",
				"ID = The items ID      AMT = The amount of items you want.",
				"", "The command to search for an item's ID is:",
				"<col=990000>::itembyname NAME", "NAME = The item's name", "",
				"The command to spawn all runes is:", "<col=990000>::runes",
				"", "The command to spawn runes for the Vengeance spell is:",
				"<col=990000>::veng", "",
				"The command to empty your inventory is:",
				"<col=990000>::empty", "", "The command to open your bank is:",
				"<col=990000>::bank", "", "", "", "<col=0000FF>Level Gaining",
				"", "The command to set a level is:",
				"<col=990000>::lvl ID AMT",
				"ID = The levels ID      AMT = The amount of levels you want.",
				"", "The command to max your character is:",
				"<col=990000>::max", "", "", "", "<col=0000FF>Spell Books", "",
				"The command to turn to moderm magics is:",
				"<col=990000>::modern         OR           ::normal", "",
				"The command to turn to ancient magicks is:",
				"<col=990000>::ancients", "",
				"The command to turn to lunar magics is:",
				"<col=990000>::lunar", "", "", "", "<col=0000FF>Teleporting",
				"", "Edgeville:", "<col=990000>::edge", "",
				"Home teleport teles you to neitiznot." };
		for (int i = 0; i < 178; i++) {
			if (i < info.length) {
				sendString(275, 4 + i, info[i]);
			} else {
				sendString(275, 4 + i, "");
			}
		}
		sendInterface(275, true);
		return this;
	}

	/**
	 * Sets a client configuration.
	 * 
	 * @param id
	 *            The id.
	 * @param value
	 *            The value.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendConfig(int id, int value) {
		if (value > 128) {
			PacketBuilder bldr = new PacketBuilder(10);
			bldr.putLEInt(value);
			bldr.putShort(id);
			player.getSession().write(bldr.toPacket());
		} else {
			PacketBuilder bldr = new PacketBuilder(253);
			bldr.putShort(id);
			bldr.putByteA(value);
			player.getSession().write(bldr.toPacket());
		}
		return this;
	}

	/**
	 * Sends the packet to construct a map region.
	 * 
	 * @param palette
	 *            The palette of map regions.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendConstructMapRegion(Palette palette) {
		player.setLastKnownRegion(player.getLocation());
		PacketBuilder bldr = new PacketBuilder(241, Type.VARIABLE_SHORT);
		bldr.putShortA(player.getLocation().getRegionY() + 6);
		bldr.startBitAccess();
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 13; x++) {
				for (int y = 0; y < 13; y++) {
					PaletteTile tile = palette.getTile(x, y, z);
					bldr.putBits(1, tile != null ? 1 : 0);
					if (tile != null) {
						bldr.putBits(26, tile.getX() << 14 | tile.getY() << 3
								| tile.getZ() << 24 | tile.getRotation() << 1);
					}
				}
			}
		}
		bldr.finishBitAccess();
		bldr.putShort(player.getLocation().getRegionX() + 6);
		// player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a debug message.
	 * 
	 * @param message
	 *            The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDebugMessage(String message) {
		return player.isDebugMode() ? sendMessage("<col=ff0000>" + message)
				: this;
	}

	/**
	 * Sends a debug message.
	 * 
	 * @param message
	 *            The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDebugPacket(int opCode, String description,
			Object[] params) {
		String paramString = "";
		for (Object object : params) {
			paramString += object.toString() + "    ";
		}
		return sendDebugMessage(
				"------------------------------------------------------------------------------------------")
				.sendDebugMessage(
						"Pkt            " + opCode + "  " + description)
				.sendDebugMessage(
						"------------------------------------------------------------------------------------------")
				.sendDebugMessage("Params    " + paramString)
				.sendDebugMessage(
						"------------------------------------------------------------------------------------------");
	}

	public ActionSender sendDefaultChatbox() {
		player.write(new PacketBuilder(15)
				.put((byte) player.getInterfaceState().getPublicChat())
				.put((byte) player.getInterfaceState().getPrivateChat())
				.put((byte) player.getInterfaceState().getTrade()).toPacket());
		player.write(new PacketBuilder(17).putByteA((byte) 0).putLEShort(137)
				.putShort(90).putShort(Constants.MAIN_WINDOW).toPacket());
		return this;
	}

	/**
	 * Sends the initial login packet (e.g. members, player id).
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendDetails() {
		// player.write(new PacketBuilder(249).putByteA(player.isMembers() ? 1 :
		// 0).putLEShortA(player.getIndex()).toPacket());
		// player.write(new PacketBuilder(107).toPacket());
		return this;
	}

	public ActionSender sendDialogue(String title, DialogueType dialogueType,
			int entityId, FacialAnimation animation, String... text) {
		int interfaceId = -1;
		switch (dialogueType) {
		case NPC:
			if (text.length > 4 || text.length < 1) {
				return this;
			}
			interfaceId = text.length + 240;
			if (interfaceId <= 240) {
				interfaceId = 241;
			}
			sendNPCHead(entityId, interfaceId, 0);
			sendInterfaceAnimation(animation.getAnimation().getId(),
					interfaceId, 0);
			sendString(interfaceId, 1, title);
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 2 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case PLAYER:
			if (text.length > 4 || text.length < 1) {
				return this;
			}
			interfaceId = text.length + 63;
			if (interfaceId <= 63) {
				interfaceId = 64;
			}
			sendPlayerHead(interfaceId, 0);
			sendInterfaceAnimation(animation.getAnimation().getId(),
					interfaceId, 0);
			sendString(interfaceId, 1, title);
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 2 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case OPTION:
			if (text.length > 5 || text.length < 2) {
				return this;
			}
			interfaceId = 224 + (text.length * 2);
			sendString(interfaceId, 0, title);
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 1 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MESSAGE:
			interfaceId = 209 + text.length;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MESSAGE_MODEL_LEFT:
			interfaceId = 519;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 1 + i, text[i]);
			}
			player.getActionSender().sendInterfaceModel(519, 0, 130, entityId);
			sendChatboxInterface(interfaceId);
			break;
		case AGILITY_LEVEL_UP:
			interfaceId = 157;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case ATTACK_LEVEL_UP:
			interfaceId = 158;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case COOKING_LEVEL_UP:
			interfaceId = 159;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case CRAFTING_LEVEL_UP:
			interfaceId = 160;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case DEFENCE_LEVEL_UP:
			interfaceId = 161;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FARMING_LEVEL_UP:
			interfaceId = 162;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FIREMAKING_LEVEL_UP:
			interfaceId = 163;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FISHING_LEVEL_UP:
			interfaceId = 164;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case FLETCHING_LEVEL_UP:
			interfaceId = 165;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case HERBLORE_LEVEL_UP:
			interfaceId = 166;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case HITPOINT_LEVEL_UP:
			interfaceId = 167;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MAGIC_LEVEL_UP:
			interfaceId = 168;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case MINING_LEVEL_UP:
			interfaceId = 169;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case PRAYER_LEVEL_UP:
			interfaceId = 170;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case RANGING_LEVEL_UP:
			interfaceId = 171;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case RUNECRAFTING_LEVEL_UP:
			interfaceId = 172;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case SLAYER_LEVEL_UP:
			interfaceId = 173;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case SMITHING_LEVEL_UP:
			interfaceId = 174;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case STRENGTH_LEVEL_UP:
			interfaceId = 175;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case THIEVING_LEVEL_UP:
			interfaceId = 176;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		case WOODCUTTING_LEVEL_UP:
			interfaceId = 177;
			for (int i = 0; i < text.length; i++) {
				sendString(interfaceId, 0 + i, text[i]);
			}
			sendChatboxInterface(interfaceId);
			break;
		}
		return this;
	}

	/**
	 * Sends the enter amount interface.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendEnterAmountInterface() {
		player.getActionSender().sendRunScript(
				Constants.NUMERICAL_INPUT_INTERFACE,
				new Object[] { "Enter amount:" }, "s");
		return this;
	}

	/**
	 * Sends the enter amount interface.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendEnterTextInterface(String question) {
		player.getActionSender().sendRunScript(
				Constants.ALPHA_NUMERICAL_INPUT_INTERFACE,
				new Object[] { question }, "s");
		return this;
	}

	/**
	 * Starts the player following a mob.
	 * 
	 * @param mob
	 *            The mob to follow.
	 * @param distance
	 *            The distance to stop moving.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFollowing(Mob mob, int distance) {
		PacketBuilder pb = new PacketBuilder(0);
		pb.putShort(mob.getIndex());
		pb.put((byte) (mob instanceof Player ? 0 : 1));
		pb.putShort(distance); // follow distance, set to 8 for range etc.
		player.write(pb.toPacket());
		player.setInteractingEntity(InteractionMode.ATTACK, mob);
		return this;
	}

	/**
	 * Sends to the client that a player is logged in on a world.
	 * 
	 * @param name
	 *            The player's name, as a long.
	 * @param world
	 *            The world they are on.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriend(long nameAsLong, int world, int clanRank) {
		player.write(new PacketBuilder(200).putLong(nameAsLong).putShort(world)
				.put((byte) clanRank).toPacket());
		return this;
	}

	/**
	 * Sends all of the player's ignore list.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriends() {
		for (long l : player.getPrivateChat().getFriends().keySet()) {
			if (World.getWorld().getPlayerNames().get(l) != null) {
				Player p = World.getWorld().getPlayerNames().get(l);
				if ((p.getInterfaceState().getPrivateChat() == 0 || p
						.getInterfaceState().getPrivateChat() == 1
						&& p.getPrivateChat().getFriends()
								.containsKey(player.getNameAsLong()))
						&& !p.getPrivateChat().getIgnores()
								.contains(player.getNameAsLong())) {
					sendFriend(p.getNameAsLong(), 1, player.getPrivateChat()
							.getFriends().get(p.getNameAsLong()).getId());
				}
				if (p.getPrivateChat().getFriends()
						.containsKey(player.getNameAsLong())
						&& player.getInterfaceState().getPrivateChat() == 1) { // if
																				// we've
																				// added
																				// them,
																				// but
																				// we
																				// had
																				// private
																				// set
																				// to
																				// 'friends',
																				// we
																				// need
																				// to
																				// tell
																				// them
																				// we
																				// are
																				// now
																				// online
																				// because
																				// we
																				// are
																				// friends
																				// with
																				// them
					p.getActionSender().sendFriend(
							player.getNameAsLong(),
							1,
							p.getPrivateChat().getFriends()
									.get(player.getNameAsLong()).getId());
				}
				sendFriend(l, 1, player.getPrivateChat().getFriends().get(l)
						.getId());
			} else {
				sendFriend(l, 0, player.getPrivateChat().getFriends().get(l)
						.getId());
			}
		}
		return this;
	}

	/**
	 * Sends to the client what state the player's friend list loading is at.
	 * 
	 * @param status
	 *            Loading = 0 Connecting = 1 OK = 2
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendFriendServer(int status) {
		player.write(new PacketBuilder(30).put((byte) status).toPacket());
		return this;
	}

	/**
	 * Sends all the game objects in a players area.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendGameObjectsInArea() {
		// TODO check distance!!
		Region[] regions = World.getWorld().getRegionManager()
				.getSurroundingRegions(player.getLocation());
		for (Region r : regions) {
			for (GameObject obj : r.getGameObjects()) {
				if (!obj.isLoadedInLandscape()) {
					sendObject(obj);
				}
			}
		}
		return this;
	}

	/**
	 * Shows an item on the ground.
	 * 
	 * @param item
	 *            The ground item.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendGroundItem(GroundItem item) {
		if (item == null || item.getItem().getId() < 1
				|| item.getLocation().getX() < 1
				|| item.getLocation().getY() < 1
				|| item.getItem().getCount() < 1) {
			return this;
		}
		sendArea(item.getLocation(), 0, 0);
		PacketBuilder bldr = new PacketBuilder(7);
		bldr.put((byte) 0);
		bldr.putShort(item.getItem().getId());
		bldr.putLEShortA(item.getItem().getCount());
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends all the ground items in a players area.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendGroundItemsInArea() {
		// TODO check distance!!
		for (Region r : World.getWorld().getRegionManager()
				.getSurroundingRegions(player.getLocation())) {
			for (GroundItem item : r.getGroundItems()) {
				if (item.isOwnedBy(player.getName())) {
					sendGroundItem(item);
				}
			}
		}
		return this;
	}

	/**
	 * Sends the hint arrow ontop of an entity
	 * 
	 * @param entity
	 *            The entity.
	 * @param height
	 *            The height of the arrow.
	 * @param position
	 *            The position on the tile of the arrow (2 = middle, 3 = west, 4
	 *            = east, 5 = south, 6 = north).
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendHintArrow(Entity entity, int height, int position) {
		PacketBuilder bldr = new PacketBuilder(48);
		if (entity.isNPC() || entity.isPlayer()) {
			bldr.put((byte) (entity.isNPC() ? 1 : 10));
			bldr.putShort(entity.getClientIndex());
			bldr.put((byte) 0);
			bldr.put((byte) 0);
			bldr.put((byte) 0);
		} else if (entity.isObject()) {
			bldr.put((byte) position);
			bldr.putShort(entity.getLocation().getX());
			bldr.putShort(entity.getLocation().getY());
			bldr.put((byte) height);
		}
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends to the client an ignored player's name.
	 * 
	 * @param nameAsLong
	 *            The player's name, as a long.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendIgnore(long nameAsLong) {
		player.write(new PacketBuilder(173, Type.VARIABLE_SHORT).putLong(
				nameAsLong).toPacket());
		return this;
	}

	/**
	 * Sends all of the player's ignore list.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendIgnores() {
		for (long l : player.getPrivateChat().getIgnores()) {
			sendIgnore(l);
		}
		return this;
	}

	/**
	 * Sends the player an option.
	 * 
	 * @param slot
	 *            The slot to place the option in the menu.
	 * @param top
	 *            Flag which indicates the item should be placed at the top.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInteractionOption(String option, int slot,
			boolean top) {
		PacketBuilder bldr = new PacketBuilder(225, Type.VARIABLE);
		bldr.putByteA(top ? (byte) 1 : (byte) 0);
		bldr.putRS2String(option);
		bldr.put((byte) -slot);
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendInterface(int interfaceId, boolean interfaceClosed) {
		if (interfaceClosed) {
			player.getInterfaceState().interfaceClosed();
		}
		return sendInterface(Constants.MAIN_WINDOW, Constants.GAME_WINDOW,
				interfaceId, false);
	}

	public ActionSender sendInterface(int windowId, int position,
			int interfaceId, boolean walkable) {
		if (windowId == Constants.MAIN_WINDOW
				&& position == Constants.GAME_WINDOW) {
			player.getInterfaceState().interfaceOpened(interfaceId, walkable);
		}
		PacketBuilder pb = new PacketBuilder(17);
		pb.putByteA((byte) (walkable ? 1 : 0));
		pb.putLEShort(interfaceId);
		pb.putShort(position);
		pb.putShort(windowId);
		player.write(pb.toPacket());
		return this;
	}

	/**
	 * Sends an animation of an interface.
	 * 
	 * @param emoteId
	 *            The emote id.
	 * @param interfaceId
	 *            The interface id.
	 * @param childId
	 *            The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceAnimation(int emoteId, int interfaceId,
			int childId) {
		player.getSession().write(
				new PacketBuilder(107).putInt2(interfaceId << 16 | childId)
						.putLEShort(emoteId).toPacket());
		return this;
	}

	/**
	 * Sets a config on an interface.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param childId
	 *            The child id.
	 * @param hidden
	 *            The hidden flag.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceConfig(int interfaceId, int childId,
			boolean hidden) {
		PacketBuilder bldr = new PacketBuilder(184);
		bldr.putByteA((byte) (hidden ? 1 : 0));
		bldr.putInt(interfaceId << 16 | childId);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends an inventory interface.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param inventoryInterfaceId
	 *            The inventory interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceInventory(int inventoryInterfaceId) {
		return sendInterface(Constants.MAIN_WINDOW, Constants.SIDE_TABS,
				inventoryInterfaceId, false);
	}

	/**
	 * Sends a model in an interface.
	 * 
	 * @param id
	 *            The interface id.
	 * @param zoom
	 *            The zoom.
	 * @param model
	 *            The model id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfaceModel(int id, int zoom, int model) {
		PacketBuilder bldr = new PacketBuilder(246);
		bldr.putLEShort(id).putShort(zoom).putShort(model);
		// player.write(bldr.toPacket());
		return this;
	}

	/**
	 * 
	 * @param interfaceId
	 *            the interface to display the model on
	 * @param childId
	 *            the childid of the interface
	 * @param size
	 *            the size of the model
	 * @param itemId
	 *            the item id of the model
	 * @return
	 */
	public ActionSender sendInterfaceModel(int interfaceId, int childId,
			int size, int itemId) {
		player.write(new PacketBuilder(249)
				.putInt1(interfaceId << 16 | childId).putInt(size)
				.putShort(itemId).toPacket());
		return this;
	}

	/**
	 * Interfaces are removed clientside (do not send any data to the client or
	 * this will cause a bug with opening interfaces such as Report Abuse).
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendInterfacesRemovedClientSide() {
		if (player.getRequestManager().getAcquaintance() != null) {
			if (player.getRequestManager().getState() != RequestState.COMMENCING
					&& player.getRequestManager().getAcquaintance()
							.getRequestManager().getState() != RequestState.COMMENCING
					&& player.getRequestManager().getState() != RequestState.ACTIVE
					&& player.getRequestManager().getAcquaintance()
							.getRequestManager().getState() != RequestState.ACTIVE
					&& player.getRequestManager().getState() != RequestState.FINISHED
					&& player.getRequestManager().getAcquaintance()
							.getRequestManager().getState() != RequestState.FINISHED) {
				player.getRequestManager().cancelRequest();
			} else if (player.getRequestManager().getState() == RequestState.FINISHED) {
				// if(player.getStake().size() > 0) {
				// for(Item item : player.getStake().getContents()) {
				// if(item != null) {
				// player.getInventory().add(item);
				// }
				// }
				// player.getStake().clear();
				// }
				// player.getRequestManager().getAcquaintance().getRequestManager().setRequestType(null);
				// player.getRequestManager().getAcquaintance().getRequestManager().setAcquaintance(null);
				// player.getRequestManager().getAcquaintance().getRequestManager().setState(RequestState.NORMAL);
				// player.getRequestManager().setRequestType(null);
				// player.getRequestManager().setAcquaintance(null);
				// player.getRequestManager().setState(RequestState.NORMAL);
			}
		}
		if (player.getInterfaceState().isWalkableInterface()) {
			return this;
		}
		sendAreaInterface(null, player.getLocation());
		return this;
	}

	/**
	 * Sends all the login packets.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogin() {
		ScriptManager.getScriptManager().call("login", player);
		InterfaceContainerListener inventoryListener = new InterfaceContainerListener(
				player, Inventory.INTERFACE, 0, 93);
		player.getInventory().addListener(inventoryListener);

		InterfaceContainerListener equipmentListener = new InterfaceContainerListener(
				player, Equipment.INTERFACE, 28, 94);
		player.getEquipment().addListener(equipmentListener);
		player.getEquipment().addListener(
				new EquipmentContainerListener(player));
		player.getEquipment().addListener(new WeaponContainerListener(player));
		return this;
	}

	public ActionSender sendLoginInterface() {
		sendString(Constants.MESSAGE_OF_THE_WEEK_SCREEN, 1,
				Constants.MESSAGE_OF_THE_WEEK);
		sendString(
				378,
				15,
				Constants.SERVER_NAME
						+ " Staff will NEVER email you. We use the message centre on this website instead.");
		int messages = 0;
		sendString(378, 16, "You have <col=" + (messages > 0 ? "55ff00" : "")
				+ ">" + messages
				+ "<col=ffea00> unread messages in your<br>message centre.");
		sendString(378, 12, "Welcome to " + Constants.SERVER_NAME);
		sendString(
				378,
				14,
				player.getRecoveryQuestionsLastSet().equalsIgnoreCase("never") ? "You have not yet set any recovery questions. It is <col=ff7000>strongly <col=ffea00>recommended that you do so."
						+ "If you don't you will be <col=ff7000>unable to recover your password <col=ffea00>if you forget it, or it is stolen."
						: "Recovery Questions Last Set:<br>"
								+ player.getRecoveryQuestionsLastSet());
		sendString(378, 17,
				"You do not have a Bank PIN. Please visit a bank if you would like one.");
		String colourForMembers = "<col=ffea00>";
		if (player.getDaysOfMembership() <= 7) {
			colourForMembers = "<col=ff0000>";
		} else if (player.getDaysOfMembership() >= 20) {
			colourForMembers = "<col=55ff00>";
		}
		sendString(
				378,
				19,
				player.isMembers() ? "You have " + colourForMembers
						+ player.getDaysOfMembership()
						+ " <col=ffea00>days of member credit remaining."
						: "You are not a member. Choose to subscribe and you'll get loads of extra benefits and features.");

		String lastLoggedInPrefix = player.getLastLoggedInDays() + " days ago";
		if (player.getLastLoggedIn() - System.currentTimeMillis() < 0x5265c00L) {
			lastLoggedInPrefix = "earlier today";
		} else if (player.getLastLoggedIn() - System.currentTimeMillis() < (0x5265c00L * 2)) {
			lastLoggedInPrefix = "yesterday";
		}
		if (player.getLastLoggedIn() == 0) {
			sendString(378, 13, "This is your first time playing, enjoy "
					+ Constants.SERVER_NAME + "!");
		} else {
			sendString(
					378,
					13,
					"You last logged in <col=ff0000>" + lastLoggedInPrefix
							+ " <col=000000>from: "
							+ player.getLastLoggedInFrom());
		}

		/*
		 * Set these details after, otherwise the first login message will not
		 * work.
		 */
		player.setLastLoggedIn(System.currentTimeMillis());
		player.setLastLoggedInFrom(player.getSession().getAttribute("remote")
				.toString());

		sendWindowPane(Constants.LOGIN_SCREEN);
		sendInterface(Constants.LOGIN_SCREEN, 2, 378, true);
		sendInterface(Constants.LOGIN_SCREEN, 3,
				Constants.MESSAGE_OF_THE_WEEK_SCREEN, true);
		return this;
	}

	/**
	 * Sends the logout packet.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendLogout() {
		ScriptManager.getScriptManager().call("logout", player);
		if (player.getCombatState().getLastHitTimer() > System
				.currentTimeMillis()) {
			sendMessage("You can't logout until 10 seconds after the end of combat.");
			return this;
		}
		player.write(new PacketBuilder(166).toPacket());
		return this;
	}

	/**
	 * Sends the map region load command.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMapRegion() {
		player.setLastKnownRegion(player.getLocation());
		PacketBuilder pb = new PacketBuilder(61, Type.VARIABLE_SHORT);
		boolean forceSend = true;
		if (((player.getLocation().getRegionX() + 6) / 8 == 48 || (player
				.getLocation().getRegionX() + 6) / 8 == 49)
				&& (player.getLocation().getRegionY() + 6) / 8 == 48) {
			forceSend = false;
		}
		if ((player.getLocation().getRegionX() + 6) / 8 == 48
				&& (player.getLocation().getRegionY() + 6) / 8 == 148) {
			forceSend = false;
		}
		pb.putLEShortA(player.getLocation().getLocalY());
		pb.put((byte) player.getLocation().getZ());
		pb.putShort(player.getLocation().getRegionX() + 6);
		for (int xCalc = player.getLocation().getRegionX() / 8; xCalc <= (player
				.getLocation().getRegionX() + 12) / 8; xCalc++) {
			for (int yCalc = player.getLocation().getRegionY() / 8; yCalc <= (player
					.getLocation().getRegionY() + 12) / 8; yCalc++) {
				@SuppressWarnings("unused")
				int region = yCalc + (xCalc << 8); // 1786653352
				if (forceSend || yCalc != 49 && yCalc != 149 && yCalc != 147
						&& xCalc != 50 && (xCalc != 49 || yCalc != 47)) {
					pb.putLEInt(0);
					pb.putLEInt(0);
					pb.putLEInt(0);
					pb.putLEInt(0);
				}
			}
		}
		pb.putShortA(player.getLocation().getLocalX());
		pb.putShort(player.getLocation().getRegionY() + 6);
		player.write(pb.toPacket());
		return this;
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            The message to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendMessage(String message) {
		player.write(new PacketBuilder(209, Type.VARIABLE)
				.putRS2String(message).toPacket());
		return this;
	}

	/**
	 * Sends an NPC's head onto an interface.
	 * 
	 * @param npcId
	 *            The NPC's id.
	 * @param interfaceId
	 *            The interface id.
	 * @param childId
	 *            The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendNPCHead(int npcId, int interfaceId, int childId) {
		player.getSession().write(
				new PacketBuilder(127).putLEShortA(npcId)
						.putInt(interfaceId << 16 | childId).toPacket());
		return this;
	}

	/**
	 * Creates a game object on the players screen.
	 * 
	 * @param obj
	 *            The object to create.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendObject(GameObject obj) {
		if (obj.getId() == -1) {
			return removeObject(obj);
		}
		if (player.getLocation().getZ() != obj.getLocation().getZ()) {
			return this;
		}
		sendArea(obj.getLocation(), 0, 0);
		PacketBuilder spb = new PacketBuilder(188);
		int ot = ((obj.getType() << 2) + (obj.getDirection() & 3));
		spb.put((byte) ot);
		spb.putShortA(obj.getId());
		spb.putByteS((byte) 0);
		player.write(spb.toPacket());
		return this;
	}

	/**
	 * Sends a specific skill.
	 * 
	 * @param skill
	 *            The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendPing() {
		PacketBuilder bldr = new PacketBuilder(238, Type.VARIABLE_SHORT);
		bldr.putInt(pingCount++ > 0xF42400 ? pingCount = 1 : pingCount);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends the player's head onto an interface.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param childId
	 *            The child id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendPlayerHead(int interfaceId, int childId) {
		player.getSession().write(
				new PacketBuilder(22).putInt(interfaceId << 16 | childId)
						.toPacket());
		return this;
	}

	/**
	 * Sends a projectile to a location.
	 * 
	 * @param start
	 *            The starting location.
	 * @param finish
	 *            The finishing location.
	 * @param id
	 *            The graphic id.
	 * @param delay
	 *            The delay before showing the projectile.
	 * @param angle
	 *            The angle the projectile is coming from.
	 * @param speed
	 *            The speed the projectile travels at.
	 * @param startHeight
	 *            The starting height of the projectile.
	 * @param endHeight
	 *            The ending height of the projectile.
	 * @param lockon
	 *            The lockon index of the projectile, so it follows them if they
	 *            move.
	 * @param slope
	 *            The slope at which the projectile moves.
	 * @param radius
	 *            The radius from the centre of the tile to display the
	 *            projectile from.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendProjectile(Location start, Location finish, int id,
			int delay, int angle, int speed, int startHeight, int endHeight,
			int lockon, int slope, int radius) {

		int offsetX = (start.getX() - finish.getX()) * -1;
		int offsetY = (start.getY() - finish.getY()) * -1;
		sendArea(start, -3, -2);

		PacketBuilder bldr = new PacketBuilder(38);
		bldr.put((byte) angle);
		bldr.put((byte) offsetX);
		bldr.put((byte) offsetY);
		bldr.putShort(lockon);
		bldr.putShort(id);
		bldr.put((byte) startHeight);
		bldr.put((byte) endHeight);
		bldr.putShort(delay);
		bldr.putShort(speed);
		bldr.put((byte) slope);
		bldr.put((byte) radius);
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends to the client that this player has receives a private message.
	 * 
	 * @param nameAsLong
	 *            The senders name, as a long.
	 * @param rights
	 *            The rank the sender has.
	 * @param message
	 *            The unpacked message.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRecievedPrivateMessage(long nameAsLong, int rights,
			byte[] message) {
		int messageCounter = player.getPrivateChat().getLastMessageIndex();
		player.write(new PacketBuilder(117, Type.VARIABLE)
				.putLong(nameAsLong)
				.putShort(1)
				.put(new byte[] { (byte) ((messageCounter << 16) & 0xFF),
						(byte) ((messageCounter << 8) & 0xFF),
						(byte) (messageCounter & 0xFF) }).put((byte) rights)
				.put(message).toPacket());
		return this;
	}

	/**
	 * Resets the players following target.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendResetFollowing() {
		player.write(new PacketBuilder(1).toPacket());
		return this;
	}

	/**
	 * Sends the player's energy.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRunEnergy() {
		player.write(new PacketBuilder(42).put(
				(byte) player.getWalkingQueue().getEnergy()).toPacket());
		return this;
	}

	/**
	 * Sends a clientscript to the client.
	 * 
	 * @param id
	 *            The id.
	 * @param params
	 *            Any parameters in the scrips.
	 * @param types
	 *            The script types
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendRunScript(int id, Object[] params, String types) {
		PacketBuilder bldr = new PacketBuilder(237, Type.VARIABLE_SHORT);
		bldr.putRS2String(types);
		if (params.length > 0) {
			int j = 0;
			for (int i = types.length() - 1; i >= 0; i--, j++) {
				if (types.charAt(i) == 115) {
					bldr.putRS2String((String) params[j]);
				} else {
					bldr.putInt((Integer) params[j]);
				}
			}
		}
		bldr.putInt(id);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends to the client that this player has sent a private message.
	 * 
	 * @param nameAsLong
	 *            The recepient's name, as a long.
	 * @param unpacked
	 *            The unpacked message.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSentPrivateMessage(long nameAsLong, byte[] message) {
		player.write(new PacketBuilder(57, Type.VARIABLE).putLong(nameAsLong)
				.put(message).toPacket());
		return this;
	}

	/**
	 * Sends a specific sidebar interface.
	 * 
	 * @param icon
	 *            The sidebar icon.
	 * @param interfaceId
	 *            The interface id.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterface(int icon, int interfaceId) {
		if (icon == 99) {
			player.getInterfaceState().setOpenAutocastType(-1);
		}
		return sendInterface(Constants.MAIN_WINDOW, icon, interfaceId, true);
	}

	/**
	 * Sends all the sidebar interfaces.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSidebarInterfaces() {
		final int[] icons = Constants.SIDEBAR_INTERFACES[0];
		final int[] interfaces = Constants.SIDEBAR_INTERFACES[1];
		for (int i = 0; i < icons.length; i++) {
			if (i == 7) {
				sendSidebarInterface(icons[i],
						SpellBook.forId(player.getCombatState().getSpellBook())
								.getInterfaceId());
			} else {
				sendSidebarInterface(icons[i], interfaces[i]);
			}
		}
		return this;
	}

	/**
	 * Sends a specific skill.
	 * 
	 * @param skill
	 *            The skill to send.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkill(int skill) {
		PacketBuilder bldr = new PacketBuilder(196);
		bldr.put((byte) skill);
		bldr.putInt2((int) player.getSkills().getExperience(skill));
		if (skill == Skills.PRAYER) {
			bldr.put((byte) (Math.ceil(player.getSkills().getPrayerPoints())));
		} else {
			bldr.put((byte) player.getSkills().getLevel(skill));
		}
		player.getSession().write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends the player's skills.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendSkills() {
		for (int i = 0; i < Skills.SKILL_COUNT; i++) {
			sendSkill(i);
		}
		return this;
	}

	/**
	 * Sends a string.
	 * 
	 * @param id
	 *            The interface id.
	 * @param string
	 *            The string.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendString(int interfaceId, int child, String string) {
		PacketBuilder bldr = new PacketBuilder(231, Type.VARIABLE_SHORT);
		bldr.putInt2(interfaceId << 16 | child);
		bldr.putRS2String(string);
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a packet to update a single item.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param slot
	 *            The slot.
	 * @param item
	 *            The item.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItem(int interfaceId, int childId, int type,
			int slot, Item item) {
		PacketBuilder bldr = new PacketBuilder(187, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		bldr.putSmart(slot);
		if (item != null) {
			bldr.putShort(item.getId() + 1);
			int count = item.getCount();
			if (count > 254) {
				bldr.put((byte) 255);
				bldr.putInt(count);
			} else {
				bldr.put((byte) count);
			}
		} else {
			bldr.putShort(0);
			bldr.put((byte) -1);
		}
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a packet to update multiple (but not all) items.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param slots
	 *            The slots.
	 * @param items
	 *            The item array.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItems(int interfaceId, int childId, int type,
			int[] slots, Item[] items) {
		PacketBuilder bldr = new PacketBuilder(187, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		for (int slot : slots) {
			Item item = items[slot];
			bldr.putSmart(slot);
			if (item != null) {
				bldr.putShort(item.getId() + 1);
				int count = item.getCount();
				if (count > 254) {
					bldr.put((byte) 255);
					bldr.putInt(count);
				} else {
					bldr.put((byte) count);
				}
			} else {
				bldr.putShort(0);
				bldr.put((byte) -1);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	/**
	 * Sends a packet to update a group of items.
	 * 
	 * @param interfaceId
	 *            The interface id.
	 * @param items
	 *            The items.
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender sendUpdateItems(int interfaceId, int childId, int type,
			Item[] items) {
		PacketBuilder bldr = new PacketBuilder(119, Type.VARIABLE_SHORT);
		bldr.putInt(interfaceId << 16 | childId);
		bldr.putShort(type);
		bldr.putShort(items.length);
		for (Item item : items) {
			if (item != null) {
				int count = item.getCount();
				if (count > 254) {
					bldr.putByteC((byte) 255);
					bldr.putLEInt(count);
				} else {
					bldr.putByteC((byte) count);
				}
				bldr.putLEShortA(item.getId() + 1);
			} else {
				bldr.putByteC((byte) 0);
				bldr.putLEShortA(0);
			}
		}
		player.write(bldr.toPacket());
		return this;
	}

	public ActionSender sendWalkableInterface(int interfaceId) {
		if (interfaceId == -1) {
			player.getInterfaceState().interfaceClosed();
			player.getSession().write(
					new PacketBuilder(174)
							.putInt(Constants.MAIN_WINDOW << 16
									| Constants.GAME_WINDOW).toPacket()); // main
																			// game
																			// screen
			return removeInterface();
		}
		return sendInterface(Constants.MAIN_WINDOW, Constants.GAME_WINDOW,
				interfaceId, true);
	}

	/**
	 * Sends the 'Window Pane'
	 * 
	 * @param interfaceId
	 *            the interfaceId to make the window pane
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendWindowPane(int interfaceId) {
		player.write(new PacketBuilder(251).putLEShort(interfaceId).toPacket());
		return this;
	}

	/**
	 * Sends the player's accept aid flag.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateAcceptAidConfig() {
		sendConfig(427, player.getSettings().isAcceptingAid() ? 1 : 0);
		sendInterfaceConfig(261, 59, !player.getSettings().isAcceptingAid());
		sendInterfaceConfig(261, 60, player.getSettings().isAcceptingAid());
		return this;
	}

	/**
	 * Sends the player's auto retaliate config.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateAutoRetaliateConfig() {
		return sendConfig(172, player.getSettings().isAutoRetaliating() ? 0 : 1);
	}

	/**
	 * Sends the player's banking configs.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateBankConfig() {
		sendConfig(304, player.getSettings().isSwapping() ? 0 : 1);
		sendConfig(115, player.getSettings().isWithdrawingAsNotes() ? 1 : 0);
		return this;
	}

	/**
	 * Sends the player's brightness setting.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateBrightnessConfig() {
		return sendConfig(166, player.getSettings().getBrightnessSetting());
	}

	/**
	 * Sends the player's two mouse buttons flag.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateChatEffectsConfig() {
		sendConfig(171, player.getSettings().chatEffects() ? 0 : 1);
		sendInterfaceConfig(261, 53, !player.getSettings().chatEffects());
		sendInterfaceConfig(261, 54, player.getSettings().chatEffects());
		return this;
	}

	/**
	 * Sends the player's two mouse buttons flag.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateMouseButtonsConfig() {
		sendConfig(170, player.getSettings().twoMouseButtons() ? 0 : 1);
		sendInterfaceConfig(261, 57, !player.getSettings().twoMouseButtons());
		sendInterfaceConfig(261, 58, player.getSettings().twoMouseButtons());
		return this;
	}

	/**
	 * Updates the player's running state.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateRunningConfig() {
		sendConfig(173, player.getWalkingQueue().isRunningToggled() ? 1 : 0);
		sendInterfaceConfig(261, 51, !player.getWalkingQueue()
				.isRunningToggled());
		sendInterfaceConfig(261, 52, player.getWalkingQueue()
				.isRunningToggled());
		return this;
	}

	/**
	 * Sends the player's special bar configurations.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateSpecialConfig() {
		sendConfig(300, player.getCombatState().getSpecialEnergy() * 10);
		sendConfig(301, player.getCombatState().isSpecialOn() ? 1 : 0);
		return this;
	}

	/**
	 * Sends the player's split private chat flag.
	 * 
	 * @return The action sender instance, for chaining.
	 */
	public ActionSender updateSplitPrivateChatConfig() {
		// if(player.getSettings().splitPrivateChat()) {
		// player.write(new PacketBuilder(237,
		// Type.VARIABLE_SHORT).putRS2String("s").putInt(83).toPacket());
		// //sends to the client that we split the chat
		// }
		sendRunScript(83, new Object[0], "");
		sendConfig(287, player.getSettings().splitPrivateChat() ? 1 : 0);
		sendInterfaceConfig(261, 55, !player.getSettings().splitPrivateChat());
		sendInterfaceConfig(261, 56, player.getSettings().splitPrivateChat());
		return this;
	}
}
