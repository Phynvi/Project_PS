/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rs2server.rs2.model.quest.impl;

import org.rs2server.rs2.model.*;

/**
 * 
 * @author Josh Mai
 * 
 */
public class TreeGnomeVillage extends AbstractQuest {

	public static int QUEST_ID = 125;

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
		return "treeGnomeVillage";
	}

	@Override
	public void getDialogues(Player player, int npc, int id) {
	}

	@Override
	public int getFinalStage() {
		return 14;
	}

	@Override
	public void getProgress(Player player, boolean show, int progress) {

		switch (progress) {
		case 0:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"I could start this quest by talking to");
			player.getActionSender().sendString(275, 8,
					"King Bolren, located in the Tree Gnome Village.");
			break;
		case 1:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender().sendString(275, 10,
					"King Bolren told me that the magical orb used to");
			player.getActionSender().sendString(275, 11,
					"protect the Tree Gnome Village had been stolen.");
			player.getActionSender().sendString(275, 12,
					"He also told me to talk to Commander Montai.");
			break;
		case 2:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender().sendString(275, 14,
					"I spoke to Commander Montai, and he told me to get him");
			player.getActionSender().sendString(275, 15,
					"6 regular logs so that they could build defences.");
			break;
		case 3:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"Commander Montai is building the defences up. He told me to");
			player.getActionSender().sendString(275, 18, "talk to him again.");
			break;
		case 4:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"Commander Montai told me to go into the heart of the battlefield");
			player.getActionSender().sendString(275, 21,
					"and find the gnome trackers to retrieve the coordinates.");
			break;
		case 5:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"Commander Montai told me to go into the heart of the battlefield");
			player.getActionSender().sendString(275, 21,
					"and find the gnome trackers to retrieve the coordinates.");
			player.getActionSender().sendString(275, 23,
					"I currently have 1 coordinate.");
			break;
		case 6:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"Commander Montai told me to go into the heart of the battlefield");
			player.getActionSender().sendString(275, 21,
					"and find the gnome trackers to retrieve the coordinates.");
			player.getActionSender().sendString(275, 23,
					"I currently have 2 coordinates.");
			break;
		case 7:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"I currently have 2 coordinates, but couldn't retrieve the");
			player.getActionSender().sendString(275, 24,
					"x coordinate due to a tracker gnome going crazy.");
			break;
		case 8:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"I've damaged the Khazard Stronghold with the Ballista. I should");
			player.getActionSender().sendString(275, 27, "go get the orb.");
			break;
		case 9:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"I've damaged the Khazard Stronghold with the Ballista. I should");
			player.getActionSender().sendString(275, 27, "go get the orb.");
			break;
		case 10:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"I've damaged the Khazard Stronghold with the Ballista. I should");
			player.getActionSender().sendString(275, 27, "go get the orb.");
			break;
		case 11:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"<str>I've damaged the Khazard Stronghold with the Ballista. I should</str>");
			player.getActionSender().sendString(275, 27,
					"<str>go get the orb.</str>");
			player.getActionSender().sendString(275, 29,
					"I have the orb. I should report back to the King.");
			break;
		case 12:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"<str>I've damaged the Khazard Stronghold with the Ballista. I should</str>");
			player.getActionSender().sendString(275, 27,
					"<str>go get the orb.</str>");
			player.getActionSender()
					.sendString(275, 29,
							"<str>I have the orb. I should report back to the King.</str>");
			player.getActionSender()
					.sendString(275, 31,
							"Khazard troops took the remaining orbs from the village while I");
			player.getActionSender()
					.sendString(275, 32,
							"was gone. I need to kill the Khazard Warlord to obtain them.");
			break;
		case 13:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"<str>I've damaged the Khazard Stronghold with the Ballista. I should</str>");
			player.getActionSender().sendString(275, 27,
					"<str>go get the orb.</str>");
			player.getActionSender()
					.sendString(275, 29,
							"<str>I have the orb. I should report back to the King.</str>");
			player.getActionSender()
					.sendString(275, 31,
							"<str>Khazard troops took the remaining orbs from the village while I</str>");
			player.getActionSender()
					.sendString(275, 32,
							"<str>was gone. I need to kill the Khazard Warlord to obtain them.</str>");
			player.getActionSender()
					.sendString(275, 34,
							"I have the remaining orbs. I should report back to the King.");
			break;
		case 14:
			player.getActionSender().sendString(275, 4, "Requirements:");
			player.getActionSender().sendString(275, 5,
					"Ability to defeat a level 112 monster.");
			player.getActionSender().sendString(275, 7,
					"<str>I could start this quest by talking to</str>");
			player.getActionSender()
					.sendString(275, 8,
							"<str>King Bolren, located in the Tree Gnome Village.</str>");
			player.getActionSender()
					.sendString(275, 10,
							"<str>King Bolren told me that the magical orb used to</str>");
			player.getActionSender()
					.sendString(275, 11,
							"<str>protect the Tree Gnome Village had been stolen.</str>");
			player.getActionSender().sendString(275, 12,
					"<str>He also told me to talk to Commander Montai.</str>");
			player.getActionSender()
					.sendString(275, 14,
							"<str>I spoke to Commander Montai, and he told me to get him</str>");
			player.getActionSender()
					.sendString(275, 15,
							"<str>6 regular logs so that they could build defences.</str>");
			player.getActionSender()
					.sendString(275, 17,
							"<str>Commander Montai is building the defences up. He told me to</str>");
			player.getActionSender().sendString(275, 18,
					"<str>talk to him again.</str>");
			player.getActionSender()
					.sendString(275, 20,
							"<str>Commander Montai told me to go into the heart of the battlefield</str>");
			player.getActionSender()
					.sendString(275, 21,
							"<str>and find the gnome trackers to retrieve the coordinates.</str>");
			player.getActionSender()
					.sendString(275, 23,
							"<str>I currently have 2 coordinates, but couldn't retrieve the</str>");
			player.getActionSender()
					.sendString(275, 24,
							"<str>x coordinate due to a tracker gnome going crazy.</str>");
			player.getActionSender()
					.sendString(275, 26,
							"<str>I've damaged the Khazard Stronghold with the Ballista. I should</str>");
			player.getActionSender().sendString(275, 27,
					"<str>go get the orb.</str>");
			player.getActionSender()
					.sendString(275, 29,
							"<str>I have the orb. I should report back to the King.</str>");
			player.getActionSender()
					.sendString(275, 31,
							"<str>Khazard troops took the remaining orbs from the village while I</str>");
			player.getActionSender()
					.sendString(275, 32,
							"<str>was gone. I need to kill the Khazard Warlord to obtain them.</str>");
			player.getActionSender()
					.sendString(275, 34,
							"<str>I have the remaining orbs. I should report back to the King.</str>");
			player.getActionSender().sendString(275, 36, "QUEST COMPLETE!");
			break;
		}
	}

	@Override
	public int getQuestButton() {
		return 125;
	}

	@Override
	public String getQuestName() {
		return "Tree Gnome Village";
	}

	@Override
	public void getRewards(Player player) {
		super.getRewards(player);
		player.getActionSender().sendString(277, 8, "2 Quest Points");
		player.getActionSender().sendString(277, 9, "11,450 Attack XP");
		player.getActionSender().sendString(277, 10,
				"Gnome Amulet of Protection");
	}

	@Override
	public boolean hasRequirements(Player player) {
		return true;
	}

	@Override
	public void killHook(Player player, Mob victim) {
		NPC npc = (NPC) victim;
		if (npc.getDefinition() != null) {
			if (npc.getDefinition().getId() == 478
					&& player.getQuestStorage().getQuestStage(QUEST_ID) == 8) {
				player.getQuestStorage().setQuestStage(this, 9);
			} else if (npc.getDefinition().getId() == 478
					&& player.getQuestStorage().getQuestStage(QUEST_ID) == 9
					&& npc.getLocation().getZ() == 1) {
				player.getQuestStorage().setQuestStage(this, 10);
			} else if (npc.getDefinition().getId() == 477
					&& (player.getQuestStorage().getQuestStage(QUEST_ID) == 12 || player
							.getQuestStorage().getQuestStage(QUEST_ID) == 13)) {
				World.getWorld().createGroundItem(
						new GroundItem(player.getName(), new Item(588),
								victim.getLocation()), player);
				player.getQuestStorage().setQuestStage(this, 13);
			}
		}
	}

	@Override
	public void objectInteraction(ObjectOptions options, int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int rewardQuestPoints(Player player) {
		return 2;
	}
}
