package org.rs2server.rs2.model.quest;

import org.rs2server.rs2.model.Player;

import java.util.HashMap;
import java.util.Map;
import org.rs2server.rs2.model.quest.impl.LostCity;
import org.rs2server.rs2.model.quest.impl.PriestInPeril;
import org.rs2server.rs2.model.quest.impl.RuneMysteries;
import org.rs2server.rs2.model.quest.impl.TreeGnomeVillage;

/**
 * Works as a quest database and handles quest-related events.
 * 
 * @author Emperor
 */
public class QuestRepository {

	/**
	 * The amount of quests available.
	 */
	public static final int AMOUNT_OF_QUESTS = 136;
	/**
	 * The mapping holding all quest data.
	 */
	public static final Map<Integer, Quest> QUEST_DATABASE = new HashMap<Integer, Quest>();

	static {
		QUEST_DATABASE.put(27, new RuneMysteries());
		QUEST_DATABASE.put(83, new LostCity());
		QUEST_DATABASE.put(99, new PriestInPeril());
		QUEST_DATABASE.put(125, new TreeGnomeVillage());
	}

	/**
	 * Grabs a quest from the mapping.
	 * 
	 * @param buttonId
	 *            The quest button id.
	 * @return The {@code Quest} which id matches the id given, or {@code null}
	 *         if the quest didn't exist.
	 */
	public static Quest get(int buttonId) {
		return QUEST_DATABASE.get(buttonId);
	}

	/**
	 * Handles the quest side bar interfaces and name
	 * 
	 * @param player
	 *            The player.
	 */
	public static void handle(Player player) {
		for (Quest quest : QUEST_DATABASE.values()) {
			String questName = quest.getQuestName();
			String line = questName;

			if (player.getQuestStorage().hasStarted(quest)) {
				line = "<col=d7e335>" + questName + "</col>";
			}
			if (player.getQuestStorage().hasFinished(quest)) {
				line = "<col=8f510>" + questName + "</col>";
			}
			player.getActionSender().sendString(274, quest.getQuestButton(),
					line);
		}
		player.getActionSender().sendString(274, 10,
				"Quest Points: " + player.getQuestPoints());
	}

	/**
	 * Initializes the quests.
	 * 
	 * @return {@code True} if succesful, {@code false} if not.
	 */
	public static boolean init() {
		return true;
	}
}
