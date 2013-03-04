package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.quest.Quest;
import org.rs2server.rs2.model.quest.QuestRepository;

/**
 * Handles global quest settings User: Physiologus Date: 4/11/12 Time: 12:25 AM
 */
public abstract class AbstractQuest implements Quest {

	@Override
	public void end(Player player) {
		player.removeAttribute(getAttributeName());
		player.getActionSender().sendInterface(277, true);
		player.getActionSender().sendString(277, 2,
				"You have completed " + getQuestName() + "!");
		player.setQuestPoints(player.getQuestPoints()
				+ rewardQuestPoints(player));
		getRewards(player);
		QuestRepository.handle(player);
		player.getActionSender().sendMessage(
				"You've completed " + getQuestName() + "!");
	}

	@Override
	public void getProgress(Player player, boolean show, int progress) {
		for (int i = 0; i < 300; i++) {
			player.getActionSender().sendString(275, i, "");
		}
		if (show) {
			player.getActionSender().sendInterface(275, true);
		}
	}

	@Override
	public void getRewards(Player player) {
		for (int i = 7; i < 15; i++) {
			player.getActionSender().sendString(277, i, "");
		}
		player.getActionSender().sendString(277, 7, "Rewards:");
		player.getActionSender().sendString(277, 4,
				"Quest Points: " + rewardQuestPoints(player));
		player.getActionSender().sendString(277, 5, "");// +
														// player.getQuestPoints());
	}

	@Override
	public void start(Player player) {
		if (!hasRequirements(player)) {
			player.getActionSender().sendMessage(
					"You don't meet the requirements for this quest.");
			return;
		}
		player.getQuestStorage().setQuestStage(this, 1);
		QuestRepository.handle(player);
		player.setAttribute(getAttributeName(), true);
	}
}
