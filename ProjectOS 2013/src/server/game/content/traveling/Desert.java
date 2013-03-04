package server.game.content.traveling;

import server.Server;
import server.game.players.Client;
import server.task.Task;

/**
 * Desert
 * @author Andrew
 */

public class Desert {
	
    public static final int[] DRINKABLES = {1823, 1825, 1827, 1829};//add all drinkables here
    private static final int DAMAGE = 4;//change the damage here
    private static final int TIMER = 45000;//change the time you get hurt
    private static final int TIMER_2 = 90000;
    public static final int DESERT_TICKS = 75;
    private static int a = -1;
    private boolean eventExecuted;

    public boolean isEventExecuted() {
           return eventExecuted;
    }

    public void setEventExecuted(boolean eventExecuted) {
           this.eventExecuted = eventExecuted;
    }

    public static void callHeat(final Client c) {
            Server.getTaskScheduler().schedule(new Task(DESERT_TICKS, false) {
                    @Override
                    protected void execute() {
                            if (!c.inDesert()) {
                                    stop();
                                    c.getDesert().setEventExecuted(false);
                                    return;
                            }
                            if (c.disconnected) {
                                stop();
                                return;
                            }
                            if (System.currentTimeMillis() - c.lastDesert > TIMER && c.inDesert()) {
                                    checkWaterskin(c);
                                    c.lastDesert = System.currentTimeMillis();
                                    c.sendMessage("You've been damaged by the heat of the desert.");
                                    c.playerLevel[3] -= DAMAGE;
                                    c.getPA().refreshSkill(3);
                            } else if (c.playerEquipment[c.playerChest] == 1833 && c.playerEquipment[c.playerLegs] == 1835 && c.playerEquipment[c.playerFeet] == 1837 && System.currentTimeMillis() - c.lastDesert > TIMER_2 && c.inDesert()) {
                                    checkWaterskin(c);
                                    c.lastDesert = System.currentTimeMillis();
                                    c.sendMessage("You've been damaged by the heat of the desert.");
                                    c.playerLevel[3] -= DAMAGE;
                                    c.getPA().refreshSkill(3);
                            } else if (c.playerHitpoints < 0) {//for glitchers
                                    c.isDead = true;
                                    c.getDesert().setEventExecuted(false);
                                    stop();
                                    return;
                            }
                    }
            });
    }
			
	public static void checkWaterskin(final Client c) {
		if (c.getItems().playerHasItem(1831, 1)) {//empty waterskin
			return;
		}
		for (int i = 0; i < CACTUS.length; i++) {
		if (c.getItems().playerHasItem(CACTUS[i][1])) {
			a = i;
		    }
		}
		if (a == -1) {
			c.sendMessage("You don't have any waterskins you should get some.");
		    return;
		}
		c.getItems().deleteItem(CACTUS[a][1], 1);
		c.getItems().addItem(CACTUS[a][0], 1);
		c.sendMessage("You drink your waterskin.");
		c.startAnimation(829);
		c.lastDesert = 0;
	}	
	
	private static int[][] CACTUS = {
			{1825, 1823},//waterskin 3
			{1827, 1825},//waterskin 2
			{1829, 1827},//waterskin 1
			{1831, 1829}//waterskin 0
	};
	
	public static void cutCactus(Client c) {
		if (!c.getItems().playerHasItem(946, 1)) {
			c.sendMessage("You can't fill your waterskin without a knife.");
		}
		if (c.getItems().playerHasItem(1823, 1)) {
			c.sendMessage("Your waterskin is already full.");
		}
	    for (int i = 0; i < CACTUS.length; i++) {
            if (c.getItems().playerHasItem(CACTUS[i][0])) {
            	a = i;
           }
        }
	    if (a == -1) {
            c.sendMessage("You don't have any waterskins to fill.");
            return;
        }
	    if (c.getItems().freeSlots() < 1) {
            c.sendMessage("You do not have enough inventory slots to do that.");
            return;
        }
	    c.getItems().addItem(CACTUS[a][1], 1);
	    c.getItems().deleteItem(CACTUS[a][0], 1);
		c.getPA().addSkillXP(10, c.playerWoodcutting);
		c.sendMessage("You fill your waterskin.");
	}
}
