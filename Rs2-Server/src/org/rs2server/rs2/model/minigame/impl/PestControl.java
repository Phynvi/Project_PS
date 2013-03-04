/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.minigame.impl;

import java.util.ArrayList;
import java.util.List;
import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.CombatNPCDefinition.Skill;
import org.rs2server.rs2.model.Mob.InteractionMode;
import org.rs2server.rs2.model.boundary.Boundary;
import org.rs2server.rs2.model.boundary.BoundaryManager;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

/**
 * 
 * @author Steve
 * 
 */
public class PestControl extends AbstractMinigame {

	/**
	 * The minimum amount of players there must be in the waiting room +
	 * participating before a game starts.
	 */
	public static final int MINIMUM_SIZE = 1;
	/**
	 * The game started flag.
	 */
	private boolean gameStarted = false;
	/**
	 * The list of players in the waiting room.
	 */
	private List<Player> waitingRoom;
	/**
	 * The list of participants in this instance.
	 */
	private List<Player> participants;
	/**
	 * The list of participants in this instance.
	 */
	private List<NPC> spawnedNPCs;
	private static final int PURPLE_PORTAL = 6142;
	private static final int BLUE_PORTAL = 6143;
	private static final int YELLOW_PORTAL = 6144;
	private static final int RED_PORTAL = 6145;
	private NPC purplePortal = new NPC(NPCDefinition.forId(PURPLE_PORTAL),
			Location.create(2628, 2591, 0), Location.create(2628, 2591, 0),
			Location.create(2628, 2591, 0), 0);
	private NPC redPortal = new NPC(NPCDefinition.forId(RED_PORTAL),
			Location.create(2645, 2569, 0), Location.create(2645, 2569, 0),
			Location.create(2645, 2569, 0), 0);
	private NPC yellowPortal = new NPC(NPCDefinition.forId(YELLOW_PORTAL),
			Location.create(2669, 2570, 0), Location.create(2669, 2570, 0),
			Location.create(2669, 2570, 0), 0);
	private NPC bluePortal = new NPC(NPCDefinition.forId(BLUE_PORTAL),
			Location.create(2680, 2588, 0), Location.create(2680, 2588, 0),
			Location.create(2680, 2588, 0), 0);
	private NPC voidKnight = new NPC(NPCDefinition.forId(3782),
			Location.create(2656, 2592, 0), Location.create(2656, 2592, 0),
			Location.create(2656, 2592, 0), 0);
	private static int[] pestMonsters = { 3727, 3728, 3729, 3730, 3731, 3732,
			3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741, 3742, 3743,
			3744, 3745, 3746, 3747, 3748, 3749, 3750, 3751, 3752, 3753, 3754,
			3755, 3756, 3757, 3758, 3759, 3760, 3761, 3762, 3763, 3764, 3765,
			3766, 3767, 3768, 3769, 3770, 3771, 3772, 3773, 3774, 3775, 3776 };

	public static int randomPestMonster() {
		return pestMonsters[(int) (Math.random() * pestMonsters.length)];
	}

	public PestControl() {
		init(); // Begins initialization
		BoundaryManager.addBoundary(Boundary.create(
				"Pest Control Waiting Room", Location.create(2660, 2638, 0),
				Location.create(2663, 2643, 0))); // waiting area
		BoundaryManager
				.addBoundary(Boundary.create("Pest Control",
						Location.create(2622, 2554, 0),
						Location.create(2691, 2629, 0))); // main area
		BoundaryManager.addBoundary(Boundary.create("MultiCombat",
				getBoundary().getBottomLeft(), getBoundary().getTopRight())); // main
																				// area
		this.participants = new ArrayList<Player>();
		this.waitingRoom = new ArrayList<Player>();
		this.spawnedNPCs = new ArrayList<NPC>();
		bluePortal.setLocation(Location.create(0, 0, 0));
		purplePortal.setLocation(Location.create(0, 0, 0));
		redPortal.setLocation(Location.create(0, 0, 0));
		yellowPortal.setLocation(Location.create(0, 0, 0));
		voidKnight.setLocation(Location.create(0, 0, 0));
	}

