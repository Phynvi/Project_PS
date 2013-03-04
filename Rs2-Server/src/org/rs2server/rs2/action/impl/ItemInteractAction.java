package org.rs2server.rs2.action.impl;

import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.tickable.Tickable;

/**
 * 
 * @author Josh Mai - Sneakyhearts
 * 
 */
public class ItemInteractAction extends Action {

	/**
	 * The item interacted with
	 */
	private Item item;
	private String scriptName = "item_interact_";

	public ItemInteractAction(Mob mob, Item item) {
		super(mob, 30);
		this.setItem(item);
		this.setScriptName(scriptName.concat(item.getDefinition().getName()
				.toLowerCase().replace(" ", "_")));
	}

	@Override
	public void execute() {
		if (getMob().getAttribute("interacting") != null) {
			return;
		}
		getMob().setAttribute("interacting", true);
		if (getMob().getAttribute("sneaky") != null) {
			System.out.println("Attempting to call item interaction script "
					+ getScriptName());
		}
		if (!ScriptManager.getScriptManager().call(getScriptName(), getMob(),
				getItem())) {
			System.out.println("Item interaction script not found !");

		}
		World.getWorld().submit(new Tickable(3) {
			@Override
			public void execute() {
				getMob().removeAttribute("interacting");
				this.stop();
			}
		});
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

	public Item getItem() {
		return item;
	}

	public String getScriptName() {
		return scriptName;
	}

	@Override
	public StackPolicy getStackPolicy() {
		return StackPolicy.NEVER;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

}
