package org.rs2server.rs2.model;

import org.rs2server.rs2.Constants;
import org.rs2server.rs2.action.impl.TeleportAction;
import org.rs2server.rs2.model.Animation.FacialAnimation;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction;
import org.rs2server.rs2.model.combat.impl.MagicCombatAction.Spell;
import org.rs2server.rs2.model.container.Bank;
import org.rs2server.rs2.model.container.Equipment.EquipmentType;
import org.rs2server.rs2.model.quest.QuestRepository;
import org.rs2server.rs2.model.quest.impl.LostCity;
import org.rs2server.rs2.model.quest.impl.PriestInPeril;
import org.rs2server.rs2.model.quest.impl.RuneMysteries;
import org.rs2server.rs2.model.quest.impl.TreeGnomeVillage;
import org.rs2server.rs2.model.skills.Slayer;
import org.rs2server.rs2.net.ActionSender.DialogueType;
import org.rs2server.rs2.tickable.Tickable;
import org.rs2server.util.Misc;

public class DialogueManager {

	private static Location teleportLocation;

	private static String message = "";

	public static void advanceDialogue(Player player, int index) {
		int dialogueId = player.getInterfaceState().getNextDialogueId(index);
		if (dialogueId == -1) {
			player.getActionSender().removeChatboxInterface();
			return;
		}

		openDialogue(player, dialogueId);
	}

