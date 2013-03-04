package org.rs2server.rs2.model.minigame.impl;

import java.util.ArrayList;
import java.util.List;

import org.rs2server.rs2.model.DialogueManager;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.NPCDefinition;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.WalkingQueue;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.boundary.Boundary;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

public class FightCaves extends AbstractMinigame {

	private enum Wave {
		ONE(0, new NPC[] { new NPC(NPCDefinition.forId(82), Location.create(
				2415, 5108, 0), null, null, WalkingQueue.NORTH) }), TWO(1,
				new NPC[] { new NPC(NPCDefinition.forId(82), Location.create(
						2415, 5108, 0), null, null, WalkingQueue.NORTH) });
		/**
		 * The list of waves of npcs.
		 */
		public static List<Wave> waves = new ArrayList<Wave>();

		/**
		 * Populates the wave list.
		 */
		static {
			for (Wave wave : Wave.values()) {
				waves.add(wave);
			}
		}

		public static Wave forId(int wave) {
			if (wave >= waves.size()) {
				return null;
			}
			return waves.get(wave);
		}

		/**
		 * The id of this wave.
		 */
		private int id;

		/**
		 * The npc spawned in this wave.
		 */
		private NPC[] npcs;

		private Wave(int id, NPC[] npcs) {
			this.id = id;
			this.npcs = npcs;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the npc
		 */
		public NPC[] getNPCs() {
			return npcs;
		}
	}

	/**
	 * The list of participants in this instance.
	 */
	private List<Player> participants;

	/**
	 * The wave of npcs this player is fighting.
	 */
	private Wave wave;

	/**
	 * The current npcs, if there is one.
	 */
	private List<NPC> currentNPCs;

	/**
	 * The specific height lvl.
	 */
	private int heightLvl;

	public FightCaves(Player player) {
		player.getCombatState().resetPrayers();
		this.participants = new ArrayList<Player>();
		this.participants.add(player);
		this.heightLvl = player.getHeight();
		start();
	}

	@Override
	public boolean deathHook(Player player) {
		quit(player);
		end();
		return true;
	}

	@Override
	public void end() {
		super.end();
		for (NPC npc : currentNPCs) {
			World.getWorld().unregister(npc);
		}
	}

	@Override
	public Boundary getBoundary() {
		return Boundary.create(getName(), Location.create(2371, 5062, 0),
				Location.create(2422, 5117, 0));
	}

	@Override
	public Tickable getGameCycle() {
		return null;
	}

	@Override
	public ItemSafety getItemSafety() {
		return ItemSafety.SAFE;
	}

	@Override
	public String getName() {
		return "Fight Caves";
	}

	@Override
	public List<Player> getParticipants() {
		return participants;
	}

	@Override
	public Location getStartLocation() {
		return Location.create(2438, 5169, 0);
	}

	@Override
	public void killHook(Player player, Mob victim) {
		super.killHook(player, victim);
		if (victim.isNPC()) {
			NPC npc = (NPC) victim;
			currentNPCs.remove(npc);
			if (currentNPCs.size() < 1) {
				wave = Wave.forId(this.wave.getId() + 1);
				if (wave == null) {
					end();
					for (Player participant : participants) {
						participant.getActionSender().sendMessage(
								"You have completed the " + this.getName()
										+ " minigame!");
					}
					return;// finished
				}
				spawnWave(wave);
			}
		}
	}

	@Override
	public void movementHook(Player player) {
		super.movementHook(player);
	}

	@Override
	public void quit(Player player) {
		super.quit(player);
	}

	@Override
	public void start() {
		super.start();
		wave = Wave.ONE;
		for (Player player : participants) {
			player.resetVariousInformation();
			player.setTeleportTarget(Location.create(Misc.random(2408, 2417),
					Misc.random(5111, 5114), 0));
			DialogueManager.openDialogue(player, 2000);
		}
		spawnWave(wave);
	}

	public void spawnWave(Wave wave) {
		NPC[] waveNPCs = wave.getNPCs();
		currentNPCs = new ArrayList<NPC>();
		for (NPC npc : waveNPCs) {
			NPC waveNPC = new NPC(npc.getDefinition(), Location.create(npc
					.getSpawnLocation().getX(), npc.getSpawnLocation().getY(),
					heightLvl), null, null, npc.getDirection());
			currentNPCs.add(waveNPC);
			World.getWorld().register(waveNPC);
			waveNPC.getCombatState().startAttacking(participants.get(0), false);
		}
	}

}
