/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model;

/**
 * 
 * @author Stephen Soltys
 */
public class MusicTrack {

	private int id;
	private Location min, max;

	public MusicTrack() {

	}

	public MusicTrack(int id, Location min, Location max) {
		this.id = id;
		this.max = max;
		this.min = min;
	}

	public int getId() {
		return id;
	}

	public Location getMax() {
		return max;
	}

	public Location getMin() {
		return min;
	}

	public boolean isInRegion(Location l) {
		int x = l.getX();
		int y = l.getY();
		return x >= min.getX() && x <= max.getX() && y >= min.getY()
				&& y <= max.getY();
	}
}