	public static void openDialogue(final Player player, int dialogueId) {
		if (dialogueId == -1) {
			return;
		}
		player.getWalkingQueue().addStep(0, 0);
		for (int i = 0; i < 5; i++) {
			player.getInterfaceState().setNextDialogueId(i, -1);
		}
		player.getInterfaceState().setOpenDialogueId(dialogueId);
		NPC npc = null;
		if (player.getInteractingEntity() != null
				&& player.getInteractingEntity().isNPC()) {
			npc = (NPC) player.getInteractingEntity();
		}
		Item skillcape = null;
		Item hood = null;
		boolean gen = Misc.intToBoolean(player.getAppearance().getGender());
		String gender = gen ? "lady" : "man";

		try {
			switch (dialogueId) {
			case 0:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Good day. How may I help you?");
				player.getInterfaceState().setNextDialogueId(0, 1);
				break;
			case 1:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I'd like to access my bank account, please.",
						"I'd like to set/change my PIN please.",
						"What is this place?");
				player.getInterfaceState().setNextDialogueId(0, 2);
				player.getInterfaceState().setNextDialogueId(1, 3);
				player.getInterfaceState().setNextDialogueId(2, 5);
				break;
			case 2:
				player.getActionSender().removeChatboxInterface();
				Bank.openBank(player);
				break;
			case 3:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'd like to set my PIN please.");
				player.getInterfaceState().setNextDialogueId(0, 4);
				break;
			case 4:
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Our account protection system is currently so secure,",
								"we are currently not offering security PIN's, though",
								"after what The Wise Old Man did at the Bank of Draynor",
								"there has been much debate about this subject.");
				break;
			case 5:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What is this place?");
				player.getInterfaceState().setNextDialogueId(0, 6);
				break;
			case 6:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(),
						DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DEFAULT,
						"This is a branch of the Bank of "
								+ Constants.SERVER_NAME + ". We have",
						"branches in many towns.");
				player.getInterfaceState().setNextDialogueId(0, 7);
				break;
			case 7:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"And what do you do?",
						"Didn't you used to be called the Bank of Varrock?");
				player.getInterfaceState().setNextDialogueId(0, 8);
				player.getInterfaceState().setNextDialogueId(1, 10);
				break;
			case 8:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"And what do you do?");
				player.getInterfaceState().setNextDialogueId(0, 9);
				break;
			case 9:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"We will look after your items and money for you.",
								"Leave your valuables with us if you want to keep them",
								"safe.");
				break;
			case 10:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Didn't you used to be called the Bank of Varrock?");
				player.getInterfaceState().setNextDialogueId(0, 11);
				break;
			case 11:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Yes we did, but people kept on coming into our",
						"branches outside of Varrock and telling us that our",
						"signs were wrong. They acted as if we didn't know",
						"what town we were in or something.");
				break;
			case 12:
				player.getActionSender().sendDialogue("Choose spellbook:",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Standard", "Ancient", "Cancel");
				player.getInterfaceState().setNextDialogueId(0, 13);
				player.getInterfaceState().setNextDialogueId(1, 14);
				player.getInterfaceState().setNextDialogueId(2, 15);
				break;
			case 13:
				player.getCombatState().setSpellbookSwap(true);
				MagicCombatAction.executeSpell(Spell.SPELLBOOK_SWAP, player,
						player, 0);
				player.getActionSender().removeAllInterfaces();
				break;
			case 14:
				player.getCombatState().setSpellbookSwap(true);
				MagicCombatAction.executeSpell(Spell.SPELLBOOK_SWAP, player,
						player, 1);
				player.getActionSender().removeAllInterfaces();
				break;
			case 15:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hello there.");
				player.getInterfaceState().setNextDialogueId(0, 16);
				break;
			case 16:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello traveller.");
				player.getInterfaceState().setNextDialogueId(0, 17);
				break;
			case 17:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Where am I?", "Who are you?", "Goodbye.");
				player.getInterfaceState().setNextDialogueId(0, 18);
				player.getInterfaceState().setNextDialogueId(1, 20);
				player.getInterfaceState().setNextDialogueId(2, 28);
				break;
			case 18:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Where am I?");
				player.getInterfaceState().setNextDialogueId(0, 19);
				break;
			case 19:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(),
						DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DEFAULT,
						"You are in " + Constants.SERVER_NAME
								+ ". Through quests and",
						"adventures you may learn the ancient origins of",
						"this mysterious land, but until then,",
						"all I can do is offer you help.");
				break;
			case 20:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Who are you?");
				player.getInterfaceState().setNextDialogueId(0, 21);
				break;
			case 21:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I am Mawnis Burowgar, leader of Neitiznot. I am here",
								"to guide travellers like you through their own adventure.",
								"Would you like help with anything?");
				player.getInterfaceState().setNextDialogueId(0, 22);
				break;
			case 22:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Where can I get basic supplies?",
						"Where can I make money?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 23);
				player.getInterfaceState().setNextDialogueId(1, 25);
				player.getInterfaceState().setNextDialogueId(2, 27);
				break;
			case 23:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Where can I get basic supplies?");
				player.getInterfaceState().setNextDialogueId(0, 24);
				break;
			case 24:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"There are many people on this island who will help you.",
								"Talk to various characters around the island who will give",
								"you starting items. Also check the shops near by for",
								"any good deals, and possibly some free items.");
				break;
			case 25:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Where can I make money?");
				player.getInterfaceState().setNextDialogueId(0, 26);
				break;
			case 26:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"The Wise Old Man often helps new comers to make",
						"some small amounts of money. Other than that, you",
						"should train your skills and sell anything you make",
						"to shops or other players for profit.");
				break;
			case 27:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"No thank you.");
				break;
			case 28:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Goodbye.");
				break;
			case 29:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hello.");
				player.getInterfaceState().setNextDialogueId(0, 30);
				break;
			case 30:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello there young warrior. I am Harlan, master",
						"of melee. What can I do for you?");
				player.getInterfaceState().setNextDialogueId(0, 31);
				break;
			case 31:
				if (player.getSkills().getLevelForExperience(Skills.DEFENCE) > 98) {
					player.getActionSender()
							.sendDialogue("Select an Option",
									DialogueType.OPTION, -1,
									FacialAnimation.DEFAULT,
									"Do you have any supplies for me?",
									"Talk about defence skillcape.",
									"Nothing, thanks.");
					player.getInterfaceState().setNextDialogueId(0, 32);
					player.getInterfaceState().setNextDialogueId(1, 52);
					player.getInterfaceState().setNextDialogueId(2, 34);
				} else {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"Do you have any supplies for me?",
							"Nothing, thanks.");
					player.getInterfaceState().setNextDialogueId(0, 32);
					player.getInterfaceState().setNextDialogueId(1, 34);
				}
				break;
			case 32:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Do you have any supplies for me?");
				player.getInterfaceState().setNextDialogueId(0, 33);
				break;
			case 33:
				if (player.getSkills().getLevel(Skills.RANGE) < 20) {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Ofcourse! I am always eager to help young little",
									"warriors like yourself. Take this sword and this shield",
									"and train your melee. But make sure you use",
									"better armour once you level up.");
					player.getInventory().add(new Item(9703));
					player.getInventory().add(new Item(9704));
				} else {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"I'm sorry, I can't help you at all. You should be",
									"using better equipment at your level.");
				}
				break;
			case 34:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Nothing, thanks.");
				break;
			case 35:
				player.getActionSender()
						.sendDialogue(player.getName(), DialogueType.PLAYER,
								-1, FacialAnimation.DEFAULT, "Hi.");
				player.getInterfaceState().setNextDialogueId(0, 36);
				break;
			case 36:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Heya! I'm Nemarti. What can I do for you?");
				player.getInterfaceState().setNextDialogueId(0, 37);
				break;
			case 37:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Do you have any supplies for me?", "Nothing, thanks.");
				player.getInterfaceState().setNextDialogueId(0, 38);
				player.getInterfaceState().setNextDialogueId(1, 34);
				break;
			case 38:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Do you have any supplies for me?");
				player.getInterfaceState().setNextDialogueId(0, 39);
				break;
			case 39:
				if (player.getSkills().getCombatLevel() < 20) {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Sure thing, I'm always helping young arrow slingers!",
									"Here you go.");
					player.getInventory().add(new Item(9705));
					player.getInventory().add(new Item(9706, 50));
				} else {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"I'm sorry, I can't help you at all. You should be",
									"using better equipment at your level.");
				}
				break;
			case 40:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hello there.");
				player.getInterfaceState().setNextDialogueId(0, 41);
				break;
			case 41:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello young sage. What can I do for you?");
				player.getInterfaceState().setNextDialogueId(0, 42);
				break;
			case 42:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Do you have any supplies for me?", "Nothing, thanks.");
				player.getInterfaceState().setNextDialogueId(0, 43);
				player.getInterfaceState().setNextDialogueId(1, 34);
				break;
			case 43:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Do you have any supplies for me?");
				player.getInterfaceState().setNextDialogueId(0, 44);
				break;
			case 44:
				if (player.hasReceivedStarterRunes()) {
					player.getActionSender()
							.sendDialogue(npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"I've already given you your set of starter runes.");
				} else {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Sure thing. Here's enough rune stones for 50 casts",
									"of Wind Strike. Use them wisely as I won't be",
									"replacing them for you.");
					player.setReceivedStarterRunes(true);
					player.getInterfaceState().setNextDialogueId(0, 45);
					player.getInventory().add(
							new Item(MagicCombatAction.AIR_RUNE, 50));
					player.getInventory().add(
							new Item(MagicCombatAction.MIND_RUNE, 50));
				}
				break;
			case 45:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Thanks!");
				break;
			case 46:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hey.");
				player.getInterfaceState().setNextDialogueId(0, 47);
				break;
			case 47:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"'Ello there! Would ya' like to buy some of me own",
						"knitwear? It's all the rage with the young folk. For",
						"no more than 750 coins, now I can't say fairer",
						"than that.");
				player.getInterfaceState().setNextDialogueId(0, 48);
				break;
			case 48:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 49);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 49:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure.");
				player.getInterfaceState().setNextDialogueId(0, 50);
				break;
			case 50:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 0, 1);
				break;
			case 51:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"No thank you.");
				break;
			case 52:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I see you have 99 defence, well done! Would you",
						"like to buy the defence cape? It'll be 99,0000",
						"coins.");
				player.getInterfaceState().setNextDialogueId(0, 53);
				break;
			case 53:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I have 99,000 coins, here you go.",
						"99,000 coins, that's outrageous.");
				player.getInterfaceState().setNextDialogueId(0, 54);
				player.getInterfaceState().setNextDialogueId(1, 56);
				break;
			case 54:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I have 99,000 coins, here you go.");
				player.getInterfaceState().setNextDialogueId(0, 55);
				break;
			case 55:
				if (player.getInventory().getCount(995) >= 99000) {
					skillcape = player.checkForSkillcape(new Item(9753));
					hood = new Item(9755);
					if (player.getInventory().hasRoomFor(skillcape)
							&& player.getInventory().hasRoomFor(skillcape)) {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT, "Here you go.");
						player.getInventory().remove(new Item(995, 99000));
						player.getInventory().add(hood);
						player.getInventory().add(skillcape);
					} else {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Perhaps you should clear some space from",
								"your inventory first.");
					}
				} else {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"You don't have 99,000 coins.");
				}
				break;
			case 56:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"99,000 coins, that's outrageous.");
				break;
			case 57:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello, how may I help you?");
				player.getInterfaceState().setNextDialogueId(0, 58);
				break;
			case 58:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What are you selling?", "Goodbye.");
				player.getInterfaceState().setNextDialogueId(0, 59);
				player.getInterfaceState().setNextDialogueId(1, 28);
				break;
			case 59:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What are you selling?");
				player.getInterfaceState().setNextDialogueId(0, 60);
				break;
			case 60:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 2, 1);
				break;
			case 61:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Hello there, would you like to sail anywhere? It's free",
								"of charge for Jatizso, but any other exotic lands you",
								"want sailing to might cost a fair bit.");
				player.getInterfaceState().setNextDialogueId(0, 62);
				break;
			case 62:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Jatizso.", "Nowhere.");
				player.getInterfaceState().setNextDialogueId(0, 63);
				player.getInterfaceState().setNextDialogueId(1, 66);
				break;
			case 63:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Jatizso please.");
				player.getInterfaceState().setNextDialogueId(0, 64);
				break;
			case 64:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Land-ho!");
				player.getInterfaceState().setNextDialogueId(0, 65);
				break;
			case 65:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2421, 3781, 0));
				break;
			case 66:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Nowhere, thanks for your time.");
				break;
			case 67:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi there. I'm offering a boating service with my",
						"husband. Would you like to go anywhere? It will cost",
						"you unless it's Neitiznot.");
				player.getInterfaceState().setNextDialogueId(0, 68);
				break;
			case 68:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Neitiznot.", "Nowhere.");
				player.getInterfaceState().setNextDialogueId(0, 69);
				player.getInterfaceState().setNextDialogueId(1, 66);
				break;
			case 69:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Neitiznot please.");
				player.getInterfaceState().setNextDialogueId(0, 70);
				break;
			case 70:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Land-ho!");
				player.getInterfaceState().setNextDialogueId(0, 71);
				break;
			case 71:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2311, 3780, 0));
				break;
			case 72:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Hello. Would you like to buy some of my fine cuisine?");
				player.getInterfaceState().setNextDialogueId(0, 73);
				break;
			case 73:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What are you selling?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 74);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 74:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What are you selling?");
				player.getInterfaceState().setNextDialogueId(0, 75);
				break;
			case 75:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 2, 1);
				break;
			case 76:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.ATTACK_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"  Congratulations, you have just advanced an Attack level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.ATTACK) + ".");
				break;
			case 77:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.DEFENCE_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								" Congratulations, you have just advanced a Defence level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.DEFENCE) + ".");
				if (player.getSkills().getLevelForExperience(Skills.DEFENCE) > 98) {
					player.getInterfaceState().setNextDialogueId(0, 107);
				}
				break;
			case 78:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.STRENGTH_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Strength level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.STRENGTH) + ".");
				break;
			case 79:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.HITPOINT_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Hitpoints level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.HITPOINTS) + ".");
				break;
			case 80:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.RANGING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Ranged level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.RANGE) + ".");
				if (player.getSkills().getLevelForExperience(Skills.RANGE) > 98) {
					player.getInterfaceState().setNextDialogueId(0, 108);
				}
				break;
			case 81:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.PRAYER_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Prayer level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.PRAYER) + ".");
				break;
			case 82:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.MAGIC_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Magic level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.MAGIC) + ".");
				break;
			case 83:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.COOKING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Cooking level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.COOKING) + ".");
				break;
			case 84:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.WOODCUTTING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Woodcutting level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.WOODCUTTING)
										+ ".");
				break;
			case 85:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.FLETCHING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"  Congratulations, you have just advanced a Fletching level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.FLETCHING) + ".");
				break;
			case 86:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.FISHING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Fishing level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.FISHING) + ".");
				break;
			case 87:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.FIREMAKING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Firemaking level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.FIREMAKING)
										+ ".");
				break;
			case 88:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.CRAFTING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Crafting level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.CRAFTING) + ".");
				break;
			case 89:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.SMITHING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"  Congratulations, you have just advanced a Smithing level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.SMITHING) + ".");
				break;
			case 90:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.MINING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"  Congratulations, you have just advanced a Mining level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.MINING) + ".");
				break;
			case 91:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.HERBLORE_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Herblore level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.HERBLORE) + ".");
				break;
			case 92:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.AGILITY_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced an Agility level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.AGILITY) + ".");
				break;
			case 93:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.THIEVING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Thieving level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.THIEVING) + ".");
				break;
			case 94:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.SLAYER_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Slayer level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.SLAYER) + ".");
				break;
			case 95:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.FARMING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Farming level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.FARMING) + ".");
				break;
			case 96:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.RUNECRAFTING_LEVEL_UP,
								-1,
								FacialAnimation.DEFAULT,
								"Congratulations, you have just advanced a Runecrafting level!",
								"You have now reached level "
										+ player.getSkills()
												.getLevelForExperience(
														Skills.RUNECRAFTING)
										+ ".");
				break;
			case 99:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello. How may I help you?");
				player.getInterfaceState().setNextDialogueId(0, 100);
				break;
			case 100:
				if (player.getSkills().getLevelForExperience(Skills.RANGE) > 98) {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"What are you selling?",
							"Talk about range skillcape.", "Goodbye.");
					player.getInterfaceState().setNextDialogueId(0, 101);
					player.getInterfaceState().setNextDialogueId(1, 103);
					player.getInterfaceState().setNextDialogueId(2, 29);
				} else {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"What are you selling?", "Goodbye.");
					player.getInterfaceState().setNextDialogueId(0, 101);
					player.getInterfaceState().setNextDialogueId(1, 29);
				}
				break;
			case 101:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What are you selling?");
				player.getInterfaceState().setNextDialogueId(0, 102);
				break;
			case 102:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 3, 1);
				break;
			case 103:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Ah, I see you have maxed the ranging skill! Bravo!",
						"I have something special for elite rangers such as",
						"yourself, would you like to buy it? It'll be",
						"99,000 coins.");
				player.getInterfaceState().setNextDialogueId(0, 104);
				break;
			case 104:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure, here's 99,000 coins.", "No way.");
				player.getInterfaceState().setNextDialogueId(0, 105);
				player.getInterfaceState().setNextDialogueId(1, 106);
				break;
			case 105:
				if (player.getInventory().getCount(995) >= 99000) {
					skillcape = player.checkForSkillcape(new Item(9756));
					hood = new Item(9758);
					if (player.getInventory().hasRoomFor(skillcape)
							&& player.getInventory().hasRoomFor(skillcape)) {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT, "Here you go.");
						player.getInventory().remove(new Item(995, 99000));
						player.getInventory().add(hood);
						player.getInventory().add(skillcape);
					} else {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Perhaps you should clear some space from",
								"your inventory first.");
					}
				} else {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"You don't have 99,000 coins.");
				}
				break;
			case 106:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"No way.");
				break;
			case 107:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.MESSAGE_MODEL_LEFT,
								9753,
								FacialAnimation.DEFAULT,
								"Congratulations! You are now a master of Defence. Why not visit the Melee combat tutor on Neitiznot? He has something special that is only available to the true masters of the Defence skill!");
				break;
			case 108:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.MESSAGE_MODEL_LEFT,
								9756,
								FacialAnimation.DEFAULT,
								"Congratulations! You are now a master of Ranging. Why not visit Lowe in the northern building on Neitiznot? He has something special that is only available to the true masters of the Ranging skill!");
				break;
			case 109:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello there, would you like to sample my fine ores?");
				player.getInterfaceState().setNextDialogueId(0, 110);
				break;
			case 110:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 111);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 111:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure.");
				player.getInterfaceState().setNextDialogueId(0, 112);
				break;
			case 112:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 4, 1);
				break;
			case 113:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Interested in me bars are ya'?");
				player.getInterfaceState().setNextDialogueId(0, 114);
				break;
			case 114:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Totally.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 115);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 115:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Totally.");
				player.getInterfaceState().setNextDialogueId(0, 116);
				break;
			case 116:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 5, 1);
				break;
			case 117:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Would you like to see some of my hand",
						"crafted items?");
				player.getInterfaceState().setNextDialogueId(0, 118);
				break;
			case 118:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 119);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 119:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Yes.");
				player.getInterfaceState().setNextDialogueId(0, 120);
				break;
			case 120:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 6, 1);
				break;
			case 121:
				// joshington
				break;
			case 122:
				// unused
				break;
			case 123:
				// unused
				break;
			case 124:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello there! Up for a quick trim or a totally",
						"new look?");
				player.getInterfaceState().setNextDialogueId(0, 125);
				break;
			case 125:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Haircut.", "Shave.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 126);
				player.getInterfaceState().setNextDialogueId(1, 129);
				player.getInterfaceState().setNextDialogueId(2, 51);
				break;
			case 126:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I want a haircut please.");
				player.getInterfaceState().setNextDialogueId(0, 127);
				break;
			case 127:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Certainly.");
				player.getInterfaceState().setNextDialogueId(0, 128);
				break;
			case 128:
				player.getActionSender()
						.removeChatboxInterface()
						.sendConfig(261, 1)
						.sendConfig(262, 1)
						.sendInterface(
								204 - player.getAppearance().getGender(), true);
				player.setInterfaceAttribute("newHair", 0 + (player
						.getAppearance().getGender() * 45));
				player.setInterfaceAttribute("newHairColour", 0);
				break;
			case 129:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I want a shave please.");
				player.getInterfaceState().setNextDialogueId(0,
						131 - player.getAppearance().getGender());
				break;
			case 130:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I'm sorry but women don't grow facial hair.");
				break;
			case 131:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Certainly.");
				player.getInterfaceState().setNextDialogueId(0, 132);
				break;
			case 132:
				player.getActionSender().removeChatboxInterface()
						.sendConfig(261, 1).sendConfig(262, 1)
						.sendInterface(199, true);
				player.setInterfaceAttribute("newBeard", 11);
				player.setInterfaceAttribute("newHairColour", 0);
				break;
			case 133:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Fancy changing your clothes?");
				player.getInterfaceState().setNextDialogueId(0, 134);
				break;
			case 134:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 135);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 135:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure.");
				player.getInterfaceState().setNextDialogueId(0, 136);
				break;
			case 136:
				player.getActionSender().removeChatboxInterface();
				player.getActionSender().sendInterface(269, true);
				break;
			case 137:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi there. Fancy a drink?");
				player.getInterfaceState().setNextDialogueId(0, 138);
				break;
			case 138:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 139);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 139:
				player.getActionSender().removeChatboxInterface();
				// Shop.open(player, 3, 1);
				break;
			case 140:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"My shields are amazing. Would you like to see",
						"the best ones I have?");
				player.getInterfaceState().setNextDialogueId(0, 141);
				break;
			case 141:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 142);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 142:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 4, 1);
				break;
			case 143:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I'm the best smith in this town. Cassie is just jealous.",
								"I'll prove it, my maces are the finest fashion work",
								"around! Would you like to see them?");
				player.getInterfaceState().setNextDialogueId(0, 144);
				break;
			case 144:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 145);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 145:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 5, 1);
				break;
			case 146:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi there. Are you interested in buying some good",
						"quality chainmail?");
				player.getInterfaceState().setNextDialogueId(0, 147);
				break;
			case 147:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 148);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 148:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 6, 1);
				break;
			case 149:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"'ay there tall stuff. Do you be needing some",
						"of the best pickaxes?");
				player.getInterfaceState().setNextDialogueId(0, 150);
				break;
			case 150:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 151);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 151:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 3, 1);
				break;
			case 152:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Fancy takin' a look at me ore store?");
				player.getInterfaceState().setNextDialogueId(0, 153);
				break;
			case 153:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 154);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 154:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 21, 1);
				break;
			case 155:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Heya. Do you need any runes or mage supplies?");
				player.getInterfaceState().setNextDialogueId(0, 156);
				break;
			case 156:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 157);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 157:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 24, 1);
				break;
			case 158:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Would ya like any bows or arrows?");
				player.getInterfaceState().setNextDialogueId(0, 159);
				break;
			case 159:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes please.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 160);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 160:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 8, 1);
				break;
			case 161:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Ello ello ello! Need any fishin' gear matey?");
				player.getInterfaceState().setNextDialogueId(0, 162);
				break;
			case 162:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes please.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 163);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 163:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 23, 1);
				break;
			case 164:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello there. Can I interest you in the best",
						"baked food in the land?");
				player.getInterfaceState().setNextDialogueId(0, 165);
				break;
			case 165:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 166);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 166:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 12, 1);
				break;
			case 167:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi. Would you like me to sail you anywhere?");
				player.getInterfaceState().setNextDialogueId(0, 168);
				break;
			case 168:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Rellekka please.", "Karamja please.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 169);
				player.getInterfaceState().setNextDialogueId(1, 194);
				player.getInterfaceState().setNextDialogueId(2, 51);
				break;
			case 169:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Rellekka please.");
				player.getInterfaceState().setNextDialogueId(0, 170);
				break;
			case 170:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2629, 3693, 0));
				break;
			case 171:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi. Would you like me to sail you anywhere?");
				player.getInterfaceState().setNextDialogueId(0, 172);
				break;
			case 172:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Port Sarim", "Nowhere.");
				player.getInterfaceState().setNextDialogueId(0, 196);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 173:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3029, 3217, 0));
				break;
			case 174:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I only offer my goods to the best warriors,",
						"and you are by far one of the best I've seen.",
						"Do you need any battle gear brother?");
				player.getInterfaceState().setNextDialogueId(0, 175);
				break;
			case 175:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes please.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 176);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 176:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 13, 1);
				break;
			case 177:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Step right up I got the best fish in all of Rellekka!");
				player.getInterfaceState().setNextDialogueId(0, 178);
				break;
			case 178:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 179);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 179:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 14, 1);
				break;
			case 180:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Wow, you beat my trial! Talk to me ",
						"anytime you need some Fremennik gear.");
				break;
			case 181:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I only offer my goods to the best warriors.");
				player.getInterfaceState().setNextDialogueId(0, 182);
				break;
			case 182:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"How do I prove I'm the best?", "Bye then.");
				player.getInterfaceState().setNextDialogueId(0, 184);
				player.getInterfaceState().setNextDialogueId(1, 183);
				break;
			case 183:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Bye then.");
				break;
			case 184:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"How do I prove I'm the best?");
				player.getInterfaceState().setNextDialogueId(0, 185);
				break;
			case 185:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You must take on my Fremennik Trial. When",
						"you are ready for combat, enter down the ladder at",
						"the back of my house. From there on your combat",
						"skills will be severely tested.");
				player.getInterfaceState().setNextDialogueId(0, 186);
				break;
			case 186:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What happens if I die?");
				player.getInterfaceState().setNextDialogueId(0, 187);
				break;
			case 187:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I shall return you your items.");
				break;
			case 188:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Would you like to buy some vintage",
						"Fremennik clothes?");
				player.getInterfaceState().setNextDialogueId(0, 189);
				break;
			case 189:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 190);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 190:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 16, 1);
				break;
			case 191:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"In need of the finest metal work in the land, mate?");
				player.getInterfaceState().setNextDialogueId(0, 189);
				break;
			case 192:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 193);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 193:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 16, 1);
				break;
			case 194:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Karamja please.");
				player.getInterfaceState().setNextDialogueId(0, 195);
				break;
			case 195:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2956, 3146, 0));
				break;
			case 196:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Port Sarim please.");
				player.getInterfaceState().setNextDialogueId(0, 173);
				break;
			case 197:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Banana's are a great source of potassium!");
				player.getInterfaceState().setNextDialogueId(0, 198);
				break;
			case 198:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Tell me more!", "Good bye.");
				player.getInterfaceState().setNextDialogueId(0, 200);
				player.getInterfaceState().setNextDialogueId(1, 199);
				break;
			case 199:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Good bye.");
				break;
			case 200:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 17, 1);
				break;
			case 201:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"*hic* my beers are great!!!");
				player.getInterfaceState().setNextDialogueId(0, 202);
				break;
			case 202:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 203);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 203:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 18, 1);
				break;
			case 204:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello JalYt-Ket-" + player.getName() + ". You want",
						"any equipment? I sell for good price.");
				player.getInterfaceState().setNextDialogueId(0, 205);
				break;
			case 205:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 206);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 206:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 19, 1);
				break;
			case 207:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"What you want JalYt-Ket-" + player.getName() + "?");
				player.getInterfaceState().setNextDialogueId(0, 208);
				break;
			case 208:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"What have you got?", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 209);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 209:
				player.getActionSender().removeChatboxInterface();
				Shop.openShop(player, 20, 1);
				break;
			case 210:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You want to access your bank account,",
						"JalYt-Ket-" + player.getName() + "?");
				player.getInterfaceState().setNextDialogueId(0, 211);
				break;
			case 211:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes please.", "No thank you.");
				player.getInterfaceState().setNextDialogueId(0, 2);
				player.getInterfaceState().setNextDialogueId(1, 51);
				break;
			case 212:
				player.getActionSender().sendDialogue("TzHaar-Mej-Kah",
						DialogueType.NPC, 2618, FacialAnimation.DEFAULT,
						"Wait for my signal before fighting.");
				break;
			case 213:
				player.getActionSender().sendDialogue("TzHaar-Mej-Kah",
						DialogueType.NPC, 2618, FacialAnimation.DEFAULT,
						"FIGHT!");
				break;
			case 214: // production action insufficient message.
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.MESSAGE, -1, FacialAnimation.DEFAULT,
						getMessage());
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 215:
				player.getActionSender().sendDialogue(
						"Where would you like to go?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Dungeon", "Slayer Tower",
						"Edgeville", "Next Page..");
				player.getInterfaceState().setNextDialogueId(0, 217);
				player.getInterfaceState().setNextDialogueId(1, 220);
				player.getInterfaceState().setNextDialogueId(2, 221);
				player.getInterfaceState().setNextDialogueId(3, 222);
				break;
			case 216: // newbie island
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2524, 4777, 0));
				player.getActionSender().sendMessage(
						"Welcome to Starter Island!");
				break;
			case 217: // dungeon
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2329, 10353, 2));
				player.getActionSender().sendMessage("Welcome to The Dungeon!");
				break;
			case 218: // tele home mage
				player.getActionSender().sendDialogue(
						"Would you like to go home?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Yes", "No");
				player.getInterfaceState().setNextDialogueId(0, 219);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 219:
				player.setTeleportTarget(Entity.DEFAULT_LOCATION);
				player.getActionSender().removeChatboxInterface();
				break;
			case 220:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3429, 3537, 0));
				player.getActionSender().sendMessage(
						"Welcome to the Slayer Tower!");
				break;
			case 221:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3093, 3493, 0));
				player.getActionSender().sendMessage("Welcome to Edgeville!");
				break;
			case 222:
				player.getActionSender().sendDialogue(
						"Where would you like to go?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Camelot", "Varrock Mine",
						"Al-Kharid Mine", "Next Page..", "Previous Page..");
				player.getInterfaceState().setNextDialogueId(0, 223);
				player.getInterfaceState().setNextDialogueId(1, 224);
				player.getInterfaceState().setNextDialogueId(2, 225);
				player.getInterfaceState().setNextDialogueId(3, 227);
				player.getInterfaceState().setNextDialogueId(4, 215);
				break;
			case 223:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2726, 3485, 0));
				player.getActionSender().sendMessage("Welcome to Camelot!");
				break;
			case 224:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3283, 3371, 0));
				player.getActionSender().sendMessage(
						"Welcome to the Varrock Mine!");
				break;
			case 225:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3301, 3311, 0));
				player.getActionSender().sendMessage(
						"Welcome to the Al-Kharid Mine!");
				break;
			case 226:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2810, 3434, 0));
				player.getActionSender().sendMessage("Welcome to Catherby!");
				break;
			case 227:
				player.getActionSender().sendDialogue(
						"Where would you like to go?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Catherby", "TzHaar",
						"Dragon Cave", "Abyss", "Previous Page..");
				player.getInterfaceState().setNextDialogueId(0, 226);
				player.getInterfaceState().setNextDialogueId(1, 228);
				player.getInterfaceState().setNextDialogueId(2, 229);
				player.getInterfaceState().setNextDialogueId(3, 230);
				player.getInterfaceState().setNextDialogueId(4, 222);
				break;
			case 228:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2445, 5174, 0));
				player.getActionSender().sendMessage("Welcome to TzHaar!");
				break;
			case 229:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2710, 9470, 0));
				player.getActionSender().sendMessage(
						"Welcome to the Dragon Cave!");
				break;
			case 230:
				player.getActionSender().sendDialogue(
						"The abyss is WILDERNESS!", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"I don't care, take me to it!", "Nevermind!");
				player.getInterfaceState().setNextDialogueId(0, 231);
				player.getInterfaceState().setNextDialogueId(1, 98);
				break;
			case 231:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3039, 4834, 0));
				player.getActionSender().sendMessage("Welcome to the Abyss!");
				break;
			case 232:
				player.getActionSender().sendDialogue("Which way?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT, "Up",
						"Down");
				player.getInterfaceState().setNextDialogueId(0, 233);
				player.getInterfaceState().setNextDialogueId(1, 234);
				break;
			case 233:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3205, 3209, 2));
				break;
			case 234:
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(3205, 3209, 0));
				break;
			case 235:

				player.getActionSender().removeChatboxInterface();
				player.getActionSender().sendDialogue("Archaeologist",
						DialogueType.NPC, 1918, FacialAnimation.DEFAULT,
						"Hmmmm...");
				player.getInterfaceState().setNextDialogueId(0, 236);
				break;
			case 237:
				player.getActionSender().removeChatboxInterface();
				player.getActionSender().sendDialogue("Diango",
						DialogueType.NPC, 970, FacialAnimation.DEFAULT,
						"Howdy, Partner! I'm really busy at the moment.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 238:
				player.getActionSender().removeChatboxInterface();
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(),
						DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DELIGHTED_EVIL,
						"Hello there, " + player.getName() + ". I am the "
								+ npc.getDefinition().getName() + ".",
						"As a loyal veteran of this village known as Draynor,",
						"I am currently offering many services which may",
						"aid you on your adventures.");
				player.getInterfaceState().setNextDialogueId(0, 700);
				break;

			case 239:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Hello there, would you like to sail to Karajma? It's free!");
				player.getInterfaceState().setNextDialogueId(0, 240);
				break;
			case 240:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Go to Karajma.", "Nevermind.");
				player.getInterfaceState().setNextDialogueId(0, 241);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 241:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Karajma please.");
				player.getInterfaceState().setNextDialogueId(0, 242);
				break;
			case 242:
				player.getActionSender().sendMessage(
						"You step the boat and set asail...");
				player.getActionSender().sendMessage(
						"The boat sets shore at Karajma.");
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2956, 3146, 0));
				break;
			case 243:

				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Ah, hello there traveler. I am Duke Horacio,",
						"what can I do for you?");
				player.getInterfaceState().setNextDialogueId(0, 245);
				break;
			case 245:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Do you have any quests?",
						"Can I have an anti-dragon shield?", "Nevermind.");
				player.getInterfaceState().setNextDialogueId(0, 246);
				player.getInterfaceState().setNextDialogueId(1, 390);
				player.getInterfaceState().setNextDialogueId(2, -1);
				break;
			case 246:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Do you have any quests?");
				player.getInterfaceState().setNextDialogueId(0, 247);
				break;
			case 247:

				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"As a matter of fact, I do have this strange talisman..",
								"Why don't you take it to Sedridor, the head of the",
								"Wizards Tower in Draynor?");
				player.getInterfaceState().setNextDialogueId(0, 248);
				break;
			case 248:
				Item talisman = new Item(1438, 1);
				if (player.getInventory().hasRoomFor(talisman)) {
					player.getActionSender().sendChatboxInterface(210);
					player.getActionSender().sendString(210, 0,
							"The Duke hands you a talisman.");
					player.getInventory().add(talisman);
					if (!player.getQuestStorage().hasStarted(
							QuestRepository.get(RuneMysteries.QUEST_ID))) {
						QuestRepository.get(RuneMysteries.QUEST_ID).start(
								player);
					}
				} else {
					player.getActionSender().sendChatboxInterface(210);
					player.getActionSender().sendString(210, 0,
							"Not enough space in your inventory.");
				}
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 249:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Why hello there, what can I help you with?");
				player.getInterfaceState().setNextDialogueId(0, 250);
				break;
			case 250:
				if (player.getQuestStorage().getQuestStage(
						RuneMysteries.QUEST_ID) == 1
						&& player.getInventory().contains(1438)) {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"Rune Mysteries", "Nevermind.");
					player.getInterfaceState().setNextDialogueId(0, 251);
					player.getInterfaceState().setNextDialogueId(1, -1);
				} else if (player.getQuestStorage().getQuestStage(
						RuneMysteries.QUEST_ID) == 3
						&& player.getInventory().contains(291)) {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"Rune Mysteries", "Nevermind.");
					player.getInterfaceState().setNextDialogueId(0, 263);
					player.getInterfaceState().setNextDialogueId(1, -1);
				} else {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Hmmmm..nevermind!");
					player.getInterfaceState().setNextDialogueId(0, -1);
				}
				break;
			case 251:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Well, Duke Horacio gave me this...");
				player.getInterfaceState().setNextDialogueId(0, 252);
				break;
			case 252:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"You hand the talisman to the wizard.");
				player.getInventory().remove(
						player.getInventory().getById(1438));
				player.getInterfaceState().setNextDialogueId(0, 253);
				break;
			case 253:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.HAPPY,
								"Th-this talisman you brought me...it is the last piece of",
								"the puzzle. Finally! The legacy of our ancestors will",
								"return to us once more.");
				player.getInterfaceState().setNextDialogueId(0, 254);
				break;
			case 254:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"The head wizard gives you a research package.");
				player.getInventory().add(new Item(290, 1));
				player.getInterfaceState().setNextDialogueId(0, 255);
				break;
			case 255:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Bring that package to Aubury in Varrock.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(RuneMysteries.QUEST_ID,
						2);
				break;
			case 256:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hi there, how can I help you?");
				player.getInterfaceState().setNextDialogueId(0, 257);
				break;
			case 257:
				if (player.getQuestStorage().getQuestStage(
						RuneMysteries.QUEST_ID) == 2
						&& player.getInventory().contains(290)) {
					player.getActionSender().sendDialogue("Select an Option",
							DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
							"Rune Mysteries", "Nevermind.");
					player.getInterfaceState().setNextDialogueId(0, 258);
					player.getInterfaceState().setNextDialogueId(1, -1);
				} else {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Nothing..nothing.");
					player.getInterfaceState().setNextDialogueId(0, -1);
				}
				break;
			case 258:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"The head wizard told me to give you this package.");
				player.getInterfaceState().setNextDialogueId(0, 259);
				break;
			case 259:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"You hand the package to the Aubury.");
				player.getInventory()
						.remove(player.getInventory().getById(290));
				player.getInterfaceState().setNextDialogueId(0, 260);
				break;
			case 260:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"Aubury studies the package.");
				player.getInterfaceState().setNextDialogueId(0, 261);
				break;
			case 261:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"No, I'm getting ahead of myself. Take this summary of",
								"my research back to Sedridor in the basement of the",
								"Wizards' Tower. He will know whether or not to",
								"let you in on our little secret.");
				player.getInterfaceState().setNextDialogueId(0, 262);
				break;
			case 262:
				Item summary = new Item(291, 1);
				player.getActionSender().sendChatboxInterface(210);
				if (player.getInventory().hasRoomFor(summary)) {
					player.getActionSender().sendString(210, 0,
							"Aubury hands you the notes.");
					player.getInventory().add(summary);
				} else {
					player.getActionSender().sendString(210, 0,
							"Aubury puts the notes in your bank.");
					player.getBank().add(summary);
				}
				player.getQuestStorage().setQuestStage(RuneMysteries.QUEST_ID,
						3);
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 263:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"You hand the notes to the head wizard.");
				player.getInventory()
						.remove(player.getInventory().getById(291));
				player.getInterfaceState().setNextDialogueId(0, 264);
				break;
			case 264:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"My god....then it is true!");
				player.getInterfaceState().setNextDialogueId(0, 265);
				break;
			case 265:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What is it?!");
				player.getInterfaceState().setNextDialogueId(0, 266);
				break;
			case 266:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"Very long ago, magicians used to craft runes for",
						"their spells. The talismans have been the key",
						"to this all along, we can finally craft runes again!");
				player.getInterfaceState().setNextDialogueId(0, 267);
				break;
			case 267:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"I thank you greatly for your help. Please accept",
						"the talisman that you gave me, it is our gift",
						"to you.");
				player.getInterfaceState().setNextDialogueId(0, 268);
				break;
			case 268:
				player.getActionSender().removeChatboxInterface();
				if (player.getInventory().hasRoomFor(new Item(1438, 1))) {
					player.getInventory().add(new Item(1438));
				} else {
					player.getBank().add(new Item(1438));
					player.getActionSender().sendMessage(
							"The talisman has been added to your bank.");
				}
				player.getQuestStorage().setQuestStage(RuneMysteries.QUEST_ID,
						4);
				QuestRepository.get(RuneMysteries.QUEST_ID).end(player);
				break;
			case 269:
				if (QuestRepository.get(LostCity.QUEST_ID).hasRequirements(
						player)) {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT, "Hello there traveler.");
					player.getInterfaceState().setNextDialogueId(0, 271);
				} else {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"Go away! I only talk to those who have the",
							"stats for Lost City.");
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 271:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What are you camped out here for?");
				player.getInterfaceState().setNextDialogueId(0, 272);
				break;
			case 272:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"We're looking for Zanaris...GAH! I mean we're not",
						"here for any particular reason at all.");
				player.getInterfaceState().setNextDialogueId(0, 273);
				break;
			case 273:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Who's Zanaris?");
				player.getInterfaceState().setNextDialogueId(0, 274);
				break;
			case 274:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Ahahahaha! Zanaris isn't a person! It's a magical hidden",
								"city filled with treasures and rich.. uh, nothing. It's",
								"nothing.");
				player.getInterfaceState().setNextDialogueId(0, 275);
				break;
			case 275:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"If it's hidden how are you planning to find it?");
				player.getInterfaceState().setNextDialogueId(0, 276);
				break;
			case 276:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Well, we don't want to tell anyone else about that,",
								"because we don't want anyone else sharing in all that",
								"glory and treasure.");
				player.getInterfaceState().setNextDialogueId(0, 277);
				break;
			case 277:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.ANGER_1,
						"Well it looks to me like YOU don't know EITHER",
						"seeing as you're all just sat around here.");
				player.getInterfaceState().setNextDialogueId(0, 278);
				break;
			case 278:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Of course we know! We just haven't found which tree",
						"the stupid leprechaun's hiding in yet!");
				player.getInterfaceState().setNextDialogueId(0, 279);
				break;
			case 279:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"So a leprechaun knows where Zanaris is, eh?");
				player.getInterfaceState().setNextDialogueId(0, 280);
				break;
			case 280:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Ye.. uh, no. No, not at all. And even if he did - which",
								"he doesn't - he DEFINITELY ISN'T hiding in some",
								"tree around here. Nope, definitely not. Honestly.");
				player.getInterfaceState().setNextDialogueId(0, 281);
				break;
			case 281:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.HAPPY,
						"Thanks for the help!");
				player.getInterfaceState().setNextDialogueId(0, 282);
				break;
			case 282:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DISTRESSED,
								"Help? What help? I didn't help! Please don't say I did,",
								"I'll get in trouble!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				QuestRepository.get(LostCity.QUEST_ID).start(player);
				player.getQuestStorage().setQuestStage(LostCity.QUEST_ID, 1);
				break;
			case 283:
				// if (player.getQuestStorage().getQuestStage(LostCity.QUEST_ID)
				// == 1) {
				player.getActionSender().sendDialogue("Shamus",
						DialogueType.NPC, 654, FacialAnimation.DISINTERESTED,
						"Ay yer big elephant! You've caught me, to be sure!",
						"What would an elephant like yer be wanting wid ol'",
						"Shamus then?");
				player.getInterfaceState().setNextDialogueId(0, 284);
				// }
				break;
			case 284:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I want to find Zanaris.");
				player.getInterfaceState().setNextDialogueId(0, 285);
				break;
			case 285:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"Zanaris is it now? Well well well... Yer'll be needing to",
								"going to that funny little shed out there in the",
								"swamp, so you will.");
				player.getInterfaceState().setNextDialogueId(0, 286);
				break;
			case 286:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"...but... I thought... Zanaris was a city...?");
				player.getInterfaceState().setNextDialogueId(0, 287);
				break;
			case 287:
				player.getActionSender().sendDialogue("Shamus",
						DialogueType.NPC, 654, FacialAnimation.DEFAULT,
						"Aye, that it is!");
				player.getInterfaceState().setNextDialogueId(0, 288);
				break;
			case 288:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"...How does it fit in a shed then?");
				player.getInterfaceState().setNextDialogueId(0, 289);
				break;
			case 289:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"Ah yer stupid elephant! The city isn't IN the shed! The",
								"doorway to the shed is being a portal to Zanaris, so it",
								"is.");
				player.getInterfaceState().setNextDialogueId(0, 290);
				break;
			case 290:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"So I just walk into the shed and end up in Zanaris",
						"then?");
				player.getInterfaceState().setNextDialogueId(0, 291);
				break;
			case 291:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"Oh I was forgetting to say? Yer need to be carrying a",
								"Dramenwood staff to be getting there! Otherwise Yer'll",
								"just be ending up in the shed.");
				player.getInterfaceState().setNextDialogueId(0, 292);
				break;
			case 292:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"So where would I get a staff?");
				player.getInterfaceState().setNextDialogueId(0, 293);
				break;
			case 293:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"Dramenwood staffs are crafted from the branches of the",
								"Dramen tree, so they are. I hear there's a Dramen",
								"tree over on the island of Entrana in a cave");
				player.getInterfaceState().setNextDialogueId(0, 294);
				break;
			case 294:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"or some such. There would probably be a good place",
								"for an elephant like yer to be starting looking I reckon.");
				player.getInterfaceState().setNextDialogueId(0, 295);
				break;
			case 295:
				player.getActionSender()
						.sendDialogue(
								"Shamus",
								DialogueType.NPC,
								654,
								FacialAnimation.DEFAULT,
								"The monks are running a ship from Port Sarim to",
								"Entrana, I hear too. Now leave me alone yer elephant!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(LostCity.QUEST_ID, 2);
				break;
			case 296:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Do you seek passage to holy Entrana? If so you must",
						"leave your weaponry and armour behind. This is",
						"Saradomin's will.");
				player.getInterfaceState().setNextDialogueId(0, 297);
				break;
			case 297:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"No, not right now.", "Yes, okay, I'm ready to go.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 298);
				break;
			case 298:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Yes, okay, I'm ready to go.");
				player.getInterfaceState().setNextDialogueId(0, 299);
				break;
			case 299:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Very well. One moment please.");
				player.getInterfaceState().setNextDialogueId(0, 300);
				break;
			case 300:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"The Monk quickly searches you.");
				boolean accepted = true;
				for (Item item : player.getEquipment().toArray()) {
					if (item != null && item.getDefinition() != null) {
						if (item.getDefinition().getName().toLowerCase()
								.contains("platebody")
								|| item.getDefinition().getName().toLowerCase()
										.contains("platelegs")
								|| item.getDefinition().getName().toLowerCase()
										.contains("helm")) {
							accepted = false;
							break;
						}
					}
					if (item != null && item.getEquipmentDefinition() != null) {
						if (item.getEquipmentDefinition().getType()
								.equals(EquipmentType.WEAPON)) {
							accepted = false;
							break;
						}
					}
				}
				for (Item item : player.getInventory().toArray()) {
					if (item != null && item.getDefinition() != null) {
						if (item.getDefinition().getName().toLowerCase()
								.contains("platebody")
								|| item.getDefinition().getName().toLowerCase()
										.contains("platelegs")
								|| item.getDefinition().getName().toLowerCase()
										.contains("helm")) {
							accepted = false;
							break;
						}
					}
					if (item != null && item.getEquipmentDefinition() != null) {
						if (item.getEquipmentDefinition().getType()
								.equals(EquipmentType.WEAPON)) {
							accepted = false;
							break;
						}
					}
				}
				if (accepted) {
					player.getInterfaceState().setNextDialogueId(0, 301);
				} else {
					player.getInterfaceState().setNextDialogueId(0, 302);
				}
				break;
			case 301:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"All is satisfactory. You may board the boat now.");
				player.getInterfaceState().setNextDialogueId(0, 303);
				break;
			case 302:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANNOYED,
						"You cannot bring weapons or armour to Entrana!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 303:
				player.getActionSender().sendMessage(
						"You step the boat and set asail...");
				player.getActionSender().sendMessage(
						"The boat sets shore at Entrana.");
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2834, 3335, 0));
				break;
			case 304:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Be careful going in there! You are unarmed, and there",
								"is much evilness lurking down there! The evillness seems",
								"to block off our contact with the gods");
				player.getInterfaceState().setNextDialogueId(0, 305);
				break;
			case 305:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"so our prayers seem to have low effect down there. Oh,",
								"also, you won't be able to come back up this way. This",
								"ladder only goes one way!");
				player.getInterfaceState().setNextDialogueId(0, 306);
				break;
			case 306:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I don't think I'm strong enough to enter yet.",
						"Well that is a risk I will have to take.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 307);
				break;
			case 307:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Well that is a risk I will have to take.");
				player.getInterfaceState().setNextDialogueId(0, 308);
				break;
			case 308:
				player.getActionSender().sendMessage(
						"You climb down the ladder.");
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2822, 9774, 0));
				break;
			case 309:
				if (player.getQuestStorage().getQuestStage(LostCity.QUEST_ID) == 2) {
					player.setTeleportTarget(Location.create(player
							.getLocation().getX(), player.getLocation().getY(),
							player.getIndex() * 4));
					NPC tree = new NPC(NPCDefinition.forId(655),
							Location.create(2861, 9736, player.getIndex() * 4),
							Location.create(2856, 9732, 0), Location.create(
									2865, 9747, 0), 6);
					World.getWorld().register(tree);
					tree.getCombatState().startAttacking(player, true);
				}
				break;
			case 310:
				if (player.getQuestStorage().getQuestStage(
						QuestRepository.get(LostCity.QUEST_ID)) >= 3) {
					if (player.getEquipment().get(
							EquipmentType.WEAPON.getSlot()) != null
							&& player.getEquipment()
									.get(EquipmentType.WEAPON.getSlot())
									.getId() == 772) {
						player.getActionSender().sendChatboxInterface(210);
						player.getActionSender()
								.sendString(210, 0,
										"As you touch the door handle, your staff begins to glow.");
						player.getInterfaceState().setNextDialogueId(0, 311);
					}
				}
				break;
			case 311:
				player.getActionSender().sendMessage(
						"You are suddenly teleported to Zanaris.");
				player.getActionSender().removeChatboxInterface();
				player.setTeleportTarget(Location.create(2452, 4475, 0));
				if (player.getQuestStorage().getQuestStage(
						QuestRepository.get(LostCity.QUEST_ID)) == 3) {
					player.getQuestStorage().setQuestStage(
							QuestRepository.get(LostCity.QUEST_ID), 4);
					QuestRepository.get(LostCity.QUEST_ID).end(player);
				}
				break;
			case 312:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"If you would like to enter the market, it is a 5000gp",
								"toll.");
				player.getInterfaceState().setNextDialogueId(0, 313);
				break;
			case 313:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Nevermind.", "Sure, here's 5000gp.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 314);
				break;
			case 314:
				if (player.getInventory().getById(995) != null
						&& player.getInventory().getById(995).getCount() >= 5000) {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"Thank you. You may now enter.");
					player.getInterfaceState().setNextDialogueId(0, 315);
				} else {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"You don't have enough money!");
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 315:
				if (player.getInventory().getById(995) != null
						&& player.getInventory().getById(995).getCount() >= 5000) {
					player.getInventory().remove(new Item(995, 5000));
					player.setTeleportTarget(Location.create(2470, 4437, 0));
					player.getActionSender().sendMessage(
							"The Door Man teleports you into the market.");
				}
				player.getActionSender().removeChatboxInterface();
				break;
			case 316:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Greetings, your majesty.");
				player.getInterfaceState().setNextDialogueId(0, 317);
				break;
			case 317:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Well hello there, what do you want?");
				if (!player.getQuestStorage().hasStarted(
						QuestRepository.get(PriestInPeril.QUEST_ID))) {
					player.getInterfaceState().setNextDialogueId(0, 318);
				} else if (player.getQuestStorage().getQuestStage(
						PriestInPeril.QUEST_ID) == 3) {
					player.getInterfaceState().setNextDialogueId(0, 343);
				} else {
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 318:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'm looking for a quest!");
				player.getInterfaceState().setNextDialogueId(0, 319);
				break;
			case 319:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"A quest you say? Hmm, what an odd request to make",
								"of the king. It's funny you should mention it though, as",
								"there is something you can do for me.");
				player.getInterfaceState().setNextDialogueId(0, 320);
				break;
			case 320:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Are you aware of the temple east of here? It stands on",
								"the river Salve and guards the entrance to the lands of",
								"Morytania?");
				player.getInterfaceState().setNextDialogueId(0, 321);
				break;
			case 321:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"No, I don't think I know it..");
				player.getInterfaceState().setNextDialogueId(0, 322);
				break;
			case 322:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hmm, how strange that you don't. Well anyway, it has",
						"been some days since I last heard from Drezel, the",
						"priest who lives there.");
				player.getInterfaceState().setNextDialogueId(0, 323);
				break;
			case 323:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Be a sport and make sure nothing untoward",
								"has happened to the silly old codger for me, would you?");
				player.getInterfaceState().setNextDialogueId(0, 324);
				break;
			case 324:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure.", "No, that sounds boring.");
				player.getInterfaceState().setNextDialogueId(0, 325);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 325:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure. I don't have anything better to do right now.");
				player.getInterfaceState().setNextDialogueId(0, 326);
				QuestRepository.get(PriestInPeril.QUEST_ID).start(player);
				break;
			case 326:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Many thanks adventurer! I would have sent one of my",
						"squires but they wanted payment for it!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 327:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender()
						.sendString(211, 0,
								"You knock at the door... You hear a voice from inside. Who are you");
				player.getActionSender().sendString(211, 1,
						"and what do you want?");
				player.getInterfaceState().setNextDialogueId(1, 328);
				break;
			case 328:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Ummmm.....");
				player.getInterfaceState().setNextDialogueId(0, 329);
				break;
			case 329:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Ronald sent me to check on Drezel.");
				player.getInterfaceState().setNextDialogueId(0, 330);
				break;
			case 330:
				player.getActionSender().sendChatboxInterface(214);
				player.getActionSender()
						.sendString(214, 0,
								"(Psst... Hey... Who's Roald? Who's Drezel?)(Uh... isn't Drezel that");
				player.getActionSender()
						.sendString(214, 1,
								"dude upstairs? Oh, wait, Roald's the King of Varrock right?)(He is???");
				player.getActionSender()
						.sendString(214, 2,
								"Aw man... Hey, you deal with this okay?) He's just coming! Wait a");
				player.getActionSender()
						.sendString(214, 3,
								"second! Hello, my name is Drevil. (Drezel!) I mean Drezel. How can");
				player.getActionSender().sendString(214, 4, "I help?");
				player.getInterfaceState().setNextDialogueId(4, 331);
				break;
			case 331:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Well, as I say, the King sent me to make sure",
						"everything's okay with you.");
				player.getInterfaceState().setNextDialogueId(0, 332);
				break;
			case 332:
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender()
						.sendString(210, 0,
								"And, uh, what would you do if everything wasn't okay with me?");
				player.getInterfaceState().setNextDialogueId(0, 333);
				break;
			case 333:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'm not sure. Ask you what help you need I suppose.");
				player.getInterfaceState().setNextDialogueId(0, 334);
				break;
			case 334:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender()
						.sendString(211, 0,
								"Ah, good, well, I don't think... (Psst... hey... the dog!) OH! Yes of");
				player.getActionSender().sendString(211, 1,
						"course! Will you do me a favor adventurer?");
				player.getInterfaceState().setNextDialogueId(1, 335);
				break;
			case 335:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure. I'm a helpful person!");
				player.getInterfaceState().setNextDialogueId(0, 336);
				break;
			case 336:
				player.getActionSender().sendChatboxInterface(213);
				player.getActionSender()
						.sendString(213, 0,
								"HAHAHAHAHA! Really? Thanks buddy! You see that mausoleum out");
				player.getActionSender()
						.sendString(213, 1,
								"there? There's a horrible big dog underneath it that I'd like you to");
				player.getActionSender()
						.sendString(213, 2,
								"kill for me! It's been really bugging me! Barking all the time and");
				player.getActionSender().sendString(213, 3,
						"stuff! Please kill it for me buddy!");
				player.getInterfaceState().setNextDialogueId(3, 337);
				break;
			case 337:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Okey-dokey, one dead dog coming up.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(PriestInPeril.QUEST_ID,
						2);
				break;
			case 338:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender().sendString(211, 0,
						"You knock at the door. You hear a voice from inside.");
				player.getActionSender().sendString(211, 1,
						"You again? What do you want now?");
				player.getInterfaceState().setNextDialogueId(1, 339);
				break;
			case 339:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I killed that dog for you.");
				player.getInterfaceState().setNextDialogueId(0, 340);
				break;
			case 340:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender()
						.sendString(211, 0,
								"HAHAHAHAHA! Really? Hey, that's great! Yeah thanks alot buddy!");
				player.getActionSender().sendString(211, 1, "HAHAHAHAHAHA!");
				player.getInterfaceState().setNextDialogueId(1, 341);
				break;
			case 341:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What's so funny?");
				player.getInterfaceState().setNextDialogueId(0, 342);
				break;
			case 342:
				player.getActionSender().sendChatboxInterface(212);
				player.getActionSender()
						.sendString(212, 0,
								"HAHAHAHAHA nothing buddy! We're just so greatful to you!");
				player.getActionSender()
						.sendString(212, 1,
								"Yeah maybe you should go tell the King what a great job");
				player.getActionSender().sendString(212, 2,
						"you did buddy! HAHAHA!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(PriestInPeril.QUEST_ID,
						3);
				break;
			case 343:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You have news of Drezel for me?");
				player.getInterfaceState().setNextDialogueId(0, 344);
				break;
			case 344:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.PLAYER,
								-1,
								FacialAnimation.DEFAULT,
								"Yeah I spoke to the guys at the temple and they said",
								"they were being bothered by that dog in the crypt, so I",
								"went and killed it for them. No problem.");
				player.getInterfaceState().setNextDialogueId(0, 345);
				break;
			case 345:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_1,
						"YOU DID WHAT???");
				player.getInterfaceState().setNextDialogueId(0, 346);
				break;
			case 346:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.ANGER_2,
								"Are you mentally deficient??? That guard dog was",
								"protecting the route to Morytania! Without it we could",
								"in severe peril of attack!");
				player.getInterfaceState().setNextDialogueId(0, 347);
				break;
			case 347:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Did I make a mistake?");
				player.getInterfaceState().setNextDialogueId(0, 348);
				break;
			case 348:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.ANGER_2,
								"YES YOU DID!!!!! You need to get there right now",
								"and find out what was happening! Before it is too late for",
								"us all!");
				player.getInterfaceState().setNextDialogueId(0, 349);
				break;
			case 349:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"B-but Drezel TOLD me to...!");
				player.getInterfaceState().setNextDialogueId(0, 350);
				break;
			case 350:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.ANGER_2,
								"No, you absolute cretin! Obviously some fiend has done",
								"something to Drezel and tricked your feeble intellect",
								"into helping them kill that guard dog!");
				player.getInterfaceState().setNextDialogueId(0, 351);
				break;
			case 351:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.ANGER_3,
								"You get back there and do whatever is necessary to",
								"safeguard my kingdom from an attack, or I will see you",
								"beheaded for high treason!");
				player.getInterfaceState().setNextDialogueId(0, 352);
				break;
			case 352:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Y-yes your Highness.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(PriestInPeril.QUEST_ID,
						4);
				break;
			case 353:
				if (player.getQuestStorage().getQuestStage(
						PriestInPeril.QUEST_ID) == 4
						&& player.getInventory().contains(2945)) {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"Oh, thank you! You have found the key!");
					player.getInterfaceState().setNextDialogueId(0, 354);
				} else if (player.getQuestStorage().getQuestStage(
						PriestInPeril.QUEST_ID) == 5
						&& npc.getLocation().getZ() == 0) {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Ah, "
											+ player.getName()
											+ ". I see you finally made it down here.",
									"Things are weres then I feared. I'm not sure if I will",
									"be able to repair the damage.");
					player.getInterfaceState().setNextDialogueId(0, 359);
				} else if (player.getQuestStorage().getQuestStage(
						PriestInPeril.QUEST_ID) == 6
						&& player.getInventory().getCount(7936) >= 25) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"I've brought you some rune essence!");
					player.getInterfaceState().setNextDialogueId(0, 373);
				}
				break;
			case 354:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"You are free to leave now!");
				player.getInterfaceState().setNextDialogueId(0, 355);
				break;
			case 355:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Well excellent work adventurer! Unfortunately, as you",
								"know, I cannot risk waking the vampire in that coffin.");
				player.getInterfaceState().setNextDialogueId(0, 356);
				break;
			case 356:
				if (player.getInventory().contains(2953)) {
					player.getActionSender()
							.sendDialogue(
									player.getName(),
									DialogueType.PLAYER,
									-1,
									FacialAnimation.DEFAULT,
									"I have some water from the Salve. It seems to have",
									"been desecrated though. Do you think you could bless",
									"it for me?");
					player.getInterfaceState().setNextDialogueId(0, 357);
				} else {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"I will find a way to stop the vampire.");
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 357:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Yes, good thinking adventurer! Give it to me, I will bless",
								"it.");
				player.getInterfaceState().setNextDialogueId(0, 358);
				break;
			case 358:
				player.getInventory().remove(new Item(2953, 1));
				player.getInventory().add(new Item(2954, 1));
				player.getActionSender().sendChatboxInterface(210);
				player.getActionSender().sendString(210, 0,
						"The priest blesses the bucket of water.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;

			case 359:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Why, what's happened?");
				player.getInterfaceState().setNextDialogueId(0, 360);
				break;
			case 360:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"From what I can tell, after you killed the guard dog",
						"who protected the entrance to the monuments, those",
						"Zamorakians forced the door into the main chamber");
				player.getInterfaceState().setNextDialogueId(0, 361);
				break;
			case 361:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"and have used some kind of evil potion upon the well",
								"which leads to the source of the river Salve. As they",
								"have done this at the very source of the river.");
				player.getInterfaceState().setNextDialogueId(0, 362);
				break;
			case 362:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"It will spread along the entire river, disrupting the",
								"blessing placed upon it and allowing the evil creatures of",
								"Morytania to invade at their leisure.");
				player.getInterfaceState().setNextDialogueId(0, 363);
				break;
			case 363:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What can we do to prevent that?");
				player.getInterfaceState().setNextDialogueId(0, 364);
				break;
			case 364:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Well, as you can see, I've placed a holy barrier on",
								"the entrance to this room from the South, but it is not",
								"very powerful and requires me to remain");
				player.getInterfaceState().setNextDialogueId(0, 365);
				break;
			case 365:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"here focussing upon keeping it intact. Should an",
								"attack come, they would be able to breach this defence",
								"very quickly indeed. What we need to do is");
				player.getInterfaceState().setNextDialogueId(0, 366);
				break;
			case 366:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"find some kind of way of removing or counteracting the",
								"evil magic that has been put into the river source at the",
								"well, so that the river will flow pure once again.");
				player.getInterfaceState().setNextDialogueId(0, 367);
				break;
			case 367:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Couldn't you just bless the river to purify it?",
						"Like you did with the water I took from the well?");
				player.getInterfaceState().setNextDialogueId(0, 368);
				break;
			case 368:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"No, that would not work, the power I have from",
						"Saradomin is not great enough to cleanse an entire",
						"river of this foul Zamorakian pollutant.");
				player.getInterfaceState().setNextDialogueId(0, 369);
				break;
			case 369:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I have heard rumors recently that Mages have found",
								"some secret ore that absorbs magic into it and allows",
								"them to create runes.");
				player.getInterfaceState().setNextDialogueId(0, 370);
				break;
			case 370:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Should you be able to collect enough of this ore, it is",
								"possible to soak up the evil potion that has been",
								"poured in the river, and purify it.");
				player.getInterfaceState().setNextDialogueId(0, 371);
				break;
			case 371:
				player.getActionSender()
						.sendDialogue(
								player.getName(),
								DialogueType.PLAYER,
								-1,
								FacialAnimation.DEFAULT,
								"Kind of like a filter? Okay, I guess it's worth a try.",
								"How many should I get?");
				player.getInterfaceState().setNextDialogueId(0, 372);
				break;
			case 372:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Twenty-five should be sufficient enough for the task.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(PriestInPeril.QUEST_ID,
						6);
				break;
			case 373:
				if (player.getInventory().remove(new Item(7936, 25)) == 25) {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Excellent! This should do it! I will bless these stones",
									"and place them within the well, and Misthalin should be",
									"protected once more!");
					player.getInterfaceState().setNextDialogueId(0, 374);
				}
				break;
			case 374:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Please take this dagger, it has been handed down within",
								"my family for generations and is filled with the power of",
								"Saradomin. You will find that");
				player.getInterfaceState().setNextDialogueId(0, 375);
				break;
			case 375:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"it has the power to prevent werewolves from adopting",
						"their wolf form in combat as long as you have it",
						"equipped.");
				player.getInterfaceState().setNextDialogueId(0, 376);
				break;
			case 376:
				player.getActionSender().removeChatboxInterface();
				player.getInventory().add(new Item(2952, 1));
				player.getSkills().addExperience(Skills.PRAYER, 1406);
				player.getQuestStorage().setQuestStage(PriestInPeril.QUEST_ID,
						7);
				QuestRepository.get(PriestInPeril.QUEST_ID).end(player);
				break;
			case 377:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello, I am Turael. How can I help you,", "traveler?");
				player.getInterfaceState().setNextDialogueId(0, 378);
				break;
			case 378:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Nevermind.", "Can I have a slayer task?");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 379);
				break;
			case 379:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Can I have a slayer task?");
				player.getInterfaceState().setNextDialogueId(0, 380);
				break;
			case 380:
				Slayer.getSingleton().setNewTask(player,
						npc.getDefinition().getId());
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(),
						DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DEFAULT,
						"Hmm.. very well. Your new task is to kill",
						player.getSlayerTask().getCount() + " "
								+ player.getSlayerTask().getName() + ".");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 381:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I am Mazchna. What do you want?");
				player.getInterfaceState().setNextDialogueId(0, 378);
				break;
			case 382:
				player.getActionSender().sendDialogue("Enter the Tunnel?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes.", "I don't think I'm ready yet.");
				player.getInterfaceState().setNextDialogueId(0, 383);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 383:
				player.setTeleportTarget(Location.create(3551, 9693, 0));
				break;
			case 384:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Oh, thank you for killing that jailer!");
				player.getInterfaceState().setNextDialogueId(0, 385);
				break;
			case 385:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Not a problem!");
				player.getInterfaceState().setNextDialogueId(0, 386);
				break;
			case 386:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hmmm, I'd like to reward you in some way,",
						"but all that I have is this key. Would you",
						"like it?");
				player.getInterfaceState().setNextDialogueId(0, 387);
				break;
			case 387:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Sure!", "No thanks.");
				player.getInterfaceState().setNextDialogueId(0, 388);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 388:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Sure!");
				player.getInterfaceState().setNextDialogueId(0, 389);
				break;
			case 389:
				Item item = new Item(1590);
				if (!player.getInventory().add(item)) {
					World.getWorld().createGroundItem(
							new GroundItem(player.getName(), item,
									player.getLocation()), player);
				}
				player.getActionSender().removeChatboxInterface();
				break;
			case 390:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Can I have an Anti-Dragon shield?");
				player.getInterfaceState().setNextDialogueId(0, 391);
				break;
			case 391:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Sure, but only until Steve puts in", "Dragon Slayer!");
				player.getInterfaceState().setNextDialogueId(0, 392);
				break;
			case 392:
				item = new Item(1540);
				if (!player.getInventory().add(item)) {
					World.getWorld().createGroundItem(
							new GroundItem(player.getName(), item,
									player.getLocation()), player);
				}
				player.getActionSender().removeChatboxInterface();
				break;
			case 393:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I am Vannaka, how can I help you?");
				player.getInterfaceState().setNextDialogueId(0, 378);
				break;
			case 394:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hello.");
				if (!player.getQuestStorage().hasStarted(
						QuestRepository.get(TreeGnomeVillage.QUEST_ID))
						&& !player.getQuestStorage().hasFinished(
								QuestRepository.get(TreeGnomeVillage.QUEST_ID))) {
					player.getInterfaceState().setNextDialogueId(0, 395);
				} else if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 11) {
					player.getInterfaceState().setNextDialogueId(0, 472);
				} else if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 13) {
					player.getInterfaceState().setNextDialogueId(0, 497);
				} else if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 14) {
					player.getInterfaceState().setNextDialogueId(0, 518);
				}
				break;
			case 395:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Well hello stranger. My name's Bolren, I'm king of",
						"the tree gnomes.");
				player.getInterfaceState().setNextDialogueId(0, 396);
				break;
			case 396:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I'm suprised you made it in, maybe I made the maze",
						"too easy.");
				player.getInterfaceState().setNextDialogueId(0, 397);
				break;
			case 397:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Maybe.");
				player.getInterfaceState().setNextDialogueId(0, 398);
				break;
			case 398:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I'm afraid I have more serious concerns at the",
						"moment. Very serious.");
				player.getInterfaceState().setNextDialogueId(0, 399);
				break;
			case 399:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I'll leave you to it then.", "Can I help at all?");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 400);
				break;
			case 400:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Can I help at all?");
				player.getInterfaceState().setNextDialogueId(0, 401);
				break;
			case 401:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I'm glad you asked.");
				player.getInterfaceState().setNextDialogueId(0, 402);
				break;
			case 402:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"The truth is my people are in grave danger. We have",
								"always been protected by the Spirit Tree. No creature",
								"of dark can harm us while its three orbs are in place.");
				player.getInterfaceState().setNextDialogueId(0, 403);
				break;
			case 403:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"We are not a violent race, but we fight when we must.",
								"Many gnomes have fallen battling the dark forces of",
								"Khazard to the North.");
				player.getInterfaceState().setNextDialogueId(0, 404);
				break;
			case 404:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"We became desperate, so we took one orb of protection",
								"to the battlefield. It was a foolish move.");
				player.getInterfaceState().setNextDialogueId(0, 405);
				break;
			case 405:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Khazard troops seized the orb. Now we are completely",
						"defenceless.");
				player.getInterfaceState().setNextDialogueId(0, 406);
				break;
			case 406:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"How can I help?");
				player.getInterfaceState().setNextDialogueId(0, 407);
				break;
			case 407:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"You would be a huge benefit on the battlefield. If you",
								"would go there and try to retrieve the orb, my people",
								"and I will be forever grateful.");
				player.getInterfaceState().setNextDialogueId(0, 408);
				break;
			case 408:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I would be glad to help.",
						"I'm sorry but I won't be involved.");
				player.getInterfaceState().setNextDialogueId(0, 409);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 409:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I would be glad to help.");
				player.getInterfaceState().setNextDialogueId(0, 410);
				break;
			case 410:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Thank you. The battlefield is north of the maze.",
						"Commander Montai will inform you of their current",
						"situation.");
				player.getInterfaceState().setNextDialogueId(0, 411);
				break;
			case 411:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"That is if he's still alive.");
				player.getInterfaceState().setNextDialogueId(0, 412);
				break;
			case 412:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"My assistant shall guide you out. Good luck friend, try",
								"your best to return the orb.");
				player.getInterfaceState().setNextDialogueId(0, 413);
				break;
			case 413:
				player.getActionSender().removeChatboxInterface();
				QuestRepository.get(TreeGnomeVillage.QUEST_ID).start(player);
				player.setTeleportTarget(Location.create(2504, 3195, 0));
				break;
			case 414:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 3) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"How are you doing Montai?");
					player.getInterfaceState().setNextDialogueId(0, 427);
				} else {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Hello.");
					player.getInterfaceState().setNextDialogueId(0, 415);
				}
				break;
			case 415:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 1) {
					player.getActionSender()
							.sendDialogue(npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Hello traveller, are you here to help or just to watch?");
					player.getInterfaceState().setNextDialogueId(0, 416);
				} else if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 2) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Hello.");
					player.getInterfaceState().setNextDialogueId(0, 423);
				} else if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 3) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"How are you doing?");
					player.getInterfaceState().setNextDialogueId(0, 427);
				} else {
					player.getActionSender()
							.sendDialogue(npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"We're too busy in combat! Come back another time.");
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 416:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I've been sent by King Bolren to retrieve the orb of",
						"protection.");
				player.getInterfaceState().setNextDialogueId(0, 417);
				break;
			case 417:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Excellent we need all the help we can get.");
				player.getInterfaceState().setNextDialogueId(0, 418);
				break;
			case 418:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I'm Commander Montai. The orb is in the Khazard",
						"stronghold to the north, but until we weaken their",
						"defences we can't get close.");
				player.getInterfaceState().setNextDialogueId(0, 419);
				break;
			case 419:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What can I do?");
				player.getInterfaceState().setNextDialogueId(0, 420);
				break;
			case 420:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"We really need to strengthen our defences. We",
						"desperately need wood to make more battlements, once",
						"the battlements are gone it's all over. Six loads of",
						"normal logs should do it.");
				player.getInterfaceState().setNextDialogueId(0, 421);
				break;
			case 421:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Okay, I'll gather some wood.",
						"Sorry, I no longer want to be involved.");
				player.getInterfaceState().setNextDialogueId(0, 422);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 422:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Please be as quick as you can, I don't know how much",
						"longer we can hold out.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 2);
				break;
			case 423:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello again, we're still desperate for wood soldier.");
				if (player.getInventory().contains(1511)
						&& player.getInventory().getCount(1511) >= 6) {
					player.getInterfaceState().setNextDialogueId(0, 425);
				} else {
					player.getInterfaceState().setNextDialogueId(0, 424);
				}
				break;
			case 424:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I don't have enough. I'll come back when I do.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 425:
				if (player.getInventory().contains(1511)
						&& player.getInventory().getCount(1511) >= 6) {
					for (int i = 0; i < 6; i++) {
						player.getInventory().remove(new Item(1511, 1));
					}
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"I have some here.",
							"(You give six loads of logs to the commander.)");
					player.getInterfaceState().setNextDialogueId(0, 426);
				} else {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"I don't have enough. I'll come back when I do.");
					player.getInterfaceState().setNextDialogueId(0, 97);
				}
				break;
			case 426:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"That's excellent, now we can make more defensive",
								"battlements. Give me a moment to organize the troops",
								"and then come speak to me. I'll inform you of our next",
								"phase of attack.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 3);
				break;
			case 427:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"We're hanging in there soldier. For the next phase of",
								"our attack we need to breach their stronghold.");
				player.getInterfaceState().setNextDialogueId(0, 428);
				break;
			case 428:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"The ballista can break through the stronghold wall, and",
								"then we can advance and seize back the orb.");
				player.getInterfaceState().setNextDialogueId(0, 429);
				break;
			case 429:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"So what's the problem?");
				player.getInterfaceState().setNextDialogueId(0, 430);
				break;
			case 430:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"From this distance we can't get an accurate enough",
								"shot. We need the correct coordinates of the",
								"stronghold for a direct hit. I've sent out three tracker",
								"gnomes to gather them.");
				player.getInterfaceState().setNextDialogueId(0, 431);
				break;
			case 431:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Have they returned?");
				player.getInterfaceState().setNextDialogueId(0, 432);
				break;
			case 432:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I'm afraid not, and we're running out of time. I need",
								"you to go into the heart of the battlefield, find the",
								"trackers, and bring back the coordinates.");
				player.getInterfaceState().setNextDialogueId(0, 433);
				break;
			case 433:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Do you think you can do it?");
				player.getInterfaceState().setNextDialogueId(0, 434);
				break;
			case 434:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"No, I've had enough of your battle.",
						"I'll try my best.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInterfaceState().setNextDialogueId(1, 435);
				break;
			case 435:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Thank you, you're braver than most.");
				player.getInterfaceState().setNextDialogueId(0, 436);
				break;
			case 436:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I don't know how long I will be able to hold out. Once",
								"you have the coordinates come back and fire the ballista",
								"right into those monsters.");
				player.getInterfaceState().setNextDialogueId(0, 437);
				break;
			case 437:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"If you can retrieve the orb and bring safety back to",
								"my people, none of the blood spilled on this field will be",
								"in vain.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 4);
				break;
			case 438:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 5) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Are you OK?");
					player.getInterfaceState().setNextDialogueId(0, 439);
				}
				break;
			case 439:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"They caught me spying on the stronghold. They beat",
						"and tortured me.");
				player.getInterfaceState().setNextDialogueId(0, 440);
				break;
			case 440:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"But I didn't crack. I told them nothing. They can't",
						"break me!");
				player.getInterfaceState().setNextDialogueId(0, 441);
				break;
			case 441:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'm sorry little man.");
				player.getInterfaceState().setNextDialogueId(0, 442);
				break;
			case 442:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Don't be. I have the position of the stronghold!");
				player.getInterfaceState().setNextDialogueId(0, 526);
				break;
			case 443:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Now leave before they find you and all is lost.");
				player.getInterfaceState().setNextDialogueId(0, 444);
				break;
			case 444:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Hang in there.");
				player.getInterfaceState().setNextDialogueId(0, 445);
				break;
			case 445:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Go!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID,
						player.getQuestStorage().getQuestStage(
								TreeGnomeVillage.QUEST_ID) + 1);
				break;
			case 446:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 4) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Do you have the coordinates of the Khazard",
							"stronghold?");
					player.getInterfaceState().setNextDialogueId(0, 447);
				}
				break;
			case 447:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I managed to get one, although it wasn't easy.",
						"(The gnome gives you the height coordinate)");
				player.getInterfaceState().setNextDialogueId(0, 448);
				break;
			case 448:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Well done.");
				player.getInterfaceState().setNextDialogueId(0, 449);
				break;
			case 449:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"The other two tracker gnomes should have the other",
						"coordinates if they're still alive.");
				player.getInterfaceState().setNextDialogueId(0, 450);
				break;
			case 450:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"OK, take care.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID,
						player.getQuestStorage().getQuestStage(
								TreeGnomeVillage.QUEST_ID) + 1);
				break;
			case 451:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 6) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Are you OK?");
					player.getInterfaceState().setNextDialogueId(0, 452);
				}
				break;
			case 452:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"OK? Who's OK? Not me! Hee hee!");
				player.getInterfaceState().setNextDialogueId(0, 453);
				break;
			case 453:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What's wrong?");
				player.getInterfaceState().setNextDialogueId(0, 454);
				break;
			case 454:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You can't see me, no one can. Monsters, demons,",
						"they're all around me!");
				player.getInterfaceState().setNextDialogueId(0, 455);
				break;
			case 455:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What do you mean?");
				player.getInterfaceState().setNextDialogueId(0, 456);
				break;
			case 456:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"They're dancing, all of them, hee hee.");
				player.getInterfaceState().setNextDialogueId(0, 457);
				break;
			case 457:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Do you have the coordinate for the Khazard",
						"stronghold?");
				player.getInterfaceState().setNextDialogueId(0, 458);
				break;
			case 458:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Who holds the stronghold?");
				player.getInterfaceState().setNextDialogueId(0, 459);
				break;
			case 459:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What?");
				player.getInterfaceState().setNextDialogueId(0, 460);
				break;
			case 460:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"More than my head, less than my fingers.");
				player.getInterfaceState().setNextDialogueId(0, 461);
				break;
			case 461:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"You're mad.");
				player.getInterfaceState().setNextDialogueId(0, 462);
				break;
			case 462:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Dance with me, and Khazard's men are beat.");
				player.getInterfaceState().setNextDialogueId(0, 463);
				break;
			case 463:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'll pray for you little man.");
				player.getInterfaceState().setNextDialogueId(0, 464);
				break;
			case 464:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"All day we pray in the hay, hee hee.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 7);
				break;
			case 465:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 7) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"The tracker gnome was a bit vague about the x",
							"coordinate! What could it be?");
					player.getInterfaceState().setNextDialogueId(0, 466);
				}
				break;
			case 466:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"0001", "0002", "0003", "0004");
				player.getInterfaceState().setNextDialogueId(0, 467);
				player.getInterfaceState().setNextDialogueId(1, 467);
				player.getInterfaceState().setNextDialogueId(2, 468);
				player.getInterfaceState().setNextDialogueId(3, 467);
				break;
			case 467:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Agh! That's not right! I hit a gnome!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 468:
				player.getActionSender()
						.sendString(212, 0,
								"The huge spear flies through the air and screams down directly into");
				player.getActionSender()
						.sendString(212, 1,
								"the Khazard stronghold. A deafening crash echoes over the battlefield");
				player.getActionSender().sendString(212, 2,
						"as the front entrance is reduced to rubble.");
				player.getActionSender().sendChatboxInterface(212);
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 8);
				break;
			case 469:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 8) {
					player.forceChat("I've come for the orb!");
					World.getWorld().submit(new Tickable(3) {

						@Override
						public void execute() {
							for (NPC npc : player.getLocalNPCs()) {
								if (npc.getDefinition().getId() == 478) {
									npc.getCombatState().startAttacking(player,
											false);
									break;
								}
							}
							this.stop();
						}
					});
				}
				break;
			case 470:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 9) {
					for (NPC n : player.getLocalNPCs()) {
						if (n.getDefinition().getId() == 478) {
							n.getCombatState().startAttacking(player, false);
							break;
						}
					}
				}
				break;
			case 471:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) >= 10) {
					player.getActionSender().sendChatboxInterface(211);
					player.getActionSender()
							.sendString(211, 0,
									"You search the chest. Inside you find the gnomes' stolen orb of");
					player.getActionSender().sendString(211, 1, "protection.");
					player.getInterfaceState().setNextDialogueId(1, 97);
					Item reward = new Item(587);
					if (player.getInventory().hasRoomFor(reward)) {
						player.getInventory().add(reward);
					} else {
						World.getWorld().createGroundItem(
								new GroundItem(player.getName(), reward,
										player.getLocation()), player);
					}
					player.getQuestStorage().setQuestStage(
							TreeGnomeVillage.QUEST_ID, 11);
				}
				break;
			case 472:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 11) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"I have the orb.");
					player.getInterfaceState().setNextDialogueId(0, 473);
				}
				break;
			case 473:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Oh my.... The misery, the horror!");
				player.getInterfaceState().setNextDialogueId(0, 474);
				break;
			case 474:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"King Borlen, are you OK?");
				player.getInterfaceState().setNextDialogueId(0, 475);
				break;
			case 475:
				player.getActionSender()
						.sendDialogue(npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Thank you traveller, but it's too late. We're all doomed.");
				player.getInterfaceState().setNextDialogueId(0, 476);
				break;
			case 476:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What happened?");
				player.getInterfaceState().setNextDialogueId(0, 477);
				break;
			case 477:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"They came in the night. I don't know how many, but",
						"enough.");
				player.getInterfaceState().setNextDialogueId(0, 478);
				break;
			case 478:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Who?");
				player.getInterfaceState().setNextDialogueId(0, 479);
				break;
			case 479:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Khazard troops. They slaughtered anyone who got in",
						"their way. Women, children, my wife.");
				player.getInterfaceState().setNextDialogueId(0, 480);
				break;
			case 480:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'm sorry.");
				player.getInterfaceState().setNextDialogueId(0, 481);
				break;
			case 481:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"They took the other orbs, now we are defenceless.");
				player.getInterfaceState().setNextDialogueId(0, 482);
				break;
			case 482:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Where did they take them?");
				player.getInterfaceState().setNextDialogueId(0, 483);
				break;
			case 483:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"They headed north of the stronghold. A warlord carries",
								"the orbs.");
				player.getInterfaceState().setNextDialogueId(0, 484);
				break;
			case 484:
				player.getActionSender().sendDialogue("Select an option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I will find the warlord and bring back the orbs.",
						"I'm sorry but I can't help.");
				player.getInterfaceState().setNextDialogueId(0, 485);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 485:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I will find the warlord and bring back the orbs.");
				player.getInterfaceState().setNextDialogueId(0, 486);
				break;
			case 486:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Thank you, but this task will be tough even for you.",
						"I wish you the best of luck. Once again you are our",
						"only hope.");
				player.getInterfaceState().setNextDialogueId(0, 487);
				break;
			case 487:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"I will safeguard this orb and pray for your safe return.",
								"My assistant will guide you out.");
				player.getInterfaceState().setNextDialogueId(0, 488);
				break;
			case 488:
				if (player.getInventory().remove(new Item(587)) >= 1) {
					player.getQuestStorage().setQuestStage(
							TreeGnomeVillage.QUEST_ID, 12);
					player.setTeleportTarget(Location.create(2504, 3195, 0));
					player.getActionSender().removeChatboxInterface();
				}
				break;
			case 489:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 12) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"You there! Stop!");
					player.getInterfaceState().setNextDialogueId(0, 490);
				}
				break;
			case 490:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Go back to your perky little green friends.");
				player.getInterfaceState().setNextDialogueId(0, 491);
				break;
			case 491:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I've come for the orbs.");
				player.getInterfaceState().setNextDialogueId(0, 492);
				break;
			case 492:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"You're out of your depth traveller. These orbs are part",
								"of a much larger picture.");
				player.getInterfaceState().setNextDialogueId(0, 493);
				break;
			case 493:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"They're stolen goods, now give them to me!");
				player.getInterfaceState().setNextDialogueId(0, 494);
				break;
			case 494:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Ha, you really think you stand a chance? I'll crush",
						"you.");
				player.getInterfaceState().setNextDialogueId(0, 495);
				break;
			case 495:
				npc.getCombatState().startAttacking(player, false);
				player.getActionSender().removeChatboxInterface();
				break;
			case 497:
				if (player.getQuestStorage().getQuestStage(
						TreeGnomeVillage.QUEST_ID) == 13) {
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Bolren, I have returned.");
					player.getInterfaceState().setNextDialogueId(0, 498);
				}
				break;
			case 498:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You made it back! Do you have the orbs?");
				if (player.getInventory().contains(588)) {
					player.getInterfaceState().setNextDialogueId(0, 499);
				} else {
					player.getInterfaceState().setNextDialogueId(0, 500);
				}
				break;
			case 499:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I have them here.");
				player.getInterfaceState().setNextDialogueId(0, 501);
				break;
			case 500:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"No, I must return with them.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 501:
				if (player.getInventory().remove(new Item(588)) >= 1) {
					player.getActionSender()
							.sendDialogue(
									npc.getDefinition().getName(),
									DialogueType.NPC,
									npc.getDefinition().getId(),
									FacialAnimation.DEFAULT,
									"Hooray, you're amazing. I didn't think it was possible",
									"but you've saved us.");
					player.getInterfaceState().setNextDialogueId(0, 502);
				}
				break;
			case 502:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Once the orbs are replaced we will be safe once more.",
								"We must begin the ceremony immediately.");
				player.getInterfaceState().setNextDialogueId(0, 503);
				break;
			case 503:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"What does the ceremony involve?");
				player.getInterfaceState().setNextDialogueId(0, 504);
				break;
			case 504:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"The spirit tree has looked over us for centeries. Now",
								"we must pay our respects.");
				player.getInterfaceState().setNextDialogueId(0, 505);
				break;
			case 505:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender()
						.sendString(211, 0,
								"The gnomes begin to chant. Meanwhile, King Bolren holds the orbs");
				player.getActionSender().sendString(211, 1,
						"of protection out in front of him.");
				player.getInterfaceState().setNextDialogueId(1, 506);
				break;
			case 506:
				player.getActionSender().sendChatboxInterface(211);
				player.getActionSender()
						.sendString(211, 0,
								"The orbs of protection come to rest gently in the branches of the");
				player.getActionSender().sendString(211, 1,
						"ancient spirit tree.");
				player.getInterfaceState().setNextDialogueId(1, 507);
				break;
			case 507:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Now at last my people are safe once more. We can live",
								"in peace again.");
				player.getInterfaceState().setNextDialogueId(0, 508);
				break;
			case 508:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I'm pleased I could help.");
				player.getInterfaceState().setNextDialogueId(0, 509);
				break;
			case 509:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"You are modest brave traveller.");
				player.getInterfaceState().setNextDialogueId(0, 510);
				break;
			case 510:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"For your efforts, take this amulet. It's made from",
						"the same sacred stone as the orbs of protection. It",
						"will help keep you safe on your adventures.");
				player.getInterfaceState().setNextDialogueId(0, 511);
				break;
			case 511:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"Thank you King Bolren.");
				player.getInterfaceState().setNextDialogueId(0, 512);
				break;
			case 512:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"The tree has many other powers, some of which I",
								"cannot reveal. As a friend of the gnome people, I can",
								"now allow you to use the tree's magic to teleport to",
								"other trees grown from related seeds.");
				player.getInterfaceState().setNextDialogueId(0, 513);
				break;
			case 513:
				player.getActionSender().removeChatboxInterface();
				player.getInventory().add(new Item(589));
				player.getSkills().addExperience(Skills.ATTACK, 11450);
				player.getQuestStorage().setQuestStage(
						TreeGnomeVillage.QUEST_ID, 14);
				QuestRepository.get(TreeGnomeVillage.QUEST_ID).end(player);
				break;
			case 514:
				if (player.getQuestStorage().hasFinished(
						QuestRepository.get(TreeGnomeVillage.QUEST_ID))) {
					player.getActionSender().sendDialogue(
							"Where would you like to go?", DialogueType.OPTION,
							-1, FacialAnimation.DEFAULT, "Tree Gnome Village",
							"Varrock");
					player.getInterfaceState().setNextDialogueId(0, 515);
					player.getInterfaceState().setNextDialogueId(1, 516);
					// player.getInterfaceState().setNextDialogueId(2, 517);
				}
				break;
			case 515:
				player.setTeleportTarget(Location.create(2542, 3169, 0));
				player.getActionSender().removeChatboxInterface();
				break;
			case 516:
				player.setTeleportTarget(Location.create(3178, 3505, 0));
				player.getActionSender().removeChatboxInterface();
				break;
			case 517:
				player.setTeleportTarget(Location.create(0, 0, 0));
				player.getActionSender().removeChatboxInterface();
				break;
			case 518:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Here traveler, take another amulet.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				player.getInventory().add(new Item(589));
				break;
			case 519:
				break;
			case 522:
				player.getActionSender().sendDialogue("Select a spellbook",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Regular", "Ancients", "Lunar");
				player.getInterfaceState().setNextDialogueId(0, 523);
				player.getInterfaceState().setNextDialogueId(1, 524);
				player.getInterfaceState().setNextDialogueId(2, 525);
				break;
			case 523:
				player.getActionSender().sendSidebarInterface(
						105,
						MagicCombatAction.SpellBook.MODERN_MAGICS
								.getInterfaceId());
				player.getCombatState().setSpellBook(
						MagicCombatAction.SpellBook.MODERN_MAGICS
								.getSpellBookId());
				player.getActionSender().removeChatboxInterface();
				break;
			case 524:
				player.getActionSender().sendSidebarInterface(
						105,
						MagicCombatAction.SpellBook.ANCIENT_MAGICKS
								.getInterfaceId());
				player.getCombatState().setSpellBook(
						MagicCombatAction.SpellBook.ANCIENT_MAGICKS
								.getSpellBookId());
				player.getActionSender().removeChatboxInterface();
				break;
			case 525:
				player.getActionSender().sendSidebarInterface(
						105,
						MagicCombatAction.SpellBook.LUNAR_MAGICS
								.getInterfaceId());
				player.getCombatState().setSpellBook(
						MagicCombatAction.SpellBook.LUNAR_MAGICS
								.getSpellBookId());
				player.getActionSender().removeChatboxInterface();
				break;
			case 526:
				if (player.getSkills().getLevelForExperience(Skills.FIREMAKING) > 98) {
					player.getActionSender()
							.sendDialogue("null", DialogueType.MESSAGE, -1,
									FacialAnimation.DEFAULT,
									"The gnome tells you the <col=0000FF>y coordinate.");
					player.getInterfaceState().setNextDialogueId(0, 443);
				} else {
					/*
					 * player.getActionSender().sendDialogue("Select an Option",
					 * DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
					 * "Nothing, thanks.");
					 * player.getInterfaceState().setNextDialogueId(0, 34);
					 */
					player.getActionSender().sendDialogue(player.getName(),
							DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
							"Nothing, thanks.");
					player.getInterfaceState().setNextDialogueId(0, -1);
				}
				break;
			case 527:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"I see you have 99 firemaking, well done! Would you",
						"like to buy the firemaking cape? It'll be 99,000",
						"coins.");
				player.getInterfaceState().setNextDialogueId(0, 528);
				break;
			case 528:
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"I have 99,000 coins, here you go.",
						"99,000 coins, that's outrageous.");
				player.getInterfaceState().setNextDialogueId(0, 529);
				player.getInterfaceState().setNextDialogueId(1, 56);
				break;
			case 529:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"I have 99,000 coins, here you go.");
				player.getInterfaceState().setNextDialogueId(0, 530);
				break;
			case 530:
				if (player.getInventory().getCount(995) >= 99000) {
					skillcape = player.checkForSkillcape(new Item(9804));
					hood = new Item(9806);
					if (player.getInventory().hasRoomFor(skillcape)
							&& player.getInventory().hasRoomFor(skillcape)) {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT, "Here you go.");
						player.getInventory().remove(new Item(995, 99000));
						player.getInventory().add(hood);
						player.getInventory().add(skillcape);
					} else {
						player.getActionSender().sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC, npc.getDefinition().getId(),
								FacialAnimation.DEFAULT,
								"Perhaps you should clear some space from",
								"your inventory first.");
					}
				} else {
					player.getActionSender().sendDialogue(
							npc.getDefinition().getName(), DialogueType.NPC,
							npc.getDefinition().getId(),
							FacialAnimation.DEFAULT,
							"You don't have 99,000 coins.");
				}
				break;
			case 531:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"99,000 coins, that's outrageous.");
				player.getInterfaceState().setNextDialogueId(0, 98);
				break;
			case 532:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.DEFAULT,
						"Hello traveler, what can I do for you?");
				player.getInterfaceState().setNextDialogueId(0, 526);
				break;

			/**
			 * Tutorial island
			 */

			case 533:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.HAPPY,
								"Greetings! I see you are a new arrival to this land. My",
								"job is to welcome all new visitors. So welcome!");
				player.getInterfaceState().setNextDialogueId(0, 534);
				break;
			case 534:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"You have already learned the first thing needed to",
						"succeed in this world; talking to other people!");
				player.getInterfaceState().setNextDialogueId(0, 535);
				break;
			case 535:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.CALM_2,
								"I would also suggest reading through some of the",
								"supporting information on the website. There you can",
								"find the Knowledge Base, which contains all the",
								"additional information you're ever likely to need. It also");
				player.getInterfaceState().setNextDialogueId(0, 536);
				break;
			case 536:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"contains maps and helpful tips to help you on your",
						"journey.");
				// player.getInterfaceState().setNextDialogueId(0, 537);
				break;

			case 700: // wise old man in draynor vilage.
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.ON_ONE_HAND,
						"What kinds of services are you offering?");
				player.getInterfaceState().setNextDialogueId(0, 701);
				break;
			case 701:
				player.getActionSender().sendDialogue("Select a Service",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Teleportation", "Healing", "Knowledge", "Gambling");
				player.getInterfaceState().setNextDialogueId(0, 702);
				player.getInterfaceState().setNextDialogueId(1, 703);
				player.getInterfaceState().setNextDialogueId(2, 704);
				player.getInterfaceState().setNextDialogueId(3, 705);
				break;
			case 702:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.CALM_2,
								"My teleportation services are strictly recreational.",
								"This means I will only teleport you somewhere that will",
								"prove useful, to ensure my powers are not being abused.",
								"Make sure you are well prepared before you proceed.");
				player.getInterfaceState().setNextDialogueId(0, 706);
				break;
			case 703:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"Your mind, body and soul will now be restored ",
						"to a state of clarity and wellness.");
				player.getInterfaceState().setNextDialogueId(0, 707);
				break;
			case 704:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"Ahh, so wisdom is what you seek... Tell me",
						"young " + gender + " what knowledge do you",
						"wish to obtain? I would gladly teach you.");
				player.getInterfaceState().setNextDialogueId(0, 708);
				break;
			case 705:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DELIGHTED_EVIL,
						"Though I do not condone gambling, I will soon be",
						"hosting the Asgarnia Lottery. You will then be",
						"able to enter for your chance to win fortunes",
						"beyond your most creative fantasies.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 706:
				player.getActionSender()
						.sendDialogue("Where would you like to teleport?",
								DialogueType.OPTION, -1,
								FacialAnimation.DEFAULT, "Kingdoms",
								"Training areas", "Minigames", "Wilderness");
				player.getInterfaceState().setNextDialogueId(0, 709);
				player.getInterfaceState().setNextDialogueId(1, 710);
				player.getInterfaceState().setNextDialogueId(2, 711);
				player.getInterfaceState().setNextDialogueId(3, 712);
				break;
			case 707:
				if (player.getCombatState().getPoisonDamage() > 0) {
					player.getCombatState().setPoisonDamage(0, null);
				}
				player.getSkills()
						.increaseLevelToMaximum(
								Skills.ATTACK,
								player.getSkills().getLevelForExperience(
										Skills.ATTACK));
				player.getSkills().increaseLevelToMaximum(
						Skills.DEFENCE,
						player.getSkills()
								.getLevelForExperience(Skills.DEFENCE));
				player.getSkills().increaseLevelToMaximum(
						Skills.STRENGTH,
						player.getSkills().getLevelForExperience(
								Skills.STRENGTH));
				player.getSkills().increaseLevelToMaximum(Skills.MAGIC,
						player.getSkills().getLevelForExperience(Skills.MAGIC));
				player.getSkills().increaseLevelToMaximum(Skills.RANGE,
						player.getSkills().getLevelForExperience(Skills.RANGE));
				player.getSkills().increaseLevelToMaximum(
						Skills.HITPOINTS,
						player.getSkills().getLevelForExperience(
								Skills.HITPOINTS));
				player.getSkills()
						.increaseLevelToMaximum(
								Skills.PRAYER,
								player.getSkills().getLevelForExperience(
										Skills.PRAYER));
				player.getActionSender().removeChatboxInterface();
				break;

			case 708:
				player.getActionSender().sendDialogue(
						"What shall I teach you?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Money making techniques",
						"How to contribute to the community");
				player.getInterfaceState().setNextDialogueId(0, 758);
				player.getInterfaceState().setNextDialogueId(1, 766);
				break;
			case 709:
				player.getActionSender().sendDialogue(
						"Select a kingdom to visit", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Varrock Market",
						"Lumbridge Courtyard", "Falador Square",
						"Camelot Castle");
				player.getInterfaceState().setNextDialogueId(0, 715);
				player.getInterfaceState().setNextDialogueId(1, 716);
				player.getInterfaceState().setNextDialogueId(2, 717);
				player.getInterfaceState().setNextDialogueId(3, 718);
				break;
			case 710:
				player.getActionSender().sendDialogue(
						"Select a type of training", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Low level combat",
						"Mid-level combat", "High level combat",
						"Non-Combat skills");
				player.getInterfaceState().setNextDialogueId(0, 719);
				player.getInterfaceState().setNextDialogueId(1, 720);
				player.getInterfaceState().setNextDialogueId(2, 721);
				player.getInterfaceState().setNextDialogueId(3, 722);
				break;
			case 711:
				player.getActionSender().sendDialogue(
						"Select a minigame to attend", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Fight Pits", "Barrows",
						"Fremminik Trials", "More...");
				player.getInterfaceState().setNextDialogueId(0, 723);
				player.getInterfaceState().setNextDialogueId(1, 724);
				player.getInterfaceState().setNextDialogueId(2, 725);
				player.getInterfaceState().setNextDialogueId(3, 726);
				break;
			case 712:
				player.getActionSender().sendDialogue(
						"Select a PvP combat area", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Edgeville",
						"Varrock (Danger: Multi Combat)",
						"Mage Bank (Danger: Deep Multi Combat)");
				player.getInterfaceState().setNextDialogueId(0, 727);
				player.getInterfaceState().setNextDialogueId(1, 728);
				player.getInterfaceState().setNextDialogueId(2, 729);
				break;
			case 713:
				if (!player.canTeleport()) {
					return;
				}
				player.getActionSender().removeChatboxInterface();
				npc.playAnimation(Animation.create(708, 0));
				npc.playGraphics(Graphic.create(343, 0));
				player.playSound(Sound.create(200, (byte) 1, 0));
				player.playAnimation(Animation.create(714));
				player.playGraphics(Graphic.create(342, 25, 0));
				player.getCombatState().setCanTeleport(false);
				/**
				 * Stops the player from teleporting for 4 cycles (2.4 secs)
				 * 
				 */
				World.getWorld().submit(new Tickable(4) {
					@Override
					public void execute() {
						player.playSound(Sound.create(201, (byte) 1, 0));
						player.playGraphics(Graphic.create(-1));
						player.playAnimation(Animation.create(-1));
						player.setTeleportTarget(getTeleportLocation());
						player.getCombatState().setCanTeleport(true);
						this.stop();
					}
				});
				break;
			case 714:
				setTeleportLocation(null);
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DELIGHTED_EVIL,
								"You have made a wise choice not to depart unprepared.",
								"Return whenever you are ready.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 715:
				setTeleportLocation(TeleportAction.getVarrockLocation());
				player.getActionSender().sendDialogue(
						"Teleport to Varrock Market?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 716:
				setTeleportLocation(TeleportAction.getLumbridgeLocation());
				player.getActionSender().sendDialogue(
						"Teleport to Lumbridge Court?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 717:
				setTeleportLocation(TeleportAction.getFaladorLocation());
				player.getActionSender().sendDialogue(
						"Teleport to Falador Square?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 718:
				setTeleportLocation(TeleportAction.getCamelotLocation());
				player.getActionSender().sendDialogue(
						"Teleport to Camelot Castle?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 719:
				player.getActionSender()
						.sendDialogue("Select a low-level training area",
								DialogueType.OPTION, -1,
								FacialAnimation.DEFAULT, "Rock Crabs", "Cows",
								"Goblins & Spiders", "Giant Rats");
				player.getInterfaceState().setNextDialogueId(0, 730);
				player.getInterfaceState().setNextDialogueId(1, 731);
				player.getInterfaceState().setNextDialogueId(2, 732);
				player.getInterfaceState().setNextDialogueId(3, 733);
				break;
			case 720:
				player.getActionSender().sendDialogue(
						"Select a mid-level training area",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Zombies", "White Knights", "Hill Giants",
						"Angry Beasts");
				player.getInterfaceState().setNextDialogueId(0, 734);
				player.getInterfaceState().setNextDialogueId(1, 735);
				player.getInterfaceState().setNextDialogueId(2, 736);
				player.getInterfaceState().setNextDialogueId(3, 737);
				break;
			case 721:
				player.getActionSender().sendDialogue(
						"Select a high-level training area",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Blue Dragons", "Lesser Demons", "TzHaar Beasts",
						"Black Demons");
				player.getInterfaceState().setNextDialogueId(0, 738);
				player.getInterfaceState().setNextDialogueId(1, 739);
				player.getInterfaceState().setNextDialogueId(2, 740);
				player.getInterfaceState().setNextDialogueId(3, 741);
				break;
			case 722:
				player.getActionSender().sendDialogue(
						"Select a non-combat training area",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Mines", "Agility Courses", "Catherby Beach",
						"Slayer Tower");
				player.getInterfaceState().setNextDialogueId(0, 742);
				player.getInterfaceState().setNextDialogueId(1, 743);
				player.getInterfaceState().setNextDialogueId(2, 744);
				player.getInterfaceState().setNextDialogueId(3, 745);
				break;
			case 723:
				setTeleportLocation(Location.create(2399,
						Misc.random(5177, 5180), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Fight Pits?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to compete in this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 724:
				setTeleportLocation(Location.create(Misc.random(3563, 3566),
						Misc.random(3286, 3290), 0));
				player.getActionSender().sendDialogue("Teleport to Barrows?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to play this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 725:
				setTeleportLocation(Location.create(2666,
						Misc.random(3691, 3694), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Fremminik Trials?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to play this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 726:
				player.getActionSender().sendDialogue(
						"Select a minigame to attend", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Mage Training Arena",
						"Duel Arena", "Castle Wars", "God Wars");
				player.getInterfaceState().setNextDialogueId(0, 754);
				player.getInterfaceState().setNextDialogueId(1, 755);
				player.getInterfaceState().setNextDialogueId(2, 756);
				player.getInterfaceState().setNextDialogueId(3, 757);
				break;
			case 727:
				setTeleportLocation(Location.create(Misc.random(3087, 3089),
						Misc.random(3515, 3517), 0));
				player.getActionSender()
						.sendDialogue(
								"Teleport to Edgeville PvP?",
								DialogueType.OPTION,
								-1,
								FacialAnimation.DEFAULT,
								"Yes, I understand this is a combat zone and am prepared to fight!",
								"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 728:
				setTeleportLocation(Location.create(3243,
						Misc.random(3515, 3518), 0));
				player.getActionSender()
						.sendDialogue(
								"Teleport to Varrock PvP?",
								DialogueType.OPTION,
								-1,
								FacialAnimation.DEFAULT,
								"Yes, I understand this is a combat zone and am prepared to fight!",
								"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 729:
				setTeleportLocation(Location.create(2538,
						Misc.random(4715, 4718), 0));
				player.getActionSender()
						.sendDialogue(
								"Teleport to Mage Bank PvP?",
								DialogueType.OPTION,
								-1,
								FacialAnimation.DEFAULT,
								"Yes, I understand this is a combat zone and am prepared to fight!",
								"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 730:
				setTeleportLocation(Location.create(Misc.random(2666, 2676),
						Misc.random(3710, 3713), 0));
				player.getActionSender().sendDialogue(
						"Train on level-13 Rock Crabs?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 731:
				setTeleportLocation(Location.create(Misc.random(3249, 3261),
						Misc.random(3279, 3289), 0));
				player.getActionSender().sendDialogue("Train on level-2 Cows?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 732:
				setTeleportLocation(Location.create(Misc.random(3245, 3247),
						Misc.random(3235, 3238), 0));
				player.getActionSender().sendDialogue(
						"Train on level-2 Goblins?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 733:
				setTeleportLocation(Location.create(Misc.random(3234, 3240),
						Misc.random(9866, 9869), 0));
				player.getActionSender().sendDialogue(
						"Train on level-3 Giant Rats?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 734:
				// 4392, 4393, 4394
				setTeleportLocation(Location.create(Misc.random(3086, 3122),
						Misc.random(9671, 9673), 0));
				player.getActionSender().sendDialogue(
						"Train on level-30 - 53 Zombies?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 735:
				// 3348 - 3350 - need config for variety of white knights
				setTeleportLocation(Location.create(Misc.random(2968, 2977),
						Misc.random(3342, 3345), 0));
				player.getActionSender().sendDialogue(
						"Train on level-36 White Knights?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 736:
				setTeleportLocation(Location.create(Misc.random(3116, 3121),
						Misc.random(9842, 9845), 0));
				player.getActionSender().sendDialogue(
						"Train on level-28 Hill Giants?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 737:
				setTeleportLocation(Location.create(Misc.random(3295, 3299),
						Misc.random(9824, 9826), 0));
				player.getActionSender().sendDialogue(
						"Train on level-45 Angry Beasts?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 738:
				setTeleportLocation(Location.create(Misc.random(2905, 2912),
						9804, 0));
				player.getActionSender().sendDialogue(
						"Train on level-111 Blue Dragons?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for dangerous combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 739:
				setTeleportLocation(Location.create(Misc.random(2933, 2936),
						Misc.random(9791, 9797), 0));
				player.getActionSender().sendDialogue(
						"Train on level-82 Lesser Demons?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for dangerous combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 740:
				setTeleportLocation(Location.create(Misc.random(2451, 2458),
						Misc.random(5140, 5144), 0));
				player.getActionSender().sendDialogue(
						"Train on level-74 - 149 TzHaar?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for dangerous combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 741:
				setTeleportLocation(Location.create(Misc.random(2862, 2873),
						Misc.random(9775, 9777), 0));
				player.getActionSender().sendDialogue(
						"Train on level-172 Black Demons?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for dangerous combat.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 742:
				setTeleportLocation(Location.create(Misc.random(2862, 2873),
						Misc.random(9775, 9777), 0));
				player.getActionSender().sendDialogue("Select a mine",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Falador Mining Guild", "East Varrock Mines",
						"Rimmington Mines", "Essence Mines");
				player.getInterfaceState().setNextDialogueId(0, 746);
				player.getInterfaceState().setNextDialogueId(1, 747);
				player.getInterfaceState().setNextDialogueId(2, 748);
				player.getInterfaceState().setNextDialogueId(3, 749);
				break;
			case 743:
				setTeleportLocation(Location.create(Misc.random(2862, 2873),
						Misc.random(9775, 9777), 0));
				player.getActionSender().sendDialogue(
						"Select an Agility course", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT, "Gnome Agility Training",
						"Barbarian Agility Training",
						"Wilderness Agility Training");
				player.getInterfaceState().setNextDialogueId(0, 750);
				player.getInterfaceState().setNextDialogueId(1, 751);
				player.getInterfaceState().setNextDialogueId(2, 752);
				break;
			case 744:
				setTeleportLocation(Location.create(Misc.random(2852, 2858),
						Misc.random(3429, 3433), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Catherby Beach?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 745:
				setTeleportLocation(Location.create(Misc.random(3420, 3424),
						Misc.random(3533, 3539), 0));
				player.getActionSender().sendDialogue(
						"Teleport to the Slayer Tower?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 746:
				setTeleportLocation(Location.create(Misc.random(3026, 3029),
						Misc.random(9832, 9834), 0));
				player.getActionSender().sendDialogue(
						"Teleport to the Mining Guild?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 747:
				setTeleportLocation(Location.create(Misc.random(3284, 3286),
						Misc.random(3365, 3367), 0));
				player.getActionSender().sendDialogue(
						"Teleport to the Varrock Mines?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 748:
				setTeleportLocation(Location.create(Misc.random(2978, 2984),
						Misc.random(3243, 3246), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Rimmington Mines?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 749:
				setTeleportLocation(Location.create(Misc.random(2909, 2911),
						Misc.random(4830, 4834), 0));
				player.getActionSender().sendDialogue(
						"Teleport to the Essence Mine?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 750:
				setTeleportLocation(Location.create(Misc.random(2473, 2478),
						Misc.random(3436, 3439), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Gnome Agility?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 751:
				setTeleportLocation(Location.create(Misc.random(2544, 2548),
						Misc.random(3552, 3554), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Barbarian Agility?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 752:
				setTeleportLocation(Location.create(Misc.random(3002, 3005),
						Misc.random(3933, 3935), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Wilderness Agility?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 753:
				setTeleportLocation(Location.create(Misc.random(0, 0),
						Misc.random(0, 0), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Wilderness Agility?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared for my adventure.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 754:
				setTeleportLocation(Location.create(Misc.random(3361, 3365),
						Misc.random(3301, 3307), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Mage Training?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to play this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 755:
				setTeleportLocation(Location.create(Misc.random(3362, 3377),
						Misc.random(3274, 3277), 0));
				player.getActionSender().sendDialogue(
						"Teleport to the Duel Arena?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to compete in this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 756:
				setTeleportLocation(Location.create(Misc.random(2439, 2442),
						Misc.random(3083, 3095), 0));
				player.getActionSender().sendDialogue(
						"Teleport to Castle Wars?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to compete in this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 757:
				setTeleportLocation(Location.create(Misc.random(2880, 2885),
						Misc.random(5307, 5309), 2));
				player.getActionSender().sendDialogue("Teleport to God Wars?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Yes, I'm well prepared to play this minigame.",
						"No, I'm not ready to go quite yet.");
				player.getInterfaceState().setNextDialogueId(0, 713);
				player.getInterfaceState().setNextDialogueId(1, 714);
				break;
			case 758:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"There are many ways for an Asgarnian to acquire",
						"wealth. The most reliable way to earn an income",
						"is to focus on independant sources of profitable",
						"items. There are three ways I suggest you do this...");
				player.getInterfaceState().setNextDialogueId(0, 759);
				break;
			case 759:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.ON_ONE_HAND,
								"The first way is to become a skiller. Skillers have",
								"the advantage of creating their own items. As a skiller",
								"you are an essential asset to maintaining the economy",
								"by crafting and collecting the items to meet the");
				player.getInterfaceState().setNextDialogueId(0, 760);
				break;
			case 760:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.EVIL,
								"needs of the community. Another way is to battle",
								"enemies that drop valuable items. Many of which can be",
								"found in the training areas provided by my teleportation",
								"services. These items can then be sold to other players");
				player.getInterfaceState().setNextDialogueId(0, 761);
				break;
			case 761:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.CALM_1,
								"or converted to gold by the spell of an alchemist.",
								"There are also several single player minigames as well as",
								"bosses that can be tackled solo which offer great rewards.",
								"Some of the finest and most valuable items however,");
				player.getInterfaceState().setNextDialogueId(0, 762);
				break;
			case 762:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.CALM_2,
								"are guarded by very powerful enemies that require",
								"team work to defeat. Please be fair to fellow players",
								"and share any loots that are earned evenly.");
				player.getInterfaceState().setNextDialogueId(0, 763);
				break;
			case 763:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.HAPPY,
						"You may find further information in our online",
						"knowledge base. Would you like to learn about",
						"something else, or might another of my services",
						"be of assistance to you young " + gender + "?");
				player.getInterfaceState().setNextDialogueId(0, 764);
				break;
			case 764:
				player.getActionSender().sendDialogue(
						"May I be of further assistance?", DialogueType.OPTION,
						-1, FacialAnimation.DEFAULT,
						"I'd like to learn about something new",
						"What other services are you offering?",
						"That will be all, thank you.");
				player.getInterfaceState().setNextDialogueId(0, 708);
				player.getInterfaceState().setNextDialogueId(1, 701);
				player.getInterfaceState().setNextDialogueId(2, 765);
				break;
			case 765:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.HAPPY,
								"It was a pleasure speaking with you, "
										+ player.getName() + ".",
								"I wish you the best on your future adventures, farewell!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 766:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.HAPPY,
								"I am delighted to hear you are interested in contributing",
								"to the community. There are several different ways to",
								"do so; I will go over a few essential ways you",
								"can make a positive impact on the community.");
				player.getInterfaceState().setNextDialogueId(0, 767);
				break;
			case 767:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.ON_ONE_HAND,
						"To help the community grow, refer your friends",
						"to join, and encourage players to remain active.",
						"Suggest any ideas to help create a more enjoyable",
						"and memorable experience for everyone.");
				player.getInterfaceState().setNextDialogueId(0, 768);
				break;
			case 768:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.HAPPY,
								"Be kind, courteous and respectful to other members of",
								"the community, and do your best to help those in need.",
								"Offer assistance to those with less experience, they",
								"will surely appreciate it; and you must not forget,");
				player.getInterfaceState().setNextDialogueId(0, 769);
				break;
			case 769:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DELIGHTED_EVIL,
						"what goes around, comes around. No good deed goes",
						"unnoticed around here. You can help build a stable",
						"economy by crafting items such as runes, armor,",
						"weaponry, jewlery, and potions which players require");
				player.getInterfaceState().setNextDialogueId(0, 770);
				break;
			case 770:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"to progress on their adventures. This is important",
						"because not all items are available for purchase at",
						"any shop. This gives you the opportunity to create a",
						"monopoly. Take note of which items are most popular");
				player.getInterfaceState().setNextDialogueId(0, 771);
				break;
			case 771:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_2,
						"and either craft or collect as many of those items",
						"as possible for maximum profits. Please do not abuse",
						"any bugs or glitches you may face, make the right",
						"decision and file a report on our online forums.");
				player.getInterfaceState().setNextDialogueId(0, 772);
				break;
			case 772:
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.CALM_1,
								"Above all, I would suggest the best way to contribute",
								"to the community would be to show initiative.",
								"If you notice an opportunity for improvement",
								"I strongly encourage you to take action and");
				player.getInterfaceState().setNextDialogueId(0, 773);
				break;
			case 773:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"make a suggestion. Administration takes all",
						"suggestions into consideration and appreciates",
						"the effort of the jesture whether the idea is",
						"utilized or not. Have you any more questions?");
				player.getInterfaceState().setNextDialogueId(0, 774);
				break;
			case 774:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, npc.getDefinition().getId(),
						FacialAnimation.EVIL,
						"Might I be rewarded for my contributions?");
				player.getInterfaceState().setNextDialogueId(0, 775);
				break;
			case 775:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_3,
						"LOL U SO FUNNY.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 1000:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DISINTERESTED, "Yes?");
				player.getInterfaceState().setNextDialogueId(0, 1001);
				break;
			case 1001:
				player.getActionSender().sendDialogue(
						"What would you like to say?", DialogueType.OPTION, -1,
						FacialAnimation.DEFAULT,
						"Can I deposit my stuff here?",
						"That wall doesn't look very good.");
				player.getInterfaceState().setNextDialogueId(0, 1002);
				player.getInterfaceState().setNextDialogueId(1, 1003);
				break;
			case 1002:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_2,
						"No. I am a security guard, not a bank clerk.");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 1003:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"That wall doesn't look very good.");
				player.getInterfaceState().setNextDialogueId(0, 1004);
				break;
			case 1004:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DISINTERESTED, "No, it doesn't.");
				player.getInterfaceState().setNextDialogueId(0, 1005);
				break;
			case 1005:
				player.getActionSender().sendDialogue("Select an Option?",
						DialogueType.OPTION, -1, FacialAnimation.DEFAULT,
						"Are you going to tell me what happened?",
						"Alright, sorry to bother you then.");
				player.getInterfaceState().setNextDialogueId(0, 1006);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 1006:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.DISINTERESTED, "I could do.");
				player.getInterfaceState().setNextDialogueId(0, 1007);
				break;
			case 1007:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.CALM_2,
						"Okay, Go on!");
				player.getInterfaceState().setNextDialogueId(0, 1008);
				break;
			case 1008:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.EVIL,
						"Someone smashed the wall when they were",
						"robbing the bank.");
				player.getInterfaceState().setNextDialogueId(0, 1009);
				break;
			case 1009:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DISTRESSED,
						"Someone's robbed the bank?");
				player.getInterfaceState().setNextDialogueId(0, 1010);
				break;
			case 1010:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.EVIL_CONTINUED, "Yes.");
				player.getInterfaceState().setNextDialogueId(0, 1011);
				break;
			case 1011:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1,
						FacialAnimation.DISTRESSED_CONTINUED,
						"But... Was anyone hurt?");
				player.getInterfaceState().setNextDialogueId(0, 1012);
				break;
			case 1012: // structure
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"Yes, but we were able to get more staff",
						"and mend the wall easily enough.");
				player.getInterfaceState().setNextDialogueId(0, 1013);
				break;
			case 1013: // structure
				player.getActionSender()
						.sendDialogue(
								npc.getDefinition().getName(),
								DialogueType.NPC,
								npc.getDefinition().getId(),
								FacialAnimation.DISINTERESTED,
								"The bank has already replaced all of the stolen items",
								"that belonged to the customers.");
				player.getInterfaceState().setNextDialogueId(0, 1014);
				break;
			case 1014:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.CALM_1,
						"Oh, good... but the bank staff got hurt?");
				player.getInterfaceState().setNextDialogueId(0, 1015);
				break;
			case 1015:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_2,
						"Yes, but the new ones are just as good.");
				player.getInterfaceState().setNextDialogueId(0, 1016);
				break;
			case 1016:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.ANGER_1,
						"You're not very nice, are you?");
				player.getInterfaceState().setNextDialogueId(0, 1017);
				break;
			case 1017:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_3,
						"Nobody is expecting me to be nice.");
				player.getInterfaceState().setNextDialogueId(0, 1018);
				break;
			case 1018:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.ON_ONE_HAND,
						"Anyway... So, someone's robbed the bank?");
				player.getInterfaceState().setNextDialogueId(0, 1019);
				break;
			case 1019:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.ANGER_4,
						"Yes");
				player.getInterfaceState().setNextDialogueId(0, 1020);
				break;
			case 1020:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DISTRESSED,
						"Do you know who did it?");
				player.getInterfaceState().setNextDialogueId(0, 1021);
				break;
			case 1021:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(),
						FacialAnimation.PLAIN_EVIL,
						"We are fairly sure we know who the robber was.",
						"The security recording was damaged by the attack",
						"but it still shows his face clearly enough.");
				player.getInterfaceState().setNextDialogueId(0, 1022);
				break;
			case 1022:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.DEFAULT,
						"You've got a security recording?");
				player.getInterfaceState().setNextDialogueId(0, 1023);
				break;
			case 1023:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_1,
						"Yes. Our insurers insisted that we install",
						"a magical scrying orb.");
				player.getInterfaceState().setNextDialogueId(0, 1024);
				break;
			case 1024:
				// TODO: implement cutscene for bank robbery here.
				player.getActionSender().sendDialogue("Select an Option",
						DialogueType.OPTION, -1, FacialAnimation.PLAIN_EVIL,
						"Can I see the recording?", "So who was the robber?");
				player.getInterfaceState().setNextDialogueId(0, 1025);
				player.getInterfaceState().setNextDialogueId(1, 97);
				break;
			case 1025:
				player.getActionSender().sendDialogue(player.getName(),
						DialogueType.PLAYER, -1, FacialAnimation.CALM_2,
						"Can I see the recording?");
				player.getInterfaceState().setNextDialogueId(0, 1026);
				break;
			case 1026:
				player.getActionSender().sendDialogue(
						npc.getDefinition().getName(), DialogueType.NPC,
						npc.getDefinition().getId(), FacialAnimation.CALM_2,
						"I suppose so, but it's quite long.");
				player.getInterfaceState().setNextDialogueId(0, 1027);
				break;
			case 1027:
				player.getActionSender().sendDialogue("", DialogueType.MESSAGE,
						-1, FacialAnimation.DEFAULT,
						"You close your eyes and watch the recording...");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 2000:
				player.getActionSender().sendDialogue("TzHaar-Mej-Jal",
						DialogueType.NPC, 2617, FacialAnimation.PLAIN_EVIL,
						"You're on your own now JalYt.",
						"Prepare to fight for your life!");
				player.getInterfaceState().setNextDialogueId(0, 97);
				break;
			case 97:
			case 98:
			default:
				player.getActionSender().removeChatboxInterface();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Location getTeleportLocation() {
		return teleportLocation;
	}

	public static void setTeleportLocation(Location teleportLocation) {
		DialogueManager.teleportLocation = teleportLocation;
	}

	/**
	 * Used to contain multiple messages in a single case. useful for skilling
	 * for example, in productionaction if the player's level is not high
	 * enough, the message is very similar but slightly different for each
	 * skill, in my opinion, this is a cleaner way of reaching that goal. Yours
	 * truly, Sneakyhearts.
	 * 
	 * @return
	 */
	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		DialogueManager.message = message;
	}

}
