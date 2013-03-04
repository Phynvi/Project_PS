/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.skills;

import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Stephen Soltys
 */
public class FlaxPicking extends Action {

	private GameObject flax;
	private Mob mob;

	public FlaxPicking(Mob mob, GameObject object) {
		super(mob, 0);
		this.mob = mob;
		this.flax = object;
	}

	@Override
	public void execute() {
		if (!getMob().getInventory().hasRoomFor(new Item(1779))) {
			getMob().getActionSender().sendString(210, 0,
					"You don't have any room to pick more flax.");
			getMob().getActionSender().sendChatboxInterface(210);
			getMob().playAnimation(Animation.create(-1));
			this.stop();
			return;
		}
		if (flax.getCurrentHealth() == 0) {
			flax.setCurrentHealth(3);
		}
		flax.decreaseCurrentHealth(1);
		if (flax.getCurrentHealth() <= 0) {
			flax.setCurrentHealth(3);
			World.getWorld().unregister(flax, true);
			World.getWorld().submit(new Tickable(100) {

				@Override
				public void execute() {
					World.getWorld().register(flax);
					Region[] regions = World.getWorld().getRegionManager()
							.getSurroundingRegions(flax.getLocation());
					for (Region r : regions) {
						for (Player p : r.getPlayers()) {
							p.getActionSender().sendObject(flax);
						}
					}
					this.stop();
				}
			});
		}
		mob.getActionSender().sendMessage("You pick some flax.");
		getMob().playAnimation(Animation.create(7270));
		mob.getInventory().add(new Item(1779));
		this.stop();
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
