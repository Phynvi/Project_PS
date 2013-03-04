package org.rs2server.rs2.model;

import org.rs2server.rs2.model.Animation.FacialAnimation;
import org.rs2server.rs2.net.ActionSender.DialogueType;

public class Cats {

	private Player player;

	public Cats(Player player) {
		this.player = player;
	}

	public void dropCat(Item catItem, int slot) {
		player.getInventory().remove(catItem, slot);
		String[] options = { "Pick-up", "Talk-to", "Interact-with" };
		NPCDefinition def = new NPCDefinition(768, "Cat", "Your cat!", 1, 1,
				options);
		Location catLoc = Location.create(player.getLocation().getX() - 1,
				player.getLocation().getY(), player.getHeight());
		Cat cat = new Cat(def, catLoc, catLoc, catLoc, player.getDirection());
		World.getWorld().register(cat);
		cat.face(player.getLocation());
		player.getActionSender().sendDialogue("",
				DialogueType.MESSAGE_MODEL_LEFT, catItem.getId(),
				FacialAnimation.DEFAULT, "You put your cat down.");
		player.getInterfaceState().setNextDialogueId(0, 97);
	}
}
