/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model;

import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Stephen Soltys
 */
public class Music {

	/**
	 * Represents the music tracks of the game. If the track name is defined, it
	 * is the correct Runescape track name.
	 * 
	 * @author Josh Mai
	 * 
	 */
	public enum Tracks {
		AUTUMN_VOYAGE(2), UNKNOWN_LAND(3), KINGDOM(9), LONG_WAY_HOME(12), HORIZON(
				18), LULLABY(20), SEA_SHANTY2(35), BAROQUE(40), HARMONY2(46), LASTING(
				60), NEWBIE_MELODY(62), FANFARE(72), HARMONY(76), INSPIRATION(
				96), FOREVER(98), CAMELOT(104), GREATNESS(116), FISHING(119), GARDEN(
				125), CRYSTAL_SWORD(169);

		/**
		 * Gets rights by a specific integer.
		 * 
		 * @param value
		 *            The integer returned by {@link #toInteger()}.
		 * @return The track number.
		 */
		public static Tracks getTracks(int value) {
			if (value == 2) {
				return AUTUMN_VOYAGE;
			} else {
				return HARMONY;
			}
		}

		/**
		 * The integer representing this track number.
		 */
		private int value;

		/**
		 * Creates a track number.
		 * 
		 * @param value
		 *            The integer representing this track number.
		 */
		private Tracks(int value) {
			this.value = value;
		}

		/**
		 * Gets an integer representing track number.
		 * 
		 * @return An integer representing this track number.
		 */
		public int toInteger() {
			return value;
		}
	}

	private MusicTrack currentTrack = null;
	private Tickable t = new Tickable(25) {

		@Override
		public void execute() {
			currentTrack = null;
		}
	};
	private Mob mob;

	public Music(Mob mob) {
		this.mob = mob;
	}

	public MusicTrack getCurrentTrack() {
		return currentTrack;
	}

	public void setCurrentTrack(MusicTrack currentTrack) {
		Player p = (Player) mob;
		this.currentTrack = currentTrack;
		if (World.getWorld().getTickableManager().getTickables().contains(t)) {
			World.getWorld().getTickableManager().getTickables().remove(t);
		}
		World.getWorld().getTickableManager().submit(t);
		p.getActionSender().playMusic(currentTrack.getId());
	}

}
