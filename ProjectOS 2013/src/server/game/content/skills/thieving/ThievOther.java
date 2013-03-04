package server.game.content.skills.thieving;

import server.Server;
import server.game.content.skills.SkillHandler;
import server.game.content.skills.thieving.ThievingConfigs.Locks;
import server.game.content.skills.thieving.ThievingConfigs.Locks.Traps;
import server.game.objects.Object;
import server.game.players.PlayerHandler;
import server.task.Task;
import server.util.Misc;

public class ThievOther extends SkillHandler {
	
	public final static int THIEVING_DAMAGE = 10;
	public final static int LOCK_PICK = 1523;
	public final static int THIEVING_ANIMATION = 2246;
	public final static int FAIL_RATE = 30;

	public static void pickLock(int objectType) {
		for (final Locks l : Locks.values()) {
		membersOnly();
		if (!THIEVING) {
			c.sendMessage("This skill is currently disabled");
			return;
		}
		if (!c.getItems().playerHasItem(LOCK_PICK, 1) && l.getReq()) {
			c.sendMessage("You can't pick a lock without a lockpick!");
			return;
		}
		if (c.playerLevel[c.playerThieving] < l.getLevel()) {
			c.sendMessage("You can't pick this lock without having " + l.getReq() + " thieving.");
			return;
		}
		if (objectType == l.getObject()) {
			  c.startAnimation(THIEVING_ANIMATION);
			  c.sendMessage("You attempt to pick the lock...");
			  Server.getTaskScheduler().schedule(new Task(4) {
	              @Override
	              protected void execute() {
	            	  if (c.disconnected) {
	                	  stop();
	                	  return;
	                  }
	            	  if (Misc.random(FAIL_RATE) < 5) {
	  					c.sendMessage("But fail to pick it.");
	  					stop();
	  					return;
	            	  } else if (Misc.random(FAIL_RATE) < 5 && l.getReq()) {
	            		c.sendMessage("But fail to pick it.");
		  				c.getItems().deleteItem2(LOCK_PICK, 1);
		  				stop();
		  				return;
	            	  }
	            	  c.getPA().movePlayer(l.getX(), l.getY(), l.getH());
	            	  c.sendMessage("And manage to pass through it.");
	            	  c.getPA().addSkillXP(l.getXP(), c.playerThieving);
	            	  stop();
	              	}			
			  	});
			}
		}
	}
	
	public void attemptTrap(int objectType) {
		for (final Traps t : Traps.values()) {
			membersOnly();
			if (!THIEVING) {
				c.sendMessage("This skill is currently disabled");
				return;
			}
			if (c.playerLevel[c.playerThieving] < t.getReq()) {
				c.sendMessage("You can't attempt this trap without " + t.getReq() + " thieving.");
				return;
			}
			if (objectType == t.getId()) {
				  c.startAnimation(THIEVING_ANIMATION);
				  c.sendMessage("You attempt to disarm the traps...");
				  Server.getTaskScheduler().schedule(new Task(3) {
		              @Override
		              protected void execute() {
		            	  if (c.disconnected) {
		                	  stop();
		                	  return;
		                  }
		            	  if (Misc.random(FAIL_RATE) < 5) {
		  					c.sendMessage("But fail to disarm it, and get hit by the traps.");
		  					stop();
		  					PlayerHandler.players[c.playerId].setHitDiff(THIEVING_DAMAGE);
		  					Misc.random(THIEVING_DAMAGE);
		  					c.getPA().refreshSkill(3);
		  					PlayerHandler.players[c.playerId].setHitUpdateRequired(true);    
		  					PlayerHandler.players[c.playerId].updateRequired = true;     
		  					return;
		            	  }
		            	  new Object(-1, c.objectX, c.objectY, c.heightLevel, 0, 10, -1, 0);
		            	  c.sendMessage("And manage to disarm it.");
		            	  c.getPA().addSkillXP(t.getXp(), c.playerThieving);
		            	  c.getItems().addItem(t.getRewards(), 1);
		            	  stop();
		              	}			
				  	});
				}
			}
		}
}