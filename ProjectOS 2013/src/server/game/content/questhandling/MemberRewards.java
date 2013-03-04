package server.game.content.questhandling;

import server.game.players.Client;

/*
 * @author Andrew
 */

public class MemberRewards extends FreeRewards {
	 
	 public static void gertFinish(Client c) {
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
}