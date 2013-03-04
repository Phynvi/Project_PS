package org.rs2server.rs2.clipping;

import java.util.HashMap;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.region.Region;

/**
 * 
 * @author Josh Mai
 * 
 */
public class TileControl {

	private static TileControl singleton = null;

	public static int calculateDistance(Location pointA, Location pointB) {
		int offsetX = Math.abs(pointA.getX() - pointB.getX());
		int offsetY = Math.abs(pointA.getY() - pointB.getY());
		return offsetX > offsetY ? offsetX : offsetY;
	}

	public static int calculateDistance(Mob mobA, Mob mobB) {
		Location[] pointsA = getHoveringTiles(mobA);
		Location[] pointsB = getHoveringTiles(mobB);

		int lowestCount = 16;
		int distance = 16;

		for (Location pointA : pointsA) {
			for (Location pointB : pointsB) {
				if (pointA.equals(pointB)) {
					return 0;
				}
				distance = calculateDistance(pointA, pointB);
				if (distance < lowestCount) {
					lowestCount = distance;
				}
			}
		}

		return lowestCount;
	}

	public static Location[] getHoveringTiles(GameObject obj) {
		return getHoveringTiles(obj, obj.getLocation());
	}

	public static Location[] getHoveringTiles(GameObject obj, Location location) {
		int bufX = obj.getDefinition().getSizeX();
		int bufY = obj.getDefinition().getSizeY();
		int offset = 0;

		Location[] locations = new Location[bufX * bufY];
		if (locations.length == 1) {
			locations[offset] = location;
		} else {
			for (int x = 0; x < bufX; x++) {
				for (int y = 0; y < bufY; y++) {
					locations[(offset++)] = Location.create(
							location.getX() + x, location.getY() + y,
							location.getZ());
				}
			}
		}
		return locations;
	}

	public static Location[] getHoveringTiles(Mob mob) {
		return getHoveringTiles(mob, mob.getLocation());
	}

	public static Location[] getHoveringTiles(Mob mob, Location location) {
		if (mob == null) {
			return null;
		}
		int buf = 1;
		int offset = 0;
		if (mob.isNPC()) {
			buf = ((NPC) mob).getDefinition().getSize();
		}
		Location[] locations = new Location[buf * buf];
		if (locations.length == 1) {
			locations[offset] = location;
		} else {
			for (int x = 0; x < buf; x++) {
				for (int y = 0; y < buf; y++) {
					locations[(offset++)] = Location.create(
							location.getX() + x, location.getY() + y,
							location.getZ());
				}
			}
		}
		return locations;
	}

	public static TileControl getSingleton() {
		if (singleton == null) {
			singleton = new TileControl();
		}
		return singleton;
	}

	private HashMap<Mob, Location[]> occupiedLocations = new HashMap<Mob, Location[]>();

	public Location[] getOccupiedLocations(Mob mob) {
		return this.occupiedLocations.get(mob);
	}

	public boolean locationOccupied(Location[] locations, Mob mob) {
		if ((locations == null) || (mob == null)) {
			return true;
		}
		Location[] npcLocations = null;
		for (Region r : World.getWorld().getRegionManager()
				.getSurroundingRegions(mob.getLocation())) {
			for (NPC npc : r.getNpcs()) {
				if ((mob.isNPC()) && ((npc == null) || (npc == mob))) {
					continue;
				}
				npcLocations = getOccupiedLocations(npc);
				if (npcLocations != null) {
					for (Location loc : locations) {
						for (Location loc2 : npcLocations) {
							if (loc.equals(loc2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public void setOccupiedLocation(Mob mob, Location[] locations) {
		if ((mob == null) || (locations == null)) {
			return;
		}
		this.occupiedLocations.remove(mob);
		this.occupiedLocations.put(mob, locations);
	}
}
