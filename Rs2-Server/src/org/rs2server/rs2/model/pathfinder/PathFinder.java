package org.rs2server.rs2.model.pathfinder;

import java.util.LinkedList;

import org.rs2server.rs2.clipping.RegionClipping;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;
import org.rs2server.util.Misc;

public class PathFinder {

	private static PathFinder singleton = null;

	public static PathFinder getSingleton() {
		if (singleton == null) {
			singleton = new PathFinder();
		}
		return singleton;
	}

	private void findRoute(Player p, int destX, int destY, boolean moveNear,
			int xLength, int yLength) {

		if (destX == p.getLocation().getLocalX()
				&& destY == p.getLocation().getLocalY() && !moveNear) {
			return;
		}
		destX -= 8 * p.getLocation().getRegionX();
		destY -= 8 * p.getLocation().getRegionY();

		int via[][] = new int[104][104];
		int cost[][] = new int[104][104];

		LinkedList<Integer> tileQueueX = new LinkedList<Integer>();
		LinkedList<Integer> tileQueueY = new LinkedList<Integer>();

		for (int xx = 0; xx < 104; xx++) {
			for (int yy = 0; yy < 104; yy++) {
				cost[xx][yy] = 99999999;
			}
		}

		int curX = p.getLocation().getLocalX();
		int curY = p.getLocation().getLocalY();

		via[curX][curY] = 99;
		cost[curX][curY] = 0;
		int tail = 0;

		tileQueueX.add(Integer.valueOf(curX));
		tileQueueY.add(Integer.valueOf(curY));

		boolean foundPath = false;

		for (int pathLength = 4000; tail != tileQueueX.size()
				&& tileQueueX.size() < pathLength;) {
			curX = tileQueueX.get(tail).intValue();
			curY = tileQueueY.get(tail).intValue();

			int curAbsX = p.getLocation().getRegionX() * 8 + curX;
			int curAbsY = p.getLocation().getRegionY() * 8 + curY;
			if (curX == destX && curY == destY) {
				foundPath = true;
				break;
			}
			tail = (tail + 1) % pathLength;
			int thisCost = cost[curX][curY] + 1;
			if (curY > 0
					&& via[curX][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(Integer.valueOf(curX));
				tileQueueY.add(Integer.valueOf(curY - 1));
				via[curX][curY - 1] = 1;
				cost[curX][curY - 1] = thisCost;
			}
			if (curX > 0
					&& via[curX - 1][curY] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, p.getLocation().getZ()) & 0x1280108) == 0) {
				tileQueueX.add(Integer.valueOf(curX - 1));
				tileQueueY.add(Integer.valueOf(curY));
				via[curX - 1][curY] = 2;
				cost[curX - 1][curY] = thisCost;
			}
			if (curY < 103
					&& via[curX][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(Integer.valueOf(curX));
				tileQueueY.add(Integer.valueOf(curY + 1));
				via[curX][curY + 1] = 4;
				cost[curX][curY + 1] = thisCost;
			}
			if (curX < 103
					&& via[curX + 1][curY] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, p.getLocation().getZ()) & 0x1280180) == 0) {
				tileQueueX.add(Integer.valueOf(curX + 1));
				tileQueueY.add(Integer.valueOf(curY));
				via[curX + 1][curY] = 8;
				cost[curX + 1][curY] = thisCost;
			}
			if (curX > 0
					&& curY > 0
					&& via[curX - 1][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY - 1, p.getLocation().getZ()) & 0x128010e) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, p.getLocation().getZ()) & 0x1280108) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(Integer.valueOf(curX - 1));
				tileQueueY.add(Integer.valueOf(curY - 1));
				via[curX - 1][curY - 1] = 3;
				cost[curX - 1][curY - 1] = thisCost;
			}
			if (curX > 0
					&& curY < 103
					&& via[curX - 1][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY + 1, p.getLocation().getZ()) & 0x1280138) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, p.getLocation().getZ()) & 0x1280108) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(Integer.valueOf(curX - 1));
				tileQueueY.add(Integer.valueOf(curY + 1));
				via[curX - 1][curY + 1] = 6;
				cost[curX - 1][curY + 1] = thisCost;
			}
			if (curX < 103
					&& curY > 0
					&& via[curX + 1][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY - 1, p.getLocation().getZ()) & 0x1280183) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, p.getLocation().getZ()) & 0x1280180) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(Integer.valueOf(curX + 1));
				tileQueueY.add(Integer.valueOf(curY - 1));
				via[curX + 1][curY - 1] = 9;
				cost[curX + 1][curY - 1] = thisCost;
			}
			if (curX < 103
					&& curY < 103
					&& via[curX + 1][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY + 1, p.getLocation().getZ()) & 0x12801e0) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, p.getLocation().getZ()) & 0x1280180) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(Integer.valueOf(curX + 1));
				tileQueueY.add(Integer.valueOf(curY + 1));
				via[curX + 1][curY + 1] = 12;
				cost[curX + 1][curY + 1] = thisCost;
			}
		}
		if (!foundPath) {
			if (moveNear) {
				int endCount = 1000;
				int thisCost = 100;
				int areaIncrease = 10;
				for (int x = destX - areaIncrease; x <= destX + areaIncrease; x++) {
					for (int y = destY - areaIncrease; y <= destY
							+ areaIncrease; y++) {
						if (x >= 0 && y >= 0 && x < 104 && y < 104
								&& cost[x][y] < 100) {
							int tempX = 0;
							if (x < destX) {
								tempX = destX - x;
							} else if (x > (destX + xLength) - 1) {
								tempX = x - ((destX + xLength) - 1);
							}
							int tempY = 0;
							if (y < destY) {
								tempY = destY - y;
							} else if (y > (destY + yLength) - 1) {
								tempY = y - ((destY + yLength) - 1);
							}
							int total = tempX * tempX + tempY * tempY;
							if (total < endCount || total == endCount
									&& cost[x][y] < thisCost) {
								endCount = total;
								thisCost = cost[x][y];
								curX = x;
								curY = y;
							}
						}
					}
				}
				if (endCount == 1000) {
					return;
				}
			} else {
				return;
			}
		}
		tail = 0;
		tileQueueX.set(tail, Integer.valueOf(curX));
		tileQueueY.set(tail++, Integer.valueOf(curY));
		int l5;
		for (int j5 = l5 = via[curX][curY]; curX != p.getLocation().getLocalX()
				|| curY != p.getLocation().getLocalY(); j5 = via[curX][curY]) {
			if (j5 != l5) {
				l5 = j5;
				tileQueueX.set(tail, Integer.valueOf(curX));
				tileQueueY.set(tail++, Integer.valueOf(curY));
			}
			if ((j5 & 2) != 0) {
				curX++;
			} else if ((j5 & 8) != 0) {
				curX--;
			}
			if ((j5 & 1) != 0) {
				curY++;
			} else if ((j5 & 4) != 0) {
				curY--;
			}
		}
		p.getWalkingQueue().reset();
		int size = tail--;
		int pathX = p.getLocation().getRegionX() * 8
				+ tileQueueX.get(tail).intValue();
		int pathY = p.getLocation().getRegionY() * 8
				+ tileQueueY.get(tail).intValue();
		p.getWalkingQueue().addStep(pathX, pathY);
		p.getWalkingQueue().finish();
		for (int i = 1; i < size; i++) {
			tail--;
			pathX = p.getLocation().getRegionX() * 8
					+ tileQueueX.get(tail).intValue();
			pathY = p.getLocation().getRegionY() * 8
					+ tileQueueY.get(tail).intValue();
			p.getWalkingQueue().addStep(pathX, pathY);
		}
	}

	public void generateRandomMovement(Player player) {
		int dir = -1;

		if (!RegionClipping.getRegionClipping().blockedNorth(player.getLocation())) {
			dir = 0;
		} else if (!RegionClipping.getRegionClipping().blockedEast(
				player.getLocation())) {
			dir = 4;
		} else if (!RegionClipping.getRegionClipping().blockedSouth(
				player.getLocation())) {
			dir = 8;
		} else if (!RegionClipping.getRegionClipping().blockedWest(
				player.getLocation())) {
			dir = 12;
		}
		int random = Misc.random(3);

		boolean found = false;

		if (random == 0) {
			if (!RegionClipping.getRegionClipping().blockedNorth(
					player.getLocation())) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX(), player
								.getLocation().getY() + 1, 0));
				found = true;
			}
		} else if (random == 1) {
			if (!RegionClipping.getRegionClipping()
					.blockedEast(player.getLocation())) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX() + 1, player
								.getLocation().getY(), 0));
				found = true;
			}
		} else if (random == 2) {
			if (!RegionClipping.getRegionClipping().blockedSouth(
					player.getLocation())) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX(), player
								.getLocation().getY() - 1, 0));
				found = true;
			}
		} else if (random == 3) {
			if (!RegionClipping.getRegionClipping()
					.blockedWest(player.getLocation())) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX() - 1, player
								.getLocation().getY(), 0));
				found = true;
			}
		}
		if (!found) {
			if (dir == 0) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX(), player
								.getLocation().getY() + 1, 0));
			} else if (dir == 4) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX() + 1, player
								.getLocation().getY(), 0));
			} else if (dir == 8) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX(), player
								.getLocation().getY() - 1, 0));
			} else if (dir == 12) {
				PathFinder.getSingleton().moveTo(
						player,
						Location.create(player.getLocation().getX() - 1, player
								.getLocation().getY(), 0));
			}
		}
	}

	public void moveTo(Player player, Location loc) {// int x, int y) {
		findRoute(player, loc.getX(), loc.getY(), true, 1, 1);
	}

	public void moveToLastStep(Player playerToMove, Mob playerWalkingTo) {
		if (playerWalkingTo.getLocation().getLastStep() != null) {
			moveTo(playerToMove, Location.create(playerWalkingTo.getLocation()
					.getLastStep()[0], playerWalkingTo.getLocation()
					.getLastStep()[1]));
		}
	}
}