	public void addPariticpant(Player participant) {
		this.participants.add(participant);
	}

	public void addWaitingPlayer(Player participant) {
		this.waitingRoom.add(participant);
		participant.setMinigame(this);
		participant.getActionSender().sendString(407, 14, "Pest Points: "); // +
																			// participant.getPestPoints());
		participant.getActionSender().sendString(407, 16, "Novice");
		for (Player p : waitingRoom) {
			p.getActionSender().sendString(407, 12,
					"Players Ready: " + waitingRoom.size());
		}
		if (waitingRoom.size() == MINIMUM_SIZE) {
			World.getWorld().submit(new Tickable(10) {

				int departure = 20;

				@Override
				public void execute() {
					if (waitingRoom.size() < MINIMUM_SIZE) {
						for (Player p : waitingRoom) {
							p.getActionSender().sendString(407, 11,
									"Next Departure: Need more players");
						}
						this.stop();
						return;
					}
					for (Player p : waitingRoom) {
						int mins = Math.round(departure / 100);
						if (mins == 0) {
							mins = 1;
						}
						p.getActionSender().sendString(407, 11,
								"Next Departure: " + mins + "min");
					}
					departure -= 10;
					if (departure == 0) {
						start();
						this.stop();
					}
				}
			});

			if (waitingRoom.size() < MINIMUM_SIZE) {
				for (Player p : waitingRoom) {
					p.getActionSender().sendString(407, 11,
							"Next Departure: Need more players");
				}
				return;
			}
			for (Player p : waitingRoom) {
				int mins = 1;
				p.getActionSender().sendString(407, 11,
						"Next Departure: " + mins + "min");
			}
		} else if (waitingRoom.size() < MINIMUM_SIZE) {
			for (Player p : waitingRoom) {
				p.getActionSender().sendString(407, 11,
						"Next Departure: Need more players");
			}
		}
	}

