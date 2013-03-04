package org.rs2server.rs2.model.pathfinder;

import org.rs2server.rs2.clipping.ObjectDef;
import org.rs2server.rs2.clipping.RegionClipping;
import org.rs2server.rs2.clipping.TileControl;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.World;
import org.rs2server.util.Misc;

/**
 * 
 * @author Josh Mai
 * 
 */
public class DumbPathFinder {

	private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	public static void follow(NPC npc, Mob target) {

		Location[] npcTiles = TileControl.getHoveringTiles(npc);
		Location npcLocation = npc.getLocation();

		boolean[] canMove = getMovementFlags(npc);

		int dir = -1;
		int distance = TileControl.calculateDistance(npc, target);

		if (distance > 16) {
			npc.setInteractingEntity(null, null);
			return;
		}
		if (!npc.getCombatState().canMove()) {
			return;
		}

		if (distance > 1) {
			for (Location tile : npcTiles) {
				if (tile.getX() == target.getLocation().getX()) {
					canMove[EAST] = false;
					canMove[WEST] = false;
				} else if (tile.getY() == target.getLocation().getY()) {
					canMove[NORTH] = false;
					canMove[SOUTH] = false;
				}
			}
			boolean[] blocked = new boolean[3];

			if (canMove[NORTH] && canMove[EAST]) {

				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedNorth(tiles)) {
						blocked[0] = true;
					}
					if (RegionClipping.getRegionClipping().blockedEast(tiles)) {
						blocked[1] = true;
					}
					if (RegionClipping.getRegionClipping().blockedNorthEast(tiles)) {
						blocked[2] = true;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() + 1, 0)), npc)) {
					blocked[0] = true;
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() + 1,
												npcLocation.getY(), 0)), npc)) {
					blocked[1] = true;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(npc, Location.create(
								npcLocation.getX() + 1, npcLocation.getY() + 1,
								0)), npc)) {
					blocked[2] = true;
				}

				if (!blocked[2] && !blocked[0] && !blocked[1]) {
					dir = 2;
				} else if (!blocked[0]) {
					dir = 0;
				} else if (!blocked[1]) {
					dir = 4;
				}

			} else if (canMove[NORTH] && canMove[WEST]) {
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedNorth(tiles)) {
						blocked[0] = true;
					}
					if (RegionClipping.getRegionClipping().blockedWest(tiles)) {
						blocked[1] = true;
					}
					if (RegionClipping.getRegionClipping().blockedNorthWest(tiles)) {
						blocked[2] = true;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() + 1, 0)), npc)) {
					blocked[0] = true;
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() - 1,
												npcLocation.getY(), 0)), npc)) {
					blocked[1] = true;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(npc, Location.create(
								npcLocation.getX() - 1, npcLocation.getY() + 1,
								0)), npc)) {
					blocked[2] = true;
				}

				if (!blocked[2] && !blocked[0] && !blocked[1]) {
					dir = 14;
				} else if (!blocked[0]) {
					dir = 0;
				} else if (!blocked[1]) {
					dir = 12;
				}

			} else if (canMove[SOUTH] && canMove[EAST]) {
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedSouth(tiles)) {
						blocked[0] = true;
					}
					if (RegionClipping.getRegionClipping().blockedEast(tiles)) {
						blocked[1] = true;
					}
					if (RegionClipping.getRegionClipping().blockedSouthEast(tiles)) {
						blocked[2] = true;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() - 1, 0)), npc)) {
					blocked[0] = true;
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() + 1,
												npcLocation.getY(), 0)), npc)) {
					blocked[1] = true;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(npc, Location.create(
								npcLocation.getX() + 1, npcLocation.getY() - 1,
								0)), npc)) {
					blocked[2] = true;
				}

				if (!blocked[2] && !blocked[0] && !blocked[1]) {
					dir = 6;
				} else if (!blocked[0]) {
					dir = 8;
				} else if (!blocked[1]) {
					dir = 4;
				}

			} else if (canMove[SOUTH] && canMove[WEST]) {
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedSouth(tiles)) {
						blocked[0] = true;
					}
					if (RegionClipping.getRegionClipping().blockedWest(tiles)) {
						blocked[1] = true;
					}
					if (RegionClipping.getRegionClipping().blockedSouthWest(tiles)) {
						blocked[2] = true;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() - 1, 0)), npc)) {
					blocked[0] = true;
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() - 1,
												npcLocation.getY(), 0)), npc)) {
					blocked[1] = true;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(npc, Location.create(
								npcLocation.getX() - 1, npcLocation.getY() - 1,
								0)), npc)) {
					blocked[2] = true;
				}

				if (!blocked[2] && !blocked[0] && !blocked[1]) {
					dir = 10;
				} else if (!blocked[0]) {
					dir = 8;
				} else if (!blocked[1]) {
					dir = 12;
				}

			} else if (canMove[NORTH]) {
				dir = 0;
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedNorth(tiles)) {
						dir = -1;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() + 1, 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[EAST]) {
				dir = 4;
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedEast(tiles)) {
						dir = -1;
					}
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() + 1,
												npcLocation.getY(), 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[SOUTH]) {
				dir = 8;
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedSouth(tiles)) {
						dir = -1;
					}
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() - 1, 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[WEST]) {
				dir = 12;
				for (Location tiles : npcTiles) {
					if (RegionClipping.getRegionClipping().blockedWest(tiles)) {
						dir = -1;
					}
				}
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() - 1,
												npcLocation.getY(), 0)), npc)) {
					dir = -1;
				}
			}
		} else if (distance == 0) {
			for (int i = 0; i < canMove.length; i++) {
				canMove[i] = true;
			}
			for (Location tiles : npcTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tiles)) {
					canMove[NORTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedEast(tiles)) {
					canMove[EAST] = false;
				}
				if (RegionClipping.getRegionClipping().blockedSouth(tiles)) {
					canMove[SOUTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedWest(tiles)) {
					canMove[WEST] = false;
				}
			}
			int randomSelection = Misc.random(3);
			if (canMove[randomSelection]) {
				dir = randomSelection * 4;
				switch (dir) {
				case 0:
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(npc, Location.create(
									npcLocation.getX(), npcLocation.getY() + 1,
									0)), npc)) {
						dir = -1;
					}
					break;
				case 4:
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(npc, Location.create(
									npcLocation.getX() + 1, npcLocation.getY(),
									0)), npc)) {
						dir = -1;
					}
					break;
				case 8:
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(npc, Location.create(
									npcLocation.getX(), npcLocation.getY() - 1,
									0)), npc)) {
						dir = -1;
					}
					break;
				case 12:
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(npc, Location.create(
									npcLocation.getX() - 1, npcLocation.getY(),
									0)), npc)) {
						dir = -1;
					}
					break;
				}
			}
			if (canMove[NORTH] && dir == -1) {
				dir = 0;
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() + 1, 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[EAST] && dir == -1) {
				dir = 4;
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() + 1,
												npcLocation.getY(), 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[SOUTH] && dir == -1) {
				dir = 8;
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(
								npc,
								Location.create(npcLocation.getX(),
										npcLocation.getY() - 1, 0)), npc)) {
					dir = -1;
				}
			} else if (canMove[WEST] && dir == -1) {
				dir = 12;
				if (TileControl.getSingleton()
						.locationOccupied(
								TileControl.getHoveringTiles(npc, Location
										.create(npcLocation.getX() - 1,
												npcLocation.getY(), 0)), npc)) {
					dir = -1;
				}
			}
		}
		if (dir == -1) {
			return;
		}

		dir >>= 1;

		if (dir < 0) {
			return;
		}

		int diffX = Misc.directionDeltaX[dir];
		int diffY = Misc.directionDeltaY[dir];
		// npc.setTeleportTarget(Location.create(npcLocation.getX() + x,
		// npcLocation.getY() + y, npcLocation.getZ()));
		npc.setTeleportTarget(npc.getLocation().transform(diffX, diffY, 0));
	}

	public static void getInAttackablePosition(Mob mob) {
		Mob target = mob.getInteractingEntity();

		if (target == null || !mob.getCombatState().canMove()) {
			return;
		}
		Location[] mobTiles = TileControl.getHoveringTiles(mob);

		boolean[] canMove = getMovementFlags(mob);

		if (canMove[NORTH] && canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
				if (mob.isNPC()) {
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(mob, Location.create(
									mob.getLocation().getX(), mob.getLocation()
											.getY() + 1, 0)), mob)) {
						canMove[NORTH] = false;
					}
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(mob, Location.create(
									mob.getLocation().getX() + 1, tile.getY(),
									tile.getZ())), mob)) {
						canMove[EAST] = false;
					}
				}
			}
		} else if (canMove[SOUTH] && canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, 0)), mob)) {
					canMove[SOUTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() + 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[EAST] = false;
				}
			}
		} else if (canMove[SOUTH] && canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, 0)), mob)) {
					canMove[SOUTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		} else if (canMove[NORTH] && canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() + 1, 0)), mob)) {
					canMove[NORTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		} else if (canMove[NORTH]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() + 1, 0)), mob)) {
					canMove[NORTH] = false;
				}
			}
		} else if (canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() + 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[EAST] = false;
				}
			}
		} else if (canMove[SOUTH]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, mob.getLocation()
										.getZ())), mob)) {
					canMove[SOUTH] = false;
				}
			}
		} else if (canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		}
		int diffX = 0;
		int diffY = 0;

		if (canMove[EAST]) {
			diffX++;
		} else if (canMove[WEST]) {
			diffX--;
		} else if (canMove[NORTH]) {
			diffY++;
		} else if (canMove[SOUTH]) {
			diffY--;
		}
		if (diffX != 0 || diffY != 0) {
			if (mob.isNPC()) {
				mob.setTeleportTarget(mob.getLocation().transform(diffX, diffY,
						0));
			} else if (mob.isPlayer()) {
				PathFinder.getSingleton().moveTo(
						(Player) mob,
						Location.create(mob.getLocation().getX() + diffX, mob
								.getLocation().getY() + diffY, 0));
			}
		}
	}

	public static void getInAttackablePosition(Mob mob, GameObject obj) {
		if (!mob.getCombatState().canMove()) {
			return;
		}
		Location[] mobTiles = TileControl.getHoveringTiles(mob);

		boolean[] canMove = getMovementFlags(mob);

		if (canMove[NORTH] && canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
				if (mob.isNPC()) {
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(mob, Location.create(
									mob.getLocation().getX(), mob.getLocation()
											.getY() + 1, 0)), mob)) {
						canMove[NORTH] = false;
					}
					if (TileControl.getSingleton().locationOccupied(
							TileControl.getHoveringTiles(mob, Location.create(
									mob.getLocation().getX() + 1, tile.getY(),
									tile.getZ())), mob)) {
						canMove[EAST] = false;
					}
				}
			}
		} else if (canMove[SOUTH] && canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, 0)), mob)) {
					canMove[SOUTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() + 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[EAST] = false;
				}
			}
		} else if (canMove[SOUTH] && canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, 0)), mob)) {
					canMove[SOUTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		} else if (canMove[NORTH] && canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() + 1, 0)), mob)) {
					canMove[NORTH] = false;
				}
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		} else if (canMove[NORTH]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedNorth(tile)) {
					canMove[NORTH] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() + 1, 0)), mob)) {
					canMove[NORTH] = false;
				}
			}
		} else if (canMove[EAST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedEast(tile)) {
					canMove[EAST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() + 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[EAST] = false;
				}
			}
		} else if (canMove[SOUTH]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedSouth(tile)) {
					canMove[SOUTH] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX(),
								mob.getLocation().getY() - 1, mob.getLocation()
										.getZ())), mob)) {
					canMove[SOUTH] = false;
				}
			}
		} else if (canMove[WEST]) {
			for (Location tile : mobTiles) {
				if (RegionClipping.getRegionClipping().blockedWest(tile)) {
					canMove[WEST] = false;
				}
			}
			if (mob.isNPC()) {
				if (TileControl.getSingleton().locationOccupied(
						TileControl.getHoveringTiles(mob, Location.create(mob
								.getLocation().getX() - 1, mob.getLocation()
								.getY(), mob.getLocation().getZ())), mob)) {
					canMove[WEST] = false;
				}
			}
		}
		int diffX = 0;
		int diffY = 0;

		if (canMove[EAST]) {
			diffX++;
		} else if (canMove[WEST]) {
			diffX--;
		} else if (canMove[NORTH]) {
			diffY++;
		} else if (canMove[SOUTH]) {
			diffY--;
		}
		if (diffX != 0 || diffY != 0) {
			if (mob.isNPC()) {
				mob.setTeleportTarget(mob.getLocation().transform(diffX, diffY,
						0));
			} else if (mob.isPlayer()) {
				PathFinder.getSingleton().moveTo(
						(Player) mob,
						Location.create(mob.getLocation().getX() + diffX, mob
								.getLocation().getY() + diffY, 0));
			}
		}
	}

	/**
	 * @param pointA
	 *            The starting point
	 * @param pointB
	 *            The ending point
	 * @return Possible movements to get from the pointA to pointB
	 */
	public static boolean[] getMovementFlags(Location pointA, Location pointB) {
		boolean[] flags = new boolean[4];

		if (pointA == null || pointB == null) {
			return flags;
		}
		if (pointA.getX() > pointB.getX()) {
			flags[WEST] = true;
		} else if (pointA.getX() < pointB.getX()) {
			flags[EAST] = true;
		}
		if (pointA.getY() > pointB.getY()) {
			flags[SOUTH] = true;
		} else if (pointA.getY() < pointB.getY()) {
			flags[NORTH] = true;
		}
		return flags;

	}

	/**
	 * @param mob
	 *            The starting point
	 * @return Possible movement flags to get from the mob to
	 *         mob.getInteractingEntity()
	 */
	public static boolean[] getMovementFlags(Mob mob) {
		boolean[] flags = new boolean[4];

		Mob target = mob.getInteractingEntity();

		if (target == null) {
			return flags;
		}
		if (mob.getLocation().getX() > target.getLocation().getX()) {
			flags[WEST] = true;
		} else if (mob.getLocation().getX() < target.getLocation().getX()) {
			flags[EAST] = true;
		}
		if (mob.getLocation().getY() > target.getLocation().getY()) {
			flags[SOUTH] = true;
		} else if (mob.getLocation().getY() < target.getLocation().getY()) {
			flags[NORTH] = true;
		}
		return flags;
	}

	public static boolean objectObstruction(Mob mob) {

		Mob target = mob.getInteractingEntity();

		if (target == null) {
			return true;
		}
		int distance = TileControl.calculateDistance(mob, target);

		if (distance != 1) {
			return false;
		}
		Location loc = mob.getLocation();
		Location loc2 = mob.getLocation().closestTileToMob(target);

		if (loc == null || loc2 == null) {
			return true;
		}
		if (loc.getX() > loc2.getX()) {
			if (loc.getY() == loc2.getY()) {
				return RegionClipping.getRegionClipping().blockedWest(loc);
			}
		} else if (loc.getX() < loc2.getX()) {
			if (loc.getY() == loc2.getY()) {
				return RegionClipping.getRegionClipping().blockedEast(loc);
			}
		}
		if (loc.getY() > loc2.getY()) {
			if (loc.getX() == loc2.getX()) {
				return RegionClipping.getRegionClipping().blockedSouth(loc);
			}
		} else if (loc.getY() < loc2.getY()) {
			if (loc.getX() == loc2.getX()) {
				return RegionClipping.getRegionClipping().blockedNorth(loc);
			}
		}
		return false;
	}

	public static boolean projectileBlocked(Location loc, Location loc2) {

		if (loc == null || loc2 == null) {
			return true;
		}
		double offsetX = Math.abs(loc.getX() - loc2.getX());
		double offsetY = Math.abs(loc.getY() - loc2.getY());

		int distance = TileControl.calculateDistance(loc, loc2);

		if (distance == 0) {
			return true;
		}
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];

		int curX = loc.getX();
		int curY = loc.getY();
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;

		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while (distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > loc2.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;
					currentTileXCount -= offsetX;
				}
			} else if (curX < loc2.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > loc2.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;
					currentTileYCount -= offsetY;
				}
			} else if (curY < loc2.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = loc.getZ();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;
		}
		for (int i = 0; i < path.length; i++) {
			Location l = Location.create(path[i][0] + path[i][3], path[i][1]
					+ path[i][4], path[i][2]);
			for (GameObject o : World.getWorld().getRegionManager()
					.getRegionByLocation(l).getGameObjects()) {
				if (o.getLocation().equals(l)) {
					/*
					 * System.out.println(o.getId());
					 * System.out.println(o.getLocation());
					 * System.out.println(ObjectDef
					 * .getObjectDef(o.getId()).solid);
					 * System.out.println(ObjectDef
					 * .getObjectDef(o.getId()).flatTerrain);
					 * System.out.println(
					 * ObjectDef.getObjectDef(o.getId()).aBoolean736);
					 * System.out
					 * .println(ObjectDef.getObjectDef(o.getId()).aBoolean757);
					 * System
					 * .out.println(ObjectDef.getObjectDef(o.getId()).aBoolean764
					 * ); System.out.println(ObjectDef.getObjectDef(o.getId()).
					 * unwalkable);
					 */
					if (!RegionClipping.getRegionClipping().getClipping(path[i][0],
							path[i][1], path[i][2], path[i][3], path[i][4])
							&& RegionClipping.getRegionClipping().getClipping(
									path[i][0], path[i][1],
									path[i][2] & 0x20000) != 0) {
						return true;
					}
					// if (ObjectDef.getObjectDef(o.getId()) != null
					// && ObjectDef.getObjectDef(o.getId()).aBoolean764) {
					// return true;
					// }
				}
			}
		}

		return false;
	}

	public static boolean projectileBlocked(Mob mob) {

		Mob target = mob.getInteractingEntity();

		if (target == null) {
			return true;
		}
		Location pointA = mob.getCentreLocation();
		Location pointB = target.getCentreLocation();

		double offsetX = Math.abs(pointA.getX() - pointB.getX());
		double offsetY = Math.abs(pointA.getY() - pointB.getY());

		int xDis = Math.abs(pointA.getX() - pointB.getX());
		int yDis = Math.abs(pointA.getY() - pointB.getY());
		int distance = xDis > yDis ? xDis : yDis;
		distance = distance > 1 ? distance - 1 : distance;

		if (distance == 0) {
			return true;
		}
		offsetX = offsetX > 0 ? offsetX / distance : 0;
		offsetY = offsetY > 0 ? offsetY / distance : 0;

		int[][] path = new int[distance][5];

		int curX = pointA.getX();
		int curY = pointA.getY();
		int next = 0;
		int nextMoveX = 0;
		int nextMoveY = 0;

		double currentTileXCount = 0.0;
		double currentTileYCount = 0.0;

		while (distance > 0) {
			distance--;
			nextMoveX = 0;
			nextMoveY = 0;
			if (curX > pointB.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX--;
					curX--;
					currentTileXCount -= offsetX;
				}
			} else if (curX < pointB.getX()) {
				currentTileXCount += offsetX;
				if (currentTileXCount >= 1.0) {
					nextMoveX++;
					curX++;
					currentTileXCount -= offsetX;
				}
			}
			if (curY > pointB.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY--;
					curY--;
					currentTileYCount -= offsetY;
				}
			} else if (curY < pointB.getY()) {
				currentTileYCount += offsetY;
				if (currentTileYCount >= 1.0) {
					nextMoveY++;
					curY++;
					currentTileYCount -= offsetY;
				}
			}
			path[next][0] = curX;
			path[next][1] = curY;
			path[next][2] = mob.getLocation().getZ();
			path[next][3] = nextMoveX;
			path[next][4] = nextMoveY;
			next++;
		}
		/*
		 * for (int i = 0; i < path.length; i++) { if
		 * (!RegionClipping.getSingleton().getClipping(path[i][0], path[i][1],
		 * path[i][2], path[i][3], path[i][4])) { // &&
		 * RegionClipping.getSingleton().getClipping(path[i][0], //path[i][1],
		 * path[i][2] & 0x20000) != 0) { // return true; } }
		 */
		for (int i = 0; i < path.length; i++) {
			Location l = Location.create(path[i][0] + path[i][3], path[i][1]
					+ path[i][4], path[i][2]);
			for (GameObject o : World.getWorld().getRegionManager()
					.getRegionByLocation(l).getGameObjects()) {
				if (o.getLocation().equals(l)) {
					if (ObjectDef.getObjectDef(o.getId()) != null
							&& !ObjectDef.getObjectDef(o.getId()).flatTerrain) {
						/*
						 * System.out.println(o.getId());
						 * System.out.println(o.getLocation());
						 * System.out.println
						 * (ObjectDef.getObjectDef(o.getId()).solid);
						 * System.out.
						 * println(ObjectDef.getObjectDef(o.getId()).flatTerrain
						 * );
						 * System.out.println(ObjectDef.getObjectDef(o.getId()
						 * ).aBoolean736);
						 * System.out.println(ObjectDef.getObjectDef
						 * (o.getId()).aBoolean757);
						 * System.out.println(ObjectDef
						 * .getObjectDef(o.getId()).aBoolean764);
						 * System.out.println
						 * (ObjectDef.getObjectDef(o.getId()).unwalkable);
						 * System
						 * .out.println(ObjectDef.getObjectDef(o.getId()).type);
						 */
						return true;
					}
				}
			}
		}
		/*
		 * if (!RegionClipping.getSingleton().getClipping(path[i][0],
		 * path[i][1], path[i][2], path[i][3], path[i][4])) { // &&
		 * RegionClipping.getSingleton().getClipping(path[i][0], // path[i][1],
		 * path[i][2] & 0x20000) != 0) { return true; }
		 */
		return false;
	}

	public static boolean standingDiagonal(Mob mob) {
		Mob victim = mob.getInteractingEntity();

		if (victim == null) {
			return true;
		}
		Location[] attackerTiles = TileControl.getHoveringTiles(mob);
		Location[] victimTiles = TileControl.getHoveringTiles(victim);

		for (Location a : attackerTiles) {
			for (Location v : victimTiles) {
				if (a.getX() == v.getX()) {
					return false;
				}
				if (a.getY() == v.getY()) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean standingDiagonal(Mob mob, GameObject obj) {
		Location[] attackerTiles = TileControl.getHoveringTiles(mob);
		Location[] victimTiles = TileControl.getHoveringTiles(obj);

		for (Location a : attackerTiles) {
			for (Location v : victimTiles) {
				if (a.getX() == v.getX()) {
					return false;
				}
				if (a.getY() == v.getY()) {
					return false;
				}
			}
		}
		return true;
	}
}
