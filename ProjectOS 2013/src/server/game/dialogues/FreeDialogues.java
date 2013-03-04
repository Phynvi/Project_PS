package server.game.dialogues;

import server.game.content.questhandling.FreeRewards;
import server.game.content.questhandling.Stages;
import server.game.content.randomevents.FreakyForester;
import server.game.content.traveling.Sailing;
import server.game.content.tutorial.TutorialIsland;
import server.game.objects.Special;
import server.game.players.Client;

public class FreeDialogues {

	public static void handledialogue(Client c, int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {	
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
		break;
		
		case 1: //was 16
			c.getDH().sendOption2("I would like to reset my barrows brothers.",
				"I would like to fix all my barrows");
			c.dialogueAction = 8;
		break;
		
		case 2:
		    if (c.canLeaveArea) {
		        c.getDH().sendNpcChat2("Just step through the glowing portal when you're ready", "to leave, and I'll ensure you get a nice reward.", c.talkingNpc, "Freaky Forester");
		    } else if (FreakyForester.hasKilledPheasant(c) && c.getItems().playerHasItem(6178, 1)) {
		        c.getDH().sendNpcChat1("Thank you. I take that pheasant, you can leave now.", c.talkingNpc, "Freaky Forester");
		        c.canLeaveArea = true;
		        c.getItems().deleteItem(6178, c.getItems().getItemSlot(6178), c.getItems().getItemAmount(6178));
		    } else {
		        c.getDH().sendNpcChat2("Hello there mate. Can you please kill a " + FreakyForester.getPheasant(c) + " and", "bring it back to me and I shall let you leave.", c.talkingNpc, "Freaky Forester");
		    }
		    c.nextChat = 0;
		    break;//go to where they appear or whatever
		    
			case 3:
		  		c.getDH().sendNpcChat1("Come back mate! You can't leave yet!", c.talkingNpc, "Freaky Forester");
		  		c.nextChat = 0;
		    break;
		  
			case 8:
				c.getDH().sendOption2("I would like to collect some banana's for you", "Never mind");
				c.dialogueAction = 92;
			break;
			
			case 9:
				c.getDH().sendPlayerChat1("I would like to collect some banana's for you.");
				c.bananas = 1;
				c.lucas = true;
				c.nextChat = 10;
			break;
			
			case 10:
			if (c.bananas > 9) {
				c.getItems().addItem(995, 30);
				c.getDH().sendNpcChat1("Thank you.", c.talkingNpc, "Luthas");
				c.bananas = 0;
				c.lucas = false;
				c.nextChat = 0;
			} else {
				c.getPA().bananasCheck();
				c.nextChat = 0;
			}
			break;
			
				case 11:
					c.getDH().sendOption2("Here's 5 coins you tramp.", "Leave, me alone.");
					c.dialogueAction = 90;
				break;
				
				case 12:
					c.getItems().deleteItem2(995, 5);
					c.getDH().sendPlayerChat1("Now please leave me alone.");
					c.nextChat = 0;
				break;
				
				case 13:
					c.getDH().sendPlayerChat1("No! Leave me alone.");
					c.nextChat = 0;
				break;
				
				case 14: //lumby guide
					c.getDH().sendNpcChat1("Greetings, welcome to 2006Redone.",c.talkingNpc, "Lumbridge Guide");
					c.nextChat = 0;
				break;
					
				case 15:
					c.getDH().sendOption2("I would like to view your shop", "I would like to fix my barrows");
					c.dialogueAction = 91;
				break;
					
				case 16:
					c.getShops().openShop(8);
					c.nextChat = 0;
				break;
					
				case 17:
					c.getPA().fixAllBarrows();
					c.nextChat = 0;
				break;
				
				case 18:
					c.getDH().sendNpcChat1("Hi welcome to the partyroom.", c.talkingNpc, "Party Pete");
					c.nextChat = 605;
				break;
				
				case 19:
	    		if (c.getItems().playerHasItem(995, 2)) {
	    			c.getDH().sendNpcChat1("Hello would you like to buy a beer for 2 gp?", c.talkingNpc, "Bartender");
	    			c.nextChat = 20;
	    		} else {
	    			c.getDH().sendPlayerChat1("I don't have enough coins.");
	    			c.nextChat = 0;
	    		}
	    		break;
	    		
	    		case 20:
	    			c.getDH().sendPlayerChat1("Yes I would love a beer.");
	    			c.getItems().deleteItem2(995, 2);
	    			c.getItems().addItem(1917,1);
	    			c.nextChat = 0;
	    		break;
	    		
	    		case 21:
	    			c.getDH().sendNpcChat1("Hello, would you like me to bring you into to shilo village?", c.talkingNpc, "Mosol Rei");
	    			c.nextChat = 852;
	    		break;
	
	    		case 22:
	    			c.getDH().sendOption2("Yes", "No");
	    			c.dialogueAction = 93;
	    		break;
	
	    		case 23:
	    			c.getPA().movePlayer(2867, 2952, 0);
	    			c.nextChat = 0;
	    		break;
	    		
	    		case 24:
	    			if(c.getItems().playerHasItem(995, 200)) {
	    			c.getDH().sendNpcChat2("Hello Fair Traveler", "Can i interest you in a ride back to shantay for 200 coins?", c.talkingNpc, "Rug Merchant");
	    			c.nextChat = 25;
	    			} else {
	    			c.getDH().sendNpcChat1("You need 200 coins to travel my rug.", c.talkingNpc, "Rug Merchant");
	    			}
	    		break;
	
	    		case 25:
	    			c.getDH().sendPlayerChat1("Yes Please.");
	    			c.getPA().startTeleport(3308, 3108, 0, "modern");
	    			c.getItems().deleteItem2(995, 200); 
	    			c.nextChat = 0;
	    		break;
	
	    		case 26:
	    			if(c.getItems().playerHasItem(995, 200)) {
	    			c.getDH().sendNpcChat2("Hello Fair Traveler", "Can i interest you in a ride for 200 coins?", c.talkingNpc, "Rug Merchant");
	    			c.nextChat = 27;
	    			} else {
	    			c.getDH().sendNpcChat1("You need 200 coins to travel my rug.", c.talkingNpc, "Rug Merchant");
	    			}
	    		break;
	    			
	    		case 27:
	    			c.getDH().sendOption4("Pollnivneach (North)", "Bedabin Camp", "Uzer", "Shantay Pass");
	    			c.dialogueAction = 700;
	    		break;
	    			
	    		case 28:
	    			c.getDH().sendPlayerChat1("Pollnivneach please.");
	    			c.getPA().startTeleport(3350, 3004, 0, "modern");
	    			c.nextChat = 32;
	    		break;
	    		case 29:
	    			c.getDH().sendPlayerChat1("Bedabin Camp please.");
	    			c.getPA().startTeleport(3180, 3043, 0, "modern");
	    			c.nextChat = 32;
	    		break;
	    		case 30:
	    			c.getDH().sendPlayerChat1("Uzer please.");
	    			c.getPA().startTeleport(3469, 3111, 0, "modern");
	    			c.nextChat = 32;
	    		break;
	    		case 31:
	    			c.getDH().sendPlayerChat1("Shantay pass please.");
	    			c.getPA().startTeleport(3308, 3108, 0, "modern");
	    			c.nextChat = 32;
	    		break;
	    		case 32:
	    			c.getDH().sendNpcChat1("Enjoy!", c.talkingNpc, "Rug Merchant");
	    			c.getItems().deleteItem2(995, 200); 
	    			c.nextChat = 0;	
	    			break;
	    		case 33:
	    			c.getDH().sendNpcChat1("The trip to karamja will cost you 30 coins.", c.talkingNpc, "Sailor");
	    			c.nextChat = 34;
	    		break;
	    		
	    		case 34:
	    			c.getDH().sendOption2("Yes", "No");
	    			c.dialogueAction = 67;
	    		break;
	    		
	    		case 35:
	    			c.getDH().sendPlayerChat1("No thank you");
	    			c.nextChat = 0;
	    		break;
	    		case 36:
	    			c.getDH().sendPlayerChat1("Yes please");
	    			c.nextChat = 583;
	    		break;
	    			    		
	    		case 37:
	    			c.getDH().sendNpcChat2("Welcome to my food store!", "Would you like to buy anything?", c.talkingNpc, "Wydin");
	    			c.nextChat = 38;
	    		break;	
	    		case 38:
	    			c.getDH().sendOption3("Yes please.", "No thank you.", "Can I get a job here?");
	    			c.dialogueAction = 68;
	    		break;
	    		case 39:
	    			c.getDH().sendPlayerChat1("Yes Please.");
	    			c.getShops().openShop(34);
	    		break;
	    		case 40:
	    			c.getDH().sendPlayerChat1("No thank you.");
	    			c.nextChat = 0;
	    		break;
	    		case 41:
	    			c.getDH().sendPlayerChat1("Can I get a job here?");
	    			c.ptjob = 1;
	    			c.nextChat = 42;
	    		break;
	    		case 42:
	    			c.getDH().sendNpcChat3("Well you're keen, I'll give you that.", "Okay, I'll give you a go.", "Have you got your own white apron?", c.talkingNpc, "Wydin");
	    			c.nextChat = 43;
	    		break;	
	    		case 43:
	    			c.getDH().sendPlayerChat1("No, I haven't.");
	    			c.nextChat = 44;
	    		break;
	    		case 44:
	    			c.getDH().sendNpcChat2("Well, you can't work here unless you have a white apron.", "Health and safety regulations, you understand.", c.talkingNpc, "Wydin");
	    			c.nextChat = 45;
	    		break;
	    		case 45:
	    			c.getDH().sendPlayerChat1("Where can I get one of those?");
	    			c.nextChat = 46;
				break;
	    		case 46:
	    			c.getDH().sendNpcChat2("Well I get all mine at the clothing shop in Varrock.", "They sell them cheap there.", c.talkingNpc, "Wydin");
	    			c.nextChat = 47;
	    			c.ptjob = 1;
	    		break;
	    		case 47:
	    			c.getDH().sendNpcChat1("Have you got your white apron now?", c.talkingNpc, "Wydin");
	    			c.nextChat = 47;
	    		break;
	    		case 48:
	    		if (c.getItems().playerHasItem(1005, 1)) {
	    			c.getDH().sendPlayerChat1("Yes I have one here.");
	    			c.nextChat = 49;
	    			c.ptjob = 1;
	    		} else {
	    			c.getDH().sendPlayerChat1("No I still need to get one.");
	    		}
	    		break;
	    		case 49:
	    			c.getDH().sendNpcChat2("Wow, your well prepared! Your hired.", "Go through the back and tidy up for me please.", c.talkingNpc, "Wydin");
	    			c.nextChat = 0;
	    			c.ptjob = 2;
	    		break;		
		case 50:
			c.getDH().sendNpcChat1("What am I to do?", c.talkingNpc, "Cook");
			c.nextChat = 51;
			break;
		case 51:
			c.getDH().sendOption4("What's wrong?", "Can you cook me a cake?", "You don't look very happy.", "Nice hat.");
			c.dialogueAction = 52;
			break;
		case 52:
			c.getDH().sendPlayerChat1("What's wrong?");
			c.nextChat = 54;
			break;
		case 54:
			c.getDH().sendNpcChat3("Oh dear, oh dear, oh dear, I'm in a terrible terrible", "mess! It's the Duke's birthday today, and I should be", "making him a lovely big birthday cake!", c.talkingNpc, "Cook");
			c.nextChat = 55;
			break;
		case 55:
			c.getDH().sendNpcChat4("I've forgotten to buy the ingredients. I'll never get", "them in time now. He'll sack me! What will I do? I have", "four children and a goat to look after. Would you help", "me? Please?", c.talkingNpc, "Cook");
			c.nextChat = 56;
			break;
		case 56:
			c.getDH().sendOption2("I'm always happy to help a cook in distress.", "I can't right now, Maybe later.");
			c.dialogueAction = 57;
			break;
		case 57:
			c.getDH().sendPlayerChat1("Yes, I'll help you.");//9157
			c.nextChat = 60;
			break;
		case 58:
			c.getDH().sendPlayerChat1("I can't right now, Maybe later.");//9158
			c.nextChat = 59;
			break;
		case 59:
			c.getDH().sendNpcChat1("Oh please! Hurry then!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 60:
			c.getDH().sendNpcChat2("Oh thank you, thank you. I need milk, an egg, and", "flour. I'd be very grateful if you can get them for me.", c.talkingNpc, "Cook");
			c.cookAss = 1;
			c.nextChat = 61;	
			break;
		case 61:
			c.getDH().sendPlayerChat1("So where do I find these ingredients then?");
			c.nextChat = 62;
			break;
		case 62:
			c.getDH().sendNpcChat3("You can find flour in any of the shops here.", "You can find eggs by killing chickens.", "You can find milk by using a bucket on a cow", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 63:
			c.getDH().sendNpcChat1("I don't have time for your jibber-jabber!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 64:
			c.getDH().sendNpcChat1("Does it look like I have the time?", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;
		case 65:
			c.getDH().sendPlayerChat1("You don't look so happy.");
			c.nextChat = 54;
			break;
		case 66:
			c.getDH().sendNpcChat1("How are you getting on with finding the ingredients?", c.talkingNpc, "Cook");
			c.nextChat = 67;
			break;
		case 67:
			if(c.getItems().playerHasItem(1944, 1) && c.getItems().playerHasItem(1927, 1) && c.getItems().playerHasItem(1933, 1)) {
			c.getDH().sendPlayerChat1("Here's all the items!");
			c.nextChat = 68;
			} else {
			c.getDH().sendPlayerChat1("I don't have all the items yet.");
			c.nextChat = 59;
			}
			break;
		case 68:
			c.getItems().deleteItem(1944, 1);
			c.getItems().deleteItem(1927, 1);
			c.getItems().deleteItem(1933, 1);
			c.cookAss = 2;
			c.getDH().sendNpcChat2("You brought me everything I need! I'm saved!", "Thank you!", c.talkingNpc, "Cook");
			c.nextChat = 69;
			break;
		case 69:
			c.getDH().sendPlayerChat1("So do I get to go to the Duke's Party?");
			c.nextChat = 70;
			break;
		case 70:
			c.getDH().sendNpcChat2("I'm agraid not, only the big cheeses get to dine with the", "Duke.", c.talkingNpc, "Cook");
			c.nextChat = 72;
			break;
		case 72:
			c.getDH().sendPlayerChat2("Well, maybe one day I'll be important enough to sit on", "the Duke's table");
			c.nextChat = 74;
			break;
		case 74:
			c.getDH().sendNpcChat1("Maybe, but I won't be holding my breath.", c.talkingNpc, "Cook");
			c.nextChat = 75;
			break;
		case 75:
			FreeRewards.cookReward(c);
			break;
		case 76:
			c.getDH().sendNpcChat1("Thanks for helping me out friend!", c.talkingNpc, "Cook");
			c.nextChat = 0;
			break;	
		case 84:
			c.getDH().sendNpcChat1("How are you getting on finding all my supplies", c.talkingNpc, "Doric");
			c.nextChat = 85;
		break;
			case 85:
			if(c.getItems().playerHasItem(434, 6) && c.getItems().playerHasItem(436, 4) && c.getItems().playerHasItem(440, 2)) {
				c.getDH().sendPlayerChat1("Here's all the items!");
			c.nextChat = 86;
			} else {
				c.getDH().sendPlayerChat1("I haven't found all the items yet.");
			c.nextChat = 88;
			}
			break;
	case 86:
		c.getItems().deleteItem2(434, 6);
		c.getItems().deleteItem2(436, 4);
		c.getItems().deleteItem2(440, 2);
		c.doricQuest = 2;
		c.getDH().sendNpcChat2("You brought me everything i need.", "Thank You!", c.talkingNpc, "Doric");
		c.nextChat = 87;
		break;
	case 87:
		FreeRewards.doricFinish(c);
		c.nextChat = 0;
		break;
	case 88:
		c.getDH().sendNpcChat1("Hurry Then!", c.talkingNpc, "Doric");
		c.nextChat = 0;
		break;
	case 89:
		c.getDH().sendNpcChat1("Hello traveler, what brings you to my humble smithy?", c.talkingNpc, "Doric");
		c.nextChat = 90;
		break;
	case 90:
		c.getDH().sendOption2("Mind your own buisness, Shortstuff!", "I wanted to use your anivils.");
		c.dialogueAction = 55;
		break;
	case 91:
		c.getDH().sendNpcChat1("Mind your own buisness, Shortstuff!", c.talkingNpc, "Doric");
		c.nextChat = 0;
		break;
	case 92:
		c.getDH().sendNpcChat1("So you want to use my anivils?", c.talkingNpc, "Doric");
		c.nextChat = 98;
		break;
	case 98:
		c.getDH().sendPlayerChat1("Yes, I would like to use your anivil.");
		c.nextChat = 93;
		break;
	case 93:
		c.getDH().sendNpcChat4("My anvils get enough work with my own use.", "I make pickaxes, and it takes a lot of hard work.", "If you could get me some more materials,", "then i could let use them.", c.talkingNpc, "Doric");
		c.nextChat = 94;
		break;	
	case 94:
		c.getDH().sendOption2("Yes i will get you the materials.", "No, hitting rocks is boring.");
		c.dialogueAction = 56;
		break;
	case 95:
		c.getDH().sendPlayerChat1("No, hitting rocks is boring.");
		c.nextChat = 0;
		break;
	case 96:
		c.getDH().sendPlayerChat1("Yes i will get you the materials.");
		c.nextChat = 97;
		break;
	case 97:
		c.getDH().sendNpcChat4("Clay is what i use more than anything, to make casts.", "Could you get me 6 clay, 4 copper, and 2 iron, please?", "I could give a nice little reward", "Take my pickaxe with you just incase you need it.", c.talkingNpc, "Doric");
		c.getItems().addItem(1265,1);
		c.nextChat = 99;
		break;
	case 99:
		c.getDH().sendPlayerChat1("Certainly, I'll be right back!");
		c.doricQuest = 1;
		c.nextChat = 0;
		break;
	case 100:
		c.getDH().sendNpcChat1("Thanks for the help!", c.talkingNpc, "Doric");
		c.nextChat = 0;
		break;
    		case 145:
    			c.getDH().sendPlayerChat1("Give me a quest!");
    			c.nextChat = 146;
    			break;
    		case 146:
    			c.getDH().sendNpcChat1("Give me a quest what?", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 147;
    			break;
    		case 147:
    			c.getDH().sendPlayerChat1("Give me a quest please.");
    			c.nextChat = 148;
    			break;
    		case 148:
    			c.getDH().sendNpcChat2("Well seeing as you asked nicely... I could do with some", "help.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 149;
    			break;
    		case 149:
    			c.getDH().sendNpcChat2("The wizard Grayzag next door decided he didn't like", "me so he enlisted an army of hundreds of imps.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 150;
    			break;
    		case 150:
    			c.getDH().sendNpcChat3("These imps stole all sorts of my things. Most of these", "things I don't really care about, just eggs and balls of", "string and things.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 151;
    			break;
    		case 151:
    			c.getDH().sendNpcChat2("But they stole my four magical beads. There was a red", "one, a yellow one, a black one, and a white one.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 152;
    			break;
    		case 152:
    			c.getDH().sendNpcChat2("These imps have now spread out all over the kingdom.", "Could you get my beads back for me?", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 153;
    			break;
    		case 153:
    			c.getDH().sendOption2("I'll try.", "I've better things to do than chase imps.");
    			c.dialogueAction = 125;
    			break;
    		case 154:
    			c.getDH().sendPlayerChat1("I'll try.");
    			c.impsC++;
    			Stages.stage(c);
    			c.nextChat = 155;
    			break;
    		case 155:
    			c.getDH().sendNpcChat1("That's great, thank you.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 0;
    			break;
    			
    		case 156:
    			c.getDH().sendNpcChat1("So how are you doing finding my beads?", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 157;
    			break;
    		case 157:
    			c.getDH().sendPlayerChat1("I am still working on it.");
    			c.nextChat = 0;
    			break;
    			
    		case 158:
    			c.getDH().sendNpcChat1("So how are you doing finding my beads?", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 159;
    			break;
    		case 159:
    			c.getDH().sendPlayerChat1("I've got all four beads. It was hard work I can tell you.");
    			c.nextChat = 160;
    			break;	
    		case 160:
    			c.getDH().sendNpcChat3("Give them here and I'll check that really are MY", "beads, before I give you your reward. You'll take it, it's", "an amulet of accuracy.", c.talkingNpc, "Wizard Mizgog");
    			c.nextChat = 161;
    			break;
    		case 161:
    			c.getDH().sendStatement("You give four coloured beads to Wizard Mizgog.");
    			c.getItems().deleteItem(1470, 1);
    			c.getItems().deleteItem(1472, 1);
    			c.getItems().deleteItem(1474, 1);
    			c.getItems().deleteItem(1476, 1);
    			c.nextChat = 162;
    			break;
    		case 162:
    			c.impsC++;
    			FreeRewards.impFinish(c);
    			break;
    		case 163:
    			c.getDH().sendPlayerChat1("I've better things to do than chase imps.");
    			c.nextChat = 0;
    			break;
    			
    			case 164:
    			c.getDH().sendNpcChat3("What are you doing on my land? ", "You're not the one who keeps leaving all my gates open", "and letting out all my sheep are you?", c.talkingNpc, "Fred");
    			c.nextChat = 165;
    			break;
    			case 165:
    			c.getDH().sendOption3("I'm looking for a quest.", "I'm looking for something to kill.", "I'm lost.");
    			c.dialogueAction = 63;
    			break;
    			case 166:
    			c.getDH().sendPlayerChat1("I'm looking for a quest.");
    			c.nextChat = 170;
    			break;
    			case 167:
    			c.getDH().sendNpcChat1("Goblins are great for killing near the bridge in lumbridge.", c.talkingNpc, "Fred");
    			c.nextChat = 0;
    			break;
    			case 168:
    			c.getDH().sendNpcChat1("Your in lumbridge silly.", c.talkingNpc, "Fred");
    			c.nextChat = 0;
    			break;
    			case 169:
    			c.getDH().sendNpcChat2("You're after a quest, you say", "Actually I could do with a bit of help.", c.talkingNpc, "Fred");
    			c.nextChat = 170;
    			break;
    			case 170:
    			c.getDH().sendNpcChat3("My sheep are getting mighty wolly.", "I'd be much obliged if you could shear them.", "And while you're at it spin the wool for me too.", c.talkingNpc, "Fred");
    			c.nextChat = 171;
    			break;
    			case 171:
    			c.getDH().sendNpcChat4("Yes, that's it.", " Bring me 20 balls of wool.", "And I'm sure I could sort out some sort of payment.", " Of course, there's the small matter of The Thing.", c.talkingNpc, "Fred");
    			c.nextChat = 172;
    			break;
    			case 172:
    			c.getDH().sendOption3("Yes okay. I can do that.", "That doesn't sound a very exciting quest.", "What do you mean, The Thing?");
    			c.sheepShear = 1;
    			c.dialogueAction = 64;
    			break;
    			case 173:
    			c.getDH().sendPlayerChat1("Yes okay. I can do that.");
    			c.nextChat = 177;
    			break;
    			case 174:
    			c.getDH().sendPlayerChat1("Nevermind, that doesn't sound a very exciting quest.");
    			c.nextChat = 0;
    			break;
    			case 175:
    			c.getDH().sendPlayerChat1("What do you mean, The Thing?");
    			c.nextChat = 176;
    			break;
    			case 176:
    			c.getDH().sendNpcChat1("Never mind.", c.talkingNpc, "Fred");
    			c.nextChat = 0;
    			break;
    			case 177:
    			c.getDH().sendNpcChat2("Good! Now one more thing,", "do you actually know how to shear a sheep?.", c.talkingNpc, "Fred");
    			c.nextChat = 178;
    			break;
    			case 178:
    			c.getDH().sendOption2("Of course!", "Err. No, I don't know acctually.");
    			c.dialogueAction = 65;
    			break;
    			case 179:
    			c.getDH().sendPlayerChat1("Of course!");
    			c.nextChat = 180;
    			break;
    			case 180:
    			c.getDH().sendNpcChat1("And you know how to spin wool into balls?", c.talkingNpc, "Fred");
    			c.nextChat = 181;
    			break;
    			case 181:
    			c.getDH().sendOption2("I'm something of an expert acctually!", "I don't know how to spin wool, sorry..");
    			c.dialogueAction = 66;
    			break;
    			case 182:
    			c.getDH().sendPlayerChat1("I'm something of an expert acctually!");
    			c.nextChat = 183;
    			break;
    			case 183:
    			c.getDH().sendNpcChat1("Well you can stop grinning and get to work then?", c.talkingNpc, "Fred");
    			c.nextChat = 184;
    			break;
    			case 184:
    			c.getDH().sendNpcChat1("I'm not paying you by the hour!", c.talkingNpc, "Fred");
    			c.nextChat = 0;
    			break;
    			case 185:
    			c.getDH().sendNpcChat1("How are you doing getting my balls of wool?", c.talkingNpc, "Fred");
    			c.nextChat = 186;
    			break;
    			case 186:
    			if (c.getItems().playerHasItem(1759, 20)) {
    			c.getDH().sendPlayerChat1("I have some.");
    			c.getItems().deleteItem(1759, 20);
    			c.nextChat = 187;
    			} else {
    			c.getDH().sendStatement("I should get 20 balls wool first.");
    			c.nextChat = 0;
    			}
    			break;
    			case 187:
    			c.getDH().sendNpcChat1("Give em here then.", c.talkingNpc, "Fred");
    			c.nextChat = 188;
    			break;
    			case 188:
    			c.getDH().sendPlayerChat1("That's the last of them.");
    			c.nextChat = 189;
    			break;
    			case 189:
    			c.getDH().sendNpcChat1("I guess I'd better pay you then.", c.talkingNpc, "Fred");
    			FreeRewards.sheepFinish(c);
    			c.nextChat = 0;
    			break;
    					case 190:
    					c.getDH().sendNpcChat1("Greetings, welcome to my castle.", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 191;
    					break;
    					case 191:
    					c.getDH().sendOption2("Have you any quests for me?", "Where can I find money?");
    					c.dialogueAction = 124;
    					break;
    					case 192://9158
    					c.getDH().sendPlayerChat1("Where can I find money?");
    					c.nextChat = 193;
    					break;
    					case 193:
    					c.getDH().sendNpcChat1("I'm sorry, I'm not sure.", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 0;
    					break;
    					case 194://9157
    					c.getDH().sendNpcChat2("Well, it's not really a quest", "but I recently discovered this strange talisman.", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 195;
    					break;
    					case 195:
    					c.getDH().sendNpcChat2("It seems to be mystical and I have never seen anything like it before.", "Would you take it to the head wizard at", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 196;
    					break;
    					case 196:
    					c.getDH().sendNpcChat3("the Wizards Tower for me?", " It's just south-west of here and should not take you very long at all.", "I would be awfully grateful.", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 197;
    					break;
    					case 197:
    					c.getDH().sendOption2("Sure, no problem.", "Not right now.");
    					c.dialogueAction = 140;
    					break;
    					case 198://9157
    					c.getDH().sendPlayerChat1("Sure, no problem.");
    					c.nextChat = 199;
    					break;
    					case 199:
    					c.getDH().sendNpcChat2("Thank you very much, stranger.", "I'm sure the head wizard will reward you for such an interesting find.", c.talkingNpc, "Duke Horacio");
    					c.nextChat = 200;
    					break;
    					case 200:
    					c.getDH().itemMessage1("The duke hands you an @blu@air talisman@blu@.", 1438, 1);
    					c.getItems().addItem(1438, 1);
    					c.runeMist = 1;
    					c.nextChat = 0;
    					break;
    					case 201:
    					c.getDH().sendNpcChat2("Welcome adventurer, to the world renowed Wizards Tower", "How may I help you?", c.talkingNpc, "Sedridor");
    					c.nextChat = 202;
    					break;
    					case 202:
    					c.getDH().sendOption3("Nothing thanks, I'm just looking around.", "What are you doing down here?", "I'm looking for the head wizard.");
    					c.dialogueAction = 126;
    					break;
    					case 203: //9168
    					c.getDH().sendNpcChat1("That's none of your buisness.", c.talkingNpc, "Sedridor");
    					c.nextChat = 0;
    					break;
    					case 204: //9169
    					c.getDH().sendPlayerChat1("I'm looking for the head wizard.");
    					c.nextChat = 205;
    					break;
    					case 205:
    					c.getDH().sendNpcChat2("Oh, you are, are you?", "And just why would you be doing that?", c.talkingNpc, "Sedridor");
    					c.nextChat = 206;
    					break;
    					case 206:
    					c.getDH().sendNpcChat2("Oh, you are, are you?", "And just why would you be doing that?", c.talkingNpc, "Sedridor");
    					c.nextChat = 207;
    					break;
    					case 207:
    					c.getDH().sendNpcChat3("The Duke of Lumbridge sent me to find him. Most of these", " I have this weird talisman he found.", "He said the head wizard would be very interested in it.", c.talkingNpc, "Sedridor");
    					c.nextChat = 208;
    					break;
    					case 208:
    					c.getDH().sendNpcChat4("Did he now? HmmmMMMMMmmmm.", "Well that IS interested. Hand it over then adverturer", "let me see what all the hubbub about it is.", "Just some amulet I'll wager.", c.talkingNpc, "Sedridor");
    					c.nextChat = 209;
    					break;
    					case 209:
    					c.getDH().sendOption2("Ok, here you are.", "No, I'll only give it to the head wizard.");
    					c.dialogueAction = 127;
    					break;
    					case 210://9157
    					c.getDH().sendPlayerChat1("Ok, here you are.");
    					c.nextChat = 211;
    					break;
    					case 212:
    					c.getDH().itemMessage1("You hand the Talisman to the wizard.", 1438, 1);
    					c.getItems().deleteItem(1438, 1);
    					c.runeMist = 2;
    					c.nextChat = 213;
    					break;
    					case 213:
    					c.getDH().sendNpcChat1("Wow! This is... incredible!", c.talkingNpc, "Sedridor");
    					c.nextChat = 214;
    					break;
    					case 214:
    					c.getDH().sendNpcChat4("Th-this talisman you brought me...! ", "It is the last piece of the puzzle, I think! Finally!", "The legacy of our ancestors.", "It will return to us once more!", c.talkingNpc, "Sedridor");
    					c.nextChat = 215;
    					break;
    					case 215:
    					c.getDH().sendNpcChat3("I need time to study this, + c.playerName +.", "Can you please do me this task while I study this talisman you have brought me?", "In the mighty town of Varrock, which", c.talkingNpc, "Sedridor");
    					c.nextChat = 216;
    					break;
    					case 216:
    					c.getDH().sendNpcChat2("is located North East of here, there is a certain shop that sells magical runes.", "I have in this package all of the research I have done relating to the Rune Stones, and", c.talkingNpc, "Sedridor");
    					c.nextChat = 217;
    					break;
    					case 217:
    					c.getDH().sendNpcChat3("require sombody to take them to the shopkeeper so that he may share my research", "and offer me his insights.", "Do this thing for me, and bring back what he gives you,", c.talkingNpc, "Sedridor");
    					c.nextChat = 218;
    					break;
    					case 218:
    					c.getDH().sendNpcChat3("and if my suspicions are correct,", "I will let you into the knowledge of one of the greatest secrets this world has ever known!", "A secret so powerful that it destroyed the", c.talkingNpc, "Sedridor");
    					c.nextChat = 219;
    					break;
    					case 219:
    					c.getDH().sendNpcChat3("original Wizards tower all of those centuries ago!", "My research, combined with this mysterious talisman...", "I cannot believe the answer to the mysteries is so close now!", c.talkingNpc, "Sedridor");
    					c.nextChat = 220;
    					break;
    					case 220:
    					c.getDH().sendNpcChat2("Do this thing for me + c.playerName +.", " Be rewarded in a way you can never imagine.", c.talkingNpc, "Sedridor");
    					c.nextChat = 221;
    					break;
    					case 222:
    					c.getDH().sendOption2("Yes, certainly.", "No, I'm busy.");
    					c.dialogueAction = 128;
    					break;
    					case 223://9157
    					c.getDH().sendPlayerChat1("Yes, certainly.");
    					c.nextChat = 224;
    					break;
    					case 224:
    					c.getDH().sendNpcChat3("Take this package, and head directly North from here.", "through Draynor village, until you reach the Barbarian Village.", "Then head East from there until you reach Varrock.", c.talkingNpc, "Sedridor");
    					c.nextChat = 225;
    					break;
    					case 225:
    					c.getDH().sendNpcChat3("Once in Varrock, take this package to the owner of the rune shop.", "His name is Aubury.", "You may find it helpful to ask one of Varrock's citizens for directions,", c.talkingNpc, "Sedridor");
    					c.nextChat = 226;
    					break;
    					case 226:
    					c.getDH().sendNpcChat3("as Varrock can be a confusing place for the first time visitor.", "He will give you a special item - bring it back to me,", "and I shall show you the mystery of the runes...", c.talkingNpc, "Sedridor");
    					c.nextChat = 227;
    					break;
    					case 227:
    					c.getDH().itemMessage1("The head wizard gives you a package.", 290, 1);
    					c.getItems().addItem(290, 1);
    					c.nextChat = 228;
    					break;
    					case 228:
    					c.getDH().sendNpcChat1("Best of luck with your quest, + c.playerName +.", c.talkingNpc, "Sedridor");
    					c.nextChat = 229;
    					break;
    					case 229:
    					c.getDH().sendNpcChat1("Do you want to buy some runes?", c.talkingNpc, "Aubury");
    					c.nextChat = 230;
    					break;
    					case 230:
    					c.getDH().sendOption3("Yes Please!", "Oh, it's a rune shop. No thank you, then.", "I have been sent here with a package for you.");
    					c.dialogueAction = 129;
    					break;
    					case 231: //9167
    					c.getDH().sendPlayerChat1("Yes Please!");
    					c.getShops().openShop(52);
    					c.nextChat = 0;
    					break;
    					case 232: //9169
    					c.getDH().sendPlayerChat1("I have been sent here with a package for you. It's for the head wizard at the Wizards Tower.");
    					c.nextChat = 233;
    					break;
    					case 233:
    					c.getDH().sendNpcChat3("Really? But... surely he can't have..?", "Please, let me have it,", "it must be extremely important for him to have sent a stranger.", c.talkingNpc, "Aubury");
    					c.nextChat = 234;
    					break;
    					case 234:
    					c.getDH().itemMessage1("You hand Aubury the research package.", 290, 1);
    					c.getItems().deleteItem(290, 1);
    					c.runeMist = 3;
    					c.nextChat = 235;
    					break;
    					case 235:
    					c.getDH().sendNpcChat2("This... this is incredible. Please,", "give me a few moments to quickly look over this, and then talk to me again.", c.talkingNpc, "Aubury");
    					c.nextChat = 236;
    					break;
    					case 236:
    					c.getDH().itemMessage1("Aubury gives you the research notes.", 290, 1);
    					c.getItems().addItem(290, 1);
    					c.nextChat = 237;
    					break;
    					case 237:
    					c.getDH().sendNpcChat1("Thank you, now you should head back to Sedridor and tell him your discoveries.", c.talkingNpc, "Aubury");
    					c.nextChat = 0;
    					break;
    					case 238:
    					c.getDH().sendNpcChat2("Welcome, adventure to the world-renowed Wizards Tower.", "How may i help you?", c.talkingNpc, "Sedridor");
    					c.nextChat = 239;
    					break;
    					case 239:
    					c.getDH().sendNpcChat2("Ah, + c.playerName +. How goes your quest?", "Have you delivered the research notes to my friend yet?", c.talkingNpc, "Sedridor");
    					c.nextChat = 240;
    					break;
    					case 240:
    					c.getDH().sendPlayerChat1("Yes, I have. He gave me some research notes to pass on to you.");
    					c.nextChat = 241;
    					break;
    					case 241:
    					c.getDH().sendNpcChat1("May I have them?", c.talkingNpc, "Sedridor");
    					c.nextChat = 242;
    					break;
    					case 242:
    					c.getDH().sendPlayerChat1("Sure. I have them here.");
    					c.nextChat = 243;
    					break;
    					case 243:
    					c.getDH().sendNpcChat2("You have been nothing but helpful, adventured.", "In return, I can let you in on the secret of our research.", c.talkingNpc, "Sedridor");
    					c.nextChat = 244;
    					break;
    					case 245:
    					c.getDH().sendNpcChat2("Many centuries ago, the wizards of the Wizards Tower learnt the secret of creating runes,", "which allowed them to cast magic very easily.", c.talkingNpc, "Sedridor");
    					c.nextChat = 246;
    					break;
    					case 247:
    					c.getDH().sendNpcChat3("But, when this tower was burnt down, the sercret of creating runes was lost with it...", "or so I thought.", "Some months ago, while searching these ruins for information, ", c.talkingNpc, "Sedridor");
    					c.nextChat = 248;
    					break;
    					case 248:
    					c.getDH().sendNpcChat2("I came upon a scroll that made refrence to a magical rock", "deep in the ice fields of the north.", c.talkingNpc, "Sedridor");
    					c.nextChat = 249;
    					break;
    					case 249:
    					c.getDH().sendNpcChat3("This rock was called the rune essence by those magicians who studied it's powers.", "Apparently, by simply breaking a chunk for it,", "a rune could be fashioned and taken to certain", c.talkingNpc, "Sedridor");
    					c.nextChat = 250;
    					break;
    					case 250:
    					c.getDH().sendNpcChat3("magical altars that were scattered across the land.", "Now, this is an intersting little peice of history,", "not much use to us since we do not have access to this rune essence", c.talkingNpc, "Sedridor");
    					c.nextChat = 251;
    					break;
    					case 251:
    					c.getDH().sendNpcChat2("teleportations spell that he had never come across before, When cast,", "it took him to a strange rock, yet it felt strangly familiar.", c.talkingNpc, "Sedridor");
    					c.nextChat = 252;
    					break;
    					case 252:
    					c.getDH().sendNpcChat3("As I'm sure you have guessed, he had discovered a spell to the mythical rune essence.", "As soon as he told me of this,", "I saw the importance of the find.", c.talkingNpc, "Sedridor");
    					c.nextChat = 253;
    					break;
    					case 253:
    					c.getDH().sendNpcChat2("For, if we could find the altars spoken of in the ancient texts", "we would once more be able to create runes as our ancestors had done!", c.talkingNpc, "Sedridor");
    					c.nextChat = 254;
    					break;
    					case 254:
    					c.getDH().sendPlayerChat1("I'm still not sure how I fit into this little story of yours.");
    					c.nextChat = 255;
    					break;
    					case 255:
    					c.getDH().sendNpcChat3("You haven't guessed?", "This talisman you brough me is the key to the elemental altar of air!", "When you hold it, it directs you to", c.talkingNpc, "Sedridor");
    					c.nextChat = 256;
    					break;
    					case 256:
    					c.getDH().sendNpcChat3("the entrance of the long-forgotten Air Altar.", "By bringing peices of the rune essence the Air Altar,", "you will be able to fashion your own air runes", c.talkingNpc, "Sedridor");
    					c.nextChat = 257;
    					break;
    					case 257:
    					c.getDH().sendNpcChat3("That's not all!", "By finding other talismans similar to his one,", "you will eventually be able to craft every rune that is available in this world, jus", c.talkingNpc, "Sedridor");
    					c.nextChat = 258;
    					break;
    					case 258:
    					c.getDH().sendNpcChat3("as our ancestors did.", "I cannot stress enough what find this is!", "Now, due to the risks invovled in letting this mighty power fall into the wrong hands.", c.talkingNpc, "Sedridor");
    					c.nextChat = 259;
    					break;
    					case 259:
    					c.getDH().sendNpcChat3("I will keep the teleport spell to the rune essence a closely guarded secret.", "This means that, if any evil power should discover the talismans required to enter the emental temples,", "we will be able to prevent their", c.talkingNpc, "Sedridor");
    					c.nextChat = 260;
    					break;
    					case 260:
    					c.getDH().sendNpcChat3("access to the rune essence.", "I know not where the altars are located, not do I know where the talismans have been scattered,", "but now return your air talisman.", c.talkingNpc, "Sedridor");
    					c.nextChat = 261;
    					break;
    					case 261:
    					c.getDH().sendNpcChat1("Find the Air Altar and you will be able to craft you blank runes into air runes at will.", c.talkingNpc, "Sedridor");
    					c.nextChat = 262;
    					break;
    					case 262:
    					c.getDH().sendNpcChat2("Any time you wish to visit the rune essence,", "speak to me or Aubury and we will open a portal to that mystical place.", c.talkingNpc, "Sedridor");
    					c.nextChat = 263;
    					break;
    					case 264:
    					c.getDH().sendPlayerChat1("So, only you and Aubury know the teleport spell to the rune essence?");
    					c.nextChat = 265;
    					break;
    					case 266:
    					c.getDH().sendNpcChat2("No, there are others. When you speak to them,", "they will know you and grant you access to that place when asked.", c.talkingNpc, "Sedridor");
    					c.nextChat = 267;
    					break;
    					case 267:
    					c.getDH().sendNpcChat2("Use the air talisman to locate the Air Altar and use any further talismans you find to locate the other altars.", "Now, my research notes please?", c.talkingNpc, "Sedridor");
    					c.nextChat = 268;
    					break;
    					case 268:
    					c.getDH().itemMessage1("You give the research notes to Sedrdior. He gives you an air talisman.", 290, 1);
    					c.getItems().deleteItem(290, 1);
    					c.runeMist = 4;
    					FreeRewards.runeFinish(c);
    					c.nextChat = 0;
    					break;
    							case 338:
                    			c.getDH().sendNpcChat1("Welcome to the church of holy Saradomin..", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 339;
                    			break;
                    			case 339:
                    			c.getDH().sendOption4("Who's Saradomin?", "Nice place you've got here", "I'm looking for a quest", "Never Mind");
                    			c.dialogueAction = 32;
                    			break;
                    			case 340://9178
                    			c.getDH().sendNpcChat1("None of your buisness.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 0;
                    			break;
                    			case 341://9179
                    			c.getDH().sendPlayerChat1("Nice place you've got here.");
                    			c.nextChat = 0;
                    			break;
                    			case 342://9180
                    			c.getDH().sendPlayerChat1("I'm looking for a quest.");
                    			c.nextChat = 343;
                    			break;
                    			case 343:
                    			c.getDH().sendNpcChat1("That's lucky, I need someone to do a quest for me.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 344;
                    			break;               			
                    			case 344:
                    			c.getDH().sendPlayerChat1("Okay, let me help then.");
                    			c.nextChat = 345;
                   				break;	
                    			case 345:
                    			c.getDH().sendNpcChat3("Thank you. The problem is there's,", "a ghost in the graveyard crypt just south of this church.", "I would like you to get rid of it.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 346;
                    			break;
                    			case 346:
                    			c.getDH().sendNpcChat2("You'll need the help of my friend, Father Urhney,", "who is a bit of a ghost expert.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 347;
                    			break;
                    			case 347:
                    			c.getDH().sendNpcChat2("He's currently living in a little shack to the south of,", "the Lumbridge Swamp near the coast.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 348;
                    			break;
                    			case 348:
                    			c.getDH().sendNpcChat2("My name is Father Aereck, by the way.", "Pleased to meet you.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 349;
                    			break;
                    			case 349:
                    			c.getDH().sendPlayerChat1("Likewise.");
                    			c.nextChat = 350;
                    			break;
                    			case 350:
                    			c.getDH().sendNpcChat3("Take care traveling through the swamps.", "To get there just follow the path south,", "through the graveyard.", c.talkingNpc, "Father Aereck");
                    			c.nextChat = 351;
                    			c.restGhost = 1;
                    			break;               			
                    			case 351:
                    			c.getDH().sendPlayerChat1("I will thanks.");
                    			c.nextChat = 0;
                    			break;              			
                    			case 352:
                    			c.getDH().sendNpcChat1("Go away! I'm meditating.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 353;
                   				break;	
                    			case 353:
                    			c.getDH().sendOption4("Well, that's friendly", "Father Aereck sent me to talk to you", "I've come to repossess your house", "Never Mind");
                    			c.dialogueAction = 33;
                    			break;
                    			case 354://9178
                    			c.getDH().sendPlayerChat1("Well, that's friendly.");
                    			c.nextChat = 0;
                    			break;
                    			case 355: //9180
                    			c.getDH().sendPlayerChat1("I've come to repossess your house.");
                    			c.nextChat = 0;
                    			break;
                    			case 356: //9179
                    			c.getDH().sendPlayerChat1("Father Aereck sent me to talk to you.");
                    			c.nextChat = 357;
                    			break;
                    			case 357:
                    			c.getDH().sendNpcChat2("I suppose I better talk to you then.", "What has he got himself into this time?", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 358;
                    			break;
                    			case 358:
                    			c.getDH().sendOption2("A ghost is haunting his graveyard", "You mean he gets into lots of problems?");
                    			c.dialogueAction = 34;
                    			break;
                    			case 359: //9158
                    			c.getDH().sendPlayerChat1("You mean he gets into lots of problems?");
                    			c.nextChat = 360;
                    			break;
                    			case 360:
                    			c.getDH().sendNpcChat1("Yes...", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 0;
                    			break;
                    			case 361:
                    			c.getDH().sendPlayerChat1("A ghost is haunting his graveyard");
                    			c.nextChat = 362;
                    			break;
                    			case 362:
                    			c.getDH().sendNpcChat1("Oh, the silly fool.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 363;
                    			break;
                    			case 363:
                    			c.getDH().sendNpcChat2("I leave town for five months,", "and he's already having problems.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 364;
                    			break;
                    			case 364:
                    			c.getDH().sendNpcChat1("*sigh*", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 365;
                    			break;
                    			case 365:
                    			c.getDH().sendNpcChat3("Well I can't go back and exorcise it", "I vowed not to leave this place until,", "I've spent a full two years praying and meditating.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 366;
                    			break;
                    			case 366:
                    			c.getDH().sendNpcChat1("I'll tell you what I can do though take this amulet.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 367;
                    			c.getItems().addItem(552, 1);
                    			c.restGhost = 2;
                    			break;
                    			case 367:
                    			c.getDH().sendNpcChat1("It's a ghost speak amulet.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 368;
                    			break;
                    			case 368:
                    			c.getDH().sendNpcChat3("It's called that because, when you wear it, you can,", "speak to ghosts. Many ghosts are doomed to remain in this,", "world because they have some important task left uncompleted.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 369;
                    			break;
                    			case 369:
                    			c.getDH().sendNpcChat3("If you know what this task is, you can get rid of the ghost.", "I'm not making any guarentees, mind you,", "but it's the best I can do right now.", c.talkingNpc, "Father Urhney");
                    			c.nextChat = 370;
                    			break;
                    			case 370:
                    			c.getDH().sendPlayerChat1("Thank you. I'll give it a try.");
                    			c.nextChat = 0;
                    			break;
                    			case 371:
                    			c.getDH().sendPlayerChat1("Hello ghost how are you?");
                    			c.nextChat = 372;
                    			break;
                    			case 372:
                    			c.getDH().sendNpcChat1("Not very good, actually.", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 373;
                    			break;
                    			case 373:
                    			c.getDH().sendPlayerChat1("What's the problem?");
                    			c.nextChat = 374;
                    			break;
                    			case 374:
                    			c.getDH().sendNpcChat1("Did you just understand what I said?", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 375;
                    			break;
                    			case 375:
                    			c.getDH().sendOption4("Yep. Now, tell me what the problem is.", "No, you sound like you're speaking nonsense to me.", "Wow, this amulet works!", "Never mind.");
                    			c.dialogueAction = 35;
                    			break;
                    			case 376: //9179
                    			c.getDH().sendPlayerChat1("No, you sound like you're speaking nonsense to me.");
                    			c.nextChat = 0;
                    			break;
                    			case 377: //9180
                    			c.getDH().sendPlayerChat1("Wow, this amulet works!");
                    			c.nextChat = 0;
                    			break;
                    			case 378: //9178
                    			c.getDH().sendPlayerChat1("Yep. Now, tell me what the problem is.");
                    			c.nextChat = 379;
                    			break;
                    			case 379:
                    			c.getDH().sendNpcChat2("Wow! This is incredible!", "I didn't expect anyone to ever understand me!", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 380;
                    			break;
                    			case 380:
                    			c.getDH().sendPlayerChat1("Okay, okay, I can understand you.");
                    			c.nextChat = 381;
                    			break;
                    			case 381:
                    			c.getDH().sendPlayerChat1("But have you any idea why you're doomed to be a ghost?");
                    			c.nextChat = 382;
                    			break;
                    			case 382:
                    			c.getDH().sendNpcChat1("Well, to be honest, I'm not sure.", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 383;
                    			break;
                    			case 383:
                    			c.getDH().sendPlayerChat2("I've been told that a certain task needs to be completed", "before you can rest in peace.");
                    			c.nextChat = 384;
                    			break;
                    			case 384:
                    			c.getDH().sendNpcChat1("I should think it's because I've lost my head.", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 385;
    	               			break;
                    			case 385:
                    			c.getDH().sendPlayerChat1("What? I can see your head perfectly fine. Well, see through it at least.");
                    			c.nextChat = 386;
                    			break;
                    			case 386:
                    			c.getDH().sendNpcChat4("No, no, I mean from my REAL body.", "If you look in my coffin you'll see my corpse is without it's,", "skull. Last thing I remember was being attacked by a warlock,", "while I was mining. It was at the mine just south of this", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 387;
                    			break;
                    			case 387:
                    			c.getDH().sendNpcChat1("graveyard.", c.talkingNpc, "Restless Ghost");
                    			c.nextChat = 388;
                    			c.restGhost = 3;
                    			break;
                    			case 388:
                    			c.getDH().sendPlayerChat1("Okay. I'll try to get your skull back for you so you can rest in peace.");
                    			c.nextChat = 0;
                    			break;
                    	  		case 389:
                                    c.getDH().sendNpcChat1("Why me?...", c.talkingNpc, "Romeo");
                                    c.nextChat = 390;
                                    break;
                                case 390:
                                     c.getDH().sendNpcChat2("Why isn't she returning any of them...",
                                            "Is it my hair...", c.talkingNpc, "Romeo");
                                    c.nextChat = 391;
                                    break;
                                case 391:
                                     c.getDH().sendOption2("What's wrong?", "Yes it's your hair");
                                    c.dialogueAction = 118;
                                    break;
                                case 392: //9158
                                     c.getDH().sendPlayerChat1("Haha yes it's your hair, get a haircut loser!");
                                    c.nextChat = 393;
                                    break;
                                case 393:
                                    c.getDH().sendNpcChat1("Tis' a sad world...", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 394: //9157
                                     c.getDH().sendPlayerChat1("What's wrong?");
                                    c.nextChat = 396;
                                    break;
                                case 396:
                                     c.getDH().sendNpcChat1("My Juliet..my poor poor Juliet", c.talkingNpc, "Romeo");
                                    c.nextChat = 397;
                                    break;
                                case 397:
                                     c.getDH().sendNpcChat3("I've been trying to contact her all day",
                                            "but the problem is...she won't return any",
                                            "of my letters...", c.talkingNpc, "Romeo");
                                    c.nextChat = 398;
                                    break;
                                case 398:
                                     c.getDH().sendOption2("Why don't you just meet in person?", "I might have to go now...");
                                    c.dialogueAction = 119;
                                    break;
                                case 399: //9157
                                    c.getDH().sendNpcChat1("Well you see...the problem is..", c.talkingNpc, "Romeo");
                                    c.nextChat = 401;
                                    break;
                                case 401:
                                    c.getDH().sendNpcChat1("Her mother doesn't know we've been dating.", c.talkingNpc, "Romeo");
                                    c.nextChat = 402;
                                    break;
                                case 402:
                                    c.getDH().sendNpcChat1("Can you please speak with Juliet and see what's going on?", c.talkingNpc, "Romeo");
                                    c.nextChat = 403;
                                    break;
                                case 403:
                                    c.getDH().sendOption2("Yes I'll do so now", "Is that my fish calling me?");
                                    c.dialogueAction = 120;
                                    break;
                                case 404://9158
                                    c.getDH().sendPlayerChat1("I might have to go now...");
                                    c.nextChat = 0;
                                    break;
                                case 405: 
                                	c.getDH().sendPlayerChat1("Why not just meet her in person?");
                                    c.nextChat = 406;
                                    break;
                                case 406:
                                    c.getDH().sendPlayerChat1("Yeah anything to help a lover in need.");
                                    c.nextChat = 407;
                                    break;
                                case 407:
                                    c.romeojuliet++;
                                    c.getDH().sendNpcChat2("Great, Juliet is just in the house west of here",
                                            "You will most likely find her upstairs.", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 408:
                                    c.getDH().sendNpcChat1("Please speak to Juliet for me", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 409:
                                    c.getDH().sendNpcChat1("How I long him...", c.talkingNpc, "Juliet");
                                    c.nextChat = 0;
                                    break;
                                case 410:
                                    c.getDH().sendNpcChat1("Please you have to go.", c.talkingNpc, "Juliet");
                                    c.nextChat = 411;
                                    break;
                                case 411:
                                    c.getDH().sendPlayerChat2("Wait, what's happening? Romeo has",
                                            "been looking all over for you.");
                                    c.nextChat = 412;
                                    break;
                                case 412:
                                    c.getDH().sendNpcChat1("I can't explain much. Please just go.", c.talkingNpc, "Juliet");
                                    c.nextChat = 413;
                                    break;
                                case 413:
                                    c.getDH().sendNpcChat1("Take this...and go...", c.talkingNpc, "Juliet");
                                    c.getItems().addItem(755, 1);
                                    c.romeojuliet++;
                                    c.nextChat = 0;
                                    break;
                                case 414:
                                    c.getDH().sendNpcChat1("Just go...you shouldn't be here.", c.talkingNpc, "Juliet");
                                    c.nextChat = 0;
                                    break;
                                case 415:
                                    c.getDH().sendNpcChat1("Hey did you talk to her yet?..How I long for Juliet.", c.talkingNpc, "Romeo");
                                    c.nextChat = 416;
                                    break;
                                case 416:
                                    c.getDH().sendPlayerChat1("Yes she gave me this let---");
                                    c.nextChat = 417;
                                    break;
                                case 417:
                                    c.getDH().sendNpcChat1("Pass it here, pass it!", c.talkingNpc, "Romeo");
                                    c.getItems().deleteItem(755, 1);
                                    c.nextChat = 418;
                                    break;
                                case 418:
                                    c.getDH().sendNpcChat3("Dear Romeo...sadly we can not see each other anymore",
                                            "mother has been complaining on how you aren't the right",
                                            "person.", c.talkingNpc, "Romeo");
                                    c.nextChat = 419;
                                    break;
                                case 419:
                                    c.getDH().sendNpcChat3("We come from two different classes..",
                                            "I'm just some lonely Varrock girl, and",
                                            "your a fine prince that travels around the world..", c.talkingNpc, "Romeo");
                                    c.nextChat = 420;
                                    break;
                                case 420:
                                    c.getDH().sendNpcChat1("This is my goodbyes...Juliet...", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    c.romeojuliet++;
                                    c.romeojuliet++;
                                    break;
                                case 421:
                                    c.getDH().sendNpcChat1("Well have you spoken to her?", c.talkingNpc, "Romeo");
                                    c.nextChat = 422;
                                    break;
                                case 422:
                                    c.getDH().sendPlayerChat2("She gave me a letter to give you..",
                                            "Which I don't have on me");
                                    c.nextChat = 423;
                                    break;
                                case 423:
                                    c.getDH().sendNpcChat2("Please bring it as soon as possible...",
                                            "How I miss my Juliet", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 424:
                                    c.getDH().sendNpcChat1("She just...", c.talkingNpc, "Romeo");
                                    c.nextChat = 425;
                                    break;
                                case 425:
                                    c.getDH().sendNpcChat1("What have I done wrong...", c.talkingNpc, "Romeo");
                                    c.nextChat = 426;
                                    break;
                                case 426:
                                    c.getDH().sendNpcChat1("My Juliet...", c.talkingNpc, "Romeo");
                                    c.nextChat = 427;
                                    break;
                                case 427:
                                    c.getDH().sendPlayerChat2("Are you just going to give up??",
                                            "What about love?");
                                    c.nextChat = 428;
                                    break;
                                case 428:
                                    c.getDH().sendNpcChat1("No...", c.talkingNpc, "Romeo");
                                    c.nextChat = 429;
                                    break;
                                case 429:
                                    c.getDH().sendNpcChat2("No, your right. Please",
                                            "speak to the witch just south west of here.", c.talkingNpc, "Romeo");
                                    c.nextChat = 430;
                                    break;
                                case 430:
                                    c.getDH().sendNpcChat1("She'll know what to do.", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    c.romeojuliet++;
                                    break;
                                case 431:
                                    c.getDH().sendNpcChat1("Speak to Winelda. She's south west.", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 432:
                                    c.getDH().sendNpcChat1("So I see that prince sent you here.", c.talkingNpc, "Winelda");
                                    c.nextChat = 433;
                                    break;
                                case 433:
                                    c.getDH().sendPlayerChat1("Wait how did you know?");
                                    c.nextChat = 434;
                                    break;
                                case 434:
                                    c.getDH().sendNpcChat2("I'm a witch..I've studied the arts",
                                            "of magic for years.", c.talkingNpc, "Winelda");
                                    c.nextChat = 435;
                                    break;
                                case 435:
                                    c.getDH().sendNpcChat2("I will help you. This one time.",
                                            "But next time I won't be so kind.", c.talkingNpc, "Winelda");
                                    c.nextChat = 436;
                                    break;
                                case 436:
                                    c.getDH().sendOption2("So what do I need to do?", "I don't think I'm up for this anymore...");
                                    c.dialogueAction = 121;
                                    break;
                                case 437://9158
                                    c.getDH().sendPlayerChat1("This is just getting more twisted...I have to go...");
                                    c.nextChat = 0;
                                    break;
                                case 438://9157
                                    c.getDH().sendPlayerChat1("So what do you need me to do?");
                                    c.nextChat = 439;
                                    break;
                                case 439:
                                    c.getDH().sendNpcChat1("You need, 1 rats tail, 1 bone, and a vial of water", c.talkingNpc, "Winelda");
                                    c.nextChat = 440;
                                    break;
                                case 440:
                                    c.getDH().sendNpcChat2("Bring those items here and I'll make you a potion",
                                            "that makes anyone tell the truth.", c.talkingNpc, "Winelda");
                                    c.nextChat = 0;
                                    c.romeojuliet++;
                                    break;
                                case 441:
                                    c.getDH().sendNpcChat1("You need, 1 rats tail, 1 bone, and a vial of water", c.talkingNpc, "Winelda");
                                    c.nextChat = 442;
                                    break;
                                case 442:
                                    c.getDH().sendNpcChat1("Speak to me when you have all 3 items.", c.talkingNpc, "Winelda");
                                    c.nextChat = 0;
                                    break;
                                case 443:
                                    c.getDH().sendNpcChat1("What did the witch say?", c.talkingNpc, "Romeo");
                                    c.nextChat = 444;
                                    break;
                                case 444:
                                    c.getDH().sendPlayerChat2("She wants me to bring her 3 items", "Then she'll speak to me");
                                    c.nextChat = 445;
                                    break;
                                case 445:
                                    c.getDH().sendNpcChat1("Which items if you don't mind me asking?", c.talkingNpc, "Romeo");
                                    c.nextChat = 446;
                                    break;
                                case 446:
                                    c.getDH().sendPlayerChat1("Oh just a rat's");
                                    c.nextChat = 447;
                                    break;
                                case 447:
                                    c.getDH().sendNpcChat1("Nevermind.", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
                                case 448:
                                    c.getDH().sendNpcChat1("I'll take that", c.talkingNpc, "Winelda");
                                    c.getItems().deleteItem(300, 1);
                                    c.getItems().deleteItem(227, 1);
                                    c.getItems().deleteItem(526, 1);
                                    c.nextChat = 449;
                                    break;
                                case 449:
                                    c.getDH().sendNpcChat1("Azari-Ahmi-Grantai!!", c.talkingNpc, "Winelda");
                                    c.nextChat = 450;
                                    break;
                                case 450:
                                    c.getDH().sendNpcChat1("Here take this", c.talkingNpc, "Winelda");
                                    c.romeojuliet++;
                                    c.getItems().addItem(4836, 1);
                                    c.nextChat = 451;
                                    break;
                                case 451:
                                    c.getDH().sendNpcChat1("Tell Juliet to drink this", c.talkingNpc, "Winelda");
                                    c.nextChat = 452;
                                    break;
                                case 453:
                                    c.getDH().sendPlayerChat1("Wait but will Juliet actually drink it?");
                                    c.nextChat = 454;
                                    break;
                                case 454:
                                    c.getDH().sendNpcChat3("I don't know?!",
                                            "What do I look like some kind of",
                                            "fortune teller?", c.talkingNpc, "Winelda");
                                    c.nextChat = 455;
                                    break;
                                case 455:
                                    c.getDH().sendNpcChat1("Tell her it's soup or something.", c.talkingNpc, "Winelda");
                                    c.nextChat = 456;
                                    break;
                                case 456:
                                    c.getDH().sendPlayerChat1("Wow she's polite...");
                                    c.nextChat = 0;
                                    break;
                                case 457:
                                    c.getDH().sendNpcChat1("I told you to leave....", c.talkingNpc, "Juliet");
                                    c.nextChat = 458;
                                    break;
                                case 458:
                                    c.getDH().sendPlayerChat1("Here take this.");
                                    c.nextChat = 459;
                                    break;
                                case 459:
                                    c.getDH().sendStatement("Juliet drinks the potion.");
                                    c.getItems().deleteItem(4836, 1);
                                    c.nextChat = 460;
                                    break;
                                case 460:
                                    c.getDH().sendNpcChat1("What was that..", c.talkingNpc, "Juliet");
                                    c.nextChat = 461;
                                    break;
                                case 461:
                                    c.getDH().sendPlayerChat1("Now tell me why you've been ignoring Romeo!");
                                    c.nextChat = 462;
                                    break;
                                case 462:
                                    c.getDH().sendNpcChat1("Well...tomorrow's Romeos birthday.", c.talkingNpc, "Juliet");
                                    c.nextChat = 463;
                                    break;
                                case 463:
                                    c.getDH().sendNpcChat3("Mama and I wanted to show Romeo that",
                                            "Even though he's richer then us we still",
                                            "care for him.", c.talkingNpc, "Juliet");
                                    c.nextChat = 464;
                                    break;
                                case 464:
                                    c.getDH().sendPlayerChat1("Wait was that it?");
                                    c.nextChat = 465;
                                    break;
                                case 465:
                                    c.getDH().sendNpcChat2("Yeah, I've been telling Mother to act",
                                            "grouchy with him.", c.talkingNpc, "Juliet");
                                    c.nextChat = 466;
                                    break;
                                case 466:
                                    c.getDH().sendNpcChat2("That way we can throw him off easily.",
                                            "that's what a surprise is.", c.talkingNpc, "Juliet");
                                    c.nextChat = 467;
                                    break;
                                case 467:
                                    c.getDH().sendPlayerChat1("I should get going");
                                    c.nextChat = 0;
                                    c.romeojuliet++;
                                    break;
                                case 468:
                                    c.getDH().sendNpcChat1("Wait your not going to tell Romeo are you?", c.talkingNpc, "Juliet");
                                    c.nextChat = 0;
                                    break;
                                case 469:
                                    c.getDH().sendNpcChat1("So? What's going on with Juliet?", c.talkingNpc, "Romeo");
                                    c.nextChat = 470;
                                    break;
                                case 470:
                                    c.getDH().sendPlayerChat2("She's been planning a surprise", "birthday party for you all along");
                                    c.nextChat = 471;
                                    break;
                                case 471:
                                    c.getDH().sendPlayerChat2("Her mother was in on in too. They", "just wanted to show that they're always there for you");
                                    c.nextChat = 472;
                                    break;
                                case 472:
                                    c.getDH().sendNpcChat1("Wait but my birthday isn't till next week", c.talkingNpc, "Romeo");
                                    c.nextChat = 473;
                                    break;
                                case 473:
                                    c.getDH().sendNpcChat2("That was very thoughtful of her",
                                            "Thank you young traveller for all your help", c.talkingNpc, "Romeo");
                                    c.nextChat = 474;
                                    break;
                                case 474:
                                    c.romeojuliet++;
                                    c.nextChat = 475;
                                    break;
                                case 475:
                                    c.getDH().sendNpcChat2("Juliet and I have been great ever",
                                            "since you've helped. Thank you adventurer", c.talkingNpc, "Romeo");
                                    c.nextChat = 0;
                                    break;
        						case 476:
                        			c.getDH().sendNpcChat1("Praise Saradomin! He has Brought you here to save us all!", c.talkingNpc, "Morgan");
                        			c.nextChat = 477;
                        			break;
                        			case 477:
                        			c.getDH().sendPlayerChat1("Wha-");
                        			c.nextChat = 478;
                        			break;
                        			case 478:
                        			c.getDH().sendNpcChat2("He has guided your steps to my door, So that", "I may beseech you to save my village from a terrible threat.", c.talkingNpc, "Morgan");
                        			c.nextChat = 479;
                        			break;
                        			case 479:
                        			c.getDH().sendOption2("Why don't you save your own village?", "What terrible threat?");
                        			c.dialogueAction = 29;
                        			break;
                        			case 480://9157
                        			c.getDH().sendPlayerChat1("Why don't you save your own village?");
                        			c.nextChat = 0;
                        			break;
                        			case 481://9158
                        			c.getDH().sendPlayerChat1("What terrible threat?");
                        			c.nextChat = 482;
                        			break;
                        			case 482:
                        			c.getDH().sendNpcChat2("Our village is plagued by a vampire. He visits us frequently", "and demands blood payments or he will terroise us all!", c.talkingNpc, "Morgan");
                        			c.nextChat = 483;
                        			break;
                        			case 483:
                        			c.getDH().sendPlayerChat2("The vampire showed up all of a sudden", "and started attacking your village?");
                        			c.nextChat = 484;
                        			break;
                        			case 484:
                        			c.getDH().sendNpcChat3("I don't know, I just moved here with my wife.", "We'd move on again,", "but we're down on our luck and can't afford to.", c.talkingNpc, "Morgan");
                        			c.nextChat = 485;
                        			break;
                        		    case 485:
                        			c.getDH().sendNpcChat3("Besides, I don't want to abandon other innocents to this fate.", "This could be a good community.", "If only that vampire would leave us.", c.talkingNpc, "Morgan");
                        			c.nextChat = 486;
                        			break;
                        			case 486:
                        			c.getDH().sendNpcChat1("Will you help me, brave adventurer?", c.talkingNpc, "Morgan");
                        			c.nextChat = 487;
                        			break;
                        			case 487:
                        			c.getDH().sendOption2("Yes", "No");
                        			c.dialogueAction = 30;
                        			break;
                        			case 488://9157
                        			c.getDH().sendPlayerChat1("Yes I'll help you.");
                        			c.vampSlayer = 1;
                        			c.nextChat = 489;
                        			break;
                        			case 489:
                        			c.getDH().sendNpcChat4("Wonderful! You will succeed.,", "I'm sure of it you are very brave to take this on.", "But you should speak to my friend Harlow before you,", "do anything else.", c.talkingNpc, "Morgan");
                        			c.nextChat = 490;
                        			break;
                        			case 490:
                        			c.getDH().sendPlayerChat1("Who is this harlow?");
                        			c.nextChat = 491;
                        			break;
                        			case 491:
                        			c.getDH().sendNpcChat4("He is a retired vampire slayer!,", "I met him when i was a missionaire, long ago.", "He will be able to advise you on the best methods to,", "vanquish the vampire.", c.talkingNpc, "Morgan");
                        			c.nextChat = 492;
                        			break;
                        			case 492:
                        			c.getDH().sendPlayerChat2("You already know a vampire slayer?", "What do you need me for?");
                        			c.nextChat = 493;
                        			break;
                        			case 493:
                        			c.getDH().sendNpcChat3("Harlow is... past his prime.. He's seen too many evil things in his life", "and, to forget that, he drinks himself into oblivion.", "I fear he will slayer vampires no more.", c.talkingNpc, "Morgan");
                        			c.nextChat = 494;
                        			break;
                        			case 494:
                        			c.getDH().sendPlayerChat1("Where can i find this Harlow?");
                        			c.nextChat = 495;
                        			break;
                        			case 495:
                        			c.getDH().sendNpcChat4("He spends his time at the Blue Moon Inn, located in Varrock.,", "If you enter Varrock from the south it is,", "the second building on your right.", "I'm sure it's filled with lively people, so you shouldn't miss it.", c.talkingNpc, "Morgan");
                        			c.nextChat = 496;
                        			break;
                        			case 496:
                        			c.getDH().sendPlayerChat1("Okay, I'll go find Harlow.");
                        			c.nextChat = 497;
                        			break;
                        			case 497:
                        			c.getDH().sendNpcChat1("May Saradomin protect you, my friend!", c.talkingNpc, "Morgan");
                        			c.nextChat = 0;
                        			break;
                        			case 498:
                        			c.getDH().sendNpcChat1("Buy me a drink please.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 499;
                        			break;
                        			case 499:
                        			c.getDH().sendOption4("No you've had enough.", "Are you Dr Harlow, the famous vampire slayer?", "You couldn't possibly be Dr Harlow, your just a drunk.", "Never mind.");
                        			c.dialogueAction = 31;
                        			break;
                        			case 500://9178
                        			c.getDH().sendPlayerChat1("No you've had enough.");
                        			c.nextChat = 0;
                        			break;
                        			case 501://9180
                        			c.getDH().sendPlayerChat1("You couldn't possibly be Dr Harlow, your just a drunk.");
                        			c.nextChat = 0;
                        			break;
                        			case 502://9179
                        			c.getDH().sendPlayerChat1("Are you Dr harlow, the famous vampire Slayer?");
                        			c.nextChat = 503;
                        			break;
                        			case 503:
                        			c.getDH().sendNpcChat1("Dependish whose is ashking.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 504;
                        			break;
                        			case 504:
                        			c.getDH().sendPlayerChat2("Your friend Morgan sent me.", "He said you could teach me how to slay a vampire.");
                        			c.nextChat = 505;
                        			break;
                        			case 505:
                        			c.getDH().sendNpcChat2("Shure I can teach you.", "I wash the best vampire shhlayer ever.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 506;
                        			break;
                        			case 506:
                        			c.getDH().sendNpcChat1("Buy me a beer and I'll teach you.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 507;
                        			break;
                        			case 507:
                        			c.getDH().sendPlayerChat2("Your good friend Morgan is living in fear of a vampire,", "and all you think about is beer?");
                        			c.nextChat = 508;
                        			break;
                        			case 508:
                        			c.getDH().sendNpcChat1("Buy ush a drink anyway.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 509;
                        			break;
                        			case 509:
                        			if (c.getItems().playerHasItem(1917, 1)) {
                        			c.getDH().sendPlayerChat1("Okay, here you go.");
                        			c.getItems().deleteItem2(1917, 1);
                        			c.nextChat = 510;
                        			} else {
                        			c.getDH().sendPlayerChat1("Okay, let me get one.");
                        			c.nextChat = 0;
                        			}
                        			break;
                        			case 510:
                        			c.getDH().sendNpcChat1("Cheersh, matey.", c.talkingNpc, "Doctor Harlow");
                        			c.vampSlayer = 2;
                        			c.nextChat = 511;
                        			break;
                        			case 511:
                        			c.getDH().sendPlayerChat1("So tell me how to kill vampires then.");
                        			c.nextChat = 512;
                        			break;
                        			case 512:
                        			c.getDH().sendNpcChat1("Yes, yes, vampires, I was very good at killing em once.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 513;
                        			break;
                        			case 513:
                        			c.getDH().sendNpcChat2("Vampire slaying is not to be undertaken lighty.", "You must go in prepared, or you will die.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 514;
                        			break;
                        			case 514:
                        			c.getDH().sendNpcChat1("*Sigh*", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 515;
                        			break;
                        			case 515:
                        			c.getDH().sendNpcChat3("A stake is an essential tool for any vampire slayer.", "The stake must be used in the final blow againt the vampire.", "Or his dark magic will regenerate him to full health.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 516;
                        			break;
                        			case 516:
                        			c.getDH().sendNpcChat1("I always carry a spare, so you can have one.", c.talkingNpc, "Doctor Harlow");
                        			c.getItems().addItem(1549, 1);
                        			c.nextChat = 517;
                        			break;
                        			case 517:
                        			c.getDH().sendNpcChat1("You'll need a special hammer as well, to drive it in in properly.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 518;
                        			break;
                        			case 518:
                        			c.getDH().sendNpcChat1("Hmm, I think i have a spare hammer you can have.", c.talkingNpc, "Doctor Harlow");
                        			c.getItems().addItem(2347, 1);
                        			c.vampSlayer = 3;
                        			c.nextChat = 519;
                        			break;
                        			case 519:
                        			c.getDH().sendNpcChat2("One last thing. It's wise to carry garlic with you,", "vampires are slightly weakened if they can smell garlic.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 520;
                        			break;
                        			case 520:
                        			c.getDH().sendNpcChat4("Garlic is pretty common,", "I know I always advised Morgan to keep a supply,", "so you might be able to get some from him.", "If not, I know they it is in Port Sarim.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 521;
                        			break;
                        			case 521:
                        			c.getDH().sendPlayerChat1("Okay, So those are the supplies I need, but how do I acctually kill him?");
                        			c.nextChat = 522;
                        			break;
                        			case 522:
                        			c.getDH().sendNpcChat1("You are a eager one.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 523;
                        			break;
                        			case 523:
                        			c.getDH().sendNpcChat4("Killing a vampire is D A N G E R O U S!", "Never forget that. Go in prepared", "Understand you may die.", "It's a risk we all take in the buisness.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 524;
                        			break;
                        			case 524:
                        			c.getDH().sendNpcChat2("I've seen many fine men and women,", "die at the hands of vampires.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 525;
                        			break;
                        			case 525:
                        			c.getDH().sendNpcChat4("Enter the vampire's lair and attempt to open the coffin.", "He should be asleep in there, so try to use the stake on him.", "As you're new at this,", "you'll probably just wake him up and the real fight begins.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 526;
                        			break;
                        			case 526:
                        			c.getDH().sendNpcChat2("Fight him until he's nearly dead, and,", "when the moment is right. Stake him through the heart and hammer it in.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 527;
                        			break;
                        			case 527:
                        			c.getDH().sendNpcChat2("It's gruesome, but it's the only way.", "Once he's dead speak to morgan so he can notify the village.", c.talkingNpc, "Doctor Harlow");
                        			c.nextChat = 528;
                        			break;
                        			case 528:
                        				c.getDH().sendPlayerChat1("Thank you!");
                        				c.nextChat = 0;
                        			break;
                        			case 529:
                        				c.getDH().sendPlayerChat1("I killed the vampire!");
                        				c.nextChat = 530;
                        			break;
                        			case 530:
                        				c.getDH().sendNpcChat1("Congratulations! You have saved the village.", c.talkingNpc, "Morgan");
                        				FreeRewards.vampFinish(c);
                        				c.nextChat = 0;
                        			break;
                        			case 531:
                        				c.getDH().sendPlayerChat1("I still need to kill the vampire.");
                        				c.nextChat = 0;
                            		break;
                            		case 532:
                            			c.getDH().sendNpcChat1("What could you want with an old woman like me?", c.talkingNpc, "Hetty");
                            			c.nextChat = 533;
                    				break;
                    				case 533:
                    					c.getDH().sendOption2("I am in search of a quest", "I've heard that you are a witch");
                    					c.dialogueAction = 74;
                    				break;
                    				case 534: //9157
                    					c.getDH().sendPlayerChat1("I am in search of a quest.");
                    					c.nextChat = 536;
                    				break;
                    				case 535: //9158
                    					c.getDH().sendNpcChat1("Yes I am...", c.talkingNpc, "Hetty");
                    					c.nextChat = 0;
                    				break;
                    				case 536:
                    					c.getDH().sendNpcChat1("Would you like to become more proficient in the dark arts?", c.talkingNpc, "Hetty");
                    					c.nextChat = 537;
                    				break;
                    				case 537:
                    					c.getDH().sendOption3("Yes help me become one with my darker side.", "No I have my principles and hour.", "What, you mean improve my magic?");
                    					c.dialogueAction = 58;
                    				break;
                    				case 538://9168
                    					c.getDH().sendPlayerChat1("No I have my principles and hour.");
                    					c.nextChat = 0;
                    				break;
                    				case 539://9169
                    					c.getDH().sendPlayerChat1("What, you mean improve my magic?");
                    					c.nextChat = 0;
                    				break;
                    				case 540://9167
                    					c.getDH().sendPlayerChat1("Yes help me become one with my darker side.");
                    					c.nextChat = 541;
                    				break;
                    				case 541:
                    					c.getDH().sendNpcChat1("Ok, I'm going to make a potion to help bring out your darker self.", c.talkingNpc, "Hetty");
                    					c.nextChat = 542;
                    				break;
                    				case 542:
                    					c.getDH().sendNpcChat1("You will need certain ingredients.", c.talkingNpc, "Hetty");
                    					c.nextChat = 543;
                    				break;
                    				case 543:
                    					c.getDH().sendPlayerChat1("What do I need?");
                    					c.nextChat = 544;
                    				break;
                    				case 544:
                    					c.getDH().sendNpcChat2("You need an eye of newt, a rat's tail, an onion...", "Oh and a peice of burnt mean.", c.talkingNpc, "Hetty");
                    					c.nextChat = 545;
                    				break;
                    				case 545:
                    					c.getDH().sendPlayerChat1("Great, I'll go and get them.");
                    					c.witchspot = 1;
                    					c.nextChat = 0;
                    				break;
                    				case 546:
                    					c.getDH().sendNpcChat1("So have you found the things for my potion?", c.talkingNpc, "Hetty");
                    					c.nextChat = 547;
                    				break;
                    				case 547:
                    				if (c.getItems().playerHasItem(221, 1) && c.getItems().playerHasItem(300, 1) && c.getItems().playerHasItem(1957, 1)) {
                    					c.getDH().sendPlayerChat1("Yes I have everything!");
                    					c.witchspot = 2;
                    					c.nextChat = 550;
                    				} else {
                    					c.getDH().sendPlayerChat1("No I still need to keep looking.");
                    					c.nextChat = 0;
                    				}
                    				break;
                    				case 548:
                    					c.getDH().sendPlayerChat1("Yes I have everything!");
                    					c.witchspot = 2;
                    					c.nextChat = 550;
                    				break;
                    				case 549:
                    					c.getDH().sendPlayerChat1("No I still need to keep looking.");
                    					c.nextChat = 0;
                    				break;
                    				case 550:
                    					c.getDH().sendNpcChat1("Excellent can I have them?", c.talkingNpc, "Hetty");
                    					c.nextChat = 551;
                    				break;
                    				case 551:
                    				if (c.getItems().playerHasItem(221, 1) && c.getItems().playerHasItem(300, 1) && c.getItems().playerHasItem(1957, 1)) {
                    					c.getDH().sendNpcChat3("You pass the ingredients to Hetty and she puts them all into here cauldron.", "Hetty closes her eyes and begins to chant.", "The caludron bubbles mysteriously.", c.talkingNpc, "Hetty");
                    					c.getItems().deleteItem(221, 1);
                    					c.getItems().deleteItem(300, 1);
                    					c.getItems().deleteItem(1957, 1);
                    					c.nextChat = 552;
                    				} else {
                    					c.getDH().sendPlayerChat1("I don't have them anymore");
                    				}
                    				break;
                    				case 552:
                    					c.getDH().sendPlayerChat1("Well, is it ready?");
                    					c.nextChat = 553;
                    				break;
                    				case 553:
                    					c.getDH().sendNpcChat1("Ok, now drink from the cauldron.", c.talkingNpc, "Hetty");
                    					c.nextChat = 0;
                    				break;
                    				case 554:
                						c.getDH().sendNpcChat1("Arr, Matey!", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 555;
                					break;
                					case 555:
                						c.getDH().sendOption2("I'm in search of treasure.", "Arr!");
                						c.dialogueAction = 71;
                					break;
                					case 556://9157
                						c.getDH().sendPlayerChat1("I'm in search of treasure.");
                						c.nextChat = 557;
                					break;
                					case 557:
                						c.getDH().sendNpcChat2("Arr, trasure you be after eh?", "Well I might be able to tell you where to find some... For a price...", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 558;
                					break;
                					case 558:
                						c.getDH().sendPlayerChat1("What sort of price?");
                						c.nextChat = 559;
                					break;
                					case 559:
                						c.getDH().sendNpcChat2("Well for example if you can get me a bottle of rum..", "Not just any rum mind...", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 560;
                					break;
                					case 560:
                						c.getDH().sendNpcChat2("I'd like some rum made on Karamja Island.", "There's no rum like Karamja Rum!", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 561;
                					break;
                					case 561:
                						c.getDH().sendOption2("Ok, I will bring you some rum.", "Not right now.");
                						c.dialogueAction = 72;
                					break;
                					case 562://9158
                						c.getDH().sendPlayerChat1("Not right now.");
                						c.nextChat = 0;
                					break;
                					case 563://9157
                						c.getDH().sendPlayerChat1("Ok, I will bring you some rum.");
                						c.nextChat = 564;
                					break;
                					case 564:
                						c.getDH().sendNpcChat1("Yer a saint, although it'll take a miracle to get it off Karamja.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 565;
                					break;
                					case 565:
                						c.getDH().sendPlayerChat1("What do you mean?");
                						c.nextChat = 566;
                					break;
                					case 566:
                						c.getDH().sendNpcChat3("The customs office has been clampin' down on the export of spirits. ", "You seem like a resourceful young lad,", " I'm sure ye'll be able to find a way to slip the stuff past them.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 567;
                					break;
                					case 567:
                						c.getDH().sendPlayerChat1("Well, I'll give it a shot.");
                						c.nextChat = 568;
                					break;
                					case 568:
                						c.getDH().sendNpcChat1("Arr, that's the spirit!", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 0;
                						c.pirateTreasure = 1;
                					break;
                					case 569:
                					if (c.pirateTreasure == 2 && c.getItems().playerHasItem(431, 1)) {
                						c.getDH().sendNpcChat1("Arr, Matey!", c.talkingNpc, "Redbeard Frank");
                						c.getItems().deleteItem2(431, 1);
                						c.nextChat = 570;
                					} else {
                						c.getDH().sendPlayerChat1("No I still need to get it");
                						c.nextChat = 0;
                					}
                					break;
                					case 570:
                						c.getDH().sendNpcChat1("Have ye brought some rum for yer ol' mate Frank?", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 571;
                					break;
                					case 571:
                					if (c.getItems().playerHasItem(431, 1)) {
                						c.getDH().sendPlayerChat1("Yes I've got some.");
                						c.nextChat = 572;
                					} else {
                						c.getDH().sendPlayerChat1("No I still need to get it");
                						c.nextChat = 0;
                					}
                					break;
                					case 572:
                						c.getDH().sendNpcChat2("Now a deal's a deal, I'll tell ye about the treasure.", "I used to server under a pirate captain called One-Eyed Hector.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 573;
                					break;
                					case 573:
                						c.getDH().sendNpcChat2("Hector were very successful and became very rich.", "But about a year ago we were boarded by the Customs and Excise Agents.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 574;
                					break;
                					case 574:
                						c.getDH().sendNpcChat2("Hector were killed along with many of the crew,", "I were one of the few to escape and I escaped with this.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 575;
                					break;
                					case 575:
                						c.getDH().sendStatement("Frank happily takes the rum... and hands you a key");
                						c.getItems().addItem(432, 1);
                						c.getItems().deleteItem(431, 1);
                						c.nextChat = 576;
                					break;
                					case 576:
                						c.getDH().sendNpcChat2("This be Hector's key. ", "I belive it opens his chest in his old room in the Blue Moon Inn in Varrock.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 577;
                					break;
                					case 577:
                						c.getDH().sendNpcChat1("With any luck his treasure will be in there.", c.talkingNpc, "Redbeard Frank");
                					c.nextChat = 578;
                					break;
                					case 578:
                						c.getDH().sendOption2("Ok thanks, I'll go and get it.", "So why didn't you ever get it?");
                						c.dialogueAction = 73;
                					break;
                					case 579://9157
                						c.getDH().sendPlayerChat1("Ok thanks, I'll go and get it.");
                						c.pirateTreasure = 3;
                						c.nextChat = 0;
                					break;
                					case 580://9158
                						c.getDH().sendPlayerChat1("So why didn't you ever get it?");
                						c.nextChat = 581;
                					break;
                					case 581:
                						c.getDH().sendNpcChat1("That's none of your buisness.", c.talkingNpc, "Redbeard Frank");
                						c.nextChat = 0;
                					break;
                					case 582://9158
                						c.getDH().sendPlayerChat1("Arr!");
                						c.nextChat = 0;
                					break;
                					
                					case 583:
                						if (!c.getItems().playerHasItem(995, 30)) {
                		    				c.getDH().sendPlayerChat1("Sorry, I don't have enough coins for that.");
                		    				c.nextChat = 0;
                		    				return;
                		    			} else {
                    						Sailing.startTravel(c, 5);
                    						c.getItems().deleteItem2(995, 30);
                    		    			c.nextChat = 0;
                		    			}
                					break;
                					
                					case 584:
                		    			c.getDH().sendNpcChat1("The trip back to port sarim will cost you 30 coins.", c.talkingNpc, "Sailor");
                		    			c.nextChat = 585;
                		    		break;
                		    		
                		    		case 585:
                		    			c.getDH().sendOption2("Yes", "No");
                		    			c.dialogueAction = 68;
                		    		break;
                		    		
                		    		case 586:
                		    			c.getDH().sendPlayerChat1("No thank you");
                		    			c.nextChat = 0;
                		    		break;
                		    		
                		    		case 587:
                		    			c.getDH().sendPlayerChat1("Yes please");
                		    			c.nextChat = 588;
                		    		break;
                		    		
                		    		case 588:
                						if (!c.getItems().playerHasItem(995, 30)) {
                		    				c.getDH().sendPlayerChat1("Sorry, I don't have enough coins for that.");
                		    				c.nextChat = 0;
                		    				return;
                		    			} else {
                     						Sailing.startTravel(c, 6);
                     						c.getItems().deleteItem2(995, 30);
                    		    			c.nextChat = 0;
                		    			}	
                					break;
                					
                		    		case 589:
                		    			c.getItems().deleteItem2(995, 30);
                		    			c.nextChat = 0;
                					break;
                					
                		    		case 590:
                		    			c.getDH().sendNpcChat1("Hello " + c.playerName + ".", c.npcType, "Gnome Pilot");
                		    			c.nextChat = 591;
                		    		break;
                		    		
                		    		case 591:
                		    			c.getDH().sendNpcChat1("Would you like to fly my glider?", c.npcType, "Gnome Pilot");
                		    			c.nextChat = 592;
                		    		break;
                		    		
                		    		case 592:
                		    			c.getDH().sendOption2("Yes", "No");
                		    			c.dialogueAction = 130;
                		    		break;
                		    		
                		    		case 593:
                		    			c.getDH().sendPlayerChat1("No thank you.");
                		    			c.nextChat = 0;
                		    		break;
                		    		
                		    		case 594:
                		    			c.getDH().sendPlayerChat1("Yes Please.");
                		    			c.nextChat = 595;
                    		    	break;
                    		    	
                		    		case 595:
                		    			c.getPA().showInterface(802);
                		    		break;
                		    		
                		    		   case 596:
                		                   c.getDH().sendNpcChat2("You didn't participate enough to take down", "You've gain less points",
                		                           c.talkingNpc, "Void Knight");
                		                   c.nextChat = 0;
                		                   break;
                		               case 597:
                		            	   c.getDH().sendNpcChat3("You couldn't take down all the portals in time.", "Please try harder next time, or ask more", "people to join your game.",
                		                           c.talkingNpc, "Void Knight");
                		                   c.nextChat = 0;
                		                   break;
                		               case 598:
                		            	   c.getDH().sendNpcChat3("Congratulations " + c.playerName + "! you have taken", "down all the portals while keeping the Knight alive", "please accept this reward from us.",
                		                           c.talkingNpc, "Void Knight");
                		                   c.sendMessage("You have won the Pest Control game!");
                		                   c.nextChat = 0;
                		                   break;
                		               case 599:
                		            	   c.getDH().sendNpcChat2("Do not let the Void Knights health reach 0!", "You can regain health by destroying more monsters",
                		                           c.talkingNpc, "Void Knight");
                		                   c.nextChat = 600;
                		                   break;
                		               case 600:
                		            	   c.getDH().sendNpcChat1("NOW GO AND DESTROY THOSE PORTALS!!!", c.talkingNpc, "Void Knight");
                		                   c.nextChat = 0;
                		                   break;
                		               case 601:
                		            	   c.getDH().sendNpcChat1("You call yourself a Knight?", c.talkingNpc, "Void Knight");
                		                   c.nextChat = 0;
                		                   break;
                		               case 602:
                		            	   c.getDH().sendNpcChat1("Hi welcome to Pest Control.", c.talkingNpc, "Void Knight");
                		                   c.nextChat = 84;
                		                   break;
                		               case 603:
                		            	   c.getDH().sendNpcChat1("Would you like to open the Armor Shop or Exp Shop?", c.talkingNpc, "Void Knight");
                		                   c.nextChat = 85;
                		                   break;
                		               case 604:
                		            	   c.getDH().sendOption2("Void Knight Armor", "Experience Shop");
                		                   c.dialogueAction = 85;
                		                   break;
                		               case 605:
                		            	   c.getDH().sendNpcChat2("The party room is a fun place where you can put your items", "in the chest and drop them and have a party with your friends.", c.npcType, "Party Pete");
                		            	   c.nextChat = 0;
                		            	   break;
     		    	   
    					    		case 1000:
    					    			c.getDH().sendNpcChat1("Is it nice and tidy round the back now?", c.talkingNpc, "Wydin");
    					    			c.nextChat = 1001;
    					    		break;
    					    		case 1001:
    					    			c.getDH().sendOption4("Yes, can I work out front now?", "Yes, are you going to pay me yet?", "No it's a complete mess", "Can I buy something please?");
    					    			c.dialogueAction = 69;
    					    		break;
    					    		case 1002://9179
    					    			c.getDH().sendNpcChat1("Not yet.", c.talkingNpc, "Wydin");
    					    			c.nextChat = 0;
    					    		break;
    					    		case 1003://9180
    					    			c.getDH().sendPlayerChat1("No it's a complete mess");
    					    			c.nextChat = 0;
    					    		break;
    					    		case 1004://9181
    					    			c.getDH().sendPlayerChat1("Can I buy something please?");
    					    			c.getShops().openShop(34);
    					    			c.nextChat = 0;
    					    		break;
    					    		case 1005://9178
    					    			c.getDH().sendPlayerChat1("Yes can I work out front now?");
    					    			c.nextChat = 1006;
    					    		break;
    					    		case 1006:
    					    			c.getDH().sendNpcChat1("Not I'm the person who works here.", c.talkingNpc, "Wydin");
    					    			c.nextChat = 0;
    					    		break;
    					    		case 1007:
    					    			c.getDH().sendStatement("You find a hole. Would you like to enter it?");
    					    			c.dialogueAction = 70;
    					    		break;
    					    		case 1008:
    					    			c.getDH().sendOption2("Yes", "No");
    					    		break;
    					    		case 1009:
    					    			c.getDH().sendPlayerChat1("Yes.");
    					    			c.getPA().movePlayer(1761, 5192, 0);
    					    		break;
    								case 1011:
    									c.getDH().sendNpcChat2("How dare you try to take dangerous equipment?", "Come back when you have left it all behind.", c.talkingNpc, "Monk of Entrana");
    									c.nextChat = 0;
    								break;
    								case 1012:
    									c.getDH().sendNpcChat2("You even defeated TzTok-Jad, I am most impressed!", "Please accept this gift as a reward.", c.talkingNpc, "Tzhaar-Mej-Tal");
    									c.nextChat = -1;
    								break;
    								
    								  /** Bank Settings **/
    					            case 1013:
    					                c.getDH().sendNpcChat1("Good day. How may I help you?", c.talkingNpc, "Banker");
    					                c.nextChat = 1014;
    					                break;
    					            case 1014://bank open done, this place done, settings done, to do delete pin
    					                c.getDH().sendOption3("I'd like to access my bank account, please.",
    					                        "I'd like to check my my P I N settings.", "What is this place?");
    					                c.dialogueAction = 251;
    					                break;
    					            /** What is this place? **/
    					            case 1015:
    					                c.getDH().sendPlayerChat1("What is this place?");
    					                c.nextChat = 1016;
    					                break;
    					            case 1016:
    					                c.getDH().sendNpcChat2("This is the bank of 2006redone." ,"We have many branches in many towns.", c.talkingNpc, "Banker");
    					                c.nextChat = 0;
    					                break;
    					            /** Note on P I N. In order to check your "Pin Settings. You must have enter your Bank Pin first **/
    					            /** I don't know option for Bank Pin **/
    					            case 1017:
    					                c.getDH().sendStartInfo("Since you don't know your P I N, it will be deleted in @red@3 days@bla@. If you",
    					                        "wish to cancel this change, you may do so by entering your P I N",
    					                        "correctly next time you attempt to use your bank.", "", "", false);
    					                c.nextChat = 0;
    					                break;
    					                
    					            case 1018:
    					            	c.getDH().sendNpcChat1("Hello " + c.playerName + " would you like to purchase anything?", c.talkingNpc, "Shantay");
    					            	c.nextChat = 1019;
    					            break;
    					            	
    					            case 1019:
    					            	c.getDH().sendOption2("Yes Please.", "No thank you.");
    					            	c.dialogueAction = 141;
    					           break;        	
    					            	
    					            case 1020:
    					            	c.getDH().sendPlayerChat1("Yes please.");
    					            	c.nextChat = 1032;
    					            break;
    					            
    					            case 1032:
    					            	c.getShops().openShop(103);
    					            break;
    					            
    					            case 1021:
    					            	c.getDH().sendPlayerChat1("No thank you.");
    					            	c.nextChat = 0;
    					            break;
    					            
    					     	case 1022:
    					                c.getDH().sendPlayerChat1("Can I come through this gate?");
    					                c.nextChat = 1023;
    					        break;
    					                
    					        case 1023:
    					                c.getDH().sendNpcChat1("You must pay a toll of 10 gold coins to pass.", c.talkingNpc, "Border Guard");
    					                c.nextChat = 1024;
    					        break;
    					        case 1024:
    					                c.getDH().sendOption3("Okay, I'll pay.", "Who does my money go to?", "No thanks, I'll walk around.");
    					                c.dialogueAction = 502;
    					        break;
    					        case 1025:
    					                c.getDH().sendPlayerChat1("Okay, I'll pay.");
    					                c.nextChat = 1026;
    					                break;
    					        case 1026:
    					                c.getDH().sendPlayerChat1("Who does my money go to?");
    					                c.nextChat = 1027;
    					                break;
    					        case 1027:
    					                c.getDH().sendNpcChat2("The money goes to the city of Al-Kharid.","Will you pay the toll?", c.talkingNpc, "Border Guard");
    					                c.nextChat = 1028;
    					        break;
    					        case 1028:
    					                c.getDH().sendOption2("Okay, I'll pay.", "No thanks, I'll walk around.");
    					                c.dialogueAction = 508;
    					        break;
    					        case 1029:
    					                c.getDH().sendPlayerChat1("No thanks, I'll walk around.");
    					                c.nextChat = 0;
    					        break;
    					                
    					       case 1030:
    					       if (!c.getItems().playerHasItem(995, 10)) {
    					    	   c.getDH().sendPlayerChat1("I haven't got that much.");
    					    	   c.nextChat = 0;
    					       } else {
    					    	   c.nextChat = 1031;
    					       }
    					       break;
    					       
    					       case 1031:
    					    	   c.getDH().sendNpcChat1("As you wish. Don't get too close to the scopions.", c.talkingNpc, "Border Guard");
    					    	   c.getItems().deleteItem2(995, 10);
    							   c.sendMessage("You pass the gate.");
    							   Special.movePlayer(c);
    							   Special.openKharid(c, c.objectId);
    							   c.turnPlayerTo(c.objectX, c.objectY);
    					    	   c.nextChat = 0;
    					      break;
    					      

    					       case 1033:
    					       c.getDH().sendOption2("I would like to skip tutorial Island", "I would like to continue");
    					       c.dialogueAction = 132;
    					       break;

    					       case 1034:
    					       c.getDH().sendNpcChat1("You have been warned, you can't go back now.", c.talkingNpc, "Runescape Guide");
    					       c.getPA().movePlayer(3098, 3107, 0);
    					       c.nextChat = 0;
    					       break;

    					       case 1035:
    					       c.getDH().sendNpcChat1("You have successfully skipped tutorial island.", c.talkingNpc, "Runescape Guide");
    					       TutorialIsland.tutorialIslandStage = 66;
    					       c.getPA().startTeleport(3222, 3218, 0, "modern");
    					       c.nextChat = 0;
    					       break;
    					       
				}
	}
}