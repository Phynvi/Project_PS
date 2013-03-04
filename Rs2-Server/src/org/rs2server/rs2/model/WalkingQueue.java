package org.rs2server.rs2.model;

import java.util.Deque;
import java.util.LinkedList;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.clipping.RegionClipping;
import org.rs2server.rs2.tickable.impl.EnergyRestoreTick;
import org.rs2server.rs2.util.DirectionUtils;

/**
 * <p>
 * A <code>WalkingQueue</code> stores steps the client needs to walk and allows
 * this queue of steps to be modified.
 * </p>
 * 
 * <p>
 * The class will also process these steps when {@link #processNextMovement()}
 * is called. This should be called once per server cycle.
 * </p>
 * 
 * @author Graham Edgecombe
 * 
 */
// TODO implement 'travelback' algorithm so you are unable to noclip while map
// TODO region is loading?
public class WalkingQueue {

	/**
	 * Represents a single point in the queue.
	 * 
	 * @author Graham Edgecombe
	 * 
	 */
	private static class Point {

		/**
		 * The x-coordinate.
		 */
		private final int x;
		/**
		 * The y-coordinate.
		 */
		private final int y;
		/**
		 * The direction to walk to this point.
		 */
		private final int dir;

		/**
		 * Creates a point.
		 * 
		 * @param x
		 *            X coord.
		 * @param y
		 *            Y coord.
		 * @param dir
		 *            Direction to walk to this point.
		 */
		public Point(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	/**
	 * The maximum size of the queue. If there are more points than this size,
	 * they are discarded.
	 */
	public static final int MAXIMUM_SIZE = 50;
	/**
	 * Direction variables.
	 */
	public static final int NORTH = 1, SOUTH = 6, EAST = 4, WEST = 3,
			NORTH_EAST = 2, SOUTH_EAST = 7, NORTH_WEST = 0, SOUTH_WEST = 5;
	/**
	 * The entity.
	 */
	private Mob mob;
	/**
	 * The queue of waypoints.
	 */
	private Deque<Point> waypoints = new LinkedList<Point>();
	/**
	 * Run toggle (button in client).
	 */
	private boolean runToggled = false;
	/**
	 * Run for this queue (CTRL-CLICK) toggle.
	 */
	private boolean runQueue = false;
	/**
	 * The entity's energy to run.
	 */
	private int energy = 100;

	/**
	 * Creates the <code>WalkingQueue</code> for the specified
	 * <code>Entity</code>.
	 * 
	 * @param entity
	 *            The entity whose walking queue this is.
	 */
	public WalkingQueue(Mob mob) {
		this.mob = mob;
	}

	/**
	 * Adds a single step to the walking queue, filling in the points to the
	 * previous point in the queue if necessary.
	 * 
	 * @param x
	 *            The local x coordinate.
	 * @param y
	 *            The local y coordinate.
	 */
	public void addStep(int x, int y) {
		/*
		 * The RuneScape client will not send all the points in the queue. It
		 * just sends places where the direction changes.
		 * 
		 * For instance, walking from a route like this:
		 * 
		 * <code> ***** * * ***** </code>
		 * 
		 * Only the places marked with X will be sent:
		 * 
		 * <code> X***X * * X***X </code>
		 * 
		 * This code will 'fill in' these points and then add them to the queue.
		 */

		/*
		 * We need to know the previous point to fill in the path.
		 */
		if (waypoints.size() == 0) {
			/*
			 * There is no last point, reset the queue to add the player's
			 * current location.
			 */
			reset();
		}

		/*
		 * We retrieve the previous point here.
		 */
		Point last = waypoints.peekLast();

		/*
		 * We now work out the difference between the points.
		 */
		int diffX = x - last.x;
		int diffY = y - last.y;

		/*
		 * The following code is unique to Rs2-Server, as I have never seen it
		 * fixed in another server.
		 * 
		 * As you know, on RuneScape, if you move a direction, and someone logs
		 * in or teleports to that area, when they are added, they are facing
		 * the correct direction. However, on every RSPS, they face south. I
		 * have experienced this when creating a video for example, having lots
		 * of friends facing north, relogging, and then they are all south,
		 * however this code fixes it.
		 */

		int newDirection = -1;

		if (diffX != 0) { // We are moving left or right
			if (diffX > 0) { // We are moving east
				if (diffY != 0) { // We are also moving up/down
					if (diffY > 0) { // We are moving north east
						newDirection = NORTH_EAST;
					} else { // We are moving south east
						newDirection = SOUTH_EAST;
					}
				} else {
					newDirection = EAST;
				}
			} else { // We are moving west
				if (diffY != 0) { // We are also moving up/down
					if (diffY > 0) { // We are moving north west
						newDirection = NORTH_WEST;
					} else { // We are moving south west
						newDirection = SOUTH_WEST;
					}
				} else {
					newDirection = WEST;
				}
			}
		}
		if (newDirection == -1) { // We aren't moving left or right, so the
									// direction will still be at -1
			if (diffY != 0) { // We are moving up or down
				// Bear in mind we do not have to recheck for diagonals,
				// otherwise diffX would not of == 0 at the previous check
				if (diffY > 0) { // We are moving north
					newDirection = NORTH;
				} else { // We are moving south
					newDirection = SOUTH;
				}
			}
		}
		if (newDirection != -1) {
			mob.setDirection(newDirection);
		}

		/*
		 * And calculate the number of steps there is between the points.
		 */
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int i = 0; i < max; i++) {
			/*
			 * Keep lowering the differences until they reach 0 - when our route
			 * will be complete.
			 */
			if (diffX < 0) {
				diffX++;
			} else if (diffX > 0) {
				diffX--;
			}
			if (diffY < 0) {
				diffY++;
			} else if (diffY > 0) {
				diffY--;
			}

			/*
			 * Add this next step to the queue.
			 */
			addStepInternal(x - diffX, y - diffY);
		}
	}

