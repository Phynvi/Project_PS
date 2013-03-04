package server.game.players.packets;


import server.Config;
import server.Connection;
import server.game.content.random.HolidayDrops;
import server.game.content.random.Holidays;
import server.game.content.tutorial.TutorialIsland;
import server.game.dialogues.FreeDialogues;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;

/**
 * Commands
 * @author Andrew
 **/
 
public class Commands implements PacketType 
{
	
    @Override
    public void processPacket(Client c, int packetType, int packetSize)	{
    String playerCommand = c.getInStream().readString();
    if (Config.SERVER_DEBUG)
        Misc.println(c.playerName+" playerCommand: "+playerCommand);
    if ((playerCommand.startsWith("ban") || playerCommand.startsWith("ip") || playerCommand.startsWith("mute") || playerCommand.startsWith("un")) && c.playerRights > 0 && c.playerRights != 4) {
        c.getLogs().writeCommandLog(playerCommand);
    }
    
    if (c.playerRights >= 0)
        playerCommands(c, playerCommand);
    if (c.playerRights >= 1)
        PlayerModCommands(c, playerCommand);
    if (c.playerRights == 3)
        developerCommands(c, playerCommand);
    if (c.playerRights == 4 || c.playerRights > 1)
        MemberCommands(c, playerCommand);  
    if (c.playerRights > 1)
       testingCommands(c, playerCommand);  
    }
    
    public void testingCommands(Client c, String playerCommand)
    {
    	
   	if (playerCommand.equalsIgnoreCase("hdrop")) {
    		for (final HolidayDrops d : HolidayDrops.values()) {
    		for (int j = 0; j < PlayerHandler.players.length; j++)
            if (PlayerHandler.players[j] != null) {
            Client p1 = (Client)PlayerHandler.players[j];
            if (d.getHoliday()) {
            p1.sendMessage("The " + d.getName() + " event has started.");
    	    Holidays.dropItems(c);
           }
        }
    }
 }
    	


	   if (playerCommand.startsWith("ipmute")) {
        try {   
           String playerToBan = playerCommand.substring(7);
           for (int i = 0; i < PlayerHandler.players.length; i++) {
              if(PlayerHandler.players[i] != null) {
                 if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                    Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
                    c.sendMessage("You have IP Muted the user: "+PlayerHandler.players[i].playerName);
                    Client c2 = (Client)PlayerHandler.players[i];
                    c2.sendMessage("You have been muted by: " + c.playerName);
                    break;
                 }
              }
           }
        } catch(Exception e) {
           c.sendMessage("Player Must Be Offline.");
        }         
     }
	
	if (playerCommand.startsWith("dialogue")) {
		int npcType = 1552;
		int id = Integer.parseInt(playerCommand.split(" ")[1]);
		FreeDialogues.handledialogue(c, id, npcType);
	}
	
	
	
	if (playerCommand.startsWith("interface")) {
		String[] args = playerCommand.split(" ");
		c.getPA().showInterface(Integer.parseInt(args[1]));
	}
	if (playerCommand.startsWith("gfx")) {
		String[] args = playerCommand.split(" ");
		c.gfx0(Integer.parseInt(args[1]));
	}
	if (playerCommand.startsWith("anim")) {
		String[] args = playerCommand.split(" ");
		c.startAnimation(Integer.parseInt(args[1]));
		c.getPA().requestUpdates();
	}

	if (playerCommand.equalsIgnoreCase("mypos")) {
		c.sendMessage("X: " + c.absX);
		c.sendMessage("Y: " + c.absY);
		c.sendMessage("H: " + c.heightLevel);
	}


