package server.game.content.skills.core;

import server.Config;
import server.Server;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.task.Task;
import server.util.Misc;

public class Agility {

	/**
	 * @author Aintaro
	 */

	Client c;

	public Agility(Client c) {
		this.c = c;
	}
	
	private boolean[] agilityProgress = new boolean[6];
	
	private void resetAgilityProgress() {
		for (int i = 0; i < 6; i++) {
			agilityProgress[i] = false;
		}
		lapBonus = 0;
	}
	
	private static final int LOG_EMOTE = 762, PIPES_EMOTE = 844, CLIMB_UP_EMOTE = 828, CLIMB_DOWN_EMOTE = 827, CLIMB_UP_MONKEY_EMOTE = 3487;
	
	private static final int LOG_OBJECT = 2295, NET1_OBJECT = 2285, TREE_OBJECT = 2313, ROPE_OBJECT = 2212, TREE_BRANCH_OBJECT = 2314, NET2_OBJECT = 2286, PIPES1_OBJECT = 154, PIPES2_OBJECT = 4058,//gnome course objects
	WILDERNESS_PIPE_OBJECT = 2288, WILDERNESS_SWING_ROPE_OBJECT = 2283, WILDERNESS_STEPPING_STONE_OBJECT = 2311, WILDERNESS_LOG_BALANCE_OBJECT = 2297, WILDERNESS_ROCKS_OBJECT = 2328, BARBARIAN_ROPE_SWING_OBJECT = 2282, //werewolve course objects
	BARBARIAN_LOG_OBJECT = 2294, BARBARIAN_NET_OBJECT = 2284, BARBARIAN_LEDGE_OBJECT = 2302, BARBARIAN_LADDER_OBJECT = 3205, BARBARIAN_WALL_OBJECT = 1948, //barbarian course objects
	PYRAMID_STAIRCE_OBJECT = 10857, PYRAMID_WALL_OBJECT = 10865, PYRAMID_PLANK_OBJECT = 10868, PYRAMID_GAP_OBJECT = 10863, //pyramid course objects
	WEREWOLF_STEPPING_STONE_OBJECT = 5138, WEREWOLF_HURDLE_OBJECT1 = 5133, WEREWOLF_HURDLE_OBJECT2 = 5134, WEREWOLF_HURDLE_OBJECT3 = 5135, WEREWOLF_PIPES_OBJECT = 5152, WEREWOLF_SKULL_OBJECT = 5136, WEREWOLF_SLING_OBJECT = 5141; //werewolf course objects
	
	
	private int steppingStone, steppingStoneTimer = 0;
	int agilityTimer = -1;
	int moveHeight = -1;
	int tropicalTreeUpdate = -1;
	int zipLine = -1;
	private int moveX, moveY, moveH;
	
	/**
	* sets a specific emote to walk to point x
	*/
	
	private void walkToEmote(int id) {
		c.isRunning2 = false;
        c.playerWalkIndex = id;
		c.getPA().requestUpdates(); //this was needed to make the agility work
    }
	
	/**
	* resets the player animation
	*/
	
	private void stopEmote() {
        c.getCombat().getPlayerAnimIndex();
		c.getPlayerAction().setAction(false);
		c.getPlayerAction().canWalk(true);
		c.getPA().requestUpdates(); //this was needed to make the agility work
		c.isRunning2 = true;
    }
	
	/**
	* walk to point x with s a specific animation
	*/

	private void walk(int EndX, int EndY, int Emote, int endingAnimation) {
			c.getPlayerAction().setAction(true);
			c.getPlayerAction().canWalk(false);
			walkToEmote(Emote);
			c.getPA().walkTo2(EndX, EndY);
			destinationReached(EndX, EndY, endingAnimation);
    }
	
	/**
	* when a player reaches he's point the stopEmote() method gets called
	* this method calculates when the player reached he's point
	*/
	