	/**
	 * Adds a single step to the queue internally without counting gaps. This
	 * method is unsafe if used incorrectly so it is private to protect the
	 * queue.
	 * 
	 * @param x
	 *            The x coordinate of the step.
	 * @param y
	 *            The y coordinate of the step.
	 */
	private void addStepInternal(int x, int y) {
		/*
		 * Check if we are going to violate capacity restrictions.
		 */
		if (waypoints.size() >= MAXIMUM_SIZE) {
			/*
			 * If we are we'll just skip the point. The player won't get a
			 * complete route by large routes are not probable and are more
			 * likely sent by bots to crash servers.
			 */
			return;
		}

		/*
		 * We retrieve the previous point (this is to calculate the direction to
		 * move in).
		 */
		Point last = waypoints.peekLast();

		/*
		 * Now we work out the difference between these steps.
		 */
		int diffX = x - last.x;
		int diffY = y - last.y;

		/*
		 * And calculate the direction between them.
		 */
		int dir = DirectionUtils.direction(diffX, diffY);

		/*
		 * Check if we actually move anywhere.
		 */
		if (dir > -1) {
			/*
			 * We now have the information to add a point to the queue! We
			 * create the actual point object and add it.
			 */
			waypoints.add(new Point(x, y, dir));

		}
	}