	if (playerCommand.startsWith("unipmute")) {

		try {
			String playerToBan = playerCommand.substring(9);
			  for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerToBan)) {
						Connection
								.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
						c.sendMessage("You have Un Ip-Muted the user: "
								+ PlayerHandler.players[i].playerName);
						break;
					}
				}
			}
		} catch (Exception e) {
			c.sendMessage("Player Must Be Offline.");
		}
	}
    	
    	if (playerCommand.startsWith("bank")) {
    		c.getPA().openUpBank();
    	}
    	
    	if (playerCommand.startsWith("xteleto")) {
			String name = playerCommand.substring(8);
			  for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						c.getPA().movePlayer(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), PlayerHandler.players[i].heightLevel);
					}
				}
			}			
		}
    	
        if (playerCommand.equalsIgnoreCase("afk"))
        {
            if (c.inWild())
            {
                c.sendMessage("You cannot use this in the wilderness11.");
                return;
            }
            c.forcedText = "I'm AFK, don't talk to me";
            c.forcedChatUpdateRequired = true;
            c.updateRequired = true; 
            c.startAnimation(1353);
            c.updateRequired = true;
            c.appearanceUpdateRequired = true;
            c.sendMessage("You now relax");
            c.sendMessage("Type in ::afkoff to undo it");
        }

        if (playerCommand.equalsIgnoreCase("afkoff"))
        {
            if (c.inWild())
            {
                c.sendMessage("You cannot use this in the wilderness12.");
                return;
            }
            c.forcedText = "I'm AFK, don't talk to me";
            c.forcedChatUpdateRequired = true;
            c.updateRequired = true; 
            c.startAnimation(6);
            c.updateRequired = true;
            c.appearanceUpdateRequired = true;
            c.sendMessage("You wake up.");
        }

	 if (playerCommand.startsWith("tele")) {
            String[] arg = playerCommand.split(" ");
            if (arg.length > 3)
                c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
            else if (arg.length == 3)
                c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), c.heightLevel);
        }
	 
	 if (playerCommand.startsWith("xteletome")) {
			String name = playerCommand.substring(10);
			  for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						Client other = (Client) PlayerHandler.players[i];
						other.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
					}
				}
			}			
		}
    	
    if (playerCommand.equalsIgnoreCase("random")) {
  	    	c.sendMessage("You currently have " + c.randomActions + " random points.");
  	  }
  	  
  	  if (playerCommand.equalsIgnoreCase("addrandom")) {
	    	c.randomActions = 750;
	    	c.sendMessage("You currently have " + c.randomActions + " random points.");
  	  }
    	
    	if (playerCommand.equalsIgnoreCase("restarttut")) {
			TutorialIsland.restartTutorial(c);
		}
    	
    	if (playerCommand.equalsIgnoreCase("commands") && c.playerRights > 1) {
    		c.sendMessage("::restarttut, ::spellbook, ::commands, ::item,");
    		c.sendMessage("::master, ::reportbug, ::mute, ::ipmute");
    		c.sendMessage("::ban, ::ipban, ::bank, ::players,");
    		c.sendMessage("::xteleto, ::tele, ::xteletome,");
    		c.sendMessage("::mypos, ::gfx, ::anim, ::addrandom, ::random, ::commands,");
    		c.sendMessage("::interface, ::dialogue.");
    	}
    	
    	if (playerCommand.equalsIgnoreCase("spellbook")) {
			if (c.inWild())
				return;
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else if(c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
		}
	
		if (playerCommand.startsWith("item") && c.playerRights >= 3) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 25000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
						c.sendMessage("You spawn (" + newItemAmount +") of item ("+ newItemID+").");
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200");
				}
			} catch (Exception e) {
		}
	}
		
		if (playerCommand.equalsIgnoreCase("master") && c.playerRights >= 3) { //now check this
				for (int i = 0; i < 25; i++) {
					c.playerLevel[i] = 99;
					c.playerXP[i] = c.getPA().getXPForLevel(100);
					c.getPA().refreshSkill(i);	
				}
				c.getPA().requestUpdates();
			}
    }
    
    
    public void playerCommands(Client c, String playerCommand)
    {	
    	
    	if (playerCommand.startsWith("reportbug")) {
    		c.sendMessage("Report to an Mod+");
    	}
	
	if (playerCommand.equalsIgnoreCase("players")) {
			c.sendMessage("There are currently "
					+ PlayerHandler.getPlayerCount() + " players online.");
		}
		
	/*if (playerCommand.equalsIgnoreCase("master")) {
		for (int i = 0; i < 25; i++) {
			c.playerLevel[i] = 99;
			c.playerXP[i] = c.getPA().getXPForLevel(100);
			c.getPA().refreshSkill(i);	
		}
		c.getPA().requestUpdates();
	}
	
	if (playerCommand.startsWith("item")) {
		try {
			String[] args = playerCommand.split(" ");
			if (args.length == 3) {
				int newItemID = Integer.parseInt(args[1]);
				int newItemAmount = Integer.parseInt(args[2]);
				if ((newItemID <= 20000) && (newItemID >= 0)) {
					c.getItems().addItem(newItemID, newItemAmount);
					c.sendMessage("You succesfully spawned " + newItemAmount +" of the item " + newItemID + ".");
					//System.out.println("Spawned: " + newItemID + " by: " + Misc.capitalize(c.playerName));
				} else {
					c.sendMessage("Could not complete spawn request.");
				}
			} else {
				c.sendMessage("Use as ::item 4151 1");
			}
		} catch (Exception e) {
		
		}
	}
	
	if (playerCommand.equals("betazone")) {
		c.getPA().startTeleport(2525, 4776, 0, "modern");
		 c.sendMessage("This will be disabled after the beta!");
	}
*/
	
	if (playerCommand.startsWith("noclip") && c.playerRights < 2) {
		return;			
	}
			
		if (playerCommand.startsWith("changepassword")) {
			c.sendMessage("Disabled for a while");
		}
		
		if (playerCommand.startsWith("commands")) {
			c.getDH().sendStatement("The following are the player commands...");
			c.sendMessage("::players, ::reportbug, ::changepassword, ::memberscommands (if member).");
		}if (playerCommand.startsWith("yell")) {
			/*
			*This is the sensor for the yell command
			*/
			String text = playerCommand.substring(5);
			String[] bad = {"chalreq", "duelreq", "tradereq", ". com", "com", 
					"org", "net", "biz", ". net", ". org", ". biz", 
					". no-ip", "- ip", ".no-ip.biz", "no-ip.org", "servegame",
					".com", ".net", ".org", "no-ip", "****", "is gay", "****",
					"crap", "rubbish", ". com", ". serve", ". no-ip", ". net", ". biz"};
			for(int i = 0; i < bad.length; i++){
				if(text.indexOf(bad[i]) >= 0){
					return;
				}
			}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client)PlayerHandler.players[j];
				
					
					if (c.ismemb == 1){
						c2.sendMessage("[Member]"+ Misc.optimizeText(c.playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
					}else if (c.playerName.equalsIgnoreCase("")) {
						c2.sendMessage("[Moderator]"+ Misc.optimizeText(c.playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
					}else if (c.playerName.equalsIgnoreCase("")) {
						c2.sendMessage("[Administator]"+ Misc.optimizeText(c.playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
					}else if (c.playerName.equalsIgnoreCase("Mr Hooligan")) {
						c2.sendMessage("[Owner]"+ Misc.optimizeText(c.playerName) +": "
										+ Misc.optimizeText(playerCommand.substring(5)) +"");
					}else if (c.ismemb == 0){
						c.sendMessage("You must be a member to use this command!");
					}
			}
		}
		}
    }
	
				
    public void PlayerModCommands(Client c, String playerCommand){
	if (c.playerRights >= 1) {
		
		
		if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') { // use as ::ban name
			try {	
				String playerToKick = playerCommand.substring(5);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToKick)) {
							PlayerHandler.players[i].disconnected = true;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("mute")) {
			String playerToBan = playerCommand.substring(5);
			Connection.addNameToMuteList(playerToBan);
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(PlayerHandler.players[i] != null) {
					if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
						Client c2 = (Client)PlayerHandler.players[i];
						c.sendMessage("You have Muted the user: "+PlayerHandler.players[i].playerName);
						c2.sendMessage("You have been muted by: " + c.playerName);
						break;
					} 
				}
			}			
		}
		if (playerCommand.startsWith("unmute")) {
			try {
				String playerToBan = playerCommand.substring(7);
				Connection.unMuteUser(playerToBan);
				c.sendMessage("Unmuted.");
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("ipmute")) {
			try {	
				String playerToBan = playerCommand.substring(7);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToMuteList(PlayerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP Muted the user: "+PlayerHandler.players[i].playerName);
							Client c2 = (Client)PlayerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player is offline.");
			}		
		}
		if (playerCommand.startsWith("unipmute")) {
			try {	
				String playerToBan = playerCommand.substring(9);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.unIPMuteUser(PlayerHandler.players[i].connectedFrom);
							c.sendMessage("You have Un Ip-Muted the user: "+PlayerHandler.players[i].playerName);
							break;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player is offline.");
			}				
		}
		if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') { // use as ::ban name
			try {	
				String playerToBan = playerCommand.substring(4);
				Connection.addNameToBanList(playerToBan);
				Connection.addNameToFile(playerToBan);
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(PlayerHandler.players[i] != null) {
						if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							PlayerHandler.players[i].disconnected = true;
						} 
					}
				}
			} catch(Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}	
		if (playerCommand.startsWith("unban")) {
			String playerToBan = playerCommand.substring(6);
			Connection.removeNameFromBanList(playerToBan);
			c.sendMessage(playerToBan + " has been unbanned.");
		}
		if (playerCommand.startsWith("staff")) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
					if(PlayerHandler.players[j].playerRights >= 1 && PlayerHandler.players[j].playerRights != 4) {
						c2.sendMessage("@whi@[STAFF CHAT]" + c.playerName + ": " + Misc.optimizeText(playerCommand.substring(6)));
					}
				}
			}
		}
	}
}

  
    public void developerCommands(Client c, String playerCommand)
    {
    	
    	if (playerCommand.startsWith("membership")) {
    		if (c.membership = true) {
    			c.membership = false;
    			c.sendMessage("Your current membership status is " + c.membership + ".");
    		} else if(c.membership = false) {
    			c.membership = true;
    			c.sendMessage("Your current membership status is " + c.membership + ".");
    		}
    	}
    	
    	if (playerCommand.startsWith("rank")) {
    		   
    	    try {   
    	        String[] args = playerCommand.split("-");
    	            if(args.length < 2) {
    	                c.sendMessage("Correct usage: ::rank-name-rank#");
    	                        return;
    	                        }
    	            String playerToStaff = args[1];
    	            int staffRank = Integer.parseInt(args[2]);
    	              
    	            for (int i = 0; i < PlayerHandler.players.length; i++) {
    		            if(PlayerHandler.players[i] != null) {
    	                if(PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToStaff)) {
    	                    Client c2 = (Client) PlayerHandler.players[i];
    	                        c2.playerRights = staffRank;
    	                        c2.logout();
    	                    break;
    	                } 
    	            }
    	        }
    	                                                                                                              
    	    } catch(Exception e) {
    	        c.sendMessage("Player Must Be Offline.");
    	    }           
    	}
			
			if (playerCommand.equals("spec")) {
					c.specAmount = 100.0;
			}
	
		if (playerCommand.startsWith("setlevel")) {
			try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				if (level > 99) {
					level = 99;
				} else if (level < 0) {
					level = 1;
				}
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(
						c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
				c.getPA().levelUp(skill);
			} catch (Exception e) {
			}
		}

		if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}
	
	if(playerCommand.startsWith("npc")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if(newNPC > 0) {
						NPCHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}
	
		
	}

    public void MemberCommands(Client c, String playerCommand)
    {
    	
    	if (playerCommand.equalsIgnoreCase("memberCommands")) {
    		c.getDH().sendStatement("The following are the member commands...");
    		c.sendMessage("Resetting stats are for 10 and under.");
    		c.sendMessage("::resetdef, ::resetatt, ::resetstr, ::resethp, ::resetmage");
    		c.sendMessage("::resetrange, ::resetpray, ::players");
    	}
    		
    		if (playerCommand.equalsIgnoreCase("resetatt")) {
       		if (c.inWild()) {
                c.sendMessage("You are not aloud to reset stats while in Wild!");
                return;
       		}
       		for (int j = 0; j < c.playerEquipment.length; j++) {
       		if (c.playerEquipment[j] > 0) {
                   c.sendMessage("Take off your items first!");
               return;
       		}
       		if (c.playerLevel[0] > 10) {
       			c.sendMessage("You can't reset stats that are over 10!");
       			return;
       		} else {
       		c.playerXP[0] = c.getPA().getXPForLevel(0);
            c.playerLevel[0] = c.getPA().getLevelForXP(c.playerXP[0]);
            c.getPA().refreshSkill(0);
       	}
      }
    }
    		
    		if (playerCommand.equalsIgnoreCase("resetdef")) {
       		if (c.inWild()) {
                 c.sendMessage("You are not aloud to reset stats while in Wild!");
                 return;
            }
       		for (int j = 0; j < c.playerEquipment.length; j++) {
       		if (c.playerEquipment[j] > 0) {
                c.sendMessage("Take off your items first!");
                return;
       		}
       		if (c.playerLevel[1] > 10) {
       			c.sendMessage("You can't reset stats that are over 10!");
       			return;
       		} else {
       		c.playerXP[1] = c.getPA().getXPForLevel(1);
            c.playerLevel[1] = c.getPA().getLevelForXP(c.playerXP[1]);
            c.getPA().refreshSkill(1);
       	}
      }
    }
    		
    		if (playerCommand.equalsIgnoreCase("resetstr")) {
           		if (c.inWild()) {
                    c.sendMessage("You are not aloud to reset stats while in Wild!");
                    return;
           		}
           		for (int j = 0; j < c.playerEquipment.length; j++) {
           		if (c.playerEquipment[j] > 0) {
                   c.sendMessage("Take off your items first!");
                   return;
           		}
           		if (c.playerLevel[2] > 10) {
           			c.sendMessage("You can't reset stats that are over 10!");
           			return;
           		} else {
           		c.playerXP[2] = c.getPA().getXPForLevel(2);
                c.playerLevel[2] = c.getPA().getLevelForXP(c.playerXP[2]);
                c.getPA().refreshSkill(2);
           	}
          }
       }
    		
    		if (playerCommand.equalsIgnoreCase("resethp")) {
           		if (c.inWild()) {
                    c.sendMessage("You are not aloud to reset stats while in Wild!");
                    return;
           		}
           		for (int j = 0; j < c.playerEquipment.length; j++) {
           		if (c.playerEquipment[j] > 0) {
                   c.sendMessage("Take off your items first!");
                   return;
           		}
           		if (c.playerLevel[3] > 10) {
           			c.sendMessage("You can't reset stats that are over 10!");
           			return;
           		} else {
           		c.playerXP[3] = c.getPA().getXPForLevel(3);
                c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
                c.getPA().refreshSkill(3);
           	}
          }
    	}
    		
    		if (playerCommand.equalsIgnoreCase("resetrange")) {
           		if (c.inWild()) {
                    c.sendMessage("You are not aloud to reset stats while in Wild!");
                    return;
           		}
           		for (int j = 0; j < c.playerEquipment.length; j++) {
           		if (c.playerEquipment[j] > 0) {
                   c.sendMessage("Take off your items first!");
                   return;
           		}
           		if (c.playerLevel[4] > 10) {
           			c.sendMessage("You can't reset stats that are over 10!");
           			return;
           		} else {
           		c.playerXP[4] = c.getPA().getXPForLevel(4);
                c.playerLevel[4] = c.getPA().getLevelForXP(c.playerXP[4]);
                c.getPA().refreshSkill(4);
           	} 
          }
       }
    		
    		if (playerCommand.equalsIgnoreCase("resetpray")) {
           		if (c.inWild()) {
                    c.sendMessage("You are not aloud to reset stats while in Wild!");
                    return;
           		}
           		for (int j = 0; j < c.playerEquipment.length; j++) {
           		if (c.playerEquipment[j] > 0) {
                   c.sendMessage("Take off your items first!");
                   return;
           		}
           		if (c.playerLevel[5] > 10) {
           			c.sendMessage("You can't reset stats that are over 10!");
           			return;
           		} else {
           		c.playerXP[5] = c.getPA().getXPForLevel(5);
                c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
                c.getPA().refreshSkill(5);
           	}
          }
       	}
    		if (playerCommand.equals("betazone")) {
    			c.getPA().startTeleport(2525, 4776, 0, "modern");
    			 c.sendMessage("This will be disabled after the beta!");
    		}
    		
    		if (playerCommand.equalsIgnoreCase("resetmage")) {
           		if (c.inWild()) {
                    c.sendMessage("You are not aloud to reset stats while in Wild!");
                    return;
           		}
           		for (int j = 0; j < c.playerEquipment.length; j++) {
           		if (c.playerEquipment[j] > 0) {
                    c.sendMessage("Take off your items first!");
                    return;
           		}
           		if (c.playerLevel[6] > 10) {
           			c.sendMessage("You can't reset stats that are over 10!");
           			return;
           		} else {
           		c.playerXP[6] = c.getPA().getXPForLevel(6);
                c.playerLevel[6] = c.getPA().getLevelForXP(c.playerXP[6]);
                c.getPA().refreshSkill(6);
           	}
          }
       }
    		
    		

    	}  
   }