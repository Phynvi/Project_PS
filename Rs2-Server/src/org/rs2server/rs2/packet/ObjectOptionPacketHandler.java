package org.rs2server.rs2.packet;

import org.rs2server.rs2.ScriptManager;
import org.rs2server.rs2.action.Action;
import org.rs2server.rs2.model.Animation;
import org.rs2server.rs2.model.Cannon;
import org.rs2server.rs2.model.DialogueManager;
import org.rs2server.rs2.model.Door;
import org.rs2server.rs2.model.GameObject;
import org.rs2server.rs2.model.GameObjectDefinition;
import org.rs2server.rs2.model.Item;
import org.rs2server.rs2.model.ItemDefinition;
import org.rs2server.rs2.model.Location;
import org.rs2server.rs2.model.NPC;
import org.rs2server.rs2.model.Player;
import org.rs2server.rs2.model.Skills;
import org.rs2server.rs2.model.World;
import org.rs2server.rs2.model.Mob.InteractionMode;
import org.rs2server.rs2.model.region.Region;
import org.rs2server.rs2.model.skills.*;
import org.rs2server.rs2.model.skills.Agility.Obstacle;
import org.rs2server.rs2.model.skills.Mining.Rock;
import org.rs2server.rs2.model.skills.Runecrafting.Rune;
import org.rs2server.rs2.model.skills.Woodcutting.Tree;
import org.rs2server.rs2.model.minigame.magetraining.MageTrainingArena;
import org.rs2server.rs2.net.Packet;

/**
 * Object option packet handler.
 * 
 * @author Graham Edgecombe
 * 
 */
public class ObjectOptionPacketHandler implements PacketHandler {

	private static final int OPTION_1 = 31, OPTION_2 = 203, OPTION_3 = 174,
			ITEM_ON_OBJECT = 134;

	@Override
	public void handle(Player player, Packet packet) {
		if (player.getAttribute("busy") != null) {
			return;
		}
		player.getActionQueue().clearRemovableActions();
		switch (packet.getOpcode()) {
		case OPTION_1:
			handleObjectOptionOne(player, packet);
			break;
		case OPTION_2:
			handleOption2(player, packet);
			break;
		case OPTION_3:
			handleOption3(player, packet);
			break;
		case ITEM_ON_OBJECT:
			handleOptionItem(player, packet);
			break;
		}
	}

