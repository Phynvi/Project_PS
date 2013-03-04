package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.Mob;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.NPCDefinition;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.Music.Tracks;
import org.rs2server.rs2.model.quest.QuestRepository;

/**
 * 
 * @author Josh Mai
 * 
 */
public class TutorialIsland extends AbstractQuest {

	public static TutorialIsland getTutorialIsland() {
		return new TutorialIsland();
	}

	protected final int[] icons = Constants.SIDEBAR_INTERFACES[0];
	protected final int[] interfaces = Constants.SIDEBAR_INTERFACES[1];
	protected final int tutorialGuide = 945;
	protected final int characterDesignInterface = 269;
	Location tutorialStartLocation = Location.create(3094, 3017, 0);
	Location tutorialGuideLocation = Location.create(3094, 3105, 0);

	@Override
	public void actionButtons(Player player, int interfaceId, int child,
			int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deathHook(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

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
	public String getAttributeName() {
		return "TutorialIsland";
	}

	public int getCharacterDesignInterface() {
		return characterDesignInterface;
	}

	@Override
	public void getDialogues(Player player, int npc, int id) {
	}

	@Override
	public int getFinalStage() {
		return 100;
	}

	@Override
	public int getQuestButton() {
		return 0;
	}

	@Override
	public String getQuestName() {
		return "Tutorial Island";
	}

	public int getTutorialGuide() {
		return tutorialGuide;
	}

	public Location getTutorialGuideLocation() {
		return tutorialGuideLocation;
	}

	@Override
	public boolean hasRequirements(Player player) {
		if (player == null) {
			return false;
		}
		return true;
	}

	@Override
	public void killHook(Player player, Mob victim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void objectInteraction(ObjectOptions options, int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int rewardQuestPoints(Player player) {
		return 0;
	}

	public void setTutorialGuideLocation(Location tutorialGuideLocation) {
		this.tutorialGuideLocation = tutorialGuideLocation;
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
		player.getActionSender().sendInterface(characterDesignInterface, true);
		NPC npc = new NPC(NPCDefinition.forId(tutorialGuide), Location.create(
				tutorialGuideLocation.getX(), tutorialGuideLocation.getY(),
				tutorialGuideLocation.getZ()), tutorialGuideLocation,
				tutorialGuideLocation, 6);
		World.getWorld().register(npc);
		player.getActionSender().sendHintArrow(npc, 0, 1);
		player.getActionSender().playMusic(Tracks.NEWBIE_MELODY.toInteger());
		player.getActionSender().sendSidebarInterface(icons[11], interfaces[11]); // logout tab
	}

}
