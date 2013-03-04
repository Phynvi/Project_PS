package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.content.combat.magic.Magic;
import server.game.content.combat.magic.TeleOther;
import server.game.content.minigames.Barrows;
import server.game.content.music.Music;
import server.game.content.questhandling.Buttons;
import server.game.content.random.PartyRoom;
import server.game.content.skills.cooking.Cooking;
import server.game.content.skills.core.Herblore;
import server.game.content.skills.crafting.JewelryMaking;
import server.game.content.skills.crafting.LeatherMaking;
import server.game.content.skills.crafting.Tanning;
import server.game.content.skills.crafting.CraftingData.tanningData;
import server.game.content.traveling.GnomeGlider;
import server.game.dialogues.FreeDialogues;
import server.game.items.ExperienceLamp;
import server.game.items.GameItem;
import server.game.items.Teles;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.task.Task;
import server.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);
		GnomeGlider.flightButtons(c, actionButtonId);
		c.getEmoteHandler().startEmote(actionButtonId);
		Buttons.questButtons(c, actionButtonId);
		Magic.handleTeleport(c, actionButtonId);
		if (Config.SOUND && c.musicOn) {
			c.getPlayList().handleButton(actionButtonId);
		}
		for (tanningData t : tanningData.values()) {
			if (actionButtonId == t.getButtonId(actionButtonId)) {
				Tanning.tanHide(c, actionButtonId);
			}
		}
		LeatherMaking.craftLeather(c, actionButtonId);
		if (c.isDead)
			return;
		if (c.playerRights == 3)
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		switch (actionButtonId) {
		
		case 3166:
        if (!c.musicOn)
        	Music.playMusic(c);
			c.musicOn = true;
		break;
			
		case 3162:
			c.musicOn = false;
		break;
		
		case 11666:
			c.getSmithing().sendSmelting();
			break;
		
		case 170:
		   FreeDialogues.handledialogue(c, 591, c.npcType);
		break;
		
		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			if (c.getFletching().fletching)
				c.getFletching().handleFletchingClick(actionButtonId);
			break;
		
		case 15147://Bronze, 1
			c.getSmithing().startSmelting(0, 1);
			break;
		case 15146://Bronze, 5
			c.getSmithing().startSmelting(0, 5);
			break;
		case 10247://Bronze, 10
			c.getSmithing().startSmelting(0, 10);
			break;
		case 15151://Iron, 1
			c.getSmithing().startSmelting(1, 1);
			break;
		case 15150://Iron, 5
			c.getSmithing().startSmelting(1, 5);
			break;
		case 15149://Iron, 10
			c.getSmithing().startSmelting(1, 10);
			break;
		case 15155://silver, 1
			c.getSmithing().startSmelting(2, 1);
			break;
		case 15154://silver, 5
			c.getSmithing().startSmelting(2, 5);
			break;
		case 15153://silver, 10
			c.getSmithing().startSmelting(2, 10);
			break;
		case 15159://steel, 1
			c.getSmithing().startSmelting(3, 1);
			break;
		case 15158://steel, 5
			c.getSmithing().startSmelting(3, 5);
			break;
		case 15157://steel, 10
			c.getSmithing().startSmelting(3, 10);
			break;
		case 15163://gold, 1
			c.getSmithing().startSmelting(4, 1);
			break;
		case 15162://gold, 5
			c.getSmithing().startSmelting(4, 5);
			break;
		case 15161://gold, 10
			c.getSmithing().startSmelting(4, 10);
			break;
		case 29017://mithril, 1
			c.getSmithing().startSmelting(5, 1);
			break;
		case 29016://mithril, 5
			c.getSmithing().startSmelting(5, 5);
			break;
		case 24253://mithril, 10
			c.getSmithing().startSmelting(5, 10);
			break;
		case 29022://addy, 1
			c.getSmithing().startSmelting(6, 1);
			break;
		case 29021://addy, 5
			c.getSmithing().startSmelting(6, 5);
			break;
		case 29019://addy, 10
			c.getSmithing().startSmelting(6, 10);
			break;
		case 29026://rune, 1
			c.getSmithing().startSmelting(7, 1);
			break;
		case 29025://rune, 5
			c.getSmithing().startSmelting(7, 5);
			break;
		case 29024://rune, 10
			c.getSmithing().startSmelting(7, 10);
			break;
		
		case 49022:
			TeleOther.teleOtherLocation(c, c.teleotherType, false);
			break;
		case 49024:
			TeleOther.teleOtherLocation(c, c.teleotherType, true);
			break;
			
		case 10252:
		    ExperienceLamp.buttonClick(c, 1);
		    break;
		case 10253:
		    ExperienceLamp.buttonClick(c, 2);
		    break;
		case 10254:
		    ExperienceLamp.buttonClick(c, 3);
		    break;
		case 10255:
		    ExperienceLamp.buttonClick(c, 4);
		    break;
		case 11000:
		    ExperienceLamp.buttonClick(c, 5);
		    break;
		case 11001:
		    ExperienceLamp.buttonClick(c, 6);
		    break;
		case 11002:
		    ExperienceLamp.buttonClick(c, 7);
		    break;
		case 11003:
		    ExperienceLamp.buttonClick(c, 8);
		    break;
		case 11004:
		    ExperienceLamp.buttonClick(c, 9);
		    break;
		case 11005:
		    ExperienceLamp.buttonClick(c, 10);
		    break;
		case 11006:
		    ExperienceLamp.buttonClick(c, 11);
		    break;
		case 11007:
		    ExperienceLamp.buttonClick(c, 12);
		    break;
		case 47002:
		    ExperienceLamp.buttonClick(c, 20);
		    break;
		case 54090:
		    ExperienceLamp.buttonClick(c, 21);
		    break;
		case 11008:
		    ExperienceLamp.buttonClick(c, 13);
		    break;
		case 11009:
		    ExperienceLamp.buttonClick(c, 14);
		    break;
		case 11010:
		    ExperienceLamp.buttonClick(c, 15);
		    break;
		case 11011:
		    ExperienceLamp.buttonClick(c, 16);
		    break;
		case 11012:
		    ExperienceLamp.buttonClick(c, 17);
		    break;
		case 11013:
		    ExperienceLamp.buttonClick(c, 18);
		    break;
		case 11014:
		    ExperienceLamp.buttonClick(c, 19);
		    break;
		case 11015:
		    ExperienceLamp.buttonClick(c, 20);
		    break;
		
		case 8198:
			PartyRoom.accept(c);
		break;
		
		case 10239:
			if (!c.secondHerb) {
				Herblore.finishUnfinished(c, 1);
			} else {
				Herblore.finishPotion(c, 1);
			}
			break;
		case 10238:
			if (c.secondHerb) {
				Herblore.finishPotion(c, 5);
			} else {
				Herblore.finishUnfinished(c, 5);
			}
			break;
		case 6212:
			if (c.secondHerb) {
				Herblore.finishPotion(c, c.getItems().getItemAmount(c.newItem));
			} else {
				Herblore.finishUnfinished(c,
						c.getItems().getItemAmount(c.doingHerb));
			}
			break;
		case 6211:
			if (c.secondHerb) {
				Herblore.finishPotion(c, c.getItems().getItemAmount(c.newItem));
			} else {
				Herblore.finishUnfinished(c,
						c.getItems().getItemAmount(c.doingHerb));
			}
			break;	
		
		case 53152:
			Cooking.cookItem(c, c.cookingItem, 1, c.cookingObject);
			break;
		case 53151:
			Cooking.cookItem(c, c.cookingItem, 5, c.cookingObject);
			break;
		case 53150:
			Cooking.cookItem(c, c.cookingItem, 10, c.cookingObject);
			break;
		case 53149:
			Cooking.cookItem(c, c.cookingItem, 28, c.cookingObject);
			break;
		
		case 17200:
			if(c.absX == 3563 && c.absY == 9694){
				c.sendMessage("You hear the doors locking mechanism grind open.");
				c.getPA().object(6725, c.objectX, c.objectY, -1, 0);
				c.getPA().removeAllWindows();
				c.getPA().walkTo(-1,0);
				 Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
						c.getPA().object(6725, c.objectX, c.objectY, -2, 0);
						stop();
						}
				 });
				} else {
				c.sendMessage("You hear the doors locking mechanism grind open.");
				c.getPA().object(6725, c.objectX, c.objectY, -2, 0);
				c.getPA().removeAllWindows();
				c.getPA().walkTo(0,1);
				 Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
						c.getPA().object(6725, c.objectX, c.objectY, -1, 0);
						stop();
						}
					});
				}
				break;
			case 17199:
				c.getPA().removeAllWindows();
				c.sendMessage("You got the riddle wrong, and it locks back up.");
				Barrows.wrongPuzzle = true;
				break;
			case 17198:
				c.getPA().removeAllWindows();
				c.sendMessage("You got the riddle wrong, and it locks back up.");
				Barrows.wrongPuzzle = true;
				break;
				
			case 58074:
				c.getBankPin().closeBankPin();
				break;

			case 58073:
                if (c.hasBankpin && !c.requestPinDelete) {
                    c.requestPinDelete = true;
                    c.getBankPin().dateRequested();
                    c.getBankPin().dateExpired();
                    FreeDialogues.handledialogue(c, 1017, 1);
                    c.sendMessage("[Notice] A PIN delete has been requested. Your PIN will be deleted in "+ c.getBankPin().recovery_Delay +" days.");
                    c.sendMessage("To cancel this change just type in the correct PIN.");
                } else {
                    c.sendMessage("[Notice] Your PIN is already pending deletion. Please wait the entire 2 days.");
                    c.getPA().closeAllWindows();
                }
				break;

			case 58025:
			case 58026:
			case 58027:
			case 58028:
			case 58029:
			case 58030:
			case 58031:
			case 58032:
			case 58033:
			case 58034:
				c.getBankPin().bankPinEnter(actionButtonId);
				break;
				
			case 58230:
                if (!c.hasBankpin) {
                    c.getBankPin().openPin();
                } else if (c.hasBankpin && c.enterdBankpin) {
                    c.getBankPin().resetBankPin();
                    c.sendMessage("Your PIN has been deleted as requested.");
                } else {
                    c.sendMessage("Please enter your Bank Pin before requesting a delete.");
                    c.sendMessage("You can do this by simply opening your bank. This is to verify it's really you.");
                    c.getPA().closeAllWindows();
                }
            break;
		
		case 34142: // tab 1
			c.getSI().menuCompilation(1);
			break;

		case 34119: // tab 2
			c.getSI().menuCompilation(2);
			break;

		case 34120: // tab 3
			c.getSI().menuCompilation(3);
			break;

		case 34123: // tab 4
			c.getSI().menuCompilation(4);
			break;

		case 34133: // tab 5
			c.getSI().menuCompilation(5);
			break;

		case 34136: // tab 6
			c.getSI().menuCompilation(6);
			break;

		case 34139: // tab 7
			c.getSI().menuCompilation(7);
			break;

		case 34155: // tab 8
			c.getSI().menuCompilation(8);
			break;

		case 34158: // tab 9
			c.getSI().menuCompilation(9);
			break;

		case 34161: // tab 10
			c.getSI().menuCompilation(10);
			break;

		case 59199: // tab 11
			c.getSI().menuCompilation(11);
			break;

		case 59202: // tab 12
			c.getSI().menuCompilation(12);
			break;
		case 59203: // tab 13
			c.getSI().menuCompilation(13);
			break;
			
		case 33206: // attack
			c.getSI().attackComplex(1);
			c.getSI().selected = 0;
			break;
		case 33209: // strength
			c.getSI().strengthComplex(1);
			c.getSI().selected = 1;
			break;
		case 33212: // Defence
			c.getSI().defenceComplex(1);
			c.getSI().selected = 2;
			break;
		case 33215: // range
			c.getSI().rangedComplex(1);
			c.getSI().selected = 3;
			break;
		case 33218: // prayer
			c.getSI().prayerComplex(1);
			c.getSI().selected = 4;
			break;
		case 33221: // mage
			c.getSI().magicComplex(1);
			c.getSI().selected = 5;
			break;
		case 33224: // runecrafting
			c.getSI().runecraftingComplex(1);
			c.getSI().selected = 6;
			break;
		case 33207: // hp
			c.getSI().hitpointsComplex(1);
			c.getSI().selected = 7;
			break;
		case 33210: // agility
			c.getSI().agilityComplex(1);
			c.getSI().selected = 8;
			break;
		case 33213: // herblore
			c.getSI().herbloreComplex(1);
			c.getSI().selected = 9;
			break;
		case 33216: // theiving
			c.getSI().thievingComplex(1);
			c.getSI().selected = 10;
			break;
		case 33219: // crafting
			c.getSI().craftingComplex(1);
			c.getSI().selected = 11;
			break;
		case 33222: // fletching
			c.getSI().fletchingComplex(1);
			c.getSI().selected = 12;
			break;
		case 47130:// slayer
			c.getSI().slayerComplex(1);
			c.getSI().selected = 13;
			break;
		case 33208: // mining
			c.getSI().miningComplex(1);
			c.getSI().selected = 14;
			break;
		case 33211: // smithing
			c.getSI().smithingComplex(1);
			c.getSI().selected = 15;
			break;
		case 33214: // fishing
			c.getSI().fishingComplex(1);
			c.getSI().selected = 16;
			break;
		case 33217: // cooking
			c.getSI().cookingComplex(1);
			c.getSI().selected = 17;
			break;
		case 33220: // firemaking
			c.getSI().firemakingComplex(1);
			c.getSI().selected = 18;
			break;
		case 33223: // woodcut
			c.getSI().woodcuttingComplex(1);
			c.getSI().selected = 19;
			break;
		case 54104: // farming
			c.getSI().farmingComplex(1);
			c.getSI().selected = 20;
			break;
		
		// crafting + fletching interface:
		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
			
		// 1st tele option
		case 9190:
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2845, 4832, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2786, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2398, 4841, 0);
				c.dialogueAction = -1;
			}
			break;
			
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2796, 4818, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2464, 4834, 0);
				c.dialogueAction = -1;
			}
			break;
		// 3rd tele option

		case 9192:
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2713, 4836, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2162, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2207, 4836, 0);
				c.dialogueAction = -1;
			}
			break;
		// 4th tele option
		case 9193:
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.dialogueAction = -1;
			}
			break;
		// 5th tele option
		case 9194:
			if (c.dialogueAction == 10 || c.dialogueAction == 11) {
				c.dialogueId++;
				FreeDialogues.handledialogue(c, c.dialogueId, 0);
			} else if (c.dialogueAction == 12) {
				c.dialogueId = 17;
				FreeDialogues.handledialogue(c, c.dialogueId, 0);
			}
			break;
	
		case 58253:
			// c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;
			
		case 9167:
			switch (c.dialogueAction) {
			case 63:
				FreeDialogues.handledialogue(c,166, c.npcType);
			return;
			case 64:
				FreeDialogues.handledialogue(c,173, c.npcType);
			return;
			case 60:
				FreeDialogues.handledialogue(c, 277, c.npcType);
			return;
			case 61:
				FreeDialogues.handledialogue(c, 295, c.npcType);
			return;
			case 129:
				FreeDialogues.handledialogue(c, 231, c.npcType);
			return;
			case 58:
				FreeDialogues.handledialogue(c, 540, c.npcType);
			return;
			case 68:
				FreeDialogues.handledialogue(c, 39, c.npcType);
			return;
			case 251:
                c.getPA().openUpBank();
            return;
			case 502:
				FreeDialogues.handledialogue(c, 1025, c.npcType);
			return;
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;
			
			case 9168:
			switch (c.dialogueAction) {
			case 63:
				FreeDialogues.handledialogue(c,167, c.npcType);
			return;
			case 64:
				FreeDialogues.handledialogue(c,174, c.npcType);
			return;
			case 60:
				FreeDialogues.handledialogue(c, 279, c.npcType);
			return;
			case 61:
				FreeDialogues.handledialogue(c, 297, c.npcType);
			return;
			case 126:
				FreeDialogues.handledialogue(c, 203, c.npcType);
			return;
			case 58:
				FreeDialogues.handledialogue(c, 538, c.npcType);
			return;
			case 68:
				FreeDialogues.handledialogue(c, 40, c.npcType);
			return;
			case 251:
                c.getBankPin().bankPinSettings();
            return;
			case 502:
				FreeDialogues.handledialogue(c, 1026, c.npcType);
			return;
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;
			
			case 9169:
			switch (c.dialogueAction) {
			case 63:
				FreeDialogues.handledialogue(c,168, c.npcType);
			return;
			case 64:
				FreeDialogues.handledialogue(c,175, c.npcType);
			return;
			case 60:
				FreeDialogues.handledialogue(c, 278, c.npcType);
			return;
			case 61:
				FreeDialogues.handledialogue(c, 296, c.npcType);
			return;
			case 129:
				FreeDialogues.handledialogue(c, 232, c.npcType);
			return;
			case 126:
				FreeDialogues.handledialogue(c, 204, c.npcType);
			return;
			case 58:
				FreeDialogues.handledialogue(c, 539, c.npcType);
			return;
			case 68:
				FreeDialogues.handledialogue(c, 41, c.npcType);
			return;
			case 251:
				FreeDialogues.handledialogue(c, 1015, 494);
            return;
			case 502:
				FreeDialogues.handledialogue(c, 1029, c.npcType);
			return;
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

			case 9157:// barrows tele to tunnels
				if (c.dialogueAction == 1) {
					int r = 4;
					// int r = Misc.random(3);
					switch (r) {
					case 0:
						c.getPA().movePlayer(3534, 9677, 0);
						break;

					case 1:
						c.getPA().movePlayer(3534, 9712, 0);
						break;

					case 2:
						c.getPA().movePlayer(3568, 9712, 0);
						break;

					case 3:
						c.getPA().movePlayer(3568, 9677, 0);
						break;
					case 4:
						c.getPA().movePlayer(3551, 9694, 0);
						break;
					}	
				} else if (c.dialogueAction == 2) {
					c.getPA().movePlayer(2507, 4717, 0);
				} else if (c.dialogueAction == 7) {
					c.getPA().startTeleport(3088, 3933, 0, "modern");
					c.sendMessage("NOTE: You are now in the wilderness...");
				} else if (c.dialogueAction == 8) {
					c.getPA().resetBarrows();
					c.sendMessage("Your barrows have been reset.");
				} else if (c.dialogueAction == 29) {
					FreeDialogues.handledialogue(c, 480, c.npcType);
					return;
				} else if (c.dialogueAction == 30) {
					FreeDialogues.handledialogue(c, 488, c.npcType);
					return;
				} else if (c.dialogueAction == 34) {
					FreeDialogues.handledialogue(c, 360, c.npcType);
					return;
				} else if (c.dialogueAction == 50) {
					c.getPA().startTeleport(2898, 3562, 0, "modern");
					Teles.necklaces(c);
					return;
				} else if (c.dialogueAction == 55) {
					FreeDialogues.handledialogue(c, 91, c.npcType);
					return;
				} else if (c.dialogueAction == 56) {
					FreeDialogues.handledialogue(c, 96, c.npcType);
					return;
				} else if (c.dialogueAction == 57) {
					FreeDialogues.handledialogue(c, 57, c.npcType);
					return;
				} else if (c.dialogueAction == 65) {
					FreeDialogues.handledialogue(c, 179, c.npcType);
					return;
				} else if (c.dialogueAction == 66) {
					FreeDialogues.handledialogue(c, 182, c.npcType);
					return;
				} else if (c.dialogueAction == 67) {
					FreeDialogues.handledialogue(c, 36, c.npcType);
					return;
				} else if (c.dialogueAction == 68) {
					FreeDialogues.handledialogue(c, 587, c.npcType);
					return;
				} else if (c.dialogueAction == 70) {
					FreeDialogues.handledialogue(c, 1009, c.npcType);
					return;
				} else if (c.dialogueAction == 71) {
					FreeDialogues.handledialogue(c, 556, c.npcType);
					return;
				} else if (c.dialogueAction == 72) {
					FreeDialogues.handledialogue(c, 563, c.npcType);
					return;
				} else if (c.dialogueAction == 73) {
					FreeDialogues.handledialogue(c, 579, c.npcType);
					return;
				} else if (c.dialogueAction == 74) {
					FreeDialogues.handledialogue(c, 534, c.npcType);
					return;
				} else if (c.dialogueAction == 90) {
					FreeDialogues.handledialogue(c, 12, c.npcType);
					return;
				} else if (c.dialogueAction == 91) {
					FreeDialogues.handledialogue(c, 16, c.npcType);
					return;
				} else if (c.dialogueAction == 92) {
					FreeDialogues.handledialogue(c, 9, c.npcType);
					return;
				} else if (c.dialogueAction == 93) {
					FreeDialogues.handledialogue(c, 23, c.npcType);
					return;
				} else if (c.dialogueAction == 118) {
					FreeDialogues.handledialogue(c, 394, c.npcType);
					return;
				} else if (c.dialogueAction == 119) {
					FreeDialogues.handledialogue(c, 399, c.npcType);
					return;
				} else if (c.dialogueAction == 120) {
					FreeDialogues.handledialogue(c, 406, c.npcType);
					return;
				} else if (c.dialogueAction == 121) {
					FreeDialogues.handledialogue(c, 438, c.npcType);
					return;
				} else if (c.dialogueAction == 124) {
					FreeDialogues.handledialogue(c, 194, c.npcType);
					return;
				} else if (c.dialogueAction == 125) {
					FreeDialogues.handledialogue(c, 154, c.npcType);
					return;
				} else if (c.dialogueAction == 127) {
					FreeDialogues.handledialogue(c, 210, c.npcType);
					return;
				} else if (c.dialogueAction == 128) {
					FreeDialogues.handledialogue(c, 223, c.npcType);
					return;
				} else if (c.dialogueAction == 130) {
					FreeDialogues.handledialogue(c, 594, c.npcType);
					return;
				} else if (c.dialogueAction == 132) { 
					FreeDialogues.handledialogue(c, 1013, c.npcType);
				} else if (c.dialogueAction == 133) { 
					FreeDialogues.handledialogue(c, 1016, c.npcType);
				} else if (c.dialogueAction == 140) {
					FreeDialogues.handledialogue(c, 198, c.npcType);
					return;
				} else if (c.dialogueAction == 141) {
					FreeDialogues.handledialogue(c, 1020, c.npcType);
					return;
				} else if (c.dialogueAction == 508) { 
					FreeDialogues.handledialogue(c, 1030, c.npcType);
					return;
				} else if (c.dialogueAction == 700 && c.heightLevel == 1) {
					c.getPA().movePlayer(c.absX, c.absY, 2);
					c.startAnimation(828);
				} else if (c.dialogueAction == 700 && c.heightLevel == 2) {
					c.getPA().movePlayer(c.absX, c.absY, 3);
					c.startAnimation(828);
				} else if (c.dialogueAction == 855) { 
					c.getItems().removeAllItems();
				} else if (c.usingROD)  {
					c.getPA().startTeleport(3360, 3213, 0, "modern");
					Teles.necklaces(c);
					return;
				}
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
				break;

		case 9158:
			if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
			} else if (c.dialogueAction == 29) {
				FreeDialogues.handledialogue(c, 481, c.npcType);
				return;
			} else if (c.dialogueAction == 34) {
				FreeDialogues.handledialogue(c, 359, c.npcType);
				return;
			} else if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2552, 3558, 0, "modern");
				Teles.necklaces(c);
				return;
			} else if (c.dialogueAction == 55) {
				FreeDialogues.handledialogue(c, 92, c.npcType);
				return;
			} else if (c.dialogueAction == 56) {
				FreeDialogues.handledialogue(c, 95, c.npcType);
				return;
			} else if (c.dialogueAction == 74) {
				FreeDialogues.handledialogue(c, 535, c.npcType);
				return;
			} else if (c.dialogueAction == 57) {
				FreeDialogues.handledialogue(c, 58, c.npcType);
				return;
			} else if (c.dialogueAction == 62) {
				FreeDialogues.handledialogue(c, 309, c.npcType);
				return;
			} else if (c.dialogueAction == 67) {
				FreeDialogues.handledialogue(c, 35, c.npcType);
				return;
			} else if (c.dialogueAction == 68) {
				FreeDialogues.handledialogue(c, 586, c.npcType);
				return;
			} else if (c.dialogueAction == 71) {
				FreeDialogues.handledialogue(c, 582, c.npcType);
				return;
			} else if (c.dialogueAction == 72) {
				FreeDialogues.handledialogue(c, 562, c.npcType);
				return;
			} else if (c.dialogueAction == 73) {
				FreeDialogues.handledialogue(c, 580, c.npcType);
				return;
			} else if (c.dialogueAction == 90) {
				FreeDialogues.handledialogue(c, 13, c.npcType);
				return;
			} else if (c.dialogueAction == 91) {
				FreeDialogues.handledialogue(c, 17, c.npcType);
				return;
			} else if (c.dialogueAction == 118) {
				FreeDialogues.handledialogue(c, 392, c.npcType);
				return;
			} else if (c.dialogueAction == 119) {
				FreeDialogues.handledialogue(c, 404, c.npcType);
				return;
			} else if (c.dialogueAction == 120) {
				FreeDialogues.handledialogue(c, 404, c.npcType);
				return;
			} else if (c.dialogueAction == 121) {
				FreeDialogues.handledialogue(c, 437, c.npcType);
				return;
			} else if (c.dialogueAction == 124) {
				FreeDialogues.handledialogue(c, 192, c.npcType);
				return;
			} else if (c.dialogueAction == 125) {
				FreeDialogues.handledialogue(c, 163, c.npcType);
				return;
			} else if (c.dialogueAction == 130) {
				FreeDialogues.handledialogue(c, 593, c.npcType);
				return;
			} else if (c.dialogueAction == 131) {
				JewelryMaking.jewelryInterface(c);
				return;
			} else if (c.dialogueAction == 141) {
				FreeDialogues.handledialogue(c, 1021, c.npcType);
				return;
			} else if (c.usingROD) {
				c.getPA().startTeleport(2441, 3090, 0, "modern");
				Teles.necklaces(c);
				return;
			} else if (c.dialogueAction == 508) { 
				FreeDialogues.handledialogue(c, 1029, c.npcType);
				return;
			} else if (c.dialogueAction == 700) {
				c.getPA().movePlayer(c.absX, c.absY, 0);
				c.startAnimation(827);
			} else if (c.dialogueAction == 700 && c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX, c.absY, 2);
				c.startAnimation(828);
			} else {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}
			break;
			
		case 9178:
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3088, 3500, 0, "modern");
			if (c.dialogueAction == 31)
				FreeDialogues.handledialogue(c, 500, c.npcType);
			if (c.dialogueAction == 32)
				FreeDialogues.handledialogue(c, 340, c.npcType);
			if (c.dialogueAction == 33)
				FreeDialogues.handledialogue(c, 354, c.npcType);
			if (c.dialogueAction == 35)
				FreeDialogues.handledialogue(c, 378, c.npcType);
			if (c.dialogueAction == 51)
				c.getPA().startTeleport(3088, 3500, 0, "modern");
				Teles.necklaces(c);
			if (c.dialogueAction == 52)
				FreeDialogues.handledialogue(c, 52, c.npcType);
			if (c.dialogueAction == 69)
				FreeDialogues.handledialogue(c, 1005, c.npcType);
			if(c.dialogueAction == 700) {
				FreeDialogues.handledialogue(c, 28, c.npcType);
			}
			break;

		case 9179:
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3243, 3513, 0, "modern");
			if (c.dialogueAction == 31)
				FreeDialogues.handledialogue(c, 502, c.npcType);
			if (c.dialogueAction == 32)
				FreeDialogues.handledialogue(c, 341, c.npcType);
			if (c.dialogueAction == 33)
				FreeDialogues.handledialogue(c, 356, c.npcType);
			if (c.dialogueAction == 35)
				FreeDialogues.handledialogue(c, 376, c.npcType);
			if (c.dialogueAction == 51)
				c.getPA().startTeleport(3293, 3174, 0, "modern");
				Teles.necklaces(c);
			if (c.dialogueAction == 52)
				FreeDialogues.handledialogue(c, 64, c.npcType);
			if (c.dialogueAction == 69)
				FreeDialogues.handledialogue(c, 1002, c.npcType);
			if(c.dialogueAction == 700) {
				FreeDialogues.handledialogue(c, 29, c.npcType);
			}
			break;

		case 9180:
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3363, 3676, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 31)
				FreeDialogues.handledialogue(c, 501, c.npcType);
			if (c.dialogueAction == 32)
				FreeDialogues.handledialogue(c, 342, c.npcType);
			if (c.dialogueAction == 33)
				FreeDialogues.handledialogue(c, 355, c.npcType);
			if (c.dialogueAction == 35)
				FreeDialogues.handledialogue(c, 377, c.npcType);
			if (c.dialogueAction == 51)
				c.getPA().startTeleport(2911, 3152, 0, "modern");
				Teles.necklaces(c);
			if (c.dialogueAction == 52)
				FreeDialogues.handledialogue(c, 65, c.npcType);
			if(c.dialogueAction == 700)
				FreeDialogues.handledialogue(c, 30, c.npcType);
			if (c.dialogueAction == 69) {
				FreeDialogues.handledialogue(c, 1003, c.npcType);
			}
			break;

		case 9181:
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(2540, 4716, 0, "modern");
			if (c.dialogueAction == 51)
				c.getPA().startTeleport(3103, 3249, 0, "modern");
				Teles.necklaces(c);
			if (c.dialogueAction == 52)
				FreeDialogues.handledialogue(c, 63, c.npcType);
			if(c.dialogueAction == 700)
				FreeDialogues.handledialogue(c, 31, c.npcType);
			if (c.dialogueAction == 69)
				FreeDialogues.handledialogue(c, 1004, c.npcType);
			if(c.dialogueAction == 700) {
				FreeDialogues.handledialogue(c, 28, c.npcType);
			}
			break;

		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			c.specBarId = 7486;
			c.getCombat().handleGmaulPlayer();
			c.getItems().updateSpecialBar();
			break;

		case 29063:
			int equippedWeapon = c.playerEquipment[c.playerWeapon]; 
			if (c.getCombat()
					.checkSpecAmount(equippedWeapon)) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2])
						+ (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;

		case 26018:
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if (c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if (o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}

			if (c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) PlayerHandler.players[c.duelingWith];
			if (o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if (o1.duelStatus == 4 && c.duelStatus == 4) {
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				 Server.getTaskScheduler().schedule(new Task(1) {
                     @Override
                     protected void execute() {
				        if(System.currentTimeMillis() - c.duelDelay > 800 && c.duelCount > 0) {
				            if(c.duelCount != 1) {
				                c.forcedChat(""+(--c.duelCount));
				                c.duelDelay = System.currentTimeMillis();
				            } else {
				                c.damageTaken = new int[Config.MAX_PLAYERS];
				                c.forcedChat("FIGHT!");
				                c.duelCount = 0;
				            }
				        }
				        if (c.duelCount == 0) {
				            stop();
				        }
				    }
				    @Override
				    public void stop() {
				    }
				});
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		case 152 :
		    if (c.runEnergy < 1) {
		        c.isRunning = false;
		        c.getPA().setConfig(173, 0);
		        return;
		    }
		    c.isRunning = !c.isRunning;
		    c.getPA().setConfig(173, c.isRunning ? 0 : 1);
		    break;

		case 9154:
			c.logout();
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;
			
		case 9125: // Accurate
		case 6221: // range accurate
		case 22228: // punch (unarmed)
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 22229: // block (unarmed)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 22230: // kick (unarmed)
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		/** Prayers **/
		case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems
							.size()) {
						c.sendMessage(ot.playerName
								+ " only has "
								+ ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName
								+ " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...",
								3431);
						ot.getPA().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...",
								3431);
						ot.getPA().sendFrame126("Other player has accepted",
								3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			c.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
			if (ot1 == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}

			if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed
					&& !c.tradeConfirmed2) {
				c.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					c.acceptedTrade = true;
					ot1.acceptedTrade = true;
					c.getTradeAndDuel().giveItems();
					ot1.getTradeAndDuel().giveItems();
					break;
				}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
			}

			break;
		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 3189:
			if (c.splitChat == false) {
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
				c.splitChat = true;
			} else if (c.splitChat == true){
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
				c.splitChat = false;
			}
		break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;

		case 24017:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems()
					.sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
		
	}

}