	/**
	 * Handles the option 1 packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleObjectOptionOne(final Player player, Packet packet) {
		final int x = packet.getLEShortA();
		final int id = packet.getShort();
		final int y = packet.getShort();
		int z = player.getLocation().getZ();

		final Location loc = Location.create(x, y, z);

		Region region = player.getRegion();
		GameObject obj2 = region.getGameObject(loc, id);
		if (obj2 == null) {
			for (Region r2 : player.getRegion().getSurroundingRegions()) {
				if (r2.getGameObject(loc, id) != null) {
					obj2 = r2.getGameObject(loc, id);
					break;
				}
			}
		}
		final GameObject obj = obj2;
		if (obj == null) {
			return;
		} else {
			player.face(player.getLocation().oppositeTileOfEntity(obj));
		}
		if (player.getCombatState().getAttackEvent() != null) {
			player.resetInteractingEntity();
		}

		GameObjectDefinition def = GameObjectDefinition.forId(id);
		int width = 1;
		int height = 1;
		if (def != null) {
			width = def.getSizeX();
			height = def.getSizeY();
		}

		if (obj != null) {
			System.out.println("Object Option One for ObjectID :  " + id
					+ "  location : " + loc + " region : "
					+ region.getCoordinates().toString());
			player.face(player.getLocation().oppositeTileOfEntity(obj));
			player.getActionSender().sendDebugPacket(
					packet.getOpcode(),
					"ObjOpt1",
					new Object[] { "ID: " + id, "Location: " + loc,
							"X: " + obj.getWidth(), "Y: " + obj.getHeight(),
							"Direction: " + obj.getDirection(),
							"Type: " + obj.getType() });
		}

		int distance = 1;

		if (id == 2282 || id == 1733 || id == 4493) {
			distance = 5;
		} else if (id == 6821) {
			distance = 2;
		}

		if (id == 6707) { // for verac's unsure of why but it won't load through
							// name and isn't null type..
			String scriptName = "objectOptionOne_"
					+ obj.getDefinition().getName().toLowerCase()
							.replace(' ', '_');
			if (!ScriptManager.getScriptManager().call(scriptName, player, obj)) {
				player.getActionSender().sendMessage(
						"Nothing interesting happens.");
			}
		}

		Action action = null;
		Tree tree = Tree.forId(id);
		Rock rock = Rock.forId(id);
		Rune rune = Runecrafting.forId(id);
		final Obstacle obstacle = Obstacle.forLocation(loc);
		Door door2 = region.doorForLocation(loc, id);
		for (Region reg : region.getSurroundingRegions()) {
			if (door2 == null) {
				if (reg.doorForLocation(loc, id) != null) {
					door2 = reg.doorForLocation(loc, id);
				}
			}
		}
		final Door door = door2;
		if (tree != null) {
			action = new Woodcutting(player, obj);
		} else if (rock != null) {
			action = new Mining(player, obj);
		} else if (rune != null) {
			action = new Runecrafting(player, rune);
		} else if (obstacle != null) {
			action = new Action(player, 0) {

				@Override
				public void execute() {
					this.stop();
					Agility.tackleObstacle(player, obstacle, obj);
				}

				@Override
				public Action.AnimationPolicy getAnimationPolicy() {
					return Action.AnimationPolicy.RESET_ALL;
				}

				@Override
				public Action.CancelPolicy getCancelPolicy() {
					return Action.CancelPolicy.ALWAYS;
				}

				@Override
				public Action.StackPolicy getStackPolicy() {
					return Action.StackPolicy.NEVER;
				}
			};
		} else {
			action = new Action(player, 0) {

				@Override
				public void execute() {
					this.stop();
					if (door != null) {
						door.open(true);
					} else {
						switch (id) {
						case 9300:
							Agility.tackleObstacle(player,
									Obstacle.VARROCK_FENCE, obj);
							break;
						case 23271:
							Agility.tackleObstacle(player,
									Obstacle.WILDERNESS_DITCH, obj);
							break;
						case 6:
						case 7:
						case 8:
						case 9:
							if (player.getAttribute("cannon") != null) {
								Cannon cannon = (Cannon) player
										.getAttribute("cannon");
								if (cannon.getGameObject().getLocation()
										.equals(loc)) {
									if (id == 6) {
										cannon.fire();
									} else {
										cannon.destroy();
									}
								} else {
									player.getActionSender().sendMessage(
											"This is not your cannon.");
								}
							} else {
								player.getActionSender().sendMessage(
										"This is not your cannon.");
							}
							break;
						case 450:
						case 451:
						case 452:
						case 453:
							player.getActionSender()
									.sendMessage(
											"There is currently no ores remaining in this rock.");
							return;
						case 26384:
							if (player.getSkills().getLevel(Skills.STRENGTH) < 70) {
								player.getActionSender()
										.sendMessage(
												"You need a Strength level of 70 to bang this door down.");
							} else if (player.getInventory().getCount(2347) < 1) {
								player.getActionSender()
										.sendMessage(
												"You need a hammer to bang this door down.");
							} else {
								player.getActionQueue().addAction(
										new Action(player, 3) {

											@Override
											public void execute() {
												if (player.getLocation().getX() == 2851) {
													player.setTeleportTarget(Location
															.create(player
																	.getLocation()
																	.getX() - 1,
																	player.getLocation()
																			.getY(),
																	player.getLocation()
																			.getZ()));
												} else if (player.getLocation()
														.getX() == 2850) {
													player.setTeleportTarget(Location
															.create(player
																	.getLocation()
																	.getX() + 1,
																	player.getLocation()
																			.getY(),
																	player.getLocation()
																			.getZ()));
												}
												this.stop();
											}

											@Override
											public Action.AnimationPolicy getAnimationPolicy() {
												return Action.AnimationPolicy.RESET_NONE;
											}

											@Override
											public Action.CancelPolicy getCancelPolicy() {
												return Action.CancelPolicy.ALWAYS;
											}

											@Override
											public Action.StackPolicy getStackPolicy() {
												return Action.StackPolicy.NEVER;
											}
										});
								player.playAnimation(Animation.create(7002));
							}
							break;
						default:
							if (obj != null && obj.getDefinition() != null) {
								if (obj.getDefinition().getName().toLowerCase()
										.contains("exit")) {
									MageTrainingArena.getMageTrainingArena()
											.handleObjectOption(player,
													obj.getId());
								} else if (obj.getDefinition().getName()
										.toLowerCase().contains("banana")) {
									player.getActionSender().sendMessage(
											"You reach out to the tree...");
									player.getActionQueue().addAction(
											new Action(player, 3) {

												@Override
												public void execute() {
													player.getActionSender()
															.sendMessage(
																	"...and grab a banana.");
													player.getInventory().add(
															new Item(1963));
													this.stop();
												}

												@Override
												public Action.AnimationPolicy getAnimationPolicy() {
													return Action.AnimationPolicy.RESET_ALL;
												}

												@Override
												public Action.CancelPolicy getCancelPolicy() {
													return Action.CancelPolicy.ALWAYS;
												}

												@Override
												public Action.StackPolicy getStackPolicy() {
													return Action.StackPolicy.NEVER;
												}
											});
									return;
								} else if (obj.getDefinition().getName()
										.toLowerCase().contains("bank")
										&& !obj.getDefinition().getName()
												.toLowerCase()
												.contains("deposit")) {
									NPC closestBanker = null;
									int closestDist = 10;
									for (NPC banker : World.getWorld()
											.getRegionManager()
											.getLocalNpcs(player)) {
										if (banker.getDefinition().getName()
												.toLowerCase()
												.contains("banker")) {
											if (obj.getLocation()
													.distanceToPoint(
															banker.getLocation()) < closestDist) {
												closestDist = obj
														.getLocation()
														.distanceToPoint(
																banker.getLocation());
												closestBanker = banker;
											}
										}
									}
									if (closestBanker != null) {
										player.setInteractingEntity(
												InteractionMode.TALK,
												closestBanker);
										closestBanker.setInteractingEntity(
												InteractionMode.TALK, player);
										DialogueManager.openDialogue(player, 0);
									}
									return;
								}
								String scriptName = "objectOptionOne_"
										+ obj.getDefinition().getName()
												.toLowerCase()
												.replace(' ', '_');
								System.out.println("Attempting to call script "
										+ scriptName);
								if (!ScriptManager.getScriptManager().call(
										scriptName, player, obj)) {
									player.getActionSender().sendMessage(
											"Nothing interesting happens.");
								}
							} else {
								if (obj != null) {
									String scriptNumber = "objectOptionOne_"
											+ id;
									System.out
											.println("Attempting to call script "
													+ scriptNumber);
									if (!ScriptManager.getScriptManager().call(
											scriptNumber, player, obj)) {
										player.getActionSender().sendMessage(
												"Nothing interesting happens.");
									}
								}
							}
							break;
						}
					}
				}

				@Override
				public Action.AnimationPolicy getAnimationPolicy() {
					return Action.AnimationPolicy.RESET_ALL;
				}

				@Override
				public Action.CancelPolicy getCancelPolicy() {
					return Action.CancelPolicy.ALWAYS;
				}

				@Override
				public Action.StackPolicy getStackPolicy() {
					return Action.StackPolicy.NEVER;
				}
			};
		}
		if (action != null) {
			player.addCoordinateAction(player.getWidth(), player.getHeight(),
					loc, width, height, distance, action);
		}
	}

	/**
	 * Handles the option 2 packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOption2(final Player player, Packet packet) {
		final int x = packet.getShort() & 0xFFFF;
		final int id = packet.getLEShort() & 0xFFFF;
		final int y = packet.getShortA() & 0xFFFF;
		int z = player.getLocation().getZ();
		final Location loc = Location.create(x, y, z);
		System.out.println("Object Interaction - Option #2 for object: " + id);
		GameObject obj = player.getRegion().getGameObject(loc, id);
		if (obj == null) {
			for (Region r : player.getRegion().getSurroundingRegions()) {
				if (r.getGameObject(loc, id) != null) {
					obj = r.getGameObject(loc, id);
					break;
				}
			}
		}

		if (obj == null) {
			String scriptNumber = "objectOptionTwo_" + id;
			if (!ScriptManager.getScriptManager().call(scriptNumber, player,
					obj)) {
				player.getActionSender().sendMessage(
						"Nothing interesting happens.");
			}
			return;
		} else {
			player.face(player.getLocation().oppositeTileOfEntity(obj));
		}
		if (player.getCombatState().getAttackEvent() != null) {
			player.resetInteractingEntity();
		}
		final GameObject finalObject = obj;
		player.getActionSender().sendDebugPacket(packet.getOpcode(), "ObjOpt2",
				new Object[] { "ID: " + id, "Loc: " + loc });

		/**
		 * Mining shit... TODO: rewrite this garbage. - Sneaky.
		 */
		Action action = null;
		Action prospectAction = null;
		final Rock rock = Rock.forId(id);
		if (rock != null || (id == 450 || id == 451 || id == 452 || id == 453)) {
			prospectAction = new Action(player, 4) {

				@Override
				public void execute() {
					if (id == 450 || id == 451 || id == 452 || id == 453) {
						player.getActionSender().sendMessage(
								"This rock has no current ore available.");
					} else {
						player.getActionSender().sendMessage(
								"This rock contains "
										+ ItemDefinition.forId(rock.getOreId())
												.getName().toLowerCase() + ".");
					}
					this.stop();
				}

				@Override
				public Action.AnimationPolicy getAnimationPolicy() {
					return Action.AnimationPolicy.RESET_ALL;
				}

				@Override
				public Action.CancelPolicy getCancelPolicy() {
					return Action.CancelPolicy.ALWAYS;
				}

				@Override
				public Action.StackPolicy getStackPolicy() {
					return Action.StackPolicy.NEVER;
				}
			};
		}
		final Action finalProspectAction = prospectAction;
		action = new Action(player, 0) {

			@Override
			public void execute() {
				if (rock != null && finalProspectAction != null) {
					player.getActionSender().sendMessage(
							"You examine the rock for ores...");
					player.getActionQueue().addAction(finalProspectAction);
				} else {
					switch (id) {
					case 6:
						if (player.getAttribute("cannon") != null) {
							Cannon cannon = (Cannon) player
									.getAttribute("cannon");
							if (loc.equals(cannon.getGameObject().getLocation())) {
								cannon.destroy();
							}
						}
						break;
					default:
						if (finalObject != null
								&& finalObject.getDefinition() != null) {
							String scriptName = "objectOptionTwo_"
									+ finalObject.getDefinition().getName()
											.toLowerCase().replace(' ', '_');
							System.out.println("Attempting to call script "
									+ scriptName);
							if (!ScriptManager.getScriptManager().call(
									scriptName, player, finalObject)) {
								player.getActionSender().sendMessage(
										"Nothing interesting happens.");
							}
						} else {
							if (finalObject != null) {
								String scriptNumber = "objectOptionTwo_"
										+ finalObject.getId();
								if (!ScriptManager.getScriptManager().call(
										scriptNumber, player, finalObject)) {
									player.getActionSender().sendMessage(
											"Nothing interesting happens.");
								}
							}
						}
						break;
					}
				}
				this.stop();
			}

			@Override
			public Action.AnimationPolicy getAnimationPolicy() {
				return Action.AnimationPolicy.RESET_ALL;
			}

			@Override
			public Action.CancelPolicy getCancelPolicy() {
				return Action.CancelPolicy.ALWAYS;
			}

			@Override
			public Action.StackPolicy getStackPolicy() {
				return Action.StackPolicy.NEVER;
			}
		};
		if (id == 2646) {
			action = new FlaxPicking(player, obj);
		}

