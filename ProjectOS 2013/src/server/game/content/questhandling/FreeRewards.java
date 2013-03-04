package server.game.content.questhandling;

import server.game.players.Client;

/*
 * @author Andrew
 */

public class FreeRewards {
	
	public static int qpGain;
	public static String questName;
	
	 public static void QuestReward(Client c, String questName, String Line1, String Line2, String Line3, String Line4, String Line5, String Line6, int itemID) {
	        c.getPA().sendFrame126("You have completed " + questName + "!", 12144);
	        c.getPA().sendFrame126("" + (c.questPoints), 12147);
	        c.getPA().sendFrame126(Line1, 12150);
	        c.getPA().sendFrame126(Line2, 12151);
	        c.getPA().sendFrame126(Line3, 12152);
	        c.getPA().sendFrame126(Line4, 12153);
	        c.getPA().sendFrame126(Line5, 12154);
	        c.getPA().sendFrame126(Line6, 12155);
	        c.getPA().sendFrame246(12145, 250, itemID);
	        c.getPA().showInterface(12140);
	        c.sendMessage("You completed " + questName + "!");
	        c.randomActions += 1;
	}
	 
	 public static void pirateFinish(Client c) {
		 	QuestReward(c, "Pirate's Treasure", "2 Quest Points", "One-Eyed Hector's Treasure", "", "", "", "", 325);
		 	questName = "Pirate's Treasure";
		 	c.questPoints += 2;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.getItems().addItem(405, 1);
	 }
	 
	 public static void witchFinish(Client c) {
		 	QuestReward(c, "Witch's Potion", "1 Quest Point", "325 Magic Experience", "", "", "", "", 325);
		 	questName = "Witch's Potion";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.getPA().addSkillXP(325, 6);
	 }
	 
	 public static void julietFinish(Client c) {
		 	QuestReward(c, "Romeo and Juliet", "5 Quest Points", "", "", "", "", "", 325);
		 	questName = "Romeo and Juliet";
		 	c.questPoints += 5;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
	 }
	 
	 public static void restFinish(Client c) {
			QuestReward(c, "Restless Ghost", "1 Quest Point", "125 Prayer Experience", "", "", "", "", 325);
		 	questName = "Restless Ghost";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
			c.restGhost = 5;
			c.getPA().addSkillXP(125, 5);
	 }

	 public static void vampFinish(Client c) {
		 	QuestReward(c, "Vampyre Slayer", "3 Quest Points", "4825 Attack Experience", "", "", "", "", 325);
		 	questName = "Vampyre Slayer";
		 	 c.questPoints += 3;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.vampSlayer = 4;
			c.getPA().addSkillXP(4825, 0);
	 }
	 
	 public static void gertFinissh(Client c) {
		 	QuestReward(c, "Gertrude's Cat", "1 Quest Point", "1525 Cooking Exp", "A kitten!", "Ability to raise cats", "A choclate cake", "A bowl of stew", 325);
		 	questName = "Gertrude's Cat";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.gertCat = 7;
			c.getItems().addItem(1897, 1);
			c.getItems().addItem(2003, 1);
			c.getItems().addItem(1560, 1);
			c.getPA().addSkillXP(1525, 7);
	 }
	 
	 public static void runeFinish(Client c) {
		 QuestReward(c, "Rune Mysteries", "1 Quest Points", "Air Talisman", "", "", "", "", 325);
		 questName = "Rune Mysteries";
		 c.questPoints ++;
		 c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 c.runeMist = 4;
	 }
	 
	 public static void sheepFinish(Client c) {
			QuestReward(c, "Sheep Shearer", "1 Quest Point", "150 Crafting Exp", "60 Coins", "", "", "", 325);
		 	questName = "Sheep Shearer";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.sheepShear = 2;
		 	c.getItems().addItem(995, 60);
			c.getPA().addSkillXP(150, 12);
	 }
	 
	 public static void doricFinish(Client c) {
		 	QuestReward(c, "Doric's Quest", "1 Quest Point", "2600 Mining Exp", "180 Coins", "", "", "", 325);
		 	questName = "Doric's Quest";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.doricQuest = 3;
		 	c.getPA().addSkillXP(2600, 14);
			c.getItems().addItem(995, 180);
	 }
	 
	 public static void impFinish(Client c) {
		 	QuestReward(c, "Imp Catcher", "1 Quest Point", "875 Magic Exp", "", "", "", "", 325);
		 	questName = "Imp Catcher";
		 	c.questPoints ++;
		 	c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
		 	c.impsC = 2;
	 }
	
	public static void cookReward(Client c) {
			QuestReward(c, "Cook's Assistant", "1 Quest Point", "500 Coins", "20 Sardines", "", "", "", 325);
			questName = "Cook's Assistant";
			c.getItems().addItem(326, 20);
			c.getItems().addItem(995, 500);
			c.questPoints ++;
			c.getPA().sendFrame126("@gre@"+ questName +"", 7333);
			c.cookAss = 3;
	}
}