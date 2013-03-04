/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.Player;

/**
 * 
 * @author Stephen Soltys
 */
public class PriestInPeril extends AbstractQuest {

	public static final int QUEST_ID = 99;

	@Override
	public void actionButtons(Player player, int interfaceId, int child,
			int button) {
	}

	@Override
	public boolean deathHook(Player player) {
		return true;
	}

	@Override
	public String getAttributeName() {
		return "priestInPeril";
	}

	@Override
	public void getDialogues(Player player, int npc, int id) {
	}

	@Override
	public int getFinalStage() {
		return 7;
	}

	@Override
	public void getProgress(Player player, boolean show, int progress) {

		switch (progress) {
		case 0:
			player.getActionSender().sendString(275, 5,
					"I must be able to defeat a level 30.");
			player.getActionSender().sendString(275, 7,
					"I could start this quest by talking to");
			player.getActionSender().sendString(275, 8,
					"King Roald, located in the Varrock Castle.");
			break;
		case 1:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender().sendString(275, 9,
					"The King told me to visit the temple east of here.");
			player.getActionSender().sendString(275, 10,
					"He told me to make sure the priest Drezel was okay.");
			break;
		case 2:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"I've talked to Drezel. He told me to kill the dog beneath");
			player.getActionSender().sendString(275, 13, "the mausoleum.");
			break;
		case 3:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I've talked to Drezel. He told me to kill the dog beneath</str>");
			player.getActionSender().sendString(275, 13,
					"<str>the mausoleum.</str>");
			player.getActionSender().sendString(275, 15,
					"I should talk to Drezel, and then report to the King");
			player.getActionSender().sendString(275, 16,
					"to tell him what I have done.");
			break;
		case 4:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I've talked to Drezel. He told me to kill the dog beneath</str>");
			player.getActionSender().sendString(275, 13,
					"<str>the mausoleum.</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>I should talk to Drezel, and then report to the King</str>");
			player.getActionSender().sendString(275, 16,
					"<str>to tell him what I have done.</str>");
			player.getActionSender()
					.sendString(275, 18,
							"I've been tricked! The King told me the dog was a guard dog");
			player.getActionSender()
					.sendString(275, 19,
							"and that now Varrock could be attacked! I should fix this.");
			break;
		case 5:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I've talked to Drezel. He told me to kill the dog beneath</str>");
			player.getActionSender().sendString(275, 13,
					"<str>the mausoleum.</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>Drezel told me that I have done good. He also told me to</str>");
			player.getActionSender().sendString(275, 16,
					"<str>tell the King what I have done.</str>");
			player.getActionSender()
					.sendString(275, 18,
							"<str>I've been tricked! The King told me the dog was a guard dog</str>");
			player.getActionSender()
					.sendString(275, 19,
							"<str>and that now Varrock could be attacked! I should fix this.</str>");
			player.getActionSender()
					.sendString(275, 21,
							"I've unlocked Drezel, and poured blessed water on the vampire's coffin.");
			player.getActionSender().sendString(275, 22,
					"I should go meet Drezel under the mausoleum.");
			break;
		case 6:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I've talked to Drezel. He told me to kill the dog beneath</str>");
			player.getActionSender().sendString(275, 13,
					"<str>the mausoleum.</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>Drezel told me that I have done good. He also told me to</str>");
			player.getActionSender().sendString(275, 16,
					"<str>tell the King what I have done.</str>");
			player.getActionSender()
					.sendString(275, 18,
							"<str>I've been tricked! The King told me the dog was a guard dog</str>");
			player.getActionSender()
					.sendString(275, 19,
							"<str>and that now Varrock could be attacked! I should fix this.</str>");
			player.getActionSender()
					.sendString(
							275,
							21,
							"<str>I've unlocked Drezel, and poured blessed water on the vampire's coffin.</str>");
			player.getActionSender().sendString(275, 22,
					"<str>I should go meet Drezel under the mausoleum.</str>");
			player.getActionSender().sendString(275, 24,
					"Drezel told me to give him 25 Rune Essence so he can");
			player.getActionSender().sendString(275, 25,
					"purify the river and remove the potion from it.");
			break;
		case 7:
			player.getActionSender().sendString(275, 5,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender().sendString(275, 6,
					"<str>King Roald, located in the Varrock Castle.</str>");
			player.getActionSender()
					.sendString(275, 9,
							"<str>The King told me to visit the temple east of here.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>He told me to make sure the priest Drezel was okay.</str>");
			player.getActionSender()
					.sendString(275, 12,
							"<str>I've talked to Drezel. He told me to kill the dog beneath</str>");
			player.getActionSender().sendString(275, 13,
					"<str>the mausoleum.</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>Drezel told me that I have done good. He also told me to</str>");
			player.getActionSender().sendString(275, 16,
					"<str>tell the King what I have done.</str>");
			player.getActionSender()
					.sendString(275, 18,
							"<str>I've been tricked! The King told me the dog was a guard dog</str>");
			player.getActionSender()
					.sendString(275, 19,
							"<str>and that now Varrock could be attacked! I should fix this.</str>");
			player.getActionSender()
					.sendString(
							275,
							21,
							"<str>I've unlocked Drezel, and poured blessed water on the vampire's coffin.</str>");
			player.getActionSender().sendString(275, 22,
					"<str>I should go meet Drezel under the mausoleum.</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>Drezel told me to give him 25 Rune Essence so he can</str>");
			player.getActionSender()
					.sendString(275, 25,
							"<str>purify the river and remove the potion from it.</str>");
			player.getActionSender().sendString(275, 27, "QUEST COMPLETE!");
			break;
		}
	}

	@Override
	public int getQuestButton() {
		return 99;
	}

	@Override
	public String getQuestName() {
		return "Priest in Peril";
	}

	@Override
	public void getRewards(Player player) {
		super.getRewards(player);
		player.getActionSender().sendString(277, 8, "1 Quest Point");
		player.getActionSender().sendString(277, 9, "1406 Prayer XP");
		player.getActionSender().sendString(277, 10, "Wolfbane Dagger");
		player.getActionSender().sendString(277, 11, "Route to Canafis");
	}

	@Override
	public boolean hasRequirements(Player player) {
		return true;
	}

	@Override
	public void killHook(Player player, Mob victim) {
		if (victim.isNPC()) {
			NPC npc = (NPC) victim;
			if (npc.getDefinition() != null) {
				if (npc.getDefinition().getId() == 1047
						&& player.getQuestStorage().getQuestStage(QUEST_ID) == 2) {
					player.getQuestStorage().setQuestStage(this, 3);
				}
			}
		}
	}

	@Override
	public void objectInteraction(ObjectOptions options, int id) {
	}

	@Override
	public int rewardQuestPoints(Player player) {
		return 1;
	}
}