	public void findRoute(int destX, int destY, boolean moveNear, int xLength,
			int yLength) {
		if (destX == mob.getLocation().getLocalX()
				&& destY == mob.getLocation().getLocalY() && !moveNear) {
			return;
		}
		destX = destX - 8 * mob.getLocation().getRegionX();
		destY = destY - 8 * mob.getLocation().getRegionY();
		int[][] via = new int[104][104];
		int[][] cost = new int[104][104];
		LinkedList<Integer> tileQueueX = new LinkedList<Integer>();
		LinkedList<Integer> tileQueueY = new LinkedList<Integer>();
		for (int xx = 0; xx < 104; xx++) {
			for (int yy = 0; yy < 104; yy++) {
				cost[xx][yy] = 99999999;
			}
		}
		int curX = mob.getLocation().getLocalX();
		int curY = mob.getLocation().getLocalY();
		via[curX][curY] = 99;
		cost[curX][curY] = 0;
		int tail = 0;
		tileQueueX.add(curX);
		tileQueueY.add(curY);
		boolean foundPath = false;
		int pathLength = 4000;
		while (tail != tileQueueX.size() && tileQueueX.size() < pathLength) {
			curX = tileQueueX.get(tail);
			curY = tileQueueY.get(tail);
			int curAbsX = mob.getLocation().getRegionX() * 8 + curX;
			int curAbsY = mob.getLocation().getRegionY() * 8 + curY;
			if (curX == destX && curY == destY) {
				foundPath = true;
				break;
			}
			tail = (tail + 1) % pathLength;
			int thisCost = cost[curX][curY] + 1;
			if (curY > 0
					&& via[curX][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, mob.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY - 1);
				via[curX][curY - 1] = 1;
				cost[curX][curY - 1] = thisCost;
			}
			if (curX > 0
					&& via[curX - 1][curY] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280108) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY);
				via[curX - 1][curY] = 2;
				cost[curX - 1][curY] = thisCost;
			}
			if (curY < 104 - 1
					&& via[curX][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, mob.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(curX);
				tileQueueY.add(curY + 1);
				via[curX][curY + 1] = 4;
				cost[curX][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& via[curX + 1][curY] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280180) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY);
				via[curX + 1][curY] = 8;
				cost[curX + 1][curY] = thisCost;
			}
			if (curX > 0
					&& curY > 0
					&& via[curX - 1][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY - 1, mob.getLocation().getZ()) & 0x128010e) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280108) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, mob.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY - 1);
				via[curX - 1][curY - 1] = 3;
				cost[curX - 1][curY - 1] = thisCost;
			}
			if (curX > 0
					&& curY < 104 - 1
					&& via[curX - 1][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY + 1, mob.getLocation().getZ()) & 0x1280138) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX - 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280108) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, mob.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(curX - 1);
				tileQueueY.add(curY + 1);
				via[curX - 1][curY + 1] = 6;
				cost[curX - 1][curY + 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY > 0
					&& via[curX + 1][curY - 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY - 1, mob.getLocation().getZ()) & 0x1280183) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280180) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY - 1, mob.getLocation().getZ()) & 0x1280102) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY - 1);
				via[curX + 1][curY - 1] = 9;
				cost[curX + 1][curY - 1] = thisCost;
			}
			if (curX < 104 - 1
					&& curY < 104 - 1
					&& via[curX + 1][curY + 1] == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY + 1, mob.getLocation().getZ()) & 0x12801e0) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX + 1,
							curAbsY, mob.getLocation().getZ()) & 0x1280180) == 0
					&& (RegionClipping.getRegionClipping().getClipping(curAbsX,
							curAbsY + 1, mob.getLocation().getZ()) & 0x1280120) == 0) {
				tileQueueX.add(curX + 1);
				tileQueueY.add(curY + 1);
				via[curX + 1][curY + 1] = 12;
				cost[curX + 1][curY + 1] = thisCost;
			}
		}
		if (!foundPath) {
			if (moveNear) {
				int i_223_ = 1000;
				int thisCost = 100;
				int i_225_ = 10;
				for (int x = destX - i_225_; x <= destX + i_225_; x++) {
					for (int y = destY - i_225_; y <= destY + i_225_; y++) {
						if (x >= 0 && y >= 0 && x < 104 && y < 104
								&& cost[x][y] < 100) {
							int i_228_ = 0;
							if (x < destX) {
								i_228_ = destX - x;
							} else if (x > destX + xLength - 1) {
								i_228_ = x - (destX + xLength - 1);
							}
							int i_229_ = 0;
							if (y < destY) {
								i_229_ = destY - y;
							} else if (y > destY + yLength - 1) {
								i_229_ = y - (destY + yLength - 1);
							}
							int i_230_ = i_228_ * i_228_ + i_229_ * i_229_;
							if (i_230_ < i_223_
									|| (i_230_ == i_223_ && (cost[x][y] < thisCost))) {
								i_223_ = i_230_;
								thisCost = cost[x][y];
								curX = x;
								curY = y;
							}
						}
					}
				}
				if (i_223_ == 1000) {
					return;
				}
			} else {
				return;
			}
		}
		tail = 0;
		tileQueueX.set(tail, curX);
		tileQueueY.set(tail++, curY);
		int l5;
		for (int j5 = l5 = via[curX][curY]; curX != mob.getLocation()
				.getLocalX() || curY != mob.getLocation().getLocalY(); j5 = via[curX][curY]) {
			if (j5 != l5) {
				l5 = j5;
				tileQueueX.set(tail, curX);
				tileQueueY.set(tail++, curY);
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
		reset();

		int size = tail--;
		int pathY = mob.getLocation().getRegionY() * 8 + tileQueueY.get(tail);
		int pathX = mob.getLocation().getRegionX() * 8 + tileQueueX.get(tail);
		this.addStep(pathX, pathY);
		Location l = mob.getLocation();
		for (int i = 1; i < size; i++) {
			tail--;
			pathY = l.getRegionY() * 8 + tileQueueY.get(tail);
			pathX = l.getRegionX() * 8 + tileQueueX.get(tail);
			this.addStep(pathX, pathY);
			// c.addToWalkingQueue(localize(pathX,
			// mob.getLocation().getRegionX()),
			// localize(pathY, mob.getLocation().getRegionY()));
		}
	}

	/**
	 * Removes the first waypoint which is only used for calculating directions.
	 * This means walking begins at the correct time.
	 */
	public void finish() {
		waypoints.removeFirst();
	}

	public boolean getClipping(int x, int y, int height, int moveTypeX,
			int moveTypeY) {
		try {
			if (height > 3) {
				height = 0;
			}
			int checkX = (x + moveTypeX);
			int checkY = (y + moveTypeY);
			if (moveTypeX == -1 && moveTypeY == 0) {
				return (RegionClipping.getRegionClipping().getClipping(x, y, height) & 0x1280108) == 0;
			} else if (moveTypeX == 1 && moveTypeY == 0) {
				return (RegionClipping.getRegionClipping().getClipping(x, y, height) & 0x1280180) == 0;
			} else if (moveTypeX == 0 && moveTypeY == -1) {
				return (RegionClipping.getRegionClipping().getClipping(x, y, height) & 0x1280102) == 0;
			} else if (moveTypeX == 0 && moveTypeY == 1) {
				return (RegionClipping.getRegionClipping().getClipping(x, y, height) & 0x1280120) == 0;
			} else if (moveTypeX == -1 && moveTypeY == -1) {
				return ((RegionClipping.getRegionClipping()
						.getClipping(x, y, height) & 0x128010e) == 0
						&& (RegionClipping.getRegionClipping().getClipping(
								checkX - 1, checkY, height) & 0x1280108) == 0 && (RegionClipping
						.getRegionClipping().getClipping(checkX - 1, checkY, height) & 0x1280102) == 0);
			} else if (moveTypeX == 1 && moveTypeY == -1) {
				return ((RegionClipping.getRegionClipping()
						.getClipping(x, y, height) & 0x1280183) == 0
						&& (RegionClipping.getRegionClipping().getClipping(
								checkX + 1, checkY, height) & 0x1280180) == 0 && (RegionClipping
						.getRegionClipping().getClipping(checkX, checkY - 1, height) & 0x1280102) == 0);
			} else if (moveTypeX == -1 && moveTypeY == 1) {
				return ((RegionClipping.getRegionClipping()
						.getClipping(x, y, height) & 0x1280138) == 0
						&& (RegionClipping.getRegionClipping().getClipping(
								checkX - 1, checkY, height) & 0x1280108) == 0 && (RegionClipping
						.getRegionClipping().getClipping(checkX, checkY + 1, height) & 0x1280120) == 0);
			} else if (moveTypeX == 1 && moveTypeY == 1) {
				return ((RegionClipping.getRegionClipping()
						.getClipping(x, y, height) & 0x12801e0) == 0
						&& (RegionClipping.getRegionClipping().getClipping(
								checkX + 1, checkY, height) & 0x1280180) == 0 && (RegionClipping
						.getRegionClipping().getClipping(checkX, checkY + 1, height) & 0x1280120) == 0);
			} else {
				System.out.println("[FATAL ERROR]: At getClipping: " + x + ", "
						+ y + ", " + height + ", " + moveTypeX + ", "
						+ moveTypeY);
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * @return The energy.
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Gets the next point of movement.
	 * 
	 * @return The next point.
	 */
	private Point getNextPoint() {
		/*
		 * Take the next point from the queue.
		 */
		Point p = waypoints.poll();

		/*
		 * Checks if there are no more points.
		 */
		if (p == null || p.dir == -1) {
			/*
			 * Return <code>null</code> indicating no movement happened.
			 */
			return null;
		} else {
			/*
			 * Set the player's new location.
			 */
			int diffX = Constants.DIRECTION_DELTA_X[p.dir];
			int diffY = Constants.DIRECTION_DELTA_Y[p.dir];
			mob.setLocation(mob.getLocation().transform(diffX, diffY, 0));
			/*
			 * And return the direction.
			 */
			return p;
		}
	}

	/**
	 * Checks if the queue is empty.
	 * 
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean isEmpty() {
		return waypoints.isEmpty();
	}

	/**
	 * Checks if any running flag is set.
	 * 
	 * @return <code>true</code. if so, <code>false</code> if not.
	 */
	public boolean isRunning() {
		return runToggled || runQueue;
	}

	/**
	 * Gets the running queue flag.
	 * 
	 * @return The running queue flag.
	 */
	public boolean isRunningQueue() {
		return runQueue;
	}

	/**
	 * Gets the run toggled flag.
	 * 
	 * @return The run toggled flag.
	 */
	public boolean isRunningToggled() {
		return runToggled;
	}

	public int localize(int x, int mapRegion) {
		return x - 8 * mapRegion;
	}

	/**
	 * Processes the next player's movement.
	 */
	public void processNextMovement() {
		/*
		 * Store the teleporting flag.
		 */
		boolean teleporting = mob.hasTeleportTarget();

		/*
		 * The points which we are walking to.
		 */
		Point walkPoint = null, runPoint = null;

		/*
		 * Checks if the player is teleporting i.e. not walking.
		 */
		if (teleporting) {
			/*
			 * Reset the walking queue as it will no longer apply after the
			 * teleport.
			 */
			reset();

			/*
			 * Set the 'teleporting' flag which indicates the player is
			 * teleporting.
			 */
			mob.setTeleporting(true);

			/*
			 * Sets the player's new location to be their target.
			 */
			mob.setLocation(mob.getTeleportTarget());

			/*
			 * Resets the teleport target.
			 */
			mob.resetTeleportTarget();
		} else {
			/*
			 * If the player isn't teleporting, they are walking (or standing
			 * still). We get the next direction of movement here.
			 */
			walkPoint = getNextPoint();

			/*
			 * Technically we should check for running here.
			 */
			if ((runToggled || runQueue)
					&& mob.getWalkingQueue().getEnergy() > 0) {
				runPoint = getNextPoint();
			}

			/*
			 * Now set the sprites.
			 */
			int walkDir = walkPoint == null ? -1 : walkPoint.dir;
			int runDir = runPoint == null ? -1 : runPoint.dir;
			mob.getSprites().setSprites(walkDir, runDir);
		}

		/*
		 * Check for a map region change, and if the map region has changed, set
		 * the appropriate flag so the new map region packet is sent.
		 */
		int diffX = mob.getLocation().getX()
				- mob.getLastKnownRegion().getRegionX() * 8;
		int diffY = mob.getLocation().getY()
				- mob.getLastKnownRegion().getRegionY() * 8;
		boolean changed = false;
		if (diffX < 16) {
			changed = true;
		} else if (diffX >= 88) {
			changed = true;
		}
		if (diffY < 16) {
			changed = true;
		} else if (diffY >= 88) {
			changed = true;
		}
		if (changed) {
			/*
			 * Set the map region changing flag so the new map region packet is
			 * sent upon the next update.
			 */
			mob.setMapRegionChanging(true);
		}

	}

	/**
	 * Resets the walking queue so it contains no more steps.
	 */
	public void reset() {
		runQueue = false;
		waypoints.clear();
		waypoints.add(new Point(mob.getLocation().getX(), mob.getLocation()
				.getY(), -1));
	}

	/**
	 * @param energy
	 *            The energy to set.
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
		if (this.energy < 100) {
			if (mob.getEnergyRestoreTick() == null) {
				EnergyRestoreTick energyRestoreTick = new EnergyRestoreTick(mob);
				mob.setEnergyRestoreTick(energyRestoreTick);
				World.getWorld().submit(energyRestoreTick);
			}
		} else {
			if (mob.getEnergyRestoreTick() != null) {
				mob.getEnergyRestoreTick().stop();
				mob.setEnergyRestoreTick(null);
			}
		}
	}

	/**
	 * Sets the run queue flag.
	 * 
	 * @param runQueue
	 *            The run queue flag.
	 */
	public void setRunningQueue(boolean runQueue) {
		this.runQueue = runQueue;
	}

	/**
	 * Sets the run toggled flag.
	 * 
	 * @param runToggled
	 *            The run toggled flag.
	 */
	public void setRunningToggled(boolean runToggled) {
		this.runToggled = runToggled;
	}

	/**
	 * Gets the queue's size.
	 * 
	 * @return The queue's size.
	 */
	public int size() {
		return waypoints.size();
	}
}