		int distance = 1;
		if (action != null) {
			player.addCoordinateAction(player.getWidth(), player.getHeight(),
					loc, obj.getWidth(), obj.getHeight(), distance, action);
		}
	}

	/**
	 * Handles the option 3 packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOption3(final Player player, Packet packet) {
		final int x = packet.getLEShortA() & 0xFFFF;
		final int id = packet.getLEShort() & 0xFFFF;
		final int y = packet.getLEShort() & 0xFFFF;
		int z = player.getLocation().getZ();
		/*
		 * if(player.getAttribute("temporaryHeight") != null) { z =
		 * player.getAttribute("temporaryHeight"); }
		 */
		final Location loc = Location.create(x, y, z);

		System.out.println("ID OP 3: " + id);

		GameObject obj = player.getRegion().getGameObject(loc, id);

		if (obj == null) {
			return;
		} else {
			player.face(player.getLocation().oppositeTileOfEntity(obj));
		}
		if (player.getCombatState().getAttackEvent() != null) {
			player.resetInteractingEntity();
		}
		final GameObject finalObject = obj;
		player.getActionSender().sendDebugPacket(packet.getOpcode(), "ObjOpt3",
				new Object[] { "ID: " + id, "Loc: " + loc });

		Action action = null;

		action = new Action(player, 0) {

			@Override
			public void execute() {
				switch (id) {
				default:
					String scriptName = "objectOptionThree" + id;
					if (!ScriptManager.getScriptManager().call(scriptName,
							player, finalObject)) {
						player.getActionSender().sendMessage(
								"Nothing interesting happens.");
					}
					break;
				}
				this.stop();
			}

			@Override
			public Action.AnimationPolicy getAnimationPolicy() {
				return Action.AnimationPolicy.RESET_ALL;
			}

			@Override
			public Action.CancelPolicy getCancelPolicy() {
				return Action.CancelPolicy.ALWAYS;
			}

			@Override
			public Action.StackPolicy getStackPolicy() {
				return Action.StackPolicy.NEVER;
			}
		};
		int distance = 1;
		if (action != null) {
			player.addCoordinateAction(player.getWidth(), player.getHeight(),
					loc, obj.getWidth(), obj.getHeight(), distance, action);
		}
	}

	/**
	 * Handles the item on object packet.
	 * 
	 * @param player
	 *            The player.
	 * @param packet
	 *            The packet.
	 */
	private void handleOptionItem(final Player player, Packet packet) {
		final int x = packet.getShortA() & 0xFFFF;
		final int slot = packet.getShortA() & 0xFFFF;
		final int id = packet.getLEShort() & 0xFFFF;
		final int y = packet.getLEShortA() & 0xFFFF;
		int z = player.getLocation().getZ();
		/*
		 * if(player.getAttribute("temporaryHeight") != null) { z =
		 * player.getAttribute("temporaryHeight"); }
		 */
		final Location loc = Location.create(x, y, z);

		final Item item = player.getInventory().get(slot);
		if (item == null) {
			return;
		}

		final GameObject obj = player.getRegion().getGameObject(loc, id);
		if (obj == null) {
			return;
		}
		player.face(player.getLocation().oppositeTileOfEntity(obj));
		if (player.getCombatState().getAttackEvent() != null) {
			player.resetInteractingEntity();
		}
		player.getActionSender().sendDebugPacket(packet.getOpcode(),
				"ItemOnObject", new Object[] { "ID: " + id, "Loc: " + loc });

		Action action = null;
		action = new Action(player, 0) {

			@Override
			public void execute() {
				if (obj.getDefinition().getName().equalsIgnoreCase("Anvil")) {
					Smithing.Bar bar = Smithing.Bar.forId(item.getId());
					if (bar != null) {
						Smithing.openSmithingInterface(player, bar);
					}
				} else {
					switch (id) {
					case 3485:
						if (item.getId() == 1925) {
							player.getInventory().remove(new Item(1925, 1));
							player.getInventory().add(new Item(2953, 1));
							player.getActionSender().sendMessage(
									"You fill the bucket up with water.");
						}
						break;
					/*
					 * case 3480:// coffin if (item.getId() == 2954) { if
					 * (player
					 * .getQuestStorage().getQuestStage(PriestInPeril.QUEST_ID)
					 * == 4) { player.getInventory().remove(new Item(2954, 1));
					 * player.getInventory().add(new Item(1925, 1));
					 * player.getActionSender().sendMessage( "You pour the
					 * blessed water over the coffin...");
					 * player.getQuestStorage
					 * ().setQuestStage(PriestInPeril.QUEST_ID, 5); } } break;
					 */
					case 2452: // air ruins
						if (item.getId() == 1438) {
							player.getActionSender()
									.sendMessage(
											"You raise the talisman up to the ruins...");
							player.getActionSender()
									.sendMessage(
											"You see a bright light and are teleported.");
							player.setTeleportTarget(Location.create(2841,
									4829, 0));
						}
						break;
					case 2454: // water
						if (item.getId() == 1444) {
							player.getActionSender()
									.sendMessage(
											"You raise the talisman up to the ruins...");
							player.getActionSender()
									.sendMessage(
											"You see a bright light and are teleported.");
							player.setTeleportTarget(Location.create(2726,
									4832, 0));
						}
						break;
					case 2455: // earth
						if (item.getId() == 1440) {
							player.getActionSender()
									.sendMessage(
											"You raise the talisman up to the ruins...");
							player.getActionSender()
									.sendMessage(
											"You see a bright light and are teleported.");
							player.setTeleportTarget(Location.create(2655,
									4830, 0));
						}
						break;
					case 2456: // fire
						if (item.getId() == 1442) {
							player.getActionSender()
									.sendMessage(
											"You raise the talisman up to the ruins...");
							player.getActionSender()
									.sendMessage(
											"You see a bright light and are teleported.");
							player.setTeleportTarget(Location.create(2575,
									4850, 0));
						}
						break;
					case 11666:
						Smelting.Bar bar = Smelting.Bar.forOreId(item.getId());
						if (bar != null) {
							player.getActionQueue().addAction(
									new Smelting(player, bar, 1));
						}
						break;
					case 6:
						if (player.getAttribute("cannon") != null) {
							Cannon cannon = (Cannon) player
									.getAttribute("cannon");
							if (loc.equals(cannon.getGameObject().getLocation())) {
								if (item.getId() == 2) {
									int cannonBalls = cannon.getCannonBalls();
									if (cannonBalls >= 30) {
										player.getActionSender().sendMessage(
												"Your cannon is already full.");
										return;
									}
									int newCannonBalls = item.getCount();
									if (newCannonBalls > 30) {
										newCannonBalls = 30;
									}
									if (newCannonBalls + cannonBalls > 30) {
										newCannonBalls = 30 - cannonBalls;
									}
									if (newCannonBalls < 1) {
										return;
									}
									player.getInventory().remove(
											new Item(2, newCannonBalls));
									cannon.addCannonBalls(newCannonBalls);
									player.getActionSender().sendMessage(
											"You load "
													+ newCannonBalls
													+ " cannonball"
													+ (newCannonBalls > 1 ? "s"
															: "")
													+ " into your cannon.");
								}
							}
						}
						break;
					case 7:
						if (player.getAttribute("cannon") != null) {
							Cannon cannon = (Cannon) player
									.getAttribute("cannon");
							if (loc.equals(cannon.getGameObject().getLocation())) {
								if (item.getId() == 8) {
									cannon.addPart(new Item(8, 1));
								}
							}
						}
						break;
					case 8:
						if (player.getAttribute("cannon") != null) {
							Cannon cannon = (Cannon) player
									.getAttribute("cannon");
							if (loc.equals(cannon.getGameObject().getLocation())) {
								if (item.getId() == 10) {
									cannon.addPart(new Item(10, 1));
								}
							}
						}
						break;
					case 9:
						if (player.getAttribute("cannon") != null) {
							Cannon cannon = (Cannon) player
									.getAttribute("cannon");
							if (loc.equals(cannon.getGameObject().getLocation())) {
								if (item.getId() == 12) {
									cannon.addPart(new Item(12, 1));
								}
							}
						}
						break;
					}
					if (Cooking.getSingleton() != null) {
						if (Cooking.getSingleton().rawInput(item.getId()) != null
								&& Cooking.getSingleton().cookingObject(
										obj.getId()) > 0) {
							player.setAttribute("permit", new Permit(player,
									item.getId(), obj.getLocation(), true));
							player.setAttribute("cookingObject", obj.getId());
							Cooking.getSingleton().showCookingInterface(player);
						}
					}
				}
				this.stop();
			}

			@Override
			public Action.AnimationPolicy getAnimationPolicy() {
				return Action.AnimationPolicy.RESET_ALL;
			}

			@Override
			public Action.CancelPolicy getCancelPolicy() {
				return Action.CancelPolicy.ALWAYS;
			}

			@Override
			public Action.StackPolicy getStackPolicy() {
				return Action.StackPolicy.NEVER;
			}
		};
		if (action != null) {
			player.addCoordinateAction(player.getWidth(), player.getHeight(),
					obj.getLocation(), obj.getWidth(), obj.getHeight(), 1,
					action);
		}
	}
}
