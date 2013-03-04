package org.rs2server.rs2.model.quest;

import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.Player;

/**
 * Quest Interface
 * 
 * @author Josh Mai
 * 
 */
public interface Quest {
	
	enum ObjectOptions {
		ONE, TWO, THREE;
	}

	/**
	 * The custom button actions.
	 * 
	 * @param player
	 *            The player.
	 * @param interfaceId
	 *            The interface id.
	 * @param child
	 *            The child id.
	 * @param button
	 *            The button Id.
	 */
	public abstract void actionButtons(Player player, int interfaceId,
			int child, int button);

	/**
	 * Performs checks when a player dies in the minigame.
	 * 
	 * @param player
	 *            The player.
	 * @return True = don't drop loot, false = do drop loot.
	 */
	public abstract boolean deathHook(Player player);

	/**
	 * Ends the quest.
	 * 
	 * @param player
	 *            The player.
	 */
	public abstract void end(Player player);

	/**
	 * Gets the attribute name.
	 */
	public abstract String getAttributeName();

	/**
	 * Gets the dialogues for the quest
	 * 
	 * @param player
	 *            The player to send dialogue to
	 * @param npc
	 *            The npc.
	 * @param id
	 *            The dialogue id(s)
	 */
	public abstract void getDialogues(Player player, int npc, int id);

	/**
	 * The last stage
	 */
	public abstract int getFinalStage();

	/**
	 * Gets the progress of the quest
	 * 
	 * @param player
	 *            The player
	 * @param show
	 *            {@code true} Shows interface {@code false} doesn't show
	 * @param progress
	 *            The progress stage
	 */
	public abstract void getProgress(Player player, boolean show, int progress);

	/**
	 * The quest interface button. Also known as the quest Id.
	 */
	public abstract int getQuestButton();

	/**
	 * Gets quest name (for quest sidebar)
	 */
	public abstract String getQuestName();

	/**
	 * Adds rewards.
	 * 
	 * @param player
	 *            The player.
	 */
	public abstract void getRewards(Player player);

	/**
	 * Gets the requirements for the quest.
	 */
	public abstract boolean hasRequirements(Player player);

	/**
	 * Performs checks for when the player kills another mob.
	 * 
	 * @param player
	 *            The player.
	 * @param victim
	 *            The victim.
	 */
	public abstract void killHook(Player player, Mob victim);

	/**
	 * The custom object actions.
	 * 
	 * @param options
	 *            The options.
	 * @param id
	 *            The object id.
	 */
	public abstract void objectInteraction(ObjectOptions options, int id);

	/**
	 * The quest points to award.
	 */
	public abstract int rewardQuestPoints(Player player);

	/**
	 * Begins the quest.
	 * 
	 * @param player
	 *            The player.
	 */
	public abstract void start(Player player);
}
