package server.game.content.tutorial;

import server.game.dialogues.FreeDialogues;
import server.game.players.Client;

public class TutorialIsland {
	
	public static int
	TUTORIAL_X = 3223,
	TUTORIAL_Y = 3219;
	
	private int progressValue = 1;
	public static int tutorialIslandStage = 0;
	
	private static int[] SIDEBARS = { 2423, 3917, 638, 3213, 1644, 5608, 1151, 18128, 5065, 5715, 2449, 904, 147, 962 };
	
	public static void showAllSideBars(Client c) {
		for (int i = 0; i < SIDEBARS.length; i++) {
			c.setSidebarInterface(i, SIDEBARS[i]);
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151);
			} else {
				c.setSidebarInterface(6, 12855);
			}
		}
	}
	
	public static void enableSideBarInterfaces(Client c, int[] listSideBar) {
		int[] sidebars = { 2423, 3917, 638, 3213, 1644, 5608, 1151, 18128, 5065, 5715, 2449, 904, 147, 962 };
		for (int i = 0; i < listSideBar.length; i++) {
			c.setSidebarInterface(listSideBar[i], sidebars[listSideBar[i]]);
		}
	}
	
	public static void hideAllSideBars(Client c) {
	for (int i = 0; i < SIDEBARS.length; i++)
		c.setSidebarInterface(i, -1);
	}
	
	public boolean sendDialogue(Client c) {
		StagesLoader stagesLoader = StagesLoader.forId(tutorialIslandStage);
		if (stagesLoader == null)
			return false;
		if (stagesLoader.getDialogueId() == 0) {
			c.getDH().sendStatement("Follow the instructions to continue!");
			c.nextChat = 0;
			return true;
		}
		FreeDialogues.handledialogue(c, c.nextChat, stagesLoader.getDialogueId());
		return true;

	}
	
	public boolean sendGiveItemsInstructor(Client c) {
		StagesLoader stagesLoader = StagesLoader.forId(tutorialIslandStage);
		if (stagesLoader == null) {
			return false;
		}
		switch (tutorialIslandStage) {
		case 0://depends what stage this is need to change this and the following
			c.getDH().sendGiveItemNpc("The Survival Guide gives you some @blu@"+c.getItems().getItemName(590)+"", "and some @blu@"+c.getItems().getItemName(1351)+"!", 590, 1351);
		break;
		case 1:
			c.getDH().sendGiveItemNpc("Terrova gives you some @blu@Air Rune","and some @blu@Mind Rune @bla@!",556,558);
		break;
		case 2:
			c.getDH().sendGiveItemNpc("The Combat Guide gives you some @blu@Shortbow", "and some @blu@Bronze Arrows@bla@!", 841, 882);
		break;
		case 3:
			c.getDH().sendGiveItemNpc("The Combat Guide gives you some @blu@Wooden shield","and some @blu@Bronze sword@bla@!", 1277, 1171);
		break;
		case 4:
			c.getDH().sendGiveItemNpc("Dezzick gives you some @blu@"+c.getItems().getItemName(2347)+"@bla@!", 2347);
		break;
		case 5:
			c.getDH().sendGiveItemNpc("Dezzick gives you some @blu@"+c.getItems().getItemName(1265)+"@bla@!", 1265);
		break;
		case 6:
			c.getDH().sendGiveItemNpc("The cooking Guide fives you a @blu@Bucket of Water@bla@ and a", "@blu@Pot of Flour@bla@!", 1929, 1933);
		break;
		case 7:
			c.getDH().sendGiveItemNpc("The survival guide gives you a @blu@Net@bla@!",303);
		break;
		case 8:
			c.getDH().sendGiveItemNpc("The Survival Guide gives you some @blu@"+c.getItems().getItemName(590)+"", "and some @blu@"+c.getItems().getItemName(1351)+"@bla@!", 590, 1351);
		break;
		}
		return true;
	}
	
	public static void restartTutorial(Client c) {
		tutorialIslandStage = 0;
		c.getPA().showInterface(3559);
		c.canChangeAppearance = true;
		hideAllSideBars(c);
		c.getPA().movePlayer(TUTORIAL_X, TUTORIAL_Y, 0);
		c.sendMessage("You have restarted tutorial island.");
	}
	
	public void startTutorial(Client c) {
		if (tutorialIslandStage == 0) {
			c.getItems().addItemToBank(995, 25);
			tutorialIslandStage = 1;
			hideAllSideBars(c);
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			c.getPA().movePlayer(TUTORIAL_X, TUTORIAL_Y, 0);
			c.sendMessage("Use the ::reportbug command to report bugs");
			c.getPA().drawHeadicon(1, 945, 0, 0);
		} else {
			return;
		}
	}
	
	public static int getTutorialIslandStage() {
		return tutorialIslandStage;
	}
	
	public void setTutorialIslandStage(Client c, int tutorialIslandStage, boolean update) {
		TutorialIsland.tutorialIslandStage = tutorialIslandStage;
		if (update)
			updateInterface(c, update);
	}
	
	public void setProgressValue(int progressValue) {
		this.progressValue = progressValue;
	}
	
	public int getProgressValue() {
		return progressValue;
	}
	
	public boolean isInTutorialIslandStage() {
		return false;//tutorialIslandStage < 100;
	}
	
	public void updateInterface(Client c, boolean send) {
		StagesLoader stagesLoader = StagesLoader.forId(tutorialIslandStage);
		if (stagesLoader == null) {
			c.getPA().removeAllWindows();
			return;
		}
		if (tutorialIslandStage >= 43)
			c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
		stagesLoader.sendInterfaces(c, send);
		if (c.isInTut())
			sendProgressInterface(c);
	}
	
	public void sendProgressInterface(Client c) {
		c.getPA().setConfig(406,  progressValue);
		c.getPA().sendFrame171(1, 12224);
		c.getPA().sendFrame171(1, 12225);
		c.getPA().sendFrame171(1, 12226);
		c.getPA().sendFrame171(1, 12227);
		c.getPA().sendFrame171(0, 12161);
		c.getPA().sendFrame126("" + progressValue + "%", 12224);
		c.getPA().walkableInterface(8680);

	}
	
	private static final int[][] STARTER_ITEMS = {
		{ 1351, 1 }, { 590, 1 }, { 303, 1 }, { 315, 1 }, { 1925, 1 }, { 1931, 1 }, { 2309, 1 }, { 1265, 1 }, { 1205, 1 }, { 1277, 1 }, { 1171, 1 }, { 841, 1 }, { 882, 25 }, { 556, 25 }, { 558, 15 }, { 555, 6 }, { 557, 4 }, { 559, 2 }
	};

	public static void addStarterItems(Client c) {
		for (int i = 0; i < STARTER_ITEMS.length; i++) {
		int item = STARTER_ITEMS[i][0];
        int amount = STARTER_ITEMS[i][1];
        c.getItems().addItem(item, amount);
		}
	}
}

