package org.rs2server.rs2.model.skills;

import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;

/**
 * 
 * @author Killamess Lets us have a permit for a skill that is running If the
 *         skill is still running but the permit has expired we know to end
 *         'this' skill.
 * 
 */
public class Permit {

	private Mob mob;

	private int value;

	private Location location;

	private boolean active;

	public Permit(Mob mob, int value, Location location, boolean active) {
		this.mob = mob;
		this.value = value;
		this.location = location;
		this.active = active;
	}

	public void disablePermit() {
		this.active = false;
	}

	public Location getLocation() {
		return location;
	}

	public Mob getMob() {
		return mob;
	}

	public int getValue() {
		return value;
	}

	public boolean isActive() {
		return active;
	}
}
