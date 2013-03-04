package server.game.dialogues;

import server.game.content.questhandling.MemberRewards;
import server.game.players.Client;

public class MemberDialogues {

	public static void handledialogue(Client c, int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {	
		case 269:
			c.getDH().sendPlayerChat1("Hello, are you ok?");
			c.nextChat = 270;
			break;
			case 270:
			c.getDH().sendNpcChat2("Do I look ok?", "Those kid's drive me crazy.", c.talkingNpc, "Gertrude");
			c.nextChat = 271;
			break;
			case 271:
			c.getDH().sendNpcChat1("I'm sorry. It's just that I've lost her.", c.talkingNpc, "Gertrude");
			c.nextChat = 272;
			break;
			case 272:
			c.getDH().sendPlayerChat1("Lost whom?");
			c.nextChat = 273;
			break;
			case 273:
			c.getDH().sendNpcChat1("Fluffs, poor Fluffs, She never hurt anyone.", c.talkingNpc, "Gertrude");
			c.nextChat = 274;
			break;
			case 274:
			c.getDH().sendPlayerChat1("Who's Fluffs?");
			c.nextChat = 275;
			break;
			case 275:
			c.getDH().sendNpcChat3("My beloved feline friend, Fluffs.", "She's been purring by my side for almost a decade.", "Could you go and search for her while I take care of the children?", c.talkingNpc, "Gertrude");
			c.nextChat = 276;
			break;
			case 276:
			c.getDH().sendOption3("Well, I suppose I could thought I'd need more details.", "What's in it for me?", "Sorry, I'm too busy to play per rescue.");
			c.dialogueAction = 60;
			break;
			case 277: //9167
			c.getDH().sendPlayerChat1("Well, I suppose I could thought I'd need more details.");
			c.nextChat = 280;
			break;
			case 278: //9169
			c.getDH().sendPlayerChat1("Sorry I'm too busy to play per rescue.");
			c.nextChat = 0;
			break;
			case 279: //9168
			c.getDH().sendNpcChat1("Come back with a better attitude a maybe you will find out.", c.talkingNpc, "Gertrude");
			c.nextChat = 0;
			break;
			case 280:
			c.getDH().sendNpcChat2("Really? Thank you so much!.", "I really have no idea where she could be!", c.talkingNpc, "Gertrude");
			c.gertCat = 1;
			c.nextChat = 281;
			break;
			case 281:
			c.getDH().sendNpcChat2("I think my sons, Shilop and Wilough, saw the cat last.", "They'll be out in the marketplace.", c.talkingNpc, "Gertrude");
			c.nextChat = 282;
			break;
			case 282:
			c.getDH().sendPlayerChat2("The marketplace? Which one would that be?", "It would help to know what they get up to, as well.");
			c.nextChat = 283;
			break;
			case 283:
			c.getDH().sendNpcChat4("Really? Well, I generally let them do what they want,", "so I've no idea exactly what they would be doing.", "They are good lads, though.", " I'm sure they are just watching the passers-by in Varrock Marketplace.", c.talkingNpc, "Gertrude");
			c.nextChat = 284;
			break;
			case 284:
			c.getDH().sendNpcChat1("Oh, to be young and carefree again!", c.talkingNpc, "Gertrude");
			c.nextChat = 285;
			break;
			case 285:
			c.getDH().sendPlayerChat2("I'll see what I can do. Two young lads in Varrock.", "I hope that there's no school trip passing through when I arrive.");
			c.nextChat = 0;
			break;
			case 286:
			c.getDH().sendPlayerChat1("Hello there, I've been looking for you.");
			c.nextChat = 287;
			break;
			case 287:
			c.getDH().sendNpcChat1("I didn't mean to take it! I just forgot to pay.", c.talkingNpc, "Wilough");
			c.nextChat = 288;
			break;
			case 288:
			c.getDH().sendPlayerChat2("What?", "I'm trying to help your mum find some cat called Fluffs.");
			c.nextChat = 289;
			break;
			case 289:
			c.getDH().sendNpcChat4("Ohh...well, in that case I might be able to help.", "Fluffs followed me to my super secret hideout.", "I haven't seen her since.", "She's probably off eating small creatures somewhere.", c.talkingNpc, "Wilough");
			c.nextChat = 290;
			break;
			case 290:
			c.getDH().sendPlayerChat2("Where is this secret hideout?", "I really need to find that cat for you mum.");
			c.nextChat = 291;
			break;
			case 291:
			c.getDH().sendNpcChat2("If I told you that, It wouldn't be a secret.", "What if I need to escape for the law? I need a hideout.", c.talkingNpc, "Wilough");
			c.nextChat = 292;
			break;
			case 292:
			c.getDH().sendPlayerChat2("From my limited knowledge of law,", "they are not usually involved in manhunts for children.");
			c.nextChat = 293;
			break;
			case 293:
			c.getDH().sendNpcChat2("Well it's still mine anyway, we need a place to relax sometimes.", "Those two little brothers at the house are just such babies.", c.talkingNpc, "Wilough");
			c.nextChat = 294;
			break;
			case 294:
			c.getDH().sendOption3("Tell me sonny, or I will inform you are a pair of criminals.", "What will make you tell me?", "Well never mind, it's Fluffs loss.");
			c.dialogueAction = 61;
			break;
			case 295: //9167
			c.getDH().sendNpcChat1("No. Where not criminals.", c.talkingNpc, "Wilough");
			c.nextChat = 0;
			break;
			case 296: //9169
			c.getDH().sendPlayerChat1("Well never mind, it's Fluffs loss.");
			c.nextChat = 0;
			break;
			case 297: //9168
			c.getDH().sendPlayerChat1("What will make you tell me?");
			c.nextChat = 298;
			break;
			case 298:
			c.getDH().sendNpcChat1("Well...now you ask, I am a bit short on cash.", c.talkingNpc, "Wilough");
			c.nextChat = 299;
			break;
			case 299:
			c.getDH().sendPlayerChat1("How much?");
			c.nextChat = 300;
			break;
			case 300:
			c.getDH().sendNpcChat1("10 coins.", c.talkingNpc, "Wilough");
			c.nextChat = 301;
			break;
			case 301:
			c.getDH().sendNpcChat1("10 coins?!", c.talkingNpc, "Shilop");
			c.nextChat = 302;
			break;
			case 302:
			c.getDH().sendNpcChat1("I'll handle this.", c.talkingNpc, "Shilop");
			c.nextChat = 303;
			break;
			case 303:
			c.getDH().sendNpcChat1("100 coins should cover it.", c.talkingNpc, "Shilop");
			c.nextChat = 304;
			break;
			case 304:
			c.getDH().sendPlayerChat2("100 coins!", "What sort of expensive things do you need that badly?");
			c.nextChat = 305;
			break;
			case 305:
			c.getDH().sendNpcChat2("Well I don't like chocolate", "and have you seen how much sweets cost to buy?", c.talkingNpc, "Shilop");
			c.nextChat = 306;
			break;
			case 306:
			c.getDH().sendPlayerChat1("Why should I pay you then, can you answer that as easily?");
			c.nextChat = 307;
			break;
			case 307:
			c.getDH().sendNpcChat3("Obviously you shouldn't pay that much,", "but I won't help otherwise. I never liked,", "that cat anyway, fussy scratchy thing it is, so what do you say?", c.talkingNpc, "Shilop");
			c.nextChat = 308;
			break;
			case 308:
			c.getDH().sendOption2("I'm not paying you a thing.", "Okay then, I'll pay.");
			c.dialogueAction = 62;
			break;
			case 309: //9158
			c.getDH().sendPlayerChat2("Okay then. I'll pay,", "but I'll want you to tell your mother what a nice person I am.");
			c.nextChat = 310;
			break;
			case 310:
			c.getDH().sendNpcChat1("What?", c.talkingNpc, "Shilop");
			c.nextChat = 311;
			break;
			case 311:
			c.getDH().sendPlayerChat1("I'll want you to tell your mother what a nice person I am so she rewards me for this search.");
			c.nextChat = 312;
			break;
			case 312:
			c.getDH().sendNpcChat1("It's a deal.", c.talkingNpc, "Shilop");
			c.nextChat = 313;
			break;
			case 313:
			if (c.getItems().playerHasItem(995, 100)) {
			c.getDH().sendStatement("You give the lad 100 coins.");
			c.getItems().deleteItem(995, 100);
			c.nextChat = 314;
			c.gertCat = 2;
			} else {
			c.getDH().sendStatement("I don't have 100 coin's I should come back.");
			}
			break;
			case 314:
			c.getDH().sendPlayerChat1("There you go, now where did you see Fluffs?");
			c.nextChat = 315;
			break;
			case 315:
			c.getDH().sendNpcChat4("We hide out at the lumber mill to the northeast.", " Just beyond the Jolly Beat Inn.", "I saw Fluffs running around in there.", "Well, not some much running as plodding lazily, but you get the idea.", c.talkingNpc, "Wilough");
			c.nextChat = 316;
			break;
			case 316:
			c.getDH().sendPlayerChat1("Anything else?");
			c.nextChat = 317;
			break;
			case 317:
			c.getDH().sendNpcChat3("Well, technically you are tresspassing inside there but noone seems to care.", "You'll have to find the broken fence to get in.", "It will be a bit of a squeeze for a grown-up but I'm sure you can manage that.", c.talkingNpc, "Wilough");
			c.nextChat = 0;
			break;
			case 318:
			c.getDH().sendStatement("Mew");
			c.nextChat = 319;
			break;
			case 319:
			c.getDH().sendPlayerChat1("Progress atleast.");
			c.nextChat = 320;
			break;
			case 321:
			c.getDH().sendStatement("Fluffs laps up the milk greedly. The she mews at you again.");
			c.nextChat = 0;
			break;
			case 322:
			c.getDH().sendStatement("Mew!");
			c.nextChat = 323;
			break;
			case 323:
			c.getDH().sendPlayerChat1("Progress atleast.");
			c.nextChat = 324;
			break;
			case 324:
			c.getDH().sendStatement("Fluffs devours the dougle sardine greedly. Then she mews at you again.");
			c.nextChat = 0;
			break;
			case 325:
			c.getDH().sendStatement("Fluffs seems afraid to leave. In the lumberyard below you can hear the mewing.");
			c.nextChat = 0;
			break;
			case 326:
			c.getDH().sendNpcChat1("Purr...", c.talkingNpc, "Fluffs");
			c.nextChat = 327;
			break;
			case 327:
			c.getDH().sendStatement("Fluffs and her offspring will now live happily.");
			c.gertCat = 6;
			c.nextChat = 0;
			break;
			case 328:
			c.getDH().sendPlayerChat2("Hello Gertrude. Fluffs has run off with her lost kittens.", "That I have now returned to her.");
			c.nextChat = 329;
			break;
			case 329:
			c.getDH().sendNpcChat4("You're Back!", "Thank you, thank you!", "Fluffs just came back.", "I think she was upset, because she couldn't find her kittens.", c.talkingNpc, "Gertrude");
			c.nextChat = 330;
			break;
			case 330:
			c.getDH().sendStatement("Gertrude thank's you heartily.");
			c.nextChat = 331;
			break;
			case 331:
			c.getDH().sendNpcChat2("If you wouldn't have found her kittens,", "then they would have died out there.", c.talkingNpc, "Gertrude");
			c.nextChat = 332;
			break;
			case 332:
			c.getDH().sendPlayerChat1("That's okay, I like to do my bit.");
			c.nextChat = 333;
			break;
			case 333:
			c.getDH().sendNpcChat3("I don't know how to thank you.", "I have no real material possesions, but I do have kittens.", "I can really only look after one or two.", c.talkingNpc, "Gertrude");
			c.nextChat = 334;
			break;
			case 334:
			c.getDH().sendPlayerChat1("Well, if one needs a home.");
			c.nextChat = 335;
			break;
			case 335:
			c.getDH().sendNpcChat4("I would sell one to my couzin in West Aroudnge.", "I hear there's a " + " epidemic there,", "but it's too far for me to travel.", "With all my boys and all.", c.talkingNpc, "Gertrude");
			c.nextChat = 336;
			break;
			case 336:
			c.getDH().sendNpcChat1("Here you go look after her and thank you.", c.talkingNpc, "Gertrude");
			c.nextChat = 337;
			break;
			case 337:
			c.getDH().sendStatement("Gertrude gives you a kitten.");
			MemberRewards.gertFinish(c);
			c.nextChat = 0;
			break;
		}
	}
}