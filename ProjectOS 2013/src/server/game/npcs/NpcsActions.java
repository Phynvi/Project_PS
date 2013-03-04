package server.game.npcs;

import server.game.content.skills.core.Fishing;
import server.game.content.skills.crafting.Tanning;
import server.game.content.skills.thieving.Pickpocket;
import server.game.content.traveling.Sailing;
import server.game.dialogues.FreeDialogues;
import server.game.dialogues.MemberDialogues;
import server.game.players.Client;
import server.util.Misc;
import server.util.ScriptManager;

public class NpcsActions {
	
	private Client c;

	public NpcsActions(Client Client) {
		this.c = Client;
	}
	
	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		Shops.openShop(c, npcType);
		if(Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
	     if (Fishing.fishingNPC(npcType)) {
	                Fishing.fishingNPC(c, 1, npcType);
	                return;
	     }
		switch (npcType) {
		case 3000:
			   c.getShops().openShop(113);//3000
			   break;
		
		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
			FreeDialogues.handledialogue(c, 2017, npcType);
		break;
		
		 case 170:
	         FreeDialogues.handledialogue(c, 591, npcType);
	     break;
		
		 case 836:
         	FreeDialogues.handledialogue(c, 1018, npcType);
         break;
         
         case 2258:
             c.getShops().openShop(55);
         break;
         
         case 519:
         	FreeDialogues.handledialogue(c, 15, npcType); //barrows open shop
         break;
			
		case 657:
			Sailing.startTravel(c, 1);
		break;
		
		case 658:
			Sailing.startTravel(c, 2);
		break;
		
		case 2728 :
		case 2729 :
			FreeDialogues.handledialogue(c, 1011, npcType);
		break;
		
		case 3506:
		case 3507:
		case 761:
		case 760:
		case 762:
		case 763:
		case 764:
		case 765:
		case 766:
		case 767:
		case 768:
		case 769:
		case 770:
		case 771:
		case 772:
		case 773:
		case 3505:
			c.getSummon().pickUpClean(c, c.summonId);
			c.hasNpc = false;
			c.summonId = 0;
		break;
		
		case 804:
			Tanning.sendTanningInterface(c);
		break;
		
		//case 657:
			//Sailing.startTravel(c, 1);
		//break;
		
		case 606:
			Sailing.startTravel(c, 14);
		break;
		
		case 376:
		case 377:
		case 378:
			if (c.getItems().playerHasItem(995, 30)) {
				FreeDialogues.handledialogue(c, 33, npcType);
			} else {
				c.getDH().sendStatement("You need 30 coins to travel on this ship.");
			}
			break;
		
			
			/**
			 * Start of Quests
			 */
			
			case 557:
			if (c.ptjob == 0) {
				FreeDialogues.handledialogue(c, 37, npcType);
			} else if (c.ptjob == 1) {
				FreeDialogues.handledialogue(c, 47, npcType);
			} else if (c.ptjob == 2) {
				FreeDialogues.handledialogue(c, 1000, npcType);
			}
			break;
		
			case 375:
			if (c.pirateTreasure == 0) {
				FreeDialogues.handledialogue(c, 533, npcType);
			} else if (c.pirateTreasure == 1) {
				c.sendMessage("Continue on with the quest, then come back and see me.");
			} else if (c.pirateTreasure == 2) {
				FreeDialogues.handledialogue(c, 569, npcType);
			} else if (c.pirateTreasure == 3) {
				FreeDialogues.handledialogue(c, 580, npcType);
			} else {
				c.sendMessage("Arr! Thanks for me helping me.");
			}
			break;
		
			case 307:
			if (c.witchspot == 0) {
				FreeDialogues.handledialogue(c, 532, npcType);
			} else if (c.witchspot == 1) {
				FreeDialogues.handledialogue(c, 546, npcType);
			} else if (c.witchspot == 2) {
				FreeDialogues.handledialogue(c, 548, npcType);
			} else if (c.witchspot == 3) {
				c.getDH().sendNpcChat1("Welcome back, thank you again for helping me.", c.talkingNpc, "Hetty");
			}
			break;
		
			case 755:
			if (c.vampSlayer == 3) {
				FreeDialogues.handledialogue(c, 531, npcType);
			} else if(c.vampSlayer == 4) {
				FreeDialogues.handledialogue(c, 529, npcType);
			} else if (c.vampSlayer == 0) {
				FreeDialogues.handledialogue(c, 476, npcType);
			}
			break;
			
			case 743:
			if (c.vampSlayer == 0) {
				FreeDialogues.handledialogue(c, 476, npcType);
			} else if (c.vampSlayer == 1) {
				c.getDH().sendStatement("I should go find harlow.");
			}
			break;
			
			case 756:
			if (c.vampSlayer == 2) {
				FreeDialogues.handledialogue(c, 510, npcType);
			} else {
				c.getDH().sendStatement("I should continue with my task.");
			}
			break;
			
			case 456:
				FreeDialogues.handledialogue(c, 338, 456);
			break;
		
			case 457:
			if (c.restGhost == 2) {
				FreeDialogues.handledialogue(c, 371, npcType);
			}
			break;
			
			case 458:
			if (c.restGhost == 1) {
				FreeDialogues.handledialogue(c, 352, npcType);
			}
			break;
		
			  case 759:
			  if (c.getItems().playerHasItem(1927, 1) && c.gertCat == 2) {
				  MemberDialogues.handledialogue(c, 319, npcType);
				  c.getItems().deleteItem2(1552, 1);
				  c.gertCat = 3;
			  } else if (c.getItems().playerHasItem(1952, 1) && c.gertCat == 3) {
				  MemberDialogues.handledialogue(c, 323, npcType);
				  c.getItems().deleteItem(1952, 1);
				  c.gertCat = 4;
			  } else if (c.gertCat == 4) {
				  c.getDH().sendStatement("Hiss!");
				  MemberDialogues.handledialogue(c, 325, npcType);
				  c.gertCat = 5;
			  } else if (c.getItems().playerHasItem(1554, 1) && c.gertCat == 5) {
				  c.getItems().deleteItem2(1554, 1);
				  MemberDialogues.handledialogue(c, 326, npcType);
			  } else {
				  c.sendMessage("Hiss!");
				  c.getDH().sendStatement("Fluffs hisses but clearly wants something - maybe she is thirsty?");
			  }
			  break;
			  
			  case 780:
			  if (c.playerLevel[10] < 4) {//requires mems but disabled because that's not the type of game this is :P
				  c.getDH().sendStatement("You don't have the requirements to do this quest.");
				  return;
			  }
			  if (c.gertCat == 0) {
				  MemberDialogues.handledialogue(c, 269, npcType);
			  } else if (c.gertCat == 1) {
				  MemberDialogues.handledialogue(c, 276, npcType);
			  } else if (c.gertCat == 6) {
				  MemberDialogues.handledialogue(c, 328, npcType);
			  } else {
				  c.getDH().sendStatement("She has nothing to say to you.");
			  }
			  break;
			  
			  case 783:
			  if (c.gertCat == 1) {
				  MemberDialogues.handledialogue(c, 286, npcType);
			  } else if (c.gertCat == 2) {
				  MemberDialogues.handledialogue(c, 314, npcType);
			  }
			  break;	
		
			case 639:
	            if (c.romeojuliet == 0) {
	            	FreeDialogues.handledialogue(c, 389, npcType);
	            } else if (c.romeojuliet == 1) {
	            	FreeDialogues.handledialogue(c, 408, npcType);
	            } else if (c.romeojuliet == 3) {
	            	FreeDialogues.handledialogue(c, 415, npcType);
	            } else if (c.romeojuliet == 4) {
	            	FreeDialogues.handledialogue(c, 424, npcType);
	            } else if (c.romeojuliet == 5) {
	            	FreeDialogues.handledialogue(c, 431, npcType);
	            } else if (c.romeojuliet == 6) {
	            	FreeDialogues.handledialogue(c, 443, npcType);
	            } else if (c.romeojuliet == 8) {
	            	FreeDialogues.handledialogue(c, 469, npcType);
	            } else if (c.romeojuliet == 9) {
	            	FreeDialogues.handledialogue(c, 475, npcType);
	            }
	            if (c.romeojuliet == 2 && c.getItems().playerHasItem(755, 1)) {
	            	FreeDialogues.handledialogue(c, 415, npcType);
	            }
	            if (c.romeojuliet == 2 && !c.getItems().playerHasItem(755, 1)) {
	            	FreeDialogues.handledialogue(c, 421, npcType);
	            }
	            break;
				
			 case 276:
	                if (c.romeojuliet == 5) {
	                	FreeDialogues.handledialogue(c, 432, npcType);
	                }
	                if (c.romeojuliet == 6 && c.getItems().playerHasItem(300, 1) && c.getItems().playerHasItem(227, 1) && c.getItems().playerHasItem(526, 1)) {
	                	FreeDialogues.handledialogue(c, 448, npcType);
	                } else {
	                    if (c.romeojuliet == 6) {
	                    	FreeDialogues.handledialogue(c, 439, npcType);
	                    }
	                }
	                break;
					
	            case 637:
	                if (c.romeojuliet == 0) {
	                	FreeDialogues.handledialogue(c, 409, npcType);
	                } else if (c.romeojuliet == 1) {
	                	FreeDialogues.handledialogue(c, 410, npcType);
	                } else if (c.romeojuliet == 2) {
	                	FreeDialogues.handledialogue(c, 414, npcType);
	                } else if (c.romeojuliet == 7) {
	                	FreeDialogues.handledialogue(c, 457, npcType);
	                } else if (c.romeojuliet == 8) {
	                	FreeDialogues.handledialogue(c, 468, npcType);
	                }
	                break;
	                
	        case 741:
	        	FreeDialogues.handledialogue(c, 190, npcType);
	        break;
		
			case 553:
			if (c.runeMist == 2) {
				FreeDialogues.handledialogue(c, 229, npcType);
			} else if (c.runeMist == 3) {
				FreeDialogues.handledialogue(c, 237, npcType);
			}
			break;
		
			case 300:
			if (c.runeMist == 1) {
				FreeDialogues.handledialogue(c, 201, npcType);
			} else if (c.runeMist == 2) {
				FreeDialogues.handledialogue(c, 213, npcType);
			} else if (c.runeMist == 3) {
				FreeDialogues.handledialogue(c, 238, npcType);
			}
			break;
		
			case 284:
			if(c.playerLevel[14] < 14) {
				c.getDH().sendStatement("You don't have the requirements to do this quest.");
				return;
			}
			if (c.doricQuest == 0) {
				FreeDialogues.handledialogue(c, 89, npcType);
			} else if (c.doricQuest == 1) {
				FreeDialogues.handledialogue(c, 84, npcType);
			} else if (c.doricQuest == 2) {
				FreeDialogues.handledialogue(c, 86, npcType);
			} else if (c.doricQuest == 3) {
				FreeDialogues.handledialogue(c, 100, npcType);
			}
			break;
		
			case 706:
			if(c.impsC == 0) {
				FreeDialogues.handledialogue(c, 145, npcType);
			} else if(c.impsC == 1) {
				FreeDialogues.handledialogue(c, 156, npcType);
			}
			if(c.impsC == 1 && c.getItems().playerHasItem(1470, 1) && c.getItems().playerHasItem(1472, 1) && c.getItems().playerHasItem(1474, 1) && c.getItems().playerHasItem(1476, 1)) {
				FreeDialogues.handledialogue(c, 158, npcType);
			}
			break;
			
			case 278:
			if(c.cookAss == 0) {
				FreeDialogues.handledialogue(c, 50, npcType);
			} else if(c.cookAss == 1) {
				FreeDialogues.handledialogue(c, 67, npcType);
			} else if(c.cookAss == 2) {
				FreeDialogues.handledialogue(c, 69, npcType);
			} else if(c.cookAss == 3) {
				FreeDialogues.handledialogue(c, 76, npcType);
			}
			break;
			
			 case 758:
			 if (c.sheepShear == 0) {
				 FreeDialogues.handledialogue(c, 164, npcType);
			 } else if (c.sheepShear == 1) {
				 FreeDialogues.handledialogue(c, 185, 1);
			 } else {
				 c.sendMessage("He has nothing to say to you.");
			 }
			 break;
			 
			case 379:
			if (c.bananas == 0) {
				FreeDialogues.handledialogue(c, 8, npcType);
			} else if (c.bananas == 1) {
				FreeDialogues.handledialogue(c, 9, npcType);	
			} else if (c.bananas == 2) {
				FreeDialogues.handledialogue(c, 10, npcType);	
			}
			break;
			
			/**
			 * End of Quests
			 */
		
			case 2294:
				FreeDialogues.handledialogue(c, 24, 2294);
			break;
			
			case 2296:
				FreeDialogues.handledialogue(c, 26, 2296);
			break;
		
			case 8689:
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
			if(c.getItems().playerHasItem(1925, 1)) {
				c.turnPlayerTo(c.objectX, c.objectY);
				c.startAnimation(2292);
				c.getItems().addItem(1927 ,1);
				c.getItems().deleteItem2(1925, 1);
				c.buryDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("You need a bucket to milk a cow!");
				}
			}
			break;
		
		case 500:
			FreeDialogues.handledialogue(c, 851, npcType);
		break;
		
		case 659:
			FreeDialogues.handledialogue(c, 18, npcType);
		break;
		
		case 2244:
			FreeDialogues.handledialogue(c, 14, npcType);
		break;
		
		case 641:
			FreeDialogues.handledialogue(c, 11, npcType);
		break;
		
		case 2458:
			FreeDialogues.handledialogue(c, 2, npcType);
	    break;
	    
		 case 731:
			 FreeDialogues.handledialogue(c, 19, npcType);
		 break;
		
		case 953:	//Banker
		case 2574:	//Banker
		case 166:	//Gnome Banker
		case 1702:	//Ghost Banker
		case 494:	//Banker
		case 495:	//Banker
		case 496:	//Banker
		case 497:	//Banker
		case 498:	//Banker
		case 499:	//Banker
		case 567: 	//Banker
		case 1036:	//Banker
		case 1360:	//Banker
		case 2163:	//Banker
		case 2164:	//Banker
		case 2354:	//Banker
		case 2355:	//Banker
		case 2568:	//Banker
		case 2569:	//Banker
		case 2570:	//Banker
			FreeDialogues.handledialogue(c, 1013, npcType);
		break;

		case 1152:
			FreeDialogues.handledialogue(c, 16, npcType);
		break;

		case 3789:
			c.sendMessage((new StringBuilder()).append("You currently have ")
					.append(c.pcPoints).append(" pest control points.")
					.toString());
		break;

		case 3788:
			c.getShops().openVoid();
		break;

		case 905:
			FreeDialogues.handledialogue(c, 5, npcType);
		break;

		case 460:
			FreeDialogues.handledialogue(c, 3, npcType);
		break;

		case 462:
			FreeDialogues.handledialogue(c, 7, npcType);		
		break;
		
		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
		break;
		}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		Shops.openShop2(c, npcType);
		if(Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
		  if (Fishing.fishingNPC(npcType)) {
              Fishing.fishingNPC(c, 2, npcType);
              return;
          }
		switch (npcType) {
		
		   case 2258:
               c.getShops().openShop(55);
           break;
           
		   case 3000:
			   c.getShops().openShop(113);//3000
			   break;
			   
           case 519:
        	   FreeDialogues.handledialogue(c, 15, npcType); //barrows fix barrows
           break;
		
		/* Bankers */
		case 953:	//Banker
		case 2574:	//Banker
		case 166:	//Gnome Banker
		case 1702:	//Ghost Banker
		case 494:	//Banker
		case 495:	//Banker
		case 496:	//Banker
		case 497:	//Banker
		case 498:	//Banker
		case 499:	//Banker
		case 567: 	//Banker
		case 1036:	//Banker
		case 1360:	//Banker
		case 2163:	//Banker
		case 2164:	//Banker
		case 2354:	//Banker
		case 2355:	//Banker
		case 2568:	//Banker
		case 2569:	//Banker
		case 2570:	//Banker
			c.getPA().openUpBank();
		break;
	}
}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		Shops.openShop3(c, npcType);
		if(Pickpocket.isNPC(c, npcType)) {
			Pickpocket.attemptPickpocket(c, npcType);
			return;
		}
		switch (npcType) {
		case 836:
		if (c.getItems().playerHasItem(995, 5)) {
			c.sendMessage("You buy a shantay pass quickly.");
			c.getItems().deleteItem2(995, 5);
			c.getItems().addItem(1854, 1);
		} else {
			c.sendMessage("You need 5 coins to buy a pass.");
		}
		break;
		default:
			ScriptManager.callFunc("npcClick3_" + npcType, c, npcType);
			if (c.playerRights == 3)
				Misc.println("Third Click NPC : " + npcType);
			break;

		}
	}

}
