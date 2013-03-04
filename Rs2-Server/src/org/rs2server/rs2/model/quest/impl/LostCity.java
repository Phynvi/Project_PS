/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;

/**
 * 
 * @author Stephen Soltys
 */
public class LostCity extends AbstractQuest {

	public static int QUEST_ID = 83;

	@Override
	public void actionButtons(Player player, int interfaceId, int child,
			int button) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean deathHook(Player player) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getAttributeName() {
		return "lostCity";
	}

	@Override
	public void getDialogues(Player player, int npc, int id) {
	}

	@Override
	public int getFinalStage() {
		return 4;
	}

	@Override
	public void getProgress(Player player, boolean show, int progress) {

		switch (progress) {
		case 0:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender()
					.sendString(
							275,
							5,
							player.getSkills().getLevel(Skills.CRAFTING) >= 31 ? "<str>31 Crafting</str>"
									: "31 Crafting");
			player.getActionSender()
					.sendString(
							275,
							6,
							player.getSkills().getLevel(Skills.WOODCUTTING) >= 36 ? "<str>36 Woodcutting</str>"
									: "36 Woodcutting");
			player.getActionSender().sendString(275, 8,
					"I could start this quest by talking to the");
			player.getActionSender().sendString(275, 9,
					"Warrior, located west of the Lumbridge Swamp.");
			break;
		case 1:
			player.getActionSender().sendString(275, 6,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender().sendString(275, 7,
					"<str>Warrior, located west of the Lumbridge Swamp.</str>");
			player.getActionSender().sendString(275, 9,
					"The Warrior told me about Zanaris. There is a Leprechaun");
			player.getActionSender().sendString(275, 10,
					"in a tree nearby. I should try to find him.");
			break;
		case 2:
			player.getActionSender().sendString(275, 6,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender().sendString(275, 7,
					"<str>Warrior, located west of the Lumbridge Swamp.</str>");
			player.getActionSender()
					.sendString(275, 9,
							".<str>The Warrior told me about Zanaris. There is a Leprechaun.</str>");
			player.getActionSender().sendString(275, 10,
					".<str>in a tree nearby. I should try to find him.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"I talked to the Leprechaun. He told me I need a Dramen staff,");
			player.getActionSender().sendString(275, 13,
					"and that Entrana is where I could find a Dramen tree.");
			break;
		case 3:
			player.getActionSender().sendString(275, 6,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender().sendString(275, 7,
					"<str>Warrior, located west of the Lumbridge Swamp.</str>");
			player.getActionSender()
					.sendString(275, 9,
							".<str>The Warrior told me about Zanaris. There is a Leprechaun.</str>");
			player.getActionSender().sendString(275, 10,
					".<str>in a tree nearby. I should try to find him.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I talked to the Leprechaun. He told me I need a Dramen staff,</str>");
			player.getActionSender()
					.sendString(275, 13,
							"<str>and that Entrana is where I could find a Dramen tree.</str>");
			player.getActionSender().sendString(275, 15,
					"I have defeated the Tree Spirit. I should cut the Dramen");
			player.getActionSender().sendString(275, 16,
					"tree and create a staff out of it, then go to the shed.");
			break;
		case 4:
			player.getActionSender().sendString(275, 6,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender().sendString(275, 7,
					"<str>Warrior, located west of the Lumbridge Swamp.</str>");
			player.getActionSender()
					.sendString(275, 8,
							".<str>The Warrior told me about Zanaris. There is a Leprechaun.</str>");
			player.getActionSender().sendString(275, 10,
					".<str>in a tree nearby. I should try to find him.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I talked to the Leprechaun. He told me I need a Dramen staff,</str>");
			player.getActionSender()
					.sendString(275, 13,
							"<str>and that Entrana is where I could find a Dramen tree.</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>I have defeated the Tree Spirit. I should cut the Dramen</str>");
			player.getActionSender()
					.sendString(275, 16,
							"<str>tree and create a staff out of it, then go to the shed.</str>");
			player.getActionSender().sendString(275, 18, "QUEST COMPLETE!");
			break;
		}
	}

	@Override
	public int getQuestButton() {
		return 83;
	}

	@Override
	public String getQuestName() {
		return "Lost City";
	}

	@Override
	public void getRewards(Player player) {
		super.getRewards(player);
		player.getActionSender().sendString(277, 8, "3 Quest Points");
		player.getActionSender().sendString(277, 9, "Access to Zanaris");
	}

	@Override
	public boolean hasRequirements(Player player) {
		if (player.getSkills().getLevel(Skills.CRAFTING) < 31
				|| player.getSkills().getLevel(Skills.WOODCUTTING) < 36) {
			return false;
		}
		return true;
	}

	@Override
	public void killHook(Player player, Mob victim) {
		NPC npc = (NPC) victim;
		if (npc.getDefinition() != null) {
			if (npc.getDefinition().getId() == 655
					&& player.getQuestStorage().getQuestStage(QUEST_ID) == 2) {
				player.setTeleportTarget(Location.create(player.getLocation()
						.getX(), player.getLocation().getY(), 0));
				player.getQuestStorage().setQuestStage(this, 3);
			}
		}
	}

	@Override
	public void objectInteraction(ObjectOptions options, int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int rewardQuestPoints(Player player) {
		return 3;
	}
}
