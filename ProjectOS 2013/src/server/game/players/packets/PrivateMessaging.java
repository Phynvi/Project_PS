package server.game.players.packets;


import server.Connection;
import server.game.content.tutorial.TutorialIsland;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;

/**
 * Private messaging, friends etc
 **/
public class PrivateMessaging implements PacketType {

	public final int ADD_FRIEND = 188, SEND_PM = 126, REMOVE_FRIEND = 215, CHANGE_PM_STATUS = 95, REMOVE_IGNORE = 59, ADD_IGNORE = 133;
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch(packetType) {
		
			case ADD_FRIEND:
			c.friendUpdate = true;
			long friendToAdd = c.getInStream().readQWord();
			boolean canAdd = true;

			for (int i1 = 0; i1 < c.friends.length; i1++) {
				if (c.friends[i1] != 0 && c.friends[i1] == friendToAdd) {
					canAdd = false;
					c.sendMessage(friendToAdd + " is already on your friends list.");
				}
			}
			if (canAdd) {
				for (int i1 = 0; i1 < c.friends.length; i1++) {
					if (c.friends[i1] == 0) {
						c.friends[i1] = friendToAdd;
						for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
							if (PlayerHandler.players[i2] != null && PlayerHandler.players[i2].isActive && Misc.playerNameToInt64(PlayerHandler.players[i2].playerName)== friendToAdd) {
								Client o = (Client)PlayerHandler.players[i2];
								if(o != null) {
									if (PlayerHandler.players[i2].privateChat == 0 || (PlayerHandler.players[i2].privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
										c.getPA().loadPM(friendToAdd, 1);
										break;
									}
								}
							}
						}
						break;
					}
				}
			}
			break;
			
			case SEND_PM:
			long sendMessageToFriendId = c.getInStream().readQWord();
            byte pmchatText[] = new byte[100];
            int pmchatTextSize = (byte) (packetSize - 8);
			c.getInStream().readBytes(pmchatText, pmchatTextSize, 0);
			c.getLogs().writePMLog(Misc.textUnpack(pmchatText, packetSize-8));
			if (Connection.isMuted(c))
				break;
			  if (System.currentTimeMillis() < c.muteTime) {
		        	c.sendMessage("You are muted for 72 hours for breaking the rules.");
		        	c.sendMessage("Please read over the rules to prevent doing so again.");
		        	return;
		        } else {
		        	c.muteTime = 0;
		        }
			  if (TutorialIsland.getTutorialIslandStage() <= 15 && c.playerRights < 2) {
		    		c.sendMessage("Your messages do not appear for other players in tutorial island.");
		    		return;
		    	}
            for (int i1 = 0; i1 < c.friends.length; i1++) {
                if (c.friends[i1] == sendMessageToFriendId) {
                    boolean pmSent = false;

                    for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
                        if (PlayerHandler.players[i2] != null && PlayerHandler.players[i2].isActive && Misc.playerNameToInt64(PlayerHandler.players[i2].playerName)== sendMessageToFriendId) {
                            Client o = (Client)PlayerHandler.players[i2];
							if(o != null) {
								if (PlayerHandler.players[i2].privateChat == 0 || (PlayerHandler.players[i2].privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
                                    if (c.friends[i1] == sendMessageToFriendId) {
                                        o.getPA().sendPM(Misc.playerNameToInt64(c.playerName), c.playerRights, pmchatText, pmchatTextSize);
                                        pmSent = true;
                                    }
	                            }
							}
                            break;
                        }
                    }
                    if (!pmSent) {
						c.sendMessage("That player is currently offline.");
						break;
                    }
                }
            }
            break;	
			
			
			case REMOVE_FRIEND:
			c.friendUpdate = true;
            long friendToRemove = c.getInStream().readQWord();

            for (int i1 = 0; i1 < c.friends.length; i1++) {
                if (c.friends[i1] == friendToRemove) {
					for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
						Client o = (Client)PlayerHandler.players[i2];		
						if(o != null) {
							if(c.friends[i1] == Misc.playerNameToInt64(PlayerHandler.players[i2].playerName)){
								o.getPA().updatePM(c.playerId, 0);
								break;
							}
						}
					}
					c.friends[i1] = 0;
                    break;
                }
            }
            break;
			
			case REMOVE_IGNORE:
				c.friendUpdate = true;
				long ignore = c.getInStream().readQWord();
				
				for(int i = 0; i < c.ignores.length; i++) {
					if(c.ignores[i] == ignore) {
						c.ignores[i] = 0;
						break;
					}
				}
			break;
			
			case CHANGE_PM_STATUS:
            //int tradeAndCompete = c.getInStream().readUnsignedByte();
            c.privateChat = c.getInStream().readUnsignedByte();
            //int publicChat = c.getInStream().readUnsignedByte();
            for (int i1 = 1; i1 < PlayerHandler.players.length; i1++) {
			   if (PlayerHandler.players[i1] != null && PlayerHandler.players[i1].isActive) {
                    Client o = (Client)PlayerHandler.players[i1];
					if(o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
                }
            }
            break;
			
			case ADD_IGNORE:
			c.friendUpdate = true;
			long ignoreAdd = c.getInStream().readQWord();
			for (int i = 0; i < c.ignores.length; i++) {
				if (c.ignores[i] == 0) {
					c.ignores[i] = ignoreAdd;
					break;
				}
			}
			break;
            
		}
		
	}	
}
