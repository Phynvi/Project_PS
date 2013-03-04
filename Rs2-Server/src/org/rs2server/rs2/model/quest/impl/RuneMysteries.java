/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;

/**
 * 
 * @author Stephen Soltys
 */
public class RuneMysteries extends AbstractQuest {

	public static int QUEST_ID = 27;

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
		return "runeMysteries";
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
			player.getActionSender().sendString(275, 5,
					"I could start this quest by talking to the");
			player.getActionSender()
					.sendString(275, 6,
							"Duke Horacio, located in the 2nd floor of Lumbridge Castle.");
			break;
		case 1:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender()
					.sendString(275, 6,
							"<str>Duke Horacio, located in the 2nd floor of Lumbridge Castle.</str>");
			player.getActionSender().sendString(275, 8,
					"The Duke gave me a talisman to bring to the");
			player.getActionSender().sendString(275, 9,
					"head wizard in the Wizards Tower.");
			break;
		case 2:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender()
					.sendString(275, 6,
							"<str>Duke Horacio, located in the 2nd floor of Lumbridge Castle.</str>");
			player.getActionSender().sendString(275, 8,
					"<str>The Duke gave me a talisman to bring to the</str>");
			player.getActionSender().sendString(275, 9,
					"<str>head wizard in the Wizards Tower.</str>");
			player.getActionSender().sendString(275, 11,
					"The head wizard wants me to bring the package he");
			player.getActionSender().sendString(275, 12,
					"gave me to Aubury in Varrock.");
			break;
		case 3:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender()
					.sendString(275, 6,
							"<str>Duke Horacio, located in the 2nd floor of Lumbridge Castle.</str>");
			player.getActionSender().sendString(275, 8,
					"<str>The Duke gave me a talisman to bring to the</str>");
			player.getActionSender().sendString(275, 9,
					"<str>head wizard in the Wizards Tower.</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>The head wizard wants me to bring the package he</str>");
			player.getActionSender().sendString(275, 12,
					"<str>gave me to Aubury in Varrock.</str>");
			player.getActionSender().sendString(275, 14,
					"Aubury wants me to give the research notes to the");
			player.getActionSender().sendString(275, 15, "head wizard.");
			break;
		case 4:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to the</str>");
			player.getActionSender()
					.sendString(275, 6,
							"<str>Duke Horacio, located in the 2nd floor of Lumbridge Castle.</str>");
			player.getActionSender().sendString(275, 8,
					"<str>The Duke gave me a talisman to bring to the</str>");
			player.getActionSender().sendString(275, 9,
					"<str>head wizard in the Wizards Tower.</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>The head wizard wants me to bring the package he</str>");
			player.getActionSender().sendString(275, 12,
					"<str>gave me to Aubury in Varrock.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>Aubury wants me to give the research notes to the</str>");
			player.getActionSender().sendString(275, 15,
					"<str>head wizard.</str>");
			player.getActionSender().sendString(275, 17, "QUEST COMPLETE!");
			break;
		}
	}

	@Override
	public int getQuestButton() {
		return 27;
	}

	@Override
	public String getQuestName() {
		return "Rune Mysteries";
	}

	@Override
	public void getRewards(Player player) {
		super.getRewards(player);
		player.getActionSender().sendString(277, 8, "1 Quest Point");
		player.getActionSender().sendString(277, 9, "Access to RuneCrafting");
		player.getActionSender().sendString(277, 10, "Air talisman");
	}

	@Override
	public boolean hasRequirements(Player player) {
		return true;
	}

	@Override
	public void killHook(Player player, Mob victim) {
	}

	@Override
	public void objectInteraction(ObjectOptions options, int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int rewardQuestPoints(Player player) {
		return 1;
	}
}
