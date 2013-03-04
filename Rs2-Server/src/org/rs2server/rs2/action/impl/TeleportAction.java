package org.rs2server.rs2.action.impl;

import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Graphic;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Sound;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Josh Mai
 * 
 */
public class TeleportAction extends Action {

	public static Location getAnnakarlLocation() {
		return ANNAKARL;
	}

	public static Location getApeAtollLocation() {
		return APE_ATOLL;
	}

	public static Location getArdougneLocation() {
		return ARDOUGNE;
	}

	public static Location getCamelotLocation() {
		return CAMELOT;
	}

	public static Location getCarrallangarLocation() {
		return CARRALLANGAR;
	}

	public static Location getDareeyakLocation() {
		return DAREEYAK;
	}

	public static Location getFaladorLocation() {
		return FALADOR;
	}

	public static Location getGhorrockLocation() {
		return GHORROCK;
	}

	public static Location getKharyrllLocation() {
		return KHARYRLL;
	}

	public static Location getLassarLocation() {
		return LASSAR;
	}

	public static Location getLumbridgeLocation() {
		return LUMBRIDGE;
	}

	public static Location getPaddewwaLocation() {
		return PADDEWWA;
	}

	public static Location getSenntistenLocation() {
		return SENNTISTEN;
	}

	public static Location getTrollheimLocation() {
		return TROLLHEIM;
	}

	public static Location getVarrockLocation() {
		return VARROCK;
	}

	public static Location getWatchtowerLocation() {
		return WATCHTOWER;
	}

	protected Mob mob = null;
	protected Location location = null;
	private static TeleportAction instance = null;

	/**
	 * Teleport location data members.
	 */
	public static final Location VARROCK = Location.create(3213, 3423, 0);

	public static final Location LUMBRIDGE = Location.create(3222, 3218, 0);

	public static final Location FALADOR = Location.create(2963, 3380, 0);

	public static final Location CAMELOT = Location.create(2757, 3477, 0);

	public static final Location ARDOUGNE = Location.create(2662, 3307, 0);

	public static final Location WATCHTOWER = Location.create(2544, 3112, 2);

	public static final Location TROLLHEIM = Location.create(2911, 3611, 0);

	public static final Location APE_ATOLL = Location.create(2756, 2780, 0);

	public static final Location PADDEWWA = Location.create(3132, 9911, 0);

	public static final Location SENNTISTEN = Location.create(3320, 3388, 0);

	public static final Location KHARYRLL = Location.create(3491, 3484, 0);

	public static final Location LASSAR = Location.create(3007, 3477, 0);

	public static final Location DAREEYAK = Location.create(3109, 3692, 0);

	public static final Location CARRALLANGAR = Location.create(3161, 3671, 0);

	public static final Location ANNAKARL = Location.create(3288, 3886, 0);

	public static final Location GHORROCK = Location.create(3091, 3963, 0);

	/**
	 * An instance of the teleport action
	 * 
	 * @param mob
	 * @return the instance.
	 */
	public static TeleportAction getTeleportAction(Mob mob) {
		if (instance == null) {
			instance = new TeleportAction(mob);
		}
		return instance;
	}

	public TeleportAction(Mob mob) {
		super(mob, 20);
		this.mob = mob;
	}

	public void castTeleport(Location location) {
		this.location = location;
		this.execute();
	}

	@Override
	public void execute() {
		if (getMob().canTeleport()) {
			int bookId = getMob().getCombatState().getSpellBook();
			switch (bookId) {
			case 0:
				getMob().playSound(Sound.create(200, (byte) 1, 0));
				getMob().playGraphics(Graphic.create(308, 25, 100));
				getMob().playAnimation(Animation.create(714));
				getMob().getCombatState().setCanTeleport(false);
				/**
				 * Stops the mob from teleporting for 4 cycles (2.4 secs)
				 * 
				 */
				World.getWorld().submit(new Tickable(4) {
					@Override
					public void execute() {
						getMob().playSound(Sound.create(201, (byte) 1, 0));
						getMob().playGraphics(Graphic.create(-1));
						getMob().playAnimation(Animation.create(715));
						getMob().setTeleportTarget(location);
						getMob().getCombatState().setCanTeleport(true);
						this.stop();
					}
				});
				this.stop();
				break;
			case 1:
				getMob().playSound(Sound.create(200, (byte) 1, 0));
				getMob().playGraphics(Graphic.create(392, 0, 0));
				getMob().playAnimation(Animation.create(714));
				getMob().getCombatState().setCanTeleport(false);
				/**
				 * Stops the mob from teleporting for 4 cycles (2.4 secs)
				 * 
				 */
				World.getWorld().submit(new Tickable(4) {
					@Override
					public void execute() {
						getMob().playSound(Sound.create(201, (byte) 1, 0));
						getMob().playGraphics(Graphic.create(455, 25, 0));
						getMob().playAnimation(Animation.create(715));
						getMob().setTeleportTarget(location);
						getMob().getCombatState().setCanTeleport(true);
						this.stop();
					}
				});
				this.stop();
				break;
			}
		}

	}

	@Override
	public AnimationPolicy getAnimationPolicy() {
		return AnimationPolicy.RESET_ALL;
	}

	@Override
	public CancelPolicy getCancelPolicy() {
		return CancelPolicy.ALWAYS;
	}

	@Override
	public StackPolicy getStackPolicy() {
		return StackPolicy.NEVER;
	}

}
