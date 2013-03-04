package org.rs2server.rs2.model;

import org.rs2server.rs2.model.region.Region;

/**
 * Represents a game object in the world with a location.
 * 
 * @author Graham Edgecombe
 * 
 */
public abstract class Entity {

	/**
	 * The default, i.e. spawn, location.
	 */
	public static final Location DEFAULT_LOCATION = Location.create(3222, 3218,
			0);
	// public static final Location DEFAULT_LOCATION = Location.create(3094,
	// 3107,
	// 0);

	/**
	 * The current location.
	 */
	private Location location;

	/**
	 * The direction this entity is facing. 7=SouthEast 6=South 5=SouthWest
	 * 4=East 3=West 2=NorthEast 1=North 0=NorthWest
	 */
	private int direction = WalkingQueue.SOUTH;

	/**
	 * The current region.
	 */
	private Region currentRegion;

	/**
	 * Adds this entity to the specified region.
	 * 
	 * @param region
	 *            The region.
	 */
	public abstract void addToRegion(Region region);

	/**
	 * Gets the centre location of the entity.
	 * 
	 * @return The centre location of the entity.
	 */
	public abstract Location getCentreLocation();

	/**
	 * Gets the client-side index of an entity.
	 * 
	 * @return The client-side index.
	 */
	public abstract int getClientIndex();

	/**
	 * Gets the direction this entity is facing.
	 * 
	 * @return The direction this entity is facing.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Gets the width of the entity.
	 * 
	 * @return The width of the entity.
	 */
	public abstract int getHeight();

	/**
	 * Gets the current location.
	 * 
	 * @return The current location.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Gets the current region.
	 * 
	 * @return The current region.
	 */
	public Region getRegion() {
		return currentRegion;
	}

	/**
	 * Gets the width of the entity.
	 * 
	 * @return The width of the entity.
	 */
	public abstract int getWidth();

	/**
	 * Is this entity an NPC.
	 */
	public abstract boolean isNPC();

	/**
	 * Is this entity an NPC.
	 */
	public abstract boolean isObject();

	/**
	 * Is this entity a player.
	 */
	public abstract boolean isPlayer();

	/**
	 * Plays graphics.
	 * 
	 * @param graphic
	 *            The graphics.
	 */
	public void playProjectile(Projectile projectile) {
		for (Region r : currentRegion.getSurroundingRegions()) {
			for (Player p : r.getPlayers()) {
				if (p.getLocation().isWithinDistance(this.getLocation())) {
					p.getActionSender().sendProjectile(projectile.getStart(),
							projectile.getFinish(), projectile.getId(),
							projectile.getDelay(), projectile.getAngle(),
							projectile.getSpeed(), projectile.getStartHeight(),
							projectile.getEndHeight(), projectile.getLockon(),
							projectile.getSlope(), projectile.getRadius());
				}
			}
		}
	}

	/**
	 * Removes this entity from the specified region.
	 * 
	 * @param region
	 *            The region.
	 */
	public abstract void removeFromRegion(Region region);

	/**
	 * Sets the direction that the entity is facing.
	 * 
	 * @param direction
	 *            The direction to set.
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Sets the current location.
	 * 
	 * @param location
	 *            The current location.
	 */
	public void setLocation(Location location) {
		this.location = location;
		Region newRegion = World.getWorld().getRegionManager()
				.getRegionByLocation(location);
		if (newRegion != getRegion()) {
			if (getRegion() != null) {
				removeFromRegion(getRegion());
			}
			setRegion(newRegion);
			addToRegion(getRegion());
		}
	}

	/**
	 * Sets the current region.
	 * 
	 * @param region
	 *            The region to set.
	 */
	public void setRegion(Region region) {
		this.currentRegion = region;
	}

}
