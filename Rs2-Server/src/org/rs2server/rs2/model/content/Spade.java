package org.rs2server.rs2.model.content;

import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.rs2.model.minigame.barrows.Barrows;

public class Spade {

	public static void dig(final Player player) {
		World.getWorld().submit(new Tickable(1) {
			@Override
			public void execute() {
				digEffects(player);
				stop();
			}
		});
	}

	public static void digEffects(final Player player) {
		player.playAnimation(Animation.create(830));
		/*
		 * Mole holes.
		 */
		if (player.getLocation().equals(Location.create(2996, 3377))
				|| player.getLocation().equals(Location.create(2999, 3375))) {
			player.setTeleportTarget(Location.create(1752, 5237));
			player.getActionSender().sendMessage(
					"You drop into deep underground!");
			return;
		}
		if (Barrows.getBarrows().handleSpade(player)) {
			return;
		} else {
			player.getActionSender().sendMessage("You dont find anything.");
		}
	}
}
