package org.rs2server.rs2.model.minigame.impl;

import java.util.List;
import java.util.logging.Logger;

import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.boundary.Boundary;
import org.rs2server.rs2.model.boundary.BoundaryManager;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Josh Mai
 * 
 */
public class CastleWars extends AbstractMinigame {

	/**
	 * Logger instance.
	 */
	private static final Logger logger = Logger.getLogger(FightPits.class
			.getName());

	/**
	 * The minimum amount of players there must be in the waiting room +
	 * participating before a game starts.
	 */
	public static final int MINIMUM_SIZE = 3;

	/**
	 * The game started flag.
	 */
	private boolean gameStarted = false;

	/**
	 * The winner string.
	 */
	private String winner = "No Winner!";

	/**
	 * The list of players in the waiting room.
	 */
	private List<Player> waitingPlayers, participants, gamePlayers,
			zamorakPlayers, saradominPlayers, zamorakWaiters, saradominWaiters;

	public Location MAIN_LOBBY = Location.create(2441, 3090),
			ZAMORAK_ROOM = Location.create(2422, 9523),
			SARADOMIN_ROOM = Location.create(2382, 9489),
			ZAMORAK_SPAWN = Location.create(2372, 3130, 1),
			SARADOMIN_SPAWN = Location.create(2427, 3077, 1);

	@Override
	public boolean attackMobHook(Player player, Mob victim) {
		return true;
	}

	@Override
	public boolean deathHook(Player player) {
		return false;
	}

	@Override
	public void end() {
		for (Player participant : getParticipants()) {
			participant.setTeleportTarget(getStartLocation());
			participant.resetVariousInformation();
		}
		if (getGameCycle() != null) {
			getGameCycle().stop();
		}
	}

	@Override
	public Boundary getBoundary() {
		return Boundary.create(getName(), Location.create(2374, 3062, 0),
				Location.create(2423, 3138, 0));
	}

	@Override
	public Tickable getGameCycle() {
		return new Tickable(10) {
			@Override
			public void execute() {
				if (!gameStarted
						&& (saradominWaiters.size() + zamorakWaiters.size()) >= MINIMUM_SIZE
						&& participants.size() <= 1) {
					start();
				}
			}
		};
	}

	@Override
	public ItemSafety getItemSafety() {
		return ItemSafety.SAFE;
	}

	@Override
	public String getName() {
		return "Castle Wars";
	}

	@Override
	public List<Player> getParticipants() {
		return participants;
	}

	@Override
	public Location getStartLocation() {
		return null;
	}

	/**
	 * Initializes the minigames instance.
	 */
	@Override
	public void init() {
		logger.info("Initializing Castle Wars...");
		/**
		 * We add safe zones otherwise players would lose items by default.
		 */
		if (getItemSafety() == ItemSafety.SAFE) {
			BoundaryManager
					.addBoundary(Boundary.create("SafeZone", getBoundary()
							.getBottomLeft(), getBoundary().getTopRight()));
		}
		BoundaryManager.addBoundary(getBoundary());
		if (getGameCycle() != null) {
			World.getWorld().submit(getGameCycle());
		}
	}

	@Override
	public void killHook(Player player, Mob victim) {
	}

	@Override
	public void movementHook(Player player) {
		if (!BoundaryManager.isWithinBoundaryNoZ(player.getLocation(),
				getBoundary())) {
			quit(player);
		}
	}

	@Override
	public void quit(Player player) {
		player.setMinigame(null);
		player.setAttribute("temporaryHeight", null);
		player.setTeleportTarget(getStartLocation());
		player.setLocation(getStartLocation());
		player.resetVariousInformation();
		if (getParticipants() != null) {
			getParticipants().remove(player);
			if (getParticipants().size() < 1) {
				end();
			}
		}
	}

	@Override
	public void start() {
	}

}