	public void destinationReached(int x2, int y2, final int endingEmote) {
		if (x2 >= 0 && y2 >= 0 && x2 != y2) {
			Server.getTaskScheduler().schedule(new Task(x2+y2) {
				@Override
				protected void execute() {
					if (c.disconnected) {
						stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPA().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					c.startAnimation(endingEmote);
					this.stop();
				}
			});
		} else if (x2 == y2) {
			Server.getTaskScheduler().schedule(new Task(x2) {
				@Override
				protected void execute() {
					if (c.disconnected) {
						stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPA().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					c.startAnimation(endingEmote);
					this.stop();
				}
			});
		} else if (x2 < 0) {
			Server.getTaskScheduler().schedule(new Task(-(x2)+y2) {
				@Override
				protected void execute() {
					if (c.disconnected) {
						stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPA().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					c.startAnimation(endingEmote);
					this.stop();
				}
			});
		} else if (y2 < 0) {
			Server.getTaskScheduler().schedule(new Task(x2-(y2)) {
				@Override
				protected void execute() {
					if (c.disconnected) {
						stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPA().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					c.startAnimation(endingEmote);
					this.stop();
				}
			});
		}
	}
	
	/**
	* @param objectId : the objectId to know how much exp a player receives
	*/
	
	private int getXp(int objectId) {
		switch (objectId) {
			case TREE_OBJECT:
			case TREE_BRANCH_OBJECT:
				return 5;
			case LOG_OBJECT:
			case PIPES1_OBJECT:
			case PIPES2_OBJECT:
			case NET2_OBJECT:
			case NET1_OBJECT:
			case ROPE_OBJECT:
			case PYRAMID_WALL_OBJECT:
			case BARBARIAN_NET_OBJECT:	
				return 8;
			case BARBARIAN_LOG_OBJECT:	
			case BARBARIAN_WALL_OBJECT:
				return 14;
			case WEREWOLF_PIPES_OBJECT: 
				return 15;
			case WEREWOLF_SKULL_OBJECT:
				return 25;
			case WEREWOLF_HURDLE_OBJECT1:
			case WEREWOLF_HURDLE_OBJECT2:
			case WEREWOLF_HURDLE_OBJECT3:
				return 20;
			case BARBARIAN_ROPE_SWING_OBJECT:
			case BARBARIAN_LEDGE_OBJECT:
				return 22;
			case WEREWOLF_STEPPING_STONE_OBJECT:
				return 50;
			case PYRAMID_GAP_OBJECT:
				return 52;
			case PYRAMID_PLANK_OBJECT:
				return 56;
			case WEREWOLF_SLING_OBJECT:
				return 190;
		}
		return -1;
	}
	
	/**
	* @param objectId : the objectId to fit with the right agility level required
	*/
	
	private int getLevelRequired(int objectId) {
		switch (objectId) {	
			case PYRAMID_WALL_OBJECT:
			case PYRAMID_STAIRCE_OBJECT:
			case PYRAMID_PLANK_OBJECT:
			case PYRAMID_GAP_OBJECT:
				return 30;
				
			case BARBARIAN_ROPE_SWING_OBJECT:
			case BARBARIAN_LOG_OBJECT:			
			case BARBARIAN_NET_OBJECT:			
			case BARBARIAN_LEDGE_OBJECT:			
			case BARBARIAN_LADDER_OBJECT:
			case BARBARIAN_WALL_OBJECT:
				return 35;
				
			case APE_ATOLL_STEPPING_STONES_OBJECT:
			case APE_ATOLL_TROPICAL_TREE_OBJECT:
			case APE_ATOLL_MONKEYBARS_OBJECT:
			case APE_ATOLL_SKULL_SLOPE_OBJECT:
			case APE_ATOLL_SWINGROPE_OBJECT:
			case APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:
				return 50;
				
			case WILDERNESS_PIPE_OBJECT:
			case WILDERNESS_SWING_ROPE_OBJECT:
			case WILDERNESS_STEPPING_STONE_OBJECT:
			case WILDERNESS_ROCKS_OBJECT:
			case WILDERNESS_LOG_BALANCE_OBJECT:
				return 52;
				
			case WEREWOLF_STEPPING_STONE_OBJECT:
			case WEREWOLF_HURDLE_OBJECT1:
			case WEREWOLF_HURDLE_OBJECT2:
			case WEREWOLF_HURDLE_OBJECT3:
			case WEREWOLF_PIPES_OBJECT: 
			case WEREWOLF_SKULL_OBJECT: 
			case WEREWOLF_SLING_OBJECT:
				return 60;
		}
		return -1;
	}
	
	/**
	* @param objectId : the objectId to fit with the right animation played
	*/
	
	private int getAnimation(int objectId) {
		switch (objectId) {
			case LOG_OBJECT:
			case WILDERNESS_LOG_BALANCE_OBJECT:
			case BARBARIAN_LOG_OBJECT:
				return LOG_EMOTE;
			case 154:
			case 4084:
			case WILDERNESS_PIPE_OBJECT:
			case WEREWOLF_PIPES_OBJECT:
				return PIPES_EMOTE;
			case WILDERNESS_SWING_ROPE_OBJECT:
			case BARBARIAN_ROPE_SWING_OBJECT:
			case WEREWOLF_STEPPING_STONE_OBJECT:
				return 3067;
			case WILDERNESS_STEPPING_STONE_OBJECT:
				return 1604; //2588
			case WILDERNESS_ROCKS_OBJECT:
			case WEREWOLF_SKULL_OBJECT:
				return 1148;
			case BARBARIAN_LEDGE_OBJECT:
				return 756;
			case BARBARIAN_WALL_OBJECT:
			case PYRAMID_WALL_OBJECT:
				return 840;
			case APE_ATOLL_STEPPING_STONES_OBJECT:
				return 3480;
			case APE_ATOLL_MONKEYBARS_OBJECT:
				return 3483;
			case APE_ATOLL_SKULL_SLOPE_OBJECT:
				return 3485;
			case APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:
				return 3494;
			case APE_ATOLL_SWINGROPE_OBJECT:
				return 3482;
			case WEREWOLF_SLING_OBJECT:
				return 744;
			case WEREWOLF_HURDLE_OBJECT1:
			case WEREWOLF_HURDLE_OBJECT2:
			case WEREWOLF_HURDLE_OBJECT3:
				return 2750;
		}
		return -1;
	}
	
	
	/**
	* method used for the tropicalTree at ape atoll
	* the problem was that the heightlevel was not correct
	* while the heightlevel was not correct we could not change heightlevels
	* and use the walkToEmote directly so we need to add a little timer to make it work
	*/
	
	private void climbUpTropicalTree(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_UP_MONKEY_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		Server.getTaskScheduler().schedule(new Task(2) {
			@Override
			protected void execute() {
				if (c.disconnected) {
					stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPA().movePlayer(moveX, moveY, moveH);
				this.stop();
			}
		});
	}
	
	/**
	* climbUp a ladder or anything.
	* small delay before getting teleported to destination
	*/
	
	private void climbUp(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_UP_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		Server.getTaskScheduler().schedule(new Task(1) {
			@Override
			protected void execute() {
				if (c.disconnected) {
					stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPA().movePlayer(moveX, moveY, moveH);
				this.stop();
			}
		});
	}
	
	/**
	* climbDown a ladder or anything.
	* small delay before getting teleported to destination
	*/
	
	private void climbDown(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_DOWN_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		Server.getTaskScheduler().schedule(new Task(1) {
			@Override
			protected void execute() {
				if (c.disconnected) {
					stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPA().movePlayer(moveX, moveY, moveH);
				this.stop();
			}
		});
	}
	
	/**
	* a specific position the player has to stand on 
	* before the action is set to true
	*/
	
	private boolean hotSpot(int hotX, int hotY) {
		if (c.getX() == hotX && c.getY() == hotY) {
			return true;
		}
		return false;
	}
	
	int lapBonus = 0;
	
	private void lapFinished() {
		if (agilityProgress[0] || agilityProgress[1] || agilityProgress[2] || agilityProgress[3] || agilityProgress[4] || agilityProgress[5]) {
			c.getPA().addSkillXP(lapBonus, c.playerAgility);
			c.sendMessage("You received some bonus experience for completing the track!");
		}
	}
	
	/**
	* @param objectId : returns the objectId the player clicks
	*/
	
	public boolean gnomeCourse(int objectId) {
		switch (objectId) {
			case LOG_OBJECT:
				if (hotSpot(2474, 3436)) {
					walk(0, -7, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				}
				return true;
				
			case NET1_OBJECT:
				climbUp(c.getX(), c.getY() - 2, 1);
				agilityProgress[1] = true;
				return true;
				
			case TREE_OBJECT:
				climbUp(c.getX(), c.getY() - 3, 2);
				agilityProgress[2] = true;
				return true;
				
			case ROPE_OBJECT:
				if (hotSpot(2477, 3420)) {
					walk(6, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[3] = true;
				}
				return true;
				
			case TREE_BRANCH_OBJECT:
				climbDown(c.getX(), c.getY(), 0);
				agilityProgress[4] = true;
				return true;
				
			case NET2_OBJECT:
				if (c.getY() == 3425) {
					climbUp(c.getX(), c.getY() + 2, 0);
					agilityProgress[5] = true;
				}
				return true;
				
			case PIPES1_OBJECT:
				if (hotSpot(2484, 3430)) {
					walk(0, 7, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					lapBonus = 1400/30;
					lapFinished();
				}
				return true;
				
			case PIPES2_OBJECT:
				if (hotSpot(2487, 3430)) {
					walk(0, 7, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					lapBonus = 1400/30;
					lapFinished();
				}
				return true;
		}
		return false;
	}
	
	/**
	* 600 ms process for some agility actions
	*/
	
	public void agilityProcess() {
		//tropicaltreeupdate timer for the object in ape atoll course
		if (tropicalTreeUpdate > 0)
			tropicalTreeUpdate --;
		if (tropicalTreeUpdate == 0) {
			walk(13, 13, getAnimation(APE_ATOLL_BIG_TROPICAL_TREE_OBJECT), -1);
			tropicalTreeUpdate = -1;
		}
		//zipline timer for the object in werewolve course
		if (zipLine > 0)
			zipLine --;
		if (zipLine == 0) {
			walk(0, -39, getAnimation(WEREWOLF_SLING_OBJECT), 743);
			zipLine = -1;
		}
		
		if (steppingStone > 0 && steppingStoneTimer == 0) {
			walk(-1, 0, getAnimation(WILDERNESS_STEPPING_STONE_OBJECT), -1);
			steppingStone--;
			steppingStoneTimer = 2;
		}
		
		if (steppingStoneTimer > 0) {
			steppingStoneTimer --;
		}
		
		if (hotSpot(3363,2851)) {
			moveX = 3368;
			moveY = 2851;
			moveH = 1;
			walk(1, 0, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3372, 2832)) {
			moveX = 3367;
			moveY = 2832;
			moveH = 1;
			walk(-1, 0, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3364, 2832)) {
			moveX = 3359;
			moveY = 2832;
			moveH = 1;
			walk(-1, 0, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3357, 2836)) {
			moveX = 3357;
			moveY = 2841;
			moveH = 2;
			walk(0, 1, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3357, 2846)) {
			moveX = 3357;
			moveY = 2849;
			moveH = 2;
			walk(0, 1, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3359, 2849)) {
			moveX = 3366;
			moveY = 2849;
			moveH = 2;
			walk(1, 0, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3372, 2841)) {
			moveX = 3372;
			moveY = 2836;
			moveH = 2;
			walk(0, -1, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3366, 2834)) {
			moveX = 3363;
			moveY = 2834;
			moveH = 2;
			walk(-1, 0, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3359, 2842)) {
			moveX = 3359;
			moveY = 2847;
			moveH = 3;
			walk(0, 1, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}
		
		if (hotSpot(3370, 2843)) {
			moveX = 3370;
			moveY = 2840;
			moveH = 3;
			walk(0, -1, 2753, -1);
			c.getPA().addSkillXP(14, c.playerAgility);
		}
		
		if (agilityTimer > 0) {
			agilityTimer --;
		}
		
		if (agilityTimer == 0) {
			c.getPA().movePlayer(moveX, moveY, moveH);
			moveX = -1;
			moveY = -1;
			moveH = 0;
			agilityTimer = -1;
			System.out.println("Bam");
		}
		
	}
	
	/**
	* handles all the objects of the wilderness agility course
	*/
	
	public boolean wildernessCourse(final int objectId) {
		switch (objectId) {
			case WILDERNESS_PIPE_OBJECT: //pipe
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3004, 3937)) {
					walk(0, 13, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				}
				return true;
			case WILDERNESS_SWING_ROPE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3005, 3953)) {
					walk(0, 1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[1] = true;
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3005, 3958, 0);
							this.stop();
						}
					});
				}
				return true;
			case WILDERNESS_STEPPING_STONE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3002, 3960)) {
					walk(-1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					steppingStone = 6;
					steppingStoneTimer = 2;
					steppingStone--;
					agilityProgress[3] = true;
				}
				return true;
				
			case WILDERNESS_LOG_BALANCE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3002, 3945)) {
					walk(-8, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[4] = true;
				}
				return true;
			
			case WILDERNESS_ROCKS_OBJECT:
				if (checkLevel(objectId))
					return false;
				walk(0, -4, getAnimation(objectId), -1);
				c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				agilityProgress[5] = true;
				lapBonus = 2400/30;
				lapFinished();
				return true;
		}
		return false;
	}
	
	/**
	* handles all the objects of the barbarian agility course
	*/
	
	public boolean barbarianCourse(int objectId) {
		switch (objectId) {
			case BARBARIAN_ROPE_SWING_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2551, 3554)) {
					walk(0, -1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(2551, 3549, 0);
							this.stop();
						}
					});
				}
				return true;
			
			case BARBARIAN_LOG_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2551, 3546)) {
					walk(-10, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[1] = true;
				}
				return true;
				
			case BARBARIAN_NET_OBJECT:
				if (checkLevel(objectId))
					return false;
				climbUp(c.getX() - 1, c.getY(), 1);
				agilityProgress[2] = true;
				return true;
				
			case BARBARIAN_LEDGE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2536, 3547)) {
					walk(-4, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[3] = true;
				}
				return true;
				
			case BARBARIAN_LADDER_OBJECT:
				if (checkLevel(objectId))
					return false;
				climbDown(c.getX(), c.getY(), 0);
				agilityProgress[4] = true;
				return true;
				
			case BARBARIAN_WALL_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2535, 3553) || hotSpot(2538, 3553)) {
					if (hotSpot(2541, 3553)) {
						walk(2, 0, getAnimation(objectId), -1);
						c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
						agilityProgress[5] = true;
						lapBonus = 1700/30;
						lapFinished();
					} else {
						walk(2, 0, getAnimation(objectId), -1);
						c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					}
				}
				return true;
				
			case 1747: //climb up ladder
				climbUp(c.getX(), c.getY(), 1);
				return true;
		}
		return false;
	}
	
	//hotspot for first plank = 3375, 2845
	
	//first stairce = c.getY() - 3
	
	//3364 2849
	
	/**
	* handles all the objects of the pyramid agility course
	*/
	
	public boolean pyramidCourse(int objectId) {
		switch (objectId) {
			case PYRAMID_STAIRCE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3357, 2832) || hotSpot(3356, 2832) || hotSpot(3355, 2830) || hotSpot(3359, 2834) || hotSpot(3358, 2834) || hotSpot(3361, 2836) || hotSpot(3360, 2836)) {
					if (c.heightLevel == 0) {
						climbUp(c.getX(), c.getY() + 3, 1);
					} else if (c.heightLevel == 1) {
						climbUp(c.getX(), c.getY() + 3, 2);
					} else if (c.heightLevel == 2) {
						climbUp(c.getX(), c.getY() + 3, 3);
					} else {
						climbUp(c.getX(), c.getY() - 8, 0);
						c.sendMessage("Congratulations you completed the pyramid course!");
					}
				}
				return true;
				
			case PYRAMID_WALL_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3354, 2848) || hotSpot(3355, 2848)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				}
				if (hotSpot(3371, 2834) || hotSpot(3371, 2833)) {
					walk(-2, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				}
				if (hotSpot(3359, 2838) || hotSpot(3358, 2838)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				}
				return true;
				
			case PYRAMID_GAP_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3363,2851)) {
					walk(1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3368, 2851, 1);
							this.stop();
						}
					});
				}
				if (hotSpot(3372, 2832)) {
					walk(1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3367, 2832, 1);
							this.stop();
						}
					});
				}
				if (hotSpot(3364, 2832)) {
					walk(-1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3359, 2832, 1);
							this.stop();
						}
					});
				}
				if (hotSpot(3357, 2836)) {
					walk(1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3357, 2841, 2);
							this.stop();
						}
					});
				}
				if (hotSpot(3357, 2846)) {
					walk(1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3357, 2849, 2);
							this.stop();
						}
					});
				}
				if (hotSpot(3359, 2849)) {
					walk(0, 1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3366, 2849, 2);
							this.stop();
						}
					});
				}
				if (hotSpot(3366, 2834)) {
					walk(-1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3363, 2834, 2);
							this.stop();
						}
					});
				}
				if (hotSpot(3359, 2842)) {
					walk(0, 1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3359, 2847, 3);
							this.stop();
						}
					});
				}
				if (hotSpot(3370, 2843)) {
					walk(0, -1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3370, 2838, 3);
							this.stop();
						}
					});
				}
				if (hotSpot(3372, 2841)) {
					walk(0, -1, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(3372, 2836, 2);
							this.stop();
						}
					});
				}
				return true;
				
			case PYRAMID_PLANK_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3375,2845)) {
					walk(0, -6, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				}
				if (hotSpot(3370,2835)) {
					walk(-6, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				}
				return true;
		}
		return false;
	}
	private static final int APE_ATOLL_STEPPING_STONES_OBJECT = 12568, 
	APE_ATOLL_TROPICAL_TREE_OBJECT = 12570, 
	APE_ATOLL_MONKEYBARS_OBJECT = 12573, 
	APE_ATOLL_SKULL_SLOPE_OBJECT = 12576, 
	APE_ATOLL_SWINGROPE_OBJECT = 12578,
	APE_ATOLL_BIG_TROPICAL_TREE_OBJECT = 12618;
	
	/**
	* handles all the objects @ the ape atoll course
	*/
	
	private static int NINJA_MONKEY_NPC = 1480;
	
	private boolean checkLevel(int objectId) {
		if (getLevelRequired(objectId) >= c.playerLevel[c.playerAgility]) {
			c.sendMessage("You need atleast " + getLevelRequired(objectId) + " agility to do this.");
			return true;
		}
		return false;
	}
	
	/**
	* handles all the objects ape atoll agility course
	*/
	
	public boolean apeAtollCourse(int objectId) {
		switch (objectId) {
			case APE_ATOLL_STEPPING_STONES_OBJECT:
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2755, 2742)) {
					c.sendMessage("You jump the step stone.");
					walk(-2, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				}
				return true;
				
			case APE_ATOLL_TROPICAL_TREE_OBJECT:
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2753, 2742) && c.heightLevel == 0) {
					c.sendMessage("You managed to climb up the Tree.");
					climbUpTropicalTree(c.getX(), c.getY(), 2);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[1] = true;
				}
				return true;
				
			case APE_ATOLL_MONKEYBARS_OBJECT:
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2752, 2741)) {
					c.sendMessage("You swing yourself to the other side");
					moveHeight = 0;
					walk(-5, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[2] = true;
				}
				return true;
				
			case APE_ATOLL_SKULL_SLOPE_OBJECT:
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2747, 2741)) {
					walk(-5, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					c.sendMessage("You climb your way up");
					agilityProgress[3] = true;
				}
				return true;
				
			case APE_ATOLL_SWINGROPE_OBJECT:
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				if (hotSpot(2751, 2731)) {
					walk(1, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[4] = true;
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(2756, 2731, 0);
							c.sendMessage("You swing yourself to the other side");
							this.stop();
						}
					});
				}
				return true;
				
			case APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:		
				if (c.npcId2 != NINJA_MONKEY_NPC) {
					c.sendMessage("You can't do that!");
					return false;
				}
				if (checkLevel(objectId))
					return false;
				c.getPlayerAction().setAction(true);
				c.getPlayerAction().canWalk(false);
				c.getPA().movePlayer(c.getX(), c.getY() + 1, 1);
				tropicalTreeUpdate = 2;
				moveHeight = 0;
				c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
				agilityProgress[5] = true;
				lapBonus = 2700;
				lapFinished();
				return true;
				
			
		}
		return false;
	}
	
	public boolean werewolfCourse(int objectId) {
		switch (objectId) {
			case WEREWOLF_STEPPING_STONE_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3538, 9873)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				} else if (hotSpot(3538, 9875)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				} else if (hotSpot(3538, 9877)) {
					walk(2, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				} else if (hotSpot(3540, 9877)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				} else if (hotSpot(3540, 9879)) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					resetAgilityProgress();
					agilityProgress[0] = true;
				}
				return true;
				
			case WEREWOLF_HURDLE_OBJECT1:
			case WEREWOLF_HURDLE_OBJECT2:
			case WEREWOLF_HURDLE_OBJECT3:
				if (checkLevel(objectId))
					return false;
				if (c.getY() == 9892 || c.getY() == 9895 || c.getY() == 9898) {
					walk(0, 2, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[1] = true;
					Server.getTaskScheduler().schedule(new Task(1) {
						@Override
						protected void execute() {
							if (c.disconnected) {
								stop();
								return;
							}
							c.getPA().movePlayer(c.getX(), c.getY() + 1, 0);
							c.sendMessage("You managed to jump over the hurdle.");
							this.stop();
						}
					});
				}
				return true;
				
			case WEREWOLF_PIPES_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (hotSpot(3538, 9904)) {
					walk(0, 6, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[2] = true;
				} else if (hotSpot(3541, 9904)) {
					walk(0, 6, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[2] = true;
				} else if (hotSpot(3544, 9904)) {
					walk(0, 6, getAnimation(objectId), 748);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[2] = true;
				}
				return true;
				
			case WEREWOLF_SKULL_OBJECT:	
				if (checkLevel(objectId))
					return false;
				if (c.getX() == 3533) {
					walk(-3, 0, getAnimation(objectId), -1);
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[3] = true;
				}
				return true;
				
			case WEREWOLF_SLING_OBJECT:
				if (checkLevel(objectId))
					return false;
				if (c.getX() == 3530) {
					c.getPlayerAction().setAction(true);
					c.getPlayerAction().canWalk(false);
					c.getPA().movePlayer(3528, 9910, 0);
					c.isRunning2 = true;
					zipLine = 2;
					moveHeight = 0;
					c.getPA().addSkillXP(getXp(objectId), c.playerAgility);
					agilityProgress[4] = true;
					agilityProgress[5] = true;
					lapBonus = 2350/30;
					lapFinished();
				}
				return true;
		}
		return false;
	}
	
	static int changeObjectTimer = 10;
	static int rndChance;
	static int newObjectX, newObjectY;
	
	public static void Brimhavenprocess() {
		if (changeObjectTimer > 0) 
			changeObjectTimer --;
		if (changeObjectTimer == 0) {
			rndChance = Misc.random(3);
			if (rndChance == 0) {
				newObjectX = 2794;
				newObjectY = 9579;
			} else if (rndChance == 1) {
				newObjectX = 2783;
				newObjectY = 9579;
			} else if (rndChance == 2) {
				newObjectX = 2783;
				newObjectY = 9568;
			} else if (rndChance == 3) {
				newObjectX = 2794;
				newObjectY = 9568;
			}
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().createObjectHints(newObjectX, newObjectY, 130, 2);
					System.out.println("Updated");
				}
			}
			changeObjectTimer = 10;
		}
	}
	
	
	

	/**
	* exchange tickets for experience points
	*/ 
	
	public void AgilityTicketCounter(Client c, int xp) {
		if (c.getItems().playerHasItem(2996, 1)) { // 500
			c.getItems().deleteItem(2996, 1);
			c.getPA().addSkillXP(xp * 1, c.playerAgility);
			c.getPA().refreshSkill(c.playerAgility);
			c.sendMessage("You got "+ xp * 1 + " Agility Exp!");
		}
		if (c.getItems().playerHasItem(2996, 10)) {
			c.getItems().deleteItem(2996, 10);
			c.getPA().addSkillXP(xp * 10, c.playerAgility);
			c.getPA().refreshSkill(c.playerAgility);
			c.sendMessage("You got "+ xp * 10 + " Agility Exp!");
		}
		if (c.getItems().playerHasItem(2996, 25)) {
			c.getItems().deleteItem(2996, 25);
			c.getPA().addSkillXP(xp * 25, c.playerAgility);
			c.getPA().refreshSkill(c.playerAgility);
			c.sendMessage("You got "+ xp * 25 + " Agility Exp!");
		}
		if (c.getItems().playerHasItem(2996, 100)) {
			c.getItems().deleteItem(2996, 100);
			c.getPA().addSkillXP(xp * 100, c.playerAgility);
			c.getPA().refreshSkill(c.playerAgility);
			c.sendMessage("You got "+ xp * 100 + " Agility Exp!");
		}
		if (c.getItems().playerHasItem(2996, 1000)) { //1m exp
			c.getItems().deleteItem(2996, 1000);
			c.getPA().addSkillXP(xp * 1000, c.playerAgility);
			c.getPA().refreshSkill(c.playerAgility);
			c.sendMessage("You got "+ xp * 1000 + " Agility Exp!");
		} else if (!c.getItems().playerHasItem(2996, 1)) {
			c.sendMessage("You need more agility tickets to get this Exp!");
			return;
		}
	}
	

	public static double getAgilityRunRestore(Client c) {
		return 2260 - c.playerLevel[Config.AGILITY] * 10;
	}
}