	@Override
	public boolean attackMobHook(Player player, Mob victim) {
		if (!gameStarted) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deathHook(Player player) {
		Location startLocation = getStartLocation();
		player.setLocation(startLocation);
		player.setTeleportTarget(startLocation);
		return true;
	}

	@Override
	public void end() {
		gameStarted = false;
		for (NPC npc : spawnedNPCs) {
			World.getWorld().unregister(npc);
		}
		spawnedNPCs.clear();
		World.getWorld().unregister(bluePortal);
		World.getWorld().unregister(yellowPortal);
		World.getWorld().unregister(redPortal);
		World.getWorld().unregister(purplePortal);
		World.getWorld().unregister(voidKnight);
		bluePortal.setLocation(Location.create(0, 0, 0));
		purplePortal.setLocation(Location.create(0, 0, 0));
		redPortal.setLocation(Location.create(0, 0, 0));
		yellowPortal.setLocation(Location.create(0, 0, 0));
		voidKnight.setLocation(Location.create(0, 0, 0));
		/*
		 * yellowPortal.resetVariousInformation();
		 * redPortal.resetVariousInformation();
		 * bluePortal.resetVariousInformation();
		 * purplePortal.resetVariousInformation();
		 * voidKnight.resetVariousInformation();
		 */
		for (int i = 0; i < participants.size(); i++) {
			Player p = participants.get(i);
			p.setLocation(Location.create(2657, 2639, 0));
			p.setTeleportTarget(Location.create(2657, 2639, 0));
			if (p.getCombatState().isDead()
					|| p.getSkills().getLevel(Skills.HITPOINTS) <= 0) {
				p.getCombatState().setDead(false);
			}
			p.resetVariousInformation();
			p.setMinigame(null);
			// p.setPestPoints(p.getPestPoints() + 2);
		}
		participants.clear();
	}

	@Override
	public Boundary getBoundary() {
		return Boundary.create(getName(), Location.create(2622, 2554, 0),
				Location.create(2691, 2629, 0));
	}

	@Override
	public Tickable getGameCycle() {
		return new Tickable(3) {

			@Override
			public void execute() {
				if (gameStarted) {
					if (voidKnight.getCombatState().isDead()
							|| !BoundaryManager.isWithinBoundary(
									voidKnight.getLocation(), "Pest Control")) {
						end();
						return;
					}
					if (portalsDestroyed()) {
						end();
						return;
					}
					if (Misc.random(6) == 0) {
						int[] shifters = { 3732, 3733, 3734, 3735, 3736, 3737,
								3738, 3739, 3740, 3741 };
						List<NPC> shifter = new ArrayList<NPC>();
						for (NPC npc : spawnedNPCs) {
							for (int i : shifters) {
								if (npc.getDefinition().getId() == i
										&& TileControl.calculateDistance(npc,
												voidKnight) >= 6) {
									shifter.add(npc);
								}
							}
						}
						if (shifter.size() > 0) {
							int rand = Misc.random(shifter.size() - 1);
							NPC npc = shifter.get(rand);
							Location l = Location.create(2654 + Misc.random(3),
									2593 + Misc.random(3), 0);
							npc.setTeleportTarget(l);
							npc.setLocation(l);
							npc.resetInteractingEntity();
						}
					}
					for (NPC npc : spawnedNPCs) {
						if (npc.getCombatDefinition() != null) {
							if (TileControl.calculateDistance(npc, voidKnight) <= 16
									&& npc.getInteractingEntity() == null) {
								if (TileControl.calculateDistance(
										npc.getLocation(),
										npc.getSpawnLocation()) > 3) {
									npc.getCombatState().startAttacking(
											voidKnight, false);
								}
							}
							if (npc.getDefinition().getId() >= 3747
									&& npc.getDefinition().getId() <= 3751) {
								if (TileControl.calculateDistance(npc,
										redPortal) <= 4) {
									int health = redPortal.getSkills()
											.getLevel(Skills.HITPOINTS);
									int rand = Misc.random(70);
									if (health + rand > redPortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										rand = redPortal.getCombatDefinition()
												.getSkills()
												.get(Skill.HITPOINTS)
												- health;
									}
									if (redPortal.getSkills().getLevel(
											Skills.HITPOINTS) < redPortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										redPortal.getSkills()
												.setLevel(Skills.HITPOINTS,
														health + rand);
									}
									npc.face(redPortal.getLocation());
								} else if (TileControl.calculateDistance(npc,
										bluePortal) <= 4) {
									int health = bluePortal.getSkills()
											.getLevel(Skills.HITPOINTS);
									int rand = Misc.random(70);
									if (health + rand > bluePortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										rand = bluePortal.getCombatDefinition()
												.getSkills()
												.get(Skill.HITPOINTS)
												- health;
									}
									if (bluePortal.getSkills().getLevel(
											Skills.HITPOINTS) < bluePortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										bluePortal.getSkills()
												.setLevel(Skills.HITPOINTS,
														health + rand);
									}
									npc.face(bluePortal.getLocation());
								} else if (TileControl.calculateDistance(npc,
										purplePortal) <= 4) {
									int health = purplePortal.getSkills()
											.getLevel(Skills.HITPOINTS);
									int rand = Misc.random(70);
									if (health + rand > purplePortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										rand = purplePortal
												.getCombatDefinition()
												.getSkills()
												.get(Skill.HITPOINTS)
												- health;
									}
									if (purplePortal.getSkills().getLevel(
											Skills.HITPOINTS) < purplePortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										purplePortal.getSkills()
												.setLevel(Skills.HITPOINTS,
														health + rand);
									}
									npc.face(purplePortal.getLocation());
								} else if (TileControl.calculateDistance(npc,
										yellowPortal) <= 4) {
									int health = yellowPortal.getSkills()
											.getLevel(Skills.HITPOINTS);
									int rand = Misc.random(70);
									if (health + rand > yellowPortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										rand = yellowPortal
												.getCombatDefinition()
												.getSkills()
												.get(Skill.HITPOINTS)
												- health;
									}
									if (yellowPortal.getSkills().getLevel(
											Skills.HITPOINTS) < yellowPortal
											.getCombatDefinition().getSkills()
											.get(Skill.HITPOINTS)) {
										yellowPortal.getSkills()
												.setLevel(Skills.HITPOINTS,
														health + rand);
									}
									npc.face(yellowPortal.getLocation());
								} else if (TileControl.calculateDistance(npc,
										redPortal) <= 10) {
									npc.setInteractingEntity(
											InteractionMode.FOLLOW, redPortal);
								} else if (TileControl.calculateDistance(npc,
										bluePortal) <= 10) {
									npc.setInteractingEntity(
											InteractionMode.FOLLOW, bluePortal);
								} else if (TileControl.calculateDistance(npc,
										purplePortal) <= 10) {
									npc.setInteractingEntity(
											InteractionMode.FOLLOW,
											purplePortal);
								} else if (TileControl.calculateDistance(npc,
										yellowPortal) <= 10) {
									npc.setInteractingEntity(
											InteractionMode.FOLLOW,
											yellowPortal);
								}
							}
						}
					}
					int randomPortal = Misc.random(3);
					switch (randomPortal) {
					case 0:
						spawnWestMonster();
						break;
					case 1:
						spawnSouthWestMonster();
						break;
					case 2:
						spawnSouthEastMonster();
						break;
					case 3:
						spawnEastMonster();
						break;
					}
					for (Player p : participants) {
						if (!BoundaryManager.isWithinBoundary(p.getLocation(),
								"Pest Control")) {
							participants.remove(p);
							continue;
						}
						if (World.getWorld().getNPCs().contains(purplePortal)) {
							p.getActionSender()
									.sendString(
											408,
											13,
											""
													+ purplePortal
															.getSkills()
															.getLevel(
																	Skills.HITPOINTS));
						}
						if (World.getWorld().getNPCs().contains(bluePortal)) {
							p.getActionSender().sendString(
									408,
									14,
									""
											+ bluePortal.getSkills().getLevel(
													Skills.HITPOINTS));
						}
						if (World.getWorld().getNPCs().contains(yellowPortal)) {
							p.getActionSender()
									.sendString(
											408,
											15,
											""
													+ yellowPortal
															.getSkills()
															.getLevel(
																	Skills.HITPOINTS));
						}
						if (World.getWorld().getNPCs().contains(redPortal)) {
							p.getActionSender().sendString(
									408,
									16,
									""
											+ redPortal.getSkills().getLevel(
													Skills.HITPOINTS));
						}
						p.getActionSender().sendString(
								408,
								1,
								""
										+ voidKnight.getSkills().getLevel(
												Skills.HITPOINTS));
					}
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
		return "Pest Control";
	}

	@Override
	public List<Player> getParticipants() {
		return participants;
	}

	@Override
	public Location getStartLocation() {
		int randomX = Misc.random(3);
		int randomY = Misc.random(5);
		return Location.create(2656 + randomX, 2609 + randomY, 0);
	}

	@Override
	public void killHook(Player player, Mob victim) {
		super.killHook(player, victim);
		if (victim.isNPC()) {
			NPC npc = (NPC) victim;
			int id = npc.getDefinition().getId();
			switch (id) {
			case RED_PORTAL:
				World.getWorld().unregister(redPortal);
				break;
			case PURPLE_PORTAL:
				World.getWorld().unregister(purplePortal);
				break;
			case YELLOW_PORTAL:
				World.getWorld().unregister(yellowPortal);
				break;
			case BLUE_PORTAL:
				World.getWorld().unregister(bluePortal);
				break;
			case 3782:
				World.getWorld().unregister(voidKnight);
				break;
			}
			for (int i : pestMonsters) {
				if (id == i) {
					World.getWorld().unregister(npc);
					spawnedNPCs.remove(npc);
				}
			}
			if (id >= 3727 && id <= 3731) {
				for (Player p : participants) {
					if (TileControl.calculateDistance(npc, p) == 1) {
						p.inflictDamage(
								new Hit(Misc.random(npc.getCombatDefinition()
										.getMaxHit() * 2)), victim);
					}
				}
				for (NPC p : spawnedNPCs) {
					if (TileControl.calculateDistance(npc, p) == 1) {
						p.inflictDamage(
								new Hit(Misc.random(npc.getCombatDefinition()
										.getMaxHit() * 2)), victim);
					}
				}
			}
		}
		// if (getParticipants().contains(player) && getParticipants().size() <=
		// 1) { //only us left
		// winGame(player);
		// } else {
		// for (Player participant : getParticipants()) {
		// participant.getActionSender().sendString(373, 1, "Foes Remaining: " +
		// (getParticipants().size() - 1));
		// }
		// }
	}

	@Override
	public void movementHook(Player player) {
		if (getParticipants().contains(player)) {
			super.movementHook(player);
		} else if (waitingRoom.contains(player)
				&& !BoundaryManager.isWithinBoundaryNoZ(player.getLocation(),
						"Pest Control Waiting Room")) {
			this.waitingRoom.remove(player);
			for (Player p : waitingRoom) {
				p.getActionSender().sendString(407, 12,
						"Players Ready: " + waitingRoom.size());
			}
		}
	}

	private boolean portalsDestroyed() {
		return !World.getWorld().getNPCs().contains(yellowPortal)
				&& !World.getWorld().getNPCs().contains(redPortal)
				&& !World.getWorld().getNPCs().contains(bluePortal)
				&& !World.getWorld().getNPCs().contains(purplePortal);
	}

	@Override
	public void quit(final Player player) {
		if (waitingRoom.contains(player)) {
			waitingRoom.remove(player);
			for (Player p : waitingRoom) {
				p.getActionSender().sendString(407, 12,
						"Players Ready: " + waitingRoom.size());
			}
		} else if (getParticipants().contains(player)) {
			player.resetVariousInformation();
			getParticipants().remove(player);
		}
		player.setLocation(Location.create(2657, 2639, 0));
		player.setTeleportTarget(Location.create(2657, 2639, 0));
	}

	private void removeShields() {
		for (Player p : participants) {
			p.getActionSender().sendInterfaceConfig(408, 18, true);
			p.getActionSender().sendInterfaceConfig(408, 20, true);
			p.getActionSender().sendInterfaceConfig(408, 22, true);
			p.getActionSender().sendInterfaceConfig(408, 24, true);
		}
	}

	public void removeWaitingPlayer(Player participant) {
		this.waitingRoom.remove(participant);
		participant.setMinigame(null);
		for (Player p : waitingRoom) {
			p.getActionSender().sendString(407, 12,
					"Players Ready: " + waitingRoom.size());
		}
	}

	private void resetNPCs() {
		purplePortal = new NPC(NPCDefinition.forId(PURPLE_PORTAL),
				Location.create(2628, 2591, 0), Location.create(2628, 2591, 0),
				Location.create(2628, 2591, 0), 0);
		redPortal = new NPC(NPCDefinition.forId(RED_PORTAL), Location.create(
				2645, 2569, 0), Location.create(2645, 2569, 0),
				Location.create(2645, 2569, 0), 0);
		yellowPortal = new NPC(NPCDefinition.forId(YELLOW_PORTAL),
				Location.create(2669, 2570, 0), Location.create(2669, 2570, 0),
				Location.create(2669, 2570, 0), 0);
		bluePortal = new NPC(NPCDefinition.forId(BLUE_PORTAL), Location.create(
				2680, 2588, 0), Location.create(2680, 2588, 0),
				Location.create(2680, 2588, 0), 0);
		voidKnight = new NPC(NPCDefinition.forId(3782), Location.create(2656,
				2592, 0), Location.create(2656, 2592, 0), Location.create(2656,
				2592, 0), 0);
		bluePortal.setLocation(bluePortal.getSpawnLocation());
		purplePortal.setLocation(purplePortal.getSpawnLocation());
		redPortal.setLocation(redPortal.getSpawnLocation());
		yellowPortal.setLocation(yellowPortal.getSpawnLocation());
		voidKnight.setLocation(voidKnight.getSpawnLocation());
	}

	private void spawnEastMonster() {
		int random = randomPestMonster(); // e
		Location[] spawns = new Location[] { Location.create(2676, 2592, 0),
				Location.create(2675, 2595, 0) };
		NPC npc = new NPC(NPCDefinition.forId(random), spawns[Misc.random(1)],
				Location.create(2671, 2582, 0), Location.create(2685, 2599, 0),
				0);
		World.getWorld().register(npc);
		spawnedNPCs.add(npc);
	}

	private void spawnSouthEastMonster() {
		int random = randomPestMonster(); // se
		Location[] spawns = new Location[] { Location.create(2673, 2575, 0),
				Location.create(2664, 2578, 0) };
		NPC npc = new NPC(NPCDefinition.forId(random), spawns[Misc.random(1)],
				Location.create(2656, 2562, 0), Location.create(2684, 2579, 0),
				0);
		World.getWorld().register(npc);
		spawnedNPCs.add(npc);
	}

	private void spawnSouthWestMonster() {
		int random = randomPestMonster(); // sw
		Location[] spawns = new Location[] { Location.create(2646, 2575, 0),
				Location.create(2655, 2578, 0) };
		NPC npc = new NPC(NPCDefinition.forId(random), spawns[Misc.random(1)],
				Location.create(2637, 2560, 0), Location.create(2663, 2584, 0),
				0);
		World.getWorld().register(npc);
		spawnedNPCs.add(npc);
	}

	private void spawnWestMonster() {
		int random = randomPestMonster(); // w
		Location[] spawns = new Location[] { Location.create(2632, 2593, 0),
				Location.create(2639, 2593, 0) };
		NPC npc = new NPC(NPCDefinition.forId(random), spawns[Misc.random(1)],
				Location.create(2625, 2581, 0), Location.create(2642, 2598, 0),
				0);
		World.getWorld().register(npc);
		spawnedNPCs.add(npc);
	}

	@Override
	public void start() {
		super.start();
		for (Player player : waitingRoom) {
			player.resetVariousInformation();
			player.setTeleportTarget(getStartLocation());
			participants.add(player);
		}
		waitingRoom.clear();
		this.resetNPCs();
		purplePortal.getCombatState().setCanMove(false);
		redPortal.getCombatState().setCanMove(false);
		yellowPortal.getCombatState().setCanMove(false);
		bluePortal.getCombatState().setCanMove(false);
		voidKnight.getCombatState().setCanMove(false);
		World.getWorld().register(purplePortal);
		World.getWorld().register(redPortal);
		World.getWorld().register(bluePortal);
		World.getWorld().register(yellowPortal);
		World.getWorld().register(voidKnight);
		bluePortal.setLocation(bluePortal.getSpawnLocation());
		purplePortal.setLocation(purplePortal.getSpawnLocation());
		redPortal.setLocation(redPortal.getSpawnLocation());
		yellowPortal.setLocation(yellowPortal.getSpawnLocation());
		voidKnight.setLocation(voidKnight.getSpawnLocation());
		for (Player p : participants) {
			p.getActionSender().sendString(408, 13,
					"" + purplePortal.getSkills().getLevel(Skills.HITPOINTS));
			p.getActionSender().sendString(408, 14,
					"" + bluePortal.getSkills().getLevel(Skills.HITPOINTS));
			p.getActionSender().sendString(408, 15,
					"" + yellowPortal.getSkills().getLevel(Skills.HITPOINTS));
			p.getActionSender().sendString(408, 16,
					"" + redPortal.getSkills().getLevel(Skills.HITPOINTS));
			p.getActionSender().sendString(408, 1,
					"" + voidKnight.getSkills().getLevel(Skills.HITPOINTS));
		}
		removeShields();
		gameStarted = true;
		for (int i = 0; i < 7; i++) {
			this.spawnEastMonster();
			this.spawnWestMonster();
			this.spawnSouthEastMonster();
			this.spawnSouthWestMonster();
		}
	}
}
