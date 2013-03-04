package server.game.players.packets;

import server.Server;
import server.game.content.minigames.castlewars.CastleWars;
import server.game.objects.Doors;
import server.game.players.*;
import server.task.Task;
import server.util.Misc;

public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132;
	public static final int SECOND_CLICK = 252;
	public static final int THIRD_CLICK = 70;

	public ClickObject() {
	}

	public void processPacket(final Client client, int i, int j) {
		client.clickObjectType = client.objectX = client.objectId = client.objectY = 0;
		client.objectYOffset = client.objectXOffset = 0;
		client.getPA().resetFollow();
		switch (i) {
		default:
			break;

		case 132:
			client.objectX = client.getInStream().readSignedWordBigEndianA();
			client.objectId = client.getInStream().readUnsignedWord();
			client.objectY = client.getInStream().readUnsignedWordA();
			client.objectDistance = 1;
			if (client.playerRights >= 0) {
				Misc.println((new StringBuilder()).append("objectId: ").append(client.objectId)
				.append("  ObjectX: ").append(client.objectX)
				.append("  ObjectY: ").append(client.objectY).toString());
				if(client.goodDistance(client.getX(), client.getY(), client.objectX, client.objectY, 1)) {
				    Doors.getSingleton();
					if(Doors.handleDoor(client.objectId, client.objectX, client.objectY, client.heightLevel)) {
				}
				if(client.teleTimer > 0) {
				client.sendMessage("You cannot use objects while teleporting");
				return;
				}
			}

				switch (client.objectId) {
				
				case 11666:
					if ((client.objectX == 2976) && (client.objectY == 3368)){
					client.sendMessage("lul");
						//client.getSmithing().sendSmelting();
					}
					break;
				
				case 1568:
					if ((client.objectX == 2399) && (client.objectY == 3099)) {
						client.getPA().object(9472, 2399, 3099, 0, 10);
					}
					if ((client.objectX == 2400) && (client.objectY == 3108)) {
						client.getPA().object(9472, 2400, 3108, 2, 10);
					}
					break;
					
				case 4437:
					if (client.getItems().playerHasItem(1265, 1)) {
						client.sendMessage("You start to break up the rocks...");
						client.startAnimation(625);
						  Server.getTaskScheduler().schedule(new Task(3) {
                              @Override
                              protected void execute() {
										stop();
										client.startAnimation(65535);
									}

									@Override
									public void stop() {
										client.getPA().object(-1, client.objectX,
												client.objectY, 0, 10);
										client.getPA().object(4438, client.objectX,
												client.objectY, 0, 10);
										client.sendMessage("You break up the rocks.");
									}
								});
					}
					break;

				case 4438:
					if (client.getItems().playerHasItem(1265, 1)) {
						client.sendMessage("You start to break up the rocks...");
						client.startAnimation(625);
						  Server.getTaskScheduler().schedule(new Task(3) {
                              @Override
                              protected void execute() {
										stop();
										client.startAnimation(65535);
									}

									@Override
									public void stop() {
										client.getPA().object(-1, client.objectX,
												client.objectY, 0, 10);
										client.sendMessage("You break up the rocks.");
									}
								});
					}
					break;
					
				case 4448:
					if (client.getItems().playerHasItem(1265, 1)) {
						client.sendMessage("You start to mine the wall...");
						client.startAnimation(625);
						  Server.getTaskScheduler().schedule(new Task(3) {
                              @Override
                              protected void execute() {
										stop();
										client.startAnimation(65535);
										client.sendMessage("You collapse the cave wall.");
									}

									@Override
									public void stop() {
										if ((client.objectX == 2390 || client.objectX == 2393)
												&& (client.objectY == 9503 || client.objectY == 9500)) { // east
																								// cave
																								// side
											client.getPA().object(-1, 2391,
													9501, 0, 10);
											client.getPA().object(4437, 2391,
													9501, 0, 10);
											CastleWars.collapseCave(1);
										}
										if ((client.objectX == 2399 || client.objectX == 2402)
												&& (client.objectY == 9511 || client.objectY == 9514)) { // north
																								// cave
																								// side
											client.getPA().object(-1, 2400,
													9512, 1, 10);
											client.getPA().object(4437, 2400,
													9512, 1, 10);
											CastleWars.collapseCave(0);
										}
										if ((client.objectX == 2408 || client.objectX == 2411)
												&& (client.objectY == 9502 || client.objectY == 9505)) { // west
																								// cave
																								// side
											client.getPA().object(-1, 2409,
													9503, 0, 10);
											client.getPA().object(4437, 2409,
													9503, 0, 10);
											CastleWars.collapseCave(3);
										}
										if ((client.objectX == 2400 || client.objectX == 2403)
												&& (client.objectY == 9496 || client.objectY == 9493)) { // south
																								// cave
																								// side
											client.getPA().object(-1, 2401,
													9494, 1, 10);
											client.getPA().object(4437, 2401,
													9494, 1, 10);
											CastleWars.collapseCave(2);
										}
									}
								});
						}
					break;
					
				case 9472:
					if ((client.objectX == 2399) && (client.objectY == 3099)) {
						client.startAnimation(828);
						client.stopMovement();
						client.resetWalkingQueue();
						client.getPA().requestUpdates();
						client.getPA().removeAllWindows();
						  Server.getTaskScheduler().schedule(new Task(1) {
                              @Override
                              protected void execute() {
										stop();
										client.startAnimation(65535);
										client.getPA().movePlayer(2400, 9507, 0);
									}
							});
					}
					if ((client.objectX == 2400) && (client.objectY == 3108)) {
						client.startAnimation(828);
						client.stopMovement();
						client.resetWalkingQueue();
						client.getPA().requestUpdates();
						client.getPA().removeAllWindows();
						  Server.getTaskScheduler().schedule(new Task(1) {
                              @Override
                              protected void execute() {
										stop();
										client.startAnimation(65535);
										client.getPA().movePlayer(2399, 9500, 0);
									}
								});
					}
					break;
				
				case 4387:
				    CastleWars.addToWaitRoom(client, 1); //saradomin
				break;
				    
				case 4388:
				    CastleWars.addToWaitRoom(client, 2); // zamorak
				break;
				    
				case 4408:
				    CastleWars.addToWaitRoom(client, 3); //guthix
				break;
				    
				case 4389: //sara
				case 4390: //zammy waiting room portal
				    CastleWars.leaveWaitingRoom(client);
			    break;
                    
                case 4459:
                	client.objectYOffset = 1;
                break;
                
                case 4460:
                	client.objectYOffset = 1;
                break;
                
                case 4462:
                	client.objectXOffset = 1;
               break;

				/*
				* werewolf agility course stepping stone
				*/
				case 5138:
				case 5136:
				case 5141:
				case 5133:
				case 5152:
					client.objectDistance = 2;
					break;
				/*
				* agility pyramid gap
				*/
				case 10863:	
				case 10857:
					client.objectDistance = 4;
					break;
				/*
				* rope swing in barbarian agility arena
				*/
				case 2282:
					client.objectDistance = 4;
					break;
				case 2294:
					client.objectDistance = 4;
					break;
				
					case 12163:
					case 4031:
					client.objectDistance = 2;
					break;
					
					case 2491:
					if (client.objectX == 2926 && client.objectY == 4817) {	
					client.objectXOffset = 1;
					client.objectYOffset = -3;
					}
					if (client.objectX == 2927 && client.objectY == 4814) {	
					client.objectXOffset = -1;
					client.objectYOffset = 3;
					}
					if (client.objectX == 2893 && client.objectY == 4812) {	
					client.objectXOffset = 2;
					client.objectYOffset = 5;
					}
					if (client.objectX == 2925 && client.objectY == 4848) {	
					client.objectXOffset = 2;
					client.objectYOffset = 5;
					}
					if (client.objectX == 2891 && client.objectY == 4847) {	
					client.objectXOffset = 2;
					client.objectYOffset = -1;
					}
					break;
					
					
					case 3033:
					if (client.objectX == 3099 && client.objectY == 3095) {
					client.objectXOffset = 2;
					client.objectYOffset = 1;
					}
					if (client.objectX == 3099 && client.objectY == 3097) {
					client.objectXOffset = 2;
					}
					if (client.objectX == 3105 && client.objectY == 3093) {
					client.objectYOffset = 2;
					}
					if (client.objectX == 3095 && client.objectY == 3088) {
					client.objectXOffset = 1;
					client.objectYOffset = 2;
					}
					if (client.objectX == 3102 && client.objectY == 3088) {
					client.objectXOffset = 2;
					client.objectYOffset = 1;
					}
					if (client.objectX == 3083 && client.objectY == 3099) {
					client.objectXOffset = 2;
					}
					if (client.objectX == 3087 && client.objectY == 3096) {
					client.objectXOffset = 2;
					}
					break;
					
					case 2609:
						client.objectYOffset = 2;
					break;
					
					case 3037:
					if (client.objectX == 3094 && client.objectY == 3093) {
					client.objectXOffset = 3;
					client.objectYOffset = 4;
					}
					break;
				
					case 4755:
					client.objectYOffset = 1;
					break;
					
					case 7056:
					client.objectXOffset = 2;
					break;
				
					case 26983:
					case 26982:
					case 24355:
					case 24354:
					client.objectYOffset = 1;
					client.objectDistance = 0;
					break;
				
					case 1722:
					if (client.objectX == 3156 && client.objectY == 3435) {
					client.objectXOffset = 3;
					} else {
					client.objectXOffset = -1;
					client.objectYOffset = 1;
					}
					break;
					
					case 1723:
					if (client.objectX == 3100 && client.objectY == 3266) {
					client.objectXOffset = 2;
					client.objectYOffset = 1;
					}
					break;
					
				case 1738:
				if (client.objectX == 3204 && client.objectY == 3207) {
				client.objectXOffset = 1;
				client.objectYOffset = 2;
				}
				if (client.objectX == 3204 && client.objectY == 3229) {
				client.objectXOffset = 2;
				}
				if (client.objectX == 2839 && client.objectY == 3537) {
				client.objectXOffset = 2;
				client.objectYOffset = 1;
				}
				break;

				case 1739:
				if (client.objectX == 3204 && client.objectY == 3207) {
				client.objectXOffset = 2;
				client.objectYOffset = 3;
				}
				if (client.objectX == 3204 && client.objectY == 3229) {
				client.objectXOffset = 2;
				}
				break;

				case 1740:
				if (client.objectX == 3205 && client.objectY == 3208) {
				client.objectXOffset = 1;
				client.objectYOffset = 2;
				}
				if (client.objectX == 3144 && client.objectY == 3448) {
				client.objectXOffset = 1;
				}
				break;

				case 12536:
				client.objectXOffset = 2;
				client.objectYOffset = 1;
				break;

				case 12537:
				client.objectXOffset = 1;
				client.objectYOffset = 2;
				break;

				case 12538:
				client.objectYOffset = 1;
				break;
			
				case 2287:
					if (client.absX == 2552 && client.absY == 3561) {
					client.objectYOffset = 2;
					} else if (client.absX == 2552 && client.absY == 3558) {
					client.objectYOffset = -1;
					}
					break;

				//case 1733:
					//client.objectYOffset = 2;
					//break;
					
				case 1734:
				if (client.objectX == 2569 && client.objectY == 9522) {
					client.objectXOffset = 1;
					client.objectYOffset = 3;
				}
				break;

				case 3044:
					client.getSmithing().sendSmelting();
					client.objectDistance = 3;
					break;

				case 245:
					client.objectYOffset = -1;
					client.objectDistance = 0;
					break;

				case 272:
					client.objectYOffset = 1;
					client.objectDistance = 0;
					break;

				case 273:
					client.objectYOffset = 1;
					client.objectDistance = 0;
					break;

				case 246:
					client.objectYOffset = 1;
					client.objectDistance = 0;
					break;

				case 4493:
				case 4494:
				case 4495:
				case 4496:
					client.objectDistance = 5;
					break;

				case 6522:
				case 10229:
					client.objectDistance = 2;
					break;

				case 8959:
					client.objectYOffset = 1;
					break;

				case 4417:
					if (client.objectX == 2425 && client.objectY == 3074) {
						client.objectYOffset = 2;
					}
					break;

				case 4420:
					if (client.getX() >= 2383 && client.getX() <= 2385) {
						client.objectYOffset = 1;
					} else {
						client.objectYOffset = -2;
					}
					// fall through

				case 2878:
				case 2879:
					client.objectDistance = 3;
					break;

				case 2558:
					client.objectDistance = 0;
					if (client.absX > client.objectX && client.objectX == 3044) {
						client.objectXOffset = 1;
					}
					if (client.absY > client.objectY) {
						client.objectYOffset = 1;
					}
					if (client.absX < client.objectX && client.objectX == 3038) {
						client.objectXOffset = -1;
					}
					break;

				case 9356:
					client.objectDistance = 2;
					break;

				case 1815:
				case 1816:
				case 5959:
				case 5960:
					client.objectDistance = 0;
					break;

				case 9293:
					client.objectDistance = 2;
					break;

				case 4418:
					if (client.objectX == 2374 && client.objectY == 3131) {
						client.objectYOffset = -2;
					} else if (client.objectX == 2369 && client.objectY == 3126) {
						client.objectXOffset = 2;
					} else if (client.objectX == 2380 && client.objectY == 3127) {
						client.objectYOffset = 2;
					} else if (client.objectX == 2369 && client.objectY == 3126) {
						client.objectXOffset = 2;
					} else if (client.objectX == 2374 && client.objectY == 3131) {
						client.objectYOffset = -2;
					}
					break;

				case 9706:
					client.objectDistance = 0;
					client.objectXOffset = 1;
					break;

				case 9707:
					client.objectDistance = 0;
					client.objectYOffset = -1;
					break;

				case 4419:
					if (client.getX() >= 2417 && client.getX() <= 2418) {
						client.objectYOffset = 3;
					} else {
						client.objectYOffset = -1;
						client.objectXOffset = -3;
						client.objectDistance = 3;
					}
					break;
					
				case 6707:
					client.objectYOffset = 3;
					break;

				case 6823:
					client.objectDistance = 2;
					client.objectYOffset = 1;
					break;

				case 6706:
					client.objectXOffset = 2;
					break;

				case 6772:
					client.objectDistance = 2;
					client.objectYOffset = 1;
					break;

				case 6705:
					client.objectYOffset = -1;
					break;

				case 6822:
					client.objectDistance = 2;
					client.objectYOffset = 1;
					break;

				case 6704:
					client.objectYOffset = -1;
					break;

				case 6773:
					client.objectDistance = 2;
					client.objectXOffset = 1;
					client.objectYOffset = 1;
					break;

				case 6703:
					client.objectXOffset = -1;
					break;

				case 6771:
					client.objectDistance = 2;
					client.objectXOffset = 1;
					client.objectYOffset = 1;
					break;

				case 6702:
					client.objectXOffset = -1;
					break;

				case 6821:
					client.objectDistance = 2;
					client.objectXOffset = 1;
					client.objectYOffset = 1;
					break;

				case 1276:
				case 1278:
				case 1306:
				case 1307:
				case 1308:
				case 1309:
				case 1281:
					client.objectDistance = 3;
				break;


				default:
					client.objectDistance = 1;
					client.objectXOffset = 0;
					client.objectYOffset = 0;
					break;
				}
				if (client.goodDistance(client.objectX + client.objectXOffset,
						client.objectY + client.objectYOffset, client.getX(),
						client.getY(), client.objectDistance)) {
					client.getObjects().firstClickObject(client.objectId,
							client.objectX, client.objectY);
				} else {
					client.clickObjectType = 1;
					Server.getTaskScheduler().schedule(new Task(1) {
                        @Override
                        protected void execute() {
					        if(client.clickObjectType == 1 && client.goodDistance(client.objectX + client.objectXOffset, client.objectY + client.objectYOffset, client.getX(), client.getY(), client.objectDistance)) {
					            client.getObjects().firstClickObject(client.objectId, client.objectX, client.objectY);
					            stop();
					        }
					        if(client.clickObjectType > 1 || client.clickObjectType == 0)
					            stop();
					    }
					    @Override
					    public void stop() {
					        client.clickObjectType = 0;
					    }
					});
				}
			}
			break;

		case 252:
			client.objectId = client.getInStream().readUnsignedWordBigEndianA();
			client.objectY = client.getInStream().readSignedWordBigEndian();
			client.objectX = client.getInStream().readUnsignedWordA();
			client.objectDistance = 1;
			if (client.playerRights >= 3) {
				Misc.println((new StringBuilder()).append("objectId: ")
						.append(client.objectId).append("  ObjectX: ")
						.append(client.objectX).append("  objectY: ")
						.append(client.objectY).append(" Xoff: ")
						.append(client.getX() - client.objectX)
						.append(" Yoff: ")
						.append(client.getY() - client.objectY).toString());
			}
			switch (client.objectId) {
			case 6162:
			case 6163:
			case 6164:
			case 6165:
			case 6166:
				client.objectDistance = 2;
				break;

			default:
				client.objectDistance = 1;
				client.objectXOffset = 0;
				client.objectYOffset = 0;
				break;
			}
			if (client.goodDistance(client.objectX + client.objectXOffset,
					client.objectY + client.objectYOffset, client.getX(),
					client.getY(), client.objectDistance)) {
				client.getObjects().secondClickObject(client.objectId,
						client.objectX, client.objectY);
			} else {
				client.clickObjectType = 2;
				 Server.getTaskScheduler().schedule(new Task(1) {
                     @Override
                     protected void execute() {
				        if(client.clickObjectType == 2 && client.goodDistance(client.objectX + client.objectXOffset, client.objectY + client.objectYOffset, client.getX(), client.getY(), client.objectDistance)) {
				            client.getObjects().secondClickObject(client.objectId, client.objectX, client.objectY);
				            stop();
				        }
				        if(client.clickObjectType < 2 || client.clickObjectType > 2)
				            stop();
				    }
				    @Override
				    public void stop() {
				        client.clickObjectType = 0;
				    }
				});
			}
			break;

		case 70: // 'F'
			client.objectX = client.getInStream().readSignedWordBigEndian();
			client.objectY = client.getInStream().readUnsignedWord();
			client.objectId = client.getInStream().readUnsignedWordBigEndianA();
			if (client.playerRights >= 3) {
				Misc.println((new StringBuilder()).append("objectId: ")
						.append(client.objectId).append("  ObjectX: ")
						.append(client.objectX).append("  objectY: ")
						.append(client.objectY).append(" Xoff: ")
						.append(client.getX() - client.objectX)
						.append(" Yoff: ")
						.append(client.getY() - client.objectY).toString());
			}
			switch (client.objectId) {
			default:
				client.objectDistance = 1;
				break;
			}
			client.objectXOffset = 0;
			client.objectYOffset = 0;
			if (client.goodDistance(client.objectX + client.objectXOffset,
					client.objectY + client.objectYOffset, client.getX(),
					client.getY(), client.objectDistance)) {
				client.getObjects().secondClickObject(client.objectId,
						client.objectX, client.objectY);
			} else {
				client.clickObjectType = 3;
				 Server.getTaskScheduler().schedule(new Task(1) {
                     @Override
                     protected void execute() {
				        if(client.clickObjectType == 3 && client.goodDistance(client.objectX + client.objectXOffset, client.objectY + client.objectYOffset, client.getX(), client.getY(), client.objectDistance)) {
				            client.getObjects().thirdClickObject(client.objectId, client.objectX, client.objectY);
				            stop();
				        }
				        if(client.clickObjectType < 3)
				            stop();
				    }
				    @Override
				    public void stop() {
				        client.clickObjectType = 0;
				    }
				});
			}
			break;
		}
	}

	public void handleSpecialCase(Client client, int i, int j, int k) {
	}
}
