package server.game.objects;

import server.Config;
import server.Server;
import server.game.content.clipping.clip.region.ObjectDef;
import server.game.content.minigames.Barrows;
import server.game.content.minigames.FightPits;
import server.game.content.minigames.PestControl;
import server.game.content.minigames.castlewars.CastleWarObjects;
import server.game.content.questhandling.FreeRewards;
import server.game.content.random.Balloons;
import server.game.content.random.PartyRoom;
import server.game.content.randomevents.FreakyForester;
import server.game.content.skills.core.Mining;
import server.game.content.skills.crafting.JewelryMaking;
import server.game.content.skills.runecrafting.AbyssalHandler;
import server.game.content.skills.thieving.Stalls;
import server.game.content.traveling.Desert;
import server.game.content.traveling.Sailing;
import server.game.dialogues.FreeDialogues;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.task.Task;
import server.util.Misc;
import server.util.ScriptManager;

public class ObjectsActions {

	private Client c;

	public ObjectsActions(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (c.getNewComersSide().isInTutorialIslandStage() && c.getNewComersSide().sendDialogue(c)) {
			return;
		}
		if (Webs.webs(c, objectType)) {
			Webs.slashWeb(c, objectType, obX, obY);
			return;
		}
		if (Searching.search(c, objectType)) {
			Searching.search(c, objectType, obX, obY);
			return;
		}
        if (Ladders.manualLadders(c, objectType)) {
        	Ladders.extraLadders(c, objectType);
            return;
        }
        if (c.getAgility().gnomeCourse(objectType))
    		return;
    	if (c.getAgility().wildernessCourse(objectType))
    		return;
    	if (c.getAgility().barbarianCourse(objectType))
    		return;
    	if (c.getAgility().pyramidCourse(objectType))
    		return;
    	if (c.getAgility().apeAtollCourse(objectType))
    		return;
    	if (c.getAgility().werewolfCourse(objectType))
    		return;
    	if (objectType >= 115 && objectType <= 121) {
			Balloons.popBalloon(c, obX, obY);
			return;
		}
    	if(Mining.rockExists(c, objectType)) {
			Mining.startMining(c, objectType, obX, obY);
			return;
		}
    	final String objectName = ObjectDef.getObjectDef(objectType).name;
		if(objectName.equalsIgnoreCase("furnace") || objectName.equalsIgnoreCase("Furnace")){
			c.getSmithing().sendSmelting();
			return;
		}
    	
		switch (objectType) {	
		  case 14315:
              if (!PestControl.waitingBoat.containsKey(c)) {
                  PestControl.addToWaitRoom(c);
              } else {
                  c.getPA().movePlayer(2661, 2639, 0);
              }
              break;
          case 14314:
              if (c.inPcBoat()) {
                  if (PestControl.waitingBoat.containsKey(c))
                      PestControl.leaveWaitingBoat(c);
                  else
                      c.getPA().movePlayer(2657, 2639, 0);
              }
              break;		
		case 9369:
		    if (c.getY() > 5175) {
		        FightPits.addPlayer(c);
		    } else {
		       FightPits.removePlayer(c, false);
		    }
		    break;
		 
		case 9368:
		    if (c.getY() < 5169) {
		        FightPits.removePlayer(c, false);
		    }
		    break;
		
		case 14879:
		case 881:
			OtherObjects.useDown(c, objectType);
		break;
			
		
		case 5492:
			c.getPA().movePlayer(3149, 9652, 0);
	        c.sendMessage("You go down the trapdoor.");
	        c.startAnimation(827);
			c.getPA().removeAllWindows();
		break;
		
		case 4879:
			c.getPA().movePlayer(2807, 9200, 0);
	        c.sendMessage("You go down the trapdoor.");
	        c.startAnimation(827);
			c.getPA().removeAllWindows();
		break;
		
		case 5493:
			c.getPA().movePlayer(3165, 3251, 0);
	        c.sendMessage("You climb up the ladder.");
	        c.startAnimation(828);
			c.getPA().removeAllWindows();
		break;
		
		case 4881:
			c.getPA().movePlayer(2806, 2785, 0);
	        c.sendMessage("You climb up the rope.");
	        c.startAnimation(828);
			c.getPA().removeAllWindows();
		break;
		
		case 11666:
			if(obX == 2976 && obY == 3368){	
				c.sendMessage("string");
			}
			break;
		case 3044:
		case 2781:
			c.getSmithing().sendSmelting();
			break;
				
		case 14921:
		case 9390:
		case 2785:
		case 2966:
		case 3294:
		case 3413:
		case 4304:
		case 4305:
		case 6189:
		case 6190:
		case 11009:
		case 11010:
		case 12100:
		case 12809:
		if (c.getItems().playerHasItem(2357, 1)) {
			  JewelryMaking.jewelryInterface(c);
		}
		break;
		
		case 4411:
		case 4415:
		case 4417:
		case 4418:
		case 4419:
		case 4420:
		case 4469:
		case 4470:
		case 4911:
		case 4912:
		case 1757:
		case 4437:
		case 6281:
		case 6280:
		case 4472:
		case 4471:
		case 4406:
		case 4407:
		case 4458:
		case 4902:
		case 4903:
		case 4900:
		case 4901:
		case 4461:
		case 4463:
		case 4464:
		case 4377:
		case 4378:
		case 4462:
		case 4460:
		case 4459:
		    CastleWarObjects.handleObject(c, objectType, obX, obY);    
		case 1568:
		    if (obX == 3097 && obY == 3468) {
		        c.getPA().movePlayer(3097, 9868, 0);
		    } else {
		        CastleWarObjects.handleObject(c, obY, obY, obY);
		    }
		    break;
		
		/**
		 * Start of Gates /ok go:D
		 */
		    
			case 9299:
			if (c.absY == 3191) {
				c.getPA().movePlayer(c.absX, c.absY-1, 0);
				c.getPA().gates();
			} else if (c.absY == 3190) {
				c.getPA().movePlayer(c.absX, c.absY+1, 0);
				c.getPA().gates();
			}
			break;
		
			case 1596:
			case 1597:
			case 1599:
			if (!Config.MEMBERS_AREAS) {
				c.getPA().membersAreas();
				return;
			}
			if(c.absX == 3319) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 2816) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
			} else if (c.absX == 2815) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
			} else if(c.absX == 3320) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if(c.absX == 2936) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if(c.absX == 2935) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if(c.absY == 9918) {
				c.getPA().movePlayer(c.absX, c.absY-1, 0);
				c.getPA().gates();
			} else if(c.absY == 9917) {
				c.getPA().movePlayer(c.absX, c.absY+1, 0);
				c.getPA().gates();
			}
			break;
		
		case 1558:
		case 1557:
			if (c.absX == 3040) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3041) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3112) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3111) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3045) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3044) { 
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3022) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3023) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if(c.absY == 3482) {
				c.getPA().movePlayer(c.absX, c.absY+1, 0);
				c.getPA().gates();
			} else if(c.absY == 3483) {
				c.getPA().movePlayer(c.absX, c.absY-1, 0);
				c.getPA().gates();
			}
			break;
			
		case 3198:
		case 3197:
			if (c.absX == 3312) {
				c.getPA().movePlayer(c.absX-1, c.absY, 0);
				c.getPA().gates();
			} else if (c.absX == 3311) {
				c.getPA().movePlayer(c.absX+1, c.absY, 0);
				c.getPA().gates();
			}
			break;
			
			/**
			 * End of Gates, wtf this is my code LOL lmfao?
			 */
		
		case 4031:
			Special.openShantay(c, objectType, obX, obY);
		break;
		
		case 2883:
		case 2882:
			FreeDialogues.handledialogue(c, 1023, c.npcType);
		break;
		
		case 2320:
		if (c.absY <= 9963) {
			c.getPA().movePlayer(3120, 9970, 0);
			c.startAnimation(744);
			c.turnPlayerTo(c.objectX, c.objectY);
			c.sendMessage("You swing on the monkey bars.");
		} else if(c.absY <= 9970) {
			c.getPA().movePlayer(3120, 9963, 0);
			c.startAnimation(744);
			c.turnPlayerTo(c.objectX, c.objectY);
			c.sendMessage("You swing on the monkey bars.");
		} else {
			c.sendMessage("You can't do the monkey bars here.");
		}
		break;
		
		case 1276:
			c.getWoodcutting().startWoodcutting(0, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1278:
			c.getWoodcutting().startWoodcutting(1, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1286:
			c.getWoodcutting().startWoodcutting(2, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1281:
			c.getWoodcutting().startWoodcutting(3, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1308:
			c.getWoodcutting().startWoodcutting(4, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 5552:
			c.getWoodcutting().startWoodcutting(5, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1307:
			c.getWoodcutting().startWoodcutting(6, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1309:
			c.getWoodcutting().startWoodcutting(7, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1306:
			c.getWoodcutting().startWoodcutting(8, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 5551:
			c.getWoodcutting().startWoodcutting(9, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 5553:
			c.getWoodcutting().startWoodcutting(10, c.objectX, c.objectY,
					c.clickObjectType);
			break;
		case 1316:
			c.getWoodcutting().startWoodcutting(11, c.objectX, c.objectY,
					c.clickObjectType);
			break;
			
			case 12163:
			case 12165:
				c.getWoodcutting().handleCanoe(c, c.objectId);
				break;
		
		case 4467:
			if (c.absX == 2384 && c.absY == 3134) {
				c.getPA().movePlayer(2385, 3134, 0);
			}
			if (c.absX == 2385 && c.absY == 3134) {
				c.getPA().movePlayer(2384, 3134, 0);
			}
			break;
		
		
		case 2491:
			Mining.mineEss(c, 2491, c.objectX, c.objectY);
		break;
		
		case 2670:
			Desert.cutCactus(c);
		break;
		
		// PARTY ROOM START
		case 2417: // 26193 if falador
			c.inPartyRoom = true;
			PartyRoom.open(c);
			break;

		case 2416:
			c.inPartyRoom = true;
			PartyRoom.dropAll();
			break;
		
			case 9356:
				c.getPA().enterCaves();
			break;
		
			case 9357:
				c.getPA().resetTzhaar();
             break;
			
			case 492:
			    c.getPA().movePlayer(2856, 9570, 0);
			break;
			    
			case 1764:
			    if (c.objectX == 2856 && c.objectY == 9569) {
			        c.getPA().movePlayer(2858, 3168, 0);
			    }
		    break;
		    
			case 1553:
				if (c.objectX == 3253 && c.objectY == 3267) {
					c.getPA().movePlayer(3253, 3267, 0);
		    }
		    break;
	    
		    
			case 9358:
	                c.getPA().movePlayer(2444, 5171, 0);
	        break;
	                
	        case 9359:
	              c.getPA().movePlayer(2862, 9572, 0);
	        break;
		    
			case 2610:
				  c.getPA().movePlayer(2833, 3257, 0);
			break;
			
			case 2609:
				  c.getPA().movePlayer(2834, 9657, 0);
			break;
		          
		    /**
		     * Levers
		     */
				case 1816:
					c.getPA().startTeleport2(2271, 4680, 0);
				break;
				
				case 1817:
					c.getPA().startTeleport(3067, 10253, 0, "modern");
				break;
				
				case 1814:
					c.getPA().startTeleport(3153, 3923, 0, "modern");
				break;
		
			/**
			 * Runecrafting
			 */
		
			case 2478:
			case 2479:
			case 2480:
			case 2481:
			case 2482:
			case 2483:
			case 2484:
			case 2485:
			case 2486:
			case 2487:
			case 2488:
			case 2489:
			case 2490:
				c.getRC().craftRunes(objectType);
			break;

			case 2452:
			case 2453:
			case 2454:
			case 2455:
			case 2456:
			case 2457:
			case 2458:
			case 2459:
			case 2460:
			case 2461:
			case 2462:
				c.getRC().enterAltar(objectType, 0);
			break;
			
			
			case 2472: 
			case 2474:
			case 2475:
			case 2467:
			//case 2465:
			case 2466:
			case 2470:
			case 2473:
			case 2471:
			case 2469:
			case 2468:
				c.getPA().movePlayer(3039, 4834, 0);
			break;
			
			case 7147:
	        case 7130:
	        case 7131:
	        case 7133:
	        case 7132:
	        case 7129:
	        case 7140:
	        case 7139:
	        case 7138:
	        case 7141:
	        case 7137:
	        case 7136:
	        case 7135:
	        case 7134:
	        	AbyssalHandler.handleAbyssalTeleport(c, objectType);
	        break;
			
				
	        case 2258:
	        if (c.playerLevel[20] >= 35) {
	        	c.getPA().spellTeleport(3027, 4852, 0);
	        } else {
	        	c.sendMessage("You need a Runecrafting level of 35 to enter the Abyss.");
	        }
	        break;
	        
	        /**
	         * End
	         */
			
			case 3829:
				c.getPA().movePlayer(3227, 3107, 0);
			break;
				
			case 4427:
			c.getPA().movePlayer(2373, c.absY == 3120 ? 3119 : 3120, 0);
			break;
			
			case 4428:
			c.getPA().movePlayer(2372, c.absY == 3120 ? 3119 : 3120, 0);
			break;
			
			case 4465:
			c.getPA().movePlayer( c.absX == 2414 ? 2415 : 2414, 3073, 0);
			break;
			
			case 4424:
			c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
			break;
			
			case 4423:
			c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
			break;
		
		
		
		/**
		 * Stairs
		 */
		
		case 1725:
			c.getPA().movePlayer(3230, 3394, 1);
		break;
		
		case 1726:
			c.getPA().movePlayer(3226, 3394, 0);
		break;
		
		/**
		 * End
		 */
		
		case 2079:
		if (c.getItems().playerHasItem(432, 1)) {
			c.getItems().addItem(433, 1);
			c.sendMessage("All that's in the chest is a message...");
			c.pirateTreasure = 4;
		} else {
			c.sendMessage("You need a key to open this chest.");
		}
		break;
		
		case 2071:
			c.getDH().sendStatement("You search the crate...");
			c.sendMessage("You find a bottle of rum and 10 bananas");
			c.getItems().addItem(431, 1);
			c.getItems().addItem(1957, 10);
		break;
		
		case 2593:
			c.sendMessage("Disabled for dragon slayer.");
		break;
		
			case 2024: // WP quest
			if (c.witchspot == 2) {
				c.getDH().sendStatement2(c, "You drink from the cauldron, it tastes horrible!", "You feel yourself imbued with power.");
				c.witchspot = 3;
				FreeRewards.witchFinish(c);
			} else {
				c.sendMessage("You are not on this part of the quest.");
			}
			break;	
		
			case 2145:
			if (c.restGhost == 2 && c.playerEquipment[c.playerAmulet] == 552) {
				NPCHandler.spawnNpc(c, 457, c.getX(), c.getY()+2, 0, 0, 0, 0, 0, 0, false, false);
				c.sendMessage("You open the coffin.");
			} else if (c.restGhost == 4 && c.getItems().playerHasItem(553, 1)) {
				c.getItems().deleteItem2(553, 1);
				c.sendMessage("You have freed the ghost!");
				FreeRewards.restFinish(c);
				NPCHandler.spawnNpc(c, 457, c.getX(), c.getY()+2, 0, 0, 0, 0, 0, 0, false, false);
			} else {
				c.getDH().sendStatement("Make sure you are wearing the ghost speak amulet at all times.");
				c.sendMessage("You are not on this part of the quest!");
			}
			break;
			
				case 2614:
				if (c.vampSlayer == 3) {
				NPCHandler.spawnNpc(c, 9356, c.getX(), c.getY(), 0, 0, 100, 34, 100, 100, true, true);
				} else if (c.vampSlayer == 4) {
				c.sendMessage("You have already killed the vampire.");
				} else if (c.vampSlayer < 3) {
				c.sendMessage("You still need to progress into vampire slayer to fight this monster.");
				}
				break;
			
				case 2617:
				c.getPA().movePlayer(3115, 3356, 0);
				break;

				case 2616:
				c.getPA().movePlayer(3077, 9771, 0);
				break;
		
		case 10093:
		case 10094:
		if (c.getItems().playerHasItem(1927, 1)) {
		c.turnPlayerTo(c.objectX, c.objectY);
		c.startAnimation(883);
		c.getItems().addItem(2130, 1);
		c.getItems().deleteItem2(1927, 1);
		 c.getPA().addSkillXP(18, c.playerCooking);
		} else {
		c.sendMessage("You need a bucket of milk to do this.");
		}
		break;
			
		
			case 2072: //crate
			if (c.getItems().playerHasItem(1963, 10) && c.bananas > 9) {
				c.getItems().deleteItem2(1963, 10);
				c.getDH().sendStatement("You pack your bananas in the crate...");
				c.bananas = 10;
				c.sendMessage("Talk to luthas for your reward.");
			} else if (c.getItems().playerHasItem(431, 1) && c.pirateTreasure == 1) {
				c.getItems().deleteItem2(431, 1);
				c.getDH().sendStatement("You stash your rum in the crate");
				c.pirateTreasure = 2;
			} else {
				c.getDH().sendStatement("I should talk to luthas and see what to do.");
				c.sendMessage("I think I should put some banana's in this crate.");
			}
			break;
			
			case 2073: //Banana tree
	        if (System.currentTimeMillis() - c.waitTime > 2000) {
	        if (c.lucas == true) {
	        	c.bananas += 1;
	        	c.getItems().addItem(1963, 1);
	 	        c.waitTime = System.currentTimeMillis();
	        }
	        c.getItems().addItem(1963, 1);
	        c.waitTime = System.currentTimeMillis();
	        } else {
	        	c.sendMessage("You must wait two seconds before grabbing another banana.");
	        }
	        break;
		
			case 2406:
			if (c.playerEquipment[c.playerWeapon] == 772 || c.getItems().playerHasItem(772, 1)) {
				c.getPA().startTeleport(2452, 4470, 0, "modern");
				c.sendMessage("You are teleported to zanaris.");
			} else {
				c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
				new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
				c.sendMessage("You need a dramen staff to go to zanaris.");
			}
			break;
		
			case 1755:
			if (c.objectX == 3209 && c.objectY == 9616) {
				c.getPA().movePlayer(3208, 3216, 0);
			} else if (c.objectX == 3097 && c.objectY == 9867) {
				c.getPA().movePlayer(3208, 3216, 0);
			} else if (c.objectX == 3116 && c.objectY == 9852) {
				c.getPA().movePlayer(3116, 3451, 0);
			} else if (c.objectX == 3237 && c.objectY == 9858) {
				c.getPA().movePlayer(3238, 3458, 0);
			} else if (c.objectX == 3088 && c.objectY == 9971) {
				OtherObjects.useUp(c, objectType);
			} else {
				c.sendMessage("This object is not yet working report this to an administrator.");
			}
			break;
					
		case 11338:	//Bank Booth
		case 2213:	//Bank Booth
		case 2214:	//Bank Booth
		case 3045:	//Bank Booth
		case 5276:	//Bank Booth
		case 6084:	//Bank Booth
		case 11758: //Bank Booth
		case 14367:
			c.getPA().openUpBank();
	    break;
	    
		case 2693:	//Bank Chest
		case 3193:	//Bank Chest
		case 3194:	//Bank Chest
		case 4483:	//Bank Chest
			c.getPA().sendFrame126("The Bank of 2006Redone - Deposit Box", 7421);
		    c.getPA().sendFrame248(4465, 197);
		    c.getItems().resetItems(7423);
		break;
			
		
			case 2412:
				Sailing.startTravel(c, 1);
		    break;
		    
			case 2414:
				Sailing.startTravel(c, 2);
		    break;
			
			case 2083:
				FreeDialogues.handledialogue(c, 33, c.npcType);
			break;
			
			case 2081:
				FreeDialogues.handledialogue(c, 584, c.npcType);
			break;
			
			case 14304:
				//Sailing.startTravel(c, 14);
				c.getPA().movePlayer(2659, 2676, 0);
				c.getDH().sendStatement("You arrive safely.");
			break;
			
			case 14306:
				//Sailing.startTravel(c, 15);
				c.getPA().movePlayer(3041, 3202, 0);
				c.getDH().sendStatement("You arrive safely.");
			break;
		
		case 1782:// full flour bin
			FlourMill.emptyFlourBin(c);
		break;
			
		case 2718: // Hopper
		   FlourMill.hopperControl(c);
		break;
		
		case 1739:
		case 12537:
		case 2884:
		case 1748:
		case 12965:
			Ladders.handleLadder(c);
			c.dialogueAction = 700;
		break;

		case 1738:
		case 12536:
		case 4772:
		case 12964:
		case 1747:
			Ladders.climbUp(c);
		break;

		case 1740:
		case 12538:
		case 1746:
		case 4778:
		case 12966:
			Ladders.climbDown(c);
		break;
		
		case 1805:	//Champions Guild
		if (c.questPoints >= 33) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Champions.");
		} else {
			c.sendMessage("You must have at least 33 quest points to enter this Guild!");
		}
		break;
	
	case 2641:	//Monastery (Prayer)
		if (c.playerLevel[c.playerPrayer] > 31) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);	
			c.sendMessage("You enter the guild of Monks.");
		} else {
			c.sendMessage("You must have a Prayer level of 31 to enter this Section of the Monastery!");
		}
		break;
	
	case 2624:
	case 2625:	//Heroes' Guild
		c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
		new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Heroes.");
			c.sendMessage("Heroes Quest is coming soon.");
		break;
	
	case 2391:
	case 2392:	//Legends Guild
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);	
			c.sendMessage("You enter the guild of Legends.");
			c.sendMessage("Legends Quest is coming soon.	");
		break;
		
	case 2712:	//Cooking Guild
		if (c.playerLevel[c.playerCooking] > 32 && c.getItems().playerHasItem(1949, 1)) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Cooks.");
		} else {
			c.sendMessage("You must have a Cooking level of 32 to enter this Guild!");
			c.sendMessage("You must also have a Chefs hat to enter.");
		}
		break;
		
	case 2647:	//Crafting Guild
		if (c.playerLevel[c.playerCrafting] > 40 && c.getItems().playerHasItem(1757, 1)) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Crafters.");
		} else {
			c.sendMessage("You must have a Crafting level of 40 to enter this Guild!");
			c.sendMessage("You must also have a Brown Apron.");
		}
		break;
		
	case 2113:	//Mining Guild
		if (c.playerLevel[c.playerMining] > 60) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Miners.");
		} else {
			c.sendMessage("You must have a Mining level of 60 to enter this Guild!");
		}
		break;
		
	case 2025:	//Fishing Guild
		if (c.playerLevel[c.playerFishing] > 68) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Fishermen.");
		} else {
			c.sendMessage("You must have a Fishing level of 68 to enter this Guild!");
		}
		break;
		
	case 1600:
	case 1601:	//Wizards Guild
		if (c.playerLevel[c.playerMagic] > 66) {
			c.getPA().object(-1, obX, obY, 0, 0); //Delete Door
			new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
			c.sendMessage("You enter the guild of Wizard's.");
		} else {
			c.sendMessage("You must have a Magic level of 66 to enter this Guild!");
		}
		break;
		
	case 2514:
            if (c.playerLevel[4] >= 40) {
                if (c.absX >= 2658 && c.absX <= 2660) {
                    c.getPA().walkTo(-2, 0);
                } else {
                    c.getPA().walkTo(2, 0);
                }
            } else {
                c.sendMessage("You need a ranging level of 40 to enter the Ranging Guild");
            }
            break;

		
		 	  case 8972:
		 	  if (!c.canLeaveArea) {
		 		 FreeDialogues.handledialogue(c, 3, 2458);
		      } else {
		    	  FreakyForester.leaveArea(c);
		      }
		      break;

		case 1765:
			c.getPA().movePlayer(3067, 10256, 0);
			break;
			
		case 272:
			c.getPA().movePlayer(c.absX, c.absY, 1);
			break;

		case 273:
			c.getPA().movePlayer(c.absX, c.absY, 0);
			break;
		case 245:
			c.getPA().movePlayer(c.absX, c.absY + 2, 2);
			break;
		case 246:
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
			break;
		case 1766:
			c.getPA().movePlayer(3016, 3849, 0);
			break;
		case 6552:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;
			
			case 1733:
				if (c.objectX == 2569 && c.objectY == 3122) {
					c.getPA().movePlayer(2569, 9525, 0);
				} else {
					c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
				}
			break;

			case 1734:
				if (c.objectX == 2569 && c.objectY == 9522) {
					c.getPA().movePlayer(2569, 3121, 0);
				} else {
					c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
				}
			break;

		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490,
						c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1,
							10, 8959, 15);
				}
			}
			break;

		case 10177:
			c.getPA().movePlayer(1890, 4407, 0);
			break;
		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
			break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
			break;
		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1, 0);
			else
				c.getPA().walkTo(1, 0);
			break;

		case 14235:
		case 14233:
		if (c.absX == 2670) {
			c.getPA().movePlayer(c.absX+1, c.absY, 0);
		} else if (c.absX == 2671) {
			c.getPA().movePlayer(c.absX-1, c.absY, 0);
		} else if (c.absY == 2585) {
			c.getPA().movePlayer(c.absX, c.absY-1, 0);
		} else if (c.absY == 2584) {
			c.getPA().movePlayer(c.absX, c.absY+1, 0);
		} else if(c.absX == 2643) {
			c.getPA().movePlayer(c.absX-1, c.absY, 0);
		} else if(c.absX == 2642) {
			c.getPA().movePlayer(c.absX+1, c.absY, 0);
		}
		break;

		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			// Server.objectHandler.startObelisk(objectType);
			Server.objectManager.startObelisk(objectType);
			break;

			/*
			 * Barrows Chest
			 */
		case 10284:
			 Server.getTaskScheduler().schedule(new Task(17) {
					@Override
					protected void execute() {
						c.getPA().createPlayersProjectile(c.absX, c.absY, c.absX, c.absY, 60, 60, 60, 43, 31, - c.playerId - 1, 0);
						c.handleHitMask(5);
						}
					});
			 c.shakeScreen(3, 2, 3, 2);
			if (c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers");
			}
			if (c.barrowsKillCount == 5
					&& c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if (c.barrowsNpcs[c.randomCoffin][1] == 0
					&& c.barrowsKillCount >= 5) {
				NPCHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0],
						3551, 9694 - 1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if ((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2)
					&& c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(),
						Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if (c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;
		/*
		 * Doors
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if (obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if (obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6746:
			if (obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6748:
			if (obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if (obX == 3545 && obY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if (obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if (obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if (obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if (obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		/*
		 * Cofins
		 */
		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;

		case 6823:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[0][1] == 0) {
				NPCHandler.spawnNpc(c, 2030, c.getX(), c.getY() - 1, -1,
						0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag
			c.getPA().movePlayer(3553, 3283, 0);
			break;

		case 6772:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[1][1] == 0) {
				NPCHandler.spawnNpc(c, 2029, c.getX() + 1, c.getY(), -1,
						0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[2][1] == 0) {
				NPCHandler.spawnNpc(c, 2028, c.getX(), c.getY() - 1, -1,
						0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[3][1] == 0) {
				NPCHandler.spawnNpc(c, 2027, c.getX(), c.getY() - 1, -1,
						0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[4][1] == 0) {
				NPCHandler.spawnNpc(c, 2026, c.getX(), c.getY() - 1, -1,
						0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if (Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if (c.barrowsNpcs[5][1] == 0) {
				NPCHandler.spawnNpc(c, 2025, c.getX(), c.getY() - 1, -1,
						0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
			break;

		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			break;

		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
			break;

		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397) {
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			} else if (c.objectX == 3088 && c.objectY == 3571) {
				OtherObjects.useDown(c, objectType);
			}
			break;
			
		case 409:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 2879:
			c.getPA().movePlayer(2538, 4716, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 5960:
			c.getPA().startTeleport2(3090, 3956, 0);
			break;

			case 1815:
				c.getPA().startTeleport2(3088, 3500, 0);
			break;

		case 9706:
			c.getPA().startTeleport2(3105, 3951, 0);
			break;
		case 9707:
			c.getPA().startTeleport2(3105, 3956, 0);
			break;

		case 5959:
			c.getPA().startTeleport2(2539, 4712, 0);
			break;

		case 2558:
			c.sendMessage("This door is locked.");
			break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
			break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		default:
			ScriptManager.callFunc("objectClick1_" + objectType, c, objectType,
					obX, obY);
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if(Stalls.isObject(objectType)) {
			Stalls.attemptStall(c, objectType, obX, obY);
			return;
		}
		switch (objectType) {
		
		case 2090:
	       case 2091:
	       case 3042:
	    	   Mining.prospectRock(c, "copper ore");
	           break;
	       case 2094:
	       case 2095:
	       case 3043:
	    	   Mining.prospectRock(c, "tin ore");
	           break;
	       case 2110:
	    	   Mining.prospectRock(c, "blurite ore");
	           break;
	       case 2092:
	       case 2093:
	    	   Mining.prospectRock(c, "iron ore");
	           break;
	       case 2100:
	       case 2101:
	    	   Mining.prospectRock(c, "silver ore");
	           break;
	       case 2098:
	       case 2099:
	    	   Mining.prospectRock(c, "gold ore");
	           break;
	       case 2096:
	       case 2097:
	    	   Mining.prospectRock(c, "coal");
	           break;
	       case 2102:
	       case 2103:
	    	   Mining.prospectRock(c, "mithril ore");
	           break;
	       case 2104:
	       case 2105:
	    	   Mining.prospectRock(c, "adamantite ore");
	           break;
	       case 2106:
	       case 2107:
	    	   Mining.prospectRock(c, "runite ore");
	           break;
	       case 10947:
	    	   Mining.prospectRock(c, "granite");
	           break;
	       case 10946:
	    	   Mining.prospectRock(c, "sandstone");
	           break;
	       case 2111:
	    	   Mining.prospectRock(c, "gem rocks");
	           break;
		
		case 11338:	//Bank Booth
		case 2213:	//Bank Booth
		case 2214:	//Bank Booth
		case 3045:	//Bank Booth
		case 5276:	//Bank Booth
		case 6084:	//Bank Booth
		case 11758: //Bank Booth
		c.getPA().openUpBank();
		break;
		
		case 2693:	//Bank Chest
		case 3193:	//Bank Chest
		case 3194:	//Bank Chest
		case 4483:	//Bank Chest
			c.getPA().sendFrame126("The Bank of ProjectOS - Deposit Box", 7421);
		    c.getPA().sendFrame248(4465, 197);
		    c.getItems().resetItems(7423);
		break;
			
		
		case 1161:
        case 2646:
        case 313:
        case 5585:
        case 5584:
        case 312:
		case 3366:
		Pickable.pickObject(c, c.objectId, c.objectX, c.objectY);
        break;
		
		case 2558:
			if (System.currentTimeMillis() - c.lastLockPick < 3000
					|| c.freezeTimer > 0)
				break;
			if (c.getItems().playerHasItem(1523, 1)) {
				c.lastLockPick = System.currentTimeMillis();
				if (Misc.random(10) <= 3) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (c.objectX == 3044 && c.objectY == 3956) {
					if (c.absX == 3045) {
						c.getPA().walkTo2(-1, 0);
					} else if (c.absX == 3044) {
						c.getPA().walkTo2(1, 0);
					}

				} else if (c.objectX == 3038 && c.objectY == 3956) {
					if (c.absX == 3037) {
						c.getPA().walkTo2(1, 0);
					} else if (c.absX == 3038) {
						c.getPA().walkTo2(-1, 0);
					}
				} else if (c.objectX == 3041 && c.objectY == 3959) {
					if (c.absY == 3960) {
						c.getPA().walkTo2(0, -1);
					} else if (c.absY == 3959) {
						c.getPA().walkTo2(0, 1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
			
		default:
			ScriptManager.callFunc("objectClick2_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		if(Stalls.isObject(objectType)) {
			Stalls.attemptStall(c, objectType, obX, obY);
			return;
		}
		switch (objectType) {
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, c, objectType,
					obX, obY);
			break;
		}
	}
}