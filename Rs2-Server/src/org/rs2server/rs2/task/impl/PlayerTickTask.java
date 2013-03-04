package org.rs2server.rs2.task.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import org.rs2server.rs2.GameEngine;
import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.*;
import org.rs2server.rs2.model.Hit.HitPriority;
import org.rs2server.rs2.model.Mob.InteractionMode;
import org.rs2server.rs2.model.UpdateFlags.UpdateFlag;
import org.rs2server.rs2.model.pathfinder.DumbPathFinder;
import org.rs2server.rs2.model.pathfinder.PathFinder;
import org.rs2server.rs2.task.Task;

/**
 * A task which is executed before an <code>UpdateTask</code>. It is similar to
 * the call to <code>process()</code> but you should use <code>Event</code>s
 * instead of putting timers in this class.
 * 
 * @author Graham Edgecombe
 * 
 */
public class PlayerTickTask implements Task {

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * Creates a tick task for a player.
	 * 
	 * @param player
	 *            The player to create the tick task for.
	 */
	public PlayerTickTask(Player player) {
		this.player = player;
	}

	@Override
	public void execute(GameEngine context) {
		Queue<ChatMessage> messages = player.getChatMessageQueue();
		if (messages.size() > 0) {
			player.getUpdateFlags().flag(UpdateFlag.CHAT);
			ChatMessage message = player.getChatMessageQueue().poll();
			player.setCurrentChatMessage(message);
		} else {
			player.setCurrentChatMessage(null);
		}

		if (player.getCombatState().getAttackDelay() > 0) {
			player.getCombatState().decreaseAttackDelay(1);
		}
		if (player.getCombatState().getSpellDelay() > 0) {
			player.getCombatState().decreaseSpellDelay(1);
		}
		if (player.getCombatState().getWeaponSwitchTimer() > 0) {
			player.getCombatState().decreaseWeaponSwitchTimer(1);
		}
		if (player.getCombatState().getSkullTicks() > 0) {
			player.getCombatState().decreaseSkullTicks(1);
		}
		if (player.getAttribute("thievingTimer") != null) {
			player.setAttribute("thievingTimer",
					((Integer) player.getAttribute("thievingTimer")) - 1);
		}
		int restoreStatsTimer = 30;
		if (player.getAttribute("restoreStatsTimer") != null) {
			restoreStatsTimer = (Integer) player
					.getAttribute("restoreStatsTimer");
		}
		if (restoreStatsTimer > 0) {
			player.setAttribute("restoreStatsTimer", restoreStatsTimer--);
		} else {
			player.setAttribute("restoreStatsTimer", 30);
			for (int i = 0; i < Skills.SKILL_COUNT; i++) {
				int level = player.getSkills().getLevel(i);
				int realLevel = player.getSkills().getLevelForExperience(i);
				if (level < realLevel) {
					player.getSkills().setLevel(i, level + 1);
				}
			}
		}

		/*
		 * Gets the next two hits from the queue.
		 */
		List<Hit> hits = player.getHitQueue();
		Hit first = null;
		if (hits.size() > 0) {
			for (int i = 0; i < hits.size(); i++) {
				Hit hit = hits.get(i);
				if (hit.getDelay() < 1) {
					first = hit;
					hits.remove(hit);
					break;
				}
			}
		}
		if (first != null) {
			player.setPrimaryHit(first);
			player.getUpdateFlags().flag(UpdateFlag.HIT);
		}
		Hit second = null;
		if (hits.size() > 0) {
			for (int i = 0; i < hits.size(); i++) {
				Hit hit = hits.get(i);
				if (hit.getDelay() < 1) {
					second = hit;
					hits.remove(hit);
					break;
				}
			}
		}
		if (second != null) {
			player.setSecondaryHit(second);
			player.getUpdateFlags().flag(UpdateFlag.HIT_2);
		}
		if (hits.size() > 0) {// tells us we still have more hits
			Iterator<Hit> hitIt = hits.iterator();
			while (hitIt.hasNext()) {
				Hit hit = hitIt.next();
				if (hit.getDelay() > 0) {
					hit.setDelay(hit.getDelay() - 1);
				}
				if (hit.getHitPriority() == HitPriority.LOW_PRIORITY) {
					hitIt.remove();
				}
			}
		}

		if (player.getInteractingEntity() != null) {
			if (TileControl.calculateDistance(player.getInteractingEntity(),
					player) > 16) {
				player.resetInteractingEntity();

			} else if (!player.getCombatState().canMove()
					|| player.getCombatState().isDead()) {
				player.getWalkingQueue().reset();

			} else if (player.getInteractionMode() == InteractionMode.FOLLOW) {

				int distance = TileControl.calculateDistance(
						player.getInteractingEntity(), player);

				if (distance == 0) {
					PathFinder.getSingleton().generateRandomMovement(player);

				} else if (distance >= 1) {
					PathFinder.getSingleton().moveToLastStep(player,
							player.getInteractingEntity());
				}

			} else if (player.getInteractionMode() == InteractionMode.ATTACK) {

				int distance = TileControl.calculateDistance(
						player.getInteractingEntity(), player);
				int interactionModeDistance = player.getActiveCombatAction()
						.distance(player, player.getInteractingEntity()) + 1;
				int stopDistance = interactionModeDistance - 2;
				if (stopDistance < 1) {
					stopDistance = 1;
				}
				if (TileControl.calculateDistance(
						player.getInteractingEntity(), player) <= stopDistance
						&& distance > 0) {
					if (stopDistance == 1) {
						if (!DumbPathFinder.standingDiagonal(player)) {
							player.getWalkingQueue().reset();
						} else {
							DumbPathFinder.getInAttackablePosition(player);
						}
					} else {
						player.getWalkingQueue().reset();
					}
				} else if (distance == 0) {
					PathFinder.getSingleton().generateRandomMovement(player);
				} else if (distance >= 1) {
					PathFinder.getSingleton().moveToLastStep(player,
							player.getInteractingEntity());
					// System.out.println("yes...");
				}
				// System.out.println(distance);
			}
		}

		player.getWalkingQueue().processNextMovement();

		if (player.isMapRegionChanging()
				&& player.getAttribute("aggressiveKillCount") != null) {
			player.setAttribute("aggressiveKillCount", 0);
		}

		for (MusicTrack track : MusicManager.getTracks()) {
			if (player.getMusic().getCurrentTrack() == null
					|| player.getMusic().getCurrentTrack().getId() != track
							.getId()) {
				if (track.isInRegion(player.getLocation())) {
					player.getMusic().setCurrentTrack(track);
					break;
				}
			}
		}
	}
}
